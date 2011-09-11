package adcowebsolutions.web.flow;

import static adcowebsolutions.constants.GlobalConstants.FAILURE;
import static adcowebsolutions.constants.GlobalConstants.SUCCESS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import adcowebsolutions.beans.PasswordBean;
import adcowebsolutions.crypto.PasswordManager;
import adcowebsolutions.domain.Account;
import adcowebsolutions.service.AccountService;

@Component("registerController")
public class RegisterController extends GenericFlowController {
	
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private PasswordManager passwordManager;
	
	private AccountService accountService;
	
	private Account account = new Account();
	
	private PasswordBean passwordBean = new PasswordBean();
	
	public RegisterController() {}
	
	@Autowired
	public RegisterController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	public String register() {
		
		log.debug("Registering user {}", getAccount());
		
		String forward = FAILURE;
		
		try {
			//Does password and verify password match?
			if (!passwordManager.passwordsMatch(passwordBean)) { 
				setFailureMessage("passwords_no_match");
				return forward;
			}
			
			//user name and password should not be the same
			if (passwordManager.passwordAndUserIdMatch(getAccount().getUserName(), passwordBean)) {
				setFailureMessage("userid_password_same");
				return forward;
			}
			
			//check if password meets minimum length requirements
			if (!passwordManager.isValidLength(passwordBean.getPassword())) {
				setFailureMessage("password_invalid_length", passwordManager.getLength());
				return forward;
			}	
			
			Account existingAccount = accountService.findUniqueOrNone(account);
			if (existingAccount == null) { //User doesn't exist
				existingAccount = accountService.getByEmail(account.getEmail());
				if (existingAccount == null) { //Email address not in system
					account.setPassword(passwordBean.getPassword());
					accountService.create(account);
					setSuccessMessage("success_register");
					//send login details to new user
					sendRegistrationEmail(account, passwordBean.getPassword());
					forward = SUCCESS;
					reset();
				} else { //A user with entered email already exists
					setFailureMessage("user_email_not_avail");
				}
			} else { //User id already exists
				setFailureMessage("user_id_not_avail");
			}
		} catch (Exception e) {
			log.error("Failed to register user due to: {}", e.getMessage());
			setErrorMessage("error_register_user", e.getMessage());
			notifySupport(e);
		}
		
		return forward;
	}
	
	private void sendRegistrationEmail(Account account, String password) {
		try {
			emailSendingService.sendRegistrationEmail(account, password);
		} catch (Exception e) {
			setErrorMessage("error_register_user_email", e.getMessage());
			notifySupport(e);
		}
	}
	
	@Override
	public void reset() {
		this.setAccount(new Account());
		this.setPasswordBean(new PasswordBean());
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setPasswordBean(PasswordBean passwordBean) {
		this.passwordBean = passwordBean;
	}

	public PasswordBean getPasswordBean() {
		return passwordBean;
	}
}
