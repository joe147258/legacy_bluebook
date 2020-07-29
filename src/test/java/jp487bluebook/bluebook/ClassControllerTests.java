package jp487bluebook.bluebook;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import jp487bluebook.app.controller.ClassController;
import jp487bluebook.app.controller.PlayQuizController;
import jp487bluebook.app.domain.Announcement;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.Classes;
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
@SpringBootTest(classes={ClassController.class, TestSecurityConfig.class, WebConfig.class})
@AutoConfigureMockMvc
public class ClassControllerTests {
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

        Classes c = new Classes();
		c.setName("Test AP 1");
		c.setConvenor("Dr. A. Tester");
		c.setDesc("test");
		c.setId(1);
        c.genClassCode();
        c.getStudents().add(user);
        c.setClass_teacher(user);
        Announcement a = new Announcement("Essay due in 3 days", "Remember to complete your essay about America's involvement in Cuba.", 1);
        a.setBb_class_ann(c);
        c.getAnnouncements().add(a);
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
        c.addQuizzes(q1);
        when(quizRepo.findById(2)).thenReturn(q1);

        when(classRepo.findById(1)).thenReturn(c);


    }

    @Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
	public void originalMapping() throws Exception {

        //create a class page
		mockMvc.perform(
            MockMvcRequestBuilders.get("/create-class")

        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("create-class"));
        //view classes
        mockMvc.perform(
            MockMvcRequestBuilders.get("/view-classes")

        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("view-classes"));
        //student class
        mockMvc.perform(
            MockMvcRequestBuilders.get("/student-class/1")

        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("student-class"));
        //teacher-class
        mockMvc.perform(
            MockMvcRequestBuilders.get("/teacher-class/1")

        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("teacher-class"));
        BluebookUser tempTeacher = new BluebookUser();
        tempTeacher.setId(-22);
        Classes c = new Classes();
		c.setName("Test AP 1");
		c.setConvenor("Dr. A. Tester");
		c.setDesc("test");
		c.setId(2);
        c.genClassCode();
        c.getStudents().clear();
        c.setClass_teacher(tempTeacher);
        c.genClassCode();
        classRepo.save(c);
        when(classRepo.findById(2)).thenReturn(c);
        List<Classes> test = new ArrayList<Classes>();
        test.add(c);
        when(classRepo.findAll()).thenReturn(test);
        //test teacher but get dnied
        mockMvc.perform(
            MockMvcRequestBuilders.get("/teacher-class/2")

        )
        .andExpect(MockMvcResultMatchers.status().is(302));

        //test student but get denied
        mockMvc.perform(
            MockMvcRequestBuilders.get("/student-class/2")

        )
        .andExpect(MockMvcResultMatchers.status().is(302));

        //testing joining a class
        mockMvc.perform(
            MockMvcRequestBuilders.get("/join-class/" + c.getClassCode())

        )
        .andExpect(MockMvcResultMatchers.status().isOk());
        //joining a non existant class
        mockMvc.perform(
            MockMvcRequestBuilders.get("/join-class/fakecode")

        )
        .andExpect(MockMvcResultMatchers.status().isOk());


    }


    
}