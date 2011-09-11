package adcowebsolutions.exception;


/**
 * SystemException handles unrecoverable exceptions that occur in a application.
 * 
 * @author Russell Adcock
 *
 * @see Exception
 *
 */
@SuppressWarnings("serial")
public class SystemException extends Exception {
	
	private String className;
	private String methodName;
	
	public SystemException(Exception exception) {
		super(exception);
	}
	
	public SystemException(Exception exception, String className, String methodName) {
		super(exception);
	    this.className = className;
	    this.methodName = methodName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}