package schedsim.plot;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import schedsim.Task;

public class Gantt {
	public static void plot(String ylabels[], String tlabels[], int activity[][] ){
		JFrame f=new JFrame();
		f.getContentPane().setLayout(new GridLayout(ylabels.length + 1,tlabels.length + 1));
		
		
		for(int i=0;i<ylabels.length;i++){
			f.getContentPane().add(new JLabel(ylabels[i]));
			for(int k=0;k<tlabels.length;k++){
				JComponent l=new JButton("    ");
				if(activity[i][k] == Task.RUNNING)
					l.setBackground(Color.green);
				else if(activity[i][k] == Task.IDLE)
					l.setBackground(Color.lightGray);
				else if(activity[i][k] == Task.BLOCKED)
					l.setBackground(Color.red);
				else if(activity[i][k] == Task.INACTIVE)
					l.setBackground(Color.gray);
				
				f.getContentPane().add(l);
			}
			
		}
		f.getContentPane().add(new JLabel());
		for(int k=0;k<tlabels.length;k++){
			JComponent l=new JLabel(tlabels[k]);
			f.getContentPane().add(l);
		}
		f.setBounds(100, 100, 800, 500);
		f.setVisible(true);
	}		
	
	public static void plot(Task [] tasks){
    	String ylabels[]=new String[tasks.length];
    	String tlabels[]=new String[tasks[0].getState().length];
    	int activity [][]=new int [tasks.length][tasks[0].getState().length];
    	for (int i=0;i<tasks.length;i++){
    		ylabels[i]="T"+tasks[i].index;
    		for(int j=0;j<tasks[0].getState().length;j++)
    			activity[i][j] = tasks[i].getState()[j];
    	}
    	
    	for(int j=0;j<tasks[0].getState().length;j=j+10)
    		tlabels[j]=j+"";
    		
    	
    	
    	plot (ylabels,tlabels,activity );
    	
	}
}
