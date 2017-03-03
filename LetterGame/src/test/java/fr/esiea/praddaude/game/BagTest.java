package fr.esiea.praddaude.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BagTest 
{

    private Bag bag;

    @Before
    public void setup() 
    {
        bag = new Bag(10000, 0.2);
    }

    @Test
    public void testCorrectFrequency()
    {
    	assertTrue(bag.correctFrequency(0.2)); // 0,2% marge d'erreur
    }
    
}