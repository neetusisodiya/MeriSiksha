package com.muravtech.meri_siksha.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.meri_siksha.Bean.UpdateBean;
import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.adapter.UdateAdapter;
import com.muravtech.meri_siksha.interfaces.OnItemClickListener;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryActivity extends AppCompatActivity implements OnItemClickListener {
    @BindView(R.id.listView)
    RecyclerView recycler_view;
    UdateAdapter adapter;
    @BindView(R.id.no_data)
    TextView noData;
    private ArrayList<UpdateBean.DataBean> updateBean;
    OnItemClickListener onItemClickListener;
    String path,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        onItemClickListener=this;

        getUpdateList();
        updateBean = new ArrayList<>();
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));


    }


    private void getUpdateList() {
        CommonUtils.progressDialogShow(this);

        //  Call<UpdateBean> call = APIClient.getClient().create(DKRInterface.class).getFileList("12");
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"school_id", "type", "status"}, new
                    String[]{AppPreferences.getSchoolId(), "image", "0"});


        Call<UpdateBean> call = service.getFileList(builder.build());

        call.enqueue(new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        updateBean = (ArrayList<UpdateBean.DataBean>) response.body().getData();
                        path=response.body().getPath();
                        adapter = new UdateAdapter(GalleryActivity.this, updateBean,path,onItemClickListener);
                        recycler_view.setAdapter(adapter);
                        CommonUtils.hideProgressDoalog();
                        //mList.addAll(childListBean1.getData());
                    } else {
                        CommonUtils.hideProgressDoalog();
                        noData.setVisibility(View.VISIBLE);
                        recycler_view.setVisibility(View.GONE);
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

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, ImageUpdateDetailActivity.class);
        intent.putExtra("path",path);
        intent.putExtra("file",updateBean.get(position));
        startActivity(intent);


    }
}
