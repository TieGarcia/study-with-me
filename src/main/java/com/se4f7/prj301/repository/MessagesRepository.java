package com.se4f7.prj301.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.se4f7.prj301.constants.ErrorMessage;
import com.se4f7.prj301.enums.StatusEnum;
import com.se4f7.prj301.model.PaginationModel;
import com.se4f7.prj301.model.request.MessagesModelRequest;
import com.se4f7.prj301.model.response.MessagesModelResponse;
import com.se4f7.prj301.utils.DBUtil;

public class MessagesRepository {
	
	private static final String GET_BY_ID_SQL = "SELECT * FROM messages WHERE id = ?";
	private static final String GET_BY_SUBJECT_SQL = "SELECT * FROM messages WHERE subject = ?";
	private static final String DELETE_BY_ID_SQL = "DELETE FROM messages WHERE id = ?";
	private static final String SEARCH_LIST_SQL = "SELECT * FROM messages WHERE subject LIKE ? LIMIT ? OFFSET ?";
	private static final String COUNT_BY_NAME_SQL = "SELECT COUNT(id) AS totalRecord FROM messages WHERE subject LIKE ?";

	

	public MessagesModelResponse getById(Long id) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {
			// Set parameters.
			preparedStatement.setLong(1, id);
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			ResultSet rs = preparedStatement.executeQuery();
			if (!rs.isBeforeFirst()) {
				return null;
			}
			MessagesModelResponse response = new MessagesModelResponse();
			while (rs.next()) {	
				response.setId(rs.getLong("id"));
				response.setSubject(rs.getString("subject"));
				response.setEmail(rs.getString("email"));
				response.setCreatedDate(rs.getString("createdDate"));
				response.setUpdatedDate(rs.getString("updatedDate"));
				response.setCreatedBy(rs.getString("createdBy"));
				response.setUpdatedBy(rs.getString("updatedBy"));
				response.setStatus(StatusEnum.valueOf(rs.getString("status")));
				response.setMessage(rs.getString("message"));
			}
			return response;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public MessagesModelResponse getBySubject(String subject) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_SUBJECT_SQL)) {
			// Set parameters.
			preparedStatement.setString(1, subject);
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			ResultSet rs = preparedStatement.executeQuery();
			if (!rs.isBeforeFirst()) {
				return null;
			}
			MessagesModelResponse response = new MessagesModelResponse();
			while (rs.next()) {
				response.setId(rs.getLong("id"));
				response.setSubject(rs.getString("subject"));
				response.setEmail(rs.getString("email"));
				response.setCreatedDate(rs.getString("createdDate"));
				response.setUpdatedDate(rs.getString("updatedDate"));
				response.setCreatedBy(rs.getString("createdBy"));
				response.setUpdatedBy(rs.getString("updatedBy"));
				response.setStatus(StatusEnum.valueOf(rs.getString("status")));
				response.setMessage(rs.getString("message"));
			}
			return response;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public boolean deleteById(Long id) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
			// Set parameters.
			preparedStatement.setLong(1, id);
			// Show SQL query.
			System.out.println(preparedStatement);
			// Execute query.
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}

	public PaginationModel filterByName(int page, int size, String name) {
		// Open connection and set SQL query into PreparedStatement.
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement stmtSelect = connection.prepareStatement(SEARCH_LIST_SQL);
				PreparedStatement stmtCount = connection.prepareStatement(COUNT_BY_NAME_SQL)) {
			// Set parameters.
			stmtSelect.setString(1, name != null ? "%" + name + "%" : "%%");
			stmtSelect.setInt(2, size);
			stmtSelect.setInt(3, page * size);
			// Show SQL query.
			System.out.println(stmtSelect);
			// Execute query.
			// Select records.
			ResultSet rs = stmtSelect.executeQuery();
			List<MessagesModelResponse> results = new ArrayList<MessagesModelResponse>();
			while (rs.next()) {
				MessagesModelResponse response = new MessagesModelResponse();
				response.setId(rs.getLong("id"));
				response.setSubject(rs.getString("subject"));
				response.setEmail(rs.getString("email"));
				response.setCreatedDate(rs.getString("createdDate"));
				response.setUpdatedDate(rs.getString("updatedDate"));
				response.setCreatedBy(rs.getString("createdBy"));
				response.setUpdatedBy(rs.getString("updatedBy"));
				response.setStatus(StatusEnum.valueOf(rs.getString("status")));
				response.setMessage(rs.getString("message"));
				results.add(response);
			}

			// Count records;
			stmtCount.setString(1, name != null ? "%" + name + "%" : "%%");
			ResultSet rsCount = stmtCount.executeQuery();
			int totalRecord = 0;
			while (rsCount.next()) {
				totalRecord = rsCount.getInt("totalRecord");
			}
			return new PaginationModel(page, size, totalRecord, results);
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessage.SQL_ERROR + e.getMessage());
		}
	}
}
