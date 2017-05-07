package JUnit;

import junit.framework.TestCase;

import java.awt.event.ActionEvent;

import Chrono.Channel;
import Chrono.Display;
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

	public void testPower(){
		action("power");
		assertEquals(controller.isRunning(), true);
	}
	
	public void testType(){
		action("power");
		action("EVENT PARIND");
		assertEquals("PARIND", controller.getComp().toShortStr());
		action("event ind");
		assertEquals("IND", controller.getComp().toShortStr());
		action("event GRP");
		assertEquals("GRP", controller.getComp().toShortStr());
		action("newrun");
	}

	public void testConn(){
		action("power");
		action("newrun");
		action("num 33");
		action("tog 1");
		assertTrue(controller.getChannel(1).isEnabled());
		action("conn eye 1");
		assertTrue(controller.getChannel(1).isConnected());
		assertNotSame(false, controller.getChannel(1).isConnected());
		action("disc 1");
		assertFalse(controller.getChannel(1).isConnected());
		action("tog 1");
		assertFalse(controller.getChannel(1).isEnabled());
		action("conn eye 1");
		assertTrue(controller.getChannel(1).isConnected());
		action("tog 1");
		assertTrue(controller.getChannel(1).isEnabled());
		action("trig 1");
		assertEquals(1, controller.getRun().getRacing().size());
		
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display display = new Display(false);
		Printer printer = new Printer(display);
		controller = new Controller(display, printer, false);
		id = ActionEvent.ACTION_FIRST;
	}

}
