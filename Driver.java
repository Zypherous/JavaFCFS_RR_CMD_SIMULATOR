/*	CPU Scheduler Project by Jonathan Rosario
 * 	Professor Steven Fulakeza http://comet.lehman.cuny.edu/sfulakeza/
 *  CMP 426 Operating Systems
 * 
 * ************NOTE****************
 *  You can comment out the Driver.pause() calls if you dont want that simulated wait effect 
 *  Locations below:
 *  	Class    Lines
 *  	CPUAlgo  19,27,35
 *  	FCFS     46,65,70,77
 *  	RR       58,107,112,118
 *  
 * */

// Imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Driver {
	
	// Program driver accepting cmd arguments to run the program with some minor error handling 
	public static void main(String [] args)   {
		
		// Clears the users cmd window for a cleaner look
		clearConsole();
		// Checking that an argument has been entered as it is required for the program to run
		if(args.length  < 2) {
			System.out.println("Enter in command line arguments to run program. (algorithm(or test) filename.... \n\nExiting...");
			System.exit(-1);
		}
		// Assigning the variables from arguments
		String fileName = args[1];

		String algo = args[0];
		
		// If block determining which algorithm to execute based on user input, RR Or FCFS are only accepted arguments
		if(algo.equals("fcfs")) {
			fcfs(fileName);
		}
		else if(algo.equals("rr") && args.length ==3) {

			try{
				rr(fileName, Integer.parseInt(args[2]));

			}catch(NumberFormatException e) {
				System.out.println("Third argument must be an integer \n Exiting...");
			}
		}
		// Error message if the arguments dont match expected input informing user to retry with correct argument structure
		else {
			System.out.println("Invalid command line arguments, use \"fcfs/rr 'filename' (timeslice if rr)\"  \nExiting...");
		}
	
	}
	
	// Method used to print out all the processes (which I have decided to use the word job for brevity's sake
	// Takes in a Queue of jobs and uses an enhanced for loop to iterate and print out the information in the Queue
	public static void printJobs(Queue<Job> jobs) {
		for(Job j : jobs) {
			System.out.println("	"+j.toString());

		}
	}
	
	// Sorts the Queue by adding them to an Array List and then calling the Collections sort to sort them based on arrival time
	// ( I could have just added all the jobs into an Array List first to skip this step but only thought about it as I wrote this
	// comment. 
	public static void arrangeByArrival(Queue<Job> jobs) {
		ArrayList <Job> temp = new ArrayList<>();
		// Emptying queue and moving over to the temp array list
	    while(jobs.isEmpty() == false)
	    {
	           temp.add(jobs.peek());
	           jobs.remove();
	    }
	    // Sorting the queue
	    Collections.sort(temp);
	    // Adding the jobs back in arrival sorted order
	    for(int i=0;i<temp.size();i++)
	    {
	        jobs.add(temp.get(i));
	    }
	}
	
	// A method which tries to sleep the thread inorder to simulate time passing
	public static void pause(double seconds)
	{
	    try {
	        Thread.sleep((long) (seconds * 1000));
	    } catch (InterruptedException e) {}
	}
	
	// Method that calls the FCFS (First come first serve) algorithm, takes in a String file name as a parameter and calls
	public static void fcfs(String fileName) {
		Queue<Job> jobs = new LinkedList<Job>();
		
		// Helper file opening method to parse incoming csv style formatted text file
		openJobsText(fileName, jobs);
		// Random line of decorative string while I was thinking through a problem
		System.out.println("\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\\\|/-\\\\|/-\\\\|/-\\\\|/-\\|/-\\\\|/-\\\\|/-\\\\|/-\\\\|/-");
		// Creating the object to start the FCFS
		FCFS fcfs = new FCFS(jobs);
		// Starts the FCFS algo
		fcfs.start();
	}
	
	// Method that calls the RR (Round Robin) algorithm which is pre-empted. Takes in a String file name as a parameter and an
	// int to determine the time slice or quantum for the algorithm
	private static void rr(String fileName, int timeSlice) {
		Queue<Job> jobs = new LinkedList<Job>();
		openJobsText(fileName, jobs);
		System.out.println("\\|/-\\|/-\\|/-\\|/-\\|/-\\|/-\\\\|/-\\\\|/-\\\\|/-\\\\|/-\\|/-\\\\|/-\\\\|/-\\\\|/-\\\\|/-");
		RR rr = new RR(jobs, timeSlice);
		rr.start();
	}
	
	// Helper method that takes in a String ane Queue to parse through text file and create the Job objects and add them to the Queue
	private static void openJobsText(String fileName, Queue<Job> jobs) {
		
		// Try catch block required for standard IO operations
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;
			
			// Iterating through the lines in the text file parsing through the information with the expected file structure of:
			// PID,ARRIVAL,BURST CYCLE
			// Splits on comma and converts second and third values into integers
			while((line = reader.readLine()) != null) {
				line.trim();
				String [] parsedLine = line.split(",");

				Job job = new Job(parsedLine[0].trim(), Integer.parseInt(parsedLine[1].trim()),Integer.parseInt(parsedLine[2].trim()));
				jobs.add(job);
			}
			
			// Sorts the Queue
			arrangeByArrival(jobs);


			reader.close();
		}catch(IOException ex) {

			System.out.println("File" + " was not found. " + ex.toString());
		}
	}
	
	// Helper method for fun, clears user cmd window regardless of whehter Windows or Linux
	public static void clearConsole() {
	    try {
	        if (System.getProperty("os.name").contains("Windows")) {
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        }
	        else {
	            System.out.print("\033\143");
	        }
	    } catch (IOException | InterruptedException ex) {}
	}
}

































//////////////////////////TEST CODE IGNORE///////////////////////////////////////
//String algo = "RR";
////System.out.println("ARGS LENGTH = " + args.length);
//if(algo.equals("test")) {
//Queue<Job> testJobsQueue = new LinkedList<Job>();
//String testJobs =
//		"	P0 0 3\n"
//				+ "	P1 1 6\n"
//				+ "	P2 5 4\n"
//				+ "	P3 7 3\n"
//				+ "";
//Scanner scanner = new Scanner(testJobs);
//
//while(scanner.hasNextLine()) {
//	String line = scanner.nextLine();
//	System.out.println(line);
//	line.trim();
//	String [] parsedLine = line.trim().split(" ");
//	Job job = new Job(parsedLine[0].trim(), Integer.parseInt(parsedLine[1].trim()),Integer.parseInt(parsedLine[2].trim()));
//	testJobsQueue.add(job);
//}
//
//for(Job j : testJobsQueue) {
//	System.out.println(j.toString());
//}
//}


//BufferedReader reader = new BufferedReader(new FileReader("process2.txt"));




//System.out.println(fileName + " was not found. " + ex.toString());

//for (Job k : jobs) {
//System.out.println(j.compareTo(k)); 
//}

//for(String s : parsedLine) {
//System.out.println(s.getClass().getName());
//}
//System.out.println(parsedLine[1] + parsedLine[2]);


//testFCFS.getJobs().forEach(job ->{
//	System.out.println(job);
//});