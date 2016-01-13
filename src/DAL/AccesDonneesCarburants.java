package DAL;
import java.sql.*;
import java.util.*;
public class AccesDonneesCarburants {

	private AccesDonneesCarburants()
	{}

	/**
	 * Insert all carburant in carburants table
	 * @param noms
	 */
	public static void insertCarburants(List<String> carbus){
		
		String requete ="INSERT INTO carburants (nom) VALUES(?)";
		
		Connection connection = ConnexionManager.GetInstance().GetConnection();
		
		try {
			PreparedStatement requeteSql = connection.prepareStatement(requete);
			for (String nom : carbus) {
				requeteSql.setString(1, nom);
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
