package com.arunbalachandran.ehealth.dto;

public class LoginRequest {
    private String emailid;
    private String pwd;

    public String getEmailid() {
        return emailid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
