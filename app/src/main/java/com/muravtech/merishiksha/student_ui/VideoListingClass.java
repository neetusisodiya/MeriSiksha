package com.muravtech.merishiksha.student_ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merishiksha.Bean.UpdateBean;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.adapter.VideoSubjectTopicsVideoAdapter;
import com.muravtech.merishiksha.server.APIClient;
import com.muravtech.merishiksha.server.DKRInterface;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.CommonUtils;
import com.muravtech.merishiksha.utils.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListingClass extends BaseActivity {
    @BindView(R.id.title_txt)
    TextView titleTxt;
    AppPreferences preferences;
    String subject_id = "",section_id;
    private Toolbar mToolBar;
    private RecyclerView mRecyclerView;
    private ArrayList<UpdateBean.DataBean> updateBean = new ArrayList<>();
    private VideoSubjectTopicsVideoAdapter mAdapter;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.video_subject_name);
        ButterKnife.bind(this);
        preferences = AppPreferences.getPrefs(this);
        if (getIntent().getExtras() != null) {
            subject_id = getIntent().getStringExtra("subject_id");
            section_id = getIntent().getStringExtra("section_id");
        }
        titleTxt.setText(R.string.video);
        findViews();
    }

    private void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (isOnline(true)) {
            getVideoList();
        }
    }

    private void getVideoList() {
        CommonUtils.progressDialogShow(this);

        //  Call<UpdateBean> call = APIClient.getClient().create(DKRInterface.class).getFileList("12");
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"school_id", "type", "status","section_id","subject_id"}, new
                String[]{AppPreferences.getSchoolId(), "video", "0",section_id,subject_id});
        Call<UpdateBean> call = service.getFileList(builder.build());

        call.enqueue(new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        updateBean = (ArrayList<UpdateBean.DataBean>) response.body().getData();
                        path=response.body().getPath();
                        mAdapter = new VideoSubjectTopicsVideoAdapter(VideoListingClass.this, updateBean,path);
                        mRecyclerView.setAdapter(mAdapter);
                        CommonUtils.hideProgressDoalog();
                        //mList.addAll(childListBean1.getData());
                    } else {
                        CommonUtils.hideProgressDoalog();
                        ((CustomTextView) findViewById(R.id.empty_view)).setVisibility(View.VISIBLE);
                        ((CustomTextView) findViewById(R.id.empty_view)).setText("No Data Found!!!");
                        mRecyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {
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
