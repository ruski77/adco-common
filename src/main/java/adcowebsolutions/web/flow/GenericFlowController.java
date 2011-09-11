package adcowebsolutions.web.flow;

import static adcowebsolutions.constants.GlobalConstants.ERROR;
import static adcowebsolutions.constants.GlobalConstants.FAILURE;
import static adcowebsolutions.constants.GlobalConstants.SUCCESS;
import static adcowebsolutions.constants.GlobalConstants.TIMESTAMP_FORMAT;
import static adcowebsolutions.constants.GlobalConstants.WARN;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.faces.webflow.FlowFacesContext;

import adcowebsolutions.beans.ErrorMessage;
import adcowebsolutions.mail.EmailSendingService;
import adcowebsolutions.utils.DateUtils;
import adcowebsolutions.utils.Interpolator;
import adcowebsolutions.utils.ResourceLoader;

@SuppressWarnings("serial")
public abstract class GenericFlowController implements Serializable {
	
	private FacesMessage facesMessage;
	
	@Autowired
	protected EmailSendingService emailSendingService;
	
	public void setSuccessMessage(String detailKey, Object... params) {
		facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, 
										ResourceLoader.getBundleMessage(SUCCESS), 
										Interpolator.instance().interpolate(ResourceLoader.getBundleMessage(detailKey), params));
		FlowFacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void setWarningMessage(String detailKey, Object... params) {
		facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
										ResourceLoader.getBundleMessage(WARN), 
										Interpolator.instance().interpolate(ResourceLoader.getBundleMessage(detailKey), params));
		FlowFacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void setFailureMessage(String detailKey, Object... params) {
		facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
										ResourceLoader.getBundleMessage(FAILURE), 
										Interpolator.instance().interpolate(ResourceLoader.getBundleMessage(detailKey), params));
		FlowFacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void setErrorMessage(String detailKey, Object... params) {
		facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
										ResourceLoader.getBundleMessage(ERROR), 
										Interpolator.instance().interpolate(ResourceLoader.getBundleMessage(detailKey), params));
		FlowFacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public HttpSession getCurrentSession() {
		ExternalContext context = FlowFacesContext.getCurrentInstance().getExternalContext();	
		HttpServletRequest request  = (HttpServletRequest) context.getRequest();
		return request.getSession();
	}
	
	public HttpServletRequest getCurrentRequest() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();	
		return (HttpServletRequest) context.getRequest();
	}
	
	public String getRequestParameterMapValue(String key) {
		return FlowFacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
	}
	
	protected void notifySupport(Exception e) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setDate(DateUtils.now(TIMESTAMP_FORMAT));
		if (e instanceof NullPointerException) {
			errorMessage.setMessage("NullPointerException");
		} else {
			errorMessage.setMessage(e.getMessage());
		}
		errorMessage.setStackTrace(getStackTrace(e));
       
		emailSendingService.sendExceptionEmail(errorMessage);
	}
	
	private String getStackTrace(Exception e) {
		StringBuilder sb = new StringBuilder();
	    for (StackTraceElement element : e.getStackTrace()) {
	        sb.append(element.toString());
	        sb.append("<br/>");
	    }
	    return sb.toString();
	}
	
	public void cancel() {
		reset();
	}
	
	public abstract void reset();

}
