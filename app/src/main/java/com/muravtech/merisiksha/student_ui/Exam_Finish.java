package com.muravtech.merisiksha.student_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.muravtech.merisiksha.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Exam_Finish extends BaseActivity {
    @BindView(R.id.tvExamResult)
    TextView tvShowResult;
    String totalQuestions;
    int totalNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_exam__finish);
        ButterKnife.bind(this);

        totalQuestions = getIntent().getStringExtra("score");
        totalNumber = Integer.parseInt(totalQuestions) * 5;
        // String numberObtain = getIntent().getStringExtra("examResult");
        // sGroupId = getIntent().getStringExtra("groupID");
        tvShowResult = findViewById(R.id.tvExamResult);
        tvShowResult.setText("Result " + "Marks : " + String.valueOf(totalNumber));

    }


    @OnClick({R.id.iv_back, R.id.submitAnswer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.submitAnswer:
                startActivity(new Intent(Exam_Finish.this, StudentDashBoardScreenActivity.class));
                // SharedPrefManager.getInstance(v.getContext()).logoutExamValue();
                //preferences.setStringValue(AppPreferences.sGroupId, sGroupId);
                finish();
                break;
        }
    }
}
