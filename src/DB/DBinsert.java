package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import BE.AgreementProduct;
import BE.Contact;
import BE.Discount;
import BE.Order;
import BE.OrderProduct;
import BE.Producer;
import BE.Product;
import BE.Supplier;
import BE.SupplierProduct;
import BE.SupplyAgreement;

public class DBinsert {
	private Connection _c;
	private Statement _stm;
	private DBUtils _utilty;
	
	
	public DBinsert(Connection c,DBUtils utilty) {
		_c = c;
		_utilty = utilty;
	}
	public <T> void insert(T or) {
		
		
		
		
		
	}
	private void insertOrder(Order order) throws SQLException{
		int pk = insert(order.getValues(), order.getClass(), Statement.RETURN_GENERATED_KEYS);
		order.setOrderID(""+pk);
		for (OrderProduct orderProduct : order.get_amountProduct()) {
			insert(orderProduct.getValues(),orderProduct.getClass(),Statement.NO_GENERATED_KEYS);
		}
	}
	private void insertSupplyAgreement(SupplyAgreement supplyAgreement) throws SQLException{
		int pk = insert(supplyAgreement.getValues(), supplyAgreement.getClass(), Statement.RETURN_GENERATED_KEYS);
		supplyAgreement.set_supplyID(""+pk);
		for (AgreementProduct agreementProduct : supplyAgreement.get_prices()) {
			insert(agreementProduct.getValues(),agreementProduct.getClass(),Statement.NO_GENERATED_KEYS);
			for (Discount discount : agreementProduct.get_discounts()) {
				insert(discount.getValues(), discount.getClass(), Statement.NO_GENERATED_KEYS);
			}
		}
		
	}
	private void insertSupplier(Supplier supplier) throws SQLException{
		insert(supplier.getValues(),supplier.getClass(),Statement.NO_GENERATED_KEYS);
		for (Contact contact : supplier.get_contacts()) {
			insert(contact.getValues(),contact.getClass(),Statement.NO_GENERATED_KEYS);
		}
		
	}
	private void insertSupplierProduct(SupplierProduct product) throws SQLException{
		try{
			insert(product.get_product().get_producer().getValues(),Producer.class,Statement.NO_GENERATED_KEYS);
		}catch(SQLException e)
		{}
		int fk = insert(product.get_product().getValues(),Product.class,Statement.RETURN_GENERATED_KEYS);
		product.get_product().set_pid(fk);
		insert(product.getValues(), product.getClass(), Statement.RETURN_GENERATED_KEYS);
	}
	
	public int insert(String[] values,Class object_class,int flag) throws SQLException
	{
		_stm = _c.createStatement();
		String sql="INSERT INTO "+_utilty.getClassToTable().get(object_class) +" (";
		String columns = "";
		String insert_columns = "";
		for (int i = 0; i < values.length; i++) {
			if(i==values.length-1)
				columns = columns + _utilty.convertToSql(values[i], object_class,i);
			else
				columns = columns + _utilty.convertToSql(values[i], object_class,i) +",";
		}
		for (int i = 1; i < _utilty.getSearch_fields().get(object_class).length; i++) {
				if(i==_utilty.getSearch_fields().get(object_class).length-1)
					insert_columns = insert_columns + _utilty.getSearch_fields().get(object_class)[i];
				else
					insert_columns = insert_columns + _utilty.getSearch_fields().get(object_class)[i] +",";
			
		}
		sql = sql +  " (" + insert_columns + " ) VALUES (" +columns + ")";

		
		
		if(flag == Statement.RETURN_GENERATED_KEYS){
		_stm.executeUpdate(sql,flag);
		ResultSet rs = _stm.getGeneratedKeys();
		rs.next();
		int pk = rs.getInt(0);
		return pk;
		}
		else
		_stm.executeUpdate(sql);
		return 0;
		
	}
	
	

}
