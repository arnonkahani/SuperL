package com.SupplierStorage.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Common.DB.DB;
import com.SupplierStorage.BE.Contact;

import com.SupplierStorage.BE.Supplier;
import com.SupplierStorage.BE.SupplierProduct;
import com.Transpotation.Models.Area;
import com.Transpotation.Models.Place;
import com.Transpotation.Models.Transportation;


public class DAOSupplier extends DAO<Supplier> {

	DAOContact _contact;
	DAOSupplierProduct _supplierProduct;
	public DAOSupplier(Connection c) {
		super(c);
		_contact = new DAOContact(c);
		_supplierProduct = new DAOSupplierProduct(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","CN","Name","BANKNUMBER","PAYMANETMETHOD","ADDRESS","SUPLLIERAREA"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Company Number","Name","Bank Number","Payment Method","Address","SUPLLIER AREA"};
	}

	@Override
	public String getTable() {
		return "Supplier";
	}

	@Override
	protected String[] getValues(Supplier object) {
		return new String[]{"'"+object.get_CN()+"'","'"+object.get_name()+"'","'"+object.getBankNumber()+"'",""+object.getPaymentMethod(),
                "'"+object.get_address()+"'","'"+object.get_area().getAreaID()+"'"};
	}

	@Override
	public Supplier getFromPK(String[] values) throws SQLException {
		return search(new int[]{1}, values).get(0);
	}

	@Override
	public void insert(Supplier object) throws SQLException{
		insert(getValues(object),false);
		for (Contact contact : object.get_contacts()) {
			contact.set_supplier_cn(object.get_CN());
			_contact.insert(contact);
		}
        try {
            DB.getInstance().getPlaceDBHandler().insert(object.get_Place());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
	
	public ArrayList<Area> getAllAreas()
    {
        ArrayList<Area> areas = new ArrayList<>();
        try {
            ;
            for (Area r:DB.getInstance().getAreaDBHandler().all())
            {
                areas.add(r);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  areas;

    }

	@Override
	public Supplier create(ResultSet rs) throws SQLException {
		Supplier supplier = new Supplier();
		supplier.set_CN(rs.getString("CN"));
		ArrayList<Contact> contacts = _contact.search(new int[]{4}, new String[]{rs.getString("CN")});
		supplier.set_contacts(contacts);
		supplier.set_name(rs.getString("name"));
		supplier.setBankNumber(rs.getString("banknumber"));
		supplier.setPaymentMethod(rs.getInt("PAYMANETMETHOD"));
		ArrayList<SupplierProduct> products = _supplierProduct.search(new int[]{4}, new String[]{supplier.get_CN()});
		supplier.set_products(products);
		supplier.set_address(rs.getString("ADDRESS"));
		supplier.set_area(getArea(rs.getInt("SUPLLIERAREA")));
        try {
            supplier.set_Place(DB.getInstance().getPlaceDBHandler().get(supplier.get_address()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return supplier;
	}

	private Area getArea(int id)
	{
		Area area = null;
		for (Area ar: com.Transpotation.Transportation.getInstance().getAllAreas())
		{
			if(ar.getAreaID() == id)
				return ar;
		}
		return  area;
	}
	

class DAOContact extends DAO<Contact> {

	public DAOContact(Connection c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","TEL","NAME","EMAIL","SUPPLIERCN"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Telephone","Name","E-mail","Supplier Company Number"};
	}

	@Override
	public String getTable() {
		return "Contact";
	}

	@Override
	protected String[] getValues(Contact object) {
		return new String[]{"'"+object.getTel()+"'","'"+object.getName()+"'","'"+object.getEmail()+"'","'"+object.get_supplier_cn()+"'"};
	}

	@Override
	public Contact getFromPK(String[] values) throws SQLException {
		return search(new int[]{1}, values).get(0);
	}

	@Override
	public Contact create(ResultSet rs) throws SQLException {
		Contact contact = new Contact();
		contact.setEmail(rs.getString("Email"));
		contact.setName(rs.getString("name"));
		contact.setTel(rs.getString("tel"));
		contact.set_supplier_cn(rs.getString("SUPPLIERCN"));
		return contact;
	}

}

}
