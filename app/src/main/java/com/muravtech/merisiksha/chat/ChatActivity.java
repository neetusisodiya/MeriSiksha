package com.muravtech.merisiksha.chat;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import com.muravtech.merisiksha.Application;
import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.Response.ChatModelList;
import com.muravtech.merisiksha.Response.ClassListBean;
import com.muravtech.merisiksha.Response.SingleChatModel;
import com.muravtech.merisiksha.adapter.ChatAdapter;

import com.muravtech.merisiksha.server.APIClient;
import com.muravtech.merisiksha.server.DKRInterface;
import com.muravtech.merisiksha.utils.AppPreferences;
import com.muravtech.merisiksha.utils.CommonUtils;
import com.muravtech.merisiksha.utils.Constants;
import com.muravtech.merisiksha.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends Activity  {

    public static String other_userid;
    public static boolean is_show = false;
    @BindView(R.id.chatSend)
    ImageView chatSend;
    @BindView(R.id.chatSendmessageEdt)
    EditText chatSendmessageEdt;
    @BindView(R.id.bottomSendMessageLayout)
    LinearLayout bottomSendMessageLayout;

    @BindView(R.id.recycle_chat)
    RecyclerView recycleChat;
    @BindView(R.id.iv_more)
    ImageView commanToolBarRightIconBlock;
    String other_name, other_image, other_gender;
    ChatAdapter chatAdapter;
    @BindView(R.id.tv_view_more)
    TextView tvViewMore;
    @BindView(R.id.title_txt)
    TextView title_txt;
    @BindView(R.id.iv_profile)
    CircleImageView iv_profile;

    String block = "false";

    BottomSheetDialog mBottomSheetDialog;
    //Context
    private Context mContext;
    private Activity mActivity;
    private int page=0;
    //String blockbyme="false";
    private ChatModelList chatModelList;
    private BroadcastReceiver chatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.hasExtra("data")) {
                String action = intent.getAction();
                String data = intent.getStringExtra("data");
                Log.e("rgg", data);

                if (action.equalsIgnoreCase(Constants.BROADCAST_RECEIVE_MESSAGE)) {
                    try {
                        SingleChatModel singleChatModel = new Gson().fromJson(data, SingleChatModel.class);
                        if (chatModelList != null) {

                            if (singleChatModel.getData().getChat_type().equalsIgnoreCase("single")) {
                                if (other_userid.equalsIgnoreCase(String.valueOf(singleChatModel.getData().getUser_id()))) {
                                    chatModelList.getData().add(singleChatModel.getData());
                                    if (chatAdapter != null) {
                                        chatAdapter.setData(chatModelList.getData());

                                        if (chatAdapter.getItemCount() > 0 && recycleChat != null) {
                                            recycleChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                                        }

                                    }
                                }
                            } else {
                                if (other_userid.equalsIgnoreCase(String.valueOf(singleChatModel.getData().getOther_user_id()))) {
                                    chatModelList.getData().add(singleChatModel.getData());
                                    if (chatAdapter != null) {
                                        chatAdapter.setData(chatModelList.getData());

                                        if (chatAdapter.getItemCount() > 0 && recycleChat != null) {
                                            recycleChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                                        }

                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (action.equalsIgnoreCase(Constants.BROADCAST_SEND_MESSAGE)) {
                    try {
                        SingleChatModel singleChatModel = new Gson().fromJson(data, SingleChatModel.class);
                        if (chatModelList != null && chatModelList.getData() != null) {
                            chatModelList.getData().add(singleChatModel.getData());
                            if (chatAdapter != null) {
                                chatAdapter.setData(chatModelList.getData());
                                if (chatAdapter.getItemCount() > 0 && recycleChat != null) {
                                    recycleChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                                }
                            }

                            Intent intent1 = new Intent();
                            intent1.setAction("get_chat_thread_again");
                            sendBroadcast(intent1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (action.equalsIgnoreCase(Constants.BROADCAST_CHAT_THREAD_RESPONSE)) {


                    try {

                        if (chatModelList != null && chatModelList.getData() != null && chatModelList.getData().size() > 0) {
                            ChatModelList localChatModelList = new Gson().fromJson(data, ChatModelList.class);

                            if (localChatModelList.getData() != null && localChatModelList.getData().size() > 0) {

                                Collections.reverse(localChatModelList.getData());
                                chatModelList.getData().addAll(0, localChatModelList.getData());
                            }
                        } else {
                            chatModelList = new Gson().fromJson(data, ChatModelList.class);

                        }
                        if (tvViewMore != null) {
                            if (chatModelList.getTotal_record() > chatModelList.getData().size())
                                tvViewMore.setVisibility(View.VISIBLE);
                            else tvViewMore.setVisibility(View.GONE);
                        }

                        block = chatModelList.getIs_blocked_by_me();
                        if (chatAdapter != null) {
                            if (chatModelList.getIs_blocked().equalsIgnoreCase("true")) {
                                chatSendmessageEdt.setText(R.string.you_have_been_blocked);
                                chatSendmessageEdt.setEnabled(false);
                            } else if (chatModelList.getIs_blocked_by_me().equalsIgnoreCase("true")) {
                                chatSendmessageEdt.setText(R.string.you_have_blocked);
                                chatSendmessageEdt.setEnabled(false);

                            } else {
                                chatSendmessageEdt.setHint(getResources().getString(R.string.typehere));
                                block = "false";
                            }

                            if (chatAdapter.getItemCount() == 0) {
                                Collections.reverse(chatModelList.getData());
                            }

                            chatAdapter.setData(chatModelList.getData());
                            if (chatAdapter.getItemCount() > 0 && recycleChat != null) {
                                recycleChat.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

        }
    };
    private String type,receiver_type;
    private Dialog dialog, dialog1;
//    private GroupChatGiftListAdapter chatGiftUserListAdapter;
//
//    public ChatActivity() {
//        super(ChatActivity);
//    }
AppPreferences mAppPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        mActivity = ChatActivity.this;
        mContext = this;
        mAppPreferences=new AppPreferences(mContext);

        other_userid = getIntent().getStringExtra("user_id");
        other_name = getIntent().getStringExtra("name");
        other_image = getIntent().getStringExtra("image");
        receiver_type = getIntent().getStringExtra("receiver_type");
       // other_gender = getIntent().getStringExtra("gender");
        type = getIntent().getStringExtra("type");
//
        Log.e("rgg", other_userid + "!" + other_name + "!"  );
//
//        if (type != null && type.equalsIgnoreCase("group")) {
//
//            findViewById(R.id.ll_parent).setVisibility(View.GONE);
//            //serverRequestForGetGroupDetail();
//        } else {
//
//        }
        //setToolbar();
        setView();
        //setupTabIcons();

    }


    private void setData() {
        commanToolBarRightIconBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog = new BottomSheetDialog(ChatActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.block_user_layout, null);
                final TextView tv_block = sheetView.findViewById(R.id.tv_block);
                TextView tv_cancle = sheetView.findViewById(R.id.tv_cancle);
                if (block.equalsIgnoreCase("false")) {
                    tv_block.setText(R.string.block);
                } else {
                    tv_block.setText(R.string.unblock);
                }
                tv_block.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBlockDialog(tv_block.getText().toString());

                    }
                });
                tv_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();
            }
        });

    }

    private void setView() {
        title_txt.setText(other_name);
        if(other_image!=null && !other_image.equalsIgnoreCase("")){
            Picasso.with(this).load(other_image).into(iv_profile);
        }
        setData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycleChat.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(this);
        recycleChat.setAdapter(chatAdapter);

        page = 0;
        getChatThread("0");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_SEND_MESSAGE);
        intentFilter.addAction(Constants.BROADCAST_CHAT_THREAD_RESPONSE);
        intentFilter.addAction(Constants.BROADCAST_RECEIVE_MESSAGE);
        registerReceiver(chatReceiver, intentFilter);


    }


    //button onclick
    @OnClick({R.id.iv_back,R.id.chatSendmessageEdt, R.id.chatSend, R.id.tv_view_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.chatSendmessageEdt:
                break;
            case R.id.chatSend:
                if (block.equalsIgnoreCase("false")) {
                    if (chatSendmessageEdt.getText().toString().trim().length() > 0) {
                        if (CommonUtils.isNetworkAvailable(ChatActivity.this) && ((Application) getApplication()).getSocket(mActivity).connected()) {
                            sendMessage();
                        } else {
                            CommonUtils.showToast(mActivity, getString(R.string.trying_to_connect));
                        }

                    } else {
                        CommonUtils.showToast(mActivity, getString(R.string.pls_enter_message));
                    }
                }
                break;
            case R.id.tv_view_more:
                if (chatModelList != null && chatModelList.getData() != null && chatModelList.getData().size() > 0) {
                    page = page + 1;
                    getChatThread(chatModelList.getData().get(0).getId() + "");
                }
                break;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        is_show = false;
    }

    private void getChatThread(String last_id) {
        try {
            String user_id = AppPreferences.getAccessId();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sender_id", user_id);
            jsonObject.put("sender_type",  mAppPreferences.getStringValue(AppPreferences.Type));
            jsonObject.put("receiver_id", other_userid);
            jsonObject.put("receiver_type", receiver_type);

            jsonObject.put("last_id", "" + last_id);
            jsonObject.put("indexValue", "" + page);
            jsonObject.put("limit", "10");
            jsonObject.put("chat_type", type);

            ((Application) getApplication()).getSocket(mActivity).emit("user_chat_history", jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //send text message on chat
    private void sendMessage() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sender_id", AppPreferences.getAccessId());
            jsonObject.put("sender_type",  mAppPreferences.getStringValue(AppPreferences.Type));
            jsonObject.put("receiver_id", other_userid);
            jsonObject.put("receiver_type", receiver_type);
            jsonObject.put("type", "Text");
//            //jsonObject.put("device_id", "123456");
//            jsonObject.put("title", "hello");
//            jsonObject.put("accept_decline", "Text");
            jsonObject.put("chat_type", type);
           // jsonObject.put("upload_img", "");

            jsonObject.put("message", chatSendmessageEdt.getText().toString().trim());

            ((Application) getApplication()).getSocket(mActivity).emit("chat_message", jsonObject);
            Log.e("TAG", "sendMessage: "+jsonObject.toString());
            chatSendmessageEdt.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //show block user dialog
    private void showBlockDialog(String title) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.logoutcustomdialog);
        TextView logoutTitleTxt = dialog.findViewById(R.id.logoutTitleTxt);
        TextView logoutCancelTxt = dialog.findViewById(R.id.logoutCancel);
        TextView logoutYesTxt = dialog.findViewById(R.id.logoutYes);
        if (title.equalsIgnoreCase("Block")) {
            logoutTitleTxt.setText(getString(R.string.are_you_sure_you_wnt_to_block) + other_name + "?");
        } else {
            logoutTitleTxt.setText(getString(R.string.are_you_sure_you_wnt_to_unblock) + other_name + "?");
        }

        //Onclick on logoutYes txt
        logoutYesTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverRequestForBlockUser();
                dialog.dismiss();
            }
        });

        //Onclick on logoutCancel txt
        logoutCancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void serverRequestForBlockUser() {
        CommonUtils.progressDialogShow(this);
        Call<RequestBody> call = APIClient.getClient().create(DKRInterface.class)
                .blockAndUnblockRequest(AppPreferences.getSchoolId(),AppPreferences.getAccessId(),other_userid,mAppPreferences.getStringValue(AppPreferences.Type));
        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (response.body() != null) {
                    CommonUtils.hideProgressDoalog();
                    CommonUtils.showToast(ChatActivity.this,"message");
                    mBottomSheetDialog.dismiss();
                    finish();
                } else {
                    CommonUtils.hideProgressDoalog();
                    CommonUtils.showToast(ChatActivity.this,"message");
                    mBottomSheetDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                call.cancel();
                CommonUtils.hideProgressDoalog();
                //  dismissProgressDialog();
                Log.e("accesstokwen20 ", "" + t);
            }
        });
    }

//    //success method of api calling
//    @Override
//    public void onSuccess(String message, String json, BaseRequestData baseRequestData) {
//        switch (baseRequestData.getTag()) {
//            case ResponseType.GROUP_DETAILS:
//                groupDetail = new Gson().fromJson(json, GroupDetail.class);
//                String friend_name = "";
//                String user_id = Common.readUsingSharedPreference(mContext, Constant.USER_ID);
//                for (int i = 0; i < groupDetail.getData().getFriends().size(); i++) {
//                    if (groupDetail.getData().getFriends().get(i).getUserId().toString().equalsIgnoreCase(user_id)) {
//                        String a = getString(R.string.you);
//                        friend_name = friend_name + ", " + a;
//                    } else {
//                        String a = groupDetail.getData().getFriends().get(i).getUsername() + "";
//                        friend_name = friend_name + ", " + a;
//                    }
//
//
//                }
//                if (friend_name.startsWith(",")) {
//                    friend_name = friend_name.substring(1, friend_name.length());
//                }
//
////                friend_name = friend_name + ", You";
//                commonToolbar.groupChatUsersName.setText(friend_name);
//                findViewById(R.id.ll_parent).setVisibility(View.VISIBLE);
//                break;
//            case ResponseType.BlockUser:
//                Common.showToast(this, message);
//                mBottomSheetDialog.dismiss();
//                finish();
//                break;
//
////            case ResponseType.GET_FRIEND_LIST:
////                getFriendList = new Gson().fromJson(json, GetFriendList.class);
////                break;
//
//
//        }
//
//    }




//
//    @Override
//    public void friendClick(int position) {
//        friendPosition = position;
//
//    }

}