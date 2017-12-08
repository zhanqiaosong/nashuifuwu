package cn.itcast.nsfw.user.action;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;

import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{
	@Resource
	private UserService userService;
	private List<User> userList;
	private User user; 
	private String[] selectedRow;
	private File headImg;
	private String headImgContentType;
	private String headImgFileName;
	
	private File userExcel;
	private String userExcelContentType;
	private String userExcelFileName;
	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}
	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}
	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}
	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}
	public String getHeadImgContentType() {
		return headImgContentType;
	}
	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}
	public String getHeadImgFileName() {
		return headImgFileName;
	}
	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	//列表页面
	public String listUI(){
		userList = userService.findObjects();
		return "listUI";
		
	}
	//跳转到新增页面
	public String addUI(){
		return "addUI";
		
	}
	//保存新增
	public String add(){
		try {
		     if (user!=null) {
			//头像处理
				if(headImg != null){
					//1.保存头像到upload/user
					//获取保存路径的绝对地址
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString().replaceAll("-", "")+headImgFileName.substring(headImgFileName.lastIndexOf('.'));
					//复制文件
					FileUtil.copyFile(headImg, new File(filePath,fileName));
					//2.设置头像路径
					user.setHeadImg("user/"+fileName);
				    }
				userService.save(user);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "list";
		
	}
	//跳转到编辑页面
	public String editUI(){
		if (user !=null && user.getId() !=null) {
			user = userService.findObjectById(user.getId());
		}
		return "editUI";
		
	}
	//保存编辑
	public String edit(){
		try {
		     if (user!=null) {
			//头像处理
				if(headImg != null){
					//1.保存头像到upload/user
					//获取保存路径的绝对地址
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					//删除原始文件 
					//new File(filePath,user.getHeadImg().split("/")[1]).delete();
					String fileName = UUID.randomUUID().toString().replaceAll("-", "")+headImgFileName.substring(headImgFileName.lastIndexOf('.'));
					//复制文件
					FileUtil.copyFile(headImg, new File(filePath,fileName));
					//2.设置头像路径
					System.out.println("ok");
					user.setHeadImg("user/"+fileName);
				    }
				userService.update(user);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "list";
		
	}
	//删除
	public String delete(){
		if (user !=null && user.getId() !=null) {
			userService.delete(user.getId());
		}
		return "list";
		
	}
	//批量删除  
	public String deleteSelected(){
		//可以循环删除
		if(selectedRow != null){
			for(String id:selectedRow){
				userService.delete(id);
			}
		}
		return "list";
	}
	//导出文件 
	public void exportExcel(){
		try {
			//可以循环删除
			userList = userService.findObjects();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-msexcel");
			response.addHeader("Content-Disposition", "attachment;filename="+new String("用户列表.xls".getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = response.getOutputStream();
			userService.exportExcel(userList,outputStream);
			if(outputStream!=null) outputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	//导入用户列表
	public String importExcel(){
	    //1.获取Excel文件
		if(userExcel!=null){
			//是否为Excel
			if(userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
				//2.导入
				userService.importExcel(userExcel,userExcelFileName);
			}
		}
		return "list";
		
	}
}
