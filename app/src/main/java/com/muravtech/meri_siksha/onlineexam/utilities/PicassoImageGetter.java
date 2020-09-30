package com.muravtech.meri_siksha.onlineexam.utilities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.ref.WeakReference;


public class PicassoImageGetter implements Html.ImageGetter {
    private final TextView mTextView;


    public PicassoImageGetter(TextView view) {
        mTextView = view;
    }

    @Override
    public Drawable getDrawable(String source) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }
        final Uri uri = Uri.parse(source);
        if (uri.isRelative()) {
            return null;
        }
        final URLDrawable urlDrawable = new URLDrawable(mTextView.getResources(), null);
        new LoadFromUriAsyncTask(mTextView, urlDrawable).execute(uri);
        return urlDrawable;
    }



    class LoadFromUriAsyncTask extends AsyncTask<Uri, Void, Bitmap> {
        private final WeakReference<TextView> mTextViewRef;
        private final URLDrawable mUrlDrawable;
        private final Picasso mImageUtils;

        public LoadFromUriAsyncTask(TextView textView, URLDrawable urlDrawable) {
            mImageUtils = Picasso.with(textView.getContext());
            mTextViewRef = new WeakReference<>(textView);
            mUrlDrawable = urlDrawable;
        }

        @Override
        protected Bitmap doInBackground(Uri... params) {
            try {
                return mImageUtils.load(params[0]).get();
            } catch (NullPointerException e) {
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            catch (IllegalStateException e)
            {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap result) {



            try{


            if (result == null) {
                return;
            }
            if (mTextViewRef.get() == null) {
                return;
            }
            TextView textView = mTextViewRef.get();

            mUrlDrawable.mDrawable = new BitmapDrawable(textView.getResources(), result);


            int width = 0;
                if( Math.round(2.0f * mUrlDrawable.mDrawable.getIntrinsicWidth())>textView.getWidth())
                {

                    width = textView.getWidth();

                }
               else
                {
                    width =   Math.round(2.0f * mUrlDrawable.mDrawable.getIntrinsicWidth());
                }


            int height = Math.round(1.0f * width *
                    mUrlDrawable.mDrawable.getIntrinsicHeight() /
                    mUrlDrawable.mDrawable.getIntrinsicWidth());
                    mUrlDrawable.setBounds(0, 0, width, height);
                    mUrlDrawable.mDrawable.setBounds(0, 0, width, height);

                    textView.setText(textView.getText());

            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }

    }




    class URLDrawable extends BitmapDrawable {
        private Drawable mDrawable;

        public URLDrawable(Resources res, Bitmap bitmap) {
            super(res, bitmap);
        }

        @Override
        public void draw(Canvas canvas) {
            if(mDrawable != null) {
                mDrawable.draw(canvas);
            }
        }
    }



}

