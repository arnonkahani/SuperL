
import java.sql.SQLException;

import BE.Supplier;
import BL.BLFactory;
import DB.DAOFactory;
import PL.viewController;

public class main {

	public static void main(String[] args) {
		
		try {
			viewController v = new viewController();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
