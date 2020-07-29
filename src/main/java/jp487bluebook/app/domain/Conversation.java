package jp487bluebook.app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;



@Entity
public class Conversation {

    @Id
    @Column(name = "convo_id")
    private int id;
    
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "user_conversations", 
		joinColumns = @JoinColumn(name = "convo_id"), 
		inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<BluebookUser> members = new ArrayList<BluebookUser>();

    @OneToMany(mappedBy = "convo", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<Message>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id", referencedColumnName = "message_id")
    private Message latestMessage;

    public Conversation(int id, BluebookUser user1, BluebookUser user2, Message message){
        this.id = id;
        members.add(user1);
        members.add(user2);
        messages.add(message);
        this.latestMessage = message;
    }
    public Conversation(){

    }
    public List<BluebookUser> getMembers() {
        return members;
    }

    public void setMembers(List<BluebookUser> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Message getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(Message latestMessage) {
        this.latestMessage = latestMessage;
    }
}