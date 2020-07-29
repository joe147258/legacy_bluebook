package jp487bluebook.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp487bluebook.app.BluebookUserDetails;
import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.ClassQuizAverage;
import jp487bluebook.app.repository.AnnouncementRepository;
import jp487bluebook.app.repository.ClassAverageRepository;
import jp487bluebook.app.repository.ClassQuizAverageRepository;
import jp487bluebook.app.repository.UserRepository;
import jp487bluebook.app.domain.ClassResult;
import jp487bluebook.app.domain.UserResults;

@Controller
public class ProfileController{
    @Autowired UserRepository userRepo;
    @Autowired ClassQuizAverageRepository cqaRepo;
    @Autowired ClassAverageRepository crRepo;
    @Autowired AnnouncementRepository annRepo;

    @RequestMapping(value="/view-profile")
    public String viewProfile(@RequestParam int id, Model model){
        BluebookUser u = userRepo.findById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(((BluebookUserDetails)principal).getUserId() == id) return "redirect:/my-profile";
        model.addAttribute("user", u);

        String username = ((BluebookUserDetails)principal).getUsername();
        int userId = ((BluebookUserDetails)principal).getUserId();
        model.addAttribute("username", username);
        model.addAttribute("userId", userId);
        model.addAttribute("socialNumber", u.getFriendRequests().size());
        return "view-profile";
    }

    @RequestMapping(value="/my-profile")
    public String myProfile(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((BluebookUserDetails)principal).getUsername();
        model.addAttribute("userId", ((BluebookUserDetails)principal).getUserId());
        BluebookUser u = userRepo.findByUsername(username);
        model.addAttribute("user", u);
        List<UserResults> all = u.getPreviousResults();
        model.addAttribute("results", all);
        List<UserResults> list = all.subList(Math.max(all.size() - 5, 0), all.size());
        Collections.reverse(list);
        model.addAttribute("recentResults", list);
        model.addAttribute("socialNumber", u.getFriendRequests().size());
        return "my-profile";
    }

    @RequestMapping(value="/get-results/{crId}")
    public @ResponseBody Object getResults(@PathVariable int crId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((BluebookUserDetails)principal).getUsername();

        
        BluebookUser u = userRepo.findByUsername(username);

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        ArrayList<String[]> label = new ArrayList<String[]>();
        ArrayList<Float> averageScores = new ArrayList<Float>();
        
        ClassResult cr = crRepo.findById(crId);
        
        for(ClassQuizAverage cqa : cr.getC().getAverageGrades()){
            averageScores.add(cqa.getAverage());
        }
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

        return resultMap;
    }

    @RequestMapping(value="/detete-result/{rid}")
    public @ResponseBody Object deleteResult(@PathVariable int rid){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((BluebookUserDetails)principal).getUsername();
        BluebookUser u = userRepo.findByUsername(username);
        u.deleteUserResultById(rid);
        userRepo.save(u);
        return true;
    }

    @RequestMapping(value="/delete-notif")
    public @ResponseBody Object deleteNotif(@RequestParam int id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((BluebookUserDetails)principal).getUsername();
        BluebookUser u = userRepo.findByUsername(username);
        int temp = -1;
        for(int i = 0; i < u.getNotifications().size(); i++) {
           if(u.getNotifications().get(i).getId() == id) temp = i;
        }
        if(temp > -1){
            u.getNotifications().remove(temp);
        }
        
        userRepo.save(u);
        return true;
    }


}