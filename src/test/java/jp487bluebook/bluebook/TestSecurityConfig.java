package jp487bluebook.bluebook;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp487bluebook.app.BluebookUserDetails;

import jp487bluebook.app.domain.BluebookUser;

import jp487bluebook.app.repository.ClassesRepository;
import jp487bluebook.app.repository.QuizRepository;
import jp487bluebook.app.repository.UserRepository;

@TestConfiguration
public class TestSecurityConfig implements UserDetailsService {
    @Autowired
    QuizRepository quizRepo;
    @Autowired
    ClassesRepository classRepo;
    @Autowired
    UserRepository userRepo;
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService(){
            
            @Override
            public BluebookUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                BluebookUser user = new BluebookUser();
                user.setUsername("VergeofEden");
                user.setFirstName("Ben");
                user.setLastName("Harold");
                user.setEmail("bob@bob.com");
                user.setId(-1);
                user.setUserScore(0);
                user.setPassword("test");

                BluebookUserDetails bbud = new BluebookUserDetails(user);
                return bbud;
            }
        };
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
