package cn.itcast.core.dao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.itcast.nsfw.user.entity.User;

import com.sun.rowset.internal.Row;

public class ExcelUtil {
	/**
	 * //1、获取excel文件
	 * //2、导入的与导出Excel的格式不一样
	 * @param userExcel
	 * @param userExcelFileName
	 * @return
	 */
	public static List<User> importUserExcel(File userExcel, String userExcelFileName){
		List<User> userList = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(userExcel);
			boolean is03Excel = userExcelFileName.matches("^.+\\.(?i)(xls)$");
			
			//1、读取工作簿
			Workbook workBook = is03Excel?new HSSFWorkbook(fileInputStream):new XSSFWorkbook(fileInputStream);
			//2、读取工作表
			Sheet sheet = workBook.getSheetAt(0);
			//3、读取行
			if(sheet.getPhysicalNumberOfRows()>2){
				userList = new ArrayList<User>();//此处new
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row row = sheet.getRow(i);
					//4、读取单元格  放入list
					User user = new User();
					user.setName(row.getCell(0).getStringCellValue());
					user.setAccount(row.getCell(1).getStringCellValue());
					user.setDept(row.getCell(2).getStringCellValue());
					user.setGender(row.getCell(3).getStringCellValue().equals("男"));
					String mobile = "";
					try {
						mobile = row.getCell(4).getStringCellValue();
					} catch (Exception e) {
						double dMobile = row.getCell(4).getNumericCellValue();
						mobile = dMobile+"";
					}
					user.setMobile(mobile);
					user.setEmail(row.getCell(5).getStringCellValue());
					if(row.getCell(6).getDateCellValue()!=null){
						user.setBirthday(row.getCell(6).getDateCellValue());
					}
					user.setPassword("123456");
					user.setState(User.USER_STATE_INVALID);
					userList.add(user);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//5、保存用户
		return userList;
	}
	/**
	 * 导出用户的所有列表到Excel
	 * @param userList
	 * @param outputStream 输出流
	 */
    public static void exportUserExcel(List<User> userList,
			ServletOutputStream outputStream){
	 try {
			//1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);
			//1.2、头标题样式
			HSSFCellStyle style1 = createCellStyle(workbook,(short) 16);
			//1.3、列标题样式
			HSSFCellStyle style2 = createCellStyle(workbook,(short) 13);
			//
			//2、创建工作表
			HSSFSheet sheet = workbook.createSheet("用户列表");
			//2.1、加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			//设置默认列宽
			sheet.setDefaultColumnWidth(25);
			
			//3、创建行
			//3.1、创建头标题行；并且设置头标题
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell titleCell = row1.createCell(0);//当前行内任意列
			//加载单元格样式
			titleCell.setCellStyle(style1);
			titleCell.setCellValue("用户列表");
			//3.2、创建列标题行；并且设置列标题
			HSSFRow row2 = sheet.createRow(1);
			String[] titles = {"用户名","账号","所属部门","性别","电子邮箱"};
			for (int i=0;i<titles.length;i++) {
				HSSFCell Cell = row2.createCell(i);
				//加载单元格样式
				Cell.setCellStyle(style2);
				Cell.setCellValue(titles[i]);
			}
			if (userList!=null) {
				//4、操作单元格；将用户列表写入excel
				for (int j = 0; j < userList.size(); j++) {
					HSSFRow row = sheet.createRow(2+j);
					HSSFCell dataCell1 = row.createCell(0);
					dataCell1.setCellValue(userList.get(j).getName());
					HSSFCell dataCell2 = row.createCell(1);
					dataCell2.setCellValue(userList.get(j).getAccount());
					HSSFCell dataCell3 = row.createCell(2);
					dataCell3.setCellValue(userList.get(j).getDept());
					HSSFCell dataCell4 = row.createCell(3);
					dataCell4.setCellValue(userList.get(j).isGender()?"男":"女");
					HSSFCell dataCell5 = row.createCell(4);
					dataCell5.setCellValue(userList.get(j).getEmail());
				}
			}
			//
			//5、输出
			workbook.write(outputStream);
			workbook.close();
			//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
	/**
	 * 创建单元格样式
	 * @param workbook
	 * @param fontSize
	 * @return 单元格样式
	 */
	public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook,Short fontSize) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//1.2.1 创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗字体
		font.setFontHeightInPoints(fontSize);//fontSize号字体
		//加载字体
		style.setFont(font);
		return style;
	}
}
