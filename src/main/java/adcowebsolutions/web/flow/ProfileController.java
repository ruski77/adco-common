package adcowebsolutions.web.flow;

import static adcowebsolutions.constants.GlobalConstants.CONST_AVATAR_HEIGHT;
import static adcowebsolutions.constants.GlobalConstants.CONST_AVATAR_MAX_SIZE_100K;
import static adcowebsolutions.constants.GlobalConstants.CONST_AVATAR_WIDTH;
import static adcowebsolutions.constants.GlobalConstants.CONST_NO;
import static adcowebsolutions.constants.GlobalConstants.CONST_SUPPORT;
import static adcowebsolutions.constants.GlobalConstants.CONST_YES;
import static adcowebsolutions.constants.GlobalConstants.FAILURE;
import static adcowebsolutions.constants.GlobalConstants.SUCCESS;
import static adcowebsolutions.constants.GlobalConstants.UPDATE_PASSWORD_DISPLAYED;

import java.io.ByteArrayInputStream;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import adcowebsolutions.beans.PasswordBean;
import adcowebsolutions.crypto.PasswordManager;
import adcowebsolutions.domain.Account;
import adcowebsolutions.service.AccountService;
import adcowebsolutions.utils.ImageUtils;

@Component("profileController")
public class ProfileController extends GenericFlowController {

	private static final long serialVersionUID = 1L;
	
	private Logger log = LoggerFactory.getLogger(ProfileController.class);
	
	private AccountService accountService;
	
	private StreamedContent avatarContent; 
	
	@Autowired
	private ImageUtils imageUtils;
	
	private Account account;
	
	private UploadedFile file;  
	
	private PasswordBean passwordBean = new PasswordBean();
	
	@Autowired
	private PasswordManager passwordManager;
	
	@Autowired
	public ProfileController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	public Account view(String userName) {
		log.debug("view() account = {}", userName);

		try {
			account = accountService.get(new Account(userName.toUpperCase()));
			if (account != null) {
				if (CONST_YES.equals(account.getUpdatePassword())) {
					if (getCurrentSession().getAttribute(UPDATE_PASSWORD_DISPLAYED) == null) {
						setWarningMessage("message_update_password");
						getCurrentSession().setAttribute(UPDATE_PASSWORD_DISPLAYED, "true");
					}
				}
			}
			
			if (getCurrentRequest() != null) {
				String successMessage = getCurrentRequest().getParameter(SUCCESS);
				if (successMessage != null && successMessage.length() > 0 ) {
					setSuccessMessage("success_saving_profile");
				}
			}
		} catch (Exception e) {
			log.error("Error retrieving profile data due to: {}", e.getMessage());
			e.printStackTrace();
			setErrorMessage("error_view_profile", e.getMessage());
			notifySupport(e);
		}
		
		return account;
	}
	
	public void uploadImage() {
		log.debug("uploadImage() file name = {}", file != null ? file.getFileName() : "null");
		
		try {
			if (file == null || file.getContents() == null || file.getContents().length == 0) {
				setFailureMessage("failure_upload_file_empty");
				return;
			}
			
			if (file.getContents().length <= CONST_AVATAR_MAX_SIZE_100K) {
				String mimeType = file.getContentType();
				byte[] scaledImage = imageUtils.rescaleImage(file.getContents(), CONST_AVATAR_WIDTH, CONST_AVATAR_HEIGHT, mimeType);
				if (account != null) {
					account.setAvatar(scaledImage);
					account.setAvatarMimeType(mimeType);
					accountService.update(account);
					setSuccessMessage("success_upload_picture", file.getFileName());
				} else {
					setFailureMessage("failure_account_null");
				}
			} else {
				setFailureMessage("failure_upload_file_size", CONST_AVATAR_MAX_SIZE_100K/1000 + "Kb");
			}
		} catch (Exception e) {
			setErrorMessage("failure_upload_picture", e.getMessage());
			e.printStackTrace();
			notifySupport(e);
		}
	} 
	
	public void deleteImage() {
		log.debug("deleteImage(): Account = {}", account != null ? account.getUserName() : "null");

		try {
			if (account != null) {
				account.setAvatar(null);
				account.setAvatarMimeType(null);
				accountService.update(account);
				setSuccessMessage("success_delete_profile_pic");
			} else {
				setFailureMessage("failure_account_null");
			}
		} catch (Exception e) {
			setErrorMessage("failure_delete_profile_pic", e.getMessage());
			e.printStackTrace();
			notifySupport(e);
		}
	}
	
	public String save(Account profile) {
		log.debug("save(): Account = {}", profile != null ? profile.toString(): "null");
		
		String forward = SUCCESS;
		
		try {
			if (passwordBean.getPassword() != null && passwordBean.getPassword().length() > 0) {
				
				//Does password and verify password match?
				if (!passwordManager.passwordsMatch(passwordBean)) { 
					setFailureMessage("passwords_no_match");
					return FAILURE;
				}
				
				//user name and password should not be the same
				if (passwordManager.passwordAndUserIdMatch(account.getUserName(), passwordBean)) {
					setFailureMessage("userid_password_same");
					return FAILURE;
				}
				
				//check if password meets minimum length requirements
				if (!passwordManager.isValidLength(passwordBean.getPassword())) {
					setFailureMessage("password_invalid_length", passwordManager.getLength());
					return FAILURE;
				}	
	
				profile.setPassword(passwordManager.encodePassword(passwordBean.getPassword()));
				profile.setUpdatePassword(CONST_NO);
				accountService.update(profile);
				reset();
			} else {
				accountService.update(profile);
				reset();
			}
		} catch (Exception e) {
			e.printStackTrace();
			forward = FAILURE;
			setErrorMessage("failure_saving_profile", e.getMessage());
			notifySupport(e);
		}
		
		return forward;
	}
	
	public String delete(Account profile) {
		log.debug("delete(): Account = {}", profile != null ? profile.toString(): "null");
		
		String forward = SUCCESS;
		
		try {
			if (CONST_SUPPORT.equals(profile.getUserName())) {
				setFailureMessage("cannot_delete_user", profile.getUserName());
				forward = FAILURE;
			} else {
				accountService.delete(profile);
			}
		} catch (Exception e) {
			forward = FAILURE;
			setErrorMessage("failure_delete_profile", e.getMessage());
			notifySupport(e);
		}
		
		return forward;
	}

	@Override
	public void reset() {
		setFile(null);
		setPasswordBean(new PasswordBean());
	}

	public StreamedContent getAvatarContent() {
		if (account.getAvatar() != null) {
			ByteArrayInputStream stream = new ByteArrayInputStream(account.getAvatar());
			avatarContent = new DefaultStreamedContent(stream, account.getAvatarMimeType());
		} else {
			avatarContent = null;
		}
		return avatarContent;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public UploadedFile getFile() {
		return file;
	}

	public PasswordBean getPasswordBean() {
		return passwordBean;
	}

	public void setPasswordBean(PasswordBean passwordBean) {
		this.passwordBean = passwordBean;
	}
}
