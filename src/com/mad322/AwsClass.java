package com.mad322;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mad322.MysqlCon;



@Path("/aws")
public class AwsClass {
	

	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;

	PreparedStatement preparedStatement = null;

	JSONObject mainObj = new JSONObject();
	JSONArray jsonArray = new JSONArray();
	JSONObject childObj = new JSONObject();

	
	@GET
	@Path("/getem")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOffice() {
		MysqlCon connection = new MysqlCon();

		con = connection.getConnection();

		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery("Select first_name from employee");

			while (rs.next()) {
				childObj = new JSONObject();

				childObj.accumulate("EMP_ID", rs.getInt("EMP_ID"));
				childObj.accumulate("END_DATE", rs.getString("END_DATE"));
				childObj.accumulate("FIRST_NAME", rs.getString("FIRST_NAME"));
				childObj.accumulate("LAST_NAME", rs.getString("LAST_NAME"));
				childObj.accumulate("START_DATE", rs.getString("START_DATE"));
				childObj.accumulate("TITLE", rs.getString("TITLE"));
				childObj.accumulate("ASSIGNED_BRANCH_ID", rs.getInt("ASSIGNED_BRANCH_ID"));
				childObj.accumulate("DEPT_ID", rs.getInt("DEPT_ID"));
				childObj.accumulate("SUPERIOR_ID", rs.getInt("SUPERIOR_ID"));

				jsonArray.put(childObj);
			}

			mainObj.put("Employee", jsonArray);
		} catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
		} finally {
			try {
				con.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("Finally Block SQL Exception : " + e.getMessage());
			}
		}

		return Response.status(200).entity(mainObj.toString()).build();

	}



	
	
	
}
