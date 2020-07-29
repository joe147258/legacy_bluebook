package jp487bluebook.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp487bluebook.app.BluebookUserDetails;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.Quizzes;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;

@Controller
public class QuizController {
	@Autowired QuizRepository quizRepo;
	@Autowired UserRepository userRepo;
	
	@RequestMapping(value="/all-quizzes", method = RequestMethod.GET)
	public String viewQuizzes(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((BluebookUserDetails)principal).getUsername();
		BluebookUser u = userRepo.findByUsername(username);
		model.addAttribute("username", username);
		model.addAttribute("userId", ((BluebookUserDetails)principal).getUserId());
		model.addAttribute("socialNumber", u.getFriendRequests().size());

		model.addAttribute("quizList", quizRepo.findAllPublic());
		model.addAttribute("privateQuizList", quizRepo.findAllByOwnerId(u.getId()));
		
		return "all-quizzes";
	}
	
	@RequestMapping(value = "/delete-quiz", method = RequestMethod.GET)
    public @ResponseBody Object delQuiz(@RequestParam int qid){

		Quizzes q = quizRepo.findById(qid);
		q.setBb_class(null);
		q.setHidden(true);
		q.setOwnerId(-1);
		quizRepo.save(q);
		

		return true;
    } 
	
		
}
