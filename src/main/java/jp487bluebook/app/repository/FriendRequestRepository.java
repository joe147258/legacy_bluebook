package jp487bluebook.app.repository;

import org.springframework.data.repository.CrudRepository;


import jp487bluebook.app.domain.FriendRequest;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Integer> {
	FriendRequest findById(int id);
	FriendRequest deleteById(int id);
}
