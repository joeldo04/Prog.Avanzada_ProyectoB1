package BaseDatos;

import Clases.Clientes;
import java.util.List;
import javax.persistence.EntityManager;

public class BDClientes {

    ConexionJPA conexion = new ConexionJPA();

    public void InsertarCliente(Clientes objCliente) {
        EntityManager em = conexion.getEntityManager();

        em.getTransaction().begin();
        em.persist(objCliente);
        em.getTransaction().commit();

        em.close();
    }

    public List<Clientes> BuscarClienteCedula(Clientes objCliente) {
        EntityManager em = conexion.getEntityManager();

        List<Clientes> lista = em.createQuery(
                "SELECT c FROM Clientes c WHERE c.cedula = :cedula",
                Clientes.class)
                .setParameter("cedula", objCliente.getCedula())
                .getResultList();

        em.close();
        return lista;
    }

    public Clientes BuscarClienteId(Integer idCliente) {
        EntityManager em = conexion.getEntityManager();

        Clientes objCliente = em.find(Clientes.class, idCliente);

        em.close();
        return objCliente;
    }
}