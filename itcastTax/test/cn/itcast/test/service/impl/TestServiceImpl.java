package cn.itcast.test.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.test.dao.TestDao;
import cn.itcast.test.entity.Person;
import cn.itcast.test.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService{
	@Resource
    public TestDao testDao;
	@Override
	public void say() {
		System.out.println("service say hello");		
	}

	@Override
	public Person findPerson(Serializable id) {
		return testDao.findPerson(id);
	}

	@Override
	public void save(Person person) {
		testDao.save(person);
	}
}