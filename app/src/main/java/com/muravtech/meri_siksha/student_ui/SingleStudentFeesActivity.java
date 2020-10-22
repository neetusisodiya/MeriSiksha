package com.muravtech.meri_siksha.student_ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.StudentFees;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;
import com.muravtech.meri_siksha.utils.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleStudentFeesActivity extends BaseActivity {

    String studentid;
    String batchid;
    ProgressDialog progressDialog;

    @BindView(R.id.textView_total)
    TextView textView_total;

    @BindView(R.id.textView_paid)
    TextView textView_paid;

    @BindView(R.id.textView_remaining)
    TextView textView_remaining;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.empty_view)
    CustomTextView emptyView;
    StudentFees.DataBean studentFees;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_fees_fragment);
        ButterKnife.bind(this);
//        preferences = AppPreferences.getPrefs(this);

        getFees();
            }

    private void getFees() {
        CommonUtils.progressDialogShow(this);
        Call<StudentFees> call = APIClient.getClient().create(DKRInterface.class)
                .getfees(AppPreferences.getAccessId(),AppPreferences.getSchoolId());
        call.enqueue(new Callback<StudentFees>() {
            @Override
            public void onResponse(Call<StudentFees> call, Response<StudentFees> response) {
                if (response.body().getStatus().equalsIgnoreCase("true")) {
                    studentFees = response.body().getData().get(0);
                    CommonUtils.hideProgressDoalog();
                    llMain.setVisibility(View.VISIBLE);
                    textView_total.setText("Total Fees:" + studentFees.getTotal_fee());

                    textView_paid.setText("Paid Fees:" + studentFees.getPaid_fee());

                    textView_remaining.setText("Due Fees:" + studentFees.getDue());

                } else {
                    emptyView.setVisibility(View.VISIBLE);
//                    search_view.setVisibility(View.GONE);
//                    listView.setVisibility(View.GONE);
                    CommonUtils.hideProgressDoalog();
                }

            }

            @Override
            public void onFailure(Call<StudentFees> call, Throwable t) {
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
