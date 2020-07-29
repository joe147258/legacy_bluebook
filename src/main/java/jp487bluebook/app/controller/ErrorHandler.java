package jp487bluebook.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import jp487bluebook.app.BluebookUserDetails;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.repository.UserRepository;

@Controller
public class ErrorHandler implements ErrorController{

    private static final String PATH = "/error";
    @Autowired UserRepository userRepo;
    
    @RequestMapping(value = PATH)
    public String error() {
        return "404";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
    
    @RequestMapping("/permission-denied")
	public String permissionDenied(Model model){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((BluebookUserDetails)principal).getUsername();
		BluebookUser u = userRepo.findByUsername(username);
		model.addAttribute("socialNumber", u.getFriendRequests().size());
		model.addAttribute("username", username);
		return "permission-denied";
	}
    
}