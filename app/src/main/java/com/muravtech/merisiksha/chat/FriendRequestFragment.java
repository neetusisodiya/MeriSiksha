package com.muravtech.merisiksha.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.GetFriendRequestList;
import com.muravtech.merisiksha.Response.teacher.FriendList;
import com.muravtech.merisiksha.adapter.GetFriendRequestAdapter;
import com.muravtech.merisiksha.server.APIClient;
import com.muravtech.merisiksha.server.DKRInterface;

import com.muravtech.merisiksha.utils.AppPreferences;
import com.muravtech.merisiksha.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FriendRequestFragment extends Fragment implements GetFriendRequestAdapter.ItemListener {
    View view;
    @BindView(R.id.recyclerTest)
    RecyclerView recycleChecked;
    @BindView(R.id.no_data)
    TextView no_data;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    GetFriendRequestAdapter getFriendRequestAdapter;

    ArrayList<GetFriendRequestList.DataBean> getFriendRequestLists=new ArrayList<>();
    int pos;
    String status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chat_layout_list, container, false);
        ButterKnife.bind(this, view);
        setCheckedAdapter();
        return view;
    }

    public void setCheckedAdapter() {
        recycleChecked.setLayoutManager(new LinearLayoutManager(getActivity()));
        getFriendRequestAdapter = new GetFriendRequestAdapter(getActivity(), getFriendRequestLists, this);
        recycleChecked.setAdapter(getFriendRequestAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
           getGetFriendRequestList();
    }

    private void getGetFriendRequestList() {
        //CommonUtils.progressDialogShow(getActivity());
        getFriendRequestLists.clear();

        Call<GetFriendRequestList> call = APIClient.getClient().create(DKRInterface.class)
                .getFriendRequest(AppPreferences.getSchoolId(),AppPreferences.getAccessId(),"4");
        call.enqueue(new Callback<GetFriendRequestList>() {
            @Override
            public void onResponse(Call<GetFriendRequestList> call, Response<GetFriendRequestList> response) {
                if (response.body().getStatus().equalsIgnoreCase("true")) {
                    // Log.e("responseSubject", "" + response.body().getData().get(0).getClassX());
                    getFriendRequestLists.clear();
                    GetFriendRequestList childListBean1 = response.body();
                  //  CommonUtils.hideProgressDoalog();
                    getFriendRequestLists.addAll(childListBean1.getData());
                    getFriendRequestAdapter.notifyDataSetChanged();
                }else {
                    no_data.setVisibility(View.VISIBLE);
                    swipeLayout.setVisibility(View.GONE);
                }

                Log.e("accesstokwen20 ", "" + "trueeeeeeeee");
            }

            @Override
            public void onFailure(Call<GetFriendRequestList> call, Throwable t) {
                call.cancel();
             //   CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }

    private void SubmitActionOnFriendRequest(int position,int value) {
        CommonUtils.progressDialogShow(getActivity());

        Call<GetFriendRequestList> call = APIClient.getClient().create(DKRInterface.class)
                .sendFriendRequestAction(AppPreferences.getSchoolId(),AppPreferences.getAccessId(),getFriendRequestLists.get(position).getId(),value+"");
        call.enqueue(new Callback<GetFriendRequestList>() {
            @Override
            public void onResponse(Call<GetFriendRequestList> call, Response<GetFriendRequestList> response) {
                if (response.body() != null) {
                    CommonUtils.hideProgressDoalog();
                    CommonUtils.showToast(getActivity(),"");
                }
            }

            @Override
            public void onFailure(Call<GetFriendRequestList> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onItemClick(int pos, String type) {
        this.pos = pos;
        status = type;
        if (type.equalsIgnoreCase("decline")) {
            SubmitActionOnFriendRequest(pos, 0);

        } else if (type.equalsIgnoreCase("accept")) {
            SubmitActionOnFriendRequest(pos, 1);
        }

    }
}
