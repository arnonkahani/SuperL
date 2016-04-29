package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



import BE.Producer;

public class DAOProducer extends DAO<Producer> {

	public DAOProducer(Connection c) {
		super(c);
	}


	@Override
	public String[] getSearchFields() {
		return new String[]{"All","NAME"};
	}

	@Override
	public String getTable() {
		return "PRODUCER";
	}


	@Override
	public Producer getFromPK(String[] values) throws SQLException{
		return search(new int[]{1}, values).get(0);
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Name"};
	}

	@Override
	public Producer create(ResultSet rs) throws SQLException {
		Producer producer = new Producer(rs.getString("Name"));
		return producer;
	}


	@Override
	protected String[] getValues(Producer object) {
			return new String[]{"'"+object.getName()+"'"};
	}

}
