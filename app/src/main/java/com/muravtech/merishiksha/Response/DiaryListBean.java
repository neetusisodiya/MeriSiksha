package com.muravtech.merishiksha.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiaryListBean {
    /**
     * data : [{"id":4,"teacher_id":19,"class_id":1,"section_id":2,"subject_id":1,"date":"2020-06-30","title":"diary title","description":"diary description","created_at":"2020-06-30 08:53:19","updated_at":"2020-06-30 08:53:19","class":"1","section":"B","subject_name":"Math"},{"id":6,"teacher_id":19,"class_id":1,"section_id":2,"subject_id":1,"date":"2020-06-30","title":"diary title1","description":"diary description1","created_at":"2020-06-30 08:53:19","updated_at":"2020-06-30 08:53:19","class":"1","section":"B","subject_name":"Math"}]
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
         * id : 4
         * teacher_id : 19
         * class_id : 1
         * section_id : 2
         * subject_id : 1
         * date : 2020-06-30
         * title : diary title
         * description : diary description
         * created_at : 2020-06-30 08:53:19
         * updated_at : 2020-06-30 08:53:19
         * class : 1
         * section : B
         * subject_name : Math
         */

        private String id;
        private String teacher_id;
        private String class_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getSection_id() {
            return section_id;
        }

        public void setSection_id(String section_id) {
            this.section_id = section_id;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        private String section_id;
        private String subject_id;
        private String date;
        private String title;
        private String description;
        private String created_at;
        private String updated_at;
        @SerializedName("class")
        private String classX;
        private String section;
        private String subject_name;



        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }



        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }
    }
}
