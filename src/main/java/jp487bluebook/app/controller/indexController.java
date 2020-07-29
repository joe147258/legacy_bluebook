package jp487bluebook.app.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp487bluebook.app.BluebookUserDetails;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.Notification;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;


@Controller
public class indexController {
	
	@Autowired QuizRepository quizRepo;
	@Autowired UserRepository userRepo;
	
    @RequestMapping("/index")
    public static String indexReDir() {
        return "redirect:/";
    }
    @RequestMapping("/")
    public String index(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		

		String username = ((BluebookUserDetails)principal).getUsername();

		String firstName = ((BluebookUserDetails)principal).getFirstName();
		int userId = ((BluebookUserDetails)principal).getUserId();
		model.addAttribute("username", username);
		model.addAttribute("firstName", firstName);
		model.addAttribute("userId", userId);
		model.addAttribute("quizzes", quizRepo.findAllPublic());
		BluebookUser u = userRepo.findByUsername(username);
		model.addAttribute("userClasses", u.getClasses());

		List<Notification> list =  u.getNotifications();
		Collections.reverse(list);
		model.addAttribute("notifications", list);
		model.addAttribute("socialNumber", u.getFriendRequests().size());
		  
		  


        return "index";
	}
	

    
}
