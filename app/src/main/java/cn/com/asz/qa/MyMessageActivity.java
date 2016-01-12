package cn.com.asz.qa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import cn.com.asz.R;
import cn.com.asz.main.MainActivity;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by nijian on 2015/5/30.
 */
public class MyMessageActivity extends ActionBarActivity implements MaterialTabListener, View.OnClickListener {

    private Intent intent;
    private ImageView back;
    private MaterialTabHost tabHost;
    private ViewPager pager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_message);
        getSupportActionBar().hide();

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        pager = (ViewPager) this.findViewById(R.id.pager);
        // init view pager
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });
        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(getResources().getDrawable(R.mipmap.cellphone))
//                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
    }


    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

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
        }
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new AnswerMeFragment();
                case 1:
                    return new QuestionMeAgainFragment();
                case 2:
                    return new InformFragment();
                default:
                    return new AnswerMeFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "回答我的";
                    break;
                case 1:
                    title = "追问我的";
                    break;
                case 2:
                    title = "通知";
                    break;
            }
            return title;
        }
    }
}