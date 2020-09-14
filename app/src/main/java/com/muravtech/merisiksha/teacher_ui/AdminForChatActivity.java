package com.muravtech.merisiksha.teacher_ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.teacher.FriendList;
import com.muravtech.merisiksha.adapter.AdminAdapter;
import com.muravtech.merisiksha.adapter.FriendListAdapter;
import com.muravtech.merisiksha.interfaces.OnItemClickListener;
import com.muravtech.merisiksha.module.AdminForChat;
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

public class AdminForChatActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.empty_view)
    CustomTextView emptyView;
    AppPreferences preferences;
    String type = "",typeId;

    private RecyclerView mRecyclerView;
    private ArrayList<AdminForChat.DataBean> mList=new ArrayList<>();
    private AdminAdapter mAdapter;
    OnItemClickListener onItemClickListener;
    int position1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.video_subject_name);
        ButterKnife.bind(this);
        preferences = AppPreferences.getPrefs(this);
        onItemClickListener=this;
        if (getIntent().getExtras() != null) {
            type = getIntent().getStringExtra("type");
            if(type.equalsIgnoreCase("teacher"))
                typeId="4";
            else
                typeId="3";

        }
        titleTxt.setText("School");
        findViews();


    }

    private void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new AdminAdapter(this, mList,onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);

        if (isOnline(true)) {
            getFriendList();
        }
    }
    private void getFriendList() {
        CommonUtils.progressDialogShow(this);
        mList.clear();
        Call<AdminForChat> call = APIClient.getClient().create(DKRInterface.class)
                    .getAdmin(AppPreferences.getSchoolId());

        call.enqueue(new Callback<AdminForChat>() {
            @Override
            public void onResponse(Call<AdminForChat> call, Response<AdminForChat> response) {
                if (response.body().isStatus()) {
                    // Log.e("responseSubject", "" + response.body().getData().get(0).getClassX());
                    //mList.clear();
                    AdminForChat childListBean1 = response.body();

                    CommonUtils.hideProgressDoalog();
                    mList.addAll(childListBean1.getData());
                } else {
                    CommonUtils.hideProgressDoalog();
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText("No Data Found!!!");
                    mRecyclerView.setVisibility(View.GONE);
                    // Toast.makeText(activity, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AdminForChat> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }

    private void sendFriendRequest(String id) {
        CommonUtils.progressDialogShow(this);

        Call<FriendList> call = APIClient.getClient().create(DKRInterface.class)
                .sendFriendRequest(AppPreferences.getSchoolId(),AppPreferences.getAccessId(),id,typeId,"9");
        call.enqueue(new Callback<FriendList>() {
            @Override
            public void onResponse(Call<FriendList> call, Response<FriendList> response) {
                CommonUtils.hideProgressDoalog();
                if (response.body().getStatus().equalsIgnoreCase("true")) {
                    CommonUtils.showToast(AdminForChatActivity.this,"Friend Request sent successfully");
                    mList.get(position1).setRelation_status("1");
                    mAdapter.notifyDataSetChanged();
                }else {
                    CommonUtils.showToast(AdminForChatActivity.this,"Something is wrong otherwise data already exits");
                }
            }

            @Override
            public void onFailure(Call<FriendList> call, Throwable t) {
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

    @Override
    public void onClick(int position) {
        sendFriendRequest(mList.get(position).getId());
        position1=position;


    }
}
