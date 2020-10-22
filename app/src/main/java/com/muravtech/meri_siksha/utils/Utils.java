package com.muravtech.meri_siksha.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.muravtech.meri_siksha.server.Config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Utils {
    public static void showDecisionDialog(Context context, String title, String message, final AlertCallback callbackListener) {

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                    callbackListener.callback();
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showAlertDialog(Context context, String title, String message, boolean isCancelable) {

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(message);

            alertDialogBuilder.setCancelable(isCancelable);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.dismiss();
                    //System.exit(1);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String timeDateFormate(String input) {

        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat outputFormatter = new SimpleDateFormat("dd-MM-yyyy HH-mm");
        Date date = null;
        try {
            date = inputFormatter.parse(input);
            String outputDateStr = outputFormatter.format(date);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String DateFormate(String input) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
      /*  String inputDateStr = "2013-06-24";
        Date date = inputFormat.parse(inputDateStr);
        String outputDateStr = outputFormat.format(date);*/
        try {
            date = inputFormat.parse(input);
            String outputDateStr = outputFormat.format(date);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public interface AlertCallback {
        void callback();
    }
    public static void showDecisionDialogRegister(Context context, String title, String message, final AlertCallback callbackListener) {

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                    callbackListener.callback();
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.setNegativeButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                    callbackListener.callback();
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void retrieveVideoFrameFromVideo(final ProgressBar pBar, ImageView targetView, final String videoPath) {
        new AsyncTask<ImageView, Void, Bitmap>() {
            ImageView imageView;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // pBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Bitmap doInBackground(ImageView... params) {
                imageView = params[0];
                Bitmap bitmap = null;
                MediaMetadataRetriever mediaMetadataRetriever = null;
                try {
                    mediaMetadataRetriever = new MediaMetadataRetriever();
                    if (Build.VERSION.SDK_INT >= 14)
                        mediaMetadataRetriever.setDataSource(Config.BASE_URL_VIDEO_PLAY + videoPath, new HashMap<String, String>());
                    else
                        mediaMetadataRetriever.setDataSource(Config.BASE_URL_VIDEO_PLAY + videoPath);
                    //   mediaMetadataRetriever.setDataSource(videoPath);
                    bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (mediaMetadataRetriever != null) {
                        mediaMetadataRetriever.release();
                    }
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap thumbnail) {
                super.onPostExecute(thumbnail);
                pBar.setVisibility(View.GONE);
                if (imageView != null && thumbnail != null) {
                    imageView.setImageBitmap(thumbnail);
                }
            }
        }.execute(targetView);
    }
}
