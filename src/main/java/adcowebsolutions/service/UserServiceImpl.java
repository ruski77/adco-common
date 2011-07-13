package adcowebsolutions.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import adcowebsolutions.crypto.PasswordManager;
import adcowebsolutions.dao.UserDAO;
import adcowebsolutions.model.WebUser;
import adcowebsolutions.model.WebUserRole;
import adcowebsolutions.utils.DateUtils;

@Service("userService")
public class UserServiceImpl implements UserService {

	private UserDAO userDAO;
	
	@Autowired
	private PasswordManager passwordManager;
	
	@Autowired
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Transactional
	@Override
	public void create(WebUser user) throws Exception {
		user.setId(user.getId().toUpperCase());
		user.setPassword(passwordManager.encodePassword(user.getPassword()));
        user.setRegisteredDate(DateUtils.currentDateTime());
        user.setLoginCount(0);
        user.setInvalidLoginCount(0);
        user.setNewsCount(0);
        user.setCommentCount(0);
        user.setBlogCount(0);
        user.setStatus("A");
        user.setUpdatePasswordFlag("N");
        
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
        	WebUserRole role = new WebUserRole();
        	//role.setRoleName(Role.GUEST.toString());
        	role.setRoleName("USER");//TODO draw from enum like above
        	role.setUser(user);
        	List<WebUserRole> roles = new ArrayList<WebUserRole>();
        	roles.add(role);
        	user.setRoles(roles);
        }
        
		userDAO.persist(user);
	}

	@Transactional
	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Transactional(readOnly=true)
	@Override
	public List<WebUser> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly=true)
	@Override
	public WebUser findByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly=true)
	@Override
	public WebUser findById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public void update(WebUser user) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public PasswordManager getPasswordManager() {
		return passwordManager;
	}

	public void setPasswordManager(PasswordManager passwordManager) {
		this.passwordManager = passwordManager;
	}
}
