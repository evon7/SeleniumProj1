package edu.ucsc.extension;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Demo {
	
	@Test
	public void demo() {
		System.setProperty("webdriver.chrome.driver", "/Users/jatins/Downloads/chromedriver");
		
		WebDriver driver = new ChromeDriver();
		
		
		driver.get("http://www.google.com");
		
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("UCSC Extension");
		
		element.submit();
		
		Util.wait(10);

		driver.quit();
	}

}
