package com.muravtech.meri_siksha.Response;

import java.util.List;

public class CalendarEventListBean {
    /**
     * data : [{"id":2,"school_id":12,"title":"calender title1","date":"2020-06-28","holiday":1,"created_at":"2020-06-30 14:22:13"},{"id":3,"school_id":12,"title":"calender title","date":"2020-06-30","holiday":0,"created_at":"2020-06-30 14:22:34"}]
     * status : true
     * message : Data get succesfully
     */

    private String status;
    private String message;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
         * id : 2
         * school_id : 12
         * title : calender title1
         * date : 2020-06-28
         * holiday : 1
         * created_at : 2020-06-30 14:22:13
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getHoliday() {
            return holiday;
        }

        public void setHoliday(String holiday) {
            this.holiday = holiday;
        }

        private String school_id;
        private String title;
        private String date;
        private String holiday;
        private String created_at;




        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }


        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
