package adcowebsolutions.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 * Provides password hashing and password generating functions.
 * 
 * @author Russell Adcock
 *
 */
public class PasswordManager {
	
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
		String passChars = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
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
