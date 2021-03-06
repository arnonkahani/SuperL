package com.SupplierStorage.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.SupplierStorage.BE.Producer;
import com.SupplierStorage.BE.Product;
import com.SupplierStorage.BE.SubCatagory;
import com.SupplierStorage.BE.SubSubCatagory;
import com.SupplierStorage.BE.Catagory;

public class DAOProduct extends DAO<Product> {

	DAOProducer _producer;
	DAOCatagory _catagory;
	DAOSubCatagory _sub_catagory;
	DAOSubSubCatagory _sub_sub_catagory;
	public DAOProduct(Connection c) {
		super(c);
		_producer = new DAOProducer(c);
		_catagory = new DAOCatagory(c);
		_sub_catagory = new DAOSubCatagory(c);
		_sub_sub_catagory = new DAOSubSubCatagory(c);
	}


	@Override
	public void insert(Product object) throws SQLException {
		try{
			_producer.insert(object.get_producer());
		}
		catch(SQLException e){}
		try{
			_catagory.insert(new Catagory(object.get_category()));
		}
		catch(SQLException e){}
		try{
			_sub_catagory.insert(new SubCatagory(object.get_category(), object.get_sub_category()));
		}
		catch(SQLException e){}
		try{
			_sub_sub_catagory.insert(new SubSubCatagory(object.get_sub_sub_category(), object.get_sub_category(),object.get_category()));
		}
		catch(SQLException e)
		{
		}
	 insert(getValues(object),true);
	 object.set_id(getLastAutoID());
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","ID","NAME","PRODUCERNAME","WEIGHT","SHELFLIFE","CATAGORY","SUB_CATAGORY"
		,"SUB_SUB_CATAGORY","MIN_AMOUNT","PRICE"};
	}

	@Override
	public String getTable() {
		return "PRODUCT";
	}


	@Override
	public Product getFromPK(String[] values) throws SQLException {
		return search(new int[]{1},values).get(0);
	}


	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","ID","Name","Producer's Name","Weight","Shelf Life",
				"Catagory","Sub Catagory","Sub Sub Catagory","Minimum Amount","Price"};
	}


	@Override
	public Product create(ResultSet rs) throws SQLException {
		Product	product = new Product();
		product.set_name(rs.getString("Name"));
		product.set_producer(_producer.getFromPK(new String[]{"'"+rs.getString("PRODUCERNAME")+"'"}));
		product.set_shelf_life(rs.getInt("SHELFLIFE"));
		product.set_weight(rs.getInt("Weight"));
		product.set_category(rs.getString("CATAGORY"));
		product.set_sub_category(rs.getString("SUB_CATAGORY"));
		product.set_sub_sub_category(rs.getString("SUB_SUB_CATAGORY"));
		product.set_id(rs.getInt("ID"));
		product.set_min_amount(rs.getInt("MIN_AMOUNT"));
		product.set_price(rs.getFloat("PRICE"));

		return product;
	}


	@Override
	protected String[] getValues(Product object) {
		
		return new String[] {""+object.get_id(),"'"+object.get_name()+"'","'"+object.get_producer().getName()+"'"
				,""+object.get_weight(),""+object.get_shelf_life(),"'"+object.get_category()+"'",
				"'"+object.get_sub_category()+"'","'"+object.get_sub_sub_category()+"'",
				object.get_min_amount()+"",""+object.get_price()};
		
	}
	
	
	public ArrayList<Catagory> getAllCatagory() throws SQLException {
		return _catagory.getAll();
	}

	public ArrayList<SubCatagory> getAllSubCatagory(String catagory) throws SQLException {
		return _sub_catagory.search(new int[]{1}, new String[]{"'"+catagory+"'"});
	}
	
	public ArrayList<SubSubCatagory> getAllSubSubCatagory(String catagory,String sub_catagory) throws SQLException {
		return _sub_sub_catagory.search(new int[]{2,3}, new String[]{"'"+sub_catagory+"'","'"+catagory+"'"});
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
	
	class DAOCatagory extends DAO<Catagory>{

		public DAOCatagory(Connection c) {
			super(c);
		}

		@Override
		public String[] getSearchFields() {
			return new String[]{"All","NAME_CAT"};
		}

		@Override
		public String[] getSearchFieldsView() {
			return new String[]{"All","Name"};
		}

		@Override
		public String getTable() {
			return "CATAGORY";
		}

		@Override
		protected String[] getValues(Catagory object) {
			return new String[]{"'"+object.getName_cat()+"'"};
		}

		@Override
		public Catagory getFromPK(String[] values) throws SQLException {
			return search(new int[]{1}, values).get(0);
		}

		@Override
		public Catagory create(ResultSet rs) throws SQLException {
			return new Catagory(rs.getString("NAME_CAT"));
		}
		@Override
		public ArrayList<Catagory> getAll() throws SQLException {
			return search(new int[]{0}, new String[]{});
		}
		
	}
	
	class DAOSubCatagory extends DAO<SubCatagory>{

		public DAOSubCatagory(Connection c) {
			super(c);
		}

		@Override
		public String[] getSearchFields() {
			return new String[]{"All","NAME_CAT","NAME_SCAT"};
		}

		@Override
		public String[] getSearchFieldsView() {
			return new String[]{"All","Catagory","Sub Catagory"};
		}

		@Override
		public String getTable() {
			return "SUB_CATAGORY";
		}

		@Override
		protected String[] getValues(SubCatagory object) {
			return new String[]{"'"+object.getName_cat()+"'","'"+object.getName_scat()+"'"};
		}

		@Override
		public SubCatagory getFromPK(String[] values) throws SQLException {
			return search(new int[]{1,2},values).get(0);
		}

		@Override
		public SubCatagory create(ResultSet rs) throws SQLException {
			return new SubCatagory(rs.getString("NAME_CAT"), rs.getString("NAME_SCAT"));
		}

		
	}
	class DAOSubSubCatagory extends DAO<SubSubCatagory>{

		public DAOSubSubCatagory(Connection c) {
			super(c);
		}

		@Override
		public String[] getSearchFields() {
			return new String[]{"All","NAME_SSCAT","NAME_SCAT","NAME_CAT"};
		}

		@Override
		public String[] getSearchFieldsView() {
			return new String[]{"All","Sub Sub Catagory","Sub Catagory","Catagory"};
		}

		@Override
		public String getTable() {
			return "SUB_SUB_CATAGORY";
		}

		@Override
		protected String[] getValues(SubSubCatagory object) {
			return new String[]{"'"+object.getName_sscat()+"'","'"+object.getName_scat()+"'","'"+object.getName_catagory()+"'"};
		}

		@Override
		public SubSubCatagory getFromPK(String[] values) throws SQLException {
			return search(new int[]{1, 2,3},values).get(0);
		}

		@Override
		public SubSubCatagory create(ResultSet rs) throws SQLException {
			return new SubSubCatagory(rs.getString("NAME_SSCAT"), rs.getString("NAME_SCAT"),rs.getString("NAME_CAT"));
		}
		
	}

}
