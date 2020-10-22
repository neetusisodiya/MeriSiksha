package com.muravtech.meri_siksha.Response.teacher;

import java.util.List;

public class FriendList {
    /**
     * status : true
     * message : Data get succesfully
     * data : [{"id":86,"name":"Arvind","roll_no":"1306232143","email":"arvind@gmail.com","contact_no":"9079881040","address":"Address","unique_id":"STU20200608073748"},{"id":87,"name":"Arrii","roll_no":"1234565432","email":"arrii@gmail.com","contact_no":"8426052285","address":"Arrii Address","unique_id":"STU20200608093329"},{"id":88,"name":"Ajay","roll_no":"123458422","email":"ajay@gmail.com","contact_no":"0946027800","address":"Ajay Address","unique_id":"STU20200608123524"}]
     */

    private String status;
    private String message;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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
         * id : 86
         * name : Arvind
         * roll_no : 1306232143
         * email : arvind@gmail.com
         * contact_no : 9079881040
         * address : Address
         * unique_id : STU20200608073748
         */

        private String id;
        private String name;
        private String roll_no;
        private String email;
        private String contact_no;
        private String address;
        private String unique_id;
        private String image;

        public String getRelation_status() {
            return relation_status;
        }

        public void setRelation_status(String relation_status) {
            this.relation_status = relation_status;
        }

        private String relation_status;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoll_no() {
            return roll_no;
        }

        public void setRoll_no(String roll_no) {
            this.roll_no = roll_no;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContact_no() {
            return contact_no;
        }

        public void setContact_no(String contact_no) {
            this.contact_no = contact_no;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUnique_id() {
            return unique_id;
        }

        public void setUnique_id(String unique_id) {
            this.unique_id = unique_id;
        }
    }
}
