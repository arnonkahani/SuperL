package BE;
import java.util.Date;

public class IssueCertificate {
	private int s_id;
	private Date s_date;
	private int s_p_id;
	private int s_serial_num;
	private String s_cat;
	private String s_sub_cat;
	private String s_sub_sub_cat;
	private String s_name;
	private String s_producer;
	private String s_valid_date;
	
	
	public IssueCertificate() {
		super();
	}
	
	
	public IssueCertificate(Date s_date, int s_p_id, int s_serial_num, String s_cat, String s_sub_cat,
			String s_sub_sub_cat, String s_name, String s_producer, double s_price, String s_valid_date) {
		this.s_date = s_date;
		this.s_p_id = s_p_id;
		this.s_serial_num = s_serial_num;
		this.s_cat = s_cat;
		this.s_sub_cat = s_sub_cat;
		this.s_sub_sub_cat = s_sub_sub_cat;
		this.s_name = s_name;
		this.s_producer = s_producer;
		this.s_valid_date = s_valid_date;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public Date getS_date() {
		return s_date;
	}
	public void setS_date(Date s_date) {
		this.s_date = s_date;
	}
	public int getS_p_id() {
		return s_p_id;
	}
	public void setS_p_id(int s_p_id) {
		this.s_p_id = s_p_id;
	}
	public int getS_serial_num() {
		return s_serial_num;
	}
	public void setS_serial_num(int s_serial_num) {
		this.s_serial_num = s_serial_num;
	}
	public String getS_cat() {
		return s_cat;
	}
	public void setS_cat(String s_cat) {
		this.s_cat = s_cat;
	}
	public String getS_sub_cat() {
		return s_sub_cat;
	}
	public void setS_sub_cat(String s_sub_cat) {
		this.s_sub_cat = s_sub_cat;
	}
	public String getS_sub_sub_cat() {
		return s_sub_sub_cat;
	}
	public void setS_sub_sub_cat(String s_sub_sub_cat) {
		this.s_sub_sub_cat = s_sub_sub_cat;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getS_producer() {
		return s_producer;
	}
	public void setS_producer(String s_producer) {
		this.s_producer = s_producer;
	}

	public String getS_valid_date() {
		return s_valid_date;
	}
	public void setS_valid_date(String s_valid_date) {
		this.s_valid_date = s_valid_date;
	}
	
	public String toString(){
		return "Id: "  + s_id + " Date: " + getS_date() + 
				" Product Id : " + s_p_id + " Serial Number: " + s_serial_num+
				"Catagory : " + s_cat + "Sub Catagory: " + s_sub_cat+
				" Sub Sub Catagory : " + s_sub_sub_cat + " Name: " + s_name+
				" Producer : " + s_producer + "Valid Date" + s_valid_date;
	}
	
	
	

}
