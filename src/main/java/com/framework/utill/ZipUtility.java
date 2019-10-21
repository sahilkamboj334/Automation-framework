package com.framework.utill;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



public class ZipUtility {

	static ArrayList<File> list = new ArrayList<>();
	static ZipOutputStream zipOutputStream;
	static FileInputStream fileInputStream;
	static ZipEntry entry;

	private static void generateFileList(String filepath) {
		File[] arr = null;
		File file = new File(filepath);
		if (file.isDirectory()) {
			arr = file.listFiles();
			if (arr.length == 0) {
				list.add(file);
			} else {
				for (File tmp : arr) {
					if (tmp.isFile()) {
						list.add(tmp);
					} else if (tmp.isDirectory()) {
						generateFileList(tmp.getAbsolutePath());
					}
				}
			}
		} else
			list.add(file);
	}
	/**
	 * 
	 * @param filepathArray--array
	 *            of files of which you want to make zip.ex={"folderlocation
	 *            with name","filename"}
	 * @param zipPathwithName--specify
	 *            path where to put zip file along with name of
	 *            zip.ex="C:\\Data" no need to put extension.
	 * @throws Exception
	 */
	public static void makeZip(String[] filepathArray, String zipPathwithName) throws Exception {
		zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPathwithName + ".zip"));
		for(String str:filepathArray){
		generateFileList(str);
		for (File file : list) {
			if(file.isDirectory()){
				entry = new ZipEntry(file.getCanonicalPath().toString()+File.separator);
				zipOutputStream.putNextEntry(entry);
			}else{
				fileInputStream = new FileInputStream(file);
				entry = new ZipEntry(file.getCanonicalPath().toString());
				zipOutputStream.putNextEntry(entry);
				byte[] bt = new byte[1024];
				int i;
				while ((i = fileInputStream.read(bt)) >= 0) {
					zipOutputStream.write(bt, 0, i);
				}
			}}
				list.clear();
		}
		fileInputStream.close();
		zipOutputStream.close();
		System.out.println("Zip has been created--!!!!!!");
	}

	public static void makeZip(File filepath,String zipPathwithName) throws Exception{
		zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPathwithName + ".zip"));
		generateFileList(filepath.getAbsolutePath().toString());
		for (File file : list) {
			if(file.isDirectory()){
				entry = new ZipEntry(file.getCanonicalPath().toString()+File.separator);
				zipOutputStream.putNextEntry(entry);
			}else{
				fileInputStream = new FileInputStream(file);
				entry = new ZipEntry(file.getCanonicalPath().toString());
				zipOutputStream.putNextEntry(entry);
				byte[] bt = new byte[1024];
				int i;
				while ((i = fileInputStream.read(bt)) >= 0) {
					zipOutputStream.write(bt, 0, i);
				}
			}
			
		}
		list.clear();
		fileInputStream.close();
		zipOutputStream.close();
		System.out.println("Zip has been created--!!!!!!");
	}
	
}
