package schedsim;

public class PeriodicTask extends Task {
	public PeriodicTask(int cost, int deadline, int period) {
		super(cost);
		this.deadline=deadline;
		this.period = period;
	}

}
