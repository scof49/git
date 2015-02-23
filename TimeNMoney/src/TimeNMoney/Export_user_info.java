package TimeNMoney;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Export_user_info extends JFrame {

	ArrayList<Despesa_new> lista_despesas;
	TreeMap<String,TarefaHoras> lista_horas;
	TreeMap<String,Projecto> lista_projectos_user;
	Data_manager dm;
	int op; //1 despesas - 2 tarefas
	private JPanel contentPane;
	private JLabel label_info;
	private JLabel label;
	private JLabel lblDe;
	private JLabel lblAt;
	private JDateChooser data_de;
	private JDateChooser data_ate;
	private JButton export_btn;
	private JButton btnCancelar;
	
	//despesas
	/**
	 * @wbp.parser.constructor
	 */
	public Export_user_info(Data_manager dm, ArrayList<Despesa_new> lista_despesas,TreeMap<String,TarefaHoras> lista_horas) {
		setTitle("ODKAS - Time &  Money");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Export_user_info.class.getResource("/TimeNMoney/odkas.tnm.png")));
		setResizable(false);
		init();
		set_datas();
		if (lista_despesas != null){
			this.op = 1;
			label_info.setText("Exportar Despesas");
		}
		else if (lista_horas != null){
			this.op = 2;
			label_info.setText("Exportar Carga Horária");
		}
		this.lista_despesas = lista_despesas;
		this.lista_horas = lista_horas;
		this.dm = dm;
		get_list_projectos_from_dm();
	}
	
	private void init(){
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				windows_open_action();
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 393, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		{
			label_info = new JLabel("Exportar despesas");
			label_info.setHorizontalAlignment(SwingConstants.CENTER);
			label_info.setFont(new Font("Tahoma", Font.BOLD, 18));
		}
		{
			label = new JLabel("");
		}
		lblDe = new JLabel("De:");
		lblDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblAt = new JLabel("Até:");
		lblAt.setHorizontalAlignment(SwingConstants.CENTER);
		data_de = new JDateChooser();
		data_ate = new JDateChooser();
		export_btn = new JButton("Exportar");
		export_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				export_button_action();
			}
		});
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose_window();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addGroup(Alignment.TRAILING, gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(10)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblAt, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(data_ate, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblDe, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(data_de, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(label_info, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnCancelar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(export_btn, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(label)
					.addGap(5)
					.addComponent(label_info)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(export_btn, Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(17)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDe, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
								.addComponent(data_de, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCancelar)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblAt, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
								.addComponent(data_ate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(84))))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void dispose_window(){
		this.dispose();
	}
	
	private void windows_open_action(){
		this.setLocationRelativeTo(null);
	}
	
	private void export_button_action(){
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		TreeMap<Date,Despesa_new> lista_despesas_filtro = new TreeMap<Date, Despesa_new>();
		TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> lista_tarefas_filtro = new TreeMap<Comparable_TarefaHoras, TreeMap<Date,Double>>();
		Date inicio = data_de.getDate();
		Date fim = data_ate.getDate();
		if (inicio == null && fim != null){
			if (op==1)
				lista_despesas_filtro = despesas_filtro_ate(fim);
			else if (op==2)
				lista_tarefas_filtro = tarefas_filtro_ate(fim);
		}
		else if (inicio != null && fim == null){
			if (op==1)
				lista_despesas_filtro = despesas_filtro_de(inicio);
			else if (op==2)
				lista_tarefas_filtro = tarefas_filtro_de(inicio);
		}
		else if (inicio != null & fim != null){
			if (inicio.after(fim))
				JOptionPane.showMessageDialog(null,"Data de fim não pode ser antes de data de início!");
			else
				if (op==1)
					lista_despesas_filtro = despesas_filtro(inicio,fim);
				else if (op==2)
					lista_tarefas_filtro = tarefas_filtro(inicio,fim);
		}
		else{
			if (op==1)
				lista_despesas_filtro = despesas_sem_filtro();
			else if (op==2)
				lista_tarefas_filtro = tarefas_sem_filtro();
		}
		
		if (op==1)
		{
			int res = 0;
			String path = choose_file();
			if (!path.equals(""))
				res = export_despesas_to_xls(create_name_file_part(inicio,fim),path,lista_despesas_filtro);
			
			if (res==0){
				JOptionPane.showMessageDialog(null, "Despesas exportadas com sucesso!");
				this.dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "Ocorreu um erro, contacte administrador!");
		}
		else if (op==2)
		{
			int res = 0;
			String path = choose_file();
			if (!path.equals(""))
				res = export_tarefas_to_xls(create_name_file_part(inicio,fim),path,lista_tarefas_filtro);
			if (res==0){
				JOptionPane.showMessageDialog(null, "Despesas exportadas com sucesso!");
				this.dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "Ocorreu um erro, contacte administrador!");
		}
		
		this.setCursor(Cursor.getDefaultCursor());
	}
	
	private void set_datas(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH,1);
		Date inicio = c.getTime();
		data_de.setDate(inicio);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date fim = c.getTime();
		data_ate.setDate(fim);
	}
	
	private TreeMap<Date,Despesa_new> despesas_filtro(Date inicio, Date fim){
		TreeMap<Date,Despesa_new> lista_filtro = new TreeMap<Date,Despesa_new>();
		for (Despesa_new d : this.lista_despesas)
		{
			Date aux = d.get_data_despesa();
			if (!aux.before(inicio) && !aux.after(fim)){
				lista_filtro.put(aux,d);
			}
		}
		return lista_filtro;
	}
	
	private TreeMap<Date,Despesa_new> despesas_sem_filtro(){
		TreeMap<Date,Despesa_new> lista_filtro = new TreeMap<Date,Despesa_new>();
		for (Despesa_new d : this.lista_despesas)
		{
			Date aux = d.get_data_despesa();
			{
				lista_filtro.put(aux,d);
			}
		}
		return lista_filtro;
	}
	
	private TreeMap<Date,Despesa_new> despesas_filtro_de(Date inicio){
		TreeMap<Date,Despesa_new> lista_filtro = new TreeMap<Date,Despesa_new>();
		for (Despesa_new d : this.lista_despesas)
		{
			Date aux = d.get_data_despesa();
			if (!aux.before(inicio))
			{
				lista_filtro.put(aux,d);
			}
		}
		return lista_filtro;
	}
	
	private TreeMap<Date,Despesa_new> despesas_filtro_ate(Date fim){
		TreeMap<Date,Despesa_new> lista_filtro = new TreeMap<Date,Despesa_new>();
		for (Despesa_new d : this.lista_despesas)
		{
			Date aux = d.get_data_despesa();
			if (!aux.after(fim)){
				lista_filtro.put(aux,d);
			}
		}
		return lista_filtro;
	}
	
	private TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> tarefas_filtro(Date inicio, Date fim){
		TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> aux = new TreeMap<>();
		for (TarefaHoras t : this.lista_horas.values())
		{
			TarefaHoras new_tarefa = clone_tarefa(t);
			TreeMap<Date,Double> new_map = new TreeMap<Date,Double>();
			for (Date d : t.get_map().keySet())
				if (!d.before(inicio) && !d.after(fim))
				{
					new_map.put(d,t.get_map().get(d));
				}
			aux.put(new Comparable_TarefaHoras(new_tarefa.get_id(),new_tarefa), new_map);
		}
		return aux;
	}
	
	private TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> tarefas_filtro_de(Date inicio){
		TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> aux = new TreeMap<>();
		for (TarefaHoras t : this.lista_horas.values())
		{
			TarefaHoras new_tarefa = clone_tarefa(t);
			TreeMap<Date,Double> new_map = new TreeMap<Date,Double>();
			for (Date d : t.get_map().keySet())
				if (!d.before(inicio))
				{
					new_map.put(d,t.get_map().get(d));
				}
			aux.put(new Comparable_TarefaHoras(new_tarefa.get_id(),new_tarefa), new_map);
		}
		return aux;
	}
	
	private TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> tarefas_filtro_ate(Date fim){
		TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> aux = new TreeMap<>();
		for (TarefaHoras t : this.lista_horas.values())
		{
			TarefaHoras new_tarefa = clone_tarefa(t);
			TreeMap<Date,Double> new_map = new TreeMap<Date,Double>();
			for (Date d : t.get_map().keySet())
				if (!d.after(fim))
				{
					new_map.put(d,t.get_map().get(d));
				}
			aux.put(new Comparable_TarefaHoras(new_tarefa.get_id(),new_tarefa), new_map);
		}
		return aux;
	}
	
	private TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> tarefas_sem_filtro(){
		TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> aux = new TreeMap<>();
		for (TarefaHoras t : this.lista_horas.values())
		{
			TarefaHoras new_tarefa = clone_tarefa(t);
			TreeMap<Date,Double> new_map = new TreeMap<Date,Double>();
			for (Date d : t.get_map().keySet())
				new_map.put(d,t.get_map().get(d));
			aux.put(new Comparable_TarefaHoras(new_tarefa.get_id(),new_tarefa), new_map);
		}
		return aux;
	}
	
	private TarefaHoras clone_tarefa(TarefaHoras t){
		TarefaHoras aux = new TarefaHoras();
		aux.set_id(t.get_id());
	    aux.set_id_projecto(t.get_id_projecto());
	    aux.set_nome_projecto(t.get_nome_projecto());
	    aux.set_etapa(t.get_etapa());
	    aux.set_desc_etapa(t.get_desc_etapa());
	    aux.set_actividade(t.get_actividade());
	    aux.set_desc_actividade(t.get_desc_actividade());
	    aux.set_tarefa(t.get_tarefa());
	    aux.set_desc_tarefa(t.get_desc_tarefa());
	    aux.set_funcionario(t.get_funcionario());
	    aux.set_local(t.get_local());
	    return aux;
	}
	
	private String choose_file(){
		String path = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Destino ficheiro!");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        int esc = chooser.showSaveDialog(this);
        if (esc==JFileChooser.APPROVE_OPTION){
            File ficheirorecebido = chooser.getSelectedFile();
            path = ficheirorecebido.getAbsolutePath();
        }
		return path;
	}
	
	private int export_despesas_to_xls(String name_file_part, String path, TreeMap<Date,Despesa_new> lista){
		int linha = 0;
		try{
			DateFormat customDateFormat = new DateFormat ("dd-MM-yyyy"); 
    		WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat);
			WritableWorkbook workbook = Workbook.createWorkbook(new File(path + "\\export_despesas"+ name_file_part +".xls"));
			WritableSheet sheet = workbook.createSheet("Despesas", 0);
			Label l1 = new Label(0,linha,"Data",getCell_desc_line());
    		sheet.addCell(l1);
    		Label l2 = new Label(1,linha,"Tipo de Despesa",getCell_desc_line());
    		sheet.addCell(l2);
    		Label l3 = new Label(2,linha,"Data aprovação",getCell_desc_line());
    		sheet.addCell(l3);
    		Label l4 = new Label(3,linha,"Quantidade",getCell_desc_line());
    		sheet.addCell(l4);
    		Label l5 = new Label(4,linha,"Valor",getCell_desc_line());
    		sheet.addCell(l5);
    		Label l6 = new Label(5,linha,"Notas",getCell_desc_line());
    		sheet.addCell(l6);
    		Label l7 = new Label(6,linha,"Etapa",getCell_desc_line());
    		sheet.addCell(l7);
    		Label l8 = new Label(7,linha,"Actividade",getCell_desc_line());
    		sheet.addCell(l8);
    		Label l9 = new Label(8,linha,"Valor Original",getCell_desc_line());
    		sheet.addCell(l9);
    		linha++;
    		double total = 0.0;
    		for (Despesa_new d : lista.values()){
    			double aux_val = round(d.get_valor_euros(),2);
            	//data
            	DateTime ldata = new DateTime(0,linha,d.get_data_despesa(),dateFormat);
            	sheet.addCell(ldata);
            	//tipo
            	Label ltipo = new Label(1,linha,d.get_tipo());
            	sheet.addCell(ltipo);
            	//data aprovacao
            	if (d.get_data_aprovacao()!=null)
            	{
            		DateTime ldata_ap = new DateTime(2,linha,d.get_data_aprovacao(),dateFormat);
            		sheet.addCell(ldata_ap);
            	}
            	//quantidade
            	Number lqtd = new Number(3,linha,d.get_quantidade());
            	sheet.addCell(lqtd);
            	//valor
            	Number lval = new Number(4,linha,d.get_valor_euros());
            	sheet.addCell(lval);
            	//notas
            	Label lnota = new Label(5,linha,remove_space_notas(d.get_notas()));
            	sheet.addCell(lnota);
            	//etapa
            	Label letapa = new Label(6,linha,d.get_etapa());
            	sheet.addCell(letapa);
            	//actividade
            	Label lact = new Label(7,linha,d.get_actividade());
            	sheet.addCell(lact);
            	//valor original
            	Label lorig = new Label(8,linha,d.get_valor_original());
            	sheet.addCell(lorig);
            	linha++;
            	
            	total += (d.get_quantidade() * aux_val);
            	total = round(total,2);
    		}
    		
    		Label ltotal = new Label(0,linha,"Total:",getCell_total_line());
    		sheet.addCell(ltotal);
    		Label t2 = new Label(1,linha,"",getCell_total_line());
    		sheet.addCell(t2);
    		Label t3 = new Label(2,linha,"",getCell_total_line());
    		sheet.addCell(t3);
    		Label t4 = new Label(3,linha,"",getCell_total_line());
    		sheet.addCell(t4);
    		Label t5 = new Label(4,linha,"",getCell_total_line());
    		sheet.addCell(t5);
    		Label t6 = new Label(5,linha,"",getCell_total_line());
    		sheet.addCell(t6);
    		Label t7 = new Label(6,linha,"",getCell_total_line());
    		sheet.addCell(t7);
    		Label t8 = new Label(7,linha,"",getCell_total_line());
    		sheet.addCell(t8);
    		Number ltotal_num = new Number(8,linha,total,getCell_total_line());
    		sheet.addCell(ltotal_num);
    		workbook.write(); 
    		workbook.close();
			return 0;
		}
		catch(Exception e){
			e.printStackTrace();
			this.setCursor(Cursor.getDefaultCursor());
			new Log_erros_class().write_log_to_file(e);
			return 1;
		}
	}
	
	private int export_tarefas_to_xls(String name_file_part, String path, TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> lista){
		int linha = 0;
        try{
        	DateFormat customDateFormat = new DateFormat ("dd-MM-yyyy"); 
    		WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat);
			WritableWorkbook workbook = Workbook.createWorkbook(new File(path + "\\export_horas"+ name_file_part +".xls"));
			WritableSheet sheet = workbook.createSheet("Horas", 0);
			Label l1 = new Label(0,linha,"Cliente",getCell_desc_line());
    		sheet.addCell(l1);
    		Label l2 = new Label(1,linha,"Projecto",getCell_desc_line());
    		sheet.addCell(l2);
    		Label l4 = new Label(2,linha,"Etapa",getCell_desc_line());
    		sheet.addCell(l4);
    		Label l5 = new Label(3,linha,"Actividade",getCell_desc_line());
    		sheet.addCell(l5);
    		Label l6 = new Label(4,linha,"Tarefa",getCell_desc_line());
    		sheet.addCell(l6);
    		Label l7 = new Label(5,linha,"Local",getCell_desc_line());
    		sheet.addCell(l7);
    		Label l8 = new Label(6,linha,"Data",getCell_desc_line());
    		sheet.addCell(l8);
    		Label l9 = new Label(7,linha,"Horas",getCell_desc_line());
    		sheet.addCell(l9);
    		linha++;
    		
    		for (Comparable_TarefaHoras ct : lista.keySet()){
    			TarefaHoras t = ct.get_tarefas();
    			String proj = t.get_id_projecto() + " : " + t.get_nome_projecto();
    			for (Date d : lista.get(ct).keySet())
    			{
    				double horas = lista.get(ct).get(d);
    				if (horas>0.0)
    				{
	    				//cliente
	                	Label lcliente = new Label(0,linha,get_cliente_projecto(t.get_id_projecto()));
	                	sheet.addCell(lcliente);
	                	//projecto
	                	Label lprojecto = new Label(1,linha,proj);
	                	sheet.addCell(lprojecto);
	                	//etapa
	                	Label letapa = new Label(2,linha,t.get_etapa());
	                	sheet.addCell(letapa);
	                	//actividade
	                	Label latividade = new Label(3,linha,t.get_actividade());
	                	sheet.addCell(latividade);
	                	//tarefa
	                	Label ltarefa = new Label(4,linha,t.get_tarefa());
	                	sheet.addCell(ltarefa);
	                	//local
	                	Label llocal = new Label(5,linha,t.get_local());
	                	sheet.addCell(llocal);
	                	//data
	                	DateTime ldata = new DateTime(6,linha,d,dateFormat);
	                	sheet.addCell(ldata);
	                	//horas
	                	Number lhora = new Number(7,linha,horas);
	                	sheet.addCell(lhora);
	                	linha++;
                	}
    			}
    		}
    		workbook.write(); 
    		workbook.close();
			return 0;
        }
        catch(Exception e){
        	e.printStackTrace();
        	this.setCursor(Cursor.getDefaultCursor());
        	new Log_erros_class().write_log_to_file(e);
        	return 1;
        }
	}
	
	private String get_cliente_projecto(String id)
    {
        Projecto p = null;
        if (this.lista_projectos_user.containsKey(id))
            p = this.lista_projectos_user.get(id);
        if (p!=null)
            return p.get_cliente().get_nome();
        return "";
    }
	
	private void get_list_projectos_from_dm(){
    	this.lista_projectos_user = this.dm.lista_projectos_user;
    }
	
    private static WritableCellFormat getCell_desc_line() throws WriteException {
        Colour colour = Colour.WHITE;
        Pattern pattern = Pattern.SOLID;
    	WritableFont cellFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
        cellFont.setColour(Colour.GRAY_25);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setBackground(colour, pattern);
        return cellFormat;
    }
    
    private static WritableCellFormat getCell_total_line() throws WriteException {
    	Colour colour = Colour.GRAY_25;
    	Pattern pattern = Pattern.SOLID;
    	WritableFont cellFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
    	cellFont.setColour(Colour.BLUE);
    	WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
    	cellFormat.setBackground(colour, pattern);
    	return cellFormat;
    }

    private String print_data(Date d){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(d);
    }
    
    private String create_name_file_part(Date inicio, Date fim){
    	String aux = "";
    	if (inicio == null && fim != null){
    		aux = "_ate_" + print_data(fim);
    	}
    	else if (inicio != null && fim == null){
    		aux = "_desde_" + print_data(inicio);
    	}
    	else if (inicio != null && fim != null){
    		aux = "_desde_" + print_data(inicio) + "_ate_" + print_data(fim);
    	}
    	else{
    		aux = "";
    	}
    	
    	return aux;
    }
    
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    private String remove_space_notas(String s){
    	return s.replaceAll("\n", "").replaceAll("\r", "");
    }
}
