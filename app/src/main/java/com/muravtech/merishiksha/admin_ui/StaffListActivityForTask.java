package com.muravtech.merishiksha.admin_ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.Response.StaffListDetailBean;
import com.muravtech.merishiksha.adapter.StaffListAdapter;
import com.muravtech.merishiksha.interfaces.OnItemClickListener;
import com.muravtech.merishiksha.server.APIClient;
import com.muravtech.merishiksha.server.DKRInterface;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StaffListActivityForTask extends AppCompatActivity implements OnItemClickListener {
    StaffListAdapter adapter;
    @BindView(R.id.search_view)
    EditText search_view;
    @BindView(R.id.no_data)
    TextView no_data;
    @BindView(R.id.listView)
    RecyclerView listView;
    String from;
    ArrayList<StaffListDetailBean.DataBean> staffResultBeanDetails;
    OnItemClickListener onItemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);
        ButterKnife.bind(this);
        onItemClickListener = this;
        from=getIntent().getStringExtra("from");

        staffResultBeanDetails = new ArrayList<>();

        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));

        getStaffList();

        search_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(adapter!=null)
                adapter.getFilter().filter(charSequence.toString());
                Log.e("TAG", "onTextChanged: "+charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(adapter!=null)
                adapter.getFilter().filter(editable);
                Log.e("TAG", "onTextChanged: "+editable);

            }
        });
    }

    private void getStaffList() {
        CommonUtils.progressDialogShow(this);
        Call<StaffListDetailBean> call = APIClient.getClient().create(DKRInterface.class)
                .getTeacherList(AppPreferences.getSchoolId());
        call.enqueue(new Callback<StaffListDetailBean>() {
            @Override
            public void onResponse(Call<StaffListDetailBean> call, Response<StaffListDetailBean> response) {
                if (response.body().getStatus().equalsIgnoreCase("true")) {
                    CommonUtils.hideProgressDoalog();
                    staffResultBeanDetails = (ArrayList<StaffListDetailBean.DataBean>) response.body().getData();
                    adapter = new StaffListAdapter(StaffListActivityForTask.this, staffResultBeanDetails, onItemClickListener);
                    listView.setAdapter(adapter);
                    search_view.setVisibility(View.VISIBLE);

                } else {
                    no_data.setVisibility(View.VISIBLE);
                    search_view.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    CommonUtils.hideProgressDoalog();
                }

            }

            @Override
            public void onFailure(Call<StaffListDetailBean> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }

    @Override
    public void onClick(int position) {
        if(from.equalsIgnoreCase("task")) {
            Intent intent = new Intent(this, AssignTaskActivity.class);
            intent.putExtra("teacher_id",staffResultBeanDetails.get(position).getId());
            startActivity(intent);
        }else {

        }

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
