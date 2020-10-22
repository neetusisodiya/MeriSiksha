package com.muravtech.meri_siksha.onlineexam.models;

import org.json.JSONArray;

/**
 * Created by recon on 12/17/2016.
 */

public class QuestionAnswerStartExamModel {
    String examType;
    String answerType;
    String qId;
    String questionStatus;
    String direction;
    String question;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    String answer5="";
    String answer6;
    String question_mark;

    String subjectName;
    String totalSubject;
    String correctAnswer;
    String customQueId;
    JSONArray myExamSubjects;
    String isReportd;
    String isAnswered;
    String questiontype;
    String unit;
    String userAnswer;
    String subjectID;

    public String getAnswer6() {
        return answer6;
    }

    public void setAnswer6(String answer6) {
        this.answer6 = answer6;
    }



    public String getUserAnswer() {
        return userAnswer;
    }
    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }


    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }



    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getIsReportd() {
        return isReportd;
    }

    public void setIsReportd(String isReportd) {
        this.isReportd = isReportd;
    }

    public JSONArray getMyExamSubjects() {
        return myExamSubjects;
    }

    public void setMyExamSubjects(JSONArray myExamSubjects) {
        this.myExamSubjects = myExamSubjects;
    }

    public String getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(String isAnswered) {
        this.isAnswered = isAnswered;
    }

    public String getCustomQueId() {
        return customQueId;
    }

    public void setCustomQueId(String customQueId) {
        this.customQueId = customQueId;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public String getIsAttemptType() {
        return isAttemptType;
    }

    public void setIsAttemptType(String isAttemptType) {
        this.isAttemptType = isAttemptType;
    }

    String isAttemptType;
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }


    public String getQuestion_mark() {
        return question_mark;
    }

    public void setQuestion_mark(String question_mark) {
        this.question_mark = question_mark;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTotalSubject() {
        return totalSubject;
    }

    public void setTotalSubject(String totalSubject) {
        this.totalSubject = totalSubject;
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
