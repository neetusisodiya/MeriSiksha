package com.muravtech.meri_siksha.onlineexam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.onlineexam.models.FragmentAnalysisModel;
import com.muravtech.meri_siksha.onlineexam.models.FragmentSolutionModel;
import com.muravtech.meri_siksha.onlineexam.models.ResultsFragmentModel;

import com.muravtech.meri_siksha.onlineexam.utilities.CircularTextView;
import com.muravtech.meri_siksha.onlineexam.utilities.CustomProgress;
import com.muravtech.meri_siksha.onlineexam.utilities.ExceptionHandler;
import com.muravtech.meri_siksha.onlineexam.utilities.MainPagerAdapter;
import com.muravtech.meri_siksha.onlineexam.utilities.PicassoImageGetter;
import com.muravtech.meri_siksha.onlineexam.utilities.SharedPrefData;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultAnSolutionActivityNew extends AppCompatActivity {

    public static ProgressDialog pDialog;
    public static ArrayList<ResultsFragmentModel> resultsFragmentModelArrayList;
    public static ArrayList<FragmentAnalysisModel> fragmentAnalysisModelArrayList;
    public static ArrayList<FragmentSolutionModel> fragmentSolutionModelArrayList;
    public static ArrayList<FragmentSolutionModel> fragmentSolutionModelArrayListSub;
    public static FragmentAnalysisAdapter mFragmentAnalysisAdapter;
    String examPDFDATA, Examname;
    String quiz_id;
    LinearLayout activity_result, activity_analysis, activity_solutions;
    View viewResult, viewAnalysis, viewSolutions;
    TextView result, analysis, solutions;

    TextView tser_ans;
    LinearLayout user_ans1;
    JSONObject mResponseJsonObject;
    // Result Views
    CustomProgress customProgressNotAnswered, customProgressCorrectAnswered, customProgressWrongAnswered;
    TextView notAnsweredProgress, textViewCorrectAnswerProgress, textViewWrongAnswerProgress, totalQuestions, yourScoreTv;
    CheckBox checkBoxTotalAnalysis;
    Resources res;

    CircularTextView allCorrectAnswer, allWrongAnswer, allNotAnswer, CorrectAnswer, WrongAnswer, NotAnswer;

    TextView yourRank, totalCandidates, bestScore, textViewQuestionNumber;
    Button feedback, downloadPdf;

    // Analysis Views
    ListView listView;
    RelativeLayout rLayoutButtons;
    Button buttonPrevious, buttonNext;
    LinearLayout correctLinear, wrongLinear, notAnsLinear;
    View viewSolutionCorrect, viewSolutionWrong, viewSolutionNotAns;
    //  private ConnectionDetector connectionDetector;
    // Solutions Views
    private ViewPager pager = null;
    private MainPagerAdapter pagerAdapter = null;
    AppPreferences preferences;

    //  Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_an_solution_new);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        preferences=new AppPreferences(this);
        quiz_id=getIntent().getStringExtra("quiz_id");
        //connectionDetector = new ConnectionDetector(getApplicationContext());


        //  Physics.writeIntPreference(SharedPrefData.PREF_GK_LANGUAGE, 1);

        System.gc();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Result Analysis");
        // setting toolbar

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        res = getResources();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        resultsFragmentModelArrayList = new ArrayList<ResultsFragmentModel>();
        fragmentAnalysisModelArrayList = new ArrayList<FragmentAnalysisModel>();
        fragmentSolutionModelArrayList = new ArrayList<FragmentSolutionModel>();
        fragmentSolutionModelArrayListSub = new ArrayList<FragmentSolutionModel>();

        getAllViews();


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int myPos = position + 1;

                textViewQuestionNumber.setText("" + myPos + "/" + fragmentSolutionModelArrayList.size());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_result.setVisibility(View.VISIBLE);
                activity_analysis.setVisibility(View.GONE);
                activity_solutions.setVisibility(View.GONE);
                viewResult.setVisibility(View.VISIBLE);
                viewAnalysis.setVisibility(View.INVISIBLE);
                viewSolutions.setVisibility(View.INVISIBLE);


            }
        });


        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity_result.setVisibility(View.GONE);
                activity_analysis.setVisibility(View.VISIBLE);
                activity_solutions.setVisibility(View.GONE);
                viewResult.setVisibility(View.INVISIBLE);
                viewAnalysis.setVisibility(View.VISIBLE);
                viewSolutions.setVisibility(View.INVISIBLE);


            }
        });


        solutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity_result.setVisibility(View.GONE);
                activity_analysis.setVisibility(View.GONE);
                activity_solutions.setVisibility(View.VISIBLE);
                viewResult.setVisibility(View.INVISIBLE);
                viewAnalysis.setVisibility(View.INVISIBLE);
                viewSolutions.setVisibility(View.VISIBLE);


            }
        });


        correctLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewSolutionCorrect.setVisibility(View.VISIBLE);
                viewSolutionWrong.setVisibility(View.INVISIBLE);
                viewSolutionNotAns.setVisibility(View.INVISIBLE);


                fragmentSolutionModelArrayList.clear();

                int i = pagerAdapter.getCount();
                while (pagerAdapter.getCount() > 0) {
                    removeView(pagerAdapter.getView(i - 1));
                    i--;
                }

                for (int j = 0; j < fragmentSolutionModelArrayListSub.size(); j++)
                {


                    FragmentSolutionModel model = fragmentSolutionModelArrayListSub.get(j);

                    if (model.getQuestiontype().equalsIgnoreCase("RangeAnswer")) {
                        String names = model.getCorrectanswer();
                        String[] namesList;


                        namesList = names.split("to");

                        String name1 = namesList[0];
                        String name2 = namesList[1];

                        float number,number2;

                        if(new Float(name1).floatValue() >= new Float(name2).floatValue())
                        {
                            number = new Float(name1).floatValue();
                            number2 = new Float(name2).floatValue();
                        }

                        else
                        {
                            number = new Float(name2).floatValue();
                            number2 = new Float(name1).floatValue();
                        }

                        if (!model.getUseranswer().equalsIgnoreCase("null"))
                        {
                            float answer = new Float(model.getUseranswer()).floatValue();

                            if (answer <= number && answer >= number2)
                            {
                                fragmentSolutionModelArrayList.add(model);
                            }
                        }

                    }
                    else {

                        if (model.getUseranswer().equalsIgnoreCase(model.getCorrectanswer()))

                        {
                            fragmentSolutionModelArrayList.add(model);
                        }
                    }

                }


                Log.e("size", "" + fragmentSolutionModelArrayList.size());

                if (fragmentSolutionModelArrayList.size() > 0) {
                    setDefaultPagerData();
                } else {
                    textViewQuestionNumber.setText("0/0");
                }


            }
        });
        wrongLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewSolutionCorrect.setVisibility(View.INVISIBLE);
                viewSolutionWrong.setVisibility(View.VISIBLE);
                viewSolutionNotAns.setVisibility(View.INVISIBLE);

                fragmentSolutionModelArrayList.clear();

                int i = pagerAdapter.getCount();
                while (pagerAdapter.getCount() > 0) {
                    removeView(pagerAdapter.getView(i - 1));
                    i--;
                }


                for (int j = 0; j < fragmentSolutionModelArrayListSub.size(); j++) {


                    FragmentSolutionModel model = fragmentSolutionModelArrayListSub.get(j);


                    if (model.getQuestiontype().equalsIgnoreCase("RangeAnswer")) {
                        String names = model.getCorrectanswer();

                        String[] namesList;

                        namesList = names.split("to");

                        String name1 = namesList[0];
                        String name2 = namesList[1];

                        float number,number2;

                        if(new Float(name1).floatValue() >= new Float(name2).floatValue())
                        {
                            number = new Float(name1).floatValue();
                            number2 = new Float(name2).floatValue();
                        }

                        else
                        {
                            number = new Float(name2).floatValue();
                            number2 = new Float(name1).floatValue();
                        }

                        if (!model.getUseranswer().equalsIgnoreCase("null"))
                        {
                            float answer = new Float(model.getUseranswer()).floatValue();

                            if (answer <= number && answer >= number2)
                            {
                            }
                            else
                            {
                                fragmentSolutionModelArrayList.add(model);
                            }


                        }
                    } else {

                        if (model.getCorrectanswer().equalsIgnoreCase(model.getUseranswer()))

                        {


                        }
                        else {

                            if (model.getUseranswer().equalsIgnoreCase("0")) {
                            }
                            else
                            {
                                fragmentSolutionModelArrayList.add(model);
                            }
                        }
                    }

                }

                Log.e("size", "" + fragmentSolutionModelArrayList.size());
                if (fragmentSolutionModelArrayList.size() > 0) {
                    setDefaultPagerData();
                } else {
                    textViewQuestionNumber.setText("0/0");
                }

            }
        });
        notAnsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewSolutionCorrect.setVisibility(View.INVISIBLE);
                viewSolutionWrong.setVisibility(View.INVISIBLE);
                viewSolutionNotAns.setVisibility(View.VISIBLE);

                fragmentSolutionModelArrayList.clear();

                int i = pagerAdapter.getCount();
                while (pagerAdapter.getCount() > 0) {
                    removeView(pagerAdapter.getView(i - 1));
                    i--;
                }

                for (int j = 0; j < fragmentSolutionModelArrayListSub.size(); j++) {

                    FragmentSolutionModel model = fragmentSolutionModelArrayListSub.get(j);

                    if (model.getQuestiontype().equalsIgnoreCase("RangeAnswer")) {
                        if (model.getUseranswer().equalsIgnoreCase("null"))

                        {
                            fragmentSolutionModelArrayList.add(model);

                        }
                    } else {
                        if (model.getUseranswer().equalsIgnoreCase("0"))

                        {
                            fragmentSolutionModelArrayList.add(model);

                        }
                    }
                }


                Log.e("size", "" + fragmentSolutionModelArrayList.size());
                if (fragmentSolutionModelArrayList.size() > 0) {
                    setDefaultPagerData();
                } else {
                    textViewQuestionNumber.setText("0/0");
                }

            }
        });

        getReports();

    }
    private void getReports() {

        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"school_id","student_id", "exam_id"}, new
                String[]{AppPreferences.getSchoolId(),AppPreferences.getAccessId(),quiz_id});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<JsonObject> call = service.getReport(builder.build());


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

                                Toast.makeText(ResultAnSolutionActivityNew.this, "Your exam is submit", Toast.LENGTH_SHORT).show();
                                String Success = jsonObject.getString("status");
                                String Message = jsonObject.getString("Message");

                                if (Success.equalsIgnoreCase("true")) {

                                    preferences.setStringValue(SharedPrefData.PREF_QUIZID, quiz_id);
                                    preferences.setStringValue(SharedPrefData.PREF_RESPONSEDATA, s);
                                    setApiData();

                                }
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
    private void setApiData(){
        try {
            resultsFragmentModelArrayList.clear();
            fragmentAnalysisModelArrayList.clear();
            fragmentSolutionModelArrayList.clear();
            fragmentSolutionModelArrayListSub.clear();

            mResponseJsonObject = new JSONObject(preferences.getStringValue(SharedPrefData.PREF_RESPONSEDATA));

            quiz_id = preferences.getStringValue(SharedPrefData.PREF_QUIZID);


            String resultResponse = mResponseJsonObject.getString("result");

            JSONArray mJsonArrayResponse = new JSONArray(resultResponse);
            JSONObject mJsonObjectResults = mJsonArrayResponse.getJSONObject(0);

            ResultsFragmentModel resultsFragmentModel = new ResultsFragmentModel();

            resultsFragmentModel.setTotalMarks(mJsonObjectResults.getString("total_marks"));
            resultsFragmentModel.setTotalQuestion(mJsonObjectResults.getString("total_questions"));
            resultsFragmentModel.setAttempedQuestion(mJsonObjectResults.getString("attempedQuestion"));
            resultsFragmentModel.setCorrectAnswer(mJsonObjectResults.getString("correctAnswer"));
            resultsFragmentModel.setWrongAnswer(mJsonObjectResults.getString("wrongAnswer"));
            // resultsFragmentModel.setNegativeMarks(mJsonObjectResults.getString("negativeMarks"));

//            resultsFragmentModel.setObtainMarks(mJsonObjectResults.getString("obtainMarks"));
//            resultsFragmentModel.setUserRank(mJsonObjectResults.getString("userRank"));
//            resultsFragmentModel.setTotalUser(mJsonObjectResults.getString("totalUser"));
//            resultsFragmentModel.setMaxMarks(mJsonObjectResults.getString("maxMarks"));
            resultsFragmentModelArrayList.add(resultsFragmentModel);

            String resultAnalysis = mResponseJsonObject.getString("analysis");

            Log.d("st_value.",resultAnalysis);
            JSONArray mJsonArrayAnalysis = new JSONArray(resultAnalysis);

            for (int l = 0; l < mJsonArrayAnalysis.length(); l++) {
                JSONObject mObject = mJsonArrayAnalysis.getJSONObject(l);
                FragmentAnalysisModel fragmentAnalysisModel = new FragmentAnalysisModel();

                fragmentAnalysisModel.setFindRecord(mObject.getString("correctAnswer")+"++"+mObject.getString("wrong_answer"));
                fragmentAnalysisModel.setCorrect(mObject.getString("correctAnswer"));
                fragmentAnalysisModel.setWrong(mObject.getString("wrong_answer"));
                fragmentAnalysisModel.setSubject(mObject.getString("subjects"));
                fragmentAnalysisModel.setTotalQuestion(mObject.getString("total_questions"));
                fragmentAnalysisModel.setAttemped(mObject.getString("attempted"));
                fragmentAnalysisModel.setTakenTime(mObject.getString("total_time"));
                fragmentAnalysisModelArrayList.add(fragmentAnalysisModel);


            }

            String resultSolutions = mResponseJsonObject.getString("examExplanation");

            JSONArray resultSolutionsArray = new JSONArray(resultSolutions);

            for (int i = 0; i < resultSolutionsArray.length(); i++) {
                JSONObject mObject = resultSolutionsArray.getJSONObject(i);

                FragmentSolutionModel model = new FragmentSolutionModel();
                model.setId(mObject.getString("question_id"));
                model.setCorrectanswer(mObject.getString("answer6"));
                model.setQuestiontype(mObject.getString("questiontype"));
                model.setDirection(mObject.getString("direction"));

                model.setQuestion(mObject.getString("question"));
                model.setAnswer1(mObject.getString("answer1"));
                model.setAnswer2(mObject.getString("answer2"));
                model.setAnswer3(mObject.getString("answer3"));
                model.setAnswer4(mObject.getString("answer4"));
                model.setAnswer5(mObject.getString("answer5"));
                model.setExplanation(mObject.getString("explanation"));
                model.setUseranswer(mObject.getString("useranswer"));
                model.setIsReportd("0");

                fragmentSolutionModelArrayListSub.add(model);
                fragmentSolutionModelArrayList.add(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // setting pdf button

        try {
            examPDFDATA = mResponseJsonObject.getString("Exampdf");
            Examname = mResponseJsonObject.getString("Examname");


            if (examPDFDATA.equalsIgnoreCase("NULL")) {
                downloadPdf.setVisibility(View.GONE);

            } else {
                downloadPdf.setVisibility(View.VISIBLE);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        int notAnswered = Integer.parseInt(resultsFragmentModelArrayList.get(0).getTotalQuestion()) - Integer.parseInt(resultsFragmentModelArrayList.get(0).getAttempedQuestion());
        int myTotalmarks = Integer.parseInt(resultsFragmentModelArrayList.get(0).getCorrectAnswer());
        int myTotalWrong = Integer.parseInt(resultsFragmentModelArrayList.get(0).getWrongAnswer());
        int myTotalQuestions = Integer.parseInt(resultsFragmentModelArrayList.get(0).getTotalQuestion());

        float mynotAnsweredPer = (float) notAnswered / myTotalQuestions;
        float myCorrectAnsweredPer = (float) myTotalmarks / myTotalQuestions;
        float myWrongAnsweredPer = (float) myTotalWrong / myTotalQuestions;

        customProgressNotAnswered.setMaximumPercentage(mynotAnsweredPer);
        customProgressNotAnswered.setProgressColor(res.getColor(R.color.colorPrimary));
        customProgressNotAnswered.setProgressBackgroundColor(res.getColor(R.color.blue_500));

        customProgressCorrectAnswered.setMaximumPercentage(myCorrectAnsweredPer);
        customProgressCorrectAnswered.setProgressColor(res.getColor(R.color.green_500));
        customProgressCorrectAnswered.setProgressBackgroundColor(res.getColor(R.color.blue_500));

        customProgressWrongAnswered.setMaximumPercentage(myWrongAnsweredPer);
        customProgressWrongAnswered.setProgressColor(res.getColor(R.color.orange_500));
        customProgressWrongAnswered.setProgressBackgroundColor(res.getColor(R.color.blue_500));

        notAnsweredProgress.setText(notAnswered + " Not Answered");
        textViewCorrectAnswerProgress.setText(resultsFragmentModelArrayList.get(0).getCorrectAnswer() + " Correct Answered");
        textViewWrongAnswerProgress.setText(resultsFragmentModelArrayList.get(0).getWrongAnswer() + " Wrong Answered");

        totalQuestions.setText(resultsFragmentModelArrayList.get(0).getTotalQuestion());
        //checkBoxTotalAnalysis.setText(resultsFragmentModelArrayList.get(0).getObtainMarks() + "/" + resultsFragmentModelArrayList.get(0).getTotalMarks());

        //yourScoreTv.setText(resultsFragmentModelArrayList.get(0).getObtainMarks() + "/" + resultsFragmentModelArrayList.get(0).getTotalMarks());


        allCorrectAnswer.setText(resultsFragmentModelArrayList.get(0).getCorrectAnswer());
        allCorrectAnswer.setStrokeWidth(1);
        allCorrectAnswer.setStrokeColor("#FFFFFF");
        allCorrectAnswer.setTextColor(Color.parseColor("#90CAF9"));
        allCorrectAnswer.setSolidColor("#FFFFFF");

        allWrongAnswer.setText(resultsFragmentModelArrayList.get(0).getWrongAnswer());
        allWrongAnswer.setStrokeWidth(1);
        allWrongAnswer.setStrokeColor("#FFFFFF");
        allWrongAnswer.setTextColor(Color.parseColor("#ef8008"));
        allWrongAnswer.setSolidColor("#FFFFFF");

        allNotAnswer.setText("" + notAnswered);
        allNotAnswer.setStrokeWidth(1);
        allNotAnswer.setStrokeColor("#FFFFFF");
        allNotAnswer.setTextColor(Color.parseColor("#0a4e74"));
        allNotAnswer.setSolidColor("#FFFFFF");


        CorrectAnswer.setText(resultsFragmentModelArrayList.get(0).getCorrectAnswer());
        CorrectAnswer.setStrokeWidth(1);
        CorrectAnswer.setStrokeColor("#FFFFFF");
        CorrectAnswer.setTextColor(Color.parseColor("#90CAF9"));
        CorrectAnswer.setSolidColor("#FFFFFF");

        WrongAnswer.setText(resultsFragmentModelArrayList.get(0).getWrongAnswer());
        WrongAnswer.setStrokeWidth(1);
        WrongAnswer.setStrokeColor("#FFFFFF");
        WrongAnswer.setTextColor(Color.parseColor("#ef8008"));
        WrongAnswer.setSolidColor("#FFFFFF");

        NotAnswer.setText("" + notAnswered);
        NotAnswer.setStrokeWidth(1);
        NotAnswer.setStrokeColor("#FFFFFF");
        NotAnswer.setTextColor(Color.parseColor("#0a4e74"));
        NotAnswer.setSolidColor("#FFFFFF");


       // yourRank.setText(resultsFragmentModelArrayList.get(0).getUserRank());
       // totalCandidates.setText(resultsFragmentModelArrayList.get(0).getTotalUser());
       // bestScore.setText(resultsFragmentModelArrayList.get(0).getMaxMarks());

        mFragmentAnalysisAdapter = new FragmentAnalysisAdapter(ResultAnSolutionActivityNew.this, fragmentAnalysisModelArrayList);
        listView.setAdapter(mFragmentAnalysisAdapter);


        // setting solutions data
        pagerAdapter = new MainPagerAdapter();
        pager.setAdapter(pagerAdapter);
        //setDefaultPagerData();


    }
    private void getAllViews() {
        activity_result = (LinearLayout) findViewById(R.id.activity_result);
        activity_analysis = (LinearLayout) findViewById(R.id.activity_analysis);
        activity_solutions = (LinearLayout) findViewById(R.id.activity_solutions);
        viewResult = findViewById(R.id.viewResult);
        viewAnalysis = findViewById(R.id.viewAnalysis);
        viewSolutions = findViewById(R.id.viewSolutions);
        result = (TextView) findViewById(R.id.result);
        analysis = (TextView) findViewById(R.id.analysis);
        solutions = (TextView) findViewById(R.id.solutions);
        downloadPdf = (Button) findViewById(R.id.downloadPdf);
        CorrectAnswer = (CircularTextView) findViewById(R.id.CorrectAnswer);
        WrongAnswer = (CircularTextView) findViewById(R.id.WrongAnswer);
        NotAnswer = (CircularTextView) findViewById(R.id.NotAnswer);

        textViewQuestionNumber = (TextView) findViewById(R.id.textViewQuestionNumber);

        // results views
        customProgressNotAnswered = (CustomProgress) findViewById(R.id.customProgressNotAnswered);
        customProgressCorrectAnswered = (CustomProgress) findViewById(R.id.customProgressCorrectAnswered);
        customProgressWrongAnswered = (CustomProgress) findViewById(R.id.customProgressWrongAnswered);
        notAnsweredProgress = (TextView) findViewById(R.id.notAnsweredProgress);
        textViewCorrectAnswerProgress = (TextView) findViewById(R.id.textViewCorrectAnswerProgress);
        textViewWrongAnswerProgress = (TextView) findViewById(R.id.textViewWrongAnswerProgress);
        totalQuestions = (TextView) findViewById(R.id.totalQuestions);
        checkBoxTotalAnalysis = (CheckBox) findViewById(R.id.checkBoxTotalAnalysis);
        allCorrectAnswer = (CircularTextView) findViewById(R.id.allCorrectAnswer);
        allWrongAnswer = (CircularTextView) findViewById(R.id.allWrongAnswer);
        allNotAnswer = (CircularTextView) findViewById(R.id.allNotAnswer);
        yourRank = (TextView) findViewById(R.id.yourRank);
        totalCandidates = (TextView) findViewById(R.id.totalCandidates);
        bestScore = (TextView) findViewById(R.id.bestScore);
        feedback = (Button) findViewById(R.id.feedback);

        yourScoreTv = (TextView) findViewById(R.id.yourScoreTv);


        // analysis views
        listView = (ListView) findViewById(R.id.listView);


        // solutions Views
        buttonPrevious = (Button) findViewById(R.id.buttonPrevious);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        rLayoutButtons = (RelativeLayout) findViewById(R.id.rLayoutButtons);
        pager = (ViewPager) findViewById(R.id.view_pager);


        correctLinear = (LinearLayout) findViewById(R.id.correctLinear);
        wrongLinear = (LinearLayout) findViewById(R.id.wrongLinear);
        notAnsLinear = (LinearLayout) findViewById(R.id.notAnsLinear);
        viewSolutionCorrect = findViewById(R.id.viewSolutionCorrect);
        viewSolutionWrong = findViewById(R.id.viewSolutionWrong);
        viewSolutionNotAns = findViewById(R.id.viewSolutionNotAns);


    }


    private void setDefaultPagerData() {


        for (int i = 0; i < 1; i++) {

            LayoutInflater inflater = this.getLayoutInflater();
            FrameLayout v0 = (FrameLayout) inflater.inflate(R.layout.item_solutions_view, null);
            addView(v0);
        }
        setDataOnTothePAger(1);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // if(pager.getCurrentItem())

                if (fragmentSolutionModelArrayList.size() > pagerAdapter.getCount()) {

                    if (pagerAdapter.getCount() == pager.getCurrentItem() + 1) {


                        LayoutInflater inflater = ResultAnSolutionActivityNew.this.getLayoutInflater();
                        FrameLayout v0 = (FrameLayout) inflater.inflate(R.layout.item_solutions_view, null);
                        addView(v0);

                        setDataOnTothePAger(pagerAdapter.getCount());
                    } else {
                        pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                        int myPso = pager.getCurrentItem() + 1;
                        textViewQuestionNumber.setText("" + myPso + "/" + fragmentSolutionModelArrayList.size());

                    }
                } else {

                    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                    int myPso = pager.getCurrentItem() + 1;
                    textViewQuestionNumber.setText("" + myPso + "/" + fragmentSolutionModelArrayList.size());


                }


            }
        });


        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pager.setCurrentItem(pager.getCurrentItem() - 1, true);

                int myPso = pager.getCurrentItem() + 1;

                textViewQuestionNumber.setText("" + myPso + "/" + fragmentSolutionModelArrayList.size());


            }
        });


    }

    private void setDataOnTothePAger(int i) {


        textViewQuestionNumber.setText("" + i + "/" + fragmentSolutionModelArrayList.size());


        Log.e("MyPos", "" + i);


        /*for(int i  = 0; i<pagerAdapter.getCount();i++)
        {*/
        View viewFrameLayout = pagerAdapter.getView(i - 1);
        LinearLayout viewLinearLayoutEnglish = (LinearLayout) viewFrameLayout.findViewById(R.id.linearEnglish);
        LinearLayout viewLinearLayoutHindi = (LinearLayout) viewFrameLayout.findViewById(R.id.linearHindi);

        int typeLang =0;
        //= Physics.ReadIntPreferences(SharedPrefData.PREF_GK_LANGUAGE);


        if (typeLang == 2) {


            viewLinearLayoutEnglish.setVisibility(View.GONE);
            viewLinearLayoutHindi.setVisibility(View.VISIBLE);


        } else {


            viewLinearLayoutEnglish.setVisibility(View.VISIBLE);
            viewLinearLayoutHindi.setVisibility(View.GONE);

        }


        TextView queDescription = (TextView) viewLinearLayoutEnglish.findViewById(R.id.queDescription);
        TextView answer1 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer1);

        LinearLayout ll_user_ans = (LinearLayout) viewLinearLayoutEnglish.findViewById(R.id.user_ans);

        LinearLayout ll_user_ans_select = (LinearLayout) viewLinearLayoutEnglish.findViewById(R.id.user_ans_select);
        TextView tv_user_anser_tv = (TextView) viewLinearLayoutEnglish.findViewById(R.id.user_anser_tv);
        TextView user_anser_user = (TextView) viewLinearLayoutEnglish.findViewById(R.id.user_anser_user);

        final CircularTextView answer1Tag = (CircularTextView) viewLinearLayoutEnglish.findViewById(R.id.answer1Tag);
        TextView answer2 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer2);
        final CircularTextView answer2Tag = (CircularTextView) viewLinearLayoutEnglish.findViewById(R.id.answer2Tag);
        TextView answer3 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer3);
        final CircularTextView answer3Tag = (CircularTextView) viewLinearLayoutEnglish.findViewById(R.id.answer3Tag);
        TextView answer4 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer4);
        final CircularTextView answer4Tag = (CircularTextView) viewLinearLayoutEnglish.findViewById(R.id.answer4Tag);
        TextView answer5 = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer5);
        final CircularTextView answer5Tag = (CircularTextView) viewLinearLayoutEnglish.findViewById(R.id.answer5Tag);

        TextView question = (TextView) viewLinearLayoutEnglish.findViewById(R.id.question);

        TextView explanation = (TextView) viewLinearLayoutEnglish.findViewById(R.id.explanation);

        LinearLayout linearLayout5 = (LinearLayout) viewLinearLayoutEnglish.findViewById(R.id.linearLayout5);

        LinearLayout single_eng = (LinearLayout) viewLinearLayoutEnglish.findViewById(R.id.single_eng);
        LinearLayout range_eng = (LinearLayout) viewLinearLayoutEnglish.findViewById(R.id.range_eng);
        TextView answer_eng = (TextView) viewLinearLayoutEnglish.findViewById(R.id.answer_eng);
        TextView your_answer_eng = (TextView) viewLinearLayoutEnglish.findViewById(R.id.your_answer_eng);


        //hindi views


        TextView queDescriptionHindi = (TextView) viewLinearLayoutHindi.findViewById(R.id.queDescriptionHindi);
        TextView answer1Hindi = (TextView) viewLinearLayoutHindi.findViewById(R.id.answer1Hindi);
        final CircularTextView answer1TagHindi = (CircularTextView) viewLinearLayoutHindi.findViewById(R.id.answer1TagHindi);
        TextView answer2Hindi = (TextView) viewLinearLayoutHindi.findViewById(R.id.answer2Hindi);
        final CircularTextView answer2TagHindi = (CircularTextView) viewLinearLayoutHindi.findViewById(R.id.answer2TagHindi);
        TextView answer3Hindi = (TextView) viewLinearLayoutHindi.findViewById(R.id.answer3Hindi);
        final CircularTextView answer3TagHindi = (CircularTextView) viewLinearLayoutHindi.findViewById(R.id.answer3TagHindi);
        TextView answer4Hindi = (TextView) viewLinearLayoutHindi.findViewById(R.id.answer4Hindi);
        final CircularTextView answer4TagHindi = (CircularTextView) viewLinearLayoutHindi.findViewById(R.id.answer4TagHindi);
        TextView answer5Hindi = (TextView) viewLinearLayoutHindi.findViewById(R.id.answer5Hindi);
        final CircularTextView answer5TagHindi = (CircularTextView) viewLinearLayoutHindi.findViewById(R.id.answer5TagHindi);

        LinearLayout ll_user_ans_h = (LinearLayout) viewLinearLayoutHindi.findViewById(R.id.user_ans_h);

        LinearLayout ll_user_ans_select_h = (LinearLayout) viewLinearLayoutHindi.findViewById(R.id.user_ans_select_h);

        TextView tv_user_anser_tv_h = (TextView) viewLinearLayoutHindi.findViewById(R.id.user_anser_tv_h);

        TextView user_anser_user_h = (TextView) viewLinearLayoutHindi.findViewById(R.id.user_anser_user_h);
        TextView questionHindi = (TextView) viewLinearLayoutHindi.findViewById(R.id.questionHindi);

        LinearLayout linearLayout5Hindi = (LinearLayout) viewLinearLayoutHindi.findViewById(R.id.linearLayout5Hindi);

        TextView explanationHindi = (TextView) viewLinearLayoutHindi.findViewById(R.id.explanationHindi);


        LinearLayout single_hindi = (LinearLayout) viewLinearLayoutHindi.findViewById(R.id.single_hindi);
        LinearLayout range_hindi = (LinearLayout) viewLinearLayoutHindi.findViewById(R.id.range_hindi);
        TextView answer_hin = (TextView) viewLinearLayoutHindi.findViewById(R.id.answer_hin);
        TextView your_answer_hin = (TextView) viewLinearLayoutHindi.findViewById(R.id.your_answer_hin);


        final FragmentSolutionModel model = fragmentSolutionModelArrayList.get(i - 1);

        int myQuestionNumber = 0;

        for (int j = 0; j < fragmentSolutionModelArrayListSub.size(); j++) {

            if (model.getId().equalsIgnoreCase(fragmentSolutionModelArrayListSub.get(j).getId())) {
                myQuestionNumber = j + 1;
            }

        }


        Log.e("myQuestionNumber", "" + myQuestionNumber);


        if (model.getQuestiontype().equalsIgnoreCase("RangeAnswer")) {
            single_hindi.setVisibility(View.GONE);
            single_eng.setVisibility(View.GONE);

            ll_user_ans.setVisibility(View.GONE);
            ll_user_ans_h.setVisibility(View.GONE);

            ll_user_ans_select.setVisibility(View.GONE);
            ll_user_ans_select_h.setVisibility(View.GONE);

            range_eng.setVisibility(View.VISIBLE);
            range_hindi.setVisibility(View.VISIBLE);

            if(!model.getUseranswer().equalsIgnoreCase("null")) {

                your_answer_eng.setVisibility(View.VISIBLE);
                your_answer_hin.setVisibility(View.VISIBLE);

                your_answer_eng.setText(Html.fromHtml("<b> Your Answer: </b>   " + model.getUseranswer(),
                        new PicassoImageGetter(question), null));

                your_answer_hin.setText(Html.fromHtml("<b> Your Answer: </b>   " + model.getUseranswer(),
                        new PicassoImageGetter(question), null));
            }
            else
            {
                your_answer_eng.setVisibility(View.GONE);
                your_answer_hin.setVisibility(View.GONE);

            }
        }

        if (model.getQuestiontype().equalsIgnoreCase("MultiAnswer")) {

            single_hindi.setVisibility(View.VISIBLE);
            single_eng.setVisibility(View.VISIBLE);
            ll_user_ans.setVisibility(View.VISIBLE);
            ll_user_ans_h.setVisibility(View.VISIBLE);

            ll_user_ans_select.setVisibility(View.VISIBLE);
            ll_user_ans_select_h.setVisibility(View.VISIBLE);

            range_eng.setVisibility(View.GONE);
            range_hindi.setVisibility(View.GONE);

            if(!model.getUseranswer().equalsIgnoreCase("0")) {
                ll_user_ans_select.setVisibility(View.VISIBLE);
                ll_user_ans_select_h.setVisibility(View.VISIBLE);

                your_answer_eng.setText(Html.fromHtml("<b> Your Answer: </b>   " + model.getUseranswer(),
                        new PicassoImageGetter(question), null));

                your_answer_hin.setText(Html.fromHtml("<b> Your Answer: </b>   " + model.getUseranswer(),
                        new PicassoImageGetter(question), null));
            }
            else
            {
                ll_user_ans_select.setVisibility(View.GONE);
                ll_user_ans_select_h.setVisibility(View.GONE);
            }

        }

        if (model.getQuestiontype().equalsIgnoreCase("SingleAnswer")) {
            ll_user_ans.setVisibility(View.GONE);
            single_hindi.setVisibility(View.VISIBLE);
            single_eng.setVisibility(View.VISIBLE);
            ll_user_ans_h.setVisibility(View.GONE);
            range_eng.setVisibility(View.GONE);
            range_hindi.setVisibility(View.GONE);

            ll_user_ans_select.setVisibility(View.GONE);
            ll_user_ans_select_h.setVisibility(View.GONE);
        }

        /*URLImageParser p = new URLImageParser(question, this);
        Spanned htmlSpan = Html.fromHtml("<b> Q.) </b>   "+model.getQuestion(), p, null);
        question.setText(htmlSpan);*/

        tv_user_anser_tv.setText("Correct Answer is: "+ String.valueOf( model.getCorrectanswer()));
        tv_user_anser_tv_h.setText("Correct Answer is: "+ String.valueOf( model.getCorrectanswer()));


        user_anser_user.setText("Your Answer: "+ String.valueOf( model.getUseranswer()));
        user_anser_user_h.setText("Your Answer: "+ String.valueOf( model.getUseranswer()));


        question.setText(Html.fromHtml("<b> Q.) </b>   " + model.getQuestion(),
                new PicassoImageGetter(question), null));






        answer_eng.setText(Html.fromHtml("<b> Correct Answer Range: </b>   " +  model.getCorrectanswer().replace("to", " - "),
                new PicassoImageGetter(question), null));

        // setting description
        if (model.getDirection().equalsIgnoreCase("")) {
            /*URLImageParser pdescription = new URLImageParser(queDescription, this);
            Spanned htmlSpanDescription = Html.fromHtml("<b>"+i+".) </b>   ", pdescription, null);
            queDescription.setText(htmlSpanDescription);*/


            queDescription.setText(Html.fromHtml("<b>" + myQuestionNumber + ".) </b>   ",
                    new PicassoImageGetter(queDescription), null));


        } else {
           /* URLImageParser pdescription = new URLImageParser(queDescription, this);
            Spanned htmlSpanDescription = Html.fromHtml("<b>"+i+".) </b>   "+"<b>Description :  </b> "+"\n"+model.getDirection(), pdescription, null);
            queDescription.setText(htmlSpanDescription);*/


            queDescription.setText(Html.fromHtml("<b>" + myQuestionNumber + ".) </b>   " + "<b>Description :  </b> " + "\n" + model.getDirection(),
                    new PicassoImageGetter(queDescription), null));

        }


        // setting answer1
 /*       URLImageParser pAnswer1 = new URLImageParser(answer1, this);
        Spanned htmlSpanAnswer1 = Html.fromHtml(model.getAnswer1(), pAnswer1, null);
        answer1.setText(htmlSpanAnswer1);*/


        answer1.setText(Html.fromHtml(model.getAnswer1(),
                new PicassoImageGetter(answer1), null));


        answer1Tag.setStrokeWidth(1);
        answer1Tag.setStrokeColor("#000000");
        answer1Tag.setTextColor(Color.parseColor("#000000"));
        answer1Tag.setSolidColor("#FFFFFF");


        // setting answer2
        /*URLImageParser pAnswer2 = new URLImageParser(answer2, this);
        Spanned htmlSpanAnswer2 = Html.fromHtml(model.getAnswer2(), pAnswer2, null);
        answer2.setText(htmlSpanAnswer2);*/


        answer2.setText(Html.fromHtml(model.getAnswer2(),
                new PicassoImageGetter(answer2), null));


        answer2Tag.setStrokeWidth(1);
        answer2Tag.setStrokeColor("#000000");
        answer2Tag.setTextColor(Color.parseColor("#000000"));
        answer2Tag.setSolidColor("#FFFFFF");


        // setting answer3
    /*    URLImageParser pAnswer3 = new URLImageParser(answer3, this);
        Spanned htmlSpanAnswer3 = Html.fromHtml(model.getAnswer3(), pAnswer3, null);
        answer3.setText(htmlSpanAnswer3);*/


        answer3.setText(Html.fromHtml(model.getAnswer3(),
                new PicassoImageGetter(answer3), null));

        answer3Tag.setStrokeWidth(1);
        answer3Tag.setStrokeColor("#000000");
        answer3Tag.setTextColor(Color.parseColor("#000000"));
        answer3Tag.setSolidColor("#FFFFFF");


        // setting answer 4
        /*URLImageParser pAnswer4 = new URLImageParser(answer4, this);
        Spanned htmlSpanAnswer4 = Html.fromHtml(model.getAnswer4(), pAnswer4, null);
        answer4.setText(htmlSpanAnswer4);*/


        answer4.setText(Html.fromHtml(model.getAnswer4(),
                new PicassoImageGetter(answer4), null));


        answer4Tag.setStrokeWidth(1);
        answer4Tag.setStrokeColor("#000000");
        answer4Tag.setTextColor(Color.parseColor("#000000"));
        answer4Tag.setSolidColor("#FFFFFF");


        if (model.getAnswer5().equalsIgnoreCase("NULL")) {
            linearLayout5.setVisibility(View.GONE);
        } else {
           /* URLImageParser pAnswer5 = new URLImageParser(answer5, this);
            Spanned htmlSpanAnswer5 = Html.fromHtml(model.getAnswer5(), pAnswer5, null);
            answer5.setText(htmlSpanAnswer5);*/


            answer5.setText(Html.fromHtml(model.getAnswer5(),
                    new PicassoImageGetter(answer5), null));

            answer5Tag.setStrokeWidth(1);
            answer5Tag.setStrokeColor("#000000");
            answer5Tag.setTextColor(Color.parseColor("#000000"));
            answer5Tag.setSolidColor("#FFFFFF");


        }


        if (model.getExplanation().equalsIgnoreCase("NULL")) {
            explanation.setVisibility(View.GONE);
        } else {
           /* URLImageParser explaninData = new URLImageParser(explanation, this);
            Spanned exp = Html.fromHtml("<b> Explanation :  </b>   "+model.getExplanation(), explaninData, null);
            explanation.setText(exp);*/


            explanation.setText(Html.fromHtml("<b> Explanation :  </b>   " + model.getExplanation(),
                    new PicassoImageGetter(explanation), null));


        }


       /* URLImageParser pHindi = new URLImageParser(questionHindi, this);
        Spanned htmlSpanHindi = Html.fromHtml("<b> Q.) </b>   "+model.getQuestion_hindi(), pHindi, null);
        questionHindi.setText(htmlSpanHindi);*/

//        questionHindi.setText(Html.fromHtml("<b> Q.) </b>   " + model.getQuestion_hindi(),
//                new PicassoImageGetter(questionHindi), null));
        your_answer_hin.setText(Html.fromHtml("<b> Your Answer: </b>   " + model.getUseranswer(),
                new PicassoImageGetter(question), null));
        answer_eng.setText(Html.fromHtml("<b> Correct Answer Range: </b>   " +  model.getCorrectanswer().replace("to", " - "),
                new PicassoImageGetter(question), null));
        // setting description
//        if (model.getDirection_hindi().equalsIgnoreCase("")) {
//          /*  URLImageParser pdescriptionHindi = new URLImageParser(queDescriptionHindi, this);
//            Spanned htmlSpanDescriptionHindi = Html.fromHtml("<b>"+i+".) </b>   ", pdescriptionHindi, null);
//            queDescriptionHindi.setText(htmlSpanDescriptionHindi);*/
//
//            queDescriptionHindi.setText(Html.fromHtml("<b>" + myQuestionNumber + ".) </b>   ",
//                    new PicassoImageGetter(queDescriptionHindi), null));
//
//        } else {
//            /*URLImageParser pdescriptionHindi = new URLImageParser(queDescriptionHindi, this);
//            Spanned htmlSpanDescriptionHindi = Html.fromHtml("<b>"+i+".) </b>   "+"<b>Description :  </b> "+"\n"+model.getDirection_hindi(), pdescriptionHindi, null);
//            queDescriptionHindi.setText(htmlSpanDescriptionHindi);
//*/
//
//            queDescriptionHindi.setText(Html.fromHtml("<b>" + myQuestionNumber + ".) </b>   " + "<b>Description :  </b> " + "\n" + model.getDirection_hindi(),
//                    new PicassoImageGetter(queDescriptionHindi), null));
//
//
//        }


        if (model.getExplanation().equalsIgnoreCase("NULL")) {
            explanationHindi.setVisibility(View.GONE);
        } else {
            explanationHindi.setText(Html.fromHtml("<b> Explanation : </b> " + model.getExplanation(), new PicassoImageGetter(explanationHindi), null));
        }

        if (model.getCorrectanswer().equalsIgnoreCase(model.getUseranswer())) {

            switch (model.getCorrectanswer()) {

                case "1":
                    answer1Tag.setStrokeWidth(1);
                    answer1Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer1Tag.setStrokeColor("#4CAF50");
                    answer1Tag.setSolidColor("#4CAF50");

                    answer1TagHindi.setStrokeWidth(1);
                    answer1TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer1TagHindi.setStrokeColor("#4CAF50");
                    answer1TagHindi.setSolidColor("#4CAF50");


                    break;

                case "2":
                    answer2Tag.setStrokeWidth(1);
                    answer2Tag.setStrokeColor("#4CAF50");
                    answer2Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer2Tag.setSolidColor("#4CAF50");

                    answer2TagHindi.setStrokeWidth(1);
                    answer2TagHindi.setStrokeColor("#4CAF50");
                    answer2TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer2TagHindi.setSolidColor("#4CAF50");

                    break;

                case "3":
                    answer3Tag.setStrokeWidth(1);
                    answer3Tag.setStrokeColor("#4CAF50");
                    answer3Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer3Tag.setSolidColor("#4CAF50");

                    answer3TagHindi.setStrokeWidth(1);
                    answer3TagHindi.setStrokeColor("#4CAF50");
                    answer3TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer3TagHindi.setSolidColor("#4CAF50");

                    break;

                case "4":
                    answer4Tag.setStrokeWidth(1);
                    answer4Tag.setStrokeColor("#4CAF50");
                    answer4Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer4Tag.setSolidColor("#4CAF50");

                    answer4TagHindi.setStrokeWidth(1);
                    answer4TagHindi.setStrokeColor("#4CAF50");
                    answer4TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer4TagHindi.setSolidColor("#4CAF50");


                    break;

                case "5":
                    answer5Tag.setStrokeWidth(1);
                    answer5Tag.setStrokeColor("#4CAF50");
                    answer5Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer5Tag.setSolidColor("#4CAF50");


                    answer5TagHindi.setStrokeWidth(1);
                    answer5TagHindi.setStrokeColor("#4CAF50");
                    answer5TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer5TagHindi.setSolidColor("#4CAF50");

                    break;


            }

        } else {
            switch (model.getCorrectanswer()) {
                case "1":
                    answer1Tag.setStrokeWidth(1);
                    answer1Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer1Tag.setStrokeColor("#4CAF50");
                    answer1Tag.setSolidColor("#4CAF50");

                    answer1TagHindi.setStrokeWidth(1);
                    answer1TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer1TagHindi.setStrokeColor("#4CAF50");
                    answer1TagHindi.setSolidColor("#4CAF50");


                    break;

                case "2":
                    answer2Tag.setStrokeWidth(1);
                    answer2Tag.setStrokeColor("#4CAF50");
                    answer2Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer2Tag.setSolidColor("#4CAF50");

                    answer2TagHindi.setStrokeWidth(1);
                    answer2TagHindi.setStrokeColor("#4CAF50");
                    answer2TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer2TagHindi.setSolidColor("#4CAF50");

                    break;

                case "3":
                    answer3Tag.setStrokeWidth(1);
                    answer3Tag.setStrokeColor("#4CAF50");
                    answer3Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer3Tag.setSolidColor("#4CAF50");

                    answer3TagHindi.setStrokeWidth(1);
                    answer3TagHindi.setStrokeColor("#4CAF50");
                    answer3TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer3TagHindi.setSolidColor("#4CAF50");

                    break;

                case "4":
                    answer4Tag.setStrokeWidth(1);
                    answer4Tag.setStrokeColor("#4CAF50");
                    answer4Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer4Tag.setSolidColor("#4CAF50");

                    answer4TagHindi.setStrokeWidth(1);
                    answer4TagHindi.setStrokeColor("#4CAF50");
                    answer4TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer4TagHindi.setSolidColor("#4CAF50");


                    break;

                case "5":
                    answer5Tag.setStrokeWidth(1);
                    answer5Tag.setStrokeColor("#4CAF50");
                    answer5Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer5Tag.setSolidColor("#4CAF50");


                    answer5TagHindi.setStrokeWidth(1);
                    answer5TagHindi.setStrokeColor("#4CAF50");
                    answer5TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer5TagHindi.setSolidColor("#4CAF50");

                    break;


            }


            switch (model.getUseranswer()) {

                case "1":
                    answer1Tag.setStrokeWidth(1);
                    answer1Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer1Tag.setStrokeColor("#F44336");
                    answer1Tag.setSolidColor("#F44336");

                    answer1TagHindi.setStrokeWidth(1);
                    answer1TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer1TagHindi.setStrokeColor("#F44336");
                    answer1TagHindi.setSolidColor("#F44336");


                    break;

                case "2":
                    answer2Tag.setStrokeWidth(1);
                    answer2Tag.setStrokeColor("#F44336");
                    answer2Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer2Tag.setSolidColor("#F44336");

                    answer2TagHindi.setStrokeWidth(1);
                    answer2TagHindi.setStrokeColor("#F44336");
                    answer2TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer2TagHindi.setSolidColor("#F44336");

                    break;

                case "3":
                    answer3Tag.setStrokeWidth(1);
                    answer3Tag.setStrokeColor("#F44336");
                    answer3Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer3Tag.setSolidColor("#F44336");

                    answer3TagHindi.setStrokeWidth(1);
                    answer3TagHindi.setStrokeColor("#F44336");
                    answer3TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer3TagHindi.setSolidColor("#F44336");

                    break;

                case "4":
                    answer4Tag.setStrokeWidth(1);
                    answer4Tag.setStrokeColor("#F44336");
                    answer4Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer4Tag.setSolidColor("#F44336");

                    answer4TagHindi.setStrokeWidth(1);
                    answer4TagHindi.setStrokeColor("#F44336");
                    answer4TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer4TagHindi.setSolidColor("#F44336");


                    break;

                case "5":
                    answer5Tag.setStrokeWidth(1);
                    answer5Tag.setStrokeColor("#F44336");
                    answer5Tag.setTextColor(Color.parseColor("#FFFFFF"));
                    answer5Tag.setSolidColor("#F44336");


                    answer5TagHindi.setStrokeWidth(1);
                    answer5TagHindi.setStrokeColor("#F44336");
                    answer5TagHindi.setTextColor(Color.parseColor("#FFFFFF"));
                    answer5TagHindi.setSolidColor("#F44336");

                    break;


            }


        }


    }


    //-----------------------------------------------------------------------------
    // Here's what the app should do to add a view to the ViewPager.
    public void addView(View newPage) {

        int pageIndex = pagerAdapter.addView(newPage);
        // You might want to make "newPage" the currently displayed page:
        pagerAdapter.notifyDataSetChanged();
        pager.setCurrentItem(pageIndex, true);
        Log.e("caountPageradd", "" + pagerAdapter.getCount());


    }


    public void removeView(View defunctPage) {
        int pageIndex = pagerAdapter.removeView(pager, defunctPage);
        pagerAdapter.notifyDataSetChanged();
        if (pageIndex == pagerAdapter.getCount())
            pageIndex--;
        pager.setCurrentItem(pageIndex);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("isFinished", "1");
        setResult(113, intent);
        finish();


    }

}
