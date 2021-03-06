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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

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

import javax.swing.JTabbedPane;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.BorderLayout;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class Menu_relatorios extends javax.swing.JFrame {
	TreeMap<String,Funcionario> lista_funcionarios_completa;
	TreeMap<String,Projecto> lista_projectos_completa;
	TreeMap<String,Cliente> lista_clientes_completa;
	TreeSet<String> lista_projectos_internos;
    String username_admin;
    
    public Menu_relatorios(String user_adm,TreeMap<String,Funcionario> lista_func) {
        this.username_admin = user_adm;
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        set_radio_button_groups();
        set_data_inicio_fim_mes();
        get_list_projectos_clientes_from_bd();
        this.lista_funcionarios_completa = lista_func;
        carrega_listas_from_bd();
        calc_lista_projectos_internos();
    }
    
    private void carrega_listas_from_bd(){
    	preencher_listas_funcionarios();
    	preencher_listas_projectos();
    	preencher_listas_clientes();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void preencher_listas_funcionarios(){
        DefaultListModel lm_todos = new DefaultListModel();
        DefaultListModel vazio = new DefaultListModel();
        for (String s : this.lista_funcionarios_completa.keySet())
            lm_todos.addElement(s);
        f_list_out.setModel(lm_todos);
        f_list_in.setModel(vazio);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void preencher_listas_projectos(){
        DefaultListModel lm_todos = new DefaultListModel();
        DefaultListModel vazio = new DefaultListModel();
        for (Projecto p : this.lista_projectos_completa.values())
            lm_todos.addElement(p.get_codigo() + " : " + p.get_nome());
        p_list_out.setModel(lm_todos);
        p_list_in.setModel(vazio);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void preencher_listas_clientes(){
        DefaultListModel lm_todos = new DefaultListModel();
        DefaultListModel vazio = new DefaultListModel();
        for (Cliente c : this.lista_clientes_completa.values())
            lm_todos.addElement(c.get_nome());
        c_list_out.setModel(lm_todos);
        c_list_in.setModel(vazio);
    }
    
    private void set_data_inicio_fim_mes(){
        Calendar cal = Calendar.getInstance();
        int mes = cal.get(Calendar.MONTH);
        int ano = cal.get(Calendar.YEAR);
        int f = 1;
        int l = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(ano, mes, f);
        Date inicio = cal.getTime();
        cal.set(ano, mes, l);
        Date fim = cal.getTime();
        data_inicio_field.setDate(inicio);
        data_fim_field.setDate(fim);
    }
    
    private void set_radio_button_groups(){
        horas_vs_despesas.add(jr_apenas_horas);
        horas_vs_despesas.add(jr_apenas_despesas);
        horas_vs_despesas.add(jr_horas_despesas);
        jr_apenas_horas.setSelected(true);
        
        single_vs_multi_file.add(jr_single);
        single_vs_multi_file.add(jr_multi);
        jr_multi.setSelected(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    @SuppressWarnings("rawtypes")
	private void initComponents() {

        horas_vs_despesas = new javax.swing.ButtonGroup();
        aprov_vs_disaprov = new javax.swing.ButtonGroup();
        single_vs_multi_file = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        data_inicio_field = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        data_fim_field = new com.toedter.calendar.JDateChooser();
        painel_filtro = new javax.swing.JPanel();
        jr_apenas_horas = new javax.swing.JRadioButton();
        jr_apenas_despesas = new javax.swing.JRadioButton();
        jr_horas_despesas = new javax.swing.JRadioButton();
        jr_single = new javax.swing.JRadioButton();
        jr_multi = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        b_cancelar = new javax.swing.JButton();
        b_relatorio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/report.png"))); // NOI18N
        jLabel1.setText("Menu de Relatórios de Horas e Despesas");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data"));

        jLabel2.setText("De:");

        jLabel3.setText("Até:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(data_inicio_field, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(data_fim_field, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(data_fim_field, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(data_inicio_field, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painel_filtro.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));

        jr_apenas_horas.setText("Horas");

        jr_apenas_despesas.setText("Despesas");

        jr_horas_despesas.setText("Tudo");

        jr_single.setText("Único ficheiro para todos consultores");

        jr_multi.setText("Consultores separados por ficheiro");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout painel_filtroLayout = new javax.swing.GroupLayout(painel_filtro);
        painel_filtroLayout.setHorizontalGroup(
        	painel_filtroLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(painel_filtroLayout.createSequentialGroup()
        			.addGap(16)
        			.addGroup(painel_filtroLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(painel_filtroLayout.createSequentialGroup()
        					.addGroup(painel_filtroLayout.createParallelGroup(Alignment.TRAILING, false)
        						.addComponent(jr_apenas_despesas, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        						.addComponent(jr_horas_despesas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addGap(65))
        				.addGroup(painel_filtroLayout.createSequentialGroup()
        					.addComponent(jr_apenas_horas, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
        			.addGap(14)
        			.addGroup(painel_filtroLayout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(jr_multi, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        				.addComponent(jr_single, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addContainerGap(24, Short.MAX_VALUE))
        );
        painel_filtroLayout.setVerticalGroup(
        	painel_filtroLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(painel_filtroLayout.createSequentialGroup()
        			.addGap(17)
        			.addGroup(painel_filtroLayout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(painel_filtroLayout.createParallelGroup(Alignment.LEADING)
        					.addComponent(jr_single)
        					.addGroup(painel_filtroLayout.createSequentialGroup()
        						.addGap(23)
        						.addComponent(jr_apenas_despesas)))
        				.addComponent(jr_multi))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jr_horas_despesas)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        		.addGroup(painel_filtroLayout.createSequentialGroup()
        			.addGap(16)
        			.addGroup(painel_filtroLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jr_apenas_horas)
        				.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(11, Short.MAX_VALUE))
        );
        painel_filtro.setLayout(painel_filtroLayout);

        b_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/Cancel-icon.png"))); // NOI18N
        b_cancelar.setText("Cancelar");
        b_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_cancelarActionPerformed(evt);
            }
        });

        b_relatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/report_mini.png"))); // NOI18N
        b_relatorio.setText("Obter Relatório");
        b_relatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_relatorioActionPerformed(evt);
            }
        });
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        check_aprovados = new JCheckBox("Apenas horas e/ou despesas aprovados");
        check_aprovados.setSelected(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(painel_filtro, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(3)
        					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE))
        				.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 596, GroupLayout.PREFERRED_SIZE)
        					.addGap(0, 184, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(check_aprovados, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
        					.addComponent(b_relatorio)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(b_cancelar, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(painel_filtro, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        					.addComponent(b_cancelar, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
        					.addComponent(b_relatorio, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
        				.addComponent(check_aprovados))
        			.addContainerGap())
        );
        
        funcionario_panel_tab = new JPanel();
        tabbedPane.addTab("Consultor", null, funcionario_panel_tab, null);
        panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "Filtrar:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "N\u00E3o filtrar:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        panel = new JPanel();
        
        all_right_f = new JButton(">>");
        all_right_f.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		all_right_f_action();
        	}
        });
        
        right_f = new JButton("->");
        right_f.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		right_f_action();
        	}
        });
        
        left_f = new JButton("<-");
        left_f.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		left_f_action();
        	}
        });
        
        all_left_f = new JButton("<<");
        all_left_f.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		all_left_f_action();
        	}
        });
        
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 78, Short.MAX_VALUE)
        		.addComponent(all_right_f, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        		.addComponent(right_f, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        		.addComponent(left_f, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        		.addComponent(all_left_f, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 223, Short.MAX_VALUE)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(49)
        			.addComponent(all_right_f, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(right_f, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(left_f, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(all_left_f, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        			.addGap(48))
        );
        panel.setLayout(gl_panel);
        GroupLayout gl_funcionario_panel_tab = new GroupLayout(funcionario_panel_tab);
        gl_funcionario_panel_tab.setHorizontalGroup(
        	gl_funcionario_panel_tab.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_funcionario_panel_tab.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        			.addContainerGap())
        );
        gl_funcionario_panel_tab.setVerticalGroup(
        	gl_funcionario_panel_tab.createParallelGroup(Alignment.LEADING)
        		.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
        		.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
        		.addGroup(gl_funcionario_panel_tab.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
        			.addContainerGap())
        );
        panel_1.setLayout(new BorderLayout(0, 0));
        
        scrollPane_1 = new JScrollPane();
        panel_1.add(scrollPane_1, BorderLayout.CENTER);
        
        f_list_in = new JList();
        scrollPane_1.setViewportView(f_list_in);
        panel_2.setLayout(new BorderLayout(0, 0));
        
        scrollPane = new JScrollPane();
        panel_2.add(scrollPane, BorderLayout.CENTER);
        
        f_list_out = new JList();
        scrollPane.setViewportView(f_list_out);
        funcionario_panel_tab.setLayout(gl_funcionario_panel_tab);
        
        projecto_panel_tab = new JPanel();
        tabbedPane.addTab("Projecto", null, projecto_panel_tab, null);
        
        panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "N\u00E3o filtrar:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_3.setLayout(new BorderLayout(0, 0));
        
        panel_4 = new JPanel();
        panel_4.setBorder(new TitledBorder(null, "Filtrar:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_4.setLayout(new BorderLayout(0, 0));
        
        panel_5 = new JPanel();
        
        p_all_left = new JButton("<<");
        p_all_left.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
            	all_left_p_action();
        	}
        });
        
        p_left = new JButton("<-");
        p_left.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
            	left_p_action();
        	}
        });
        
        p_right = new JButton("->");
        p_right.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		right_p_action();
        	}
        });
        
        p_all_right = new JButton(">>");
        p_all_right.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    all_right_p_action();
        	}
        });
        GroupLayout gl_panel_5 = new GroupLayout(panel_5);
        gl_panel_5.setHorizontalGroup(
        	gl_panel_5.createParallelGroup(Alignment.TRAILING)
        		.addComponent(p_all_right, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        		.addComponent(p_right, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        		.addComponent(p_left, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        		.addComponent(p_all_left, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );
        gl_panel_5.setVerticalGroup(
        	gl_panel_5.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_5.createSequentialGroup()
        			.addGap(49)
        			.addComponent(p_all_right, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(p_right, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(p_left, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(p_all_left, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        			.addGap(48))
        );
        panel_5.setLayout(gl_panel_5);
        GroupLayout gl_projecto_panel_tab = new GroupLayout(projecto_panel_tab);
        gl_projecto_panel_tab.setHorizontalGroup(
        	gl_projecto_panel_tab.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_projecto_panel_tab.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        			.addContainerGap())
        );
        gl_projecto_panel_tab.setVerticalGroup(
        	gl_projecto_panel_tab.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_projecto_panel_tab.createSequentialGroup()
        			.addGap(11)
        			.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addContainerGap())
        		.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        		.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );
        {
        	scrollPane_3 = new JScrollPane();
        	panel_4.add(scrollPane_3, BorderLayout.CENTER);
        	{
        		p_list_in = new JList();
        		scrollPane_3.setViewportView(p_list_in);
        	}
        }
        
        scrollPane_2 = new JScrollPane();
        panel_3.add(scrollPane_2, BorderLayout.CENTER);
        {
        	p_list_out = new JList();
        	scrollPane_2.setViewportView(p_list_out);
        }
        projecto_panel_tab.setLayout(gl_projecto_panel_tab);
        
        cliente_panel_tab = new JPanel();
        tabbedPane.addTab("Cliente", null, cliente_panel_tab, null);
        panel_6 = new JPanel();
        panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "N\u00E3o filtrar:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_6.setLayout(new BorderLayout(0, 0));
        panel_7 = new JPanel();
        panel_7.setBorder(new TitledBorder(null, "Filtrar:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_7.setLayout(new BorderLayout(0, 0));
        panel_8 = new JPanel();
        c_all_left = new JButton("<<");
        c_all_left.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		all_left_c_action();
        	}
        });
        c_left = new JButton("<-");
        c_left.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		left_c_action();
        	}
        });
        c_right = new JButton("->");
        c_right.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		right_c_action();
        	}
        });
        c_all_right = new JButton(">>");
        c_all_right.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		all_right_c_action();
        	}
        });
        GroupLayout gl_panel_8 = new GroupLayout(panel_8);
        gl_panel_8.setHorizontalGroup(
        	gl_panel_8.createParallelGroup(Alignment.TRAILING)
        		.addComponent(c_all_right, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        		.addComponent(c_right, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        		.addComponent(c_left, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        		.addComponent(c_all_left, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );
        gl_panel_8.setVerticalGroup(
        	gl_panel_8.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_8.createSequentialGroup()
        			.addGap(49)
        			.addComponent(c_all_right, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(c_right, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(c_left, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(c_all_left, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        			.addGap(49))
        );
        panel_8.setLayout(gl_panel_8);
        GroupLayout gl_cliente_panel_tab = new GroupLayout(cliente_panel_tab);
        gl_cliente_panel_tab.setHorizontalGroup(
        	gl_cliente_panel_tab.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_cliente_panel_tab.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_8, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        			.addContainerGap())
        );
        gl_cliente_panel_tab.setVerticalGroup(
        	gl_cliente_panel_tab.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_cliente_panel_tab.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel_8, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        			.addGap(10))
        		.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        		.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );
        {
        	scrollPane_5 = new JScrollPane();
        	panel_7.add(scrollPane_5, BorderLayout.CENTER);
        	{
        		c_list_in = new JList();
        		scrollPane_5.setViewportView(c_list_in);
        	}
        }
        {
        	scrollPane_4 = new JScrollPane();
        	panel_6.add(scrollPane_4, BorderLayout.CENTER);
        	{
        		c_list_out = new JList();
        		scrollPane_4.setViewportView(c_list_out);
        	}
        }
        cliente_panel_tab.setLayout(gl_cliente_panel_tab);
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Date reset_data(Date d){
    	Date d_retorno = null;
    	Calendar c = new GregorianCalendar();
    	c.setTime(d);
    	int dia = c.get(Calendar.DAY_OF_MONTH);
    	int mes = c.get(Calendar.MONTH);
    	int ano = c.get(Calendar.YEAR);
    	c.clear();
    	c.set(Calendar.DAY_OF_MONTH, dia);
    	c.set(Calendar.MONTH, mes);
    	c.set(Calendar.YEAR, ano);
    	d_retorno = c.getTime();
    	return d_retorno;
    }
    
    private Date reset_data(Date d,int i){
    	Date d_retorno = null;
    	Calendar c = new GregorianCalendar();
    	c.setTime(d);
    	int dia = c.get(Calendar.DAY_OF_MONTH);
    	int mes = c.get(Calendar.MONTH);
    	int ano = c.get(Calendar.YEAR);
    	c.clear();
    	c.set(Calendar.DAY_OF_MONTH, dia);
    	c.set(Calendar.MONTH, mes);
    	c.set(Calendar.YEAR, ano);
    	c.add(Calendar.DAY_OF_MONTH, i);
    	c.add(Calendar.SECOND, -1);
    	d_retorno = c.getTime();
    	return d_retorno;
    }
    
    private void b_relatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_relatorioActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Date d1 = reset_data(data_inicio_field.getDate());
        Date d2 = reset_data(data_fim_field.getDate(),1);
        if (d1 == null || d2 == null){
            JOptionPane.showMessageDialog(null, "Tem que preencher os campos de data de início e fim de relatório!");
        }
        else if (d2.before(d1)){
            JOptionPane.showMessageDialog(null, "Data de fim de filtro tem que ser posterior à data de início!");
        }
        else{
        	TreeMap<String,Funcionario> funcionarios = return_funcionarios_from_list();
        	TreeMap<String,Projecto> projectos = return_projectos_from_list();
        	TreeMap<String,Cliente> clientes = return_clientes_from_list();
            int horas_despesas;
            //HORAS - DESPESAS --> 1 horas. 2 despesas. 3 tudo
            if (jr_apenas_horas.isSelected()){
                //JOptionPane.showMessageDialog(null, "Relatorio de Horas");
                horas_despesas = 1;
            }
            else if (jr_apenas_despesas.isSelected()){
                //JOptionPane.showMessageDialog(null, "Relatorio de Despesas");
                horas_despesas = 2;
            }
            else{
                //JOptionPane.showMessageDialog(null, "Relatorio de Horas e Despesas");
                horas_despesas = 3;
            }
            
            int sfile;
            //Ficheiros --> 1 single file. 2 multi file
            if (jr_single.isSelected()){
                //JOptionPane.showMessageDialog(null, "Single File");
                sfile = 1;
            }
            else{
                //JOptionPane.showMessageDialog(null, "Multi File");
                sfile = 2;
            }
            //chooser
            String path = "";
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Destino ficheiros relatórios!");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            int esc = chooser.showSaveDialog(this);
            if (esc==JFileChooser.APPROVE_OPTION){
                File ficheirorecebido = chooser.getSelectedFile();
                path = ficheirorecebido.getAbsolutePath();
            
	            //fim chooser
	            if (!path.equals(""))
	            	path += "\\";
	            do_filter(d1,d2,funcionarios,projectos,clientes, horas_despesas, sfile, path);
            }   
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_b_relatorioActionPerformed

    private void do_filter(Date inicio, Date fim, TreeMap<String,Funcionario> funcionarios, TreeMap<String,Projecto> projectos, TreeMap<String,Cliente> clientes, int horas_despesas, int sfile, String path){
        int res = 0;
    	
    	//horas
        if (horas_despesas==1 || horas_despesas==3){
            res += file_horas(inicio,fim,funcionarios,projectos,clientes,sfile, path);
        }
        
        //Despesas
        if (horas_despesas==2 || horas_despesas==3){
            res += file_despesas(inicio,fim,funcionarios,projectos,clientes,sfile, path);
        }
        
//        JOptionPane.showMessageDialog(null, "Relatório concluído!");
        String message = "Relatório terminado! Deseja gerar mais algum relatório?";
        if (res!=0)
        	message = "Ocorrência de erro. Deseja tentar outra vez? Se persistir o erro, contacte administrador!";
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        int dialogResult = JOptionPane.showConfirmDialog (null,message,"Relatórios",JOptionPane.YES_NO_OPTION);
        if(dialogResult != JOptionPane.YES_OPTION){
        	this.dispose();
        }
        
    }
    
    private int file_horas(Date inicio, Date fim, TreeMap<String,Funcionario> funcionarios, TreeMap<String,Projecto> projectos, TreeMap<String,Cliente> clientes, int sfile, String path){
    	int linha_indice;
    	if (sfile!=1){
        //um ficheiro para cada funcionario
            try{
            for (Funcionario f : funcionarios.values())
            {
            	linha_indice = 0;
                WritableWorkbook workbook = Workbook.createWorkbook(new File(path + "horas_"+f.get_nome()+"_"+ print_data(inicio) +"_to_"+ print_data(fim) +".xls"));
        		WritableSheet sheet = workbook.createSheet("Horas", 0);
        		Label label_from = new Label(0,linha_indice, "De:",getCell_first_line()); 
        		sheet.addCell(label_from); 
        		DateTime label_from_data = new DateTime(1,linha_indice,inicio,getCell_first_line_data());
        		sheet.addCell(label_from_data); 
        		Label label_to = new Label(2, linha_indice, "Até:",getCell_first_line()); 
        		sheet.addCell(label_to); 
        		DateTime label_to_data = new DateTime(3,linha_indice,fim,getCell_first_line_data());
        		sheet.addCell(label_to_data);
        		Label laux1 = new Label(4,linha_indice,"",getCell_first_line());
        		Label laux2 = new Label(5,linha_indice,"",getCell_first_line());
        		Label laux3 = new Label(6,linha_indice,"",getCell_first_line());
        		Label laux4 = new Label(7,linha_indice,"",getCell_first_line());
        		Label laux5 = new Label(8,linha_indice,"",getCell_first_line());
        		sheet.addCell(laux1);
        		sheet.addCell(laux2);
        		sheet.addCell(laux3);
        		sheet.addCell(laux4);
        		sheet.addCell(laux5);
        		linha_indice++;
        		
                Label l1 = new Label(0,linha_indice,"Cliente",getCell_desc_line());
        		sheet.addCell(l1);
        		Label l2 = new Label(1,linha_indice,"Projecto",getCell_desc_line());
        		sheet.addCell(l2);
        		Label l3 = new Label(2,linha_indice,"Recurso",getCell_desc_line());
        		sheet.addCell(l3);
        		Label l4 = new Label(3,linha_indice,"Etapa",getCell_desc_line());
        		sheet.addCell(l4);
        		Label l5 = new Label(4,linha_indice,"Actividade",getCell_desc_line());
        		sheet.addCell(l5);
        		Label l6 = new Label(5,linha_indice,"Tarefa",getCell_desc_line());
        		sheet.addCell(l6);
        		Label l7 = new Label(6,linha_indice,"Local",getCell_desc_line());
        		sheet.addCell(l7);
        		Label l8 = new Label(7,linha_indice,"Data",getCell_desc_line());
        		sheet.addCell(l8);
        		Label l9 = new Label(8,linha_indice,"Horas",getCell_desc_line());
        		sheet.addCell(l9);
        		linha_indice++;
                
        		Indice_Relatorio indice = get_horas_funcionario(f,inicio,fim,sheet,linha_indice,projectos,clientes);
                linha_indice = indice.get_linha_indice();
                
                workbook.write(); 
        		workbook.close();
            }
            
            }
            catch(FileNotFoundException e){
            	JOptionPane.showMessageDialog(null, "O ficheiro escolhido já está a ser usado e não pode ser modificado!");
            	return 1;
            }
            catch(IOException | WriteException e){
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(this.username_admin,e);
            }
        }
        else{
        //mesmo ficheiro para todos os funcionarios
        	try{
        		linha_indice = 0;
                WritableWorkbook workbook = Workbook.createWorkbook(new File(path + "horas_todos_funcionarios_"+ print_data(inicio) +"_to_"+ print_data(fim) +".xls"));
        		WritableSheet sheet = workbook.createSheet("Horas", 0);
        		Label label_from = new Label(0,linha_indice, "De:",getCell_first_line()); 
        		sheet.addCell(label_from); 
        		DateTime label_from_data = new DateTime(1,linha_indice,inicio,getCell_first_line_data());
        		sheet.addCell(label_from_data); 
        		Label label_to = new Label(2, linha_indice, "Até:",getCell_first_line()); 
        		sheet.addCell(label_to); 
        		DateTime label_to_data = new DateTime(3,linha_indice,fim,getCell_first_line_data());
        		sheet.addCell(label_to_data);
        		Label laux1 = new Label(4,linha_indice,"",getCell_first_line());
        		Label laux2 = new Label(5,linha_indice,"",getCell_first_line());
        		Label laux3 = new Label(6,linha_indice,"",getCell_first_line());
        		Label laux4 = new Label(7,linha_indice,"",getCell_first_line());
        		Label laux5 = new Label(8,linha_indice,"",getCell_first_line());
        		sheet.addCell(laux1);
        		sheet.addCell(laux2);
        		sheet.addCell(laux3);
        		sheet.addCell(laux4);
        		sheet.addCell(laux5);
        		linha_indice++;
        		
                Label l1 = new Label(0,linha_indice,"Cliente",getCell_desc_line());
        		sheet.addCell(l1);
        		Label l2 = new Label(1,linha_indice,"Projecto",getCell_desc_line());
        		sheet.addCell(l2);
        		Label l3 = new Label(2,linha_indice,"Recurso",getCell_desc_line());
        		sheet.addCell(l3);
        		Label l4 = new Label(3,linha_indice,"Etapa",getCell_desc_line());
        		sheet.addCell(l4);
        		Label l5 = new Label(4,linha_indice,"Actividade",getCell_desc_line());
        		sheet.addCell(l5);
        		Label l6 = new Label(5,linha_indice,"Tarefa",getCell_desc_line());
        		sheet.addCell(l6);
        		Label l7 = new Label(6,linha_indice,"Local",getCell_desc_line());
        		sheet.addCell(l7);
        		Label l8 = new Label(7,linha_indice,"Data",getCell_desc_line());
        		sheet.addCell(l8);
        		Label l9 = new Label(8,linha_indice,"Horas",getCell_desc_line());
        		sheet.addCell(l9);
        		linha_indice++;
        		
        		for (Funcionario f : funcionarios.values())
                {
        			Indice_Relatorio indice = get_horas_funcionario(f,inicio,fim,sheet,linha_indice,projectos,clientes);
                    linha_indice = indice.get_linha_indice();
                }
        		workbook.write(); 
        		workbook.close();
            }
        	catch(FileNotFoundException e){
            	JOptionPane.showMessageDialog(null, "O ficheiro escolhido já está a ser usado e não pode ser modificado!");
            	return 1;
            }
            catch(IOException | WriteException e){
            	e.printStackTrace();
            	this.setCursor(Cursor.getDefaultCursor());
            	new Log_erros_class().write_log_to_file(this.username_admin,e);
            }
        }
    	return 0;
    }

    private static WritableCellFormat getCell_first_line() throws WriteException {
    	Colour colour = Colour.GRAY_25;
    	Pattern pattern = Pattern.SOLID;
    	WritableFont cellFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
        cellFont.setColour(Colour.DARK_BLUE);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setBackground(colour, pattern);
        return cellFormat;
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
    
    private static WritableCellFormat getCell_funcionario() throws WriteException {
        Colour colour = Colour.ICE_BLUE;
        Pattern pattern = Pattern.SOLID;
    	WritableFont cellFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
        cellFont.setColour(Colour.BLACK);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setBackground(colour, pattern);
        return cellFormat;
    }
    
    private static WritableCellFormat getCell_projecto() throws WriteException {
        Colour colour = Colour.GRAY_25;
        Pattern pattern = Pattern.SOLID;
    	WritableFont cellFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
        cellFont.setColour(Colour.BLACK);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setBackground(colour, pattern);
        return cellFormat;
    }
    
    private static WritableCellFormat getCell_first_line_data() throws WriteException {
    	Colour colour = Colour.GRAY_25;
    	Pattern pattern = Pattern.SOLID;
    	DateFormat customDateFormat = new DateFormat ("dd-MM-yyyy");
    	WritableFont cellFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
        cellFont.setColour(Colour.DARK_BLUE);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont,customDateFormat);
        cellFormat.setBackground(colour, pattern);
        return cellFormat;
    }
    
    private static WritableCellFormat getCell_interno(int ferias) throws WriteException {
    	Colour colour = Colour.IVORY;
    	Pattern pattern = Pattern.SOLID;
    	WritableFont cellFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
        if (ferias == 1)
        	cellFont.setColour(Colour.RED);
        else
        	cellFont.setColour(Colour.BLUE_GREY);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setBackground(colour, pattern);
        return cellFormat;
    }
    
    private static WritableCellFormat getCell_interno_data(int ferias) throws WriteException {
    	Colour colour = Colour.IVORY;
    	Pattern pattern = Pattern.SOLID;
    	DateFormat customDateFormat = new DateFormat ("dd-MM-yyyy");
    	WritableFont cellFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
    	if (ferias == 1)
        	cellFont.setColour(Colour.RED);
        else
        	cellFont.setColour(Colour.BLUE_GREY);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont,customDateFormat);
        cellFormat.setBackground(colour, pattern);
        return cellFormat;
    }
    
    private int file_despesas(Date inicio, Date fim, TreeMap<String,Funcionario> funcionarios, TreeMap<String,Projecto> projectos, TreeMap<String,Cliente> clientes, int sfile, String path){
    	if (sfile!=1){
        //um ficheiro para cada funcionario
            try{
            int linha_indice;
            for (Funcionario f : funcionarios.values())
            {
                linha_indice = 0;
                WritableWorkbook workbook = Workbook.createWorkbook(new File(path + "despesas_"+f.get_nome()+"_"+ print_data(inicio) +"_to_"+ print_data(fim) +".xls"));
        		WritableSheet sheet = workbook.createSheet("Despesas", 0);
        		Label label_from = new Label(0,linha_indice, "De:",getCell_first_line()); 
        		sheet.addCell(label_from); 
        		DateTime label_from_data = new DateTime(1,linha_indice,inicio,getCell_first_line_data());
        		sheet.addCell(label_from_data); 
        		Label label_to = new Label(2, linha_indice, "Até:",getCell_first_line()); 
        		sheet.addCell(label_to); 
        		DateTime label_to_data = new DateTime(3,linha_indice,fim,getCell_first_line_data());
        		sheet.addCell(label_to_data);
        		Label laux1 = new Label(4,linha_indice,"",getCell_first_line());
        		Label laux2 = new Label(5,linha_indice,"",getCell_first_line());
        		Label laux3 = new Label(6,linha_indice,"",getCell_first_line());
        		Label laux4 = new Label(7,linha_indice,"",getCell_first_line());
        		Label laux5 = new Label(8,linha_indice,"",getCell_first_line());
        		sheet.addCell(laux1);
        		sheet.addCell(laux2);
        		sheet.addCell(laux3);
        		sheet.addCell(laux4);
        		sheet.addCell(laux5);
        		linha_indice++;
        		
        		Label l1 = new Label(0,linha_indice,"Data",getCell_desc_line());
        		sheet.addCell(l1);
        		Label l2 = new Label(1,linha_indice,"Tipo de Despesa",getCell_desc_line());
        		sheet.addCell(l2);
        		Label l3 = new Label(2,linha_indice,"Data aprovação",getCell_desc_line());
        		sheet.addCell(l3);
        		Label l4 = new Label(3,linha_indice,"Quantidade",getCell_desc_line());
        		sheet.addCell(l4);
        		Label l5 = new Label(4,linha_indice,"Valor",getCell_desc_line());
        		sheet.addCell(l5);
        		Label l6 = new Label(5,linha_indice,"Notas",getCell_desc_line());
        		sheet.addCell(l6);
        		Label l7 = new Label(6,linha_indice,"Etapa",getCell_desc_line());
        		sheet.addCell(l7);
        		Label l8 = new Label(7,linha_indice,"Actividade",getCell_desc_line());
        		sheet.addCell(l8);
        		Label l9 = new Label(8,linha_indice,"Valor Original",getCell_desc_line());
        		sheet.addCell(l9);
        		linha_indice++;
        		
                linha_indice++;
                Indice_Relatorio indice = get_despesas_funcionario(f,inicio,fim,sheet,linha_indice,projectos,clientes);
                linha_indice = indice.get_linha_indice();
                
                workbook.write(); 
        		workbook.close();
            }
            
            }
            catch(FileNotFoundException e){
            	JOptionPane.showMessageDialog(null, "O ficheiro escolhido já está a ser usado e não pode ser modificado!");
            	return 1;
            }
            catch(WriteException | IOException e){
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(this.username_admin,e);
            }
        }
        else{
        	//mesmo ficheiro para todos os funcionarios
            try{
            double total = 0.0;
            int linha_indice = 0;
            WritableWorkbook workbook = Workbook.createWorkbook(new File(path + "despesas_todos_funcionarios_"+ print_data(inicio) +"_to_"+ print_data(fim) +".xls"));
    		WritableSheet sheet = workbook.createSheet("Despesas", 0);
    		Label label_from = new Label(0,linha_indice, "De:",getCell_first_line()); 
    		sheet.addCell(label_from); 
    		DateTime label_from_data = new DateTime(1,linha_indice,inicio,getCell_first_line_data());
    		sheet.addCell(label_from_data); 
    		Label label_to = new Label(2, linha_indice, "Até:",getCell_first_line()); 
    		sheet.addCell(label_to); 
    		DateTime label_to_data = new DateTime(3,linha_indice,fim,getCell_first_line_data());
    		sheet.addCell(label_to_data);
    		Label laux1 = new Label(4,linha_indice,"",getCell_first_line());
    		Label laux2 = new Label(5,linha_indice,"",getCell_first_line());
    		Label laux3 = new Label(6,linha_indice,"",getCell_first_line());
    		Label laux4 = new Label(7,linha_indice,"",getCell_first_line());
    		Label laux5 = new Label(8,linha_indice,"",getCell_first_line());
    		sheet.addCell(laux1);
    		sheet.addCell(laux2);
    		sheet.addCell(laux3);
    		sheet.addCell(laux4);
    		sheet.addCell(laux5);
    		linha_indice++;
    		
    		Label l1 = new Label(0,linha_indice,"Data",getCell_desc_line());
    		sheet.addCell(l1);
    		Label l2 = new Label(1,linha_indice,"Tipo de Despesa",getCell_desc_line());
    		sheet.addCell(l2);
    		Label l3 = new Label(2,linha_indice,"Data aprovação",getCell_desc_line());
    		sheet.addCell(l3);
    		Label l4 = new Label(3,linha_indice,"Quantidade",getCell_desc_line());
    		sheet.addCell(l4);
    		Label l5 = new Label(4,linha_indice,"Valor",getCell_desc_line());
    		sheet.addCell(l5);
    		Label l6 = new Label(5,linha_indice,"Notas",getCell_desc_line());
    		sheet.addCell(l6);
    		Label l7 = new Label(6,linha_indice,"Etapa",getCell_desc_line());
    		sheet.addCell(l7);
    		Label l8 = new Label(7,linha_indice,"Actividade",getCell_desc_line());
    		sheet.addCell(l8);
    		Label l9 = new Label(8,linha_indice,"Valor Original",getCell_desc_line());
    		sheet.addCell(l9);
    		linha_indice++;
    		
            linha_indice++;
            
            for (Funcionario f : funcionarios.values())
            {
            	Indice_Relatorio indice = get_despesas_funcionario(f,inicio,fim,sheet,linha_indice,projectos,clientes);
                linha_indice = indice.get_linha_indice();
                total += indice.get_total();
            }
            Label end_main = new Label(0,linha_indice,"Total:");
            Number end_main_val = new Number(1,linha_indice,total);
            sheet.addCell(end_main);
            sheet.addCell(end_main_val);
            workbook.write(); 
    		workbook.close();
            }
            catch(FileNotFoundException e){
            	JOptionPane.showMessageDialog(null, "O ficheiro escolhido já está a ser usado e não pode ser modificado!");
            	return 1;
            }
            catch(WriteException | IOException e){
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(this.username_admin,e);
            }
        }
    	return 0;
    }
    
    private Indice_Relatorio get_horas_funcionario(Funcionario f, Date inicio, Date fim, WritableSheet sheet,int linha_indice,TreeMap<String,Projecto> projectos, TreeMap<String,Cliente> clientes){
    	TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> aux;
        if (check_aprovados.isSelected()){
	    	ArrayList<Date> dias_aprovados = get_lista_dias_aprovados(f.get_username(),inicio,fim);
	        aux = get_tarefas_dias_aprovados(f.get_username(),dias_aprovados,projectos,clientes);
        }
        else{
        	ArrayList<Date> dias_submetidos = get_lista_dias_submetidos(f.get_username(),inicio,fim);
        	aux = get_tarefas_todas(f.get_username(),dias_submetidos,projectos, clientes);
        }
        return print_all_funcionario_horas(f.get_username(),aux,sheet,f.get_nome(),linha_indice);
    }
    
    private Indice_Relatorio get_despesas_funcionario(Funcionario f, Date inicio, Date fim, WritableSheet sheet,int linha_indice,TreeMap<String,Projecto> projectos,TreeMap<String,Cliente> clientes){
        TreeMap<String,TreeMap<Integer,Despesa_new>> aux;
        if (check_aprovados.isSelected())
        	aux = get_lista_despesas_aprovadas(f.get_username(),inicio,fim,projectos,clientes);
        else
        	aux = get_lista_despesas_todas(f.get_username(),inicio,fim,projectos,clientes);
        return print_all_funcionarios_despesas(aux,sheet,f.get_nome(),linha_indice);
    }
    
    private Indice_Relatorio print_all_funcionario_horas(String username, TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> aux, WritableSheet sheet,String nome,int linha){
        ArrayList<Indice_percentagens> lista_ip = new ArrayList<Indice_percentagens>();
    	String proj_antigo = "";
    	String proj = "";
    	String id_proj_lastline = "";
    	String proj_lastline = "";
    	double total_horas = 0.0;
        DateFormat customDateFormat = new DateFormat ("dd-MM-yyyy"); 
		WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat);
        for (Comparable_TarefaHoras ct : aux.keySet()){
            try{  
            	TarefaHoras t = ct.get_tarefas();
        		proj = t.get_id_projecto() + " : " + t.get_nome_projecto();
        		if (proj_antigo.equals("") && !is_projecto_interno(t.get_id_projecto()))
        			proj_antigo = proj;
        		
        		if (!proj.equals(proj_antigo) && !is_projecto_interno(t.get_id_projecto()))//!t.get_id_projecto().contains("ODKASF0998"))
        		{
        			id_proj_lastline = t.get_id_projecto();
        			proj_lastline = t.get_id_projecto() + " : " + t.get_nome_projecto();
        			Label lproject_line = new Label(0,linha,"Projecto:",getCell_projecto());
        			Label lproject_name = new Label(1,linha,proj_antigo,getCell_projecto());
        			Label l1 = new Label(2,linha,"",getCell_projecto());
        			Label l2 = new Label(3,linha,"",getCell_projecto());
        			Label l3 = new Label(4,linha,"",getCell_projecto());
        			Label l4 = new Label(5,linha,"",getCell_projecto());
        			Label l5 = new Label(6,linha,"",getCell_projecto());
        			Label l6 = new Label(7,linha,"",getCell_projecto());
        			Number ltotal = new Number(8,linha,total_horas,getCell_projecto());
        			sheet.addCell(lproject_line);
        			sheet.addCell(lproject_name);
        			sheet.addCell(l1);
        			sheet.addCell(l2);
        			sheet.addCell(l3);
        			sheet.addCell(l4);
        			sheet.addCell(l5);
        			sheet.addCell(l6);
        			sheet.addCell(ltotal);
        			Indice_percentagens ip = new Indice_percentagens(linha, total_horas);
        			lista_ip.add(ip);
        			
        			total_horas = 0;
        			linha++;
        			proj_antigo = proj;
        		}
        		
        		
        		
        		if (!is_projecto_interno(t.get_id_projecto()))//!t.get_id_projecto().equals("ODKASF0998"))
                {
        			TreeMap<Date,Double> datas = aux.get(ct);
	                for (Date d : datas.keySet())
	                { 
	                	//cliente
	                	Label lcliente = new Label(0,linha,get_cliente_projecto(t.get_id_projecto()));
	                	sheet.addCell(lcliente);
	                	//projecto
	                	Label lprojecto = new Label(1,linha,proj);
	                	sheet.addCell(lprojecto);
	                	//nome
	                	Label lnome = new Label(2,linha,nome);
	                	sheet.addCell(lnome);
	                	//etapa
	                	Label letapa = new Label(3,linha,t.get_etapa());
	                	sheet.addCell(letapa);
	                	//actividade
	                	Label latividade = new Label(4,linha,t.get_actividade());
	                	sheet.addCell(latividade);
	                	//tarefa
	                	Label ltarefa = new Label(5,linha,t.get_tarefa());
	                	sheet.addCell(ltarefa);
	                	//local
	                	Label llocal = new Label(6,linha,t.get_local());
	                	sheet.addCell(llocal);
	                	//data
	                	DateTime ldata = new DateTime(7,linha,d,dateFormat);
	                	sheet.addCell(ldata);
	                	//horas
	                	Number lhora = new Number(8,linha,datas.get(d));
	                	sheet.addCell(lhora);
	                	linha++;
	                	total_horas+=datas.get(d);
	                }
                }
            }
            catch(Exception e)
            {
            	e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(this.username_admin,e);
            }
        }
        if (!proj_lastline.equals("") && !is_projecto_interno(id_proj_lastline))//!proj.contains("ODKASF0998"))
        try{
        	Label lproject_line = new Label(0,linha,"Projecto:",getCell_projecto());
			//Label lproject_name = new Label(1,linha,proj,getCell_projecto());
			Label lproject_name = new Label(1,linha,proj_lastline,getCell_projecto());
			Label l1 = new Label(2,linha,"",getCell_projecto());
			Label l2 = new Label(3,linha,"",getCell_projecto());
			Label l3 = new Label(4,linha,"",getCell_projecto());
			Label l4 = new Label(5,linha,"",getCell_projecto());
			Label l5 = new Label(6,linha,"",getCell_projecto());
			Label l6 = new Label(7,linha,"",getCell_projecto());
			Number ltotal = new Number(8,linha,total_horas,getCell_projecto());
			sheet.addCell(lproject_line);
			sheet.addCell(lproject_name);
			sheet.addCell(l1);
			sheet.addCell(l2);
			sheet.addCell(l3);
			sheet.addCell(l4);
			sheet.addCell(l5);
			sheet.addCell(l6);
			sheet.addCell(ltotal);
			Indice_percentagens ip = new Indice_percentagens(linha, total_horas);
			lista_ip.add(ip);
			
			total_horas = 0;
			linha++;
        }
        catch(Exception e){
        	e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        //add percentagens
        double total = get_total_indices(lista_ip);
        for (Indice_percentagens ip : lista_ip){
        	double percent = (ip.total / total )* 100;
        	DecimalFormat df = new DecimalFormat("#.##");
        	String percent_str = df.format(percent);
        	percent_str = percent_str.replace(",", ".");
        	percent = Double.parseDouble(percent_str);
        	try {
				Number lpercent = new Number(7,ip.linha,percent,getCell_projecto());
				sheet.addCell(lpercent);
			} catch (WriteException e) {
				e.printStackTrace();
	            this.setCursor(Cursor.getDefaultCursor());
	            new Log_erros_class().write_log_to_file(this.username_admin,e);
			}
        }
        
        for (Comparable_TarefaHoras ct : aux.keySet()){
            try{
        	TarefaHoras t = ct.get_tarefas();
        	proj = t.get_id_projecto() + " : " + t.get_nome_projecto();
            if (is_projecto_interno(t.get_id_projecto()))//t.get_id_projecto().equals("ODKASF0998"))														
            {
                TreeMap<Date,Double> datas = aux.get(ct);
                for (Date d : datas.keySet())
                { 
                	int ferias = 0;
                	if (t.get_id_projecto().equals("ODKASF0998"))
                		ferias = 1;
                	
                	//cliente
                	Label lcliente = new Label(0,linha,get_cliente_projecto(t.get_id_projecto()),getCell_interno(ferias));
                	sheet.addCell(lcliente);
                	//projecto
                	Label lprojecto = new Label(1,linha,proj,getCell_interno(ferias));
                	sheet.addCell(lprojecto);
                	//nome
                	Label lnome = new Label(2,linha,nome,getCell_interno(ferias));
                	sheet.addCell(lnome);
                	//etapa
                	Label letapa = new Label(3,linha,t.get_etapa(),getCell_interno(ferias));
                	sheet.addCell(letapa);
                	//actividade
                	Label latividade = new Label(4,linha,t.get_actividade(),getCell_interno(ferias));
                	sheet.addCell(latividade);
                	//tarefa
                	Label ltarefa = new Label(5,linha,t.get_tarefa(),getCell_interno(ferias));
                	sheet.addCell(ltarefa);
                	//local
                	Label llocal = new Label(6,linha,t.get_local(),getCell_interno(ferias));
                	sheet.addCell(llocal);
                	//data
                	DateTime ldata = new DateTime(7,linha,d,getCell_interno_data(ferias));
                	sheet.addCell(ldata);
                	//horas
                	Number lhora = new Number(8,linha,datas.get(d),getCell_interno(ferias));
                	sheet.addCell(lhora);
                	linha++;
                }
            }
            }
            catch(Exception e)
            {
            	e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(this.username_admin,e);
            }
            
        }
        try {
			Label lfuncionario = new Label(0,linha,"Total para:",getCell_funcionario());
			Label lfunc_nome = new Label(1,linha,nome,getCell_funcionario());
			Label l1 = new Label(2,linha,"",getCell_funcionario());
			Label l2 = new Label(3,linha,"",getCell_funcionario());
			Label l3 = new Label(4,linha,"",getCell_funcionario());
			Label l4 = new Label(5,linha,"",getCell_funcionario());
			Label l5 = new Label(6,linha,"",getCell_funcionario());
			Label l6 = new Label(7,linha,"",getCell_funcionario());
			Number lfunc_number = new Number(8,linha,total,getCell_funcionario());
			sheet.addCell(lfuncionario);
			sheet.addCell(lfunc_nome);
			sheet.addCell(lfunc_number);
			sheet.addCell(l1);
			sheet.addCell(l2);
			sheet.addCell(l3);
			sheet.addCell(l4);
			sheet.addCell(l5);
			sheet.addCell(l6);
			linha++;
			
		} catch (WriteException e) {
			e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
		}
        return new Indice_Relatorio(linha, 0);
    }
    
    private void calc_lista_projectos_internos(){
    	TreeSet<String> prj_internos = new TreeSet<String>();
    	for (Projecto p : this.lista_projectos_completa.values()){
    		if (p.get_cliente().get_id().equals("00"))
			{
				prj_internos.add(p.get_codigo());
			}
    	}
    	this.lista_projectos_internos = prj_internos;
    }
    
    private boolean is_projecto_interno(String id){
    	return this.lista_projectos_internos.contains(id);
    }
    
    private double get_total_indices(ArrayList<Indice_percentagens> lista){
    	double total = 0.0;
    	for (Indice_percentagens ip : lista)
    		total += ip.total;
    	return total;
    }
    
    private String get_cliente_projecto(String id)
    {
        Projecto p = null;
        if (this.lista_projectos_completa.containsKey(id))
            p = this.lista_projectos_completa.get(id);
        if (p!=null)
            return p.get_cliente().get_nome();
        return "";
    }
    
    private void get_list_projectos_clientes_from_bd(){
    	TreeMap<String,Projecto> projectos = new TreeMap<>();
    	TreeMap<String,Cliente> clientes = new TreeMap<>();
        
        try{
        Connection con = (new Connection_bd(this.username_admin)).get_connection();
        //projectos
        String sql = "select * from tnm_trf_projecto" ;
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
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(this.username_admin,e);
            }
        }  
        rs.close();
        ps.close();
        
        //clientes
        sql = "select * from tnm_trf_cliente";
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()){
        	Cliente c = new Cliente();
        	c.set_id(rs.getString("ID"));
        	c.set_nome(rs.getString("NOME"));
        	c.set_morada(rs.getString("MORADA"));
        	c.set_contactos(rs.getString("CONTACTOS"));
        	c.set_notas(rs.getString("NOTAS"));
        	clientes.put(c.get_nome(), c);
        }
        rs.close();
        ps.close();
        con.close();
        
        this.lista_projectos_completa = projectos;
        this.lista_clientes_completa = clientes;
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
    }
    
    private Indice_Relatorio print_all_funcionarios_despesas(TreeMap<String,TreeMap<Integer,Despesa_new>> aux, WritableSheet sheet,String nome,int linha){
    	DateFormat customDateFormat = new DateFormat ("dd-MM-yyyy"); 
		WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat);
    	double total = 0.0;
        try{
	    	double total_proj;
	        Label nome_desc = new Label(0,linha, "Funcionário:",getCell_funcionario());
	        Label nome_label = new Label(1,linha, nome,getCell_funcionario()); 
			sheet.addCell(nome_desc); 
			sheet.addCell(nome_label);
			Label laux1 = new Label(2,linha,"",getCell_funcionario());
			Label laux2 = new Label(3,linha,"",getCell_funcionario());
			Label laux3 = new Label(4,linha,"",getCell_funcionario());
    		Label laux4 = new Label(5,linha,"",getCell_funcionario());
    		Label laux5 = new Label(6,linha,"",getCell_funcionario());
    		Label laux6 = new Label(7,linha,"",getCell_funcionario());
    		Label laux7 = new Label(8,linha,"",getCell_funcionario());
    		sheet.addCell(laux1);
    		sheet.addCell(laux2);
    		sheet.addCell(laux3);
    		sheet.addCell(laux4);
    		sheet.addCell(laux5);
    		sheet.addCell(laux6);
    		sheet.addCell(laux7);
			linha++;
	        for (String projecto : aux.keySet())
	        {
	            total_proj = 0.0;
	            Label proj_desc = new Label(0,linha, "Projecto:",getCell_projecto());
	            Label proj_label = new Label(1,linha, projecto,getCell_projecto()); 
	            Label proj_cliente = new Label(2,linha, "(" + get_cliente_projecto(projecto.split(":")[0].trim()) + ")",getCell_projecto());
	    		sheet.addCell(proj_desc); 
	    		sheet.addCell(proj_cliente); 
	    		sheet.addCell(proj_label); 
				Label laux32 = new Label(3,linha,"",getCell_projecto());
				Label laux33 = new Label(4,linha,"",getCell_projecto());
	    		Label laux34 = new Label(5,linha,"",getCell_projecto());
	    		Label laux35 = new Label(6,linha,"",getCell_projecto());
	    		Label laux36 = new Label(7,linha,"",getCell_projecto());
	    		Label laux37 = new Label(8,linha,"",getCell_projecto());
	    		sheet.addCell(laux32);
	    		sheet.addCell(laux33);
	    		sheet.addCell(laux34);
	    		sheet.addCell(laux35);
	    		sheet.addCell(laux36);
	    		sheet.addCell(laux37);
	    		linha++;
	    		for (Despesa_new d : aux.get(projecto).values()){
	            	double aux_val = round(d.get_valor_euros(),2);
	            	//data
	            	DateTime ldata = new DateTime(0,linha,d.get_data_despesa(),dateFormat);
	            	sheet.addCell(ldata);
	            	//tipo
	            	Label ltipo = new Label(1,linha,d.get_tipo());
	            	sheet.addCell(ltipo);
	            	//data aprovacao
	            	if (d.get_data_aprovacao()!=null){
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
	            	
	            	total_proj += (d.get_quantidade() * aux_val);
	            	total_proj = round(total_proj,2);
	            }
	    		Label desc_total_proj = new Label(0,linha,"Total for:",getCell_projecto());
	    		Label desc_total_nome = new Label(1,linha,projecto,getCell_projecto());
	    		sheet.addCell(desc_total_proj);
	    		sheet.addCell(desc_total_nome);
	    		Number tot_proj_label = new Number(2,linha,total_proj,getCell_projecto());
	    		sheet.addCell(tot_proj_label);
				Label laux42 = new Label(3,linha,"",getCell_projecto());
				Label laux43 = new Label(4,linha,"",getCell_projecto());
	    		Label laux44 = new Label(5,linha,"",getCell_projecto());
	    		Label laux45 = new Label(6,linha,"",getCell_projecto());
	    		Label laux46 = new Label(7,linha,"",getCell_projecto());
	    		Label laux47 = new Label(8,linha,"",getCell_projecto());
	    		sheet.addCell(laux42);
	    		sheet.addCell(laux43);
	    		sheet.addCell(laux44);
	    		sheet.addCell(laux45);
	    		sheet.addCell(laux46);
	    		sheet.addCell(laux47);
	    		linha++;
	            total += total_proj;
	        }
	        Label nome_total_desc = new Label(0,linha, "Total for:",getCell_funcionario());
	        Label nome_total_nome = new Label(1,linha, nome,getCell_funcionario());
	        Number nome_total_number = new Number(2,linha, total,getCell_funcionario()); 
			sheet.addCell(nome_total_desc); 
			sheet.addCell(nome_total_nome); 
			sheet.addCell(nome_total_number); 
			Label laux22 = new Label(3,linha,"",getCell_funcionario());
			Label laux23 = new Label(4,linha,"",getCell_funcionario());
    		Label laux24 = new Label(5,linha,"",getCell_funcionario());
    		Label laux25 = new Label(6,linha,"",getCell_funcionario());
    		Label laux26 = new Label(7,linha,"",getCell_funcionario());
    		Label laux27 = new Label(8,linha,"",getCell_funcionario());
    		sheet.addCell(laux22);
    		sheet.addCell(laux23);
    		sheet.addCell(laux24);
    		sheet.addCell(laux25);
    		sheet.addCell(laux26);
    		sheet.addCell(laux27);
			linha++;
        }
        catch(Exception e){
        	e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        Indice_Relatorio indice = new Indice_Relatorio(linha,total);
        return indice;
    }
    
    private String remove_space_notas(String s){
    	return s.replaceAll("\n", "").replaceAll("\r", "");
    }
    
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    @SuppressWarnings("unused")
	private String print_nota(String username,Date d,String id_tarefa){
        String nota = "";
        try{
            Connection con = (new Connection_bd(this.username_admin)).get_connection();
            String sql = "select * from tnm_notas_tarefas where username = ? and id_tarefa = ? and data_nota = ?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, id_tarefa);
            java.sql.Date aux = new java.sql.Date(d.getTime());
            ps.setDate(3, aux);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                nota = rs.getString("NOTA");
            }
            nota = nota.replaceAll("\n", "").replaceAll("\r", "");
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        return nota;
    }
    
    private String print_data(Date d){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(d);
    }
    
    private TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> get_tarefas_dias_aprovados(String username, ArrayList<Date> dias_aprovados,TreeMap<String,Projecto> projectos, TreeMap<String,Cliente> clientes){
    	TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> aux = new TreeMap<>();
        try{
            Connection con = (new Connection_bd(this.username_admin)).get_connection();
            String sql = "select * from tnm_tarefas where username = '"+ username + "'";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ByteArrayInputStream bais;
                ObjectInputStream ois;
                try{
                    bais = new ByteArrayInputStream(rs.getBytes("TAREFA"));
                    ois = new ObjectInputStream(bais);
                    TarefaHoras aux_tarefa = (TarefaHoras)ois.readObject();
                    TreeMap<Date,Double> mapa_horas = new TreeMap<>();
                    //if filtro projecto + cliente
                    if (pertence_filtro(aux_tarefa, projectos, clientes))
                    {
                    	for (Date d : aux_tarefa.get_map().keySet())
	                        if (dias_aprovados.contains(d) && aux_tarefa.get_map().get(d) != 0.0)
	                            mapa_horas.put(d,aux_tarefa.get_map().get(d));
	                    if (mapa_horas.size()>0)
	                    {
	                        //aux_tarefa.set_map(mapa_horas);
	                        aux.put(new Comparable_TarefaHoras(aux_tarefa.get_id(),aux_tarefa), mapa_horas);
	                    }
                    }
                    ois.close();
                    bais.close();
                }
                catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                    this.setCursor(Cursor.getDefaultCursor());
                    new Log_erros_class().write_log_to_file(this.username_admin,e);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        return aux;
    }
    
    private TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> get_tarefas_todas(String username, ArrayList<Date> dias_submetidos,TreeMap<String,Projecto> projectos, TreeMap<String,Cliente> clientes){
    	TreeMap<Comparable_TarefaHoras,TreeMap<Date,Double>> aux = new TreeMap<>();
        try{
            Connection con = (new Connection_bd(this.username_admin)).get_connection();
            String sql = "select * from tnm_tarefas where username = '"+ username + "'";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ByteArrayInputStream bais;
                ObjectInputStream ois;
                try{
                    bais = new ByteArrayInputStream(rs.getBytes("TAREFA"));
                    ois = new ObjectInputStream(bais);
                    TarefaHoras aux_tarefa = (TarefaHoras)ois.readObject();
                    TreeMap<Date,Double> mapa_horas = new TreeMap<>();
                    //if filtro projecto + cliente
                    if (pertence_filtro(aux_tarefa, projectos, clientes))
                    {
                    	for (Date d : aux_tarefa.get_map().keySet())
            				if (dias_submetidos.contains(d) && aux_tarefa.get_map().get(d) != 0.0)
                        		mapa_horas.put(d,aux_tarefa.get_map().get(d));
	                    if (mapa_horas.size()>0)
	                    {
//	                        //aux_tarefa.set_map(mapa_horas);
	                        aux.put(new Comparable_TarefaHoras(aux_tarefa.get_id(),aux_tarefa), mapa_horas);
	                    }
                    }
                    ois.close();
                    bais.close();
                }
                catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                    this.setCursor(Cursor.getDefaultCursor());
                    new Log_erros_class().write_log_to_file(this.username_admin,e);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        return aux;
    }
    
    private ArrayList<Date> get_lista_dias_aprovados(String username_funcionario, Date inicio, Date fim){
        ArrayList<Date> dias_aprovados = new ArrayList<>();
        try{
            Connection con = (new Connection_bd(this.username_admin)).get_connection();
            String sql = "select * from tnm_handle_horas where username = ? and data between ? and ?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, username_funcionario);
            java.sql.Date sql_inicio = new java.sql.Date(inicio.getTime());
            ps.setDate(2, sql_inicio);
            java.sql.Date sql_fim = new java.sql.Date(fim.getTime());
            ps.setDate(3, sql_fim);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int sit = rs.getInt("SITUACAO");
                if (sit == 1)
                    dias_aprovados.add(rs.getDate("DATA"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        return dias_aprovados;
    }
    
    private ArrayList<Date> get_lista_dias_submetidos(String username_funcionario, Date inicio, Date fim){
        ArrayList<Date> dias_submetidos = new ArrayList<>();
        try{
            Connection con = (new Connection_bd(this.username_admin)).get_connection();
            String sql = "select * from tnm_handle_horas where username = ? and data between ? and ?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, username_funcionario);
            java.sql.Date sql_inicio = new java.sql.Date(inicio.getTime());
            ps.setDate(2, sql_inicio);
            java.sql.Date sql_fim = new java.sql.Date(fim.getTime());
            ps.setDate(3, sql_fim);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int sit = rs.getInt("SITUACAO");
                if (sit != 3)
                	dias_submetidos.add(rs.getDate("DATA"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        return dias_submetidos;
    }
    
    private TreeMap<String,TreeMap<Integer,Despesa_new>> get_lista_despesas_aprovadas(String username_funcionario, Date inicio, Date fim,TreeMap<String,Projecto> projectos,TreeMap<String,Cliente> clientes){
        TreeMap<String,TreeMap<Integer,Despesa_new>> aprovados = new TreeMap<>();
        try{
            Connection con = (new Connection_bd(this.username_admin)).get_connection();
            String sql = "select * from tnm_handlepagamentos where username = ? and situacao = 2 and data between ? and ?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, username_funcionario);
            ps.setDate(2, new java.sql.Date(inicio.getTime()));
            ps.setDate(3,  new java.sql.Date(fim.getTime()));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Despesa_new d = get_despesa(username_funcionario,rs.getInt("ID"),con);
                //aprovados.add(d);
                //if projecto e cliente
                if(pertence_filtro(d,projectos,clientes))
        		{
	                if ((!d.get_data_despesa().before(inicio)) && (!d.get_data_despesa().after(fim))){
	                    String proj = d.get_id_projecto() + " : " + d.get_nome_projecto();
	                    if (aprovados.containsKey(proj))
	                    {
	                        if (!aprovados.get(proj).containsKey(d.get_id()))
	                            aprovados.get(proj).put(d.get_id(), d);
	                    }
	                    else{
	                        TreeMap<Integer,Despesa_new> novo = new TreeMap<>();
	                        novo.put(d.get_id(),d);
	                        aprovados.put(proj, novo);
	                    }
	                }
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        return aprovados;
    }
    
    private TreeMap<String,TreeMap<Integer,Despesa_new>> get_lista_despesas_todas(String username_funcionario, Date inicio, Date fim,TreeMap<String,Projecto> projectos,TreeMap<String,Cliente> clientes){
        TreeMap<String,TreeMap<Integer,Despesa_new>> submetidas = new TreeMap<>();
        Connection con = (new Connection_bd(this.username_admin)).get_connection();
        ArrayList<Despesa_new> lista_despesas = get_despesas_bd(username_funcionario, con);
        for (Despesa_new d : lista_despesas){
        	if ((!d.get_data_despesa().before(inicio)) && (!d.get_data_despesa().after(fim))){
                //if projecto e cliente
        		if(pertence_filtro(d,projectos,clientes))
        		{
                	String proj = d.get_id_projecto() + " : " + d.get_nome_projecto();
	                if (submetidas.containsKey(proj))
	                {
	                    if (!submetidas.get(proj).containsKey(d.get_id()))
	                    	submetidas.get(proj).put(d.get_id(), d);
	                }
	                else{
	                    TreeMap<Integer,Despesa_new> novo = new TreeMap<>();
	                    novo.put(d.get_id(),d);
	                    submetidas.put(proj, novo);
	                }
                }
            }
        }
        return submetidas;
    }
    
    private Despesa_new get_despesa(String username, int id, Connection con){
        Despesa_new aux_res = null;
        try{
            String sql = "select * from tnm_despesas where username = ? and id_despesa = ?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ByteArrayInputStream bais;
                ObjectInputStream ois;
                try{
                    bais = new ByteArrayInputStream(rs.getBytes("DESPESA"));
                    ois = new ObjectInputStream(bais);
                    aux_res = (Despesa_new)ois.readObject();
                    
                    ois.close();
                    bais.close();
                }catch(IOException | ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                    this.setCursor(Cursor.getDefaultCursor());
                    new Log_erros_class().write_log_to_file(this.username_admin,e);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        return aux_res;
    }
    
    private ArrayList<Despesa_new> get_despesas_bd(String username, Connection con){
        ArrayList<Despesa_new> despesas = new ArrayList<>();
        try{
            String sql = "select * from tnm_despesas where username = ?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ByteArrayInputStream bais;
                ObjectInputStream ois;
                try{
                    bais = new ByteArrayInputStream(rs.getBytes("DESPESA"));
                    ois = new ObjectInputStream(bais);
                    Despesa_new aux_res = (Despesa_new)ois.readObject();
                    despesas.add(aux_res);
                    ois.close();
                    bais.close();
                }catch(IOException | ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                    this.setCursor(Cursor.getDefaultCursor());
                    new Log_erros_class().write_log_to_file(this.username_admin,e);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        return despesas;
    }
    
    private TreeMap<String,Funcionario> return_funcionarios_from_list(){
    	TreeMap<String,Funcionario> aux = new TreeMap<>();
        
        if (f_list_in.getModel().getSize() > 0)
            for (int i=0;i<f_list_in.getModel().getSize();i++)
            {
                String str_aux = f_list_in.getModel().getElementAt(i).toString();
                aux.put(str_aux, this.lista_funcionarios_completa.get(str_aux));
            }
        else
            for (int i=0;i<f_list_out.getModel().getSize();i++)
            {
                String str_aux = f_list_out.getModel().getElementAt(i).toString();
                aux.put(str_aux, this.lista_funcionarios_completa.get(str_aux));
            }
        return aux;
    }
    
    private TreeMap<String,Projecto> return_projectos_from_list(){
    	TreeMap<String,Projecto> aux = new TreeMap<>();
        
        if (p_list_in.getModel().getSize() > 0)
            for (int i=0;i<p_list_in.getModel().getSize();i++)
            {
                String str_aux = p_list_in.getModel().getElementAt(i).toString().split(" : ")[0];
                aux.put(str_aux, this.lista_projectos_completa.get(str_aux));
            }
        else
            for (int i=0;i<p_list_out.getModel().getSize();i++)
            {
                String str_aux = p_list_out.getModel().getElementAt(i).toString().split(" : ")[0];
                aux.put(str_aux, this.lista_projectos_completa.get(str_aux));
            }
        return aux;
    }
    
    private TreeMap<String,Cliente> return_clientes_from_list(){
    	TreeMap<String,Cliente> aux = new TreeMap<>();
        
        if (c_list_in.getModel().getSize() > 0)
            for (int i=0;i<c_list_in.getModel().getSize();i++)
            {
                String str_aux = c_list_in.getModel().getElementAt(i).toString();
                aux.put(str_aux, this.lista_clientes_completa.get(str_aux));
            }
        else
            for (int i=0;i<c_list_out.getModel().getSize();i++)
            {
                String str_aux = c_list_out.getModel().getElementAt(i).toString();
                aux.put(str_aux, this.lista_clientes_completa.get(str_aux));
            }
        return aux;
    }
    
    private void b_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_cancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_b_cancelarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void all_right_p_action(){
    	ListModel lm = p_list_out.getModel();
        ListModel lm2 = p_list_in.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        p_list_out.setModel(vazio);
        p_list_in.setModel(uniao);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void all_right_c_action(){
    	ListModel lm = c_list_out.getModel();
        ListModel lm2 = c_list_in.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        c_list_out.setModel(vazio);
        c_list_in.setModel(uniao);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void all_right_f_action(){
    	ListModel lm = f_list_out.getModel();
        ListModel lm2 = f_list_in.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        f_list_out.setModel(vazio);
        f_list_in.setModel(uniao);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void all_left_p_action(){
    	ListModel lm = p_list_out.getModel();
        ListModel lm2 = p_list_in.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        p_list_in.setModel(vazio);
        p_list_out.setModel(uniao);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void all_left_c_action(){
    	ListModel lm = c_list_out.getModel();
        ListModel lm2 = c_list_in.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        c_list_in.setModel(vazio);
        c_list_out.setModel(uniao);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void all_left_f_action(){
    	ListModel lm = f_list_out.getModel();
        ListModel lm2 = f_list_in.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        f_list_in.setModel(vazio);
        f_list_out.setModel(uniao);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private DefaultListModel une_list_model(ListModel l1, ListModel l2){
        DefaultListModel aux = new DefaultListModel();
        for (int i=0;i<l1.getSize();i++)
            aux.addElement(l1.getElementAt(i));
        for (int i=0;i<l2.getSize();i++)
            aux.addElement(l2.getElementAt(i));
        return aux;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void right_p_action(){
    	int[] lista_select = p_list_out.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < p_list_out.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = p_list_out.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = p_list_out.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = p_list_out.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = p_list_out.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < p_list_in.getModel().getSize();i++){
                    Object obj = p_list_in.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            p_list_out.setModel(lm_todos);
            p_list_in.setModel(lm_escolhidos);
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum projecto!");
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void right_c_action(){
    	int[] lista_select = c_list_out.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < c_list_out.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = c_list_out.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = c_list_out.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = c_list_out.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = c_list_out.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < c_list_in.getModel().getSize();i++){
                    Object obj = c_list_in.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            c_list_out.setModel(lm_todos);
            c_list_in.setModel(lm_escolhidos);
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum cliente!");
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void right_f_action(){
    	int[] lista_select = f_list_out.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < f_list_out.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = f_list_out.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = f_list_out.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = f_list_out.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = f_list_out.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < f_list_in.getModel().getSize();i++){
                    Object obj = f_list_in.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            f_list_out.setModel(lm_todos);
            f_list_in.setModel(lm_escolhidos);
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum consultor!");
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void left_p_action(){
    	int[] lista_select = p_list_in.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < p_list_in.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = p_list_in.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = p_list_in.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = p_list_in.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = p_list_in.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < p_list_out.getModel().getSize();i++){
                    Object obj = p_list_out.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            p_list_in.setModel(lm_todos);
            p_list_out.setModel(lm_escolhidos); 
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum projecto!");
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void left_c_action(){
    	int[] lista_select = c_list_in.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < c_list_in.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = c_list_in.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = c_list_in.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = c_list_in.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = c_list_in.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < c_list_out.getModel().getSize();i++){
                    Object obj = c_list_out.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            c_list_in.setModel(lm_todos);
            c_list_out.setModel(lm_escolhidos); 
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum cliente!");
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void left_f_action(){
    	int[] lista_select = f_list_in.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < f_list_in.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = f_list_in.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = f_list_in.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = f_list_in.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = f_list_in.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < f_list_out.getModel().getSize();i++){
                    Object obj = f_list_out.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            f_list_in.setModel(lm_todos);
            f_list_out.setModel(lm_escolhidos); 
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum consultor!");
    }
    
    private boolean pertence_filtro(TarefaHoras t,TreeMap<String,Projecto> projectos, TreeMap<String,Cliente> clientes){
    	boolean res = false;
    	String id_projecto = t.get_id_projecto();
    	if (projectos.containsKey(id_projecto)){
    		res = true;
    		Projecto proj = projectos.get(id_projecto);
    		String cliente = proj.get_cliente().get_nome();
    		if (!clientes.containsKey(cliente))
    			res = false;
    	}
    	return res;
    }
    
    private boolean pertence_filtro(Despesa_new d,TreeMap<String,Projecto> projectos, TreeMap<String,Cliente> clientes){
    	boolean res = false;
    	String id_projecto = d.get_id_projecto();
    	if (projectos.containsKey(id_projecto)){
    		res = true;
    		Projecto proj = projectos.get(id_projecto);
    		String cliente = proj.get_cliente().get_nome();
    		if (!clientes.containsKey(cliente))
    			res = false;
    	}
    	return res;
    }
    
    @SuppressWarnings("unused")
	private javax.swing.ButtonGroup aprov_vs_disaprov;
    private javax.swing.JButton b_cancelar;
    private javax.swing.JButton b_relatorio;
    private com.toedter.calendar.JDateChooser data_fim_field;
    private com.toedter.calendar.JDateChooser data_inicio_field;
    private javax.swing.ButtonGroup horas_vs_despesas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton jr_apenas_despesas;
    private javax.swing.JRadioButton jr_apenas_horas;
    private javax.swing.JRadioButton jr_horas_despesas;
    private javax.swing.JRadioButton jr_multi;
    private javax.swing.JRadioButton jr_single;
    private javax.swing.JPanel painel_filtro;
    private javax.swing.ButtonGroup single_vs_multi_file;
    private JTabbedPane tabbedPane;
    private JPanel funcionario_panel_tab;
    private JPanel projecto_panel_tab;
    private JPanel cliente_panel_tab;
    private JPanel panel_1;
    private JPanel panel_2;
    private JScrollPane scrollPane;
    @SuppressWarnings("rawtypes")
	private JList f_list_out;
    private JScrollPane scrollPane_1;
    @SuppressWarnings("rawtypes")
    private JList f_list_in;
    private JPanel panel_3;
    private JPanel panel_4;
    private JPanel panel_5;
    private JButton p_all_left;
    private JButton p_left;
    private JButton p_right;
    private JButton p_all_right;
    private JScrollPane scrollPane_2;
    @SuppressWarnings("rawtypes")
    private JList p_list_out;
    private JScrollPane scrollPane_3;
    @SuppressWarnings("rawtypes")
    private JList p_list_in;
    private JPanel panel_6;
    private JPanel panel_7;
    private JPanel panel_8;
    private JButton c_all_left;
    private JButton c_left;
    private JButton c_right;
    private JButton c_all_right;
    private JScrollPane scrollPane_4;
    @SuppressWarnings("rawtypes")
    private JList c_list_out;
    private JScrollPane scrollPane_5;
    @SuppressWarnings("rawtypes")
    private JList c_list_in;
    private JPanel panel;
    private JButton all_right_f;
    private JButton right_f;
    private JButton left_f;
    private JButton all_left_f;
    private JCheckBox check_aprovados;
}

class Indice_Relatorio{
	int indice_linha;
	double total;
	
	public Indice_Relatorio(int linha, double tot){
		this.indice_linha = linha;
		this.total = tot;
	}
	
	public int get_linha_indice(){
		return this.indice_linha;
	}
	
	public double get_total(){
		return this.total;
	}
}

class Indice_percentagens{
	double total;
	int linha;
	
	public Indice_percentagens(int linha, double total){
		this.linha = linha;
		this.total = total;
	}
}