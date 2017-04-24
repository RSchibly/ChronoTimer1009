package Chrono;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import com.google.gson.Gson;

import Chrono.Channel.Sensor;

public class Controller implements ActionListener {
	private static final int NUM_CHANNELS = 8;

	public enum ChronoState {
		OFF, INITIAL, RACING
	}

	public enum Competition {
		IND("Individual"), PARIND("Parallel Individual"), GRP("Group"), PARGRP("Parallel Group");
		private final String display;
		  private Competition(String s) {
		    display = s;
		  }
		  @Override
		  public String toString() {
		    return display;
		  }
	}

	private Printer m_printer;
	private Display m_display;

	private boolean running;
	private boolean fromFile;
	private ChronoState m_state;
	public ChronoState getState() {
		return m_state;
	}

	private Competition m_comp;

	// Holds the offset time
	private LocalTime m_sysTime;

	public LocalTime getSysTime() {
		if(m_sysTime != null && fromFile) return m_sysTime;
		else return LocalTime.now();
	}

	private Channel[] m_channels;
	public Channel getChannel(int channelIndex) {
		return m_channels[channelIndex-1];
	}

	private Run m_run;
	private int runID;
	public int getRunID() {
		return runID;
	}

	private ArrayList<Run> runHistory;

	public Controller(Display display, Printer printer, boolean fromFile) {
		this.fromFile = fromFile;
		m_display = display;
		m_printer = printer;
		runHistory = new ArrayList<Run>();
		running = false;
		m_state = ChronoState.OFF;
		m_sysTime = null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().startsWith("POWER"))
			power();
		else if (e.getActionCommand().startsWith("EXIT"))
			exit();
		else if (e.getActionCommand().startsWith("RESET"))
			reset();
		else if (e.getActionCommand().startsWith("TIME")) {
			// TIME <hour>:<min>:<sec>:<nano>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			// splits on ":" or "."
			String[] timeArgs = cmdArgs[1].split(":|\\.");
			if (timeArgs.length < 4) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				time(Integer.parseInt(timeArgs[0]), Integer.parseInt(timeArgs[1]), Double.parseDouble(timeArgs[2]),
						Double.parseDouble(timeArgs[3]));
			} catch (NumberFormatException ex) {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("TOG")) {
			// TOG <channel>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				toggle(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("CONN")) {
			// CONN <sensor> <num>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 3) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			int channel;

			try {
				channel = Integer.parseInt(cmdArgs[2]);
			} catch (NumberFormatException ex) {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			if (cmdArgs[1].equalsIgnoreCase("EYE"))
				connect(Sensor.EYE, channel);
			else if (cmdArgs[1].equalsIgnoreCase("GATE"))
				connect(Sensor.GATE, channel);
			else if (cmdArgs[1].equalsIgnoreCase("PAD"))
				connect(Sensor.PAD, channel);
			else {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("DISC")) {
			// DISC <num>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				disconnect(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("EVENT")) {
			// EVENT <type>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			if (cmdArgs[1].equalsIgnoreCase("IND"))
				event(Competition.IND);
			else if (cmdArgs[1].equalsIgnoreCase("PARIND"))
				event(Competition.PARIND);
			else if (cmdArgs[1].equalsIgnoreCase("GRP"))
				event(Competition.GRP);
			else if (cmdArgs[1].equalsIgnoreCase("PARGRP"))
				event(Competition.PARGRP);
			else {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("NEWRUN"))
			new_run();
		else if (e.getActionCommand().startsWith("ENDRUN"))
			end_run();
		else if (e.getActionCommand().startsWith("PRINT")) {
			// PRINT <run>
			String[] cmdArgs = e.getActionCommand().split(" ");
			// TODO later: Default no args to current run if exists
			if (cmdArgs.length < 2) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				print(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("EXPORT")) {
			// EXPORT <run>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				export(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("NUM")) {
			// NUM <number>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				num(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}
			

		} else if (e.getActionCommand().startsWith("CLR")) {
			// CLR <number>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				clr(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("SWAP"))
			swap();
		else if (e.getActionCommand().startsWith("DNF"))
			dnf();
		else if (e.getActionCommand().startsWith("TRIG")) {
			// TRIG <num>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				display_error(Messages.numArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				trigger(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				display_error(Messages.parseArgError + " \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("START"))
			trigger(1);
		else if (e.getActionCommand().startsWith("FINISH"))
			trigger(2);
		else if (e.getActionCommand().startsWith("CANCEL"))
			cancel();
		else {
			display_error(Messages.cmdNotRecognized);
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void display_error(String errorMessage) {
		display_error(errorMessage, true);
	}

	public void display(String display) {
		m_display.displayStr(display);
	}

	public void display_error(String errorMessage, boolean ignored) {
		// Add to event log and/or do something with error
		m_display.displayErr(errorMessage);
		if (!ignored)
			System.exit(1);
	}
	
	public String getReadyText() {
		String ret = "";
		for(Racer r : m_run.getReady()) {
			ret += r.getReadyStr() + "\n";
		}
		return ret;
	}
	
	public String getRacingText() {
		String ret = "";
		for(Racer r : m_run.getRacing()) {
			ret += r.getRacingStr(getSysTime()) + "\n";
		}
		return ret;
	}
	
	public String getFinishedText() {
		String ret = "";
		for(Racer r : m_run.getFinished()) {
			ret += r.getFinishedStr() + "\n";
		}
		return ret;
	}

	// POWER
	// States allowed: ALL
	// (if off) turn system on, enter initial state.
	// (if on) turn system off, but stay in simulator.
	// Possibly moving to Timer
	private void power() {
		if (running) {
			running = false;
			display(Messages.powerDown);
			m_display.powerOff();
		} else {
			// resets system to initial state
			// Basically like a "restart" on a computer
			running = true;
			reset();
			display(Messages.powerOn);
			m_display.powerOn(this);
		}
	}

	// EXIT
	// States allowed: ALL
	// Exit the simulator.
	private void exit() {
		// NUKING SELF
		display(Messages.systemExiting);
		System.exit(0);
	}

	// RESET
	// States allowed: ALL
	// Resets the System to initial state and resets everything.
	private void reset() {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		m_state = ChronoState.INITIAL;
		m_comp = Competition.IND;
		m_channels = new Channel[NUM_CHANNELS];
		m_run = null;
		m_sysTime = null;
		runID = 1;
		runHistory = new ArrayList<Run>();
		for (int i = 0; i < NUM_CHANNELS; i++)
			m_channels[i] = new Channel(i + 1);
	}

	// TIME <hour>:<min>:<sec>
	// States allowed: ALL
	// Sets(advances) the System time to the time specified(so there is no wait
	// for test output).
	private void time(int hour, int min, double sec, double nano) {
		m_sysTime = LocalTime.of(hour, min, (int) sec, (int) nano);
	}

	// TOG <channel>
	// States allowed: ALL
	// Toggle the state of the channel specified.
	private void toggle(int channel) {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (channel < 1 || channel > NUM_CHANNELS) {
			display_error(Messages.channelRangeError);
			return;
		}

		m_channels[channel - 1].setEnabled(!m_channels[channel - 1].isEnabled());

		if (m_channels[channel - 1].isEnabled()) {
			display(Messages.enabledChannel + channel);
		} else {
			display(Messages.disabledChannel + channel);
		}

	}

	// CONN <sensor> <num>
	// States allowed: ALL
	// Connect a sensor(of type specified) to the channel specified.
	private void connect(Sensor sensor, int channel) {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (channel < 1 || channel > NUM_CHANNELS) {
			display_error(Messages.channelRangeError);
			return;
		}

		if (m_channels[channel - 1].isConnected()) {
			display_error(Messages.channelConnectionError);
			return;
		}

		display(Messages.connectingChannel + channel);
		m_channels[channel - 1].connect(sensor);
	}

	// DISC <num>
	// States allowed: ALL
	// Disconnect a sensor from the channel specified.
	private void disconnect(int channel) {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (channel < 1 || channel > NUM_CHANNELS) {
			display_error(Messages.channelRangeError);
			return;
		}

		if (!m_channels[channel - 1].isConnected()) {
			display_error(Messages.channelConnectionError);
			return;
		}

		display(Messages.disconnectingChannel + channel);
		m_channels[channel - 1].disconnect();
	}

	// EVENT <type>
	// States allowed: INITIAL
	// Changes the current competition to specified type.
	private void event(Competition type) {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (m_state != ChronoState.INITIAL) {
			display_error(Messages.compChangeError);
		}

		m_comp = type;
		display(Messages.eventComp + type.toString());
	}

	// NEWRUN
	// States allowed: INITIAL
	// Create a new Run (must end a run first).
	private void new_run() {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (m_state != ChronoState.INITIAL) {
			display_error(Messages.runInProgress);
			return;
		}

		m_run = new Run(runID, m_comp, this);
		m_state = ChronoState.RACING;
		display(Messages.creatingRun + runID);
	}

	// ENDRUN
	// States allowed: RACING
	// Done with the Run.
	private void end_run() {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (m_state != ChronoState.RACING) {
			display_error(Messages.runNotStarted);
			return;
		}
		m_run.endRun();

		runHistory.add(m_run);
		m_state = ChronoState.INITIAL;
		display(Messages.endingRun + runID++);
	}

	// PRINT <run>
	// States allowed: INITIAL
	// Print the run specified on stdout.
	private void print(int run) {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}

		// Check if run is not ended, need to use run id to reference the run
		boolean foundIt = false;
		for (Run r : runHistory) {
			if (r.getID() == run) {
				//TODO possibly sort r.getRacers() Collections.sort(r.getRacer......
				for (Racer x : r.getRacers()) {
					m_printer.printLine(x.toString());
				}
				foundIt = true;
				break;
			}
		}
		if (!foundIt) {
			display_error(Messages.runDoesNotExist);
		}
	}

	// EXPORT <run>
	// States allowed: INITIAL
	// Export the run specified as JSON to file "RUN<"+runID+">.json"
	private void export(int run) {
		// TODO Bug Testing
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		Gson g = new Gson();
		boolean foundIt = false;
		for (Run r : runHistory) {
			if (r.getID() == run) {
				String out = g.toJson(r.getRacers());

				try {
					PrintWriter writer = new PrintWriter("RUN" + run + ".json");
					writer.println(out);
					writer.close();
				} catch (Exception e) {
					display_error(Messages.exportError + e.getMessage());
					System.exit(1);
				}
				foundIt = true;
				break;
			}
		}
		if (!foundIt) {
			display_error(Messages.runDoesNotExist);
		}
	}

	//TODO
//	private void HTMLExport(int run) {
//		if (!running) {
//			display_error(Messages.systemNotRunning);
//			return;
//		}
//		String html;
//		Gson g = new Gson();
//		boolean foundIt = false;
//		for (Run r : runHistory) {
//			if (r.getID() == run) {
//				// TODO: Error-causing POST Request
//				try {
//					// now create a POST request
//					conn.setRequestMethod("POST");
//					conn.setDoOutput(true);
//					conn.setDoInput(true);
//					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//
//					// build a string that contains JSON from console
//					html = g.toJson(r.getHTMLRun());
//
//					// write out string to output buffer for message
//					out.writeBytes(html);
//					out.flush(); // cleans up the buffer
//					out.close(); // sends it to the server
//				} catch (Exception f) {
//					f.printStackTrace();
//				}
//
//				foundIt = true;
//				break;
//			}
//		}
//		if (!foundIt) {
//			display_error(Messages.runDoesNotExist);
//		}
//	}

	// NUM <number>
	// States allowed: RACING
	// Set <number> as the next competitor to start.
	private void num(int number) {

		if(runHistory.isEmpty() && m_state != ChronoState.RACING){
			display_error(Messages.runNotStarted);
			return;
		}
		if (m_run.isGRPStartedAndFinished()) {
			m_run.setGRPNumber(number);
			
		} else if (!m_run.isGRPStarted()) {
			if (m_run.addRacer(new Racer(number))) {
				display(Messages.addingRacer + number);
			} else {
				display_error(Messages.addingRacerError + number);
			}
		}
		
//OLD CODE
//		if (!running) {
//			display_error(Messages.systemNotRunning);
//			return;
//		}
//		if (m_state != ChronoState.RACING) {
//			display_error(Messages.runNotStarted);
//			return;
//		}
//		if (m_run.addRacer(new Racer(number))) {
//			display(Messages.addingRacer + number);
//		} else {
//			display_error(Messages.addingRacerError + number);
//		}
	}

	// CLR <number>
	// States allowed: RACING
	// Clear <number> from the run.
	private void clr(int number) {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (m_state != ChronoState.RACING) {
			display_error(Messages.runNotStarted);
			return;
		}

		if (m_run.removeRacer(new Racer(number))) {
			display(Messages.clearingRacer + number);
		} else {
			display_error(Messages.clearingRacerError + number);
		}
	}

	// SWAP ??
	// States allowed: -
	// This is a stupid command.
	private void swap() {
		// TODO Later
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (m_state != ChronoState.RACING) {
			display_error(Messages.runNotStarted);
			return;
		}
		
		m_run.swap();
	}

	// DNF
	// States allowed: RACING
	// The next competitor to finish will not finish.
	private void dnf() {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (m_state != ChronoState.RACING) {
			display_error(Messages.runNotStarted);
			return;
		}
		int number = m_run.dnf();
		if (number > 0)
			display("Racer DNF: " + number);
	}

	// TRIG <num>
	// States allowed: RACING
	// Trigger channel <num>
	private void trigger(int channel) {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (m_state != ChronoState.RACING) {
			display_error(Messages.runNotStarted);
			return;
		}
		if (channel < 1 || channel > NUM_CHANNELS) {
			display_error(Messages.channelRangeError);
			return;
		}

		if (m_channels[channel - 1].isEnabled() ) {//&& m_channels[channel - 1].isConnected()) {
			display("Tigger channel: " + channel);
			m_run.triggerChannel(m_channels[channel - 1], getSysTime());
		} else {
			if(!m_channels[channel - 1].isEnabled()) display_error(Messages.channelDisabled + channel);
			if(!m_channels[channel - 1].isConnected()) display_error(Messages.channelDisconnected + channel);
		}
		
	}

	// CANCEL
	// States allowed: RACING
	// Discard a racer's current start time and put racer back in queue as next
	// to start.
	private void cancel() {
		if (!running) {
			display_error(Messages.systemNotRunning);
			return;
		}
		if (m_state != ChronoState.RACING) {
			display_error(Messages.runNotStarted);
			return;
		}
		int number = m_run.cancel();
		if (number > 0)
			display(Messages.cancelRacer + number);
	}
}
