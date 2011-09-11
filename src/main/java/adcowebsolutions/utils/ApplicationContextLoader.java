package adcowebsolutions.utils;

import static adcowebsolutions.constants.GlobalConstants.APPLICATION_CONTEXT_PATH;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.support.XmlWebApplicationContext;

public class ApplicationContextLoader {
	
	public static Object getBean(FacesContext context, String beanName) {
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		ServletContext servletContext = request.getSession().getServletContext();
		XmlWebApplicationContext ctx = new XmlWebApplicationContext(); 
		ctx.setConfigLocation(APPLICATION_CONTEXT_PATH); 
		ctx.setServletContext(servletContext); 
		ctx.refresh(); 
	    return ctx.getBean(beanName);
	}
}
