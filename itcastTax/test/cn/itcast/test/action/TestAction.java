package cn.itcast.test.action;

import javax.annotation.Resource;

import cn.itcast.test.service.TestService;

import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport{
	//这是注入方式的比较
	//第一种注入
	@Resource
	TestService testService;
	//第二种注入
/*	@Resource
   	public void setTestService(TestService testService) {
		this.testService = testService;
	}*/

	public String execute() throws Exception {
   		testService.say();
		return SUCCESS;
	}
}
