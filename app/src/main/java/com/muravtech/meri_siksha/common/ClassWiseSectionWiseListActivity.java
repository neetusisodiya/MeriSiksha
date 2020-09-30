package com.muravtech.meri_siksha.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.SectionWiseList;
import com.muravtech.meri_siksha.adapter.ClassWiseSectionWiseListAdapter;
import com.muravtech.meri_siksha.interfaces.OnItemClickListener;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.student_ui.BaseActivity;
import com.muravtech.meri_siksha.teacher_ui.StudentListForChatActivity;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;
import com.muravtech.meri_siksha.utils.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassWiseSectionWiseListActivity extends BaseActivity implements OnItemClickListener {
    String type, from, class_id = "";
    OnItemClickListener onItemClickListener;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    CustomTextView emptyView;
    private ArrayList<SectionWiseList.DataBean> mList = new ArrayList<>();
    private ClassWiseSectionWiseListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.video_subject_name);
        ButterKnife.bind(this);
        onItemClickListener = this;
        titleTxt.setText("Section List");

        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra("id") != null) {
                class_id = getIntent().getStringExtra("id");
                type = getIntent().getStringExtra("type");
                from = getIntent().getStringExtra("from");
            }
        }
        findViews();

    }

    private void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        mAdapter = new ClassWiseSectionWiseListAdapter(this, mList, onItemClickListener);
        mRecyclerView.setAdapter(mAdapter);

        if (isOnline(true)) {
            getSectionList();
        }
    }

    private void getSectionList() {
        CommonUtils.progressDialogShow(this);
        mList.clear();
        Call<SectionWiseList> call;
        if(type.equalsIgnoreCase("admin")) {
           call = APIClient.getClient().create(DKRInterface.class)
                    .getClassWiseSectionList(AppPreferences.getSchoolId(), "", class_id);
        }else {
            call = APIClient.getClient().create(DKRInterface.class)
                    .getClassWiseSectionList(AppPreferences.getSchoolId(), AppPreferences.getAccessId(), class_id);
        }

        call.enqueue(new Callback<SectionWiseList>() {
            @Override
            public void onResponse(Call<SectionWiseList> call, Response<SectionWiseList> response) {
                if (response.body() != null) {
                    //Log.e("responseSubject", "" + response.body().getData().get(0).getClassX());
                    mList.clear();
                    SectionWiseList childListBean1 = response.body();

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
            public void onFailure(Call<SectionWiseList> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }


    @Override
    public void onClick(int position) {
        if (from.equalsIgnoreCase("chat")) {
            Intent intent = new Intent(this, StudentListForChatActivity.class);
            intent.putExtra("id", mList.get(position).getId());
            intent.putExtra("type", type);
            startActivity(intent);
        }else if (from.equalsIgnoreCase("attadance")) {
            Intent intent = new Intent(this, StudentListActivity.class);
            intent.putExtra("id", mList.get(position).getId());
            intent.putExtra("type", type);
            startActivity(intent);
        } else if (from.equalsIgnoreCase("uploads") || from.equalsIgnoreCase("notes")) {
            Intent intent = new Intent(this, SubjectListActivity.class);
            intent.putExtra("id", mList.get(position).getId());
            intent.putExtra("type", type);
            intent.putExtra("from", from);
            startActivity(intent);
        } else{
            Intent intent = new Intent(this, StudentDiaryListActivity.class);
            intent.putExtra("id", mList.get(position).getId());
            intent.putExtra("type", type);
            startActivity(intent);
        }

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
