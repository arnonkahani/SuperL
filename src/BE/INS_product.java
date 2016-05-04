package BE;
import java.util.Date;

public class INS_product {
	
	int serial_num;
	int id;
	Date valid_date;
	int defected;
	
	
	
	
	
	public INS_product() {
		super();
	}


	public INS_product(int id, Date valid_date, int defected) {
		this.serial_num=0;
		this.id = id;
		this.valid_date = valid_date;
		this.defected = defected;
	}
	
	public INS_product(int serial_num,int id, Date valid_date, int defected) {
		this.serial_num=serial_num;
		this.id = id;
		this.valid_date = valid_date;
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
