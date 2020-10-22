package com.muravtech.meri_siksha.Response;

import java.util.List;

public class StaffListDetailBean {
    /**
     * data : [{"id":19,"name":"Arrii","qualification":"Btech","email":"arrii@gmail.com","email_verified_at":null,"password":"12345","addby_id":"12","addby_name":"school","dob":"2009-06-10","contact_no":"907988140","alt_contact_no":"1234567890","father_name":"Arrii Father","mother_name":"ArriiMother","profile_pic":"15931576585ef5a81a87978.png","state_id":12,"state":"Gujarat","city_id":881,"city":"Visnagar","type":null,"unique_id":"THR20200609053337","address":"Arrii Address","remember_token":null,"created_at":null,"updated_at":null},{"id":21,"name":"Arvind","qualification":"Btech","email":"arvind1@gmail.com","email_verified_at":null,"password":"12345","addby_id":"12","addby_name":"school","dob":null,"contact_no":"8426052285","alt_contact_no":null,"father_name":null,"mother_name":null,"profile_pic":null,"state_id":11,"state":"Goa","city_id":707,"city":"Madgaon","type":null,"unique_id":"THR20200612072931","address":null,"remember_token":null,"created_at":null,"updated_at":null}]
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
         * id : 19
         * name : Arrii
         * qualification : Btech
         * email : arrii@gmail.com
         * email_verified_at : null
         * password : 12345
         * addby_id : 12
         * addby_name : school
         * dob : 2009-06-10
         * contact_no : 907988140
         * alt_contact_no : 1234567890
         * father_name : Arrii Father
         * mother_name : ArriiMother
         * profile_pic : 15931576585ef5a81a87978.png
         * state_id : 12
         * state : Gujarat
         * city_id : 881
         * city : Visnagar
         * type : null
         * unique_id : THR20200609053337
         * address : Arrii Address
         * remember_token : null
         * created_at : null
         * updated_at : null
         */

        private String id;
        private String name;
        private String qualification;
        private String email;

        private String dob;
        private String contact_no;
        private String profile_pic;
        private String state_id;
        private String state;
        private String city_id;
        private String city;

        private String unique_id;
        private String address;


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

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
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


        public String getUnique_id() {
            return unique_id;
        }

        public void setUnique_id(String unique_id) {
            this.unique_id = unique_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


    }
}
