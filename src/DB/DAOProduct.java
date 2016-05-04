package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import BE.Producer;
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
			_producer.insert(object.get_producer());
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
	class DAOProducer extends DAO<Producer> {

		public DAOProducer(Connection c) {
			super(c);
		}


		@Override
		public String[] getSearchFields() {
			return new String[]{"All","NAME"};
		}

		@Override
		public String getTable() {
			return "PRODUCER";
		}


		@Override
		public Producer getFromPK(String[] values) throws SQLException{
			return search(new int[]{1}, values).get(0);
		}

		@Override
		public String[] getSearchFieldsView() {
			return new String[]{"All","Name"};
		}

		@Override
		public Producer create(ResultSet rs) throws SQLException {
			Producer producer = new Producer(rs.getString("Name"));
			return producer;
		}


		@Override
		protected String[] getValues(Producer object) {
				return new String[]{"'"+object.getName()+"'"};
		}

	}

}
