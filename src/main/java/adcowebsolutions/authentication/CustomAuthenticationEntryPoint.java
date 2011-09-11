package adcowebsolutions.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	
	private String accessDeniedUrl;
	
	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
		if (exception != null) {
			if (exception instanceof InsufficientAuthenticationException) {
				return getAccessDeniedUrl();
			}
		}
        return getLoginFormUrl();
    }

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}
}
