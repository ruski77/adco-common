package adcowebsolutions.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class WebUtils {
	
	public static String encodeURL(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String decodeURL(String string) {
        try {
            return URLDecoder.decode(string, "UTF-8");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static String escapeJSMessage(String message) {
        return message.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\\\"");
    }
    
    public static Throwable unwrap(Throwable throwable) throws IllegalArgumentException {
        if (throwable == null) {
            throw new IllegalArgumentException("Cannot unwrap null throwable");
        }
        for (Throwable current = throwable; current != null; current = current.getCause()) {
            throwable = current;
        }
        return throwable;
    }

    public static int getSessionTimeoutSeconds() {
        return ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getMaxInactiveInterval();
    }
}
