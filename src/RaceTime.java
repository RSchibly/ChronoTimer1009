import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class RaceTime {
	private double raceTime; // This is in milliseconds!
	private Timer time; // Link for reference:
						// https://docs.oracle.com/javase/7/docs/api/javax/swing/Timer.html
	private boolean DNF;

	public RaceTime() {
		raceTime = 0;
		DNF = true;
		time = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				raceTime += 100;
			}
		});

	}

	public void raceStart() {
		time.start();
	}

	public void raceEnd() {
		time.stop();
	}

	public void raceRestart() {
		time.restart(); // Why not? Issue and needs to re-race
		raceTime = 0;
	}

	public double getTime() {
		return raceTime % 1000;
	}

	public boolean getDNF() {
		return DNF; // Maybe we'll need this
	}

}
