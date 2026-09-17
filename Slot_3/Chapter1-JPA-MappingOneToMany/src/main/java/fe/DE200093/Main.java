package fe.DE200093;

import fe.DE200093.pojo.Department;
import fe.DE200093.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        System.out.println("========== BẮT ĐẦU TODO 2.8: TÁI HIỆN N+1 QUERY ==========");

        // 1. Câu query thứ nhất (1): Lấy toàn bộ danh sách phòng ban
        System.out.println("--> GỌI SELECT TẤT CẢ DEPARTMENT:");
        List<Department> list = em.createQuery("SELECT d FROM Department d", Department.class)
                .getResultList();
        System.out.println("Số lượng phòng ban tìm thấy: " + list.size());

        // 2. N câu query phụ (+ N): Mỗi vòng lặp truy cập d.getEmployees().size()
        // Session đang mở nên Hibernate sẽ bắn thêm 1 câu SELECT employees cho mỗi phòng ban
        System.out.println("\n--> DUYỆT DANH SÁCH VÀ TRUY CẬP EMPLOYEES:");
        for (Department d : list) {
            System.out.println("Phòng ban: " + d.getName() + " | Số nhân viên: " + d.getEmployees().size());
        }

        System.out.println("========== KẾT THÚC TODO 2.8 ==========");

        em.close();
        JPAUtil.close();
    }
}