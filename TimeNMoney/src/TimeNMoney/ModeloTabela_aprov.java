package TimeNMoney;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ModeloTabela_aprov extends AbstractTableModel
{
    private final String[] columnNames;
    private final double[][] data; 
    
    public ModeloTabela_aprov(String[] columnNames, double[][] data){
        this.columnNames = columnNames;
        this.data = data;
    }   
    
    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
}
