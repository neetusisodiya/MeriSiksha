package com.muravtech.merisiksha.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.ClassListBean;
import com.muravtech.merisiksha.adapter.ClassWiseListAdapter;
import com.muravtech.merisiksha.admin_ui.FeesActivity;
import com.muravtech.merisiksha.interfaces.OnItemClickListener;
import com.muravtech.merisiksha.server.APIClient;
import com.muravtech.merisiksha.server.DKRInterface;
import com.muravtech.merisiksha.student_ui.BaseActivity;
import com.muravtech.merisiksha.utils.AppPreferences;
import com.muravtech.merisiksha.utils.CommonUtils;
import com.muravtech.merisiksha.utils.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassWiseListActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    CustomTextView emptyView;
    private ArrayList<ClassListBean.DataBean> mList = new ArrayList<>();
    private ClassWiseListAdapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String type, from;
    OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.video_subject_name);
        ButterKnife.bind(this);
        titleTxt.setText("Class List");
        if(getIntent().getExtras()!=null) {
            type = getIntent().getStringExtra("type");
            from = getIntent().getStringExtra("from");
        }
        onItemClickListener = this;

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

    }

    private void findViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        mAdapter = new ClassWiseListAdapter(this, mList, onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);

        if (isOnline(true)) {
            getClassList();
        }
    }

    private void getClassList() {
        CommonUtils.progressDialogShow(this);
        mList.clear();
        Call<ClassListBean> call = APIClient.getClient().create(DKRInterface.class)
                .getClassList(AppPreferences.getSchoolId());
        call.enqueue(new Callback<ClassListBean>() {
            @Override
            public void onResponse(Call<ClassListBean> call, Response<ClassListBean> response) {
                if (response.body() != null) {
                    // Log.e("responseSubject", "" + response.body().getData().get(0).getClassX());
                    mList.clear();
                    ClassListBean childListBean1 = response.body();

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
            public void onFailure(Call<ClassListBean> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }


    @Override
    public void onClick(int position) {
        if (from.equalsIgnoreCase("fees")) {
            Intent intent = new Intent(this, FeesActivity.class);
            intent.putExtra("id", mList.get(position).getId());
            startActivity(intent);
        } else {
            // Log.e("TAG", "onClick: " + mList.get(position).getId());
            Intent intent = new Intent(this, ClassWiseSectionWiseListActivity.class);
            intent.putExtra("id", mList.get(position).getId());
            intent.putExtra("type", type);
            intent.putExtra("from", from);
            startActivity(intent);
        }
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
