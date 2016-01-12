package cn.com.asz.main;

import cn.com.asz.R;
import cn.jpush.android.api.JPushInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class ReceiverActivity extends Activity implements View.OnClickListener {

    private ImageView back;
    private TextView content, title;
    private String title_jPush, content_jPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiver);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        content = (TextView) findViewById(R.id.content);
        title = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            title_jPush = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            content_jPush = bundle.getString(JPushInterface.EXTRA_ALERT);
            content.setText(content_jPush);
            title.setText(title_jPush);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
