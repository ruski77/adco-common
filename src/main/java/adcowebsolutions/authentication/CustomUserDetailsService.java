package adcowebsolutions.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import adcowebsolutions.dao.AccountDao;
import adcowebsolutions.domain.Account;
import adcowebsolutions.utils.Interpolator;
import adcowebsolutions.utils.ResourceLoader;

public class CustomUserDetailsService extends Authenticator implements UserDetailsService {
	
	private Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private AccountDao accountDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		log.debug("Getting user {} for remember me", username);
		
		try {
			Account account = new Account(username.toUpperCase());
			account = accountDao.get(account);
			return createUserDetails(account);
		} catch (Exception e) {
			log.error("Could not load user due to: {}", e.getMessage());
			throw new BadCredentialsException(Interpolator.instance().interpolate(ResourceLoader.getBundleMessage("login_failed_error"), e.getMessage()));
		}
	}

	private UserDetails createUserDetails(Account account) {
		AccountUserDetails accountUserDetails = new AccountUserDetails();
		
		if (account != null) {
			accountUserDetails.setUsername(account.getUserName());
			accountUserDetails.setPassword(account.getPassword());
			accountUserDetails.setEnabled(account.getEnabled());
			accountUserDetails.setAccountNonExpired(true);
			accountUserDetails.setAccountNonLocked(true);
			accountUserDetails.setCredentialsNonExpired(true);
			accountUserDetails.setAuthorities(createGrantedAuthoritiesList(account.getRoles()));
		} else {
			accountUserDetails.setUsername("");
			accountUserDetails.setPassword("");
		}
		
		return accountUserDetails;
	}
}
