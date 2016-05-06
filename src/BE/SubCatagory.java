package BE;
import java.util.List;

public class SubCatagory {
	String name_cat;
	String name_scat;
	
	
	public SubCatagory(String name_cat, String name_scat) {
		this.name_cat = name_cat;
		this.name_scat = name_scat;
	}
	
	public String getName_cat() {
		return name_cat;
	}
	public void setName_cat(String name_cat) {
		this.name_cat = name_cat;
	}
	public String getName_scat() {
		return name_scat;
	}
	public void setName_scat(String name_scat) {
		this.name_scat = name_scat;
	}
	
	public String toString()
	{
		return "Catagory Name: " + name_cat + "Sub Catagory Name: " + name_scat ;
	}

}
