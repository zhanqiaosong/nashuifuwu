package cn.itcast.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.test.entity.Person;
import cn.itcast.test.service.TestService;

public class TestMerge {
	ClassPathXmlApplicationContext ctx;
  @Before
  public void loadCtx(){
	  ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
  }
  
  @Test
  public void testSpring(){//只读，有对数据的操作回滚
	  TestService ts = (TestService) ctx.getBean("testService");
	  ts.say();
	  System.out.println(Person.class.getSimpleName());
  }
  @Test
  public void testTranactionReadOnly(){//只读，有对数据的操作回滚
	  TestService ts = (TestService) ctx.getBean("testService");
	  Person per = ts.findPerson("4028d381601b6e2d01601b6e2e990000");
	  System.out.println(per.getName());
  }
  @Test
  public void testTranactionRollback(){//只读，有对数据的操作回滚
	  TestService ts = (TestService) ctx.getBean("testService");
	  ts.save(new Person("abc"));
  }
  
}
