package model.dao;



import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer obj);
	Department findById(Integer id);
	//retorna um departamento, vai ser responsavel por pegar o Id do argumento e se exxistir ele retorna o departamento,
	//se não retorna null
	List<Department> findAll();
	
	

}
