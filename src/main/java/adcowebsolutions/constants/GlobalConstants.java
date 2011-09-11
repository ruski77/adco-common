package adcowebsolutions.constants;

/**
 * This class defines common constant values for all applications.
 * 
 * @author Russell Adcock
 */
public class GlobalConstants {
	
	/* user account constants */
	public static final String CONST_SUPPORT = "SUPPORT";
	public static final String CONST_ADMIN = "ADMIN";
	
	/* Exception Constants */
    public static final String CONST_SQL_EXCEPTION = "SQL_EXCEPTION";
    public static final String CONST_GENERAL_EXCEPTION = "GENERAL_EXCEPTION";
    public static final String CONST_DATABASE_EXCEPTION = "DATABASE_EXCEPTION";
    public static final String CONST_IO_EXCEPTION = "IO_EXCEPTION";
    public static final String CONST_ENCRYPTION_DECRYPTION_EXCEPTION = "ENCRYPTION_DECRYPTION_EXCEPTION";
    public static final String CONST_DUPLICATE_KEY_EXCEPTION = "DUPLICATE_KEY_EXCEPTION";
    public static final String CONST_NO_ALGORITHM_EXCEPTION = "NO_ALGORITHM_EXCEPTION";
    
    /* Postgre SQL error codes */
    public static final String CONST_DUPLICATE_KEY_ERROR_CODE  = "23505";
    
    /* mime type helpers */
    public static final String CONST_IMAGE_MIME_PATH = "image/";
    public static final String CONST_APPLICATION_MIME_PATH = "application/";
    public static final String CONST_IMAGE = "image";
    public static final String CONST_VIDEO = "video";
    
    /*
     * The token that represents a nominal outcome
     * in an ActionForward.
     */
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String ERROR = "error";
    public static final String WARN = "warning";
    public static final String LOGON = "logon";
    public static final String LOGOUT = "logout";
    public static final String WELCOME = "welcome";
     
    /*
     * The value to indicate debug logging.
     */
    public static final int DEBUG = 1;
    
    /*
     * The value to indicate normal logging.
     */
    public static final int NORMAL = 0;
    
    public static final String CONST_ACTIVE_LONG = "ACTIVE";
    public static final String CONST_INACTIVE_LONG = "INACTIVE";
    
    public static final String CONST_ACTIVE = "A";
    public static final String CONST_INACTIVE = "I";
    public static final String CONST_YES = "Y";
    public static final String CONST_NO = "N";
    public static final String CONST_TRUE = "true";
    public static final String CONST_FALSE = "false";
    
    
    /* max file size constants - represented in bytes */
    public static final int CONST_FILE_MAX_SIZE_1MB = 1024000;
    public static final int CONST_FILE_MAX_SIZE_2MB = 2048000;
    public static final int CONST_FILE_MAX_SIZE_3MB = 3072000;
    public static final int CONST_FILE_MAX_SIZE_4MB = 4096000;
    public static final int CONST_FILE_MAX_SIZE_5MB = 5120000;
    public static final int CONST_FILE_MAX_SIZE_10MB = 10240000;
    public static final int CONST_AVATAR_MAX_SIZE_500K =500000;
    public static final int CONST_AVATAR_MAX_SIZE_250K =250000;
    public static final int CONST_AVATAR_MAX_SIZE_100K =100000;
    
    public static final int CONST_AVATAR_HEIGHT = 64;
    public static final int CONST_AVATAR_WIDTH = 64;
    
    public static final String CONST_CACHE_SCHEDULE_DATA = "SCHEDULE_DATA";
    public static final String CONST_BLOG_TOPIC = "BLOG_TOPIC";
    
    public static final String CONST_BLOB = "blob";
    public static final String CONST_EMAIL_SUBSCRIBER = "emailSubscriber";
    public static final String CONST_FAQ = "FAQ";
    public static final String CONST_BLOG = "blog";
    public static final String CONST_FLAVOUR = "flavour";
    public static final String CONST_NEWS = "news";
    public static final String CONST_NEWS_COMMENT = "newsComment";
    public static final String CONST_SCHEDULE = "schedule";
    public static final String CONST_USER = "user";
    public static final String CONST_ALL = "ALL";
	
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String CONST_INVALID_LOGIN_EVENT = "invalidLogin";
	public static final String CONST_LOGIN_WARNING_EVENT = "loginWarning";
	public static final String CONST_LOGIN_SUCCESS_EVENT = "loginSuccess";
	public static final String CONST_UPDATE_USER_LOGIN_SUCCESS_EVENT = "loginSuccessUpdateUser";
	public static final String CONST_DELETE_PROFILE_EVENT = "au.com.adcowebsolutions.deletedProfile";
	public static final String CONST_SEND_TO_MAIL_LIST_EVENT = "sentToMailListEvent";
	
	//system config types
	public static final String CONST_SYS_CONFIG_MAIL_UPDATES = "send.content.updates.to.mailing.list";
	
	public static final String CONST_MESSAGES = "Messages";
	
	public static final String CONST_ACCOUNT_SESSION = "accountSession";
	
	public static final String UPDATE_PASSWORD_DISPLAYED = "UPDATE_PASSWORD_DISPLAYED";
	
	public static final String APPLICATION_CONTEXT_PATH = "/WEB-INF/spring/applicationContext.xml";
	
	public static final String LOGOUT_FILTER_APPLIED = "LOGOUT_FILTER_APPLIED";

}
