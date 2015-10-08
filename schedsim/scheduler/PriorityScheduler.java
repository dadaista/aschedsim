package schedsim.scheduler;

import java.util.PriorityQueue;

import schedsim.Task;


/**
 * e identico al round robin ma tiene conto delle priorità. L'implementazione si basa sulla
 * PriorityQueue usata come coda dei pronti.
 * @author davide
 *
 */
public class PriorityScheduler extends RR_Scheduler {

	public PriorityScheduler(int quantum) {
		super(quantum);
		this.idle_tasks = new PriorityQueue<Task>();
	}

	public PriorityScheduler() {
		this(1);
	}
	
	public static void main(String a[]){
		
		PriorityScheduler s= new PriorityScheduler();
		Task t0=new Task(5);
		Task t1=new Task(5);
		Task t2=new Task(5);
		Task t3=new Task(5);
		t0.priority = 3;
		t1.priority = 2;
		t2.priority = 1;
		t3.priority = 3;
		
		
		System.out.println(t2.compareTo(t2));
		
		s.schedule(t0);
		s.schedule(t1);
		s.schedule(t2);
		s.schedule(t3);
		
		for (int time = 0; time <20; time ++)
			System.out.println(s.idle_tasks.poll());
	}

}
