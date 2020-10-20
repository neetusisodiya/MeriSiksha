package com.muravtech.merishiksha.admin_ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.module.Data;
import com.muravtech.merishiksha.server.APIClient;
import com.muravtech.merishiksha.server.DKRInterface;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddCalendarEventActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.editText_date)
    TextView editText_date;
    Calendar startDate;
    String staffid;
    DatePickerDialog startDatePickerDialog;
    SimpleDateFormat dateFormatter, df;
    @BindView(R.id.editText_title)
    EditText editText_title;

    String formattedCurrentDate;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    MaterialRadioButton genderradioButton;

    private AppPreferences preferences;
    Date c;
    Calendar newCalendar;
    String holiday="yes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sbmit_calaender_event);
        ButterKnife.bind(this);
        //staffid=getIntent().getStringExtra("staffid");
        preferences = AppPreferences.getPrefs(this);
        dateMethod();

    }

    private void dateMethod() {
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();

        startDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDate = Calendar.getInstance();
                startDate.set(year, monthOfYear, dayOfMonth);

                editText_date.setText(dateFormatter.format(startDate.getTime()));
                formattedCurrentDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        startDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        editText_date.setText(dateFormatter.format(newCalendar.getTime()));
        formattedCurrentDate = new SimpleDateFormat("yyyy-MM-dd").format(newCalendar.getTime());
        // Log.e("TAG", "onCreate:>>>>>>>>>>>>>" + formattedCurrentDate);
    }

    private void validation() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (MaterialRadioButton) findViewById(selectedId);
        if(genderradioButton.getText().toString().equalsIgnoreCase("Yes")){
            holiday="1";
        }else {
            holiday="0";
        }

        if (editText_title.getText().toString().length() == 0) {
            CommonUtils.showToast(this, "Enter title");
        } else {
            submitTask();
        }
    }

    private void submitTask() {
        CommonUtils.progressDialogShow(this);
        Call<Data> call = APIClient.getClient().create(DKRInterface.class).
                submitEventHoliday(AppPreferences.getSchoolId(), formattedCurrentDate, holiday, editText_title.getText().toString());
        call.enqueue(new Callback<Data>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    try {
                        CommonUtils.hideProgressDoalog();

                        String msg = response.body().getMessage();
                        CommonUtils.showToast(AddCalendarEventActivity.this, msg);
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


    @OnClick({R.id.iv_back, R.id.editText_date, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.editText_date:
                startDatePickerDialog.show();
                break;
            case R.id.tv_submit:
                validation();
                break;

        }
    }

}
