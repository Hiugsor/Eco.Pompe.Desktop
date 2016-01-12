package DAL;
import java.sql.*;

import java.util.*;

import javax.swing.event.ListSelectionEvent;

import Boite_a_outil.traitementString;

public class AccesDonneesVendre {

	private AccesDonneesVendre()
	{}


	public static void insertVendre0(int idStation, String PrixCarbus){


		String requete ="INSERT INTO Vendre(prix,Rupture,id_Station,id_Carburant) "
				+ "VALUES(?,?,?,?);";

		String[] CompoCarbu = PrixCarbus.split(";");
		//System.out.println("prixcarburant " + PrixCarbus);

		Connection connection = ConnexionManager.GetInstance().GetConnection();

		try {
			//System.out.println("ici");
			PreparedStatement requeteSql = connection.prepareStatement(requete);
			//System.out.println(CompoCarbu.length);

			if (CompoCarbu.length>0) {
				//for (String PrixCarbu : PrixCarbus) {
				requeteSql.setFloat(1,Float.parseFloat(CompoCarbu[2])); //prix -> valeur
				requeteSql.setInt(2, 0);
				requeteSql.setInt(3, idStation);
				requeteSql.setInt(4, Integer.parseInt(CompoCarbu[1])); //id_carburant
				requeteSql.addBatch();
				//}

				//int[] count =requeteSql.executeBatch();
				requeteSql.executeBatch();

				//System.out.println(count.length);
			}


		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
		}

	}

	public static void insertVendre(int idStation,String colonne){
		//System.out.println("LA insertVendre");	

		//remplissage de la table non vendre
		try {
			//String colonne= "Gazole,1,2015-12-10T10:12:48,1027/SP95,2,2015-12-10T10:12:48,1259"; // a récuperer du fichier CSV
			//int idStation = 9999999; // a modifier
			List<String> listeCarbus = traitementString.recupererDonnées(colonne, "/");
			//System.out.println("listeCarbus.size"+listeCarbus.size());
			//if(!listeCarbus.isEmpty()){


			for (String listeCarbu : listeCarbus) {
				//System.out.println("listeCarbu "+listeCarbu);
				if (!listeCarbu.equals("")) {
					insertVendre0(idStation,listeCarbu);
				}
				
			}
			//			}
			//			else{
			//				System.out.println("PAS DE DEDANS");	
			//			}

			//AccesDonneesProposer.insertProposer(idStation, nomServices);


		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
