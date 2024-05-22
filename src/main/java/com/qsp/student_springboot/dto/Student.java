package com.qsp.student_springboot.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "studentInfo")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "studentId")
	private int id;

//	@NotBlank
//	@NotNull
	@NotEmpty
	@Column(name = "studentName")
	private String name;

	@NotEmpty
	@Column(name = "fatherName")
	private String fatherName;

	@NotEmpty
	@Column(name = "motherName")
	private String motherName;

	@NotEmpty
	@Column(name = "studentAddress")
	private String address;

	@NotEmpty(message = "email can't be Empty")
	@Email(regexp = "[a-z0-9,_$]+@(a-z]+\\.[a-z](2,3)", message = "incorrect email format")
	@Column(name = "studentEmail", unique = true)
	private String email;

	@NotEmpty(message = "password can't be Empty")
	@Size(min = 6, max = 16, message = "password must be between 6 and 16 characters long")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,16}$", message = "password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
	@Column(name = "studentPassword")
	private String password;

	@Min(6000000000L)
	@Max(9999999999L)
	@Column(name = "studentPhone", unique = true)
	private long phone;

	@Min(0)
	@Column(name = "securedMarks")
	private int securedMarks;

	@Column(name = "studenTotalMarks")
	private int totalMarks;

	@Column(name = "studentPercentage")
	private double percentage;

	@Column(name = "studentGrade")
	private String grade;

}
