package com.muravtech.meri_siksha.admin_ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.module.Data;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;

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


public class AssignTaskActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.editText_date)
    TextView editText_date;
    Calendar startDate;
    String teacher_id;
    DatePickerDialog startDatePickerDialog;
    SimpleDateFormat dateFormatter, df;
    @BindView(R.id.editText_title)
    EditText editText_title;
    @BindView(R.id.editText_description)
    EditText editText_description;
    String formattedCurrentDate, formatedCompletedate;

    private AppPreferences preferences;
    Date c;
    Calendar newCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        ButterKnife.bind(this);
        teacher_id=getIntent().getStringExtra("teacher_id");
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
                formatedCompletedate = new SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime());
                Log.e("TAG", "formatedCompletedate:>>>>>>>>>>>>>" + formatedCompletedate);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        startDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        c = newCalendar.getTime();
        formattedCurrentDate = new SimpleDateFormat("yyyy-MM-dd").format(c);
        formatedCompletedate = new SimpleDateFormat("yyyy-MM-dd").format(c);
        editText_date.setText(dateFormatter.format(c.getTime()));
        Log.e("TAG", "onCreate:>>>>>>>>>>>>>" + formattedCurrentDate);
    }

    private void validation() {
        if (editText_date.getText().toString().length() == 0) {
            CommonUtils.showToast(this, "Select date");
        } else if (editText_title.getText().toString().length() == 0) {
            CommonUtils.showToast(this, "Enter title");
        } else if (editText_description.getText().toString().length() == 0) {
            CommonUtils.showToast(this, "Enter description");
        } else {
            submitTask();
        }
    }

    private void submitTask() {
        CommonUtils.progressDialogShow(this);
        Call<Data> call = APIClient.getClient().create(DKRInterface.class).
                submitTaskforTeacher(AppPreferences.getSchoolId(),teacher_id, editText_description.getText().toString(),
                        formatedCompletedate, formattedCurrentDate, editText_title.getText().toString());
        call.enqueue(new Callback<Data>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                    if (response.isSuccessful()) {
                        try {
                            CommonUtils.hideProgressDoalog();

                            String msg = response.body().getMessage();
                            CommonUtils.showToast(AssignTaskActivity.this,"Task assigned successfully");
                            finish();
                        } catch (Exception e) {

                            Log.e("TAG", "errrrr:>>>>>>>>>>> "+e.toString()+e.getMessage());
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



    @OnClick({R.id.iv_back, R.id.editText_date, R.id.create_task_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.editText_date:
                startDatePickerDialog.show();
                break;
            case R.id.create_task_button:
                validation();
                break;
        }
    }
}
