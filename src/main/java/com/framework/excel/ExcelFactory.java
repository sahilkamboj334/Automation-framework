package com.framework.excel;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelFactory extends ExcelConfigFactory {
	private static Logger logger = Logger.getLogger(ExcelFactory.class);
	private static ExcelFactory instance;

	public static ExcelFactory getInstance() {
		return instance == null ? instance = new ExcelFactory() : instance;
	}

	public void initObject(String excelpath) {
		try {
			super.initObject(excelpath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, String> getTestDataRefMap(String sheet) throws Exception {
		HashMap<String, String> testDataMap = new HashMap<>();
		Sheet testData = (XSSFSheet) workbook.getSheet(sheet);
		if (testData == null)
			throw new NullPointerException("No Test Refrence Sheet Exist!!!");
		for (int i = 1; i <=testData.getLastRowNum(); i++) {
			Row row = testData.getRow(i);
			for (int j = 1; j <row.getLastCellNum(); j++) {
				testDataMap.put(row.getCell(j-1).toString(), row.getCell(j).toString());
			}
		}
		logger.info("Test Data Reference map has been loaded Succesfully!!!");
		return testDataMap;
	}

	public ArrayList<TestCaseSheet> loadSheets(String moduleIID) {
		ArrayList<TestCaseSheet> testCaseGroups = new ArrayList<TestCaseSheet>();
		TestCaseSheet caseGroup;
		Sheet sheet = (XSSFSheet) workbook.getSheet(moduleIID);
		for (int i = 1; i < (sheet.getLastRowNum() + 1) - sheet.getFirstRowNum(); i++) {
			Row row = sheet.getRow(i);
			caseGroup = new TestCaseSheet();
			for (int j = 0; j < row.getLastCellNum() - row.getFirstCellNum(); j++) {
				row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
				if (j == 0)
					caseGroup.setSno(Integer.parseInt(row.getCell(j).toString()));
				else if (j == 1)
					caseGroup.setTestCaseIID(row.getCell(j).toString());
				else if (j == 2)
					caseGroup.setIsActive(row.getCell(j).toString().equalsIgnoreCase("Yes") ? true : false);
			}
			logger.info("Loaded row number " + i + " of test case group sheet name " + moduleIID);
			testCaseGroups.add(caseGroup);
		}
		return testCaseGroups;
	}

	public ArrayList<TestCaseSheet> getActiveSheetGroup(ArrayList<TestCaseSheet> sheets) {
		ArrayList<TestCaseSheet> active = new ArrayList<>();
		for (TestCaseSheet caseSheet : sheets) {
			if (caseSheet.isActive()) {
				active.add(caseSheet);
			}
		}
		logger.info("Total Number of Active sheets are " + active.size());
		return active;
	}

	public ArrayList<TestStepGroup> getTestCaseGroups(TestCaseSheet caseSheet) {
		ArrayList<TestStepGroup> testStepGroups = new ArrayList<>();
		TestStepGroup testStepGroup = null;
		TestStepEntity testStepEntity = null;
			if (caseSheet.isActive()) {
				Sheet sheet = workbook.getSheet(caseSheet.getTestCaseIID());
				for (int j = 0; j < sheet.getNumMergedRegions(); j++) {
					org.apache.poi.ss.util.CellRangeAddress region = sheet.getMergedRegion(j);
					int firstRownum = region.getFirstRow();
					int firstColnum = region.getFirstColumn();
					if (j % 3 == 0) {
						testStepGroup = new TestStepGroup();
						testStepGroup
								.setTestCaseID(sheet.getRow(firstRownum).getCell(firstColnum).getStringCellValue());
						testStepGroup.setSheetID(caseSheet.getTestCaseIID());
					} else if (j % 3 == 1) {
						testStepGroup
								.setTestCaseDesc(sheet.getRow(firstRownum).getCell(firstColnum).getStringCellValue());
					} else if (j % 3 == 2) {
						ArrayList<TestStepEntity> testStepEntities = new ArrayList<>();
						testStepGroup.setActive(sheet.getRow(firstRownum).getCell(firstColnum).getStringCellValue()
								.equalsIgnoreCase("Yes") ? true : false);
						for (int k = region.getFirstRow(); k <= (region.getLastRow()); k++) {
							Row row = sheet.getRow(k);
							testStepEntity = new TestStepEntity();
							for (int _k = 0; _k < row.getLastCellNum(); _k++) {
								row.getCell(_k).setCellType(Cell.CELL_TYPE_STRING);
								switch (_k) {
								case 0:
									testStepEntity.setSrNo(row.getCell(_k).toString());
									break;
								case 3:
									testStepEntity.setIsActive(
											row.getCell(_k).toString().equalsIgnoreCase("Yes") ? true : false);
									break;
								case 4:
									testStepEntity.setTestStepIID(row.getCell(_k).toString());
									break;
								case 5:
									testStepEntity.setTestStepDesc(row.getCell(_k).toString());
									break;
								case 6:
									testStepEntity.setKeyword(row.getCell(_k).toString());
									break;
								case 7:
									testStepEntity.setWebelementIID(row.getCell(_k).toString());
									break;
								case 8:
									testStepEntity.setTestData(row.getCell(_k).toString());
									break;
								case 9:
									testStepEntity.setExtraParam1(row.getCell(_k).toString());
									break;
								case 10:
									testStepEntity.setExtraParam2(row.getCell(_k).toString());
									break;
								case 11:
									testStepEntity.setIsTakeScreenshot(row.getCell(_k).toString());
									break;
								case 12:
									testStepEntity.setIsTestStepActive(row.getCell(_k).toString().equalsIgnoreCase("Yes")?true:false);
									break;
								default:
									break;
								}
							}
							testStepEntities.add(testStepEntity);
							testStepGroup.setTestSteplist(testStepEntities);
							logger.info("Row number " + k + " has been mapped for teststepgroup..."
									+ testStepGroup.getTestCaseID());
						}
						logger.info("Added TestCaseGroup " + testStepGroup.getTestCaseID());
						testStepGroups.add(testStepGroup);
					}
				}

			}

		return testStepGroups;

	}

}
