package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Sẽ bổ sung method dần từ TODO 7

    Optional<Course> findByCode(String code); // Dùng cho TODO 7, 8 và Part E
}