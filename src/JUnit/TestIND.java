package JUnit;

import java.awt.event.ActionEvent;

import Chrono.CommandLineDisplay;
import Chrono.Controller;
import Chrono.Display;
import Chrono.Printer;
import Chrono.Controller.Competition;
import junit.framework.TestCase;

public class TestIND extends TestCase {
	private Competition PARIND;
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
		assertNotSame(1, controller.getRun().getReadyQ().size());
		assertEquals(2, controller.getRun().getRacers().size());
	}
	public void test2(){
		action("power");
		assertEquals(true, controller.isRunning());
		action("newrun");
		action("PARIND");
		action("event parind");
		assertSame("PARIND", controller.getComp().toString());
		action("num 369");
		action("num 323");
		action("num 312");
		action("num 838");
		assertEquals(4, controller.getRun().getRacers().size());
		assertNotSame(2, controller.getRun().getFinishedQ().size());
		action("tog 1");
		action("tog 2");
		action("tog 3");
		action("tog 4");
		action("start");
		assertEquals(3, controller.getRun().getReadyQ().size());
		assertEquals(1, controller.getRun().getRunningQ().size());
		action("start");
		assertEquals(2, controller.getRun().getRunningQ().size());
		action("cancel");
		assertEquals(1, controller.getRun().getRunningQ().size());
		action("endrun");
		assertEquals(4, controller.getRun().getFinishedQ().size());
	}
	
	public void test3(){
		action("power");
		assertEquals(true, controller.isRunning());
		action("newrun");
		assertSame("IND", controller.getComp().toString());
		action("num 369");
		action("num 323");
		action("num 312");
		action("num 838");
		action("tog 1");
		action("tog 2");
		action("tog 3");
		action("tog 4");
		action("start");
		action("start");
		action("start");
		action("start");
		assertEquals(4, controller.getRun().getRunningQ().size());
		action("reset");
		action("newrun");
		action("num 369");
		action("num 323");
		action("num 312");
		action("num 838");
		assertEquals(4, controller.getRun().getReadyQ().size());
	}
}
