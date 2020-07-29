package jp487bluebook.app.repository;

import org.springframework.data.repository.CrudRepository;


import jp487bluebook.app.domain.BluebookUser;

public interface UserRepository extends CrudRepository<BluebookUser, Integer> {
	BluebookUser findById(int id);
	BluebookUser findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	BluebookUser deleteById(int id);
}
