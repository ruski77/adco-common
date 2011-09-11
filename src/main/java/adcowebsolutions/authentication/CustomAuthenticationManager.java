package adcowebsolutions.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import adcowebsolutions.crypto.PasswordManager;
import adcowebsolutions.dao.AccountDao;
import adcowebsolutions.domain.Account;
import adcowebsolutions.utils.Interpolator;
import adcowebsolutions.utils.ResourceLoader;

/**
 * A custom authentication manager that allows access if the user details
 * exist in the database and if the user name and password are not the same.
 * Otherwise, throw a {@link BadCredentialsException}
 */
public class CustomAuthenticationManager extends Authenticator implements AuthenticationManager {
	
	private Logger log = LoggerFactory.getLogger(CustomAuthenticationManager.class);
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private PasswordManager passwordManager;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.debug("Performing custom authentication");
		
		if (authentication == null) {
			throw new BadCredentialsException(ResourceLoader.getBundleMessage("login_id_password_required"));
		}
		
		if (authentication.getName() == null || authentication.getName().trim().length() == 0) {
			throw new BadCredentialsException(ResourceLoader.getBundleMessage("login_id_password_required"));
		}
		
		if (authentication.getCredentials() == null || ((String)authentication.getCredentials()).trim().length() == 0) {
			throw new BadCredentialsException(ResourceLoader.getBundleMessage("login_id_password_required"));
		}

		Account account = new Account(authentication.getName().toUpperCase());
		
		try {
			// Retrieve user details from database
			account = getAccountDao().get(account);
		} catch (Exception e) {
			log.error("Could  authenticate user due to: {}", e.getMessage());
			throw new BadCredentialsException(Interpolator.instance().interpolate(ResourceLoader.getBundleMessage("login_failed_error"), e.getMessage()));
		}
		
		if (account == null) {
			log.error("User {} not found", authentication.getName());
			throw new BadCredentialsException(ResourceLoader.getBundleMessage("login_validation_failed"));
		} else {
			if (!account.getEnabled()) {
				throw new BadCredentialsException(ResourceLoader.getBundleMessage("login_disabled"));
			}
			
			if (account.getRoles() == null || account.getRoles().isEmpty()) {
				throw new BadCredentialsException(ResourceLoader.getBundleMessage("login_failed_no_permissions"));
			}
			
			if (passwordManager.isPasswordValid(account.getPassword(), (String) authentication.getCredentials()) == false) {
				log.error("Wrong password entered for {}", account.getUserName());
				throw new BadCredentialsException(ResourceLoader.getBundleMessage("login_validation_failed"));
			}
			
			log.debug("User {} passed login validation", account.getUserName());
			return new UsernamePasswordAuthenticationToken(
					authentication.getName(), 
					authentication.getCredentials(), 
					createGrantedAuthoritiesList(account.getRoles()));
		}
	}

	public PasswordManager getPasswordManager() {
		return passwordManager;
	}

	public void setPasswordManager(PasswordManager passwordManager) {
		this.passwordManager = passwordManager;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}
}
