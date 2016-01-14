package com.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.main.GUI;

public class InsertStationFrame {

	
	
	public InsertStationFrame(JPanel  tabListe, int indexTabList,String Adresse,String CP,String Ville )
	{
		
		
		JPanel blocBox = new JPanel();
		blocBox.setBackground(Color.BLACK);
		GridBagConstraints gbc_blocBox = new GridBagConstraints();
		gbc_blocBox.insets = new Insets(0, 0, 5, 5);
		gbc_blocBox.fill = GridBagConstraints.BOTH;
		gbc_blocBox.gridx = 1;
		gbc_blocBox.gridy = indexTabList;		
		blocBox.setLayout(new BorderLayout(0, 0));
		tabListe.add(blocBox, gbc_blocBox);
		
		JLabel top = new JLabel("");
		top.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/FrameBoxListe/NorthFrameBox.png")));
		blocBox.add(top, BorderLayout.NORTH);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/FrameBoxListe/SouthFrameBox.png")));
		blocBox.add(label, BorderLayout.SOUTH);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/FrameBoxListe/WestFrameBox.png")));
		blocBox.add(label_1, BorderLayout.WEST);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/FrameBoxListe/EastFrameBox.png")));
		blocBox.add(lblNewLabel_1, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.BLACK);
		blocBox.add(panel_3, BorderLayout.CENTER);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{79, 131, 485, 244, 0};
		gbl_panel_3.rowHeights = new int[]{120, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel distance = new JLabel(donneeTest.distanceAléa());
		distance.setForeground(Color.GRAY);
		distance.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_distance = new GridBagConstraints();
		gbc_distance.insets = new Insets(0, 0, 0, 5);
		gbc_distance.gridx = 0;
		gbc_distance.gridy = 0;
		panel_3.add(distance, gbc_distance);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/Agip_logomini.png")));
		GridBagConstraints gbc_logo = new GridBagConstraints();
		gbc_logo.insets = new Insets(0, 0, 0, 5);
		gbc_logo.gridx = 1;
		gbc_logo.gridy = 0;
		panel_3.add(logo, gbc_logo);
		
		
				
		JLabel info = new JLabel("<html><h2>"+ donneeTest.adresseTitreAléa()+"</h2>"+ Adresse+"<BR>"+CP+" "+Ville+"</html>");
		info.setForeground(Color.GRAY);
		info.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_info = new GridBagConstraints();
		gbc_info.insets = new Insets(0, 0, 0, 5);
		gbc_info.gridx = 2;
		gbc_info.gridy = 0;
		panel_3.add(info, gbc_info);
		
		
		JLabel prix = new JLabel(donneeTest.prixAléa());
		prix.setForeground(Color.WHITE);
		prix.setFont(new Font("Tahoma", Font.PLAIN, 28));
		GridBagConstraints gbc_prix = new GridBagConstraints();
		gbc_prix.gridx = 3;
		gbc_prix.gridy = 0;
		panel_3.add(prix, gbc_prix);
		
	}
}
