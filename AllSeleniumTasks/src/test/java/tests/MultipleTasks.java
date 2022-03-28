package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.mysql.cj.x.protobuf.MysqlxExpect.Open.Condition.Key;

import base.BaseClass;;

public class MultipleTasks extends BaseClass {
	
	@Test(priority = 0,enabled=true)
	public void DragAndDrop() {
		WebElement frame = driver.findElement(By.xpath("//iframe[@src='/resources/demos/droppable/default.html']"));
		driver.switchTo().frame(frame);
		
		WebElement drag = driver.findElement(By.id("draggable"));
		WebElement drop = driver.findElement(By.id("droppable"));
		Actions action = new Actions(driver);
		action.dragAndDrop(drag, drop);
		action.build().perform();
		
	}
	@Test(priority = 1,enabled=true)
	public void Scrooling() {
//		driver.switchTo().defaultContent();
//		WebDriverWait wait = new WebDriverWait(driver, 30);	
		WebElement ele = driver.findElement(By.xpath("//*[contains(text(),'Widget Factory')]"));
//		wait.until(ExpectedConditions.invisibilityOf(ele));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView()",ele);
	}
	
	
	@Test(priority = 2,enabled=true)
	public void ActionsTask() {
		driver.switchTo().defaultContent();
		Actions action = new Actions(driver);
		WebElement ele = driver.findElement(By.name("s"));
		Action a = action.moveToElement(ele)
				.keyDown(Keys.SHIFT)
				.sendKeys("java")
				.keyUp(Keys.SHIFT)
				.doubleClick(ele)
				.contextClick()
				.build();
		a.perform();
	}
	
	@Test(priority = 3,enabled=true)
	public void AlertTask() {
		driver.get("https://demoqa.com/alerts");
		driver.findElement(By.id("confirmButton")).click();
		driver.switchTo().alert().dismiss();
		driver.findElement(By.id("promtButton")).click();
		Alert promptAlert  = driver.switchTo().alert();
		String text =promptAlert.getText();
		System.out.println(text);
		promptAlert.sendKeys("Pawde");
		promptAlert.accept();
		
	}

	@Test(priority = 5,enabled=true)
	public void ExcelTask() throws Exception {
		FileInputStream file = new FileInputStream("D:\\Users\\Temp\\Desktop\\Exceltest.xlsx");
		XSSFWorkbook workbook =new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		double pin = sheet.getRow(0).getCell(1).getNumericCellValue();
		int pin1 =(int) pin;
		driver.get("https://www.google.com/");
		driver.findElement(By.name("q")).sendKeys(String.valueOf(pin1));
		sheet.createRow(2).createCell(2).setCellValue("New Value1");
		workbook.write(new FileOutputStream("D:\\Users\\Temp\\Desktop\\Exceltest.xlsx"));
		workbook.close();
			
	}
	@Test(priority = 4,enabled=true)
	public void DropDown() {
		driver.get("https://demoqa.com/select-menu");
		driver.manage().window().maximize();
		Select sel =new Select(driver.findElement(By.id("oldSelectMenu")));
		sel.selectByVisibleText("Purple");
		sel.selectByIndex(2);
		WebElement option =sel.getFirstSelectedOption();
		System.out.println("First selected Option is "+option.getText());
		
		List <WebElement>list=sel.getAllSelectedOptions();
		for (WebElement list1 : list)
			System.out.println(list1.getText());
	}
	

	@Test(priority = 6,enabled=false)
	public void WindowsHandleTask() {
		driver.get("https://www.selenium.dev/");
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).build().perform();
		driver.findElement(By.xpath("//span[contains(text(),'Documentation')]")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Downloads')]")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Projects')]")).click();
		action.keyUp(Keys.CONTROL).build().perform();
		//It will provide parent window id
		String pid = driver.getWindowHandle();
		System.out.println("Parent id "+pid);
		//It will provide all ids of available window
		Set <String> allWin  = driver.getWindowHandles();
		//Convert set value to array
//		ArrayList<String> ids = new ArrayList<String>(allWin);
		//Id at first index will be same as parent id
//		System.out.println("Id at 0th index "+ids.get(0));
//		System.out.println("Id at 1st index "+ids.get(1));
		//Switching to next window
//		driver.switchTo().window(ids.get(1));
//		System.out.println(driver.getTitle());
		
		//Depends on title checking
		for(String id : allWin) {
			if(!id.equals(pid)) {
				driver.switchTo().window(id);
				System.out.println("Window Ids are - "+id);
				if(driver.getTitle().contains("Downloads")) {
					System.out.println(driver.getTitle());
				}
				driver.close();
			}
		}
		
	}
	
	
	
	@Test(priority = 2,enabled=false)
	public void SoftAssertTest() {
		SoftAssert sa = new SoftAssert();
		String title = driver.getTitle();
		sa.assertEquals(title, "Droppable");
		
		System.out.println("Checking soft assert");
		sa.assertAll();
		
	}

	
	
}
