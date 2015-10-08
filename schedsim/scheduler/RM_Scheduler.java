package schedsim.scheduler;


import schedsim.Task;

/**
 * deriva dal PriorityScheduler ma con la differenza che le priorità le assegna lui sulla base del
 * periodo del task
 * @author davide
 *
 */

public class RM_Scheduler extends PriorityScheduler {
	
	
	public RM_Scheduler(int quantum) {
		super(quantum);
		
	}

	
	@Override
	public void schedule(Task t) {
		t.priority = (int)(100.0 / ((Task)t).period);
		//System.out.println("priority of "+t+":"+t.priority);
		t.instruction_pointer=0;
		super.schedule(t);
	}

	

	public RM_Scheduler() {
		this(1);
	}

}
