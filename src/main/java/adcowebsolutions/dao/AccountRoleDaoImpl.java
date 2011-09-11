package adcowebsolutions.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import adcowebsolutions.dao.hibernate.HibernateGenericDao;
import adcowebsolutions.domain.AccountRole;

/**
 * Hibernate implementation of the AccountRole DAO interface.
 */
@Repository
public class AccountRoleDaoImpl extends HibernateGenericDao<AccountRole, Long> implements AccountRoleDao {
    public AccountRoleDaoImpl() {
        super(AccountRole.class);
    }
    
    @Transactional
    public void deleteByUserName(String userName) {
    //	User user = getEntityManager().find(Account.class, );
    	Query query = getEntityManager().createQuery("delete from AccountRole where user_name = :userName").setParameter("userName", userName.toUpperCase());
    	query.executeUpdate();
    	getEntityManager().flush();
    }
}