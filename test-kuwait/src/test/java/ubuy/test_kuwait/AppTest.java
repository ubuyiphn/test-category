package ubuy.test_kuwait;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest 
{
    static JavascriptExecutor js;
    
    static WebDriver driver;
    
    static List<String> urls;
    
    static File console_output_file_path = new File(System.getProperty("user.dir")+"/Console-Output.txt");
    
    static PrintStream console_output_stream;
    
    static int category_elements_size;
    
    static int rndm;
    
    static boolean availability;
    
    static int round = 1;
	
	public static void main(String[] args) throws InterruptedException, IOException
    {
		console_output_file_path.createNewFile();
		
		console_output_stream = new PrintStream(new FileOutputStream(console_output_file_path));
		
        //System.setOut(console_output_stream);
		
		//System.setErr(console_output_stream);
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();
		
		//options.addArguments("headless");
	   
	    driver = new ChromeDriver(options);
	   
	    driver.manage().window().maximize();
	   
	    js = (JavascriptExecutor) driver;
	   
	    driver.get("https://test_abroad:!test$1234test!@test-abroad.ubuy.com.kw/ar/");
	   
	    Thread.sleep(1000);
	   
	    driver.findElement(By.xpath("//i[@class='fas fa-angle-down']")).click();
	   
	    Thread.sleep(5000);
	   
	    List<WebElement> category_elements  = driver.findElements(By.xpath("//a[starts-with(@href,'https://test-abroad.ubuy.com.kw/ar/category')]"));
      
	    category_elements_size = category_elements.size();
       
	    System.out.println(category_elements_size);
       
	    urls = new ArrayList<>();
       
	    for(WebElement category_element : category_elements)
        {
    	    String url = category_element.getAttribute("href");
    	  
    	    urls.add(url);
        }
	    
	    driver.close();
	    
       // driver.get(urls.get(0));
        
        int url_count_min = 1;
        
        int url_count_max = url_count_min + 30;
        
        while(url_count_min<=category_elements_size)
        {
            hit_category_urls(url_count_min,url_count_max);
            
            close_tabs();
            
            url_count_min  = url_count_max + 1;
            
            url_count_max = url_count_min + 30;
        }
        
       
    }
	
	public static int get_random_no()
	{
		int min = 1;
		
		int max = category_elements_size;
		
		rndm = (int) (Math.random() * (max - min + 1)) + min;
		
		return rndm;
	}
   
    public static void hit_category_urls(int url_count_min,int url_count_max)
    {	    
    	WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();
	
	    driver = new ChromeDriver(options);
	   
	    driver.manage().window().maximize();
	    
	    js = (JavascriptExecutor) driver;
	    
	    int tab_count = 1;
    	
	    try
	    {
    	while(url_count_min <= url_count_max)
	    {
	    	js.executeScript("window.open();");
			   
	        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
	    
	        String url = urls.get(url_count_min);
	        
	        String new_url = null;
	        
	        for(String temp : url.split("https://"))
	    	{
	    	    System.out.println(temp);
	    	    
	    	    new_url = "https://test_abroad:!test$1234test!@"+temp;
	    	}
	        
	        System.out.println("URL going to hit is "+new_url);
	   
	        driver.switchTo().window(tabs.get(tab_count));
	        
	        System.out.println(tabs.get(tab_count));
	   
	        driver.get(new_url);
	        
	        url_count_min++;
	        
	        tab_count++;
	    }
	    }
	    catch(Exception e)
	    {
	    	
	    }
    }
    
    public static Boolean verify_xpath(String x_path)
	{
		try
		{
			availability = driver.findElement(By.xpath(x_path)).isDisplayed();
			
			return availability;
		}
		
		catch(Exception e)
		{
			availability = false;
			
			return availability;
		}
	}
    
    public static void close_tabs() throws IOException 
	{
    	List<String> tabs = new ArrayList<>(driver.getWindowHandles());
    	
    	int tab_count = 0;
    	
    	int available_screenshot_count = 1;
    	
    	int not_available_screenshot_count = 1;
    	
    	while(tab_count<tabs.size())
    	{
    	
    	    driver.switchTo().window(tabs.get(tab_count));
    	    
    	    if(verify_xpath("//h3[@class='product-price mb-2']") == true)
    	    {
    	    	System.out.println("Result available on this URL "+driver.getCurrentUrl());
    	    	
                Screenshot.takescreenshot("av-"+available_screenshot_count);
    	    	
                available_screenshot_count++;
    	    }
    	    
    	    else
    	    {
    	    	System.out.println("Result not available on this URL "+driver.getCurrentUrl());
    	    	
    	    	Screenshot.takescreenshot("nav-"+not_available_screenshot_count);
    	    	
    	    	not_available_screenshot_count++;
    	    }
    	    driver.close();
    	    
    	    tab_count++;
    	}
    	
	}
}
