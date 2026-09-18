package fe.DE200093;

import fe.DE200093.pojo.Employee;
import fe.DE200093.pojo.Gender;
import fe.DE200093.pojo.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsf302FU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Employee nv1 = new Employee("Nguyen Van A", new BigDecimal("15000000"), LocalDate.of(2023, 1, 15), "nva@gmail.com", Gender.MALE, true);
            Employee nv2 = new Employee("Tran Thi B", new BigDecimal("18000000"), LocalDate.of(2022, 5, 20), "ttb@gmail.com", Gender.FEMALE, true);
            Employee nv3 = new Employee("Le Van C", new BigDecimal("12000000"), LocalDate.of(2024, 2, 10), "lvc@gmail.com", Gender.MALE, true);

            Project prjA = new Project("PRJ-001", "Project A", new BigDecimal("500000000"), LocalDate.of(2026, 1, 1), null);
            Project prjB = new Project("PRJ-002", "Project B", new BigDecimal("800000000"), LocalDate.of(2026, 3, 1), null);

            nv1.assignToProject(prjA);
            nv1.assignToProject(prjB);
            nv2.assignToProject(prjB);
            nv3.assignToProject(prjA);

            em.persist(prjA);
            em.persist(prjB);
            em.persist(nv1);
            em.persist(nv2);
            em.persist(nv3);

            em.getTransaction().commit();
            em.clear();

            System.out.println("--- DANH SÁCH DỰ ÁN CỦA TỪNG NHÂN VIÊN ---");
            em.createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList()
                    .forEach(e -> {
                        System.out.println("Nhân viên: " + e.getFullName());
                        e.getProjects().forEach(p -> System.out.println("  -> Dự án: " + p.getProjectName()));
                    });
        } finally {
            em.close();
            emf.close();
        }
    }
}