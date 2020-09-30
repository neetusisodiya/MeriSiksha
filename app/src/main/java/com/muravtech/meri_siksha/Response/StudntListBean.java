package com.muravtech.meri_siksha.Response;

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

        private String type;
        private String created_at;
        private String updated_at;
        private String add_by;
        private String status;

        private String student_id;
        private String class_id;
        private String section_id;
        private String class_name;
        private String section;

    }
}