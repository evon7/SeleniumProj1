package edu.ucsc.extension;

import org.apache.commons.io.FileUtils;
import java.util.List;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class MortgageCalculator {

	private static String outputFolder;

	@BeforeClass
	public static void before() {
		outputFolder = System.getProperty("tmp.dir") + File.separator + "tmp";
		System.out.println("OUTPUT DIR: " + outputFolder);
	}

	@Test
	public void testMortgageCalculator() {
		WebDriver driver = new FirefoxDriver();

		// Chrome Driver. Not using because it won't take a full page picture
		 //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Yvonne\\chromedriver_win32\\chromedriver.exe");
		 //WebDriver driver = new ChromeDriver();

		driver.get("http://www.mortgagecalculator.org/");

		// Wait 5s for the page to load, and then take a pic
		Util.wait(5);
		
		File scrFileBefore = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFileBefore, new File(outputFolder + File.separator + "before.png"));
		} catch (Exception e) {
		}

		// *** Text Box ***
		WebElement homeValTxt = driver.findElement(By.name("param[homevalue]"));
		WebElement loanAmtTxt = driver.findElement(By.id("loanamt"));
		WebElement intRatTxt = driver.findElement(By.id("intrstsrate"));

		homeValTxt.clear();
		loanAmtTxt.clear();
		intRatTxt.clear();

		homeValTxt.sendKeys("600000");
		loanAmtTxt.sendKeys("500000");
		intRatTxt.sendKeys("5");
		// loanTrmTxt.sendKeys("30");

		// ***Drop Down Menu ***
		Select startMnthDrp = new Select(driver.findElement(By.name("param[start_month]")));
		Select startYearDrp = new Select(driver.findElement(By.name("param[start_year]")));

		startMnthDrp.selectByVisibleText("Jan");
		startYearDrp.selectByVisibleText("2016");

		// *** Click that link ***
		//driver.findElement(By.partialLinkText("»")).click(); // chrome and firefox ok
		 driver.findElement(By.partialLinkText("Output")).click(); //chrome and firefox ok
		// driver.findElement(By.className("output-param")).click(); //works in chrome only...???
		// driver.findElement(By.cssSelector(".output-param")).click();//works in chrome only...?

		// Check Boxes
		if (driver.findElement(By.name("param[draw_charts]")).isSelected())
			driver.findElement(By.name("param[draw_charts]")).click();

		if (!driver.findElement(By.name("param[show_m_vs_w]")).isSelected())
			driver.findElement(By.name("param[show_m_vs_w]")).click();

		if (!driver.findElement(By.name("param[show_annual]")).isSelected())
			driver.findElement(By.name("param[show_annual]")).click();

		if (!driver.findElement(By.name("param[show_monthly]")).isSelected())
			driver.findElement(By.name("param[show_monthly]")).click();

		// Click that Cal Button
		driver.findElement(By.name("cal")).click();

		Util.wait(10);
		// Great. Now take a picture before we leave
		File scrFileAfter = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFileAfter, new File(outputFolder + File.separator + "after.png"));

		} catch (Exception e) {
		}
		
		//Mortgage Repayment Summary
		List<WebElement> leftCellElem = driver.findElements(By.xpath("//div[@class='repayment-block']/div[@class='rw-box']/div[@class='left-cell']/h3"));
		List<WebElement> leftTextElem = driver.findElements(By.xpath("//div[@class='repayment-block']/div[@class='rw-box']/div[@class='left-cell']/span"));

		List<WebElement> rightCellElem = driver.findElements(By.xpath("//div[@class='repayment-block']/div[@class='rw-box']/div[@class='right-cell']/h3"));
		List<WebElement> rightTextElem = driver.findElements(By.xpath("//div[@class='repayment-block']/div[@class='rw-box']/div[@class='right-cell']/span"));

		for(int i=0; i<leftCellElem.size(); i++){
			System.out.println(leftTextElem.get(i).getText() + " : " + leftCellElem.get(i).getText());
			System.out.println(rightTextElem.get(i).getText() + " : " + rightCellElem.get(i).getText());
		}
		
		//Monthly vs Bi-weekly Payment
		List<WebElement> leftCellElem2 = driver.findElements(By.xpath("//div[@class='repayment-block biweekly-outer']/div[@class='rw-box']/div[@class='left-cell']/h3"));
		List<WebElement> leftTextElem2 = driver.findElements(By.xpath("//div[@class='repayment-block biweekly-outer']/div[@class='rw-box']/div[@class='left-cell']/span"));

		List<WebElement> rightCellElem2 = driver.findElements(By.xpath("//div[@class='repayment-block biweekly-outer']/div[@class='rw-box']/div[@class='right-cell']/h3"));
		List<WebElement> rightTextElem2 = driver.findElements(By.xpath("//div[@class='repayment-block biweekly-outer']/div[@class='rw-box']/div[@class='right-cell']/span"));

		for(int j=0; j<leftCellElem2.size(); j++){
			System.out.println(leftTextElem.get(j).getText() + " : " + leftCellElem2.get(j).getText());
			System.out.println(rightTextElem.get(j).getText() + " : " + rightCellElem2.get(j).getText());
		}
		Util.wait(10);
		driver.quit();
	}

}
