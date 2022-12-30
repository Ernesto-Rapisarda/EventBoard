package it.sad.students.eventboard.persistenza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdBroker {
	//Standard SQL (queste stringhe andrebbero scritte in un file di configurazione
	//private static final String query="SELECT NEXT VALUE FOR SEQUENZA_ID AS id";
	//private String sequence="";
	
	//private static final String query = "SELECT nextval('event_id') AS id";//postgresql


	public static Long getNewEventID(Connection connection){
		String query = "SELECT nextval('event_id') AS id";
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
