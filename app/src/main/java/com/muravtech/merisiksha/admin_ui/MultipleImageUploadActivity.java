package com.muravtech.merisiksha.admin_ui;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.adapter.MultipleImageUploadAdapter;
import com.muravtech.merisiksha.interfaces.OnItemClickListener;
import com.muravtech.merisiksha.module.Data;
import com.muravtech.merisiksha.server.APIClient;
import com.muravtech.merisiksha.server.DKRInterface;
import com.muravtech.merisiksha.utils.AppPreferences;
import com.muravtech.merisiksha.utils.CommonUtils;

import java.io.File;
import java.util.ArrayList;
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

public class MultipleImageUploadActivity extends AppCompatActivity implements OnItemClickListener {
    private static final int REQUEST_CODE_READ_STORAGE = 100;
    @BindView(R.id.recycleImage)
    RecyclerView listView;
    @BindView(R.id.editText_title)
    EditText editText_title;
    @BindView(R.id.tv_choose)
    TextView tv_choose;
    String title;
    private ArrayList<Uri> arrayList = new ArrayList<>();
    OnItemClickListener onItemClickListener;
    MultipleImageUploadAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        ButterKnife.bind(this);
        onItemClickListener=this;
        requestMultiplePermissions();
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new GridLayoutManager(this, 2));
    }
    private void showChooser() {
        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ_STORAGE) {
                if (resultData != null) {
                    tv_choose.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    editText_title.setVisibility(View.VISIBLE);
                    if (resultData.getClipData() != null) {
                        int count = resultData.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = resultData.getClipData().getItemAt(currentItem).getUri();
                            currentItem = currentItem + 1;

                            Log.d("Uri Selected", imageUri.toString());

                            try {
                                if(arrayList.size()>7){
                                    CommonUtils.showToast(this,"You can select 10 image only");
                                }else {
                                    arrayList.add(imageUri);
                                    mAdapter = new MultipleImageUploadAdapter(MultipleImageUploadActivity.this, arrayList, onItemClickListener);
                                    listView.setAdapter(mAdapter);
                                }

                            } catch (Exception e) {
                                Log.e("TAG", "File select error", e);
                            }
                        }
                    } else if (resultData.getData() != null) {

                        final Uri uri = resultData.getData();
                        Log.i("TAG", "Uri = " + uri.toString());
                        try {
                            if(arrayList.size()>7){
                                CommonUtils.showToast(this,"You can select 10 image only");
                            }else {
                                arrayList.add(uri);
                                mAdapter = new MultipleImageUploadAdapter(MultipleImageUploadActivity.this, arrayList, onItemClickListener);
                                listView.setAdapter(mAdapter);
                            }

                        } catch (Exception e) {
                            Log.e("TAG", "File select error", e);
                        }
                    }
                }
            }
        }
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(MultipleImageUploadActivity.this)
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

    @OnClick({R.id.iv_back, R.id.btnChoose, R.id.btnUpload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btnChoose:
                if(arrayList.size()>7){
                    CommonUtils.showToast(this,"You can select 10 image only");
                }else {
                    showChooser();
                }
                break;
            case R.id.btnUpload:
                title=editText_title.getText().toString();
                if(title.length()==0){
                    CommonUtils.showToast(this, getString(R.string.enter_description));
                }else
                if (arrayList.size() == 0) {
                    CommonUtils.showToast(this, "Select atleast one image");
                } else {
                    uploadFile();
                }
                break;
        }
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        File file = new File(getRealPathFromURI(fileUri));
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    // Uploading Image/Video
    private void uploadFile() {
        CommonUtils.progressDialogShow(this);

        DKRInterface getResponse = APIClient.getClient().create(DKRInterface.class);

        List<MultipartBody.Part> parts = new ArrayList<>();

        if (arrayList != null) {
            // create part for file (photo, video, ...)
            for (int i = 0; i < arrayList.size(); i++) {
                // parts.add(prepareFilePart("image"+i, arrayList.get(i)));
                parts.add(prepareFilePart("filename[]", arrayList.get(i)));
            }
        }

        // Map is used to multipart the file using okhttp3.RequestBody
        RequestBody school_id = RequestBody.create(MediaType.parse("school_id"), AppPreferences.getSchoolId());
        RequestBody type1 = RequestBody.create(MediaType.parse("type"), "image");
        RequestBody status = RequestBody.create(MediaType.parse("status"), "0");
        RequestBody title1 = RequestBody.create(MediaType.parse("title"), title);
        Call<Data> call = getResponse.uploadFile(school_id, type1,status,title1, parts);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    CommonUtils.hideProgressDoalog();

                    String msg = response.body().getMessage();
                    CommonUtils.showToast(MultipleImageUploadActivity.this, msg);

                    finish();
                } else {
                    CommonUtils.showToast(MultipleImageUploadActivity.this, response.body().getMessage());
                }

                // progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(int position) {
        if(arrayList.size()==1){
            tv_choose.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            editText_title.setVisibility(View.GONE);
        }
            arrayList.remove(position);
            mAdapter.notifyDataSetChanged();

    }
}