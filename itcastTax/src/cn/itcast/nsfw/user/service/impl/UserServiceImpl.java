package cn.itcast.nsfw.user.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import cn.itcast.core.dao.util.ExcelUtil;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource private UserDao userDao;
	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void delete(Serializable id) {
		userDao.delete(id);
	}

	@Override
	public User findObjectById(Serializable id) {
		return userDao.findObjectById(id);
	}

	@Override
	public List<User> findObjects() {
		return userDao.findObjects();
	}

	@Override
	public void exportExcel(List<User> userList,
			ServletOutputStream outputStream){
		ExcelUtil.exportUserExcel(userList, outputStream);
	}

	@Override
	public void importExcel(File userExcel, String userExcelFileName) {
		List<User> importUserExcel = ExcelUtil.importUserExcel(userExcel, userExcelFileName);
		if (importUserExcel!=null) {
			for (User user : importUserExcel) {
				save(user);
			}
		}
	}
}
