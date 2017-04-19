package Chrono;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import Chrono.Run;
import Chrono.Controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Scanner;
import org.junit.Rule;
import org.junit.Test;
import java.io.File;

import Chrono.Channel;
import Chrono.CommandLineDisplay;
import Chrono.Lane;
import Chrono.Messages;
import Chrono.Racer;
import Chrono.Timer;
import Chrono.Viewer;
import Chrono.Controller;
import org.junit.Before;
import org.junit.Test;

public class TestChrono {
	public void test() throws Exception{
		ClassLoader cL = getClass().getClassLoader();
		File f = new File(cL.getResource("file/AddingTest.txt").getFile());
		System.out.println(f);
	}

}
