package Server;

import java.time.LocalTime;

public class ServerRacer {
	private int racerNumber;
	private ServerTimer time;
	private String racerName;

	public ServerRacer(int racerNumber) {
		this.racerNumber = racerNumber;
		time = new ServerTimer();
		racerName = "---";
	}

	public int getNumber() {
		return racerNumber;
	}

	public void setNumber(int rNum) {
		racerNumber = rNum;
	}

	public ServerTimer getTimer() {
		return time;
	}

	public String toString() {
		return getFinishedStr();
	}
	
	public String getReadyStr() {
		return "Racer Number : " + getNumber();
	}
	
	public String getRacingStr(LocalTime time) {
		return "Racer Number : " + getNumber()  + ": " + " -> Time: " + getTimer().getTimeDiff(time);
	}
	
	public String getFinishedStr() {
		return "Racer Number : " + getNumber()  + ": " + " -> Time: " + getTimer().toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServerRacer) {
			ServerRacer ptr = (ServerRacer) obj;
			return ptr.racerNumber == this.racerNumber;
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return racerNumber * time.hashCode();
	}
	
	public void setName(String name){
		this.racerName = name;
	}
	
	public int getBib(){
		return racerNumber;
	}
	
	public String HTMLRacer(){

		return "<td>" + this.time + "</td><td>"
		+ this.racerNumber + "</td><td>" 
		+ this.racerName + "</td>";
	}
}
