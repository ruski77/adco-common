package adcowebsolutions.authentication;

import static adcowebsolutions.constants.GlobalConstants.CONST_ACCOUNT_SESSION;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

public class CustomLogoutHandler implements LogoutHandler {
	
	private boolean invalidateHttpSession = true;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Assert.notNull(request, "HttpServletRequest required");
		HttpSession session = request.getSession(false);
        if (session != null) {
        	session.removeAttribute(CONST_ACCOUNT_SESSION);
        	if (isInvalidateHttpSession()) {
        		 session.invalidate();
            }
        }
        
        SecurityContextHolder.clearContext();
	}

	public void setInvalidateHttpSession(boolean invalidateHttpSession) {
		this.invalidateHttpSession = invalidateHttpSession;
	}

	public boolean isInvalidateHttpSession() {
		return invalidateHttpSession;
	}
}
