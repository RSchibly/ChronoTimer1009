package Chrono;

import java.util.ArrayList;
import java.util.LinkedList;

import Chrono.Channel.TriggerType;
import Chrono.Controller.Competition;

//Has all of our Run logic
public class Run {

	private LinkedList<Racer> readyQ, runningQ, finishedQ;
	private Competition raceType;
	private int id;
	private Controller parentController;

	public Run(int id, Competition raceType, Controller parent) {
		this.id = id;
		this.raceType = raceType;
		this.parentController = parent;
		this.readyQ = new LinkedList<Racer>();
		this.runningQ = new LinkedList<Racer>();
		this.finishedQ = new LinkedList<Racer>();
	}

	public int dnf() {
		Racer r = runningQ.removeFirst();
		finishedQ.addLast(r);
		return r.getNumber();
	}
	
	public int cancel(){
		Racer r = runningQ.removeLast();
		readyQ.addFirst(r);
		return r.getNumber();
	}
	public void triggerChannel(Channel c) {
		if (raceType == Competition.IND) {
			if (c.getTriggerType() == TriggerType.START) {
				if (readyQ.isEmpty()) {
					// TODO Pipe through chronoController
					parentController.cmd_error("No racers to start");
					return;
				}
				Racer r = readyQ.removeFirst();
				runningQ.addLast(r);
				System.out.println("Starting racer: " + r.getNumber());
			} else if (c.getTriggerType() == TriggerType.FINISH) {
				if (runningQ.isEmpty()) {
					// TODO Pipe through chronoController
					parentController.cmd_error("No racers to end");
					return;
				}
				Racer r = runningQ.removeFirst();
				finishedQ.addLast(r);
				System.out.println("Ending racer " + r.getNumber());
			}
		} else if (raceType == Competition.GRP) {
			// TODO later
		} else if (raceType == Competition.PARGRP) {
			// TODO later
		} else if (raceType == Competition.PARIND) {
			// TODO later
		}
	}

	public boolean addRacer(Racer racer) {
		if (readyQ.contains(racer) || runningQ.contains(racer) || finishedQ.contains(racer)) {
			// Racer already exists
			return false;
		} else
			readyQ.addLast(racer);
		return true;
	}

	public boolean removeRacer(Racer racer) {
		if (readyQ.contains(racer))
			readyQ.remove(racer);
		else if (runningQ.contains(racer))
			runningQ.remove(racer);
		else if (finishedQ.contains(racer))
			finishedQ.remove(racer);
		else {
			//Racer does not exist
			return false;
		}
		
		return true;
	}

	public int getID() {
		return id;
	}

}
