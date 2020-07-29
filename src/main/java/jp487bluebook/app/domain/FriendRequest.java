package jp487bluebook.app.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FriendRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 170688652789946819L;

    public FriendRequest(int id, BluebookUser sender, BluebookUser recepient) {
        this.sender = sender;
        this.recepient = recepient;
        this.id = id;
    }
    public FriendRequest(){

    }
    
    @Id
    int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recepient_id")
    private BluebookUser recepient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private BluebookUser sender;

    public BluebookUser getRecepient() {
        return recepient;
    }

    public void setRecepient(BluebookUser recepient) {
        this.recepient = recepient;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BluebookUser getSender() {
        return sender;
    }

    public void setSender(BluebookUser sender) {
        this.sender = sender;
    }

}