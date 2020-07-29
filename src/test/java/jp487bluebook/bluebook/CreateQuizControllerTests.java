package jp487bluebook.bluebook;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jp487bluebook.app.WebConfig;
import jp487bluebook.app.controller.CreateQuizController;
import jp487bluebook.app.controller.PlayQuizController;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.BoolQuestion;
import jp487bluebook.app.domain.Classes;
import jp487bluebook.app.domain.InputQuestion;
import jp487bluebook.app.domain.MultiChoiceQuestion;
import jp487bluebook.app.domain.Quizzes;
import jp487bluebook.app.repository.AnnouncementRepository;
import jp487bluebook.app.repository.ClassAverageRepository;
import jp487bluebook.app.repository.ClassQuizAverageRepository;
import jp487bluebook.app.repository.ClassesRepository;
import jp487bluebook.app.repository.NotificationRepository;
import jp487bluebook.app.repository.QuestionRepository;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;
import jp487bluebook.app.repository.UserResultsRepository;
import jp487bluebook.app.service.NotificationService;
import jp487bluebook.app.service.TimerService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={CreateQuizController.class, TestSecurityConfig.class, WebConfig.class})
@AutoConfigureMockMvc
public class CreateQuizControllerTests {
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
	AnnouncementRepository annRepo;
	@MockBean
	NotificationRepository notifRepo;
	@MockBean
    NotificationService notifService;
    @MockBean
    TimerService timeService;
    

    @Autowired
    WebConfig webConfig;

	@InjectMocks
    private PlayQuizController playQuizControllerTester;
    
    @Before
	public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        Quizzes q = new Quizzes();
		q.setName("European Knowledge");
		q.setQuestionAmount(6);
		q.setId(1);
		q.setIsPublic(true);
		q.setActive(true);
        q.setOwnerId(-1);
        q.setHidden(false);
        quizRepo.save(q);
        MultiChoiceQuestion question = new MultiChoiceQuestion(1, "What is the capital of France?", q);
		//sets up answers 
		question.addAnswer("Paris");
		question.addAnswer("Madrid");
		question.addAnswer("Amsterdam");
		question.addAnswer("Antwerp");
		question.setCorrectAnswer(question.getAnswer(0));
		question.shuffle();

		MultiChoiceQuestion question1 = new MultiChoiceQuestion(2, "What is the capital of Ukraine?", q);
		//sets up answers 
		question1.addAnswer("Kiev");
		question1.addAnswer("Minsk");
		question1.addAnswer("Moscow");
		question1.addAnswer("Crimea");
		question1.setCorrectAnswer(question1.getAnswer(0));
		question1.shuffle();
		
		MultiChoiceQuestion question2 = new MultiChoiceQuestion(3, "What is the capital of Sweden?", q);

		question2.addAnswer("Stockholm");
		question2.addAnswer("Oslo");
		question2.addAnswer("Helsinki");
		question2.addAnswer("Gotenburg");
		question2.setCorrectAnswer(question2.getAnswer(0));
		question2.shuffle();
		
		BoolQuestion question3 = new BoolQuestion(4, "Norway is in the European Union.", q);
		//sets up answers
		question3.setCorrectAnswer("False");
		
		InputQuestion question4 = new InputQuestion(5, "Which German speaking country remained neutral during WW2?", q);
		//sets up answers
		question4.setCorrectAnswer("Switerzland");
		
		MultiChoiceQuestion question5 = new MultiChoiceQuestion(6, "What is the capital of Latvia?", q);
		//sets up answers
		question5.addAnswer("Riga");
		question5.addAnswer("Vilnius");
		question5.addAnswer("Tallinn");
		question5.setCorrectAnswer(question5.getAnswer(0));
		question5.shuffle();
		

		//saves answer to question array list
		//adds the answer to quiz's question array list
		q.addQuestion(question);
		q.addQuestion(question1);
		q.addQuestion(question2);
		q.addQuestion(question3);
		q.addQuestion(question4);
        q.addQuestion(question5);

        when(quizRepo.findById(1)).thenReturn(q);
        BluebookUser user = new BluebookUser();

        user.setUsername("VergeofEden");
		user.setFirstName("Ben");
		user.setLastName("Harold");
		user.setEmail("bob@bob.com");
		user.setId(-1);
		user.setUserScore(0);
        user.setPassword("test");
        userRepo.save(user);
        when(userRepo.findByUsername("VergeofEden")).thenReturn(user);
        when(userRepo.findById(-1)).thenReturn(user);

    }

    @Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
	public void mainTests() throws Exception {


		mockMvc.perform(
            MockMvcRequestBuilders.get("/create-quiz")

        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("create-quiz"));

        

        
    }

    @Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
	public void addingAndEditingQuestion() throws Exception {


		mockMvc.perform(
            MockMvcRequestBuilders.get("/quiz-add-question/1?isPublic=true")

        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("add-question-page"));

        
        mockMvc.perform(
            MockMvcRequestBuilders.get("/quiz-edit-question/1")

        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("add-question-page"));

        
    }
    @Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
	public void submitQuiz() throws Exception {
        //public quiz
        mockMvc.perform(
            MockMvcRequestBuilders.get("/submit-pub-quiz/1?visibilityOption=everyone")

        )
        .andExpect(MockMvcResultMatchers.status().is(302));
        mockMvc.perform(
            MockMvcRequestBuilders.get("/submit-pub-quiz/1?visibilityOption=private")

        )
        .andExpect(MockMvcResultMatchers.status().is(302));

        //submitting class quiz
        Classes c = new Classes();
		c.setName("Test AP 1");
		c.setConvenor("Dr. A. Tester");
		c.setDesc("test");
		c.setId(1);
        c.genClassCode();
        
        Quizzes q1 = new Quizzes();
		q1.setName("European Knowledge");
		q1.setQuestionAmount(6);
		q1.setId(2);
		q1.setIsPublic(false); //not open for public
		q1.setActive(true);
        q1.setOwnerId(1);
        q1.setHidden(false);
        q1.setBb_class(c);
        Date dueDate = new Date();
        dueDate = DateUtils.addMinutes(dueDate, 20);
        q1.setDueDate(dueDate);
        quizRepo.save(q1);
        when(quizRepo.findById(2)).thenReturn(q1);
        //submit private quiz
        mockMvc.perform(
            MockMvcRequestBuilders.get("/submit-quiz/2?visibilityOption=private")

        )
        .andExpect(MockMvcResultMatchers.status().is(302));
        //submit public quiz
        mockMvc.perform(
            MockMvcRequestBuilders.get("/submit-quiz/2?visibilityOption=everyone")

        )
        .andExpect(MockMvcResultMatchers.status().is(302));
        //submit classroom quiz
        mockMvc.perform(
            MockMvcRequestBuilders.get("/submit-quiz/2?visibilityOption=class")

        )
        .andExpect(MockMvcResultMatchers.status().is(302));
        //submit scheduled for class quiz
        mockMvc.perform(
            MockMvcRequestBuilders.get("/submit-quiz/2?dateString=2021-05-15 04:04&visibilityOption=schedule&schedVis=class")

        )
        .andExpect(MockMvcResultMatchers.status().is(302));
        //submit sheduled for everyone quiz
        mockMvc.perform(
            MockMvcRequestBuilders.get("/submit-quiz/2?dateString=2021-05-15 04:04&visibilityOption=schedule&schedVis=everyone")

        )
        .andExpect(MockMvcResultMatchers.status().is(302));
        //before due date scheduled quiz
        mockMvc.perform(
            MockMvcRequestBuilders.get("/submit-quiz/2?dateString=2019-05-15 04:04&visibilityOption=schedule&schedVis=everyone")

        )
        .andExpect(MockMvcResultMatchers.status().is(302));

        
    }
    
}