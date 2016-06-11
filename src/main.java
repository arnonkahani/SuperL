
import java.sql.SQLException;

import com.SupplierStorage.PL.ViewController;

public class main {

	
	
	public static void main(String[] args) {
		
		try {
			ViewController v = new ViewController();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
