package adcowebsolutions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adcowebsolutions.dao.AccountRoleDao;
import adcowebsolutions.dao.support.GenericDao;
import adcowebsolutions.domain.AccountRole;
import adcowebsolutions.service.support.GenericEntityServiceImpl;

/**
 *
 * Default implementation of the {@link AccountRoleService} interface
 * @see AccountRoleService
 */
@Service("accountRoleService")
public class AccountRoleServiceImpl extends GenericEntityServiceImpl<AccountRole, Long> implements AccountRoleService {

	private Logger log = LoggerFactory.getLogger(AccountRoleServiceImpl.class);

    protected AccountRoleDao accountRoleDao;

    @Autowired
    public void setAccountRoleDao(AccountRoleDao accountRoleDao) {
        this.accountRoleDao = accountRoleDao;
    }

    /**
     * Dao getter used by the {@link GenericEntityServiceImpl}.
     */
    @Override
    public GenericDao<AccountRole, Long> getDao() {
        return accountRoleDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountRole getNew() {
        return new AccountRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountRole getNewWithDefaults() {
        AccountRole result = getNew();
        result.initDefaultValues();
        return result;
    }

	@Override
	public void deleteByUserName(String userName) {
		accountRoleDao.deleteByUserName(userName);
	}

}