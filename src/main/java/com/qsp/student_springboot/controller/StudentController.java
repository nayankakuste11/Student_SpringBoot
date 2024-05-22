package com.qsp.student_springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.student_springboot.dto.Student;
import com.qsp.student_springboot.response.ResponseStructure;
import com.qsp.student_springboot.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService service;

	// save

	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Student>> saveStudent(@RequestBody Student student) {
		return service.saveStudent(student);
	}

	@PostMapping("/saveAll")
	public ResponseEntity<ResponseStructure<List<Student>>> saveAllStudent(@RequestBody List<Student> student) {
		return service.saveAllStudent(student);
	}

	// Find
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Student>>> findAll() {
		return service.findAll();
	}

	@GetMapping("/login/{userName}/{password}")
	public ResponseEntity<ResponseStructure<String>> login(@PathVariable String userName,
			@PathVariable String password) {
		return service.login(userName, password);
	}

	@GetMapping("/findByGrade/{grade}")
	public ResponseEntity<ResponseStructure<List<Student>>> findByGrade(@PathVariable String grade) {
		return service.findByGrade(grade);
	}

	@GetMapping("/findByFatherName/{fatherName}")
	public ResponseEntity<ResponseStructure<List<Student>>> findByfatherName(@PathVariable String fatherName) {
		return service.findByFatherName(fatherName);
	}

	@GetMapping("/findByMotherName/{motherName}")
	public ResponseEntity<ResponseStructure<List<Student>>> findByMotherName(@PathVariable String motherName) {
		return service.findByMotherName(motherName);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<ResponseStructure<Student>> findById(@PathVariable int id) {
		return service.findById(id);
	}

	@GetMapping("/findByEmail/{email}")
	public ResponseEntity<ResponseStructure<Student>> findByEmail(@PathVariable String email) {
		return service.findByEmail(email);
	}

	@GetMapping("/findByName/{name}")
	public ResponseEntity<ResponseStructure<List<Student>>> findByName(@PathVariable String name) {
		return service.findByName(name);
	}

	@GetMapping("/findByPhone/{phone}")
	public ResponseEntity<ResponseStructure<Student>> findByPhone(@PathVariable long phone) {
		return service.findByPhone(phone);
	}

	@GetMapping("/findByAddress/{address}")
	public ResponseEntity<ResponseStructure<List<Student>>> findByAddress(@PathVariable String address) {
		return service.findByAddress(address);
	}

	@GetMapping("/lessThan/{percentage}")
	public ResponseEntity<ResponseStructure<List<Student>>> findByPercentageLessThan(@PathVariable double percentage) {
		return service.findByPercentageLessThan(percentage);
	}

	@GetMapping("/greaterThan/{percentage}")
	public ResponseEntity<ResponseStructure<List<Student>>> findByPercentageGreaterThan(
			@PathVariable double percentage) {
		return service.findByPercentageGreaterThan(percentage);
	}

	@GetMapping("/between/{percentage1}/{percentage2}")
	public ResponseEntity<ResponseStructure<List<Student>>> findByPercentageBetween(@PathVariable double percentage1,
			@PathVariable double percentage2) {
		return service.findByPercentageBetween(percentage1, percentage2);
	}
	// delete

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteStudentById(@PathVariable int id) {
		return service.deleteStudentById(id);

	}

	@DeleteMapping("/delete/byEmail/{email}")
	public ResponseEntity<ResponseStructure<String>> deleteStudentByEmail(@PathVariable String email) {
		return service.deleteStudentByEmail(email);

	}

	@DeleteMapping("/delete/byPhone/{phone}")
	public ResponseEntity<ResponseStructure<String>> deleteStudentByPhone(@PathVariable long phone) {
		return service.deleteStudentByPhone(phone);

	}

	@DeleteMapping("/delete/all")
	public ResponseEntity<ResponseStructure<String>> deleteAllStudents() {
		return service.deleteAllStudent();
	}

	@DeleteMapping("/delete/byName/{name}")
	public ResponseEntity<ResponseStructure<String>> deleteStudentByName(@PathVariable String name) {
		return service.deleteStudentByName(name);

	}

	@DeleteMapping("/delete/byAddress/{address}")
	public ResponseEntity<ResponseStructure<String>> deleteStudentByAddress(@PathVariable String address) {
		return service.deleteStudentByAddress(address);

	}

	@DeleteMapping("/delete/byGrade/{grade}")
	public ResponseEntity<ResponseStructure<String>> deleteStudentByGrade(@PathVariable String grade) {
		return service.deleteStudentByGrade(grade);

	}
	// update

	@PatchMapping("/updateName/{id}/{name}")
	public ResponseEntity<ResponseStructure<Student>> updateStudentNameById(@PathVariable int id,
			@PathVariable String name) {
		return service.updateStudentNameById(id, name);

	}

	@PatchMapping("/updateFatherName/{id}/{name}")
	public ResponseEntity<ResponseStructure<Student>> updateFatherNameById(@PathVariable int id,
			@PathVariable String name) { 
		return service.updateFatherNameById(id, name);
	}

	@PatchMapping("/updateMotherName/{id}/{name}")
	public ResponseEntity<ResponseStructure<Student>> updateMotherNameById(@PathVariable int id,
			@PathVariable String name) {
		return service.updateMotherNameById(id, name);
	}

	@PatchMapping("/updateAddress/{id}/{address}")
	public ResponseEntity<ResponseStructure<Student>> updateAddressById(@PathVariable int id,
			@PathVariable String address) {
		return service.updateAddressById(id, address);
	}

	@PatchMapping("/updateEmail/{id}/{email}")
	public ResponseEntity<ResponseStructure<Student>> updateEmailById(@PathVariable int id,
			@PathVariable String email) {
		return service.updateEmailById(id, email);
	}

	@PatchMapping("/update/password/{email}/{password}")
	public ResponseEntity<ResponseStructure<Student>> updateStudentPasswordByEmail(@PathVariable String email,
			@PathVariable String password) {
		return service.updateStudentPasswordByEmail(email, password);
	}

	@PatchMapping("/update/password/{phone}/{password}")
	public ResponseEntity<ResponseStructure<Student>> updateStudentPasswordByPhone(@PathVariable long phone,
			@PathVariable String password) {
		return service.updateStudentPasswordByPhone(phone, password);
	}

	@PatchMapping("/update/all")
	public ResponseEntity<ResponseStructure<Student>> updateStudent(@RequestBody Student updatedStudent) {
		return service.updateStudent(updatedStudent);
	}
}
