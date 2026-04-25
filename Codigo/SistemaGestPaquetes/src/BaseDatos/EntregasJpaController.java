package BaseDatos;

import BaseDatos.exceptions.IllegalOrphanException;
import BaseDatos.exceptions.NonexistentEntityException;
import Clases.Entregas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Historialpaquetes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntregasJpaController implements Serializable {

    public EntregasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entregas entregas) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Historialpaquetes idHistoriaPaqOrphanCheck = entregas.getIdHistoriaPaq();
        if (idHistoriaPaqOrphanCheck != null) {
            Entregas oldEntregasOfIdHistoriaPaq = idHistoriaPaqOrphanCheck.getEntregas();
            if (oldEntregasOfIdHistoriaPaq != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Historialpaquetes " + idHistoriaPaqOrphanCheck + " already has an item of type Entregas whose idHistoriaPaq column cannot be null. Please make another selection for the idHistoriaPaq field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historialpaquetes idHistoriaPaq = entregas.getIdHistoriaPaq();
            if (idHistoriaPaq != null) {
                idHistoriaPaq = em.getReference(idHistoriaPaq.getClass(), idHistoriaPaq.getIdHistorial());
                entregas.setIdHistoriaPaq(idHistoriaPaq);
            }
            em.persist(entregas);
            if (idHistoriaPaq != null) {
                idHistoriaPaq.setEntregas(entregas);
                idHistoriaPaq = em.merge(idHistoriaPaq);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entregas entregas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entregas persistentEntregas = em.find(Entregas.class, entregas.getIdEntrega());
            Historialpaquetes idHistoriaPaqOld = persistentEntregas.getIdHistoriaPaq();
            Historialpaquetes idHistoriaPaqNew = entregas.getIdHistoriaPaq();
            List<String> illegalOrphanMessages = null;
            if (idHistoriaPaqNew != null && !idHistoriaPaqNew.equals(idHistoriaPaqOld)) {
                Entregas oldEntregasOfIdHistoriaPaq = idHistoriaPaqNew.getEntregas();
                if (oldEntregasOfIdHistoriaPaq != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Historialpaquetes " + idHistoriaPaqNew + " already has an item of type Entregas whose idHistoriaPaq column cannot be null. Please make another selection for the idHistoriaPaq field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idHistoriaPaqNew != null) {
                idHistoriaPaqNew = em.getReference(idHistoriaPaqNew.getClass(), idHistoriaPaqNew.getIdHistorial());
                entregas.setIdHistoriaPaq(idHistoriaPaqNew);
            }
            entregas = em.merge(entregas);
            if (idHistoriaPaqOld != null && !idHistoriaPaqOld.equals(idHistoriaPaqNew)) {
                idHistoriaPaqOld.setEntregas(null);
                idHistoriaPaqOld = em.merge(idHistoriaPaqOld);
            }
            if (idHistoriaPaqNew != null && !idHistoriaPaqNew.equals(idHistoriaPaqOld)) {
                idHistoriaPaqNew.setEntregas(entregas);
                idHistoriaPaqNew = em.merge(idHistoriaPaqNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = entregas.getIdEntrega();
                if (findEntregas(id) == null) {
                    throw new NonexistentEntityException("The entregas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entregas entregas;
            try {
                entregas = em.getReference(Entregas.class, id);
                entregas.getIdEntrega();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entregas with id " + id + " no longer exists.", enfe);
            }
            Historialpaquetes idHistoriaPaq = entregas.getIdHistoriaPaq();
            if (idHistoriaPaq != null) {
                idHistoriaPaq.setEntregas(null);
                idHistoriaPaq = em.merge(idHistoriaPaq);
            }
            em.remove(entregas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entregas> findEntregasEntities() {
        return findEntregasEntities(true, -1, -1);
    }

    public List<Entregas> findEntregasEntities(int maxResults, int firstResult) {
        return findEntregasEntities(false, maxResults, firstResult);
    }

    private List<Entregas> findEntregasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entregas.class));
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

    public Entregas findEntregas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entregas.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntregasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entregas> rt = cq.from(Entregas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
