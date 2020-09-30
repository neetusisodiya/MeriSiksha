package com.muravtech.meri_siksha.server;

import com.google.gson.JsonObject;
import com.muravtech.meri_siksha.Bean.Paidfee;
import com.muravtech.meri_siksha.Bean.UpdateBean;
import com.muravtech.meri_siksha.Response.CalendarEventListBean;
import com.muravtech.meri_siksha.Response.ClassListBean;
import com.muravtech.meri_siksha.Response.DiaryListBean;
import com.muravtech.meri_siksha.Response.GetFriendRequestList;
import com.muravtech.meri_siksha.Response.QuizWrapper;
import com.muravtech.meri_siksha.Response.ResponseLoginGetIngo;
import com.muravtech.meri_siksha.Response.SectionWiseList;
import com.muravtech.meri_siksha.Response.SingleStudentAttadenceBean;
import com.muravtech.meri_siksha.Response.StaffListDetailBean;
import com.muravtech.meri_siksha.Response.StudentFees;
import com.muravtech.meri_siksha.Response.StudntListBean;
import com.muravtech.meri_siksha.Response.SubjectListBean;
import com.muravtech.meri_siksha.Response.TaskListBean;
import com.muravtech.meri_siksha.Response.teacher.FriendList;
import com.muravtech.meri_siksha.module.AdminForChat;
import com.muravtech.meri_siksha.module.Data;
import com.muravtech.meri_siksha.module.InstuctionBean;
import com.muravtech.meri_siksha.module.Login;
import com.muravtech.meri_siksha.module.Notification;
import com.muravtech.meri_siksha.module.TeacherListForFriend;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DKRInterface {

    @POST("check-user-status")
    public Call<Data> checkUser(
            @Body RequestBody requestBody);

    @POST("edit-profile")
    Call<ResponseLoginGetIngo> editProfile(
            @Body RequestBody requestBody);

    @POST("login")
    Call<Login> LoginInfoGet(@Body RequestBody requestBody);

    @POST("otp-verify-data")
    Call<ResponseLoginGetIngo> otpVerify(@Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("get-fee")
    public Call<StudentFees> getfees(
            @Field("student_id") String studentid,
            @Field("school_id") String school_id
    );

    @POST("upload-file-list")
    public Call<UpdateBean> getFileList(@Body RequestBody requestBody);


    @FormUrlEncoded
    @POST("get-class")
    Call<ClassListBean> getClassList(
            @Field("id") String school_id,
            @Field("teacher_id") String teacher_id
    );

    @FormUrlEncoded
    @POST("notification")
    Call<Notification> getNotificationList(
            @Field("user_id") String user_id,
            @Field("school_id") String school_id
    );

    @FormUrlEncoded
    @POST("get-student-list-by-section-id")
    Call<FriendList> getStudentListForFriend(
            @Field("student_id") String student_id,
            @Field("school_id") String school_id,
            @Field("section_id") String section_id,
            @Field("user_type") String user_type);


    @FormUrlEncoded
    @POST("get-friend-request")
    Call<GetFriendRequestList> getFriendRequest(
            @Field("school_id") String school_id,
            @Field("user_id") String user_id,
            @Field("user_type") String user_type
    );

    @FormUrlEncoded
    @POST("friend-request-action")
    Call<GetFriendRequestList> sendFriendRequestAction(
            @Field("school_id") String school_id,
            @Field("user_id") String user_id,
            @Field("request_id") String request_id,
            @Field("user_type") String user_type,
            @Field("request_type") String request_type,
            @Field("action") String action
    );

    @FormUrlEncoded
    @POST("send-friend-request")
    Call<FriendList> sendFriendRequest(
            @Field("school_id") String school_id,
            @Field("user_id") String user_id,
            @Field("friend_id") String sender_id,
            @Field("user_type") String user_type,
            @Field("friend_type") String receiver_type
    );

    @FormUrlEncoded
    @POST("block-unblock-request")
    Call<RequestBody> blockAndUnblockRequest(
            @Field("school_id") String school_id,
            @Field("user_id") String user_id,
            @Field("request_id") String request_id,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("calendar-list")
    Call<CalendarEventListBean> getCalendarEventList(
            @Field("school_id") String school_id);

    @FormUrlEncoded
    @POST("subject-list")
    Call<SubjectListBean> getSubjectList(
            @Field("school_id") String school_id,
            @Field("section_id") String id);

    @FormUrlEncoded
    @POST("get-attendence")
    Call<SingleStudentAttadenceBean> getAttendence(
            @Field("school_id") String school_id,
            @Field("student_id") String student_id,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date);

    @FormUrlEncoded
    @POST("teacher-list")
    Call<StaffListDetailBean> getTeacherList(
            @Field("school_id") String id);

    @FormUrlEncoded
    @POST("fee-list")
    Call<Paidfee> getFeesList(
            @Field("class_id") String id,
            @Field("school_id") String school_id);


    @FormUrlEncoded
    @POST("student-list")
    public Call<StudntListBean> getStudentList(
            @Field("school_id") String school_id,
            @Field("section_id") String id,
            @Field("date") String date
    );


    @FormUrlEncoded
    @POST("get-section")
    public Call<SectionWiseList> getClassWiseSectionList(
            @Field("school_id") String school_id,
            @Field("teacher_id") String teacher_id,
            @Field("class_id") String class_id);


    @FormUrlEncoded
    @POST("teacher-assign-task")
    Call<Data> submitTaskforTeacher(
            @Field("school_id") String school_id,
            @Field("teacher_id") String teacher_id,
            @Field("description") String description,
            @Field("complete_data") String complete_data,
            @Field("created_date") String created_date,
            @Field("title") String title);


    @FormUrlEncoded
    @POST("calendar")
    Call<Data> submitEventHoliday(
            @Field("school_id") String school_id,
            @Field("date") String date,
            @Field("holiday") String holiday,
            @Field("title") String title);


    @FormUrlEncoded
    @POST("set-diary")
    Call<Data> submitDiary(
            @Field("school_id") String school_id,
            @Field("teacher_id") String teacher_id,
            @Field("section_id") String section_id,
            @Field("date") String date,
            @Field("subject") String subject,
            @Field("title") String title,
            @Field("description") String description);

    @Multipart
    @POST("upload-file")
    Call<Data> uploadFile(@Part("school_id") RequestBody school_id,
                          @Part("type") RequestBody type,
                          @Part("status") RequestBody status,
                          @Part("title") RequestBody title,
                          @Part List<MultipartBody.Part> file1);


    @Multipart
    @POST("upload-file")
    Call<Data> uploadFile(@Part("school_id") RequestBody school_id,
                          @Part("teacher_id") RequestBody teacher_id,
                          @Part("type") RequestBody type,
                          @Part("status") RequestBody status,
                          @Part("section_id") RequestBody section_id,
                          @Part("subject_id") RequestBody subject_id,
                          @Part("title") RequestBody title,
                          @Part MultipartBody.Part file1);

    @FormUrlEncoded
    @POST("upload-file")
    Call<Data> uploadLink(@Field("school_id") String school_id,
                          @Field("teacher_id") String teacher_id,
                          @Field("type") String type,
                          @Field("status") String status,
//                          @Field("title") String title,
                          @Field("section_id") String section_id,
                          @Field("subject_id") String subject_id,
                          @Field("link") String link);


    @POST("diary-list")
    Call<DiaryListBean> getDiaryList(@Body RequestBody requestBody);


    //not added school id
    @POST("list-teacher-assign-task")
    Call<TaskListBean> getTeacherTaskList(@Body RequestBody requestBody);

    @POST("check-teacher-assign-task")
    Call<Data> getTeacherComplete(@Body RequestBody requestBody);


    @FormUrlEncoded
    @POST("daily-quiz-question-list")
    Call<QuizWrapper> DailyQuiz(@Field("school_id") String school_id,
                                @Field("section_id") String section_id);


    @POST("save-attendence")
    Call<Data> saveAttendence(@Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("admin_list")
    Call<AdminForChat> getAdmin(
            @Field("school_id") String school_id,
            @Field("user_id") String user_id,
            @Field("user_type") String user_type
    );

    @FormUrlEncoded
    @POST("teacher_list")
    Call<TeacherListForFriend> getTeacherListForFriend(
            @Field("user_id") String user_id,
            @Field("school_id") String school_id,
            @Field("section_id") String section,
            @Field("user_type") String type
    );


    //Online exam related api
//    @POST("instruction_api.php")
//    Call<InstuctionBean> getInstuction(@Body RequestBody requestBody);

    @POST("exam_list")
    Call<InstuctionBean> getExamList(@Body RequestBody requestBody);

    @POST("exampage")
    Call<JsonObject> getQuestionAnswer(@Body RequestBody requestBody);

    @POST("submit_answer_api")
    Call<Data> submitQuestionAnswer(@Body RequestBody requestBody);

    @POST("result-list")
    Call<InstuctionBean> getResultList(@Body RequestBody requestBody);

    @POST("student_result_list")
    Call<InstuctionBean> getResultForStudentList(@Body RequestBody requestBody);

    @POST("publish_result")
    Call<ResponseBody> publishResult(@Body RequestBody requestBody);

    @POST("get_report_api")
    Call<JsonObject> getReport(@Body RequestBody requestBody);


}
