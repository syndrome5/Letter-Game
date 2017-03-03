package fr.esiea.praddaude.dictionary;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DictionaryTest 
{

    private Dictionary dictionary;

    @Before
    public void setup() 
    {
        dictionary = new Dictionary();
    }

    @Test
    public void testIsWord() 
    {
        assertTrue(dictionary.isWord("maman")==1); // value = 1
        assertTrue(dictionary.isWord("comic book")==2); // value = 2
        assertFalse(dictionary.isWord("namam")>0); // got a value ?
    }
    
    @Test
    public void testIsNewWord() // used to add elements
    {
    	assertFalse(dictionary.isNewWord("maman")); // already exist
    	assertFalse(dictionary.isNewWord("a")); // word < 2 character forbidden
    	assertTrue(dictionary.isNewWord("wordnotinthelist"));
    }
}
