import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;


// Anstract algorithm class from which other algortihms are built on.
// Has methods that are useful for the whole process and made creating the other two simpler
public abstract class CPUAlgo {
	// Two queues for the jobs that are incoming
	protected Queue<Job> jobs = new LinkedList<Job>();
	protected Queue<Job> readyQueue = new LinkedList<Job>();
	
	// Counter to count the "time" which in this case is an iteration of the loop....sort of, as there are parts of the loop where this variable isnt updated
	protected int timeCounter = 0;
	
	// Initalized variables for the requested information at end of program
	protected double avgTurnaroundTime = 0.00;
	protected double avgWaitTime = 0.00;
	protected double avgResponseTime = 0.00;
	
	// A flag to determine whether a process is currently being worked on or the "cpu" is in use
	protected boolean idle = true;
	
	// Array list for storing the completed jobs in order to retain the information stored in each one
	protected ArrayList<Job> completed = new ArrayList<Job>();
	
	// The main method of each algorithm, to be implemented by the child classes
	abstract void start();
	
	// Helper methods to print out the information of the completed jobs TAT = Turnaround time, RT = Response times, WT = wait times
	protected void printTAT() {
		System.out.println("	Turnaround times:");
		for(Job job: completed) {
			Driver.pause(.3);
			System.out.println("		"+job.getPID() + " = " + job.getTurnAroundTime());
		}
		System.out.println();
	}
	protected void printRT() {
		System.out.println("	Response times:");
		for(Job job: completed) {
			Driver.pause(.3);
			System.out.println("		"+job.getPID() + " = " + job.getResponseTime());
		}
		System.out.println();
	}
	protected void printWT() {
		System.out.println("	Wait times:");
		for(Job job: completed) {
			Driver.pause(.3);
			System.out.println("		"+job.getPID() + " = " + job.getWaitTime());
		}
		System.out.println();
	}
	// Self explanatory method (I guess most my methods are hahaha) this takes in an array list and sorts
	// using a lambda expression ( I didn't want to create another comparable class) to sort the jobs by 
	// PID in order to print out the PID in order as shown in project specifications
	protected void sortByPID(ArrayList<Job> jobs) {
		Collections.sort(jobs, (j1,j2)-> j1.getPID().compareTo(j2.getPID()));
	}
	
	// Calculates the avg response time and the two methods bellow are for wait and turnaround time
	protected double calcAvgResponseTime() {
		int cumulator = 0;
		for(Job job: completed) {
			cumulator += job.getResponseTime();
		}
		avgResponseTime = (double)cumulator/completed.size();
		return avgResponseTime;
	}

	protected double calcAvgWaitTime() {
		int cumulator = 0;
		for(Job job: completed) {
			cumulator += job.getWaitTime();
		}
		avgWaitTime = (double)cumulator/completed.size();
		return avgWaitTime;
	}

	protected double calcAvgTurnaroundTime() {
		int cumulator = 0;
		for(Job job: completed) {
			cumulator += job.getTurnAroundTime();
		}
		avgTurnaroundTime = (double)cumulator/completed.size();
		return avgTurnaroundTime;
	}
	
	// Getter and setter for the Jobs Queue
	protected Queue<Job> getJobs() {
		return jobs;
	}
	protected void setJobs(Queue<Job> jobs) {
		this.jobs = jobs;
	}
}
