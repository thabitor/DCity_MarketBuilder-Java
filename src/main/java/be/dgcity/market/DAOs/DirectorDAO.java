package be.dgcity.market.DAOs;
import be.dgcity.market.Entities.Director;
import be.dgcity.market.Exceptions.EntityAlreadyExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class DirectorDAO {
    private final EntityManager manager;

    public DirectorDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Optional<Director> getById(int id ){
        return Optional.ofNullable( manager.find(Director.class, id) );
    }

    public List<Director> getAll(){
        TypedQuery<Director> query = manager.createQuery("SELECT d.directorId FROM Director d", Director.class);
        return query.getResultList();
    }

    public void insert( Director toInsert ){

        try{
            beginTransact();
            manager.persist(toInsert);
            commit();
        }
        catch (RollbackException ex){
            throw new EntityAlreadyExistsException(toInsert.getDirectorId(), Director.class);
        }

    }

    public void deleteById(int id){

        beginTransact();
        getById(id).ifPresent( manager::remove );
        commit();

    }

    public void delete( Director toDelete ){
        deleteById(toDelete.getDirectorId());
    }


    public void update( Director director ){

        if( director == null )
            throw new IllegalArgumentException("section cannot be null");

        if( !existsById(director.getDirectorId()) )
            throw new EntityNotFoundException("Entity not found");

        beginTransact();
        manager.merge(director);
        commit();
    }

    public boolean existsById(int id){
        TypedQuery<Integer> query = manager.createQuery("SELECT COUNT(d) FROM Director d WHERE d.directorId = " + id, Integer.class);
        return query.getSingleResult() > 0;
    }


    private void beginTransact(){
        manager.getTransaction().begin();
    }

    private void commit(){
        manager.getTransaction().commit();
    }
}
