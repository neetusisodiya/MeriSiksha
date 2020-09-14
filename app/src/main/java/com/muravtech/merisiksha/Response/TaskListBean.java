package com.muravtech.merisiksha.Response;

import java.util.List;

public class TaskListBean {
    /**
     * data : [{"id":3,"school_id":12,"teacher_id":19,"teacher_name":"Arrii","title":"teacher title1","description":"teacher description1","complete_data":"2020-06-09","created_date":"2020-06-11"},{"id":4,"school_id":12,"teacher_id":19,"teacher_name":"Arrii","title":"Arrii Title","description":"Arrii Description","complete_data":"2020-06-15","created_date":"2020-06-09"},{"id":7,"school_id":12,"teacher_id":19,"teacher_name":"Arrii","title":"arrii","description":"bfgb","complete_data":"2020-06-17","created_date":"2020-06-10"}]
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
         * id : 3
         * school_id : 12
         * teacher_id : 19
         * teacher_name : Arrii
         * title : teacher title1
         * description : teacher description1
         * complete_data : 2020-06-09
         * created_date : 2020-06-11
         */

        private int id;
        private int school_id;
        private int teacher_id;
        private String teacher_name;
        private String title;
        private String description;
        private String complete_data;
        private String created_date;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSchool_id() {
            return school_id;
        }

        public void setSchool_id(int school_id) {
            this.school_id = school_id;
        }

        public int getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(int teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getComplete_data() {
            return complete_data;
        }

        public void setComplete_data(String complete_data) {
            this.complete_data = complete_data;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }
    }
}
