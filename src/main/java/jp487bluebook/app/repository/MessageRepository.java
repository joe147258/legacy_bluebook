package jp487bluebook.app.repository;

import org.springframework.data.repository.CrudRepository;


import jp487bluebook.app.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
	Message findById(int id);
	Message deleteById(int id);
}
