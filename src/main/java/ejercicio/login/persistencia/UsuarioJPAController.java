package ejercicio.login.persistencia;

import ejercicio.login.logica.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;

public class UsuarioJPAController {

    private EntityManagerFactory emf = null;

    public UsuarioJPAController() {
        this.emf = Persistence.createEntityManagerFactory("UsuarioPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // ── CREATE ──────────────────────────────────────────
    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    // ── READ (todos) ─────────────────────────────────────
    public List<Usuario> findEntities() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // ── READ (uno por ID) ────────────────────────────────
    public Usuario find(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    // ── UPDATE ───────────────────────────────────────────
    public void edit(Usuario usuario) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            if (em != null) em.close();
        }
    }

    // ── DELETE ───────────────────────────────────────────
    public void destroy(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario autoABorrar = em.merge(usuario); // sincronizar con el contexto
            em.remove(autoABorrar);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

}
