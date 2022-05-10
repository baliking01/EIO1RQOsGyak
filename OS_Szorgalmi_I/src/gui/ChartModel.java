package gui;
import javax.swing.table.AbstractTableModel;
@SuppressWarnings("serial")
public class ChartModel extends AbstractTableModel{
	private String[] columnNames = {};
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
	
	public String[] getColumnNames() {
		return columnNames;
	}
	
	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	// Methods
	
	public void setProcessOrder(String[] newData) {
		columnNames = newData;
		fireTableStructureChanged();
	}
	
	public void setExitTimes(int[] exitTimes) {
		String[][] newExitTimes = new String[1][getColumnCount()];
		for(int i = 0; i < exitTimes.length; i++) {
			newExitTimes[0][i] = String.valueOf(exitTimes[i]);
		}
		
		data = newExitTimes;
		fireTableStructureChanged();
	}
}
