package com.skidson.database;

import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Persistent;

public class Course extends DatastoreObject {
	@Persistent
	private String faculty;
	@Persistent
	private String description;
	@Persistent
	private String title;
	@Persistent
	private Integer cnum;
	@Persistent
	private Integer grade;
	@Persistent
	private Integer year;
	@Persistent
	private Float credits;
	@Persistent
	@Element(dependent = "true")
	private List<Project> projects;
	
	public Course(String faculty, Integer cnum, String title, 
			String description, Integer grade, Float credits, Integer year) {
		this.faculty = faculty;
		this.description = description;
		this.title = title;
		this.cnum = cnum;
		this.grade = grade;
		this.credits = credits;
		this.year = year;
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
	
	public Integer getGrade() {
		return grade;
	}
	
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setCredits(Float credits) {
		this.credits = credits;
	}

	public Float getCredits() {
		return credits;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYear() {
		return year;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Project> getProjects() {
		return projects;
	}
	
	public void addProject(Project project) {
		this.projects.add(project);
	}

}
