//package JUnit;
//
//import java.awt.event.ActionEvent;
//
//import Chrono.CommandLineDisplay;
//import Chrono.Controller;
//import Chrono.Display;
//import Chrono.Printer;
//import junit.framework.TestCase;
//
//public class TestTime extends TestCase {
//	Controller controller;
//	int id;
//	public void action(String arg) {
//		controller.actionPerformed(new ActionEvent("null", id++, arg.toUpperCase()));
//	}
//	protected void setUp() throws Exception {
//		super.setUp();
//		Display display = new CommandLineDisplay();
//		Printer printer = new Printer();
//		controller = new Controller(display, printer);
//		id = ActionEvent.ACTION_FIRST;
//	}
//	
//	/*public void test1(){
//		action("power");
//		action("newrun");
//		action("num 134");
//		action("num 939");
//		action("num 765");
//		action("num 098");
//		action("tog 1");
//		action("tog 2");
//		action("tog 3");
//		action("tog 4");
//		action("14:02:00.0 start");
//		action("start");
//		action("start");
//		action("start");
//		action("16:02:00.0 finish");
//		action("finish");
//		action("finish");
//		action("finish");
//		action("endrun");
//		action("print 1");
//
//		
//	}*/
//	public void test2() throws InterruptedException{
//		action("power");
//		action("newrun");
//		action("num 134");
//		action("tog 1");
//		action("tog 2");
//		action("time 16:33:33:32");
//		action("start");
//		action("time 16:35:33:22");
//		action("finish");
//		action("time 18:38:33:33");
//		action("endrun");
//		action("print 1");
//	}
//
//}
