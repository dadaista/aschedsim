package schedsim;

public class AperiodicTask extends Task {

	
	public AperiodicTask(int activation,int cost,int deadline) {
		super(cost);
		this.deadline=deadline;
		this.activation=activation;
		setState(Task.INACTIVE);
	}


}
