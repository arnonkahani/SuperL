package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import BE.Discount;

public class DAODiscount extends DAO<Discount> {

	public DAODiscount(Connection c) {
		super(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","Amount","SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID","SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN","Precent"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Amount","SUPPLY AGREEMENT PRODUCT AGREEMENT ID","SUPPLY AGREEMENT PRODUCT PRODUCT SN","Precent"};
	}

	@Override
	public String getTable() {
		return "SUPPLY_AGREEMENT_PRODUCT_DISCOUNT";
	}

	@Override
	protected String[] getValues(Discount object) {
		return new String[]{""+object.get_quantity(),""+object.getSupplyid(),""+object.getAgreementProductSN(),""+object.get_precent()};
	}

	@Override
	public Discount getFromPK(String[] values) throws SQLException {
		return search(new int[]{1,2,3},values).get(0);
	}

	@Override
	public Discount create(ResultSet rs) throws SQLException {
		Discount discount = new Discount();
		discount.set_precent(rs.getFloat("precent"));
		discount.set_quantity(rs.getInt("amount"));
		discount.setAgreementProductSN(""+rs.getInt("SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN"));
		discount.setSupplyid(""+rs.getInt("SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID"));
		
		return discount;
	}

}
