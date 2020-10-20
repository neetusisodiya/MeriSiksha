package com.muravtech.merishiksha.onlineexam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.adapter.ResultListAdapter;
import com.muravtech.merishiksha.interfaces.OnItemClickListener;
import com.muravtech.merishiksha.module.InstuctionBean;
import com.muravtech.merishiksha.server.APIClient;
import com.muravtech.merishiksha.server.DKRInterface;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultListForStudentActivity extends AppCompatActivity implements OnItemClickListener {
    List<InstuctionBean.DataBean> list;
    AppPreferences preferences;
    OnItemClickListener onItemClickListener;
    ResultListAdapter adapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.no_data)
    TextView no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        preferences = new AppPreferences(this);
        onItemClickListener = this;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getResultList();
        titleTxt.setText("Result List");
    }

    private void getResultList() {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"student_id","school_id"}, new
                String[]{AppPreferences.getAccessId(),AppPreferences.getSchoolId()});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<InstuctionBean> call = service.getResultForStudentList(builder.build());

            call.enqueue(new Callback<InstuctionBean>() {
                @Override
                public void onResponse(Call<InstuctionBean> call, Response<InstuctionBean> response) {
                    try {
                        CommonUtils.hideProgressDoalog();
                        Log.e("TAG", "onResponse: " + response);
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            list = response.body().getData();
                            adapter = new ResultListAdapter(ResultListForStudentActivity.this, list,onItemClickListener,"student");
                            recyclerView.setAdapter(adapter);
                        } else {
                            no_data.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            //Toast.makeText(LauncherActivity.this, "Wrong mobile number", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // DynamicToast.makeSuccess(getApplicationContext(), "successfully Login").show();
                    }
                }

                @Override
                public void onFailure(Call<InstuctionBean> call, Throwable t) {
                    CommonUtils.hideProgressDoalog();
                }
            });
        } else {
            CommonUtils.hideProgressDoalog();
        }
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(ResultListForStudentActivity.this, ResultAnSolutionActivityNew.class);
        intent.putExtra("quiz_id",list.get(position).getExam_id());
        startActivity(intent);
       // finish();
    }
}
