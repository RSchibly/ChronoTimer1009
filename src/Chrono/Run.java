package Chrono;
import java.util.ArrayList;
import java.util.LinkedList;

import Chrono.ChronoController.Competition;

public class Run {

	private LinkedList<Racer> readyQ, runningQ, finishedQ;
	private Competition raceType;
	private int id;
	

	public Run(int id, Competition raceType) {
		this.id = id;
		this.raceType = raceType;
		
		this.readyQ = new LinkedList<Racer>();
		this.runningQ = new LinkedList<Racer>();
		this.finishedQ = new LinkedList<Racer>();
	}


	public void addRacer(Racer racer) {
		if(readyQ.contains(racer) || runningQ.contains(racer) || finishedQ.contains(racer)) {
			//Error: racer already exists
		}
		else readyQ.add(racer);
	}

	public void removeRacer(Racer racer) {
		if(readyQ.contains(racer)) readyQ.remove(racer);
		else if(runningQ.contains(racer)) runningQ.remove(racer);
		else if(finishedQ.contains(racer)) finishedQ.remove(racer);
		else {
			//Error: racer does not exist
		}
	}
	
	public int getID() {
		return id;
	}

}
