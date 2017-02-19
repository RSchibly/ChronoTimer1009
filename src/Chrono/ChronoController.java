package Chrono;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChronoController implements ActionListener {
	private static final int NUM_CHANNELS = 8;
	
	public enum ChronoState {INITIAL, RACING}
	public enum Competition {IND, PARAIND, GRP, PARGRP}
	
	private boolean running;
	private ChronoState m_state;
	private Competition m_comp;
	
	private Run m_run;
	private Channel[] m_channels;
	
	public ChronoController() {
		running = true;
		m_state = ChronoState.INITIAL;
		m_comp = Competition.IND;
		m_channels = new Channel[NUM_CHANNELS];
		
		for(int i = 0; i < NUM_CHANNELS; i++) m_channels[i] = new Channel(i);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().startsWith("POWER")) {
			//Need
		}
		else if(e.getActionCommand().startsWith("EXIT")) {
			//Need
		}
		else if(e.getActionCommand().startsWith("RESET")) {
			//Need
		}
		else if(e.getActionCommand().startsWith("TIME")) {
			//Need but this is stupid, may do differently
		}
		else if(e.getActionCommand().startsWith("TOG")) {
			//Need
		}
		else if(e.getActionCommand().startsWith("CONN")) {
			//
		}
		else if(e.getActionCommand().startsWith("DISC")) {
			//
		}
		else if(e.getActionCommand().startsWith("EVENT")) {
			//
		}
		else if(e.getActionCommand().startsWith("NEWRUN")) {
			switch(m_state) {
				case INITIAL:
					m_run = new Run(m_comp);
					
				break;
				default:
					//Problem
				break;
			}
		} 
		else if(e.getActionCommand().startsWith("ENDRUN")) {
			m_run = null;
		} 
		else if(e.getActionCommand().startsWith("PRINT")) {
			//
		}
		else if(e.getActionCommand().startsWith("EXPORT")) {
			//
		}
		else if(e.getActionCommand().startsWith("NUM")) {
			//
		}
		else if(e.getActionCommand().startsWith("CLR")) {
			//
		}
		else if(e.getActionCommand().startsWith("SWAP")) {
			//
		}
		else if(e.getActionCommand().startsWith("DNF")) {
			//
		}
		else if(e.getActionCommand().startsWith("TRIG")) {
			//
		}
		else if(e.getActionCommand().startsWith("START")) {
			//
		}
		else if(e.getActionCommand().startsWith("FINISH")) {
			//
		}
		else if(e.getActionCommand().startsWith("CANCEL")) {
			//
		}
		else {
			//Unknown command
		}
		
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
