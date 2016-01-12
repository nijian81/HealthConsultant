package cn.com.asz.qa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.com.asz.R;

/**
 * Created by neokree on 16/12/14.
 */
public class AnswerMeFragment extends Fragment implements AbsListView.OnScrollListener {

    private List<AVObject> list;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyAdapter myAdapter;
    private Dialog progressDialog;
    private int preLast;
    private static int showNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        progressDialog = ProgressDialog.show(getActivity(), "", "数据加载中，请稍后...", true);
        progressDialog.setCancelable(true);
        new RemoteDataTask().execute();
        myAdapter = new MyAdapter();
        list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.answer_me_fragment, container, false);

        // 设置初期数据
        list = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showNum = 0;
                new RemoteDataTask().execute();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        // 设置ListView
        listView = (ListView) rootView.findViewById(R.id.listView);
        myAdapter.setArrayList(list);
        listView.setAdapter(myAdapter);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AVObject avObject = (AVObject) adapterView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.qaDetailActivity");
                intent.putExtra("asker", avObject.getString("asker"));
                intent.putExtra("questionID", avObject.getObjectId());
                intent.putExtra("content", avObject.getString("content"));
                intent.putExtra("answerNum", avObject.getInt("answerNum"));
                Date date = new Date(avObject.getCreatedAt().getTime());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String time = format.format(date);
                intent.putExtra("time_param", time);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    public class MyAdapter extends BaseAdapter {

        List<AVObject> list;
        private Context context;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int index) {
            return list.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View view, ViewGroup arg2) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.answer_me_item, null);
            TextView question = (TextView) view.findViewById(R.id.question);
            TextView time = (TextView) view.findViewById(R.id.time);
            TextView answerNum = (TextView) view.findViewById(R.id.answerNum);
            question.setText(list.get(index).getString("content"));
            Date date = new Date(list.get(index).getCreatedAt().getTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String timeParam = format.format(date);
            time.setText(timeParam);
            answerNum.setText(list.get(index).getInt("answerNum") + "");
            return view;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public void setArrayList(List<AVObject> list) {
            this.list = list;
        }

    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            list = findAVObjects();
            myAdapter.setArrayList(list);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            // 设置初期数据
            progressDialog.dismiss();
            swipeRefreshLayout.setRefreshing(false);
            myAdapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    AVObject avObject = (AVObject) adapterView.getItemAtPosition(position);
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.qaDetailActivity");
                    intent.putExtra("asker", avObject.getString("asker"));
                    intent.putExtra("questionID", avObject.getObjectId());
                    intent.putExtra("content", avObject.getString("content"));
                    intent.putExtra("answerNum", avObject.getInt("answerNum"));
                    Date date = new Date(avObject.getCreatedAt().getTime());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String time = format.format(date);
                    intent.putExtra("time_param", time);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }
            });
        }
    }

    public static List<AVObject> findAVObjects() {
        // 查询当前AVObject列表
        AVQuery<AVObject> query = new AVQuery<>("Question");
        // 按照更新时间降序排序
        query.orderByDescending("updatedAt");
        // 最大返回1000条
        showNum = showNum + 10;
        query.limit(showNum);
        query.whereEqualTo("asker", AVUser.getCurrentUser().getUsername());
        try {
            return query.find();
        } catch (AVException exception) {
            Log.e("tag", "Query AVObjects failed.", exception);
            return Collections.emptyList();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        switch (view.getId()) {
            case R.id.listView:
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        if (lastItem % 10 == 0) {
                            new RemoteDataTask().execute();
                            progressDialog = ProgressDialog.show(getActivity(), "", "数据加载中，请稍后...", true);
                            progressDialog.setCancelable(true);
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "没有更多了哦~", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        preLast = lastItem;
                    }
                }
        }
    }
}
