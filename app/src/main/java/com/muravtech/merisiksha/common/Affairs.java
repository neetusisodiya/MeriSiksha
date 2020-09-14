//package com.muravtech.merisikashastudents.Activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.muravtech.merisikashastudents.Bean.AffairBean;
//import com.muravtech.merisikashastudents.R;
//import com.muravtech.merisikashastudents.Response.ResponseAffair;
//import com.muravtech.merisikashastudents.adapter.AffairsAdapter;
//import com.muravtech.merisikashastudents.server.APIClient;
//import com.muravtech.merisikashastudents.server.DKRInterface;
//
//import java.util.ArrayList;
//
//import okhttp3.FormBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class Affairs extends AppCompatActivity {
//
//    private RecyclerView recyclerAffairs;
//    private ImageView backAffair;
//    private ProgressDialog progressDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_affairs);
//        recyclerAffairs=findViewById(R.id.recyclerAffairs);
//        backAffair=findViewById(R.id.backAffair);
//
//        progressDialog=new ProgressDialog(this);
//
//        progressDialog.setTitle("Please Wait");
//        progressDialog.setMessage("Wait For Some Time..");
//
//        getNewClass();
//
//
//        backAffair.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//    }
//   /* private List<affairmodel> getData() {
//        List<affairmodel> list = new ArrayList<>();
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//        list.add(new affairmodel("Daily Current Affairs 7th january 2020","2020-01-12","07:57:43","The Indian Spaces Research Organization (ISRO)"));
//
//
//        return list;
//    }*/
//
//    private void getNewClass(){
//
//        progressDialog.show();
//
//        DKRInterface service = APIClient.getClient().create(DKRInterface.class);
//        FormBody.Builder builder = APIClient.createBuilder(new String[]{"subject_id"}, new
//                String[]{"1"});
//
//        Call<ResponseAffair> call = service.AffairList(builder.build());
//
//
//        call.enqueue(new Callback<ResponseAffair>() {
//            @Override
//            public void onResponse(Call<ResponseAffair> call, Response<ResponseAffair> response) {
//
////                String resturentMenu = response.body().toString();
////                Log.d("resturentMenu", resturentMenu);
////                JSONObject jsonObject = null;
////                try {
////                    jsonObject = new JSONObject(resturentMenu);
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////                String status = jsonObject.optString("status");
////
//                 if (response.body().getStatus().equals("true")) {
//                genrateAffairList((ArrayList<AffairBean>) response.body().getData());
//                 }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseAffair> call, Throwable t) {
//
//                Toast.makeText(getApplicationContext(),"Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    private void genrateAffairList(ArrayList<AffairBean> data) {
//
//        progressDialog.dismiss();
//        AffairsAdapter activity_affairs=new AffairsAdapter(data,this);
//        recyclerAffairs.setHasFixedSize(true);
//        recyclerAffairs.setLayoutManager(new LinearLayoutManager(this));
//        recyclerAffairs.setAdapter(activity_affairs);
//
//    }
//}
