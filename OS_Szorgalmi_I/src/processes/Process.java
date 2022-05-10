package processes;

public class Process {
	private int arrivalTime;
	private int burstTime;
	private int burstTimeDynamic;
	private String pid;
	
	private int startTime;
	private int exitTime;
	
	private int waitTime;
	
	public Process() {}
	
	public Process(int arrivalTime, int burstTime, String pid)
	{
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.burstTimeDynamic = burstTime;
		this.startTime = -1;
		this.pid = pid;
	}
	
	// Getters, Setters
	
	public void setArrivalTime(int arrivalTime)
	{
		this.arrivalTime = arrivalTime;
	}
	public void setBurstTime(int burstTime)
	{
		this.burstTime = burstTime;
	}
	
	public int getArrivalTime()
	{
		return arrivalTime;
	}
	public int getBurstTime()
	{
		return burstTime;
	}
	
	
	public void setStartTime(int startTime)
	{
		this.startTime = startTime;
	}
	public void setExitTime(int exitTime)
	{
		this.exitTime = exitTime;
	}
	
	public int getStartTime()
	{
		return startTime;
	}
	public int getExitTime()
	{
		return exitTime;
	}
	
	public void setWaitTime(int waitTime)
	{
		this.waitTime = waitTime;
	}
	
	public int getWaitTime()
	{
		return waitTime;
	}
	
	public void setBurstTimeDynamic(int burstTimeDynamic)
	{
		this.burstTimeDynamic = burstTimeDynamic;
	}
	
	public int getBurstTimeDynamic()
	{
		return burstTimeDynamic;
	}
	
	public String getPid() {
		return pid;
	}
	
	// Methods
	
	@Override
	public String toString()
	{
		return "Arrival Time: " + arrivalTime + " Burst Time: " + burstTime + " Start Time: " +  startTime
				+ " Exit Time: " + exitTime + " Wait Time: " + waitTime;
	}
}
