package com.muravtech.merisiksha.module;

import java.util.List;

public class AdminForChat {
    /**
     * status : true
     * data : [{"id":"12","unique_id":"SCH20200502105851","school_name":"Murav","contact_person":"Robin","contact_no":"9079881040","alt_contact":"0987654321","reg_code":"12345","email":"arrii@gmail.com","password":"12345","state":"Haryana","state_id":"13","city":"Bhiwani","city_id":"893","address":"Haryana","area_code":"12345","package_id":"1","start_package_date":"2020-05-02","end_package_date":"2020-05-31","remember_token":null,"created_at":"2020-05-02 10:42:52","updated_at":"2020-05-02 10:58:51","is_approve":"2","approve_on":null,"branch_ids":null,"image":"15956719095f1c0565e30a1.png","relation_status":"0"}]
     */

    private boolean status;
    private List<DataBean> data;

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
         * id : 12
         * unique_id : SCH20200502105851
         * school_name : Murav
         * contact_person : Robin
         * contact_no : 9079881040
         * alt_contact : 0987654321
         * reg_code : 12345
         * email : arrii@gmail.com
         * password : 12345
         * state : Haryana
         * state_id : 13
         * city : Bhiwani
         * city_id : 893
         * address : Haryana
         * area_code : 12345
         * package_id : 1
         * start_package_date : 2020-05-02
         * end_package_date : 2020-05-31
         * remember_token : null
         * created_at : 2020-05-02 10:42:52
         * updated_at : 2020-05-02 10:58:51
         * is_approve : 2
         * approve_on : null
         * branch_ids : null
         * image : 15956719095f1c0565e30a1.png
         * relation_status : 0
         */

        private String id;
        private String unique_id;
        private String school_name;
        private String contact_person;
        private String contact_no;
        private String alt_contact;
        private String reg_code;
        private String email;
        private String state;
        private String state_id;
        private String city;
        private String city_id;
        private String address;
        private String is_approve;
        private String image;
        private String relation_status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUnique_id() {
            return unique_id;
        }

        public void setUnique_id(String unique_id) {
            this.unique_id = unique_id;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getContact_person() {
            return contact_person;
        }

        public void setContact_person(String contact_person) {
            this.contact_person = contact_person;
        }

        public String getContact_no() {
            return contact_no;
        }

        public void setContact_no(String contact_no) {
            this.contact_no = contact_no;
        }

        public String getAlt_contact() {
            return alt_contact;
        }

        public void setAlt_contact(String alt_contact) {
            this.alt_contact = alt_contact;
        }

        public String getReg_code() {
            return reg_code;
        }

        public void setReg_code(String reg_code) {
            this.reg_code = reg_code;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


        public String getIs_approve() {
            return is_approve;
        }

        public void setIs_approve(String is_approve) {
            this.is_approve = is_approve;
        }


        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRelation_status() {
            return relation_status;
        }

        public void setRelation_status(String relation_status) {
            this.relation_status = relation_status;
        }
    }
}