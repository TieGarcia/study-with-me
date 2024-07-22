package com.se4f7.prj301.service;

import com.se4f7.prj301.model.PaginationModel;

import com.se4f7.prj301.model.response.MessagesModelResponse;

public interface MessagesService {

	

	public boolean deleteById(String id);
	
	public MessagesModelResponse getBySubject(String subject);
	public MessagesModelResponse getById(String id);

	public PaginationModel filter(String page, String size, String name);

}
