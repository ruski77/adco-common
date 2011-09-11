package adcowebsolutions.service;

import adcowebsolutions.dao.support.SearchTemplate;
import adcowebsolutions.domain.AccountRole;
import adcowebsolutions.service.support.GenericEntityService;

/**
 * The AccountRoleService is a data-centric service for the {@link AccountRole} entity.
 * It provides the expected methods to get/delete a {@link AccountRole} instance
 * plus some methods to perform searches.
 * <p>
 * Search logic is driven by 2 kinds of parameters: a {@link AccountRole} instance used
 * as a properties holder against which the search will be performed and a {@link SearchTemplate}
 * instance from where you can control your search options including the usage
 * of named queries.
 */
public interface AccountRoleService extends GenericEntityService<AccountRole, Long> {
	
	public void deleteByUserName(String userName);
}