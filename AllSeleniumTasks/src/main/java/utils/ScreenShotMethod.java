package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import base.BaseClass;;

public class ScreenShotMethod extends BaseClass {
	

	public static String takeScreenshotAtEndOfTest(String methodName) throws Exception {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
	    String destination = System.getProperty("user.dir") + "\\target\\" + methodName + timestamp + ".png";
	            
	    File finalDestination = new File(destination);
	    FileHandler.copy(src, finalDestination);
	    return destination;
	}

}