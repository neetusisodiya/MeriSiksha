package com.muravtech.merishiksha.onlineexam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultListActivity extends AppCompatActivity implements OnItemClickListener {
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
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"school_id"}, new
                String[]{AppPreferences.getSchoolId()});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<InstuctionBean> call = service.getResultList(builder.build());

            call.enqueue(new Callback<InstuctionBean>() {
                @Override
                public void onResponse(Call<InstuctionBean> call, Response<InstuctionBean> response) {
                    try {
                        CommonUtils.hideProgressDoalog();
                        Log.e("TAG", "onResponse: " + response);
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            list = response.body().getData();
                            adapter = new ResultListAdapter(ResultListActivity.this, list, onItemClickListener, "admin");
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

    private void publishResult(int position) {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"school_id", "exam_id"}, new
                String[]{AppPreferences.getSchoolId(), list.get(position).getExam_id()});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<ResponseBody> call = service.publishResult(builder.build());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        CommonUtils.hideProgressDoalog();
                        Log.e("TAG", "onResponse: " + response);
                        if (response.isSuccessful()) {
                            Toast.makeText(ResultListActivity.this, "Result Published", Toast.LENGTH_SHORT).show();
                            list.get(position).setStatus("1");
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ResultListActivity.this, "Some error occur", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
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
        publishResult(position);

    }
}
