package fr.esiea.praddaude.dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.esiea.praddaude.game.Game;

public class Dictionary 
{
    private List<Word> content = new ArrayList<Word>();
    
    public Dictionary()
    {
		try
		{
			InputStream ips = new FileInputStream("src/main/resources/dico.txt"); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String mot, cMot; // compressed mot
			int value;
			while ((mot = br.readLine()) != null)
			{
				value = countValue(mot);
				cMot = compressWord(mot);
				if (isNewWord(cMot) == true) 
				{
					content.add(new Word(mot, cMot, value));
				}
			}
			br.close(); 
		}		
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
    }
    
    public int countValue(String str)
    {
    	if (str.contains("-") || str.contains(" ")) return 2;
    	else return 1;
    }
    
    public String compressWord(String str)
    {
    	str = str.replace('é', 'e');
        str = str.replace('è', 'e');
        str = str.replace('ê', 'e');
        str = str.replace('ë', 'e');
        str = str.replace('î', 'i');
        str = str.replace('û', 'u');
        str = str.replace('ï', 'i');
        str = str.replace('â', 'a');
        str = str.replace('ö', 'o');
        str = str.replace('ç', 'c');
        str = str.replace('ô', 'o');
        str = str.replace('ç', 'c');
        str = str.replace('ü', 'u');
        str = str.replace('ñ', 'n');
        str = str.replace('à', 'a');
        str = str.replace('ã', 'a');
        
        str = str.replace(".", "");
        str = str.replace("'", "");
        str = str.replace("-", "");
        str = str.replace(" ", "");
        
        return str;
    }
    
    public boolean isNewWord(String word)
    {
    	for (Word test : content) 
    	{
            if (test.getCW().equals(word) || word.length() < 2) return false; // if word already exists or too little
        }
    	return true;
    }
    
    public int isWord(String word)
    {
    	for (Word test : content) 
    	{
            if (test.getCW().equals(word) || test.getRW().equals(word)) return test.getValue();
        }
    	return 0;
    }
    
    public List<String> findWord(List<Character> letters, Game g)
    {
    	List<String> validate = new ArrayList<String>();
    	List<Character> temp = new ArrayList<Character>();
    	for (Word test : content) 
    	{
    		char[] testC = test.getCW().toCharArray();
    		if (testC.length > 1 && letters.size() >= testC.length)
    		{
    			temp.clear();
    			temp = findWordByLetters(new ArrayList<Character>(letters),testC);
    			if (temp.size() == 0 && letters.size() != 0) // found and no more letters
    			{
    				letters.clear();
    				validate.add(test.getCW());
    				g.pickLetterFromBagToPot();
    			}
    			else if (temp.size()==0) // security against temp.get(0) unexistent
    			{
    				
    			}
    			else if (temp.get(0).equals('?')==false)
    			{
    				letters.clear();
    				for (Character c : temp)
    				{
    					letters.add(c);
    				}
    				validate.add(test.getCW());
    				g.pickLetterFromBagToPot();
    			}
    		}
    	}
    	return validate;
    }
    
    public List<Character> findWordByLetters(List<Character> letters, char[] testC)
    {
    	boolean success;
    	for (int i=0;i<testC.length;i++)
		{
    		success=false;
			for (int j=0;j<letters.size();j++)
			{
				if (testC[i] == (char)letters.get(j))
				{
					letters.remove(j);
					success=true;
					break;
				}
			}
			if (success==false)
			{
				List<Character> fail = new ArrayList<Character>();
				fail.add('?');
				return fail;
			}
		}
    	return letters;
    }
    
    public int getNbWord()
    {
    	return content.size();
    }
    
}
