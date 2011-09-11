package adcowebsolutions.beans;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component("person")
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private String address;
	private String password;
	private String userName;
   
	public Person() {}
   
	public String getAddress() {
		return address;
    }
   
	public void setAddress(String address) {
		this.address = address;
	}
   
	public String getFirstName() {
		return firstName;
	}
   
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
  
	public String getLastName() {
		return lastName;
	}
   
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void reset() {
		this.firstName = null;
		this.lastName = null;
		this.address = null;
		this.password = null;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

}
