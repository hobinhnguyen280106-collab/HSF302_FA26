package fe.DE200093;

import fe.DE200093.dao.EmployeeDAO;
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


        EmployeeDAO employeeDAO = new EmployeeDAO();

//         TODO 5.7 - assign employees to projects
//        employeeDAO.assignEmployeeToProject(4L, 1L); // NV1 -> Project A
//        employeeDAO.assignEmployeeToProject(4L, 2L); // NV1 -> Project B
//        employeeDAO.assignEmployeeToProject(5L, 2L); // NV2 -> Project B
//        employeeDAO.assignEmployeeToProject(6L, 1L); // NV3 -> Project A
//
//        // TODO 5.8 - project statistics
//        employeeDAO.getProjectStatistics();

        employeeDAO.unassignEmployeeFromProject(4L, 1L);
    }
}