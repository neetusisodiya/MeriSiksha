package com.muravtech.meri_siksha.onlineexam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.adapter.ExamListAdapter;
import com.muravtech.meri_siksha.interfaces.OnItemClickListener;
import com.muravtech.meri_siksha.module.InstuctionBean;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamListActivity extends AppCompatActivity implements OnItemClickListener {
    String name, deauration, totalquestion, totalmarks, quiz_id;
    List<InstuctionBean.DataBean> list;
    AppPreferences preferences;
    OnItemClickListener onItemClickListener;
    ExamListAdapter adapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.no_data)
    TextView no_data;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        preferences = new AppPreferences(this);
        onItemClickListener = this;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getExamList();
        year=Calendar.getInstance().get(Calendar.YEAR);


    }


    private void getExamList() {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"user_id","school_id", "section_id", "class_id"}, new
               String[]{AppPreferences.getAccessId(),AppPreferences.getSchoolId(), AppPreferences.getSectionId(),preferences.getStringValue(AppPreferences.CLASS_ID)});
                //String[]{AppPreferences.getSchoolId(), AppPreferences.getSectionId(), preferences.getStringValue(AppPreferences.CLASS_ID)});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<InstuctionBean> call = service.getExamList(builder.build());

            call.enqueue(new Callback<InstuctionBean>() {
                @Override
                public void onResponse(Call<InstuctionBean> call, Response<InstuctionBean> response) {
                    try {
                        CommonUtils.hideProgressDoalog();
                        Log.e("TAG", "onResponse: " + response);
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            list = response.body().getData();
                            adapter = new ExamListAdapter(ExamListActivity.this, list, onItemClickListener);
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
                    // DynamicToast.makeError(getApplicationContext(), "Wrong mobile number").show();
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
        String time = CommonUtils.ConvertInMinute(String.valueOf(list.get(position).getDuration()));
        Intent intent = new Intent(ExamListActivity.this, StartExamQuestionAnswerActivity.class);
        intent.putExtra("quiz_id", String.valueOf(list.get(position).getExam_id()));
        intent.putExtra("deauration", list.get(position).getDuration());
        startActivity(intent);

    }
}
