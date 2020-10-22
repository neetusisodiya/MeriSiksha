package com.muravtech.meri_siksha.student_ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.QuizWrapper;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends Activity {
    int score = 0;
    @BindView(R.id.triviTimer)
    TextView triviTimer;
    @BindView(R.id.quiz_question)
    TextView quizQuestion;
    @BindView(R.id.radio0)
    RadioButton optionOne;
    @BindView(R.id.radio1)
    RadioButton optionTwo;
    @BindView(R.id.radio2)
    RadioButton optionThree;
    @BindView(R.id.radio3)
    RadioButton optionFour;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.rl_main)
    RelativeLayout rlmain;
    @BindView(R.id.questionNumber)
    TextView questionNumber;
    int number;

    private int currentQuizQuestion;
    private int quizCount;
    private QuizWrapper.DataBean firstQuestion;
    private List<QuizWrapper.DataBean> parsedObject;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daily_quiz);
        ButterKnife.bind(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);


        CountDownTimer countDownTimer = new CountDownTimer(300 * 1000, 1000) {
            @Override
            public void onTick(long l) {

                triviTimer.setText("Timer:" + l / 1000 + " " + "sec");
            }

            @Override
            public void onFinish() {
                triviTimer.setText("TIME OUT");
                Intent quantiveintent = new Intent(getApplicationContext(), Exam_Finish.class);
                quantiveintent.putExtra("score", String.valueOf(score));
                startActivity(quantiveintent);
                finish();
                Toast.makeText(QuizActivity.this, "End of the Quiz Questions", Toast.LENGTH_LONG).show();
            }
        };
        countDownTimer.start();
        getDailyQuiz();

    }

    private void setData() {
       // questionNumber.setText("Question No."+String.valueOf(number));
        if (currentQuizQuestion >= quizCount) {
            Intent quantiveintent = new Intent(getApplicationContext(), Exam_Finish.class);
            quantiveintent.putExtra("score", String.valueOf(score));
            startActivity(quantiveintent);
            finish();
            Toast.makeText(QuizActivity.this, "End of the Quiz Questions", Toast.LENGTH_LONG).show();
            return;
        }
        number=currentQuizQuestion+1;
        radioGroup.clearCheck();
        firstQuestion = parsedObject.get(currentQuizQuestion);
        quizQuestion.setText(String.valueOf(number)+". "+firstQuestion.getQuestion_name());
        optionOne.setText(firstQuestion.getOption_a());
        optionTwo.setText(firstQuestion.getOption_b());
        optionThree.setText(firstQuestion.getOption_c());
        optionFour.setText(firstQuestion.getOption_d());
    }


    private void getDailyQuiz() {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {
            Call<QuizWrapper> call = service.DailyQuiz(AppPreferences.getSchoolId(),AppPreferences.getSectionId());

            call.enqueue(new Callback<QuizWrapper>() {
                @Override
                public void onResponse(Call<QuizWrapper> call, Response<QuizWrapper> response) {

                    try {
                        CommonUtils.hideProgressDoalog();
                        if (response.body().getStatus().equals("true")) {
                            parsedObject = response.body().getData();
                            quizCount = parsedObject.size();
                            setData();
                            rlmain.setVisibility(View.VISIBLE);

                        } else {
                            noData.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<QuizWrapper> call, Throwable t) {
                    CommonUtils.hideProgressDoalog();
                    Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            CommonUtils.hideProgressDoalog();
            Toast.makeText(getApplicationContext(), "Please check your Internet Connection.", Toast.LENGTH_SHORT).show();

        }
    }


    @OnClick({R.id.iv_back, R.id.nextquiz, R.id.previousquiz, R.id.tvFinishButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.nextquiz:
                int radioSelected = radioGroup.getCheckedRadioButtonId();
                String correctAnswerForQuestion = firstQuestion.getFinalanswer();

                RadioButton radioButton = (RadioButton) findViewById(radioSelected);

                if (radioButton.getText().toString().equalsIgnoreCase(correctAnswerForQuestion)) {
                    // correct answer
                    Toast.makeText(QuizActivity.this, "You got the answer correct", Toast.LENGTH_LONG).show();
                    score += 1;
                    //Log.e("TAG", "score:>>>>>>>>>>>>>>" + score);
                    currentQuizQuestion++;
                    setData();
                } else {
                    currentQuizQuestion++;
                    setData();
                }

                break;
            case R.id.previousquiz:
                currentQuizQuestion--;

                if (currentQuizQuestion < 0) {
                    return;
                }

                radioGroup.clearCheck();

                firstQuestion = parsedObject.get(currentQuizQuestion);

                break;
            case R.id.tvFinishButton:
                Intent quantiveintent = new Intent(getApplicationContext(), Exam_Finish.class);
                quantiveintent.putExtra("score", String.valueOf(score));
                startActivity(quantiveintent);
                finish();
                break;
        }
    }

}
