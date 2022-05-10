package processes.run;
import processes.Process;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ProcessHandler {
	public static List<Integer> RRProcessExitTimes = new ArrayList<>();
	public static Process[] scheduler(String type, Process[] userInput)
	{
		List<Process> processList = Arrays.asList(userInput);
		List<Process> processOrder  = new ArrayList<Process>();
		List<Process> processOrderSJF = new ArrayList<Process>();
		List<Process> readyQueue = new ArrayList<Process>();		
		int clock = 0;
		Process running = null;
		
		int maxClock = 0;
		for(Process process : processList)
		{
			maxClock += process.getBurstTime();
		}
		maxClock += processList.get(0).getArrivalTime();
		while(clock <= maxClock)
		{
			for(Process process : processList)
			{
				if(clock == process.getArrivalTime())	// Fill up ready queue along with order list
				{
					readyQueue.add(process);
					processOrder.add(process);
				}
			}
			
			if(running != null)	// Check if process is finished
			{
				if(running.getExitTime() == clock) // Finished
				{
					running = null;	// CPU is now free
				}
				
			}
			
			if(running == null && !readyQueue.isEmpty())
			{
				if(type.equalsIgnoreCase("FCFS"))	// Move ready to running state
				{
					running = FCFS(readyQueue, clock);
				}
				else if(type.equalsIgnoreCase("SJF"))
				{
					running = SJF(readyQueue, clock);
					processOrderSJF.add(running);
				}	
				
				if(running.getStartTime() == -1)	// Set start time
				{
					running.setStartTime(clock);	
				}
				
				readyQueue.remove(readyQueue.indexOf(running));	// Remove process form ready queue (or move to the end for RR)
			}
			
			clock++;
		}
		Process[] temp = new Process[processOrder.size()];
		if(type.equalsIgnoreCase("SJF")) {
			return processOrderSJF.toArray(temp);
		}
		processOrder.toArray(temp);
		return temp;
	}
	
	public static Process FCFS(List<Process> readyqueue, int clock)
	{	
		Process running = readyqueue.get(0);
		running.setExitTime(clock + running.getBurstTime());	// Calculate estimated exit time
		running.setWaitTime(clock - running.getArrivalTime());	// Calculate wait time
		return running;
	}
	
	public static Process SJF(List<Process> readyqueue, int clock)
	{
		Process shortest = readyqueue.get(0);
		for(Process process : readyqueue)
		{
			if(process.getBurstTime() < shortest.getBurstTime())
			{
				shortest = process;
			}
		}
		shortest.setExitTime(clock + shortest.getBurstTime());	// Calculate estimated exit time
		shortest.setWaitTime(clock - shortest.getArrivalTime());	// Calculate wait time
		return shortest;
	}
	
	public static Process[] RR(Process[] userInput, int timeQuantum)
	{
		List<Process> processList = Arrays.asList(userInput);
		List<Process> processOrder  = new ArrayList<Process>();
		List<Process> readyQueue = new ArrayList<Process>();
		RRProcessExitTimes.clear();
		int clock = 0;
		int maxClock = 0;
		int quantumClock = 0;
		Process running = null;
		
		for(Process process : processList)
		{
			maxClock += process.getBurstTime();
		}
		maxClock += processList.get(0).getArrivalTime();
		
		while(clock <= maxClock)
		{
			for(Process process : processList)
			{
				if(clock == process.getArrivalTime())	// Fill up ready queue along with order list
				{
					readyQueue.add(process);
					processOrder.add(process);
				}
			}
			
			if(running != null)	// Check if process is finished
			{
				quantumClock++;
				if(quantumClock == timeQuantum || quantumClock == running.getBurstTimeDynamic()) // timeQuantum reached
				{
					running.setBurstTimeDynamic(running.getBurstTimeDynamic() - quantumClock);	// Reduce burst time by quantum
					if(running.getBurstTimeDynamic() <= 0 || clock == maxClock)		// Process finished
					{
						running.setExitTime(clock);
						running.setWaitTime(running.getWaitTime() + (clock - (running.getStartTime() +
								running.getBurstTime())));
						RRProcessExitTimes.add(clock);
						readyQueue.remove(running);
						running = null;
						
					}
					else {		// Time quantum finished
						readyQueue.remove(running);	// Remove process from ready queue
						readyQueue.add(running);	// Add process to the end of ready queue
						processOrder.add(running);	// Add process to p. execution order
						RRProcessExitTimes.add(clock);
						running = null;
					}
					quantumClock = 0;	// Reset quantumClock
				}
			}
			
			if(running == null && !readyQueue.isEmpty())
			{
				running = readyQueue.get(0);	// Move ready to running state
				if(running.getStartTime() == -1)
				{
					running.setStartTime(clock);	// Set start time
					running.setWaitTime(clock - running.getArrivalTime());
				}
				readyQueue.remove(0);	// Remove process form ready queue (or move to the end for RR)
			}
			clock++;
		}
		Process[] temp = new Process[processOrder.size()];
		return processOrder.toArray(temp);
	}	
}
