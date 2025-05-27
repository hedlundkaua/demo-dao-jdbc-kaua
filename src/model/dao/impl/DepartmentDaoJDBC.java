package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn; 
	
	//para ter a dependência do Connection
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
		
	@Override
	public void insert(Department obj) {
		//interface do JDBC que tem uma instrução SQL pré compilada
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Department " 
					+ "(Name) "  
					+ "VALUES "
					+ "(?) ", 
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
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
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "  
					+ "SET Name = ? "  
					+ "WHERE Id = ?");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			int rowsAffected = st.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public void deleteById(Integer obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
