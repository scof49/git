package TimeNMoney;


import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;

@SuppressWarnings("serial")
public class Menu_aprova_tarefas_horas extends javax.swing.JFrame {
    Calendar cal;
    TreeMap<String,TarefaHoras> lista;
    ArrayList<TarefaHoras> tarefas_activas;
    Date d_inicio;
    Date d_fim;
    String username_activo;
    String username_admin;
    TreeMap<String,Integer> estados;
    ArrayList<Integer> dias_aprovados;
    ArrayList<Integer> dias_rejeitados;
    ArrayList<Integer> dias_em_revisao;
    ArrayList<Integer> dias_submetidos;
    ArrayList<Horas_Handle_Obj> lista_dias;
    TreeMap<String,Projecto> lista_projectos;
    Connection con;
    boolean inicializacao;
    
    Date ultimo_por_aprovar;
    Date ultimo_preenchido;
    TreeMap<String,Funcionario> funcionarios;
    
    public Menu_aprova_tarefas_horas(String user_admin, TreeMap<String,Projecto> lista_p, Connection con_rec,TreeMap<String,Funcionario> func) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.inicializacao = true;
        this.username_admin = user_admin;
        this.cal = Calendar.getInstance();
        this.lista = new TreeMap<>();
        this.tarefas_activas = new ArrayList<>();
        this.username_activo = "";
        this.estados = new TreeMap<>();
        this.con = con_rec;
        //set_lista_projectos();
        this.lista_projectos = lista_p;
        
        dias_aprovados = new ArrayList<>();
        dias_rejeitados = new ArrayList<>();
        dias_em_revisao = new ArrayList<>();
        lista_dias = new ArrayList<>();
        //carregar lista funcionarios
        this.funcionarios = func;
        carrega_combo_box_from_bd();
        //
        get_from_bd();
        init_models();
        set_data_goto();
        this.inicializacao = false;
        set_ultimos_dias();
    }
    
    @SuppressWarnings("rawtypes")
	private void initComponents() {

        pop_menu_1 = new javax.swing.JPopupMenu();
        consultar = new javax.swing.JMenuItem();
        cons_nota = new javax.swing.JMenuItem();
        aprova_dia = new javax.swing.JMenuItem();
        rejeita_dia = new javax.swing.JMenuItem();
        espera_dia = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        intLabel = new javax.swing.JLabel();
        nextWeekButton = new javax.swing.JButton();
        beforeWeekButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        botao_voltar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        combo_funcionario = new javax.swing.JComboBox();
        botao_aprovar = new javax.swing.JButton();
        botao_rejeitar = new javax.swing.JButton();
        botao_em_espera = new javax.swing.JButton();
        aprov_label = new javax.swing.JLabel();

        consultar.setText("Consultar");
        consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarActionPerformed(evt);
            }
        });
        pop_menu_1.add(consultar);

        cons_nota.setText("Consultar Nota");
        cons_nota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cons_notaActionPerformed(evt);
            }
        });
        pop_menu_1.add(cons_nota);

        aprova_dia.setText("Aprovar dia");
        aprova_dia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aprova_diaActionPerformed(evt);
            }
        });
        pop_menu_1.add(aprova_dia);

        rejeita_dia.setText("Rejeitar dia");
        rejeita_dia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejeita_diaActionPerformed(evt);
            }
        });
        pop_menu_1.add(rejeita_dia);

        espera_dia.setText("Liberar dia");
        espera_dia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                espera_diaActionPerformed(evt);
            }
        });
        pop_menu_1.add(espera_dia);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        setMinimumSize(new java.awt.Dimension(900, 700));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Seg 1", "Ter 2", "Qua 3", "Qui 4", "Sex 5", "Sab 6 ", "Dom 7"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        intLabel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        intLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        intLabel.setText("01/01/2014");

        nextWeekButton.setText(">");
        nextWeekButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextWeekButtonActionPerformed(evt);
            }
        });

        beforeWeekButton.setText("<");
        beforeWeekButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beforeWeekButtonActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Projecto", "Etapa", "Actividade", "Tarefa"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable2MouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        botao_voltar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        botao_voltar.setIcon(new ImageIcon(Menu_aprova_tarefas_horas.class.getResource("/TimeNMoney/back.png"))); // NOI18N
        botao_voltar.setText("Voltar");
        botao_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_voltarActionPerformed(evt);
            }
        });

        jLabel1.setText("Funcionário:");

        combo_funcionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_funcionarioActionPerformed(evt);
            }
        });

        botao_aprovar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        botao_aprovar.setIcon(new ImageIcon(Menu_aprova_tarefas_horas.class.getResource("/TimeNMoney/aprov_icon.png"))); // NOI18N
        botao_aprovar.setText("Aprovar Semana");
        botao_aprovar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_aprovarActionPerformed(evt);
            }
        });

        botao_rejeitar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        botao_rejeitar.setIcon(new ImageIcon(Menu_aprova_tarefas_horas.class.getResource("/TimeNMoney/disaprov_icon.png"))); // NOI18N
        botao_rejeitar.setText("Rejeitar Semana");
        botao_rejeitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_rejeitarActionPerformed(evt);
            }
        });

        botao_em_espera.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        botao_em_espera.setIcon(new ImageIcon(Menu_aprova_tarefas_horas.class.getResource("/TimeNMoney/awaitprov_icon.png"))); // NOI18N
        botao_em_espera.setText("Liberar semana");
        botao_em_espera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_em_esperaActionPerformed(evt);
            }
        });

        aprov_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        aprov_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/rejeitado-selo.png"))); // NOI18N
        aprov_label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        goto_chooser = new JDateChooser();
        goto_chooser.setDateFormatString("dd/MMM/yyyy");
        button = new JButton("Ir Para");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		goto_chooser_action();
        	}
        });
        button.setFont(new Font("Tahoma", Font.BOLD, 11));
        
        label = new JLabel();
        label.setText("Totais:");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        t0 = new JLabel();
        t0.setText("0.0");
        t0.setHorizontalAlignment(SwingConstants.CENTER);
        t0.setFont(new Font("Tahoma", Font.BOLD, 10));
        
        t1 = new JLabel();
        t1.setText("0.0");
        t1.setHorizontalAlignment(SwingConstants.CENTER);
        t1.setFont(new Font("Tahoma", Font.BOLD, 10));
        
        t2 = new JLabel();
        t2.setText("0.0");
        t2.setHorizontalAlignment(SwingConstants.CENTER);
        t2.setFont(new Font("Tahoma", Font.BOLD, 10));
        
        t3 = new JLabel();
        t3.setText("0.0");
        t3.setHorizontalAlignment(SwingConstants.CENTER);
        t3.setFont(new Font("Tahoma", Font.BOLD, 10));
        
        t4 = new JLabel();
        t4.setText("0.0");
        t4.setHorizontalAlignment(SwingConstants.CENTER);
        t4.setFont(new Font("Tahoma", Font.BOLD, 10));
        
        t5 = new JLabel();
        t5.setText("0.0");
        t5.setHorizontalAlignment(SwingConstants.CENTER);
        t5.setFont(new Font("Tahoma", Font.BOLD, 10));
        
        t6 = new JLabel();
        t6.setText("0.0");
        t6.setHorizontalAlignment(SwingConstants.CENTER);
        t6.setFont(new Font("Tahoma", Font.BOLD, 10));
        label_last_pre = new JLabel("Último dia submetido: ");
        label_last_pre.setForeground(Color.BLUE);
        label_last_pre.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblltimoDiaPor = new JLabel("Último dia aprovado: ");
        lblltimoDiaPor.setForeground(Color.BLUE);
        lblltimoDiaPor.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnIrParalt = new JButton("Ir Para Últ Submetido");
        btnIrParalt.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnIrParalt.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		goto_last_preenchido();
        	}
        });
        btnIrParalt_1 = new JButton("Ir Para Últ Aprovado");
        btnIrParalt_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnIrParalt_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		goto_last_aprovado();
        	}
        });
        label_pr = new JLabel("31/12/2014");
        label_pr.setFont(new Font("Tahoma", Font.BOLD, 11));
        
        label_ap = new JLabel("31/12/2014");
        label_ap.setFont(new Font("Tahoma", Font.BOLD, 11));
        
        JButton btnAprovarAt = new JButton();
        btnAprovarAt.setIcon(new ImageIcon(Menu_aprova_tarefas_horas.class.getResource("/TimeNMoney/aprov_icon_multi.png")));
        btnAprovarAt.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		aprovar_varios();
        	}
        });
        btnAprovarAt.setText("Aprovar até...");
        btnAprovarAt.setFont(new Font("Tahoma", Font.BOLD, 11));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(combo_funcionario, 0, 566, Short.MAX_VALUE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(goto_chooser, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(button, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(botao_aprovar)
        							.addGap(18)
        							.addComponent(botao_rejeitar)
        							.addGap(18)
        							.addComponent(botao_em_espera)
        							.addGap(18)
        							.addComponent(btnAprovarAt, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(0, 8, Short.MAX_VALUE)
        							.addComponent(beforeWeekButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(intLabel, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(nextWeekButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(aprov_label, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(botao_voltar, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE))))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(label_last_pre, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(lblltimoDiaPor, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(label_pr, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE)
        								.addComponent(label_ap, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE))
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(btnIrParalt_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        								.addComponent(btnIrParalt, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        							.addGap(18)
        							.addComponent(label, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
        						.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(t0, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(t1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(t2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(t3, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(t4, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(t5, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(t6, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
        						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(20)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(aprov_label, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        					.addComponent(botao_voltar, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
        					.addComponent(botao_aprovar)
        					.addComponent(botao_rejeitar)
        					.addComponent(botao_em_espera)
        					.addComponent(btnAprovarAt, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
        			.addGap(25)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        					.addComponent(intLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        					.addComponent(beforeWeekButton)
        					.addComponent(jLabel1)
        					.addComponent(combo_funcionario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(nextWeekButton)
        					.addComponent(button))
        				.addComponent(goto_chooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        				.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
        				.addComponent(t0, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
        				.addComponent(t1, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
        				.addComponent(t2, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
        				.addComponent(t3, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
        				.addComponent(t4, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
        				.addComponent(t5, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
        				.addComponent(t6, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
        				.addComponent(label_last_pre)
        				.addComponent(label_pr)
        				.addComponent(btnIrParalt))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblltimoDiaPor)
        				.addComponent(label_ap)
        				.addComponent(btnIrParalt_1))
        			.addGap(22))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void beforeWeekButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beforeWeekButtonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int i = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(Calendar.WEEK_OF_YEAR, i-1);
        cal.set(Calendar.YEAR, year);
        Date d1 = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 6);
        Date d2 = cal.getTime();
        set_model_table(d1,d2);
        this.d_inicio = d1;
        this.d_fim = d2;
        set_aproved_or_not();
        calc_totais();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_beforeWeekButtonActionPerformed

    private void nextWeekButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextWeekButtonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int i = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(Calendar.WEEK_OF_YEAR, i+1);
        cal.set(Calendar.YEAR, year);
        Date d1 = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 6);
        Date d2 = cal.getTime();
        set_model_table(d1,d2);
        this.d_inicio = d1;
        this.d_fim = d2;
        set_aproved_or_not();
        calc_totais();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_nextWeekButtonActionPerformed
    
    @SuppressWarnings("unchecked")
	private void carrega_combo_box_from_bd(){
        for (Funcionario f : this.funcionarios.values())
        	combo_funcionario.addItem(f.get_nome() + " ( " + f.get_username() + " ) ");
    }
    
    private void init_models(){
        cal = Calendar.getInstance();
        int i = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(Calendar.WEEK_OF_YEAR, i);
        cal.set(Calendar.YEAR, year);
        Date d1 = cal.getTime();
        this.d_inicio = d1;
        cal.add(Calendar.DAY_OF_MONTH, 6);
        Date d2 = cal.getTime();
        this.d_fim = d2;
        set_model_table(this.d_inicio,this.d_fim);
        set_aproved_or_not();
        calc_totais();
        jTable1.setEnabled(false);
        jTable2.setEnabled(false);
    }
    
    private String get_mes(int mes){
        String str;
        switch(mes){
            case 1: str="Janeiro";break;
            case 2: str="Fevereiro";break;
            case 3: str="Março";break;
            case 4: str="Abril";break;
            case 5: str="Maio";break;
            case 6: str="Junho";break;
            case 7: str="Julho";break;
            case 8: str="Agosto";break;
            case 9: str="Setembro";break;
            case 10: str="Outubro";break;
            case 11: str="Novembro";break;
            case 12: str="Dezembro";break;
            default: str="";break;
        }
        return str;
    }
    
    private void botao_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_voltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botao_voltarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void jTable2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseReleased
        if ( SwingUtilities.isLeftMouseButton(evt))
        {
//            int[] rows = jTable2.getSelectedRows();
//            jTable1.clearSelection();
//            jTable2.clearSelection();
//            for (int i : rows)
//            {
//                jTable1.addRowSelectionInterval(i, i);
//                jTable2.addRowSelectionInterval(i, i);
//            }
            Point p = evt.getPoint();
            int rowNumber = jTable2.rowAtPoint(p);
            jTable1.clearSelection();
            jTable2.clearSelection();
            jTable1.setRowSelectionInterval(rowNumber,rowNumber);
            jTable2.setRowSelectionInterval(rowNumber,rowNumber);
        }
        if ( SwingUtilities.isRightMouseButton(evt))
        {
            Point p = evt.getPoint();
            int rowNumber = jTable2.rowAtPoint(p);
            jTable1.clearSelection();
            jTable2.clearSelection();
            jTable1.setRowSelectionInterval(rowNumber,rowNumber);
            jTable2.setRowSelectionInterval(rowNumber,rowNumber);
            jTable2.setComponentPopupMenu(pop_menu_1);
            cons_nota.setVisible(false);
            aprova_dia.setVisible(false);
            rejeita_dia.setVisible(false);
            espera_dia.setVisible(false);
            pop_menu_1.show(jTable2, evt.getX(), evt.getY());  
        }     
    }//GEN-LAST:event_jTable2MouseReleased

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        
    }//GEN-LAST:event_jTable1MouseReleased

    private void combo_funcionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_funcionarioActionPerformed
        if (!this.inicializacao){
        	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        	get_from_bd();
        	init_models();
        	set_ultimos_dias();
        	this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_combo_funcionarioActionPerformed

    private void botao_aprovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_aprovarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //0 em espera
        //1 aprovado
        //2 rejeitado
        //3 alterado
        
        String username = this.username_activo;
        int situacao = 1;
        //add_to_bd_handler_horas(username, this.d_inicio, this.d_fim, situacao);
        add_semana_to_handler(username, this.d_inicio, this.d_fim, situacao);
        load_table();
        set_ultimos_dias();
        create_thread_notification(this.username_activo);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_aprovarActionPerformed

    private void botao_rejeitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_rejeitarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //0 em espera
        //1 aprovado
        //2 rejeitado
        //3 alterado
        
        String username = this.username_activo;
        int situacao = 2;
        //add_to_bd_handler_horas(username, this.d_inicio, this.d_fim, situacao);
        add_semana_to_handler(username, this.d_inicio, this.d_fim, situacao);
        load_table();
        set_ultimos_dias();
        create_thread_notification(this.username_activo);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_rejeitarActionPerformed

    private void botao_em_esperaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_em_esperaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //0 em espera
        //1 aprovado
        //2 rejeitado
        //3 alterado
        
        String username = this.username_activo;
        int situacao = 0;
        //add_to_bd_handler_horas(username, this.d_inicio, this.d_fim, situacao);
        add_semana_to_handler(username, this.d_inicio, this.d_fim, situacao);
        load_table();
        set_ultimos_dias();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_em_esperaActionPerformed

    private void add_semana_to_handler(String username, Date inicio, Date fim, int situacao){
        Calendar c_fim = Calendar.getInstance();
        c_fim.clear();
        c_fim.setTime(fim);
        Calendar c_inicio = Calendar.getInstance();
        c_inicio.clear();
        c_inicio.setTime(inicio);
        while(!c_inicio.after(c_fim))
        {
            add_to_bd_handler_horas(username, c_inicio.getTime(), situacao);
            c_inicio.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
    
    private void consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarActionPerformed
        //get tarefa seleccionada
        int row = jTable2.getSelectedRow();
        if (row>=0){
            String id = this.tarefas_activas.get(row).get_id();//jTable2.getValueAt(row, 0).toString();
            for (TarefaHoras t : this.lista.values()){
                if (t.get_id().equals(id))
                    call_menu_consultar(t);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
        }
    }//GEN-LAST:event_consultarActionPerformed

    private void jTable2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MousePressed
//        if ( SwingUtilities.isLeftMouseButton(evt))
//            {
//                int[] rows = jTable2.getSelectedRows();
//                jTable1.clearSelection();
//                jTable2.clearSelection();
//                for (int i : rows)
//                {
//                    jTable1.addRowSelectionInterval(i, i);
//                    jTable2.addRowSelectionInterval(i, i);
//                }
//                
//            }
        
       if ( SwingUtilities.isRightMouseButton(evt))
            {
                Point p = evt.getPoint();
                int rowNumber = jTable2.rowAtPoint(p);
                jTable1.clearSelection();
                jTable2.clearSelection();
                jTable1.setRowSelectionInterval(rowNumber,rowNumber);
                jTable2.setRowSelectionInterval(rowNumber,rowNumber);
                jTable2.setComponentPopupMenu(pop_menu_1);
                cons_nota.setVisible(false);
                aprova_dia.setVisible(false);
                rejeita_dia.setVisible(false);
                espera_dia.setVisible(false);
                pop_menu_1.show(jTable2, evt.getX(), evt.getY());  
            }
    }//GEN-LAST:event_jTable2MousePressed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        if ( SwingUtilities.isLeftMouseButton(evt))
            {
//                int[] rows = jTable1.getSelectedRows();
//                jTable1.clearSelection();
//                jTable2.clearSelection();
//                for (int i : rows)
//                {
//                    jTable1.addRowSelectionInterval(i, i);
//                    jTable2.addRowSelectionInterval(i, i);
//                }
//                
                Point p = evt.getPoint();
                int rowNumber = jTable1.rowAtPoint(p);
                int colNumber = jTable1.columnAtPoint(p);
                jTable1.clearSelection();
                jTable2.clearSelection();
                jTable1.setRowSelectionInterval(rowNumber,rowNumber);
                jTable2.setRowSelectionInterval(rowNumber,rowNumber);
                jTable1.setColumnSelectionInterval(colNumber, colNumber);
            }
        if ( SwingUtilities.isRightMouseButton(evt))
            {
                Point p = evt.getPoint();
                int rowNumber = jTable1.rowAtPoint(p);
                int colNumber = jTable1.columnAtPoint(p);
                jTable1.clearSelection();
                jTable2.clearSelection();
                jTable1.setRowSelectionInterval(rowNumber,rowNumber);
                jTable2.setRowSelectionInterval(rowNumber,rowNumber);
                jTable1.setColumnSelectionInterval(colNumber, colNumber);
                jTable1.setComponentPopupMenu(pop_menu_1);
                cons_nota.setVisible(existe_nota(rowNumber,colNumber));
                aprova_dia.setVisible(true);
                rejeita_dia.setVisible(true);
                espera_dia.setVisible(true);
                pop_menu_1.show(jTable1, evt.getX(), evt.getY());

            }
    }//GEN-LAST:event_jTable1MousePressed

    private boolean existe_nota(int row, int col){
        String id_tarefa = this.tarefas_activas.get(row).get_id();//jTable2.getValueAt(row, 0).toString();
        Calendar c_aux = Calendar.getInstance();
        c_aux.clear();
        c_aux.setTime(d_inicio);
        c_aux.add(Calendar.DAY_OF_MONTH, col);
        java.sql.Date d_aux = new java.sql.Date(c_aux.getTime().getTime());
        try{
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from tnm_notas_tarefas where username=? and id_tarefa = ? and data_nota = ?";
        ps=this.con.prepareStatement(sql);
        ps.setString(1, username_activo);
        ps.setString(2, id_tarefa);
        ps.setDate(3, d_aux);
        rs = ps.executeQuery();
        boolean res = rs.next();
        rs.close();
        ps.close();
        return res;
        }catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
            return false;
        }
    }
    
    private void aprova_diaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aprova_diaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Calendar c_aux = Calendar.getInstance();
        c_aux.clear();
        c_aux.setTime(d_inicio);
        int col = jTable1.getSelectedColumn();
        c_aux.add(Calendar.DAY_OF_MONTH, col);
        add_to_bd_handler_horas(this.username_activo, c_aux.getTime(), 1);
        load_table();
        set_ultimos_dias();
        create_thread_notification(this.username_activo);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_aprova_diaActionPerformed

    private void rejeita_diaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejeita_diaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Calendar c_aux = Calendar.getInstance();
        c_aux.clear();
        c_aux.setTime(d_inicio);
        int col = jTable1.getSelectedColumn();
        c_aux.add(Calendar.DAY_OF_MONTH, col);
        add_to_bd_handler_horas(this.username_activo, c_aux.getTime(), 2);
        load_table();
        set_ultimos_dias();
        create_thread_notification(this.username_activo);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_rejeita_diaActionPerformed

    private void espera_diaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_espera_diaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Calendar c_aux = Calendar.getInstance();
        c_aux.clear();
        c_aux.setTime(d_inicio);
        int col = jTable1.getSelectedColumn();
        c_aux.add(Calendar.DAY_OF_MONTH, col);
        add_to_bd_handler_horas(this.username_activo, c_aux.getTime(), 3);
        load_table();
        set_ultimos_dias();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_espera_diaActionPerformed

    private void cons_notaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cons_notaActionPerformed
        int row = jTable1.getSelectedRow();
        int col = jTable1.getSelectedColumn();
        String id_tarefa = this.tarefas_activas.get(row).get_id();//jTable2.getValueAt(row, 0).toString();
        Calendar c_aux = Calendar.getInstance();
        c_aux.clear();
        c_aux.setTime(d_inicio);
        c_aux.add(Calendar.DAY_OF_MONTH, col);
        java.sql.Date d_aux = new java.sql.Date(c_aux.getTime().getTime());
        try{
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select * from tnm_notas_tarefas where username=? and id_tarefa = ? and data_nota = ?";
        ps=this.con.prepareStatement(sql);
        ps.setString(1, username_activo);
        ps.setString(2, id_tarefa);
        ps.setDate(3, d_aux);
        rs = ps.executeQuery();
        if (rs.next())
            JOptionPane.showMessageDialog(null, rs.getString("NOTA"));
        rs.close();
        ps.close();
        }catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
    }//GEN-LAST:event_cons_notaActionPerformed

     private void call_menu_consultar(TarefaHoras t){
        Consultar_tarefa_time at = new Consultar_tarefa_time(this.cal,t,this.username_activo);
        at.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {}
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });
        at.setVisible(true);
    }
    
    private String[] set_label(){
        int i = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(Calendar.WEEK_OF_YEAR, i);
        cal.set(Calendar.YEAR, year);
        int dia1 = cal.get(Calendar.DAY_OF_MONTH);
        int mes1 = cal.get(Calendar.MONTH)+1;
        int ano1 = cal.get(Calendar.YEAR);
        String mesS = get_mes(mes1);
        String st1 = dia1+" "+mesS+" "+ano1;
        String[] col = new String[7];
        col[0] = "Seg "+dia1;
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int dia2 = cal.get(Calendar.DAY_OF_MONTH);
        col[1] = "Ter "+dia2;
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int dia3 = cal.get(Calendar.DAY_OF_MONTH);
        col[2] = "Qua "+dia3;
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int dia4 = cal.get(Calendar.DAY_OF_MONTH);
        col[3] = "Qui "+dia4;
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int dia5 = cal.get(Calendar.DAY_OF_MONTH);
        col[4] = "Sex "+dia5;
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int dia6 = cal.get(Calendar.DAY_OF_MONTH);
        col[5] = "Sáb "+dia6;
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int dial = cal.get(Calendar.DAY_OF_MONTH);
        int mesl = cal.get(Calendar.MONTH)+1;
        int anol = cal.get(Calendar.YEAR);
        mesS = get_mes(mesl);
        String stl = dial+" "+mesS+" "+anol;
        col[6] = "Dom "+dial;
        intLabel.setText(st1 + " - " + stl);
        return col;
    }
   
    private void get_from_bd(){
    	TreeMap<String,TarefaHoras> aux = new TreeMap<>();
        String res = combo_funcionario.getSelectedItem().toString();
        String[] aux_user = res.split("\\(");
        String username = aux_user[1].replace(" ", "");
        username = username.replace(")", "");
        this.username_activo = username;
        try{
            PreparedStatement ps;
            ResultSet rs = null;
            String sql = "select * from tnm_tarefas where username='"+ username +"'";
            ps=this.con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                ByteArrayInputStream bais;
                ObjectInputStream ois;
                try{
                    bais = new ByteArrayInputStream(rs.getBytes("TAREFA"));
                    ois = new ObjectInputStream(bais);
                    TarefaHoras t = (TarefaHoras)ois.readObject();
                    aux.put(t.get_id(), t);
                }
                catch(HeadlessException | IOException | SQLException e){
                    e.printStackTrace();
                    this.setCursor(Cursor.getDefaultCursor());
                    new Log_erros_class().write_log_to_file(this.username_admin,e);
                }
            }
            rs.close();
            ps.close();
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        this.lista = aux;
    }
    
//    private void set_model_table(Date inicio, Date fim){
//        ArrayList<TarefaHoras> lista2 = new ArrayList<>();
//        for (TarefaHoras t : this.lista){
//            for (Date d : t.get_map().keySet())
//                if (!d.before(inicio) && !d.after(fim))
//                    if (!exists(t,lista2))
//                        lista2.add(t);
//        }
//        this.tarefas_activas = lista2;
//        //String columnNamesTarefas[] = {"ID","Projecto","Etapa","Actividade","Tarefa","Local"};
//        String columnNamesTarefas[] = {"Cliente","Projecto","Etapa","Actividade","Tarefa","Local"};
//        jTable2.setModel(new ModeloTabelaTarefas_aprov(columnNamesTarefas, lista2,this.lista_projectos));
//        jTable2.getTableHeader().setReorderingAllowed(false);
//        String columnNamesHoras[] = set_label();
//        double[][] data = place_data(inicio,fim,lista2);
//        jTable1.setModel(new ModeloTabela_aprov(columnNamesHoras,data));
//        jTable1.getTableHeader().setReorderingAllowed(false);
//        
//    }
    
    private void set_model_table(Date inicio, Date fim){
    	ArrayList<TarefaHoras> lista2 = new ArrayList<>();
        for (TarefaHoras t : this.lista.values()){
            for (Date d : t.get_map().keySet())
                if (!d.before(inicio) && !d.after(fim))
                    if (!exists(t,lista2))
                        lista2.add(t);
        }
        this.tarefas_activas = lista2;
        clean_map_empty_hours(this.username_activo,inicio,fim);
        //String columnNamesTarefas[] = {"ID","Projecto","Etapa","Actividade","Tarefa","Local"};
        String columnNamesTarefas[] = {"Cliente","Projecto","Etapa","Actividade","Tarefa","Local"};
        jTable2.setModel(new ModeloTabelaTarefas_aprov(columnNamesTarefas, this.tarefas_activas,this.lista_projectos));
        jTable2.getTableHeader().setReorderingAllowed(false);
        String columnNamesHoras[] = set_label();
//        double[][] data = place_data(inicio,fim,lista2);
        double[][] data = place_data(inicio,fim,this.tarefas_activas);
        jTable1.setModel(new ModeloTabela_aprov(columnNamesHoras,data));
        jTable1.getTableHeader().setReorderingAllowed(false);
        
    }
    
    private double[][] place_data(Date inicio, Date fim, ArrayList<TarefaHoras> list){
        int i = 0;
        double[][] dados = new double[list.size()][7];
        for (TarefaHoras t : list){
            Date d = inicio;
            int j=0;
            double[] dad = new double[7];
            while (!d.after(fim)){
                if (t.get_map().get(d)!=null){
                    dad[j] = t.get_map().get(d);
                }
                j++;
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.DAY_OF_MONTH, 1);
                d = c.getTime();
            }
            dados[i]=dad;
            i++;    
        }
        return dados;
    }
    
    private boolean exists(TarefaHoras t, ArrayList<TarefaHoras> lista){
        for (TarefaHoras ts : lista)
            if (t.get_id().equals(ts.get_id()) && t.get_id_projecto().equals(ts.get_id_projecto()) && t.get_etapa().equals(ts.get_etapa()) && t.get_actividade().equals(ts.get_actividade()) && t.get_tarefa().equals(ts.get_tarefa()))
                return true;
        return false;
    }
    
    private void set_aproved_or_not(){
        int ap = get_aprov_or_not(this.username_activo, this.d_inicio,this.d_fim);    
        if (ap==1){
            ImageIcon fillingIcon = new ImageIcon(getClass().getResource("/TimeNMoney/aprovado_selo.png"));
            aprov_label.setIcon(fillingIcon);
        }
        else if (ap==2){
            ImageIcon fillingIcon = new ImageIcon(getClass().getResource("/TimeNMoney/rejeitado-selo.png"));
            aprov_label.setIcon(fillingIcon);
        }
        else if (ap==3){
            ImageIcon fillingIcon = new ImageIcon(getClass().getResource("/TimeNMoney/alterado_selo.png"));
            aprov_label.setIcon(fillingIcon);
        }
        else{
            aprov_label.setIcon(null);
        }
        int n = jTable1.getColumnCount();
        int m = jTable2.getColumnCount();
        int i =0;
        if (ap!=0){
            while (i<n){
                jTable1.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer(ap));
                i++;
            }
            i = 0;
            while (i<m){
                jTable2.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer(ap));
                i++;
            }
        }
        else{
            while (i<n){
                int aux_ap;
                if (dias_aprovados.contains(i))
                    aux_ap = 1;
                else if (dias_rejeitados.contains(i))
                    aux_ap = 2;
                else if (dias_em_revisao.contains(i))
                    aux_ap = 3;
                else if (dias_submetidos.contains(i))
                	aux_ap = 9;
                else 
                    aux_ap = 0;
                jTable1.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer(aux_ap));
                i++;
            }
        }
    }
    
	private void clean_map_empty_hours(String username, Date inicio, Date fim){
    	ArrayList<TarefaHoras> a_ficar = new ArrayList<TarefaHoras>();
    	try{
    		java.sql.Date d1 = new java.sql.Date(inicio.getTime());
    		java.sql.Date d2 = new java.sql.Date(fim.getTime());
          lista_dias = new ArrayList<>();
          PreparedStatement ps;
          ResultSet rs;
          String sql = "select * from tnm_handle_horas where username='"+ username +"' and data between '"+ d1 + "' and '" + d2 + "'";
          ps=this.con.prepareStatement(sql);
          rs = ps.executeQuery();
          while (rs.next()){
              Date data = rs.getDate("DATA");
              int res = rs.getInt("SITUACAO");
              Horas_Handle_Obj novo = new Horas_Handle_Obj();
              novo.set_username(username);
              novo.set_data(data);
              novo.set_estado(res);
              lista_dias.add(novo);
          }
          rs.close();
          ps.close();
	  }
	  catch(SQLException e)
	  {
	      e.printStackTrace();
	      this.setCursor(Cursor.getDefaultCursor());
	      new Log_erros_class().write_log_to_file(this.username_admin,e);
	  }
    	
    	ArrayList<Date> dias_submetidos = get_dias_submetidos();
    	for (TarefaHoras t : this.tarefas_activas){
    		HashMap<Date,Double> new_map = new HashMap<Date,Double>();
    		for (Date d : t.get_map().keySet())
    			if (dias_submetidos.contains(d) )//&& t.get_map().get(d) != 0)
    				new_map.put(d, t.get_map().get(d));
    		if (new_map.size() > 0)
    		{
    			TarefaHoras aux = clone_tarefa(t);
    			aux.set_map(new_map);
    			a_ficar.add(aux);
    		}
    	}
    	this.tarefas_activas = a_ficar;
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
	
    private ArrayList<Date> get_dias_submetidos(){
    	ArrayList<Date> lista = new ArrayList<Date>();
    	for (Horas_Handle_Obj hho : this.lista_dias)
    		if (hho.get_estado()>0)
    			lista.add(hho.get_data());
    	return lista;
    }
    
    private int get_aprov_or_not(String username, Date inicio, Date fim){
        this.dias_aprovados = new ArrayList<>();
        this.dias_rejeitados = new ArrayList<>();
        this.dias_em_revisao = new ArrayList<>();
        this.dias_submetidos = new ArrayList<>();
        ArrayList<Integer> lista_estados = new ArrayList<>();
        int tam = 0;
        int tam_apro = 0;
        int tam_rej = 0;
        int tam_espera = 0;
        int tam_bloqueado = 0;
        for (Horas_Handle_Obj h : this.lista_dias)
            if (h.get_username().equals(username) && (!h.get_data().before(inicio) && !h.get_data().after(fim)))
            {
                long d1_aux = inicio.getTime();
                long d2_aux = h.get_data().getTime();
                long days = (d2_aux - d1_aux) / 86400000;
                int estado = h.get_estado();
                lista_estados.add(estado);
                if (estado==1)
                    this.dias_aprovados.add((int)days);
                else if (estado ==2)
                    this.dias_rejeitados.add((int)days);
                else if (estado == 3)
                    this.dias_em_revisao.add((int)days);
                else if (estado == 9)
                	this.dias_submetidos.add((int)days);
            }
        for (Integer i : lista_estados)
        {
            if (i==1)
                tam_apro++;
            else if (i==2)
                tam_rej++;
            else if (i==9)
            	tam_bloqueado++;
            else
                tam_espera++;
            tam++;
        }
        if ((tam == 7) && (tam==tam_apro))
            return 1;
        else if ((tam == 7) && (tam==tam_rej))
            return 2;
        else if ((tam == 7) && (tam==tam_espera))
            return 3;
        else if ((tam == 7) && (tam==tam_bloqueado))
        	return 9;
        return 0;
    }
    
    private void load_table(){
        set_model_table(d_inicio, d_fim);
        set_aproved_or_not();
    }
    
    @SuppressWarnings("resource")
	private void add_to_bd_handler_horas(String username, Date data, int aprov){
        java.sql.Date d1 = new java.sql.Date(data.getTime());
        try {
            PreparedStatement ps;
            String sql;
            ResultSet rs;
            sql = "select * from tnm_handle_horas where username='"+ username +"' and data = '" + d1 + "'";
            ps=this.con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next()){
                sql="insert into tnm_handle_horas (username,data,situacao) values(?,?,?)";
                ps=this.con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setDate(2, d1);
                ps.setInt(3, aprov);
                ps.executeUpdate();
            }
            else
            {
                sql="update tnm_handle_horas set situacao=? where username=? and data =?";
                ps=this.con.prepareStatement(sql);
                ps.setInt(1, aprov);
                ps.setString(2, username);
                ps.setDate(3, d1);
                ps.executeUpdate();
            }
          rs.close();
          ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
    }
    
    private void goto_chooser_action(){
    	Date d = goto_chooser.getDate();
    	if (d==null){
    		JOptionPane.showMessageDialog(null, "Escolha uma data, data vazia ou formato de data inválido!");
    	}
    	else{
    		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    		Calendar aux = new GregorianCalendar();
    		aux.setTime(d);
    		cal.clear();
            cal.set(Calendar.WEEK_OF_YEAR, aux.get(Calendar.WEEK_OF_YEAR));
            cal.set(Calendar.YEAR, aux.get(Calendar.YEAR));
            Date d1 = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, 6);
            Date d2 = cal.getTime();
            set_model_table(d1,d2);
            this.d_inicio = d1;
            this.d_fim = d2;
            set_aproved_or_not();
            calc_totais();
            this.setCursor(Cursor.getDefaultCursor());
    	}
    }
    
    private void set_data_goto(){
    	Calendar c = Calendar.getInstance();
    	Date d = c.getTime();
    	goto_chooser.setDate(d);
    }
    
    private void calc_totais(){
        int i=0;
        for (i=0;i<7;i++)
        {
            double tot = 0.0;
            try{
                for (int j=0; j<jTable1.getRowCount();j++){
                    tot += Double.valueOf(jTable1.getValueAt(j, i).toString());
                }
            }
            catch(NumberFormatException e){
            	this.setCursor(Cursor.getDefaultCursor());
            	new Log_erros_class().write_log_to_file(this.username_admin,e);
                tot = 0.0;
            }
            switch(i){
                case 0: {
                    t0.setText(String.valueOf(tot));
                    break;
                }
                case 1: {
                    t1.setText(String.valueOf(tot));
                    break;
                }
                case 2: {
                    t2.setText(String.valueOf(tot));
                    break;
                }
                case 3: {
                    t3.setText(String.valueOf(tot));
                    break;
                }
                case 4: {
                    t4.setText(String.valueOf(tot));
                    break;
                }
                case 5: {
                    t5.setText(String.valueOf(tot));
                    break;
                }
                case 6: {
                    t6.setText(String.valueOf(tot));
                    break;
                }
            }
        }
    }
    
    private void set_ultimos_dias(){
    	//ultimo dia por aprovar
//    	TreeMap<Date,Integer> lista_hand = get_lista_handler_horas();
//		this.ultimo_por_aprovar = horas_adicionadas_not_aprov(lista_hand);
		this.ultimo_por_aprovar = horas_adicionadas_not_aprov();
		label_ap.setText(print_hora(this.ultimo_por_aprovar));
		
    	//ultimo dia com horas inseridas
		this.ultimo_preenchido = ultima_hora_adicionada();
		label_pr.setText(print_hora(this.ultimo_preenchido));
    }
    
    private String print_hora(Date d){
    	if (d!=null)
    	{
    	Calendar c = new GregorianCalendar();
    	c.clear();
    	c.setTime(d);
    	int dia = c.get(Calendar.DAY_OF_MONTH);
    	String s_dia = "";
    	if (dia<10)
    		s_dia = "0" + dia;
    	else
    		s_dia = String.valueOf(dia);
    	int mes = c.get(Calendar.MONTH);
    	mes++;
    	String s_mes = "";
    	if (mes<10)
    		s_mes = "0" + mes;
    	else
    		s_mes = String.valueOf(mes);
    	int ano = c.get(Calendar.YEAR);
    	return s_dia + "/" + s_mes + "/" + ano; 
    	}
    	else
    		return print_hora(Calendar.getInstance().getTime());
    }
    
//    private Date ultima_hora_adicionada(){
//    	Calendar aux = Calendar.getInstance();
//    	aux.add(Calendar.YEAR, -5);
//    	Date last_date = aux.getTime();
//    	for (TarefaHoras t : this.lista.values())
//    	{
//    		for (Date d : t.get_map().keySet())
//    			if (d.after(last_date) && t.get_map().get(d)>0)
//    				last_date = d;
//    	}
//    	if (last_date.equals(aux.getTime()))
//    		last_date = Calendar.getInstance().getTime();
//    	return last_date;
//    }
    
    private Date ultima_hora_adicionada(){
    	try{
    		String sql = "select * from tnm_handle_horas where username = ? and situacao = ? order by data desc";
    		PreparedStatement ps = this.con.prepareStatement(sql);
    		ps.setString(1, this.username_activo);
    		ps.setInt(2, 9);
    		ResultSet rs = ps.executeQuery();
    		Date d = this.ultimo_por_aprovar;
    		while(rs.next()){
    			java.sql.Date aux = rs.getDate("data");
    			Date aux2 = new Date(aux.getTime());
    			if (d == null)
    				d = aux2;
    			else
    				if (aux2.after(d))
    					d = aux2;
    		}
    		return d;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return this.ultimo_por_aprovar;
    	}
    }
    
//    private Date horas_adicionadas_not_aprov(TreeMap<Date,Integer> lista_hand){
//    	Date min = (Calendar.getInstance().getTime());
//    	for (TarefaHoras t : this.lista.values())
//    	{
//    		for (Date d : t.get_map().keySet())
//    		{
//    			if (d.before(min))// && ((lista_hand.containsKey(d) && lista_hand.get(d) != 1)) || !lista_hand.containsKey(d))
//    				if (!lista_hand.containsKey(d) || (lista_hand.get(d) != 1 ))
//    					min = d;
//    		}
//    	}    	
//    	return min;
//    }
    
    private Date horas_adicionadas_not_aprov(){
    	try{
    		String sql = "select * from tnm_handle_horas where username = ? order by data desc";
    		PreparedStatement ps = this.con.prepareStatement(sql);
    		ps.setString(1, this.username_activo);
    		ResultSet rs = ps.executeQuery();
    		Date data_apr = null;
    		Date last_sub = null;
    		while(rs.next() && data_apr==null){
    			java.sql.Date aux = rs.getDate("data");
    			Date data_rec = new Date(aux.getTime());
    			int status = rs.getInt("situacao");
    			if (status==1)
    				data_apr = data_rec;
    			else
    				last_sub = data_rec;
    		}
    		if (data_apr!=null)
    			return data_apr;
    		else
    			return last_sub;
    	}
    	catch(Exception e){
    		e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
    		return Calendar.getInstance().getTime();
    	}
    }
    
    private void goto_last_preenchido(){
    	if (this.ultimo_preenchido!=null)
    	{
    		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			Calendar aux = new GregorianCalendar();
			aux.setTime(this.ultimo_preenchido);
			cal.clear();
	        cal.set(Calendar.WEEK_OF_YEAR, aux.get(Calendar.WEEK_OF_YEAR));
	        cal.set(Calendar.YEAR, aux.get(Calendar.YEAR));
	        Date d1 = cal.getTime();
	        cal.add(Calendar.DAY_OF_MONTH, 6);
	        Date d2 = cal.getTime();
	        set_model_table(d1,d2);
	        this.d_inicio = d1;
	        this.d_fim = d2;
	        set_aproved_or_not();
	        calc_totais();
	        this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void goto_last_aprovado(){
    	if (this.ultimo_por_aprovar!=null)
    	{
    		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			Calendar aux = new GregorianCalendar();
			aux.setTime(this.ultimo_por_aprovar);
			cal.clear();
	        cal.set(Calendar.WEEK_OF_YEAR, aux.get(Calendar.WEEK_OF_YEAR));
	        cal.set(Calendar.YEAR, aux.get(Calendar.YEAR));
	        Date d1 = cal.getTime();
	        cal.add(Calendar.DAY_OF_MONTH, 6);
	        Date d2 = cal.getTime();
	        set_model_table(d1,d2);
	        this.d_inicio = d1;
	        this.d_fim = d2;
	        set_aproved_or_not();
	        calc_totais();
	        this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void aprovar_varios(){
        //1 aprovado
        
        String username = this.username_activo;
        int situacao = 1;
        Date data_i;
        if (this.ultimo_por_aprovar!=null)
        	data_i = this.ultimo_por_aprovar;
        else
        	data_i = new Date();
        
        Aprovar_multiplas_datas amd = new Aprovar_multiplas_datas(username, data_i, situacao,this.con);
        amd.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {}
			
			@Override
			public void windowClosed(WindowEvent e) {
				load_table();
				set_ultimos_dias();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});
        amd.setVisible(true);
    }
    
    @SuppressWarnings("resource")
	private void begin_notification_thread(String username){
    	try {
			 Calendar c = Calendar.getInstance();
	         int dia = c.get(Calendar.DAY_OF_MONTH);
	         int mes = c.get(Calendar.MONTH);
	         int ano = c.get(Calendar.YEAR);
	         c.clear();
	         c.set(Calendar.DAY_OF_MONTH,dia);
	         c.set(Calendar.MONTH,mes);
	         c.set(Calendar.YEAR,ano);
	         Date hoje = c.getTime();
	         
            PreparedStatement ps;
            String sql;
            ResultSet rs;
            sql = "select * from tnm_handle_notif where username='"+ username + "'";
            ps=this.con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next())
            {
            	Date aux = rs.getDate("DATA");
            	if (!aux.equals(hoje)){
            		SendMailTLS smt = new SendMailTLS();
                	smt.send_mail_notificacao(username);
                	java.sql.Date d_hoje = new java.sql.Date(hoje.getTime());
                	sql="update tnm_handle_notif set data=? where username=?";
                	ps=this.con.prepareStatement(sql);
                	ps.setDate(1, d_hoje);
                	ps.setString(2, username);
                	ps.executeUpdate();
            	}
            }
            else
            {
            	SendMailTLS smt = new SendMailTLS();
            	smt.send_mail_notificacao(username);
            	
            	java.sql.Date d_hoje = new java.sql.Date(hoje.getTime());
            	sql="insert into tnm_handle_notif (username,data) values(?,?)";
                ps=this.con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setDate(2, d_hoje);
                ps.executeUpdate();
            }
          rs.close();
          ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
    }
    

	private void create_thread_notification(String username){
    	Runnable r = new Runnable() {
			public void run() {
				begin_notification_thread(username);
			}
		};
		new Thread(r).start();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aprov_label;
    private javax.swing.JMenuItem aprova_dia;
    private javax.swing.JButton beforeWeekButton;
    private javax.swing.JButton botao_aprovar;
    private javax.swing.JButton botao_em_espera;
    private javax.swing.JButton botao_rejeitar;
    private javax.swing.JButton botao_voltar;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox combo_funcionario;
    private javax.swing.JMenuItem cons_nota;
    private javax.swing.JMenuItem consultar;
    private javax.swing.JMenuItem espera_dia;
    private javax.swing.JLabel intLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton nextWeekButton;
    private javax.swing.JPopupMenu pop_menu_1;
    private javax.swing.JMenuItem rejeita_dia;
    private JDateChooser goto_chooser;
    private JButton button;
    private JLabel label;
    private JLabel t0;
    private JLabel t1;
    private JLabel t2;
    private JLabel t3;
    private JLabel t4;
    private JLabel t5;
    private JLabel t6;
    private JLabel label_last_pre;
    private JLabel lblltimoDiaPor;
    private JButton btnIrParalt;
    private JButton btnIrParalt_1;
    private JLabel label_pr;
    private JLabel label_ap;
}
