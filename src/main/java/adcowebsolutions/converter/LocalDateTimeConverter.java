package adcowebsolutions.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Simple converter for JodaTime LocalDateTime.
 */
@Component("localDateTimeConverter")
public class LocalDateTimeConverter implements Converter {

    private final String pattern = "dd-MM-yyyy h:mm a";
    private DateTimeFormatter formatter;

    public LocalDateTimeConverter() {
        formatter = DateTimeFormat.forPattern(pattern);
    }

    /**
     * rich:calendar must to use the same pattern as the converter, so we provide it here.
     */
    public String getPattern() {
        return pattern;
    }

    @Override
    public Object getAsObject(FacesContext pFacesCtx, UIComponent pComponent, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        return formatter.withLocale(LocaleContextHolder.getLocale()).parseDateTime(value).toLocalDateTime();
    }

    @Override
    public String getAsString(FacesContext pFacesCtx, UIComponent pComponent, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof LocalDateTime) {
            return formatter.withLocale(LocaleContextHolder.getLocale()).print((LocalDateTime) value);
        }

        throw new IllegalArgumentException("Expecting a LocalDateTime instance but received "
                + value.getClass().getName());
    }
}
