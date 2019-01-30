package com.sample;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class Setup {
	
		//Enter New phone number
		String phoneNumber = "9399038386";
		//Enter Registered phone number
		String phoneNumberReg = "9277756840";
		static //APK build to test
		File testAPK = new File("src","app-ph-stg-debug.apk");
		
		//Create instance for appium driver
		static AppiumDriver<AndroidElement> driver;

	public static void main(String[] args) throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		cap.setCapability(MobileCapabilityType.APP, testAPK.getAbsolutePath());
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "co.tala.MainActivity");
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "ph.com.talastg");
		driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),cap);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	
		
	}
}
