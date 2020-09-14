package com.muravtech.merisiksha.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassListBean {
    /**
     * data : [{"id":1,"class":"1"},{"id":2,"class":"2"},{"id":3,"class":"3"}]
     * status : true
     */

    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
         * class : 1
         */

        private String id;
        @SerializedName("class")
        private String classX;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }
    }
}
