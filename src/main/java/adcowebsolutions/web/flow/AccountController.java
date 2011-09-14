package adcowebsolutions.web.flow;

import static adcowebsolutions.constants.GlobalConstants.CONST_NO;
import static adcowebsolutions.constants.GlobalConstants.CONST_SUPPORT;
import static adcowebsolutions.constants.GlobalConstants.FAILURE;
import static adcowebsolutions.constants.GlobalConstants.SUCCESS;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.el.ValueExpression;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.faces.webflow.FlowFacesContext;
import org.springframework.stereotype.Component;

import adcowebsolutions.beans.PasswordBean;
import adcowebsolutions.crypto.PasswordManager;
import adcowebsolutions.domain.Account;
import adcowebsolutions.domain.AccountRole;
import adcowebsolutions.enums.Role;
import adcowebsolutions.service.AccountRoleService;
import adcowebsolutions.service.AccountService;

@Component("accountController")
@ViewScoped
public class AccountController extends GenericFlowController {
	
	private static final long serialVersionUID = 1L;
	
	private Logger log = LoggerFactory.getLogger(AccountController.class);
	
	private AccountService accountService;
	
	private AccountRoleService accountRoleService;
	
	private Account selectedAccount;
	
	private StreamedContent streamedAvatar;
	
	private List<Account> accounts;
	
	private DualListModel<String> roleNames;
	
	private PasswordBean passwordBean = new PasswordBean();
	
	@Autowired
	private PasswordManager passwordManager;
	
	@Autowired
	public AccountController(AccountService accountService, AccountRoleService accountRoleService) {
		this.accountService = accountService;
		this.accountRoleService = accountRoleService;
	}
	
	public void loadAccounts() {
		log.debug("loadAccounts()");
		boolean messageFound = false;

		try {
			accounts = accountService.findAll();
			
			if (getCurrentRequest() != null) {
				String successUpdateMessage = getCurrentRequest().getParameter("successUpdate");
				if (successUpdateMessage != null && successUpdateMessage.length() > 0) {
					setSuccessMessage("success_update_user");
					messageFound = true;
				}
				
				if (!messageFound) {
					String successCreateMessage = getCurrentRequest().getParameter("successCreate");
					if (successCreateMessage != null && successCreateMessage.length() > 0) {
						setSuccessMessage("success_created_user");
					}
				}
			}
		} catch (Exception e) {
			log.error("Error retrieving user accounts due to: {}", e.getMessage());
			e.printStackTrace();
			setErrorMessage("error_view_users", e.getMessage());
			notifySupport(e);
		}
	}
	
	public StreamedContent getStreamedAvatar() {
		log.debug("getStreamedAvatar()");
		
		try {  
			String userName = getCurrentRequest().getParameter("userName");
			log.debug("getStreamedAvatar() userName param = {}", userName);
			
			if (userName == null || userName.length() == 0) {
				log.debug("getStreamedAvatar() userName from param is null, checking UIComponents...");
				List<UIComponent> components = UIComponent.getCurrentComponent(FlowFacesContext.getCurrentInstance()).getChildren();
				if (components != null) {
					for (UIComponent component : components) {
						if (component.getId().equals("userName")) {
							ValueExpression ve = component.getValueExpression("value");
							userName  = (String) ve.getValue(FlowFacesContext.getCurrentInstance().getELContext());
							log.debug("getStreamedAvatar() userName from UIComponent = {}", userName);
						}
					}
				}
			}

			if (userName != null && userName.length() > 0) {
				if (accounts != null) {
					for (Account account : accounts) {	
						if (account.getUserName().equals(userName)) {
							ByteArrayInputStream stream = new ByteArrayInputStream(account.getAvatar());
							streamedAvatar = new DefaultStreamedContent(stream, account.getAvatarMimeType());
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error setting streamed avatar content due to: {}", e.getMessage());
			e.printStackTrace();
			notifySupport(e);
		}
		
		return streamedAvatar;	
	}
	
	public String deleteAvatar() {
		log.debug("deleteAvatar() for Account = {}", getSelectedAccount() != null ? getSelectedAccount().getUserName() : "null");
		
		String forward = SUCCESS;
		
		try {
			Account account = accountService.get(getSelectedAccount());
			account.setAvatar(null);
			account.setAvatarMimeType(null);
			accountService.update(account);
			loadAccounts();
			setSuccessMessage("success_delete_avatar", getSelectedAccount().getUserName());
		} catch (Exception e) {
			log.error("Error deleting avatar for {} due to: {}", getSelectedAccount().getUserName(), e.getMessage());
			e.printStackTrace();
			setErrorMessage("failure_delete_avatar", e.getMessage());
			notifySupport(e);
			forward = FAILURE;
		}
		
		return forward;
	}

	public void resetPassword() {
		log.debug("resetPassword()");
		//TODO
	}
	
	public String edit(Account account) {
		log.debug("edit() account = {}", account != null ? account.getUserName() : "null");
		
		String forward = SUCCESS;
		
		try {
			if (account != null) {
				if (passwordBean.getPassword() != null && passwordBean.getPassword().length() > 0) {
					
					if (!validPassword(account.getUserName())) {
						return FAILURE;
					}
	
					//password present and valid.. update it!
					account.setPassword(passwordManager.encodePassword(passwordBean.getPassword()));
					account.setUpdatePassword(CONST_NO);
				} 
				
				//Continue with update
				accountRoleService.deleteByUserName(account.getUserName());
				account.setRoles(null);
				addRolesToAccount(account);
				accountService.update(account);
				reset();
			} else {
				setFailureMessage("failure_update_user_null");
				forward = FAILURE;
			}
		} catch (Exception e) {
			log.error("Error updating user details for {} due to: {}", account.getUserName(), e.getMessage());
			e.printStackTrace();
			setErrorMessage("failure_update_user",  account.getUserName(), e.getMessage());
			notifySupport(e);
			forward = FAILURE;
		}
		
		return forward;
	}
	
	public String create(Account account) {
		log.debug("create() account = {}", account != null ? account.getUserName() : "null");
		
		String forward = FAILURE;
		
		try {
			if (account != null) {
				if (!validPassword(account.getUserName())) {
					return forward;
				}
				
				Account existingAccount = accountService.findUniqueOrNone(account);
				if (existingAccount == null) { //User doesn't exist
					existingAccount = accountService.getByEmail(account.getEmail());
					if (existingAccount == null) { //Email address not in system
						account.setPassword(passwordBean.getPassword());
						addRolesToAccount(account);
						accountService.create(account);
						//send login details to new user
						sendAccountCreationEmail(account, passwordBean.getPassword());
						reset();
						forward = SUCCESS;
					} else { //A user with entered email already exists
						setFailureMessage("user_email_not_avail");
					}
				} else { //User id already exists
					setFailureMessage("user_id_not_avail");
				}
			} else {
				setFailureMessage("failure_update_user_null");
			}
		} catch (Exception e) {
			log.error("Error creating user details for {} due to: {}", account.getUserName(), e.getMessage());
			e.printStackTrace();
			setErrorMessage("failure_saving_user",  account.getUserName(), e.getMessage());
			notifySupport(e);
			forward = FAILURE;
		}
		
		return forward;
	}
	
	public void delete() {
		log.debug("delete() for Account = {}", getSelectedAccount() != null ? getSelectedAccount().getUserName() : "null");
		
		try {
			if (getSelectedAccount() != null) {
				if (!CONST_SUPPORT.equals(getSelectedAccount().getUserName())) {
					accountService.delete(getSelectedAccount());
					loadAccounts();
					setSuccessMessage("success_delete_account", getSelectedAccount().getUserName());
					reset();
				} else {
					setFailureMessage("cannot_delete_user", getSelectedAccount().getUserName());
				}
			} else {
				setFailureMessage("failure_update_user_null");
			}
		} catch (Exception e) {
			log.error("Error deleting account {} due to: {}", getSelectedAccount().getUserName(), e.getMessage());
			e.printStackTrace();
			setErrorMessage("failure_delete_account", getSelectedAccount().getUserName(),  e.getMessage());
			notifySupport(e);
		}
	}
	
	public void updateStatus() {
		log.debug("updateStatus() for Account = {}", getSelectedAccount() != null ? getSelectedAccount().getUserName() : "null");
		
		boolean enabling = false;
		
		try {
			if (getSelectedAccount() != null) {
				if (!CONST_SUPPORT.equals(getSelectedAccount().getUserName())) {
					Account account = accountService.get(getSelectedAccount());
					if (account != null) {
						if (getSelectedAccount().getEnabled()) {
							enabling = false;
							account.setEnabled(false); //disable the account
						} else {
							enabling = true;
							account.setEnabled(true); //enable the account
						}
						accountService.update(account);
						loadAccounts();
						if (enabling) {
							setSuccessMessage("success_enabled_user", account.getUserName());
						} else {
							setSuccessMessage("success_disabled_user", account.getUserName());
						}
					}
					reset();
				} else {
					if (getSelectedAccount().getEnabled()) {
						setFailureMessage("cannot_disable_user", getSelectedAccount().getUserName());
					}
				}
			} else {
				setFailureMessage("failure_update_user_null");
			}
		} catch (Exception e) {
			log.error("Error updating status for account {} due to: {}", getSelectedAccount().getUserName(), e.getMessage());
			e.printStackTrace();
			setErrorMessage("failure_update_user", getSelectedAccount().getUserName(),  e.getMessage());
			notifySupport(e);
		}
	}
	
	public Account loadUserForEdit() {
		populateRoleValues();
		setPasswordBean(new PasswordBean());
		return getSelectedAccount();
	}
	
	public Account loadUserForCreation() {
		populateRoleValues();
		setPasswordBean(new PasswordBean());
		return new Account();
	}
	
	protected void addRolesToAccount(Account account) {
		if (getRoleNames() != null) {
			List<String> targetRoles = getRoleNames().getTarget();
			AccountRole userRole = null;
			List<AccountRole> roles = new ArrayList<AccountRole>();
		
			for (String role : targetRoles) {
				userRole = new AccountRole();
				userRole.setRoleName(role);
				userRole.setAccount(account);
				roles.add(userRole);
			}
			account.setRoles(roles);	
		}
	}
	
	protected void populateRoleValues() {
		List<String> roleNameSource = new ArrayList<String>();  
        List<String> roleNameTarget = new ArrayList<String>();  
        
        Set<String> allRoles = new HashSet<String>();
        
        for (Role role : Role.values()) {
        	allRoles.add(role.name());
        }
        
        if (getSelectedAccount() != null && getSelectedAccount().isPersisted()) {
        	List<AccountRole> roles = getSelectedAccount().getRoles();
        	Iterator<AccountRole> i = roles.iterator();
        	while (i.hasNext()) {
        		String roleName = i.next().getRoleName();
        		roleNameTarget.add(roleName);
        		allRoles.remove(roleName);
        	}  	
        }
        
        Iterator<String> roleItr = allRoles.iterator();
        while (roleItr.hasNext()) {
        	roleNameSource.add(roleItr.next());
        }
  
        roleNames = new DualListModel<String>(roleNameSource, roleNameTarget);  
	}
	
	private boolean validPassword(String userName) {
		
		//Does password and verify password match?
		if (!passwordManager.passwordsMatch(passwordBean)) { 
			setFailureMessage("passwords_no_match");
			return false;
		}
		
		//user name and password should not be the same
		if (passwordManager.passwordAndUserIdMatch(userName, passwordBean)) {
			setFailureMessage("userid_password_same");
			return false;
		}
		
		//check if password meets minimum length requirements
		if (!passwordManager.isValidLength(passwordBean.getPassword())) {
			setFailureMessage("password_invalid_length", passwordManager.getLength());
			return false;
		}	
		
		return true;
	}
	
	private void sendAccountCreationEmail(Account account, String password) {
		try {
			emailSendingService.sendRegistrationEmail(account, password);
		} catch (Exception e) {
			setErrorMessage("error_register_user_email", e.getMessage());
			notifySupport(e);
		}
	}
	
	public DualListModel<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(DualListModel<String> roleNames) {
		this.roleNames = roleNames;
	}
	
	public PasswordBean getPasswordBean() {
		return passwordBean;
	}

	public void setPasswordBean(PasswordBean passwordBean) {
		this.passwordBean = passwordBean;
	}
	
	public List<Account> getAccounts() {
		return this.accounts;
	}
	
	public int numberOfAccounts() {
		if (accounts != null) {
			return accounts.size();
		} else {
			return 0;
		}
	}
	
	@Override
	public void reset() {
		setSelectedAccount(null);
		setPasswordBean(new PasswordBean());
	}

	public void setSelectedAccount(Account selectedAccount) {
		this.selectedAccount = selectedAccount;
	}

	public Account getSelectedAccount() {
		return selectedAccount;
	}
}
