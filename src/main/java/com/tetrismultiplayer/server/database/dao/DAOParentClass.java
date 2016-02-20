package main.java.com.tetrismultiplayer.server.database.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Abstract class containing common methods and fields for DAO classes.
 *
 * @param <T> referenced DTO class
 */
public abstract class DAOParentClass<T>
{
    protected EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;
    protected String dtoClassName;
    protected String tableName;
    protected Class<T> entityClass;

    protected DAOParentClass(Class<T> clazz, String tableName, EntityManagerFactory entityManagerFactory)
    {
        this.entityClass = clazz;
        this.tableName = tableName;
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = createEntityManager();
        this.dtoClassName = this.getClass().getName().substring(29).replace("DAO", "DTO");
    }

    /**
     * Creates entity manager
     * @return EntityManager
     */
    protected EntityManager createEntityManager()
    {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Executes single result query on database.
     * @param query
     * @return
     */
    protected T executeSingleResultQuery(String query)
    {
        return (T) entityManager.createNativeQuery(query, entityClass).getSingleResult();
    }

    /**
     * Executes multi result query on databse
     * @param query
     * @return
     */
    protected List<T> executeMultiResultQuery(String query)
    {
        return entityManager.createNativeQuery(query, entityClass).getResultList();
    }

    /**
     * Closes entity manager
     */
    public void closeEntityManager()
    {
        entityManager.close();
    }

    /**
     * Returns number of all entites in table
     * @return
     */
    public long getNumberOfAllEntities()
    {
        return (long) entityManager.createQuery("SELECT count(*) FROM " + tableName).getSingleResult();
    }

    /**
     * Returns multiple entites matching with statement
     * @param whereStmnt
     * @return
     */
    public List<T> getMultipleEntities(String whereStmnt)
    {
        return executeMultiResultQuery("SELECT * FROM " + tableName + " WHERE " + whereStmnt);
    }

    /**
     * Returns single entity matching with statement
     * @param whereStmnt
     * @return
     */
    public T getSingleEntity(String whereStmnt)
    {
        return executeSingleResultQuery("SELECT * FROM " + tableName + " WHERE " + whereStmnt);
    }

    /**
     * Returns all entites from table
     * @return
     */
    public List<T> getAllEntities()
    {
        return executeMultiResultQuery("SELECT * FROM " + tableName);
    }

    /**
     * Inserts new entity to table
     * @param entity
     */
    public void insert(T entity)
    {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.persist(entity);
        et.commit();
    }
}