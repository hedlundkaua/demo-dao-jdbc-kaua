package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//metodos estaticos para concetar e desconectar noo banco de dados
public class DB {
	
	//metodo para conectar no banco de dados
	private static Connection conn = null;
	
	public static Connection getConnetion() {
		if(conn == null) {
			try {
			//pegar as propriedade de conexão com o props = LoadProperties();
			Properties props = loadProperties();
			
			//pego a url do properties
			String url = props.getProperty("dburl");
			
			//para obter uma conexão com o BD, vou chamar driver manager, passando a URL e o props
			//instanciamos um obj do tipo cnnection
			conn = DriverManager.getConnection(url, props);
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		//agora a conexão está salva
		return conn;
	}
	
	
	//metodo para fecha a conexão com o BD
	public static void CloseConnection() {
		try {
			if(conn != null) {
				conn.close();
			}
		}
		catch(SQLException e) {
			//proque?? o SQL exeption e derivado da classe exception( somos obrigados a trata-la)
			//a DbException é derivada da RuntimeException, então não precisamo ficar toda hora
			//colocando try catch
			//temos a nossa exceção personalizada
			throw new DbException(e.getMessage());
		}
		
	}
	
	
	private static Properties loadProperties() {
		//isso serve para abrir o arquivo db.properties e guardas as informações 
		//em um objeto do tipo Properties
		try(FileInputStream fs = new FileInputStream("db.properties")){
			//instancio umobjeto do tipo properties
			Properties props = new Properties();
			//p .load ele faz a leitura do arquivo apontado do input stream e 
			//guarda os dados dentro do obj properties
			//depois retornamos props
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			//lanço a exceção personalizada criada antes
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		try {
			if(st != null) {				
				st.close();
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
}
