package adcowebsolutions.service;

import java.util.List;

import adcowebsolutions.model.WebUser;

public interface UserService {
	
	public WebUser findById(String id) throws Exception;
	
	public void delete(String id) throws Exception;
	
	public WebUser findByEmail(String email) throws Exception;
	
	public void create(WebUser user) throws Exception;
	
	public void update(WebUser user) throws Exception;
	
	public List<WebUser> findAll() throws Exception;

}
