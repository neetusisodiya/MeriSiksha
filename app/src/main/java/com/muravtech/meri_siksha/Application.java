package com.muravtech.meri_siksha;

import android.content.Context;
import android.util.Log;


import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.muravtech.meri_siksha.utils.AppPreferences;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//import io.fabric.sdk.android.Fabric;
import io.socket.client.IO;
import io.socket.client.Socket;

// Declare for Google Analytics for Firebase

public class Application extends android.app.Application {
    // [START declare_analytics]
  //  private FirebaseAnalytics mFirebaseAnalytics;
    public static Socket getmSocket() {
        return mSocket;
    }

    public static void setmSocket(Socket mSocket) {
        Application.mSocket = mSocket;
    }

    private static Socket mSocket;
    private static Context mContex;
    // [END declare_analytics]
    static AppPreferences appPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        mContex = this;
        appPreferences=new AppPreferences(mContex);

        // [END FirebaseAnalytics instance]
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);


        FirebaseApp.initializeApp(getApplicationContext());
        getSocket(this);

    }

    public static Context getContext() {
        return mContex;
    }

    public static Socket getSocket(Context mContext) {

        if ( appPreferences.getAccessId() != null) {
            String userId = String.valueOf(AppPreferences.getAccessId());
           // Log.e("Userid", userId + "inApp");
            if (mSocket != null) {
                return mSocket;
            } else {
                try {
                    HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
                        @Override

                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    };
                    SSLContext mySSLContext = null;
                    try {
                        mySSLContext = SSLContext.getInstance("TLS");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }

                        public void checkClientTrusted(X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        public void checkServerTrusted(X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }
                    }};

                    try {
                        mySSLContext.init(null, trustAllCerts, new java.security.SecureRandom());
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    }

//                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                            .hostnameVerifier(myHostnameVerifier)
//                            .sslSocketFactory(mySSLContext.getSocketFactory(), new X509TrustManager() {
//                                @Override
//                                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//                                }
//
//                                @Override
//                                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//                                }
//
//                                @Override
//                                public X509Certificate[] getAcceptedIssuers() {
//                                    return new X509Certificate[0];
//                                }
//                            })
//                            .build();


                    // HttpsURLConnection.setDefaultHostnameVerifier(myHostnameVerifier);
                    IO.Options options = new IO.Options();

                    options.sslContext = mySSLContext;
                    options.hostnameVerifier = myHostnameVerifier;

                    if (mSocket == null && userId != null && !userId.equals("")) {
                        try {
                            //String SocketUrl = "http://192.168.1.80:3000/";
                            String SocketUrl = "http://merishiksha.in:3000/";
                            Log.e("socket url", SocketUrl);
                            mSocket = IO.socket(SocketUrl, options);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                       // mSocket = IO.socket(SocketConstants.SOCKET_URL, options);
                  //  mSocket.connect();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }

//        Log.e("MSOCKET", mSocket.open().id() + "ID");

        return mSocket;
    }

}
