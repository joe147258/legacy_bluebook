package jp487bluebook.app.repository;

import org.springframework.data.repository.CrudRepository;
import jp487bluebook.app.domain.Classes;

public interface ClassesRepository extends CrudRepository<Classes, Integer> {
	Classes findById(int id);
	Classes deleteById(int id);
	

	
}
