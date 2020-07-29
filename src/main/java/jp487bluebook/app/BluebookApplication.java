package jp487bluebook.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp487bluebook.app.domain.Announcement;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.BoolQuestion;
import jp487bluebook.app.domain.Classes;
import jp487bluebook.app.domain.InputQuestion;
import jp487bluebook.app.domain.MultiChoiceQuestion;
import jp487bluebook.app.domain.Quizzes;
import jp487bluebook.app.repository.AnnouncementRepository;
import jp487bluebook.app.repository.ClassesRepository;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;


@SpringBootApplication
@EnableScheduling
public class BluebookApplication implements CommandLineRunner {
	@Autowired
	private QuizRepository quizRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ClassesRepository classRepo;
	@Autowired
	AnnouncementRepository annRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {

				
		SpringApplication.run(BluebookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// // //Sets up quiz
		Quizzes quiz = new Quizzes();
		quiz.setName("European Knowledge");
		quiz.setQuestionAmount(6);
		quiz.setId(1);
		quiz.setIsPublic(true);
		quiz.setActive(true);
		quiz.setOwnerId(1);
		//set up classes
		
		Classes c = new Classes();
		c.setName("History AP 1");
		c.setConvenor("Dr. A. Barker");
		c.setDesc("This module examines contemporary literature and cultural artefacts emerging from and reacting to America's border regions. You'll engage with literary and historical representations of American borders, considering issues such as territorial expansion and sovereignty, immigration and labour, community formation and race and ethnicity, and Indigenous communities, as well as broader questions of citizenship, nationalism, hemispherism and terrorism. You'll read texts 'originating' from and responding to both the US-Mexico and Canada-US border, as well as visual representations and artistic responses to North American borders. Alongside each primary literary text you'll read historical documents. These co-texts, whether state documents or artistic manifestos, will contextualise the primary material within border studies and North American history more broadly.\n" + 
				"\n" + 
				"Learning\n" + 
				"20 hours of seminars\n" + 
				"2 hours of practical classes and workshops\n" + 
				"128 hours of guided independent study");
		
		c.setId(1);
		c.genClassCode();
		classRepo.save(c);

		BluebookUser bbu = new BluebookUser();
		bbu.setUsername("joe147258");
		bbu.setFirstName("Joe");
		bbu.setLastName("Phillips");
		bbu.setEmail("joe@joe.com");
		bbu.setPassword(passwordEncoder.encode("123")); 
		bbu.setId(1);
		userRepo.save(bbu);
		
		c.setClass_teacher(bbu);

		BluebookUser bbu1 = new BluebookUser();
		bbu1.setUsername("ninja111999");
		bbu1.setFirstName("Jake");
		bbu1.setLastName("Keen");
		bbu1.setEmail("jake@jake.com");
		bbu1.setPassword(passwordEncoder.encode("123")); 
		bbu1.setId(2);
		bbu1.getClasses().add(c);;
		userRepo.save(bbu1);
		
		BluebookUser bbu2 = new BluebookUser();
		bbu2.setUsername("VergeofEden");
		bbu2.setFirstName("Seb");
		bbu2.setLastName("Adamo");
		bbu2.setEmail("seb@seb.com");
		bbu2.setPassword(passwordEncoder.encode("123")); 
		bbu2.setId(3);
		bbu2.getClasses().add(c);;
		userRepo.save(bbu2);
		
		BluebookUser bbu3 = new BluebookUser();
		bbu3.setUsername("simmyb1798");
		bbu3.setFirstName("Simrit");
		bbu3.setLastName("Bains");
		bbu3.setEmail("sim@sim.com");
		bbu3.setPassword(passwordEncoder.encode("123")); 
		bbu3.setId(4);
		bbu3.getClasses().add(c);;
		userRepo.save(bbu3);
		
		BluebookUser bbu4 = new BluebookUser();
		bbu4.setUsername("KitchenPorter");
		bbu4.setFirstName("Thomas");
		bbu4.setLastName("Pardo");
		bbu4.setEmail("pard@pard.com");
		bbu4.setPassword(passwordEncoder.encode("123")); 
		bbu4.setId(5);
		bbu4.getClasses().add(c);;
		userRepo.save(bbu4);
		
		BluebookUser bbu6 = new BluebookUser();
		bbu6.setUsername("EvilMarmot");
		bbu6.setFirstName("Lewis");
		bbu6.setLastName("Kershaw");
		bbu6.setEmail("lew@lew.com");
		bbu6.setPassword(passwordEncoder.encode("123")); 
		bbu6.setId(7);
		bbu6.getClasses().add(c);;
		userRepo.save(bbu6);
		
		BluebookUser bbu5 = new BluebookUser();
		bbu5.setUsername("thenogtom");
		bbu5.setFirstName("Thomas");
		bbu5.setLastName("Grayling");
		bbu5.setEmail("tom@tom.com");
		bbu5.setPassword(passwordEncoder.encode("123")); 
		bbu5.setId(6);
		bbu5.getClasses().add(c);
		userRepo.save(bbu5);
		



		
		//sets up first q&a
		


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
		quiz.setHidden(false);
		quizRepo.save(quiz);

		Announcement a = new Announcement("Essay due in 3 days", "Remember to complete your essay about America's involvement in Cuba.", 1);
		a.setBb_class_ann(c);
		annRepo.save(a);

	}

}

