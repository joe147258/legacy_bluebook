package jp487bluebook.bluebook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import org.springframework.boot.test.context.SpringBootTest;


import jp487bluebook.app.domain.BoolQuestion;
import jp487bluebook.app.domain.InputQuestion;
import jp487bluebook.app.domain.MultiChoiceQuestion;
import jp487bluebook.app.domain.Quizzes;
import jp487bluebook.app.utilities.Levenshtein;


@SpringBootTest
public class BluebookApplicationTests {
	

	@Test
	public void exampleTest() {
	 assertTrue(true);
	}
	
	Quizzes quiz1;
	BoolQuestion questions1;
	MultiChoiceQuestion questions2;
	InputQuestion questions3;
	
	@Before
	public void initialise() {
		//Initially creating the quiz\\
		quiz1 = new Quizzes();
		quiz1.setName("Unit Test Test Quiz!");
		quiz1.setId(1);
		quiz1.setQuestionAmount(3);
		//first question\\
		questions1 = new BoolQuestion();
		questions1.setCorrectAnswer("True");
		questions1.setId(1);
		questions1.setQuestionString("Test bool, ans: true");
		questions1.setQuiz(quiz1);
		
		questions2 = new MultiChoiceQuestion();
		questions2.setCorrectAnswer("yellow");
		questions2.setId(2);
		questions2.setQuestionString("Test multi, ans: yellow");
		questions2.setQuiz(quiz1);
		
		questions3 = new InputQuestion();
		questions3.setCorrectAnswer("Answer");
		questions3.setId(3);
		questions3.setQuestionString("Test multi, ans: testt");
		questions3.setQuiz(quiz1);
		
		quiz1.addQuestion(questions1);
		quiz1.addQuestion(questions2);
		quiz1.addQuestion(questions3);
	}
	@Test
	public void checkQuestionsInQuiz() {
		assertEquals(quiz1.getQuestions().get(0).getCorrectAnswer(), "True");
		assertEquals(quiz1.getQuestions().get(1).getCorrectAnswer(), "yellow");
		assertEquals(quiz1.getQuestions().get(2).getCorrectAnswer(), "Answer");
	}
	@Test
	public void testArraySize() {
		assertEquals(quiz1.getQuestions().size(), 3);
		assertEquals(quiz1.getQuestionAmount(), 3);
		quiz1.DeleteQuestionLastQues();
		assertEquals(quiz1.getQuestions().size(), 2);
		assertEquals(quiz1.getQuestionAmount(), 2);
	}
	
	@Test
	public void checkLevinshtein() {
		int i = Levenshtein.calculate("Answer", quiz1.getQuestions().get(2).getCorrectAnswer());
		assertEquals(i, 0);
		int j = Levenshtein.calculate("Answe", quiz1.getQuestions().get(2).getCorrectAnswer());
		assertEquals(j, 1);
		int k = Levenshtein.calculate("Answerss", quiz1.getQuestions().get(2).getCorrectAnswer());
		assertEquals(k, 2);
		int l = Levenshtein.calculate("lame", quiz1.getQuestions().get(2).getCorrectAnswer());
		assertEquals(l, 5);
	}
	
}

