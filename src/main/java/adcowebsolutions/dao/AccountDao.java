package adcowebsolutions.dao;

import java.util.List;

import adcowebsolutions.dao.support.GenericDao;
import adcowebsolutions.domain.Account;

/**
 * DAO Interface for {@link Account}
 */
public interface AccountDao extends GenericDao<Account, String> {
	
	List<Account> findAll();
}
