package DB;

import java.sql.Connection;

public class DAOFactory {

	private Connection _c;
	public DAOFactory(Connection c)
	{
		_c = c;
	}
	public DAO create(Class object_class)
	{
		DAO dao = null;
		switch(object_class.getName())
		{
		case "BE.Order":
			dao = new DAOOrder(_c);
			break;
		case "BE.Supplier":
			dao = new DAOSupplier(_c);
			break;
		case "BE.SupplierProduct":
			dao = new DAOSupplierProduct(_c);
			break;
		case "BE.SupplyAgreement":
			dao = new DAOSupplyAgreement(_c);
			break;
		case "BE.Product":
			dao = new DAOProduct(_c);
			break;
		}
		if(dao == null)
			System.err.println("Error in factory");
		return dao;
	}
}
