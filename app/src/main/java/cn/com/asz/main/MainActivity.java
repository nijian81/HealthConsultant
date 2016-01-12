package cn.com.asz.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

import cn.com.asz.R;
import cn.com.asz.library.LibraryFragment;
import cn.com.asz.me.MeFragment;
import cn.com.asz.more.MoreFragment;
import cn.com.asz.physicalExamination.PhysicalExaminationFragment;
import cn.com.asz.qa.QaFragment;
import cn.jpush.android.api.JPushInterface;


public class MainActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPaper;
    private List<Fragment> list = new ArrayList<>();
    private FragmentPagerAdapter fragmentPaperAdapter;
    private TextView library_text, psy_exam_text, me_text, qa_text, more_text;
    private RelativeLayout library, psy_exam, me, qa, more;
    private ImageView library_image, psyExam_image, me_image, qa_image, more_image;
    private LibraryFragment libraryFragment;
    private PhysicalExaminationFragment physicalExaminationFragment;
    private MeFragment meFragment;
    private QaFragment qaFragment;
    private MoreFragment moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        library = (RelativeLayout) findViewById(R.id.library);
        library.setOnClickListener(this);
        psy_exam = (RelativeLayout) findViewById(R.id.psy_exam);
        psy_exam.setOnClickListener(this);
        me = (RelativeLayout) findViewById(R.id.me);
        me.setOnClickListener(this);
        qa = (RelativeLayout) findViewById(R.id.qa);
        qa.setOnClickListener(this);
        more = (RelativeLayout) findViewById(R.id.more);
        more.setOnClickListener(this);
        //底部导航条的文字和图片
        library_image = (ImageView) findViewById(R.id.library_image);
        psyExam_image = (ImageView) findViewById(R.id.psyExam_image);
        me_image = (ImageView) findViewById(R.id.me_image);
        qa_image = (ImageView) findViewById(R.id.qa_image);
        more_image = (ImageView) findViewById(R.id.more_image);
        library_text = (TextView) findViewById(R.id.library_text);
        psy_exam_text = (TextView) findViewById(R.id.psy_exam_text);
        me_text = (TextView) findViewById(R.id.me_text);
        qa_text = (TextView) findViewById(R.id.qa_text);
        more_text = (TextView) findViewById(R.id.more_text);
        //初始化fragment
        libraryFragment = new LibraryFragment();
        meFragment = new MeFragment();
        moreFragment = new MoreFragment();
        physicalExaminationFragment = new PhysicalExaminationFragment();
        qaFragment = new QaFragment();
        library_image.setImageResource(R.drawable.library_xz);
        library_text.setTextColor(Color.rgb(69, 192, 26));
        BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(library);
        badgeView.setBadgeCount(3);
        viewPaper = (ViewPager) findViewById(R.id.viewPaper);
        viewPaper.setOnPageChangeListener(this);
        list.add(libraryFragment);
        list.add(physicalExaminationFragment);
        list.add(meFragment);
        list.add(qaFragment);
        list.add(moreFragment);
        fragmentPaperAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }
        };
        viewPaper.setAdapter(fragmentPaperAdapter);
        //初始化JPUSH
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //leancloud
        AVOSCloud.setDebugLogEnabled(true);
        AVOSCloud.initialize(this, "srqndt5aqk46096mrk80pkpjilyveideq55e1t1ilht8jvku", "25t9c2949uuuql2dxkmxly0sbasjk9wo1dpilogbeq8lj40c");
        AVAnalytics.trackAppOpened(getIntent());
        AVAnalytics.enableCrashReport(this, true);
        AVOSCloud.setLastModifyEnabled(true);
        //判断是否有用户登录
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            // 允许用户使用应用
        } else {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.Initial");
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.library:
                viewPaper.setCurrentItem(0, false);
                library_image.setImageResource(R.drawable.library_xz);
                psyExam_image.setImageResource(R.drawable.psy_exam);
                me_image.setImageResource(R.drawable.me);
                qa_image.setImageResource(R.drawable.qa);
                more_image.setImageResource(R.drawable.more);
                library_text.setTextColor(Color.rgb(69, 192, 26));
                psy_exam_text.setTextColor(Color.rgb(153, 153, 153));
                me_text.setTextColor(Color.rgb(153, 153, 153));
                qa_text.setTextColor(Color.rgb(153, 153, 153));
                more_text.setTextColor(Color.rgb(153, 153, 153));
                break;
            case R.id.psy_exam:
                viewPaper.setCurrentItem(1, false);
                library_image.setImageResource(R.drawable.library);
                psyExam_image.setImageResource(R.drawable.psy_exam_xz);
                me_image.setImageResource(R.drawable.me);
                qa_image.setImageResource(R.drawable.qa);
                more_image.setImageResource(R.drawable.more);
                library_text.setTextColor(Color.rgb(153, 153, 153));
                psy_exam_text.setTextColor(Color.rgb(69, 192, 26));
                me_text.setTextColor(Color.rgb(153, 153, 153));
                qa_text.setTextColor(Color.rgb(153, 153, 153));
                more_text.setTextColor(Color.rgb(153, 153, 153));
                break;
            case R.id.me:
                viewPaper.setCurrentItem(2, false);
                library_image.setImageResource(R.drawable.library);
                psyExam_image.setImageResource(R.drawable.psy_exam);
                me_image.setImageResource(R.drawable.me_xz);
                qa_image.setImageResource(R.drawable.qa);
                more_image.setImageResource(R.drawable.more);
                library_text.setTextColor(Color.rgb(153, 153, 153));
                psy_exam_text.setTextColor(Color.rgb(153, 153, 153));
                me_text.setTextColor(Color.rgb(69, 192, 26));
                qa_text.setTextColor(Color.rgb(153, 153, 153));
                more_text.setTextColor(Color.rgb(153, 153, 153));
                break;
            case R.id.qa:
                viewPaper.setCurrentItem(3, false);
                library_text.setTextColor(Color.rgb(153, 153, 153));
                psy_exam_text.setTextColor(Color.rgb(153, 153, 153));
                me_text.setTextColor(Color.rgb(153, 153, 153));
                qa_text.setTextColor(Color.rgb(69, 192, 26));
                more_text.setTextColor(Color.rgb(153, 153, 153));
                library_image.setImageResource(R.drawable.library);
                psyExam_image.setImageResource(R.drawable.psy_exam);
                me_image.setImageResource(R.drawable.me);
                qa_image.setImageResource(R.drawable.qa_xz);
                more_image.setImageResource(R.drawable.more);
                break;
            case R.id.more:
                viewPaper.setCurrentItem(4, false);
                library_image.setImageResource(R.drawable.library);
                psyExam_image.setImageResource(R.drawable.psy_exam);
                me_image.setImageResource(R.drawable.me);
                qa_image.setImageResource(R.drawable.qa);
                more_image.setImageResource(R.drawable.more_xz);
                library_text.setTextColor(Color.rgb(153, 153, 153));
                psy_exam_text.setTextColor(Color.rgb(153, 153, 153));
                me_text.setTextColor(Color.rgb(153, 153, 153));
                qa_text.setTextColor(Color.rgb(153, 153, 153));
                more_text.setTextColor(Color.rgb(69, 192, 26));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (position) {
            case 0:
                library_image.setImageResource(R.drawable.library_xz);
                psyExam_image.setImageResource(R.drawable.psy_exam);
                me_image.setImageResource(R.drawable.me);
                qa_image.setImageResource(R.drawable.qa);
                more_image.setImageResource(R.drawable.more);
                library_text.setTextColor(Color.rgb(69, 192, 26));
                psy_exam_text.setTextColor(Color.rgb(153, 153, 153));
                me_text.setTextColor(Color.rgb(153, 153, 153));
                qa_text.setTextColor(Color.rgb(153, 153, 153));
                more_text.setTextColor(Color.rgb(153, 153, 153));
                break;
            case 1:
                library_image.setImageResource(R.drawable.library);
                psyExam_image.setImageResource(R.drawable.psy_exam_xz);
                me_image.setImageResource(R.drawable.me);
                qa_image.setImageResource(R.drawable.qa);
                more_image.setImageResource(R.drawable.more);
                library_text.setTextColor(Color.rgb(153, 153, 153));
                psy_exam_text.setTextColor(Color.rgb(69, 192, 26));
                me_text.setTextColor(Color.rgb(153, 153, 153));
                qa_text.setTextColor(Color.rgb(153, 153, 153));
                more_text.setTextColor(Color.rgb(153, 153, 153));
                break;
            case 2:
                library_image.setImageResource(R.drawable.library);
                psyExam_image.setImageResource(R.drawable.psy_exam);
                me_image.setImageResource(R.drawable.me_xz);
                qa_image.setImageResource(R.drawable.qa);
                more_image.setImageResource(R.drawable.more);
                library_text.setTextColor(Color.rgb(153, 153, 153));
                psy_exam_text.setTextColor(Color.rgb(153, 153, 153));
                me_text.setTextColor(Color.rgb(69, 192, 26));
                qa_text.setTextColor(Color.rgb(153, 153, 153));
                more_text.setTextColor(Color.rgb(153, 153, 153));
                break;
            case 3:
                library_text.setTextColor(Color.rgb(153, 153, 153));
                psy_exam_text.setTextColor(Color.rgb(153, 153, 153));
                me_text.setTextColor(Color.rgb(153, 153, 153));
                qa_text.setTextColor(Color.rgb(69, 192, 26));
                more_text.setTextColor(Color.rgb(153, 153, 153));
                library_image.setImageResource(R.drawable.library);
                psyExam_image.setImageResource(R.drawable.psy_exam);
                me_image.setImageResource(R.drawable.me);
                qa_image.setImageResource(R.drawable.qa_xz);
                more_image.setImageResource(R.drawable.more);
                break;
            case 4:
                library_image.setImageResource(R.drawable.library);
                psyExam_image.setImageResource(R.drawable.psy_exam);
                me_image.setImageResource(R.drawable.me);
                qa_image.setImageResource(R.drawable.qa);
                more_image.setImageResource(R.drawable.more_xz);
                library_text.setTextColor(Color.rgb(153, 153, 153));
                psy_exam_text.setTextColor(Color.rgb(153, 153, 153));
                me_text.setTextColor(Color.rgb(153, 153, 153));
                qa_text.setTextColor(Color.rgb(153, 153, 153));
                more_text.setTextColor(Color.rgb(69, 192, 26));
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }
}
