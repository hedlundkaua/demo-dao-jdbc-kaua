package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
//uma classe auxiliar responsavel por instanciar os Daos
//tem operações estaticas para instanciaas os daos
	
	public static SellerDao createSellerDao() {
		//vai instanciar uma implementação.
		return new SellerDaoJDBC(DB.getConnetion());
	}

}
