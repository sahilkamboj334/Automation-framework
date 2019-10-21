package com.framework.webdriver.mobile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.framework.logger.LoggerUtil;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class IOSDriverProvider extends MobileDriverProvider{
	private static IOSDriverProvider driverProvider = new IOSDriverProvider();

	public static IOSDriverProvider getInstance() {
		return driverProvider;
	}
	
	
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.APP,mobConfig.getAppPath());
		desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID,mobConfig.getBundleId());
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, mobConfig.getDeviceName());
		desiredCapabilities.setCapability(MobileCapabilityType.UDID, mobConfig.getUdid());
		desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
		desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"XCUITest");
		return desiredCapabilities;
	}


	public WebDriver getDriver() {
		this.startServer();
		IOSDriver<WebElement> iosDriver=null;
		try {
			waitUntilAppiumIsRunning(10, this.url());
			Thread.sleep(5000);
			iosDriver=new IOSDriver<>(this.getAppiumUrl(), this.getCapabilities());
		} catch (Exception e) {
			LoggerUtil.logError("Exception occurrred!!!"+e.getMessage());
			LoggerUtil.logInfo("Trying to create driver again.......");
			try {		
				waitUntilAppiumIsRunning(50, this.url());
				iosDriver=new IOSDriver<>(this.getAppiumUrl(), this.getCapabilities());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return iosDriver;
	}
}
