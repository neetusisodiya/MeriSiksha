package com.muravtech.merishiksha.common;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.Response.ResponseLoginGetIngo;
import com.muravtech.merishiksha.server.APIClient;
import com.muravtech.merishiksha.server.DKRInterface;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity
        // implements View.OnClickListener
{
    @BindView(R.id.et_name)
    TextInputEditText etName;
    @BindView(R.id.et_email)
    TextInputEditText etEmail;
//    @BindView(R.id.et_changeNumber)
//    TextInputEditText etChangeNumber;
    @BindView(R.id.et_father_name)
    TextInputEditText etFatherName;
    @BindView(R.id.changeDOB)
    TextView changeDOB;
    private AppPreferences appPreferences;
    String STRtv_service_DAte,name,email,dob="",f_name;
    DatePickerDialog startDatePickerDialog;
    SimpleDateFormat dateFormatter;
    Calendar newCalendar;
    Calendar startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        appPreferences = new AppPreferences(EditProfile.this);

        name = (appPreferences.getStringValue(AppPreferences.NAME));
        email = (appPreferences.getStringValue(AppPreferences.EMAIL));
        dob = (appPreferences.getStringValue(AppPreferences.DOB));
        f_name = (appPreferences.getStringValue(AppPreferences.FatherName));
//
        etName.setText(name);
        etEmail.setText(email);
        changeDOB.setText(dob);
        etFatherName.setText(f_name);
        dateMethod();

    }


    @OnClick({R.id.backEdit, R.id.changeDOB, R.id.submitChange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backEdit:
                onBackPressed();
                break;
            case R.id.changeDOB:
                startDatePickerDialog.show();
                break;
            case R.id.submitChange:
                validation();
                break;
        }
    }
    private void validation(){
        name=etName.getText().toString();
        email=etEmail.getText().toString();
       // dob=changeDOB.getText().toString();
        f_name=etFatherName.getText().toString();

        if (name.length() == 0) {
            Toast.makeText(EditProfile.this, "Enter name", Toast.LENGTH_SHORT).show();
        } else if (email.length() == 0) {
            Toast.makeText(EditProfile.this, "Enter email", Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.isEmailValid(email)) {
            Toast.makeText(EditProfile.this, "Enter valid email", Toast.LENGTH_SHORT).show();
        } else if (f_name.length() == 0) {
            Toast.makeText(EditProfile.this, "Enter father name", Toast.LENGTH_SHORT).show();
        } else if (dob.length() == 0) {
            Toast.makeText(EditProfile.this, "Enter dob", Toast.LENGTH_SHORT).show();

        }else {
            editProfile();
        }
    }


    private void dateMethod() {
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();

        startDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDate = Calendar.getInstance();
                startDate.set(year, monthOfYear, dayOfMonth);

                changeDOB.setText(dateFormatter.format(startDate.getTime()));
                dob = new SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime());
                Log.e("TAG", "formatedCompletedate:>>>>>>>>>>>>>" + dob);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        startDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

    }

    private void editProfile() {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"school_id","id","type", "name","email","father_name","dob"}, new
                String[]{AppPreferences.getSchoolId(),AppPreferences.getAccessId() ,appPreferences.getStringValue(AppPreferences.Type),name,email,f_name,dob});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<ResponseLoginGetIngo> call = service.editProfile(builder.build());


            call.enqueue(new Callback<ResponseLoginGetIngo>() {
                @Override
                public void onResponse(Call<ResponseLoginGetIngo> call, Response<ResponseLoginGetIngo> response) {
                    try {
                        CommonUtils.hideProgressDoalog();
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            appPreferences.setStringValue(AppPreferences.NAME, response.body().getData().getName());
                            appPreferences.setStringValue(AppPreferences.EMAIL, response.body().getData().getEmail());
                            appPreferences.setStringValue(AppPreferences.IMAGE, response.body().getData().getImage());
                            appPreferences.setStringValue(AppPreferences.DOB, response.body().getData().getDob());
                            appPreferences.setStringValue(AppPreferences.FatherName, response.body().getData().getFather_name());
                            finish();
                        } else {
                            Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // DynamicToast.makeSuccess(getApplicationContext(), "successfully Login").show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseLoginGetIngo> call, Throwable t) {
                    CommonUtils.hideProgressDoalog();
                    // DynamicToast.makeError(getApplicationContext(), "Wrong mobile number").show();
                }
            });
        } else {
            CommonUtils.hideProgressDoalog();
        }
    }

}