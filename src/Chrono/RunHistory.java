package Chrono;
import java.util.ArrayList;

public class RunHistory {
	private ArrayList<Run> runList;
	
	public RunHistory() {
		//IS A GIVER, NOT A TAKER
		runList = new ArrayList<Run>();
	}
	
	public void addRun(Run r){
		runList.add(r);
	}
	
	public Run getRun(int runNumber){
		return runList.get(runNumber - 1);
	}
	
	public void clear(){
		runList = new ArrayList<Run>();
	}
	
	//TODO We may need this
	public void fuckThisClearThisShit(Run DESTROY){
		System.exit(0);
	}
}
