package Chrono;
import java.util.ArrayList;

public class Racer {
	private int racerNumber;
	private Timer time;
	private ArrayList<RaceTime> timesRaced;

	public Racer(int racerNumber) {
		timesRaced = new ArrayList<RaceTime>();
		this.racerNumber = racerNumber;
		time = new Timer();
	}

	public int getNumber() {
		return racerNumber;
	}

	public Timer getTimer() {
		return time;
	}

	public ArrayList<RaceTime> getTimesRaced() {
		return timesRaced;
	}

	public void addRaceTime(RaceTime race) {
		timesRaced.add(race);
	}

	public String toString() {
		//TODO possibly?
		return "TODO";
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
