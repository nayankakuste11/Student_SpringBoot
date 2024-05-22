package com.qsp.student_springboot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qsp.student_springboot.dto.Student;
import com.qsp.student_springboot.repo.StudentRepo;

@Repository
public class StudentDao {

	@Autowired
	StudentRepo repo;

	// save
	public Student saveStudent(Student student) {
		return repo.save(student);
	}

	public List<Student> saveAllStudent(List<Student> list) {
		return repo.saveAll(list);

	}

//find
	public List<Student> findAll() {
		return repo.findAll();
	}

	public Student loginStudent(String userName) {
		try {
			long phone = Long.parseLong(userName);
			return findByPhone(phone);
		} catch (Exception e) {
			String email = userName;
			return findByEmail(email);
		}
	}

	public List<Student> findByGrade(String grade) {
		return repo.findByGrade(grade);
	}

	public List<Student> findByFatherName(String fatherName) {
		return repo.findByFatherName(fatherName);
	}

	public List<Student> findByMotherName(String motherName) {
		return repo.findByMotherName(motherName);
	}

	public Optional<Student> findById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	public Student findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public List<Student> findByName(String name) {

		return repo.findByName(name);
	}

	public Student findByPhone(long phone) {

		return repo.findByPhone(phone);
	}

	public List<Student> findByAddress(String address) {

		return repo.findByAddress(address);
	}

//	//delete
	// delete by ID
	public Student deleteStudentById(int id) {
		Optional<Student> studentOptional = repo.findById(id);
		if (studentOptional.isPresent()) {
			repo.deleteById(id);
			return studentOptional.get();
		} else {
			return null;
		}
	}

	// delete by Email
	public Student deleteStudentByEmail(String email) {
		Student student = repo.findByEmail(email);
		if (student != null) {
			repo.delete(student);
		}
		return student;
	}

	// delete by Phone
	public Student deleteStudentByPhone(long phone) {
		Student student = repo.findByPhone(phone);
		if (student != null) {
			repo.delete(student);
		}
		return student;
	}

	// delete all students
	public List<Student> deleteAllStudent() {
		List<Student> students = repo.findAll();
		repo.deleteAll();
		return students;
	}

	// delete by Name
	public Student deleteStudentByName(String name) {
		List<Student> students = repo.findByName(name);
		if (students != null && !students.isEmpty()) {
			repo.deleteAll(students);
			return students.get(0); // return the first student as a sample
		}
		return null;
	}

	// delete by Address
	public List<Student> deleteStudentByAddress(String address) {
		List<Student> students = repo.findByAddress(address);
		if (students != null && !students.isEmpty()) {
			repo.deleteAll(students);
		}
		return students;
	}

	// delete by Grade
	public List<Student> deleteStudentByGrade(String grade) {
		List<Student> students = repo.findByGrade(grade);
		if (students != null && !students.isEmpty()) {
			repo.deleteAll(students);
		}
		return students;
	}

	public List<Student> findByPercentageLessThan(double percentage) {
		return repo.findByPercentageLessThan(percentage);
	}

	public List<Student> findByPercentageGreaterThan(double percentage) {
		return repo.findByPercentageGreaterThan(percentage);
	}

	public List<Student> findByPercentageBetween(double percentage1, double percentage2) {
		return repo.findByPercentageBetween(percentage1, percentage2);
	}

	public Student updateStudentNameById(int id, String name) {
		Optional<Student> student = repo.findById(id);

		if (student.isPresent()) {
			Student student2 = student.get();
			student2.setName(name);

			return repo.save(student2);
		} else {
			return null;
		}
	}

	public Student updateMotherNameById(int id, String name) {
		Optional<Student> student = repo.findById(id);

		if (student.isPresent()) {
			Student student2 = student.get();
			student2.setMotherName(name);

			return repo.save(student2);
		} else {
			return null;
		}

	}

	public Student updateFatherNameById(int id, String name) {
		Optional<Student> student = repo.findById(id);

		if (student.isPresent()) {
			Student student2 = student.get();
			student2.setFatherName(name);

			return repo.save(student2);
		} else {
			return null;
		}
	}

	public Student updateAddressById(int id, String address) {
		Optional<Student> student = repo.findById(id);

		if (student.isPresent()) {
			Student student2 = student.get();
			student2.setAddress(address);
			return repo.save(student2);
		} else {
			return null;
		}
	}

	public Student updateEmailById(int id, String email) {
		Optional<Student> student = repo.findById(id);

		if (student.isPresent()) {
			Student student2 = student.get();
			student2.setEmail(email);
			return repo.save(student2);
		} else {
			return null;
		}
	}

	public Student updateStudentPasswordById(int id, String password) {
		Optional<Student> optional = repo.findById(id);
		if (optional.isPresent()) {
			Student Student = optional.get();
			Student.setPassword(password);
			return repo.save(Student);
		} else {
			return null;
		}
	}

	public Student updateStudentPasswordByPhone(long phone, String password) {
		Student Student = repo.findByPhone(phone);
		if (Student != null) {
			Student.setPassword(password);
			return repo.save(Student);
		} else {
			return null;
		}
	}

	public Student updateStudentPasswordByEmail(String email, String password) {
		Student Student = repo.findByEmail(email);
		if (Student != null) {
			Student.setPassword(password);
			return repo.save(Student);
		} else {
			return null;
		}
	}

	public Student updateStudent(Student Student) {
		return repo.save(Student);
	}
}
