package com.se4f7.prj301.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.se4f7.prj301.constants.ErrorMessage;
import com.se4f7.prj301.enums.StatusEnum;
import com.se4f7.prj301.model.PaginationModel;
import com.se4f7.prj301.model.request.WebSettingsModelRequest;
import com.se4f7.prj301.model.response.WebSettingsModelResponse;
import com.se4f7.prj301.utils.DBUtil;

public class WebSettingsRepository {
	private static final String INSERT_SQL = "INSERT INTO web_setting (content, createdBy, updatedBy, types, image) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE web_setting SET content = ?, createdBy = ?, updatedBy = ?, status = ?, types = ?, image = ? WHERE id = ?";
	private static final String GET_BY_TYPE_SQL = "SELECT * FROM web_setting AS w WHERE w.types = ? ";
	private static final String GET_BY_ID_SQL = "SELECT * FROM web_setting WHERE id = ?";
	private static final String DELETE_BY_ID_SQL = "DELETE FROM web_setting  WHERE id= ? ";
	private static final String SEARCH_LIST_SQL = "SELECT * FROM web_setting WHERE types LIKE ? LIMIT ? OFFSET ?";
	private static final String COUNT_BY_NAME_SQL = "SELECT COUNT(id) AS totalRecord FROM web_setting WHERE types LIKE ?";
	public boolean create (WebSettingsModelRequest request, String username) {
		// Open connection and set SQL query into PreparedStatement.
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL))	{
			// Set parameters.
			preparedStatement.setString(1, request.getContent());;
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, username);
			preparedStatement.setString(4, request.getType());
			preparedStatement.setString(5, request.getImage());
			//Show SQL query.
			System.out.println(preparedStatement);
			//Execute query.
			preparedStatement.executeUpdate();			
			return true;
			} catch(Exception e){
				throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
			}
	}
	public boolean update (Long id, WebSettingsModelRequest request, String username) {
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)){
			preparedStatement.setString(1, request.getContent());
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, username);
			preparedStatement.setString(4, request.getType());
			preparedStatement.setString(5, request.getImage());
			preparedStatement.setLong(6, id);
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}
	public WebSettingsModelResponse getByType(String type) {
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_TYPE_SQL)) {
			//Set parameter.
			preparedStatement.setString(1, type);
			//Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			ResultSet resultSet = preparedStatement.executeQuery();
			if(!resultSet.isBeforeFirst()) {
				return null;
			}
		WebSettingsModelResponse response = new WebSettingsModelResponse();
		while(resultSet.next()) {
			//can change name column to position column, like 1 for id column
			response.setId(resultSet.getLong("id"));
			response.setContent(resultSet.getString("content"));
			response.setCreatedDate(resultSet.getString("createdDate"));
			response.setUpdatedDate(resultSet.getString("updatedDate"));
			response.setCreatedBy(resultSet.getString("createdBy"));
			response.setUpdatedBy(resultSet.getString("updatedBy"));
			response.setStatus(StatusEnum.valueOf(resultSet.getString("status")));
			response.setType(resultSet.getString("types"));
			response.setImage(resultSet.getString("image"));
		}
		return response;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e .getMessage());
		}
	}
	public WebSettingsModelResponse getById(Long id) {
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {
			//Set parameter.
			preparedStatement.setLong(1, id);
			//Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			ResultSet resultSet = preparedStatement.executeQuery();
			if(!resultSet.isBeforeFirst()) {
				return null;
			}
		WebSettingsModelResponse response = new WebSettingsModelResponse();
		while(resultSet.next()) {
			//can change name column to position column, like 1 for id column
			response.setId(resultSet.getLong("id"));
			response.setContent(resultSet.getString("content"));
			response.setCreatedDate(resultSet.getString("createdDate"));
			response.setUpdatedDate(resultSet.getString("updatedDate"));
			response.setCreatedBy(resultSet.getString("createdBy"));
			response.setUpdatedBy(resultSet.getString("updatedBy"));
			response.setStatus(StatusEnum.valueOf(resultSet.getString("status")));
			response.setType(resultSet.getString("types"));
			response.setImage(resultSet.getString("image"));
		}
		return response;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e .getMessage());
		}
	}
	public boolean deleteById(Long id) {
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)){
			// Set parameters.
			preparedStatement.setLong(1, id);
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			preparedStatement.executeUpdate();
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());	
		}
	}
	public PaginationModel filterByName(int page, int size, String name) {
		// Open connection and set SQL query into PreparedStatement.
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement stmtSelect = connection.prepareStatement(SEARCH_LIST_SQL);
				PreparedStatement stmtCount = connection.prepareStatement(COUNT_BY_NAME_SQL)){
			stmtSelect.setString(1, name != null ? "%" + name + "%" : "%%");
			stmtSelect.setInt(2, size);
			stmtSelect.setInt(3, page * size);
			//Show SQL query.
			System.out.println(stmtSelect);
			// Execute query.
			// Select records.
			ResultSet rs = stmtSelect.executeQuery();
			List<WebSettingsModelResponse> results = new ArrayList<WebSettingsModelResponse>();
			while(rs.next()) {
				WebSettingsModelResponse response = new WebSettingsModelResponse();
				response.setId(rs.getLong("id"));
				response.setContent(rs.getString("content"));
				response.setCreatedDate(rs.getString("createdDate"));
				response.setUpdatedDate(rs.getString("updatedDate"));
				response.setCreatedBy(rs.getString("createdBy"));
				response.setUpdatedBy(rs.getString("updatedBy"));
				response.setStatus(StatusEnum.valueOf(rs.getString("status")));
				response.setType(rs.getString("types"));
				response.setImage(rs.getString("image"));
				results.add(response);
			}
			//Count records
			stmtCount.setString(1, name != null ? "%" + name + "%" : "%%");
			ResultSet rsCount = stmtCount.executeQuery();
			int totalRecord = 0;
			while(rsCount.next()) {
				totalRecord = rsCount.getInt("totalRecord");
			}
			return new PaginationModel(page, size, totalRecord, results);
		}catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}
}
