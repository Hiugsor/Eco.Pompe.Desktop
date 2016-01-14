package com.database.DAL;
import java.sql.*;
import java.util.*;
public class AccesDonneesNonTravailler {

	private AccesDonneesNonTravailler()
	{}

	
	public static void insertNonTravailler(int idStation, List<String> NomJours){
		
		
		String requete ="INSERT INTO nontravailler (id_Station,id_Jour)"
					  + "VALUES(?,(SELECT id_Jour FROM jours WHERE jour LIKE ?));";
		
		Connection connection = ConnexionManager.GetInstance().GetConnection();
		
		try {
			PreparedStatement requeteSql = connection.prepareStatement(requete);
			if (!NomJours.isEmpty()) {
				for (String jour : NomJours) {
					requeteSql.setInt(1, idStation);
					requeteSql.setString(2, jour);
					requeteSql.addBatch();
				}
				
				
				int[] count =requeteSql.executeBatch();
				
				System.out.println(count.length);
			}
			
			
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
		}
		
	}
	
}
