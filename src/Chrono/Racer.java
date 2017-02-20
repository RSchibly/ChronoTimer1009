package Chrono;
import java.util.ArrayList;

public class Racer {
	private int racerNumber;
	private RaceTime time;
	private ArrayList<RaceTime> timesRaced;

	public Racer(int racerNumber) {
		timesRaced = new ArrayList<RaceTime>();
		this.racerNumber = racerNumber;
		time = new RaceTime();
	}

	public int getNumber() {
		return racerNumber;
	}

	public RaceTime getTimer() {
		return time;
	}

	public ArrayList<RaceTime> getTimesRaced() {
		return timesRaced;
	}

	public void addRaceTime(RaceTime race) {
		timesRaced.add(race);
	}

	public String toString() {
		String ret = "Racer's number: " + racerNumber + "/n";
		for (int i = 0; i < timesRaced.size(); i++) {
			if (i == 0) {
				ret += "Race #1 time: " + getTimer().getTime();
			} else {
				ret += "Race #" + (i + 1) + ": " + getTimer().getTime();
			}
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Racer) {
			Racer ptr = (Racer) obj;
			return ptr.racerNumber == this.racerNumber;
		}
		else return false;
	}

	@Override
	public int hashCode() {
		return racerNumber * time.hashCode() * timesRaced.hashCode();
	}

}
