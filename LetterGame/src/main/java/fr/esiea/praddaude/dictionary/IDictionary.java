package fr.esiea.praddaude.dictionary;

public interface IDictionary 
{
	boolean isWord(String word);
    boolean isNewWord(String word);
    public int countValue(String str);
    public String compressWord(String str);
    public int getNbWord();
    public void showAll();
}
