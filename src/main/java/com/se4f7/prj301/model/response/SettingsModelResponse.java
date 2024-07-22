package com.se4f7.prj301.model.response;
import com.se4f7.prj301.enums.StatusEnum;
import com.se4f7.prj301.model.BaseModel;

public class SettingsModelResponse extends BaseModel {
	private String content;
	private StatusEnum status;
	private String types;
	private String image;
	public SettingsModelResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SettingsModelResponse(String content, StatusEnum status, String types, String image) {
		super();
		this.content = content;
		this.status = status;
		this.types = types;
		this.image = image;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	
}
