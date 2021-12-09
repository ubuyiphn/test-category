package ubuy.test_kuwait;

public class test 
{
    public static void main(String[] args)
    {
    	String s = "https://test_abroad:!test$1234test!@test-abroad.ubuy.com.kw/ar/";
    	
    	for(String temp : s.split("https://"))
    	{
    	    System.out.println(temp);
    	}
    }
}
