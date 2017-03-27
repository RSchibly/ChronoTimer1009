package Chrono;

import java.time.LocalTime;
import java.util.LinkedList;

public class Lane {

	private int laneNum;
	private Controller parentController;
	private LinkedList<Racer> readyQ, runningQ, finishedQ;
	
	public Lane(int laneNum, Controller parentController) {
		this.laneNum = laneNum;
		this.parentController = parentController;
		this.readyQ = new LinkedList<Racer>();
		this.runningQ = new LinkedList<Racer>();
		this.finishedQ = new LinkedList<Racer>();
	}
	
	public int getLaneNum() {
		return laneNum;
	}
	
	public LinkedList<Racer> getReadyQ() {
		return readyQ;
	}

	public LinkedList<Racer> getRunningQ() {
		return runningQ;
	}

	public LinkedList<Racer> getFinishedQ() {
		return finishedQ;
	}

	public void addRacer(Racer racer) {
		readyQ.addLast(racer);
	}

	public void removeRacer(Racer racer) {
		if (readyQ.contains(racer)) {
			readyQ.remove(racer);
		}
		else if (runningQ.contains(racer)) {
			runningQ.remove(racer);
		}
		else if (finishedQ.contains(racer)) {
			finishedQ.remove(racer);
		}
	}
	
	public int dnf() {
		if (runningQ.isEmpty()) {
			parentController.display_error(Messages.noRacersRunning);
			return -1;
		}
		Racer r = runningQ.removeFirst();
		finishedQ.addLast(r);
		return r.getNumber();
	}

	public int cancel() {
		if (runningQ.isEmpty()) {
			parentController.display_error(Messages.noRacersRunning);
			return -1;
		}
		Racer r = runningQ.removeLast();
		readyQ.addFirst(r);
		return r.getNumber();
	}
	
	public int getSize() {
		return readyQ.size() + runningQ.size() + finishedQ.size();
	}
	
	public boolean startNext(LocalTime time) {
		if (readyQ.isEmpty()) {
			parentController.display_error(Messages.noRacersToStart);
			return false;
		}
		Racer r = readyQ.removeFirst();
		r.getTimer().Start(time);
		runningQ.addLast(r);
		parentController.display(Messages.startingRacer + r.getNumber());
		return true;
	}
	
	public boolean finishNext(LocalTime time) {
		if (runningQ.isEmpty()) {
			parentController.display_error(Messages.noRacersToFinish);
			return false;
		}
		Racer r = runningQ.removeFirst();
		r.getTimer().Stop(time);
		finishedQ.addLast(r);
		parentController.display(Messages.finishingRacer + r.getNumber());
		return true;
	}
	
}
