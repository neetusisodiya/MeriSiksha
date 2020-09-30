package com.muravtech.meri_siksha.Bean;

public class VideoBean{
    /**
     * id : 2
     * subject_name : Netwroking
     * ytd_link : https://www.youtube.com/watch?v=beqprrnaKFc
     * course_name : BCA
     * subject_id : 1
     */

    private int id;
    private String subject_name;
    private String ytd_link;
    private String course_name;
    private String subject_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getYtd_link() {
        return ytd_link;
    }

    public void setYtd_link(String ytd_link) {
        this.ytd_link = ytd_link;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }
}