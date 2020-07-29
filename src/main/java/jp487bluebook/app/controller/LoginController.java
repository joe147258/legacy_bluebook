package jp487bluebook.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.repository.UserRepository;

@Controller
public class LoginController {
	
	@Autowired UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value="/sign-in")
	public String loginpage() {
		return "login";
	}
	
	@RequestMapping(value="/logout")
	public String logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		return "redirect:/";
	}
	@RequestMapping(value="/register-page")
	public String RegisterUser(){
		
		return "register-page";
	}
	
	@RequestMapping(value="/register")
	public String register(@RequestParam Map<String, String> params){
		String username = params.get("username");
		String firstName = params.get("firstName");
		String lastName = params.get("lastName");
		String password = params.get("password");
		String matchPassword = params.get("matchPassword");
		String email = params.get("email");
		if(username == null || firstName == null || lastName == null || password == null || matchPassword == null || email == null){
			return "redirect:/register-page?error=4";
		} else if(!password.equals(matchPassword)) {
			return "redirect:/register-page?error=1";
		} else if(userRepo.existsByUsername(username) == true) {
			return "redirect:/register-page?error=2";
		} else if (userRepo.existsByEmail(email) == true) {
			return "redirect:/register-page?error=3";
		} else {
			BluebookUser u = new BluebookUser();
			u.setUsername(username);
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setPassword(passwordEncoder.encode(password));
			u.setEmail(email);
			int tempId=0;
			while (userRepo.existsById(tempId)) tempId++;
			u.setId(tempId);
			userRepo.save(u);
			
			return "redirect:/sign-in";
		}
		
		
	}
	@RequestMapping(value="/check-user/{username}", method = RequestMethod.POST) 
	public @ResponseBody Object checkUser(@PathVariable String username){
		HashMap<String, String> returnMap = new HashMap<String, String>();
		
		if(userRepo.existsByUsername(username)){
			returnMap.put("ret", "exists");
		} else {
			returnMap.put("ret", "free");
		}
		return returnMap;
	}
	@RequestMapping(value="/check-email/{email}", method = RequestMethod.POST) 
	public @ResponseBody Object checkEmail(@PathVariable String email){
		HashMap<String, String> returnMap = new HashMap<String, String>();
		if(userRepo.existsByEmail(email)){
			returnMap.put("ret", "exists");
		} else {
			returnMap.put("ret", "free");
		}
		return returnMap;
	}
}
