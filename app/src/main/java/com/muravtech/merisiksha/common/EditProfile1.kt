//package com.muravtech.merisiksha.common
//
//import android.app.DatePickerDialog
//import android.os.Bundle
//import android.widget.DatePicker
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import com.muravtech.merisiksha.R
//import com.muravtech.merisiksha.utils.AppPreferences
//import java.text.SimpleDateFormat
//import java.util.*
//
//class EditProfile1 : AppCompatActivity() {
//    private var backEdit: ImageView? = null
//    private val mcalendar: Calendar? = null
//
//
//    private val dob:DatePickerDialog?=null
//    val dateFormatter:SimpleDateFormat?=null
//    var picker: DatePicker? = null
//    var appPreferences: AppPreferences?=null
//    var Name : String?=null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edit_profile)
//        appPreferences = AppPreferences(this)
//         Name = appPreferences!!.getStringValue(AppPreferences.NAME)
//
//
//        //changeName = findViewById(R.id.changeName);
//        // changeEamil = findViewById(R.id.changeEmail);
//        //changeContact = findViewById(R.id.changeNumber);
//        //changeFather = findViewById(R.id.changeFatherName);
//        //changeDOB=findViewById(R.id.changeDOB);
//        backEdit = findViewById(R.id.backEdit)
//    }
//}