package BaseDatos;

import Clases.Historialpaquetes;
import Clases.Paquetes;
import java.util.List;
import javax.persistence.EntityManager;

public class BDHistorialPaquetes {

    ConexionJPA conexion = new ConexionJPA();

    public Historialpaquetes InsertarHistorial(Historialpaquetes objHistorial) {
        EntityManager em = conexion.getEntityManager();

        em.getTransaction().begin();
        em.persist(objHistorial);
        em.getTransaction().commit();

        em.close();
        return objHistorial;
    }

    public List<Historialpaquetes> ListarHistorialPaquete(Paquetes objPaquete) {
        EntityManager em = conexion.getEntityManager();

        List<Historialpaquetes> lista = em.createQuery(
                "SELECT h FROM Historialpaquetes h "
                + "WHERE h.idPaquete = :paquete "
                + "ORDER BY h.fecha ASC",
                Historialpaquetes.class)
                .setParameter("paquete", objPaquete)
                .getResultList();

        em.close();
        return lista;
    }
}