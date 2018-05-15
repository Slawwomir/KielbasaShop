package pl.KielbasaShop.services;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;
import java.util.function.Function;

abstract public class EntityService <T> {
    final EntityManager manager;
    private final Class<T> entityClass;
    private final Function<T, Object> idSupplier;

    public EntityService(EntityManager manager, Class<T> entityClass, Function<T, Object> idSupplier) {
        this.manager = manager;
        this.entityClass = entityClass;
        this.idSupplier = idSupplier;
    }

    @Transactional
    public void save(T entity){
        if(manager.find(entityClass, idSupplier.apply(entity)) == null)
            manager.persist(entity);
        else
            manager.merge(entity);
    }

    public T find(UUID id){
        return manager.find(entityClass, id);
    }
}
