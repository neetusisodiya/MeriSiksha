package com.muravtech.merishiksha.student_ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.muravtech.merishiksha.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {
    String url;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*  getWindow().requestFeature(Window.FEATURE_PROGRESS);*/
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_how_to_use);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");


        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 19) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        // Makes Progress bar Visible
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setTitle("Loading...");
                setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if (progress == 100)
                    setTitle(R.string.app_name);
            }
        });
        webView.setWebViewClient(new HelloWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
//        if (getIntent() != null && getIntent().getStringExtra("type") != null) {
//            if (getIntent().getStringExtra("type").equalsIgnoreCase("terms")) {
//                API_NAME = "Terms";
//                getSupportActionBar().setTitle(getResources().getString(R.string.terms_condition));
//                webView.getSettings().setJavaScriptEnabled(true);
//                webView.loadUrl(Config.BASE_URL_STATIC_PAGE + "term-condition-android.php");
//
//            } else if (getIntent().getStringExtra("type").equalsIgnoreCase("privacy")) {
//                API_NAME = "PrivacyPolicy";
//                getSupportActionBar().setTitle(getResources().getString(R.string.privacy_policy));
//                webView.getSettings().setJavaScriptEnabled(true);
//                webView.loadUrl(Config.BASE_URL_STATIC_PAGE + "disclamer-android.php");
//            }
//        }

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }

    }
}