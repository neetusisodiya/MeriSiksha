package com.muravtech.meri_siksha.common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.StudntListBean;
import com.muravtech.meri_siksha.interfaces.OnItemClickListener;
import com.muravtech.meri_siksha.module.Data;
import com.muravtech.meri_siksha.server.APIClient;
import com.muravtech.meri_siksha.server.DKRInterface;
import com.muravtech.meri_siksha.student_ui.BaseActivity;
import com.muravtech.meri_siksha.student_ui.SingleStudentAttandanceActivity;
import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.CommonUtils;
import com.muravtech.meri_siksha.utils.CustomTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.title_txt)
    TextView titleTxt;

    private Toolbar mToolBar;
    private RecyclerView mRecyclerView;
    private ArrayList<StudntListBean.DataBean> mList = new ArrayList<>();
    // private ArrayList<StudntListBean.DataBean> mListAtta = new ArrayList<>();
    private StudentListAdapter mAdapter;
    LinearLayout linearLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    AppPreferences preferences;
    String section_id = "";
    EditText editText_date;
    TextView tv_done_value, tv_select_all;
    ImageView imageView_date;
    Button button_search;
    OnItemClickListener onItemClickListener;

    Calendar startDate;

    DatePickerDialog startDatePickerDialog;
    SimpleDateFormat dateFormatter;

    SparseBooleanArray mChecked = new SparseBooleanArray();
    private static int count = 0;
    JSONObject jsonObject;
    private static boolean isNotAdded = true;
    JSONArray jsonArray = new JSONArray();
    String type,date;
    Date c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.video_subject_name);
        ButterKnife.bind(this);
        onItemClickListener=this;
        preferences = AppPreferences.getPrefs(this);

        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra("id") != null) {
                section_id = getIntent().getStringExtra("id");
                type = getIntent().getStringExtra("type");
                titleTxt.setText(R.string.student_attendance);
            }
        }
        findViews();
        if (type.equalsIgnoreCase("admin")) {
            tv_select_all.setVisibility(View.GONE);
            tv_done_value.setVisibility(View.GONE);
        }


    }

    private void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tv_done_value = (TextView) findViewById(R.id.tv_done_value);
        tv_select_all = (TextView) findViewById(R.id.tv_select_all);
        editText_date = (EditText) findViewById(R.id.editText_date);
        imageView_date = (ImageView) findViewById(R.id.imageView_date);
        button_search = (Button) findViewById(R.id.button_search);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        mAdapter = new StudentListAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        editText_date.setInputType(InputType.TYPE_NULL);
        editText_date.requestFocus();

        linearLayout.setVisibility(View.VISIBLE);

        imageView_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDatePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        startDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDate = Calendar.getInstance();
                startDate.set(year, monthOfYear, dayOfMonth);
                editText_date.setText(dateFormatter.format(startDate.getTime()));

                date = new SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime());
                if (isOnline(true)) {
                    getStudentList(date);
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        startDatePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTime().getTime());
        c = newCalendar.getTime();
        editText_date.setText(dateFormatter.format(c));
        date = new SimpleDateFormat("yyyy-MM-dd").format(c);

        if (isOnline(true)) {
            getStudentList(date);
        }

        tv_done_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onClick: >>>>>>");
                if (mChecked != null && mChecked.size() > 0) {
                    //mListAtta = new ArrayList<>();
                    for (int i = 0; i < mList.size(); i++) {
                        if (mChecked.get(i)) {
                            // mListAtta.add(mList.get(i).getMobile());

                            jsonObject = new JSONObject();
                            try {
                                jsonObject.put("student_id", mList.get(i).getId());
                                jsonObject.put("status", "1");
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Log.e("TAG", "onClick:>>>>>>>>>>>> "+jsonArray );

                        } else {
                            jsonObject = new JSONObject();
                            try {
                                jsonObject.put("student_id", mList.get(i).getId());
                                jsonObject.put("status", "0");
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                }
                submitAttadance();
                Log.e("TAG", "onClick:>>>>>>>>>>>> " + jsonArray);
            }

        });

        tv_select_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /*
                 * Set all the checkbox to True/False
                 */
                if (isNotAdded) {
                    for (int i = 0; i < count; i++) {
                        mChecked.put(i, true);
                        isNotAdded = false;
                    }
                } else {
                    for (int i = 0; i < count; i++) {
                        mChecked.put(i, false);
                        isNotAdded = true;
                    }

                }


                /*
                 * Update View
                 */
                mAdapter.notifyDataSetChanged();

            }
        });
        // if (jsonArray.length()!=0){
       // tv_done_value.setVisibility(View.VISIBLE);
        // }
    }

    private void getStudentList(String date) {
        CommonUtils.progressDialogShow(this);
        mList.clear();
        //  StudentIDPojo studentIDPojo = new StudentIDPojo();
        //studentIDPojo.setStudent_id(preferences.getStringValue(AppPreferences.ID));
        Call<StudntListBean> call = APIClient.getClient().create(DKRInterface.class)
                .getStudentList(AppPreferences.getSchoolId(),section_id,date);
        call.enqueue(new Callback<StudntListBean>() {
            @Override
            public void onResponse(Call<StudntListBean> call, Response<StudntListBean> response) {
                if (response.body().getStatus().equalsIgnoreCase("true")) {
                    //Log.e("responseSubject", "" + response.body().getData().get(0).getClassX());
                    mList.clear();
                    StudntListBean childListBean1 = response.body();

                    CommonUtils.hideProgressDoalog();
                    mList.addAll(childListBean1.getData());
                   // linearLayout.setVisibility(View.VISIBLE);
                    if (!type.equalsIgnoreCase("admin")) {
                        tv_select_all.setVisibility(View.VISIBLE);
                        tv_done_value.setVisibility(View.VISIBLE);

                    }
                    ((CustomTextView) findViewById(R.id.empty_view)).setVisibility(View.GONE);

                } else {
                    tv_select_all.setVisibility(View.GONE);
                    tv_done_value.setVisibility(View.GONE);
                   // linearLayout.setVisibility(View.GONE);
                    CommonUtils.hideProgressDoalog();
                    // Toast.makeText(activity, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
                if (mList.size() > 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);

                } else {
                    ((CustomTextView) findViewById(R.id.empty_view)).setVisibility(View.VISIBLE);
                    ((CustomTextView) findViewById(R.id.empty_view)).setText("No Data Found!!!");
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<StudntListBean> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }


    private void submitAttadance() {
        CommonUtils.progressDialogShow(this);
        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
        FormBody.Builder builder = APIClient.createBuilder(new String[]{"school_id","data"}, new
       // FormBody.Builder builder = APIClient.createBuilder(new String[]{"data"}, new
                String[]{AppPreferences.getSchoolId(),jsonArray.toString()});
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            Call<Data> call = service.saveAttendence(builder.build());


            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    try {
                        jsonArray = new JSONArray();
                        CommonUtils.hideProgressDoalog();
                        if (response.body().getStatus().equals("true")) {
                            Log.e("TAG", "onClick:>>>>>>>>>>>> " + true);
                           CommonUtils.showToast(StudentListActivity.this,getString(R.string.save_attendance_succesfully));
                        } else {
                            Log.e("TAG", "onClick:>>>>>>>>>>>> " + false);
                            Toast.makeText(StudentListActivity.this, "Wrong ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // DynamicToast.makeSuccess(getApplicationContext(), "successfully Login").show();
                    }
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    CommonUtils.hideProgressDoalog();
                    // DynamicToast.makeError(getApplicationContext(), "Wrong mobile number").show();
                }
            });
        } else {
            CommonUtils.hideProgressDoalog();
        }
    }

    @OnClick({R.id.iv_back,R.id.button_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.button_search:
                if (isOnline(true)) {
                    getStudentList(date);
                }
                break;
        }
    }

    @Override
    public void onClick(int position) {
        Intent intent=new Intent(this, SingleStudentAttandanceActivity.class);
        intent.putExtra("student_id",mList.get(position).getId());
        intent.putExtra("name",mList.get(position).getName());
        startActivity(intent);

    }


    public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.VideoViewHolder> {
        Context context;
        ArrayList<StudntListBean.DataBean> listSubjectName;


        public StudentListAdapter(Context context, ArrayList<StudntListBean.DataBean> listSubjectName) {
            this.context = context;
            this.listSubjectName = listSubjectName;
        }

        @NonNull
        @Override
        public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_groupchild, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
            holder.subject_name.setText("Name : " + listSubjectName.get(position).getName());
            //holder.tv_class.setText("Class : "+listSubjectName.get(position).getClass_name());
            if (listSubjectName.get(position).getRoll_no() != null && !listSubjectName.get(position).getRoll_no().equalsIgnoreCase("null")) {
                holder.tv_roll_no.setText("Roll no. : " + listSubjectName.get(position).getRoll_no());
            } else {
                holder.tv_roll_no.setVisibility(View.GONE);
            }
            if (listSubjectName.get(position).getAttendence_id() != null && !listSubjectName.get(position).getAttendence_id().equalsIgnoreCase("null")) {
                if (mChecked.get(position) == true || listSubjectName.get(position).getAttendence_id().equalsIgnoreCase("1")) {
                    // mChecked.put(position, true);
                    holder.checkBox.setButtonDrawable(context.getResources().getDrawable(R.drawable.right_blue));
                } else {
                    holder.checkBox.setButtonDrawable(R.drawable.right_brdr);
                }
            }

            if (type.equalsIgnoreCase("admin")) {
                if (listSubjectName.get(position).getAttendence_id() != null && !listSubjectName.get(position).getAttendence_id().equalsIgnoreCase("null")) {
                    if (mChecked.get(position) == true || listSubjectName.get(position).getAttendence_id().equalsIgnoreCase("1")) {
                        // mChecked.put(position, true);
                        holder.checkBox.setButtonDrawable(context.getResources().getDrawable(R.drawable.right_blue));
                    } else {
                        holder.checkBox.setButtonDrawable(R.drawable.right_brdr);
                    }
                }
                holder.checkBox.setEnabled(false);
            }

//            holder.ll_main.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                Intent intent = new Intent(context, VideoSubjectTopicsClass.class);
////                intent.putExtra("id",listSubjectName.get(position).getId());
////                intent.putExtra("className",VideoSubjectClass);
////               context.startActivity(intent);
//                }
//            });


            holder.checkBox.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {


                                /*
                                 * Saving Checked Position
                                 */
                                mChecked.put(position, isChecked);
                                holder.checkBox.setButtonDrawable(context.getResources().getDrawable(R.drawable.right_blue));
                                /*
                                 * Find if all the check boxes are true
                                 */
//                                if (isAllValuesChecked()) {
//
//                                    /*
//                                     * set HeaderCheck box to true
//                                     */
//                                    holder.checkBox.setChecked(isChecked);
//                                }

                            } else {

                                /*
                                 * Removed UnChecked Position
                                 */
                                mChecked.delete(position);
                                holder.checkBox.setButtonDrawable(R.drawable.right_brdr);

                                /*
                                 * Remove Checked in Header
                                 */
                                // holder.checkBox.setChecked(isChecked);

                            }

                        }
                    });

            /*
             * Set CheckBox "TRUE" or "FALSE" if mChecked == true
             */
            holder.checkBox.setChecked((mChecked.get(position) == true ? true : false));

        }

        /*
         * Find if all values are checked.
         */
//        protected boolean isAllValuesChecked() {
//
//            for (int i = 0; i < count; i++) {
//                if (!mChecked.get(i)) {
//                    return false;
//                }
//            }
//
//            return true;
//        }


        public int getItemCount() {
            count = listSubjectName.size();
            return count;
        }

        class VideoViewHolder extends RecyclerView.ViewHolder {
            TextView subject_name, tv_roll_no, tv_class;
            CheckBox checkBox;
            RelativeLayout ll_main;

            public VideoViewHolder(View itemView) {
                super(itemView);
                subject_name = (TextView) itemView.findViewById(R.id.tv_username);
                tv_roll_no = (TextView) itemView.findViewById(R.id.tv_roll_no);
                tv_class = (TextView) itemView.findViewById(R.id.tv_class);
                checkBox = (CheckBox) itemView.findViewById(R.id.iv_check);
                ll_main = (RelativeLayout) itemView.findViewById(R.id.ll_main);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClick(getAdapterPosition());
                    }
                });

            }
        }
    }

}
