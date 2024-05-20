package com.qsp.student_springboot.service;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.hibernate.cache.spi.entry.StructuredCacheEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.qsp.student_springboot.dao.StudentDao;
import com.qsp.student_springboot.dto.Student;
import com.qsp.student_springboot.response.ResponseStructure;

@Service
public class StudentService {

	@Autowired
	StudentDao dao;
	// response methods

	private ResponseStructure<String> createDeleteResponse(String message) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage(message);
		structure.setData(message);
		return structure;
	}

	private ResponseStructure<Student> createUpdateResponse(Student student, String message) {
		ResponseStructure<Student> structure = new ResponseStructure<>();
		if (student != null) {
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage(message);
			structure.setData(student);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student Not Found");
			structure.setData(null);
		}
		return structure;
	}

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
	public ResponseStructure<Student> saveStudent(Student student) {
		if (validateMarks(student)) {
			calculatePercentageAndGrade(student);
		} else {
			System.out.println("Invalid marks");
		}

		ResponseStructure<Student> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("Saved Successfully");
		structure.setData(dao.saveStudent(student));
		return structure;
	}

	// save a list of students
	public ResponseStructure<List<Student>> saveAllStudent(List<Student> list) {
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
		return structure;
	}

	// find
	public ResponseStructure<List<Student>> findAll() {

		List<Student> list = dao.findAll();
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Not Data found");
			structure.setData(list);

			return structure;
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return structure;

		}

	}

	public ResponseStructure<String> login(String userName, String password) {
		Student student = dao.loginStudent(userName);
		ResponseStructure<String> structure = new ResponseStructure<>();
		if (student != null) {
			if (student.getPassword().equals(password)) {
				structure.setStatus(HttpStatus.OK.value());
				structure.setMessage("Login Success");
				structure.setData("Login Success");
			} else {
				structure.setStatus(HttpStatus.UNAUTHORIZED.value());
				structure.setMessage("Invalid Password");
				structure.setData("Invalid Password");
			}
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Student Not Found");
			structure.setData("Student Not Found with the username: " + userName);
		}
		return structure;
	}

	public ResponseStructure<List<Student>> findByGrade(String grade) {
		List<Student> list = dao.findByGrade(grade);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Not Data found");
			structure.setData(list);

			return structure;
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return structure;

		}
	}

	public ResponseStructure<List<Student>> findByFatherName(String fatherName) {
		List<Student> list = dao.findByFatherName(fatherName);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Not Data found");
			structure.setData(list);

			return structure;
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return structure;

		}

	}

	public ResponseStructure<List<Student>> findByMotherName(String motherName) {
		List<Student> list = dao.findByMotherName(motherName);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Not Data found");
			structure.setData(list);

			return structure;
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return structure;

		}
	}

	public ResponseStructure<Student> findById(int id) {
		Optional<Student> optional = dao.findById(id);
		ResponseStructure<Student> structure = new ResponseStructure<>();

		if (optional.isPresent()) {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(optional.get());
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("No Data found");
			structure.setData(null);
		}

		return structure;
	}

	public ResponseStructure<Student> findByEmail(String email) {

		Student student = dao.findByEmail(email);
		ResponseStructure<Student> structure = new ResponseStructure<>();

		if (student != null) {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(student);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("No Data found");
			structure.setData(null);
		}

		return structure;
	}

	public ResponseStructure<List<Student>> findByName(String name) {
		List<Student> list = dao.findByName(name);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Not Data found");
			structure.setData(list);

			return structure;
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return structure;

		}

	}

	public ResponseStructure<Student> findByPhone(long phone) {
		Student student = dao.findByPhone(phone);
		ResponseStructure<Student> structure = new ResponseStructure<>();

		if (student != null) {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(student);
		} else {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("No Data found");
			structure.setData(null);
		}

		return structure;
	}

	public ResponseStructure<List<Student>> findByAddress(String address) {
		List<Student> list = dao.findByAddress(address);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Not Data found");
			structure.setData(list);

			return structure;
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return structure;

		}
	}

	public ResponseStructure<List<Student>> findByPercentageLessThan(double percentage) {
		List<Student> list = dao.findByPercentageLessThan(percentage);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Not Data found");
			structure.setData(list);

			return structure;
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return structure;

		}
	}

	public ResponseStructure<List<Student>> findByPercentageGreaterThan(double percentage) {
		List<Student> list = dao.findByPercentageGreaterThan(percentage);

		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Not Data found");
			structure.setData(list);

			return structure;
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return structure;

		}

	}

	public ResponseStructure<List<Student>> findByPercentageBetween(double percentage1, double percentage2) {
		List<Student> list = dao.findByPercentageBetween(percentage1, percentage2);
		ResponseStructure<List<Student>> structure = new ResponseStructure<List<Student>>();

		if (list.isEmpty()) {
			structure.setStatus(HttpStatus.NOT_FOUND.value());
			structure.setMessage("Not Data found");
			structure.setData(list);

			return structure;
		} else {
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Data found");
			structure.setData(list);

			return structure;

		}

	}

	// delete

	public ResponseStructure<String> deleteStudentById(int id) {
		dao.deleteStudentById(id);
		return createDeleteResponse("Deleted student with ID: " + id);
	}

	public ResponseStructure<String> deleteStudentByEmail(String email) {
		dao.deleteStudentByEmail(email);
		return createDeleteResponse("Deleted student with email: " + email);
	}

	public ResponseStructure<String> deleteStudentByPhone(long phone) {
		dao.deleteStudentByPhone(phone);
		return createDeleteResponse("Deleted student with phone: " + phone);
	}

	public ResponseStructure<String> deleteAllStudent() {
		dao.deleteAllStudent();
		return createDeleteResponse("Deleted All student");
	}

	public ResponseStructure<String> deleteStudentByName(String name) {
		dao.deleteStudentByName(name);
		return createDeleteResponse("Deleted student with name: " + name);
	}

	public ResponseStructure<String> deleteStudentByAddress(String address) {
		dao.deleteStudentByAddress(address);
		return createDeleteResponse("Deleted student with address: " + address);
	}

	public ResponseStructure<String> deleteStudentByGrade(String grade) {
		dao.deleteStudentByGrade(grade);
		return createDeleteResponse("Deleted student with grade: " + grade);
	}
	// update

	public ResponseStructure<Student> updateStudentNameById(int id, String name) {
		Student student = dao.updateStudentNameById(id, name);
		return createUpdateResponse(student, "Student Name Updated");
	}

	public ResponseStructure<Student> updateMotherNameById(int id, String name) {
		Student student = dao.updateMotherNameById(id, name);
		return createUpdateResponse(student, "Mother Name Updated");
	}

	public ResponseStructure<Student> updateFatherNameById(int id, String name) {
		Student student = dao.updateFatherNameById(id, name);
		return createUpdateResponse(student, "Father Name Updated");
	}

	public ResponseStructure<Student> updateAddressById(int id, String address) {
		Student student = dao.updateAddressById(id, address);
		return createUpdateResponse(student, "Address Updated");
	}

	public ResponseStructure<Student> updateEmailById(int id, String email) {
		Student student = dao.updateEmailById(id, email);
		return createUpdateResponse(student, "Email Updated");
	}

	public ResponseStructure<Student> updateStudentPasswordByEmail(String email, String password) {
		Student student = dao.updateStudentPasswordByEmail(email, password);
		return createUpdateResponse(student, "Password Updated");
	}

	public ResponseStructure<Student> updateStudentPasswordByPhone(long phone, String password) {
		Student student = dao.updateStudentPasswordByPhone(phone, password);
		return createUpdateResponse(student, "Password Updated");
	}

	public ResponseStructure<Student> updateStudent(Student updatedStudent) {
		Optional<Student> studentOptional = dao.findById(updatedStudent.getId());

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
				return createUpdateResponse(existingStudent, "Student data updated");
			} else {
				return createUpdateResponse(null, "Invalid marks");
			}
		} else {
			return createUpdateResponse(null, "Student not found");
		}
	}

}
