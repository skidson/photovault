package com.skidson.database;

import javax.jdo.annotations.Persistent;

public class Resource extends DatastoreObject {
	@Persistent
	private String url;
	@Persistent
	private String project;
	@Persistent
	private String type;
	@Persistent
	private String description;
	
	public Resource(String url, String project, String type, String description) {
		this.url = url;
		this.project = project;
		this.type = type;
		this.description = description;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProject() {
		return project;
	}
}
