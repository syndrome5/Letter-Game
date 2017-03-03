package fr.esiea.praddaude.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI 
{
	
	public GUI() {}
	
	public JPanel makeBoxLayout(int start, int space, Component... args) 
	{
		JPanel p = new JPanel();
	    p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
	    p.add(Box.createRigidArea(new Dimension(start,0)));
	    for (Component arg : args) 
	    {
	    	p.add(Box.createRigidArea(new Dimension(space,0)));
		    p.add( arg);
	    }
	    p.add(Box.createRigidArea(new Dimension(start,0)));
	    return p;
	}
	
	public JPanel makeBoxLayoutSmart(int start, double separator, List<Component> args) 
	{
		JPanel p = new JPanel();
	    p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
	    p.add(Box.createRigidArea(new Dimension(start/2,0)));
	    int count=0;
	    separator-=start;
	    for (Component arg : args) 
	    {
		    p.add( arg);
		    if (count+1 != args.size()) 
		    {
		    	count++;
		    	p.add(Box.createRigidArea(new Dimension((int)separator-(int)arg.getPreferredSize().getWidth(),0)));
		    }
	    }
	    p.add(Box.createRigidArea(new Dimension(start/2,0)));
	    return p;
	}
	
	public JPanel addCompo(Component c)
	{
		JPanel p = new JPanel(new FlowLayout());
		p.add(c);
		return p;
	}
}
