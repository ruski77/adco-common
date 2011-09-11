package adcowebsolutions.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import adcowebsolutions.dao.hibernate.HibernateGenericDao;
import adcowebsolutions.dao.hibernate.HibernateUtil;
import adcowebsolutions.dao.support.SearchTemplate;
import adcowebsolutions.domain.Account;

/**
 * Hibernate implementation of the Account DAO interface.
 */
@Repository
public class AccountDaoImpl extends HibernateGenericDao<Account, String> implements AccountDao {
    
	public AccountDaoImpl() {
        super(Account.class);
    }

    /**
     * Add criterion so the primary key is 
     * included in a search by example.
     */
    @Override
    protected List<Criterion> getByExampleExtraCriterions(Account model, SearchTemplate searchTemplate) {
        List<Criterion> criterions = new ArrayList<Criterion>();

        // handle pk fields
        if (model.isPrimaryKeySet() && model.getUserName().length() > 0) {
            criterions.add(HibernateUtil.constructPatternCriterion("userName", model.getPrimaryKey(), searchTemplate));
        }
        return criterions;
    }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Account> findAll() {
		Query query = getEntityManager().createQuery("SELECT a FROM Account a order by a.lastLoginDate desc");
		return query.getResultList();
	}
}