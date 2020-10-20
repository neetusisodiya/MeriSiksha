package com.muravtech.merishiksha.onlineexam.models;


/**
 * Created by recon on 12/30/2016.
 */

public class FragmentSolutionModel {

    String id;
    String correctanswer;
    String direction;
    String question_name;
    String option_a;
    String option_b;
    String option_c;
    String option_d;
    String option_e=null;
    String explanation;
    String useranswer;
    String questiontype;
    String isReportd;

    public String getFinalanswer() {
        return finalanswer;
    }

    public void setFinalanswer(String finalanswer) {
        this.finalanswer = finalanswer;
    }

    String finalanswer;
    public String getIsReportd() {
        return isReportd;
    }

    public void setIsReportd(String isReportd) {
        this.isReportd = isReportd;
    }
    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    public String getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getQuestion() {
        return question_name;
    }

    public void setQuestion(String question) {
        this.question_name = question;
    }

    public String getAnswer1() {
        return option_a;
    }

    public void setAnswer1(String answer1) {
        this.option_a = answer1;
    }

    public String getAnswer2() {
        return option_b;
    }

    public void setAnswer2(String answer2) {
        this.option_b = answer2;
    }

    public String getAnswer3() {
        return option_c;
    }

    public void setAnswer3(String answer3) {
        this.option_c = answer3;
    }

    public String getAnswer4() {
        return option_d;
    }

    public void setAnswer4(String answer4) {
        this.option_d = answer4;
    }

    public String getAnswer5() {
        return option_e;
    }

    public void setAnswer5(String answer5) {
        this.option_e = answer5;
    }


    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getUseranswer() {
        return useranswer;
    }

    public void setUseranswer(String useranswer) {
        this.useranswer = useranswer;
    }


}
