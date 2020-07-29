package jp487bluebook.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp487bluebook.app.BluebookUserDetails;

import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.ClassQuizAverage;
import jp487bluebook.app.domain.ClassResult;
import jp487bluebook.app.domain.Classes;
import jp487bluebook.app.domain.Notification;
import jp487bluebook.app.domain.Quizzes;
import jp487bluebook.app.repository.AnnouncementRepository;
import jp487bluebook.app.repository.ClassAverageRepository;
import jp487bluebook.app.repository.ClassesRepository;
import jp487bluebook.app.repository.NotificationRepository;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;
import jp487bluebook.app.service.NotificationService;
import jp487bluebook.app.service.TimerService;
import jp487bluebook.app.utilities.SearchAlgorithm;

@RestController
public class TeacherClassController {
    
    @Autowired
	ClassesRepository classRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	AnnouncementRepository annRepo;

	@Autowired
	QuizRepository quizRepo;
	
	@Autowired
	ClassAverageRepository crRepo; 
    
	@Autowired
	TimerService timeService;

	@Autowired 
	NotificationRepository notifRepo;
	@Autowired 
	NotificationService notifService;

    
	@RequestMapping(value="/addStudent/{username}/{classId}", method = RequestMethod.POST)
	public @ResponseBody Object addStudent (@PathVariable String username, @PathVariable int classId) {
		Classes c = classRepo.findById(classId);
		BluebookUser user = userRepo.findByUsername(username);
		HashMap<String, Object> map = new HashMap<>();
	
		if(user != null) {
			for(int i = 0; i < c.getStudents().size(); i++) {
				if(c.getStudents().get(i).getUsername().equals(user.getUsername())) {
					map.put("ret", false);
					map.put("res", "User is in class already");
					return map;
				}
			}
			if(user.getId() != c.getClass_teacher().getId()){
				user.getClasses().add(c);
				classRepo.save(c);
				map.put("ret", true);
			} else {
				map.put("ret", "false");
				map.put("res", "cannot join your own class");
			}
			
			} else {
				map.put("ret", "false");
				map.put("res", "class doesn't exist");
			}
		return map; 
	}
	
	@RequestMapping(value="/remStudent/{uid}/{classId}", method = RequestMethod.POST)
	public @ResponseBody Object remStudent (@PathVariable int uid, @PathVariable int classId) {
		Classes c = classRepo.findById(classId);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int currentId = ((BluebookUserDetails)principal).getUserId();
		if(uid == currentId || currentId == c.getClass_teacher().getId()){
			HashMap<String, Object> map = new HashMap<>();
			for(int i = 0; i < c.getStudents().size(); i++) {
				if(c.getStudents().get(i).getId() == uid) {
					BluebookUser u = userRepo.findById(uid);
					for(int j = 0; j < u.getClasses().size(); j++) {
						if(u.getClasses().get(j).getId() == classId) {
							u.getClasses().remove(j);
						}
					}
					c.getStudents().remove(i);
					classRepo.save(c);
					map.put("ret", "true");
					return map;
				} 
			}
			map.put("ret", "false");
			map.put("res", "not-in-class");
			return map;
		}
		return false;
    }

    @RequestMapping(value="/search-students")
	public @ResponseBody void searchStudents(
			@RequestParam Map <String, Object> params, @RequestParam int classId){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BluebookUser u = null;
		if (principal instanceof UserDetails) {
		  String username = ((BluebookUserDetails)principal).getUsername();
		  u = userRepo.findByUsername(username);
		}
		u.getSearchResults().clear();
		
		Classes c = classRepo.findById(classId);
		
		ArrayList<Integer> OrderedArray = SearchAlgorithm.searchAndSort((String) params.get("value"), c);
		for(int i = 0; i < OrderedArray.size(); i++) {
			BluebookUser u1 = userRepo.findById(OrderedArray.get(i).intValue());
			u.getSearchResults().add(u1);
		}
		userRepo.save(u);
		
    }

    @RequestMapping(value = "/change-due")
	public @ResponseBody Object changedue(@RequestParam int quizId, @RequestParam String dueDate,
			@RequestParam String dueTime) throws ParseException {
		Quizzes q = quizRepo.findById(quizId);
		dueDate += " " + dueTime;
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dueDate);
		if(!date.before(new Date())){
			q.setDueDate(date);
		} else {
			q.setDueDate(date);
			q.setActive(false);
		}
		quizRepo.save(q);

		return true;
	}

	@RequestMapping(value = "/reschedule")
	public @ResponseBody Object reschedule(@RequestParam int quizId, @RequestParam String dueDate,
			@RequestParam String dueTime, @RequestParam String schedVis) throws ParseException {
		Quizzes q = quizRepo.findById(quizId);
		Classes c = q.getBb_class();
		int i = 0;
        while(notifRepo.findById(i) != null) i++;
        Notification n = new Notification(i, "New Quiz | " + c.getName(), "quiz");

		notifRepo.save(n);

		dueDate += " " + dueTime;
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dueDate);
		if(!date.before(new Date())){
			TimerTask task = new TimerTask() { 
				public void run() {
					timeService.scheduleQuiz(quizId, n, schedVis);
				}
			};
			timeService.schedule(task, date, quizId);
			q.setScheduledFor(date);
		} else {
		}
		quizRepo.save(q);

		return true;
	}

	@RequestMapping(value = "/schedule")
	public @ResponseBody Object schedule(@RequestParam int quizId, @RequestParam String dueDate,
			@RequestParam String dueTime, @RequestParam String schedVis) throws ParseException {
		Quizzes q = quizRepo.findById(quizId);
		Classes c = q.getBb_class();
		int i = 0;
        while(notifRepo.findById(i) != null) i++;
        Notification n = new Notification(i, "New Quiz | " + c.getName(), "quiz");
		notifRepo.save(n);
		dueDate += " " + dueTime;
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dueDate);
		if(!date.before(new Date())){
			TimerTask task = new TimerTask() { 
				public void run() {
					timeService.scheduleQuiz(quizId, n, schedVis);
				}
			};
			q.setScheduledFor(date);
			q.setScheduled(true);
			timeService.schedule(task, date, quizId);
		} else {
		}
		quizRepo.save(q);

		return true;
	}
	@RequestMapping(value = "/toggle-quiz")
	public @ResponseBody Object toggleQuiz(@RequestParam int quizId){
		Quizzes q = quizRepo.findById(quizId);
		if(q.getHidden() == false){
			q.setHidden(true);
		} else {
			q.setHidden(false);
		}
		quizRepo.save(q);
		return true;
	} 

	@RequestMapping("/get-stu-results/{sid}/{cid}")
	public @ResponseBody Object getStudentResult(@PathVariable int sid, @PathVariable int cid){
		BluebookUser u = userRepo.findById(sid);
		Classes c = classRepo.findById(cid);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((BluebookUserDetails)principal).getUsername();
		BluebookUser user = userRepo.findByUsername(username);
		
		if(user.getId() == c.getClass_teacher().getId()){
			ArrayList<Float> averageScores = new ArrayList<Float>();
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			for(ClassQuizAverage cqa : c.getAverageGrades()){
				averageScores.add(cqa.getAverage());
			}
			ArrayList<String[]> label = new ArrayList<String[]>();
			
			for(ClassResult cr : u.getClassAverages()){
				if(cr.getC().getId() == cid){
					for(int i = 0; i < cr.getDates().size(); i++) {
						String[] arr = {cr.getNames().get(i), cr.getDates().get(i)};
						label.add(arr);
					}
					resultMap.put("results", cr.getResults().toArray());
					resultMap.put("average", averageScores.toArray());
					resultMap.put("label", label.toArray());
					resultMap.put("className", cr.getC().getName());
					resultMap.put("percentChange", u.getPercentChange());
					resultMap.put("studentAverage", cr.getAverage());
					resultMap.put("name", u.getUsername());

				}
			}

	
			return resultMap;
		} else return "invalid";


	}
	@RequestMapping(value="/get-averages")
	public @ResponseBody Object getAverages(@RequestParam int qid){
		Quizzes q = quizRepo.findById(qid);
		Classes c = q.getBb_class();
		List<Float> results = new LinkedList<Float>();
		int[] returnArr = {0, 0, 0};
		for(ClassQuizAverage cqa : c.getAverageGrades()){
			if(cqa.getQuizId() == q.getId()){
				results = cqa.getResults();
				break;
			}
		}
		
		for(Float f : results){
			if(f.floatValue() < 50f) {
				returnArr[0] = returnArr[0] + 1;
			} else if (f.floatValue() > 50f && f < 70f) {
				returnArr[1] = returnArr[1] + 1;
			} else if (f.floatValue() >= 70f) {
				returnArr[2] = returnArr[2] + 1;
			}
		}

		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("arr", returnArr);
		returnMap.put("title", q.getName());
		return returnMap;
	}
}