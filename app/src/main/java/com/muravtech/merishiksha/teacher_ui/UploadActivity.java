package com.muravtech.merishiksha.teacher_ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.muravtech.merishiksha.R;
import com.muravtech.merishiksha.module.Data;
import com.muravtech.merishiksha.server.APIClient;
import com.muravtech.merishiksha.server.DKRInterface;
import com.muravtech.merishiksha.utils.AppPreferences;
import com.muravtech.merishiksha.utils.CommonUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {

    @BindView(R.id.iv_preview)
    ImageView ivPreview;
    @BindView(R.id.editText_title)
    EditText editText_title;
    String mediaPath;
    String type;
    String link = "";
    AlertDialog alertDialog;
    RequestBody requestBody;
    String subject_id, section_id, title;
    String[] mediaColumns = {MediaStore.Video.Media._ID};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            subject_id = getIntent().getStringExtra("subject_id");
            section_id = getIntent().getStringExtra("section_id");
        }

        requestMultiplePermissions();
    }

    // Providing Thumbnail For Selected Image
    public Bitmap getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        long fileId = getFileId(context, fileUri);
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }

    // Getting Selected File ID
    public long getFileId(Activity context, Uri fileUri) {
        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            return cursor.getInt(columnIndex);
        }
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                editText_title.setVisibility(View.VISIBLE);

                // Get the Image from data
                Uri selectedImage = data.getData();

                mediaPath = FilePath.getPath(this, selectedImage);
                //str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                ivPreview.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                //cursor.close();

            } // When an Video is picked
            else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                editText_title.setVisibility(View.VISIBLE);
                // Get the Video from data
                Uri selectedVideo = data.getData();

                mediaPath = FilePath.getPath(this, selectedVideo);

                try {
                    if (mediaPath != null) {
                        File file = new File(mediaPath);
                        long length = file.length();
                        length = length / 1024;
                        System.out.println("File Path : " + file.getPath() + ", File size : " + length + " KB");
                        if (length > 20000) {
                            mediaPath = null;
                            // Show Your Messages
                            CommonUtils.showToast(this, "Your video may only 20 mb");
                        } else {
                            // Set the Video Thumb in ImageView Previewing the Media
                            ivPreview.setImageBitmap(getThumbnailPathForLocalFile(UploadActivity.this, selectedVideo));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
                editText_title.setVisibility(View.VISIBLE);
                Uri selectedDoc = data.getData();

                mediaPath = FilePath.getPath(this, selectedDoc);
                ivPreview.setImageResource(R.drawable.ic_pdf);
                Log.e("TAG", "mediaPath>>>>>>>>>>>: " + mediaPath);
            } else {
                Toast.makeText(this, "You haven't picked Image or Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("TAG", "error>>>>: "+e );
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }


    @OnClick({R.id.iv_back,R.id.image_upload, R.id.video_upload, R.id.link, R.id.doc_file, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.image_upload:
                type = "image";
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
                break;
            case R.id.video_upload:
                type = "video";
                customDialog("Send Video Link","Enter video link here","Upload link");
//                Intent i = new Intent(Intent.ACTION_PICK,
//                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, 1);
                break;
            case R.id.link:
                type = "link";
                customDialog("Send Link","Enter link here","upload link");
                break;
            case R.id.doc_file:
                type = "document";
                Intent galleryIntent3 = new Intent(Intent.ACTION_GET_CONTENT);
                //galleryIntent3.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                galleryIntent3.setType("application/pdf");
                startActivityForResult(galleryIntent3, 2);
                break;
            case R.id.tv_submit:
                title = editText_title.getText().toString();

                if (mediaPath == null) {
                    CommonUtils.showToast(this, getString(R.string.select_file_link));
                }else if (title.length() == 0) {
                    CommonUtils.showToast(this, getString(R.string.enter_description));
                } else {
                    uploadFile();
                }

                break;
        }
    }

    private void customDialog(String title,String hint,String btnName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.send_link, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (alertDialog.isShowing()) {
            alertDialog.hide();
        } else {
            alertDialog.show();
        }

        EditText editText_title = (EditText) dialogView.findViewById(R.id.editText_title);
        TextView title_txt = (TextView) dialogView.findViewById(R.id.title_txt);
        Button buttonUpload = (Button) dialogView.findViewById(R.id.buttonUpload);

        title_txt.setText(title);
        buttonUpload.setText(btnName);
        editText_title.setHint(hint);

        // if decline button is clicked, close the custom dialog
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                link = editText_title.getText().toString();
                Log.e("TAG", "link>>>>>>>>>>>>: " + link);
                if (!link.equalsIgnoreCase("")) {
                    uploadFile();
                } else {
                    CommonUtils.showToast(UploadActivity.this, getString(R.string.enter_link));
                }
                //alertDialog.dismiss();
            }
        });

    }

    // Uploading Image/Video
    private void uploadFile() {
        CommonUtils.progressDialogShow(this);

        DKRInterface getResponse = APIClient.getClient().create(DKRInterface.class);
        Call<Data> call;


        if (mediaPath != null) {
            // Map is used to multipart the file using okhttp3.RequestBody
            File file = new File(mediaPath);

            // Parsing any Media type file
            if (type.equalsIgnoreCase("document"))
                requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
            else requestBody = RequestBody.create(MediaType.parse("*/*"), file);

            //RequestBody PDFreq = RequestBody.create(MediaType.parse("application/pdf"),file);

            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("filename[]", file.getName(), requestBody);

            RequestBody school_id = RequestBody.create(MediaType.parse("school_id"), AppPreferences.getSchoolId());
            RequestBody type1 = RequestBody.create(MediaType.parse("type"), type);
            RequestBody teacher_id = RequestBody.create(MediaType.parse("teacher_id"), AppPreferences.getAccessId());
            RequestBody status = RequestBody.create(MediaType.parse("status"), "1");
            RequestBody section_id1 = RequestBody.create(MediaType.parse("section_id"), section_id);
            RequestBody subject_id1 = RequestBody.create(MediaType.parse("subject_id"), subject_id);
            RequestBody title1 = RequestBody.create(MediaType.parse("title"), title);

            call = getResponse.uploadFile(school_id, teacher_id, type1, status, section_id1, subject_id1, title1, fileToUpload);
        } else {
            call = getResponse.uploadLink(AppPreferences.getSchoolId(), AppPreferences.getAccessId(), type, "1", section_id, subject_id, link);
        }
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                CommonUtils.hideProgressDoalog();
                if (response.body().getStatus().equalsIgnoreCase("true")) {
                    String msg = response.body().getMessage();
                    CommonUtils.showToast(UploadActivity.this, msg);
                    if (alertDialog != null) {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                    }
                    finish();
                } else {
                    //  CommonUtils.showToast(UploadActivity.this,response.body().getMessage());
                }

                // progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(UploadActivity.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
//                            DynamicToast.makeSuccess(this, "All permissions are granted by user!").show();

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        //DynamicToast.makeError(mContext, "Some Error!").show();

                    }
                })
                .onSameThread()
                .check();
    }
}