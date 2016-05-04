package BE;

public class Producer{
	private String name;
	"hi ziv";
	
	public Producer(String Name)
	{
		name = Name;
	}
	
	public String toString()
	{
		return "Producer: " + name;
	}

	public String getName() {
		return name;
	}
	
	public boolean equals(Object o)
	{
		return o.toString().toUpperCase().equals(toString().toUpperCase());
	}


}
