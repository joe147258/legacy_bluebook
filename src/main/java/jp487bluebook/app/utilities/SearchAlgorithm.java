package jp487bluebook.app.utilities;

import java.util.ArrayList;
import java.util.Collections;
import jp487bluebook.app.domain.Classes;

public class SearchAlgorithm {

//	This method takes the search input and the class that is being searched.
//	It then creates a list of tempUser class which stores how similar it is to 
//	the search input. It then sorts the list by how similar it is to the value
//	and returns the three most similar.
	
	public static ArrayList<Integer> searchAndSort(String input, Classes c){
		
		ArrayList<TempUser> tempUsers = new ArrayList<TempUser>();
		ArrayList<Integer> userIds = new ArrayList<Integer>();

		for(int i = 0; i < c.getStudents().size(); i++) {
			int similarity = Levenshtein.calculate(c.getStudents().get(i).getUsername(), input);
    		TempUser tu = new TempUser(similarity, c.getStudents().get(i).getId());
    		tempUsers.add(tu);
		}

		Collections.sort(tempUsers, new TempUserSimComparer());
		
		int forLoop = tempUsers.size();

		if(forLoop > 3) {
			forLoop = 3;
		}

		for(int i = 0; i < forLoop; i++) 
			userIds.add(tempUsers.get(i).id);
		
		
		return userIds;
	}
	
}
