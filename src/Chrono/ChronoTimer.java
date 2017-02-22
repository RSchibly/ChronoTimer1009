package Chrono;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ChronoTimer {

	public static void main(String[] args) throws InterruptedException {
		boolean isFile = false;
		String fileFile = "test.txt";
		Scanner scan;
		try {
			scan = new Scanner(new File(fileFile));
			isFile = true;
		} catch (FileNotFoundException e) {
			scan = new Scanner(System.in);
		}
		ChronoController controller = new ChronoController();

		// Main event loop

		while (controller.isRunning()) {

			int id = 0;
			if (isFile) {
				// TODO Parse time stamp if(isFile)
			} else {
				System.out.println("Please enter a command: ");
			}
			ActionEvent cmd = new ActionEvent(scan, id, scan.nextLine());
			id++;
			controller.actionPerformed(cmd);
		}

	}

}
