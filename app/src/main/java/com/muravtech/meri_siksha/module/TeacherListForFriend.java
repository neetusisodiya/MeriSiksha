package com.muravtech.meri_siksha.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherListForFriend {
    /**
     * status : true
     * data : [{"sno":"1","school_id":"12","teacher_id":"14","class":"4","section":"A","status":"1","name":"neetu"}]
     */

    private boolean status;
    private List<DataBean> data;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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
         * school_id : 12
         * teacher_id : 14
         * class : 4
         * section : A
         * status : 1
         * name : neetu
         */

        private String id;
        private String school_id;
        private String teacher_id;
        @SerializedName("class")
        private String classX;
        private String section;
        private String status;
        private String name;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        private String image;

        public String getRelation_status() {
            return relation_status;
        }

        public void setRelation_status(String relation_status) {
            this.relation_status = relation_status;
        }

        private String relation_status;

        public String getSno() {
            return id;
        }

        public void setSno(String sno) {
            this.id = sno;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
