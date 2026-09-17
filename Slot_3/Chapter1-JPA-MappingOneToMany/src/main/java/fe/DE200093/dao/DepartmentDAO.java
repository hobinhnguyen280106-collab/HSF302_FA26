package fe.DE200093.dao;

import fe.DE200093.pojo.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import fe.DE200093.util.JPAUtil;

import java.util.List;

public class DepartmentDAO {

    private final EntityManagerFactory emf;

    // Nhận EntityManagerFactory khi khởi tạo DAO
    public DepartmentDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public DepartmentDAO() {
        this.emf = JPAUtil.getEMF();
    }
    public void save(Department department) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(department);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Department findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Department.class, id);
        } finally {
            em.close();
        }
    }

    public List<Department> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Department update(Department department) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Department updated = em.merge(department);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Department department = em.find(Department.class, id);
            if (department != null) {
                em.remove(department);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Department findByIdWithEmployees(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d JOIN FETCH d.employees WHERE d.id = :id", Department.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Department> findAllWithEmployees() {
        EntityManager em = emf.createEntityManager();
        try {
            // Dùng DISTINCT JOIN FETCH để lấy cả Department và nạp sẵn tập hợp Employees
            return em.createQuery("SELECT DISTINCT d FROM Department d JOIN FETCH d.employees", Department.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}