package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	
	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer obj);
	public Seller findById(Integer id);
	//retorna um departamento, vai ser responsavel por pegar o Id do argumento e se exxistir ele retorna o departamento,
	//se não retorna null
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);
	
	
}
