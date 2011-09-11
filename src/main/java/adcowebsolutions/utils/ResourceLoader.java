package adcowebsolutions.utils;

import static adcowebsolutions.constants.GlobalConstants.CONST_MESSAGES;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.context.i18n.LocaleContextHolder;

public class ResourceLoader {
	
	private static ResourceBundle resourceBundle;
	private static Locale locale;
	
	public static String getBundleMessage(String key) {
		String messageTemplate = "";
        if (key != null) {
        	ResourceBundle resourceBundle = getResourceBundle();
            if (resourceBundle != null) {
            	try {
            		String bundleMessage = resourceBundle.getString(key);
                    if (bundleMessage != null) {
                    	messageTemplate = bundleMessage;
                    }
            	} catch (MissingResourceException mre) {} //swallow
            }
        }
        return messageTemplate;
    }

	public static ResourceBundle getResourceBundle() {
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle(CONST_MESSAGES, getLocale());
		}
		return resourceBundle;
	}
	
	public static ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundle.getBundle(CONST_MESSAGES, locale);
	}

	public static Locale getLocale() {
		if (locale == null) {
			locale = LocaleContextHolder.getLocale();
		}
		return locale;
	}
}
