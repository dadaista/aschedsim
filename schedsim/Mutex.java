package schedsim;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class Mutex {
	Task holder=null;
	Set<Task> blocked=new TreeSet<Task>();
	
	static Vector<Mutex> mutexes=new Vector<Mutex>();
	
	public static int lock(int mutex, Task task) {
		Mutex m=mutexes.elementAt(mutex);
		if(m.holder == null){
			m.holder = task;
			System.out.println("MUTEX "+mutex+" LOCKED BY " + task);
			return Code.SUCCESS;
		}
		else{
			m.blocked.add(task);
			System.out.println("BLOCKED BY " + mutex + "::" + m.blocked);
			return Code.ERROR;
		}
		
		
	}
	
	public static int release(int mutex){
		Mutex m=mutexes.elementAt(mutex);
		m.holder=null;
		Iterator<Task> en=m.blocked.iterator();
		
		//rimettiamo in IDLE tutti i task che erano in competizione per il lock
		//essi verranno rischedulati e tutti meno uno si ribloccheranno su questo 
		//semaforo
		while(en.hasNext())
			Runtime.scheduler.schedule(en.next());
		
		System.out.println("MUTEX "+mutex+" RELEASED");
		return Code.SUCCESS;
	}

	
	public static void init(int num_mutexes){
		mutexes.removeAllElements();
		for(int i=0;i<num_mutexes;i++){
			mutexes.add(new Mutex());
		}
	}
}
