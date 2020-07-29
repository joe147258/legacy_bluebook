package jp487bluebook.app.repository;

import org.springframework.data.repository.CrudRepository;

import jp487bluebook.app.domain.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Integer>{
    Notification findById(int id);
	Notification deleteById(int id);
}