package jp487bluebook.bluebook;

import org.junit.Test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import jp487bluebook.app.controller.indexController;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.Classes;
import jp487bluebook.app.repository.ClassesRepository;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={indexController.class, TestSecurityConfig.class})
@AutoConfigureMockMvc
public class IndexControllerTests {

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

	@InjectMocks
	private indexController indexControllerTest;
	
	public BluebookUser saveUser(BluebookUser user) {
	  return userRepo.save(user);
	}
	
	@Before
	public void setUp() throws Exception {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		//mocked user set up
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
		
		classRepo.save(c);
		userRepo.save(user);



		when(userRepo.findByUsername("VergeofEden")).thenReturn(user);


	}
    
	@Test
	@WithUserDetails(value = "VergeofEden", userDetailsServiceBeanName="userDetailsService")
	public void mainMapping() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.get("/")
		)
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	//@WithUserDetails(value = "username", userDetailsServiceBeanName="userDetailsService")
	@WithMockUser(value = "username")
	public void testRedirect() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.get("/index")
		)
		.andExpect(MockMvcResultMatchers.status().is(302));
	}

	
}