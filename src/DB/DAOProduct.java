package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import BE.Product;

public class DAOProduct extends DAO<Product> {

	DAOProducer _producer;
	public DAOProduct(Connection c) {
		super(c);
		_producer = new DAOProducer(c);
		
	}


	@Override
	public void insert(Product object) throws SQLException {
		try{
			//TODO: DELETE 
			System.out.println("stage 1.1");
			_producer.insert(object.get_producer());
			System.out.println("stage 1.1 over");
		}
		catch(SQLException e)
		{
		}
	 insert(getValues(object),false);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","NAME","PRODUCERNAME","WEIGHT","SHELFLIFE"};
	}

	@Override
	public String getTable() {
		return "PRODUCT";
	}


	@Override
	public Product getFromPK(String[] values) throws SQLException {
		return search(new int[]{1,2},values).get(0);
	}


	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Name","Producer's Name","Weight","Shelf Life"};
	}


	@Override
	public Product create(ResultSet rs) throws SQLException {
		Product	product = new Product();
		product.set_name(rs.getString("Name"));
		product.set_producer(_producer.getFromPK(new String[]{"'"+rs.getString("PRODUCERNAME")+"'"}));
		product.set_shelf_life(rs.getInt("SHELFLIFE"));
		product.set_weight(rs.getInt("Weight"));

		return product;
	}


	@Override
	protected String[] getValues(Product object) {
		
		return new String[] {"'"+object.get_name()+"'","'"+object.get_producer().getName()+"'"
				,""+object.get_weight(),""+object.get_shelf_life()};
		
	}

}
