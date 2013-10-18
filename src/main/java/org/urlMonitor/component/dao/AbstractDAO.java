package org.urlMonitor.component.dao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class AbstractDAO<E, I extends Serializable>
{
   private Class<E> entityClass;

   protected AbstractDAO(Class<E> entityClass)
   {
      this.entityClass = entityClass;
   }

   //   @Autowired
   //   private SessionFactory sessionFactory;

   protected EntityManager entityManager;

   @PersistenceContext
   public void setEntityManager(EntityManager entityManager)
   {
      this.entityManager = entityManager;
   }

   public EntityManager getEntityManager()
   {
      return entityManager;
   }

   public E findById(I id)
   {
      return (E) getEntityManager().find(entityClass, id);
   }

   public void saveOrUpdate(E e)
   {
      getEntityManager().persist(e);
   }

   public void delete(E e)
   {
      getEntityManager().remove(e);
   }
}
