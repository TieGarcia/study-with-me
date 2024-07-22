package com.se4f7.prj301.service.impl;



import com.se4f7.prj301.constants.ErrorMessage;
import com.se4f7.prj301.model.PaginationModel;

import com.se4f7.prj301.model.response.MessagesModelResponse;

import com.se4f7.prj301.repository.MessagesRepository;
import com.se4f7.prj301.service.MessagesService;

import com.se4f7.prj301.utils.StringUtil;

public class MessagesServiceImpl implements MessagesService {

	private MessagesRepository messagesRepository = new MessagesRepository();

	
	@Override
	public boolean deleteById(String id) {
		Long idNumber = StringUtil.parseLong("Id", id);
		MessagesModelResponse oldPosts = messagesRepository.getById(idNumber);
		if (oldPosts == null) {
			throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
		}
		return messagesRepository.deleteById(idNumber);
	}
	public MessagesModelResponse getBySubject(String subject) {

		MessagesModelResponse oldSettings = messagesRepository.getBySubject(subject);
		if (oldSettings == null) {
			throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
		}
		return oldSettings;
	}
	@Override
	public MessagesModelResponse getById(String id) {
		Long idNumber = StringUtil.parseLong("Id", id);
		MessagesModelResponse oldPosts = messagesRepository.getById(idNumber);
		if (oldPosts == null) {
			throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
		}
		return oldPosts;
	}

	@Override
	public PaginationModel filter(String page, String size, String name) {
		int pageNumber = StringUtil.parseInt("Page", page);
		int sizeNumber = StringUtil.parseInt("Size", size);
		return messagesRepository.filterByName(pageNumber, sizeNumber, name);
	}

}
