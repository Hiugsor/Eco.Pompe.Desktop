package com.database.DAL;
import java.sql.*;
import java.util.*;
public class AccesDonneesJours {

	private AccesDonneesJours()
	{}

	
	public static void insertJours(List<String> jours){
		
		String requete ="INSERT INTO jours (jour) VALUES(?)";
		
		Connection connection = ConnexionManager.GetInstance().GetConnection();
		
		try {
			PreparedStatement requeteSql = connection.prepareStatement(requete);
			for (String jour : jours) {
				requeteSql.setString(1, jour);
				requeteSql.addBatch();

			}
			int[] count =requeteSql.executeBatch();
			
			System.out.println(count.length);
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
		}
		
	}
	
}
