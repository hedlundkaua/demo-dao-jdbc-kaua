package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	//o nosso Dao vai ter uma dependência com o banco de dados
	
	//esse obj conn vamos ter ele a disposição em qualquer lugar da classe SellerDaoJDBC
	private Connection conn;
	
	//construtor para ter a dependencia do connection
	public SellerDaoJDBC(Connection conn) {
		 this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller " 
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "  
					+ "VALUES "
					+ "(?, ?, ?, ?, ?) ", 
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());	
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Unexpected error! No rows affected");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Seller obj) {
				PreparedStatement st = null;
				try {
					st = conn.prepareStatement(
							"UPDATE seller "  
							+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "  
							+ "WHERE Id = ?");
					st.setString(1, obj.getName());
					st.setString(2, obj.getEmail());
					st.setDate(3, new Date(obj.getBirthDate().getTime()));
					st.setDouble(4, obj.getBaseSalary());
					st.setInt(5, obj.getDepartment().getId());	
					st.setInt(6, obj.getId());
					
					int rowsAffected = st.executeUpdate();
					
				}
				catch (SQLException e) {
					throw new DbException(e.getMessage());
				}finally {
					DB.closeStatement(st);
				}
	
	
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
	try {
		st = conn.prepareStatement(
				"DELETE FROM seller WHERE Id = ?");
		//ele executa é para apagar quando o id for igual a tanto
		//se esse id não existir ele vai executar igual
		//o where não vai dar ninguem, só que não vai dar erro
		st.setInt(1, id);
		
		int rows = st.executeUpdate();
	
		if(rows == 0) {
			throw new DbException("Linha não existe");
		}
	}
	catch (SQLException e) {
		throw new DbException(e.getMessage());
	}finally {
		DB.closeStatement(st);
	}

		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "  
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+" WHERE seller.Id = ? ");
			st.setInt(1, id);
			rs = st.executeQuery(); 
			//vamos testar se veio algum resultado, 
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				
				Seller obj = instantiateSeller(rs, dep);
				return obj;					
			}
			return null;
						
		//o resultSet ele traz os dados e formato de tabela
		//a classe Dao ela é responsavel por pegar os dados do BD relacional e transformar eles em objetos associados
		//vamos querer criar um obj com os dados coletados e associado a ele vai ter outro objeto com os dados de Department coletados dele
		
			//quando estamos programando POO mesmo buscnado os dados em forma de tabela, na memoria do computador, vamos querer ter os objetos associado, instanciados em memoria 
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			//nesse caso não precisa fechar a conexão
			//porque o Dao pde fazer mais de uma operação
			//no programa fechamos a operação
		}
		
		
	}

	@Override
	public List<Seller> findAll() {
		//busca todos os vendedores com nome de Dep e os dados e ordenando pelo nome
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department " 
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
					
			rs = st.executeQuery(); 
			//vamos testar se veio algum resultado, 
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			
			while(rs.next()) {
				//criamos um map vazio, e vamos guardar dentro do map qualquer departamento que instanciarmos
				//a cada vez que passar n while, vamos testar se o departamento ja existe
				//como? vamos no map e tentamos buscar no metodo get um departamento
				// que tem o Id de departmentId
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				
				//se o dep for nulo, ai sim instanciamos o novo departamento
				if(dep == null) {					
					dep = instantiateDepartment(rs);
					//vamos salvar o departamento dentro do map, para que na proxima vez 
					//possamos verificar e ver que ele existe.
					//o valor da key no map, é o de DepartmentId, e o departamento é dep
					//fazendo isso criamos um departamanetp só
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	//outro metodo auxiliar que vamos propagar.
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
		
	}

	//metodo auxilliar, vamos propagar ele
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "  
					+"FROM seller INNER JOIN department "  
					+"ON seller.DepartmentId = department.Id "
					+"WHERE DepartmentId = ? "
					+"ORDER BY Name");
					
			st.setInt(1, department.getId());
			rs = st.executeQuery(); 
			//vamos testar se veio algum resultado, 
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			
			while(rs.next()) {
				//criamos um map vazio, e vamos guardar dentro do map qualquer departamento que instanciarmos
				//a cada vez que passar n while, vamos testar se o departamento ja existe
				//como? vamos no map e tentamos buscar no metodo get um departamento
				// que tem o Id de departmentId
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				//se o dep for nulo, ai sim instanciamos o novo departamento
				if(dep == null) {					
					dep = instantiateDepartment(rs);
					//vamos salvar o departamento dentro do map, para que na proxima vez 
					//possamos verificar e ver que ele existe.
					//o valor da key no map, é o de DepartmentId, e o departamento é dep
					//fazendo isso criamos um departamanetp só
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
			
	
	}

	

}
