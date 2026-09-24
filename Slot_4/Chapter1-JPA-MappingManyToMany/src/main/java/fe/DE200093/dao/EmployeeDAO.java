package fe.DE200093.dao;

import fe.DE200093.pojo.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import fe.DE200093.util.JPAUtil;
import fe.DE200093.pojo.Project;

import java.util.List;

public class EmployeeDAO {

    private final EntityManagerFactory emf;

    public EmployeeDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EmployeeDAO() {
        this.emf = JPAUtil.getEMF();
    }

    public void save(Employee employee) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(employee);
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

    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Employee update(Employee employee) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee updated = em.merge(employee);
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
            Employee employee = em.find(Employee.class, id);
            if (employee != null) {
                em.remove(employee);
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

    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employee employee = em.find(Employee.class, employeeId);
            fe.DE200093.pojo.Project project = em.find(fe.DE200093.pojo.Project.class, projectId);

            if (employee != null && project != null) {
                employee.assignToProject(project); // Gọi helper method của TODO 5.5
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

    public void getProjectStatistics() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            String jpql = """
                SELECT p.projectName, COUNT(e), SUM(e.salary)
                FROM Project p
                JOIN p.employees e
                WHERE e.active = true
                GROUP BY p.projectName
                """;

            List<Object[]> results = em.createQuery(jpql, Object[].class)
                    .getResultList();

            System.out.println("=== PROJECT STATISTICS ===");

            for (Object[] row : results) {
                String projectName = (String) row[0];
                Long employeeCount = (Long) row[1];
                BigDecimal totalSalary = (BigDecimal) row[2];

                System.out.println(
                        "Project: " + projectName
                                + " | Active employees: " + employeeCount
                                + " | Total salary: " + totalSalary
                );
            }

        } finally {
            em.close();
        }
    }
}