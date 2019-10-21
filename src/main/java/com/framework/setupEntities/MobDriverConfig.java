package com.framework.setupEntities;

public class MobDriverConfig {
private String deviceName;
private String platformName;
private String platformVersion;
private String appPath;
private String appPackage;
private String appActivity;
private String serverURL;
private String bundleId;
private String udid;
private String ip;
private int port;
public String getIp() {
	return ip;
}
public void setIp(String ip) {
	this.ip = ip;
}
public int getPort() {
	return port;
}
public void setPort(String port) {
	this.port = Integer.valueOf(port);
}
public String getDeviceName() {
	return deviceName;
}
public void setDeviceName(String deviceName) {
	this.deviceName = deviceName;
}
public String getPlatformName() {
	return platformName;
}
public void setPlatformName(String platformName) {
	this.platformName = platformName;
}
public String getPlatformVersion() {
	return platformVersion;
}
public void setPlatformVersion(String platformVersion) {
	this.platformVersion = platformVersion;
}
public String getAppPath() {
	return appPath;
}
public void setAppPath(String appPath) {
	this.appPath = appPath;
}
public String getAppPackage() {
	return appPackage;
}
public void setAppPackage(String appPackage) {
	this.appPackage = appPackage;
}
public String getAppActivity() {
	return appActivity;
}
public void setAppActivity(String appActivity) {
	this.appActivity = appActivity;
}
public String getServerURL() {
	return serverURL;
}
public void setServerURL(String serverURL) {
	this.serverURL = serverURL;
}
public String getBundleId() {
	return bundleId;
}
public void setBundleId(String bundleId) {
	this.bundleId = bundleId;
}
public String getUdid() {
	return udid;
}
public void setUdid(String udid) {
	this.udid = udid;
}
}
