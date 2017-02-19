package Chrono;
import java.util.ArrayList;

import Chrono.ChronoController.Competition;

public class Run {

	private ArrayList<Racer> racerQueue;
	public Competition raceType;

	public Run(Competition m_comp) {
		racerQueue = new ArrayList<Racer>();
		this.raceType = m_comp;
	}

	public ArrayList<Racer> getQueue() {
		return racerQueue;
	}

	public void addRacer(Racer racer) {
		racerQueue.add(racer);
	}

	public void addRacer(ArrayList<Racer> racerList) {
		racerQueue.addAll(racerList);
	}

	public void removeRacer(Racer racer) {
		racerQueue.remove(racer);
	}

}
