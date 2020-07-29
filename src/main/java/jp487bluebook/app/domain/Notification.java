package jp487bluebook.app.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;



@Entity
public class Notification {

    @Column(name = "notif_id")
    @Id
    private int id;
    
    private Date date;

    private String contents;

    private String type;

    private int quizId;
    
    private String quizName;
    
    private int quizQuestionAmount;

    private int annId;

    private String attachedUsername;
    


    @ManyToMany(mappedBy = "notifications")
	private List<BluebookUser> recipients = new ArrayList<BluebookUser>();

    public Notification(){

    }

    public Notification(int id, String contents, String type){
        this.id = id;
        this.date = new Date();
        this.contents = contents;
        this.type = type;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public List<BluebookUser> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<BluebookUser> recipients) {
        this.recipients = recipients;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getQuizQuestionAmount() {
        return quizQuestionAmount;
    }

    public void setQuizQuestionAmount(int quizQuestionAmount) {
        this.quizQuestionAmount = quizQuestionAmount;
    }

    public int getAnnId() {
        return annId;
    }

    public void setAnnId(int annId) {
        this.annId = annId;
    }

    public String getAttachedUsername() {
        return attachedUsername;
    }

    public void setAttachedUsername(String attachedUsername) {
        this.attachedUsername = attachedUsername;
    }
}