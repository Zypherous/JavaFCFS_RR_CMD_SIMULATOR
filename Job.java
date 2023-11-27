public class Job implements Comparable <Job>{
	
// Variables I determined I wanted to use to represent a Job
// Included variables to store some information such as wait time,
// turn around time, and response time in order to make my life simpler.
	
	private String PID;
	private int arrivalTime;
	private int firstWorkedOn;
	private int lastWorkedOn;
	private int burstTime;
	private int timeWorkedOn;
	private int waitTime;
	private int completionTime;
	private int turnAroundTime;
	private int responseTime;
	private boolean complete;
	
	// Constructer, takes in a String representing the PID , arrival time and burst time  as  ints
	Job(String PID, int arrivalTime, int burstTime){
		// Initializes values based on what the  parameters above are as well as initializing some values
		this.PID = PID;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.complete = false;
		this.waitTime =0;
		this.firstWorkedOn = -1;
	}
	
	// default constructor that just calls the three argument constructor, only made for testing purposes
	Job(){
		this("PX", 0, 0);
	}

	// Typical getters and setters auto generated most through eclipse
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getBurstTime() {
		return burstTime;
	}
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	public int getTimeWorkedOn() {
		return timeWorkedOn;
	}
	public void setTimeWorkedOn(int timeWorkedOn) {
		this.timeWorkedOn = timeWorkedOn;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	public int getTurnAroundTime() {
		return turnAroundTime;
	}
	public void setTurnAroundTime(int turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}
	public int getCompletionTime() {
		return completionTime;
	}
	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public int getFirstWorkedOn() {
		return firstWorkedOn;
	}
	public void setFirstWorkedOn(int firstWorkedOn) {
		this.firstWorkedOn = firstWorkedOn;
	}
	
	public int getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}
	
	public int getLastWorkedOn() {
		return lastWorkedOn;
	}
	public void setLastWorkedOn(int lastWorkedOn) {
		this.lastWorkedOn = lastWorkedOn;
	}
	
	// Implemented the comparable interface for sorting purposes, sorted by arrival time
	@Override
	public int compareTo(Job job) {
		return (getArrivalTime() > job.getArrivalTime()? 1: getArrivalTime() < job.getArrivalTime() ? -1:0);
	}
	
	// Override the toString for helpful information when debugging
	@Override
	public String toString() {
		return ("PID: " + this.getPID() + " Arrival Time: " + this.getArrivalTime() + " Burst Time: " + this.getBurstTime());
		
	}
	
	// Calculations for response time, wait time, and turn around time
	public void calcResponseTime() {
		this.responseTime = this.firstWorkedOn - this.arrivalTime;
	}
	public void calcWaitTime(int timer) {
		this.waitTime = this.waitTime + timer - this.lastWorkedOn;
	}
	public void calcTurnAroundTime() {
		this.turnAroundTime = this.completionTime - this.arrivalTime;
	}
	
	// helper method to call all the calculations at once
	public void calculate(int timer) {
		this.calcResponseTime();
		this.calcTurnAroundTime();
		this.calcWaitTime(timer);
	}
//
}
