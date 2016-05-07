package BE;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class INS_product extends Product {
	
	private int serial_num;
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
	
	public INS_product(int _id,String _catagory, String _sub_catagory, String _sub_sub_category ,String _name,Producer _producer,float _weight,float _price, int defected,int shelf_life) {
		super._id = _id;
		super._catagory = _catagory;
		super._sub_catagory = _sub_catagory;
		super._sub_sub_category = _sub_sub_category;
		super._name = _name;
		super._producer = _producer;
		super._weight = _weight;
		super._price = _price;
		Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, shelf_life);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String x= format1.format(c.getTime());
        this.valid_date = stringConverDate(format1.format(c.getTime()));
		this.defected = defected;
	}
	
	public INS_product(Product product,Date valid,int serial_num, int defected) {
		super._id = product.get_id();
		super._catagory = product.get_category();
		super._sub_catagory = product.get_sub_category();
		super._sub_sub_category = product.get_sub_sub_category();
		super._name = product.get_name();
		super._producer = product.get_producer();
		super._weight = product.get_weight();
		super._price = product.get_price();
        this.serial_num=serial_num;
        this.valid_date = valid;
		this.defected = defected;
	}
	
	public int getSerial_num() {
		return serial_num;
	}
	public void setSerial_num(int serial_num) {
		this.serial_num = serial_num;
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
	public String toString(){
		return "Serial Number: "  + serial_num + 
				" valid_date : " + getValid_date() + " Defected: " + defected;
	}
	
	public Date stringConverDate(String date)
	{
		Date date1 = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date1 = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return date1;
	}


	public String dateConvert(Date date)
	{
		String date1 = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		date1 = df.format(date);
	return ""+date1+"";
	}

}
	
