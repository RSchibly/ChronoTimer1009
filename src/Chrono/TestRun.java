package Chrono;

import java.util.ArrayList;

import Chrono.Controller.Competition;
import junit.framework.TestCase;

public class TestRun extends TestCase {

	private ArrayList<Lane> lanes;
    private ArrayList<Racer> racers;
	private Competition raceType;
	private int id;
	private int numLanes;
	private Controller parent;
	
	
	public TestRun(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		id = 344;
		Competition IND = null;
		raceType = IND;
		Racer e = null;
		Run run = new Run(id, IND, parent);
	}
	
	public void testRun() {
		
		
		
	}

	public void testGetRacers() {
		fail("Not yet implemented");
	}

	public void testDnf() {
		fail("Not yet implemented");
	}

	public void testCancel() {

		fail("Not yet implemented");
	}

	public void testTriggerChannel() {
		fail("Not yet implemented");
	}

	public void testAddRacer() throws Exception {
		setUp();
		Racer e=null;
		racers.add(e);
		assertEquals(1, racers.size());
		
	}

	public void testRemoveRacer() {
		fail("Not yet implemented");
	}

	public void testEndRun() {
		fail("Not yet implemented");
	}

	public void testGetID() {
		fail("Not yet implemented");
	}

}
