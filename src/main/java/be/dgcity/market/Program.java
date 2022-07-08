package be.dgcity.market;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;


public class Program {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("PersistenceDemoMySQL");
        EntityManager manager = emf.createEntityManager();

//        Course c = manager.find(Course.class, "EG1010");
//        Section s = manager.find(Section.class, 1010);

        manager.getTransaction().begin();


        manager.getTransaction().commit();



        emf.close();

    }
}
