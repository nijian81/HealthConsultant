package cn.com.asz.qa;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import cn.com.asz.R;
import cn.com.asz.main.MainActivity;
import cn.com.asz.me.PersonalInfoActivity;

/**
 * Created by nijian on 2015/5/30.
 */
public class ChooseDepartmentActivity extends Activity implements View.OnClickListener {

    int one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0, eight = 0, nine = 0, ten = 0, eleven = 0, twelve = 0, num = 0;
    private List<String> list;
    private ImageView back;
    private Intent intent;
    private RelativeLayout commit;
    private String question;
    private Dialog progressDialog;
    private RelativeLayout neike, waike, erke, fuke, yanke, erbihouke, kouqiangke, pifuke, zhongyike, zhenjiutuinake, xinlizixunshi, qita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_department);

        intent = getIntent();
        list = new ArrayList<>();
        question = intent.getStringExtra("question");
        commit = (RelativeLayout) findViewById(R.id.commit);
        commit.setOnClickListener(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        neike = (RelativeLayout) findViewById(R.id.neike);
        neike.setOnClickListener(this);
        waike = (RelativeLayout) findViewById(R.id.waike);
        waike.setOnClickListener(this);
        erke = (RelativeLayout) findViewById(R.id.erke);
        erke.setOnClickListener(this);
        fuke = (RelativeLayout) findViewById(R.id.fuke);
        fuke.setOnClickListener(this);
        yanke = (RelativeLayout) findViewById(R.id.yanke);
        yanke.setOnClickListener(this);
        erbihouke = (RelativeLayout) findViewById(R.id.erbihouke);
        erbihouke.setOnClickListener(this);
        kouqiangke = (RelativeLayout) findViewById(R.id.kouqiangke);
        kouqiangke.setOnClickListener(this);
        pifuke = (RelativeLayout) findViewById(R.id.pifuke);
        pifuke.setOnClickListener(this);
        zhongyike = (RelativeLayout) findViewById(R.id.zhongyike);
        zhongyike.setOnClickListener(this);
        zhenjiutuinake = (RelativeLayout) findViewById(R.id.zhenjiutuinake);
        zhenjiutuinake.setOnClickListener(this);
        xinlizixunshi = (RelativeLayout) findViewById(R.id.xinlizixunshi);
        xinlizixunshi.setOnClickListener(this);
        qita = (RelativeLayout) findViewById(R.id.qita);
        qita.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit:
                new RemoteDataTask().execute();
                break;
            case R.id.back:
                intent = new Intent();
                intent.setAction("android.intent.action.addQuestionActivity");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
            case R.id.neike:
                if (num == 3 && !list.contains("内科")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (one % 2 == 0) {
                        neike.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("内科");
                        num++;
                    } else {
                        neike.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("内科"));
                        num--;
                    }
                    one++;
                }
                break;
            case R.id.waike:
                if (num == 3 && !list.contains("外科")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (two % 2 == 0) {
                        waike.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("外科");
                        num++;
                    } else {
                        waike.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("外科"));
                        num--;
                    }
                    two++;
                }
                break;
            case R.id.erke:
                if (num == 3 && !list.contains("儿科")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (three % 2 == 0) {
                        erke.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("儿科");
                        num++;
                    } else {
                        erke.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("儿科"));
                        num--;
                    }
                    three++;
                }
                break;
            case R.id.fuke:
                if (num == 3 && !list.contains("妇科")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (four % 2 == 0) {
                        fuke.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("妇科");
                        num++;
                    } else {
                        fuke.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("妇科"));
                        num--;
                    }
                    four++;
                }
                break;
            case R.id.yanke:
                if (num == 3 && !list.contains("眼科")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (five % 2 == 0) {
                        yanke.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("眼科");
                        num++;
                    } else {
                        yanke.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("眼科"));
                        num--;
                    }
                    five++;
                }
                break;
            case R.id.erbihouke:
                if (num == 3 && !list.contains("耳鼻喉科")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (six % 2 == 0) {
                        erbihouke.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("耳鼻喉科");
                        num++;
                    } else {
                        erbihouke.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("耳鼻喉科"));
                        num--;
                    }
                    six++;
                }
                break;
            case R.id.kouqiangke:
                if (num == 3 && !list.contains("口腔科")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (seven % 2 == 0) {
                        kouqiangke.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("口腔科");
                        num++;
                    } else {
                        kouqiangke.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("口腔科"));
                        num--;
                    }
                    seven++;
                }
                break;
            case R.id.pifuke:
                if (num == 3 && !list.contains("皮肤科")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (eight % 2 == 0) {
                        pifuke.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("皮肤科");
                        num++;
                    } else {
                        pifuke.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("皮肤科"));
                        num--;
                    }
                    eight++;
                }
                break;
            case R.id.zhongyike:
                if (num == 3 && !list.contains("中医科")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (nine % 2 == 0) {
                        zhongyike.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("中医科");
                        num++;
                    } else {
                        zhongyike.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("中医科"));
                        num--;
                    }
                    nine++;
                }
                break;
            case R.id.zhenjiutuinake:
                if (num == 3 && !list.contains("针灸推拿科")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (ten % 2 == 0) {
                        zhenjiutuinake.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("针灸推拿科");
                        num++;
                    } else {
                        zhenjiutuinake.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("针灸推拿科"));
                        num--;
                    }
                    ten++;
                }
                break;
            case R.id.xinlizixunshi:
                if (num == 3 && !list.contains("心理咨询室")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (eleven % 2 == 0) {
                        xinlizixunshi.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("心理咨询室");
                        num++;
                    } else {
                        xinlizixunshi.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("心理咨询室"));
                        num--;
                    }
                    eleven++;
                }
                break;
            case R.id.qita:
                if (num == 3 && !list.contains("其他")) {
                    Toast toast = Toast.makeText(this, "最多可以添加三个标签哦~", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (twelve % 2 == 0) {
                        qita.setBackgroundResource(R.drawable.corners_bq_xz);
                        list.add("其他");
                        num++;
                    } else {
                        qita.setBackgroundResource(R.drawable.corners_bq);
                        list.remove(list.indexOf("其他"));
                        num--;
                    }
                    twelve++;
                }
                break;
        }
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            AVObject post = new AVObject("Question");
            post.put("content", question);
            for (int i = 0; i < list.size(); i++) {
                post.put("department" + (i + 1), list.get(i));
            }
            post.put("asker", AVUser.getCurrentUser().getUsername());
            post.saveInBackground(new SaveCallback() {
                public void done(AVException e) {
                    if (e == null) {
                        // 保存成功
                        Toast toast = Toast.makeText(ChooseDepartmentActivity.this, "问题提交成功了哦~", Toast.LENGTH_LONG);
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
            progressDialog = ProgressDialog.show(ChooseDepartmentActivity.this, "", "数据加载中，请稍后...", true);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            intent = new Intent(ChooseDepartmentActivity.this, MainActivity.class);
            startActivity(intent);
            //新的activity进入动画，旧的activity退出的动画
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        }
    }

    @Override
    protected void onResume() {
        list = new ArrayList<>();
        super.onResume();
    }

}