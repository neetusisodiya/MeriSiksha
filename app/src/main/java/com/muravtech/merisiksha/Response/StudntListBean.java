package com.muravtech.merisiksha.Response;

import java.util.List;

public class StudntListBean {
    /**
     * data : [{"id":3,"name":"neetu","roll_no":null,"email":"abc@gmail.com","email_verified_at":null,"password":"12345","institute_name":null,"institue_id":null,"dob":null,"contact_no":"9079884624","alt_contact_no":null,"father_name":null,"mother_name":null,"fcontact_no":null,"mcontact_no":null,"state_id":null,"state":null,"city_id":null,"city":null,"address":null,"unique_id":null,"remember_token":null,"type":null,"created_at":"2020-06-08 06:32:59","updated_at":"2020-06-08 06:32:59","add_by":null,"status":null,"add_by_id":null,"sign_hindi_img":null,"sign_eng_img":null,"student_id":"32","class_id":2,"section_id":"3","class_name":"2","section":"A"}]
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

        public String getEmail_verified_at() {
            return email_verified_at;
        }

        public void setEmail_verified_at(String email_verified_at) {
            this.email_verified_at = email_verified_at;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getInstitute_name() {
            return institute_name;
        }

        public void setInstitute_name(String institute_name) {
            this.institute_name = institute_name;
        }

        public String getInstitue_id() {
            return institue_id;
        }

        public void setInstitue_id(String institue_id) {
            this.institue_id = institue_id;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getContact_no() {
            return contact_no;
        }

        public void setContact_no(String contact_no) {
            this.contact_no = contact_no;
        }

        public String getAlt_contact_no() {
            return alt_contact_no;
        }

        public void setAlt_contact_no(String alt_contact_no) {
            this.alt_contact_no = alt_contact_no;
        }

        public String getFather_name() {
            return father_name;
        }

        public void setFather_name(String father_name) {
            this.father_name = father_name;
        }

        public String getMother_name() {
            return mother_name;
        }

        public void setMother_name(String mother_name) {
            this.mother_name = mother_name;
        }

        public String getFcontact_no() {
            return fcontact_no;
        }

        public void setFcontact_no(String fcontact_no) {
            this.fcontact_no = fcontact_no;
        }

        public String getMcontact_no() {
            return mcontact_no;
        }

        public void setMcontact_no(String mcontact_no) {
            this.mcontact_no = mcontact_no;
        }

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
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

        public String getUnique_id() {
            return unique_id;
        }

        public void setUnique_id(String unique_id) {
            this.unique_id = unique_id;
        }

        public String getRemember_token() {
            return remember_token;
        }

        public void setRemember_token(String remember_token) {
            this.remember_token = remember_token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getAdd_by() {
            return add_by;
        }

        public void setAdd_by(String add_by) {
            this.add_by = add_by;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAdd_by_id() {
            return add_by_id;
        }

        public void setAdd_by_id(String add_by_id) {
            this.add_by_id = add_by_id;
        }

        public String getSign_hindi_img() {
            return sign_hindi_img;
        }

        public void setSign_hindi_img(String sign_hindi_img) {
            this.sign_hindi_img = sign_hindi_img;
        }

        public String getSign_eng_img() {
            return sign_eng_img;
        }

        public void setSign_eng_img(String sign_eng_img) {
            this.sign_eng_img = sign_eng_img;
        }

        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
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

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        /**
         * id : 3
         * name : neetu
         * roll_no : null
         * email : abc@gmail.com
         * email_verified_at : null
         * password : 12345
         * institute_name : null
         * institue_id : null
         * dob : null
         * contact_no : 9079884624
         * alt_contact_no : null
         * father_name : null
         * mother_name : null
         * fcontact_no : null
         * mcontact_no : null
         * state_id : null
         * state : null
         * city_id : null
         * city : null
         * address : null
         * unique_id : null
         * remember_token : null
         * type : null
         * created_at : 2020-06-08 06:32:59
         * updated_at : 2020-06-08 06:32:59
         * add_by : null
         * status : null
         * add_by_id : null
         * sign_hindi_img : null
         * sign_eng_img : null
         * student_id : 32
         * class_id : 2
         * section_id : 3
         * class_name : 2
         * section : A
         */

        private String id;

        public String getAttendence_id() {
            return attendence_status;
        }

        public void setAttendence_id(String attendence_id) {
            this.attendence_status = attendence_id;
        }

        private String attendence_status;
        private String name;
        private String roll_no;
        private String email;
        private String email_verified_at;
        private String password;
        private String institute_name;
        private String institue_id;
        private String dob;
        private String contact_no;
        private String alt_contact_no;
        private String father_name;
        private String mother_name;
        private String fcontact_no;
        private String mcontact_no;
        private String state_id;
        private String state;
        private String city_id;
        private String city;
        private String address;
        private String unique_id;
        private String remember_token;
        private String type;
        private String created_at;
        private String updated_at;
        private String add_by;
        private String status;
        private String add_by_id;
        private String sign_hindi_img;
        private String sign_eng_img;
        private String student_id;
        private String class_id;
        private String section_id;
        private String class_name;
        private String section;

    }
}