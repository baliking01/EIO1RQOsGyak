package gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.*;
import processes.Process;
import processes.run.*;

public class GraphicalUserInterface{
	JTextField textField = new RoundedJTextField(15);	// Arrival Time
	JTextField textField2 = new RoundedJTextField(15);	// Burst Time
	JTextField textField3 = new RoundedJTextField(15);	// Time Quantum
	JLabel label, label2, label3, warningLabel;
	JButton button;
	JComboBox<String> menu;
	JFrame frame;
	JTable table, chart;
	
	public GraphicalUserInterface(){
		JPanel p = new RoundedPanel();
		p.setLayout(null);
		p.setBounds(20, 10, 200, 300);
		p.setBackground(Color.lightGray);
		
		frame = new JFrame("Klasszikus ütemezési algoritmusok - Nagy Balázs,"
				+ " Miskolci Egyetem, Informatikai Intézet, balazsnagy220@gmail.com");	// Main frame
		frame.getContentPane().setBackground(Color.cyan.darker());
				
		p.add(textField);
		p.add(textField2);
		p.add(textField3);
		// User Inputs
		textField.setBounds(25, 100, 150, 20);
		textField2.setBounds(25, 160, 150, 20);
		textField3.setBounds(25, 220, 150, 20);
		
		// Labels
		label = new JLabel("Arrival Times");
		label.setFont(new Font("Courier", Font.BOLD,12));
		
		label2 = new JLabel("Burst Times");
		label2.setFont(new Font("Courier", Font.BOLD,12));
		
		label3 = new JLabel("Time Quantum");
		label3.setFont(new Font("Courier", Font.BOLD,12));
		
		warningLabel = new JLabel("Warning label position, testing for longer error messages");
		warningLabel.setFont(new Font("Courier", Font.BOLD,14));
		
		p.add(label);
		p.add(label2);
		p.add(label3);

		label.setBounds(25, 80, 100, 20);
		label2.setBounds(25, 140, 100, 20);
		label3.setBounds(25, 200, 100, 20);
		warningLabel.setBounds(150, 320, 400, 20);
		warningLabel.setForeground(Color.red.brighter());
		warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Button
		button = new RoundedJButton();
		button.setText("Solve");
		
		button.setBackground(Color.white);
		p.add(button);
		button.setBounds(50, 250, 80, 30);
		
		// Drop-down Menu
		menu = new JComboBox<>(new String[] {"FCFS", "SJF", "Round Robin"});
		p.add(menu);
		
		menu.setBounds(25, 25, 150, 30);
		menu.setBackground(Color.white);
		
		// Table, scrollpane
		JPanel tablePanel = new RoundedPanel();
		tablePanel.setLayout(null);
		tablePanel.setBounds(250, 80, 400, 230);
		
		TableModel tableModel = new TableModel();
		table = new JTable(tableModel);
		
		
		JScrollPane sp = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		tablePanel.add(sp);
		sp.setBounds(5, 5, 385, 215);
		
		// Gannt Chart table
		JPanel chartPanel = new RoundedPanel();
		chartPanel.setLayout(null);
		chartPanel.setBounds(250, 10, 400, 60);
		
		ChartModel chartModel = new ChartModel();
		
		chart = new JTable(chartModel);
		JScrollPane sp2 = new JScrollPane(chart);
		
		chartPanel.add(sp2);
		sp2.setBounds(5, 5, 385, 45);
		
		// Alignment renderer
		
		
		// Adding component to the frame
		frame.setLocation(300, 170);
		frame.add(p);
		frame.add(chartPanel);
		frame.add(tablePanel);
		chartPanel.setVisible(false);
		tablePanel.setVisible(false);
		frame.add(warningLabel);
		warningLabel.setVisible(false);
		
		// Frame settings
		label3.setVisible(false);
		textField3.setVisible(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 400);
		frame.setLayout(null);
		frame.setVisible(true);
		
		// Action listener for menu
		menu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				if(menu.getItemAt(menu.getSelectedIndex()) != "Round Robin") {
					label3.setVisible(false);
					textField3.setVisible(false);
				}
				else {
					label3.setVisible(true);
					textField3.setVisible(true);
				}
				
			}
		});
		
		// Action listener for button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String[] burstTime, arrivalTime;
				Process[] inputStream;
				int timeQuantum = 0;
				
				// Check for incorrect user input
				if(textField.getText().equals("") || textField2.getText().equals("") || 
						textField.getText().split(" ").length != textField2.getText().split(" ").length) {
					warningLabel.setText("Error! Incorrect input for process arrival and or burst time!");
					warningLabel.setVisible(true);
					return;
				}
				arrivalTime = textField.getText().split(" ");
				burstTime = textField2.getText().split(" ");
				
				if(menu.getItemAt(menu.getSelectedIndex()) == "Round Robin") {
					try {
							timeQuantum = Integer.parseInt(textField3.getText());
					}catch(Exception e) {
						warningLabel.setText("Error! Invalid input for time quantum!");
						warningLabel.setVisible(true);
						return;
					}
				}
				
				if(warningLabel.isVisible()) {
					warningLabel.setVisible(false);
				}
				
				// Add process table
				inputStream = mergeInputs(arrivalTime, burstTime);
				String[][] data = initializeData(inputStream,
						menu.getItemAt(menu.getSelectedIndex()),
						timeQuantum);
				tableModel.setData(data);
				tablePanel.setVisible(true);
				
				// Add Gantt Chart
				chartModel.setProcessOrder(runScheduler(mergeInputs(arrivalTime, burstTime),
						menu.getItemAt(menu.getSelectedIndex()),
						timeQuantum));
				if(menu.getItemAt(menu.getSelectedIndex()).equals("Round Robin")) {
					chartModel.setExitTimes(ProcessHandler.RRProcessExitTimes.stream().mapToInt(i->i).toArray());
				}else {
					chartModel.setExitTimes(extractExitTimes(inputStream));
				}
				
				DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
				rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
				for(int i = 0; i < chart.getColumnCount(); i++) {
					chart.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
				}
				
				chartPanel.setVisible(true);
			}
		});
	}
	
	// Merge user inputs(String[]) into Process[]
	public static Process[] mergeInputs(String[] arrivalTimes, String[] burstTimes) {
		Process[] processes = new Process[arrivalTimes.length];
		for(int i = 0; i < arrivalTimes.length; i++) {
			processes[i] = new Process(Integer.parseInt(arrivalTimes[i]), Integer.parseInt(burstTimes[i]), "P" + (i + 1));
		}
		return processes;
	}
	
	// Chooses scheduler, runs the scheduler, returns process order
	public static String[] runScheduler(Process[] userInput, String type, int timeQuantum) {
		Process[] processOrder = null;
		switch(type) {
		case("FCFS"):
			processOrder = ProcessHandler.scheduler(type, userInput);
			break;
		case("SJF"):
			processOrder = ProcessHandler.scheduler(type, userInput);
			break;
		case("Round Robin"):
			processOrder = ProcessHandler.RR(userInput, timeQuantum);
			break;
		default:
			break;
		}
		String[] processOrderString = new String[processOrder.length];
		for(int i = 0; i < processOrderString.length; i++) {
			processOrderString[i] = processOrder[i].getPid();
		}
		return processOrderString;
	}
	
	// Run chosen scheduler algorithm
	public static String[][] initializeData(Process[] userInput, String type, int timeQuantum){
		String[][] data = new String[userInput.length][6];
		runScheduler(userInput,  type, timeQuantum);
		
		for(int i = 0; i < userInput.length; i++) {		// Insert process data
			data[i][0] = String.valueOf(userInput[i].getPid());
			data[i][1] = String.valueOf(userInput[i].getArrivalTime());
			data[i][2] = String.valueOf(userInput[i].getBurstTime());
			data[i][3] = String.valueOf(userInput[i].getExitTime());
			data[i][4] = String.valueOf(userInput[i].getBurstTime() + userInput[i].getWaitTime());
			data[i][5] = String.valueOf(userInput[i].getWaitTime());
		}
		return data;
	}
	
	public int[] extractExitTimes(Process[] processes) {
		int[] arr = new int[processes.length];
		for(int i = 0; i < processes.length; i++) {
			arr[i] = processes[i].getExitTime();
		}
		return arr;
	}
	
	public static void main(String[] args) {
		new GraphicalUserInterface();
	}
}
