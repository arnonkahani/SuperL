package BE;

public class Contact {

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
	
	
}
