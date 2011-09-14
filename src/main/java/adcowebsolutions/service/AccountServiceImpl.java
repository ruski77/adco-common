package adcowebsolutions.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import adcowebsolutions.constants.GlobalConstants;
import adcowebsolutions.crypto.PasswordManager;
import adcowebsolutions.dao.AccountDao;
import adcowebsolutions.dao.support.GenericDao;
import adcowebsolutions.domain.Account;
import adcowebsolutions.domain.AccountRole;
import adcowebsolutions.enums.Role;
import adcowebsolutions.service.support.GenericEntityServiceImpl;

/**
 *
 * Default implementation of the {@link AccountService} interface
 * @see AccountService
 */
@Service("accountService")
public class AccountServiceImpl extends GenericEntityServiceImpl<Account, String> implements AccountService {

	private Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    protected AccountDao accountDao;
    
    @Autowired
	private PasswordManager passwordManager;

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Dao getter used by the {@link GenericEntityServiceImpl}.
     */
    @Override
    public GenericDao<Account, String> getDao() {
        return accountDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getNew() {
        return new Account();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getNewWithDefaults() {
        Account result = getNew();
        result.initDefaultValues();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Account get(Account model) {
        if (model == null) {
            return null;
        }

        if (model.isPrimaryKeySet()) {
            return super.get(model);
        }

        if (model.getEmail() != null && !model.getEmail().isEmpty()) {
            Account result = getByEmail(model.getEmail());
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Account getByEmail(String _email) {
        Account account = new Account();
        account.setEmail(_email);
        return findUniqueOrNone(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteByEmail(String email) {
        delete(getByEmail(email));
    }
    
    @Transactional
	@Override
	public void create(Account account) {
		log.debug("create(): {}", account);
		account.setUserName(account.getUserName().toUpperCase());
		account.setPassword(passwordManager.encodePassword(account.getPassword()));
        account.setRegisteredDate(new LocalDateTime());
        account.setLastLoginDate(null);
        account.setLoginCount(0);
        account.setInvalidLoginCount(0);
        account.setNewsCount(0);
        account.setCommentCount(0);
        account.setBlogCount(0);
        account.setUpdatePassword(GlobalConstants.CONST_NO);
        
        if (account.getRoles() == null || account.getRoles().isEmpty()) {
        	AccountRole role = new AccountRole();
        	role.setRoleName(Role.USER.toString());
        	role.setAccount(account);
        	List<AccountRole> roles = new ArrayList<AccountRole>();
        	roles.add(role);
        	account.setRoles(roles);
        }
        
        accountDao.save(account);
	}
    
	@Override
    @Transactional
	public void updateLoginCountAndDate(String userName) {
    	Account account = accountDao.get(new Account(userName.toUpperCase()));    	
    	account.setLastLoginDate(new LocalDateTime());
		account.setLoginCount(account.getLoginCount()+1);
		accountDao.save(account);
    }

    @Override
    @Transactional(readOnly = true)
	public List<Account> findAll() {
		return accountDao.findAll();
	}
}