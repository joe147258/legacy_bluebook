package jp487bluebook.bluebook;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jp487bluebook.app.WebConfig;
import jp487bluebook.app.controller.PlayQuizController;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.BoolQuestion;
import jp487bluebook.app.domain.InputQuestion;
import jp487bluebook.app.domain.MultiChoiceQuestion;
import jp487bluebook.app.domain.Quizzes;
import jp487bluebook.app.repository.ClassAverageRepository;
import jp487bluebook.app.repository.ClassQuizAverageRepository;
import jp487bluebook.app.repository.ClassesRepository;
import jp487bluebook.app.repository.NotificationRepository;
import jp487bluebook.app.repository.QuestionRepository;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;
import jp487bluebook.app.repository.UserResultsRepository;
import jp487bluebook.app.service.NotificationService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={PlayQuizController.class, TestSecurityConfig.class, WebConfig.class, HashMap.class})
@AutoConfigureMockMvc(addFilters = false)
public class PlayQuizRESTTests {
    @Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	QuizRepository quizRepo;

	@MockBean
	ClassesRepository classRepo;

	@MockBean
    UserRepository userRepo;

    @MockBean
    QuestionRepository quesRepo;

    @MockBean
    UserResultsRepository urRepo;
	@MockBean
	ClassAverageRepository crRepo;
	@MockBean
	ClassQuizAverageRepository cqaRepo;
	@MockBean
	NotificationRepository notifRepo;
	@MockBean
    NotificationService notifService;

    @Autowired
	WebConfig webConfig;
	

	
	@InjectMocks
    private PlayQuizController playQuizControllerTester;
    @Before
	public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        //sets up mocked user
		BluebookUser user = new BluebookUser();
		user.setUsername("VergeofEden");
		user.setFirstName("Ben");
		user.setLastName("Harold");
		user.setEmail("bob@bob.com");
		user.setId(-1);
		user.setUserScore(0);
        user.setPassword("test");
        when(userRepo.findByUsername("VergeofEden")).thenReturn(user);

        //mock quiz
		Quizzes quiz = new Quizzes();
		quiz.setName("European Knowledge");
		quiz.setQuestionAmount(6);
		quiz.setId(1);
		quiz.setIsPublic(true);
		quiz.setActive(true);
        quiz.setOwnerId(1);
        quiz.setHidden(false);
        MultiChoiceQuestion question = new MultiChoiceQuestion(1, "What is the capital of France?", quiz);
		//sets up answers 
		question.addAnswer("Paris");
		question.addAnswer("Madrid");
		question.addAnswer("Amsterdam");
		question.addAnswer("Antwerp");
		question.setCorrectAnswer(question.getAnswer(0));
		question.shuffle();

		MultiChoiceQuestion question1 = new MultiChoiceQuestion(2, "What is the capital of Ukraine?", quiz);
		//sets up answers 
		question1.addAnswer("Kiev");
		question1.addAnswer("Minsk");
		question1.addAnswer("Moscow");
		question1.addAnswer("Crimea");
		question1.setCorrectAnswer(question1.getAnswer(0));
		question1.shuffle();
		
		MultiChoiceQuestion question2 = new MultiChoiceQuestion(3, "What is the capital of Sweden?", quiz);

		question2.addAnswer("Stockholm");
		question2.addAnswer("Oslo");
		question2.addAnswer("Helsinki");
		question2.addAnswer("Gotenburg");
		question2.setCorrectAnswer(question2.getAnswer(0));
		question2.shuffle();
		
		BoolQuestion question3 = new BoolQuestion(4, "Norway is in the European Union.", quiz);
		//sets up answers
		question3.setCorrectAnswer("False");
		
		InputQuestion question4 = new InputQuestion(5, "Which German speaking country remained neutral during WW2?", quiz);
		//sets up answers
		question4.setCorrectAnswer("Switerzland");
		
		MultiChoiceQuestion question5 = new MultiChoiceQuestion(6, "What is the capital of Latvia?", quiz);
		//sets up answers
		question5.addAnswer("Riga");
		question5.addAnswer("Vilnius");
		question5.addAnswer("Tallinn");
		question5.setCorrectAnswer(question5.getAnswer(0));
		question5.shuffle();
		

		//saves answer to question array list
		//adds the answer to quiz's question array list
		quiz.addQuestion(question);
		quiz.addQuestion(question1);
		quiz.addQuestion(question2);
		quiz.addQuestion(question3);
		quiz.addQuestion(question4);
        quiz.addQuestion(question5);
        quizRepo.save(quiz);
		when(quizRepo.findById(1)).thenReturn(quiz);


    }
   
    @Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
    public void checkQuestion() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.get("/getQues?quizId=1&qNo=0")
		).andReturn().getResponse();
		mockMvc.perform(
			MockMvcRequestBuilders.get("/getQues?quizId=1&qNo=3")
		).andReturn().getResponse();
		mockMvc.perform(
			MockMvcRequestBuilders.get("/getQues?quizId=1&qNo=4")
		).andReturn().getResponse();
		//these arent working?
		//comment out before submitting
		BluebookUser u = userRepo.findByUsername("VergeofEden");
		u.getAnsweredQuestions().put(0, "test");
		u.getAnsweredQuestions().put(1, "kiev");
		when(userRepo.findByUsername("VergeofEden")).thenReturn(u);
		mockMvc.perform(
			MockMvcRequestBuilders.get("/getQues?quizId=1&qNo=0")
		).andReturn().getResponse();
		mockMvc.perform(
			MockMvcRequestBuilders.get("/getQues?quizId=1&qNo=1")
		).andReturn().getResponse();
		//-----

		

			
        

	}

}