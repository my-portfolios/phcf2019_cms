package egovframework.phcf.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import egovframework.phcf.hubizCommonMethod.CommonMethod;

public class ExcelUtil {
	
	public void exportExcel( HttpServletRequest request, HttpServletResponse response, List<Map<String, Object>> headList, List<Map<String, Object>> valueList ) throws Exception {
		OutputStream os = null;
		XSSFWorkbook wb = null;
		
		try {
			 
			 InputStream is = new ClassPathResource("/egovframework/excel/excelTemplate.xlsx").getInputStream();
			 wb = new XSSFWorkbook(is);
			 
			 XSSFSheet sheet = wb.getSheetAt(0);
			 
			 CellStyle headCellStyle = wb.createCellStyle();
			 
			 int cellNumber = 0;
			 XSSFRow row = sheet.getRow(0);
			 XSSFCell cell = row.createCell(cellNumber);
			 cell.setCellStyle(headCellStyle);
			 cell.setCellValue("No.");
			 
			 for(int i = 0; i < headList.size(); i++) {
				 HashMap<String, Object> map = (HashMap<String, Object>) headList.get(i);
				 String header = reduceText( String.valueOf( map.get("text") ) );

				 cell = row.createCell( ++cellNumber );
				 cell.setCellStyle(headCellStyle);
				 cell.setCellValue( header );
			 }
			 
			 CellStyle valueCellStyle = wb.createCellStyle();
			 
			 for(int i=0;i<valueList.size();i++) {
				 row = sheet.createRow( i + 1 );
				 
				 cell = row.createCell(0);
				 cell.setCellStyle(valueCellStyle);
				 cell.setCellValue(valueList.size() - i);
				 
				 for(int k=0;k<headList.size();k++) {
					 cell = row.createCell(k+1);
					 
					 cell.setCellStyle(valueCellStyle);
					 cell.setCellValue( CommonMethod.stringConvert(valueList.get(i).get(headList.get(k).get("value")), "") );
				 }
			 }
			 
			 // 응답 처리할 Header 및 Workbook 내용 쓰기..
			 
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			 
			 response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			 response.setHeader("Content-Type", "application/octet-stream");
			 response.setHeader("Content-Disposition", "attachment; filename=" + sdf.format(System.currentTimeMillis()) + "_download" + ".xlsx");
			 
			 os = response.getOutputStream();
			 
			 wb.write(os);
			 
		 } catch(Exception ex) {
			 ex.printStackTrace();
			 
			 response.setHeader("Set-Cookie", "fileDownload=false; path=/");
		     response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		     response.setHeader("Content-Type","text/html; charset=utf-8");
			 
		     throw ex;
		 } finally {
			 if(os != null) { os.flush(); }
		 }
	}
	
	private static String reduceText(String text) {
		return text.replaceAll("\\<br\\/\\>", "\r\n");
	}
}