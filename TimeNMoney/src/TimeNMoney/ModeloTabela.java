package TimeNMoney;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ModeloTabela extends AbstractTableModel
{
    private final String[] columnNames;
    private final double[][] data; 
    private final ArrayList<Integer> lista_ap;
    private final ArrayList<Integer> lista_bl;
    
    public ModeloTabela(String[] columnNames, double[][] data){
        this.columnNames = columnNames;
        this.data = data;
        this.lista_ap = null;
        this.lista_bl = null;
    } 
    
    public ModeloTabela(String[] columnNames, double[][] data, ArrayList<Integer> lista_ap,ArrayList<Integer> lista_bl){
        this.columnNames = columnNames;
        this.data = data;
        this.lista_ap = lista_ap;
        this.lista_bl = lista_bl;
    } 
    
    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        boolean aux = true;
    	if (this.lista_ap == null && this.lista_bl == null)
    		aux = true;
    	else if (this.lista_ap != null && (this.lista_ap.contains(columnIndex)) || (this.lista_bl != null && this.lista_bl.contains(columnIndex)))
    		aux = false;
    	return aux;
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
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        double aux;
        try{
            aux = Double.valueOf(value.toString().replace(",", "."));
            if (aux > 24)
            {
                JOptionPane.showMessageDialog(null, "Máximo permitido: 24");
                aux = 24;
            }
            else if (aux < 0)
            {
                JOptionPane.showMessageDialog(null, "Mínimo permitido: 0");
                aux = 0;
            }
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Só são permitidos valores numéricos!");
            aux = this.data[rowIndex][columnIndex];
        }
        this.data[rowIndex][columnIndex] = aux;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
