package jp487bluebook.app.repository;

import org.springframework.data.repository.CrudRepository;

import jp487bluebook.app.domain.Conversation;

public interface ConversationRepository extends CrudRepository<Conversation, Integer> {
	Conversation findById(int id);
	Conversation deleteById(int id);
}
