package adcowebsolutions.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import adcowebsolutions.model.WebUser;
import adcowebsolutions.service.UserService;

@Component("register")
@Scope("request")
public class Register {
	
private Logger log = LoggerFactory.getLogger(Register.class);
	
	private UserService userService;
	
	private WebUser webUser = new WebUser();
	
	private String verifyPassword;
	
	public Register() {}
	
	@Autowired
	public Register(UserService userService) {
		this.userService = userService;
	}
	
	public String register() {
		
		log.debug("Registering user {}", webUser);
		
		String returnPage = "/login.jsf";
		
		try {
			if (passwordsMatch()) {
				userService.create(webUser);
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Success, please login!");
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			} else {
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", "Password do not match");
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
				returnPage = "/register.jsf";
			}
			
			reset();
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "an error occured");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			e.printStackTrace();
			returnPage = "/register.jsf";
		}
		
		return returnPage;
		//return "/login.jsf?faces-redirect=true";
	}

	private boolean passwordsMatch() {
		if (webUser.getPassword() == null || webUser.getPassword().length() == 0) {
			return false;
		}
		if (getVerifyPassword() == null || getVerifyPassword().length() == 0) {
			return false;
		}
		
		if (webUser.getPassword().equals(getVerifyPassword())) {
			return true;
		}
		return false;
	}

	public WebUser getWebUser() {
		return webUser;
	}

	public void setWebUser(WebUser webUser) {
		this.webUser = webUser;
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}
	
	public void reset() {
		this.webUser = new WebUser();
	}
	
	public void cancel() {
		reset();
	}

}
