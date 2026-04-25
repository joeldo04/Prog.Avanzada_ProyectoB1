package BaseDatos;

import BaseDatos.exceptions.IllegalOrphanException;
import BaseDatos.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Rolessistema;
import Clases.Historialpaquetes;
import java.util.ArrayList;
import java.util.Collection;
import Clases.Paquetes;
import Clases.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getHistorialpaquetesCollection() == null) {
            usuarios.setHistorialpaquetesCollection(new ArrayList<Historialpaquetes>());
        }
        if (usuarios.getPaquetesCollection() == null) {
            usuarios.setPaquetesCollection(new ArrayList<Paquetes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rolessistema idRol = usuarios.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getIdRol());
                usuarios.setIdRol(idRol);
            }
            Collection<Historialpaquetes> attachedHistorialpaquetesCollection = new ArrayList<Historialpaquetes>();
            for (Historialpaquetes historialpaquetesCollectionHistorialpaquetesToAttach : usuarios.getHistorialpaquetesCollection()) {
                historialpaquetesCollectionHistorialpaquetesToAttach = em.getReference(historialpaquetesCollectionHistorialpaquetesToAttach.getClass(), historialpaquetesCollectionHistorialpaquetesToAttach.getIdHistorial());
                attachedHistorialpaquetesCollection.add(historialpaquetesCollectionHistorialpaquetesToAttach);
            }
            usuarios.setHistorialpaquetesCollection(attachedHistorialpaquetesCollection);
            Collection<Paquetes> attachedPaquetesCollection = new ArrayList<Paquetes>();
            for (Paquetes paquetesCollectionPaquetesToAttach : usuarios.getPaquetesCollection()) {
                paquetesCollectionPaquetesToAttach = em.getReference(paquetesCollectionPaquetesToAttach.getClass(), paquetesCollectionPaquetesToAttach.getIdPaquete());
                attachedPaquetesCollection.add(paquetesCollectionPaquetesToAttach);
            }
            usuarios.setPaquetesCollection(attachedPaquetesCollection);
            em.persist(usuarios);
            if (idRol != null) {
                idRol.getUsuariosCollection().add(usuarios);
                idRol = em.merge(idRol);
            }
            for (Historialpaquetes historialpaquetesCollectionHistorialpaquetes : usuarios.getHistorialpaquetesCollection()) {
                Usuarios oldIdUsuarioOfHistorialpaquetesCollectionHistorialpaquetes = historialpaquetesCollectionHistorialpaquetes.getIdUsuario();
                historialpaquetesCollectionHistorialpaquetes.setIdUsuario(usuarios);
                historialpaquetesCollectionHistorialpaquetes = em.merge(historialpaquetesCollectionHistorialpaquetes);
                if (oldIdUsuarioOfHistorialpaquetesCollectionHistorialpaquetes != null) {
                    oldIdUsuarioOfHistorialpaquetesCollectionHistorialpaquetes.getHistorialpaquetesCollection().remove(historialpaquetesCollectionHistorialpaquetes);
                    oldIdUsuarioOfHistorialpaquetesCollectionHistorialpaquetes = em.merge(oldIdUsuarioOfHistorialpaquetesCollectionHistorialpaquetes);
                }
            }
            for (Paquetes paquetesCollectionPaquetes : usuarios.getPaquetesCollection()) {
                Usuarios oldIdRepartidorOfPaquetesCollectionPaquetes = paquetesCollectionPaquetes.getIdRepartidor();
                paquetesCollectionPaquetes.setIdRepartidor(usuarios);
                paquetesCollectionPaquetes = em.merge(paquetesCollectionPaquetes);
                if (oldIdRepartidorOfPaquetesCollectionPaquetes != null) {
                    oldIdRepartidorOfPaquetesCollectionPaquetes.getPaquetesCollection().remove(paquetesCollectionPaquetes);
                    oldIdRepartidorOfPaquetesCollectionPaquetes = em.merge(oldIdRepartidorOfPaquetesCollectionPaquetes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdUsuario());
            Rolessistema idRolOld = persistentUsuarios.getIdRol();
            Rolessistema idRolNew = usuarios.getIdRol();
            Collection<Historialpaquetes> historialpaquetesCollectionOld = persistentUsuarios.getHistorialpaquetesCollection();
            Collection<Historialpaquetes> historialpaquetesCollectionNew = usuarios.getHistorialpaquetesCollection();
            Collection<Paquetes> paquetesCollectionOld = persistentUsuarios.getPaquetesCollection();
            Collection<Paquetes> paquetesCollectionNew = usuarios.getPaquetesCollection();
            List<String> illegalOrphanMessages = null;
            for (Historialpaquetes historialpaquetesCollectionOldHistorialpaquetes : historialpaquetesCollectionOld) {
                if (!historialpaquetesCollectionNew.contains(historialpaquetesCollectionOldHistorialpaquetes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialpaquetes " + historialpaquetesCollectionOldHistorialpaquetes + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getIdRol());
                usuarios.setIdRol(idRolNew);
            }
            Collection<Historialpaquetes> attachedHistorialpaquetesCollectionNew = new ArrayList<Historialpaquetes>();
            for (Historialpaquetes historialpaquetesCollectionNewHistorialpaquetesToAttach : historialpaquetesCollectionNew) {
                historialpaquetesCollectionNewHistorialpaquetesToAttach = em.getReference(historialpaquetesCollectionNewHistorialpaquetesToAttach.getClass(), historialpaquetesCollectionNewHistorialpaquetesToAttach.getIdHistorial());
                attachedHistorialpaquetesCollectionNew.add(historialpaquetesCollectionNewHistorialpaquetesToAttach);
            }
            historialpaquetesCollectionNew = attachedHistorialpaquetesCollectionNew;
            usuarios.setHistorialpaquetesCollection(historialpaquetesCollectionNew);
            Collection<Paquetes> attachedPaquetesCollectionNew = new ArrayList<Paquetes>();
            for (Paquetes paquetesCollectionNewPaquetesToAttach : paquetesCollectionNew) {
                paquetesCollectionNewPaquetesToAttach = em.getReference(paquetesCollectionNewPaquetesToAttach.getClass(), paquetesCollectionNewPaquetesToAttach.getIdPaquete());
                attachedPaquetesCollectionNew.add(paquetesCollectionNewPaquetesToAttach);
            }
            paquetesCollectionNew = attachedPaquetesCollectionNew;
            usuarios.setPaquetesCollection(paquetesCollectionNew);
            usuarios = em.merge(usuarios);
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getUsuariosCollection().remove(usuarios);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getUsuariosCollection().add(usuarios);
                idRolNew = em.merge(idRolNew);
            }
            for (Historialpaquetes historialpaquetesCollectionNewHistorialpaquetes : historialpaquetesCollectionNew) {
                if (!historialpaquetesCollectionOld.contains(historialpaquetesCollectionNewHistorialpaquetes)) {
                    Usuarios oldIdUsuarioOfHistorialpaquetesCollectionNewHistorialpaquetes = historialpaquetesCollectionNewHistorialpaquetes.getIdUsuario();
                    historialpaquetesCollectionNewHistorialpaquetes.setIdUsuario(usuarios);
                    historialpaquetesCollectionNewHistorialpaquetes = em.merge(historialpaquetesCollectionNewHistorialpaquetes);
                    if (oldIdUsuarioOfHistorialpaquetesCollectionNewHistorialpaquetes != null && !oldIdUsuarioOfHistorialpaquetesCollectionNewHistorialpaquetes.equals(usuarios)) {
                        oldIdUsuarioOfHistorialpaquetesCollectionNewHistorialpaquetes.getHistorialpaquetesCollection().remove(historialpaquetesCollectionNewHistorialpaquetes);
                        oldIdUsuarioOfHistorialpaquetesCollectionNewHistorialpaquetes = em.merge(oldIdUsuarioOfHistorialpaquetesCollectionNewHistorialpaquetes);
                    }
                }
            }
            for (Paquetes paquetesCollectionOldPaquetes : paquetesCollectionOld) {
                if (!paquetesCollectionNew.contains(paquetesCollectionOldPaquetes)) {
                    paquetesCollectionOldPaquetes.setIdRepartidor(null);
                    paquetesCollectionOldPaquetes = em.merge(paquetesCollectionOldPaquetes);
                }
            }
            for (Paquetes paquetesCollectionNewPaquetes : paquetesCollectionNew) {
                if (!paquetesCollectionOld.contains(paquetesCollectionNewPaquetes)) {
                    Usuarios oldIdRepartidorOfPaquetesCollectionNewPaquetes = paquetesCollectionNewPaquetes.getIdRepartidor();
                    paquetesCollectionNewPaquetes.setIdRepartidor(usuarios);
                    paquetesCollectionNewPaquetes = em.merge(paquetesCollectionNewPaquetes);
                    if (oldIdRepartidorOfPaquetesCollectionNewPaquetes != null && !oldIdRepartidorOfPaquetesCollectionNewPaquetes.equals(usuarios)) {
                        oldIdRepartidorOfPaquetesCollectionNewPaquetes.getPaquetesCollection().remove(paquetesCollectionNewPaquetes);
                        oldIdRepartidorOfPaquetesCollectionNewPaquetes = em.merge(oldIdRepartidorOfPaquetesCollectionNewPaquetes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getIdUsuario();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Historialpaquetes> historialpaquetesCollectionOrphanCheck = usuarios.getHistorialpaquetesCollection();
            for (Historialpaquetes historialpaquetesCollectionOrphanCheckHistorialpaquetes : historialpaquetesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Historialpaquetes " + historialpaquetesCollectionOrphanCheckHistorialpaquetes + " in its historialpaquetesCollection field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Rolessistema idRol = usuarios.getIdRol();
            if (idRol != null) {
                idRol.getUsuariosCollection().remove(usuarios);
                idRol = em.merge(idRol);
            }
            Collection<Paquetes> paquetesCollection = usuarios.getPaquetesCollection();
            for (Paquetes paquetesCollectionPaquetes : paquetesCollection) {
                paquetesCollectionPaquetes.setIdRepartidor(null);
                paquetesCollectionPaquetes = em.merge(paquetesCollectionPaquetes);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
