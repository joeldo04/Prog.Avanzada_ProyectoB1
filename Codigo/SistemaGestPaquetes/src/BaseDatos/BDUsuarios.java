package BaseDatos;

import Clases.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;

public class BDUsuarios {

    ConexionJPA conexion = new ConexionJPA();

    public List<Usuarios> BuscarUsuarioCedula(Usuarios objUsuario) {
        EntityManager em = conexion.getEntityManager();

        List<Usuarios> lista = em.createQuery(
                "SELECT u FROM Usuarios u WHERE u.cedula = :cedula",
                Usuarios.class)
                .setParameter("cedula", objUsuario.getCedula())
                .getResultList();

        em.close();
        return lista;
    }

    public List<Usuarios> ListarRepartidores() {
        EntityManager em = conexion.getEntityManager();

        List<Usuarios> lista = em.createQuery(
                "SELECT u FROM Usuarios u WHERE u.idRol.nombreRol = :rol",
                Usuarios.class)
                .setParameter("rol", "REPARTIDOR")
                .getResultList();

        em.close();
        return lista;
    }
}
