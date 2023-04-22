package com.tarladala.recipe.scraping.utilities;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class WriteExcel {
public XSSFWorkbook workbook;
public FileOutputStream fo;
public FileInputStream fi;
public XSSFSheet sheet;
public XSSFRow row;
public XSSFCell cell;

    public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {
        File xlfile =  new File("C:/Users/ayesh/IdeaProjects/Data_Scraping_Framework_Demo1/src/test/resources/outPutData.xlsx");
        if(!xlfile.exists()){
         workbook = new XSSFWorkbook();
fo = new FileOutputStream("C:/Users/ayesh/IdeaProjects/Data_Scraping_Framework_Demo1/src/test/resources/outPutData.xlsx");
workbook.write(fo);
        }
        fi= new FileInputStream("C:/Users/ayesh/IdeaProjects/Data_Scraping_Framework_Demo1/src/test/resources/outPutData.xlsx");
        workbook = new XSSFWorkbook(fi);

        if(workbook.getSheetIndex(sheetName)==-1)
            workbook.createSheet(sheetName);
        sheet=workbook.getSheet(sheetName);

        if(sheet.getRow(rownum)==null)
            sheet.createRow(rownum);
        row=sheet.getRow(rownum);

        cell=row.createCell(column);
        cell.setCellValue(data);
        fo=new FileOutputStream("C:/Users/ayesh/IdeaProjects/Data_Scraping_Framework_Demo1/src/test/resources/outPutData.xlsx");
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
    }
}
