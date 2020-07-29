package jp487bluebook.app.domain;

import java.util.ArrayList;


import javax.persistence.Entity;

@Entity
public class InputQuestion extends Questions {
	
	public InputQuestion() {
		super();
	}
	public InputQuestion(int id, String question, Quizzes quiz) {
		super();
		this.id = id;
		this.questionString = question;
		this.quiz = quiz;
	}

	@Override
	public void shuffle() {

		
	}

	@Override
	public ArrayList<String> getAnswerList() {

		return null;
	}

	@Override
	public String getAnswer(int i) {

		return null;
	}

}
