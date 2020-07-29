package jp487bluebook.bluebook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.Classes;
import jp487bluebook.app.utilities.Levenshtein;
import jp487bluebook.app.utilities.SearchAlgorithm;

@SpringBootTest
public class UtilitiesTests {
	Classes c;
	BluebookUser bbu;
	BluebookUser bbu1;
	BluebookUser bbu2;
	BluebookUser bbu3;
	BluebookUser bbu4;
	BluebookUser bbu5;
	static Levenshtein lev = new Levenshtein(); //checks if constructor
	static SearchAlgorithm algo = new SearchAlgorithm(); //checks if constructor  
	@Before
	public void initialise() {
		bbu = new BluebookUser();
		bbu.setUsername("joe147258");
		bbu.setFirstName("Joe");
		bbu.setLastName("Phillips");
		bbu.setEmail("joe@joe.com");
		bbu.setPassword("test"); 
		bbu.setId(1);

		c = new Classes();
		c.setName("Test AP 1");
		c.setConvenor("Dr. Tester");
		c.setDesc("test class");
		c.setId(1);
		c.genClassCode();
		c.setClass_teacher(bbu);

		bbu1 = new BluebookUser();
		bbu1.setUsername("ninja111999");
		bbu1.setFirstName("Jake");
		bbu1.setLastName("Keen");
		bbu1.setEmail("jake@jake.com");
		bbu1.setPassword("123"); 
		bbu1.setId(2);
		bbu1.getClasses().add(c);
		
		bbu2 = new BluebookUser();
		bbu2.setUsername("VergeofEden");
		bbu2.setFirstName("Seb");
		bbu2.setLastName("Adamo");
		bbu2.setEmail("seb@seb.com");
		bbu2.setPassword("123"); 
		bbu2.setId(3);
		bbu2.getClasses().add(c);
		
		bbu3 = new BluebookUser();
		bbu3.setUsername("simmyb1798");
		bbu3.setFirstName("Simrit");
		bbu3.setLastName("Bains");
		bbu3.setEmail("sim@sim.com");
		bbu3.setPassword("123"); 
		bbu3.setId(4);

		bbu4 = new BluebookUser();
		bbu4.setUsername("KitchenPorter");
		bbu4.setFirstName("Thomas");
		bbu4.setLastName("Pardo");
		bbu4.setEmail("pard@pard.com");
		bbu4.setPassword("123"); 
		bbu4.setId(5);
		bbu4.getClasses().add(c);

		bbu5 = new BluebookUser();
		bbu5.setUsername("EvilMarmot");
		bbu5.setFirstName("Lewis");
		bbu5.setLastName("Kershaw");
		bbu5.setEmail("lew@lew.com");
		bbu5.setPassword("123"); 
		bbu5.setId(7);
		bbu5.getClasses().add(c);

		bbu3.getClasses().add(c);
		c.getStudents().add(bbu);
		c.getStudents().add(bbu1);
		c.getStudents().add(bbu2);
		c.getStudents().add(bbu3);
		c.getStudents().add(bbu4);
		c.getStudents().add(bbu5);



	}

    @Test
	public void checkLevinshtein() {
		//checks if the Levenshtein is calculating the correct distances

		int i = Levenshtein.calculate("Answer", "Answer");
		assertEquals(i, 0);
		int j = Levenshtein.calculate("Answe", "Answer");
		assertEquals(j, 1);
		int k = Levenshtein.calculate("Answerss", "Answer");
		assertEquals(k, 2);
		int l = Levenshtein.calculate("lame", "Answer");
		assertEquals(l, 5);
	}

	@Test
	public void checkSearchCorrectOrder() {
		//checking if the search and sort algorithm expects the correct ordered arrayList
		ArrayList<Integer> results = SearchAlgorithm.searchAndSort("joe147258", c);
		System.out.println(results.get(1));
		System.out.println(results.get(2));
		assertEquals(results.get(0).intValue(), bbu.getId()); //id of joe147258 is first
		assertEquals(results.get(1).intValue(), bbu3.getId()); //id of simmyb1798 (next closest result)
		assertEquals(results.get(2).intValue(), bbu1.getId()); //id of ninja111999 (third closest result)
		//when the list is shorter than three
		c.setStudents(c.getStudents().subList(0, 2));
		results = SearchAlgorithm.searchAndSort("joe147258", c);
		assertEquals(results.get(0).intValue(), bbu.getId()); //id of joe147258 is first
		assertEquals(results.get(1).intValue(), bbu1.getId()); //id of ninja111999 (next closest result)

    }
    
}