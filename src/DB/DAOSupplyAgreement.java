package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BE.SupplyAgreementProduct;
import BE.SupplyAgreement;
import BE.SupplyAgreement.Day;

public class DAOSupplyAgreement extends DAO<SupplyAgreement> {

	DAOSupplier _supplier;
	DAOSupplyAgreementProduct _product;
	public DAOSupplyAgreement(Connection c) {
		super(c);
		_supplier = new DAOSupplier(c);
		_product = new DAOSupplyAgreementProduct(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","ID","SupplierCN","SupplyType","Day","DELEVERYTYPE"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","ID","Supplier's company number","Supply Type","Days","Delevry Type"};
	}

	@Override
	public String getTable() {
		return "Supply_Agreement";
	}

	@Override
	protected String[] getValues(SupplyAgreement object) {
		
			return new String[]{object.get_supplyID(),"'" + object.get_sup().get_CN() + "'","'"+object.get_sType().name()+"'","'"+getDays(object)+"'","'"+object.get_dType().name()+"'"};
		}

		
	private String getDays(SupplyAgreement object){
		if(object.get_day().size() == 0)
			return "0";
		String day_string ="";
		
		for (Day day : object.get_day()) {
			day_string = day_string + "" + day.getValue();
		}
		return day_string;
	}

	@Override
	public void insert(SupplyAgreement object) throws SQLException {
		insert(getValues(object), true);
		int pk = getLastAutoID();
		object.set_supplyID(""+pk);
		for (SupplyAgreementProduct agreementProduct : object.get_prices()) {
			agreementProduct.set_sp(object.get_supplyID());
			_product.insert(agreementProduct);
		}
		object.set_sup(_supplier.getFromPK(new String[]{object.get_sup().get_CN()}));
		
	
	}
	
	@Override
	public SupplyAgreement getFromPK(String[] values) throws SQLException {
		return search(new int[]{1}, values).get(0);
	}

	@Override
	public SupplyAgreement create(ResultSet rs) throws SQLException {
		SupplyAgreement supplyAgreement = new SupplyAgreement();
		supplyAgreement.set_supplyID(""+rs.getInt("ID"));
		supplyAgreement.set_day(rs.getString("day"));
		supplyAgreement.set_dType(rs.getString("DELEVERYTYPE"));
		supplyAgreement.set_sType(rs.getString("SupplyType"));
		supplyAgreement.set_sup(_supplier.getFromPK(new String[]{"'"+rs.getString("SUPPLIERCN")+"'"}));
		ArrayList<SupplyAgreementProduct> products = _product.search(new int[]{1}, new String[]{supplyAgreement.get_supplyID()});
		supplyAgreement.set_prices(products);
		
		
		return supplyAgreement;
	}

}
