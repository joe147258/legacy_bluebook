package jp487bluebook.app.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserResults implements Serializable{

    private static final long serialVersionUID = 501845659937812563L;
    @Id
    @Column(name="result_id")
    private int id;
    private int quizId;
    private int questionAmount; 
    private int userScore;
    private String quizName;
    Boolean important;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "results_owner")
    private BluebookUser results_owner;

    private Date date;

    public UserResults(int quizId, int questionAmount, int userScore, String quizName, int id){
        this.quizId = quizId;
        this.questionAmount = questionAmount;
        this.userScore = userScore;
        this.date = new Date();
        this.quizName = quizName;
        this.id = id;
        this.important = false;
    }

    public UserResults(){

    }
    
    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public Date getDate() {
        return date;
    }
    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy - HH:mm");
        return sdf.format(date);
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BluebookUser getResults_owner() {
        return results_owner;
    }

    public void setResults_owner(BluebookUser user) {
        this.results_owner = user;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }
    

}