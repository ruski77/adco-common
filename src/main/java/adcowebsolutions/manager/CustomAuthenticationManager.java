package adcowebsolutions.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import adcowebsolutions.crypto.PasswordManager;
import adcowebsolutions.dao.UserDAO;
import adcowebsolutions.model.WebUser;
import adcowebsolutions.model.WebUserRole;

/**
 * A custom authentication manager that allows access if the user details
 * exist in the database and if the user name and password are not the same.
 * Otherwise, throw a {@link BadCredentialsException}
 */
public class CustomAuthenticationManager implements AuthenticationManager {
	
	private Logger log = LoggerFactory.getLogger(CustomAuthenticationManager.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordManager passwordManager;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.debug("Performing custom authentication");
		
		//TODO - check authenication is not null or empty values, throw badcredentials exception
		
		// Init a database user object
		WebUser user = null;
		
		try {
			// Retrieve user details from database
			user = userDAO.findById(authentication.getName());
		} catch (Exception e) {
			log.error("User does not exists!");
			throw new BadCredentialsException("User does not exists!");
		}
		
		// Compare passwords
		// Make sure to encode the password first before comparing
		if (passwordManager.isPasswordValid(user.getPassword(), (String) authentication.getCredentials()) == false ) {
			log.error("Wrong password!");
			throw new BadCredentialsException("Wrong password!");
		}
		
		// Here's the main logic of this custom authentication manager
		// Username and password must be the same to authenticate
		if (authentication.getName().equals(authentication.getCredentials()) == true) {
			log.debug("Entered username and password are the same!");
			throw new BadCredentialsException("Entered username and password are the same!");
			
		} else {
			log.debug("User details are good and ready to go");
			return new UsernamePasswordAuthenticationToken(
					authentication.getName(), 
					authentication.getCredentials(), 
					createGrantedAuthoritiesList(user.getRoles()));
		}
	}
	
	private List<GrantedAuthority> createGrantedAuthoritiesList(List<WebUserRole> authorities) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		Iterator<WebUserRole> i = authorities.iterator();
		while (i.hasNext()) {
			WebUserRole role = i.next();
			grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_" + role.getRoleName()));
		}
		return grantedAuthorities;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public PasswordManager getPasswordManager() {
		return passwordManager;
	}

	public void setPasswordManager(PasswordManager passwordManager) {
		this.passwordManager = passwordManager;
	}
}
