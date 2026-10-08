package com.hsf302.chapter6.config;

import com.hsf302.chapter6.entity.Major;
import com.hsf302.chapter6.entity.Student;
import com.hsf302.chapter6.repository.MajorRepository;
import com.hsf302.chapter6.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;

    public DataInitializer(StudentRepository studentRepository, MajorRepository majorRepository) {
        this.studentRepository = studentRepository;
        this.majorRepository = majorRepository;
    }

    @Override
    public void run(String... args) {
        if (majorRepository.count() == 0) {
            Major cntt = majorRepository.save(new Major("CNTT", "Công nghệ thông tin"));
            Major ktpm = majorRepository.save(new Major("KTPM", "Kỹ thuật phần mềm"));
            Major attt = majorRepository.save(new Major("ATTT", "An toàn thông tin"));
            Major httt = majorRepository.save(new Major("HTTT", "Hệ thống thông tin"));

            if (studentRepository.count() == 0) {
                Student s1 = new Student();
                s1.setName("Nguyễn Văn An");
                s1.setEmail("an@fpt.edu.vn");
                s1.setAge(20);
                s1.setGpa(3.5);
                s1.setMajor(cntt);

                Student s2 = new Student();
                s2.setName("Trần Thị Bình");
                s2.setEmail("binh@fpt.edu.vn");
                s2.setAge(21);
                s2.setGpa(3.2);
                s2.setMajor(ktpm);

                Student s3 = new Student();
                s3.setName("Lê Minh Cường");
                s3.setEmail("cuong@fpt.edu.vn");
                s3.setAge(19);
                s3.setGpa(3.8);
                s3.setMajor(attt);

                Student s4 = new Student();
                s4.setName("Phạm Thị Dung");
                s4.setEmail("dung@fpt.edu.vn");
                s4.setAge(22);
                s4.setGpa(2.9);
                s4.setMajor(httt);

                studentRepository.saveAll(List.of(s1, s2, s3, s4));
            }
        }
    }
}