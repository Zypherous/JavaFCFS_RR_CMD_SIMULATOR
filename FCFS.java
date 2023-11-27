// First Come First Serve Scheduler
import java.util.Queue;
import java.util.Scanner;

public class FCFS extends CPUAlgo {

	FCFS (Queue<Job> jobs){
		this.jobs=jobs;
	}
	
	// Required override of the start method and the only method in this class which is the entire algorithm including print statements I didn't want to create
	// separate methods for
	@Override
	public void start()   {   // FCFS Main method
		
		// Prints that mimic project required output with my addition of printing the jobs passed in
		System.out.println(
				  "-------------------------------------------------\n"
				+ "            CPU Scheduling Simulation\n"
				+ "-------------------------------------------------\n"
				+ "\n"
				+ "\n");
		Driver.printJobs(jobs);
		System.out.println(
				 "\n"
				+"\n"
				+ "-------------------------------------------------\n"
				+ "       First Come First Served Scheduling\n"
				+ "-------------------------------------------------\n"
				+ ""
			);
		
		if(getJobs().isEmpty()) {  // Check if there are jobs to perform
			System.out.println("idle.... Time =" + timeCounter); // If no jobs then prints out idle
		}
		
		// While there are jobs in the input queue
		while(!this.jobs.isEmpty()){
			
			// Check to see if the job's arrival time is less than or equal to the counter (equivalent of saying time arrived is not in the future)
			if(this.jobs.peek().getArrivalTime() <= timeCounter) {
				// Moves job from the input file queue into the ready queue for processing
				readyQueue.add(getJobs().peek());
				
				// Used for printing out part of the gannt chart
				if(!idle && timeCounter !=0) {
					System.out.print("] ");
				}
				
				// Removes the job from input queue
				getJobs().remove();
				
				// Sets the time the job was first worked on
				readyQueue.peek().setFirstWorkedOn(timeCounter);
				// Sets the appropriate wait time the job waited before the "cpu" begins working on it
				readyQueue.peek().setWaitTime(timeCounter - readyQueue.peek().getArrivalTime() );
				
				// Another print statement for the gannt chart
				System.out.print(timeCounter + " ["+ readyQueue.peek().getPID());
				
				// For the first come first serve, the ready queue keeps running until the job is completed
				while(!readyQueue.isEmpty()) {
					
					// Check for if the time worked on is = to the burst cycle to determine if the job is complete
					if(readyQueue.peek().getTimeWorkedOn() < readyQueue.peek().getBurstTime()) {
						
						// for visual representation in terminal window of the gannt chart length
						System.out.print( "%" );
						// increasing the time worked on variable
						readyQueue.peek().setTimeWorkedOn(readyQueue.peek().getTimeWorkedOn() +1);
						
						// For animating a "loading" like bar in the terminal
						Driver.pause(.5);
						
						// Incrementing the time or "Time going by"
						timeCounter++;
					}
					else{
						
						// if  complete, update the variables to final values, mark as complete and print last part of gannt chart, set flag to idle if
						readyQueue.peek().setLastWorkedOn(timeCounter);
						readyQueue.peek().setCompletionTime(timeCounter);
						readyQueue.peek().calculate(timeCounter);
						readyQueue.peek().setComplete(true);
						completed.add(readyQueue.peek());
						readyQueue.remove();
						System.out.print("] ");
						idle = true;
					}
				}
			}else {
				
				// if idle and ready queue not empty, prints out idle
				if(idle) {
					System.out.print(timeCounter + " [idle");
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
		
		// Final time printed to finish the gannt chart
		System.out.print( timeCounter );
		
		// some user friendly delays for readability
		Driver.pause(2);
		System.out.println("\n\nProcessing complete.\nHit enter to continue...\n");
		Scanner s = new Scanner(System.in);
		String line = s.nextLine();
		
		// Print outs of the information calling appropriate methods from parent class
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
		
		// Display final required information
		System.out.format("\n\n\n    Average turnaround time: %.2f\n", calcAvgTurnaroundTime());
		System.out.format("    Average wait time: %.2f\n", calcAvgWaitTime());
		System.out.format("    Average Response time: %.2f\n\n", calcAvgResponseTime());
		System.out.println("-------------------------------------------------\n"
				+ "    Project done by [Jonathan Rosario]\n"
				+ "-------------------------------------------------\n"
				+ "");
	}

	
	
}