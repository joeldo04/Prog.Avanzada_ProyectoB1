package BaseDatos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConexionJPA {

    EntityManagerFactory emf;

    public ConexionJPA() {
        emf = Persistence.createEntityManagerFactory("SistemaGestPaquetesPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}