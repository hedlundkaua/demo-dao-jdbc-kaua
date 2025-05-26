package db;

public class DbIntegrityException extends RuntimeException {

	//Exceção personalizada de intregridade referêncial
	private static final long serialVersionUID = 1L;

	public DbIntegrityException(String msg) {
		super(msg);
	}
}
