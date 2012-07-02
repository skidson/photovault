package com.skidson.database;

import javax.jdo.annotations.Persistent;

public class Project extends DatastoreObject {
	@Persistent
	private String name;
	@Persistent
	private String faculty;
	@Persistent
	private String description;
	@Persistent
	private String url;
	@Persistent
	private Integer cnum;
	@Persistent
	private Float grade;
	
	public Project(String name, String faculty, Integer cnum, 
			String description, String url, Float grade) {
		this.setName(name);
		this.faculty = faculty;
		this.description = description;
		this.cnum = cnum;
		this.url = url;
		this.grade = grade;
	}

	public String getFaculty() {
		return faculty;
	}
	
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getCnum() {
		return cnum;
	}
	
	public void setCnum(Integer cnum) {
		this.cnum = cnum;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setGrade(Float grade) {
		this.grade = grade;
	}

	public Float getGrade() {
		return grade;
	}
	
}
