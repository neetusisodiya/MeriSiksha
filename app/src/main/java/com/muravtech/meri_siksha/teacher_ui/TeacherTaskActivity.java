package com.muravtech.meri_siksha.teacher_ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.TaskListBean;
import com.muravtech.meri_siksha.adapter.TaskAdapter;
import com.muravtech.meri_siksha.admin_ui.StaffListActivityForTask;
import com.muravtech.meri_siksha.interfaces.OnItemClickListener;
import com.muravtech.meri_siksha.module.Data;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeacherTaskActivity extends AppCompatActivity implements OnItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.title_txt)
    TextView title_txt;
    @BindView(R.id.no_data)
    TextView no_data;
    String staffid, type;
    TaskAdapter testAdapter;
    @BindView(R.id.imageview_add)
    ImageView imageviewAdd;
    @BindView(R.id.tv_date)
    TextView tv_date;
    OnItemClickListener onItemClickListener;
    private ArrayList<TaskListBean.DataBean> tests;
    private AppPreferences preferences;
    String formatedCompletedate="",id;
    SimpleDateFormat dateFormatter;
    Calendar newCalendar,startDate;
    DatePickerDialog startDatePickerDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_task_fragment);
        ButterKnife.bind(this);
        onItemClickListener=this;
        //staffid=getIntent().getStringExtra("userid");
        type=getIntent().getStringExtra("type");
        id=getIntent().getStringExtra("id");

        preferences = AppPreferences.getPrefs(this);
        if (type.equalsIgnoreCase("admin")) {
            title_txt.setText("Teacher Task List");
        } else {
            title_txt.setText("Task List");
            imageviewAdd.setVisibility(View.GONE);
           // tv_date.setVisibility(View.GONE);
        }


        tests = new ArrayList<>();
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        dateMethod();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(tests!=null && tests.size()!=0){
            tests.clear();
        }
        getTeacherTaskList();
        no_data.setVisibility(View.GONE);
        recycler_view.setVisibility(View.VISIBLE);
    }

    private void dateMethod() {
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();

        startDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDate = Calendar.getInstance();
                startDate.set(year, monthOfYear, dayOfMonth);
                tv_date.setText(dateFormatter.format(startDate.getTime()));
                formatedCompletedate = new SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime());
                getTeacherTaskList();
                Log.e("TAG", "onDateSet: "+formatedCompletedate );
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        //startDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        Calendar twoDaysAgo = (Calendar) newCalendar.clone();
        twoDaysAgo.add(Calendar.YEAR, -5);
        startDatePickerDialog.getDatePicker().setMinDate(twoDaysAgo.getTimeInMillis());
        //startDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());


        //formatedCompletedate = new SimpleDateFormat("yyyy-MM-dd").format(newCalendar.getTime());
        Log.e("TAG", "onDateSet:>> "+formatedCompletedate );
    }

    private void getTeacherTaskList() {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder;
        if(!formatedCompletedate.equalsIgnoreCase("")) {
            if(type.equalsIgnoreCase("admin")) {
                builder = APIClient.createBuilder(new String[]{"date", id}, new
                        String[]{formatedCompletedate, AppPreferences.getSchoolId()});
            }else {
                builder = APIClient.createBuilder(new String[]{"date", id,"school_id"}, new
                        String[]{formatedCompletedate, AppPreferences.getAccessId(),AppPreferences.getSchoolId()});
            }
        }else {
            if(type.equalsIgnoreCase("admin")) {
                builder = APIClient.createBuilder(new String[]{id}, new
                        String[]{AppPreferences.getAccessId()});
            }else {
                builder = APIClient.createBuilder(new String[]{id,"school_id"}, new
                        String[]{AppPreferences.getAccessId(),AppPreferences.getSchoolId()});
            }
        }


        Call<TaskListBean> call = service.getTeacherTaskList(builder.build());
        call.enqueue(new Callback<TaskListBean>() {
            @Override
            public void onResponse(Call<TaskListBean> call, Response<TaskListBean> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        tests = (ArrayList<TaskListBean.DataBean>) response.body().getData();
                        testAdapter = new TaskAdapter(TeacherTaskActivity.this, tests, type,onItemClickListener);
                        recycler_view.setAdapter(testAdapter);
                        tv_date.setVisibility(View.VISIBLE);
                        CommonUtils.hideProgressDoalog();
                        no_data.setVisibility(View.GONE);
                        recycler_view.setVisibility(View.VISIBLE);
                        //mList.addAll(childListBean1.getData());
                    } else {
                        if(tests.size()!=0){
                            tests.clear();
                        }
                        CommonUtils.hideProgressDoalog();
                        no_data.setVisibility(View.VISIBLE);
                        recycler_view.setVisibility(View.GONE);
                      //  tv_date.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<TaskListBean> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.imageview_add,R.id.tv_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.imageview_add:
                startActivity(new Intent(this, StaffListActivityForTask.class)
                        .putExtra("from", "task"));
                break;
            case R.id.tv_date:
                startDatePickerDialog.show();
                break;
        }
    }


    @Override
    public void onClick(int position) {
        if(tests.get(position).getStatus().equalsIgnoreCase("0"))
        getTeacherComplete(position);

    }
    private void getTeacherComplete(int position) {
        CommonUtils.progressDialogShow(this);

        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"school_id","status", "id"}, new
                String[]{AppPreferences.getSchoolId(),"1",String.valueOf(tests.get(position).getId())});


        Call<Data> call = service.getTeacherComplete(builder.build());
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.body() != null) {
                    CommonUtils.hideProgressDoalog();
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        tests.get(position).setStatus("1");
                        CommonUtils.showToast(TeacherTaskActivity.this,"Your task complete");
                    }
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }
}






