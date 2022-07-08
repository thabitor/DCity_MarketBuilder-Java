package be.dgcity.market.DAOs;

import be.dgcity.market.Entities.Aisle;
import be.dgcity.market.Exceptions.EntityAlreadyExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class AisleDAO {

    private final EntityManager manager;

    public AisleDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Optional<Aisle> getById(int id ){
        return Optional.ofNullable( manager.find(Aisle.class, id) );
    }

    public List<Aisle> getAll(){
        TypedQuery<Aisle> query = manager.createQuery("SELECT a.aisleId FROM Aisle a", Aisle.class);
        return query.getResultList();
    }

    public void insert( Aisle toInsert ){

//        if( toInsert == null )
//            throw new IllegalArgumentException();
//
//        if( existsById(toInsert.getId()) )
//            throw new EntityAlreadyExistsException(toInsert.getId(), Aisle.class);
//
//        manager.getTransaction().begin();
//        manager.persist(toInsert);
//        manager.getTransaction().commit();

        try{
            beginTransact();
            manager.persist(toInsert);
            commit();
        }
        catch (RollbackException ex){
            throw new EntityAlreadyExistsException(toInsert.getAisleId(), Aisle.class);
        }

    }

    public void deleteById(int id){

        beginTransact();
        getById(id).ifPresent( manager::remove );
        commit();

    }

    public void delete( Aisle toDelete ){
        deleteById(toDelete.getAisleId());
    }


    public void update( Aisle aisle ){

        if( aisle == null )
            throw new IllegalArgumentException("section cannot be null");

        if( !existsById(aisle.getAisleId()) )
            throw new EntityNotFoundException("Entity not found");

        beginTransact();
        manager.merge(aisle);
        commit();
    }

    public boolean existsById(int id){
        TypedQuery<Integer> query = manager.createQuery("SELECT COUNT(a) FROM Aisle a WHERE a.aisleId = " + id, Integer.class);
        return query.getSingleResult() > 0;
    }


    private void beginTransact(){
        manager.getTransaction().begin();
    }

    private void commit(){
        manager.getTransaction().commit();
    }
}
