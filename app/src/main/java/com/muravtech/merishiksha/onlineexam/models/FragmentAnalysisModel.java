package com.muravtech.merishiksha.onlineexam.models;

/**
 * Created by recon on 12/30/2016.
 */

public class FragmentAnalysisModel {

    String subject;
    String totalQuestion;
    String attemped;
    String correct;
    String wrong;
    String findRecord;
    String takenTime;

    public String getFindRecord() {
        return findRecord;
    }

    public void setFindRecord(String findRecord) {
        this.findRecord = findRecord;
    }
    public String getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttemped() {
        return attemped;
    }

    public void setAttemped(String attemped) {
        this.attemped = attemped;
    }

    public String getCorrect() {
        return correct;
    }


    public void setCorrect(String correct) {
        this.correct = correct;
    }


    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getTakenTime() {
        return takenTime;
    }

    public void setTakenTime(String takenTime) {
        this.takenTime = takenTime;
    }


}
