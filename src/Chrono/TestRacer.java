package Chrono;

import junit.framework.TestCase;

public class TestRacer extends TestCase {

	private int racerNumber;
	private Timer time;
	
	
	public TestRacer(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		this.racerNumber = racerNumber;
		time = new Timer();
		super.setUp();
	}

	public void testHashCode() throws Exception {
		setUp();
		racerNumber = 8;
		int q = racerNumber * time.hashCode();
		int w = hashCode();
		assertEquals(q, w);
	}

	public void testRacer() {
		
	}

	public void testGetNumber() {
		assertEquals(racerNumber, this.racerNumber);
		assertNotSame(racerNumber+1, this.racerNumber);
	}

	public void testGetTimer() {
		assertEquals(time, this.time);
	}


	public void testEqualsObject() {
		
	}

}
