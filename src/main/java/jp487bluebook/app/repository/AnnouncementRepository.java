package jp487bluebook.app.repository;

import org.springframework.data.repository.CrudRepository;

import jp487bluebook.app.domain.Announcement;

public interface AnnouncementRepository extends CrudRepository<Announcement, Integer> {
	Announcement findById(int id);
	
	Announcement deleteById(int id);
	

	
}
