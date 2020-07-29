package jp487bluebook.app.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Message {

    @Id
    @Column(name = "message_id")
    private int id;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "sender_id")
    private BluebookUser sender;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "convo_id")
    private Conversation convo;
    
    @Lob
    private String content;

    private Date date;
    public Message(){

    }
    
    public Message(int id, BluebookUser sender,  String content){
        this.id = id;
        this.sender = sender;
        this.content = content;
        date = new Date();
    }

    public BluebookUser getSender() {
        return sender;
    }

    public void setSender(BluebookUser sender) {
        this.sender = sender;
    }

    public Conversation getConvo() {
        return convo;
    }

    public void setConvo(Conversation convo) {
        this.convo = convo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }
    public String getDateChat() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM | dd-MM-yyyy");
        return sdf.format(date);
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
}