package com.muravtech.merishiksha.teacher_ui;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExeleFileActivity extends AppCompatActivity implements OnClickListener {
    Button writeExcelButton;
    TextView readExcelButton;
    ImageView iv_back;
    static String TAG = "ExelLog";
    private static final String IMAGE_DIRECTORY = "/MeriSiksha";
    String mediaPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exele_file);
        requestMultiplePermissions();

        writeExcelButton = (Button) findViewById(R.id.writeExcel);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        writeExcelButton.setOnClickListener(this);
        readExcelButton = (TextView) findViewById(R.id.readExcel);
        readExcelButton.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.writeExcel:
                saveExcelFile("quizExcel.csv");
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.readExcel:
                mediaPath= Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY+"/"+"quizExcel.csv";
                uploadFile();
                break;
        }
    }

    private  boolean saveExcelFile(String fileName) {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("quizFile");

        // Generate column headings
        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("question_name");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("answer_options");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("option_a");
        c.setCellStyle(cs);

        c = row.createCell(3);
        c.setCellValue("option_b");
        c.setCellStyle(cs);

        c = row.createCell(4);
        c.setCellValue("option_c");
        c.setCellStyle(cs);

        c = row.createCell(5);
        c.setCellValue("option_d");
        c.setCellStyle(cs);

        c = row.createCell(6);
        c.setCellValue("finalanswer");
        c.setCellStyle(cs);

        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));
        sheet1.setColumnWidth(3, (15 * 500));
        sheet1.setColumnWidth(4, (15 * 500));
        sheet1.setColumnWidth(5, (15 * 500));
        sheet1.setColumnWidth(6, (15 * 500));

        // Create a path where we will place our List of objects on external storage
      //  File file = new File(context.getExternalFilesDir(null), fileName);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        FileOutputStream os = null;

        try {
            File file = new File(wallpaperDirectory,
                    fileName);
            file.createNewFile();
            os = new FileOutputStream(file);

            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
            CommonUtils.showToast(this,"Excel file created successfully in app folder");
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + wallpaperDirectory, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }


    private void uploadFile() {
        CommonUtils.progressDialogShow(this);

        DKRInterface getResponse = APIClient.getClient().create(DKRInterface.class);
        Call<Data> call;


        if (mediaPath != null) {
            // Map is used to multipart the file using okhttp3.RequestBody
            File file = new File(mediaPath);

            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/csv"), file);

            //RequestBody PDFreq = RequestBody.create(MediaType.parse("application/pdf"),file);

            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("filename[]", file.getName(), requestBody);

            RequestBody school_id = RequestBody.create(MediaType.parse("school_id"), AppPreferences.getSchoolId());

            RequestBody teacher_id = RequestBody.create(MediaType.parse("teacher_id"), AppPreferences.getAccessId());
            RequestBody section_id = RequestBody.create(MediaType.parse("section_id"), "2");
            RequestBody class_id = RequestBody.create(MediaType.parse("class_id"), "1");


            call = getResponse.uploadExcel(school_id, teacher_id,class_id,  section_id ,fileToUpload);

            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    CommonUtils.hideProgressDoalog();
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        String msg = response.body().getMessage();
                        CommonUtils.showToast(ExeleFileActivity.this, msg);

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
    }


    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(ExeleFileActivity.this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                           // Toast.makeText(ExeleFileActivity.this, "All permissions are granted by user!", Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ExeleFileActivity.this, "Some Error!", Toast.LENGTH_LONG).show();

                    }
                })
                .onSameThread()
                .check();
    }
}
