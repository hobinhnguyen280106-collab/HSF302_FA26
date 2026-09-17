package fe.DE200093;

import fe.DE200093.dao.DepartmentDAO;
import fe.DE200093.dao.EmployeeDAO;
import fe.DE200093.pojo.Department;
import fe.DE200093.pojo.Employee;
import fe.DE200093.pojo.Gender;
import fe.DE200093.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO deptDAO = new DepartmentDAO();
        EmployeeDAO empDAO = new EmployeeDAO();

        // 1. Tạo 1 Department + 3 Employee qua addEmployee()
        Department dept = new Department("Marketing", "Ha Noi");
        Employee e1 = new Employee("emp1@company.com", "Nguyen Van A", Gender.MALE,
                new BigDecimal("1000"), LocalDate.now());
        Employee e2 = new Employee("emp2@company.com", "Tran Thi B", Gender.FEMALE,
                new BigDecimal("1200"), LocalDate.of(2023, 2, 1));
        Employee e3 = new Employee("emp3@company.com", "Le Van C", Gender.OTHER,
                new BigDecimal("1500"), LocalDate.of(2023, 3, 1));

        dept.addEmployee(e1);
        dept.addEmployee(e2);
        dept.addEmployee(e3);

        // 2. Chỉ persist Department (CascadeType.ALL tự lưu cả 3 Employee)
        deptDAO.save(dept);
        System.out.println("Đã lưu thành công Department ID: " + dept.getId());

        // 3. Test ném lỗi khi trùng email (Unique Constraint)
        System.out.println("\n--- BẮT ĐẦU TEST TRÙNG EMAIL ---");
        try {
            Employee duplicateEmailEmp = new Employee("emp1@company.com", "Nguyen Fake",
                    Gender.MALE, new BigDecimal("2000"), LocalDate.now());
            dept.addEmployee(duplicateEmailEmp);
            deptDAO.update(dept); // hoặc empDAO.save(duplicateEmailEmp);
            System.out.println("TEST THẤT BẠI: Không có ngoại lệ nào xảy ra!");
        } catch (Exception ex) {
            System.out.println("TEST THÀNH CÔNG: Đã ném exception vi phạm Unique Key: " + ex.getMessage());
        }

        JPAUtil.close();
    }
}