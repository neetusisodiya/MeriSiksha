package com.muravtech.merishiksha.student_ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.muravtech.merishiksha.Bean.UpdateBean;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.adapter.PdfAdapter;
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

public class NotesPDFList extends AppCompatActivity {

    PdfAdapter pdfAdapter;
    @BindView(R.id.img_network)
    ImageView imgNetwork;
    @BindView(R.id.reli)
    RelativeLayout reli;
    @BindView(R.id.recyclePDF)
    RecyclerView recyclerPDF;

    String subject_id, type, section_id;
    @BindView(R.id.empty_view)
    CustomTextView emptyView;
    // private AppPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_p_d_f_list);
        ButterKnife.bind(this);

        // preferences = new AppPreferences(this)

        subject_id = getIntent().getStringExtra("subject_id");
        type = getIntent().getStringExtra("type");
        section_id = getIntent().getStringExtra("section_id");
//        if(type.equalsIgnoreCase("teacher")) {
//            teacher_id = getIntent().getStringExtra("teacher_id");
//        }

        getNotes();

    }

    private void getNotes() {
        CommonUtils.progressDialogShow(this);

        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder;
        if (type.equalsIgnoreCase("teacher")) {
            builder = APIClient.createBuilder(new String[]{"teacher_id", "school_id", "status", "section_id", "subject_id"}, new
                    String[]{AppPreferences.getAccessId(), AppPreferences.getSchoolId(), "1", section_id, subject_id});
        } else {
            builder = APIClient.createBuilder(new String[]{"school_id", "status", "section_id", "subject_id"}, new
                    String[]{AppPreferences.getSchoolId(), "1", section_id, subject_id});
        }

        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {
            Call<UpdateBean> call = service.getFileList(builder.build());

            call.enqueue(new Callback<UpdateBean>() {
                @Override
                public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("true")) {
                            String path = response.body().getPath();
                            CommonUtils.hideProgressDoalog();
                            genratePDF((ArrayList<UpdateBean.DataBean>) response.body().getData(), path);
                            //mList.addAll(childListBean1.getData());
                        } else {
                            CommonUtils.hideProgressDoalog();
                            emptyView.setVisibility(View.VISIBLE);
                            recyclerPDF.setVisibility(View.GONE);
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
        } else {
            CommonUtils.hideProgressDoalog();

            reli.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Please check your Internet Connection.", Toast.LENGTH_SHORT).show();

        }
    }

    private void genratePDF(ArrayList<UpdateBean.DataBean> data, String path) {
        CommonUtils.hideProgressDoalog();
        //   progressDialog.dismiss();
        pdfAdapter = new PdfAdapter(this, data, path);
        recyclerPDF.setHasFixedSize(true);
        recyclerPDF.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerPDF.setAdapter(pdfAdapter);
    }

    @OnClick({R.id.iv_back, R.id.btnretry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btnretry:
                break;
        }
    }
}
