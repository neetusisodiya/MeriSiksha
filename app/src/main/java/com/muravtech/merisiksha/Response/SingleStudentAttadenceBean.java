package com.muravtech.merisiksha.Response;

import java.util.List;

public class SingleStudentAttadenceBean {
    /**
     * data : [{"id":101,"student_id":86,"attendance":"2020-06-11","status":1},{"id":102,"student_id":86,"attendance":"2020-06-12","status":1},{"id":103,"student_id":86,"attendance":"2020-06-29","status":1},{"id":104,"student_id":86,"attendance":"2020-07-01","status":1}]
     * status : true
     * message : Default Data
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
         * id : 101
         * student_id : 86
         * attendance : 2020-06-11
         * status : 1
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String student_id;
        private String attendance;
        private String status;


        public String getAttendance() {
            return attendance;
        }

        public void setAttendance(String attendance) {
            this.attendance = attendance;
        }

    }
}
