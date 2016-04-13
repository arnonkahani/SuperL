package BE;

public class Producer {
 
	public enum Search{
		ProducerName("Name");
		
		public final String columnName;       

	    private Search(String s) {
	    	columnName = s;
	    }
	}
}
