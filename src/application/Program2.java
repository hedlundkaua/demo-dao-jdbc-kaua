package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		//instancia a classe departmentDao e liga o Connection
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		
		System.out.println("== Department Insert ==");
		Department newDep = new Department(null, "Musicas");
		departmentDao.insert(newDep);
		System.out.println("Insert completed!");
		
		
		System.out.println("\n== Department Delete ==");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed!");
		
		
		System.out.println("\n== Department findById ==");
		Department dp = departmentDao.findById(2);
		 System.out.println(dp);
		
		System.out.println("\n== Department findAll ==");
		List<Department> dep2 =  departmentDao.findAll();
		for(Department dep : dep2) {
			System.out.println(dep);
		}
		
		System.out.println("\n== Department Update ==");
		dp = departmentDao.findById(2);
		dp.setName("Cosmeticos");
		departmentDao.update(dp);
		System.out.println(dp);
	}

}
