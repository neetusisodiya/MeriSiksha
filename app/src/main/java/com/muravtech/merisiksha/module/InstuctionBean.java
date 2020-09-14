package com.muravtech.merisiksha.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstuctionBean {
    /**
     * status : success
     * data : [{"sno":"1","exam_id":"20204A","exam_name":"exam2","class":"4","section":"A","year":"2020","duration":"60","total_marks":"450","total_subjects":"6","status":"1","discription":"please check exam duration, Student should be on time, student once start exam will not revert back,"}]
     */

    private String status;
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
         * sno : 1
         * exam_id : 20204A
         * exam_name : exam2
         * class : 4
         * section : A
         * year : 2020
         * duration : 60
         * total_marks : 450
         * total_subjects : 6
         * status : 1
         * discription : please check exam duration, Student should be on time, student once start exam will not revert back,
         */

        private String sno;
        private String exam_id;
        private String exam_name;
        @SerializedName("class")
        private String classX;
        private String section;
        private String year;
        private String duration;
        private String total_marks;
        private String total_questions;
        private String status;
        private String discription;
        
        public String getTotal_questions() {
            return total_questions;
        }

        public void setTotal_questions(String total_questions) {
            this.total_questions = total_questions;
        }


        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getExam_id() {
            return exam_id;
        }

        public void setExam_id(String exam_id) {
            this.exam_id = exam_id;
        }

        public String getExam_name() {
            return exam_name;
        }

        public void setExam_name(String exam_name) {
            this.exam_name = exam_name;
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

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getTotal_marks() {
            return total_marks;
        }

        public void setTotal_marks(String total_marks) {
            this.total_marks = total_marks;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }
    }
}
