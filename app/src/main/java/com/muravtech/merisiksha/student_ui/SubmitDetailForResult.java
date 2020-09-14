//package com.muravtech.merisiksha.student_ui;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//
//import com.muravtech.merisiksha.R;
//import com.muravtech.merisiksha.utils.AppPreferences;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class SubmitDetailForResult extends AppCompatActivity {
//    String regid;
//    ProgressDialog progressDialog;
//    //ArrayList<ResultBeanDetail> resultDetails;
//    @BindView(R.id.editText_rollnumber)
//    EditText editText_rollnumber;
//
//    @OnClick(R.id.button_submit)
//    public void back() {
//
//        startActivity(new Intent(this,ResultActivity.class));
//    }
//
////    @OnClick(R.id.button_submit)
////    public void submit() {
////
////        if (editText_rollnumber.getText().toString().trim().length() == 0) {
////            editText_rollnumber.setError("Please enter Roll Number.");
////            editText_rollnumber.requestFocus();
////        }else{
////
////            progressDialog = new ProgressDialog(SubmitDetailForResult.this);
////            progressDialog.setMessage("Wait");
////
////            progressDialog.setCancelable(false);
////            progressDialog.show();
////
////            new RestClient(getApplicationContext()).getDataService().getResult(editText_rollnumber.getText().toString(), new Callback<ResultBean>() {
////
////                @Override
////                public void success(ResultBean result, Response response) {
////
////                    progressDialog.cancel();
////                    if (result.getStatus().equalsIgnoreCase("success")) {
////                        resultDetails = (ArrayList<ResultBeanDetail>)result.getData();
////
////                        Intent intent = new Intent(SubmitDetailForResult.this, ResultActivity.class);
////                        intent.putExtra("Name", resultDetails.get(0).getStudentname());
////                        intent.putExtra("Mobile", resultDetails.get(0).getMobile());
////                        intent.putExtra("TotalMarks", resultDetails.get(0).getTotalmarks());
////                        intent.putExtra("CorrectCount", resultDetails.get(0).getCorrectqcount());
////                        intent.putExtra("InCorrectCount", resultDetails.get(0).getIncorrectqcount());
////                        intent.putExtra("Unattempt", resultDetails.get(0).getUnattempted());
////                        intent.putExtra("TotalCount", resultDetails.get(0).getTotalqcount());
////                        intent.putExtra("Score", resultDetails.get(0).getScore());
////                        intent.putExtra("Percentage", resultDetails.get(0).getPercentage());
////                        intent.putExtra("Rank", resultDetails.get(0).getRank());
////                        startActivity(intent);
////                        finish();
////
////
////                    } else if (result != null && result.getStatus().equalsIgnoreCase("unsuccess")) {
////                        Toast.makeText(getApplicationContext(), "Result Not Found", Toast.LENGTH_SHORT).show();
////                    }
////
////
////                }
////
////
////                @Override
////                public void failure(RetrofitError error) {
////                    progressDialog.cancel();
////                    Log.d("error", error.toString());
////                    Toast.makeText(getApplicationContext(), "Error occurs...Try again", Toast.LENGTH_SHORT).show();
////                }
////
////            });
////
////        }
////    }
//private AppPreferences mAppPreferences;
//    Toolbar toolbar;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_submit_detail_for_result);
//        ButterKnife.bind(this);
//        //regid = getIntent().getStringExtra("RegId");
//
//        setToolbar();
//        mAppPreferences = new AppPreferences(this);
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
//
//}
