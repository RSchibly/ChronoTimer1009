package Chrono;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Chrono.Channel.Sensor;
import Chrono.Controller.Competition;

import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Viewer extends JFrame {

	private JPanel contentPane;
	private JTextField inputText;
	private JLabel lblStatusText;
	private JTextArea printerText;
	private KeyPadCommand m_cmd;
	private Controller controller;
	private JTextArea readyText;
	private JTextArea racingText;
	private JTextArea finishedText;
	private javax.swing.Timer updateTimer;
	
	public enum KeyPadCommand {
		NONE("NONE"), NUM("NUM"), CLR("CLR"), PRINT("PRINT"), EXPORT("EXPORT");
		private final String display;
		  private KeyPadCommand(String s) {
		    display = s;
		  }
		  @Override
		  public String toString() {
		    return display;
		  }
	}

	public Viewer(Controller controller) {
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("fill", "[grow][grow][grow][73.00][grow][]", "[][][][grow][][grow][][grow][]"));
		m_cmd = KeyPadCommand.NONE;
		
		//Panels
		JPanel channelPanel = new JPanel();
		contentPane.add(channelPanel, "cell 2 5 2 1,alignx center,grow");
		channelPanel.setLayout(new MigLayout("", "[][][][][]", "[][][][][][][]"));
		
		JPanel commandPanel = new JPanel();
		contentPane.add(commandPanel, "cell 0 5 2 1,grow");
		commandPanel.setLayout(new MigLayout("", "[grow]", "[][][][][][][]"));
		
		JPanel keypadPanel = new JPanel();
		contentPane.add(keypadPanel, "cell 4 5 2 1,grow");
		keypadPanel.setLayout(new MigLayout("", "[grow][grow][grow]", "[][][][]"));
		
		JPanel connectionPanel = new JPanel();
		contentPane.add(connectionPanel, "cell 0 7 4 1,grow");
		connectionPanel.setLayout(new MigLayout("", "[][][][][][][][]", "[][][][]"));
				

		//General Labels
		JLabel lblChronotimer = new JLabel("ChronoTimer1009");
		contentPane.add(lblChronotimer, "cell 1 0,span 5,alignx center,aligny center");
		
		JLabel lblCompetitionType = new JLabel("Competition Type:");
		contentPane.add(lblCompetitionType, "cell 0 1,alignx trailing");
		
		JLabel lblRunId = new JLabel("Run ID#: ");
		contentPane.add(lblRunId, "cell 4 1,alignx trailing");
		
		JLabel lblRunIdNum = new JLabel(" ");
		contentPane.add(lblRunIdNum, "flowx,cell 5 1");
		
		JLabel lblReady = new JLabel("Ready");
		contentPane.add(lblReady, "cell 0 2 2 1,alignx center");
		
		JLabel lblRacing = new JLabel("Racing");
		contentPane.add(lblRacing, "cell 2 2 2 1,alignx center");
		
		JLabel lblFinished = new JLabel("Finished");
		contentPane.add(lblFinished, "cell 4 2 2 1,alignx center");
		
		JLabel lblStatus = new JLabel("Status:");
		contentPane.add(lblStatus, "cell 0 4");
		
		lblStatusText = new JLabel("Welcome to ChronoTimer1009!");
		contentPane.add(lblStatusText, "cell 1 4 2 1");
		
		JLabel lblInput = new JLabel("Input:");
		contentPane.add(lblInput, "cell 3 4,alignx trailing");
		
		JLabel lblChannelConnections = new JLabel("Channel Connections:");
		contentPane.add(lblChannelConnections, "cell 0 6,alignx center");
		
		JLabel lblPrinter = new JLabel("Printer:");
		contentPane.add(lblPrinter, "cell 4 6 2 1,alignx center");		
		
		JLabel lblStart = new JLabel("Start:");
		channelPanel.add(lblStart, "cell 0 1");
		
		JLabel lblFinish = new JLabel("Finish:");
		channelPanel.add(lblFinish, "cell 0 5");
		
		JLabel lblEnabledisable = new JLabel("Enable/Disable:");
		channelPanel.add(lblEnabledisable, "cell 0 2");
		JLabel lbl2Enabledisable = new JLabel("Enable/Disable:");
		channelPanel.add(lbl2Enabledisable, "cell 0 6");
		
		
		//Channel Labels
		JLabel lblChan1 = new JLabel("1");
		channelPanel.add(lblChan1, "cell 1 0,alignx center");
		JLabel lbl2Chan1 = new JLabel("1");
		connectionPanel.add(lbl2Chan1, "cell 0 0,alignx center");
		
		JLabel lblChan2 = new JLabel("2");
		channelPanel.add(lblChan2, "cell 1 4,alignx center");
		JLabel lbl2Chan2 = new JLabel("2");
		connectionPanel.add(lbl2Chan2, "cell 4 0,alignx trailing");
		
		JLabel lblChan3 = new JLabel("3");
		channelPanel.add(lblChan3, "cell 2 0,alignx center");
		JLabel lbl2Chan3 = new JLabel("3");
		connectionPanel.add(lbl2Chan3, "cell 0 1,alignx trailing");
		
		JLabel lblChan4 = new JLabel("4");
		channelPanel.add(lblChan4, "cell 2 4,alignx center");
		JLabel lbl2Chan4 = new JLabel("4");
		connectionPanel.add(lbl2Chan4, "cell 4 1,alignx trailing");
		
		JLabel lblChan5 = new JLabel("5");
		channelPanel.add(lblChan5, "cell 3 0,alignx center");
		JLabel lbl2Chan5 = new JLabel("5");
		connectionPanel.add(lbl2Chan5, "cell 0 2,alignx trailing");
		
		JLabel lblChan6 = new JLabel("6");
		channelPanel.add(lblChan6, "cell 3 4,alignx center");
		JLabel lbl2Chan6 = new JLabel("6");
		connectionPanel.add(lbl2Chan6, "cell 4 2,alignx trailing");
		
		JLabel lblChan7 = new JLabel("7");
		channelPanel.add(lblChan7, "cell 4 0,alignx center");
		JLabel lbl2Chan7 = new JLabel("7");
		connectionPanel.add(lbl2Chan7, "cell 0 3,alignx trailing");
		
		JLabel lblChan8 = new JLabel("8");
		channelPanel.add(lblChan8, "cell 4 4,alignx center");
		JLabel lbl2Chan8 = new JLabel("8");
		connectionPanel.add(lbl2Chan8, "cell 4 3,alignx trailing");
		
		
		//TextAreas
		inputText = new JTextField();
		contentPane.add(inputText, "cell 4 4 2 1,growx");
		inputText.setColumns(10);
		
		readyText = new JTextArea();
		contentPane.add(readyText, "cell 0 3 2 1,grow");
		
		racingText = new JTextArea();
		contentPane.add(racingText, "cell 2 3 2 1,grow");
		
		finishedText = new JTextArea();
		contentPane.add(finishedText, "cell 4 3 2 1,grow");
		
		printerText = new JTextArea();
		contentPane.add(printerText, "cell 4 7 2 1,grow");
		
		updateTimer = new javax.swing.Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				readyText.setText(controller.getReadyText());
				racingText.setText(controller.getRacingText());
				finishedText.setText(controller.getFinishedText());
			}
		});
		
		
		//ComboBoxes
		JComboBox<Competition> cbbxCompType = new JComboBox<Competition>();
		cbbxCompType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				Competition c = (Competition)cbbxCompType.getSelectedItem();
				String actionCmd = "EVENT ";
				if(c == Competition.IND) {
					actionCmd += "IND";
				}
				else if (c == Competition.PARIND) {
					actionCmd += "PARIND";
				}
				else if (c == Competition.GRP) {
					actionCmd += "GRP";
				}
				else if (c == Competition.PARGRP) {
					actionCmd += "PARGRP";
				}
				controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCmd));
			}
		});
		cbbxCompType.setModel(new DefaultComboBoxModel<Competition>(Competition.values()));
		cbbxCompType.setSelectedItem(Competition.IND);
		contentPane.add(cbbxCompType, "cell 1 1 2 1,growx");
		
		JComboBox<Sensor> cbbxChan1 = new JComboBox<Sensor>();
		cbbxChan1.setModel(new DefaultComboBoxModel<Sensor>(Sensor.values()));
		cbbxChan1.setSelectedItem(Sensor.NONE);
		connectionPanel.add(cbbxChan1, "cell 1 0,growx");
		
		JComboBox<Sensor> cbbxChan2 = new JComboBox<Sensor>();
		cbbxChan2.setModel(new DefaultComboBoxModel<Sensor>(Sensor.values()));
		cbbxChan2.setSelectedItem(Sensor.NONE);
		connectionPanel.add(cbbxChan2, "cell 5 0,growx");
		
		JComboBox<Sensor> cbbxChan3 = new JComboBox<Sensor>();
		cbbxChan3.setModel(new DefaultComboBoxModel<Sensor>(Sensor.values()));
		cbbxChan3.setSelectedItem(Sensor.NONE);
		connectionPanel.add(cbbxChan3, "cell 1 1,growx");
		
		JComboBox<Sensor> cbbxChan4 = new JComboBox<Sensor>();
		cbbxChan4.setModel(new DefaultComboBoxModel<Sensor>(Sensor.values()));
		cbbxChan4.setSelectedItem(Sensor.NONE);
		connectionPanel.add(cbbxChan4, "cell 5 1,growx");
		
		JComboBox<Sensor> cbbxChan5 = new JComboBox<Sensor>();
		cbbxChan5.setModel(new DefaultComboBoxModel<Sensor>(Sensor.values()));
		cbbxChan5.setSelectedItem(Sensor.NONE);
		connectionPanel.add(cbbxChan5, "cell 1 2,growx");
		
		JComboBox<Sensor> cbbxChan6 = new JComboBox<Sensor>();
		cbbxChan6.setModel(new DefaultComboBoxModel<Sensor>(Sensor.values()));
		cbbxChan6.setSelectedItem(Sensor.NONE);
		connectionPanel.add(cbbxChan6, "cell 5 2,growx");
		
		JComboBox<Sensor> cbbxChan7 = new JComboBox<Sensor>();
		cbbxChan7.setModel(new DefaultComboBoxModel<Sensor>(Sensor.values()));
		cbbxChan7.setSelectedItem(Sensor.NONE);
		connectionPanel.add(cbbxChan7, "cell 1 3,growx");		
		
		JComboBox<Sensor> cbbxChan8 = new JComboBox<Sensor>();
		cbbxChan8.setModel(new DefaultComboBoxModel<Sensor>(Sensor.values()));
		cbbxChan8.setSelectedItem(Sensor.NONE);
		connectionPanel.add(cbbxChan8, "cell 5 3,growx");
		
		
		//Channel Buttons
		JButton btnChan1 = new JButton(" ");
		btnChan1.setActionCommand("TRIG 1");
		btnChan1.addActionListener(controller);
		channelPanel.add(btnChan1, "cell 1 1,alignx center");
		
		JButton btnChan2 = new JButton(" ");
		btnChan2.setActionCommand("TRIG 2");
		btnChan2.addActionListener(controller);
		channelPanel.add(btnChan2, "cell 1 5,alignx center");
		
		JButton btnChan3 = new JButton(" ");
		btnChan3.setActionCommand("TRIG 3");
		btnChan3.addActionListener(controller);
		channelPanel.add(btnChan3, "cell 2 1,alignx center");
		
		JButton btnChan4 = new JButton(" ");
		btnChan4.setActionCommand("TRIG 4");
		btnChan4.addActionListener(controller);
		channelPanel.add(btnChan4, "cell 2 5,alignx center");
		
		JButton btnChan5 = new JButton(" ");
		btnChan5.setActionCommand("TRIG 5");
		btnChan5.addActionListener(controller);
		channelPanel.add(btnChan5, "cell 3 1,alignx center");
		
		JButton btnChan6 = new JButton(" ");
		btnChan6.setActionCommand("TRIG 6");
		btnChan6.addActionListener(controller);
		channelPanel.add(btnChan6, "cell 3 5,alignx center");
		
		JButton btnChan7 = new JButton(" ");
		btnChan7.setActionCommand("TRIG 7");
		btnChan7.addActionListener(controller);
		channelPanel.add(btnChan7, "cell 4 1,alignx center");
		
		JButton btnChan8 = new JButton(" ");
		btnChan8.setActionCommand("TRIG 8");
		btnChan8.addActionListener(controller);
		channelPanel.add(btnChan8, "cell 4 5,alignx center");
		
		
		//Channel Checkboxes
		JCheckBox ckbxChan1 = new JCheckBox("");
		ckbxChan1.setSelected(controller.getChannel(1).isEnabled());
		ckbxChan1.setActionCommand("TOG 1");
		ckbxChan1.addActionListener(controller);
		channelPanel.add(ckbxChan1, "cell 1 2,alignx center");
		
		JCheckBox ckbxChan2 = new JCheckBox("");
		ckbxChan2.setSelected(controller.getChannel(2).isEnabled());
		ckbxChan2.setActionCommand("TOG 2");
		ckbxChan2.addActionListener(controller);
		channelPanel.add(ckbxChan2, "cell 1 6,alignx center");
		
		JCheckBox ckbxChan3 = new JCheckBox("");
		ckbxChan3.setSelected(controller.getChannel(3).isEnabled());
		ckbxChan3.setActionCommand("TOG 3");
		ckbxChan3.addActionListener(controller);
		channelPanel.add(ckbxChan3, "cell 2 2,alignx center");
		
		JCheckBox ckbxChan4 = new JCheckBox("");
		ckbxChan4.setSelected(controller.getChannel(4).isEnabled());
		ckbxChan4.setActionCommand("TOG 4");
		ckbxChan4.addActionListener(controller);
		channelPanel.add(ckbxChan4, "cell 2 6,alignx center");
		
		JCheckBox ckbxChan5 = new JCheckBox("");
		ckbxChan5.setSelected(controller.getChannel(5).isEnabled());
		ckbxChan5.setActionCommand("TOG 5");
		ckbxChan5.addActionListener(controller);
		channelPanel.add(ckbxChan5, "cell 3 2,alignx center");
		
		JCheckBox ckbxChan6 = new JCheckBox("");
		ckbxChan6.setSelected(controller.getChannel(6).isEnabled());
		ckbxChan6.setActionCommand("TOG 6");
		ckbxChan6.addActionListener(controller);
		channelPanel.add(ckbxChan6, "cell 3 6,alignx center");
		
		JCheckBox ckbxChan7 = new JCheckBox("");
		ckbxChan7.setSelected(controller.getChannel(7).isEnabled());
		ckbxChan7.setActionCommand("TOG 7");
		ckbxChan7.addActionListener(controller);
		channelPanel.add(ckbxChan7, "cell 4 2,alignx center");
		
		JCheckBox ckbxChan8 = new JCheckBox("");
		ckbxChan8.setSelected(controller.getChannel(8).isEnabled());
		ckbxChan8.setActionCommand("TOG 8");
		ckbxChan8.addActionListener(controller);
		channelPanel.add(ckbxChan8, "cell 4 6,alignx center");
		
		//Channel Connection Buttons
		JButton btnChan1Con = new JButton("Connect");
		btnChan1Con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sensorConnection(btnChan1Con, cbbxChan1, 1);
			}
		});
		connectionPanel.add(btnChan1Con, "cell 2 0,alignx center");
		
		JButton btnChan2Con = new JButton("Connect");
		btnChan2Con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sensorConnection(btnChan2Con, cbbxChan2, 2);
			}
		});
		connectionPanel.add(btnChan2Con, "cell 6 0,alignx center");
		
		JButton btnChan3Con = new JButton("Connect");
		btnChan3Con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sensorConnection(btnChan3Con, cbbxChan3, 3);
			}
		});
		connectionPanel.add(btnChan3Con, "cell 2 1,alignx center");
		
		JButton btnChan4Con = new JButton("Connect");
		btnChan4Con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sensorConnection(btnChan4Con, cbbxChan4, 4);
			}
		});
		connectionPanel.add(btnChan4Con, "cell 6 1,alignx center");
		
		JButton btnChan5Con = new JButton("Connect");
		btnChan5Con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sensorConnection(btnChan5Con, cbbxChan5, 5);
			}
		});
		connectionPanel.add(btnChan5Con, "cell 2 2,alignx center");
		
		JButton btnChan6Con = new JButton("Connect");
		btnChan6Con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sensorConnection(btnChan6Con, cbbxChan6, 6);
			}
		});
		connectionPanel.add(btnChan6Con, "cell 6 2,alignx center");
		
		JButton btnChan7Con = new JButton("Connect");
		btnChan7Con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sensorConnection(btnChan7Con, cbbxChan7, 7);
			}
		});
		connectionPanel.add(btnChan7Con, "cell 2 3,alignx center");
		
		JButton btnChan8Con = new JButton("Connect");
		btnChan8Con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sensorConnection(btnChan8Con, cbbxChan8, 8);
			}
		});
		connectionPanel.add(btnChan8Con, "cell 6 3,alignx center");
		
		
		//Command buttons
		JButton btnNum = new JButton("NUM");
		btnNum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_cmd = KeyPadCommand.NUM;
				setStatus(Messages.enterRacerNum);
			}
		});
		commandPanel.add(btnNum, "cell 0 0,growx");
		
		JButton btnClr = new JButton("CLR");
		btnClr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_cmd = KeyPadCommand.CLR;
				setStatus(Messages.enterRacerNum);
			}
		});
		commandPanel.add(btnClr, "cell 0 1,growx");
		
		JButton btnSwap = new JButton("SWAP");
		btnSwap.setActionCommand("SWAP");
		btnSwap.addActionListener(controller);
		commandPanel.add(btnSwap, "cell 0 2,growx");
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.setActionCommand("CANCEL");
		btnCancel.addActionListener(controller);
		commandPanel.add(btnCancel, "cell 0 3,growx");
		
		JButton btnDnf = new JButton("DNF");
		btnCancel.setActionCommand("CANCEL");
		btnCancel.addActionListener(controller);
		commandPanel.add(btnDnf, "cell 0 4,growx");
		
		JButton btnPrint = new JButton("PRINT");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_cmd = KeyPadCommand.PRINT;
				setStatus(Messages.enterRunID);
			}
		});
		commandPanel.add(btnPrint, "cell 0 5,growx");
		
		JButton btnExport = new JButton("EXPORT");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_cmd = KeyPadCommand.EXPORT;
				setStatus(Messages.enterRunID);
			}
		});
		commandPanel.add(btnExport, "cell 0 6,growx");
		
		//Keypad Buttons
		ActionListener keyPadListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputStr = inputText.getText();
				if(e.getActionCommand().equalsIgnoreCase("Back")) {
					if(inputStr.length() > 1) {
						inputStr = inputStr.substring(0, inputStr.length()-1);
						inputText.setText(inputStr);
					}
					else {
						inputText.setText("");
					}
				}
				else if(e.getActionCommand().equalsIgnoreCase("Enter")) {
					//Do action with number
					String actionCmd = "";
					if(m_cmd == KeyPadCommand.NUM) {
						actionCmd += "NUM";
					}
					else if(m_cmd == KeyPadCommand.CLR) {
						actionCmd += "CLR";
					}
					else if(m_cmd == KeyPadCommand.PRINT) {
						actionCmd += "PRINT";
					}
					else if(m_cmd == KeyPadCommand.EXPORT) {
						actionCmd += "EXPORT";
					}
					else {
						//No command, do nothing
						return;
					}
					actionCmd += " " + inputStr;
					controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCmd));
					m_cmd = KeyPadCommand.NONE;
					inputText.setText("");
				}
				else {
					if(inputStr.length() < 10) inputStr += e.getActionCommand();
					inputText.setText(inputStr);
				}
			}
		};
		
		JButton btnKey1 = new JButton("1");
		btnKey1.setActionCommand("1");
		btnKey1.addActionListener(keyPadListener);
		keypadPanel.add(btnKey1, "cell 0 0,growx");
		
		JButton btnKey2 = new JButton("2");
		btnKey2.setActionCommand("2");
		btnKey2.addActionListener(keyPadListener);
		keypadPanel.add(btnKey2, "cell 1 0,growx");
		
		JButton btnKey3 = new JButton("3");
		btnKey3.setActionCommand("3");
		btnKey3.addActionListener(keyPadListener);
		keypadPanel.add(btnKey3, "cell 2 0,growx");
		
		JButton btnKey4 = new JButton("4");
		btnKey4.setActionCommand("4");
		btnKey4.addActionListener(keyPadListener);
		keypadPanel.add(btnKey4, "cell 0 1,growx");
		
		JButton btnKey5 = new JButton("5");
		btnKey5.setActionCommand("5");
		btnKey5.addActionListener(keyPadListener);
		keypadPanel.add(btnKey5, "cell 1 1,growx");
		
		JButton btnKey6 = new JButton("6");
		btnKey6.setActionCommand("6");
		btnKey6.addActionListener(keyPadListener);
		keypadPanel.add(btnKey6, "cell 2 1,growx");
		
		JButton btnKey7 = new JButton("7");
		btnKey7.setActionCommand("7");
		btnKey7.addActionListener(keyPadListener);
		keypadPanel.add(btnKey7, "cell 0 2,growx");
		
		JButton btnKey8 = new JButton("8");
		btnKey8.setActionCommand("8");
		btnKey8.addActionListener(keyPadListener);
		keypadPanel.add(btnKey8, "cell 1 2,growx");
		
		JButton btnKey9 = new JButton("9");
		btnKey9.setActionCommand("9");
		btnKey9.addActionListener(keyPadListener);
		keypadPanel.add(btnKey9, "cell 2 2,growx");
		
		JButton btnKeyBack = new JButton("<-");
		btnKeyBack.setActionCommand("Back");
		btnKeyBack.addActionListener(keyPadListener);
		keypadPanel.add(btnKeyBack, "cell 0 3,growx");
		
		JButton btnKey0 = new JButton("0");
		btnKey0.setActionCommand("0");
		btnKey0.addActionListener(keyPadListener);
		keypadPanel.add(btnKey0, "cell 1 3,growx");
		
		JButton btnKeyEnter = new JButton("Enter");
		btnKeyEnter.setActionCommand("Enter");
		btnKeyEnter.addActionListener(keyPadListener);
		keypadPanel.add(btnKeyEnter, "cell 2 3,growx");
			
		//General Buttons
		JButton btnPower = new JButton("Power");
		btnPower.setActionCommand("POWER");
		btnPower.addActionListener(controller);
		contentPane.add(btnPower, "cell 0 0,alignx center,aligny top");
		
		JButton btnStartEndRun = new JButton("Start Run");
		btnStartEndRun.setActionCommand("NEWRUN");
		btnStartEndRun.addActionListener(controller);
		btnStartEndRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cbbxCompType.isEnabled()) {
					btnStartEndRun.setActionCommand("ENDRUN");
					btnStartEndRun.setText("End Run");
					cbbxCompType.setEnabled(false);
					lblRunIdNum.setText("" + controller.getRunID());
					updateTimer.start();
				}
				else {
					btnStartEndRun.setActionCommand("NEWRUN");
					btnStartEndRun.setText("Start Run");
					cbbxCompType.setEnabled(true);
					updateTimer.stop();
					readyText.setText("");
					racingText.setText("");
					finishedText.setText("");
				}
			}
			
		});
		contentPane.add(btnStartEndRun, "cell 3 1,alignx center");
		
		JButton btnPrinterPower = new JButton("Printer Power");
		contentPane.add(btnPrinterPower, "cell 4 8 2 1,alignx center,aligny top");
		
		
	}

	public void printLine(String line) {
		String out = printerText.getText();
		if(!out.isEmpty()) out += "\n";
		out += line;
		printerText.setText(out);
	}

	public void setStatus(String string) {
		lblStatusText.setText(string);
	}
	
	private void sensorConnection(JButton btnChanCon, JComboBox<Sensor> cbbxChan, int sensorNum) {
		if(cbbxChan.isEnabled()) {
			Sensor s = (Sensor) cbbxChan.getSelectedItem();
			String actionCmd = "CONN ";
			if(s == Sensor.EYE) {
				actionCmd += "EYE";
			}
			else if(s == Sensor.GATE) {
				actionCmd += "GATE";
			}
			else if(s == Sensor.PAD) {
				actionCmd += "PAD";
			}
			else {
				//Cannot Connect NONE
				return;
			}
			actionCmd += " " + sensorNum;
			controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCmd));
			cbbxChan.setEnabled(false);
			btnChanCon.setText("Disconnect");
		}
		else {
			controller.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "DISC " + sensorNum));
			cbbxChan.setEnabled(true);
			btnChanCon.setText("Connect");
		}
	}
	
}
