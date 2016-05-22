package BE;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Contact{
	

	private String _supplier_cn;
	
	private String _tel;
	private String _email;
	private String _name;
	
	public Contact(String tel,String email,String name)
	{
		_tel = tel;
		_email = email;
		_name = name;
	}
	
	
	public Contact(Contact contact) {
		this._tel = contact._tel;
		this._name = contact._name;
		this._email = contact._email;
	}


	public Contact() {
		// TODO Auto-generated constructor stub
	}

	
	public String getTel() {
		return _tel;
	}
	public void setTel(String _tel) {
		this._tel = _tel;
	}
	public String getEmail() {
		return _email;
	}
	public void setEmail(String _email) {
		this._email = _email;
	}
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}




	public String toString(){
		return "Tel: " + _tel + " Name: " + _name + " Email: " + _email + " Supplier: " + _supplier_cn; 
		
	}
	
	public boolean equals(Object o){
		if(o.getClass().equals(Contact.class))
		{
			return o.toString().toUpperCase().equals(toString().toUpperCase());
		}
		else
			return false;
	}


	public String get_supplier_cn() {
		return _supplier_cn;
	}


	public void set_supplier_cn(String _supplier_cn) {
		this._supplier_cn = _supplier_cn;
	}
	
	
}
