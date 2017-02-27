package Chrono;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import Chrono.Channel.Sensor;

public class Controller implements ActionListener {
	private static final int NUM_CHANNELS = 8;
	
	public enum ChronoState {INITIAL, RACING}
	public enum Competition {IND, PARIND, GRP, PARGRP}
	
	private boolean running;
	private ChronoState m_state;
	private Competition m_comp;
	
	private Channel[] m_channels;
	private Run m_run;
	private int runID;
	private ArrayList<Run> runHistory;
	
	public Controller() {
		running = true;
		m_state = ChronoState.INITIAL;
		m_comp = Competition.IND;
		m_channels = new Channel[NUM_CHANNELS];
		m_run = null;
		runID = 1;
		runHistory = new ArrayList<Run>();
		for(int i = 0; i < NUM_CHANNELS; i++) m_channels[i] = new Channel(i+1);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().startsWith("POWER")) power();
		else if(e.getActionCommand().startsWith("EXIT")) exit();
		else if(e.getActionCommand().startsWith("RESET")) reset();
		else if(e.getActionCommand().startsWith("TIME")) {
			//TIME <hour>:<min>:<sec>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if(cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			String[] timeArgs = cmdArgs[1].split(":");
			if(timeArgs.length < 3) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			try{
				time(Integer.parseInt(timeArgs[0]), Integer.parseInt(timeArgs[1]), Integer.parseInt(timeArgs[2]));
			}
			catch(NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
		}
		else if(e.getActionCommand().startsWith("TOG")) {
			//TOG <channel>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if(cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			try {
				toggle(Integer.parseInt(cmdArgs[1]));
			}
			catch(NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
		}
		else if(e.getActionCommand().startsWith("CONN")) {
			//CONN <sensor> <num>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if(cmdArgs.length < 3) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			int channel;
			
			try {
				channel = Integer.parseInt(cmdArgs[2]);
			}
			catch(NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \""+e.getActionCommand()+"\"");
				return;
			}

			if(cmdArgs[1].equalsIgnoreCase("EYE")) connect(Sensor.EYE, channel);
			else if(cmdArgs[1].equalsIgnoreCase("GATE")) connect(Sensor.GATE, channel);
			else if(cmdArgs[1].equalsIgnoreCase("PAD")) connect(Sensor.PAD, channel);
			else {
				cmd_error("Could not resolve argument type in command \""+e.getActionCommand()+"\"");
				return;
			}
		}
		else if(e.getActionCommand().startsWith("DISC")) {
			//DISC <num>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if(cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			try {
				disconnect(Integer.parseInt(cmdArgs[1]));
			}
			catch(NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
		}
		else if(e.getActionCommand().startsWith("EVENT")) {
			//EVENT <type>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if(cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			if(cmdArgs[1].equalsIgnoreCase("IND")) event(Competition.IND);
			else if(cmdArgs[1].equalsIgnoreCase("PARIND")) event(Competition.PARIND);
			else if(cmdArgs[1].equalsIgnoreCase("GRP")) event(Competition.GRP);
			else if(cmdArgs[1].equalsIgnoreCase("PARGRP")) event(Competition.PARGRP);
			else {
				cmd_error("Could not resolve argument type in command \""+e.getActionCommand()+"\"");
				return;
			}
		}
		else if(e.getActionCommand().startsWith("NEWRUN")) new_run();
		else if(e.getActionCommand().startsWith("ENDRUN")) end_run();
		else if(e.getActionCommand().startsWith("PRINT")) {
			//PRINT <run>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if(cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			try {
				print(Integer.parseInt(cmdArgs[1]));
			}
			catch(NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
		}
		else if(e.getActionCommand().startsWith("EXPORT")) {
			//EXPORT <run>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if(cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			try {
				export(Integer.parseInt(cmdArgs[1]));
			}
			catch(NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
		}
		else if(e.getActionCommand().startsWith("NUM")) {
			//NUM <number>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if(cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			try {
				num(Integer.parseInt(cmdArgs[1]));
			}
			catch(NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
		}
		else if(e.getActionCommand().startsWith("CLR")) {
			//CLR <number>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if(cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			try {
				clr(Integer.parseInt(cmdArgs[1]));
			}
			catch(NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
		}
		else if(e.getActionCommand().startsWith("SWAP")) swap();
		else if(e.getActionCommand().startsWith("DNF")) dnf();
		else if(e.getActionCommand().startsWith("TRIG")) {
			//TRIG <num>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if(cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
			
			try {
				trigger(Integer.parseInt(cmdArgs[1]));
			}
			catch(NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \""+e.getActionCommand()+"\"");
				return;
			}
		}
		else if(e.getActionCommand().startsWith("START")) trigger(1);
		else if(e.getActionCommand().startsWith("FINISH")) trigger(2);
		else if(e.getActionCommand().startsWith("CANCEL")) cancel();
		else {
			//Error: Unknown command
		}
		
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void cmd_error(String errorMessage) {
		cmd_error(errorMessage, true);
	}
	public void cmd_error(String errorMessage, boolean ignored) {
		//Add to event log and/or do something with error
		System.err.println(errorMessage);
	}
	
	//POWER
	//States allowed: ALL
	//(if off) turn system on, enter initial state.
	//(if on) turn system off, but stay in simulator.
	//Possibly moving to Timer
	private void power() {
		//TODO Sprint 1, but this will probably need to be moved to ChronoTimer?
	}
	
	//EXIT
	//States allowed: ALL
	//Exit the simulator.
	private void exit() {
		//TODO Sprint 1
		running = false;
	}
	
	//RESET
	//States allowed: ALL
	//Resets the System to initial state and resets everything.
	private void reset() {
		//TODO Sprint 1
		m_state = ChronoState.INITIAL;
		m_comp = Competition.IND;
		m_channels = new Channel[NUM_CHANNELS];
		m_run = null;
		runID = 1;
		runHistory = new ArrayList<Run>();
		for(int i = 0; i < NUM_CHANNELS; i++) m_channels[i] = new Channel(i+1);
	}
	
	//TIME <hour>:<min>:<sec>
	//States allowed: ALL
	//Sets(advances) the System time to the time specified(so there is no wait for test output).
	private void time(int hour, int min, int sec) {
		//TODO Sprint 1
		//Possible use as an offset and pass System time + offset to relevant functions?
		//Maybe this command is unnecessary
	}
	
	//TOG <channel>
	//States allowed: ALL
	//Toggle the state of the channel specified.
	private void toggle(int channel) {
		if(channel < 1 || channel > NUM_CHANNELS) {
			cmd_error("Channel out of range.");
			return;
		}
		m_channels[channel-1].setEnabled(!m_channels[channel-1].isEnabled());
	}
	
	//CONN <sensor> <num>
	//States allowed: ALL
	//Connect a sensor(of type specified) to the channel specified.
	private void connect(Sensor sensor, int channel) {
		//TODO Later
		if(channel < 1 || channel > NUM_CHANNELS) {
			cmd_error("Channel out of range.");
			return;
		}
		
		if(m_channels[channel-1].isConnected()) {
			cmd_error("Channel is already connected to a sensor.");
			return;
		}
		
		m_channels[channel-1].connect(sensor);
	}
	
	//DISC <num>
	//States allowed: ALL
	//Disconnect a sensor from the channel specified.
	private void disconnect(int channel) {
		//TODO Later
		if(channel < 1 || channel > NUM_CHANNELS) {
			cmd_error("Channel out of range.");
			return;
		}
		
		if(!m_channels[channel-1].isConnected()) {
			cmd_error("Channel is already disconnected.");
			return;
		}
		
		m_channels[channel-1].disconnect();
	}

	//EVENT <type>
	//States allowed: INITIAL
	//Changes the current competition to specified type.
	private void event(Competition type) {
		//TODO Later
		if(m_state != ChronoState.INITIAL) {
			cmd_error("Cannot change competition type during a Run, must end current Run first.");
		}
		
		m_comp = type;
	}
	
	//NEWRUN
	//States allowed: INITIAL
	//Create a new Run (must end a run first).
	private void new_run() {
		//TODO Sprint 1
		
		if(m_state != ChronoState.INITIAL) {
			cmd_error("A Run is already in progress, must end the current Run first.");
			return;
		}
		
		m_run = new Run(runID, m_comp);
		m_state = ChronoState.RACING;
	}
	
	//ENDRUN
	//States allowed: RACING
	//Done with the Run.
	private void end_run() {
		//TODO Sprint 1

		if(m_state != ChronoState.RACING) {
			cmd_error("A Run is not in progress, must start a Run first.");
			return;
		}
		
		runHistory.add(m_run);
		m_state = ChronoState.INITIAL;
	}
	
	//PRINT <run>
	//States allowed: INITIAL
	//Print the run specified on stdout.
	private void print(int run) {
		//TODO Later
		//print runHistory.get(run);
	}
	
	//EXPORT <run>
	//States allowed: INITIAL
	//Export the run specified in XML to file "RUN<"+runID+">.xml"
	private void export(int run) {
		//TODO Later
		//export runHistory.get(run);
	}
	
	//NUM <number>
	//States allowed: RACING
	//Set <number> as the next competitor to start.
	private void num(int number) {
		//TODO Sprint 1
		m_run.addRacer(new Racer(number));
	}
	
	//CLR <number>
	//States allowed: RACING
	//Clear <number> from the run.
	private void clr(int number) {
		//TODO Sprint 1
		m_run.removeRacer(new Racer(number));
	}
	
	//SWAP ??
	//States allowed: -
	//This is a stupid command.
	private void swap() {
		//TODO Later
	}
	
	//DNF
	//States allowed: RACING
	//The next competitor to finish will not finish.
	private void dnf() {
		//TODO Sprint 1
		m_run.dnf();
	}
	
	//TRIG <num>
	//States allowed: RACING
	//Trigger channel <num>
	private void trigger(int channel) {
		//TODO Sprint 1
		if(channel < 1 || channel > NUM_CHANNELS) {
			cmd_error("Channel out of range.");
			return;
		}
		
		if(m_channels[channel-1].isEnabled()) {
			m_run.triggerChannel(m_channels[channel-1]);
		}
	}
	
	//CANCEL
	//States allowed: RACING
	//Discard any racers current start time and put racer back in queue as next to start.
	private void cancel() {
		//TODO Sprint 1
		m_run.cancel();
	}
}
