package JUnit;

import java.awt.event.ActionEvent;

import Chrono.Controller;
import Chrono.Display;
import Chrono.Printer;
import Chrono.Controller.Competition;
import junit.framework.TestCase;

public class TestIND extends TestCase {
	Controller controller;
	int id;
	public void action(String arg) {
		controller.actionPerformed(new ActionEvent("null", id++, arg.toUpperCase()));
	}
	protected void setUp() throws Exception {
		super.setUp();
		Display display = new Display(false);
		Printer printer = new Printer(display);
		controller = new Controller(display, printer, false);
		id = ActionEvent.ACTION_FIRST;
	}

	public void test1(){
		action("power");
		assertEquals(true, controller.isRunning());
		action("newrun");
		action("num 344");
		action("num 69");
		assertEquals(2, controller.getRun().getReady().size());
		action("tog 1");
		assertNotSame(2, controller.getRun().getRacing().size());
		action("tog 2");
		action("conn eye 1");
		action("conn gate 2");
		action("start");
		action("start");
		assertEquals(2, controller.getRun().getRacing().size());
		action("finish");
		action("finish");
		assertEquals(2, controller.getRun().getFinished().size());
		action("endrun");
		assertNotSame(1, controller.getRun().getReady().size());
		assertEquals(2, controller.getRun().getRacers().size());
	}
	public void test2(){
		action("power");
		assertEquals(true, controller.isRunning());
		action("newrun");
		action("PARIND");
		action("event parind");
		assertSame("PARIND", controller.getComp().toShortStr());
		action("num 369");
		action("num 323");
		action("num 312");
		action("num 838");
		assertEquals(4, controller.getRun().getRacers().size());
		assertNotSame(2, controller.getRun().getFinished().size());
		action("tog 1");
		action("conn eye 1");
		action("tog 2");
		action("conn eye 2");
		action("tog 3");
		action("conn eye 3");
		action("tog 4");
		action("conn eye 4");
		action("start");
		assertEquals(3, controller.getRun().getReady().size());
		assertEquals(1, controller.getRun().getRacing().size());
		action("start");
		assertEquals(2, controller.getRun().getRacing().size());
		action("cancel");
		assertEquals(1, controller.getRun().getRacing().size());
		action("endrun");
		assertEquals(4, controller.getRun().getFinished().size());
	}
	
	public void test3(){
		action("power");
		assertEquals(true, controller.isRunning());
		action("newrun");
		assertSame("IND", controller.getComp().toShortStr());
		action("num 369");
		action("num 323");
		action("num 312");
		action("num 838");
		action("tog 1");
		action("conn eye 1");
		action("tog 2");
		action("conn eye 2");
		action("tog 3");
		action("conn eye 3");
		action("tog 4");
		action("conn eye 4");
		action("start");
		action("start");
		action("start");
		action("start");
		assertEquals(4, controller.getRun().getRacing().size());
		action("reset");
		action("newrun");
		action("num 369");
		action("num 323");
		action("num 312");
		action("num 838");
		assertEquals(4, controller.getRun().getReady().size());
	}
}
