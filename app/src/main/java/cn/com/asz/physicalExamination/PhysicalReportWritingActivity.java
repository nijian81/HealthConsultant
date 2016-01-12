package cn.com.asz.physicalExamination;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.asz.R;
import cn.com.asz.main.MainActivity;

/**
 * Created by nijian on 2015/5/30.
 */
public class PhysicalReportWritingActivity extends Activity implements View.OnClickListener {

    private TextView save;
    private ImageView back;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.psysical_report_writing);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        save = (TextView) findViewById(R.id.save);
        save.setOnClickListener(this);
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
            case R.id.save:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
        }
    }
}
