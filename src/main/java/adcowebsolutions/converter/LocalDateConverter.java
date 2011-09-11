package adcowebsolutions.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Simple converter for JodaTime LocalDate.
 */
@Component("localDateConverter")
public class LocalDateConverter implements Converter {

    private final String pattern = "dd-MM-yyyy";
    private DateTimeFormatter formatter;

    public LocalDateConverter() {
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

        return formatter.withLocale(LocaleContextHolder.getLocale()).parseDateTime(value).toLocalDate();
    }

    @Override
    public String getAsString(FacesContext pFacesCtx, UIComponent pComponent, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof LocalDate) {
            return formatter.withLocale(LocaleContextHolder.getLocale()).print((LocalDate) value);
        }

        throw new IllegalArgumentException("Expecting a LocalDate instance but received " + value.getClass().getName());
    }
}
