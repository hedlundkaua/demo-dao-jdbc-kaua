package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer Id;
	private String name;
	private String email;
	private Date BirthDate;
	private Double BaseSalary;
	
	private Department department;

	public Seller() {
	}

	public Seller(Integer id, String name, String email, Date birthDate, Double baseSalary, Department department) {
		Id = id;
		this.name = name;
		this.email = email;
		BirthDate = birthDate;
		BaseSalary = baseSalary;
		this.department = department;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return BirthDate;
	}

	public void setBirthDate(Date birthDate) {
		BirthDate = birthDate;
	}

	public Double getBaseSalary() {
		return BaseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		BaseSalary = baseSalary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		return Objects.equals(Id, other.Id);
	}

	@Override
	public String toString() {
		return "Seller [Id=" + Id + ", name=" + name + ", email=" + email + ", BirthDate=" + BirthDate + ", BaseSalary="
				+ BaseSalary + ", department=" + department + "]";
	}
	
	
	

}
