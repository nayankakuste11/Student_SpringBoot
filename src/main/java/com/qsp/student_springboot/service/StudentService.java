package com.qsp.student_springboot.service;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.hibernate.cache.spi.entry.StructuredCacheEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.qsp.student_springboot.dao.StudentDao;
import com.qsp.student_springboot.dto.Student;
import com.qsp.student_springboot.exception.EmailNotFoudException;
import com.qsp.student_springboot.exception.IdNotFoundException;
import com.qsp.student_springboot.exception.NoDataAvaliableException;
import com.qsp.student_springboot.exception.PhoneNotFoundException;
import com.qsp.student_springboot.response.ResponseStructure;

@Service
public class StudentService {

	@Autowired
	StudentDao dao;

	// calculate percentage and check for valid marks
	private boolean validateMarks(Student student) {
		int securedMarks = student.getSecuredMarks();
		int totalMarks = student.getTotalMarks();
		return securedMarks >= 0 && securedMarks <= totalMarks;
	}

	private void calculatePercentageAndGrade(Student student) {
		int securedMarks = student.getSecuredMarks();
		int totalMarks = student.getTotalMarks();

		double percentage = ((double) securedMarks / totalMarks) * 100;
		student.setPercentage(percentage);

		if (percentage >= 85) {
			student.setGrade("Distinction");
		} else if (percentage >= 70) {
			student.setGrade("First Class");
		} else if (percentage >= 49) {
			student.setGrade("Second Class");
		} else if (percentage >= 35) {
			student.setGrade("Pass");
		} else {
			student.setGrade("Fail");
		}
	}

	// save
	public ResponseEntity<ResponseStructure<Student>> saveStudent(Student student) {
		ResponseStructure<Student> structure = new ResponseStructure<>();

		if (validateMarks(student)) {
			calculatePercentageAndGrade(student);
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setMessage("Saved Successfully");
			structure.setData(dao.saveStudent(student));
			return new ResponseEntity<>(structure, HttpStatus.CREATED);
		} else {
			structure.setStatus(HttpStatus.BAD_REQUEST.value());
			structure.setMessage("Invalid marks");
			structure.setData(null);
			return new ResponseEntity<ResponseStructure<Student>>(structure, HttpStatus.BAD_REQUEST);
		}
	}

	// save a list of students
	public ResponseEntity<ResponseStructure<List<Student>>> saveAllStudent(List<Student> list) {
		for (Student student : list) {
			if (validateMarks(student)) {
				calculatePercentageAndGrade(student);
			} else {
				System.out.println("Invalid marks");
			}
		}

		ResponseStructure<List<Student>> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("Saved Successfully");
		structure.setData(dao.saveAllStudent(list));
		return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.CREATED);
	}

//	 find
	public ResponseEntity<ResponseStructure<List<Student>>> findAll() {

		List<Student> list = dao.findAll();
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			throw new NoDataAvaliableException("no data avaliable");
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.FOUND);

		}

	}

	public ResponseEntity<ResponseStructure<String>> login(String userName, String password) {
		Student student = dao.loginStudent(userName);
		ResponseStructure<String> structure = new ResponseStructure<>();
		if (student != null) {
			if (student.getPassword().equals(password)) {
				structure.setStatus(HttpStatus.OK.value());
				structure.setMessage("Login Success");
				structure.setData("Login Success");
				return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
			} else {
				structure.setStatus(HttpStatus.UNAUTHORIZED.value());
				structure.setMessage("Invalid Password");
				structure.setData("Invalid Password");
				return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.UNAUTHORIZED);
			}
		} else {

			throw new EmailNotFoudException("Email " + userName + " not found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Student>>> findByGrade(String grade) {
		List<Student> list = dao.findByGrade(grade);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {

			throw new NoDataAvaliableException("no data avaliable for grade" + grade);
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.FOUND);

		}
	}

	public ResponseEntity<ResponseStructure<List<Student>>> findByFatherName(String fatherName) {
		List<Student> list = dao.findByFatherName(fatherName);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {

			throw new NoDataAvaliableException("no data available");
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.FOUND);

		}

	}

	public ResponseEntity<ResponseStructure<List<Student>>> findByMotherName(String motherName) {
		List<Student> list = dao.findByMotherName(motherName);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();
		if (list.isEmpty()) {

			throw new NoDataAvaliableException("no data available");
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<Student>> findById(int id) {
		Optional<Student> optional = dao.findById(id);
		ResponseStructure<Student> structure = new ResponseStructure<>();

		if (optional.isPresent()) {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(optional.get());
			return new ResponseEntity<ResponseStructure<Student>>(structure, HttpStatus.FOUND);
		} else {
			throw new IdNotFoundException("Employee with id  " + id + " not found ");
		}
	}

	public ResponseEntity<ResponseStructure<Student>> findByEmail(String email) {

		Student student = dao.findByEmail(email);
		ResponseStructure<Student> structure = new ResponseStructure<>();

		if (student != null) {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(student);
			return new ResponseEntity<ResponseStructure<Student>>(structure, HttpStatus.FOUND);
		} else {
			throw new EmailNotFoudException("Email " + email + " not found");
		}

	}

	public ResponseEntity<ResponseStructure<List<Student>>> findByName(String name) {
		List<Student> list = dao.findByName(name);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
//			structure.setStatus(HttpStatus.NOT_FOUND.value());
//			structure.setMessage("Not Data found");
//			structure.setData(list);
//
//			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.FOUND);

			throw new NoDataAvaliableException("no data avaliable for " + name);

		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.NOT_FOUND);

		}

	}

	public ResponseEntity<ResponseStructure<Student>> findByPhone(long phone) {
		Student student = dao.findByPhone(phone);
		ResponseStructure<Student> structure = new ResponseStructure<>();

		if (student != null) {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(student);
			return new ResponseEntity<ResponseStructure<Student>>(structure, HttpStatus.FOUND);
		} else {
			throw new PhoneNotFoundException("Phone number " + phone + "not found");
		}

	}

	public ResponseEntity<ResponseStructure<List<Student>>> findByAddress(String address) {
		List<Student> list = dao.findByAddress(address);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {

			throw new NoDataAvaliableException("no data avaliable " + address);
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.NOT_FOUND);

		}
	}

	public ResponseEntity<ResponseStructure<List<Student>>> findByPercentageLessThan(double percentage) {
		List<Student> list = dao.findByPercentageLessThan(percentage);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			throw new NoDataAvaliableException("no data available");
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.NOT_FOUND);

		}
	}

	public ResponseEntity<ResponseStructure<List<Student>>> findByPercentageGreaterThan(double percentage) {
		List<Student> list = dao.findByPercentageGreaterThan(percentage);

		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			throw new NoDataAvaliableException("no data available");
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.NOT_FOUND);
		}

	}

	public ResponseEntity<ResponseStructure<List<Student>>> findByPercentageBetween(double percentage1,
			double percentage2) {
		List<Student> list = dao.findByPercentageBetween(percentage1, percentage2);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Not Data found");
			structure.setData(list);

			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.FOUND);
		} else {
			throw new NoDataAvaliableException("no data available");
		}

	}

	// delete

	// delete by ID
	public ResponseEntity<ResponseStructure<String>> deleteStudentById(int id) {
		Student student = dao.deleteStudentById(id);

		ResponseStructure<String> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Deleted student with ID: " + id);
			structure.setData("Deleted student with ID: " + id);
			return new ResponseEntity<>(structure, HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student not found with ID: " + id);
			structure.setData("Student not found with ID: " + id);
			return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
		}
	}

	// delete by Email
	public ResponseEntity<ResponseStructure<String>> deleteStudentByEmail(String email) {
		Student student = dao.deleteStudentByEmail(email);

		ResponseStructure<String> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Deleted student with email: " + email);
			structure.setData("Deleted student with email: " + email);
			return new ResponseEntity<>(structure, HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student not found with email: " + email);
			structure.setData("Student not found with email: " + email);
			return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
		}
	}

	// delete by Phone
	public ResponseEntity<ResponseStructure<String>> deleteStudentByPhone(long phone) {
		Student student = dao.deleteStudentByPhone(phone);

		ResponseStructure<String> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Deleted student with phone: " + phone);
			structure.setData("Deleted student with phone: " + phone);
			return new ResponseEntity<>(structure, HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student not found with phone: " + phone);
			structure.setData("Student not found with phone: " + phone);
			return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
		}
	}

	// delete all students
	public ResponseEntity<ResponseStructure<String>> deleteAllStudent() {
		List<Student> list = dao.deleteAllStudent();

		ResponseStructure<String> structure = new ResponseStructure<>();
		if (list != null && !list.isEmpty()) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Deleted all students");
			structure.setData("Deleted all students");
			return new ResponseEntity<>(structure, HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("No students to delete");
			structure.setData("No students to delete");
			return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
		}
	}

	// delete by Name
	public ResponseEntity<ResponseStructure<String>> deleteStudentByName(String name) {
		Student student = dao.deleteStudentByName(name);

		ResponseStructure<String> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Deleted student with name: " + name);
			structure.setData("Deleted student with name: " + name);
			return new ResponseEntity<>(structure, HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student not found with name: " + name);
			structure.setData("Student not found with name: " + name);
			return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
		}
	}

	// delete by Address
	public ResponseEntity<ResponseStructure<String>> deleteStudentByAddress(String address) {
		List<Student> students = dao.deleteStudentByAddress(address);

		ResponseStructure<String> structure = new ResponseStructure<>();
		if (students != null && !students.isEmpty()) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Deleted students with address: " + address);
			structure.setData("Deleted students with address: " + address);
			return new ResponseEntity<>(structure, HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("No students found with address: " + address);
			structure.setData("No students found with address: " + address);
			return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
		}
	}

	// delete by Grade
	public ResponseEntity<ResponseStructure<String>> deleteStudentByGrade(String grade) {
		List<Student> students = dao.deleteStudentByGrade(grade);

		ResponseStructure<String> structure = new ResponseStructure<>();
		if (students != null && !students.isEmpty()) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Deleted students with grade: " + grade);
			structure.setData("Deleted students with grade: " + grade);
			return new ResponseEntity<>(structure, HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("No students found with grade: " + grade);
			structure.setData("No students found with grade: " + grade);
			return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
		}
	} // update

	public ResponseEntity<ResponseStructure<Student>> updateStudentNameById(int id, String name) {
		Student student = dao.updateStudentNameById(id, name);
		ResponseStructure<Student> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Student Name Updated");
			structure.setData(student);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student Not Found");
			structure.setData(null);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.NOT_FOUND);
		}

	}

	public ResponseEntity<ResponseStructure<Student>> updateMotherNameById(int id, String name) {
		Student student = dao.updateMotherNameById(id, name);
		ResponseStructure<Student> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Mother Name Updated");
			structure.setData(student);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student Not Found");
			structure.setData(null);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<Student>> updateFatherNameById(int id, String name) {
		Student student = dao.updateFatherNameById(id, name);
		ResponseStructure<Student> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Father Name Updated");
			structure.setData(student);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student Not Found");
			structure.setData(null);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<Student>> updateAddressById(int id, String address) {
		Student student = dao.updateAddressById(id, address);
		ResponseStructure<Student> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Address Updated");
			structure.setData(student);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student Not Found");
			structure.setData(null);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<Student>> updateEmailById(int id, String email) {
		Student student = dao.updateEmailById(id, email);
		ResponseStructure<Student> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Email Updated");
			structure.setData(student);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student Not Found");
			structure.setData(null);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.NOT_FOUND);
		}

	}

	public ResponseEntity<ResponseStructure<Student>> updateStudentPasswordByEmail(String email, String password) {
		Student student = dao.updateStudentPasswordByEmail(email, password);
		ResponseStructure<Student> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Password Updated");
			structure.setData(student);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student Not Found");
			structure.setData(null);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<Student>> updateStudentPasswordByPhone(long phone, String password) {
		Student student = dao.updateStudentPasswordByPhone(phone, password);
		ResponseStructure<Student> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Password Updated");
			structure.setData(student);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.OK);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student Not Found");
			structure.setData(null);
			return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<Student>> updateStudent(Student updatedStudent) {
		Optional<Student> studentOptional = dao.findById(updatedStudent.getId());
		ResponseStructure<Student> structure = new ResponseStructure<>();
		if (studentOptional.isPresent()) {
			Student existingStudent = studentOptional.get();
			existingStudent.setName(updatedStudent.getName());
			existingStudent.setFatherName(updatedStudent.getFatherName());
			existingStudent.setMotherName(updatedStudent.getMotherName());
			existingStudent.setAddress(updatedStudent.getAddress());
			existingStudent.setPhone(updatedStudent.getPhone());
			existingStudent.setSecuredMarks(updatedStudent.getSecuredMarks());
			existingStudent.setTotalMarks(updatedStudent.getTotalMarks());

			if (validateMarks(existingStudent)) {
				calculatePercentageAndGrade(existingStudent);
				dao.saveStudent(existingStudent);

				if (existingStudent != null) {
					structure.setStatus(HttpStatus.OK.value());
					structure.setMessage("Student data updated");
					structure.setData(existingStudent);

				}
				return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.OK);
			} else {
				structure.setStatus(HttpStatus.OK.value());
				structure.setMessage("invalid marks");
				structure.setData(null);
				return new ResponseEntity<ResponseStructure<Student>>(HttpStatus.NOT_FOUND);
			}
		} else {
			throw new NoDataAvaliableException("no data avaliable");
		}
	}

}
