package fr.esiea.praddaude.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import fr.esiea.praddaude.dictionary.Word;
import fr.esiea.praddaude.game.Game;


public class GameGUI extends GUI implements ActionListener 
{
	private JButton passButton, tryWordButton, pickLetterButton;
	private JLabel infoLabel;
	private JPanel potPanel, namesPanel, wordsPanel[];
	private JTextField motTF;
	private JFrame frame;
	private GridLayout generalLayout;
	private Game game;
	private String[] infoStr = { "Au tour de ", ", piochez dans le sac !", "L'ordre a été établi !", "Le mot ", " n'existe pas ou il n'y a pas assez de lettres pour le former.", " vient de marquer ", " point(s) avec le mot: ", "Félicitations à " };
	private int sizeW, sizeH;
	
	public GameGUI(Game g)
	{
		
		game = g;
		frame = new JFrame("Letter Game by Syndrome5");
		frame.setMinimumSize(new Dimension(100+500/game.getNbPlayer(),600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(25,2+game.getNbPlayer()));
		sizeW = frame.getSize().width;
		sizeH = frame.getSize().height;

		frame.add(addCompo(new JLabel("")));
		namesPanel = new JPanel(new GridBagLayout());
		majTour();
		frame.add(namesPanel);
		frame.add(addCompo(new JLabel("")));
		frame.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		wordsPanel = new JPanel[10];
		for (int i=0;i<10;i++) wordsPanel[i] = new JPanel(new GridBagLayout());
		majWords();
		for (int i=0;i<10;i++) frame.add(wordsPanel[i]);
		frame.add(addCompo(new JLabel("")));
		frame.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		generalLayout = new GridLayout(1,1);
		potPanel = new JPanel(generalLayout);
		majCommonPot();
		frame.add(potPanel);
		frame.add(addCompo(new JLabel("")));
		frame.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		tryWordButton = new JButton("Essayer le mot");
		tryWordButton.setActionCommand("Essayer le mot");
		tryWordButton.setEnabled(false);
		tryWordButton.addActionListener(this);
		
		passButton = new JButton("Passer");
		passButton.setActionCommand("Passer");
		passButton.setEnabled(false);
		passButton.addActionListener(this);
		
		pickLetterButton = new JButton("Piocher");
		pickLetterButton.setActionCommand("Piocher");
		pickLetterButton.addActionListener(this);
		
		motTF = new JTextField("",10);
		motTF.setEnabled(false);
		
		frame.add(makeBoxLayout(10,15, new JLabel("Entrez votre mot: "), motTF, tryWordButton));
		frame.add(Box.createRigidArea(new Dimension(0,7)));
		frame.add(makeBoxLayout(20,50, pickLetterButton, passButton));
		frame.add(addCompo(new JLabel("")));
		frame.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		infoLabel = new JLabel("Letter Game informations");
		frame.add(addCompo(infoLabel));
		
		frame.pack();
		frame.setVisible(true);
		
		infoLabel.setText(infoStr[0] + game.getNameByTour() + infoStr[1]);
		if (game.isBot())
		{
			pickLetterButton.doClick();
		}
	}
	
	public void majTour()
	{
		namesPanel.removeAll();
		
		List<Component> cmp = new ArrayList<Component>();
		int counter = 0;
		for (String names : game.getNames())
		{
			JLabel jlb = new JLabel(names);
			if (game.getTour() == counter) jlb.setBorder(BorderFactory.createLineBorder(Color.red));
			cmp.add(jlb);
			counter++;
		}
		namesPanel.add(makeBoxLayoutSmart(100, sizeW, cmp));
		
		namesPanel.revalidate();
		namesPanel.repaint();
	}
	
	public void majWords()
	{
		List<Component> cmp = new ArrayList<Component>();
		for (int j=0;j<10;j++)
		{
			wordsPanel[j].removeAll();
			
			cmp.clear();
			for (Word w : game.getWordPerLine(j))
			{
				cmp.add(new JLabel(w.getRW()));
			}
			wordsPanel[j].add(makeBoxLayoutSmart(100, sizeW, cmp));
			
			wordsPanel[j].revalidate();
			wordsPanel[j].repaint();
		}
	}
	
	public void majCommonPot()
	{
		potPanel.removeAll();
		
		int counter = 0;
		String listCharacters = "";
		int nbRow=1;
		for (Character c : game.getCharacters())
		{
			listCharacters += c + "  ";
			if (counter*17 >= frame.getPreferredSize().getWidth())
			{
				potPanel.add(makeBoxLayout(30,0,new JLabel(listCharacters)));
				counter = 0;
				nbRow++;
				listCharacters = "";
			}
			else counter++;
		}
		if (counter != 0) potPanel.add(makeBoxLayout(30,0,new JLabel(listCharacters)));
		else if (game.getCharacters() != null) nbRow--;
		generalLayout.setRows(nbRow);
		
		potPanel.revalidate();
		potPanel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
			case "Essayer le mot":
				int value=0;
				if (motTF.getText().length() > 1)
				{
					if ((value=game.tryWord(motTF.getText())) > 0 && (game.availableCharacters(motTF.getText()) || game.availableCharactersSteal(motTF.getText())))
					{
						game.addWordByTour(motTF.getText(),value);
						if (!game.isBot()) game.pickLetterFromBagToPot();
						infoLabel.setText(game.getNameByTour() + infoStr[5] + value + infoStr[6] + motTF.getText());
						motTF.setText("");
						if (game.testWinner())
						{
							JOptionPane.showMessageDialog(null, game.getNameByTour() + " gagne la partie !\n\nCliquez sur passer pour retourner au menu.", "C'est gagné !", JOptionPane.INFORMATION_MESSAGE);
							infoLabel.setText(infoStr[7] + game.getNameByTour());
							game.hardResetTour();
							game.setFinished();
							pickLetterButton.setEnabled(false);
							tryWordButton.setEnabled(false);
							motTF.setEnabled(false);
						}
						majCommonPot();
						majTour();
						majWords();
					}
					else
					{
						if (!game.isBot()) JOptionPane.showMessageDialog(null, infoStr[3] + motTF.getText() + infoStr[4], "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			break;
			case "Passer":
				if (game.getFinished() == true)
				{
					frame.setVisible(false);
					frame.dispose();
					new MenuGUI();
					return;
				}
				if (game.getOrderSet() == false)
				{
					game.switchOrderSet();
					game.setLettersToPot();
					game.clearAllWordAllPlayers();
					pickLetterButton.setEnabled(true);
					tryWordButton.setEnabled(true);
					motTF.setEnabled(true);
				}
				else
				{
					if (game.getTour()+1 == game.getNbPlayer()) game.hardResetTour();
					motTF.setText("");
					pickLetterButton.setEnabled(true);
				}
				game.nextTour();
				infoLabel.setText(infoStr[0] + game.getNameByTour() + infoStr[1]);
				
				if (game.isBot())
				{
					pickLetterButton.doClick();
				}
				
				majCommonPot();
				majTour();
				majWords();
			break;
			case "Piocher":
				if (game.getOrderSet() == false)
				{
					game.pickLetterFromBagToCollection();
					game.nextTour();
					if (game.getTour() == game.getNbPlayer())
					{
						game.hardResetTour();
						pickLetterButton.setEnabled(false);
						passButton.setEnabled(true);
						infoLabel.setText(infoStr[2]);
						game.putNamesByFirstCharacter();
					}
					else infoLabel.setText(infoStr[0] + game.getNameByTour() + infoStr[1]);
				}
				else
				{
					pickLetterButton.setEnabled(false);
					game.pickLetterFromBagToPot();
					game.pickLetterFromBagToPot();
				}
				majCommonPot();
				majTour();
				majWords();
				
				if (game.getTour() != -1)
				{
					if (game.isBot())
					{
						if (game.getOrderSet() == false)
						{
							pickLetterButton.doClick();
						}
						else
						{
							motTF.setEnabled(false);
							List<String> tests = game.botLaunch();
							for (String str : tests)
							{
								motTF.setText(str);
								tryWordButton.doClick();
								if (game.getFinished() == true) return;
							}
							motTF.setEnabled(true);
							passButton.doClick();
						}
					}
				}
			break;
		}
	}

}
