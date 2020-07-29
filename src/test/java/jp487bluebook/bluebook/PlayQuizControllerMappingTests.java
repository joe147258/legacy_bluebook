package jp487bluebook.bluebook;

import static org.mockito.Mockito.when;

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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.Date;

import jp487bluebook.app.WebConfig;
import jp487bluebook.app.controller.PlayQuizController;

import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.ClassQuizAverage;
import jp487bluebook.app.domain.ClassResult;
import jp487bluebook.app.domain.Classes;
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
@SpringBootTest(classes={PlayQuizController.class, TestSecurityConfig.class, WebConfig.class})
@AutoConfigureMockMvc
public class PlayQuizControllerMappingTests {
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
        //mocked class
		Classes c = new Classes();
		c.setName("Test AP 1");
		c.setConvenor("Dr. A. Tester");
		c.setDesc("test");
		c.setId(1);
		c.genClassCode();

		c.getStudents().add(user);
		user.getClasses().add(c);
		//saving mocked quiz and class
		classRepo.save(c);
        userRepo.save(user);
        
        //sets up mocked PUBLIC quiz
        //WITH: hidden = false 
        //WITH: active = true
        //with: isPublic = true
        //these three variables are changed throughout
		Quizzes q = new Quizzes();
		q.setName("European Knowledge");
		q.setQuestionAmount(6);
		q.setId(1);
		q.setIsPublic(true);
		q.setActive(true);
        q.setOwnerId(1);
        q.setHidden(false);
        quizRepo.save(q);

        when(userRepo.findByUsername("VergeofEden")).thenReturn(user);
        when(quizRepo.findById(1)).thenReturn(q);
        //new private class quiz
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

        ClassQuizAverage cqa = new ClassQuizAverage(-1, 0f, q1.getId());
        cqaRepo.save(cqa);

        when(cqaRepo.findByQuizId(2)).thenReturn(cqa);
    }
    
    @Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
	public void startingPublicQuiz() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.get("/start-quiz?quizId=1")
        )
        .andExpect(MockMvcResultMatchers.view().name("start-quiz"))
        .andExpect(MockMvcResultMatchers.status().isOk());
        //Now testing if when getCompleted == true
        Quizzes q = quizRepo.findById(1);
        q.setCompleted(true);
        quizRepo.save(q);
        mockMvc.perform(
			MockMvcRequestBuilders.get("/start-quiz?quizId=1")
        )
        .andExpect(MockMvcResultMatchers.view().name("start-quiz"))
        .andExpect(MockMvcResultMatchers.status().isOk());
        //now testing if the quiz is hidden
        q.setHidden(true);
        quizRepo.save(q);
        mockMvc.perform(
			MockMvcRequestBuilders.get("/start-quiz?quizId=1")
        )
        .andExpect(MockMvcResultMatchers.status().is(302));
    }

    @Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
	public void startingAClassQuiz() throws Exception {
        //WITH: hidden = false
        //WITH: active = true
        //with: isPublic = false
        //WITH: has a class
        //The user has not completed it before
		mockMvc.perform(
			MockMvcRequestBuilders.get("/start-quiz?quizId=2")
        )
        .andExpect(MockMvcResultMatchers.view().name("start-quiz"))
        .andExpect(MockMvcResultMatchers.status().isOk());

        //Now the user with cleared previous result
        //This scenario is when a user's first time 
        Quizzes q1 = quizRepo.findById(2);
        BluebookUser u = userRepo.findByUsername("VergeofEden");
        u.getPreviousResults().remove(0);
        ClassResult cr1 = new ClassResult((float)0.0, 1, q1.getName());
        cr1.setAverageOwner(u);
        cr1.setC(q1.getBb_class());
        crRepo.save(cr1);
        u.getClassAverages().add(cr1);

        quizRepo.save(q1);
        userRepo.save(u);

        mockMvc.perform(
			MockMvcRequestBuilders.get("/start-quiz?quizId=2")
        )
        .andExpect(MockMvcResultMatchers.view().name("start-quiz"))
        .andExpect(MockMvcResultMatchers.status().isOk());

        //Now if a user has completed it before
        //thefore an average score for class has been made
        ClassResult cr = new ClassResult((float)0.0, 1, q1.getName());
        cr.setAverageOwner(u);
        cr.setC(q1.getBb_class());
        crRepo.save(cr);
        u.getClassAverages().add(cr);

        quizRepo.save(q1);
        userRepo.save(u);

        mockMvc.perform(
			MockMvcRequestBuilders.get("/start-quiz?quizId=2")
        )
        .andExpect(MockMvcResultMatchers.view().name("start-quiz"))
        .andExpect(MockMvcResultMatchers.status().isOk());
        //No class average has been made for the class
        //(cqa) 
        when(cqaRepo.findByQuizId(2)).thenReturn(null);
        mockMvc.perform(
			MockMvcRequestBuilders.get("/start-quiz?quizId=2")
        )
        .andExpect(MockMvcResultMatchers.view().name("start-quiz"))
        .andExpect(MockMvcResultMatchers.status().isOk());

        //MOCK test with late submission
        q1.setActive(false);
        q1.setDueDate(new Date()); //sets due date to right now, so will fail by time is run
        quizRepo.save(q1);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/start-quiz?quizId=2")
        )
        .andExpect(MockMvcResultMatchers.view().name("start-quiz"))
        .andExpect(MockMvcResultMatchers.status().isOk());  
    }
    
  

    @Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
	public void submitQuiz() throws Exception {
        //quiz without a class
        mockMvc.perform(
			MockMvcRequestBuilders.get("/start-quiz?quizId=1")
        );
        mockMvc.perform(
			MockMvcRequestBuilders.get("/submit-quiz?quizId=1")
        )
        .andExpect(MockMvcResultMatchers.status().is(302));
        //quiz with a class
        mockMvc.perform(
			MockMvcRequestBuilders.get("/start-quiz?quizId=2")
        );

        mockMvc.perform(
			MockMvcRequestBuilders.get("/submit-quiz?quizId=2")
        )
        .andExpect(MockMvcResultMatchers.status().is(302));


	}
}