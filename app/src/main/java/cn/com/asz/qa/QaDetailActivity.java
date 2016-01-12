package cn.com.asz.qa;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.com.asz.R;
import cn.com.asz.main.MainActivity;
import cn.com.asz.utils.RoundedImageView;
import cn.com.asz.utils.StatusUtils;

/**
 * Created by nijian on 2015/5/30.
 */
public class QaDetailActivity extends Activity implements View.OnClickListener, AbsListView.OnScrollListener {

    private RelativeLayout addAnswer;
    private ImageView back;
    private List<AVObject> list;
    private ListView listView;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Dialog progressDialog;
    private Intent intent;
    private String asker, content, questionID, time;
    int answerNum;
    private TextView askerTextView, questionTextView, answerNumTextView, timeTextView;
    static String questionIDParam;
    private int preLast;
    private static int showNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qa_detail);

        intent = getIntent();
        asker = intent.getStringExtra("asker");
        content = intent.getStringExtra("content");
        answerNum = intent.getIntExtra("answerNum", 0);
        questionID = intent.getStringExtra("questionID");
        time = intent.getStringExtra("time_param");
        questionIDParam = questionID;
        answerNumTextView = (TextView) findViewById(R.id.answerNum);
        questionTextView = (TextView) findViewById(R.id.question);
        askerTextView = (TextView) findViewById(R.id.asker);
        timeTextView = (TextView) findViewById(R.id.time);
        timeTextView.setText(time);
        answerNumTextView.setText(answerNum + "");
        askerTextView.setText(asker + "的提问");
        addAnswer = (RelativeLayout) findViewById(R.id.addAnswer);
        addAnswer.setOnClickListener(this);
        questionTextView.setText(content);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showNum = 0;
                new RemoteDataTask().execute();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        // 设置ListView
        listView = (ListView) findViewById(R.id.listView);
        myAdapter = new MyAdapter();
        list = new ArrayList<>();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        progressDialog = ProgressDialog.show(this, "", "数据加载中，请稍后...", true);
        progressDialog.setCancelable(true);
        new RemoteDataTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAdapter.setArrayList(list);
        myAdapter.setContext(QaDetailActivity.this);
        listView.setAdapter(myAdapter);
        listView.setOnScrollListener(this);
        questionIDParam = questionID;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
            case R.id.addAnswer:
                intent = new Intent();
                intent.putExtra("asker", asker);
                intent.putExtra("questionID", questionID);
                intent.setAction("android.intent.action.answerQuestionActivity");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_down, R.anim.exit_to_up);
                break;
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
        public View getView(int index, View view, ViewGroup arg2) {

            view = LayoutInflater.from(QaDetailActivity.this).inflate(R.layout.qa_detail_item, null);
            TextView answer = (TextView) view.findViewById(R.id.answer);
            TextView honeyName = (TextView) view.findViewById(R.id.honeyName);
            TextView time = (TextView) view.findViewById(R.id.time);
            final RoundedImageView portrait = (RoundedImageView) view.findViewById(R.id.portrait);
            answer.setText(this.list.get(index).getString("content"));
            honeyName.setText(this.list.get(index).getString("sponsor"));
            Date date = new Date(this.list.get(index).getCreatedAt().getTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String time_param = format.format(date);
            time.setText(time_param);
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(QaDetailActivity.this));
            AVQuery<AVUser> query = AVUser.getQuery();
            query.whereEqualTo("username", this.list.get(index).getString("sponsor"));
            query.findInBackground(new FindCallback<AVUser>() {
                public void done(List<AVUser> avObjects, AVException e) {
                    if (e == null) {
                        Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                        ImageLoader.getInstance().displayImage(avObjects.get(0).getAVFile("portrait").getUrl(), portrait, StatusUtils.normalImageOptions);
                    } else {
                        Log.d("失败", "查询错误: " + e.getMessage());
                    }
                }
            });
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
            QaDetailActivity.this.progressDialog.dismiss();
            swipeRefreshLayout.setRefreshing(false);
            myAdapter.notifyDataSetChanged();
        }
    }

    public static List<AVObject> findAVObjects() {
        // 查询当前AVObject列表
        AVQuery<AVObject> query = new AVQuery<>("Answer");
        // 按照更新时间降序排序
        query.orderByDescending("createdAt");
        // 最大返回100条
        showNum = showNum + 10;
        query.limit(showNum);
        query.whereEqualTo("questionID", QaDetailActivity.questionIDParam);
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
                            progressDialog = ProgressDialog.show(this, "", "数据加载中，请稍后...", true);
                            progressDialog.setCancelable(true);
                        } else {
                            Toast toast = Toast.makeText(this, "没有更多了哦~", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        preLast = lastItem;
                    }
                }
        }
    }
}
