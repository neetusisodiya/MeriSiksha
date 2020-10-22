package com.muravtech.meri_siksha.Response;

public class ResponseLoginGetIngo {

    /**
     * otp : 131666
     * data : {"id":12,"unique_id":"SCH20200502105851","school_name":"Arrii","contact_person":"arvind","contact_no":"9079881040","alt_contact":"0987654321","reg_code":"12345","email":"arrii@gmail.com","password":"12345","state":"Haryana","state_id":"13","city":"Bhiwani","city_id":"893","address":"Haryana","area_code":12345,"package_id":"1","start_package_date":"2020-05-02","end_package_date":"2020-05-31","school_id":null,"remember_token":null,"created_at":"2020-05-02 10:42:52","updated_at":"2020-05-02 10:58:51","is_approve":2,"approve_on":null,"branch_ids":null}
     * status : true
     * message : Data get succesfully
     */

    private String otp;
    private DataBean data;
    private String status;
    private String message;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * id : 12
         * unique_id : SCH20200502105851
         * school_name : Arrii
         * contact_person : arvind
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
         * school_id : null
         * remember_token : null
         * created_at : 2020-05-02 10:42:52
         * updated_at : 2020-05-02 10:58:51
         * is_approve : 2
         * approve_on : null
         * branch_ids : null
         */

        private String id;
        private String school_name;
        private String contact_person;
        private String contact_no;
        private String email;
        private String state;
        private String city;
        private String address;
        private String school_id;
        private String class_id;
        private String class_name;
        private String section_id;

        public String getFather_name() {
            return father_name;
        }

        public void setFather_name(String father_name) {
            this.father_name = father_name;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        private String father_name;
        private String dob;

        public String getSection_id() {
            return section_id;
        }

        public void setSection_id(String section_id) {
            this.section_id = section_id;
        }

        public String getSection_name() {
            return section_name;
        }

        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        private String section_name;
        private String name;
        private String image;

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }


        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


    }
}
