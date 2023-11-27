// Round Robin Scheduling class
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RR extends CPUAlgo{
	
	// Has an additional queue and variable compared to the FCFS
	// time slice is how long each job is worked on, set by user
	int timeSlice;
	
	// a waiting queue when a job isn't complete it is placed here in order to wait its turn to 
	// return to being worked on 
	Queue<Job> waitQueue = new LinkedList<Job>();
	
	
	// Constructor takes in the input queue and a int for time slice
	RR (Queue<Job> jobs, int timeSlice){
		this.jobs=jobs;
		this.timeSlice = timeSlice;
	}
	
	// the algorithm method which is only one present, prints and logic required for the algorithm
	@Override
	void start() {
		
		// Prints that mimic project required output with my addition of printing the jobs passed in
		System.out.println(
				  "-------------------------------------------------\n"
				+ "            CPU Scheduling Simulation\n"
				+ "-------------------------------------------------\n"
				+ "\n"
				+ "\n"
				);
		Driver.printJobs(jobs);
		System.out.println( 
				 "\n"
				+"\n"
				+ "-------------------------------------------------\n"
				+ "              Round Robin Scheduling\n"
				+ "-------------------------------------------------\n"
				+ ""
			);
		
		if(getJobs().isEmpty()) {  // Check if there are jobs to perform
			System.out.println("idle.... Time =" + timeCounter);  // If no jobs then prints out idle
		}
		
		// While there are jobs in the input queue or the wait queue isn't empty
		while(!this.jobs.isEmpty() || !waitQueue.isEmpty()){

		// If the wait queue isnt empther, take a job from front of the queue and place it into the ready queue
			if(!waitQueue.isEmpty()) {
				readyQueue.add(waitQueue.peek());
				waitQueue.remove(); // remove the job from the wait queue
				if(!idle && timeCounter !=0) { //print for the gannt chart
					System.out.print("] ");
				}
				
				// if its the first time the job is worked on (by checking if initial value is still -1,
				// then sets initial worked on time, initializes the last worked on time for calculation purposes,
				// and sets the wait time to the initial value
				if(readyQueue.peek().getFirstWorkedOn() == -1 ) {
					readyQueue.peek().setFirstWorkedOn(timeCounter);
					readyQueue.peek().setWaitTime(timeCounter - readyQueue.peek().getArrivalTime() );
					readyQueue.peek().setLastWorkedOn(timeCounter);
				}
				
				// sets wait time to account for previous wait times 
				readyQueue.peek().setWaitTime(readyQueue.peek().getWaitTime() + timeCounter - readyQueue.peek().getLastWorkedOn());
				System.out.print(timeCounter + " ["+ readyQueue.peek().getPID());  // Gannt chart print
				
				// Initializing the quantum to keep track of how long a job as been in the "cpu"
				int quantum = 0;
				
				// If the  queue isnt empty, work on it until complete or the quantum is met, whichever comes first
				while((!readyQueue.isEmpty())) {
					
					// Work on the job if not complete and quantum hasn't finished
					if(readyQueue.peek().getTimeWorkedOn() < readyQueue.peek().getBurstTime() && quantum < timeSlice) {
						
						System.out.print( "%" ); // Gannt chart print
						// Update variables of job
						readyQueue.peek().setTimeWorkedOn(readyQueue.peek().getTimeWorkedOn() +1);
						Driver.pause(.5);
						// Increment counters
						timeCounter++;
						quantum++;
					}
					// Job is either complete or time is up 
					else{
						// update the last worked on variable
						readyQueue.peek().setLastWorkedOn(timeCounter);
						// check if the job is complete
						if(readyQueue.peek().getTimeWorkedOn() == readyQueue.peek().getBurstTime()) {
							
							// update the variables and move to completed list
							readyQueue.peek().setCompletionTime(timeCounter);
							readyQueue.peek().setComplete(true);
							readyQueue.peek().calculate(timeCounter);
							completed.add(readyQueue.peek());
							// remove from the ready queue and reset quantum for next job
							readyQueue.remove();
							quantum =0;
						}
						// Job not complete, time is up
						else {
							// Check if there is a new job arriving to pre-empt the current job
							// if the input queue isnt empty and the next job hasnt arrived
							if(!this.jobs.isEmpty() && !(this.jobs.peek().getArrivalTime() <= timeCounter)){
								// return current process to back of the wait queue
								waitQueue.add(readyQueue.peek());
								readyQueue.remove();
							
							//  if the job queue is not empty check all the jobs to see whether they are arriving
							// uses an enhanced for loop incase there are multiple jobs arriving at the same time
							}else if(!this.jobs.isEmpty()) {
								// add any job to the wait queue in order of arrival that have arrived at this time
								while(!this.jobs.isEmpty() && this.jobs.peek().getArrivalTime() <= timeCounter) {
									waitQueue.add(getJobs().peek());
									getJobs().remove();
								}
								
								// finally add the current job from the ready queue back into the end of the wait queue
								waitQueue.add(readyQueue.peek());									
								readyQueue.remove();
							}else{
								// No jobs left in the input queue, return current job to back of wait queue
								waitQueue.add(readyQueue.peek());
								readyQueue.remove();
							}

							}
						 // Gannt chart print
						System.out.print("] ");
						idle = true;
					}
				}
				
			}
			// Add job to wait Queue from input queue if time is right
			else if(this.jobs.peek().getArrivalTime() <= timeCounter) {
				waitQueue.add(getJobs().peek());
				getJobs().remove();
			}
			// idle if no jobs
			else {
				if(idle) {
					System.out.print(timeCounter + "[idle");
					System.out.print( "%" );
					timeCounter++;
					Driver.pause(.5);
					idle= false;
				}else{
					System.out.print( "%" );
					timeCounter++;
					Driver.pause(.5);
				}
			}
		}
		
		// Same as FCFS, delays and prints to display information in accordance with project specs
		System.out.print( timeCounter );
		
		Driver.pause(2);
		System.out.println("\n\nProcessing complete.\nHit enter to continue...\n");
		Scanner s = new Scanner(System.in);
		String line = s.nextLine();
		sortByPID(completed);
		System.out.println();
		System.out.println();
		printTAT();
		System.out.println("\nHit enter to continue...\n");
		line = s.nextLine();
		printWT(); 
		System.out.println("\nHit enter to continue...\n");
		line = s.nextLine();
		printRT();
		System.out.println("\nHit enter to continue...\n");
		line = s.nextLine();
		s.close();
		
		System.out.format("\n\n\n    Average turnaround time: %.2f\n", calcAvgTurnaroundTime());
		System.out.format("    Average wait time: %.2f\n", calcAvgWaitTime());
		System.out.format("    Average Response time: %.2f\n\n", calcAvgResponseTime());
		System.out.println("-------------------------------------------------\n"
				+ "    Project done by [Jonathan Rosario]\n"
				+ "-------------------------------------------------\n"
				+ "");
	}
}















/////////////////////FAILED CODE HERE FOR PERSONAL REASON IGNORE///////////////////////////////////

//if(this.jobs.peek().getArrivalTime() <= timeCounter) {
////Queue<Job> temp = new LinkedList<Job>();
////while(!waitQueue.isEmpty()) {
////temp.add(waitQueue.peek());
////waitQueue.remove();
////}
//waitQueue.add(getJobs().peek());
////while(!temp.isEmpty()) {
////waitQueue.add(temp.peek());
////temp.remove();
//}
//getJobs().remove();