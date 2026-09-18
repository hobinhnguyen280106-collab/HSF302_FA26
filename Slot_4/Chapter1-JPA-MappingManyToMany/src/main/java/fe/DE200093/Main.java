package fe.DE200093;

import fe.DE200093.dao.DepartmentDAO;
import fe.DE200093.pojo.Department;
import fe.DE200093.util.JPAUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO deptDAO = new DepartmentDAO();

        System.out.println("========== TODO 2.9: FIX N+1 BẰNG JOIN FETCH ==========");

        /*
         * SO SÁNH TRƯỚC VÀ SAU KHI FIX:
         *
         * TRƯỚC (TODO 2.8 - Không có JOIN FETCH):
         * - Sinh ra 1 + N câu SQL:
         *     + 1 câu SELECT d FROM Department d
         *     + N câu SELECT e FROM Employee e WHERE e.department_id = ? (mỗi vòng lặp một câu)
         * - Tổng cộng: 1 + N câu truy vấn.
         *
         * SAU (TODO 2.9 - Dùng JOIN FETCH):
         * - Sinh ra DUY NHẤT 1 câu SQL kết hợp cả 2 bảng bằng INNER JOIN:
         *     select distinct ... from departments d1_0
         *     join employees e1_0 on d1_0.id = e1_0.department_id
         * - Khi duyệt vòng lặp và gọi getEmployees().size(), không phát sinh thêm bất kỳ câu SQL nào.
         * - Tổng cộng: Đúng 1 câu truy vấn.
         */

        System.out.println("--> GỌI findAllWithEmployees():");
        List<Department> list = deptDAO.findAllWithEmployees();

        System.out.println("\n--> DUYỆT DANH SÁCH (KHÔNG PHÁT SINH THÊM SQL NÀO):");
        for (Department d : list) {
            System.out.println("Phòng ban: " + d.getName() + " | Số nhân viên: " + d.getEmployees().size());
        }

        System.out.println("========== KẾT THÚC TODO 2.9 ==========");
        JPAUtil.close();
    }
}