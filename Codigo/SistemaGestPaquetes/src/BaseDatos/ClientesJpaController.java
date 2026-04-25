package BaseDatos;

import BaseDatos.exceptions.IllegalOrphanException;
import BaseDatos.exceptions.NonexistentEntityException;
import Clases.Clientes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Paquetes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ClientesJpaController implements Serializable {

    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) {
        if (clientes.getPaquetesCollection() == null) {
            clientes.setPaquetesCollection(new ArrayList<Paquetes>());
        }
        if (clientes.getPaquetesCollection1() == null) {
            clientes.setPaquetesCollection1(new ArrayList<Paquetes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Paquetes> attachedPaquetesCollection = new ArrayList<Paquetes>();
            for (Paquetes paquetesCollectionPaquetesToAttach : clientes.getPaquetesCollection()) {
                paquetesCollectionPaquetesToAttach = em.getReference(paquetesCollectionPaquetesToAttach.getClass(), paquetesCollectionPaquetesToAttach.getIdPaquete());
                attachedPaquetesCollection.add(paquetesCollectionPaquetesToAttach);
            }
            clientes.setPaquetesCollection(attachedPaquetesCollection);
            Collection<Paquetes> attachedPaquetesCollection1 = new ArrayList<Paquetes>();
            for (Paquetes paquetesCollection1PaquetesToAttach : clientes.getPaquetesCollection1()) {
                paquetesCollection1PaquetesToAttach = em.getReference(paquetesCollection1PaquetesToAttach.getClass(), paquetesCollection1PaquetesToAttach.getIdPaquete());
                attachedPaquetesCollection1.add(paquetesCollection1PaquetesToAttach);
            }
            clientes.setPaquetesCollection1(attachedPaquetesCollection1);
            em.persist(clientes);
            for (Paquetes paquetesCollectionPaquetes : clientes.getPaquetesCollection()) {
                Clientes oldIdDestinatarioOfPaquetesCollectionPaquetes = paquetesCollectionPaquetes.getIdDestinatario();
                paquetesCollectionPaquetes.setIdDestinatario(clientes);
                paquetesCollectionPaquetes = em.merge(paquetesCollectionPaquetes);
                if (oldIdDestinatarioOfPaquetesCollectionPaquetes != null) {
                    oldIdDestinatarioOfPaquetesCollectionPaquetes.getPaquetesCollection().remove(paquetesCollectionPaquetes);
                    oldIdDestinatarioOfPaquetesCollectionPaquetes = em.merge(oldIdDestinatarioOfPaquetesCollectionPaquetes);
                }
            }
            for (Paquetes paquetesCollection1Paquetes : clientes.getPaquetesCollection1()) {
                Clientes oldIdRemitenteOfPaquetesCollection1Paquetes = paquetesCollection1Paquetes.getIdRemitente();
                paquetesCollection1Paquetes.setIdRemitente(clientes);
                paquetesCollection1Paquetes = em.merge(paquetesCollection1Paquetes);
                if (oldIdRemitenteOfPaquetesCollection1Paquetes != null) {
                    oldIdRemitenteOfPaquetesCollection1Paquetes.getPaquetesCollection1().remove(paquetesCollection1Paquetes);
                    oldIdRemitenteOfPaquetesCollection1Paquetes = em.merge(oldIdRemitenteOfPaquetesCollection1Paquetes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getIdCliente());
            Collection<Paquetes> paquetesCollectionOld = persistentClientes.getPaquetesCollection();
            Collection<Paquetes> paquetesCollectionNew = clientes.getPaquetesCollection();
            Collection<Paquetes> paquetesCollection1Old = persistentClientes.getPaquetesCollection1();
            Collection<Paquetes> paquetesCollection1New = clientes.getPaquetesCollection1();
            List<String> illegalOrphanMessages = null;
            for (Paquetes paquetesCollectionOldPaquetes : paquetesCollectionOld) {
                if (!paquetesCollectionNew.contains(paquetesCollectionOldPaquetes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Paquetes " + paquetesCollectionOldPaquetes + " since its idDestinatario field is not nullable.");
                }
            }
            for (Paquetes paquetesCollection1OldPaquetes : paquetesCollection1Old) {
                if (!paquetesCollection1New.contains(paquetesCollection1OldPaquetes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Paquetes " + paquetesCollection1OldPaquetes + " since its idRemitente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Paquetes> attachedPaquetesCollectionNew = new ArrayList<Paquetes>();
            for (Paquetes paquetesCollectionNewPaquetesToAttach : paquetesCollectionNew) {
                paquetesCollectionNewPaquetesToAttach = em.getReference(paquetesCollectionNewPaquetesToAttach.getClass(), paquetesCollectionNewPaquetesToAttach.getIdPaquete());
                attachedPaquetesCollectionNew.add(paquetesCollectionNewPaquetesToAttach);
            }
            paquetesCollectionNew = attachedPaquetesCollectionNew;
            clientes.setPaquetesCollection(paquetesCollectionNew);
            Collection<Paquetes> attachedPaquetesCollection1New = new ArrayList<Paquetes>();
            for (Paquetes paquetesCollection1NewPaquetesToAttach : paquetesCollection1New) {
                paquetesCollection1NewPaquetesToAttach = em.getReference(paquetesCollection1NewPaquetesToAttach.getClass(), paquetesCollection1NewPaquetesToAttach.getIdPaquete());
                attachedPaquetesCollection1New.add(paquetesCollection1NewPaquetesToAttach);
            }
            paquetesCollection1New = attachedPaquetesCollection1New;
            clientes.setPaquetesCollection1(paquetesCollection1New);
            clientes = em.merge(clientes);
            for (Paquetes paquetesCollectionNewPaquetes : paquetesCollectionNew) {
                if (!paquetesCollectionOld.contains(paquetesCollectionNewPaquetes)) {
                    Clientes oldIdDestinatarioOfPaquetesCollectionNewPaquetes = paquetesCollectionNewPaquetes.getIdDestinatario();
                    paquetesCollectionNewPaquetes.setIdDestinatario(clientes);
                    paquetesCollectionNewPaquetes = em.merge(paquetesCollectionNewPaquetes);
                    if (oldIdDestinatarioOfPaquetesCollectionNewPaquetes != null && !oldIdDestinatarioOfPaquetesCollectionNewPaquetes.equals(clientes)) {
                        oldIdDestinatarioOfPaquetesCollectionNewPaquetes.getPaquetesCollection().remove(paquetesCollectionNewPaquetes);
                        oldIdDestinatarioOfPaquetesCollectionNewPaquetes = em.merge(oldIdDestinatarioOfPaquetesCollectionNewPaquetes);
                    }
                }
            }
            for (Paquetes paquetesCollection1NewPaquetes : paquetesCollection1New) {
                if (!paquetesCollection1Old.contains(paquetesCollection1NewPaquetes)) {
                    Clientes oldIdRemitenteOfPaquetesCollection1NewPaquetes = paquetesCollection1NewPaquetes.getIdRemitente();
                    paquetesCollection1NewPaquetes.setIdRemitente(clientes);
                    paquetesCollection1NewPaquetes = em.merge(paquetesCollection1NewPaquetes);
                    if (oldIdRemitenteOfPaquetesCollection1NewPaquetes != null && !oldIdRemitenteOfPaquetesCollection1NewPaquetes.equals(clientes)) {
                        oldIdRemitenteOfPaquetesCollection1NewPaquetes.getPaquetesCollection1().remove(paquetesCollection1NewPaquetes);
                        oldIdRemitenteOfPaquetesCollection1NewPaquetes = em.merge(oldIdRemitenteOfPaquetesCollection1NewPaquetes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientes.getIdCliente();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Paquetes> paquetesCollectionOrphanCheck = clientes.getPaquetesCollection();
            for (Paquetes paquetesCollectionOrphanCheckPaquetes : paquetesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Paquetes " + paquetesCollectionOrphanCheckPaquetes + " in its paquetesCollection field has a non-nullable idDestinatario field.");
            }
            Collection<Paquetes> paquetesCollection1OrphanCheck = clientes.getPaquetesCollection1();
            for (Paquetes paquetesCollection1OrphanCheckPaquetes : paquetesCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Paquetes " + paquetesCollection1OrphanCheckPaquetes + " in its paquetesCollection1 field has a non-nullable idRemitente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
