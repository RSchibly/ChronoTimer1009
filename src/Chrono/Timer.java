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

	public String toString() {
		if (DNF) {
			return Messages.DNF;
		}else {
			LocalTime dur = end.minusHours(start.getHour()).minusMinutes(start.getMinute()).minusSeconds(start.getSecond()).minusNanos(start.getNano());
			return dur.getHour() + ":" + dur.getMinute() + ":" + dur.getSecond() + "." + Integer.toString(dur.getNano()).substring(0, 1);
		}		
	}
	
	public boolean isDNF() {
		return DNF;
	}

	public void setDNF(boolean dNF) {
		DNF = dNF;
	}

}
