package be.dgcity.market.DAOs;


import be.dgcity.market.Entities.Product;
import be.dgcity.market.Exceptions.EntityAlreadyExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ProductDAO {

        private final EntityManager manager;

        public ProductDAO(EntityManager manager) {
            this.manager = manager;
        }

        public Optional<Product> getById(String id ){
            return Optional.ofNullable( manager.find(Product.class, id) );
        }

        public List<Product> getAll(){
            TypedQuery<Product> query = manager.createQuery("SELECT p.productId FROM Product p", Product.class);
            return query.getResultList();
        }

        public void insert( Product toInsert ){

            try{
                beginTransact();
                manager.persist(toInsert);
                commit();
            }
            catch (RollbackException ex){
                throw new EntityAlreadyExistsException(toInsert.getProductId(), Product.class);
            }

        }

        public void deleteById(String id){

            beginTransact();
            getById(id).ifPresent( manager::remove );
            commit();

        }

        public void delete( Product toDelete ){
            deleteById(toDelete.getProductId());
        }


        public void update( Product product ){

            if( product == null )
                throw new IllegalArgumentException("section cannot be null");

            if( !existsById(product.getProductId()) )
                throw new EntityNotFoundException("Entity not found");

            beginTransact();
            manager.merge(product);
            commit();
        }

        public boolean existsById(String id){
            TypedQuery<Integer> query = manager.createQuery("SELECT COUNT(p) FROM Product p WHERE p.productId = " + id, Integer.class);
            return query.getSingleResult() > 0;
        }


        private void beginTransact(){
            manager.getTransaction().begin();
        }

        private void commit(){
            manager.getTransaction().commit();
        }


    }

