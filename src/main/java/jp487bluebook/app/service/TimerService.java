package jp487bluebook.app.service;

import java.util.ArrayList;

import java.util.Date;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jp487bluebook.app.domain.Notification;
import jp487bluebook.app.domain.Quizzes;
import jp487bluebook.app.repository.AnnouncementRepository;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;
import jp487bluebook.app.utilities.taskUtil;

@Service
public class TimerService {


    private ArrayList<taskUtil> tasks = new ArrayList<taskUtil>();
    private Timer timer = new Timer();

    @Autowired QuizRepository quizRepo;
    @Autowired AnnouncementRepository annRepo;
    @Autowired UserRepository userRepo;
    @Autowired NotificationService notifService;

    public void scheduleQuiz(int qid, Notification n, String schedVis){
        for(taskUtil tu : tasks){
            if(tu.getQuizId() == qid){
                if(tu.getHidden() == false){
                    Quizzes q = quizRepo.findById(qid);
                    q.setHidden(false);
                    if(schedVis.equals("everyone")) q.setIsPublic(true);
                    notifService.sendClassNotification(q.getBb_class(), n);
                    q.setScheduled(false);
                    q.setScheduledFor(null);
                    quizRepo.save(q);
                    tu.setQuizId(null);
                    tasks.remove(tu);
                    break;
                } else {
					tu.setQuizId(null);
                    tasks.remove(tu);
					break;
				}
            }
        }
    }

    public void schedule(TimerTask task, Date d, int quizId) {
		Quizzes q = quizRepo.findById(quizId);
		for(taskUtil tu : tasks) {
            if(tu.getQuizId() == quizId){

				if(d.before(q.getScheduledFor())){
                    tu.setQuizId(null);
                    tasks.remove(tu);
                    break;
                } else {
                    tu.setHidden(true);
                }
            }
        }
		
		taskUtil tu = new taskUtil(false, quizId);
        tasks.add(tu);
        timer.schedule(task, d);
    }






}

