package adcowebsolutions.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import adcowebsolutions.beans.ErrorMessage;
import adcowebsolutions.beans.Message;
import adcowebsolutions.beans.Person;
import adcowebsolutions.domain.Account;
import adcowebsolutions.utils.ResourceLoader;

@Component
public class EmailSendingService {
	
	private Logger log = LoggerFactory.getLogger(EmailSendingService.class);
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
    private JavaMailSender mailSender;

    /**
     * Sends e-mail via the html contact form.
     * 
     * @param   person   Details of the person sending the email.
     * @param   msg      The message to send. 
     */
    public void sendContactEmail(final Person person,  final Message msg) {
    	
    	final ResourceBundle bundle = ResourceLoader.getResourceBundle();
    	
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
               MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
               message.setTo(bundle.getString("contact_email"));
               message.setFrom(person.getAddress(), person.getFirstName());
               message.setSubject(msg.getSubject());
               
               Map<String, Object> model = new HashMap<String, Object>();
               model.put("message", msg);
               model.put("person", person);
               model.put("msg", bundle);

               String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/contactEmail.vm", model);
               
               log.info("body={}", body);

               message.setText(body, true);
            }
         };
         
         mailSender.send(preparator);
        
         log.info("Contact email sent from '{}'.", person.getAddress());
    }
    
    public void sendRegistrationEmail(final Account account, final String password) throws Exception {
    	
    	 final ResourceBundle bundle = ResourceLoader.getResourceBundle();
    	
    	 MimeMessagePreparator preparator = new MimeMessagePreparator() {
             public void prepare(MimeMessage mimeMessage) throws Exception {
            	 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                 message.setTo(account.getEmail());
                 message.setFrom(bundle.getString("support_email"), bundle.getString("support_name"));
                 message.setSubject(bundle.getString("new_user_subject") + " " + bundle.getString("website_name") + "!");
                 Map<String, Object> model = new HashMap<String, Object>();
                 model.put("account", account);
                 model.put("password", password);
                 model.put("msg", bundle);
                 String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/newUser.vm", model);
                 message.setText(text, true);
                 message.addInline("logo", new ClassPathResource("mail/img/logo_email.jpg"));
             }
          };
          
          mailSender.send(preparator);
         
          log.info("Sent new user e-mail to '{}'.", account.getEmail());
    }
    
    public void sendExceptionEmail(final ErrorMessage errorMessage) {
    	
    	 final ResourceBundle bundle = ResourceLoader.getResourceBundle();
    	
    	MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
           	 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setTo(bundle.getString("adcowebsolutions_support"));
                message.setFrom(bundle.getString("support_email"), bundle.getString("support_name"));
                message.setSubject(bundle.getString("subject_error_on") + " " + bundle.getString("website_name"));
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("errorMessage", errorMessage);
                model.put("msg", bundle);
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/errorNotification.vm", model);
                message.setText(text, true);
                message.addInline("logo", new ClassPathResource("mail/img/logo_email.jpg"));
            }
         };
         
         mailSender.send(preparator);
        
         log.info("Sent error message e-mail to '{}'.", bundle.getString("adcowebsolutions_support"));
    	
    }
    
    public void sendResetPasswordEmail() {
    	
    }
    
    public void sendMailingListEmail() {
    	
    }

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}
