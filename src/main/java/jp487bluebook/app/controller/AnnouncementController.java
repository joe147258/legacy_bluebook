package jp487bluebook.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp487bluebook.app.domain.Announcement;
import jp487bluebook.app.domain.Classes;
import jp487bluebook.app.domain.Notification;
import jp487bluebook.app.repository.AnnouncementRepository;
import jp487bluebook.app.repository.ClassesRepository;
import jp487bluebook.app.repository.NotificationRepository;
import jp487bluebook.app.repository.UserRepository;
import jp487bluebook.app.service.NotificationService;


@RestController
public class AnnouncementController {

    @Autowired AnnouncementRepository annRepo;
    @Autowired ClassesRepository classRepo;
	@Autowired UserRepository userRepo;
	@Autowired NotificationRepository notifRepo;
	@Autowired NotificationService notifService;
	
    @RequestMapping(value="/addAnnouncement", method = RequestMethod.POST)
	public @ResponseBody Object addAnnouncment
		(@RequestParam Map<String, String> params, @RequestParam int classId) {
		Classes c = classRepo.findById(classId);
		int i = 0;
		while(annRepo.findById(i) != null) i++;
		Announcement a = new Announcement(params.get("annTitle"), params.get("annBody"), i);
		a.setBb_class_ann(c);
		annRepo.save(a);

		int j = 0;
		while(notifRepo.findById(j) != null) j++;
		Notification n = new Notification(j, "Announcement | " + c.getName(), "announcement");
		n.setAnnId(a.getId());
		notifRepo.save(n);
		notifService.sendClassNotification(c, n);

		classRepo.save(c);
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}

	@RequestMapping(value="/del-announcement", method = RequestMethod.GET)
	public @ResponseBody Object delAnnouncement(@RequestParam int annId) {
		Announcement a = annRepo.findById(annId);
		a.setBb_class_ann(null);
		annRepo.save(a);
		annRepo.deleteById(annId);
		HashMap<String, Object> map = new HashMap<>();
		map.put("ret", true);
		return map;
	}
	
	@RequestMapping(value="/edit-announcement", method = RequestMethod.POST)
	public @ResponseBody Object editAnnouncment (
			@RequestParam Map<String, String> stringParams,
			@RequestParam int annId) {
		Announcement a = annRepo.findById(annId);
		a.setTitle(stringParams.get("annTitle"));
		a.setContents(stringParams.get("annContent"));
		annRepo.save(a);
		HashMap<String, Object> map = new HashMap<>();
		map.put("ret", true);
		return map;
	}

	@RequestMapping(value = "/get-announcement")
    public @ResponseBody Object getAnn(@RequestParam int annId) {
		Announcement a = annRepo.findById(annId);
		String[] returnVal = {a.getTitle() , a.getContents()};
		return returnVal;
    } 

}