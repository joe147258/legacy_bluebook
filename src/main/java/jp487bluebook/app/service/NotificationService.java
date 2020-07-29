package jp487bluebook.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.Classes;
import jp487bluebook.app.domain.Notification;
import jp487bluebook.app.repository.UserRepository;


@Service
public class NotificationService {
    @Autowired UserRepository userRepo;

    public void sendClassNotification(Classes c, Notification n){
        for(BluebookUser u : c.getStudents()){
            u.addNotification(n);
            userRepo.save(u);
        }
    }

    public void sendPersonalNotification(Notification n, BluebookUser u){
        u.addNotification(n);
        userRepo.save(u);
    }
    





}

