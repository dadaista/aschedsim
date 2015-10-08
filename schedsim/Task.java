/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schedsim;



/**
 *
 * @author davide
 */
public class Task implements Comparable{
    public final static int IDLE   =0;
    public final static int RUNNING=1;
    public final static int BLOCKED=2;
    public final static int INACTIVE=3;
    static int taskcreated=0;
 
    public int cost;
    public int instruction_pointer=0;
    public int quantum_remained=0;
    
    public int priority=0;
    public int index;
	private int[] state;
	int[] code;
	
	//i seguenti sono posti a infinito 
	//un task non realtime è pari ad un task realtime con deadline infinita
	public int deadline=Integer.MAX_VALUE;
	
    //un task non periodico è pari ad un task periodico con periodo infinito
	public int period=Integer.MAX_VALUE;
	
	
	//si considera sempre activation=0 eccetto per i task aperiodici 
	//in cui può avere un valore diverso
	
	public int activation=0;
    
	/**
	 * Costruisce un task in base al semplice costo. Di fatto crea un programma di NOOP
	 * lungo quanto il costo indicato
	 * @param cost
	 */
    public Task(int cost){
    	this.index=taskcreated ++;
        this.code  = new int[cost*2];
        this.state = new int[Runtime.globaltime.size()];
        this.cost=cost;
        //carichiamo un programma fatto di soli NOOP 0
        for (int i=0;i<cost;i++){
        	code[2*i]=Code.NOOP;
        	code[2*i+1]=0;
        }
        
       
    }


    /**
     * Assegna lo stato a state[i] per i che va da now() alla fine.
     * @param state
     */
    public void setState(int state){
    	int time = Runtime.globaltime.current;
    	for(int i=time;i<this.state.length;i++)
    		this.state[i]=state;
    }
    
    /**
     * esegue la prossima istruzione nel code. Se si blocca su un mutex non
     * esegue niente e restituisce ERROR
     * @return
     */
    public int execNextInstruction() {
		int instruction = code[instruction_pointer *2 ];
		int arg = code[instruction_pointer * 2 + 1];
		
		if(instruction == Code.NOOP){
			instruction_pointer ++;
			setState(Task.RUNNING);
			return Code.SUCCESS;
		}
		
		if (instruction == Code.LOCK){
			if (Mutex.lock(arg,this) == Code.SUCCESS){
				instruction_pointer ++;
				setState(Task.RUNNING);
				return Code.SUCCESS;
			}
			else{
				setState(Task.BLOCKED);
				return Code.ERROR;
			}
		}
		
		if(instruction == Code.RELEASE){
			Mutex.release(arg);
			instruction_pointer ++;
			setState(Task.RUNNING);
			return Code.SUCCESS;
		}
		
		return Code.SUCCESS;
			
		
	}



	public String toString(){
        return "Task"+index+":"+"(c="+cost+",ip="+instruction_pointer+",dl="+deadline+",T="+period+",a="+activation+")";
    }

	/**
	 * confronta due task in base alla loro priorità
	 */
    public int compareTo(Object arg0) {
        return  ((Task)arg0).priority - this.priority;
    }

    /**
     * 
     * @return
     */
	public int getIndex() {
		
		return this.index;
	}

	public int[] getState() {
		
		return this.state;
	}
	
	
	
	
}
