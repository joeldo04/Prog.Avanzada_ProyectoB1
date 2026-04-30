package BaseDatos;

import Clases.Paquetes;
import Clases.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;

public class BDPaquetes {

    ConexionJPA conexion = new ConexionJPA();

    public void InsertarPaquete(Paquetes objPaquete) {
        EntityManager em = conexion.getEntityManager();

        em.getTransaction().begin();
        em.persist(objPaquete);
        em.getTransaction().commit();

        em.close();
    }

    public List<Paquetes> BuscarPaqueteCodigo(Paquetes objPaquete) {
        EntityManager em = conexion.getEntityManager();

        List<Paquetes> lista = em.createQuery(
                "SELECT p FROM Paquetes p WHERE p.codigoPaquete = :codigo",
                Paquetes.class)
                .setParameter("codigo", objPaquete.getCodigoPaquete())
                .getResultList();

        em.close();
        return lista;
    }

    public List<Paquetes> BuscarPaqueteSeguimiento(Paquetes objPaquete) {
        EntityManager em = conexion.getEntityManager();

        List<Paquetes> lista = em.createQuery(
                "SELECT p FROM Paquetes p WHERE p.numSeguimiento = :seguimiento",
                Paquetes.class)
                .setParameter("seguimiento", objPaquete.getNumSeguimiento())
                .getResultList();

        em.close();
        return lista;
    }

    public List<Paquetes> ListarPaquetesAsignados(Usuarios objRepartidor) {
        EntityManager em = conexion.getEntityManager();

        List<Paquetes> lista = em.createQuery(
                "SELECT p FROM Paquetes p "
                + "WHERE p.idRepartidor = :repartidor "
                + "AND p.estadoActualPaquete = :estado",
                Paquetes.class)
                .setParameter("repartidor", objRepartidor)
                .setParameter("estado", "EN_TRANSITO")
                .getResultList();

        em.close();
        return lista;
    }

    public void ActualizarEstadoPaquete(Paquetes objPaquete) {
        EntityManager em = conexion.getEntityManager();

        em.getTransaction().begin();

        em.createQuery(
                "UPDATE Paquetes p "
                + "SET p.estadoActualPaquete = :estado "
                + "WHERE p.idPaquete = :idPaquete")
                .setParameter("estado", objPaquete.getEstadoActualPaquete())
                .setParameter("idPaquete", objPaquete.getIdPaquete())
                .executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    public void ActualizarEstadoYRepartidor(Paquetes objPaquete) {
        EntityManager em = conexion.getEntityManager();

        Usuarios repartidor = em.find(
                Usuarios.class,
                objPaquete.getIdRepartidor().getIdUsuario()
        );

        em.getTransaction().begin();

        em.createQuery(
                "UPDATE Paquetes p "
                + "SET p.estadoActualPaquete = :estado, "
                + "p.idRepartidor = :repartidor "
                + "WHERE p.idPaquete = :idPaquete")
                .setParameter("estado", objPaquete.getEstadoActualPaquete())
                .setParameter("repartidor", repartidor)
                .setParameter("idPaquete", objPaquete.getIdPaquete())
                .executeUpdate();

        em.getTransaction().commit();
        em.close();
    }
}