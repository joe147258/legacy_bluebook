package jp487bluebook.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp487bluebook.app.domain.BluebookUser;
import jp487bluebook.app.domain.Classes;

public class BluebookUserDetails implements UserDetails {

	private static final long serialVersionUID = -6205068871475377605L;
	private String userName;
	private String password;
	private String firstName;
	private int UserId;
	private int userScore;
	private List<Classes> classes = new ArrayList<Classes>();
	
	
	public BluebookUserDetails (BluebookUser user) {
		userName = user.getUsername();
		password = user.getPassword();
		firstName = user.getFirstName();
		UserId = user.getId();
		classes = user.getClasses();
		userScore = user.getUserScore();
	} 
	public BluebookUserDetails () {

	} 
	
	public int getUserScore() {
		return userScore;
	}
	public void setUserScore(int userScore) {
		this.userScore = userScore;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		return authorities;
		
	}

	public List<Classes> getClasses() {
		return classes;
	}
	public void setClasses(ArrayList<Classes> classes) {
		this.classes = classes;
	}
	public int getUserId() {
		return UserId;
	}
	
	public void setUserId(int userId) {
		UserId = userId;
	}
	
	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}
	public String getFirstName() {

		return firstName;
	}


}
