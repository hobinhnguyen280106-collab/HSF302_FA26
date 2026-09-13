package fe.DE200093.dao;

import fe.DE200093.pojo.Employee;
import jakarta.persistence.*;

public class EmployeeDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsf302FU");

    // TODO 0.3: CREATE
    public void save(Employee e) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public java.util.List<Employee> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Employee findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public java.util.List<Employee> findBySalaryGreaterThan(java.math.BigDecimal minSalary) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.salary > :minSalary", Employee.class);
            query.setParameter("minSalary", minSalary);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
