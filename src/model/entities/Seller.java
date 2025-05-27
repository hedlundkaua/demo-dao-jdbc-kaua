package model.entities;

public class Seller {
	private Integer Id;
	private String name;
	private String email;
	private Date BirthDate;
	private Double BaseSalary;

	public Seller() {
	}

	public Seller(Integer id, String name, String email, Date birthDate, Double baseSalary) {
		Id = id;
		this.name = name;
		this.email = email;
		BirthDate = birthDate;
		BaseSalary = baseSalary;
	}
	
	
	
	

}
