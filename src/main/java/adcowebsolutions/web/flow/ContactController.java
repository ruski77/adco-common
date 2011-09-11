package adcowebsolutions.web.flow;

import static adcowebsolutions.constants.GlobalConstants.FAILURE;
import static adcowebsolutions.constants.GlobalConstants.SUCCESS;

import org.hibernate.validator.constraints.impl.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import adcowebsolutions.beans.Message;
import adcowebsolutions.beans.Person;

@Component("contactController")
public class ContactController extends GenericFlowController {
	
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private Person person;

	@Autowired
	private Message message;
	
	public String send() {
		
		log.debug("Sending Cotact email from {}", person.getAddress());
		
		String forward = FAILURE;
		
		try {
			if (new EmailValidator().isValid(person.getAddress(), null)) {
				emailSendingService.sendContactEmail(person, message);
				setSuccessMessage("success_email_sent");
				forward = SUCCESS;
				resetMailData();
			} else {
				setFailureMessage("your_email_invalid");
			}
		} catch (Exception e) {
			log.error("Error sending contact mail", e);
			setErrorMessage("error_email_send", e.getMessage());
			notifySupport(e);
		}
		
		return forward;
	}
	
	private void resetMailData() {
		person.reset();
		message.reset();
	}

	@Override
	public void reset() {
		resetMailData();
	}

}
