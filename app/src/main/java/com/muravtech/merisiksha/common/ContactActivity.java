package com.muravtech.merisiksha.common;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.utils.AppPreferences;

import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity {

//    @OnClick(R.id.imageButton_back)
//    public void back(){
//        finish();
//    }
private AppPreferences mAppPreferences;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        setToolbar();
        mAppPreferences = new AppPreferences(this);
    }
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.mipmap.ic_back_arrow));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
