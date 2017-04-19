package Chrono;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import Chrono.Run;
import Chrono.Controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Scanner;

import Chrono.Channel;
import Chrono.CommandLineDisplay;
import Chrono.Lane;
import Chrono.Messages;
import Chrono.Racer;
import Chrono.Timer;
import Chrono.Viewer;
import Chrono.Controller;

public class TestChronoTimer extends TestCase {

	public enum ChronoState {
		OFF, INITIAL, RACING
	}

	public enum Competition {
		IND, PARIND, GRP, PARGRP
	}
	
	Display display;
	Printer printer;
	Controller controller = new Controller(display, printer);
	public void test3() throws InterruptedException{
		Controller controller = new Controller(display, printer);
		String[] args = null;
		Simulator.main(args);
		String resp = "n";
		Display display = new CommandLineDisplay();
		//display = "power";
	//	controller.actionPerformed(null)
		//controller.actionPerformed(POWER);
		assertEquals(controller.isRunning(), true);
	}

	
	
	
	protected void setUp() throws Exception {
		super.setUp();		
		Display display = new CommandLineDisplay();
		Printer printer = new Printer();
		Controller controller = new Controller(display, printer);
	}
	
}
