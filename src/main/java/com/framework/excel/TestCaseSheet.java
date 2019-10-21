package com.framework.excel;

public class TestCaseSheet {
	private double Sno;
	private String testCaseIID;
	private boolean isActive;

	public double getSno() {
		return Sno;
	}

	public void setSno(double sno) {
		Sno = sno;
	}

	public String getTestCaseIID() {
		return testCaseIID;
	}

	public void setTestCaseIID(String testCaseIID) {
		this.testCaseIID = testCaseIID;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}
