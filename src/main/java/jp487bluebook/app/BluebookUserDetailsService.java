package jp487bluebook.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.repository.UserRepository;

@Service
public class BluebookUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		BluebookUser user = userRepo.findByUsername(username);

		return new BluebookUserDetails(user);
	}


}
