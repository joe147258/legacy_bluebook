package jp487bluebook.app.repository;

import org.springframework.data.repository.CrudRepository;

import jp487bluebook.app.domain.Questions;
import jp487bluebook.app.domain.Quizzes;



public interface QuestionRepository extends CrudRepository<Questions, Integer> {
	
	Questions findById(int id);
	Questions findById(Quizzes quiz);
	Questions deleteById(int id);
}