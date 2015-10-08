package schedsim;

import java.util.Enumeration;
import java.util.Vector;

public class TimeIterator extends Vector<Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Enumeration<Integer> e;
	public int current;
	
	public TimeIterator(int lifetime){
		for (int i=0;i<lifetime;i++){
			this.add(new Integer(i));
		}
		e=this.elements();
		
	}
	
	public boolean hasNext(){
		return e.hasMoreElements();
	}
	
	public int next(){
		current = e.nextElement().intValue();
		System.out.println("TIME="+current);
		
		return current;
		
	}
}
