package cn.com.asz.library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.com.asz.R;
import cn.com.asz.main.MainActivity;
import cn.com.asz.utils.StatusUtils;

/**
 * Created by nijian on 2015/5/30.
 */
public class LibraryDetailActivity extends Activity implements View.OnClickListener {

    private ImageView back, pic;
    private Intent intent;
    private String url, title_param,time_param,content_param;
    private TextView title,time,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.library_detail);
        intent = getIntent();
        url = intent.getStringExtra("url");
        title_param = intent.getStringExtra("title_param");
        time_param = intent.getStringExtra("time_param");
        content_param  =intent.getStringExtra("content_param");
        content  = (TextView)findViewById(R.id.content);
        content.setText(content_param);
        time = (TextView)findViewById(R.id.time);
        time.setText(time_param);
        title = (TextView) findViewById(R.id.title);
        title.setText(title_param);
        pic = (ImageView) findViewById(R.id.pic);
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        ImageLoader.getInstance().displayImage(url, pic, StatusUtils.normalImageOptions);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
        }
    }
}
