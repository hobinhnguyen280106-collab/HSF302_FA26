package com.hsf302.chapter6.service.impl;

import com.hsf302.chapter6.dto.StudentForm;
import com.hsf302.chapter6.entity.Major;
import com.hsf302.chapter6.entity.Student;
import com.hsf302.chapter6.repository.MajorRepository;
import com.hsf302.chapter6.repository.StudentRepository;
import com.hsf302.chapter6.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;

    public StudentServiceImpl(StudentRepository studentRepository, MajorRepository majorRepository) {
        this.studentRepository = studentRepository;
        this.majorRepository = majorRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Major> getAllMajors() {
        return majorRepository.findAll();
    }

    @Override
    public List<String> getMajors() {
        return majorRepository.findAll().stream().map(Major::getCode).toList();
    }

    @Override
    public List<Student> search(String keyword) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        if (keyword != null && !keyword.isBlank()) {
            String trimmed = keyword.trim();
            return studentRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    trimmed, trimmed, Pageable.unpaged()
            ).getContent();
        }
        return studentRepository.findAll(sort);
    }

    @Override
    public Page<Student> findStudents(String keyword, int page, int size) {
        return findStudents(keyword, page, size, "id", "asc");
    }

    @Override
    public Page<Student> findStudents(String keyword, int page, int size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (keyword != null && !keyword.isBlank()) {
            String trimmed = keyword.trim();
            return studentRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    trimmed, trimmed, pageable
            );
        }
        return studentRepository.findAll(pageable);
    }

    @Override
    public StudentForm findFormById(Long id) {
        return studentRepository.findById(id).map(s -> {
            StudentForm form = new StudentForm();
            form.setId(s.getId());
            form.setName(s.getName());
            form.setEmail(s.getEmail());
            form.setAge(s.getAge());
            form.setMajorId(s.getMajor() != null ? s.getMajor().getId() : null);
            form.setGpa(s.getGpa());
            return form;
        }).orElse(null);
    }

    @Override
    @Transactional
    public Student create(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public void create(StudentForm form) {
        Major major = majorRepository.findById(form.getMajorId())
                .orElseThrow(() -> new IllegalArgumentException("Chuyên ngành không tồn tại ID: " + form.getMajorId()));

        Student s = new Student();
        s.setName(form.getName());
        s.setEmail(form.getEmail());
        s.setAge(form.getAge());
        s.setMajor(major);
        s.setGpa(form.getGpa());
        studentRepository.save(s);
    }

    @Override
    @Transactional
    public boolean update(Long id, Student data) {
        return studentRepository.findById(id)
                .map(existing -> {
                    existing.setName(data.getName());
                    existing.setEmail(data.getEmail());
                    existing.setAge(data.getAge());
                    existing.setMajor(data.getMajor());
                    existing.setGpa(data.getGpa());
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public boolean update(Long id, StudentForm form) {
        return studentRepository.findById(id).map(s -> {
            Major major = majorRepository.findById(form.getMajorId())
                    .orElseThrow(() -> new IllegalArgumentException("Chuyên ngành không tồn tại ID: " + form.getMajorId()));

            s.setName(form.getName());
            s.setEmail(form.getEmail());
            s.setAge(form.getAge());
            s.setMajor(major);
            s.setGpa(form.getGpa());
            return true;
        }).orElse(false);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean isEmailTaken(String email, Long excludeId) {
        if (email == null || email.isBlank()) return false;
        return excludeId == null
                ? studentRepository.existsByEmailIgnoreCase(email.trim())
                : studentRepository.existsByEmailIgnoreCaseAndIdNot(email.trim(), excludeId);
    }
}