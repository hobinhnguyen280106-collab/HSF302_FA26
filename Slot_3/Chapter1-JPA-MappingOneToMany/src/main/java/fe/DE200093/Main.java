package fe.DE200093;

import fe.DE200093.dao.DepartmentDAO;
import fe.DE200093.pojo.Department;
import fe.DE200093.pojo.Employee;
import fe.DE200093.pojo.Gender;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // 1. Tạo EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsf302FU");

        Department dept = new Department("Marketing", "Ha Noi");
        Employee emp = new Employee("test2@company.com", "Test", Gender.OTHER,
                new BigDecimal("1000"), LocalDate.now());
        Employee e2 = new Employee("bc@company.com", "B", Gender.FEMALE,
                new BigDecimal("1200"), LocalDate.of(2022, 2, 1));
        Employee e3 = new Employee("cd@company.com", "C", Gender.OTHER,
                new BigDecimal("1500"), LocalDate.of(2022, 3, 1));

        dept.addEmployee(emp);
        dept.addEmployee(e2);
        dept.addEmployee(e3);

        // 2. Truyền emf vào constructor
        DepartmentDAO deptDAO = new DepartmentDAO(emf);
        deptDAO.save(dept);
        System.out.println("Đã thêm: " + dept.getName());

        // 3. Đóng emf khi kết thúc
        emf.close();
    }
}