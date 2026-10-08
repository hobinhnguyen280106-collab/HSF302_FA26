package com.hsf302.chapter6.service;

import com.hsf302.chapter6.dto.StudentForm;
import com.hsf302.chapter6.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    Optional<Student> findById(Long id);

    Student create(Student student);

    /** @return true nếu tìm thấy và cập nhật; false nếu không tồn tại id */
    boolean update(Long id, Student data);

    /** @return true nếu xoá được; false nếu không tồn tại id */
    boolean delete(Long id);

    /** Kiểm tra email trùng. excludeId = null khi thêm mới, = id hiện tại khi cập nhật */
    boolean isEmailTaken(String email, Long excludeId);

    List<String> getMajors();

    List<Student> search(String keyword);

    Page<Student> findStudents(String keyword, int page, int size);

    Page<Student> findStudents(String keyword, int page, int size, String sortField, String sortDir);

    // MỚI: Lấy thông tin sinh viên dạng Form DTO để hiển thị lên form edit
    StudentForm findFormById(Long id);

    // MỚI: Nhận DTO để tạo mới
    void create(StudentForm form);

    // MỚI: Nhận DTO để cập nhật
    boolean update(Long id, StudentForm form);

}