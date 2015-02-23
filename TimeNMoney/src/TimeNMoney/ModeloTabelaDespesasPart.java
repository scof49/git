package TimeNMoney;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ModeloTabelaDespesasPart extends AbstractTableModel
{
    private final String[] columnNames;
    private final ArrayList<Despesa_new> data; 
    private final TreeMap<String,Projecto> projectos;
    
    public ModeloTabelaDespesasPart(String[] columnNames, ArrayList<Despesa_new> data,TreeMap<String,Projecto> proj){
        this.columnNames = columnNames;
//        this.data = data;
        this.data = sort_data(data);
        this.projectos = proj;
    }   
    
    private ArrayList<Despesa_new> sort_data(ArrayList<Despesa_new> lista){
    	Collections.sort(lista, new Comparator<Despesa_new>(){
			public int compare(Despesa_new d1, Despesa_new d2) {
				int res = 0;
				if (d1.get_data_despesa().before(d2.get_data_despesa()))
					res++;
				else if (d2.get_data_despesa().before(d1.get_data_despesa()))
					res--;
				else
					res = 0;
				return res;
			}
    	});
    	return lista;
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
        Despesa_new despesa = data.get(rowIndex);
        switch(columnIndex)
        {
            case 0: 
                {
                String proj_id = despesa.get_id_projecto();
                String cliente = "";
                try{
                	cliente = this.projectos.get(proj_id).get_cliente().get_nome();
                }catch(Exception e){
                	e.printStackTrace();
                	new Log_erros_class().write_log_to_file(e);
                	cliente = "ERRO";
                }
                return cliente;
                } 
                //return despesa.id;
            case 1: 
                return (despesa.id_projecto + " - "+ despesa.nome_projecto);
            case 2:{
                if (despesa.dataDespesa!=null)
                {
                    Calendar c = Calendar.getInstance();
                    c.setTime(despesa.dataDespesa);
                    int dia = c.get(Calendar.DAY_OF_MONTH);
                    int mes = c.get(Calendar.MONTH)+1;
                    int ano = c.get(Calendar.YEAR);
                    String aux = dia + "/" + mes + "/" + ano;
                    return aux;
                }
                return "";
            }
            case 3: 
                return despesa.tipo;
            case 4:
                return despesa.quantidade;
            case 5:
                return despesa.valor_euros;
            case 6:
                return despesa.valor_original;
            default:
                throw new UnsupportedOperationException("Not supported yet.");         
        }
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
}
