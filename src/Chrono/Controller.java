package Chrono;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import Chrono.Channel.Sensor;

public class Controller implements ActionListener {
	private static final int NUM_CHANNELS = 8;

	public enum ChronoState {
		OFF, INITIAL, RACING
	}

	public enum Competition {
		IND, PARIND, GRP, PARGRP
	}

	private boolean running;
	private ChronoState m_state;
	private Competition m_comp;
	private RunHistory m_runHistory;

	// Holds the offset time
	private LocalTime m_sysTime;

	private Channel[] m_channels;
	private Run m_run;
	private int runID;
	private ArrayList<Run> runHistory;

	public Controller() {
		m_runHistory = new RunHistory();
		running = false;
		m_state = ChronoState.OFF;
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
			// TIME <hour>:<min>:<sec>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			String[] timeArgs = cmdArgs[1].split(":");
			if (timeArgs.length < 3) {
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				time(Integer.parseInt(timeArgs[0]), Integer.parseInt(timeArgs[1]), Double.parseDouble(timeArgs[2]));
			} catch (NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("TOG")) {
			// TOG <channel>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				toggle(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("CONN")) {
			// CONN <sensor> <num>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 3) {
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			int channel;

			try {
				channel = Integer.parseInt(cmdArgs[2]);
			} catch (NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			if (cmdArgs[1].equalsIgnoreCase("EYE"))
				connect(Sensor.EYE, channel);
			else if (cmdArgs[1].equalsIgnoreCase("GATE"))
				connect(Sensor.GATE, channel);
			else if (cmdArgs[1].equalsIgnoreCase("PAD"))
				connect(Sensor.PAD, channel);
			else {
				cmd_error("Could not resolve argument type in command \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("DISC")) {
			// DISC <num>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				disconnect(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("EVENT")) {
			// EVENT <type>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
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
				cmd_error("Could not resolve argument type in command \"" + e.getActionCommand() + "\"");
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
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				print(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("EXPORT")) {
			// EXPORT <run>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				export(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("NUM")) {
			// NUM <number>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				num(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("CLR")) {
			// CLR <number>
			String[] cmdArgs = e.getActionCommand().split(" ");
			if (cmdArgs.length < 2) {
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				clr(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \"" + e.getActionCommand() + "\"");
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
				cmd_error("Incorrect number of arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}

			try {
				trigger(Integer.parseInt(cmdArgs[1]));
			} catch (NumberFormatException ex) {
				cmd_error("Could not parse arguments in command \"" + e.getActionCommand() + "\"");
				return;
			}
		} else if (e.getActionCommand().startsWith("START"))
			trigger(1);
		else if (e.getActionCommand().startsWith("FINISH"))
			trigger(2);
		else if (e.getActionCommand().startsWith("CANCEL"))
			cancel();
		else {
			cmd_error("Command not recognized.");
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

	public void cmd_print(String print) {
		System.out.println(print);
	}

	public void cmd_error(String errorMessage, boolean ignored) {
		// Add to event log and/or do something with error
		System.out.println(errorMessage);
	}

	// POWER
	// States allowed: ALL
	// (if off) turn system on, enter initial state.
	// (if on) turn system off, but stay in simulator.
	// Possibly moving to Timer
	private void power() {
		if (running) {
			running = false;
			System.out.println("Powering off...");
		} else {
			// resets system to initial state
			// Basically like a "restart" on a computer
			running = true;
			reset();
			System.out.println("Powering on...");
		}
	}

	// EXIT
	// States allowed: ALL
	// Exit the simulator.
	private void exit() {
		// NUKING SELF
		System.err.println("There goes your System32...");
		System.exit(0);
	}

	// RESET
	// States allowed: ALL
	// Resets the System to initial state and resets everything.
	private void reset() {
		if (!running) {
			cmd_error("System not running.");
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
	private void time(int hour, int min, double sec) {
		// TODO Sprint 1
		// Possible use as an offset and pass System time + offset to relevant
		// functions?
		// Maybe this command is unnecessary
		m_sysTime = LocalTime.of(hour, min, (int) sec);
	}

	// TOG <channel>
	// States allowed: ALL
	// Toggle the state of the channel specified.
	private void toggle(int channel) {
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (channel < 1 || channel > NUM_CHANNELS) {
			cmd_error("Channel out of range.");
			return;
		}
		m_channels[channel - 1].setEnabled(!m_channels[channel - 1].isEnabled());
		if (m_channels[channel - 1].isEnabled()) {
			System.out.println("Enabled channel: " + channel);
		} else {
			System.out.println("Disabled channel: " + channel);
		}

	}

	// CONN <sensor> <num>
	// States allowed: ALL
	// Connect a sensor(of type specified) to the channel specified.
	private void connect(Sensor sensor, int channel) {
		// TODO Later
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (channel < 1 || channel > NUM_CHANNELS) {
			cmd_error("Channel out of range.");
			return;
		}

		if (m_channels[channel - 1].isConnected()) {
			cmd_error("Channel is already connected to a sensor.");
			return;
		}

		System.out.println("Connecting channel: " + channel);
		m_channels[channel - 1].connect(sensor);
	}

	// DISC <num>
	// States allowed: ALL
	// Disconnect a sensor from the channel specified.
	private void disconnect(int channel) {
		// TODO Later
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (channel < 1 || channel > NUM_CHANNELS) {
			cmd_error("Channel out of range.");
			return;
		}

		if (!m_channels[channel - 1].isConnected()) {
			cmd_error("Channel is already disconnected.");
			return;
		}

		System.out.println("Disconnect channel: " + channel);
		m_channels[channel - 1].disconnect();
	}

	// EVENT <type>
	// States allowed: INITIAL
	// Changes the current competition to specified type.
	private void event(Competition type) {
		// TODO Later
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (m_state != ChronoState.INITIAL) {
			cmd_error("Cannot change competition type during a Run, must end current Run first.");
		}

		m_comp = type;
		System.out.println("Event competition: " + type.toString());
	}

	// NEWRUN
	// States allowed: INITIAL
	// Create a new Run (must end a run first).
	private void new_run() {
		// TODO Sprint 1
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (m_state != ChronoState.INITIAL) {
			cmd_error("A Run is already in progress, must end the current Run first.");
			return;
		}

		m_run = new Run(runID, m_comp, this);
		m_state = ChronoState.RACING;
		System.out.println("Creating new run...");
	}

	// ENDRUN
	// States allowed: RACING
	// Done with the Run.
	private void end_run() {
		// TODO Sprint 1
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (m_state != ChronoState.RACING) {
			cmd_error("A Run is not in progress, must start a Run first.");
			return;
		}
		if (!m_run.getReadyQ().isEmpty()) {
			for (int i = 0; i < m_run.getReadyQ().size(); i++) {
				m_run.getReadyQ().get(i).getTimer().setDNF(true);
				m_run.getFinishedQ().add(m_run.getReadyQ().get(i));
			}
		}
		if (!m_run.getRunningQ().isEmpty()) {
			for (int i = 0; i < m_run.getRunningQ().size(); i++) {
				m_run.getRunningQ().get(i).getTimer().setDNF(true);
				m_run.getFinishedQ().add(m_run.getRunningQ().get(i));
			}
		}
		//Running Q and Ready Q are NOT empty after end_run if during run they weren't
		m_runHistory.addRun(m_run);
		m_state = ChronoState.INITIAL;
		System.out.println("Ending current run...");
	}

	// PRINT <run>
	// States allowed: INITIAL
	// Print the run specified on stdout.
	private void print(int run) {
		// TODO Later
		if (!running) {
			cmd_error("System not running.");
			return;
		}

		Run r = m_runHistory.getRun(run);
		for (Racer x : r.getFinishedQ()) {
			cmd_print("Racer Number : " + x.getNumber() + "\t -> Time: " + x.getTimer().getTime());
		}
	}

	// EXPORT <run>
	// States allowed: INITIAL
	// Export the run specified in XML to file "RUN<"+runID+">.xml"
	private void export(int run) {
		// TODO Later
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		// export runHistory.get(run);
	}

	// NUM <number>
	// States allowed: RACING
	// Set <number> as the next competitor to start.
	private void num(int number) {
		// TODO Sprint 1
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (m_state != ChronoState.RACING) {
			cmd_error("A Run is not in progress, must start a Run first.");
			return;
		}
		if (m_run.addRacer(new Racer(number))) {
			System.out.println("Adding racer: " + number);
		} else {
			cmd_error("Failed to add racer: " + number);
		}
	}

	// CLR <number>
	// States allowed: RACING
	// Clear <number> from the run.
	private void clr(int number) {
		// TODO Sprint 1
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (m_state != ChronoState.RACING) {
			cmd_error("A Run is not in progress, must start a Run first.");
			return;
		}

		if (m_run.removeRacer(new Racer(number))) {
			System.out.println("Clearing racer: " + number);
		} else {
			cmd_error("Failed to clear racer: " + number);
		}
	}

	// SWAP ??
	// States allowed: -
	// This is a stupid command.
	private void swap() {
		// TODO Later
		if (!running) {
			cmd_error("System not running.");
			return;
		}
	}

	// DNF
	// States allowed: RACING
	// The next competitor to finish will not finish.
	private void dnf() {
		// TODO Sprint 1
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (m_state != ChronoState.RACING) {
			cmd_error("A Run is not in progress, must start a Run first.");
			return;
		}
		int number = m_run.dnf();
		if (number > 0)
			System.out.println("Racer DNF: " + number);
	}

	// TRIG <num>
	// States allowed: RACING
	// Trigger channel <num>
	private void trigger(int channel) {
		// TODO Sprint 1
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (m_state != ChronoState.RACING) {
			cmd_error("A Run is not in progress, must start a Run first.");
			return;
		}
		if (channel < 1 || channel > NUM_CHANNELS) {
			cmd_error("Channel out of range.");
			return;
		}

		if (m_channels[channel - 1].isEnabled()) {
			System.out.println("Tigger channel: " + channel);
			m_run.triggerChannel(m_channels[channel - 1], m_sysTime);
		} else {
			cmd_error("Channel currently disabled: " + channel);
		}

	}

	// CANCEL
	// States allowed: RACING
	// Discard a racer's current start time and put racer back in queue as next
	// to start.
	private void cancel() {
		// TODO Sprint 1
		if (!running) {
			cmd_error("System not running.");
			return;
		}
		if (m_state != ChronoState.RACING) {
			cmd_error("A Run is not in progress, must start a Run first.");
			return;
		}
		int number = m_run.cancel();
		if (number > 0)
			System.out.println("Cancelling racer: " + number);
	}
}
