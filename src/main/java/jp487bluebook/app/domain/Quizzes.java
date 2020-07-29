package jp487bluebook.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Quizzes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2166094133497509437L;
	@Id
	@Column(name="quiz_id")
	private int id = -1;;
	private String name;
	private int questionAmount;
	Boolean isPublic;
	private Boolean scheduled;
	private Date scheduledFor;

	private int ownerId;
	
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private Classes bb_class;
	
	@OneToMany(mappedBy = "quiz", cascade=CascadeType.ALL)
	private List <Questions> questions = new ArrayList<>();
	
	private Boolean active;

	private Date dueDate;

	private Boolean hidden;

	private Boolean completed;

	
	public Quizzes() {
		this.hidden = true;
		this.scheduled = false;
		completed = false;
	}
	public Quizzes(int id, String name, List<Questions> questionList) {
		this.id = id;
		this.name = name;
		this.questions = questionList;
		this.hidden = true;
		this.scheduled = false;
		completed = false;
	}
	
	public Boolean getIsPublic() {
		return isPublic;
	}


	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}


	public int getOwnerId() {
		return ownerId;
	}


	public void setOwnerId(int owner_id) {
		this.ownerId = owner_id;
	}





	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addQuestion(Questions question) {
		questions.add(question);
	}
	
	public void DeleteQuestionLastQues() {
		questions.remove(questions.size() - 1);
		setQuestionAmount(getQuestionAmount() - 1);
	}
	
	public int getQuestionAmount() {
		return questionAmount;
	}


	public void setQuestionAmount(int questionAmount) {
		this.questionAmount = questionAmount;
	}


	public List<Questions> getQuestions() {
		return questions;
	}


	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Classes getBb_class() {
		return bb_class;
	}

	public void setBb_class(Classes bb_class) {
		this.bb_class = bb_class;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Boolean getScheduled() {
		return scheduled;
	}

	public void setScheduled(Boolean scheduled) {
		this.scheduled = scheduled;
	}

	public Date getScheduledFor() {
		return scheduledFor;
	}

	public void setScheduledFor(Date scheduledFor) {
		this.scheduledFor = scheduledFor;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}




}
