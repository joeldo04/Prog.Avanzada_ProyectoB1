package BaseDatos;

import BaseDatos.exceptions.IllegalOrphanException;
import BaseDatos.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Paquetes;
import Clases.Usuarios;
import Clases.Entregas;
import Clases.Historialpaquetes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HistorialpaquetesJpaController implements Serializable {

    public HistorialpaquetesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public HistorialpaquetesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaGestPaquetesPU");    
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historialpaquetes historialpaquetes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paquetes idPaquete = historialpaquetes.getIdPaquete();
            if (idPaquete != null) {
                idPaquete = em.getReference(idPaquete.getClass(), idPaquete.getIdPaquete());
                historialpaquetes.setIdPaquete(idPaquete);
            }
            Usuarios idUsuario = historialpaquetes.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                historialpaquetes.setIdUsuario(idUsuario);
            }
            Entregas entregas = historialpaquetes.getEntregas();
            if (entregas != null) {
                entregas = em.getReference(entregas.getClass(), entregas.getIdEntrega());
                historialpaquetes.setEntregas(entregas);
            }
            em.persist(historialpaquetes);
            if (idPaquete != null) {
                idPaquete.getHistorialpaquetesCollection().add(historialpaquetes);
                idPaquete = em.merge(idPaquete);
            }
            if (idUsuario != null) {
                idUsuario.getHistorialpaquetesCollection().add(historialpaquetes);
                idUsuario = em.merge(idUsuario);
            }
            if (entregas != null) {
                Historialpaquetes oldIdHistoriaPaqOfEntregas = entregas.getIdHistoriaPaq();
                if (oldIdHistoriaPaqOfEntregas != null) {
                    oldIdHistoriaPaqOfEntregas.setEntregas(null);
                    oldIdHistoriaPaqOfEntregas = em.merge(oldIdHistoriaPaqOfEntregas);
                }
                entregas.setIdHistoriaPaq(historialpaquetes);
                entregas = em.merge(entregas);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historialpaquetes historialpaquetes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historialpaquetes persistentHistorialpaquetes = em.find(Historialpaquetes.class, historialpaquetes.getIdHistorial());
            Paquetes idPaqueteOld = persistentHistorialpaquetes.getIdPaquete();
            Paquetes idPaqueteNew = historialpaquetes.getIdPaquete();
            Usuarios idUsuarioOld = persistentHistorialpaquetes.getIdUsuario();
            Usuarios idUsuarioNew = historialpaquetes.getIdUsuario();
            Entregas entregasOld = persistentHistorialpaquetes.getEntregas();
            Entregas entregasNew = historialpaquetes.getEntregas();
            List<String> illegalOrphanMessages = null;
            if (entregasOld != null && !entregasOld.equals(entregasNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Entregas " + entregasOld + " since its idHistoriaPaq field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPaqueteNew != null) {
                idPaqueteNew = em.getReference(idPaqueteNew.getClass(), idPaqueteNew.getIdPaquete());
                historialpaquetes.setIdPaquete(idPaqueteNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                historialpaquetes.setIdUsuario(idUsuarioNew);
            }
            if (entregasNew != null) {
                entregasNew = em.getReference(entregasNew.getClass(), entregasNew.getIdEntrega());
                historialpaquetes.setEntregas(entregasNew);
            }
            historialpaquetes = em.merge(historialpaquetes);
            if (idPaqueteOld != null && !idPaqueteOld.equals(idPaqueteNew)) {
                idPaqueteOld.getHistorialpaquetesCollection().remove(historialpaquetes);
                idPaqueteOld = em.merge(idPaqueteOld);
            }
            if (idPaqueteNew != null && !idPaqueteNew.equals(idPaqueteOld)) {
                idPaqueteNew.getHistorialpaquetesCollection().add(historialpaquetes);
                idPaqueteNew = em.merge(idPaqueteNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getHistorialpaquetesCollection().remove(historialpaquetes);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getHistorialpaquetesCollection().add(historialpaquetes);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (entregasNew != null && !entregasNew.equals(entregasOld)) {
                Historialpaquetes oldIdHistoriaPaqOfEntregas = entregasNew.getIdHistoriaPaq();
                if (oldIdHistoriaPaqOfEntregas != null) {
                    oldIdHistoriaPaqOfEntregas.setEntregas(null);
                    oldIdHistoriaPaqOfEntregas = em.merge(oldIdHistoriaPaqOfEntregas);
                }
                entregasNew.setIdHistoriaPaq(historialpaquetes);
                entregasNew = em.merge(entregasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historialpaquetes.getIdHistorial();
                if (findHistorialpaquetes(id) == null) {
                    throw new NonexistentEntityException("The historialpaquetes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historialpaquetes historialpaquetes;
            try {
                historialpaquetes = em.getReference(Historialpaquetes.class, id);
                historialpaquetes.getIdHistorial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialpaquetes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Entregas entregasOrphanCheck = historialpaquetes.getEntregas();
            if (entregasOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Historialpaquetes (" + historialpaquetes + ") cannot be destroyed since the Entregas " + entregasOrphanCheck + " in its entregas field has a non-nullable idHistoriaPaq field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Paquetes idPaquete = historialpaquetes.getIdPaquete();
            if (idPaquete != null) {
                idPaquete.getHistorialpaquetesCollection().remove(historialpaquetes);
                idPaquete = em.merge(idPaquete);
            }
            Usuarios idUsuario = historialpaquetes.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getHistorialpaquetesCollection().remove(historialpaquetes);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(historialpaquetes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historialpaquetes> findHistorialpaquetesEntities() {
        return findHistorialpaquetesEntities(true, -1, -1);
    }

    public List<Historialpaquetes> findHistorialpaquetesEntities(int maxResults, int firstResult) {
        return findHistorialpaquetesEntities(false, maxResults, firstResult);
    }

    private List<Historialpaquetes> findHistorialpaquetesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historialpaquetes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Historialpaquetes findHistorialpaquetes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historialpaquetes.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialpaquetesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historialpaquetes> rt = cq.from(Historialpaquetes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
