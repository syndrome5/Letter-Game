package fr.esiea.praddaude.dictionary;

public class Word 
{
	
	private String realWord, compressedWord;
	private int value;
	
	public Word(String rW, String cW, int val)
	{
		realWord = rW;
		compressedWord = cW;
		value = val;
	}
	
	public String getRW()
	{
		return realWord;
	}
	
	public String getCW()
	{
		return compressedWord;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setRW(String str)
	{
		realWord = str;
	}
	
	public void setCW(String str)
	{
		compressedWord = str;
	}
	
	public void setValue(int val)
	{
		value = val;
	}
	
}
