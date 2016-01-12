package cn.com.asz.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import cn.com.asz.R;
import cn.com.asz.utils.BasicHealthInfoAdapter;
import cn.com.asz.utils.BasicHealthInfoItem;
import cn.com.asz.utils.RoundedImageView;
import cn.com.asz.utils.StatusUtils;

/**
 * Created by nijian on 2015/5/30.
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    private BasicHealthInfoAdapter basicHealthInfoAdapter;
    private ListView listView;
    private LinearLayout basic_health_info;
    private ArrayList<BasicHealthInfoItem> list;
    private RelativeLayout me;
    private Intent intent;
    private TextView honeyName;
    private RoundedImageView portrait;
    private AVUser currentUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.me, container, false);

        me = (RelativeLayout) rootView.findViewById(R.id.me);
        me.setOnClickListener(this);
        portrait = (RoundedImageView) rootView.findViewById(R.id.portrait);
        basic_health_info = (LinearLayout) rootView.findViewById(R.id.basic_health_info);
        basic_health_info.setOnClickListener(this);
        honeyName = (TextView) rootView.findViewById(R.id.honeyName);
        currentUser = AVUser.getCurrentUser();
        currentUser.getString("portrait");
        honeyName.setText(currentUser.getUsername());
        if (currentUser.getAVFile("portrait") != null) {
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
            ImageLoader.getInstance().displayImage(currentUser.getAVFile("portrait").getUrl(), portrait, StatusUtils.normalImageOptions);
        }
        list = new ArrayList<>();
        list.add(new BasicHealthInfoItem("身高：170cm"));
        list.add(new BasicHealthInfoItem("年龄：45岁"));
        list.add(new BasicHealthInfoItem("心率：69"));
        list.add(new BasicHealthInfoItem("BMI：20"));
        list.add(new BasicHealthInfoItem("体重指数：15"));
        list.add(new BasicHealthInfoItem("体重：70kg"));
        basicHealthInfoAdapter = new BasicHealthInfoAdapter();
        basicHealthInfoAdapter.setContext(getActivity());
        basicHealthInfoAdapter.setArrayList(list);
        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(basicHealthInfoAdapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        if (currentUser.getAVFile("portrait") != null) {
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
            ImageLoader.getInstance().displayImage(currentUser.getAVFile("portrait").getUrl(), portrait, StatusUtils.normalImageOptions);
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.basic_health_info:
                intent = new Intent();
                intent.setAction("android.intent.action.BasicHealthInfo");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.me:
                intent = new Intent();
                intent.setAction("android.intent.action.personalInfoActivity");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
        }
    }
}

