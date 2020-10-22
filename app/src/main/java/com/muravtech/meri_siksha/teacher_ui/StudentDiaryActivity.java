package com.muravtech.meri_siksha.teacher_ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.module.Data;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.student_ui.BaseActivity;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDiaryActivity extends BaseActivity {
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_date)
    TextView etDate;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    String formatedCompletedate;
    Calendar newCalendar;
    DatePickerDialog startDatePickerDialog;
    SimpleDateFormat dateFormatter;
    Calendar startDate;
    @BindView(R.id.et_subject)
    EditText etSubject;
    String type,description,title,date,subject,section_id;
    private AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_diary);
        ButterKnife.bind(this);
        preferences = AppPreferences.getPrefs(this);
        if(getIntent()!=null && !getIntent().getStringExtra("type").equalsIgnoreCase("")) {
            type = getIntent().getStringExtra("type");
            if (type.equalsIgnoreCase("student")) {
                title = getIntent().getStringExtra("title");
                subject = getIntent().getStringExtra("subject");
                date = getIntent().getStringExtra("date");
                description = getIntent().getStringExtra("description");

                etTitle.setText(title);
                etSubject.setText(subject);
                etDate.setText(date);
                etContent.setText(description);

                etTitle.setEnabled(false);
                etDate.setEnabled(false);
                etContent.setEnabled(false);
                etSubject.setEnabled(false);
                tvSubmit.setVisibility(View.GONE);
            }else {
                section_id=getIntent().getStringExtra("section_id");
            }
        }
        dateMethod();
    }

    private void validation() {
        if (etTitle.getText().toString().length() == 0) {
            CommonUtils.showToast(this, "Enter title");
        } else if (etContent.getText().toString().length() == 0) {
            CommonUtils.showToast(this, "Enter description");
//        } else if (etSubject.getText().toString().length() == 0) {
//            CommonUtils.showToast(this, "Enter subject");

        } else {
            submitTask();
        }

    }

    private void dateMethod() {
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();

        startDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDate = Calendar.getInstance();
                startDate.set(year, monthOfYear, dayOfMonth);

                etDate.setText(dateFormatter.format(startDate.getTime()));
                formatedCompletedate = new SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        startDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


        etDate.setText(dateFormatter.format(newCalendar.getTime()));
        formatedCompletedate = new SimpleDateFormat("yyyy-MM-dd").format(newCalendar.getTime());

    }

    private void submitTask() {
        CommonUtils.progressDialogShow(this);
        Call<Data> call = APIClient.getClient().create(DKRInterface.class).
                submitDiary(AppPreferences.getSchoolId(),AppPreferences.getAccessId(),section_id,formatedCompletedate,
                        etSubject.getText().toString(),
                        etTitle.getText().toString(),etContent.getText().toString());
        call.enqueue(new Callback<Data>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    try {
                        CommonUtils.hideProgressDoalog();

                        String msg = response.body().getMessage();
                        CommonUtils.showToast(StudentDiaryActivity.this, msg);
                        finish();
                    } catch (Exception e) {

                        Log.e("TAG", "errrrr:>>>>>>>>>>> " + e.toString() + e.getMessage());
                        e.printStackTrace();
                    }

                } else {
                    CommonUtils.hideProgressDoalog();
                    // Toast.makeText(activity, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                // call.cancel();
                CommonUtils.hideProgressDoalog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.et_date, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_date:
                startDatePickerDialog.show();
                break;
            case R.id.tv_submit:
                validation();
                break;
        }
    }
}
