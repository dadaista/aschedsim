/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schedsim.scheduler;

import java.util.Queue;
import schedsim.Task;

/**
 *
 * @author davide
 */
public abstract class Scheduler {
    
	/**
	 * coda dei processi pronti
	 */
    protected Queue<Task> idle_tasks;
    
    /**
     * coda dei processi terminati o che hanno terminato un job e potrebbero riattivarsi
     */
    protected Queue<Task> inactive_tasks;
    
    /**
     * metodo principale che quando invocato restituisce il task che merita il processore
     * @return
     */
    public abstract Task nextToRun();
    
    /**
     * inserisce il task t nella coda nei pronti e lo rende attivo e pronto a essere schedulato
     * @param t
     */
    public void schedule(Task t){
    	
	        idle_tasks.add(t);
	        t.setState(Task.IDLE);
	        if(inactive_tasks.contains(t))
	        	inactive_tasks.remove(t);
    	    	
    }
    
    /**
     * aggiorna le code in modo che se un processo era inattivo venga messo tra i processi attivi
     * se activation>=now()
     *
     */
    public void updateQueues(){
    	for(int i=0;i<inactive_tasks.size();i++){
    		Task t=inactive_tasks.poll();
    		if(t.activation<= now()){
    			schedule(t);
    		}
    		else
    			inactive_tasks.add(t);
    	}
    }
    
    public int now(){
    	return schedsim.Runtime.globaltime.current;
    }
    
}
