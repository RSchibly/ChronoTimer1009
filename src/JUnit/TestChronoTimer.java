package JUnit;

import junit.framework.TestCase;

import java.awt.event.ActionEvent;

import Chrono.CommandLineDisplay;
import Chrono.Controller;
import Chrono.Display;
import Chrono.Printer;

public class TestChronoTimer extends TestCase {
	Controller controller;
	int id;

	public enum ChronoState {
		OFF, INITIAL, RACING
	}

	public enum Competition {
		IND, PARIND, GRP, PARGRP
	}

	public void action(String arg) {
		controller.actionPerformed(new ActionEvent("null", id++, arg.toUpperCase()));
	}

	public void test3() {
		action("power");
		assertEquals(controller.isRunning(), true);
	}

	public void test4() {

		action("power");
		assertEquals(controller.isRunning(), true);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display display = new CommandLineDisplay();
		Printer printer = new Printer();
		controller = new Controller(display, printer);
		id = ActionEvent.ACTION_FIRST;
	}

}
