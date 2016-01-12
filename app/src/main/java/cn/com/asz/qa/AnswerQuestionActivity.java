package cn.com.asz.qa;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.com.asz.R;
import cn.com.asz.main.MainActivity;
import cn.com.asz.utils.StatusUtils;

/**
 * Created by nijian on 2015/5/30.
 */
public class AnswerQuestionActivity extends Activity implements View.OnClickListener {

    private TextView commit, asker;
    private Intent intent;
    private ImageView back;
    private EditText answer;
    private String askerParam, questionID;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_question);

        intent = getIntent();
        askerParam = intent.getStringExtra("asker");
        questionID = intent.getStringExtra("questionID");
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        commit = (TextView) findViewById(R.id.commit);
        commit.setOnClickListener(this);
        asker = (TextView) findViewById(R.id.asker);
        asker.setText(askerParam + "的提问");
        answer = (EditText) findViewById(R.id.answer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit:
                if (answer.getText().toString().length() < 4) {
                    Toast toast = Toast.makeText(this, "请不要提交字数少于4的问题哦~", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
                new RemoteDataTask().execute();
                break;
            case R.id.back:
                intent = new Intent();
                intent.setAction("android.intent.action.qaDetailActivity");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_up, R.anim.exit_to_down);
                break;
        }
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            AVObject post = new AVObject("Answer");
            AVQuery<AVObject> query = new AVQuery<AVObject>("Question");
            query.whereEqualTo("objectId", questionID);
            query.findInBackground(new FindCallback<AVObject>() {
                public void done(List<AVObject> avObjects, AVException e) {
                    if (e == null) {
                        Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                        avObjects.get(0).increment("answerNum");
                        avObjects.get(0).saveInBackground();
                    } else {
                        Log.d("失败", "查询错误: " + e.getMessage());
                    }
                }
            });
            post.put("content", answer.getText().toString());
            post.put("sponsor", AVUser.getCurrentUser().getUsername());
            post.put("questionID", questionID);
            post.saveInBackground(new SaveCallback() {
                public void done(AVException e) {
                    if (e == null) {
                        // 保存成功
                        Toast toast = Toast.makeText(AnswerQuestionActivity.this, "答案提交成功了哦~", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        // 保存失败，输出错误信息
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AnswerQuestionActivity.this, "", "数据加载中，请稍后...", true);
            progressDialog.setCancelable(true);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            intent = new Intent(AnswerQuestionActivity.this, MainActivity.class);
            startActivity(intent);
            //新的activity进入动画，旧的activity退出的动画
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        }
    }

}