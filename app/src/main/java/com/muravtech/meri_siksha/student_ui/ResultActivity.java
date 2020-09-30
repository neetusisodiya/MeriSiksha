//package com.muravtech.merisiksha.student_ui;
//
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import com.muravtech.merisiksha.R;
//import com.muravtech.merisiksha.utils.AppPreferences;
//
//import butterknife.ButterKnife;
//
//public class ResultActivity extends AppCompatActivity {
//    String name,mobile,totalmarks,correctcount,incorrectcount,unattampte,totalcount,score,percentage,rank;
////    @BindView(R.id.textView_name)
////    TextView textView_name;
////    @BindView(R.id.textView_mobileno)
////    TextView textView_mobileno;
////    @BindView(R.id.textView_total_marks)
////    TextView textView_total_marks;
////    @BindView(R.id.textView_correct_question)
////    TextView textView_correct_question;
////    @BindView(R.id.textView_incorrect_question)
////    TextView textView_incorrect_question;
////    @BindView(R.id.textView_unattempt)
////    TextView textView_unattempt;
////    @BindView(R.id.textView_total_count)
////    TextView textView_total_count;
////    @BindView(R.id.textView_score)
////    TextView textView_score;
////    @BindView(R.id.textView_percent)
////    TextView textView_percent;
////    @BindView(R.id.textView_rank)
////    TextView textView_rank;
////
////    @OnClick(R.id.imageButton_back)
////    public void back() {
////        finish();
////    }
//    private AppPreferences mAppPreferences;
//    Toolbar toolbar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result);
//        ButterKnife.bind(this);
//        setToolbar();
//        mAppPreferences = new AppPreferences(this);
//
////        name=getIntent().getStringExtra("Name");
////        mobile=getIntent().getStringExtra("Mobile");
////        totalmarks=getIntent().getStringExtra("TotalMarks");
////        correctcount=getIntent().getStringExtra("CorrectCount");
////        incorrectcount=getIntent().getStringExtra("InCorrectCount");
////        unattampte=getIntent().getStringExtra("Unattempt");
////        totalcount=getIntent().getStringExtra("TotalCount");
////        score=getIntent().getStringExtra("Score");
////        percentage=getIntent().getStringExtra("Percentage");
////        rank=getIntent().getStringExtra("Rank");
////
////
////
////        textView_name.setText(name);
////        textView_mobileno.setText(mobile);
////        textView_total_marks.setText(totalmarks);
////        textView_correct_question.setText(correctcount);
////        textView_incorrect_question.setText(incorrectcount);
////        textView_unattempt.setText(unattampte);
////        textView_total_count.setText(totalcount);
////        textView_score.setText(score);
////        textView_percent.setText(percentage);
////        textView_rank.setText(rank);
//    }
//    private void setToolbar() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.mipmap.ic_back_arrow));
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//            return true;
//        }
//        return false;
//    }
//}
