package adcowebsolutions.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Interpolator {
	
	private Logger log = LoggerFactory.getLogger(Interpolator.class);
	
	private static Interpolator interpolator;
	
    public static Interpolator instance() {
    	if (interpolator == null) {
    		return new Interpolator(); 
    	}
    	return interpolator;
    }

    /**
     * Replace all EL expressions in the form #{...} with their evaluated
     * values.
     * 
     * @param string a template
     * @return the interpolated string
     */
    public String interpolate(String string, Object... params) {
        if (params == null) {
            params = new Object[0];
        }

        if (params.length > 10) {
            throw new IllegalArgumentException("more than 10 parameters");
        }

        if (string.indexOf('#') >= 0 || string.indexOf('{') >= 0) {
            string = interpolateExpressions(string, params);
        }

        return string;
    }

    private String interpolateExpressions(String string, Object... params) {
        StringTokenizer tokens = new StringTokenizer(string, "#{}", true);
        StringBuilder builder = new StringBuilder(string.length());
        ExpressionParser parser = new SpelExpressionParser();
        
        try {
            while (tokens.hasMoreTokens()) {
                String tok = tokens.nextToken();

                if ("#".equals(tok) && tokens.hasMoreTokens()) {
                    String nextTok = tokens.nextToken();

                    while (nextTok.equals("#") && tokens.hasMoreTokens()) {
                        builder.append(tok);
                        nextTok = tokens.nextToken();
                    }

                    if ("{".equals(nextTok)) {
                        String exp = "#{" + tokens.nextToken() + "}";
                        try {
                    		Expression expression = parser.parseExpression(exp);
                    		String value = (String) expression.getValue();
                        	
                            if (value != null) {
                                builder.append(value);
                            }
                       } catch (Exception e) {
                            log.warn("exception interpolating string: "
                                    + string, e);
                        }
                        tokens.nextToken(); // the trailing "}"

                    } else if (nextTok.equals("#")) {
                        // could be trailing # 
                        builder.append("#");
                    } else {
                        int index;
                        try {
                            index = Integer.parseInt(nextTok.substring(0, 1));
                            if (index >= params.length) {
                                //log.warn("parameter index out of bounds: " + index + " in: " + string);
                                builder.append("#").append(nextTok);
                            } else {
                                builder.append(params[index]).append(nextTok.substring(1));
                            }
                        } catch (NumberFormatException nfe) {
                            builder.append("#").append(nextTok);
                        }
                    }
                } else if ("{".equals(tok)) {
                    StringBuilder expr = new StringBuilder();

                    expr.append(tok);
                    int level = 1;

                    while (tokens.hasMoreTokens()) {
                        String nextTok = tokens.nextToken();
                        expr.append(nextTok);

                        if (nextTok.equals("{")) {
                            ++level;
                        } else if (nextTok.equals("}")) {
                            if (--level == 0) {
                                try {
                                    String value = new MessageFormat(expr.toString(), Locale.getDefault()).format(params);
                                    builder.append(value);
                                } catch (Exception e) {
                                    // if it is a bad message, use the expression itself
                                    builder.append(expr);
                                }
                                expr = null;
                                break;
                            }
                        }
                    }

                    if (expr != null) {
                        builder.append(expr);
                    }
                } else {
                    builder.append(tok);
                }
            }
        } catch (Exception e) {
            log.warn("exception interpolating string: " + string, e);
        }

        return builder.toString();
    }
}
