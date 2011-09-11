package adcowebsolutions.service;

import java.util.List;

import adcowebsolutions.dao.support.SearchTemplate;
import adcowebsolutions.domain.Account;
import adcowebsolutions.service.support.GenericEntityService;

/**
 * The AccountService is a data-centric service for the {@link Account} entity.
 * It provides the expected methods to get/delete a {@link Account} instance
 * plus some methods to perform searches.
 * <p>
 * Search logic is driven by 2 kinds of parameters: a {@link Account} instance used
 * as a properties holder against which the search will be performed and a {@link SearchTemplate}
 * instance from where you can control your search options including the usage
 * of named queries.
 */
public interface AccountService extends GenericEntityService<Account, String> {

    /**
     * Return the persistent instance of {@link Account} with the given unique property value email,
     * or null if there is no such persistent instance.
     *
     * @param email the unique value
     * @return the corresponding {@link Account} persistent instance or null
     */
    Account getByEmail(String email);

    /**
     * Delete a {@link Account} using the unique column email
     *
     * @param email the unique value
     */
    void deleteByEmail(String email);
    
    /**
     * Creates a user account when registering.
     * 
     * @param account to register
     */
    void create(Account account);

	void updateLoginCountAndDate(String userName);
	
	List<Account> findAll();
}