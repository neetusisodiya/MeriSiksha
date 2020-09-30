package com.muravtech.meri_siksha.module;

import java.util.List;

public class Notification {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * status : true
     * data : [{"id":1,"user_id":"2","message":"notification1","created_at":"2020-08-21 11:24:13"},{"id":2,"user_id":"2","message":"notification2","created_at":"2020-08-22 11:24:41"}]
     * message : Data get succesfully
     */

    private boolean status;
    private String message;
    private List<DataBean> data;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * user_id : 2
         * message : notification1
         * created_at : 2020-08-21 11:24:13
         */

        private int id;
        private String user_id;
        private String message;
        private String created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
