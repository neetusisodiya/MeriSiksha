package com.muravtech.meri_siksha.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.DiaryListBean;
import com.muravtech.meri_siksha.adapter.StudentDiaryListAdapter;
import com.muravtech.meri_siksha.interfaces.OnItemClickListener;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.student_ui.BaseActivity;
import com.muravtech.meri_siksha.teacher_ui.StudentDiaryActivity;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;
import com.muravtech.meri_siksha.utils.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDiaryListActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.imageview_add)
    ImageView imageviewAdd;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    private RecyclerView mRecyclerView;
    private ArrayList<DiaryListBean.DataBean> mList = new ArrayList<>();
    private StudentDiaryListAdapter mAdapter;
    String type, id;
    OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.video_subject_name);
        ButterKnife.bind(this);
        onItemClickListener = this;
        titleTxt.setText("Student diary list");


        if (getIntent().getExtras() != null) {
            type = getIntent().getStringExtra("type");
            id = getIntent().getStringExtra("id");
        }
        findViews();
        if (type.equalsIgnoreCase("teacher")) {
            imageviewAdd.setVisibility(View.VISIBLE);
        }

    }

    private void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnline(true)) {
            getDiaryList();
        }
    }

    private void getDiaryList() {
        CommonUtils.progressDialogShow(this);
        mList.clear();

        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder;
        if (type.equalsIgnoreCase("teacher")) {
            builder = APIClient.createBuilder(new String[]{"school_id","teacher_id", "section_id"},
                    new String[]{AppPreferences.getSchoolId(),AppPreferences.getAccessId(), id});
        } else {
            builder = APIClient.createBuilder(new String[]{"school_id", "section_id"},
                    new String[]{AppPreferences.getSchoolId(), id});
        }
        Call<DiaryListBean> call = service.getDiaryList(builder.build());
        call.enqueue(new Callback<DiaryListBean>() {
            @Override
            public void onResponse(Call<DiaryListBean> call, Response<DiaryListBean> response) {
                if (response.body().getStatus().equalsIgnoreCase("true")) {
                    // Log.e("responseSubject", "" + response.body().getData().get(0).getClassX());
                    mList.clear();
                    mRecyclerView.setVisibility(View.VISIBLE);
                    ((CustomTextView) findViewById(R.id.empty_view)).setVisibility(View.GONE);
                    DiaryListBean childListBean1 = response.body();
                    CommonUtils.hideProgressDoalog();
                    mList.addAll(childListBean1.getData());
                    mAdapter = new StudentDiaryListAdapter(StudentDiaryListActivity.this, mList, onItemClickListener);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    CommonUtils.hideProgressDoalog();
                    ((CustomTextView) findViewById(R.id.empty_view)).setVisibility(View.VISIBLE);
                    ((CustomTextView) findViewById(R.id.empty_view)).setText("No Data Found!!!");
                    mRecyclerView.setVisibility(View.GONE);
                    // Toast.makeText(activity, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DiaryListBean> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }


    @Override
    public void onClick(int position) {
        if (type.equalsIgnoreCase("student")) {
            Intent intent = new Intent(this, StudentDiaryActivity.class);
            intent.putExtra("title", mList.get(position).getTitle());
            intent.putExtra("date", mList.get(position).getDate());
            intent.putExtra("subject", mList.get(position).getSubject_name());
            intent.putExtra("description", mList.get(position).getDescription());
            intent.putExtra("type", type);
            startActivity(intent);

        }

    }


    @OnClick({R.id.iv_back, R.id.imageview_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.imageview_add:
                Intent intent = new Intent(this, StudentDiaryActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("section_id", id);
                startActivity(intent);
                break;
        }
    }
}
