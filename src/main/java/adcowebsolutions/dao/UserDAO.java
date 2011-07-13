package adcowebsolutions.dao;

import java.util.List;

import adcowebsolutions.model.WebUser;

public interface UserDAO {
	
	public WebUser findById(String id) throws Exception;
	
	public List<WebUser> findByEmail(String email) throws Exception;
	
	public List<WebUser> findAll() throws Exception;
	
	public void persist(WebUser user) throws Exception;
	
	public void merge(WebUser user) throws Exception;
	
	public void delete(String id) throws Exception;

}
