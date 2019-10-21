package com.framework.excel;

import java.util.List;

public class TestStepGroup implements Comparable<TestStepGroup> {
	private String sheetID;

	public String getSheetID() {
		return sheetID;
	}

	public void setSheetID(String sheetID) {
		this.sheetID = sheetID;
	}

	private String testCaseID;
	private String testCaseDesc;
	private boolean isActive;
	private List<TestStepEntity> testSteplist;

	public List<TestStepEntity> getTestSteplist() {
		return testSteplist;
	}

	public void setTestSteplist(List<TestStepEntity> testSteplist) {
		this.testSteplist = testSteplist;
	}

	public String getTestCaseID() {
		return testCaseID;
	}

	public void setTestCaseID(String testCaseID) {
		this.testCaseID = testCaseID;
	}

	public String getTestCaseDesc() {
		return testCaseDesc;
	}

	public void setTestCaseDesc(String testCaseDesc) {
		this.testCaseDesc = testCaseDesc;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return testCaseDesc + " " + testCaseID + " " + isActive + " " + testSteplist + "\n";
	}

	@Override
	public int compareTo(TestStepGroup o) {
		return this.testCaseID.compareTo(o.testCaseID);
	}
}
