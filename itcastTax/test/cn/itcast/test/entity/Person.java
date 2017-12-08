package cn.itcast.test.entity;

import java.io.Serializable;

public class Person implements Serializable {
	  private String id;
	  private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Person(String name) {
		super();
		this.name = name;
	}
	public Person() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
