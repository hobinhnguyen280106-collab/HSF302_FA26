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
}
