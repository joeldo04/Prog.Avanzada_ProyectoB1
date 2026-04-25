package BaseDatos;

import BaseDatos.exceptions.IllegalOrphanException;
import BaseDatos.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Clientes;
import Clases.Usuarios;
import Clases.Historialpaquetes;
import Clases.Paquetes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PaquetesJpaController implements Serializable {

    public PaquetesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paquetes paquetes) {
        if (paquetes.getHistorialpaquetesCollection() == null) {
            paquetes.setHistorialpaquetesCollection(new ArrayList<Historialpaquetes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes idDestinatario = paquetes.getIdDestinatario();
            if (idDestinatario != null) {
                idDestinatario = em.getReference(idDestinatario.getClass(), idDestinatario.getIdCliente());
                paquetes.setIdDestinatario(idDestinatario);
            }
            Clientes idRemitente = paquetes.getIdRemitente();
            if (idRemitente != null) {
                idRemitente = em.getReference(idRemitente.getClass(), idRemitente.getIdCliente());
                paquetes.setIdRemitente(idRemitente);
            }
            Usuarios idRepartidor = paquetes.getIdRepartidor();
            if (idRepartidor != null) {
                idRepartidor = em.getReference(idRepartidor.getClass(), idRepartidor.getIdUsuario());
                paquetes.setIdRepartidor(idRepartidor);
            }
            Collection<Historialpaquetes> attachedHistorialpaquetesCollection = new ArrayList<Historialpaquetes>();
            for (Historialpaquetes historialpaquetesCollectionHistorialpaquetesToAttach : paquetes.getHistorialpaquetesCollection()) {
                historialpaquetesCollectionHistorialpaquetesToAttach = em.getReference(historialpaquetesCollectionHistorialpaquetesToAttach.getClass(), historialpaquetesCollectionHistorialpaquetesToAttach.getIdHistorial());
                attachedHistorialpaquetesCollection.add(historialpaquetesCollectionHistorialpaquetesToAttach);
            }
            paquetes.setHistorialpaquetesCollection(attachedHistorialpaquetesCollection);
            em.persist(paquetes);
            if (idDestinatario != null) {
                idDestinatario.getPaquetesCollection().add(paquetes);
                idDestinatario = em.merge(idDestinatario);
            }
            if (idRemitente != null) {
                idRemitente.getPaquetesCollection().add(paquetes);
                idRemitente = em.merge(idRemitente);
            }
            if (idRepartidor != null) {
                idRepartidor.getPaquetesCollection().add(paquetes);
                idRepartidor = em.merge(idRepartidor);
            }
            for (Historialpaquetes historialpaquetesCollectionHistorialpaquetes : paquetes.getHistorialpaquetesCollection()) {
                Paquetes oldIdPaqueteOfHistorialpaquetesCollectionHistorialpaquetes = historialpaquetesCollectionHistorialpaquetes.getIdPaquete();
                historialpaquetesCollectionHistorialpaquetes.setIdPaquete(paquetes);
                historialpaquetesCollectionHistorialpaquetes = em.merge(historialpaquetesCollectionHistorialpaquetes);
                if (oldIdPaqueteOfHistorialpaquetesCollectionHistorialpaquetes != null) {
                    oldIdPaqueteOfHistorialpaquetesCollectionHistorialpaquetes.getHistorialpaquetesCollection().remove(historialpaquetesCollectionHistorialpaquetes);
                    oldIdPaqueteOfHistorialpaquetesCollectionHistorialpaquetes = em.merge(oldIdPaqueteOfHistorialpaquetesCollectionHistorialpaquetes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paquetes paquetes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paquetes persistentPaquetes = em.find(Paquetes.class, paquetes.getIdPaquete());
            Clientes idDestinatarioOld = persistentPaquetes.getIdDestinatario();
            Clientes idDestinatarioNew = paquetes.getIdDestinatario();
            Clientes idRemitenteOld = persistentPaquetes.getIdRemitente();
            Clientes idRemitenteNew = paquetes.getIdRemitente();
            Usuarios idRepartidorOld = persistentPaquetes.getIdRepartidor();
            Usuarios idRepartidorNew = paquetes.getIdRepartidor();
            Collection<Historialpaquetes> historialpaquetesCollectionOld = persistentPaquetes.getHistorialpaquetesCollection();
            Collection<Historialpaquetes> historialpaquetesCollectionNew = paquetes.getHistorialpaquetesCollection();
            List<String> illegalOrphanMessages = null;
            for (Historialpaquetes historialpaquetesCollectionOldHistorialpaquetes : historialpaquetesCollectionOld) {
                if (!historialpaquetesCollectionNew.contains(historialpaquetesCollectionOldHistorialpaquetes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialpaquetes " + historialpaquetesCollectionOldHistorialpaquetes + " since its idPaquete field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDestinatarioNew != null) {
                idDestinatarioNew = em.getReference(idDestinatarioNew.getClass(), idDestinatarioNew.getIdCliente());
                paquetes.setIdDestinatario(idDestinatarioNew);
            }
            if (idRemitenteNew != null) {
                idRemitenteNew = em.getReference(idRemitenteNew.getClass(), idRemitenteNew.getIdCliente());
                paquetes.setIdRemitente(idRemitenteNew);
            }
            if (idRepartidorNew != null) {
                idRepartidorNew = em.getReference(idRepartidorNew.getClass(), idRepartidorNew.getIdUsuario());
                paquetes.setIdRepartidor(idRepartidorNew);
            }
            Collection<Historialpaquetes> attachedHistorialpaquetesCollectionNew = new ArrayList<Historialpaquetes>();
            for (Historialpaquetes historialpaquetesCollectionNewHistorialpaquetesToAttach : historialpaquetesCollectionNew) {
                historialpaquetesCollectionNewHistorialpaquetesToAttach = em.getReference(historialpaquetesCollectionNewHistorialpaquetesToAttach.getClass(), historialpaquetesCollectionNewHistorialpaquetesToAttach.getIdHistorial());
                attachedHistorialpaquetesCollectionNew.add(historialpaquetesCollectionNewHistorialpaquetesToAttach);
            }
            historialpaquetesCollectionNew = attachedHistorialpaquetesCollectionNew;
            paquetes.setHistorialpaquetesCollection(historialpaquetesCollectionNew);
            paquetes = em.merge(paquetes);
            if (idDestinatarioOld != null && !idDestinatarioOld.equals(idDestinatarioNew)) {
                idDestinatarioOld.getPaquetesCollection().remove(paquetes);
                idDestinatarioOld = em.merge(idDestinatarioOld);
            }
            if (idDestinatarioNew != null && !idDestinatarioNew.equals(idDestinatarioOld)) {
                idDestinatarioNew.getPaquetesCollection().add(paquetes);
                idDestinatarioNew = em.merge(idDestinatarioNew);
            }
            if (idRemitenteOld != null && !idRemitenteOld.equals(idRemitenteNew)) {
                idRemitenteOld.getPaquetesCollection().remove(paquetes);
                idRemitenteOld = em.merge(idRemitenteOld);
            }
            if (idRemitenteNew != null && !idRemitenteNew.equals(idRemitenteOld)) {
                idRemitenteNew.getPaquetesCollection().add(paquetes);
                idRemitenteNew = em.merge(idRemitenteNew);
            }
            if (idRepartidorOld != null && !idRepartidorOld.equals(idRepartidorNew)) {
                idRepartidorOld.getPaquetesCollection().remove(paquetes);
                idRepartidorOld = em.merge(idRepartidorOld);
            }
            if (idRepartidorNew != null && !idRepartidorNew.equals(idRepartidorOld)) {
                idRepartidorNew.getPaquetesCollection().add(paquetes);
                idRepartidorNew = em.merge(idRepartidorNew);
            }
            for (Historialpaquetes historialpaquetesCollectionNewHistorialpaquetes : historialpaquetesCollectionNew) {
                if (!historialpaquetesCollectionOld.contains(historialpaquetesCollectionNewHistorialpaquetes)) {
                    Paquetes oldIdPaqueteOfHistorialpaquetesCollectionNewHistorialpaquetes = historialpaquetesCollectionNewHistorialpaquetes.getIdPaquete();
                    historialpaquetesCollectionNewHistorialpaquetes.setIdPaquete(paquetes);
                    historialpaquetesCollectionNewHistorialpaquetes = em.merge(historialpaquetesCollectionNewHistorialpaquetes);
                    if (oldIdPaqueteOfHistorialpaquetesCollectionNewHistorialpaquetes != null && !oldIdPaqueteOfHistorialpaquetesCollectionNewHistorialpaquetes.equals(paquetes)) {
                        oldIdPaqueteOfHistorialpaquetesCollectionNewHistorialpaquetes.getHistorialpaquetesCollection().remove(historialpaquetesCollectionNewHistorialpaquetes);
                        oldIdPaqueteOfHistorialpaquetesCollectionNewHistorialpaquetes = em.merge(oldIdPaqueteOfHistorialpaquetesCollectionNewHistorialpaquetes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = paquetes.getIdPaquete();
                if (findPaquetes(id) == null) {
                    throw new NonexistentEntityException("The paquetes with id " + id + " no longer exists.");
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
            Paquetes paquetes;
            try {
                paquetes = em.getReference(Paquetes.class, id);
                paquetes.getIdPaquete();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paquetes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Historialpaquetes> historialpaquetesCollectionOrphanCheck = paquetes.getHistorialpaquetesCollection();
            for (Historialpaquetes historialpaquetesCollectionOrphanCheckHistorialpaquetes : historialpaquetesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paquetes (" + paquetes + ") cannot be destroyed since the Historialpaquetes " + historialpaquetesCollectionOrphanCheckHistorialpaquetes + " in its historialpaquetesCollection field has a non-nullable idPaquete field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes idDestinatario = paquetes.getIdDestinatario();
            if (idDestinatario != null) {
                idDestinatario.getPaquetesCollection().remove(paquetes);
                idDestinatario = em.merge(idDestinatario);
            }
            Clientes idRemitente = paquetes.getIdRemitente();
            if (idRemitente != null) {
                idRemitente.getPaquetesCollection().remove(paquetes);
                idRemitente = em.merge(idRemitente);
            }
            Usuarios idRepartidor = paquetes.getIdRepartidor();
            if (idRepartidor != null) {
                idRepartidor.getPaquetesCollection().remove(paquetes);
                idRepartidor = em.merge(idRepartidor);
            }
            em.remove(paquetes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paquetes> findPaquetesEntities() {
        return findPaquetesEntities(true, -1, -1);
    }

    public List<Paquetes> findPaquetesEntities(int maxResults, int firstResult) {
        return findPaquetesEntities(false, maxResults, firstResult);
    }

    private List<Paquetes> findPaquetesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paquetes.class));
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

    public Paquetes findPaquetes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paquetes.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaquetesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paquetes> rt = cq.from(Paquetes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
