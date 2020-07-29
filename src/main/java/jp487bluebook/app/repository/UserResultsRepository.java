package jp487bluebook.app.repository;

import org.springframework.data.repository.CrudRepository;

import jp487bluebook.app.domain.UserResults;

public interface UserResultsRepository extends CrudRepository<UserResults, Integer> {
	UserResults findById(int id);

	UserResults deleteById(int id);
}
