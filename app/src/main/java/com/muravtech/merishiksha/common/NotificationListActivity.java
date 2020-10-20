package com.muravtech.merishiksha.common;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.adapter.NotficationAdapter;
import com.muravtech.merishiksha.interfaces.OnItemClickListener;
import com.muravtech.merishiksha.module.Notification;
import com.muravtech.merishiksha.server.APIClient;
import com.muravtech.merishiksha.server.DKRInterface;
import com.muravtech.merishiksha.student_ui.BaseActivity;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.CommonUtils;
import com.muravtech.merishiksha.utils.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationListActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    CustomTextView emptyView;
   // private ArrayList<ClassListBean.DataBean> mList = new ArrayList<>();
    private NotficationAdapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String type, from;
    OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.video_subject_name);
        ButterKnife.bind(this);
        titleTxt.setText("Notifications");
        if(getIntent().getExtras()!=null) {
            type = getIntent().getStringExtra("type");
            from = getIntent().getStringExtra("from");
        }
        onItemClickListener = this;
        findViews();
    }

    private void findViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        if (isOnline(true)) {
            try {
                getClassList();
            }catch (Exception e){

            }
        }
    }

    private void getClassList() {
        CommonUtils.progressDialogShow(this);
        Call<Notification> call = APIClient.getClient().create(DKRInterface.class)
                .getNotificationList(AppPreferences.getAccessId(),AppPreferences.getSchoolId());
        call.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                CommonUtils.hideProgressDoalog();
                if (response.body().isStatus()) {
                    Notification childListBean1 = response.body();
                    CommonUtils.hideProgressDoalog();
                    //mList.addAll(childListBean1.getData());
                    mAdapter = new NotficationAdapter(NotificationListActivity.this, childListBean1.getData(), onItemClickListener);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText("No Data Found!!!");
                    mRecyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }


    @Override
    public void onClick(int position) {
//        if (from.equalsIgnoreCase("fees")) {
//            Intent intent = new Intent(this, FeesActivity.class);
//            intent.putExtra("id", mList.get(position).getId());
//            startActivity(intent);
//        } else {
//            // Log.e("TAG", "onClick: " + mList.get(position).getId());
//            Intent intent = new Intent(this, ClassWiseSectionWiseListActivity.class);
//            intent.putExtra("id", mList.get(position).getId());
//            intent.putExtra("type", type);
//            intent.putExtra("from", from);
//            startActivity(intent);
//        }
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
