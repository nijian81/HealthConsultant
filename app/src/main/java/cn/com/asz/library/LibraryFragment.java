package cn.com.asz.library;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.com.asz.R;
import cn.com.asz.utils.StatusUtils;

/**
 * Created by nijian on 2015/5/30.
 */
public class LibraryFragment extends Fragment implements AbsListView.OnScrollListener {

    private List<AVObject> list;
    private ListView listView;
    private MyAdapter myAdapter;
    private Dialog progressDialog;
    private String time;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static int showNum;
    private int preLast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        myAdapter = new MyAdapter();
        LibraryFragment.this.progressDialog =
                ProgressDialog.show(getActivity(), "", "数据加载中，请稍后...", true);
        progressDialog.setCancelable(true);
        new RemoteDataTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.library_fragment, container, false);

        // 设置ListView
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showNum = 0;
                new RemoteDataTask().execute();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        listView = (ListView) rootView.findViewById(R.id.listView);
        myAdapter.setList(list);
        listView.setAdapter(myAdapter);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AVObject avObject = (AVObject) adapterView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("url", avObject.getAVFile("Picture").getUrl());
                intent.putExtra("title_param", avObject.getString("Title"));
                Date date = new Date(avObject.getCreatedAt().getTime());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                time = format.format(date);
                intent.putExtra("time_param", time);
                intent.putExtra("content_param", avObject.getString("content"));
                intent.setAction("android.intent.action.LibDetail");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        // AVObject Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onResume() {
        // AVObject Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onStop() {
        // AVObject Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        switch (absListView.getId()) {
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

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            list = findAVObjects();
            myAdapter.setList(list);
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
            LibraryFragment.this.progressDialog.dismiss();
            swipeRefreshLayout.setRefreshing(false);
            myAdapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    AVObject avObject = (AVObject) adapterView.getItemAtPosition(position);
                    Intent intent = new Intent();
                    intent.putExtra("url", avObject.getAVFile("Picture").getUrl());
                    intent.putExtra("title_param", avObject.getString("Title"));
                    Date date = new Date(avObject.getCreatedAt().getTime());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    time = format.format(date);
                    intent.putExtra("time_param", time);
                    intent.putExtra("content_param", avObject.getString("content"));
                    intent.setAction("android.intent.action.LibDetail");
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }
            });
        }
    }

    public static List<AVObject> findAVObjects() {
        // 查询当前AVObject列表
        AVQuery<AVObject> query = new AVQuery<>("LibraryNews");
        // 按照更新时间降序排序
        query.orderByDescending("createdAt");
        // 最大返回1000条
        showNum = showNum + 10;
        query.limit(showNum);
        try {
            return query.find();
        } catch (AVException exception) {
            Log.e("tag", "Query AVObjects failed.", exception);
            return Collections.emptyList();
        }
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
        public View getView(int position, View view, ViewGroup arg2) {

            view = LayoutInflater.from(getActivity()).inflate(R.layout.library_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic);
            TextView textView = (TextView) view.findViewById(R.id.text);
            TextView time = (TextView) view.findViewById(R.id.time);
            textView.setText(this.list.get(position).getString("Title"));
            Date date = new Date(this.list.get(position).getCreatedAt().getTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String time_param = format.format(date);
            time.setText(time_param);
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
            ImageLoader.getInstance().displayImage(this.list.get(position).getAVFile("Picture").getUrl(), imageView, StatusUtils.normalImageOptions);

            return view;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public void setList(List<AVObject> list) {
            this.list = list;
        }
    }

}

