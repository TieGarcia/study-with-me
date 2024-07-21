package com.se4f7.prj301.service;


import javax.servlet.http.Part;

import com.se4f7.prj301.model.PaginationModel;
import com.se4f7.prj301.model.request.WebSettingsModelRequest;
import com.se4f7.prj301.model.response.WebSettingsModelResponse;

public interface WebSettingsService {
	public boolean create(WebSettingsModelRequest request, Part image, String username);

	public boolean update(String id, WebSettingsModelRequest request, Part file, String username);

	public boolean deleteById(String id);

	public WebSettingsModelResponse getByType(String type);

	public PaginationModel filter(String page, String size, String name);
		
}
