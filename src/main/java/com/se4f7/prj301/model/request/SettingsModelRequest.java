package com.se4f7.prj301.model.request;



public class SettingsModelRequest {
	private String content;
	private String types;
	private String image;
	public SettingsModelRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SettingsModelRequest(String content, String types, String image) {
		super();
		this.content = content;	
		this.types = types;
		this.image = image;		
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	
}
