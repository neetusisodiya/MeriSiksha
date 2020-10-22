package com.muravtech.meri_siksha.admin_ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.meri_siksha.Bean.Paidfee;
import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.adapter.AdminFeesAdapter;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeesActivity extends AppCompatActivity {
    ArrayList<Paidfee.DataBean> resultDetails;
    AdminFeesAdapter adapter;
    String id;
    @BindView(R.id.listView)
    RecyclerView listView;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.no_data)
    TextView noData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");

        resultDetails = new ArrayList<>();
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        titleTxt.setText("Fees List");
        getFeesList();

    }
    private void getFeesList() {
        CommonUtils.progressDialogShow(this);
        Call<Paidfee> call = APIClient.getClient().create(DKRInterface.class)
                .getFeesList(id,AppPreferences.getSchoolId());
        call.enqueue(new Callback<Paidfee>() {
            @Override
            public void onResponse(Call<Paidfee> call, Response<Paidfee> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        CommonUtils.hideProgressDoalog();
                        resultDetails = (ArrayList<Paidfee.DataBean>) response.body().getData();
                        adapter = new AdminFeesAdapter(FeesActivity.this, resultDetails);
                        listView.setAdapter(adapter);

                    } else {
                        noData.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        CommonUtils.hideProgressDoalog();
                    }
                }

            }

            @Override
            public void onFailure(Call<Paidfee> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
