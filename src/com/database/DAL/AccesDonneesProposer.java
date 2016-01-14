package com.database.DAL;
import java.sql.*;
import java.util.*;
public class AccesDonneesProposer {

	private AccesDonneesProposer()
	{}


	public static void insertProposer(int idStation, List<String> nomServices){


		String requete ="INSERT INTO proposer (id_Station,id_Service) "
				+ "VALUES(?,(SELECT id_Service FROM services WHERE types_services LIKE ?));";

		Connection connection = ConnexionManager.GetInstance().GetConnection();

		try {
			PreparedStatement requeteSql = connection.prepareStatement(requete);
			if (!nomServices.isEmpty()) {
				for (String service : nomServices) {
					if(!service.isEmpty()){
						//System.out.println(service);
						requeteSql.setInt(1, idStation);
						requeteSql.setString(2, service);
						//System.out.println(requeteSql);
						requeteSql.addBatch();
						//continue;
					}
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
