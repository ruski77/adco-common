package adcowebsolutions.dao;

import adcowebsolutions.dao.support.GenericDao;
import adcowebsolutions.domain.AccountRole;

/**
 * DAO Interface for {@link AccountRole}
 */
public interface AccountRoleDao extends GenericDao<AccountRole, Long> {
	
	 public void deleteByUserName(String userName);
}
