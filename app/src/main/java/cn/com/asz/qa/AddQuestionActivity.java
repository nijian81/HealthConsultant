package cn.com.asz.qa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.asz.R;
import cn.com.asz.main.MainActivity;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by nijian on 2015/5/30.
 */
public class AddQuestionActivity extends Activity implements View.OnClickListener {

    private TextView next;
    private Intent intent;
    private ImageView back;
    private EditText question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);
        question = (EditText) findViewById(R.id.question);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (question.getText().toString().length() < 4) {
                    Toast toast = Toast.makeText(this, "请不要提交字数少于4的问题哦~", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
                intent = new Intent();
                intent.setAction("android.intent.action.chooseDepartmentActivity");
                intent.putExtra("question", question.getText().toString());
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.back:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
        }
    }
}