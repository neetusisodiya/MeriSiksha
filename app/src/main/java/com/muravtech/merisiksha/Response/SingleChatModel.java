package com.muravtech.merisiksha.Response;

/**
 * Created by Akram Khan
 * Nine Heartz
 * Date 05/12/2018.
 */

public class SingleChatModel {


    /**
     * data : {"user_id":"189","username":"sumitkumawat","profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","profile_pic":"http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg","other_user_id":"191","other_user_username":"perfect","other_user_profile_pic":"http://demo2server.com/hearu/uploads/users/thumb/15435671851087938239.jpg","other_user_profile_pic_thumb":"http://demo2server.com/hearu/uploads/users/thumb/15435671851087938239.jpg","type":"Accept","device_id":"123456","title":"hello","accept_decline":"Text","message":"fghfghgh","msg_id":104,"id":104,"created_at":"2018-11-30 10:49:55","user_time":"2018-11-30 10:49:55"}
     * message : Receiver Data
     * status : true
     */

    private ChatModelList.DataBean data;
    private String message;
    private String status;

    public ChatModelList.DataBean getData() {
        return data;
    }

    public void setData(ChatModelList.DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * user_id : 189
         * username : sumitkumawat
         * profile_pic_thumb : http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg
         * profile_pic : http://demo2server.com/hearu/uploads/users/15435571875136782751.jpg
         * other_user_id : 191
         * other_user_username : perfect
         * other_user_profile_pic : http://demo2server.com/hearu/uploads/users/thumb/15435671851087938239.jpg
         * other_user_profile_pic_thumb : http://demo2server.com/hearu/uploads/users/thumb/15435671851087938239.jpg
         * type : Accept
         * device_id : 123456
         * title : hello
         * accept_decline : Text
         * message : fghfghgh
         * msg_id : 104
         * id : 104
         * created_at : 2018-11-30 10:49:55
         * user_time : 2018-11-30 10:49:55
         */

        private String user_id;
        private String username;
        private String profile_pic_thumb;
        private String profile_pic;
        private String other_user_id;
        private String other_user_username;
        private String other_user_profile_pic;
        private String other_user_profile_pic_thumb;
        private String type;
        private String device_id;
        private String title;
        private String accept_decline;
        private String message;
        private String msg_id;
        private String id;
        private String created_at;
        private String user_time;
        private String gift_id;

        private String transaction_id;
        private String gift_price;
        private String gift_type;
        private String gift_value;

        public String getGift_id() {
            return gift_id;
        }

        public void setGift_id(String gift_id) {
            this.gift_id = gift_id;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getGift_price() {
            return gift_price;
        }

        public void setGift_price(String gift_price) {
            this.gift_price = gift_price;
        }

        public String getGift_type() {
            return gift_type;
        }

        public void setGift_type(String gift_type) {
            this.gift_type = gift_type;
        }

        public String getGift_value() {
            return gift_value;
        }

        public void setGift_value(String gift_value) {
            this.gift_value = gift_value;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProfile_pic_thumb() {
            return profile_pic_thumb;
        }

        public void setProfile_pic_thumb(String profile_pic_thumb) {
            this.profile_pic_thumb = profile_pic_thumb;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAccept_decline() {
            return accept_decline;
        }

        public void setAccept_decline(String accept_decline) {
            this.accept_decline = accept_decline;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUser_time() {
            return user_time;
        }

        public void setUser_time(String user_time) {
            this.user_time = user_time;
        }
    }
}