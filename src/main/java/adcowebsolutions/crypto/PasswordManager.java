package adcowebsolutions.crypto;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import adcowebsolutions.beans.PasswordBean;

/**
 * Provides password hashing and password generating functions.
 * 
 * @author Russell Adcock
 */
public class PasswordManager implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer length;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Returns a hashed value for a plain text password.
	 * 
	 * @param password - The plain text password to hash
	 * 
	 * @return The hashed password
	 */
	public String encodePassword(String password) {
		try {
			return passwordEncoder.encodePassword(password, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isPasswordValid(String encodedPassword, String rawPassword) {
		return passwordEncoder.isPasswordValid(encodedPassword, rawPassword, null);
	}
	
	/**
	 * Returns a String of the specified length, randomly generated from ranges 
	 * encompassing [a-z], and [A-Z].
	 * 
	 * @return As generated password
	 */
	public String generatePassword() {
		String passChars = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ1234567890";
	    //Next I am going to generate a number that will
	    //be used to reference a character in this string
	    StringBuffer password = new StringBuffer(length);
	    int characterCount = 0;
	    while (characterCount < length) {
	    	int index = new Long(Math.round(Math.random()*(passChars.length()-1))).intValue();
	        char selectedChar = passChars.charAt(index);
	        password.append(selectedChar);
	        characterCount++;
	    }
	      
	    return password.toString();
	}
	
	public boolean isValidLength(String password) {
		if (password == null) return false;
		return length <= password.length();
	}
	
	public boolean passwordAndUserIdMatch(String userName, PasswordBean passwordBean) {
		return userName.toLowerCase().equals(passwordBean.getPassword().toLowerCase());
	}

	public boolean passwordsMatch(PasswordBean passwordBean) {
		if (passwordBean.getPassword() == null || passwordBean.getPassword().length() == 0) {
			return false;
		}
		if (passwordBean.getVerify() == null || passwordBean.getVerify().length() == 0) {
			return false;
		}
		
		if (passwordBean.getPassword().equals(passwordBean.getVerify())) {
			return true;
		}
		return false;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
}
