package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Report;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;



public class BaseClass {
	public static WebDriver driver;
	public static ExtentReports extent;
	public static ExtentSparkReporter spark;
	public static ExtentTest test;
	static String add1=System.getProperty("user.dir")+"//MyOwnExtentReport.html";

	
	@BeforeClass
	public static void getReport()
	{
		extent=new ExtentReports();
		spark = new ExtentSparkReporter(add1);//it is an observer
		spark.config().setDocumentTitle("SeleniumPractiseTests");
		spark.config().setReportName("Testing Methods from my Project");
		spark.config().setTheme(Theme.STANDARD);
		spark.config().setEncoding("utf-8");
		extent.attachReporter(spark);
		extent.setSystemInfo("Company Name", "AQM Technelogies");
		extent.setSystemInfo("Project Name", "MultipleTasks");
		extent.setSystemInfo("Tester Name", "Vaibhav Pawde");
		test = extent.createTest("SeleniumTests");
		
	}
	@BeforeTest
	public static WebDriver WebDriverManager1() throws Exception  {
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//Configfiles//config.properties");
		Properties prop = new Properties();
		prop.load(fis);
		
		String url = prop.getProperty("url");
		
		if(driver == null) {
			if(prop.getProperty("browser").equalsIgnoreCase("chrome")) {
//				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//test//resources//chromedriver.exe");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				
			}
			
			else if(prop.getProperty("browser").equalsIgnoreCase("firefox")) {
				//Firefox code
			}
			
			else if(prop.getProperty("browser").equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
			
			else if(prop.getProperty("browser").equalsIgnoreCase("safari")) {
				//Safari code
			}
		driver.get(url);
//		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		test.log(Status.INFO, "Browser Started Successfully");
		}
		return driver;
	}
	
	@AfterMethod
	public void ScreenShot(ITestResult result) throws Exception {
		
		if(result.getStatus() == ITestResult.FAILURE) {	
			String screenshotPath = utils.ScreenShotMethod.takeScreenshotAtEndOfTest(result.getName());
			test.log(Status.FAIL, result.getName()+" Method is failed");
			test.fail("Test Case failed, check screenshot below ", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
//			test.fail("Test Case failed, check screenshot below "+test.addScreenCaptureFromPath(screenshotPath));
		}
		else if(result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, result.getName()+" Method is Passed");
		}
	
	}
	
	
	
	@AfterTest
	public void afterTest() {
		extent.flush();
		driver.close();
		driver.quit();
	}

}

