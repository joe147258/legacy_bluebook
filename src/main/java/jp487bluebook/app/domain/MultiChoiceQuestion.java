package jp487bluebook.app.domain;

import java.util.ArrayList;
import java.util.Collections;

import javax.persistence.Entity;

@Entity
public class MultiChoiceQuestion extends Questions {

	ArrayList<String> AnswerList = new ArrayList<>();
	
	public MultiChoiceQuestion() {
		
	}
	
	public void shuffle() {
		Collections.shuffle(AnswerList);
	}
	
	public String getAnswer(int i) {
		if (!((i < 0) || i > AnswerList.size())) return AnswerList.get(i);
		else {
			System.out.println("getAnswer ERROR! out of bounds");
			return "ERROR: out of bounds";
		}
	}
	
	public ArrayList<String> getAnswerList() {
		return AnswerList;
	}
	
	public void addAnswer(String s) {
		AnswerList.add(s);
	}
	
	public MultiChoiceQuestion(int id, String question, Quizzes quiz) {
		super(id, question, quiz);
		this.id = id;
		this.questionString = question;
		this.quiz = quiz;
	}




	


}
