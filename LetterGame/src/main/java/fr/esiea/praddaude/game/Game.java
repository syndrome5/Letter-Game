package fr.esiea.praddaude.game;

import fr.esiea.praddaude.dictionary.Dictionary;
import fr.esiea.praddaude.dictionary.Word;
import fr.esiea.praddaude.ui.GameGUI;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class Game
{
	
	private List<Player> players = new ArrayList<Player>();
	private List<Character> pot = new ArrayList<Character>();
	private Bag bag;
	private Dictionary dico;
	private int tour;
	private boolean orderSet, finished;
	
	public Game(List<Player> players)
	{
		this.players = players;
		tour = 0;
		orderSet = false;
		finished = false;
		
		Loading load = new Loading(this);
		load.execute();
	}
	
	public boolean getOrderSet()
	{
		return orderSet;
	}
	
	public void setFinished()
	{
		finished = true;
	}
	
	public boolean getFinished()
	{
		return finished;
	}
	
	public List<Character> getCharacters()
	{
		return pot;
	}
	
	public void addWordByTour(String str, int val)
	{
		players.get(tour).addWord(new Word(str, dico.compressWord(str), val));
	}
	
	public List<String> getNames()
	{
		List<String> strs = new ArrayList<String>();
		for (Player p : players)
		{
			strs.add(p.getName());
		}
		return strs;
	}
	
	public List<Word> getWordPerLine(int index)
	{
		List<Word> strs = new ArrayList<Word>();
		for (int i=0;i<players.size();i++)
		{
			strs.add(players.get(i).getWordByIndex(index));
		}
		return strs;
	}
	
	public boolean testWinner()
	{
		if (players.get(tour).getPoints()>=10) // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		{
			return true;
		}
		return false;
	}
	
	public int tryWord(String w)
	{
		return dico.isWord(dico.compressWord(w.toLowerCase()));
	}
	
	public boolean isBot()
	{
		return players.get(tour).isBot();
	}
	
	public List<String> botLaunch()
	{
		List<String> tests = new ArrayList<String>();
		tests = dico.findWord(new ArrayList<Character>(pot), this);
		return tests;
	}
	
	public boolean availableCharactersSteal(String w)
	{
		w = dico.compressWord(w.toLowerCase());
		for (int i=0;i<players.size();i++)
		{
			if (i != tour)
			{
				for (int j=0;j<players.get(i).getNbWord();j++)
				{
					int rep;
					if ((rep=w.indexOf(players.get(i).getWordByIndex(j).getCW())) != -1)
					{
						String t = "";
						if (rep != 0) t+=w.substring(0, rep);
						if (rep+players.get(i).getWordByIndex(j).getCW().length()!=w.length()) t+=w.substring(rep+players.get(i).getWordByIndex(j).getCW().length(), w.length());
						if (availableCharacters(t))
						{
							players.get(i).delWordById(j);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean availableCharacters(String w)
	{
		boolean testPassed = false;
		List<Character> potTest = new ArrayList<Character>();
	    for (Character c : pot) 
	    {
	        potTest.add(c);
	    }
		w = dico.compressWord(w.toLowerCase());
		String[] wSplitted = w.split("");
		for (int i=0;i<wSplitted.length;i++)
		{
			testPassed = false;
			for (int j=0;j<potTest.size();j++)
			{
				if (potTest.get(j).equals((Character)wSplitted[i].charAt(0)))
				{
					potTest.remove(j);
					testPassed = true;
					break;
				}
			}
			if (testPassed == false)
			{
				return false;
			}
		}
		// If passed so it's okay
		for (int i=0;i<wSplitted.length;i++)
		{
			for (int j=0;j<pot.size();j++)
			{
				if (pot.get(j).equals((Character)wSplitted[i].charAt(0)))
				{
					pot.remove(j);
					break;
				}
			}
		}
		return true;
	}
	
	public String getNameByTour()
	{
		return players.get(tour).getName();
	}
	
	public void putNamesByFirstCharacter()
	{
		char best,temp;
		int repere;
		List<Player> newP = new ArrayList<Player>();
		while(players.size()!=0)
		{
			best = 254;
			repere = -1;
			for (int i=0; i<players.size(); i++)
			{
				temp = players.get(i).getWordByIndex(0).getRW().charAt(0);
				if (temp < best)
				{
					repere = i;
					best = temp;
				}
			}
			newP.add(players.get(repere));
			players.remove(repere);
		}
		players = newP;
	}
	
	public void setLettersToPot()
	{
		for (Player p : players)
		{
			pot.add((Character)p.getWordByIndex(0).getRW().charAt(0));
		}
	}
	
	public void clearAllWordAllPlayers()
	{
		for (Player p : players)
		{
			p.resetWords();
		}
	}
	
	public void switchOrderSet()
	{
		if (orderSet == true) orderSet=false;
		else orderSet=true;
	}
	
	public void nextTour()
	{
		tour++;
	}
	
	public int getTour()
	{
		return tour;
	}
	
	public void hardResetTour()
	{
		tour = -1;
	}
	
	public int getNbPlayer()
	{
		return players.size();
	}
	
	public void pickLetterFromBagToPot()
	{
		Character c = (Character)bag.pickLetter();
		pot.add(c);
	}
	
	public void pickLetterFromBagToCollection()
	{
		Character c = (Character)bag.pickLetter();
		players.get(tour).addWord(new Word(c.toString(),"",(char)c));
	}
	
	class Loading extends SwingWorker<Integer,Integer>
	{
		private JFrame frame;
		private Game g;
		
		public Loading(Game g)
		{
			this.g= g;
			frame = new JFrame("Chargement...");
		    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		    frame.setSize(200, 100);
		    frame.setResizable(false);
		    frame.setLocationRelativeTo(null);
		    frame.setLayout(new FlowLayout(FlowLayout.CENTER));
		    JLabel jlb = new JLabel("Chargement du jeu, veuillez patienter...");
		    frame.add(jlb);
		    frame.pack();
		    frame.setVisible(true);
		}
		
		@Override
	    protected Integer doInBackground()
		{
			bag = new Bag(10000, 0.2);
			dico = new Dictionary();
	        return 1;
	    }

	    @Override
	    protected void done() 
	    {
	    	frame.setVisible(false);
			frame.dispose();
			
			GameGUI gameGUI = new GameGUI(g);
	    }
	}
	

}