package Chrono;

import java.time.LocalTime;

public class Racer {
	private int racerNumber;
	private Timer time;

	public Racer(int racerNumber) {
		this.racerNumber = racerNumber;
		time = new Timer();
	}

	public int getNumber() {
		return racerNumber;
	}

	public void setNumber(int rNum) {
		racerNumber = rNum;
	}

	public Timer getTimer() {
		return time;
	}

	public String toString() {
		return getFinishedStr();
	}
	
	public String getReadyStr() {
		return Messages.racerNumber + getNumber();
	}
	
	public String getRacingStr(LocalTime time) {
		return Messages.racerNumber + getNumber()  + ": " + Messages.racerTime + getTimer().getTimeDiff(time);
	}
	
	public String getFinishedStr() {
		return Messages.racerNumber + getNumber()  + ": " + Messages.racerTime + getTimer().toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Racer) {
			Racer ptr = (Racer) obj;
			return ptr.racerNumber == this.racerNumber;
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return racerNumber * time.hashCode();
	}
}
