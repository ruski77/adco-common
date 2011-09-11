package adcowebsolutions.authentication;

import static adcowebsolutions.constants.GlobalConstants.CONST_ACCOUNT_SESSION;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import adcowebsolutions.domain.Account;
import adcowebsolutions.service.AccountService;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
	
	@Autowired
	private AccountService accountService;
	
    @Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		try {
			HttpSession session = request.getSession();
			String userName = authentication.getName().toUpperCase();
			
			Account account = accountService.get(new Account(userName));
			account.setLastLoginDate(account.getLastLoginDate());
			account.setPassword("");
			session.setAttribute(CONST_ACCOUNT_SESSION, account);
			
			accountService.updateLoginCountAndDate(userName);
		} catch (Exception e) {
			log.error("Could not update user details on login due to: {}", e.getMessage());
			e.printStackTrace();
		}
    	
    	super.onAuthenticationSuccess(request, response, authentication);
    }
}
