package com.framework.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelConfigFactory {
protected String excelPath;
public Workbook workbook;
public void initObject(String excelPath) throws Exception {
	this.excelPath = excelPath;
	workbook=new XSSFWorkbook(new FileInputStream(new File(excelPath)));
}
public void closeWorkbook(){
	try {
		workbook.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public ExcelConfigFactory(){}
}