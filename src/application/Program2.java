package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		
		Department newDep = new Department(null, "Musicas");
		departmentDao.insert(newDep);
		System.out.println("Insert completed!");
	}

}
