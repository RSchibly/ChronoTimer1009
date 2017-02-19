public class Timer {
	private boolean isRunning;

	public Timer() {
		this.isRunning = false;
	}

	public double Start() {
		System.out.println("Starting Timer");
		isRunning = true;
		return System.nanoTime();
	}

	//Needs a start time and end time
	public void getTime(double end, double start) {
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
		if(numberOfSeconds >= 1) System.out.printf("%.9f", numberOfSeconds);
		else System.out.print("00:");  
	}

	public double Stop() {
		System.out.println("Ending Timer");
		isRunning = false;
		return System.nanoTime();
	}
	
	public double reset(){
		isRunning = true;
		return System.nanoTime();
	}
	
	public boolean isRunning(){
		return isRunning;
	}
}

