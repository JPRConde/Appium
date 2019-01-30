package com.sample;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;

public class SignInTest {
	
	//Enter Registered phone number
	String phoneNumberReg = "9277756840";
	//APK build to test
	File testAPK = new File("src","app-ph-stg-debug.apk");
	
	//Create instance for appium driver
	AppiumDriver<AndroidElement> driver;
	
	@BeforeClass
	public void Setup() throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		cap.setCapability(MobileCapabilityType.APP, testAPK.getAbsolutePath());
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "co.tala.MainActivity");
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "ph.com.talastg");
		driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),cap);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	
	}
	
	@AfterMethod
	public void ResetApp() {
		driver.resetApp();	
	}

	@Test
	public void LogInPage() {
		//Check app is loading or not
		Assert.assertNotNull(driver.getContext());
		driver.findElement(By.id("get_started_button")).isDisplayed();
		driver.findElement(By.id("learn_more_button")).isDisplayed();	
		driver.findElement(By.id("sign_in_button")).isDisplayed();
		driver.findElement(By.id("tala_logo")).isDisplayed();
		driver.findElement(By.id("primary_text")).isDisplayed();
		
   }
	
	@Test
	public void LearnMore() {
		driver.findElement(By.id("learn_more_button")).click();
		//Check page elements
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("get_started_button"))); 
		driver.findElement(By.id("get_started_button")).click();
   }
	
	@Test
	public void SignIn() {
		driver.findElement(By.id("sign_in_button")).click();
		driver.findElement(By.id("number_edit_text")).click();
		driver.findElement(By.id("number_edit_text")).sendKeys(phoneNumberReg);
		driver.findElement(By.id("next_button")).isEnabled();
		driver.findElement(By.id("next_button")).click();
		
		//Entering PIN
		TouchAction t = new TouchAction(driver);
		
		//Coordinates of Key 1
		int pointX = 144;
		int pointY = 1204;
		
		driver.findElement(By.id("first_digit_edit_text")).click();
		driver.findElement(By.id("first_digit_edit_text")).isSelected();
		t.tap(new PointOption().withCoordinates(pointX, pointY)).perform();
		driver.findElement(By.id("second_digit_edit_text")).isSelected();
		t.tap(new PointOption().withCoordinates(pointX, pointY)).perform();
		driver.findElement(By.id("third_digit_edit_text")).isSelected();
		t.tap(new PointOption().withCoordinates(pointX, pointY)).perform();
		driver.findElement(By.id("fourth_digit_edit_text")).isSelected();
		t.tap(new PointOption().withCoordinates(pointX, pointY)).perform();
	
		driver.findElement(By.id("next_button")).isEnabled();
		driver.findElement(By.id("next_button")).click();
		driver.findElement(By.id("allow_button")).click();
		driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
		driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
		driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
		driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
  }	
}
