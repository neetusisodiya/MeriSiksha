package com.muravtech.meri_siksha.Response;

import java.util.List;

public class QuizWrapper {
    /**
     * data : [{"id":7,"class_id":"1","section_id":"1","question_name":"test question3","answer_options":"2","finalanswer":"B","option_a":"one3","option_b":"two3","option_c":null,"option_d":null,"option_e":null,"teacher_id":19,"created_at":"2020-07-03 11:42:11"},{"id":8,"class_id":"1","section_id":"1","question_name":"question_name_test","answer_options":"5","finalanswer":"B","option_a":"one","option_b":"two","option_c":"three","option_d":"four","option_e":"five","teacher_id":19,"created_at":"2020-07-03 11:58:04"}]
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
         * id : 7
         * class_id : 1
         * section_id : 1
         * question_name : test question3
         * answer_options : 2
         * finalanswer : B
         * option_a : one3
         * option_b : two3
         * option_c : null
         * option_d : null
         * option_e : null
         * teacher_id : 19
         * created_at : 2020-07-03 11:42:11
         */

        private String id;
        private String class_id;
        private String section_id;
        private String question_name;
        private String answer_options;
        private String finalanswer;
        private String option_a;
        private String option_b;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOption_c() {
            return option_c;
        }

        public void setOption_c(String option_c) {
            this.option_c = option_c;
        }

        public String getOption_d() {
            return option_d;
        }

        public void setOption_d(String option_d) {
            this.option_d = option_d;
        }

        public String getOption_e() {
            return option_e;
        }

        public void setOption_e(String option_e) {
            this.option_e = option_e;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        private String option_c;
        private String option_d;
        private String option_e;
        private String teacher_id;
        private String created_at;



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

        public String getQuestion_name() {
            return question_name;
        }

        public void setQuestion_name(String question_name) {
            this.question_name = question_name;
        }

        public String getAnswer_options() {
            return answer_options;
        }

        public void setAnswer_options(String answer_options) {
            this.answer_options = answer_options;
        }

        public String getFinalanswer() {
            return finalanswer;
        }

        public void setFinalanswer(String finalanswer) {
            this.finalanswer = finalanswer;
        }

        public String getOption_a() {
            return option_a;
        }

        public void setOption_a(String option_a) {
            this.option_a = option_a;
        }

        public String getOption_b() {
            return option_b;
        }

        public void setOption_b(String option_b) {
            this.option_b = option_b;
        }


        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}