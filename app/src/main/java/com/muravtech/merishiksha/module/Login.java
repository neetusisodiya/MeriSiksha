package com.muravtech.merishiksha.module;

import java.util.List;

public class Login {
    /**
     * otp : 649946
     * data : []
     * status : true
     * message : OTP succesfully send
     */

    private String otp;
    private String status;
    private String message;
    private List<?> data;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
