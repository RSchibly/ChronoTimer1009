package Chrono;

import java.awt.event.ActionEvent;
import java.util.Scanner;

public class ChronoTimer {

	public static void main(String[] args) throws InterruptedException {
		
		Scanner scan = new Scanner(System.in);
		ChronoController controller = new ChronoController();
		
		//Main event loop
		while(controller.isRunning()) {
			int id = 0;
			ActionEvent cmd = new ActionEvent(scan, id, scan.nextLine());
			id++;
			controller.actionPerformed(cmd);
		}
	}

}
