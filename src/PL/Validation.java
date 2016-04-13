package PL;

public class Validation {

	
	public boolean isPosFloat(float f)
	{
		return f>0;
	}
	
	public boolean isEmail(String email)
	{
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(EMAIL_REGEX);
	}
	
	public boolean isOnlyInt(String num)
	{
		return num.matches("[0-9]+");
	}
	
	public boolean isPosInt(int i)
	{
		return i>=0;
	}
}
