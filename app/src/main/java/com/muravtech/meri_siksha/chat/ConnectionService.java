package com.muravtech.meri_siksha.chat;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.muravtech.meri_siksha.Application;
import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.Response.SingleChatModel;

import com.muravtech.meri_siksha.utils.AppPreferences;
import com.muravtech.meri_siksha.utils.Constants;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ConnectionService extends Service {
    private static final String TAG = "MeriSiksha";
    public static Context context;
    public int EventConnect = 1,
            EventDisconnect = 2,
            EventError = 3,
            ConnectionTimeOut = 4,

    sendMessageTag = 5,
            receiveMessageTag = 7,
            getMessageHistoryTag = 8,
            getGetMessageHistoryResponseTag = 9,
            runGetUserThreadTag = 10,
            runThreadResponseTag = 11,
            runMultiUserRreponseTag = 12;
    Timer timer;
    TimerTask timerTask;
    String userid = "";
    Emitter.Listener eventOnlineLatest = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (args[0] != null) Log.e("eventOnlineLatest", args[0].toString());
        }
    };


    //Chat Handlers and Runnables
    Handler chatHandler = new Handler(Looper.getMainLooper());


    Emitter.Listener sendMessage = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            if (args[0] != null) {
                Log.e("socket", "sendMessage");
                response = args[0].toString();
                runSendMessage.setRun(response, sendMessageTag);
                chatHandler.post(runSendMessage);
            }
        }
    };

    Emitter.Listener receiveMessage = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            if (args[0] != null) {
                Log.e("socket", "receiveMessage");
                response = args[0].toString();
                runReceiveMessage.setRun(response, receiveMessageTag);
                chatHandler.post(runReceiveMessage);
            }
        }
    };

    Emitter.Listener getMessageHistory = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            if (args[0] != null) {
                Log.e("socket", "getMessageHistory");
                response = args[0].toString();
                runGetMessageHistory.setRun(response, getMessageHistoryTag);
                chatHandler.post(runGetMessageHistory);
            }
        }
    };
    Emitter.Listener getMessageHistoryResponse = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            if (args[0] != null) {
                Log.e("socket", "getMessageHistoryResponse");
                response = args[0].toString();
                runGetMessageHistoryResponse.setRun(response, getGetMessageHistoryResponseTag);
                chatHandler.post(runGetMessageHistoryResponse);
            }
        }
    };
    Emitter.Listener getUserThread = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            if (args[0] != null) {
                Log.e("socket", "getUserThread");
                response = args[0].toString();
                runGetUserThread.setRun(response, runGetUserThreadTag);
                chatHandler.post(runGetUserThread);
            }
        }
    };
    Emitter.Listener threadResponse = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            if (args[0] != null) {
                Log.e("socket", "threadResponse");
                response = args[0].toString();
                runThreadResponse.setRun(response, runThreadResponseTag);
                chatHandler.post(runThreadResponse);
            }
        }
    };


    Emitter.Listener eventDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e("socket", "eventDisconnect");
            Log.e("DISCONNECTED", "disconnected");
            runEventDisconnect.setRun("Disconnected" + args[0], EventDisconnect);
            chatHandler.post(runEventDisconnect);

        }
    };
    Emitter.Listener eventError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e("socket", "eventError" + args[0]);
            runEventError.setRun("FailedToConnect" + args[0], EventError);
            chatHandler.post(runEventError);
        }
    };
    Emitter.Listener connectionTimeOut = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e("socket", "connectionTimeOut");
            runConnectionTimeOut.setRun("ConnectionTimeOut" + args[0], ConnectionTimeOut);
            chatHandler.post(runConnectionTimeOut);

        }
    };
    ChatRunnable runEventConnect = new ChatRunnable(),
            runEventDisconnect = new ChatRunnable(),
            runEventError = new ChatRunnable(),
            runConnectionTimeOut = new ChatRunnable(),
            runSendMessage = new ChatRunnable(),
            runReceiveMessage = new ChatRunnable(),
            runGetMessageHistory = new ChatRunnable(),
            runGetMessageHistoryResponse = new ChatRunnable(),
            runGetUserThread = new ChatRunnable(),
            runThreadResponse = new ChatRunnable(), runmultiUserResponse = new ChatRunnable();


    Emitter.Listener eventConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e("connected", "true");
            runEventConnect.setRun("Connected", EventConnect);
            chatHandler.post(runEventConnect);

        }
    };
    private Thread mThread;
    private Emitter.Listener getUserThreadResponse = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runEventConnect.setRun("Connected", EventConnect);
            chatHandler.post(runEventConnect);
        }
    };
    private Emitter.Listener multiUser = new Emitter.Listener() {
        String response = "";

        @Override
        public void call(Object... args) {
            response = args[0].toString();
            runmultiUserResponse.setRun(response, runMultiUserRreponseTag);
            chatHandler.post(runmultiUserResponse);
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        context = getApplicationContext();
        startTimer();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        context = getApplicationContext();
        stop();
        start();
        return Service.START_STICKY;
        //RETURNING START_STICKY CAUSES OUR CODE TO STICK AROUND WHEN THE APP ACTIVITY HAS DIED.
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        stop();

        stoptimertask();
    }


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, 10000); //
    }

    private void initializeTimerTask() {

        timerTask = new TimerTask() {

            public void run() {


                start();

            }
        };
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    void doSocketWork() {
        try {
            if (((Application) getApplication()).getSocket(context) != null && !((Application) getApplication()).getSocket(context).connected() == true) {
                Log.d(TAG, "trying socket connection");
                stop();

                ((Application) getApplication()).getSocket(context).on(Socket.EVENT_CONNECT, eventConnect);
                ((Application) getApplication()).getSocket(context).on(Socket.EVENT_DISCONNECT, eventDisconnect);
                ((Application) getApplication()).getSocket(context).on(Socket.EVENT_CONNECT_ERROR, eventError);
                ((Application) getApplication()).getSocket(context).on(Socket.EVENT_CONNECT_TIMEOUT, connectionTimeOut);
//                ((ObjectPreference) getApplication()).getSocket(context).on(Manager.EVENT_TRANSPORT, connectionTransport);

                ((Application) getApplication()).getSocket(context).on("send_message", sendMessage);
                ((Application) getApplication()).getSocket(context).on("receive_message", receiveMessage);
             //   ((Application) getApplication()).getSocket(context).on("get_message_history", getMessageHistory);
                ((Application) getApplication()).getSocket(context).on("user_chat_list", getMessageHistoryResponse);
                ((Application) getApplication()).getSocket(context).on("get_user_thread", getUserThread);
                ((Application) getApplication()).getSocket(context).on("user_chat_history", threadResponse);
                ((Application) getApplication()).getSocket(context).on("send_message", multiUser);


                ((Application) getApplication()).getSocket(context).connect();
                ((Application) getApplication()).getSocket(context).emit("room", AppPreferences.getAccessId());

                Log.e("connectedToIo", ((Application) getApplication()).getSocket(context).connected() + "");


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAppIsInBackground(Context context) {///false if app is not in background
        // true if app is in background
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }


    public void start() {
        context = getApplicationContext();
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                doSocketWork();
            }
        });
        mThread.start();
    }

    public void stop() {


        if (((Application) getApplication()).getSocket(context) != null) {
            //UserDetails userResponse = PreferenceManager.getInstance(context).getUserdetail();
            String userId = AppPreferences.getAccessId();
            if (userId != null)
                userid = userId + "";
            Log.e(TAG, "stop: >>>>>"+userid);
            ((Application) getApplication()).getSocket(context).disconnect();
            ((Application) getApplication()).getSocket(context).off(Socket.EVENT_CONNECT, eventConnect);
            ((Application) getApplication()).getSocket(context).off(Socket.EVENT_DISCONNECT, eventDisconnect);
            ((Application) getApplication()).getSocket(context).off(Socket.EVENT_CONNECT_ERROR, eventError);
            ((Application) getApplication()).getSocket(context).off(Socket.EVENT_CONNECT_TIMEOUT, connectionTimeOut);
//            ((ObjectPreference) getApplication()).getSocket(context).off(Manager.EVENT_TRANSPORT, connectionTransport);

            ((Application) getApplication()).getSocket(context).off("send_message", sendMessage);
            ((Application) getApplication()).getSocket(context).off("receive_message", receiveMessage);
           // ((Application) getApplication()).getSocket(context).off("get_message_history", getMessageHistory);
            ((Application) getApplication()).getSocket(context).off("user_chat_list", getMessageHistoryResponse);
            ((Application) getApplication()).getSocket(context).off("get_user_thread", getUserThread);
            ((Application) getApplication()).getSocket(context).off("user_chat_history", threadResponse);
            ((Application) getApplication()).getSocket(context).off("send_message", multiUser);

            chatHandler.removeCallbacks(runEventConnect);
            chatHandler.removeCallbacks(runEventDisconnect);
            chatHandler.removeCallbacks(runEventError);
            chatHandler.removeCallbacks(runConnectionTimeOut);

            chatHandler.removeCallbacks(runSendMessage);
            chatHandler.removeCallbacks(runReceiveMessage);
            chatHandler.removeCallbacks(runGetMessageHistory);
            chatHandler.removeCallbacks(runGetMessageHistoryResponse);
            chatHandler.removeCallbacks(runGetUserThread);
            chatHandler.removeCallbacks(runThreadResponse);
            chatHandler.removeCallbacks(runmultiUserResponse);
        }
    }

    private void showNotification(String data) {
        try {
            int icon = R.mipmap.icon_logo;
            long when = System.currentTimeMillis();
            String title = this.getString(R.string.app_name);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            SingleChatModel singleChatModel = new Gson().fromJson(data, SingleChatModel.class);

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification(icon, "", when);

            Intent notificationIntent = null;

//            if (jsonObject.getJSONObject("data").getString("user_type").equalsIgnoreCase("RESTAURANT")) {
            notificationIntent = new Intent(context, ChatActivity.class);
            if (singleChatModel.getData().getChat_type().equalsIgnoreCase("Group")) {
                notificationIntent.putExtra("user_id", singleChatModel.getData().getOther_user_id() + "");
                notificationIntent.putExtra("image", singleChatModel.getData().getOther_user_profile_pic_thumb());
                notificationIntent.putExtra("name", singleChatModel.getData().getOther_user_username());
                notificationIntent.putExtra("gender", "");
            } else {
                notificationIntent.putExtra("user_id", singleChatModel.getData().getUser_id() + "");
                notificationIntent.putExtra("image", singleChatModel.getData().getProfile_pic_thumb());
                notificationIntent.putExtra("name", singleChatModel.getData().getUsername());
                notificationIntent.putExtra("gender", "");
            }
            notificationIntent.putExtra("type", singleChatModel.getData().getChat_type());

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent intent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(),
                    notificationIntent, PendingIntent.FLAG_ONE_SHOT);


            notification.flags |= Notification.FLAG_AUTO_CANCEL;


//            RemoteViews remoteViews = getComplexNotificationView(singleChatModel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

//            builder.setContent(remoteViews);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // only for gingerbread and newer versions
                int notifyID = 1;
                String CHANNEL_ID = "my_channel_01";// The id of the channel.
                CharSequence name = "MeriSiksha";// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                builder.setChannelId(CHANNEL_ID);
                notificationManager.createNotificationChannel(mChannel);


            }
            builder.setContentIntent(intent);
//            notification = builder.setSmallIcon(R.drawable.logo_icon).setStyle(new NotificationCompat.DecoratedCustomViewStyle())
////                    .setCustomContentView(remoteViews)
//                    .setTicker("Lunchbx").setWhen(when).setLights(0xff00ff00, 300, 100).setPriority(Notification.PRIORITY_MAX).setDefaults(Notification.DEFAULT_LIGHTS).build();
            String text = "";

            text = "New message from " + singleChatModel.getData().getUsername() + ": " + singleChatModel.getData().getMessage();


            NotificationCompat.BigTextStyle bigStyle =
                    new NotificationCompat.BigTextStyle();
            bigStyle.setBigContentTitle(title);
            bigStyle.bigText(text);
            notification = builder
                    .setContentTitle(title).setContentText(text)
                    .setColor(Color.parseColor("#41A9E5"))
                    .setSmallIcon(icon).setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setContentIntent(intent).setSound(soundUri).setLights(0xff00ff00, 300, 100).setPriority(Notification.PRIORITY_HIGH).setDefaults(Notification.DEFAULT_LIGHTS)
                    .setAutoCancel(true).setStyle(bigStyle).build();

            notificationManager.notify(1, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class ChatRunnable implements Runnable {
        String response = "";
        int tag = -1;

        public void setRun(String response, int tag) {
            this.response = response;
            this.tag = tag;
        }

        @Override
        public void run() {
            Log.e("connection" + tag + " ", response);
            Intent intent = new Intent();
            intent.setAction("com.lunchbx.chat");

            if (tag == EventConnect) {
                Intent intent1 = new Intent("show_connect_icon");
                sendBroadcast(intent1);

            } else if (tag == EventDisconnect) {
//                ProgressDialog.hideprogressbar();

                Intent intent1 = new Intent("show_disconnect_icon");
                intent1.putExtra("type", "disconnect");
                intent1.putExtra("data", "" + response);
                sendBroadcast(intent1);

            } else if (tag == EventError) {
                Log.e("connection", "" + response);

//                ProgressDialog.hideprogressbar();

                Intent intent1 = new Intent("show_disconnect_icon");
                intent1.putExtra("type", "error" + response);
                intent1.putExtra("data", "" + response);
                sendBroadcast(intent1);
            } else if (tag == ConnectionTimeOut) {
//                ProgressDialog.hideprogressbar();
                Intent intent1 = new Intent("show_disconnect_icon");
                intent1.putExtra("type", "connection_time_out");
                intent1.putExtra("data", "" + response);
                sendBroadcast(intent1);
//                Toast.makeText(context, "socket Connection timeout error.", Toast.LENGTH_SHORT).show();
            } else if (tag == runMultiUserRreponseTag) {
                Log.e("SendMessageResponseTag", response);
                Intent intent1 = new Intent(Constants.BROADCAST_SEND_MESSAGE);
                intent1.putExtra("data", response);
                sendBroadcast(intent1);

            } else if (tag == receiveMessageTag) {
                try {
                    Log.e("receiveMessageTag", response);
                  //  JSONObject jsonObject = new JSONObject(response);
                   // String userId = AppPreferences.getAccessId();
                  //  Log.e("userid", userId + "");

//                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    SingleChatModel singleChatModel = new Gson().fromJson(response, SingleChatModel.class);
//                    if (userId.equalsIgnoreCase(jsonObject1.getString("other_user_id"))) {
                    Intent intent1 = new Intent(Constants.BROADCAST_RECEIVE_MESSAGE);
                    //intent.setAction(Constants.BROADCAST_RECEIVE_MESSAGE);
                    intent1.putExtra("data", response);
                    sendBroadcast(intent1);

                    Intent intent2 = new Intent();
                    intent2.setAction("get_chat_thread_again");
                    sendBroadcast(intent2);

                    if (isAppIsInBackground(context)) {
                        showNotification(response);
                    } else {
                        if (!ChatActivity.is_show) {
                            showNotification(response);

                        } else {
                            if (singleChatModel.getData().getChat_type().equalsIgnoreCase("Group")) {
                                if (!ChatActivity.other_userid.equalsIgnoreCase(String.valueOf(singleChatModel.getData().getOther_user_id())))
                                    showNotification(response);
                            } else {
                                if (!ChatActivity.other_userid.equalsIgnoreCase(String.valueOf(singleChatModel.getData().getUser_id()))) {
                                    showNotification(response);

                                }
                            }
                        }
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (tag == getMessageHistoryTag) {
                try {
                    Log.e("getMessageHistoryTag", getMessageHistoryTag + "" + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (tag == getGetMessageHistoryResponseTag) {
                try {
                    Log.e("getGetMessageHistor", "" + "" + response);

                    intent.setAction(Constants.BROADCAST_MESSAGE_HISTORY_RESPONSE);
                    intent.putExtra("data", response);
                    sendBroadcast(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (tag == runThreadResponseTag) {
                try {
                    Log.e("ChatThread", response);
                    intent.setAction(Constants.BROADCAST_CHAT_THREAD_RESPONSE);
                    intent.putExtra("data", response);
                    sendBroadcast(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
