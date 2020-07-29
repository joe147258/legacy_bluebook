package jp487bluebook.app.controller;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp487bluebook.app.BluebookUserDetails;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.ClassQuizAverage;
import jp487bluebook.app.domain.Classes;
import jp487bluebook.app.domain.Notification;
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
import jp487bluebook.app.domain.ClassResult;
import jp487bluebook.app.utilities.Levenshtein;
import jp487bluebook.app.domain.UserResults;

@Controller
public class PlayQuizController {

	@Autowired
	QuizRepository quizRepo;
	@Autowired
	QuestionRepository quesRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ClassesRepository classRepo;
	@Autowired
	UserResultsRepository urRepo;
	@Autowired
	ClassAverageRepository crRepo;
	@Autowired
	ClassQuizAverageRepository cqaRepo;
	@Autowired
	NotificationRepository notifRepo;
	@Autowired
	NotificationService notifService;

	
	
	@RequestMapping(value="/start-quiz")
	public String startquiz(Model model, @RequestParam int quizId){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String username = ((BluebookUserDetails)principal).getUsername();
		model.addAttribute("username", username);
		BluebookUser u = userRepo.findByUsername(username);
		u.setUserScore(0);
		u.getAnsweredQuestions().clear();
		u.setCompletedQuiz(false);
		model.addAttribute("userId", ((BluebookUserDetails)principal).getUserId());
		model.addAttribute("socialNumber", u.getFriendRequests().size());
		Quizzes q = quizRepo.findById(quizId);
		if(q.getCompleted() == false) q.setCompleted(true);
		int tempUrId = 0;
		while(urRepo.findById(tempUrId) != null) tempUrId++;
		UserResults ur = new UserResults(quizId, q.getQuestionAmount(), 0, q.getName(), tempUrId);
		ur.setResults_owner(u);
		if(q.getHidden() == true) return "redirect:/permission-denied";
		if(q.getIsPublic() == false){
			Boolean redir = true;
			for(Classes c : u.getClasses()){
				if(q.getBb_class().getId() == c.getId()){
					redir = false;
					break;
				}
			}
			if(redir == true) return "redirect:/permission-denied";;
		}

		if(q.getDueDate() != null){
			Boolean active = (!q.getDueDate().before(new Date()));
			q.setActive(active);
		}

		Boolean recordClass = false;
		if(q.getBb_class() != null){
			for(BluebookUser loopUser : q.getBb_class().getStudents()){
				if(loopUser.getId() == u.getId()) {
					recordClass = true;
					break;
				}
			}
		}


		if (q.getActive() == true && q.getBb_class() != null && recordClass == true){
			Integer completed = null;
			for (int i = 0; i < u.getPreviousResults().size(); i++){
				if(u.getPreviousResults().get(i).getQuizId() == quizId){
					completed = i;
					break;
				}
			}	
			if(completed != null){
				u.setCompletedQuiz(true);
				model.addAttribute("timeCompleted", u.getPreviousResults().get(completed.intValue()).getDateString()); 
				model.addAttribute("completed", true);
			} else {
				ur.setImportant(true);
			}
			//this looks to see if the quiz is the first for this classroom
			Boolean classFirstQuiz = true;
			for(ClassResult cr : u.getClassAverages()){
				if(cr.getC().getId() == q.getBb_class().getId()){
					classFirstQuiz = false;
					break;
				}
			}
			//if it is then it will create a new class average to add to the list.
			if(classFirstQuiz == true){
				int tempId = 0;
				while(crRepo.findById(tempId) != null) tempId++;
				ClassResult cr = new ClassResult((float)0.0, tempId, q.getName());
				cr.setAverageOwner(u);
				cr.setC(q.getBb_class());
				u.getClassAverages().add(cr);
			} else { // if there is already a class average it will find it's position
				Integer pos = u.classAverageContainsAndGetPos(q.getBb_class().getId());
				if(pos != null){
					if(completed == null){
						u.setOldAverage(u.getClassAverages().get(pos.intValue()).getAverage());
						u.getClassAverages().get(pos.intValue()).addResult((float)0.0, new Date(), q.getName());
						float curAvg = u.getClassAverages().get(pos.intValue()).getAverage();
						if(u.getOldAverage() > curAvg){
							float f = 0.0f - (u.getOldAverage() -curAvg);
							u.setPercentChange(f);
						} else {
							u.setPercentChange(u.getOldAverage() - u.getClassAverages().get(pos.intValue()).getAverage());
						}
						
					} 

				}
			}
			//code below is to check if there is a class average grade for this quiz or not. if there isn't create one
			if(cqaRepo.findByQuizId(q.getId()) != null){
				ClassQuizAverage cqa = cqaRepo.findByQuizId(q.getId());
				cqa.addResult(0f);
				cqaRepo.save(cqa);
				
			} else {
				int tempId = 0;
				while(cqaRepo.findById(tempId) != null) tempId++;
				ClassQuizAverage cqa = new ClassQuizAverage(tempId, 0f, q.getId());
				cqa.setRelatedClass(q.getBb_class());
				cqaRepo.save(cqa);
				Classes c = q.getBb_class();
				
				c.getAverageGrades().add(cqa);
				classRepo.save(c);
			}
		}
		


		u.getPreviousResults().add(ur);
		
		if(q.getActive() == false){
			model.addAttribute("late", true);
		} else {
			model.addAttribute("late", false);
		}
		u.setUserScore(0);
		model.addAttribute("quizName", q.getName());
		model.addAttribute("quizId", q.getId());
		model.addAttribute("quizSize", q.getQuestionAmount());
		userRepo.save(u);
		return "start-quiz";
	}
	

	@RequestMapping(value="/getQues", method = RequestMethod.GET)
	public @ResponseBody Object getQues(@RequestParam int quizId, @RequestParam int qNo){
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BluebookUser u = null;

		if (principal instanceof UserDetails) {
		  String username = ((BluebookUserDetails)principal).getUsername();

		  u = userRepo.findByUsername(username);

		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		Quizzes q = quizRepo.findById(quizId);
		
		map.put("question", q.getQuestions().get(qNo).getQuestionString());
		if(q.getQuestions().get(qNo).getClass().getSimpleName().equals("MultiChoiceQuestion")) {
			map.put("type", "multi");
			map.put("ammount", q.getQuestions().get(qNo).getAnswerList().size());

			for(int i = 0; i < q.getQuestions().get(qNo).getAnswerList().size(); i++) {
				map.put("box" + i, q.getQuestions().get(qNo).getAnswerList().get(i));
			}

		} else if(q.getQuestions().get(qNo).getClass().getSimpleName().equals("BoolQuestion")){
			map.put("type", "boolean");
		} else if(q.getQuestions().get(qNo).getClass().getSimpleName().equals("InputQuestion")) {
			map.put("type", "input");
		}
		
		if(u.getAnsweredQuestions().containsKey(qNo)){
			map.put("answered", true);
			map.put("correctAns", q.getQuestions().get(qNo).getCorrectAnswer());
			if(u.getAnsweredQuestions().get(qNo).equalsIgnoreCase(q.getQuestions().get(qNo).getCorrectAnswer())) {
				map.put("correct", true);
			} else {
				map.put("userAns", u.getAnsweredQuestions().get(qNo));
				map.put("correct", false);
			}
		} else {
			map.put("answered", false);
		}
		return map;
	}
	
	@RequestMapping(value="/check-answer", method = RequestMethod.POST)
	public @ResponseBody Object checkAns(@RequestParam String ans, @RequestParam int qNo, @RequestParam int quizId){

		HashMap<String, Object> map = new HashMap<String, Object>();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BluebookUser u = null;
		if (principal instanceof UserDetails) {
		  String username = ((BluebookUserDetails)principal).getUsername();
		  u = userRepo.findByUsername(username);
		  
		}
		
		if(!u.getAnsweredQuestions().containsKey(qNo)){
			Quizzes q = quizRepo.findById(quizId);
			u.getAnsweredQuestions().put(qNo, ans);
			if(q.getQuestions().get(qNo).getClass().getSimpleName().equals("InputQuestion")) {
				if(Levenshtein.calculate(q.getQuestions().get(qNo).getCorrectAnswer(), ans) <= 3) {
					map.put("ret", true);
					  u.setUserScore(u.getUserScore()+1);
				} else {
					map.put("ret", false);
				}
			} else if(q.getQuestions().get(qNo).getCorrectAnswer().equalsIgnoreCase(ans)) {
				map.put("ret", true);
				u.setUserScore(u.getUserScore()+1);
			} else {
				map.put("ret", false);
			}
			userRepo.save(u);
		} else {
			map.put("ret", "already-answered");
		}
		return map;
	}
	
	@RequestMapping(value="/submit-quiz")
	public String submitQuiz(Model model, @RequestParam int quizId){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BluebookUser u = null;
		if (principal instanceof UserDetails) {
		  String username = ((BluebookUserDetails)principal).getUsername();
		  u = userRepo.findByUsername(username);
		  
		}
		Quizzes q = quizRepo.findById(quizId);
		
		Boolean recordClass = false;
		if(q.getBb_class() != null){
			for(BluebookUser loopUser : q.getBb_class().getStudents()){
				if(loopUser.getId() == u.getId()) {
					recordClass = true;
					break;
				}
			}
		}


		if(q.getBb_class() != null && recordClass == true){
			if(u.getCompletedQuiz() == false){
				Integer pos = u.classAverageContainsAndGetPos(q.getBb_class().getId());
				if(pos != null ){ 
					float f = ((float) u.getUserScore() / (float) q.getQuestionAmount()) * 100;
					u.getClassAverages().get(pos.intValue()).ammendLatestResult(f);
					float curAvg = u.getClassAverages().get(pos.intValue()).getAverage();
					if(curAvg < 40.0){
						int tempId = 0;
						while(notifRepo.findById(tempId) != null) tempId++;
						
						Notification n = new Notification(tempId, "Struggling Student in " + q.getBb_class().getName(), "badStu");
						n.setAttachedUsername(u.getUsername());
						notifRepo.save(n);
						notifService.sendPersonalNotification(n, q.getBb_class().getClass_teacher());
					}
					if(u.getOldAverage() > curAvg){
						float temp = 0.0f - (u.getOldAverage() - curAvg);
						u.setPercentChange(temp);
					} else {
						u.setPercentChange(u.getClassAverages().get(pos.intValue()).getAverage() - u.getOldAverage());
					}

					float f1 = ((float) u.getUserScore() / (float) q.getQuestionAmount()) * 100;
					ClassQuizAverage cqa = cqaRepo.findByQuizId(q.getId());
					cqa.ammendLatestResult(f1);
					Classes c = q.getBb_class();
					c.getAverageGrades().add(cqa);
					cqa.setRelatedClass(c);
					classRepo.save(c);
					cqaRepo.save(cqa);
				} else System.out.println("Non active quiz");
			}
		}

		//a blank UR with the score of 0 is above in start quiz
		//this accessed the user's lastest (which will always be made in start quiz)
		u.getPreviousResults().get(u.getPreviousResults().size() - 1).setUserScore(u.getUserScore());


		userRepo.save(u);
		return "redirect:/";
	}

	
}


