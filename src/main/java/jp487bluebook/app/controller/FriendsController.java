package jp487bluebook.app.controller;

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
import jp487bluebook.app.domain.Conversation;
import jp487bluebook.app.domain.FriendRequest;
import jp487bluebook.app.domain.Message;
import jp487bluebook.app.domain.Notification;
import jp487bluebook.app.repository.AnnouncementRepository;
import jp487bluebook.app.repository.ConversationRepository;
import jp487bluebook.app.repository.FriendRequestRepository;
import jp487bluebook.app.repository.MessageRepository;
import jp487bluebook.app.repository.NotificationRepository;
import jp487bluebook.app.repository.UserRepository;
import jp487bluebook.app.service.NotificationService;

@Controller
public class FriendsController {

    @Autowired UserRepository userRepo;
    @Autowired AnnouncementRepository annRepo;
    @Autowired FriendRequestRepository friendRepo;
    @Autowired ConversationRepository convoRepo;
    @Autowired MessageRepository messageRepo;
    @Autowired NotificationRepository notifRepo;
	@Autowired NotificationService notifService;

    @RequestMapping(value = "/friends-page")
    public String friendsPage(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((BluebookUserDetails)principal).getUsername();
        BluebookUser u = userRepo.findByUsername(username);
        model.addAttribute("friends", u.getFriends());
        model.addAttribute("friendReqs", u.getFriendRequests());
        model.addAttribute("userId", u.getId());
        model.addAttribute("username", u.getUsername());
        model.addAttribute("numberOfRequests", u.getFriendRequests().size());
        model.addAttribute("convos", u.getConversations());
        model.addAttribute("socialNumber", u.getFriendRequests().size());
        return "friends-page";
    }



    @RequestMapping(value = "/send-request/{recepient}")
    public @ResponseBody Object sendRequest(@PathVariable String recepient){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((BluebookUserDetails)principal).getUsername();
        BluebookUser u = userRepo.findByUsername(recepient);
        BluebookUser sender = userRepo.findByUsername(username);
        if(!u.getFriends().contains(sender) && !sender.getFriends().contains(u) && !recepient.equalsIgnoreCase(username)){
            for(FriendRequest fr : u.getFriendRequests()){
                if(fr.getSender().getUsername().equals(username)) return "failure";
                if(fr.getRecepient().getUsername().equals(recepient)) return "failure";
            }
            int i = 0;
            while(notifRepo.findById(i) != null) i++;
            Notification n = new Notification(i, "Friend Request from " + username, "request");
            notifRepo.save(n);
            notifService.sendPersonalNotification(n, u);
            int j = 0;
            while(friendRepo.findById(j) != null) j++;
            FriendRequest req = new FriendRequest(j, sender, u);
            u.getFriendRequests().add(req);
            userRepo.save(u);
            return "success";
        } else return "failure";
    }

    @RequestMapping(value = "/accept-friend")
    public @ResponseBody Object acceptFriend(@RequestParam String sender){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((BluebookUserDetails)principal).getUsername();

        BluebookUser reciever = userRepo.findByUsername(username);
        BluebookUser senderUser = userRepo.findByUsername(sender);

        reciever.getFriends().add(senderUser);
        senderUser.getFriends().add(reciever);

        FriendRequest fr = reciever.getFriendRequestBySender(sender);
        if(fr!=null) {
            reciever.removeFriendRequestBySender(sender);
            senderUser.removeSentRequestByRecpient(username);

            fr.setRecepient(null);
            fr.setSender(null);
        }
       // friendRepo.save(fr);
        userRepo.save(reciever);
        userRepo.save(senderUser);
        return "success";
    }

    @RequestMapping(value = "/deny-friend")
    public @ResponseBody Object denyFriend(@RequestParam String sender){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((BluebookUserDetails)principal).getUsername();

        BluebookUser reciever = userRepo.findByUsername(username);
        BluebookUser senderUser = userRepo.findByUsername(sender);

        FriendRequest fr = reciever.getFriendRequestBySender(sender);
        if(fr!=null) {
            reciever.removeFriendRequestBySender(sender);
            senderUser.removeSentRequestByRecpient(username);

            fr.setRecepient(null);
            fr.setSender(null);
        }

        userRepo.save(reciever);
        userRepo.save(senderUser);
        return "success";
    }
    @RequestMapping(value = "/remove-friend/{id}")
    public @ResponseBody Object removeFriend(@PathVariable int id){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((BluebookUserDetails)principal).getUsername();
        BluebookUser u = userRepo.findByUsername(username);
        BluebookUser u1 = userRepo.findById(id);
        u.removeFriendById(id);
        u1.removeFriendById(u.getId());
        userRepo.save(u);
        userRepo.save(u1);

        return true;
    }
    @RequestMapping(value = "/new-conversation/{username}/{contents}")
    public @ResponseBody Object newConvo(@PathVariable String username, @PathVariable String contents){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String senderUsername = ((BluebookUserDetails)principal).getUsername();
        
        
        BluebookUser reciever = userRepo.findByUsername(username);
        BluebookUser sender = userRepo.findByUsername(senderUsername);

        int tempId = 0;
        while(messageRepo.findById(tempId) != null) tempId++;
        Message m = new Message(tempId, sender, contents);

        tempId = 0;
        while(convoRepo.findById(tempId) != null) tempId++;
        Conversation convo = new Conversation(tempId, reciever, sender, m);

        m.setConvo(convo);
        messageRepo.save(m);
        convoRepo.save(convo);

        tempId = 0;
        while(notifRepo.findById(tempId) != null) tempId++;
        Notification n = new Notification(tempId, "Message from " + senderUsername, "msg");
        n.setAttachedUsername(senderUsername);
        notifRepo.save(n);
        notifService.sendPersonalNotification(n, reciever);

        return true;
    }

    @RequestMapping(value = "/send-message/{convoId}/{contents}")
    public @ResponseBody Object sendMessage(@PathVariable int convoId, @PathVariable String contents) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String senderUsername = ((BluebookUserDetails)principal).getUsername();

        BluebookUser sender = userRepo.findByUsername(senderUsername);
        Conversation c = convoRepo.findById(convoId);
        
        int tempId = 0;
        while(messageRepo.findById(tempId) != null) tempId++;
        Message m = new Message(tempId, sender, contents);
        m.setConvo(c);
        c.getMessages().add(m);
        c.setLatestMessage(m);
        messageRepo.save(m);
        convoRepo.save(c);
        
        tempId = 0;
        while(notifRepo.findById(tempId) != null) tempId++;
        Notification n = new Notification(tempId, "Message from " + senderUsername, "msg");
        n.setAttachedUsername(senderUsername);
        notifRepo.save(n);
        BluebookUser reciever = null;
        for(BluebookUser u : c.getMembers()){
            if(!u.getUsername().equals(senderUsername)) reciever = u;
        }
        notifService.sendPersonalNotification(n, reciever);

        return true;
    }
    
}