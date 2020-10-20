package com.muravtech.merishiksha.chat;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import com.muravtech.merishiksha.Application;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.Response.MessageHistoryList;
import com.muravtech.merishiksha.adapter.ChatRoomListAdapter;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.Constants;
import com.muravtech.merishiksha.utils.EndlessRecyclerViewScrollListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatRoomFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerTest)
    RecyclerView chatRoomRecycleview;
    Unbinder unbinder;
    ChatRoomListAdapter chatRoomListAdapter;
    @BindView(R.id.no_data)
    TextView tvNoChat;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    private Context mContext;
    private MessageHistoryList messageHistoryLists;
    private BroadcastReceiver getMessageHistoryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.hasExtra("data")) {
                String action = intent.getAction();

                if (action.equalsIgnoreCase(Constants.BROADCAST_MESSAGE_HISTORY_RESPONSE)) {

                    if (swipeLayout != null) {
                        swipeLayout.setRefreshing(false);
                    }
                    Log.e("rahul", intent.getStringExtra("data"));
                    if (page == 0) {
                        messageHistoryLists = (MessageHistoryList) new Gson().fromJson(intent.getStringExtra("data"), MessageHistoryList.class);
                        Log.e("messageHistoryLists",messageHistoryLists.toString());
                    } else {
                        MessageHistoryList localMessageHistoryLists = (MessageHistoryList) new Gson().fromJson(intent.getStringExtra("data"), MessageHistoryList.class);
                        messageHistoryLists.getData().addAll(localMessageHistoryLists.getData());
                    }
                    if (tvNoChat != null) {
                        Log.e("no data", "no data");
                        if (messageHistoryLists.getData().size() > 0) {
                            tvNoChat.setVisibility(View.GONE);
                            chatRoomRecycleview.setVisibility(View.VISIBLE);
                        } else {
                            tvNoChat.setVisibility(View.VISIBLE);
                            swipeLayout.setVisibility(View.GONE);
                        }

                        if (chatRoomListAdapter != null) {
                            chatRoomListAdapter.setData(messageHistoryLists.getData());
                        }
                    }

                }
            }
        }
    };
    private int page=0;
    private BroadcastReceiver getChatThreadAgainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            page =0;
            messageHistoryLists = null;
            getChatMessageHistory();
        }
    };

    private LinearLayoutManager linearLayoutManager;
    AppPreferences mAppPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chat_layout_list, container, false);
        mContext = getActivity();
        //bind view with butter knife
        unbinder = ButterKnife.bind(this, view);
        mAppPreferences=new AppPreferences(getActivity());

        swipeLayout.setOnRefreshListener(this);
        setAdapter();
        getActivity().registerReceiver(getMessageHistoryReceiver, new IntentFilter(Constants.BROADCAST_MESSAGE_HISTORY_RESPONSE));
        getActivity().registerReceiver(getChatThreadAgainReceiver, new IntentFilter("get_chat_thread_again"));

        return view;
    }

    private void setAdapter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        chatRoomRecycleview.setLayoutManager(linearLayoutManager);
        chatRoomListAdapter = new ChatRoomListAdapter(getActivity());
        chatRoomRecycleview.setAdapter(chatRoomListAdapter);
        page = 0;
        getChatMessageHistory();
        RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int p, int totalItemsCount) {
                if (messageHistoryLists != null && messageHistoryLists.getData().size() > 0) {
                    if (messageHistoryLists.getTotal_page() > page) {
                        if(page==0)
                        {
                            page=0;
                        }else {
                            page = page + 1;
                        }
                        getChatMessageHistory();
                    }
                }
            }
        };
        chatRoomRecycleview.addOnScrollListener(scrollListener);


    }

    private void getChatMessageHistory() {
        try {

            JSONObject jsonObject = new JSONObject();
            String user_id = AppPreferences.getAccessId();
            jsonObject.put("sender_id", user_id + "");
           // jsonObject.put("sender_id", "UD12350" + "");
            jsonObject.put("indexValue", "" + page);
            jsonObject.put("sender_type",  mAppPreferences.getStringValue(AppPreferences.Type));
            jsonObject.put("limit", "10");
            Log.e("TAG", "getChatMessageHistory: "+jsonObject );

            ((Application) getActivity().getApplication()).getSocket(getActivity()).emit("user_chat_list", jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getActivity().unregisterReceiver(getMessageHistoryReceiver);
        getActivity().unregisterReceiver(getChatThreadAgainReceiver);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mainActivity.commanToolbar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onRefresh() {
        page = 0;
        messageHistoryLists = null;
        getChatMessageHistory();

    }


}