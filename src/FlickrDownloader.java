package edu.ucsc.extension;

import java.io.File;
import org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.List;

//import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//import static org.apache.commons.io.FileUtils.*;

public class FlickrDownloader {

	private static String outputFolder;

	@BeforeClass
	public static void before() {
		outputFolder = System.getProperty("tmp.dir") + File.separator + "tmp";
		System.out.println("OUTPUT DIR: " + outputFolder);
	}

	@Test
	public void flickrDownloader() {
		WebDriver driver = new FirefoxDriver();
		// System.setProperty("webdriver.chrome.driver",
		// "C:\\Users\\Yvonne\\chromedriver_win32\\chromedriver.exe");
		// WebDriver driver = new ChromeDriver();

		driver.get("http://www.flickr.com/explore");
		Util.wait(10);

		// List<WebElement> links =
		// driver.findElements(By.partialLinkText("href"));
		List<WebElement> links = driver.findElements(By.cssSelector("a.overlay"));

		List<String> listOfLinks = new ArrayList<String>();

		for (WebElement link : links) {
			String url = link.getAttribute("href");
			listOfLinks.add(url);
		}
		 System.out.println(listOfLinks);
		
		File outDir = new File(outputFolder);
		System.out.println(outDir);

		for (int i = 0; i <listOfLinks.size(); i++) {
			
			driver.navigate().to(listOfLinks.get(i));
			Util.wait(10);

			WebElement mainPhoto = driver.findElement(By.className("main-photo"));
			String fileString = mainPhoto.getAttribute("src");
			System.out.println(fileString);

			URL fileURL = null;
			try {
				fileURL = new URL(fileString);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String newFileName = "flickr_pic_"+i+".png";
			System.out.println(newFileName);

			try {
				FileUtils.copyURLToFile(fileURL, new File(outputFolder + File.separator + newFileName));
				//Util.wait(10);
			} catch (IOException e) {
				System.out.println("copying fail");
			}
		}
		Util.wait(10);
		driver.quit();
	}
}
