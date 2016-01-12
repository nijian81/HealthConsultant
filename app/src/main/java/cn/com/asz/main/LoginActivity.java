package cn.com.asz.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import cn.com.asz.R;

/**
 * Created by nijian on 2015/6/1.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView forget_password;
    private ImageButton back;
    private Intent intent;
    private RelativeLayout complete;
    private EditText username, password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        forget_password = (TextView) findViewById(R.id.forget_password);
        forget_password.setOnClickListener(this);
        complete = (RelativeLayout) findViewById(R.id.complete);
        complete.setOnClickListener(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        // 隐藏输入法
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(LoginActivity.this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(username.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(password.getWindowToken(), 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.complete:
                progressDialog = ProgressDialog.show(this, "", "数据加载中，请稍后...", true);
                AVUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback<AVUser>() {
                    public void done(AVUser user, AVException e) {
                        if (user != null) {
                            progressDialog.dismiss();
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast toast = Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
                break;
            case R.id.back:
                intent = new Intent();
                intent.setAction("android.intent.action.Initial");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
            case R.id.forget_password:
                intent = new Intent();
                intent.setAction("android.intent.action.ForgetPassword");
                startActivity(intent);
                break;
        }
    }
}
