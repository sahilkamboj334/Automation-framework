package com.framework.utill;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtil {
	static File a = new File("./Screenshots");
public static String takeScreenShot(WebDriver driver,String name) {
		new WebDriverWait(driver, 10).until(WebDriver->((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File image=null;
		if (!a.exists())
			a.mkdir();
		try {
			image=new File(a.getAbsolutePath()+ "/"+name + ".png");
			FileUtils.copyFile(src,image); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image.getAbsolutePath();
	}

public static String getHostName(){
	String host=null;
	try {
		host= InetAddress.getLocalHost().getHostName();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return host;
}
public static void getScreenShotUsingRobot(String name){
	Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
	int height=(int)screen.getHeight();
	int width=(int)screen.getWidth();
	Robot robot=null;
	try {
		robot=new Robot();
	} catch (AWTException e) {
		e.printStackTrace();
	}
	BufferedImage image=robot.createScreenCapture(new Rectangle(width, height));
	try {
		ImageIO.write(image, "png",new File(a.getAbsolutePath()+"/"+name+".png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
}
