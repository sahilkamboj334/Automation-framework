package com.framework.webdriver.mobile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.framework.logger.LoggerUtil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class AndroidDriverProvider extends MobileDriverProvider{
	private static AndroidDriverProvider DriverProvider = new AndroidDriverProvider();
	public static AndroidDriverProvider instance() {
		return DriverProvider;
	}
	
	
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.APP,mobConfig.getAppPath());
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,mobConfig.getAppActivity());
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,mobConfig.getAppPackage());
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, mobConfig.getDeviceName());
		desiredCapabilities.setCapability(MobileCapabilityType.UDID, mobConfig.getUdid());
		desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
//		desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UIAutomator2");
		return desiredCapabilities;
	}


	public WebDriver getDriver() {
		this.startServer();
		AndroidDriver<WebElement> androidDriver=null;
		try {
			waitUntilAppiumIsRunning(10, this.url());
			Thread.sleep(5000);
			androidDriver=new AndroidDriver<>(this.getAppiumUrl(), this.getCapabilities());
		} catch (Exception e) {
			LoggerUtil.logError("Exception occurrred!!!"+e.getMessage());
			LoggerUtil.logInfo("Trying to create driver again.......");
			try {		
				waitUntilAppiumIsRunning(50, this.url());
				androidDriver=new AndroidDriver<>(this.getAppiumUrl(), this.getCapabilities());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return androidDriver;
	}

}
