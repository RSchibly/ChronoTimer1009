package JUnit;

import java.awt.event.ActionEvent;

import Chrono.CommandLineDisplay;
import Chrono.Controller;
import Chrono.Display;
import Chrono.Printer;
import junit.framework.TestCase;

public class TestParIND extends TestCase {
	Controller controller;
	int id;
	public void action(String arg) {
		controller.actionPerformed(new ActionEvent("null", id++, arg.toUpperCase()));
	}
	protected void setUp() throws Exception {
		super.setUp();
		Display display = new CommandLineDisplay();
		Printer printer = new Printer();
		controller = new Controller(display, printer);
		id = ActionEvent.ACTION_FIRST;
	}
	
	public void test1(){
		action("power");
		assertEquals(true, controller.isRunning());
		action("newrun");
		action("num 344");
		action("num 69");
		assertEquals(2, controller.getRun().getReadyQ().size());
		action("tog 1");
		assertNotSame(2, controller.getRunningQSize());
		action("tog 2");
		action("start");
		action("start");
		assertEquals(2, controller.getRun().getRunningQ().size());
		action("finish");
		action("finish");
		assertEquals(2, controller.getRun().getFinishedQ().size());
		action("endrun");
		
	}
	

}
