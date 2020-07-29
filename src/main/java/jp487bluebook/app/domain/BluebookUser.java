package jp487bluebook.app.domain;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import javax.persistence.JoinColumn;

@Entity
public class BluebookUser implements Serializable {
	private static final long serialVersionUID = 842075939208392890L;
	public BluebookUser(){
		percentChange = 0.0f;
		oldAverage = 0.0f;
	}
	@Column(name="user_id")
	@Id
	private int id = -1;

	private String username;
	
	private String password;

	private String firstName;
	
	private String lastName;
	
	private String email;

	@OneToMany(mappedBy="class_teacher", cascade=CascadeType.ALL)
	private List<Classes> ownedClasses = new ArrayList<Classes>();

	@ManyToMany
	@JoinTable(
		name = "student_classes", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "class_id"))
	private List<Classes> classes = new ArrayList<Classes>(); 

	@OneToMany(mappedBy="results_owner", cascade=CascadeType.ALL)
	private List<UserResults> previousResults = new ArrayList<UserResults>();

	private int userScore;
	
	@Lob
	private ArrayList<BluebookUser> searchResults = new ArrayList<BluebookUser>();
	
	@Lob
	private HashMap<Integer, String> answeredQuestions = new HashMap<Integer, String>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "user_notifications", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "notif_id"))
	@OrderColumn
	private List<Notification> notifications = new ArrayList<Notification>(); 

	@OneToMany(mappedBy="averageOwner", cascade=CascadeType.ALL)
	private List<ClassResult> classAverages = new ArrayList<ClassResult>();
	
	@ManyToMany
	@JoinTable(
		name = "friendslist",
		joinColumns = @JoinColumn(name="user_id"),
		inverseJoinColumns=@JoinColumn(name="friendId"))
	private List<BluebookUser> friends = new ArrayList<BluebookUser>();

	@ManyToMany
	@JoinTable(
	 name="friendslist",
	 joinColumns=@JoinColumn(name="friendId"),
	 inverseJoinColumns=@JoinColumn(name="user_id"))
	private List<BluebookUser> friendsOf = new ArrayList<BluebookUser>();

	@OneToMany(mappedBy="recepient", cascade = CascadeType.ALL)
	private List<FriendRequest> friendRequests = new ArrayList<FriendRequest>();

	@OneToMany(mappedBy="sender", cascade = CascadeType.ALL)
	private List<FriendRequest> sentRequests = new ArrayList<FriendRequest>();

	@ManyToMany(mappedBy = "members")
	List<Conversation> conversations = new ArrayList<Conversation>();

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private List<Message> sentMessages = new ArrayList<Message>();

	private Boolean completedQuiz;

	private Float percentChange;

	private Float oldAverage;

	public HashMap<Integer, String> getAnsweredQuestions() {
		return answeredQuestions;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<BluebookUser> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(ArrayList<BluebookUser> searchResults) {
		this.searchResults = searchResults;
	}

	public void setAnsweredQuestions(HashMap<Integer, String> answeredQuestions) {
		this.answeredQuestions = answeredQuestions;
	}

	public int getUserScore() {
		return userScore;
	}

	public void setUserScore(int userScore) {
		this.userScore = userScore;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public List<Classes> getOwnedClasses() {
		return ownedClasses;
	}

	public void setOwnedClasses(List<Classes> ownedClasses) {
		this.ownedClasses = ownedClasses;
	}

	public List<Classes> getClasses() {
		return classes;
	}

	public void setClasses(List<Classes> classes) {
		this.classes = classes;
	}

	public List<UserResults> getPreviousResults() {
		return previousResults;
	}

	public void setPreviousResults(List<UserResults> previousResults) {
		this.previousResults = previousResults;
	}



	public List<ClassResult> getClassAverages() {
		return classAverages;
	}

	public void setClassAverages(List<ClassResult> classAverages) {
		this.classAverages = classAverages;
	}

	public Integer classAverageContainsAndGetPos(int classId){
		for(int i = 0; i < classAverages.size(); i++){
			if(classAverages.get(i).getC().getId() == classId){
				return i;
			}
		}
		return null;
	}

	public Boolean getCompletedQuiz() {
		return completedQuiz;
	}

	public void setCompletedQuiz(Boolean completedQuiz) {
		this.completedQuiz = completedQuiz;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void addNotification(Notification a) {
		if(notifications.size() >= 5) notifications.remove(0);
		notifications.add(a);
	}

	public Float getPercentChange() {
		return percentChange;
	}

	public void setPercentChange(Float percentChange) {
		this.percentChange = percentChange;
	}

	public Float getOldAverage() {
		return oldAverage;
	}

	public void setOldAverage(Float oldAverage) {
		this.oldAverage = oldAverage;
	}

	public Boolean deleteUserResultById(int id) {
		for(int i = 0; i < previousResults.size(); i++){
			if((previousResults.get(i).getId() == id) &&  (previousResults.get(i).getImportant() == false)){
				previousResults.get(i).setResults_owner(null);
				previousResults.remove(i);
				return true;
			}
		}
		return false;
	}

	public List<BluebookUser> getFriends() {
		return friends;
	}

	public void setFriends(List<BluebookUser> friends) {
		this.friends = friends;
	}

	public List<BluebookUser> getFriendsOf() {
		return friendsOf;
	}

	public void setFriendsOf(List<BluebookUser> friendsOf) {
		this.friendsOf = friendsOf;
	}

	public void removeFriendById(int id){
		for(int i =0; i < friends.size(); i++){
			if(friends.get(i).getId() == id){
				friends.remove(i);
				break;
			}
		}
	}

	public List<FriendRequest> getFriendRequests() {
		return friendRequests;
	}

	public void setFriendRequests(List<FriendRequest> friendRequests) {
		this.friendRequests = friendRequests;
	}

	public FriendRequest getFriendRequestBySender(String username){
		for(int i = 0; i < friendRequests.size(); i++){
			if(friendRequests.get(i).getSender().getUsername().equals(username)){
				return friendRequests.get(i);
			}
		}
		return null;
	}

	public void removeFriendRequestBySender(String username){
		for(int i = 0; i < friendRequests.size(); i++){
			if(friendRequests.get(i).getSender().getUsername().equals(username)){
				friendRequests.remove(i);
				break;
			}
		}
	}
	public void removeSentRequestByRecpient(String username){
		for(int i = 0; i < sentRequests.size(); i++){
			if(sentRequests.get(i).getRecepient().getUsername().equals(username)){
				sentRequests.remove(i);
				break;
			}
		}
	}

	public List<FriendRequest> getSentRequests() {
		return sentRequests;
	}

	public void setSentRequests(List<FriendRequest> sentRequests) {
		this.sentRequests = sentRequests;
	}

	public List<Conversation> getConversations() {
		return conversations;
	}

	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}

	public List<Message> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

}
