package adcowebsolutions.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationTestContext.xml"})
public class PasswordManagerTest {
	
	@Autowired
	private PasswordManager passwordManager;
 
	@Test
	public void testEncodePassword() throws Exception {
		
		assertNotNull(passwordManager);
		
		String passwordToHash = "test1234";
		String hashedPassword = passwordManager.encodePassword(passwordToHash);
		
		assertNotNull("Hashed Password returned null", hashedPassword);
		assertNotSame("Password To Hash and Hasded Password are the same", passwordToHash, hashedPassword);
		assertNotSame("Password To Hash and Hasded Password lengths are the same", passwordToHash.length(), hashedPassword.length());
		assertEquals("Hashed password different to expected", "9bc34549d565d9505b287de0cd20ac77be1d3f2c", hashedPassword);
	}
	
	@Test
	public void testGeneratePassword() throws Exception {
		
		assertNotNull(passwordManager);
		
		String password = passwordManager.generatePassword();
		
		assertNotNull("Generated password is null", password);
		assertEquals("Generated password is not equal to specified length", password.length(), 8);
	}
	
	@Test
	public void testIsPasswordValid() throws Exception {
		
		assertNotNull(passwordManager);
		
		assertTrue(passwordManager.isPasswordValid("9bc34549d565d9505b287de0cd20ac77be1d3f2c", "test1234"));
		assertFalse(passwordManager.isPasswordValid("9bc34549d565d9505b287de0cd20ac77be1d3f2c", "test123"));
	}
	
	// By default, passwordManager is initialised with password max length of 8.
	@Test
	public void testIsPasswordValidLength() throws Exception {
		
		assertNotNull(passwordManager);
		
		assertTrue(passwordManager.isValidLength("test1234"));
		assertTrue(passwordManager.isValidLength("test12345"));
		assertFalse(passwordManager.isValidLength("test123"));
	
	}

	public PasswordManager getPasswordManager() {
		return passwordManager;
	}

	public void setPasswordManager(PasswordManager passwordManager) {
		this.passwordManager = passwordManager;
	}
}
