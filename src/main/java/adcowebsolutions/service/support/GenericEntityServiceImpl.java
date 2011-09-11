package adcowebsolutions.service.support;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import adcowebsolutions.dao.support.GenericDao;
import adcowebsolutions.dao.support.SearchTemplate;
import adcowebsolutions.domain.Identifiable;

/**
 * Default implementation of the {@link GenericEntityService}
 * @see GenericEntityService
 */
public abstract class GenericEntityServiceImpl<T extends Identifiable<PK>, PK extends Serializable> implements GenericEntityService<T, PK> {

    private Logger logger;

    public GenericEntityServiceImpl() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    abstract public GenericDao<T, PK> getDao();

    /**
     * {@inheritDoc}
     */
    public abstract T getNew();

    /**
     * {@inheritDoc}
     */
    public abstract T getNewWithDefaults();

    //-------------------------------------------------------------
    //  Save methods
    //-------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void save(T model) {
        getDao().save(model);
    }
    
    @Transactional
    public void update(T model) {
        getDao().update(model);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void save(Iterable<T> models) {
        getDao().save(models);
    }

    //-------------------------------------------------------------
    //  Get and Delete methods (primary key or unique constraints)
    //-------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public T getByPrimaryKey(PK primaryKey) {
        T entity = getNew();
        entity.setPrimaryKey(primaryKey);
        return get(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void deleteByPrimaryKey(PK primaryKey) {
        delete(getByPrimaryKey(primaryKey));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public T get(T model) {
        return getDao().get(model);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void delete(T model) {
        if (model == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping deletion for null model!");
            }

            return;
        }

        getDao().delete(model);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void delete(Iterable<T> models) {
        getDao().delete(models);
    }

    //-------------------------------------------------------------
    //  Refresh
    //-------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public void refresh(T entity) {
        getDao().refresh(entity);
    }

    //-------------------------------------------------------------
    //  Finders methods
    //-------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public T findUnique(T model) {
        return findUnique(model, new SearchTemplate());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public T findUnique(T model, SearchTemplate searchTemplate) {
        T result = findUniqueOrNone(model, searchTemplate);

        if (result == null) {
            throw new InvalidDataAccessApiUsageException(
                    "Developper: You expected 1 result but we found none ! sample: " + model);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public T findUniqueOrNone(T model) {
        return findUniqueOrNone(model, new SearchTemplate());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public T findUniqueOrNone(T model, SearchTemplate searchTemplate) {
        // this code is an optimisation to prevent using a count
        // we request at most 2, if there's more than one then we throw an InvalidDataAccessApiUsageException
        SearchTemplate searchTemplateBounded = new SearchTemplate(searchTemplate);
        searchTemplateBounded.setFirstResult(0);
        searchTemplateBounded.setMaxResults(2);
        List<T> results = find(model, searchTemplateBounded);

        if (results == null || results.isEmpty()) {
            return null;
        }

        if (results.size() > 1) {
            throw new InvalidDataAccessApiUsageException(
                    "Developper: You expected 1 result but we found more ! sample: " + model);
        }

        return results.iterator().next();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public List<T> find() {
        return find(getNew(), new SearchTemplate());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public List<T> find(T model) {
        return find(model, new SearchTemplate());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public List<T> find(SearchTemplate searchTemplate) {
        return find(getNew(), searchTemplate);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public List<T> find(T model, SearchTemplate searchTemplate) {
        return getDao().find(model, searchTemplate);
    }

    //-------------------------------------------------------------
    //  Count methods
    //-------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public int findCount() {
        return findCount(getNew(), new SearchTemplate());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public int findCount(T model) {
        return findCount(model, new SearchTemplate());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public int findCount(SearchTemplate searchTemplate) {
        return findCount(getNew(), searchTemplate);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public int findCount(T model, SearchTemplate searchTemplate) {
        return getDao().findCount(model, searchTemplate);
    }
}