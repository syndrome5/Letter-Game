package fr.esiea.praddaude.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bag 
{
	
	private List<Character> listChar = new ArrayList<Character>();
	private List<Double> freqLetter = new ArrayList<Double>();
	private double errorAdmitted;
	
	public Bag(int nb, double eA)
	{
		errorAdmitted = eA;
		freqLetter.addAll(Arrays.asList(8.40, 1.06, 3.03, 4.18, 17.26, 1.12, 1.27, 0.92, 7.34, 0.31, 0.05, 6.01, 2.96, 7.13, 5.26, 3.01, 0.99, 6.55, 8.08, 7.07, 5.74, 1.32, 0.04, 0.45, 0.30, 0.12));
		refillBag(nb);
	}
	
	public void refillBag(int nb)
	{
		Random r = new Random(); // 97 -> 122
		int i;
		double temp, rand;
		char let;
		for (i=0;i<nb;i++)
		{
			rand = r.nextDouble()*100;
			temp = 0;
			let = 97;
			for (double freq : freqLetter)
			{
				temp += freq;
				if (rand < temp)
				{
					addLetter(let);
					break;
				}
				let++;
			}
		}
		Collections.shuffle(listChar);
		if (correctFrequency(errorAdmitted) == false)
		{
			resetBag();
			refillBag(10000);
		}
	}
	
	public boolean correctFrequency(double errorAdmitted) 
    {
    	double freqq[] = new double[26];
    	int i;
    	for (i=0;i<26;i++)
    	{
    		freqq[i]=0;
    	}
    	for (char l : listChar)
    	{
    		freqq[l-97]++;
    	}
    	for (i=0;i<26;i++)
    	{
    		if (freqLetter.get(i)-errorAdmitted <= freqq[i]/100 && freqLetter.get(i)+errorAdmitted >= freqq[i]/100) {}
    		else return false;
    	}
    	return true;
    }
	
	public void resetBag()
	{
		listChar.clear();
	}
	
	public char pickLetter()
	{
		Random r = new Random();
		if (getNbLetter() == 0) refillBag(10000);
		int index = r.nextInt(listChar.size());
		char letter = listChar.get(index);
		listChar.remove(index);
		return letter;
	}
	
	public void addLetter(char letter)
	{
		listChar.add(letter);
	}
	
	public int getNbLetter()
	{
		return listChar.size();
	}
	
}
