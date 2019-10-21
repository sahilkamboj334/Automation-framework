package com.framework.excel;

public class TestStepEntity{
	private String SrNo;
	private String testStepDesc;
	private String keyword;
	private boolean isActive;
	private String testStepIID;
	private String webelementIID;
	private String testData;
	private String extraParam1;
	private String extraParam2;
	private String isTakeScreenshot;
	private boolean isTestStepActive;

	public String getTestStepIID() {
		return testStepIID;
	}

	public void setTestStepIID(String testStepIID) {
		this.testStepIID = testStepIID;
	}

	public String getSrNo() {
		return SrNo;
	}

	public void setSrNo(String srNo) {
		SrNo = srNo;
	}

	public String getTestStepDesc() {
		return testStepDesc;
	}

	public void setTestStepDesc(String testStepDesc) {
		this.testStepDesc = testStepDesc;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getWebelementIID() {
		return webelementIID;
	}

	public void setWebelementIID(String webelementIID) {
		this.webelementIID = webelementIID;
	}

	public String getTestData() {
		return testData;
	}

	public void setTestData(String testData) {
		this.testData = testData;
	}

	public String getExtraParam1() {
		return extraParam1;
	}

	public void setExtraParam1(String extraParam1) {
		this.extraParam1 = extraParam1;
	}

	public String getExtraParam2() {
		return extraParam2;
	}

	public void setExtraParam2(String extraParam2) {
		this.extraParam2 = extraParam2;
	}

	public String getIsTakeScreenshot() {
		return isTakeScreenshot;
	}

	public void setIsTakeScreenshot(String isTakeScreenshot) {
		this.isTakeScreenshot = isTakeScreenshot;
	}

	public boolean getIsTestStepActive() {
		return isTestStepActive;
	}

	public void setIsTestStepActive(boolean isTestStepActive) {
		this.isTestStepActive = isTestStepActive;
	}

	@Override
	public String toString() {
	return SrNo+" "+" "+testStepIID+" "+testStepDesc+" "+keyword+" "+webelementIID+" "+testData+" "+extraParam1+" "+extraParam2+" "+isTakeScreenshot+" "+isActive;
	}
}
