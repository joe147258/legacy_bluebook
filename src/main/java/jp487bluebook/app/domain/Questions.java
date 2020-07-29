package jp487bluebook.app.domain;



import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public abstract class Questions {
	@Id
	protected int id = -1;
	protected String questionString;
	protected String CorrectAnswer;
	
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    protected Quizzes quiz;
    
	public Questions() {
		
	}
	
	public Questions(int id, String question, Quizzes quiz) {
		this.id = id;
		this.questionString = question;
		this.quiz = quiz;
	}

	public String getQuestionString() {
		return questionString;
	}

	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}

	public Quizzes getQuiz() {
		return quiz;
	}

	public void setQuiz(Quizzes quiz) {
		this.quiz = quiz;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorrectAnswer() {
		return CorrectAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		CorrectAnswer = correctAnswer;
	}

	public abstract void shuffle();

	public abstract ArrayList<String> getAnswerList();

	public abstract String getAnswer(int i);




}
