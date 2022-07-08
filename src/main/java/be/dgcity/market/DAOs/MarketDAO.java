package be.dgcity.market.DAOs;

import be.dgcity.market.Entities.Market;
import be.dgcity.market.Exceptions.EntityAlreadyExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class MarketDAO {
    private final EntityManager manager;

    public MarketDAO(EntityManager manager) {
        this.manager = manager;
    }

    public Optional<Market> getById(int id ){
        return Optional.ofNullable( manager.find(Market.class, id) );
    }

    public List<Market> getAll(){
        TypedQuery<Market> query = manager.createQuery("SELECT m.marketId FROM Market m", Market.class);
        return query.getResultList();
    }

    public void insert( Market toInsert ){

        try{
            beginTransact();
            manager.persist(toInsert);
            commit();
        }
        catch (RollbackException ex){
            throw new EntityAlreadyExistsException(toInsert.getMarketId(), Market.class);
        }

    }

    public void deleteById(int id){

        beginTransact();
        getById(id).ifPresent( manager::remove );
        commit();

    }

    public void delete( Market toDelete ){
        deleteById(toDelete.getMarketId());
    }


    public void update( Market market ){

        if( market == null )
            throw new IllegalArgumentException("section cannot be null");

        if( !existsById(market.getMarketId()) )
            throw new EntityNotFoundException("Entity not found");

        beginTransact();
        manager.merge(market);
        commit();
    }

    public boolean existsById(int id){
        TypedQuery<Integer> query = manager.createQuery("SELECT COUNT(m) FROM Market m WHERE m.marketId = " + id, Integer.class);
        return query.getSingleResult() > 0;
    }


    private void beginTransact(){
        manager.getTransaction().begin();
    }

    private void commit(){
        manager.getTransaction().commit();
    }


}
