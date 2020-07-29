package jp487bluebook.app.repository;

import org.springframework.data.repository.CrudRepository;

import jp487bluebook.app.domain.ClassQuizAverage;
import jp487bluebook.app.domain.Classes;

public interface ClassQuizAverageRepository extends CrudRepository<ClassQuizAverage, Integer> {

    ClassQuizAverage findById(int id);
    ClassQuizAverage deleteById(int id);
    ClassQuizAverage findByQuizId(int quizId);
    ClassQuizAverage findByRelatedClass(Classes c);
}

