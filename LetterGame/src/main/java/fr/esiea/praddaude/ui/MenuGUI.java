package fr.esiea.praddaude.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import fr.esiea.praddaude.game.Game;
import fr.esiea.praddaude.game.Player;

public class MenuGUI extends GUI implements ActionListener
{
	private JButton newGameButton, rulesButton;
	private List<JComboBox> typePlayer = new ArrayList<JComboBox>(); //rawTypes,unchecked
	private List<JTextField> namePlayer = new ArrayList<JTextField>();
	private int maxPlayer;
	private String[] botNames = { "© Skewdoric", "© Tarkhrod", "© Bolog", "© Brenriss", "© Tolalior", "© Mereliora", "© Lurggar", "© Vilevard", "© Wulenol", "© Durbagar" };
	private JFrame frame;
	
	public MenuGUI()
	{
		int i;
		maxPlayer = 8;
		frame = new JFrame("Letter Game by Syndrome5");
		frame.setMinimumSize(new Dimension(640,480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(13,3));
		
		newGameButton = new JButton("Nouvelle partie");
		newGameButton.setEnabled(false);
		newGameButton.setActionCommand("Nouvelle partie");
		newGameButton.addActionListener(this);
		
		rulesButton = new JButton("Règles");
		rulesButton.setActionCommand("Règles");
		rulesButton.addActionListener(this);
		
		String[] typePlayerString = { "N/A", "Joueur", "Ordinateur" };
		for (i=0; i<maxPlayer; i++)
		{
			typePlayer.add(new JComboBox(typePlayerString));
			typePlayer.get(typePlayer.size()-1).addActionListener (
	            new ActionListener()
	            {
	                public void actionPerformed(ActionEvent e)
	                {
	                    majNewGameButton();
	                    majTextFields((JComboBox)e.getSource());
	                }
	            }            
	        );
			namePlayer.add(new JTextField());
			namePlayer.get(namePlayer.size()-1).setEnabled(false);
		}
		
		frame.add(addCompo(new JLabel("Letter Game - by Syndrome5")));
		frame.add(addCompo(new JLabel("")));
		for (i=0; i<maxPlayer; i++)
		{
			frame.add(makeBoxLayout(50, 30, new JLabel("Joueur " + (i+1)), typePlayer.get(i), namePlayer.get(i)));
		}
		frame.add(addCompo(new JLabel("")));
		frame.add(makeBoxLayout(80, 30, addCompo(rulesButton), addCompo(newGameButton)));
		frame.add(addCompo(new JLabel("")));
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void majNewGameButton()
	{
		int nbJ = 0;
		for (JComboBox jcb : typePlayer)
		{
			if ("N/A" != (String)jcb.getSelectedItem())
			{
				nbJ++;
			}
		}
		if (nbJ >= 2) newGameButton.setEnabled(true);
		else newGameButton.setEnabled(false); 
	}
	
	public void majTextFields(JComboBox jcb)
	{
		Iterator<JComboBox> it1 = typePlayer.iterator();
	    Iterator<JTextField> it2 = namePlayer.iterator();
	    Random r = new Random();
	    while (it1.hasNext()) 
	    {
	        JComboBox combo = it1.next();
	        JTextField textf = it2.next();
	        if ((String)combo.getSelectedItem() == "Joueur") textf.setEnabled(true);
	        else textf.setEnabled(false);
	        if ((String)combo.getSelectedItem() == "Ordinateur")
	        {
	        	do
	        	{
	        		textf.setText(botNames[r.nextInt(botNames.length-1)]);
	        	} while (differentName() == false);
	        }
	        else
	        {
	        	if (jcb.equals(combo)) textf.setText("");
	        }
	    }
	}
	
	public boolean differentName()
	{
		for (JTextField textf : namePlayer)
		{
			if (textf.getText().equals("") == false)
			{
				for (JTextField str : namePlayer)
				{
					if (str.getText().equals("") == false)
					{
				        if (textf.getText().equals(str.getText()) && textf.equals(str) == false)
				        {
				        	return false;
				        }
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case "Nouvelle partie":
				Iterator<JComboBox> it1 = typePlayer.iterator();
			    Iterator<JTextField> it2 = namePlayer.iterator();
			    List<Player> players = new ArrayList<Player>();
			    while (it1.hasNext()) 
			    {
			        JComboBox combo = it1.next();
			        JTextField textf = it2.next();
			        if ((String)combo.getSelectedItem() == "Joueur")
			        {
			        	if (textf.getText().equals("") || textf.getText().contains(" "))
			        	{
			        		JOptionPane.showMessageDialog(null, "Un joueur ne possède pas de nom ou un espace s'y trouve (interdit).", "Erreur", JOptionPane.ERROR_MESSAGE);
			        		return;
			        	}
			        	else
			        	{
			        		players.add(new Player((String)textf.getText(), false));
			        	}
			        }
			        else if ((String)combo.getSelectedItem() == "Ordinateur")
			        {
			        	players.add(new Player((String)textf.getText(), true));
			        }
			    }
			    if (differentName() == false)
			    {
			    	JOptionPane.showMessageDialog(null, "Des joueurs ont le même nom. Chaque joueur doit posséder un nom unique.", "Erreur", JOptionPane.ERROR_MESSAGE);
			    	return;
			    }
			    frame.setVisible(false);
			    frame.dispose();
			    Game game = new Game(players);
			    //frame.setVisible(true);
			break;
			case "Règles":
				JOptionPane.showMessageDialog(null, "Règles du jeu Letter Game by Syndrome5\n\n/!\\ Vous ne pouvez proposer que des noms communs /!\\\n\nBut : Réaliser 10 mots différents\n\nDéroulement du jeu :\n1) Chacun pioche une lettre. Celui qui commence est celui qui possède la plus petite lettre (plus proche de 'a'). Les lettres sont mises dans le pot commun.\n2) A chaque début de tour, le joueur tire deux lettres et les rajoute dans le pot commun. Il peut alors former tous les mots possibles avec les lettres disponibles. Pour chaque mot formé, il tire une nouvelle lettre pour le pot commun.\n3) Une fois terminé, il passe son tour.\n\nParticularités :\n - Vous pouvez voler les mots d'un adversaire en lui rajoutant des lettres du pot commun\n - Un mot composé compte pour double", "Règles", JOptionPane.INFORMATION_MESSAGE);
			break;
		}
	}
	
}
