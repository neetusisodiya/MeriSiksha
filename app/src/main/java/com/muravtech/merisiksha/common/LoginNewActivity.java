package com.muravtech.merisiksha.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.muravtech.merisiksha.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginNewActivity extends AppCompatActivity {
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_home);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.rl_school, R.id.rl_teacher, R.id.rl_parent})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_school:
                 i = new Intent(LoginNewActivity.this, LoginWithAnimation.class);
                i.putExtra("typeId","9");
                startActivity(i);
                break;
            case R.id.rl_teacher:
                 i = new Intent(LoginNewActivity.this, LoginWithAnimation.class);
                i.putExtra("typeId","4");
                startActivity(i);

                break;
            case R.id.rl_parent:
                i = new Intent(LoginNewActivity.this, LoginWithAnimation.class);
                i.putExtra("typeId","3");
                startActivity(i);
                break;
        }
    }
}