package adcowebsolutions.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import adcowebsolutions.model.WebUser;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<WebUser> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebUser> findByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebUser findById(String id) throws Exception {
		log.debug("findById() id = {}", id);
		
		WebUser webUser = null;
		
		try {
			webUser = entityManager.find(WebUser.class, id.toUpperCase());
		} catch (Exception e) {
			log.error("Failed to find user due to: {}", e, e.getMessage());
			throw new Exception(this.getClass().getName());
		} 
		
		return webUser;
	}

	@Override
	public void merge(WebUser user) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void persist(WebUser user) throws Exception {
		log.debug("persist() {}", user);
		entityManager.persist(user);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
