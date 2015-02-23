package TimeNMoney;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ModeloTabelaAuxiliarProjectos extends AbstractTableModel
{
    private final String[] columnNames;
    private final ArrayList<Class_auxiliar_tabelas> data; 
    
    public ModeloTabelaAuxiliarProjectos(String[] columnNames, ArrayList<Class_auxiliar_tabelas> data){
        this.columnNames = columnNames;
        this.data = data;
    }   
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return true;
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Class_auxiliar_tabelas aux = data.get(rowIndex);
        switch(columnIndex)
        {
            case 0: 
                return aux.descricao;
            case 1: 
                return aux.selected;
            default:
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
        }
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Class<?> getColumnClass(int col){
        if (col==1)
            return Boolean.class;
        return super.getColumnClass(col);
    }
    
}
