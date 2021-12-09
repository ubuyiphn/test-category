package ubuy.test_kuwait;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


public class Screenshot 
{ 
	static String screenshotpath;

	public static void takescreenshot(String name) throws IOException
    {
		screenshotpath = System.getProperty("user.dir")+"/"+name+".png";
		
		TakesScreenshot scrnshot = ((TakesScreenshot) AppTest.driver);
    
    	File SrcFile = scrnshot.getScreenshotAs(OutputType.FILE);
    	
    	File DestFile = new File(screenshotpath);
    	
    	FileUtils.copyFile(SrcFile, DestFile);
    }
   
}


