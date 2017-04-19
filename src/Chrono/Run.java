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
	private int numLanes;
	private Controller parentController;
	private boolean isGroupStarted;
	private int groupNumCounter;

	public Run(int id, Competition raceType, Controller parent) {
		isGroupStarted = false;
		groupNumCounter = 1;
		this.numLanes = 0;
		this.id = id;
		this.raceType = raceType;
		this.parentController = parent;
		this.racers = new ArrayList<Racer>();
		this.lanes = new ArrayList<Lane>();
		this.lanes.add(new Lane(numLanes++, parentController));
		if (raceType == Competition.PARIND)
			this.lanes.add(new Lane(numLanes++, parentController));
	}

	public ArrayList<Racer> getRacers() {
		return racers;
	}

	public Lane getLane(int laneNumber) {
		return lanes.get(laneNumber);
	}

	public int dnf() {
		if (raceType == Competition.IND) {
			return lanes.get(0).dnf();
		} else {
			parentController.display_error(Messages.noDNF);
			return -1;
		}
	}

	public int cancel() {
		if (raceType == Competition.IND) {
			return lanes.get(0).cancel();
		} else {
			parentController.display_error(Messages.noCancel);
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
			if (c.getTriggerType() == TriggerType.START && !isGroupStarted) {
				if (c.getChannelIndex() == 1) {
					while (!lanes.get(0).getReadyQ().isEmpty()) {
						lanes.get(0).startNext(time);
					}
					isGroupStarted = true;
				}
				// TODO how to finish, possibly parameter
			} else if (c.getTriggerType() == TriggerType.FINISH) {
				if (c.getChannelIndex() == 2 && !lanes.get(0).getRunningQ().isEmpty()) {
					lanes.get(0).getRunningQ().getFirst().setNumber(groupNumCounter++);
					lanes.get(0).finishNext(time);
					// This is used to change the numbers of the racers after
					// all the raceres are done racing
					if (lanes.get(0).getRunningQ().isEmpty() && lanes.get(0).getReadyQ().isEmpty()) {
						groupNumCounter = 0;
					}
				}
			}
		} else if (raceType == Competition.PARGRP) {
			// TODO later
		} else if (raceType == Competition.PARIND) {
			if (c.getTriggerType() == TriggerType.START) {
				if (c.getChannelIndex() == 1) {
					lanes.get(0).startNext(time);
				} else if (c.getChannelIndex() == 3) {
					lanes.get(1).startNext(time);
				}
			} else if (c.getTriggerType() == TriggerType.FINISH) {
				if (c.getChannelIndex() == 2) {
					lanes.get(0).finishNext(time);
				} else if (c.getChannelIndex() == 4) {
					lanes.get(1).finishNext(time);
				}
			}
		}
	}

	public boolean addRacer(Racer racer) {
		if (racers.contains(racer)) {
			parentController.display_error(Messages.racerAlreadyExists);
			return false;
		} else {
			racers.add(racer);
			Lane smallestLane = lanes.get(0);
			for (Lane currentLane : lanes) {
				if (smallestLane.getSize() > currentLane.getSize()) {
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
			for (Lane currentLane : lanes) {
				currentLane.removeRacer(racer);
			}
			return true;
		} else {
			parentController.display_error(Messages.racerDoesNotExist);
			return false;
		}
	}

	public void endRun() {
		for (Lane currentLane : lanes) {
			if (!currentLane.getReadyQ().isEmpty()) {
				for (Racer r : currentLane.getReadyQ()) {
					r.getTimer().setDNF(true);
					currentLane.getFinishedQ().add(r);
				}
			}
			if (!currentLane.getRunningQ().isEmpty()) {
				for (Racer r : currentLane.getRunningQ()) {
					r.getTimer().setDNF(true);
					currentLane.getFinishedQ().add(r);
				}
			}
		}
	}

	public void setGRPNumber(int rNum) {
		for(int i = 0; i < lanes.get(0).getFinishedQ().size(); i++){
			Racer r = lanes.get(0).getFinishedQ().get(i);
			if(r.getNumber() == rNum && i < groupNumCounter){
				//TODO print error
				//This one is for you Rick
				System.out.println("CaNnOt AdD - GrOuP NuM");
				return;
			}
			else if(r.getNumber() == rNum){
				int temp = lanes.get(0).getFinishedQ().get(groupNumCounter).getNumber();
				r.setNumber(temp);
			}
		}
		
		lanes.get(0).getFinishedQ().get(groupNumCounter++).setNumber(rNum);
	}

	public boolean isGRPStartedAndFinished() {
		return (isGroupStarted && lanes.get(0).getReadyQ().isEmpty() && lanes.get(0).getRunningQ().isEmpty());
	}

	public boolean isGRPStarted() {
		return isGroupStarted;
	}

	public int getID() {
		return id;
	}

	public String getHTMLRun(){
		String html = "";
		for(Racer r: racers){
			//Number, Time
			html += "<td>" + r.getNumber() + "</td><td>"
			+ r.getTimer() + "</td><td>";
		}
		
		return html;
	}
	
}
