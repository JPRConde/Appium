package com.sample;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class NewTest {
	
	//Create instance for appium driver
	AppiumDriver<MobileElement> driver;
	
	
	@BeforeClass
	public void Setup() throws MalformedURLException {
		File f=new File("src");
		File fs=new File(f,"app-ph-qa-release.apk");
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "co.tala.MainActivity");
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "ph.com.talaqa");
		
		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),cap);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void TC001_LogInScreen() {
		//Check app is loading or not
		Assert.assertNotNull(driver.getContext());
		driver.findElement(By.id("get_started_button")).isDisplayed();
		driver.findElement(By.id("learn_more_button")).isDisplayed();	
		driver.findElement(By.id("sign_in_button")).isDisplayed();
		driver.findElement(By.id("tala_logo")).isDisplayed();
		driver.findElement(By.id("primary_text")).isDisplayed();
   }
	
	@Test
	public void TC002_SignUp() {
		driver.findElement(By.id("get_started_button")).click();
		driver.findElement(By.id("phone_number_input")).sendKeys("09399038386");
		driver.findElement(By.id("submit_button")).isEnabled();
		driver.findElement(By.id("submit_button")).click();
		driver.closeApp();
   }
	
	@Test
	public void TC003_SignIn() {
		driver.launchApp();
		driver.findElement(By.id("sign_in_button")).click();
		driver.findElement(By.id("number_edit_text")).sendKeys("09399038386");
		driver.closeApp();
  }
	
	@Test
	public void TC004_LearnMore() {
		driver.launchApp();
		driver.findElement(By.id("learn_more_button")).click();
  }
	
}
