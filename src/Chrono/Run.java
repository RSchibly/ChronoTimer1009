package Chrono;

import java.util.ArrayList;
import java.util.LinkedList;

import Chrono.Channel.TriggerType;
import Chrono.Controller.Competition;

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

	public void dnf() {
		finishedQ.addLast(runningQ.removeFirst());
	}
	public void cancel(){
		readyQ.addFirst(runningQ.removeLast());
	}
	public void triggerChannel(Channel c) {
		if (raceType == Competition.IND) {
			if (c.getTriggerType() == TriggerType.START) {
				if (readyQ.isEmpty()) {
					// TODO Pipe through chronoController
					System.err.println("No racers to start");
				}
				runningQ.push(readyQ.pop());
			} else if (c.getTriggerType() == TriggerType.FINISH) {
				if (runningQ.isEmpty()) {
					// TODO Pipe through chronoController
					System.err.println("No racers running");
				}
				finishedQ.addLast(runningQ.removeFirst());
			}
		} else if (raceType == Competition.GRP) {
			// TODO later
		} else if (raceType == Competition.PARGRP) {
			// TODO later
		} else if (raceType == Competition.PARIND) {
			// TODO later
		}
	}

	public void addRacer(Racer racer) {
		if (readyQ.contains(racer) || runningQ.contains(racer) || finishedQ.contains(racer)) {
			// Error: racer already exists
		} else
			readyQ.addLast(racer);
	}

	public void removeRacer(Racer racer) {
		if (readyQ.contains(racer))
			readyQ.remove(racer);
		else if (runningQ.contains(racer))
			runningQ.remove(racer);
		else if (finishedQ.contains(racer))
			finishedQ.remove(racer);
		else {
			// Error: racer does not exist
		}
	}

	public int getID() {
		return id;
	}

}
