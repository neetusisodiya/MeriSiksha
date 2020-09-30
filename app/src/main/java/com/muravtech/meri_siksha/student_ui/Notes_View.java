package com.muravtech.meri_siksha.student_ui;

import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.chrisbanes.photoview.PhotoView;
import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.utils.CommonUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Notes_View extends AppCompatActivity {
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.viewPDF)
    PDFView pdfViewOfNotes;
    String pdfname;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.iv_notes)
    PhotoView iv_notes;
    String type,title;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_notes__view);
        ButterKnife.bind(this);
        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());
        pdfname = getIntent().getStringExtra("PDF_File");
        type = getIntent().getStringExtra("type");

        Log.e("TAG", "type>>>>>>: "+type+"jjjjjjjjj"+pdfname );

        if(type.equalsIgnoreCase("image")){
            if(pdfname!=null && !pdfname.equalsIgnoreCase(""))
            Glide.with(this).load(pdfname).into(iv_notes);
        }else {
            if (CommonUtils.isNetworkAvailable(getApplicationContext())) {
                new RetrievePDFStream().execute(pdfname);
            } else {
                Toast.makeText(getApplicationContext(), "Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        scaleGestureDetector.onTouchEvent(ev);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            matrix.setScale(scaleFactor, scaleFactor);
            iv_notes.setImageMatrix(matrix);
            return true;
        }
    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfViewOfNotes.setVisibility(View.VISIBLE);
            pdfViewOfNotes.fromStream(inputStream).load();

        }
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
