package com.qsp.student_springboot.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "studentInfo")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "studentId")
	private int id;

	@Column(name = "studentName")
	private String name;

	@Column(name = "fatherName")
	private String fatherName;

	@Column(name = "motherName")
	private String motherName;

	@Column(name = "studentAddress")
	private String address;

	@Column(name = "studentEmail", unique = true)
	private String email;

	@Column(name = "studentPassword")
	private String password;

	@Column(name = "studentPhone", unique = true)
	private long phone;

	@Column(name = "securedMarks")
	private int securedMarks;

	@Column(name = "studenTotalMarks")
	private int totalMarks;

	@Column(name = "studentPercentage")
	private double percentage;

	@Column(name = "studentGrade")
	private String grade;

}
