package jp487bluebook.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jp487bluebook.app.domain.Quizzes;



public interface QuizRepository extends CrudRepository<Quizzes, Integer> {
	
	Quizzes findById(int id);
	Quizzes deleteById(int id);

	@Query("select q from Quizzes q where owner_id = :ownerid")
	List<Quizzes> findAllByOwnerId(@Param("ownerid") int ownerid);

	@Query("select q from Quizzes q where isPublic = true and hidden = false")
	List<Quizzes> findAllPublic();
}