package adcowebsolutions.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {
	
	private String errorView;

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView(errorView);
		mv.getModel().put("exceptionMesg", "test error?");
		return mv;

	}
	
	public String getErrorView() {
		return errorView;
	}
	
	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}
}
