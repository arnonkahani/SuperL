package BE;
import java.util.Calendar;
import java.util.Date;

public class INS_product extends Product {
	
	private int serial_num;
	private int id;
	private Date valid_date;
	private int defected;
	
	
	public INS_product() {
		super();
	}


	public INS_product(int defected) {
		this.serial_num=0;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, get_shelf_life());
        this.valid_date = c.getTime();
        this.defected = defected;
	}
	
	public INS_product(int serial_num,int _id,String _catagory, String _sub_catagory, String _sub_sub_category ,String _name,Producer _producer,float _weight,float _price, int defected) {
		this.serial_num=serial_num;
		this.id = _id;
		this._catagory = _catagory;
		this._sub_catagory = _sub_catagory;
		this._sub_sub_category = _sub_sub_category;
		this._name = _name;
		this._producer = _producer;
		this._weight = _weight;
		this._price = _price;
		Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, get_shelf_life());
        this.valid_date = c.getTime();
		this.defected = defected;
	}
	public int getSerial_num() {
		return serial_num;
	}
	public void setSerial_num(int serial_num) {
		this.serial_num = serial_num;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getValid_date() {
		return valid_date;
	}
	public void setValid_date(Date valid_date) {
		this.valid_date = valid_date;
	}
	public int isDefected() {
		return defected;
	}

	public void setDefected(int defected) {
		this.defected = defected;
	}
	
}
