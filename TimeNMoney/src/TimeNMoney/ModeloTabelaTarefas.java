package TimeNMoney;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ModeloTabelaTarefas extends AbstractTableModel
{
    private final String[] columnNames;
    private final ArrayList<TarefaHoras> data; 
    private final TreeMap<String,Projecto> projectos;
    
    public ModeloTabelaTarefas(String[] columnNames, ArrayList<TarefaHoras> data, TreeMap<String,Projecto> proj){
        this.columnNames = columnNames;
        this.data = data;
        this.projectos = proj;
    }   
    
    @Override
    public int getRowCount() {
        return data.size();
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
        TarefaHoras tr = data.get(rowIndex);
        switch(columnIndex)
        {
            case 0: 
                {
                String proj_id = tr.get_id_projecto();
                String cliente;
                try{
                	cliente = (this.projectos.get(proj_id).get_cliente().get_nome());
                }
                catch(Exception e)
                {
                	cliente = "";
                }
                return cliente;
                } 
                //return tr.id;
            case 1: 
                return (tr.id_projecto + " - " + tr.nome_projecto);
            case 2: 
                return tr.etapa;
            case 3: 
                return tr.actividade;
            case 4: 
                return tr.tarefa;
            case 5: 
//                if (tr.local.equals(""))
//                        return "Portugal";
//                else
                    return tr.local;
                
            default:
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
        }
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
}
