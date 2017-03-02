package Chrono;

public class Racer {
	private int racerNumber;
	private Timer time;
	
	public Racer(int racerNumber) {
		this.racerNumber = racerNumber;
		time = new Timer();
	}

	public int getNumber() {
		return racerNumber;
	}

	public Timer getTimer() {
		return time;
	}
	
	public String toString() {
		//TODO possibly?
		return "TODO";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Racer) {
			Racer ptr = (Racer) obj;
			return ptr.racerNumber == this.racerNumber;
		}
		else return false;
	}

	@Override
	public int hashCode() {
		return racerNumber * time.hashCode();
	}

}
