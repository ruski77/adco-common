package adcowebsolutions.beans;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("message")
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String subject;
	private String content;
	private List<byte[]> attachments;
	private List<Person> recipients;
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<byte[]> getAttachments() {
		return attachments;
	}
	
	public void setAttachments(List<byte[]> attachments) {
		this.attachments = attachments;
	}

	public void setRecipients(List<Person> recipients) {
		this.recipients = recipients;
	}

	public List<Person> getRecipients() {
		return recipients;
	}
	
	public void reset() {
		subject = null;
		content = null;
		attachments = null;
		recipients = null;
	}
}
