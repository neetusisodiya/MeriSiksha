package com.muravtech.merisiksha.Response;

import java.util.List;

/**
 * Created by neetu
 * Nine Heartz
 * Date 05/12/2018.
 */

public class MessageHistoryList {

    //    private int total_record;
//    private int total_unread_message;
//    private int page;
    private String message;
    private String status;
    private int page;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public int getTotal_record() {
//        return total_record;
//    }
//
//    public void setTotal_record(int total_record) {
//        this.total_record = total_record;
//    }
//
//    public int getTotal_unread_message() {
//        return total_unread_message;
//    }
//
//    public void setTotal_unread_message(int total_unread_message) {
//        this.total_unread_message = total_unread_message;
//    }
//
//    public int getPage() {
//        return page;
//    }
//
//    public void setPage(int page) {
//        this.page = page;
//    }

    public int getTotal_page() {
        return page;
    }

    public void setTotal_page(int total_page) {
        this.page = total_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {


       // private Object type_id;
       //private String other_data;
       //private String device_id;
        private String id;
        private String type;
        private String user_id;
        private String profile_pic;
        private String profile_pic_thumb;
        private String username;
        private String other_user_id;
        private String other_user_username;
        private String other_user_profile_pic;
        private String other_user_profile_pic_thumb;
        private String message;
        private String created_at;
        private String updated_at;
        private String gender;
        private String chat_type;

        public String getReceiver_type() {
            return receiver_type;
        }

        public void setReceiver_type(String receiver_type) {
            this.receiver_type = receiver_type;
        }

        private String receiver_type;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getOther_gender() {
            return other_gender;
        }

        public void setOther_gender(String other_gender) {
            this.other_gender = other_gender;
        }

        private String other_gender;

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        private String group_id;

        public String getChat_type() {
            return chat_type;
        }
        public void setChat_type(String chat_type) {
            this.chat_type = chat_type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

//        public Object getType_id() {
//            return type_id;
//        }
//
//        public void setType_id(Object type_id) {
//            this.type_id = type_id;
//        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getProfile_pic_thumb() {
            return profile_pic_thumb;
        }

        public void setProfile_pic_thumb(String profile_pic_thumb) {
            this.profile_pic_thumb = profile_pic_thumb;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getOther_user_id() {
            return other_user_id;
        }

        public void setOther_user_id(String other_user_id) {
            this.other_user_id = other_user_id;
        }

        public String getOther_user_username() {
            return other_user_username;
        }

        public void setOther_user_username(String other_user_username) {
            this.other_user_username = other_user_username;
        }

        public String getOther_user_profile_pic() {
            return other_user_profile_pic;
        }

        public void setOther_user_profile_pic(String other_user_profile_pic) {
            this.other_user_profile_pic = other_user_profile_pic;
        }

        public String getOther_user_profile_pic_thumb() {
            return other_user_profile_pic_thumb;
        }

        public void setOther_user_profile_pic_thumb(String other_user_profile_pic_thumb) {
            this.other_user_profile_pic_thumb = other_user_profile_pic_thumb;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

//        public String getOther_data() {
//            return other_data;
//        }
//
//        public void setOther_data(String other_data) {
//            this.other_data = other_data;
//        }

//        public String getDevice_id() {
//            return device_id;
//        }
//
//        public void setDevice_id(String device_id) {
//            this.device_id = device_id;
//        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}