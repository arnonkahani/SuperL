
import java.sql.SQLException;

import BE.Supplier;
import BL.BLFactory;
import DB.DAOFactory;
import PL.ViewController;

public class main {

	
	
	public static void main(String[] args) {
		
		try {
			ViewController v = new ViewController();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
