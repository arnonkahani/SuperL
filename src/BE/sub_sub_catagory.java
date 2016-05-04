package BE;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class sub_sub_catagory {
	String name_sscat;
	String name_scat;
	
	
	public sub_sub_catagory(String name_sscat, String name_scat) {
		this.name_sscat = name_sscat;
		this.name_scat = name_scat;
		
	}


	public String getName_sscat() {
		return name_sscat;
	}


	public void setName_sscat(String name_sscat) {
		this.name_sscat = name_sscat;
	}


	public String getName_scat() {
		return name_scat;
	}


	public void setName_scat(String name_scat) {
		this.name_scat = name_scat;
	}
	
	
}



