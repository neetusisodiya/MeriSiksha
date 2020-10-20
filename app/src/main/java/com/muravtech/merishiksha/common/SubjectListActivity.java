package com.muravtech.merishiksha.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.Response.SubjectListBean;
import com.muravtech.merishiksha.adapter.SubjectListAdapter;
import com.muravtech.merishiksha.interfaces.OnItemClickListener;
import com.muravtech.merishiksha.server.APIClient;
import com.muravtech.merishiksha.server.DKRInterface;
import com.muravtech.merishiksha.student_ui.BaseActivity;
import com.muravtech.merishiksha.student_ui.NotesPDFList;
import com.muravtech.merishiksha.student_ui.VideoListingClass;
import com.muravtech.merishiksha.teacher_ui.UploadActivity;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.CommonUtils;
import com.muravtech.merishiksha.utils.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectListActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    CustomTextView emptyView;
    private ArrayList<SubjectListBean.DataBean> mList = new ArrayList<>();
    private SubjectListAdapter mAdapter;
    String type, id,from;
    OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.video_subject_name);
        ButterKnife.bind(this);
        if(getIntent().getExtras()!=null) {
            type = getIntent().getStringExtra("type");
            if(getIntent().getStringExtra("id")!=null) {
                id = getIntent().getStringExtra("id");
            }
            from = getIntent().getStringExtra("from");
        }
        onItemClickListener = this;
        titleTxt.setText(R.string.subject);


        findViews();
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                if (isOnline(true)) {
//                    mList.clear();
//                    getClassList();
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//            }
//        });
//
    }

    private void findViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        mAdapter = new SubjectListAdapter(this, mList, onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);

        if (isOnline(true)) {
            getClassList();
        }
    }

    private void getClassList() {
        CommonUtils.progressDialogShow(this);
        mList.clear();
        Call<SubjectListBean> call = APIClient.getClient().create(DKRInterface.class)
               // .getSubjectList(AppPreferences.getSchoolId());
                .getSubjectList(AppPreferences.getSchoolId(),id);
        call.enqueue(new Callback<SubjectListBean>() {
            @Override
            public void onResponse(Call<SubjectListBean> call, Response<SubjectListBean> response) {
                if (response.body() != null) {
                    // Log.e("responseSubject", "" + response.body().getData().get(0).getClassX());
                    mList.clear();
                    SubjectListBean childListBean1 = response.body();

                    CommonUtils.hideProgressDoalog();
                    mList.addAll(childListBean1.getData());
                } else {
                    CommonUtils.hideProgressDoalog();
                    // Toast.makeText(activity, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
                if (mList.size() > 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);

                } else {
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText("No Data Found!!!");
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SubjectListBean> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }


    @Override
    public void onClick(int position) {
        if(from.equalsIgnoreCase("video")) {
            Intent intent = (new Intent(this, VideoListingClass.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            intent.putExtra("section_id", id);
            intent.putExtra("subject_id", mList.get(position).getId());
            startActivity(intent);
        }else if(from.equalsIgnoreCase("notes")) {
            Intent intent=(new Intent(this, NotesPDFList.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            intent.putExtra("subject_id",mList.get(position).getId());
            intent.putExtra("section_id", id);
            intent.putExtra("type",type);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, UploadActivity.class);
            intent.putExtra("subject_id", mList.get(position).getId());
            intent.putExtra("section_id", id);
            intent.putExtra("type", type);
            // intent.putExtra("from", from);
            startActivity(intent);
        }
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
