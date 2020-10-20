package com.muravtech.merishiksha.common;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.Response.ResponseLoginGetIngo;
import com.muravtech.merishiksha.admin_ui.AdminDashBoardScreenActivity;
import com.muravtech.merishiksha.module.Login;
import com.muravtech.merishiksha.receiver.SmsBroadcastReceiver;
import com.muravtech.merishiksha.server.APIClient;
import com.muravtech.merishiksha.server.DKRInterface;
import com.muravtech.merishiksha.student_ui.BaseActivity;
import com.muravtech.merishiksha.student_ui.StudentDashBoardScreenActivity;
import com.muravtech.merishiksha.teacher_ui.TeacherDashBoardScreenActivity;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.CommonUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpReset extends BaseActivity implements View.OnClickListener {
    private static final int REQ_USER_CONSENT = 200;
    String Otp;
    SmsBroadcastReceiver smsBroadcastReceiver;
    ImageView imgback;
    AppPreferences mAppPreferences;
    private TextView verifyOtpReset;
    private TextView resendTxt, timeTxt;
    private PinEntryEditText pinEntry;
    private String Mobile;
    private String typeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_submit);
        Mobile = getIntent().getStringExtra("Mobile");
        Otp = getIntent().getStringExtra("Otp");
        typeId = getIntent().getStringExtra("typeId");
        mAppPreferences = new AppPreferences(this);
        findView();
        getTiming();
        pinEntry.setText(Otp);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals(Otp)) {
                        //Toast.makeText(OtpReset.this, "OTP is Valid ", Toast.LENGTH_SHORT).show();
//                        moveDasboard();
                        otpVertify();
                    } else {
                        Toast.makeText(OtpReset.this, "Submit Valid Otp", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        startSmsUserConsent();
    }

    private void moveDasboard() {
        mAppPreferences.setBooleanValue(AppPreferences.IS_LOGIN, true);
        mAppPreferences.setStringValue(AppPreferences.Type,typeId);
        if (typeId.equalsIgnoreCase("9")) {
            Intent i = new Intent(OtpReset.this, AdminDashBoardScreenActivity.class);
            startActivity(i);
            finish();

        } else if (typeId.equalsIgnoreCase("3")) {
            Intent i = new Intent(OtpReset.this, StudentDashBoardScreenActivity.class);
            startActivity(i);
            finish();

        } else {
            Intent i = new Intent(OtpReset.this, TeacherDashBoardScreenActivity.class);
            startActivity(i);
            finish();

        }
    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                textViewMessage.setText(
//                        String.format("%s - %s", getString(R.string.received_message), message));
                getOtpFromMessage(message);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            pinEntry.setText(matcher.group(0));

        }
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
                    }

                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

    private void findView() {
        verifyOtpReset = findViewById(R.id.OtpVerify);
        resendTxt = findViewById(R.id.ResendOtpTxt);
        timeTxt = findViewById(R.id.TimeOtpTxt);
        pinEntry = findViewById(R.id.OtpReset);

        resendTxt.setOnClickListener(this);
        verifyOtpReset.setOnClickListener(this);


    }

    public void getTiming() {

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeTxt.setText("Wait for Otp: " + millisUntilFinished / 1000);
                resendTxt.setVisibility(View.GONE);
                timeTxt.setVisibility(View.VISIBLE);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                resendTxt.setVisibility(View.VISIBLE);
                timeTxt.setVisibility(View.GONE);
                resendTxt.setText("Resend OTP");
            }

        }.start();
    }

    @Override
    public void onClick(View v) {
        if (v == verifyOtpReset) {
            if (pinEntry != null) {
                if (pinEntry.getText().toString().equalsIgnoreCase(Otp)) {
                    //Toast.makeText(OtpReset.this, "OTP is Valid ", Toast.LENGTH_SHORT).show();
                   otpVertify();
                } else {
                    Toast.makeText(OtpReset.this, "Valid Otp Submit", Toast.LENGTH_SHORT).show();
                    pinEntry.setText(null);
                }
//                pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
//                    @Override
//                    public void onPinEntered(CharSequence str) {
//                        if (str.toString().equalsIgnoreCase(Otp)) {
//                            //Toast.makeText(OtpReset.this, "OTP is Valid ", Toast.LENGTH_SHORT).show();
//                            moveDasboard();
//                        } else {
//                            Toast.makeText(OtpReset.this, "Valid Otp Submit", Toast.LENGTH_SHORT).show();
//                            pinEntry.setText(null);
//                        }
//
//                    }
//                });

            }

        }
        if (v == resendTxt) {
            OtpService();
        }
    }

    private void OtpService() {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"contact_no", "type"}, new
                String[]{Mobile, typeId});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<Login> call = service.LoginInfoGet(builder.build());


            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    try {
                        CommonUtils.hideProgressDoalog();
                        if (response.body().getStatus().equals("true")) {

                            //mAppPreferences.setLoginBean(AppPreferences.Loginbean, response.body());
                            //AppPreferences.setAccessId(response.body().getData().getId());
                            //AppPreferences.setSchoolId(response.body().getData().getSchool_id());
                            getTiming();

                        } else {
                            Toast.makeText(OtpReset.this, "Wrong mobile number", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // DynamicToast.makeSuccess(getApplicationContext(), "successfully Login").show();
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    CommonUtils.hideProgressDoalog();
                    // DynamicToast.makeError(getApplicationContext(), "Wrong mobile number").show();
                }
            });
        } else {
            CommonUtils.hideProgressDoalog();
        }
    }

    private void otpVertify() {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"contact_no", "type","secure_token"}, new
                String[]{Mobile, typeId,mAppPreferences.getAccessToken()});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<ResponseLoginGetIngo> call = service.otpVerify(builder.build());


            call.enqueue(new Callback<ResponseLoginGetIngo>() {
                @Override
                public void onResponse(Call<ResponseLoginGetIngo> call, Response<ResponseLoginGetIngo> response) {
                    try {
                        CommonUtils.hideProgressDoalog();
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            moveDasboard();
                            mAppPreferences.setLoginBean(AppPreferences.Loginbean, response.body());
                            mAppPreferences.setStringValue(AppPreferences.NAME, response.body().getData().getName());
                            mAppPreferences.setStringValue(AppPreferences.EMAIL, response.body().getData().getEmail());
                            mAppPreferences.setStringValue(AppPreferences.IMAGE, response.body().getData().getImage());
                            mAppPreferences.setStringValue(AppPreferences.DOB, response.body().getData().getDob());
                            mAppPreferences.setStringValue(AppPreferences.FatherName, response.body().getData().getFather_name());
                            mAppPreferences.setStringValue(AppPreferences.CLASS_ID, response.body().getData().getClass_id());

                            AppPreferences.setAccessId(response.body().getData().getId());
                            AppPreferences.setSectionId(response.body().getData().getSection_id());
                          //  AppPreferences.setSchoolId("12");
                            AppPreferences.setSchoolId(response.body().getData().getSchool_id());


                        } else {
                            Toast.makeText(OtpReset.this, "Wrong mobile number", Toast.LENGTH_SHORT).show();
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
