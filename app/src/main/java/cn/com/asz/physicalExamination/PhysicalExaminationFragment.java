package cn.com.asz.physicalExamination;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.com.asz.R;

/**
 * Created by nijian on 2015/5/30.
 */
public class PhysicalExaminationFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout psy_exam_reading, psy_exam_look, psy_exam_write, psy_exam_analyse;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.psysical_examination, container, false);

        psy_exam_analyse = (RelativeLayout) rootView.findViewById(R.id.psy_exam_analyse);
        psy_exam_analyse.setOnClickListener(this);
        psy_exam_reading = (RelativeLayout) rootView.findViewById(R.id.psy_exam_reading);
        psy_exam_reading.setOnClickListener(this);
        psy_exam_look = (RelativeLayout) rootView.findViewById(R.id.psy_exam_look);
        psy_exam_look.setOnClickListener(this);
        psy_exam_write = (RelativeLayout) rootView.findViewById(R.id.psy_exam_write);
        psy_exam_write.setOnClickListener(this);

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
            case R.id.psy_exam_analyse:
                intent = new Intent();
                intent.setAction("android.intent.action.psyExamReportAnalyse");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.psy_exam_write:
                intent = new Intent();
                intent.setAction("android.intent.action.psyExamReportWrite");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.psy_exam_look:
                intent = new Intent();
                intent.setAction("android.intent.action.psyExamReportLook");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.psy_exam_reading:
                intent = new Intent();
                intent.setAction("android.intent.action.psyExamReportRead");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
        }
    }
}

