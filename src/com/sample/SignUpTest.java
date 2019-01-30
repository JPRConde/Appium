package com.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType; 

public class SignUpTest {
	
	//Enter New phone number
	String phoneNumber = "9897654321";
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
	
	@BeforeMethod
	public void ResetApp() {
		driver.resetApp();	
	}
	
	@Test
	public void SignUp() throws Exception {
		Assert.assertNotNull(driver.getContext());
		driver.findElement(By.id("get_started_button")).click();
		driver.findElement(By.id("phone_number_input")).sendKeys(phoneNumber);
		driver.findElement(By.id("submit_button")).isEnabled();
		driver.findElement(By.id("submit_button")).click();
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otp_layout"))); 
		
		String url = "http://172.30.13.118:8090/utility/v2/otp/to/63" + phoneNumber + "/template/110";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        //optional default is GET
        con.setRequestMethod("GET");
        
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL: " + url);
        System.out.println("Response Code: " + responseCode);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
        }
        in.close();
        
        //print Response in String
        System.out.println(response.toString());
        
        //Read "message:" JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("Result after Reading JSON Response");
        System.out.println("Message -- "+myResponse.getString("message"));
        
        //Extract number from string
        String OTP = myResponse.getString("message");
        OTP = OTP.replaceAll("\\D+","");
        System.out.println("OTP: " + OTP);
	
		//Enter OTP Key 1
		char c = OTP.charAt(0);
		String key = new StringBuilder().append(c).toString();
		driver.findElement(By.id("ph.com.talastg:id/first_digit_edit_text")).sendKeys(key);
		
		//Enter OTP Key 2
		c = OTP.charAt(1);
		key = new StringBuilder().append(c).toString();
		driver.findElement(By.id("ph.com.talastg:id/second_digit_edit_text")).sendKeys(key);
		
		//Enter OTP Key 3
		c = OTP.charAt(2);
		key = new StringBuilder().append(c).toString();
		driver.findElement(By.id("ph.com.talastg:id/third_digit_edit_text")).sendKeys(key);
		
		//Enter OTP Key 4
		c = OTP.charAt(3);
		key = new StringBuilder().append(c).toString();
		driver.findElement(By.id("ph.com.talastg:id/fourth_digit_edit_text")).sendKeys(key);
	}
}