package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import BE.Contact;

public class DAOContact extends DAO<Contact> {

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
		return new String[]{"'"+object.getTel()+"'","'"+object.getName()+"'","'"+object.getEmail()+"'","'"+object.get_supplier().get_CN()+"'"};
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
		return contact;
	}

}
