package com.muravtech.merisiksha.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.ResponseLoginGetIngo;
import com.muravtech.merisiksha.module.Login;
import com.muravtech.merisiksha.server.APIClient;
import com.muravtech.merisiksha.server.DKRInterface;
import com.muravtech.merisiksha.utils.AppPreferences;
import com.muravtech.merisiksha.utils.CommonUtils;

import java.util.Random;

import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginWithAnimation extends Activity {
    String typeId;
    EditText edt_mobile;
    TextView submit;
    AppPreferences mAppPreferences;
    private String formatted = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_animation);
        mAppPreferences = new AppPreferences(this);
        typeId=getIntent().getStringExtra("typeId");
        findViewId();
    }

    private void findViewId() {
        edt_mobile = findViewById(R.id.edt_mobile);
        submit = findViewById(R.id.submit);
        edt_mobile.setCursorVisible(true);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_mobile.getText().toString().length() == 0) {
                    Toast.makeText(LoginWithAnimation.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                } else {
                    checkUser();
                }
            }
        });
    }


    private void checkUser() {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"contact_no", "type"}, new
                String[]{edt_mobile.getText().toString(), typeId});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<ResponseBody> call = service.checkUser(builder.build());


            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        CommonUtils.hideProgressDoalog();
                        if (response.isSuccessful()) {

                            OtpService();

                        } else {
                            Toast.makeText(LoginWithAnimation.this, "Wrong mobile number", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // DynamicToast.makeSuccess(getApplicationContext(), "successfully Login").show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    CommonUtils.hideProgressDoalog();
                    // DynamicToast.makeError(getApplicationContext(), "Wrong mobile number").show();
                }
            });
        } else {
            CommonUtils.hideProgressDoalog();
        }
    }

    private void OtpService() {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"contact_no", "type"}, new
                String[]{edt_mobile.getText().toString(), typeId});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<Login> call = service.LoginInfoGet(builder.build());


            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    try {
                        CommonUtils.hideProgressDoalog();
                        if (response.body().getStatus().equalsIgnoreCase("true")) {

//
                            Intent i = new Intent(LoginWithAnimation.this, OtpReset.class);
                            i.putExtra("Otp",response.body().getOtp());
                            i.putExtra("Mobile",edt_mobile.getText().toString());
                            i.putExtra("typeId",typeId);
                            startActivity(i);
                            finishAffinity();
                            finish();

                        } else {
                            Toast.makeText(LoginWithAnimation.this, "Wrong mobile number", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // DynamicToast.makeSuccess(getApplicationContext(), "successfully Login").show();
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    CommonUtils.hideProgressDoalog();
                    Log.e("TAG", "onFailure: "+t );
                    // DynamicToast.makeError(getApplicationContext(), "Wrong mobile number").show();
                }
            });
        } else {
            CommonUtils.hideProgressDoalog();
        }
    }


}
