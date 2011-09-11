package adcowebsolutions.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import adcowebsolutions.dao.AccountDao;
import adcowebsolutions.domain.Account;
import adcowebsolutions.utils.ApplicationContextLoader;
import adcowebsolutions.utils.ResourceLoader;

public class UserIdUniqueValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {	    
		AccountDao accountDao = (AccountDao) ApplicationContextLoader.getBean(context, "accountDao");
		String userName = (String) value;
		Account account = accountDao.get(new Account(userName.toUpperCase()));
		if (account != null) { //User name already exists
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceLoader.getBundleMessage("user_name_already_exists"), null));
		}
	}
}
