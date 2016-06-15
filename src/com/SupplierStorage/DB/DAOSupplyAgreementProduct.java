package com.SupplierStorage.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.SupplierStorage.BE.SupplyAgreement;
import com.SupplierStorage.BE.SupplyAgreementProduct;
import com.SupplierStorage.PL.ViewController;
import com.SupplierStorage.BE.Discount;
import com.SupplierStorage.BE.Product;

public class DAOSupplyAgreementProduct extends DAO<SupplyAgreementProduct> {

    DAOSupplyAgreement _supplyagreemnt;
	DAOSupplierProduct _product;
	DAODiscount _discount;
	
	public DAOSupplyAgreementProduct(Connection c) {
		super(c);
		_product = new DAOSupplierProduct(c);
		_discount = new DAODiscount(c);
        _supplyagreemnt = new DAOSupplyAgreement(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","SUPPLY_AGREEMENT_ID","SUPPLIER_PRODUCT_SN","PRICE"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Supply Agreement ID","Supplier Product SN","Price"};
	}

	@Override
	public String getTable() {
		return "Supply_Agreement_Product";
	}

	@Override
	protected String[] getValues(SupplyAgreementProduct object) {
		return new String[]{""+object.get_sp(),""+object.get_serial_number(),""+object.get_price()};
	}

	@Override
	public void insert(SupplyAgreementProduct object) throws SQLException {
		insert(getValues(object), false);
		
		for (Discount discount : object.get_discounts()) {
			discount.setAgreementProductSN(object.get_serial_number());
			discount.setSupplyid(object.get_sp());
			//TODO: Delete
			if(ViewController.debug)
			System.out.println("DAOSppluAgreementProdt: " + discount.get_precent());
			_discount.insert(discount);
		}
		
	}
	@Override
	public SupplyAgreementProduct getFromPK(String[] values) throws SQLException {
		return search(new int[]{1,2},values).get(0);
	}

	@Override
	public SupplyAgreementProduct create(ResultSet rs) throws SQLException {
		//TODO: Delete
				if(ViewController.debug)
		System.out.println("DAOSupplyAgreementProduct : " + _product.getFromPK(new String[]{rs.getString("Supplier_Product_SN")}));
		SupplyAgreementProduct agreementProduct = new SupplyAgreementProduct(_product.getFromPK(new String[]{""+rs.getInt("Supplier_Product_SN")}));
		agreementProduct.set_price(rs.getFloat("PRICE"));
		agreementProduct.set_sp(rs.getString("SUPPLY_AGREEMENT_ID"));
		ArrayList<Discount> discounts = _discount.search(new int[]{2,3},new String[]{
				agreementProduct.get_sp(),agreementProduct.get_serial_number()});
		agreementProduct.set_discounts(discounts);
		return agreementProduct;
		
	}
	
	public ArrayList<SupplyAgreementProduct> getProductByNameProducer(String producer,String name) throws SQLException
	{
		ArrayList<SupplyAgreementProduct> products = new ArrayList<>();
		String sql = "SELECT * FROM SUPPLY_AGREEMENT_PRODUCT WHERE SUPPLIER_PRODUCT_SN IN (SELECT SN FROM SUPPLIER_PRODUCT WHERE PRODUCTPRODUCERNAME="+
				producer + "PRODUCTNAME = " + name +")";
		_stm = _c.createStatement();
		ResultSet rs = _stm.executeQuery(sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		return products;
	}

	public ArrayList<SupplyAgreementProduct> getProductByDay(Product product,int day) throws SQLException {
		ArrayList<SupplyAgreementProduct> products = new ArrayList<>();
		String sql = "SELECT * FROM SUPPLY_AGREEMENT_PRODUCT WHERE (SUPPLY_AGREEMENT_ID IN (SELECT ID "
				+ "FROM SUPPLY_AGREEMENT WHERE DAY LIKE '%" + day + "%')) AND Supplier_Product_SN IN (SELECT SN FROM SUPPLIER_PRODUCT WHERE "
						+"PRODUCT_ID = " + product.get_id() + ")";
		_stm = _c.createStatement();
		ResultSet rs = _stm.executeQuery(sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		return products;
	}

	public ArrayList<SupplyAgreementProduct> getProductOnDemand(Product product) throws SQLException {
		ArrayList<SupplyAgreementProduct> products = new ArrayList<>();
		//TODO: Delete
				if(ViewController.debug)
		System.out.println(product.get_id());
		String sql = "SELECT * FROM SUPPLY_AGREEMENT_PRODUCT WHERE (SUPPLY_AGREEMENT_ID IN (SELECT ID "
				+ "FROM SUPPLY_AGREEMENT WHERE DAY LIKE '%" + 0 + "%')) AND Supplier_Product_SN IN (SELECT SN FROM SUPPLIER_PRODUCT WHERE "
						+ "PRODUCT_ID = " + product.get_id() + ")";
	
		//TODO: Delete
				if(ViewController.debug)
		System.out.println(sql);
		_stm = _c.createStatement();
		ResultSet rs = _stm.executeQuery(sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		return products;
	}

	public ArrayList<SupplyAgreementProduct> getAllOnDemand() throws SQLException {
		ArrayList<SupplyAgreementProduct> products = new ArrayList<>();
		String sql = "SELECT SUPPLY_AGREEMENT_ID,SUPPLIER_PRODUCT_SN,PRICE FROM"
				+ "((SELECT * FROM SUPPLY_AGREEMENT_PRODUCT WHERE (SUPPLY_AGREEMENT_ID IN (SELECT ID "
				+ "FROM SUPPLY_AGREEMENT WHERE DAY LIKE '%" + 0 + "%'))) JOIN SUPPLIER_PRODUCT ON SUPPLIER_PRODUCT_SN = SN) GROUP BY PRODUCT_ID";
		_stm = _c.createStatement();
		ResultSet rs = _stm.executeQuery(sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		return products;
		
	}

	public ArrayList<SupplyAgreementProduct> getAllDay(int d) throws SQLException {
		ArrayList<SupplyAgreementProduct> products = new ArrayList<>();
		String sql = "SELECT SUPPLY_AGREEMENT_ID,SUPPLIER_PRODUCT_SN,PRICE FROM"
				+ "((SELECT * FROM SUPPLY_AGREEMENT_PRODUCT WHERE (SUPPLY_AGREEMENT_ID IN (SELECT ID "
				+ "FROM SUPPLY_AGREEMENT WHERE DAY LIKE '%" + d + "%'))) JOIN SUPPLIER_PRODUCT ON SUPPLIER_PRODUCT_SN = SN) GROUP BY PRODUCT_ID";
		_stm = _c.createStatement();
		ResultSet rs = _stm.executeQuery(sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		//TODO: Delete
		if(ViewController.debug)
			System.out.println("number of products in day: "+products.size());
		return products;
	}


	/*public ArrayList<SupplyAgreementProduct> getDeliveryType(Product product , SupplyAgreement.Day day) throws SQLException {
		ArrayList<SupplyAgreementProduct> products = new ArrayList<>();
		//TODO: Delete
		if(ViewController.debug)
			System.out.println(product.get_id());
		String sql = "SELECT PRICE , SUPPLYID , Supplier_Product_SN  FROM SUPPLY_AGREEMENT_PRODUCT JOIN PRODUCT JOIN SUPPLY_AGREEMENT JOIN SUPPLIER_PRODUCT WHERE PRODUCT.ID=SUPPLIER_PRODUCT.PRODUCTID AND SUPPLIER_PRODUCT.SN=SUPPLY_AGREEMENT_PRODUCT.SUPPLIER_PRODUCT_SN AND SUPPLY_AGREEMENT_PRODUCT.SUPPLYID= SUPPLY_AGREEMENT.ID"
				+ "AND PRODUCT.ID="+ product.get_id()+"AND SUPPLY_AGREEMENT.DELIVERYTYPE= 'deliver' AND SUPPLY_AGREEMENT.SUPPLYTYPE= 'ondemand' ;";

		//TODO: Delete
		if(ViewController.debug)
			System.out.println(sql);
		_stm = _c.createStatement();
		ResultSet rs = _stm.executeQuery(sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		if (products.size()==0){
			 sql = "SELECT PRICE , SUPPLYID , Supplier_Product_SN  FROM SUPPLY_AGREEMENT_PRODUCT JOIN PRODUCT JOIN SUPPLY_AGREEMENT JOIN SUPPLIER_PRODUCT WHERE PRODUCT.ID=SUPPLIER_PRODUCT.PRODUCTID AND SUPPLIER_PRODUCT.SN=SUPPLY_AGREEMENT_PRODUCT.SUPPLIER_PRODUCT_SN AND SUPPLY_AGREEMENT_PRODUCT.SUPPLYID= SUPPLY_AGREEMENT.ID"
					+ "AND PRODUCT.ID="+ product.get_id()+"AND SUPPLY_AGREEMENT.SUPPLYTYPE= 'setday' ;";

			//TODO: Delete
			if(ViewController.debug)
				System.out.println(sql);
			_stm = _c.createStatement();
			rs = _stm.executeQuery(sql);
			while(rs.next())
			{
				products.add(create(rs));
			}
			ArrayList<SupplyAgreementProduct> min_products = new ArrayList<>();
			Calendar c = Calendar.getInstance();
			Date date = new Date();
            c.setTime(date);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			int min_day = dayOfWeek+1;
            for(int i=0; i<products.size(); i++) {
                SupplyAgreement sp = _supplyagreemnt.getFromPK(new String[]{products.get(i).get_sp()});
                products.get(i).set_supplyAgreement(sp);

            }
            boolean found=false;
            boolean finish=false;
            while (!found && !finish) {
                for (int j = 0; j < products.size(); j++) {
                    for (int i = 0; i < products.get(j).get_supplyAgreement().get_day().size(); i++){
                        if (products.get(j).get_supplyAgreement().get_day().get(i).getValue() == min_day) {
                            found = true;
                            min_products.add(products.get(j));
                        }
                    }
                }
                if (!found){
                    if (min_day== day.getValue()-1){
                        finish=true;
                    }
                    else{
                        if (min_day==7){
                            min_day=1;
                        }
                        else{
                            min_day++;
                        }
                    }

                }

            }
            if (!found){
                sql = "SELECT PRICE , SUPPLYID , Supplier_Product_SN  FROM SUPPLY_AGREEMENT_PRODUCT JOIN PRODUCT JOIN SUPPLY_AGREEMENT JOIN SUPPLIER_PRODUCT WHERE PRODUCT.ID=SUPPLIER_PRODUCT.PRODUCTID AND SUPPLIER_PRODUCT.SN=SUPPLY_AGREEMENT_PRODUCT.SUPPLIER_PRODUCT_SN AND SUPPLY_AGREEMENT_PRODUCT.SUPPLYID= SUPPLY_AGREEMENT.ID"
                        + "AND PRODUCT.ID="+ product.get_id()+"AND SUPPLY_AGREEMENT.SUPPLYTYPE= 'ondemand' AND SUPPLY_AGREEMENT.DELIVERYTYPE= 'cometake';";

                //TODO: Delete
                if(ViewController.debug)
                    System.out.println(sql);
                _stm = _c.createStatement();
                rs = _stm.executeQuery(sql);
                while(rs.next())
                {
                    products.add(create(rs));
                }
            }
            else{
                products=min_products;
            }

            }
		return products;
	}
	*/


    public ArrayList<SupplyAgreementProduct> getDeliveryType(Product product) throws SQLException {
		ArrayList<SupplyAgreementProduct> products = new ArrayList<>();
		//TODO: Delete
		if(ViewController.debug)
			System.out.println(product.get_id());
		String sql = "SELECT PRICE , SUPPLYID , Supplier_Product_SN  FROM SUPPLY_AGREEMENT_PRODUCT JOIN PRODUCT JOIN SUPPLY_AGREEMENT JOIN SUPPLIER_PRODUCT WHERE PRODUCT.ID=SUPPLIER_PRODUCT.PRODUCTID AND SUPPLIER_PRODUCT.SN=SUPPLY_AGREEMENT_PRODUCT.SUPPLIER_PRODUCT_SN AND SUPPLY_AGREEMENT_PRODUCT.SUPPLYID= SUPPLY_AGREEMENT.ID"
				+ "AND PRODUCT.ID="+ product.get_id()+"AND SUPPLY_AGREEMENT.DELIVERYTYPE= 'deliver' AND SUPPLY_AGREEMENT.SUPPLYTYPE= 'ondemand' ;";

		//TODO: Delete
		if(ViewController.debug)
			System.out.println(sql);
		_stm = _c.createStatement();
		ResultSet rs = _stm.executeQuery(sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		if (products.size()==0){
            sql = "SELECT PRICE , SUPPLYID , Supplier_Product_SN  FROM SUPPLY_AGREEMENT_PRODUCT JOIN PRODUCT JOIN SUPPLY_AGREEMENT JOIN SUPPLIER_PRODUCT WHERE PRODUCT.ID=SUPPLIER_PRODUCT.PRODUCTID AND SUPPLIER_PRODUCT.SN=SUPPLY_AGREEMENT_PRODUCT.SUPPLIER_PRODUCT_SN AND SUPPLY_AGREEMENT_PRODUCT.SUPPLYID= SUPPLY_AGREEMENT.ID"
                    + "AND PRODUCT.ID="+ product.get_id()+"AND SUPPLY_AGREEMENT.SUPPLYTYPE= 'ondemand' AND SUPPLY_AGREEMENT.DELIVERYTYPE= 'cometake';";

            //TODO: Delete
            if(ViewController.debug)
                System.out.println(sql);
            _stm = _c.createStatement();
            rs = _stm.executeQuery(sql);
            while(rs.next())
            {
                products.add(create(rs));
            }
        }
		return products;
	}

}
