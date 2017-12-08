package cn.itcast.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestWrite03Excel {

	@Test
	public void testWrite03Excel() throws Exception {
		 // 1.创建工作簿 
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		 // 2.创建工作表 
		HSSFSheet sheet = workbook.createSheet("hello word");
		 // 3.创建行 :创建第三行
		HSSFRow row = sheet.createRow(3-1);
		 // 4.创建单元格:创建第三行第三列
		HSSFCell cell = row.createCell(3-1);
		cell.setCellValue("hello qq");
		
		//5.输出到硬盘
		FileOutputStream out = new FileOutputStream("E:\\codingsoftware\\myeclipse_workspace\\itcastTax\\测试.xls");
		workbook.write(out);
		workbook.close();
		out.close();
	}
/*	@Test
	public void testRead03Excel() throws Exception {	
		//从硬盘读取文件
		FileInputStream in = new FileInputStream("E:\\codingsoftware\\myeclipse_workspace\\itcastTax\\测试.xls");
		
		// 1.读取工作簿 
		HSSFWorkbook workbook = new HSSFWorkbook(in); 
		// 2.读取工作表 
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 3.读取行 :创建第三行
		HSSFRow row = sheet.getRow(3-1);
		// 4.读取单元格:创建第三行第三列
		HSSFCell cell = row.getCell(3-1);
		System.out.println(cell.getStringCellValue());
		workbook.close();
		in.close();
	}*/
	@Test
	public void testWrite07Excel() throws Exception {
		// 1.创建工作簿 
		XSSFWorkbook workbook = new XSSFWorkbook(); 
		// 2.创建工作表 
		XSSFSheet sheet = workbook.createSheet("hello word");
		// 3.创建行 :创建第三行
		XSSFRow row = sheet.createRow(3-1);
		// 4.创建单元格:创建第三行第三列
		XSSFCell cell = row.createCell(3-1);
		cell.setCellValue("hello qq");
		
		//5.输出到硬盘
		FileOutputStream out = new FileOutputStream("E:\\codingsoftware\\myeclipse_workspace\\itcastTax\\测试.xlsx");
		workbook.write(out);
		workbook.close();
		out.close();
	}
	@Test
	public void testRead03And07Excel() throws Exception {	
		String filename = "E:\\codingsoftware\\myeclipse_workspace\\itcastTax\\测试.xls";
		/**
		 * 不应该区分大小写 用(?!)
		 */
		if(filename.matches("^.+\\.(?i)((xls)|(xlsx))$")){//判断管是否为excel文档
			//从硬盘读取文件
			FileInputStream in = new FileInputStream(filename);
			boolean is03Excel = filename.matches("^.+\\.(?i)(xls)$");
			// 1.读取工作簿 
			Workbook workbook = is03Excel?new HSSFWorkbook(in):new XSSFWorkbook(in); 
			// 2.读取工作表 
			Sheet sheet = workbook.getSheetAt(0);
			// 3.读取行 :创建第三行
			Row row = sheet.getRow(3-1);
			// 4.读取单元格:创建第三行第三列
			Cell cell = row.getCell(3-1);
			System.out.println(cell.getStringCellValue());
			workbook.close();
			in.close();
		}
	}
	@Test
	public void testExcelStyle() throws Exception {	
		 // 1.创建工作簿 
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		//1.1创建合并单元格对象；合并第三行的第三列到第五列
		CellRangeAddress cellRangeAddress = new CellRangeAddress(2, 2, 2, 5);
		//1.2创建单元格 样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//1.3创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗字体
		font.setFontHeightInPoints((short)16) ;//设置字体大小
		//加载字体
		style.setFont(font);//样式设置字体
		//单元格背景
		//设置背景填充模式
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//必须有。设置的模式以填充的前景背景色为准
		//设置填充背景色
		style.setFillBackgroundColor(HSSFColor.YELLOW.index);
		//设置填充前景色
		style.setFillForegroundColor(HSSFColor.RED.index);
		// 2.创建工作表 
		HSSFSheet sheet = workbook.createSheet("Hell Word");
		//2.1加载合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		 // 3.创建行 :创建第三行
		HSSFRow row = sheet.createRow(3-1);
		 // 4.创建单元格:创建第三行第三列
		HSSFCell cell = row.createCell(3-1);
		cell.setCellValue("hello qq");
		cell.setCellStyle(style);		
		//5.输出到硬盘
		FileOutputStream out = new FileOutputStream("E:\\codingsoftware\\myeclipse_workspace\\itcastTax\\测试.xls");
		workbook.write(out);
		workbook.close();
		out.close();
	}

}
