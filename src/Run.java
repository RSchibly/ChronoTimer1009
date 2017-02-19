import java.util.ArrayList;

public class Run {
	private ArrayList<Racer> racerQueue;
	public String raceType; // Make a object for raceType?
							// Use a int instead so we don't have to parse?

	public Run(String raceType) {
		racerQueue = new ArrayList<Racer>();
		this.raceType = raceType;
	}

	public ArrayList<Racer> getQueue() {
		return racerQueue;
	}

	public void addRacer(Racer racer) {
		racerQueue.add(racer);
	}

	public void addRacer(ArrayList<Racer> racerList) {
		for (int i = 0; i < racerList.size(); i++) {
			racerQueue.add(racerList.get(i)); // Maybe at some point we want to
												// add a bunch of people? Or
												// change to group? Simple
												// method either way.
		}
	}

	public void removeRacer(Racer racer) {
		racerQueue.remove(racer);
	}

	public String getRaceType() {
		return raceType;
	}
}
