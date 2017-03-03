package fr.esiea.praddaude.game;

import java.util.ArrayList;
import java.util.List;

import fr.esiea.praddaude.dictionary.Word;

public class Player 
{
	
	private String name;
	private boolean bot;
	private List<Word> listWord = new ArrayList<Word>();
	
	public Player(String name, boolean isBot)
	{
		this.name = name;
		this.bot = isBot;
	}
	
	public void resetWords()
	{
		listWord.clear();
	}
	
	public int getPoints()
	{
		int pts = 0;
		for (Word word : listWord) 
    	{
            pts += word.getValue();
        }
		return pts;
	}
	
	public boolean isBot()
	{
		return bot;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addWord(Word w)
	{
		listWord.add(w);
	}
	
	public int getNbWord()
	{
		return listWord.size();
	}
	
	public void delWordById(int i)
	{
		listWord.remove(i);
	}
	
	public Word getWordByIndex(int i)
	{
		if (i >= listWord.size()) return new Word("","",0);
		return listWord.get(i);
	}
	
}
