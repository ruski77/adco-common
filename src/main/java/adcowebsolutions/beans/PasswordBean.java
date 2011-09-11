package adcowebsolutions.beans;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component("passwordBean")
public class PasswordBean  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String password;
	private String verify;
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getVerify() {
		return verify;
	}
}
