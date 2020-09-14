package com.muravtech.merisiksha.Bean;

import java.util.List;

public class Paidfee {
    /**
     * data : [{"student_id":"86","payment_mode":"cash","total_fee":"2000","paid_fee":2000,"due":0},{"student_id":"88","payment_mode":"cash","total_fee":"2000","paid_fee":100,"due":1900}]
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
         * student_id : 86
         * payment_mode : cash
         * total_fee : 2000
         * paid_fee : 2000
         * due : 0
         */

        private String student_id;
        private String payment_mode;
        private String total_fee;
        private String paid_fee;
        private String due;

        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public String getPaid_fee() {
            return paid_fee;
        }

        public void setPaid_fee(String paid_fee) {
            this.paid_fee = paid_fee;
        }

        public String getDue() {
            return due;
        }

        public void setDue(String due) {
            this.due = due;
        }
    }
}