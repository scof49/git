/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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

import java.awt.Font;

/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Export_to_excel extends javax.swing.JFrame {
    String username;
    
    /**
     * @wbp.parser.constructor
     */
    public Export_to_excel(String user_name) {
        initComponents();
        this.username = user_name;
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
    }
    
    public Export_to_excel(String user_name, int month, int year, int flag) {
        initComponents();
        this.username = user_name;
        this.month_field.setMonth(month);
        this.year_field.setYear(year);
        if (flag==0)
            tarefas_check.setSelected(true);
        else
            despesas_check.setSelected(true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
    }

    
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tarefas_check = new javax.swing.JCheckBox();
        despesas_check = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        month_field = new com.toedter.calendar.JMonthChooser();
        year_field = new com.toedter.calendar.JYearChooser();
        jButton2 = new javax.swing.JButton();
        jButton2.setFont(new Font("Tahoma", Font.BOLD, 11));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/export_excel.png"))); // NOI18N
        jLabel1.setText("Exportar para excel");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_back.png"))); // NOI18N
        jButton1.setText("Voltar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Excel para:");

        tarefas_check.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tarefas_check.setText("Tarefas");

        despesas_check.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        despesas_check.setText("Despesas");

        jLabel3.setText("Mês:");

        year_field.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/export_button.png"))); // NOI18N
        jButton2.setText("Exportar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(despesas_check)
                            .addComponent(tarefas_check))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(year_field, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(month_field, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tarefas_check)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(despesas_check))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(month_field, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(year_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        this.setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!tarefas_check.isSelected() && !despesas_check.isSelected())
            JOptionPane.showMessageDialog(null,"Tem que seleccionar pelo menos uma das caixas de despesas ou tarefas!");
        else{
        	//chooser            
        	String path = "";
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Destino ficheiros!");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            int esc = chooser.showSaveDialog(this);
            if (esc==JFileChooser.APPROVE_OPTION){
                File ficheirorecebido = chooser.getSelectedFile();
                path = ficheirorecebido.getAbsolutePath();
            }   
            //fim chooser

        	
        	int mon = month_field.getMonth();
            int year = year_field.getYear();
            if (tarefas_check.isSelected()){
                String name_file = (mon+1) + "-" + year + "_tarefas.xls";
                generateTarefaXlsFile(name_file, mon, year, path);
            }
            if (despesas_check.isSelected()){
                String name_file = (mon+1) + "-" + year + "_despesas.xls";
                generateDespesaXlsFile(name_file, mon, year, path);
            }
            JOptionPane.showMessageDialog(null,"Ficheiro(s) criado(s) em: "+ path);
            this.dispose();
        }
    }
    
    private static WritableCellFormat set_cell_style() throws WriteException {
        Colour colour = Colour.GRAY_25;
        Pattern pattern = Pattern.SOLID;
    	WritableFont cellFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
        cellFont.setColour(Colour.BLACK);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setBackground(colour, pattern);
        return cellFormat;
    }
    
    private static WritableCellFormat get_cell_data() throws WriteException {
    	Colour colour = Colour.GRAY_25;
    	Pattern pattern = Pattern.SOLID;
    	DateFormat customDateFormat = new DateFormat ("dd-MM-yyyy");
    	WritableFont cellFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
        cellFont.setColour(Colour.BLACK);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont,customDateFormat);
        cellFormat.setBackground(colour, pattern);
        return cellFormat;
    }
    
    private void generateTarefaXlsFile(String sFileName, int month,int year, String path)
    {
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       Calendar c = Calendar.getInstance();
       c.set(Calendar.HOUR_OF_DAY, 4);
       c.set(Calendar.DAY_OF_MONTH, 1);
       c.set(Calendar.MONTH, month);
       c.set(Calendar.YEAR, year);
       int num_col =  c.getActualMaximum(Calendar.DAY_OF_MONTH) + 6;
       try
       {
            File s_file = new File(".");
            s_file.mkdirs();
            if (path.equals(""))
            	path = s_file.getAbsolutePath();
            String name_file = path + "\\" + sFileName;
            WritableWorkbook workbook = Workbook.createWorkbook(new File(name_file));
    		WritableSheet sheet = workbook.createSheet("Horas", 0);
            //primeira linha -> tarefas + mes + ano
    		int linha = 0;
    		Label l01 = new Label(0,linha,"Tarefas:");
    		sheet.addCell(l01);
    		Number l02 = new Number(1,linha,(month+1));
    		sheet.addCell(l02);
    		Number l03 = new Number(2,linha,year);
    		sheet.addCell(l03);
    		//segunda linha-> username 
    		linha++;
    		Label n01 = new Label(0,linha,"Username:");
    		sheet.addCell(n01);
    		Label n02 = new Label(1,linha,this.username);
    		sheet.addCell(n02);
    		//terceira linha -> vazio
    		linha+=2;
            //linha de cabeçalho -> cliente + id proj + etapa + actividade + etapa + local + dias do mes
    		Label c01 = new Label(0,linha,"Cliente",set_cell_style());
    		sheet.addCell(c01);
    		Label c02 = new Label(1,linha,"Id Projecto",set_cell_style());
    		sheet.addCell(c02);
    		Label c03 = new Label(2,linha,"Etapa",set_cell_style());
    		sheet.addCell(c03);
    		Label c04 = new Label(3,linha,"Actividade",set_cell_style());
    		sheet.addCell(c04);
    		Label c05 = new Label(4,linha,"Tarefa",set_cell_style());
    		sheet.addCell(c05);
    		Label c06 = new Label(5,linha,"Local",set_cell_style());
    		sheet.addCell(c06);
    		DateTime dia;
    		int aux = 1;
    		while(aux<(num_col-5)){
    			c.set(Calendar.DAY_OF_MONTH, aux);
    			Date d = c.getTime();
    			dia = new DateTime(aux+5,linha,d,get_cell_data());
    			sheet.addCell(dia);
    			aux++;
    		}
    		
    		Connection con = new Connection_bd(this.username).get_connection();
    		WritableSheet sheet_projectos = workbook.createSheet("Projectos", 1);
    		sheet_projectos = set_projectos_sheet(sheet_projectos,con);
    		WritableSheet sheet_cliente = workbook.createSheet("Cliente", 2);
    		sheet_cliente = set_clientes_sheet(sheet_cliente,con);
    		WritableSheet sheet_etapas = workbook.createSheet("Etapas", 3);
    		sheet_etapas = set_etapas_sheet(sheet_etapas,con);
    		WritableSheet sheet_atividades = workbook.createSheet("Actividades", 4);
    		sheet_atividades = set_atividades_sheet(sheet_atividades,con);
    		WritableSheet sheet_tarefas = workbook.createSheet("Tarefas", 5);
    		sheet_tarefas = set_tarefas_sheet(sheet_tarefas,con);
    		con.close();
    		workbook.write(); 
    		workbook.close();
    		
		}
		catch(IOException | WriteException | SQLException e)
		{
			e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username,e);
		} 
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    private WritableSheet set_tipos_sheet(WritableSheet sheet_tipo_despesa,Connection con) {
    	ArrayList<Tipo_despesa> tipos = new ArrayList<>();
        try{
        //tipos despesas
        String sql = "select * from tnm_tipo_despesa order by descricao";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String nome = rs.getString("DESCRICAO");
            Tipo_despesa td = new Tipo_despesa();
            td.set_nome(nome);            
            tipos.add(td);
        }
        
        int contador = 0;
        
        for (Tipo_despesa t : tipos){
        	Label tip = new Label(0,contador,t.get_nome());
        	sheet_tipo_despesa.addCell(tip);
        	contador++;
        }
        
        
        }
        catch(SQLException | WriteException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            this.setCursor(Cursor.getDefaultCursor());
        }
		return sheet_tipo_despesa;
	}

	private WritableSheet set_tarefas_sheet(WritableSheet sheet_tarefas,Connection con) {
    	ArrayList<Tarefa> tarefas = new ArrayList<>();
        try{
        //tarefas
        String sql = "select * from tnm_trf_tarefa order by nome";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String nome = rs.getString("NOME");
            Tarefa ta = new Tarefa();
            ta.set_nome(nome);            
            tarefas.add(ta);
        }
        
        int contador = 0;
        
        for (Tarefa t : tarefas){
        	Label tar = new Label(0,contador,t.get_nome());
        	sheet_tarefas.addCell(tar);
        	contador++;
        }
        
        
        }
        catch(SQLException | WriteException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            this.setCursor(Cursor.getDefaultCursor());
        }
		return sheet_tarefas;
	}

	private WritableSheet set_atividades_sheet(WritableSheet sheet_atividades,Connection con) {
    	ArrayList<Actividade> atividades = new ArrayList<>();
        try{
        //actividades
        String sql = "select * from tnm_trf_actividade order by nome";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String nome = rs.getString("NOME");
            Actividade at = new Actividade();
            at.set_nome(nome);            
            atividades.add(at);
        }
        
        int contador = 0;
        
        for (Actividade a : atividades){
        	Label at = new Label(0,contador,a.get_nome());
        	sheet_atividades.addCell(at);
        	contador++;
        }
        
        
        }
        catch(SQLException | WriteException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            this.setCursor(Cursor.getDefaultCursor());
        }
		return sheet_atividades;
	}

	private WritableSheet set_etapas_sheet(WritableSheet sheet_etapas,Connection con) {
    	ArrayList<Etapa> etapas = new ArrayList<>();
        try{
        //etapas
        String sql = "select * from tnm_trf_etapa order by nome";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String nome = rs.getString("NOME");
            Etapa et = new Etapa();
            et.set_nome(nome);            
            etapas.add(et);
        }
        
        int contador = 0;
        
        for (Etapa e : etapas){
        	Label eta = new Label(0,contador,e.get_nome());
        	sheet_etapas.addCell(eta);
        	contador++;
        }
        
        
        }
        catch(SQLException | WriteException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            this.setCursor(Cursor.getDefaultCursor());
        }
		return sheet_etapas;
	}

	private WritableSheet set_clientes_sheet(WritableSheet sheet_cliente,Connection con) {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        try{
            PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_trf_cliente order by nome";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                String nome = rs.getString("NOME");
                Cliente c = new Cliente();
                c.set_nome(nome);
                clientes.add(c);
            }
            ps.close();
            rs.close();
            
            int contador = 0;
            for (Cliente c : clientes){
            	Label cli = new Label(0,contador,c.get_nome());
            	sheet_cliente.addCell(cli);
            	contador++;
            }
        }
        catch(SQLException | WriteException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            this.setCursor(Cursor.getDefaultCursor());
        }
    	
		return sheet_cliente;
	}

	private WritableSheet set_projectos_sheet(WritableSheet sheet_projectos,Connection con) {
    	TreeMap<String,Projecto> projectos = new TreeMap<>();
        try{
        //projectos
        String sql = "select * from tnm_trf_projecto order by id_projecto" ;
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            ByteArrayInputStream bais;
            ObjectInputStream ois;
            try{
                String codigo = rs.getString("ID_PROJECTO");
                bais = new ByteArrayInputStream(rs.getBytes("PROJECTO"));
                ois = new ObjectInputStream(bais);
                Projecto p = (Projecto)ois.readObject();
                projectos.put(codigo, p);
                
            }
            catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                e.printStackTrace();
                new Log_erros_class().write_log_to_file(this.username,e);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }  
        rs.close();
        ps.close();
        
        //loop a lista de projectos para adicionar na sheet
        int contador = 0;
        for (Projecto p : projectos.values()){
        	Label id = new Label(0,contador,p.get_codigo());
        	sheet_projectos.addCell(id);
        	Label nome = new Label(1,contador,p.get_nome());
        	sheet_projectos.addCell(nome);
        	contador++;
        }
        
        }
        catch(SQLException | WriteException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            this.setCursor(Cursor.getDefaultCursor());
        }
		return sheet_projectos;
	}

	private void generateDespesaXlsFile(String sFileName, int month,int year, String path)
    {
       try
       {
            File s_file = new File(".");
            s_file.mkdirs();
            if (path.equals(""))
            	path = s_file.getAbsolutePath();
            String name_file = path + "\\" + sFileName;
            WritableWorkbook workbook = Workbook.createWorkbook(new File(name_file));
    		WritableSheet sheet = workbook.createSheet("Horas", 0);
            //primeira linha -> despesas + mes + ano
    		int linha = 0;
    		Label l01 = new Label(0,linha,"Despesas:");
    		sheet.addCell(l01);
    		Number l02 = new Number(1,linha,(month+1));
    		sheet.addCell(l02);
    		Number l03 = new Number(2,linha,year);
    		sheet.addCell(l03);
    		//segunda linha-> username 
    		linha++;
    		Label n01 = new Label(0,linha,"Username:");
    		sheet.addCell(n01);
    		Label n02 = new Label(1,linha,this.username);
    		sheet.addCell(n02);
    		//terceira linha -> vazio
    		linha+=2;
    		//cabeçalho Tipo Despesa;Cliente;ID Projecto;Etapa;Actividade;Transacao;Quantidade;Valor;Moeda(USD,AKZ,EUR);Notas
    		Label c01 = new Label(0,linha,"Tipo de Despesa",set_cell_style());
    		sheet.addCell(c01);
    		Label c02 = new Label(1,linha,"Cliente",set_cell_style());
    		sheet.addCell(c02);
    		Label c03 = new Label(2,linha,"ID Projecto",set_cell_style());
    		sheet.addCell(c03);
    		Label c04 = new Label(3,linha,"Etapa",set_cell_style());
    		sheet.addCell(c04);
    		Label c05 = new Label(4,linha,"Actividade",set_cell_style());
    		sheet.addCell(c05);
    		Label c06 = new Label(5,linha,"Transação",set_cell_style());
    		sheet.addCell(c06);
    		Label c07 = new Label(6,linha,"Quantidade",set_cell_style());
    		sheet.addCell(c07);
    		Label c08 = new Label(7,linha,"Valor",set_cell_style());
    		sheet.addCell(c08);
    		Label c09 = new Label(8,linha,"Moeda (USD,AKZ,EUR)",set_cell_style());
    		sheet.addCell(c09);
    		Label c10 = new Label(9,linha,"Notas",set_cell_style());
    		sheet.addCell(c10);
    		
    		Connection con = new Connection_bd(this.username).get_connection();

    		WritableSheet sheet_tipo_despesa = workbook.createSheet("Tipo de despesa", 1);
    		sheet_tipo_despesa = set_tipos_sheet(sheet_tipo_despesa,con);
    		WritableSheet sheet_projectos = workbook.createSheet("Projectos", 2);
    		sheet_projectos = set_projectos_sheet(sheet_projectos,con);
    		WritableSheet sheet_cliente = workbook.createSheet("Cliente", 3);
    		sheet_cliente = set_clientes_sheet(sheet_cliente,con);
    		WritableSheet sheet_etapas = workbook.createSheet("Etapas", 4);
    		sheet_etapas = set_etapas_sheet(sheet_etapas,con);
    		WritableSheet sheet_atividades = workbook.createSheet("Actividades", 5);
    		sheet_atividades = set_atividades_sheet(sheet_atividades,con);
    		con.close();
    		
    		workbook.write(); 
    		workbook.close();
		}
		catch(IOException | WriteException | SQLException e)
		{
			e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username,e);
		} 
    }

    // Variables declaration - do not modify
    private javax.swing.JCheckBox despesas_check;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private com.toedter.calendar.JMonthChooser month_field;
    private javax.swing.JCheckBox tarefas_check;
    private com.toedter.calendar.JYearChooser year_field;
    
}
