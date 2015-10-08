/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schedsim.scheduler;

import java.util.concurrent.ArrayBlockingQueue;

import schedsim.Task;

/**
 * semplice scheduler rount robin
 * @author davide
 */
public class RR_Scheduler extends Scheduler{
	int quantum;
	int time;
	Task current=null;
	
	/**
	 * crea uno scheduler con un certo quanto
	 * @param quantum
	 */
    public RR_Scheduler(int quantum) {
        this.idle_tasks = new ArrayBlockingQueue<Task>(50);
        this.inactive_tasks=new ArrayBlockingQueue<Task>(50);;
        this.quantum=quantum;
    }

    /**
     * crea uno scheduler il cui quanto è pari a uno
     *
     */
    public RR_Scheduler() {
        this(1);
    }
    
    
    @Override
    public Task nextToRun(){
    	updateQueues();
    	System.out.print("idle tasks:");
    	System.out.println(this.idle_tasks);
    	
    	System.out.print("inactive tasks:");
    	System.out.println(this.inactive_tasks);
    	
    	
    	
    	if(current == null){
    		current= this.idle_tasks.poll();
    		
    		if (current == null)
        		return null;
        	else
        		current.quantum_remained=this.quantum;
    		
    	}
    	
    	if(current.getState()[schedsim.Runtime.globaltime.current] == Task.BLOCKED){
    		current=null;
    		return nextToRun();
    	}
    		
    	
    	
    	
    	
        if (current.instruction_pointer == current.cost){//se il task corrente è finito ne prendo uno nuovo
        	this.inactive_tasks.add(current);
        	this.idle_tasks.remove(current);
        	current.setState(Task.INACTIVE);
        	current.instruction_pointer = 0;
        	current.activation = current.activation + current.period;
        	if (current.activation < 0)//succede per overflow
        		current.activation = Integer.MAX_VALUE;
        	current = null;
        	return nextToRun();
        }
       
        
        
        
        if(current.quantum_remained > 0){//se current ha ancora un pezzo di quanto
        	
        	current.quantum_remained--;
        	return current;
        }
        
        else{//se il task corrente ha finito il quanto ne prendo uno nuovo e questo lo rimetto in idle
        	    
        		this.idle_tasks.add(current);
        		current.setState(Task.IDLE);
        		current= this.idle_tasks.poll();
            	if (current == null)
            		return null;
            	current.quantum_remained = quantum-1;
        }
        
        
        
        
        return current;
    }


    
    
    
}
