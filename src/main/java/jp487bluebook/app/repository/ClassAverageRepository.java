package jp487bluebook.app.repository;




import org.springframework.data.repository.CrudRepository;

import jp487bluebook.app.domain.ClassResult;


public interface ClassAverageRepository extends CrudRepository<ClassResult, Integer> {
	ClassResult findById(int id);
	ClassResult deleteById(int id);

	
}
