package BaseDatos;

import BaseDatos.exceptions.IllegalOrphanException;
import BaseDatos.exceptions.NonexistentEntityException;
import Clases.Rolessistema;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Usuarios;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RolessistemaJpaController implements Serializable {

    public RolessistemaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public RolessistemaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaGestPaquetesPU");
    }
       
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rolessistema rolessistema) {
        if (rolessistema.getUsuariosCollection() == null) {
            rolessistema.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : rolessistema.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getIdUsuario());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            rolessistema.setUsuariosCollection(attachedUsuariosCollection);
            em.persist(rolessistema);
            for (Usuarios usuariosCollectionUsuarios : rolessistema.getUsuariosCollection()) {
                Rolessistema oldIdRolOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getIdRol();
                usuariosCollectionUsuarios.setIdRol(rolessistema);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldIdRolOfUsuariosCollectionUsuarios != null) {
                    oldIdRolOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldIdRolOfUsuariosCollectionUsuarios = em.merge(oldIdRolOfUsuariosCollectionUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rolessistema rolessistema) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rolessistema persistentRolessistema = em.find(Rolessistema.class, rolessistema.getIdRol());
            Collection<Usuarios> usuariosCollectionOld = persistentRolessistema.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = rolessistema.getUsuariosCollection();
            List<String> illegalOrphanMessages = null;
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosCollectionOldUsuarios + " since its idRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getIdUsuario());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            rolessistema.setUsuariosCollection(usuariosCollectionNew);
            rolessistema = em.merge(rolessistema);
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    Rolessistema oldIdRolOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getIdRol();
                    usuariosCollectionNewUsuarios.setIdRol(rolessistema);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldIdRolOfUsuariosCollectionNewUsuarios != null && !oldIdRolOfUsuariosCollectionNewUsuarios.equals(rolessistema)) {
                        oldIdRolOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldIdRolOfUsuariosCollectionNewUsuarios = em.merge(oldIdRolOfUsuariosCollectionNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rolessistema.getIdRol();
                if (findRolessistema(id) == null) {
                    throw new NonexistentEntityException("The rolessistema with id " + id + " no longer exists.");
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
            Rolessistema rolessistema;
            try {
                rolessistema = em.getReference(Rolessistema.class, id);
                rolessistema.getIdRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolessistema with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Usuarios> usuariosCollectionOrphanCheck = rolessistema.getUsuariosCollection();
            for (Usuarios usuariosCollectionOrphanCheckUsuarios : usuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rolessistema (" + rolessistema + ") cannot be destroyed since the Usuarios " + usuariosCollectionOrphanCheckUsuarios + " in its usuariosCollection field has a non-nullable idRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rolessistema);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rolessistema> findRolessistemaEntities() {
        return findRolessistemaEntities(true, -1, -1);
    }

    public List<Rolessistema> findRolessistemaEntities(int maxResults, int firstResult) {
        return findRolessistemaEntities(false, maxResults, firstResult);
    }

    private List<Rolessistema> findRolessistemaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rolessistema.class));
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

    public Rolessistema findRolessistema(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rolessistema.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolessistemaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rolessistema> rt = cq.from(Rolessistema.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
