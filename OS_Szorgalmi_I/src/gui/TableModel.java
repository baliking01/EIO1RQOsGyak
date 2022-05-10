package gui;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel{
	private String[] columnNames = {"Process ID", "Arrival time", "Burst time", "Exit time", "Turn. time", "Wait time"};
	private String[][] data = {};

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	// Methods
	
	public void setData(String[][] newData) {
		data = newData;
		fireTableDataChanged();
	}
}
