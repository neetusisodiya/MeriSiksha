package com.muravtech.merisiksha.onlineexam;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;

import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.module.Data;
import com.muravtech.merisiksha.module.InstuctionBean;
import com.muravtech.merisiksha.onlineexam.models.QuestionAnswerStartExamModel;
import com.muravtech.merisiksha.onlineexam.utilities.CircularTextView;
import com.muravtech.merisiksha.onlineexam.utilities.Cofig;
import com.muravtech.merisiksha.onlineexam.utilities.CustomViewPager;
import com.muravtech.merisiksha.onlineexam.utilities.MainPagerAdapter;
import com.muravtech.merisiksha.onlineexam.utilities.PicassoImageGetter;
import com.muravtech.merisiksha.onlineexam.utilities.SharedPrefData;
import com.muravtech.merisiksha.server.APIClient;
import com.muravtech.merisiksha.server.DKRInterface;
import com.muravtech.merisiksha.utils.AppPreferences;
import com.muravtech.merisiksha.utils.CommonUtils;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartExamQuestionAnswerActivity extends AppCompatActivity {
    public static HorizontalScrollView horizontalScrollView;
    public static ProgressDialog pDialog;
    final ArrayList<String> subjectIdArrayList = new ArrayList<String>();
    final ArrayList<String> subjectTimeArrayList = new ArrayList<String>();
    boolean isRunning = false;
    AlertDialog alertDialog;
    String op_a = "", op_b = "", op_c = "", op_d = "", op_e = "";
    long timeLeftMileseconds = 0;
    String status = "0";
    TextView tv_attemptedQuestions;
    LinearLayout drawerLayoutLiner;
    RelativeLayout rLayoutButtons;
    Button buttonPrevious, buttonNext;
    TextView textViewExamDownload;
    Toolbar toolbar;
    MyCounter timer;
    String series_id, type, quiz_id, deauration;
    ImageView leftArrow, rightArrow;
    int width;
    ImageView privQn;
    LinearLayout llPrivQn;
    private ArrayList<QuestionAnswerStartExamModel> answerStartExamModelArrayList;
    private LinearLayout linearAllButtonsLauout;
    private DrawerLayout mDrawerLayout;
    private CustomViewPager pager = null;
    private MainPagerAdapter pagerAdapter = null;
    AppPreferences preferences;
    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {

        @Override
        public void onDrawerStateChanged(int status) {

        }

        @Override
        public void onDrawerSlide(View view, float slideArg) {

        }

        @Override
        public void onDrawerOpened(View view) {
        }

        @Override
        public void onDrawerClosed(View view) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exam_question_answer);
        preferences=new AppPreferences(this);
        // Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

       // App.writeIntPreference(SharedPrefData.PREF_GK_LANGUAGE, 1);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        leftArrow = (ImageView) findViewById(R.id.leftArrow);
        rightArrow = (ImageView) findViewById(R.id.rightArrow);
        leftArrow.setVisibility(View.GONE);
        rightArrow.setVisibility(View.GONE);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {

        } else {
            quiz_id = extras.getString("quiz_id");
            //type = extras.getString("type");
            //series_id = extras.getString("series_id");
            deauration = extras.getString("deauration");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Online Test");
        // setting toolbar
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        setUpDrawer();

        answerStartExamModelArrayList = new ArrayList<QuestionAnswerStartExamModel>();
        linearAllButtonsLauout = (LinearLayout) findViewById(R.id.linearAllButtonsLauout);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        buttonPrevious = (Button) findViewById(R.id.buttonPrevious);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        privQn = (ImageView) findViewById(R.id.privQn);
        llPrivQn = (LinearLayout) findViewById(R.id.llPrivQn);
        rLayoutButtons = (RelativeLayout) findViewById(R.id.rLayoutButtons);

        textViewExamDownload = (TextView) findViewById(R.id.textViewExamDownload);

        textViewExamDownload.setVisibility(View.VISIBLE);

        pagerAdapter = new MainPagerAdapter();
        pager = (CustomViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(pagerAdapter);
        if (pager.getCurrentItem() == 0) {

            privQn.setVisibility(View.INVISIBLE);
            llPrivQn.setVisibility(View.INVISIBLE);
        } else {
            privQn.setVisibility(View.VISIBLE);
            llPrivQn.setVisibility(View.VISIBLE);
        }

        privQn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {

                int pos11 = pager.getCurrentItem();
                final String timeAA = String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeftMileseconds) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds)));

                subjectIdArrayList.add(answerStartExamModelArrayList.get(pos11).getSubjectID());
                subjectTimeArrayList.add(timeAA);

                if (pager.getCurrentItem() == 0) {

                    Toast.makeText(getApplicationContext(), "This Is First Question", 2000).show();

                } else {
                    int pos = pager.getCurrentItem();
                    if (answerStartExamModelArrayList.get(pos).getCorrectAnswer().equalsIgnoreCase("-1")) {
                        answerStartExamModelArrayList.get(pos).setIsAttemptType("3");
                        answerStartExamModelArrayList.get(pos).setQuestionStatus("1");
                        answerStartExamModelArrayList.get(pos).setIsAnswered("0");

                        int position = pos - 1;
                        pager.setCurrentItem(position);
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    } else if (answerStartExamModelArrayList.get(pos).getCorrectAnswer().equalsIgnoreCase("-4999")) {

                        answerStartExamModelArrayList.get(pos).setIsAttemptType("3");
                        answerStartExamModelArrayList.get(pos).setQuestionStatus("1");
                        answerStartExamModelArrayList.get(pos).setIsAnswered("0");

                        int position = pos - 1;
                        pager.setCurrentItem(position);
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    } else {
                        int position = pos - 1;
                        pager.setCurrentItem(position);
                    }
                }
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos11 = pager.getCurrentItem();
                final String timeAA = String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeftMileseconds) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds)));

                subjectIdArrayList.add(answerStartExamModelArrayList.get(pos11).getSubjectID());
                subjectTimeArrayList.add(timeAA);

                answerStartExamModelArrayList.get(pos11).setCorrectAnswer(answerStartExamModelArrayList.get(pos11).getUserAnswer());

                if (buttonNext.getText().toString().equalsIgnoreCase("Save & Finish")) {

                    int pos = pager.getCurrentItem();
                    if (answerStartExamModelArrayList.get(pos).getCorrectAnswer().equalsIgnoreCase("-1")) {
                        answerStartExamModelArrayList.get(pos).setIsAttemptType("3");
                        answerStartExamModelArrayList.get(pos).setIsAnswered("0");
                        answerStartExamModelArrayList.get(pos).setQuestionStatus("1");
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    } else if (answerStartExamModelArrayList.get(pos).getCorrectAnswer().equalsIgnoreCase("-4999")) {
                        answerStartExamModelArrayList.get(pos).setIsAnswered("0");
                        answerStartExamModelArrayList.get(pos).setIsAttemptType("3");
                        answerStartExamModelArrayList.get(pos).setQuestionStatus("1");

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                    } else {
                        answerStartExamModelArrayList.get(pos).setIsAttemptType("4");
                        answerStartExamModelArrayList.get(pos).setIsAnswered("1");
                        answerStartExamModelArrayList.get(pos).setQuestionStatus("2");
                        int position = pos + 1;
                        updateStatus(2, "" + position, "#37c482");

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }


                    int allQues = answerStartExamModelArrayList.size();
                    int markedQues1 = getAllMarkedQues();
                    int attempted = getAllAttampetedQuestions();
                    int notVisited1 = getQueNotVisited();
                    //int notAnswered = getAllNotAns();
                    int visited = allQues - notVisited1;


                    final String time = String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds),
                            TimeUnit.MILLISECONDS.toSeconds(timeLeftMileseconds) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds)));

                    Log.e("time_new", time);

                    LayoutInflater li = LayoutInflater.from(StartExamQuestionAnswerActivity.this);
                    View promptsView = li.inflate(R.layout.layout_finish_exam_dialog, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            StartExamQuestionAnswerActivity.this);

                    alertDialogBuilder.setView(promptsView);
                    alertDialogBuilder
                            .setCancelable(false);


                    alertDialog = alertDialogBuilder.create();

                    ImageView closeWin = (ImageView) promptsView.findViewById(R.id.closeWin);
                    closeWin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();

                        }
                    });
                    TextView timeLeft = (TextView) promptsView.findViewById(R.id.timeLeft);
                    TextView totalQues = (TextView) promptsView.findViewById(R.id.totalQues);
                    TextView attemptedQues = (TextView) promptsView.findViewById(R.id.attemptedQues);
                    TextView markedQues = (TextView) promptsView.findViewById(R.id.markedQues);
                    TextView visitedQues = (TextView) promptsView.findViewById(R.id.visitedQues);
                    TextView notVisited = (TextView) promptsView.findViewById(R.id.notVisited);

                    Button resumeButton = (Button) promptsView.findViewById(R.id.resumeButton);
                    Button finishExaButton = (Button) promptsView.findViewById(R.id.finishExaButton);


                    timeLeft.setText(time);
                    totalQues.setText("" + allQues);
                    attemptedQues.setText("" + attempted);
                    markedQues.setText("" + markedQues1);
                    visitedQues.setText("" + visited);
                    notVisited.setText("" + notVisited1);


                    resumeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();

                        }
                    });


                    finishExaButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(timer!=null)
                            timer.cancel();
                            // textViewTimer.setText("Exam Finished by user");

                            try {
                                JSONArray mJsonObject = makJsonObject();
                                sendAnswerQuestionOnServer(mJsonObject.toString(), time);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                    Button exitButton = (Button) promptsView.findViewById(R.id.exitButton);
                    exitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent();
                            intent.putExtra("isFinished", status);
                            setResult(112, intent);
                            finish();


                        }
                    });


                    // show it
                    alertDialog.show();
                } else {

                    int pos = pager.getCurrentItem();
                    if (answerStartExamModelArrayList.get(pos).getCorrectAnswer().equalsIgnoreCase("-1")) {


                        answerStartExamModelArrayList.get(pos).setQuestionStatus("1");
                        answerStartExamModelArrayList.get(pos).setIsAnswered("0");
                        answerStartExamModelArrayList.get(pos).setIsAttemptType("3");
                        int position = pos + 1;
                        pager.setCurrentItem(position);
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    } else if (answerStartExamModelArrayList.get(pos).getCorrectAnswer().equalsIgnoreCase("-4999")) {

                        answerStartExamModelArrayList.get(pos).setIsAttemptType("3");
                        answerStartExamModelArrayList.get(pos).setQuestionStatus("1");
                        answerStartExamModelArrayList.get(pos).setIsAnswered("0");

                        int position = pos + 1;
                        pager.setCurrentItem(position);
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    } else {
                        answerStartExamModelArrayList.get(pos).setIsAnswered("1");
                        answerStartExamModelArrayList.get(pos).setIsAttemptType("4");
                        answerStartExamModelArrayList.get(pos).setQuestionStatus("2");
                        int position = pos + 1;
                        updateStatus(2, "" + position, "#37c482");

                        pager.setCurrentItem(position);
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }

                }


            }
        });


        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = pager.getCurrentItem();
                final String timeAA = String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds),
                        TimeUnit.MILLISECONDS.toSeconds(timeLeftMileseconds) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds)));

                subjectIdArrayList.add(answerStartExamModelArrayList.get(pos).getSubjectID());
                subjectTimeArrayList.add(timeAA);

                if (answerStartExamModelArrayList.get(pos).getUserAnswer().equalsIgnoreCase("-1")) {

                    answerStartExamModelArrayList.get(pos).setQuestionStatus("1");
                    answerStartExamModelArrayList.get(pos).setIsAttemptType("1");
                    answerStartExamModelArrayList.get(pos).setIsAnswered("0");
                    int position = pos + 1;
                    updateStatus(2, "" + position, "#f5d76e");
                    pager.setCurrentItem(position);
                    tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                } else if (answerStartExamModelArrayList.get(pos).getUserAnswer().equalsIgnoreCase("-4999")) {
                    answerStartExamModelArrayList.get(pos).setQuestionStatus("1");
                    answerStartExamModelArrayList.get(pos).setIsAttemptType("1");
                    answerStartExamModelArrayList.get(pos).setIsAnswered("0");
                    int position = pos + 1;
                    updateStatus(2, "" + position, "#f5d76e");
                    pager.setCurrentItem(position);
                    tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                } else {

                    answerStartExamModelArrayList.get(pos).setQuestionStatus("4");
                    answerStartExamModelArrayList.get(pos).setIsAnswered("1");
                    int position = pos + 1;
                    answerStartExamModelArrayList.get(pos).setIsAttemptType("2");
                    updateStatus(2, "" + position, "#ff7900");
                    pager.setCurrentItem(position);

                    tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                }
            }
        });

        getAllDrawerViews();

        loadAllQuestions();

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                setDataOnQuestion(position + 1);

                int pos = position + 1;


                if (answerStartExamModelArrayList.get(position).getCorrectAnswer().equalsIgnoreCase("-1")) {
                    answerStartExamModelArrayList.get(position).setIsAttemptType("3");
                    answerStartExamModelArrayList.get(position).setQuestionStatus("1");
                    answerStartExamModelArrayList.get(position).setIsAnswered("0");

                    tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                } else if (answerStartExamModelArrayList.get(position).getCorrectAnswer().equalsIgnoreCase("-4999")) {

                    answerStartExamModelArrayList.get(position).setIsAttemptType("3");
                    answerStartExamModelArrayList.get(position).setQuestionStatus("1");
                    answerStartExamModelArrayList.get(position).setIsAnswered("0");

                    tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                }

                if (pos == 1) {
                    privQn.setVisibility(View.INVISIBLE);
                    llPrivQn.setVisibility(View.INVISIBLE);

                } else {
                    privQn.setVisibility(View.VISIBLE);
                    llPrivQn.setVisibility(View.VISIBLE);
                }

                if (answerStartExamModelArrayList.get(position).getIsAnswered().equalsIgnoreCase("0")) {
                    updateStatus(2, "" + pos, "#ed5555");

                    answerStartExamModelArrayList.get(position).setQuestionStatus("1");

                }

                if (answerStartExamModelArrayList.size() == pos) {
                    buttonNext.setText("Save & Finish");

                } else {
                    buttonNext.setText("Save & Next");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });

        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

        horizontalScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                try {
                    int scrollX = horizontalScrollView.getScrollX();

                    int scrollBarWidth = horizontalScrollView.getChildAt(0)
                            .getMeasuredWidth() - getWindowManager().getDefaultDisplay().getWidth();


                    if (scrollX == 0) {
                        leftArrow.setVisibility(View.GONE);
                        if (scrollBarWidth > width) {
                            rightArrow.setVisibility(View.VISIBLE);
                        }

                    } else if (scrollX == scrollBarWidth) {

                        leftArrow.setVisibility(View.VISIBLE);
                        rightArrow.setVisibility(View.GONE);


                    } else {

                        leftArrow.setVisibility(View.VISIBLE);
                        rightArrow.setVisibility(View.VISIBLE);

                    }


                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        });


        leftArrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                horizontalScrollView.smoothScrollTo((int) horizontalScrollView.getScrollX() - 300, (int) horizontalScrollView.getScrollY());

            }
        });


        rightArrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                horizontalScrollView.smoothScrollTo((int) horizontalScrollView.getScrollX() + 300, (int) horizontalScrollView.getScrollY());
            }
        });


    }


    private void sendAnswerQuestionOnServer(final String quesstionAnswerData, final String finishTimeValue) {
        JSONArray mJsonObject = null;
        String SuccessA = "";
        try {
            mJsonObject = subjectRecordValue();
            SuccessA = mJsonObject.toString();

            Log.d("arrraaaaa", SuccessA);
            Log.e("arrraaaaa", SuccessA);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalSubjectValue = SuccessA;

        // RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());

            CommonUtils.progressDialogShow(this);
            DKRInterface service = APIClient.getClient().create(DKRInterface.class);
            FormBody.Builder builder = APIClient.createBuilder(new String[]{"school_id","student_id", "exam_id","quizarray","finish_time","subjectData"}, new
                    String[]{AppPreferences.getSchoolId(),AppPreferences.getAccessId(),quiz_id,quesstionAnswerData,finishTimeValue,finalSubjectValue});
            if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

                Call<Data> call = service.submitQuestionAnswer(builder.build());


                call.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, retrofit2.Response<Data> response) {
                        try {
                            CommonUtils.hideProgressDoalog();
                            if (response.body().getStatus().equalsIgnoreCase("true")) {
                                Intent intent = new Intent(StartExamQuestionAnswerActivity.this, ResultAnSolutionActivityNew.class);
                                intent.putExtra("quiz_id",quiz_id);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(StartExamQuestionAnswerActivity.this, "Your Exam not submitted", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            // DynamicToast.makeSuccess(getApplicationContext(), "successfully Login").show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        CommonUtils.hideProgressDoalog();
                        // DynamicToast.makeError(getApplicationContext(), "Wrong mobile number").show();
                    }
                });
            } else {
                CommonUtils.hideProgressDoalog();
            }
    }



    private void loadAllQuestions() {
        answerStartExamModelArrayList.clear();

            CommonUtils.progressDialogShow(this);
            DKRInterface service = APIClient.getClient().create(DKRInterface.class);
            FormBody.Builder builder = APIClient.createBuilder(new String[]{"school_id","exam_id"}, new
                    String[]{AppPreferences.getSchoolId(),quiz_id});
            if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

                Call<JsonObject> call = service.getQuestionAnswer(builder.build());


                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        CommonUtils.hideProgressDoalog();
                        try {
                            if (response != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                                    String s=response.body().toString();
                                    Log.e("TAG", "onResponse:>>>>>>>>>"+s.toString() );
                                    parseJsonFeedFirstDefaultData(jsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            // DynamicToast.makeSuccess(getApplicationContext(), "successfully Login").show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        CommonUtils.hideProgressDoalog();
                        Log.e("TAG", "onFailure: "+t );
                       //  DynamicToast.makeError(getApplicationContext(), "Wrong mobile number").show();
                    }
                });
            } else {
                CommonUtils.hideProgressDoalog();
            }

    }


    @SuppressLint("WrongConstant")
    private void parseJsonFeedFirstDefaultData(JSONObject response) {
        try {
            String Success = response.getString("Success");
            String Message = response.getString("Message");

            if (Success.equalsIgnoreCase("true")) {


                rLayoutButtons.setVisibility(View.VISIBLE);


                String subjectsIDS = response.getString("subjectids");

                preferences.setStringValue(SharedPrefData.PREF_QUESTION_DATA, subjectsIDS);
                String subjects = response.getString("subjects");

                JSONArray arraysubjects = new JSONArray(subjects);

                final LinearLayout linearLayout = new LinearLayout(StartExamQuestionAnswerActivity.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

// Add Buttons
                final int sdk = android.os.Build.VERSION.SDK_INT;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, 60);
                params.setMargins(7, 0, 7, 0);


                for (int i = 0; i < arraysubjects.length(); i++) {


                    Button button = new Button(StartExamQuestionAnswerActivity.this);
                    button.setText(arraysubjects.get(i).toString());
                    button.setLayoutParams(params);
                    button.setTextSize(12);
                    button.setAllCaps(true);
                    button.setGravity(Gravity.CENTER);
                    button.setTextColor(Color.parseColor("#4c4c4c"));
                    button.setPadding(10, 0, 10, 0);
                    button.setId(i);
                    button.setTag(arraysubjects.get(i).toString());

//                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                        button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_disable_mode));
//                    } else {
                        button.setBackground(getResources().getDrawable(R.drawable.item_border));
                   // }

                    linearLayout.addView(button);

                    setClickListener(sdk, linearLayout, button, i);


                    if (i == arraysubjects.length() - 1) {


                        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @SuppressLint("NewApi")
                            @Override
                            public void onGlobalLayout() {
                                int widtha = linearLayout.getWidth();

                                if (widtha > width) {
                                    rightArrow.setVisibility(View.VISIBLE);
                                }


                                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        });


                    }


                    String mySubjectData = response.getString(arraysubjects.get(i).toString());

                    JSONObject mJsonObject = new JSONObject(mySubjectData);
                    String mStringData = mJsonObject.getString("data");
                    String SubjectIdValue = "";

                    SubjectIdValue = mJsonObject.getString("subject_id");

                    JSONArray mJsonArray = new JSONArray(mStringData);

                    for (int j = 0; j < mJsonArray.length(); j++) {


                        QuestionAnswerStartExamModel model = new QuestionAnswerStartExamModel();

                        JSONObject mObject = mJsonArray.getJSONObject(j);

                        model.setSubjectID(SubjectIdValue);
                        model.setQuestiontype(mObject.getString("questiontype"));


                        if (mObject.getString("questiontype").equalsIgnoreCase("MultiAnswer")) {
                            model.setCorrectAnswer("-1");
                            model.setUserAnswer("-1");
                            model.setUnit("");
                        }

                        if (mObject.getString("questiontype").equalsIgnoreCase("RangeAnswer")) {
                            model.setUnit("");
                            model.setCorrectAnswer("-4999");
                            model.setUserAnswer("-4999");
                        }
                        if (mObject.getString("questiontype").equalsIgnoreCase("SingleAnswer")) {

                            model.setCorrectAnswer("-1");
                            model.setUserAnswer("-1");
                            model.setUnit("");
                        }

                        model.setDirection(mObject.getString("direction"));
                        model.setQuestion(mObject.getString("question"));
                        model.setAnswer1(mObject.getString("answer1"));
                        model.setAnswer2(mObject.getString("answer2"));
                        model.setAnswer3(mObject.getString("answer3"));
                        model.setAnswer4(mObject.getString("answer4"));
                        model.setAnswer5(mObject.getString("answer5"));

                        model.setQuestion_mark(mObject.getString("question_mark"));
                        // model.setNegative_mark(mObject.getString("negative_mark"));


                        model.setqId(mObject.getString("question_id"));
                        model.setAnswer6(mObject.getString("answer6"));

                        model.setQuestionStatus("0");
                        model.setSubjectName(arraysubjects.get(i).toString());
                        model.setTotalSubject("" + arraysubjects.length());
                        model.setIsAttemptType("5");
                        model.setIsAnswered("0");
                        model.setCustomQueId("" + (answerStartExamModelArrayList.size() + 1));
                        model.setIsReportd("0");

                        model.setMyExamSubjects(arraysubjects);

                        answerStartExamModelArrayList.add(model);

                    }


                }


                horizontalScrollView.addView(linearLayout);


                textViewExamDownload.setText("You Almost Done. We are setting your question paper. Please wait...");


                int count = answerStartExamModelArrayList.size();
                linearAllButtonsLauout.addView(tableLayout(count));


                setDefaultPagerData();
                updateStatus(2, "" + 1, "#ed5555");
                setDataOnQuestion(1);


                answerStartExamModelArrayList.get(0).setQuestionStatus("1");


                int myTotalTime = Integer.parseInt(deauration);


                timer = new MyCounter(myTotalTime * 60 * 1000, 1000);

                timer.start();


            } else {
                Toast.makeText(getApplicationContext(), "My Message " + Message, 5000).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


    }

    private void setClickListener(final int sdk, final LinearLayout linearLayout, final Button button, final int j) {


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final int childcount = linearLayout.getChildCount();
                for (int i = 0; i < childcount; i++) {
                    View view = linearLayout.getChildAt(i);

                    if (i == j) {

                        String subjectIdData = preferences.getStringValue(SharedPrefData.PREF_QUESTION_DATA);


                        try {
                            JSONArray jsonArray = new JSONArray(subjectIdData);

                            for (int l = 0; l < jsonArray.length(); l++) {


                                JSONObject jsonObject = jsonArray.getJSONObject(l);
                                if (jsonObject.getString("name").equalsIgnoreCase(((Button) view).getText().toString())) {


                                    final String time = String.format("%d:%d",
                                            TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds),
                                            TimeUnit.MILLISECONDS.toSeconds(timeLeftMileseconds) -
                                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds)));


                                    subjectIdArrayList.add(jsonObject.getString("subjectid"));
                                    subjectTimeArrayList.add(time);
                                    //subjectRecord(quiz_id,jsonObject.getString("subjectid"),time);


                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // change to current (enable mode)
//                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_enable_mode));
//                            ((Button) view).setTextColor(Color.parseColor("#FFFFFF"));
//
//                        } else {
                            view.setBackground(getResources().getDrawable(R.drawable.itme_blue));
                            ((Button) view).setTextColor(Color.parseColor("#FFFFFF"));
                       // }

                    } else {


                        // all remaining ((enable mode))
//                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background_disable_mode));
//
//                            ((Button) view).setTextColor(Color.parseColor("#4c4c4c"));
//                        } else {
                            view.setBackground(getResources().getDrawable(R.drawable.item_border));
                            ((Button) view).setTextColor(Color.parseColor("#4c4c4c"));
                       // }

                    }


                }


                JSONArray jsonArray = answerStartExamModelArrayList.get(0).getMyExamSubjects();

                int position = 0;

                try {
                    String subjectName = "" + jsonArray.get(button.getId());

                    for (int i = 0; i < answerStartExamModelArrayList.size(); i++) {

                        if (answerStartExamModelArrayList.get(i).getSubjectName().equalsIgnoreCase(subjectName)) {
                            position = i;


                            if (answerStartExamModelArrayList.get(position).getIsAnswered().equalsIgnoreCase("0")) {

                                int pos = position + 1;
                                updateStatus(2, "" + pos, "#ed5555");
                            }

                            setDataOnQuestion(position + 1);
                            return;
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    //main method for code

    private void setDataOnQuestion(final int i) {


        try {
            tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


            LinearLayout linearLayout = (LinearLayout) horizontalScrollView.getChildAt(0);


            JSONArray jsonArray = answerStartExamModelArrayList.get(0).getMyExamSubjects();

            int position = 0;

            String subjectName = answerStartExamModelArrayList.get(i - 1).getSubjectName();

            for (int m = 0; m < jsonArray.length(); m++) {

                try {
                    if (jsonArray.getString(m).toString().equalsIgnoreCase(subjectName)) {
                        position = m;

                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            final int sdk = android.os.Build.VERSION.SDK_INT;
            final int childcount = linearLayout.getChildCount();
            for (int k = 0; k < childcount; k++) {
                View view = linearLayout.getChildAt(k);

                if (k == position) {

                        view.setBackground(getResources().getDrawable(R.drawable.itme_blue));
                        ((Button) view).setTextColor(Color.parseColor("#FFFFFF"));

                } else {

                    // all remaining ((enable mode))

                        view.setBackground(getResources().getDrawable(R.drawable.item_border));
                        ((Button) view).setTextColor(Color.parseColor("#4c4c4c"));
                }
            }

            View viewFrameLayout = pagerAdapter.getView(i - 1);
            LinearLayout viewLinearLayoutEnglish = (LinearLayout) viewFrameLayout.findViewById(R.id.linearEnglish);
            LinearLayout viewLinearLayoutHindi = (LinearLayout) viewFrameLayout.findViewById(R.id.linearHindi);


            TextView totalMarks = (TextView) viewLinearLayoutEnglish.findViewById(R.id.totalMarks);
            TextView negMarks = (TextView) viewLinearLayoutEnglish.findViewById(R.id.negMarks);


            final TextView tv1 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.tv1);
            final TextView tv2 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.tv2);
            final TextView tv3 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.tv3);
            final TextView tv4 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.tv4);
            final TextView tv5 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.tv5);


            final CheckBox cb1 = (CheckBox) viewLinearLayoutEnglish.findViewById(R.id.cb1);
            final CheckBox cb2 = (CheckBox) viewLinearLayoutEnglish.findViewById(R.id.cb2);
            final CheckBox cb3 = (CheckBox) viewLinearLayoutEnglish.findViewById(R.id.cb3);
            final CheckBox cb4 = (CheckBox) viewLinearLayoutEnglish.findViewById(R.id.cb4);
            final CheckBox cb5 = (CheckBox) viewLinearLayoutEnglish.findViewById(R.id.cb5);

            op_a = "";
            op_b = "";
            op_c = "";
            op_d = "";
            op_e = "";
            TextView queDescription = (TextView) viewLinearLayoutEnglish.findViewById(R.id.queDescription);

            final TextView answer1 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer1);
            final CircularTextView answer1Tag = (CircularTextView) viewLinearLayoutEnglish.findViewById(R.id.answer1Tag);
            final TextView answer2 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer2);
            final CircularTextView answer2Tag = (CircularTextView) viewLinearLayoutEnglish.findViewById(R.id.answer2Tag);
            final TextView answer3 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer3);
            final CircularTextView answer3Tag = (CircularTextView) viewLinearLayoutEnglish.findViewById(R.id.answer3Tag);
            final TextView answer4 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer4);
            final CircularTextView answer4Tag = (CircularTextView) viewLinearLayoutEnglish.findViewById(R.id.answer4Tag);
            final TextView answer5 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer5);
            final CircularTextView answer5Tag = (CircularTextView) viewLinearLayoutEnglish.findViewById(R.id.answer5Tag);

            TextView question = (TextView) viewLinearLayoutEnglish.findViewById(R.id.question);
            LinearLayout ll_5 = (LinearLayout) viewLinearLayoutEnglish.findViewById(R.id.ll_5);
            LinearLayout linearLayout5 = (LinearLayout) viewLinearLayoutEnglish.findViewById(R.id.linearLayout5);
            LinearLayout type_single = (LinearLayout) viewLinearLayoutEnglish.findViewById(R.id.type_single);

            LinearLayout type_mul = (LinearLayout) viewLinearLayoutEnglish.findViewById(R.id.type_mul);
            LinearLayout type_range = (LinearLayout) viewLinearLayoutEnglish.findViewById(R.id.type_range);


            final TextView value_answer = (TextView) viewLinearLayoutEnglish.findViewById(R.id.value_answer);
            final EditText answer_range = (EditText) viewLinearLayoutEnglish.findViewById(R.id.answer_range);
            answer_range.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

            op_a = "";
            op_b = "";
            op_c = "";
            op_d = "";
            op_e = "";

            LinearLayout type_single_h = (LinearLayout) viewLinearLayoutHindi.findViewById(R.id.type_single_h);
            LinearLayout type_range_h = (LinearLayout) viewLinearLayoutHindi.findViewById(R.id.type_range_h);
            LinearLayout type_mul_h = (LinearLayout) viewLinearLayoutHindi.findViewById(R.id.type_mul_h);
            LinearLayout ll_5_h = (LinearLayout) viewLinearLayoutHindi.findViewById(R.id.ll_5_h);
            TextView value_answer_h = (TextView) viewLinearLayoutHindi.findViewById(R.id.value_answer_h);
            final EditText answer_range_h = (EditText) viewLinearLayoutHindi.findViewById(R.id.answer_range_h);
            answer_range_h.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

            setIemBorder(answer1, answer2, answer3, answer4, answer5);


            final QuestionAnswerStartExamModel model = answerStartExamModelArrayList.get(i - 1);


            if (model.getQuestiontype().equalsIgnoreCase("MultiAnswer")) {

                type_single.setVisibility(View.GONE);
                type_range.setVisibility(View.GONE);
                type_mul.setVisibility(View.VISIBLE);

                type_single_h.setVisibility(View.GONE);
                type_range_h.setVisibility(View.GONE);
                type_mul_h.setVisibility(View.VISIBLE);
            }


            if (model.getQuestiontype().equalsIgnoreCase("RangeAnswer")) {
                type_mul_h.setVisibility(View.GONE);
                type_mul.setVisibility(View.GONE);
                type_single.setVisibility(View.GONE);
                type_range.setVisibility(View.VISIBLE);
                value_answer.setText(model.getUnit());
                value_answer.setVisibility(View.GONE);
                type_single_h.setVisibility(View.GONE);
                type_range_h.setVisibility(View.VISIBLE);
                value_answer_h.setText(model.getUnit());

                value_answer_h.setVisibility(View.GONE);

            }

            if (model.getQuestiontype().equalsIgnoreCase("SingleAnswer")) {

                type_mul_h.setVisibility(View.GONE);
                type_mul.setVisibility(View.GONE);
                type_single.setVisibility(View.VISIBLE);
                type_range.setVisibility(View.GONE);

                type_single_h.setVisibility(View.VISIBLE);
                type_range_h.setVisibility(View.GONE);
            }


            question.setText(Html.fromHtml("<b> Q.) </b>   " + model.getQuestion(),
                    new PicassoImageGetter(question), null));


            totalMarks.setText("+ve Marks : " + model.getQuestion_mark());
          // negMarks.setText("-ve Marks : " + model.getNegative_mark());


            // setting description
            if (model.getDirection().equalsIgnoreCase("")) {


                queDescription.setText(Html.fromHtml("<b>" + model.getCustomQueId() + ".) </b>   ",
                        new PicassoImageGetter(queDescription), null));


            } else {

                queDescription.setText(Html.fromHtml("<b>" + model.getCustomQueId() + ".) </b>   " + "<b>Description :  </b> " + "\n" + model.getDirection(),
                        new PicassoImageGetter(queDescription), null));

            }

            answer1.setText(Html.fromHtml(model.getAnswer1(),
                    new PicassoImageGetter(answer1), null));

            tv1.setText(Html.fromHtml(model.getAnswer1(),
                    new PicassoImageGetter(tv1), null));

            setTagAnswerStrockBlack(answer1Tag);


            answer2.setText(Html.fromHtml(model.getAnswer2(),
                    new PicassoImageGetter(answer2), null));

            tv2.setText(Html.fromHtml(model.getAnswer2(),
                    new PicassoImageGetter(tv2), null));

            setTagAnswerStrockBlack(answer2Tag);


            answer3.setText(Html.fromHtml(model.getAnswer3(),
                    new PicassoImageGetter(answer3), null));


            tv3.setText(Html.fromHtml(model.getAnswer3(),
                    new PicassoImageGetter(tv3), null));

            setTagAnswerStrockBlack(answer3Tag);


            answer4.setText(Html.fromHtml(model.getAnswer4(), new PicassoImageGetter(answer4), null));


            tv4.setText(Html.fromHtml(model.getAnswer4(),
                    new PicassoImageGetter(tv4), null));
            setTagAnswerStrockBlack(answer4Tag);
            answer4Tag.setStrokeWidth(1);


            if (model.getAnswer5().equalsIgnoreCase("")) {
                linearLayout5.setVisibility(View.GONE);
                ll_5.setVisibility(View.GONE);

            } else {


                tv5.setText(Html.fromHtml(model.getAnswer5(),
                        new PicassoImageGetter(tv5), null));
                answer5.setText(Html.fromHtml(model.getAnswer5(),
                        new PicassoImageGetter(answer5), null));
                setTagAnswerStrockBlack(answer5Tag);

            }


            answer1Tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setTagAnswerStrockBlue(answer1Tag);
                    setTagAnswerStrockBlack(answer2Tag);
                    setTagAnswerStrockBlack(answer3Tag);
                    setTagAnswerStrockBlack(answer4Tag);
                    setTagAnswerStrockBlack(answer5Tag);

                    setOrangeIemBorder(answer1, answer2, answer3, answer4, answer5);


                    answerStartExamModelArrayList.get(i - 1).setUserAnswer("1");

                    if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {


                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                    } else {

                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");


                        updateStatus(2, "" + i, "#ed5555");

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }

                }
            });


            answer_range.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable mEdit) {

                    if (mEdit.length() > 0) {

                        if (answer_range.getText().toString().equalsIgnoreCase("0")) {
                            answerStartExamModelArrayList.get(i - 1).setUserAnswer("00");
                        } else {
                            answerStartExamModelArrayList.get(i - 1).setUserAnswer(answer_range.getText().toString());
                        }

                        if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {

                            tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                        } else {

                            answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");


                            updateStatus(2, "" + i, "#ed5555");

                            tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                        }

                        answer_range.setBackground(getResources().getDrawable(R.drawable.itme_blue));
                       // answer_range_h.setBackground(getResources().getDrawable(R.drawable.item_border_orange));
                    } else {
                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");


                        updateStatus(2, "" + i, "#ed5555");

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });


            answer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setTagAnswerStrockBlue(answer1Tag);

                    setTagAnswerStrockBlack(answer2Tag);
                    setTagAnswerStrockBlack(answer3Tag);
                    setTagAnswerStrockBlack(answer4Tag);
                    setTagAnswerStrockBlack(answer5Tag);

                    setOrangeIemBorder(answer1, answer2, answer3, answer4, answer5);

                    answerStartExamModelArrayList.get(i - 1).setUserAnswer("1");

                    if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                    } else {

                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");


                        updateStatus(2, "" + i, "#ed5555");

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }

                }
            });


            answer2Tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setTagAnswerStrockBlue(answer2Tag);

                    setTagAnswerStrockBlack(answer1Tag);
                    setTagAnswerStrockBlack(answer3Tag);
                    setTagAnswerStrockBlack(answer4Tag);
                    setTagAnswerStrockBlack(answer5Tag);


                    answerStartExamModelArrayList.get(i - 1).setUserAnswer("2");


                    if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                    } else {

                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");


                        updateStatus(2, "" + i, "#ed5555");

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }

                    setOrangeIemBorder(answer2, answer1, answer3, answer4, answer5);


                }
            });


            answer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setTagAnswerStrockBlue(answer2Tag);
                    setTagAnswerStrockBlack(answer1Tag);
                    setTagAnswerStrockBlack(answer3Tag);
                    setTagAnswerStrockBlack(answer4Tag);
                    setTagAnswerStrockBlack(answer5Tag);
                    answerStartExamModelArrayList.get(i - 1).setUserAnswer("2");

                    if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    } else {
                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");

                        updateStatus(2, "" + i, "#ed5555");
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }

                    setOrangeIemBorder(answer2, answer1, answer3, answer4, answer5);


                }
            });


            answer3Tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setTagAnswerStrockBlue(answer3Tag);
                    setTagAnswerStrockBlack(answer1Tag);
                    setTagAnswerStrockBlack(answer2Tag);
                    setTagAnswerStrockBlack(answer4Tag);
                    setTagAnswerStrockBlack(answer5Tag);


                    answerStartExamModelArrayList.get(i - 1).setUserAnswer("3");


                    if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {


                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                    } else {

                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");


                        updateStatus(2, "" + i, "#ed5555");
                        // Toast.makeText(StartExamQuestionAnswerActivity.this,"C is Selected !",5000).show();

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }
                    setOrangeIemBorder(answer3, answer2, answer1, answer4, answer5);

                }
            });

            answer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setTagAnswerStrockBlue(answer3Tag);
                    setTagAnswerStrockBlack(answer1Tag);
                    setTagAnswerStrockBlack(answer2Tag);
                    setTagAnswerStrockBlack(answer4Tag);
                    setTagAnswerStrockBlack(answer5Tag);

                    answerStartExamModelArrayList.get(i - 1).setUserAnswer("3");
                    if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                    } else {
                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");

                        updateStatus(2, "" + i, "#ed5555");

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }
                    setOrangeIemBorder(answer3, answer2, answer1, answer4, answer5);
                }
            });

            answer4Tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setTagAnswerStrockBlue(answer4Tag);

                    setTagAnswerStrockBlack(answer1Tag);
                    setTagAnswerStrockBlack(answer2Tag);
                    setTagAnswerStrockBlack(answer3Tag);
                    setTagAnswerStrockBlack(answer5Tag);

                    answerStartExamModelArrayList.get(i - 1).setUserAnswer("4");

                    if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {

                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());


                    } else {

                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");
                        updateStatus(2, "" + i, "#ed5555");
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }

                    setOrangeIemBorder(answer4, answer5, answer3, answer2, answer1);


                }
            });


            answer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTagAnswerStrockBlue(answer4Tag);
                    setTagAnswerStrockBlack(answer1Tag);
                    setTagAnswerStrockBlack(answer2Tag);
                    setTagAnswerStrockBlack(answer3Tag);
                    setTagAnswerStrockBlack(answer5Tag);

                    answerStartExamModelArrayList.get(i - 1).setUserAnswer("4");
                    if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());
                    } else {
                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");
                        updateStatus(2, "" + i, "#ed5555");
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());
                    }
                    setOrangeIemBorder(answer4, answer5, answer3, answer2, answer1);
                }
            });


            answer5Tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setTagAnswerStrockBlue(answer5Tag);
                    setTagAnswerStrockBlack(answer1Tag);
                    setTagAnswerStrockBlack(answer2Tag);
                    setTagAnswerStrockBlack(answer3Tag);
                    setTagAnswerStrockBlack(answer4Tag);
                    answerStartExamModelArrayList.get(i - 1).setUserAnswer("5");

                    if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());
                    } else {
                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");
                        updateStatus(2, "" + i, "#ed5555");
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());
                    }
                    setOrangeIemBorder(answer5, answer3, answer2, answer1, answer4);

                }
            });


            answer5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTagAnswerStrockBlue(answer5Tag);
                    setTagAnswerStrockBlack(answer1Tag);
                    setTagAnswerStrockBlack(answer2Tag);
                    setTagAnswerStrockBlack(answer3Tag);
                    setTagAnswerStrockBlack(answer4Tag);

                    answerStartExamModelArrayList.get(i - 1).setUserAnswer("5");
                    if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());
                    } else {
                        answerStartExamModelArrayList.get(i - 1).setIsAnswered("0");
                        updateStatus(2, "" + i, "#ed5555");
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());
                    }
                }
            });


            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cb1.isChecked()) {
                        cb1.setChecked(false);
                        op_a = "";
                        tvBorderBlack(tv1);
                    } else {
                        op_a = "-1";
                        cb1.setChecked(true);
                        tvBorderOrange(tv1);
                    }
                    String value_result = op_a + op_b + op_c + op_d + op_e;

                    if (value_result.equalsIgnoreCase("")) {
                        answerStartExamModelArrayList.get(i - 1).setUserAnswer("-1");
                    } else {
                        answerStartExamModelArrayList.get(i - 1).setUserAnswer(value_result.substring(1));
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }
                }
            });


            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cb2.isChecked()) {

                        cb2.setChecked(false);
                        op_b = "";
                        tvBorderBlack(tv2);

                    } else {
                        op_b = "-2";
                        cb2.setChecked(true);
                        tvBorderOrange(tv2);
                    }
                    String value_result = op_a + op_b + op_c + op_d + op_e;

                    if (value_result.equalsIgnoreCase("")) {

                        answerStartExamModelArrayList.get(i - 1).setUserAnswer("-1");
                    } else {
                        answerStartExamModelArrayList.get(i - 1).setUserAnswer(value_result.substring(1));
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }
                }
            });

            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cb3.isChecked()) {

                        cb3.setChecked(false);
                        op_c = "";
                        tvBorderBlack(tv3);
                    } else {
                        op_c = "-3";
                        cb3.setChecked(true);
                        tvBorderOrange(tv3);
                    }
                    String value_result = op_a + op_b + op_c + op_d + op_e;

                    if (value_result.equalsIgnoreCase("")) {

                        answerStartExamModelArrayList.get(i - 1).setUserAnswer("-1");
                    } else {
                        answerStartExamModelArrayList.get(i - 1).setUserAnswer(value_result.substring(1));
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }
                }
            });


            tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cb4.isChecked()) {
                        cb4.setChecked(false);
                        op_d = "";

                        tvBorderBlack(tv4);

                    } else {
                        op_d = "-4";
                        cb4.setChecked(true);
                        tvBorderOrange(tv4);
                    }


                    String value_result = op_a + op_b + op_c + op_d + op_e;

                    if (value_result.equalsIgnoreCase("")) {

                        answerStartExamModelArrayList.get(i - 1).setUserAnswer("-1");
                    } else {
                        answerStartExamModelArrayList.get(i - 1).setUserAnswer(value_result.substring(1));
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }
                }
            });

            tv5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cb5.isChecked()) {
                        cb5.setChecked(false);
                        op_e = "";
                        tvBorderBlack(tv5);
                    } else {
                        op_e = "-5";
                        cb5.setChecked(true);
                        tvBorderOrange(tv5);
                    }
                    String value_result = op_a + op_b + op_c + op_d + op_e;

                    if (value_result.equalsIgnoreCase("")) {

                        answerStartExamModelArrayList.get(i - 1).setUserAnswer("-1");
                    } else {
                        answerStartExamModelArrayList.get(i - 1).setUserAnswer(value_result.substring(1));
                        tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                    }
                }
            });

            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                                                   if (compoundButton.isChecked()) {
                                                       op_a = "-1";
                                                       tvBorderOrange(tv1);
                                                   } else {
                                                       op_a = "";
                                                       tvBorderBlack(tv1);
                                                   }

                                                   String value_result = op_a + op_b + op_c + op_d + op_e;

                                                   if (value_result.equalsIgnoreCase("")) {

                                                       answerStartExamModelArrayList.get(i - 1).setUserAnswer("-1");
                                                   } else {
                                                       answerStartExamModelArrayList.get(i - 1).setUserAnswer(value_result.substring(1));
                                                       tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                                                   }
//
                                               }


                                           }
            );


            cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                                                   if (compoundButton.isChecked()) {
                                                       op_b = "-2";

                                                       //   cb2_h.setChecked(true);
                                                       tvBorderOrange(tv2);

                                                   } else {
                                                       // cb2_h.setChecked(false);
                                                       op_b = "";

                                                       tvBorderBlack(tv2);
                                                   }
                                                   String value_result = op_a + op_b + op_c + op_d + op_e;

                                                   if (value_result.equalsIgnoreCase("")) {

                                                       answerStartExamModelArrayList.get(i - 1).setUserAnswer("-1");
                                                   } else {
                                                       answerStartExamModelArrayList.get(i - 1).setUserAnswer(value_result.substring(1));
                                                       tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                                                   }
                                               }


                                           }
            );
            cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                                                   if (compoundButton.isChecked()) {
                                                       op_c = "-3";
                                                       // cb3_h.setChecked(true);
                                                       tvBorderOrange(tv3);

                                                   } else {
                                                       //cb3_h.setChecked(false);
                                                       op_c = "";

                                                       tvBorderBlack(tv3);
                                                   }
                                                   String value_result = op_a + op_b + op_c + op_d + op_e;

                                                   if (value_result.equalsIgnoreCase("")) {

                                                       answerStartExamModelArrayList.get(i - 1).setUserAnswer("-1");
                                                   } else {
                                                       answerStartExamModelArrayList.get(i - 1).setUserAnswer(value_result.substring(1));
                                                       tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                                                   }
                                               }


                                           }
            );
            cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                                                   if (compoundButton.isChecked()) {
                                                       //cb4_h.setChecked(true);
                                                       op_d = "-4";
                                                       tvBorderOrange(tv4);
                                                   } else {
                                                       // cb4_h.setChecked(false);
                                                       op_d = "";

                                                       tvBorderBlack(tv4);
                                                   }
                                                   String value_result = op_a + op_b + op_c + op_d + op_e;

                                                   if (value_result.equalsIgnoreCase("")) {

                                                       answerStartExamModelArrayList.get(i - 1).setUserAnswer("-1");
                                                   } else {
                                                       answerStartExamModelArrayList.get(i - 1).setUserAnswer(value_result.substring(1));
                                                       tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                                                   }
                                               }


                                           }
            );
            cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                                                   if (compoundButton.isChecked()) {
                                                       op_e = "-5";
                                                       //cb5_h.setChecked(true);
                                                       tvBorderOrange(tv5);
                                                   } else {
                                                       // cb5_h.setChecked(false);
                                                       op_e = "";

                                                       tvBorderBlack(tv5);
                                                   }
                                                   String value_result = op_a + op_b + op_c + op_d + op_e;

                                                   if (value_result.equalsIgnoreCase("")) {

                                                       answerStartExamModelArrayList.get(i - 1).setUserAnswer("-1");
                                                   } else {
                                                       answerStartExamModelArrayList.get(i - 1).setUserAnswer(value_result.substring(1));
                                                       tv_attemptedQuestions.setText("Attempted Questions : " + getAllAttampetedQuestions() + "/" + answerStartExamModelArrayList.size());

                                                   }
//
                                               }


                                           }
            );


            if (answerStartExamModelArrayList.get(i - 1).getIsAnswered().equalsIgnoreCase("1")) {

                switch (answerStartExamModelArrayList.get(i - 1).getCorrectAnswer()) {

                    case "1":
                        setTagAnswerStrockBlue(answer1Tag);
                        setOrangeIemBorder(answer1, answer2, answer3, answer4, answer5);
                        break;
                    case "2":
                        setTagAnswerStrockBlue(answer2Tag);
                        setOrangeIemBorder(answer2, answer3, answer4, answer5, answer1);
                        break;
                    case "3":
                        setTagAnswerStrockBlue(answer3Tag);
                        setOrangeIemBorder(answer3, answer4, answer5, answer1, answer2);
                        break;
                    case "4":
                        setTagAnswerStrockBlue(answer4Tag);
                        setOrangeIemBorder(answer4, answer5, answer1, answer2, answer3);
                        break;
                    case "5":
                        setTagAnswerStrockBlue(answer5Tag);
                        setOrangeIemBorder(answer5, answer1, answer2, answer3, answer4);
                        break;
                }
            }
            pager.setCurrentItem(i - 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDefaultPagerData() {
        for (int i = 0; i < answerStartExamModelArrayList.size(); i++) {
            LayoutInflater inflater = StartExamQuestionAnswerActivity.this.getLayoutInflater();
            FrameLayout v0 = (FrameLayout) inflater.inflate(R.layout.item_question_answer_exam, null);
            addView(v0);
            if (i == answerStartExamModelArrayList.size() - 1) {

                textViewExamDownload.setVisibility(View.GONE);
            }
        }
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to add a view to the ViewPager.
    public void addView(View newPage) {

        int pageIndex = pagerAdapter.addView(newPage);
        // You might want to make "newPage" the currently displayed page:
        pagerAdapter.notifyDataSetChanged();
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to remove a view from the ViewPager.
    public void removeView(View defunctPage) {
        int pageIndex = pagerAdapter.removeView(pager, defunctPage);
        // You might want to choose what page to display, if the current page was "defunctPage".

    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to get the currently displayed page.
    public View getCurrentPage() {
        return pagerAdapter.getView(pager.getCurrentItem());
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to set the currently displayed page.  "pageToShow" must
    // currently be in the adapter, or this will crash.
    public void setCurrentPage(View pageToShow) {
        pager.setCurrentItem(pagerAdapter.getItemPosition(pageToShow), true);
    }

    private void getAllDrawerViews() {
        tv_attemptedQuestions = (TextView) findViewById(R.id.tv_attemptedQuestions);
    }

    private void setUpDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        mDrawerLayout.setDrawerListener(mDrawerListener);
        drawerLayoutLiner = (LinearLayout) findViewById(R.id.drawerLayoutLiner);
        mDrawerLayout.closeDrawer(drawerLayoutLiner);
    }

    private void updateStatus(int status, String myButtonId, String colorCode) {
        TableLayout tableLayout = (TableLayout) linearAllButtonsLauout.getChildAt(0);
        // int count =  tableLayout.getChildCount();
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            TableRow parentRow = (TableRow) tableLayout.getChildAt(i);
            if (parentRow instanceof TableRow) {
                for (int j = 0; j < parentRow.getChildCount(); j++) {
                    Button button = (Button) parentRow.getChildAt(j);
                    if (button instanceof Button) {
                        String text = button.getText().toString();
                        if (button.getId() == Integer.parseInt(myButtonId)) {
                            button.setBackgroundColor(Color.parseColor(colorCode));

                        }
                    }
                }
            }
        }
    }

    // Using a TableLayout as it provides you with a neat ordering structure

    private TableLayout tableLayout(int count) {
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        int noOfRows = count / 4;
        for (int i = 0; i < noOfRows; i++) {
            int rowId = 4 * i;
            tableLayout.addView(createOneFullRow(rowId));
        }
        int individualCells = count % 4;
        tableLayout.addView(createLeftOverCells(individualCells, count));
        return tableLayout;
    }

    private TableRow createLeftOverCells(int individualCells, int count) {
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0, 0, 0, 0);
        int rowId = count - individualCells;
        for (int i = 1; i <= individualCells; i++) {
            tableRow.addView(editText(String.valueOf(rowId + i)));
        }
        return tableRow;
    }

    private TableRow createOneFullRow(int rowId) {
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0, 0, 0, 0);
        for (int i = 1; i <= 4; i++) {
            tableRow.addView(editText(String.valueOf(rowId + i)));
        }
        return tableRow;
    }

    private Button editText(String hint) {
        final Button editText = new Button(this);
        // ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        editText.setId(Integer.valueOf(hint));
        // editText.setLayoutParams(params);
        editText.setText(hint);
        editText.setGravity(Gravity.CENTER);
        editText.setTextColor(Color.parseColor("#FFFFFF"));
        editText.setPadding(4, 4, 4, 4);
        TableRow.LayoutParams params = new TableRow.LayoutParams(60, 60);
        params.setMargins(5, 5, 5, 5);
        editText.setLayoutParams(params);
        editText.setBackgroundColor(Color.parseColor("#60bae3"));


        QuestionAnswerStartExamModel questionAnswerStartExamModel = new QuestionAnswerStartExamModel();
        questionAnswerStartExamModel.setqId(hint);

        questionAnswerStartExamModel.setQuestionStatus("0");


        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDataOnQuestion(editText.getId());
                mDrawerLayout.closeDrawer(drawerLayoutLiner);
            }
        });


        return editText;
    }



    public int getAllAttampetedQuestions() {
        int totalAttampeted = 0;
        for (int i = 0; i < answerStartExamModelArrayList.size(); i++) {

            if (answerStartExamModelArrayList.get(i).getIsAnswered().equalsIgnoreCase("1")) {
                totalAttampeted++;
            }

        }


        return totalAttampeted;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuFinishExam) {
            int allQues = answerStartExamModelArrayList.size();
            int markedQues1 = getAllMarkedQues();
            int attempted = getAllAttampetedQuestions();
            int notVisited1 = getQueNotVisited();
            int notAnswered = getAllNotAns();
            int visited = allQues - notVisited1;
            final String time = String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds),
                    TimeUnit.MILLISECONDS.toSeconds(timeLeftMileseconds) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds)));
            Log.e("time_new", time);

            LayoutInflater li = LayoutInflater.from(StartExamQuestionAnswerActivity.this);
            View promptsView = li.inflate(R.layout.layout_finish_exam_dialog, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    StartExamQuestionAnswerActivity.this);

            alertDialogBuilder.setView(promptsView);
            alertDialogBuilder
                    .setCancelable(false);


            alertDialog = alertDialogBuilder.create();
            ImageView closeWin = (ImageView) promptsView.findViewById(R.id.closeWin);
            closeWin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.dismiss();

                }
            });

            TextView timeLeft = (TextView) promptsView.findViewById(R.id.timeLeft);
            TextView totalQues = (TextView) promptsView.findViewById(R.id.totalQues);
            TextView attemptedQues = (TextView) promptsView.findViewById(R.id.attemptedQues);
            TextView markedQues = (TextView) promptsView.findViewById(R.id.markedQues);
            TextView visitedQues = (TextView) promptsView.findViewById(R.id.visitedQues);
            TextView notVisited = (TextView) promptsView.findViewById(R.id.notVisited);

            Button resumeButton = (Button) promptsView.findViewById(R.id.resumeButton);
            Button finishExaButton = (Button) promptsView.findViewById(R.id.finishExaButton);
            timeLeft.setText(time);
            totalQues.setText("" + allQues);
            attemptedQues.setText("" + attempted);
            markedQues.setText("" + markedQues1);
            visitedQues.setText("" + visited);
            notVisited.setText("" + notVisited1);

            resumeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.dismiss();

                }
            });


            finishExaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timer.cancel();
                    try {
                        JSONArray mJsonObject = makJsonObject();
                        sendAnswerQuestionOnServer(mJsonObject.toString(), time);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            Button exitButton = (Button) promptsView.findViewById(R.id.exitButton);
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.putExtra("isFinished", status);
                    setResult(112, intent);
                    finish();


                }
            });
            alertDialog.show();

        }


        if (id == R.id.drawer) {
            if (mDrawerLayout.isDrawerOpen(drawerLayoutLiner)) {
                mDrawerLayout.closeDrawer(drawerLayoutLiner);
            } else {
                mDrawerLayout.openDrawer(drawerLayoutLiner);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public JSONArray subjectRecordValue()
            throws JSONException {
        JSONObject obj = null;
        Log.e("TAG", "subjectRecordValue: "+subjectIdArrayList.size());
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < subjectIdArrayList.size(); i++) {
            obj = new JSONObject();
            try {
                obj.put("subject_id", subjectIdArrayList.get(i));
                obj.put("per_question_time", subjectTimeArrayList.get(i));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            jsonArray.put(obj);
        }

        JSONObject finalobject = new JSONObject();
        finalobject.put("data", jsonArray);
        return jsonArray;
    }


    public JSONArray makJsonObject()
            throws JSONException {
        JSONObject obj = null;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < answerStartExamModelArrayList.size(); i++) {
            obj = new JSONObject();
            try {
                obj.put("question_id", answerStartExamModelArrayList.get(i).getqId());
                obj.put("answer6", answerStartExamModelArrayList.get(i).getAnswer6());

                if (answerStartExamModelArrayList.get(i).getCorrectAnswer().equalsIgnoreCase("-1")) {
                    obj.put("answer_objective", "0");
                } else if (answerStartExamModelArrayList.get(i).getCorrectAnswer().equalsIgnoreCase("-4999")) {
                    obj.put("answer_objective", null);
                } else {
                    obj.put("answer_objective", answerStartExamModelArrayList.get(i).getCorrectAnswer());
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            jsonArray.put(obj);
        }

        //JSONObject finalobject = new JSONObject();
       // finalobject.put("data", jsonArray);
        return jsonArray;
    }

    @Override
    public void onBackPressed() {
        if (!StartExamQuestionAnswerActivity.this.isFinishing()) {
            int allQues = answerStartExamModelArrayList.size();
            int markedQues1 = getAllMarkedQues();
            int attempted = getAllAttampetedQuestions();
            int notVisited1 = getQueNotVisited();
            int visited = allQues - notVisited1;
            final String time = String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds),
                    TimeUnit.MILLISECONDS.toSeconds(timeLeftMileseconds) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds)));
            Log.e("time_new", time);
            LayoutInflater li = LayoutInflater.from(StartExamQuestionAnswerActivity.this);
            View promptsView = li.inflate(R.layout.layout_finish_exam_dialog, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    StartExamQuestionAnswerActivity.this);

            alertDialogBuilder.setView(promptsView);
            alertDialogBuilder
                    .setCancelable(false);
            alertDialog = alertDialogBuilder.create();

            ImageView closeWin = (ImageView) promptsView.findViewById(R.id.closeWin);
            closeWin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.dismiss();

                }
            });
            TextView timeLeft = (TextView) promptsView.findViewById(R.id.timeLeft);
            TextView totalQues = (TextView) promptsView.findViewById(R.id.totalQues);
            TextView attemptedQues = (TextView) promptsView.findViewById(R.id.attemptedQues);
            TextView markedQues = (TextView) promptsView.findViewById(R.id.markedQues);
            TextView visitedQues = (TextView) promptsView.findViewById(R.id.visitedQues);
            TextView notVisited = (TextView) promptsView.findViewById(R.id.notVisited);

            Button resumeButton = (Button) promptsView.findViewById(R.id.resumeButton);
            Button finishExaButton = (Button) promptsView.findViewById(R.id.finishExaButton);
            timeLeft.setText(time);
            totalQues.setText("" + allQues);
            attemptedQues.setText("" + attempted);
            markedQues.setText("" + markedQues1);
            visitedQues.setText("" + visited);
            notVisited.setText("" + notVisited1);


            resumeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.dismiss();

                }
            });


            finishExaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    timer.cancel();
                    try {
                        JSONArray mJsonObject = makJsonObject();
                        sendAnswerQuestionOnServer(mJsonObject.toString(), time);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });

            Button exitButton = (Button) promptsView.findViewById(R.id.exitButton);
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("isFinished", status);
                    setResult(112, intent);
                    finish();


                }
            });

            alertDialog.show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // from view series activity
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 113) {

            if (alertDialog != null) {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }

            }
            status = data.getStringExtra("isFinished");


            Intent intent = new Intent();
            intent.putExtra("isFinished", status);
            setResult(112, intent);
            finish();
        }
    }

    public int getQueNotVisited() {
        int totalAttampeted = 0;
        for (int i = 0; i < answerStartExamModelArrayList.size(); i++) {
            if (answerStartExamModelArrayList.get(i).getQuestionStatus().equalsIgnoreCase("0")) {
                totalAttampeted++;
            }
        }
        return totalAttampeted;
    }

    public int getAllMarkedQues() {
        int totalAttampeted = 0;
        for (int i = 0; i < answerStartExamModelArrayList.size(); i++) {

            if (answerStartExamModelArrayList.get(i).getQuestionStatus().equalsIgnoreCase("4")) {
                totalAttampeted++;
            }
        }
        return totalAttampeted;
    }

    public int getAllNotAns() {
        int totalAttampeted = 0;
        for (int i = 0; i < answerStartExamModelArrayList.size(); i++) {

            if (answerStartExamModelArrayList.get(i).getQuestionStatus().equalsIgnoreCase("1")) {
                totalAttampeted++;
            }
        }
        return totalAttampeted;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer = new MyCounter(timeLeftMileseconds, 1000);
            timer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isRunning) {
            timer.cancel();
        }
    }

    private void setIemBorder(TextView answer1, TextView answer2, TextView answer3, TextView answer4, TextView answer5) {
        answer1.setBackground(getResources().getDrawable(R.drawable.item_border));
        answer2.setBackground(getResources().getDrawable(R.drawable.item_border));
        answer3.setBackground(getResources().getDrawable(R.drawable.item_border));
        answer4.setBackground(getResources().getDrawable(R.drawable.item_border));
        answer5.setBackground(getResources().getDrawable(R.drawable.item_border));
    }

    private void setOrangeIemBorder(TextView answer1, TextView answer2, TextView answer3, TextView answer4, TextView answer5) {
        tvBorderOrange(answer1);
        tvBorderBlack(answer2);
        tvBorderBlack(answer3);
        tvBorderBlack(answer4);
        tvBorderBlack(answer5);

    }

    private void setTagAnswerStrockBlack(CircularTextView answer1Tag) {
        answer1Tag.setStrokeWidth(1);
        answer1Tag.setStrokeColor("#000000");
        answer1Tag.setTextColor(Color.parseColor("#000000"));
        answer1Tag.setSolidColor("#FFFFFF");
    }

    private void setTagAnswerStrockBlue(CircularTextView answer4Tag) {
        answer4Tag.setStrokeWidth(1);
        answer4Tag.setStrokeColor("#0a4e74");
        answer4Tag.setTextColor(Color.parseColor("#FFFFFF"));
        answer4Tag.setSolidColor("#ef8008");
    }

    private void tvBorderOrange(TextView tv1) {
        tv1.setBackground(getResources().getDrawable(R.drawable.itme_blue));
        tv1.setTextColor(getResources().getColor(R.color.white));
        Log.e("TAG", "tvBorderOrange: "+"kkfgkkfdkfd" );
    }

    private void tvBorderBlack(TextView tv1) {
        tv1.setBackground(getResources().getDrawable(R.drawable.item_border));
        tv1.setTextColor(getResources().getColor(R.color.black));
    }

    public class MyCounter extends CountDownTimer {

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            isRunning = false;
            toolbar.setSubtitle("00:00");
            //int totalAttempted = getAllAttampetedQuestions();
            int allQues = answerStartExamModelArrayList.size();
            int markedQues1 = getAllMarkedQues();
            int attempted = getAllAttampetedQuestions();
            int notVisited1 = getQueNotVisited();
            //int notAnswered = getAllNotAns();
            int visited = allQues - notVisited1;
            final String time = String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds),
                    TimeUnit.MILLISECONDS.toSeconds(timeLeftMileseconds) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeftMileseconds)));

            Log.e("time_new", time);

            LayoutInflater li = LayoutInflater.from(StartExamQuestionAnswerActivity.this);
            View promptsView = li.inflate(R.layout.layout_finish_exam_dialog, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    StartExamQuestionAnswerActivity.this);

            alertDialogBuilder.setView(promptsView);
            alertDialogBuilder
                    .setCancelable(false);

            alertDialog = alertDialogBuilder.create();

            ImageView closeWin = (ImageView) promptsView.findViewById(R.id.closeWin);
            closeWin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.dismiss();

                }
            });
            TextView timeLeft = (TextView) promptsView.findViewById(R.id.timeLeft);
            TextView totalQues = (TextView) promptsView.findViewById(R.id.totalQues);
            TextView attemptedQues = (TextView) promptsView.findViewById(R.id.attemptedQues);
            TextView markedQues = (TextView) promptsView.findViewById(R.id.markedQues);
            TextView visitedQues = (TextView) promptsView.findViewById(R.id.visitedQues);
            TextView notVisited = (TextView) promptsView.findViewById(R.id.notVisited);

            Button resumeButton = (Button) promptsView.findViewById(R.id.resumeButton);
            Button finishExaButton = (Button) promptsView.findViewById(R.id.finishExaButton);


            timeLeft.setText(time);
            totalQues.setText("" + allQues);
            attemptedQues.setText("" + attempted);
            markedQues.setText("" + markedQues1);
            visitedQues.setText("" + visited);
            notVisited.setText("" + notVisited1);


            resumeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.dismiss();

                }
            });

            finishExaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    timer.cancel();

                    try {
                        JSONArray mJsonObject = makJsonObject();
                        sendAnswerQuestionOnServer(mJsonObject.toString(), time);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            Button exitButton = (Button) promptsView.findViewById(R.id.exitButton);
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.putExtra("isFinished", status);
                    setResult(112, intent);
                    finish();


                }
            });


            if (!StartExamQuestionAnswerActivity.this.isFinishing()) {
                // show it
                alertDialog.show();
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {

            isRunning = true;
            timeLeftMileseconds = millisUntilFinished;

            toolbar.setSubtitle(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

        }
    }

}

