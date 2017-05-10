package Chrono;

import java.util.ArrayList;

import Chrono.Controller.Competition;

public class RunData {
	private int id;
	private Competition raceType;
	private ArrayList<Racer> racers;
	
	public RunData(int id, Competition raceType, ArrayList<Racer> racers) {
		this.id = id;
		this.raceType = raceType;
		this.racers = racers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Competition getRaceType() {
		return raceType;
	}

	public void setRaceType(Competition raceType) {
		this.raceType = raceType;
	}

	public ArrayList<Racer> getRacers() {
		return racers;
	}

	public void setRacers(ArrayList<Racer> racers) {
		this.racers = racers;
	}
}
