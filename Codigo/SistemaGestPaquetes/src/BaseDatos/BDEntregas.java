package BaseDatos;

import Clases.Entregas;
import Clases.Historialpaquetes;
import java.util.List;
import javax.persistence.EntityManager;

public class BDEntregas {

    ConexionJPA conexion = new ConexionJPA();

    public void InsertarEntrega(Entregas objEntrega) {
        EntityManager em = conexion.getEntityManager();

        em.getTransaction().begin();
        em.persist(objEntrega);
        em.getTransaction().commit();

        em.close();
    }

    public List<Entregas> BuscarEntregaPorHistorial(Historialpaquetes objHistorial) {
        EntityManager em = conexion.getEntityManager();

        List<Entregas> lista = em.createQuery(
                "SELECT e FROM Entregas e WHERE e.idHistoriaPaq = :historial",
                Entregas.class)
                .setParameter("historial", objHistorial)
                .getResultList();

        em.close();
        return lista;
    }
}