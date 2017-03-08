package Chrono;

import java.time.LocalTime;

public class Timer {
	private LocalTime start;
	private LocalTime end;
	private boolean DNF;

	public Timer() {
		setDNF(true);
		start = null;
		end = null;
	}

	public void Start(LocalTime time) {
		start = time;
	}

	public void Stop(LocalTime time) {
		setDNF(false);
		end = time;
	}

	public LocalTime getStart() {
		return start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public String getTime() {
		// TODO What if dnf? What if in readyQ and endrun is typed?
		return (end.getHour() - start.getHour()) + ":" + (end.getMinute() - start.getMinute()) + ":"
				+ (end.getNano() - start.getNano());
	}

	//
	// //Needs a start time and end time
	// public void printTime() {
	// end = System.nanoTime() - start;
	// //Converts to seconds
	// double seconds = end / 1000000000.0;
	//
	// //Format correctly
	// double input = seconds;
	// double numberOfHours, numberOfMinutes, numberOfSeconds;
	// numberOfHours = (input % 86400 ) / 3600;
	// numberOfMinutes = ((input % 86400 ) % 3600 ) / 60;
	// numberOfSeconds = ((input % 86400 ) % 3600 ) % 60;
	// System.out.print("elapseTime: ");
	//
	// //Hours
	// if(numberOfHours >= 1){
	// System.out.printf("%.0f", numberOfHours);
	// System.out.print(":");
	// }
	// else System.out.print("00:");
	//
	// //Minutes
	// if(numberOfMinutes >= 1){
	// System.out.printf("%.0f", numberOfMinutes);
	// System.out.print(":");
	// }
	// else System.out.print("00:");
	//
	// //Seconds
	// if(numberOfSeconds >= 1) System.out.printf("%.2f", numberOfSeconds);
	// else System.out.print("00:");
	// }

	public boolean isDNF() {
		return DNF;
	}

	public void setDNF(boolean dNF) {
		DNF = dNF;
	}

}
