package Chrono;
public class Timer {
	private double start;
	private double end;
	private boolean DNF;

	public Timer() {
		DNF = true;
		start = 0;
		end = 0;
	}

	public void Start() {
		start = System.nanoTime();
	}
	public void Stop() {
		DNF = false;
		end = System.nanoTime();
	}
	//overloaded, used for timer button
	public void start(double offest){
		start = System.nanoTime() + offest;
	}
	public double getStart(){
		return start;
	}
	public double getEnd(){
		return end;
	}
	public double getTime(){
		return (end - start)/1000000000.0;
	}
	//Needs a start time and end time
	public void printTime() {
		end = System.nanoTime() - start;
		//Converts to seconds
		double seconds = end / 1000000000.0;
		
		//Format correctly
		double input = seconds;
		double numberOfHours, numberOfMinutes, numberOfSeconds;
		numberOfHours = (input % 86400 ) / 3600;
		numberOfMinutes = ((input % 86400 ) % 3600 ) / 60;
		numberOfSeconds = ((input % 86400 ) % 3600 ) % 60;
		System.out.print("elapseTime: ");
		
		//Hours
		if(numberOfHours >= 1){
			System.out.printf("%.0f", numberOfHours);
			System.out.print(":");
		}
		else System.out.print("00:");
		
		//Minutes
		if(numberOfMinutes >= 1){
			System.out.printf("%.0f", numberOfMinutes);
			System.out.print(":");
		}
		else System.out.print("00:");
		
		//Seconds
		if(numberOfSeconds >= 1) System.out.printf("%.2f", numberOfSeconds);
		else System.out.print("00:");  
	}

}

