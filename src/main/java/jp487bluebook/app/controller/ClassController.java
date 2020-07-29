package jp487bluebook.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.util.List;
import java.util.Map;

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
import jp487bluebook.app.domain.Announcement;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.Classes;

import jp487bluebook.app.domain.Quizzes;
import jp487bluebook.app.repository.AnnouncementRepository;
import jp487bluebook.app.repository.ClassesRepository;

import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;


@Controller
public class ClassController {
	@Autowired
	ClassesRepository classRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	AnnouncementRepository annRepo;

	@Autowired
	QuizRepository quizRepo;

	//display create-class view
	@RequestMapping(value="/create-class")
	public String createClassPage(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  String username = ((BluebookUserDetails)principal).getUsername();
		  model.addAttribute("username", username);
		  model.addAttribute("userId", ((BluebookUserDetails)principal).getUserId());
		  BluebookUser u = userRepo.findByUsername(username);
		  model.addAttribute("socialNumber", u.getFriendRequests().size());
		}
		
		return "create-class";
	}

	@RequestMapping(value="/create-classroom", method = RequestMethod.POST)
	public String createClass(@RequestParam Map<String, String> params) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BluebookUser u = userRepo.findById(((BluebookUserDetails)principal).getUserId());
		Classes bbClass = new Classes();
		String className = params.get("className");
		String convener = params.get("convenor");
		String desc = params.get("desc");
		if(className !=null && convener != null && desc != null){
			bbClass.setName(className);
			bbClass.setConvenor(convener);
			bbClass.setDesc(desc);
			int tempId = 1;
			while(classRepo.existsById(tempId)==true) tempId++; 
			bbClass.setId(tempId);
	
			
			bbClass.setClass_teacher(u);
			bbClass.genClassCode();
			classRepo.save(bbClass);
			
			return "redirect:/teacher-class/" + bbClass.getId();
		} else {
			return "redirect:/create-class";
		}

	}
	
	@RequestMapping(value="/view-classes")
	public String viewClassesPage(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BluebookUser u = null;
		if (principal instanceof UserDetails) {
		  String username = ((BluebookUserDetails)principal).getUsername();
		  model.addAttribute("username", username);
		  
		  u = userRepo.findByUsername(username);
		  model.addAttribute("userId", u.getId());
		  model.addAttribute("socialNumber", u.getFriendRequests().size());
		}
	
		List<Classes> ownedClasses = u.getOwnedClasses();
		model.addAttribute("AllClasses", ownedClasses);
		List<Classes> studentClasses = u.getClasses();
		model.addAttribute("studentClasses", studentClasses); 
		  
		return "view-classes";
	}
	

	@RequestMapping(value="/student-class/{id}")
	public String openStudentClass (@PathVariable int id, Model model) {
		BluebookUser u = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  String username = ((BluebookUserDetails)principal).getUsername();
		  model.addAttribute("username", username);
		  u = userRepo.findByUsername(username);
		  model.addAttribute("socialNumber", u.getFriendRequests().size());
		}
		Classes c = classRepo.findById(id);
		model.addAttribute("name", c.getName());
		model.addAttribute("userId", ((BluebookUserDetails)principal).getUserId());
		model.addAttribute("classId", c.getId());
		model.addAttribute("teacherName", c.getConvenor());
		model.addAttribute("teacher", c.getClass_teacher());
		model.addAttribute("desc", c.getDesc());
		List<Announcement> announcements = c.getAnnouncements();
		Collections.reverse(announcements);
		model.addAttribute("ann", announcements);
		if(c.getQuizzes().size() > 0 ) {
			List<Quizzes> Quizzes = c.getQuizzes();
			ArrayList<Quizzes> activeQuizzes = new ArrayList<Quizzes>();
			ArrayList<Quizzes> inactiveQuizzes = new ArrayList<Quizzes>();

			for (Quizzes q : Quizzes){
				if(q.getActive() == true && q.getHidden() == false){
					activeQuizzes.add(q);
				}
				if(q.getActive() == false && q.getHidden() == false){
					inactiveQuizzes.add(q);
				}
			}
			Collections.reverse(activeQuizzes);
			model.addAttribute("activeQuizzes", activeQuizzes);
			Collections.reverse(inactiveQuizzes);
			model.addAttribute("inactiveQuizzes", inactiveQuizzes);

		} else {
			model.addAttribute("noQuizzes", "No Quizzes have been set yet.");
		}
		

		if (c.getStudents().size() > 0) model.addAttribute("classStudents", c.getStudents());
		else model.addAttribute("noStu", "Currently no students enrolled in this class.");
		
		for(BluebookUser user : c.getStudents()) {
			if(u.getId() == user.getId()) {
				return "student-class";
			}
		}
		return "redirect:/permission-denied";
	}
	
	
	@RequestMapping(value="join-class/{classCode}")
	public @ResponseBody Object joinClass(@PathVariable String classCode) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BluebookUser u = null;
		if (principal instanceof UserDetails) {
		  String username = ((BluebookUserDetails)principal).getUsername();
		  u = userRepo.findByUsername(username);
		  
		}
		for(Classes c : classRepo.findAll()){
			if(classCode.equals(c.getClassCode())) {
				if(u.getId() != c.getClass_teacher().getId()) {
					u.getClasses().add(c);
					classRepo.save(c);
					return "Success";
				} else{
					return "Cannot join your own class";
				}

			}
			
		}
		return "Invalid Code";
	
	}
	
	@RequestMapping(value="/teacher-class/{id}")
	public String openTeacherClass(@PathVariable int id, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = ((BluebookUserDetails)principal).getUsername();
		int teacherId = ((BluebookUserDetails)principal).getUserId();
		model.addAttribute("username", username);
		model.addAttribute("teacherId", teacherId);
		BluebookUser u = userRepo.findByUsername(username);
		model.addAttribute("userId", ((BluebookUserDetails)principal).getUserId());
		model.addAttribute("socialNumber", u.getFriendRequests().size());
		
		Classes c = classRepo.findById(id);
		if(u.getId() != c.getClass_teacher().getId()) return "redirect:/permission-denied";
		model.addAttribute("name", c.getName());
		model.addAttribute("classId", c.getId());
		model.addAttribute("classCode", c.getClassCode());
		
		if(c.getAnnouncements().size() > 0 ) {
			List<Announcement> announcements = c.getAnnouncements();
			Collections.reverse(announcements);
			model.addAttribute("ann", announcements);
		} else {
			model.addAttribute("noAnn", "Currently no announcements in this class");
		}
		
		if(c.getQuizzes().size() > 0 ) {
			List<Quizzes> Quizzes = c.getQuizzes();
			ArrayList<Quizzes> activeQuizzes = new ArrayList<Quizzes>();
			ArrayList<Quizzes> inactiveQuizzes = new ArrayList<Quizzes>();
			ArrayList<Quizzes> scheduledQuizzes = new ArrayList<Quizzes>();

			for (Quizzes q : Quizzes){
				if(!q.getDueDate().before(new Date()) == true && q.getHidden() == false){
					activeQuizzes.add(q);
				} else if(q.getDueDate().before(new Date()) && q.getHidden() == false){
					inactiveQuizzes.add(q);
				} else 	if(q.getHidden() == true){
					scheduledQuizzes.add(q);
				}
			}
			Collections.reverse(activeQuizzes);
			model.addAttribute("activeQuizzes", activeQuizzes);

			Collections.reverse(inactiveQuizzes);
			model.addAttribute("inactiveQuizzes", inactiveQuizzes);

			Collections.reverse(scheduledQuizzes);
			model.addAttribute("scheduledQuizzes", scheduledQuizzes);

			model.addAttribute("teacher", c.getConvenor());
		} else {
			model.addAttribute("noQuizzes", "No previous quizzes in this class.");
		}
		
		model.addAttribute("searchResults", u.getSearchResults());
		
		if (c.getStudents().size() > 0) model.addAttribute("classStudents", c.getStudents());
		else model.addAttribute("noStu", "Currently no students enrolled in this class.");
		return "teacher-class";
	}

}
