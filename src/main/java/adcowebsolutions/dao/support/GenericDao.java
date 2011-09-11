package adcowebsolutions.dao.support;

import java.io.Serializable;
import java.util.List;

import adcowebsolutions.dao.hibernate.HibernateGenericDao;
import adcowebsolutions.domain.Identifiable;

/**
 * Generic data access object interface.
 * @see HibernateGenericDao
 * @see Identifiable
 * @see SearchTemplate
 */
public interface GenericDao<E extends Identifiable<PK>, PK extends Serializable> {

    /**
     * Gets from the repository the E entity instance
     * whose primary key or unique field matches the corresponding
     * primary key or unique field of the passed E instance.
     *
     * DAO for the local database will typically use the primary key
     * or unique fields of the passed entity, while DAO for external repository
     * may use a unique field present in the entity as they probably have no
     * knowledge of the primary key. Hence, passing the entity as an argument instead
     * of the primary key allows you to switch the DAO more easily.
     *
     * @param entity a E instance having a primary key or a unique field set
     * @return the corresponding E persistent instance or null if none could be found.
     */
    E get(E entity);

    /**
     * Refresh the passed entity with up to date data.
     * Does nothing if the passed entity is a new entity (not yet managed).
     *
     * @param entity the entity to refresh.
     */
    void refresh(E entity);

    /**
     * Saves or updates the passed entity E to the repository.
     *
     * @param entity the entity to be saved or updated.
     */
    void save(E entity);
    
    /**
     * Updates the passed entity E to the repository.
     *
     * @param entity the entity to be updated.
     */
    void update(E entity);

    /**
     * Saves or updates the passed list of E entities to the repository.
     *
     * @param entities the list of entities to be saved or updated.
     */
    void save(Iterable<E> entities);

    /**
     * Delete the passed entity E from the repository.
     * 
     * @param entity the entity to be deleted.
     */
    void delete(E entity);

    /**
     * Delete from the repository the passed list of E entities.
     * 
     * @param entities the list of entities to be deleted.
     */
    void delete(Iterable<E> entities);

    /**
     * Find and load a list of E instance.
     *
     * @param entity a sample entity whose non-null properties may be used as search hints
     * @param searchTemplate carries additional search information
     * @return the entities matching the search.
     */
    List<E> find(E entity, SearchTemplate searchTemplate);

    /**
     * Count the number of E instances.
     *
     * @param entity a sample entity whose non-null properties may be used as search hint
     * @param searchTemplate carries additional search information
     * @return the number of entities matching the search.
     */
    int findCount(E entity, SearchTemplate searchTemplate);
}
