package Chrono;

import java.time.LocalTime;
import java.util.ArrayList;
import Chrono.Channel.TriggerType;
import Chrono.Controller.Competition;

//Has all of our Run logic
public class Run {

    private ArrayList<Lane> lanes;
    private ArrayList<Racer> racers;
	private Competition raceType;
	private int id;
	private int numLanes = 0;
	private Controller parentController;

	public Run(int id, Competition raceType, Controller parent) {
		this.id = id;
		this.raceType = raceType;
		this.parentController = parent;
		this.racers = new ArrayList<Racer>();
		this.lanes = new ArrayList<Lane>();
		this.lanes.add(new Lane(numLanes++, parentController));
		if(raceType == Competition.PARIND) this.lanes.add(new Lane(numLanes++, parentController));
	}
	
	public ArrayList<Racer> getRacers() {
		return racers;
	}

	public int dnf() {
		if(raceType == Competition.IND) {
			return lanes.get(0).dnf();
		}
		else {
			parentController.display_error("Can't use DNF in this type of Competition");
			return -1;
		}
	}

	public int cancel() {
		if(raceType == Competition.IND) {
			return lanes.get(0).cancel();
		}
		else {
			parentController.display_error("Can't use Cancel in this type of Competition");
			return -1;
		}
	}

	public void triggerChannel(Channel c, LocalTime time) {
		if (raceType == Competition.IND) {
			if (c.getTriggerType() == TriggerType.START) {
				lanes.get(0).startNext(time);
			} else if (c.getTriggerType() == TriggerType.FINISH) {
				lanes.get(0).finishNext(time);
			}
		} else if (raceType == Competition.GRP) {
			// TODO later
		} else if (raceType == Competition.PARGRP) {
			// TODO later
		} else if (raceType == Competition.PARIND) {
			if (c.getTriggerType() == TriggerType.START) {
				if(c.getChannelIndex() == 1) {
					lanes.get(0).startNext(time);
				}
				else if(c.getChannelIndex() == 3) {
					lanes.get(1).startNext(time);
				}
				
			} else if (c.getTriggerType() == TriggerType.FINISH) {
				if(c.getChannelIndex() == 2) {
					lanes.get(0).finishNext(time);
				}
				else if(c.getChannelIndex() == 4) {
					lanes.get(1).finishNext(time);
				}
			}

		}
	}



	public boolean addRacer(Racer racer) {
		if(racers.contains(racer)) {
			parentController.display_error("Racer already exists");
			return false;
		} else {
			racers.add(racer);
			Lane smallestLane = lanes.get(0);
			for(Lane currentLane : lanes) {
				if(smallestLane.getSize() > currentLane.getSize()) {
					smallestLane = currentLane;
				}
			}
			smallestLane.addRacer(racer);
			return true;
		}
		
	}

	public boolean removeRacer(Racer racer) {
		if (racers.contains(racer)) {
			racers.remove(racer);
			for(Lane currentLane : lanes) {
				currentLane.removeRacer(racer);
			}
			return true;
		}
		else {
			parentController.display_error("Racer does not exist");
			return false;
		}

		
	}
	
	public void endRun() {
		for(Lane currentLane : lanes) {
			if (!currentLane.getReadyQ().isEmpty()) {
				for (int i = 0; i < currentLane.getReadyQ().size(); i++) {
					currentLane.getReadyQ().get(i).getTimer().setDNF(true);
					currentLane.getFinishedQ().add(currentLane.getReadyQ().get(i));
				}
			}
			if (!currentLane.getRunningQ().isEmpty()) {
				for (int i = 0; i < currentLane.getRunningQ().size(); i++) {
					currentLane.getRunningQ().get(i).getTimer().setDNF(true);
					currentLane.getFinishedQ().add(currentLane.getRunningQ().get(i));
				}
			}
		}
	}

	public int getID() {
		return id;
	}

}
