package com.bridgeit.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bridgeit.customAnnotation.FieldMatch;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Ajit Shikalgar User entity with fields userId, firstName, lastName,
 *         email, gender, phone, dob, password, isValid and it's noteList
 */
@Entity
@Table(name = "user")
@FieldMatch(message = "Passwords do not match")
// @Component("user")

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	// @NotBlank(message = "*Required")
	// @Size(min = 2, max = 15)
	// @NotNull(message = "*Required")
	// @Pattern(regexp = "^[A-Za-z]*$", message = "Invalid Entry")
	@Column(name = "firstname")
	private String firstName;

	// @NotBlank(message = "*Required")
	// @Size(min = 4, max = 10)
	// @NotNull(message = "*Required")
	// @Pattern(regexp = "^[A-Za-z]*$", message = "Invalid Entry")
	@Column(name = "lastname")
	private String lastName;

	// @NotBlank(message = "*Required")
	// @Size(min = 6, max = 30, message = "Invalid Entry")
	// @NotNull(message = "*Required")
	// @Email(message = "Invalid Entry")
	@Column(name = "email")
	private String email;

	// @NotBlank(message = "*Required")
	// @Size(min = 4, max = 6, message = "Invalid Entry")
	// @NotNull(message = "*Required")
	@Column(name = "gender")
	private String gender;

	// @NotBlank(message = "*Required")
	// @NotNull(message = "*Required")
	// @DateTimeFormat(pattern = "dd/MM/yyyy")
	// @Past(message = "Invalid Entry")
	@Column(name = "dob")
	private String dob;

	// @NotBlank(message = "*Required")
	// @NotNull(message = "*Required")
	// @Size(min = 10, max = 10, message = "Invalid Entry")
	// @Pattern(regexp = "^[0-9]*$", message = "Invalid Entry")
	@Column(name = "phone")
	private String phone;

	// @NotBlank(message = "*Required")
	// @NotNull(message = "*Required")
	// @Size(min = 4, max = 30, message = "Short Entry")
	@Column(name = "password")
	private String password;

	// @NotBlank(message = "*Required")
	// @NotNull(message = "*Required")
	// @Size(min = 4, max = 30, message = "Short Entry")
	@Transient
	private String confirmPassword;

	@Column(name = "isvalid")
	private boolean isValid = false;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER, mappedBy = "user")
	private List<Note> noteList;

	// figured out a way to match both passwords
	// in annotations using Class level Validator

	public Collection<Note> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<Note> noteList) {
		this.noteList = noteList;
	}

	public boolean getIsValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User() {

	}

	@Override
	public int hashCode() {
		return this.id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;

		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public User(Integer id, String firstName, String lastName, String email, String gender, String dob, String phone,
			String password, String confirmPassword, boolean isValid, List<Note> noteList) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.dob = dob;
		this.phone = phone;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.isValid = isValid;
		this.noteList = noteList;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", gender=" + gender + ", dob=" + dob + ", phone=" + phone + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", isValid=" + isValid + ", noteList=" + noteList + "]";
	}

}