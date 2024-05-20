package com.qsp.student_springboot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.student_springboot.dto.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {

	List<Student> findByGrade(String grade);

	List<Student> findByFatherName(String fatherName);

	List<Student> findByMotherName(String motherName);

	Student findByEmail(String email);

	List<Student> findByName(String name);

	Student findByPhone(long phone);

	List<Student> findByAddress(String address);

	List<Student> findByPercentageLessThan(double percentage);

	List<Student> findByPercentageGreaterThan(double percentage);

	List<Student> findByPercentageBetween(double percentage1, double percentage2);

	
}
