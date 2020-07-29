package jp487bluebook.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp487bluebook.app.BluebookUserDetails;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.BoolQuestion;
import jp487bluebook.app.domain.Classes;
import jp487bluebook.app.domain.InputQuestion;
import jp487bluebook.app.domain.MultiChoiceQuestion;
import jp487bluebook.app.domain.Notification;
import jp487bluebook.app.domain.Questions;
import jp487bluebook.app.domain.Quizzes;
import jp487bluebook.app.repository.AnnouncementRepository;
import jp487bluebook.app.repository.ClassesRepository;
import jp487bluebook.app.repository.NotificationRepository;
import jp487bluebook.app.repository.QuestionRepository;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;
import jp487bluebook.app.service.NotificationService;
import jp487bluebook.app.service.TimerService;



@Controller
public class CreateQuizController {

	@Autowired
	QuizRepository quizRepo;
	@Autowired
	QuestionRepository quesRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ClassesRepository classRepo;
	@Autowired
	AnnouncementRepository annRepo;

	@Autowired 
	NotificationRepository notifRepo;

	@Autowired
	NotificationService notifService;


	@Autowired
	TimerService timeService;

	@RequestMapping(value = "/create-quiz", method = RequestMethod.GET)
	public String createQuiz(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((BluebookUserDetails) principal).getUsername();
			model.addAttribute("username", username);
			int ownerId = ((BluebookUserDetails) principal).getUserId();
			model.addAttribute("ownerId", ownerId);
			model.addAttribute("userId", ((BluebookUserDetails)principal).getUserId());
			BluebookUser u = userRepo.findByUsername(username);
			model.addAttribute("socialNumber", u.getFriendRequests().size());
		}
		return "create-quiz";
	}

	@RequestMapping(value="/submit-pub-quiz/{qId}", method = RequestMethod.GET)
	public String submitPubQuiz(@PathVariable int qId, @RequestParam String visibilityOption) throws ParseException {
		Quizzes q = quizRepo.findById(qId);
			if(visibilityOption.equals("private")){
				q.setHidden(true);
			}  else if (visibilityOption.equals("everyone")){
				q.setHidden(false);
				q.setIsPublic(true);
			} else {
				System.out.println("error publishing quiz");
			}
		quizRepo.save(q);
		return "redirect:/";
	}

	@RequestMapping(value="/submit-quiz/{qId}", method = RequestMethod.GET)
	public String submitQuiz(@PathVariable int qId, @RequestParam(required = false) String dateString, 
	@RequestParam String visibilityOption, @RequestParam(required = false) String schedVis) throws ParseException {
		Quizzes q = quizRepo.findById(qId);
		Classes c = q.getBb_class();
        int i = 0;
        while(notifRepo.findById(i) != null) i++;
		Notification n = new Notification(i, "New Quiz | " + c.getName(), "quiz");
		n.setQuizId(q.getId());
		n.setQuizName(q.getName());
		n.setQuizQuestionAmount(q.getQuestionAmount());
		notifRepo.save(n);
		
		if(visibilityOption.equals("schedule") && dateString != null){
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateString);
			if(!date.before(new Date())) {
				q.setScheduled(true);
				q.setScheduledFor(date);
				TimerTask task = new TimerTask() { 
					public void run() {
						timeService.scheduleQuiz(qId, n, schedVis);
					}
				};
				timeService.schedule(task, date, qId);
		} else {
			q.setActive(false);
		} 
		} else {
			if(visibilityOption.equals("private")){
				q.setHidden(true);
				q.setIsPublic(false);
			} else if (visibilityOption.equals("class")){
				q.setHidden(false);
				q.setIsPublic(false);
				notifService.sendClassNotification(c, n);
			} else if (visibilityOption.equals("everyone")){
				q.setHidden(false);
				notifService.sendClassNotification(c, n);
				q.setIsPublic(true);
			} else {
				System.out.println("error publishing quiz");
			}		
		}
		quizRepo.save(q);
		return "redirect:/";
	}

	@RequestMapping(value = "/create-new-quiz", method = RequestMethod.POST)
	public String createNewQuiz(@RequestParam Map<String, String> params, Model model) throws ParseException {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("userId", ((BluebookUserDetails)principal).getUserId());
		Quizzes q = new Quizzes();
		String name = null;
		Integer questionAmount = null;
		try{
			questionAmount = Integer.parseInt(params.get("questionNo")); 
			name = params.get("name");
		} catch(NumberFormatException e){
			return "redirect:/";
		}
		
		if(name != null && questionAmount != null) {
			q.setName(name);
			q.setQuestionAmount(questionAmount);
			q.setOwnerId(((BluebookUserDetails) principal).getUserId());
			q.setActive(true);
			BluebookUser u = userRepo.findByUsername(((BluebookUserDetails)principal).getUsername());
			model.addAttribute("socialNumber", u.getFriendRequests().size());
	
	
			
			int tempId = 1;
			while(quizRepo.existsById(tempId)==true) tempId++; 
			q.setId(tempId);
			
			/*This handles if it was made for a classroom or not.*/
			if(params.get("classId") != null) {
				int classId = Integer.parseInt(params.get("classId"));
				Classes c = classRepo.findById(classId);
				Boolean isInClass = false;
				for(Classes item : u.getOwnedClasses()){
					if(item.getId() == classId) {
						isInClass = true;
						break;
					}
				}
				if(isInClass == true){
					q.setBb_class(c);
					String dueDate = params.get("dueDate");
					String dueTime = params.get("dueTime");
					if ((dueDate != null) && (dueTime != null)) {
						/*Formats the dueDate from the parameters
						Sets dueDate to the quiz.
						Creates a notification */
						Date dateObject = 
							new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dueDate + " " + dueTime);
						q.setDueDate(dateObject);
					}
				}
			}
			Boolean isPublic;
			if(params.get("isPublic") != null){
				isPublic = true;
			} else {
				isPublic = false;
			}
			quizRepo.save(q);
			return "redirect:/quiz-add-question/" + q.getId() + "?isPublic=" + isPublic;
		} else return "redirect:/";
		
    }
	
	@RequestMapping(value="/quiz-add-question/{quizId}")
	public String quizAddQuestions(@PathVariable int quizId, Model model, @RequestParam Boolean isPublic) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  String username = ((BluebookUserDetails)principal).getUsername();
		  model.addAttribute("username", username);
		  model.addAttribute("userId", ((BluebookUserDetails)principal).getUserId());
		  BluebookUser u = userRepo.findByUsername(username);
		  model.addAttribute("socialNumber", u.getFriendRequests().size());
		  
		}
		model.addAttribute("isPublic", isPublic);
		Quizzes q = quizRepo.findById(quizId);
		quizRepo.save(q);
		model.addAttribute("qAmount", q.getQuestionAmount());
		model.addAttribute("quizId", q.getId());
		model.addAttribute("editSave", false);
		return "add-question-page";
	}
	
	@RequestMapping(value="/quiz-edit-question/{quizId}")
	public String quizEditQuestion(@PathVariable int quizId, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BluebookUser u = null;
		if (principal instanceof UserDetails) {
		  String username = ((BluebookUserDetails)principal).getUsername();
		  model.addAttribute("username", username);
		  model.addAttribute("userId", ((BluebookUserDetails)principal).getUserId());
		  u = userRepo.findById(((BluebookUserDetails)principal).getUserId());
		  model.addAttribute("socialNumber", u.getFriendRequests().size());
		}
		Quizzes q = quizRepo.findById(quizId);
		if(u.getId() != q.getOwnerId()) return "redirect:/permission-denied";
		model.addAttribute("qAmount", q.getQuestionAmount());
		model.addAttribute("quizId", q.getId());
		model.addAttribute("toEdit", "1");
		model.addAttribute("nonClass", false);

		ArrayList<String> questionTypes = new ArrayList<String>();
		ArrayList<String> questionStrings = new ArrayList<String>();
		ArrayList<String> questionAnswers = new ArrayList<String>();

		for(int i = 0; i < q.getQuestions().size(); i++) {
			questionTypes.add(q.getQuestions().get(i).getClass().getSimpleName());
			questionStrings.add(q.getQuestions().get(i).getQuestionString());
			questionAnswers.add(q.getQuestions().get(i).getCorrectAnswer());
		}
		model.addAttribute("questionTypes", questionTypes.toString().replace("[", "").replace("]", "").replace(" ", ""));
		model.addAttribute("questionStrings", questionStrings.toString().replace("[", "").replace("]", "")); 
		
		model.addAttribute("questionAnswers", questionAnswers.toString().replace("[", "").replace("]", "").replace(" ", ""));
		if(q.getBb_class() != null){
			model.addAttribute("isPublic", false);
		} else {
			model.addAttribute("isPublic", true);
		}
		model.addAttribute("editSave", true);
		
		return "add-question-page";
	}

	@RequestMapping(value="/get-incorrect-answers")
	public @ResponseBody Object getIncAns(
			@RequestParam int quizId,
			@RequestParam int quesNo
			) {
		ArrayList<String> toReturn = new ArrayList<String>();
		
		Quizzes q = quizRepo.findById(quizId);
		if(q.getQuestions().get(quesNo).getAnswerList() != null) {
			toReturn.add(Integer.toString(quesNo));
			for(int j = 0; j < q.getQuestions().get(quesNo).getAnswerList().size(); j++) {
				if(!q.getQuestions().get(quesNo).getCorrectAnswer().equals(q.getQuestions().get(quesNo).getAnswer(j))) {
					toReturn.add(q.getQuestions().get(quesNo).getAnswer(j));
				}
				
			}
		}

			
		
		if(!toReturn.isEmpty()) return toReturn.toArray();
		else return "";
	}
	
	@RequestMapping(value="/save-question/{qNo}/{quizId}",  method = RequestMethod.GET)
	public @ResponseBody Object saveQuestion(@PathVariable int qNo, @PathVariable int quizId,
			@RequestParam String type,
			@RequestParam String questionString,
			@RequestParam String correctAns,
			@RequestParam(required=false) String incor1,
			@RequestParam(required=false) String incor2,
			@RequestParam(required=false) String incor3,
			@RequestParam Boolean edit
			) { //need to get all of the question details
		Quizzes quiz = quizRepo.findById(quizId);
		ArrayList<Questions> tempQuesList = new ArrayList<Questions>();
		for(int i = 0; i < quiz.getQuestionAmount(); i++) {
			tempQuesList.add(null); // temp question added, will be replaced later
		}

		int tempId;
	    tempId = 1;
	   	while(quesRepo.existsById(tempId)==true) tempId++;
		
		if(type.equals("multi")) {
			MultiChoiceQuestion question;
			if(edit == false) {
				question = new MultiChoiceQuestion();
				question.setId(tempId);
			} else {
				question = (MultiChoiceQuestion) quesRepo.findById(quiz.getQuestions().get(qNo - 1).getId());
				question.getAnswerList().clear();

			}
			
			question.setQuestionString(questionString);
			question.setCorrectAnswer(correctAns);
			question.addAnswer(correctAns);
			question.addAnswer(incor1);
			question.addAnswer(incor2);
			question.addAnswer(incor3);
			question.setQuiz(quiz);
			quesRepo.save(question);
			tempQuesList.set(qNo - 1, question);
			quiz.setQuestions(tempQuesList);
		} else if (type.equals("boolean")) {
			BoolQuestion question;
			if(edit == false) {
				question = new BoolQuestion();
				question.setId(tempId);
			} else {
				question = (BoolQuestion) quesRepo.findById(quiz.getQuestions().get(qNo - 1).getId());
			}
			question.setQuestionString(questionString);
			question.setCorrectAnswer(correctAns);
			question.setQuiz(quiz);
			quesRepo.save(question);
			tempQuesList.set(qNo - 1, question);
			quiz.setQuestions(tempQuesList);
		} else if (type.equals("input")) {
			InputQuestion question;
			if(edit == false) {
				question = new InputQuestion();
				question.setId(tempId);
			} else {
				question = (InputQuestion) quesRepo.findById(quiz.getQuestions().get(qNo - 1).getId());
			}
			question.setQuestionString(questionString);
			question.setCorrectAnswer(correctAns);
			question.setQuiz(quiz);
			quesRepo.save(question);
			tempQuesList.set(qNo - 1, question);
			quiz.setQuestions(tempQuesList);
		} else {
			System.out.println("Something went wrong! Recieved wrong values from the AJAX function.");
		}
		
		quizRepo.save(quiz);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ret", true);
		return map;
	}

}
