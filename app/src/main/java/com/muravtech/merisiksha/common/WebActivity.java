package com.muravtech.merisiksha.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.utils.CommonUtils;

public class WebActivity extends AppCompatActivity {
    WebView webView;
    String url;
    private RelativeLayout reli;
    private Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        reli=findViewById(R.id.reli);
        retry=findViewById(R.id.btnretry);


            Intent in = getIntent();
            url = in.getStringExtra("url");
            if (TextUtils.isEmpty(url)) {
                Toast.makeText(getApplicationContext(), "URL not found", Toast.LENGTH_SHORT).show();
                finish();
            }
            webView = findViewById(R.id.webView);
            initWebView();
            webView.loadUrl(url);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initWebView();
                }
            });
        }

        private void initWebView () {

            if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

                reli.setVisibility(View.GONE);
                webView.setWebChromeClient(new MyWebChromeClient(this));
                webView.clearCache(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setHorizontalScrollBarEnabled(false);
                try {
                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
                        }

                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            webView.loadUrl(url);
                            return true;
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                        }

                        @Override
                        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                            super.onReceivedError(view, request, error);
                            invalidateOptionsMenu();
                        }
                    });
                }catch (Exception e){

                }
                webView.clearCache(true);
                webView.clearHistory();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setHorizontalScrollBarEnabled(false);
            }else{
                reli.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Please check your Internet Connection.", Toast.LENGTH_SHORT).show();

            }
        }

        private class MyWebChromeClient extends WebChromeClient {
            Context context;

            public MyWebChromeClient(Context context) {
                super();
                this.context = context;
            }
        }
    }
