package BE;

public class Producer implements DBEntity{
	private String name;
	
	
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

	@Override
	public String[] getValues() {
		return new String[]{"'"+name+"'"};
	}
}
