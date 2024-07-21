package com.se4f7.prj301.service.impl;

import javax.management.RuntimeErrorException;
import javax.servlet.http.Part;

import com.se4f7.prj301.constants.ErrorMessage;
import com.se4f7.prj301.model.PaginationModel;
import com.se4f7.prj301.model.request.WebSettingsModelRequest;
import com.se4f7.prj301.model.response.PostsModelResponse;
import com.se4f7.prj301.model.response.WebSettingsModelResponse;
import com.se4f7.prj301.repository.WebSettingsRepository;

import com.se4f7.prj301.service.WebSettingsService;
import com.se4f7.prj301.utils.FileUtil;
import com.se4f7.prj301.utils.StringUtil;

public class WebSettingsServiceImpl implements WebSettingsService {

	private WebSettingsRepository webSettingsRepository = new WebSettingsRepository();

	@Override
	public boolean create(WebSettingsModelRequest request, Part image, String username) {
		// TODO Auto-generated method stub
		// Validate type is exists.
		WebSettingsModelResponse oldWebSettings = webSettingsRepository.getByType(request.getType());
		if (oldWebSettings != null) {
			throw new RuntimeException(ErrorMessage.NAME_IS_EXISTS);
		}
		// Saving file from request.
		if (image != null && image.getSubmittedFileName() != null) {
			// Call function save file and return file name.
			String fileName = FileUtil.saveFile(image);
			// Set filename saved to Model.
			request.setImage(fileName);
		}
		// Call repository saving file.
		return webSettingsRepository.create(request, username);
	}

	@Override
	public boolean update(String id, WebSettingsModelRequest request, Part file, String username) {
		// TODO Auto-generated method stub
		// Parse String to Long.
		Long idNumber = StringUtil.parseLong("Id", id);
		String typeWebSettings = request.getType();
		// Get old WebSetting
		WebSettingsModelResponse oldWebSettings = webSettingsRepository.getById(idNumber);
		if (oldWebSettings != null) {
			throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
		}
		// Compare is title change.
		if (!request.getType().equalsIgnoreCase(oldWebSettings.getType())) {
			// Compare new type with other name in database.
			WebSettingsModelResponse othersType = webSettingsRepository.getByType(request.getType());
			if (othersType != null) {
				throw new RuntimeException(ErrorMessage.NAME_IS_EXISTS);
			}
		}
		// Saving file from request.
		if (file != null && file.getSubmittedFileName() != null) {
			// Delete old banner -> saving memory.
			FileUtil.removeFile(oldWebSettings.getImage());
			// Call function save file and return file name.
			String fileName = FileUtil.saveFile(file);
			// Set filename saved to Model.
			request.setImage(fileName);
		} else {
			// If banner not change we don't need replace it.
			// Re-use old name.
			request.setImage(oldWebSettings.getImage());
		}

		// Call repository saving file.
		return webSettingsRepository.update(idNumber, request, username);

	}

	@Override
	public boolean deleteById(String id) {
		// TODO Auto-generated method stub
		Long idNumber = StringUtil.parseLong("Id", id);
		WebSettingsModelResponse oldWebSettings = webSettingsRepository.getById(idNumber);
		if (oldWebSettings == null) {
			throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
		}
		if (oldWebSettings.getImage() != null) {
			//Delete old image --> saving memory.
			FileUtil.removeFile(oldWebSettings.getImage());
		}
		return webSettingsRepository.deleteById(idNumber);
	}

	@Override
	public WebSettingsModelResponse getByType(String type) {
		// TODO Auto-generated method stub
		String type_var = type;
		WebSettingsModelResponse oldWebSettings = webSettingsRepository.getByType(type_var);
		if (oldWebSettings == null){
			throw new RuntimeException(ErrorMessage.RECORD_NOT_FOUND);
		}
		return oldWebSettings;
	}

	@Override
	public PaginationModel filter(String page, String size, String name) {
		// TODO Auto-generated method stub
		int pageNumber = StringUtil.parseInt("Page", page);
		int sizeNumber = StringUtil.parseInt("Size", size);
		return webSettingsRepository.filterByName(pageNumber, sizeNumber, name);
	}

}
