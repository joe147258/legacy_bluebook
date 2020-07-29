package jp487bluebook.bluebook;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jp487bluebook.app.WebConfig;
import jp487bluebook.app.controller.FriendsController;
import jp487bluebook.app.controller.PlayQuizController;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.repository.AnnouncementRepository;
import jp487bluebook.app.repository.ClassAverageRepository;
import jp487bluebook.app.repository.ClassQuizAverageRepository;
import jp487bluebook.app.repository.ClassesRepository;
import jp487bluebook.app.repository.ConversationRepository;
import jp487bluebook.app.repository.FriendRequestRepository;
import jp487bluebook.app.repository.MessageRepository;
import jp487bluebook.app.repository.NotificationRepository;
import jp487bluebook.app.repository.QuestionRepository;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;
import jp487bluebook.app.repository.UserResultsRepository;
import jp487bluebook.app.service.NotificationService;
import jp487bluebook.app.service.TimerService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={FriendsController.class, TestSecurityConfig.class, WebConfig.class})
@AutoConfigureMockMvc
public class SocialControllerTests {
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
    FriendRequestRepository frRepo;
    @MockBean
    MessageRepository msgRepo;
    @MockBean
	ConversationRepository convoRepo;
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

        BluebookUser user1 = new BluebookUser();
        user1.setUsername("joe147258");
		user1.setFirstName("Benney");
		user1.setLastName("Haroldey");
		user1.setEmail("joe@bob.com");
		user1.setId(-2);
		user1.setUserScore(0);
        user1.setPassword("test");
        userRepo.save(user);
        when(userRepo.findByUsername("joe147258")).thenReturn(user1);


    }

    @Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
	public void originalMapping() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/friends-page/")

        )
        .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
            MockMvcRequestBuilders.get("/send-request/VergeofEden")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
            MockMvcRequestBuilders.get("/send-request/joe147258")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
            MockMvcRequestBuilders.get("/send-request/joe147258")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk());
        //acept friend
        mockMvc.perform(
            MockMvcRequestBuilders.get("/accept-friend?sender=joe147258")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk());
        //deny friend
        mockMvc.perform(
            MockMvcRequestBuilders.get("/deny-friend?sender=joe147258")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk());
     }
}