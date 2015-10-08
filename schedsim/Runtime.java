/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schedsim;

import schedsim.plot.Gantt;
import schedsim.scheduler.PriorityScheduler;
import schedsim.scheduler.RM_Scheduler;
import schedsim.scheduler.RR_Scheduler;
import schedsim.scheduler.Scheduler;

/**
 *
 * @author davide
 */
public class Runtime {
	final static int N = 3;
	final static int MAX_TIME=50;
	public static TimeIterator globaltime=new TimeIterator(MAX_TIME);
	
	static Task tasks[]=new Task[N];
	static Scheduler scheduler=null;
	
	
    public static void main(String []a){
    	test_mutex1();        
    	
    	
    	int time=globaltime.next();
    	int ret;
    	do {
        	Task running=scheduler.nextToRun();
            if(running == null) {
            	time = globaltime.next();
            	continue; //nessun task è pronto, vai avanti in system idle
            }
            ret=running.execNextInstruction();
            System.out.println("in time="+time+" scheduled "+running);
            if(ret == Code.SUCCESS){
            	time = globaltime.next();
            }
            // se ret == ERROR, il tempo non scorre e si ripesca un nuovo task 
            // ma per lo stesso istante
            
            
            
        } while(globaltime.hasNext());
        
        Gantt.plot(tasks);
    	
    }
    

    public static void test_RM(){//RR with Priority
    	 	
    	
        scheduler = new RM_Scheduler();

        tasks[0]=new PeriodicTask(2,6,6);
        tasks[1]=new PeriodicTask(3,12,12);
        tasks[2]=new PeriodicTask(1,8,8);
        
        scheduler.schedule(tasks[0]);
        scheduler.schedule(tasks[1]);
        scheduler.schedule(tasks[2]);
        
        double rm_schedulability=N * (Math.pow(2, 1.0/N) - 1);
        
        double load_factor=0;
        for(int i=0;i<N;i++)
        	load_factor += tasks[i].cost * 1.0 / ((Task)tasks[i]).period;
        
        
        System.out.println("load factor="+load_factor);
        System.out.println("schedulability limit in RM="+rm_schedulability);
        

        
    }    
    
    
    public static void test_PRR(){//RR with Priority
    	
    	
    	
        scheduler = new PriorityScheduler();
        for (int i=0;i<N;i++){
        	tasks[i]=new Task(5);
        	scheduler.schedule(tasks[i]);
            
        }
        
        tasks[0].priority = 3;
        tasks[1].priority = 3;
        tasks[2].priority = 2;
        //tasks[3].priority = 1;
        
        
    }    
    
    
    public static void test_mutex1(){//RR with one mutex
    	
        scheduler = new RR_Scheduler();
        
        //task0 ha 10 istruzioni
        tasks[0]=new Task(10);
        
        //come prima istruzione cerca il lock di m0
        tasks[0].code[0]=Code.LOCK;
        tasks[0].code[1]=0;
        //come 6 istruzione rilascia m0
        tasks[0].code[10]=Code.RELEASE;
        tasks[0].code[11]=0;
        
        tasks[1]=new Task(10);
        
        
        

        tasks[2]=new Task(10);
        //identico a task0
        tasks[2].code[0]=Code.LOCK;
        tasks[2].code[1]=0;
        //come 6 istruzione rilascia m0
        tasks[2].code[10]=Code.RELEASE;
        tasks[2].code[11]=0;
        
        Mutex.init(1);
        
        for (int i=0;i<N;i++){
        	
        	scheduler.schedule(tasks[i]);
            
        }
        

        
        
    }        
    
    
    public static void test_RR(){
    	
    	scheduler = new RR_Scheduler(2);
        for (int i=0;i<N;i++){
        	tasks[i]=new Task(5);
        	scheduler.schedule(tasks[i]);
            
        }
        
        
        
        

    }
    	
}

