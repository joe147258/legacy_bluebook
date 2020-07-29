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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jp487bluebook.app.WebConfig;
import jp487bluebook.app.controller.PlayQuizController;
import jp487bluebook.app.controller.ProfileController;
import jp487bluebook.app.domain.BluebookUser;
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
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={ProfileController.class, TestSecurityConfig.class, WebConfig.class, HashMap.class})
@AutoConfigureMockMvc(addFilters = false)
public class ProfileControllerTests {
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
    AnnouncementRepository annRepo;
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
		BluebookUser user1 = new BluebookUser();
		user1.setUsername("bobby1");
		user1.setFirstName("Benen");
		user1.setLastName("Hareld");
		user1.setEmail("bob1@bob.com");
		user1.setId(1);
        user1.setPassword("test");
        when(userRepo.findById(1)).thenReturn(user1);



    }
   
    @Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
    public void profilePageMappings() throws Exception {
		//view-profile
		mockMvc.perform(
            MockMvcRequestBuilders.get("/view-profile?id=1")

		)
		.andExpect(MockMvcResultMatchers.view().name("view-profile"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		//redirect
		mockMvc.perform(
            MockMvcRequestBuilders.get("/view-profile?id=-1")

		)
		.andExpect(MockMvcResultMatchers.status().is(302));
		//my-profile
		mockMvc.perform(
            MockMvcRequestBuilders.get("/my-profile")

		)
		.andExpect(MockMvcResultMatchers.status().isOk());
			
        

	}

}