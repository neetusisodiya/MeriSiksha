package com.muravtech.meri_siksha.Response;

import java.util.List;


/**
 * Created by Neetu
 * Nine Heartz
 * Date 05/12/2018.
 */

public class ChatModelList {


    /**
     * data : [{"id":110,"conversation_id":15,"user_id":189,"profile_pic":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","username":"sumitkumawat","other_user_id":181,"other_user_username":"Rahul","other_user_profile_pic":"http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg","other_user_profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg","message":"hii","type":"Accept","other_data":null,"read_status":1,"status":0,"user_time":"2018-11-30 11:16:47","device_id":123456,"created_at":"2018-11-30 11:15:58"},{"id":109,"conversation_id":15,"user_id":189,"profile_pic":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","username":"sumitkumawat","other_user_id":181,"other_user_username":"Rahul","other_user_profile_pic":"http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg","other_user_profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg","message":"hello","type":"Accept","other_data":null,"read_status":1,"status":0,"user_time":"2018-11-30 11:15:51","device_id":123456,"created_at":"2018-11-30 11:15:02"},{"id":108,"conversation_id":15,"user_id":189,"profile_pic":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","username":"sumitkumawat","other_user_id":181,"other_user_username":"Rahul","other_user_profile_pic":"http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg","other_user_profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg","message":"hi","type":"Accept","other_data":null,"read_status":1,"status":0,"user_time":"2018-11-30 11:15:23","device_id":123456,"created_at":"2018-11-30 11:14:35"},{"id":67,"conversation_id":15,"user_id":189,"profile_pic":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","username":"sumitkumawat","other_user_id":181,"other_user_username":"Rahul","other_user_profile_pic":"http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg","other_user_profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg","message":"hiii","type":"Accept","other_data":null,"read_status":1,"status":0,"user_time":"2018-11-30 06:48:31","device_id":123456,"created_at":"2018-11-30 06:47:42"},{"id":66,"conversation_id":15,"user_id":189,"profile_pic":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","username":"sumitkumawat","other_user_id":181,"other_user_username":"Rahul","other_user_profile_pic":"http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg","other_user_profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg","message":"hi","type":"Accept","other_data":null,"read_status":1,"status":0,"user_time":"2018-11-30 06:48:10","device_id":123456,"created_at":"2018-11-30 06:47:22"}]
     * message : User thread get successfully
     * status : true
     * total_record : 5
     * total_unread_message : 0
     * is_blocked : false
     * is_blocked_by_me : false
     */

    private String message;
    private String status;
    private int total_record;
    private int total_unread_message;
    private String is_blocked;
    private String is_blocked_by_me;
    private List<DataBean> data;


    public String getIs_blocked_by_me() {
        return is_blocked_by_me;
    }

    public void setIs_blocked_by_me(String is_blocked_by_me) {
        this.is_blocked_by_me = is_blocked_by_me;
    }

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

    public int getTotal_record() {
        return total_record;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }

    public int getTotal_unread_message() {
        return total_unread_message;
    }

    public void setTotal_unread_message(int total_unread_message) {
        this.total_unread_message = total_unread_message;
    }

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 110
         * conversation_id : 15
         * user_id : 189
         * profile_pic : http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg
         * profile_pic_thumb : http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg
         * username : sumitkumawat
         * other_user_id : 181
         * other_user_username : Rahul
         * other_user_profile_pic : http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg
         * other_user_profile_pic_thumb : http://demo2server.com/hearu/uploads/users/thumb/15434926346371892033.jpg
         * message : hii
         * type : Accept
         * other_data : null
         * read_status : 1
         * status : 0
         * user_time : 2018-11-30 11:16:47
         * device_id : 123456
         * created_at : 2018-11-30 11:15:58
         */

        private int id;
        private int conversation_id;
        private int user_id;
        private String profile_pic;
        private String profile_pic_thumb;
        private String username;
        private String other_user_id;
        private String other_user_username;
        private String other_user_profile_pic;
        private String other_user_profile_pic_thumb;
        private String message;
        private String type;
        private String chat_type;
        private String status;

        private String created_at;


        private String transaction_id;

        //private String chat_type;


        public String getChat_type() {
            return chat_type;
        }

        public void setChat_type(String chat_type) {
            this.chat_type = chat_type;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}

