package it.sad.students.eventboard.persistenza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdBroker {
	public static Long getNewPersonID(Connection connection){
		String query = "SELECT nextval('person_id') AS id";
		return getId(connection,query);

	}

	public static Long getNewRoleID(Connection connection){
		String query = "SELECT nextval('role_id') AS id";
		return getId(connection,query);

	}
	public static Long getNewEventID(Connection connection){
		String query = "SELECT nextval('event_id') AS id";
		return getId(connection,query);

	}
	public static Long getNewPositionID(Connection connection){
		String query = "SELECT nextval('position_id') AS id";
		return getId(connection,query);

	}
	public static Long getNewEventTypeID(Connection connection){
		String query = "SELECT nextval('event_type_id') AS id";
		return getId(connection,query);

	}
	public static Long getNewCommentID(Connection connection){
		String query = "SELECT nextval('comment_id') AS id";
		return getId(connection,query);

	}

	public static Long getNewReportID(Connection connection){
		String query = "SELECT nextval('report_id') AS id";
		return getId(connection,query);

	}
	public static Long getNewReportTypeID(Connection connection){
		String query = "SELECT nextval('report_type_id') AS id";
		return getId(connection,query);

	}


	private static Long getId(Connection connection,String query){
		Long id = null;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			
			ResultSet result = statement.executeQuery();
			result.next();
			id = result.getLong("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}

}
