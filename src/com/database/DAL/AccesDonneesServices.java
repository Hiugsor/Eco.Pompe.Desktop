package com.database.DAL;
import java.sql.*;
import java.util.*;
public class AccesDonneesServices {

	private AccesDonneesServices()
	{}

	/**
	 * Insert all service in services table
	 * @param noms
	 */
	public static void insertServices(List<String> noms){
		
		String requete ="INSERT INTO services (types_services) VALUES(?)";
		//int[] count= new int[0];

		
		Connection connection = ConnexionManager.GetInstance().GetConnection();
		
		try {
			PreparedStatement requeteSql = connection.prepareStatement(requete);
			for (String nom : noms) {
				requeteSql.setString(1, nom);
				requeteSql.addBatch();

			}
			int[] count =requeteSql.executeBatch();
			//requeteSql.executeBatch();
			System.out.println(count.length);
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
		}
		//ResultSet resultat = null;
	}
	
}
