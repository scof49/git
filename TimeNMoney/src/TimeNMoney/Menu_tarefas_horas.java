package TimeNMoney;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.text.JTextComponent;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.toedter.calendar.JDateChooser;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

@SuppressWarnings("serial")
public class Menu_tarefas_horas extends javax.swing.JFrame {
    Calendar cal;
    TreeMap<String,TarefaHoras> lista;
    ArrayList<Horas_Handle_Obj> modificacoes_handler;
    ArrayList<TarefaHoras> tarefas_activas;
    String username;
    Date d_inicio;
    Date d_fim;
    boolean save;
    boolean semana_rejeitada;
    boolean bloqueado;
    Data_manager dm;
    int row_nota;
    int col_nota;
    ArrayList<Integer> dias_aprovados;
    ArrayList<Integer> dias_rejeitados;
    ArrayList<Integer> dias_em_revisao;
    ArrayList<Integer> dias_bloqueados;
    boolean edit_rejected_row;
    ExcelAdapter_tabela_horas excel_adapter_tabela_horas;
    ExcelAdapter_tabela_desc excel_adapter_descritivo_horas;
    Point ponto_click_rato;
    int tabela_seleccionada;
    boolean tab_flag;
    
    public Menu_tarefas_horas(String user, Data_manager dm) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.cal = Calendar.getInstance();
        this.lista = new TreeMap<>();//getTarefas();
        this.modificacoes_handler = new ArrayList<>();
        this.tarefas_activas = new ArrayList<>();
        this.username = user;
        this.save = false;
        this.semana_rejeitada = false;
        this.bloqueado = false;
        edit_rejected_row = false;
        this.dm = dm;
        row_nota = 0;
        col_nota = 0;
        this.tab_flag = false;
        dias_aprovados = new ArrayList<>();
        dias_rejeitados = new ArrayList<>();
        dias_em_revisao = new ArrayList<>();
        
        set_data_goto();
        
        //get_from_bd();
        get_from_dm();
        init_models();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                JFrame frame = (JFrame)e.getSource();
                //save_if_not_saved();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
            }
        });
    }

    private void change_save_variable(){
        this.save = true;
    }
    
    private void set_semana_to_alterado(){
    	Calendar c = new GregorianCalendar();
    	c.setTime(this.d_inicio);
    	Date d_aux = c.getTime();
    	while (!this.d_fim.before(d_aux) && !this.d_inicio.after(d_aux))
    	{
    		set_dia_to_alterado(d_aux);
    		c.add(Calendar.DAY_OF_MONTH, 1);
    		d_aux = c.getTime();
    	}
    	set_aproved_or_not(); 
    }
    
    private void set_dia_to_alterado(Date dia){
    	add_to_dm_handler_horas(username, dia, 3);
    	this.edit_rejected_row = true;
//    	set_aproved_or_not(); 
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pop_menu_1 = new javax.swing.JPopupMenu();
        editar_pop_menu = new javax.swing.JMenuItem();
        eliminar_pop_menu = new javax.swing.JMenuItem();
        add_to_fav = new javax.swing.JMenuItem();
        add_to_fav2 = new javax.swing.JMenuItem();
        add_fav = new javax.swing.JMenuItem();
        add_despesa_menu_item = new javax.swing.JMenuItem();
        add_nota = new javax.swing.JMenuItem();
        copy_line_menu_item = new javax.swing.JMenuItem();
        paste_menu_item = new javax.swing.JMenuItem();
        pop_menu_2 = new javax.swing.JPopupMenu();
        consultar_item = new javax.swing.JMenuItem();
        cons_nota = new javax.swing.JMenuItem();
        jf = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        intLabel = new javax.swing.JLabel();
        nextWeekButton = new javax.swing.JButton();
        beforeWeekButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        saveBDButton = new javax.swing.JButton();
        adicionar_botao = new javax.swing.JButton();
        eliminar_botao = new javax.swing.JButton();
        editar_botao = new javax.swing.JButton();
        botao_voltar = new javax.swing.JButton();
        aprov_label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        t0 = new javax.swing.JLabel();
        t1 = new javax.swing.JLabel();
        t3 = new javax.swing.JLabel();
        t6 = new javax.swing.JLabel();
        t2 = new javax.swing.JLabel();
        t5 = new javax.swing.JLabel();
        t4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fav_tarefas = new javax.swing.JMenu();
        add_favoritos = new javax.swing.JMenuItem();
        fav_preferencias = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        editar_pop_menu.setText("Editar/Consultar");
        editar_pop_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editar_pop_menuActionPerformed(evt);
            }
        });
        pop_menu_1.add(editar_pop_menu);

        eliminar_pop_menu.setText("Eliminar");
        eliminar_pop_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminar_pop_menuActionPerformed(evt);
            }
        });
        pop_menu_1.add(eliminar_pop_menu);

        add_to_fav.setText("Adicionar tarefa aos Favoritos");
        add_to_fav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_to_favActionPerformed(evt);
            }
        });
        pop_menu_1.add(add_to_fav);
        
        add_to_fav2.setText("Adicionar tarefa aos Favoritos");
        add_to_fav2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_to_favActionPerformed(evt);
            }
        });
        pop_menu_2.add(add_to_fav2);

        add_fav.setText("Acrescentar Favoritos");
        add_fav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_favActionPerformed(evt);
            }
        });
        pop_menu_1.add(add_fav);

        add_despesa_menu_item.setText("Adicionar Despesa");
        add_despesa_menu_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_despesa_menu_itemActionPerformed(evt);
            }
        });
        pop_menu_1.add(add_despesa_menu_item);

        add_nota.setText("Adicionar Nota");
        add_nota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_notaActionPerformed(evt);
            }
        });
        pop_menu_1.add(add_nota);
        
        copy_line_menu_item.setText("Copiar Linha");
        copy_line_menu_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                set_copy();
            }
        });
        pop_menu_1.add(copy_line_menu_item);

        paste_menu_item.setText("Colar");
        paste_menu_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                set_paste();
            }
        });
        pop_menu_1.add(paste_menu_item);
        
        consultar_item.setText("Consultar");
        consultar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultar_itemActionPerformed(evt);
            }
        });
        pop_menu_2.add(consultar_item);

        cons_nota.setText("Consultar Nota");
        cons_nota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cons_notaActionPerformed(evt);
            }
        });
        pop_menu_2.add(cons_nota);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        setMinimumSize(new java.awt.Dimension(900, 500));
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
        });
        jTable1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTable1InputMethodTextChanged(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
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

        saveBDButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        saveBDButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/send_icon.png"))); // NOI18N
        saveBDButton.setText("Enviar");
        saveBDButton.setToolTipText("Enviar Dados");
        saveBDButton.setMaximumSize(new java.awt.Dimension(125, 75));
        saveBDButton.setMinimumSize(new java.awt.Dimension(125, 75));
        saveBDButton.setPreferredSize(new java.awt.Dimension(125, 75));
        saveBDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBDButtonActionPerformed(evt);
            }
        });

        adicionar_botao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        adicionar_botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/add_task_icon.png"))); // NOI18N
        adicionar_botao.setText("Adicionar");
        adicionar_botao.setToolTipText("Adicionar Nova Tarefa");
        adicionar_botao.setMaximumSize(new java.awt.Dimension(125, 75));
        adicionar_botao.setMinimumSize(new java.awt.Dimension(125, 75));
        adicionar_botao.setPreferredSize(new java.awt.Dimension(125, 75));
        adicionar_botao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionar_botaoActionPerformed(evt);
            }
        });

        eliminar_botao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        eliminar_botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/remove_task_icon.png"))); // NOI18N
        eliminar_botao.setText("Eliminar");
        eliminar_botao.setToolTipText("Eliminar Tarefa Seleccionada");
        eliminar_botao.setMaximumSize(new java.awt.Dimension(125, 75));
        eliminar_botao.setMinimumSize(new java.awt.Dimension(125, 75));
        eliminar_botao.setPreferredSize(new java.awt.Dimension(125, 75));
        eliminar_botao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminar_botaoActionPerformed(evt);
            }
        });

        editar_botao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        editar_botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/edit_task_icon.png"))); // NOI18N
        editar_botao.setText("Editar");
        editar_botao.setToolTipText("Editar Tarefa Seleccionada");
        editar_botao.setMaximumSize(new java.awt.Dimension(125, 75));
        editar_botao.setMinimumSize(new java.awt.Dimension(125, 75));
        editar_botao.setPreferredSize(new java.awt.Dimension(125, 75));
        editar_botao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editar_botaoActionPerformed(evt);
            }
        });

        botao_voltar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        botao_voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_back.png"))); // NOI18N
        botao_voltar.setText("Voltar");
        botao_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_voltarActionPerformed(evt);
            }
        });

        aprov_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/task_icon.png"))); // NOI18N
        jLabel1.setText("Menu de Carga Horária");

        t0.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        t0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        t0.setText("0.0");

        t1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        t1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        t1.setText("0.0");
        t1.setToolTipText("");

        t3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        t3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        t3.setText("0.0");

        t6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        t6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        t6.setText("0.0");

        t2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        t2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        t2.setText("0.0");

        t5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        t5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        t5.setText("0.0");

        t4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        t4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        t4.setText("0.0");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Totais:");

        fav_tarefas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/fav_icon.png"))); // NOI18N
        fav_tarefas.setText("Favoritos");

        add_favoritos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/add_fav.png"))); // NOI18N
        add_favoritos.setText("Adicionar Favoritos");
        add_favoritos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_favoritosActionPerformed(evt);
            }
        });
        fav_tarefas.add(add_favoritos);

        fav_preferencias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/opt_fav.png"))); // NOI18N
        fav_preferencias.setText("Preferências");
        fav_preferencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fav_preferenciasActionPerformed(evt);
            }
        });
        fav_tarefas.add(fav_preferencias);

        jMenuBar1.add(fav_tarefas);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/export_excel_mini.png"))); // NOI18N
        jMenu1.setText("Importar/Exportar");

        jMenuItem1.setText("Exportar excel exemplo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Importar excel tarefas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);
        
        mntmExportarDados = new JMenuItem("Exportar dados");
        mntmExportarDados.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		exportar_dados_action();
        	}
        });
        jMenu1.add(mntmExportarDados);

        setJMenuBar(jMenuBar1);
        goto_chooser = new JDateChooser();
        goto_chooser.setDateFormatString("dd/MMM/yyyy");
        btnIrPara = new JButton("Ir Para");
        btnIrPara.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		goto_chooser_action();
        	}
        });
        btnIrPara.setFont(new Font("Tahoma", Font.BOLD, 11));
        button = new JButton("Ir Para Últ Preenchido");
        button.setFont(new Font("Tahoma", Font.BOLD, 11));
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		goto_last_preenchido();
        	}
        });
        smart_clean_button = new JButton("Apagar dias a zeros");
        smart_clean_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		smart_clear_action();
        	}
        });
        smart_clean_button.setFont(new Font("Tahoma", Font.BOLD, 11));
        botao_enviar_parte = new JButton("Enviar parte");
        botao_enviar_parte.setFont(new Font("Tahoma", Font.BOLD, 11));
        botao_enviar_parte.setIcon(new ImageIcon(Menu_tarefas_horas.class.getResource("/TimeNMoney/send_icon.png")));
        botao_enviar_parte.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
//        		Date d_fim = Calendar.getInstance().getTime();
//        		enviar_parte_action(d_fim);
        		final Send_partial_message spm = new Send_partial_message();
        		spm.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent we) { }
                    @Override
                    public void windowClosing(WindowEvent we) { }
                    @Override
                    public void windowClosed(WindowEvent we) { }
                    @Override
                    public void windowIconified(WindowEvent we) {}
                    @Override
                    public void windowDeiconified(WindowEvent we) {}
                    @Override
                    public void windowActivated(WindowEvent we) {}
                    @Override
                    public void windowDeactivated(WindowEvent we) {
                    	Date d_fim = spm.get_data_resposta();
                    	int res = spm.get_resposta();
                    	if (res==1){
                    		enviar_parte_action(d_fim);
                    	}
                    }
	            });
	            spm.setVisible(true);
        	}
        });
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel1)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(adicionar_botao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(editar_botao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(eliminar_botao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(aprov_label, GroupLayout.PREFERRED_SIZE, 356, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
        					.addComponent(botao_enviar_parte)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(saveBDButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_voltar, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(goto_chooser, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(btnIrPara))
        						.addComponent(jScrollPane2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 785, Short.MAX_VALUE)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(button, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(smart_clean_button, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
        							.addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(beforeWeekButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        							.addGap(2)
        							.addComponent(intLabel, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(nextWeekButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
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
        							.addComponent(t6, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(20)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(aprov_label, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(saveBDButton, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        								.addComponent(botao_enviar_parte, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
        							.addComponent(botao_voltar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(goto_chooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(nextWeekButton)
        							.addComponent(intLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        							.addComponent(beforeWeekButton)
        							.addComponent(btnIrPara))))
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(jLabel1)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(adicionar_botao, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
        						.addComponent(editar_botao, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
        						.addComponent(eliminar_botao, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(t0)
        				.addComponent(t1)
        				.addComponent(t3)
        				.addComponent(t6)
        				.addComponent(t5)
        				.addComponent(t4)
        				.addComponent(t2)
        				.addComponent(jLabel9)
        				.addComponent(button)
        				.addComponent(smart_clean_button))
        			.addGap(13))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void beforeWeekButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beforeWeekButtonActionPerformed
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
    }//GEN-LAST:event_beforeWeekButtonActionPerformed

    
    private void nextWeekButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextWeekButtonActionPerformed
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
    }//GEN-LAST:event_nextWeekButtonActionPerformed

    private void saveBDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBDButtonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        set_send_handler();
        int res = save_button_action(true);
        if (res==0){
            this.save = false;
            JOptionPane.showMessageDialog(null, "Dados de tarefas enviados com sucesso!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Erro ao enviar dados de tarefas, tente novamente mais tarde ou contacte administrador!");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_saveBDButtonActionPerformed

    public int save_button_action(boolean backup){
    	int res = 0;
    	Backup_data_manager bdm = new Backup_data_manager(this.dm);
    	if(backup){
    		bdm.save_backup_file();
    	}
    	
    	try {
	    	Connection con = (new Connection_bd()).get_connection();
	    	if (con!=null){
	    	
		    	for (TarefaHoras t : this.lista.values()){
		            res += set_to_bd(t.get_id(), t,con);
		        }
		        for (Horas_Handle_Obj h : this.modificacoes_handler){
		            res += add_to_bd_handler_horas(h.get_username(), h.get_data(), h.get_estado(),con);
		        }
		        if (res == 0)
		        {
		            this.dm.lista_handler_horas_modificacoes = new ArrayList<>();
		        }
		        //update notas
		        res += send_notas_bd(con);
		        res += send_nova_tarefa_proj(con);
	       
				con.close();
			}
	    	else{
	    		res++;
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
			this.setCursor(Cursor.getDefaultCursor());
			new Log_erros_class().write_log_to_file(e);
        	res++;
		}
    	
    	if(backup){
    		if (res == 0)
    			bdm.delete_backup();
    		else
    			this.dm = bdm.get_file_backup();
    	}
    	
        return res;
    }
    
    private int send_nova_tarefa_proj(Connection con){
        int res = 0;
        try{
	        for (String proj : this.dm.projectos_alterados)
	        {
	            Projecto p = this.dm.lista_projectos_user.get(proj);
	            res += send_proj_bd(p,con);
	        }
	        if (res==0)
	        	this.dm.projectos_alterados = new ArrayList<String>();
	        
	        for (String tar : this.dm.tarefas_adicionadas)
	        {
	            Tarefa t = this.dm.lista_tarefa_total.get(tar);
	            if (t!=null)
	            	res+= send_tarefa_bd(t,con);
	        }
	        
	        if (res==0)
	        	this.dm.tarefas_adicionadas = new ArrayList<String>();
        }
        catch(Exception e){
        	e.printStackTrace();
        	this.setCursor(Cursor.getDefaultCursor());
        	new Log_erros_class().write_log_to_file(e);
        	res++;
        }
        return res;
    }
    
    private int send_tarefa_bd(Tarefa t,Connection con){
        int res = 0;
        try{
        String sql;
        PreparedStatement ps;
        sql = "select * from tnm_trf_tarefa where id = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1, t.get_id());
        ResultSet rs = ps.executeQuery();
        if (!rs.next())
        {
        sql="insert into tnm_trf_tarefa (id,nome,descricao) values (?,?,?)";
	        ps=con.prepareStatement(sql);
	        ps.setString(1, t.get_id());
	        ps.setString(2, t.get_nome());
	        ps.setString(3, t.get_descricao());
	        ps.executeUpdate();
	        add_user_tarefa_bd(t.get_id(),this.username,con);
        }
        rs.close();
        ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            res++;
        }

        return res;
    }
    
    private void add_user_tarefa_bd(String id, String username, Connection con) {
		try{
    	String sql = "insert into tnm_user_tarefa (id_tarefa,username) values (?,?)";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, username);
        ps.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
			this.setCursor(Cursor.getDefaultCursor());
			new Log_erros_class().write_log_to_file(e);
		}
	}
    
    private int send_proj_bd(Projecto p,Connection con){
        int res = 0;
        try{
        String sql;
        PreparedStatement ps;
        sql="select * from tnm_trf_projecto where id_projecto = ?";
        ps=con.prepareStatement(sql);
        ps.setString(1, p.get_codigo());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            ByteArrayInputStream bais;
            ObjectInputStream ois;
            try{
                bais = new ByteArrayInputStream(rs.getBytes("PROJECTO"));
                ois = new ObjectInputStream(bais);
                Projecto aux = (Projecto)ois.readObject();
                aux = add_tarefa_new_proj(aux, p);
                sql="update tnm_trf_projecto set projecto = ? where id_projecto = ?";
                ps=con.prepareStatement(sql);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(aux);
                oos.flush();
                oos.close();
                bos.close();
                byte[] data = bos.toByteArray();
                ps.setObject(1, data);
                ps.setString(2, p.get_codigo());
                ps.executeUpdate();
                ps.close();
            }
            catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(e);
                res++;
            }  
        }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            res++;
        }

        return res;
    }
    
    private Projecto add_tarefa_new_proj(Projecto bd, Projecto novo){
        Projecto retorno;
        for (String s : novo.get_tarefas().keySet())
            if (!bd.get_tarefas().containsKey(s))
                bd.get_tarefas().put(s,novo.get_tarefas().get(s));
        retorno = bd;
        return retorno;
    }
    
    private int send_notas_bd(Connection con){
        int res = 0;
        for (String id_tarefa : this.dm.notas_tarefas.keySet())
        {
            for (Date d : this.dm.notas_tarefas.get(id_tarefa).keySet())
                res += send_nota_bd(id_tarefa,d,this.dm.notas_tarefas.get(id_tarefa).get(d),con);
        }
        return res;
    }
    
    @SuppressWarnings("resource")
	private int send_nota_bd(String id_tarefa,Date data_nota, String nota,Connection con){
        int res = 0;
        try{
        String sql;
        PreparedStatement ps;
        sql="select * from tnm_notas_tarefas where username = ? and id_tarefa = ? and data_nota = ?";
        ps=con.prepareStatement(sql);
        ps.setString(1, this.username);
        ps.setString(2, id_tarefa);
        java.sql.Date aux = new java.sql.Date(data_nota.getTime());
        ps.setDate(3, aux);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            String nota_aux = rs.getString("NOTA");
            if (!nota_aux.equals(nota)){
                sql="update tnm_notas_tarefas set nota = ? where username = ? and id_tarefa = ? and data_nota = ?";
                ps=con.prepareStatement(sql);
                ps.setString(1, nota);
                ps.setString(2, this.username);
                ps.setString(3, id_tarefa);
                ps.setDate(4, aux);
                ps.executeUpdate();
            }
        }
        else{
            sql="insert into tnm_notas_tarefas (nota,username,id_tarefa,data_nota) values (?,?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setString(1, nota);
            ps.setString(2, this.username);
            ps.setString(3, id_tarefa);
            ps.setDate(4, aux);
            ps.executeUpdate();
        }
        rs.close();
        ps.close();
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            res++;
        }
        

        return res;
    }
    
    private void init_models(){
        cal = Calendar.getInstance();
        int dia_da_semana = cal.get(Calendar.DAY_OF_WEEK);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH);
        int ano = cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(Calendar.DAY_OF_MONTH,dia);
        cal.set(Calendar.MONTH,mes);
        cal.set(Calendar.YEAR,ano);
        
        cal.add(Calendar.DAY_OF_MONTH, (-1 * (dia_da_semana - 2)));
        Date d1 = cal.getTime();
        this.d_inicio = d1;
        cal.add(Calendar.DAY_OF_MONTH, 6);
        Date d2 = cal.getTime();
        this.d_fim = d2;
        set_model_table(this.d_inicio,this.d_fim);
        set_aproved_or_not();
        calc_totais();
    }
    
//    private void init_models(){
//      cal = Calendar.getInstance();
//      int i = cal.get(Calendar.WEEK_OF_YEAR);
//      int year = cal.get(Calendar.YEAR);
//      cal.clear();
//      cal.set(Calendar.WEEK_OF_YEAR, i);
//      cal.set(Calendar.YEAR, year);
//      Date d1 = cal.getTime();
//      this.d_inicio = d1;
//      cal.add(Calendar.DAY_OF_MONTH, 6);
//      Date d2 = cal.getTime();
//      this.d_fim = d2;
//      set_model_table(this.d_inicio,this.d_fim);
//      set_aproved_or_not();
//      calc_totais();
//  }
    
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
            	new Log_erros_class().write_log_to_file(e);
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
    
    private void refresh_models(){
        set_model_table(this.d_inicio,this.d_fim);
        set_aproved_or_not();
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
    
    private void adicionar_botaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionar_botaoActionPerformed
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	TarefaHoras aux = get_most_hours_task();
        Adicionar_tarefa_time ad;
        if (aux == null)
        	ad = new Adicionar_tarefa_time(this.cal,this.username,this.dm);
        else
        	ad = new Adicionar_tarefa_time(this.cal,this.username,this.dm,aux);
        ad.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {
                    get_from_dm();
                    refresh_models();
                	set_semana_to_alterado();
                }
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });
        ad.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_adicionar_botaoActionPerformed

    private TarefaHoras get_most_hours_task(){
    	TarefaHoras t_max = null;
    	int max = 0;
    	for (TarefaHoras t : this.lista.values()){
    		int aux = get_count_map(t.get_map());
    		if (aux>max && !this.tarefas_activas.contains(t))
    		{
    			max = aux;
    			t_max = t;
    		}
    	}
    	return t_max;
    }
    
    private int get_count_map(HashMap<Date,Double> map){
    	int count = 0;
    	for (double d : map.values())
    		if (d>0.0)
    			count++;
    	return count;
    }
    
    private void eliminar_botaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminar_botaoActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int[] rows = jTable2.getSelectedRows();
        if (rows.length>0){
            int i = rows.length - 1;
            while(i>=0){
                //delete_task(jTable2.getValueAt(rows[i], 0).toString());
                delete_task(this.tarefas_activas.get(rows[i]).get_id());
                i--;
            }

        	set_semana_to_alterado();
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_eliminar_botaoActionPerformed

    private void delete_task(String id){
        TarefaHoras t = this.lista.get(id);
        
        //tarefa tem horas aprovadas ?
        boolean dias_aprov = false;
        ArrayList<Date> aprov_lista = new ArrayList<>();
        ArrayList<Date> block_lista = new ArrayList<>();
        if (t!=null)
        {
            boolean existem_apr = (this.dias_aprovados.size() > 0) || (this.dias_bloqueados.size() > 0);
            if (existem_apr)
            {
            for (int i : this.dias_aprovados)
                aprov_lista.add(get_date_col(i));
            for (int i : this.dias_bloqueados)
                block_lista.add(get_date_col(i));
            for (Date d : t.get_map().keySet())
                if (((aprov_lista.contains(d)) && (t.get_map().get(d) != 0)) || ((block_lista.contains(d)) && (t.get_map().get(d) != 0)) )
                    dias_aprov = true;
            }
            if (!dias_aprov){
                delete_task_date(t,this.d_inicio, this.d_fim);
                set_model_table(this.d_inicio,this.d_fim);
                set_aproved_or_not();
                calc_totais();
            }
            else
                JOptionPane.showMessageDialog(null, "Não é possível eliminar uma tarefa com horas adicionadas em dias aprovados ou submetidos!");
        }
        
    }
    
    private Date get_date_col(int col){
        Calendar aux = Calendar.getInstance();
        aux.clear();
        aux.setTime(this.d_inicio);
        aux.add(Calendar.DAY_OF_MONTH, col);
        return aux.getTime();
    }
    
    private void delete_task_date(TarefaHoras t, Date d1, Date d2){
        Date d = d1;
        HashMap<Date,Double> aux = t.get_map();
        while (!d.after(d2)){
            aux.remove(d);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            c.add(Calendar.DAY_OF_MONTH, 1);
            d = c.getTime();
        }
        t.set_map(aux);
    }
    
    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
    	this.tabela_seleccionada = 1;
    	set_copy_paste_itens_visible();
    	if ( SwingUtilities.isLeftMouseButton(evt))
            {
                int row = jTable1.getSelectedRow();
                int col = jTable1.getSelectedColumn();
                if (jTable1.isCellEditable(row, col))
                {
                    jTable1.editCellAt(row, col);
                
                    Component editor = jTable1.getEditorComponent();
                    editor.requestFocusInWindow();
                    ((JTextComponent)editor).selectAll();
                }
                jTable2.clearSelection();
                jTable2.addRowSelectionInterval(row,row);
                
            }
        if ( SwingUtilities.isRightMouseButton(evt))
            {
                Point p = evt.getPoint();
                ponto_click_rato = p;
                int rowNumber = jTable1.rowAtPoint(p);
                int colNumber = jTable1.columnAtPoint(p);
                this.row_nota = rowNumber;
                this.col_nota = colNumber;
                jTable1.clearSelection();
                jTable2.clearSelection();
                jTable1.setRowSelectionInterval(rowNumber,rowNumber);
                jTable2.setRowSelectionInterval(rowNumber,rowNumber);
                if (!this.bloqueado){
                    jTable1.setComponentPopupMenu(pop_menu_1);
                    add_nota.setVisible(true);
                    if (existe_nota(rowNumber,colNumber))
                        add_nota.setText("Editar Nota");
                    else
                        add_nota.setText("Adicionar Nota");
                    pop_menu_1.show(jTable1, evt.getX(), evt.getY());
                }
                else{
                    jTable1.setComponentPopupMenu(pop_menu_2);
                    pop_menu_2.show(jTable1, evt.getX(), evt.getY());
                    cons_nota.setVisible(existe_nota(rowNumber,colNumber));
                }
            }
    }//GEN-LAST:event_jTable1MousePressed

    private boolean existe_nota(int row, int col){
        String id_tarefa = this.tarefas_activas.get(row).get_id();//jTable2.getValueAt(row, 0).toString();
        Calendar c_aux = Calendar.getInstance();
        c_aux.clear();
        c_aux.setTime(d_inicio);
        c_aux.add(Calendar.DAY_OF_MONTH, col);
        Date d_aux = c_aux.getTime();
        if (this.dm.notas_tarefas.containsKey(id_tarefa))
            if (this.dm.notas_tarefas.get(id_tarefa).containsKey(d_aux))
                return true;
        return false;
    }
    
    private void editar_botaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editar_botaoActionPerformed
        //get tarefa seleccionada
        int row = jTable2.getSelectedRow();
        if (row>=0){
            String id = tarefas_activas.get(row).get_id();//jTable2.getValueAt(row, 0).toString();
            //tarefa tem horas aprovadas ?
            boolean dias_aprov = false;
            ArrayList<Date> aprov_lista = new ArrayList<>();
            ArrayList<Date> block_lista = new ArrayList<>();
            
            TarefaHoras t = this.lista.get(id);
            if (t!=null){
                boolean existem_apr = (this.dias_aprovados.size() > 0) || (this.dias_bloqueados.size()>0);
                if (existem_apr)
                {
                for (int i : this.dias_aprovados)
                    aprov_lista.add(get_date_col(i));
                for (int i : this.dias_bloqueados)
                    block_lista.add(get_date_col(i));
                for (Date d : t.get_map().keySet())
                    if ((aprov_lista.contains(d)) && (t.get_map().get(d) != 0) || (block_lista.contains(d)) && (t.get_map().get(d) != 0))
                        dias_aprov = true;
                }
                if (!dias_aprov){
                    call_menu_alterar(t);
                }
                else
                    JOptionPane.showMessageDialog(null, "Não é possível alterar uma tarefa com horas adicionadas em dias aprovados ou submetidos!");
            }

        	set_semana_to_alterado();
        }
        else{
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
        }
    }//GEN-LAST:event_editar_botaoActionPerformed

    private void call_menu_alterar(TarefaHoras t){
        Alterar_tarefa_time at = new Alterar_tarefa_time(this.cal,t,this.username,this.dm);
        at.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {
                    get_from_dm();
                    refresh_models();
                }
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
    
    private void call_menu_consultar(TarefaHoras t){
        Consultar_tarefa_time at = new Consultar_tarefa_time(this.cal,t,this.username,this.dm);
        at.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {
                    get_from_dm();
                    refresh_models();
                }
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
    
    private void botao_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_voltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botao_voltarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void editar_pop_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editar_pop_menuActionPerformed
        //get tarefa seleccionada
        int row = jTable2.getSelectedRow();
        if (row>=0){
            String id = tarefas_activas.get(row).get_id();//jTable2.getValueAt(row, 0).toString();
            TarefaHoras t = this.lista.get(id);
            if (t!=null){
                call_menu_alterar(t);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
        }
    }//GEN-LAST:event_editar_pop_menuActionPerformed

    private void eliminar_pop_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminar_pop_menuActionPerformed
        int[] rows = jTable2.getSelectedRows();
        if (rows.length>0){
            int i = rows.length - 1;
            while(i>=0){
                //delete_task(jTable2.getValueAt(rows[i], 0).toString());
                delete_task(tarefas_activas.get(rows[i]).get_id());
                i--;
            }
            JOptionPane.showMessageDialog(null, "Eliminado com sucesso!");
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
    }//GEN-LAST:event_eliminar_pop_menuActionPerformed

    private void consultar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultar_itemActionPerformed
        //get tarefa seleccionada
        int row = jTable2.getSelectedRow();
        if (row>=0){
            String id = tarefas_activas.get(row).get_id();//jTable2.getValueAt(row, 0).toString();
            TarefaHoras t = this.lista.get(id);
            if (t!=null){
                call_menu_consultar(t);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
        }
    }//GEN-LAST:event_consultar_itemActionPerformed

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped
        
        if (evt.getKeyChar() == KeyEvent.VK_TAB){
        	edit_col();
        }
    }//GEN-LAST:event_jTable1KeyTyped

    private void edit_col(){
    	int row = jTable1.getSelectedRow();
        int col = jTable1.getSelectedColumn();
//        jTable1.editCellAt(row, col);
//        jTable1.transferFocus();
        boolean success = jTable1.editCellAt(row, col);
        if (success) {
          Component editor = jTable1.getEditorComponent();
          editor.requestFocusInWindow();
          ((JTextComponent)editor).selectAll();
        }
    }
    
    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        //JOptionPane.showMessageDialog(null, "KEY");
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTable1InputMethodTextChanged
//        JOptionPane.showMessageDialog(null, "KEY");
    }//GEN-LAST:event_jTable1InputMethodTextChanged

    private void fav_preferenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fav_preferenciasActionPerformed
        Menu_preferencias_favoritos mpf = new Menu_preferencias_favoritos(this.username, this.dm);
        mpf.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {
                }
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });
        mpf.setVisible(true);
    }//GEN-LAST:event_fav_preferenciasActionPerformed

    private void add_favoritosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_favoritosActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (TarefaHoras t : this.dm.get_lista_tarefas_favoritas())
        {
            if (!this.lista.containsKey(t.get_id()))
            {
                t.get_map().put(this.d_inicio, 0.0);
                this.lista.put(t.get_id(), t);
            }
            else{
                HashMap<Date,Double> aux = this.lista.get(t.get_id()).get_map();
                if (!aux.containsKey(this.d_inicio))
                    aux.put(this.d_inicio, 0.0);
            }
        }
        refresh_models();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_add_favoritosActionPerformed

    private void add_despesa_menu_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_despesa_menu_itemActionPerformed
        int row = jTable2.getSelectedRow();
        if (row>=0){
            String id = tarefas_activas.get(row).get_id();//jTable2.getValueAt(row, 0).toString();
            TarefaHoras t = this.lista.get(id);
            if (t!=null){
                new Adicionar_despesa(this.username,this.dm,t).setVisible(true);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
        }
    }//GEN-LAST:event_add_despesa_menu_itemActionPerformed

    private void add_to_favActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_to_favActionPerformed
        int row = jTable2.getSelectedRow();
        if (row>=0){
            String id = tarefas_activas.get(row).get_id();//jTable2.getValueAt(row, 0).toString();
            TarefaHoras t = this.lista.get(id);
            if (t!=null){
                //new Adicionar_despesa(this.username,this.dm,t).setVisible(true);
                int flag = 0;
                for (TarefaHoras aux : this.dm.get_lista_tarefas_favoritas())
                    if (aux.get_id().equals(t.get_id()))
                        flag = 1;
                if (flag==0){
                    this.dm.get_lista_tarefas_favoritas().add(t);
                    JOptionPane.showMessageDialog(null, "Tarefa adicionada aos favoritos!");
                }
                else
                    JOptionPane.showMessageDialog(null, "Tarefa já existe nos favoritos!");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
        }
    }//GEN-LAST:event_add_to_favActionPerformed

    private void add_favActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_favActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (TarefaHoras t : this.dm.get_lista_tarefas_favoritas())
        {
            if (!this.lista.containsKey(t.get_id()))
            {
                t.get_map().put(this.d_inicio, 0.0);
                this.lista.put(t.get_id(), t);
            }
            else{
                HashMap<Date,Double> aux = this.lista.get(t.get_id()).get_map();
                if (!aux.containsKey(this.d_inicio))
                    aux.put(this.d_inicio, 0.0);
            }
        }
        refresh_models();
        this.setCursor(Cursor.getDefaultCursor());        
    }//GEN-LAST:event_add_favActionPerformed

    private void add_notaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_notaActionPerformed
        String id_tarefa = get_id_tarefa();
        Date data_da_nota = get_data_tabela();
        new Adicionar_nota_tarefas(id_tarefa,data_da_nota,this.dm).setVisible(true);
    }//GEN-LAST:event_add_notaActionPerformed

    private void jTable2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseReleased
        this.tabela_seleccionada = 2;
        set_copy_paste_itens_visible();
    	if ( SwingUtilities.isLeftMouseButton(evt))
        {
            int[] rows = jTable2.getSelectedRows();
            jTable1.clearSelection();
            jTable2.clearSelection();
            for (int i : rows)
            {
                jTable1.addRowSelectionInterval(i, i);
                jTable2.addRowSelectionInterval(i, i);
            }
        }
        if ( SwingUtilities.isRightMouseButton(evt))
        {
            Point p = evt.getPoint();
            int rowNumber = jTable2.rowAtPoint(p);
            jTable1.clearSelection();
            jTable2.clearSelection();
            jTable1.setRowSelectionInterval(rowNumber,rowNumber);
            jTable2.setRowSelectionInterval(rowNumber,rowNumber);
            if (!this.bloqueado){
                jTable2.setComponentPopupMenu(pop_menu_1);
                pop_menu_1.show(jTable2, evt.getX(), evt.getY());
            }
            else{
                jTable2.setComponentPopupMenu(pop_menu_2);
                pop_menu_2.show(jTable2, evt.getX(), evt.getY());
            }

        }
    }//GEN-LAST:event_jTable2MouseReleased
    
    private void set_copy_paste_itens_visible(){
    	if (this.tabela_seleccionada==1){
    		copy_line_menu_item.setVisible(true);
    		paste_menu_item.setVisible(true);
    	}
    	else if (this.tabela_seleccionada==2){
    		copy_line_menu_item.setVisible(true);
    		paste_menu_item.setVisible(false);
    	}
    }

    private void jTable2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MousePressed
    	this.tabela_seleccionada = 2;
    	set_copy_paste_itens_visible();
//        if ( SwingUtilities.isLeftMouseButton(evt))
//        {
//            int[] rows = jTable2.getSelectedRows();
//            jTable1.clearSelection();
//            jTable2.clearSelection();
//            for (int i : rows)
//            {
//                jTable1.addRowSelectionInterval(i, i);
//                jTable2.addRowSelectionInterval(i, i);
//            }
//        }
        if ( SwingUtilities.isRightMouseButton(evt))
        {
            Point p = evt.getPoint();
            int rowNumber = jTable2.rowAtPoint(p);
            jTable1.clearSelection();
            jTable2.clearSelection();
            jTable1.setRowSelectionInterval(rowNumber,rowNumber);
            jTable2.setRowSelectionInterval(rowNumber,rowNumber);
            if (!this.bloqueado){
                jTable2.setComponentPopupMenu(pop_menu_1);
                add_nota.setVisible(false);
                pop_menu_1.show(jTable2, evt.getX(), evt.getY());
            }
            else{
                jTable2.setComponentPopupMenu(pop_menu_2);
                pop_menu_2.show(jTable2, evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_jTable2MousePressed

    private void cons_notaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cons_notaActionPerformed
        int row = jTable1.getSelectedRow();
        int col = jTable1.getSelectedColumn();
        String id_tarefa = this.tarefas_activas.get(row).get_id();//jTable2.getValueAt(row, 0).toString();
        Calendar c_aux = Calendar.getInstance();
        c_aux.clear();
        c_aux.setTime(d_inicio);
        c_aux.add(Calendar.DAY_OF_MONTH, col);
        Date d_aux = c_aux.getTime();
        try{
        	String nota = this.dm.notas_tarefas.get(id_tarefa).get(d_aux);
        	JOptionPane.showMessageDialog(null, nota);
        }
        catch(Exception e){
        	JOptionPane.showMessageDialog(null, "A tarefa não tem nota associada!");
        }
    }//GEN-LAST:event_cons_notaActionPerformed

    @SuppressWarnings("deprecation")
	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new Export_to_excel(this.username,this.d_fim.getMonth(),(this.d_fim.getYear()+1900),0).setVisible(true);
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        jf.setMultiSelectionEnabled(false);
        int esc = jf.showOpenDialog(this);
        if (esc==JFileChooser.APPROVE_OPTION){
            File file = jf.getSelectedFile();
            read_file_from_import_xls(file);
        }  
        get_from_dm();
        refresh_models();
        calc_totais();
    }

    private void read_file_from_import_xls(File f){
    	WorkbookSettings ws = new WorkbookSettings();
    	ws.setEncoding("Cp1252");
        try {
          Workbook w = Workbook.getWorkbook(f,ws);
          // Get the first sheet
          Sheet sheet = w.getSheet(0);
          // Loop over first 10 column and lines
          boolean add_flag = true;
          Calendar c = Calendar.getInstance();
    	  c.clear();
		  int month = 0;
		  int year = 0;
		  
          for (int row = 0; row < sheet.getRows(); row++) {
        	  String cliente = "";
        	  String id_projecto = "";
        	  String etapa = "";
        	  String atividade = "";
        	  String tarefa = "";
        	  String local = "";
        	  String dias = "";
        	  for (int col = 0; col < sheet.getColumns(); col++) {
        		  if (row==0 && add_flag)
        		  {
        			  //saber mes e ano
        			  if (col == 1){
        				  Cell cell = sheet.getCell(col,row);
        				  month = Integer.parseInt(cell.getContents());
        				  month--;
        				  c.set(Calendar.MONTH, month);
        			  }
        			  else if (col==2){
        				  Cell cell = sheet.getCell(col,row);
        				  year = Integer.parseInt(cell.getContents());
        				  c.set(Calendar.YEAR, year);
        			  }
        		  }
        		  if (row==1 && col==1 && add_flag)
                  {
                      //saber username
                      Cell cell = sheet.getCell(col,row);
                      String username_file = cell.getContents();
                      if (!username_file.equals(this.username))
                      {
                          add_flag = false;
                          JOptionPane.showMessageDialog(null, "Username do ficheiro não é igual ao user logado! Apenas pode importar despesas relativas ao user logado!");
                      }
                  }
                  else if (row>3 && add_flag)
                  {
                	  switch(col){
	                	  case 0:{
	                		  //cliente
	                		  Cell cell = sheet.getCell(col,row);
	                          cliente = cell.getContents();
	                		  break;
	                	  }
	                	  case 1:{
	                		  //id_projecto
	                		  Cell cell = sheet.getCell(col,row);
	                          id_projecto = cell.getContents();
	                		  break;
	                	  }
	                	  case 2:{
	                		  //etapa
	                		  Cell cell = sheet.getCell(col,row);
	                          etapa = cell.getContents();
	                		  break;
	                	  }
	                	  case 3:{
	                		  //atividade
	                		  Cell cell = sheet.getCell(col,row);
	                          atividade = cell.getContents();
	                		  break;
	                	  }
	                	  case 4:{
	                		  //tarefa
	                		  Cell cell = sheet.getCell(col,row);
	                          tarefa = cell.getContents();
	                		  break;
	                	  }
	                	  case 5:{
	                		  //local
	                		  Cell cell = sheet.getCell(col,row);
	                          local = cell.getContents();
	                		  break;
	                	  }
	                	  default:{
	                		  //dias
	                		  Cell cell = sheet.getCell(col,row);
	                          dias += cell.getContents() + " & ";
	                		  break;
	                	  }  
                	  }
                  }
        	  }
        	  if (row>3 && add_flag)
        		  parse_line_xls(c,cliente, id_projecto, etapa, atividade, tarefa, local, dias.substring(0, dias.length()-3), row);
        	  
          } 
        }
        catch (BiffException | IOException e) {
        	e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        } 
    }
    
    private void parse_line_xls(Calendar c,String cliente, String id_projecto, String etapa, String atividade, String tarefa, String local,String dias, int cont_linha){
        
    	String id_etapa = "";
        String id_atividade = "";
        String id_tarefa = "";
        String id_local = "";
    	id_projecto = id_projecto.trim().toUpperCase();
        if (this.dm.get_projectos_user().containsKey(id_projecto))
        {
            TarefaHoras t = new TarefaHoras();
            Projecto p = this.dm.get_projectos_user().get(id_projecto);
            t.set_id_projecto(p.get_codigo());
            t.set_nome_projecto(p.get_nome());
            
            //etapa
            for (Etapa e : p.get_etapas().values())
            {
                if (e.get_nome().trim().toUpperCase().equals(etapa.trim().toUpperCase()))
                {
                    t.set_etapa(e.get_nome());
                    t.set_desc_etapa(e.get_descricao());
                    id_etapa = e.get_id();
                }
            }
            if (t.get_etapa().equals("") && !etapa.equals(""))
                JOptionPane.showMessageDialog(null, "Não existe a etapa: " + etapa + "! Linha: " + cont_linha);
            
            //actividade
            for (Actividade a : p.get_actividades().values())
            {
                if (a.get_nome().trim().toUpperCase().equals(atividade.trim().toUpperCase()))
                {
                    t.set_actividade(a.get_nome());
                    t.set_desc_actividade(a.get_descricao());
                    id_atividade = a.get_id();
                }
            }
            if (t.get_actividade().equals("") && !atividade.equals(""))
                JOptionPane.showMessageDialog(null, "Não existe a actividade: " + atividade + "! Linha: " + cont_linha);
            
            for (Tarefa tr : p.get_tarefas().values())
            {
              if (remove_special_chars(tr.get_nome().trim()).toUpperCase().equals(remove_special_chars(tarefa).trim().toUpperCase()))
              {
                t.set_tarefa(tr.get_nome());
                t.set_desc_tarefa(tr.get_descricao());
                id_tarefa = tr.get_id();
              }
            }
            if ((t.get_tarefa().equals("")) && (!tarefa.equals("")))
            {
              boolean encontrou = false;
              for (Tarefa tr : this.dm.lista_tarefa_total.values()) {
                if (remove_special_chars(tr.get_nome().trim()).toUpperCase().equals(remove_special_chars(tarefa).trim().toUpperCase()))
                {
                  t.set_tarefa(tr.get_nome());
                  t.set_desc_tarefa(tr.get_descricao());
                  id_tarefa = tr.get_id();
                  
                  ((Projecto)this.dm.get_projectos_user().get(p.get_codigo())).get_tarefas().put(tr.get_nome(), tr);
                  this.dm.projectos_alterados.add(p.get_codigo());
                  encontrou = true;
                }
              }
              if (!encontrou)
              {
                Tarefa aux = new Tarefa();
                aux.set_id(get_id_auto());
                aux.set_nome(tarefa);
                aux.set_descricao(tarefa);
                ((Projecto)this.dm.get_projectos_user().get(p.get_codigo())).get_tarefas().put(aux.get_nome(), aux);
                this.dm.projectos_alterados.add(p.get_codigo());
                this.dm.tarefas_adicionadas.add(aux.get_id());
                this.dm.lista_tarefa_total.put(aux.get_id(), aux);
                
                t.set_tarefa(aux.get_nome());
                t.set_desc_tarefa(aux.get_descricao());
                id_tarefa = aux.get_id();
              }
            }
            
            //local
            if (local.toUpperCase().equals("ANGOLA"))
            {
                t.set_local("Angola");
                id_local = "AO";
            }
            else
            {
                t.set_local("Portugal");
                id_local = "PT";
            }
            String id_tarefahoras = set_id_from_csv(id_projecto,id_etapa,id_atividade,id_tarefa,id_local);
            t.set_id(id_tarefahoras);
            t.set_funcionario(this.username);
            if (this.dm.lista_tarefas_time_user.containsKey(id_tarefahoras))
            {
                if (this.dm.lista_tarefas_time_user.get(id_tarefahoras).get_map() == null)
                    this.dm.lista_tarefas_time_user.get(id_tarefahoras).set_map(new HashMap<Date,Double>());
            }
            else
            {
                t.set_map(new HashMap<Date,Double>());
                this.dm.lista_tarefas_time_user.put(id_tarefahoras, t);
            }
            
            HashMap<Date,Double> t_comp = this.dm.lista_tarefas_time_user.get(id_tarefahoras).get_map();
            
            String[] dias_separado = dias.split("&");
            int cont = dias_separado.length;
            int i = 1;
            while (i <= cont)
            {
                double horas_dia;
                try{ 
                    String aux_horas = dias_separado[i-1].trim();
                    aux_horas = aux_horas.replace(",",	".");
                    horas_dia = Double.parseDouble(aux_horas); 
                }
                catch(NumberFormatException e){
                    horas_dia = 0;
                }
                
                if (horas_dia!=0)
                {	
                	c.set(Calendar.DAY_OF_MONTH, i);
                	boolean bloq = verify_dia_bloqueado(c.getTime());
                	if (!bloq)
                		t_comp.put(c.getTime(),horas_dia);
                }
                i++;
            }
            this.dm.lista_tarefas_time_user.get(id_tarefahoras).set_map(t_comp);
            this.dm.tarefas_adicionadas.add(t.get_id());
        }
        else
            JOptionPane.showMessageDialog(null, "Não existe projecto com id: " + id_projecto + "! Linha: "+cont_linha);
    }
    
    private boolean verify_dia_bloqueado(Date d){
    	for (Horas_Handle_Obj hho : this.dm.lista_handler_horas){
    		if (hho.get_data().equals(d))
    			if (hho.get_estado() == 1 || hho.get_estado() == 9)
    				return true;
    			else
    				return false;
    	}
    	return false;
    }
    
    private String get_id_auto()
    {
      int max = 0;
      String pesquisa = "usr" + this.dm.get_id_user_tarefa() + "trf";
      for (String s : this.dm.lista_tarefa_total.keySet()) {
        if (s.contains(pesquisa))
        {
          String resto = s.replace(pesquisa, "");
          int id_resto = 0;
          try
          {
            id_resto = Integer.parseInt(resto);
          }
          catch (NumberFormatException e)
          {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            id_resto = 0;
          }
          if (id_resto > max) {
            max = id_resto;
          }
        }
      }
      max++;
      String id = pesquisa;
      if (max < 10) {
        id = id + "000" + max;
      } else if (max < 100) {
        id = id + "00" + max;
      } else if (max < 1000) {
        id = id + "0" + max;
      } else {
        id = id + max;
      }
      return id;
    }
    
    private String remove_special_chars(String s)
    {
      s = s.replace("ç", "c");
      s = s.replace("á", "a");
      s = s.replace("à", "a");
      s = s.replace("ã", "a");
      s = s.replace("â", "a");
      s = s.replace("ê", "e");
      s = s.replace("é", "e");
      s = s.replace("è", "e");
      s = s.replace("í", "i");
      s = s.replace("ì", "i");
      s = s.replace("î", "i");
      s = s.replace("ó", "o");
      s = s.replace("ò", "o");
      s = s.replace("õ", "o");
      s = s.replace("ô", "o");
      s = s.replace("ú", "u");
      s = s.replace("ù", "u");
      s = s.replace("û", "u");
      return s;
    }
    
    private String set_id_from_csv(String id_projecto, String id_etapa, String id_actividade, String id_tarefax, String id_local){
        return id_projecto + "_" + id_etapa +"."+ id_actividade +"."+ id_tarefax + "_" + id_local;
    }
    
    private String get_id_tarefa(){
        String id;
        Object res = tarefas_activas.get(this.row_nota).get_id();//jTable2.getValueAt(this.row_nota,0);
        id = res.toString();
        return id;
    }
    
    private Date get_data_tabela(){
        Date data;
        Calendar aux = Calendar.getInstance();
        aux.clear();
        aux.setTime(this.d_inicio);
        aux.add(Calendar.DAY_OF_MONTH, this.col_nota);
        data = aux.getTime();
        return data;
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
   
    @SuppressWarnings("unused")
	public void set_to_dm(String id, TarefaHoras t){
        TarefaHoras th = this.dm.get_lista_tarefas_horas_user().get(t.get_id());
        th = t;
    }
    
    public int set_to_bd(String id, TarefaHoras t, Connection con){
        int res = 0;
        try{
        PreparedStatement ps;
        String sql;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(t);
        oos.flush();
        oos.close();
        bos.close();

        
        byte[] data = bos.toByteArray();
        sql = "select * from tnm_tarefas where id_tarefa = '" +id+"' and username='"+this.username+"'";
        ResultSet rs;
        ps=con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next())
            sql = "update tnm_tarefas set tarefa=? where id_tarefa=? and username=?";
        else
            sql="insert into tnm_tarefas (tarefa,id_tarefa,username) values(?,?,?)";
        ps=con.prepareStatement(sql);
        ps.setObject(1, data);
        ps.setString(2, id);
        ps.setString(3, this.username);
        ps.executeUpdate();
        
        rs.close();
        ps.close();
        }
        catch(IOException | SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            res++;
        }
        return res;
    }
    
    private void get_from_dm(){
        this.lista = dm.get_lista_tarefas_horas_user();
        this.modificacoes_handler = dm.get_lista_handle_horas_modificacoes();
    }
    
    @SuppressWarnings("unused")
	private void set_model_table(Date inicio, Date fim){
        ArrayList<TarefaHoras> lista2 = new ArrayList<>();
        for (TarefaHoras t : this.lista.values()){
            for (Date d : t.get_map().keySet())
                if (!d.before(inicio) && !d.after(fim))
                    if (!exists(t,lista2))
                        lista2.add(t);
        }
        this.tarefas_activas = lista2;
        //String columnNamesTarefas[] = {"ID","Projecto","Etapa","Actividade","Tarefa","Local"};
        String columnNamesTarefas[] = {"Cliente","Projecto","Etapa","Actividade","Tarefa","Local"};
        jTable2.setModel(new ModeloTabelaTarefas(columnNamesTarefas, lista2,this.dm.get_projectos_user()));
        jTable2.getTableHeader().setReorderingAllowed(false);
        String columnNamesHoras[] = set_label();
        double[][] data = place_data(inicio,fim,lista2);
        jTable1.setModel(new ModeloTabela(columnNamesHoras,data));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        ((DefaultCellEditor) jTable1.getDefaultEditor(Object.class)).setClickCountToStart(1);
        ((DefaultCellEditor) jTable1.getDefaultEditor(Object.class)).getComponent().addKeyListener(new KeyAdapter() {
	  	      @Override
	  	      public void keyPressed(KeyEvent e){
	  	        if(e.getKeyCode()==KeyEvent.VK_TAB)
	  	          {
//		  	        ((DefaultCellEditor) jTable1.getDefaultEditor(Object.class)).stopCellEditing();
	  	        	set_tab_flag(true);
	  	          }
	  	      }
  	    });
        ((DefaultCellEditor) jTable1.getDefaultEditor(Object.class)).getComponent().addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				select_next_cell();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                    actualiza_bd_horas();
                    int col = jTable1.getSelectedColumn();
                    Calendar new_cal = Calendar.getInstance();
                    new_cal.clear();
                    new_cal.setTime(d_inicio);
                    new_cal.add(Calendar.DAY_OF_MONTH, col);
                    set_semana_to_alterado();
            }
        };
        TableCellListener tcl = new TableCellListener(jTable1, action); 
        jTable1.getModel().addTableModelListener(
        new TableModelListener() 
        {
            @Override
            public void tableChanged(TableModelEvent evt) 
            {
                  change_save_variable();
                  calc_totais();
            }
        });
        this.excel_adapter_tabela_horas = new ExcelAdapter_tabela_horas(jTable1,this);
        this.excel_adapter_descritivo_horas = new ExcelAdapter_tabela_desc(jTable2);
    }
    
    private void select_next_cell(){
		if (this.tab_flag == true){
			edit_col();
		}
		this.tab_flag = false;
    }
    
    private void set_tab_flag(boolean flag){
    	this.tab_flag = flag;
    }
    
    public void action_table(){
    	actualiza_bd_horas();
        int col = jTable1.getSelectedColumn();
        Calendar new_cal = Calendar.getInstance();
        new_cal.clear();
        new_cal.setTime(d_inicio);
        new_cal.add(Calendar.DAY_OF_MONTH, col);
        set_semana_to_alterado();
    }
    
    @SuppressWarnings("unused")
	private void set_model_table(Date inicio, Date fim, ArrayList<Integer> lista_ap,ArrayList<Integer> lista_bl){
        ArrayList<TarefaHoras> lista2 = new ArrayList<>();
        for (TarefaHoras t : this.lista.values()){
            for (Date d : t.get_map().keySet())
                if (!d.before(inicio) && !d.after(fim))
                    if (!exists(t,lista2))
                        lista2.add(t);
        }
        this.tarefas_activas = lista2;
        String columnNamesTarefas[] = {"Cliente","Projecto","Etapa","Actividade","Tarefa","Local"};
        jTable2.setModel(new ModeloTabelaTarefas(columnNamesTarefas, lista2,this.dm.get_projectos_user()));
        jTable2.getTableHeader().setReorderingAllowed(false);
        String columnNamesHoras[] = set_label();
        double[][] data = place_data(inicio,fim,lista2);
        jTable1.setModel(new ModeloTabela(columnNamesHoras,data,lista_ap,lista_bl));
        jTable1.getTableHeader().setReorderingAllowed(false);
        ((DefaultCellEditor) jTable1.getDefaultEditor(Object.class)).setClickCountToStart(1);
        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                    actualiza_bd_horas();
                    int col = jTable1.getSelectedColumn();
                    Calendar new_cal = Calendar.getInstance();
                    new_cal.clear();
                    new_cal.setTime(d_inicio);
                    new_cal.add(Calendar.DAY_OF_MONTH, col);
//                    set_dia_to_alterado(new_cal.getTime());
                    set_semana_to_alterado();
            }
        };
        TableCellListener tcl = new TableCellListener(jTable1, action); 
        jTable1.getModel().addTableModelListener(
        new TableModelListener() 
        {
            @Override
            public void tableChanged(TableModelEvent evt) 
            {
                  change_save_variable();
                  calc_totais();
            }
        });
    }
    
    private void actualiza_bd_horas(){
        Calendar aux = (Calendar) this.cal.clone();
        Date d2 = aux.getTime();
        aux.add(Calendar.DAY_OF_MONTH, -6);
        Date d1 = aux.getTime();
        set_data_bdates(d1, d2);
    }
    
    private void set_data_bdates(Date inicio, Date fim){
        ArrayList<TarefaHoras> lista2 = new ArrayList<>();
        for (TarefaHoras t : this.lista.values()){
            for (Date d : t.get_map().keySet())
                if (!d.before(inicio) && !d.after(fim))
                    if (!exists(t,lista2))
                        lista2.add(t);
        }
        change_map_data(inicio,fim,lista2);
    }
    
    private void change_map_data(Date inicio, Date fim, ArrayList<TarefaHoras> list){
        int i = 0;
        for (TarefaHoras t : list){
            Date d = inicio;
            int j=0;
            while (!d.after(fim)){
                double aux = Double.valueOf(jTable1.getValueAt(i, j).toString());
                t.get_map().put(d,aux);
                j++;
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.DAY_OF_MONTH, 1);
                d = c.getTime();
            }
            i++;
        }
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
    
    private void block_part_menu_approved(boolean ap){
        adicionar_botao.setEnabled(ap);
        eliminar_botao.setEnabled(ap);
        editar_botao.setEnabled(ap);
        jTable1.setEnabled(ap);
        jTable2.setEnabled(ap);
        this.bloqueado = !ap;
    }
    
    private void set_aproved_or_not(){
        int ap = get_aprov_or_not(this.username, this.d_inicio,this.d_fim);    
        
        if (ap==1){
            ImageIcon fillingIcon = new ImageIcon(getClass().getResource("/TimeNMoney/aprovado_selo.png"));
            aprov_label.setIcon(fillingIcon);
            this.semana_rejeitada = false;
            block_part_menu_approved(false);
        }
        else if (ap==2){
            ImageIcon fillingIcon = new ImageIcon(getClass().getResource("/TimeNMoney/rejeitado-selo.png"));
            aprov_label.setIcon(fillingIcon);
            this.semana_rejeitada = true;
            block_part_menu_approved(true);
        }
        else if (ap==3){
            ImageIcon fillingIcon = new ImageIcon(getClass().getResource("/TimeNMoney/alterado_selo.png"));
            aprov_label.setIcon(fillingIcon);
            this.semana_rejeitada = false;
            block_part_menu_approved(true);
        }
        else if (ap==9){
        	this.semana_rejeitada = false;
            block_part_menu_approved(false);
        }
        else{
            aprov_label.setIcon(null);
            this.semana_rejeitada = false;
            block_part_menu_approved(true);
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
            
            if (!edit_rejected_row)
                set_model_table(this.d_inicio, this.d_fim, this.dias_aprovados,this.dias_bloqueados);
            while (i<n){
                int aux_ap;
                if (dias_aprovados.contains(i))
                    aux_ap = 1;
                else if (dias_rejeitados.contains(i))
                    aux_ap = 2;
                else if (dias_em_revisao.contains(i))
                    aux_ap = 3;
                else if (dias_bloqueados.contains(i))
                	aux_ap = 9;
                else 
                    aux_ap = 0;
                jTable1.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer(aux_ap));
                i++;
            }
            this.edit_rejected_row = false;
        }
    }
    
    private int get_aprov_or_not(String username, Date inicio, Date fim){
        this.dias_aprovados = new ArrayList<>();
        this.dias_rejeitados = new ArrayList<>();
        this.dias_em_revisao = new ArrayList<>();
        this.dias_bloqueados = new ArrayList<>();
        ArrayList<Integer> lista = new ArrayList<>();
        int tam = 0;
        int tam_apro = 0;
        int tam_rej = 0;
        int tam_espera = 0;
        int tam_bloqueado = 0;
        for (Horas_Handle_Obj h : this.dm.lista_handler_horas)
            if (h.get_username().equals(username) && (!h.get_data().before(inicio) && !h.get_data().after(fim)))
            {
                long d1 = inicio.getTime();
                long d2 = h.get_data().getTime();
                long days = (d2 - d1) / 86400000;
                int estado = h.get_estado();
                lista.add(estado);
                if (estado==1)
                    this.dias_aprovados.add((int)days);
                else if (estado ==2)
                    this.dias_rejeitados.add((int)days);
                else if (estado == 3)
                    this.dias_em_revisao.add((int)days);
                else if (estado == 9)
                	this.dias_bloqueados.add((int)days);
            }
        for (Integer i : lista)
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
    
    @SuppressWarnings("unused")
	private void load_table(){
        set_model_table(d_inicio, d_fim);
        set_aproved_or_not();
    }
    
    @SuppressWarnings("resource")
	private int add_to_bd_handler_horas(String username, Date dia, int aprov,Connection con){
        int ret = 0;
        java.sql.Date d1 = new java.sql.Date(dia.getTime());
        try {
            PreparedStatement ps;
            String sql;
            ResultSet rs;
            sql = "select * from tnm_handle_horas where username='"+ username +"' and data = '" + d1 + "'";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next()){
                sql="insert into tnm_handle_horas (username,data,situacao) values(?,?,?)";
                ps=con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setDate(2, d1);
                ps.setInt(3, aprov);
                ps.executeUpdate();
            }
            else
            {
                int sit_bd = rs.getInt("SITUACAO");
            	if(sit_bd != 1){
            		sql="update tnm_handle_horas set username=?, situacao=?, data=? where username=? and data = ?";
	                ps=con.prepareStatement(sql);
	                ps.setString(1, username);
	                ps.setInt(2, aprov);
	                ps.setDate(3, d1);
	                ps.setString(4, username);
	                ps.setDate(5, d1);
	                ps.executeUpdate();
                }
            }
          rs.close();
          ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            ret++;
        }
        return ret;
    }
    
    private void add_to_dm_handler_horas(String username, Date dia, int aprov){
        for (Horas_Handle_Obj h : this.dm.lista_handler_horas)
            if (h.get_username().equals(username) && h.get_data().equals(dia))
            {
                if (aprov == 3){
                	if (h.get_estado()==2)
                	{
                		h.set_estado(aprov);
                    	this.modificacoes_handler.add(h);
                    	this.dm.lista_handler_horas_modificacoes.add(h);
                	}
                }
                else{
                	h.set_estado(aprov);
                	this.modificacoes_handler.add(h);
                	this.dm.lista_handler_horas_modificacoes.add(h);
                }
            }
    }
    
	private void goto_chooser_action(){
    	Date d = goto_chooser.getDate();
    	if (d==null){
    		JOptionPane.showMessageDialog(null, "Escolha uma data, data vazia ou formato de data inválido!");
    	}
    	else{
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
    	}
    }
    
    private void set_data_goto(){
    	Calendar c = Calendar.getInstance();
    	Date d = c.getTime();
    	goto_chooser.setDate(d);
    }
    
    private Date ultima_hora_adicionada(){
    	Calendar aux = Calendar.getInstance();
    	aux.add(Calendar.YEAR, -5);
    	Date last_date = aux.getTime();
    	for (TarefaHoras t : this.lista.values())
    	{
    		for (Date d : t.get_map().keySet())
    			if (d.after(last_date) && t.get_map().get(d)>0)
    				last_date = d;
    	}
    	if (last_date.equals(aux.getTime()))
    		last_date = Calendar.getInstance().getTime();
    	return last_date;
    }
    
    private void goto_last_preenchido(){
    	Date ultimo_preenchido = ultima_hora_adicionada();
    	if (ultimo_preenchido!=null)
    	{
    		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			Calendar aux = new GregorianCalendar();
			aux.setTime(ultimo_preenchido);
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
    
    private void smart_clear_action(){
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		for (TarefaHoras t : this.lista.values()){
			ArrayList<Date> datas_a_eliminar = new ArrayList<Date>();
			for (Date d : t.get_map().keySet())
			{
				if (t.get_map().get(d)==0)
					datas_a_eliminar.add(d);
			}
			for (Date d : datas_a_eliminar)
				t.get_map().remove(d);
		}
		set_aproved_or_not();
        calc_totais();
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void set_copy(){
    	switch(this.tabela_seleccionada){
    	case 1: this.excel_adapter_tabela_horas.set_copy_line();break;
    	case 2: this.excel_adapter_descritivo_horas.set_copy_line();break;
    	default:break;
    	}
    }
    
    private void set_paste(){
    	int rowNumber = jTable1.rowAtPoint(ponto_click_rato);
        int colNumber = jTable1.columnAtPoint(ponto_click_rato);
    	this.excel_adapter_tabela_horas.set_paste(rowNumber,colNumber);
    }
    
    private void enviar_parte_action(Date d_fim){
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	set_send_handler_data_fim(d_fim);
        int res = save_button_action(true);
        if (res==0){
            this.save = false;
            JOptionPane.showMessageDialog(null, "Dados de tarefas enviados com sucesso!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Erro ao enviar dados de tarefas, tente novamente mais tarde ou contacte administrador!");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void bloquear_horas_from_to(Date inicio, Date fim){
    	Calendar c = new GregorianCalendar();
    	c.setTime(inicio);
    	Date iter = inicio;
    	while (!iter.after(fim)){
    		add_date_to_handler(iter);
    		c.add(Calendar.DAY_OF_MONTH, 1);
    		iter = c.getTime();
    	}
    }
    
    private void add_date_to_handler(Date d){
    	boolean encontrou = false;
    	for (Horas_Handle_Obj hho : this.dm.lista_handler_horas){
    		if (hho.get_data().equals(d))
    		{
    			encontrou = true;
    			if (hho.get_estado()!=1){
    				hho.set_estado(9);
    				this.dm.lista_handler_horas_modificacoes.add(hho);
    			}
    		}
    	}
    	if (!encontrou){
			Horas_Handle_Obj nhho = new Horas_Handle_Obj();
			nhho.set_username(username);
			nhho.set_data(d);
			nhho.set_estado(9);
			this.dm.lista_handler_horas.add(nhho);
			this.dm.lista_handler_horas_modificacoes.add(nhho);
		}
    }
    
    private void set_send_handler_data_fim(Date d_fim){
    	ArrayList<Horas_Handle_Obj> lista = this.dm.lista_handler_horas;
    	//ver ultimo enviado
    	//se nao tiver nenhum, primeiro preenchido
    	Date d_inicio = null;
    	if (lista.size()>0)
    	{
	    	for (Horas_Handle_Obj hho : lista)
	    	{
	    		if (hho.get_estado() != 1){
	    			//d_inicio a null
	    			if (d_inicio == null)
	    				d_inicio = hho.get_data();
	    			//d_inicio not null
	    			if ((d_inicio != null) && hho.get_data().before(d_inicio))
	    				d_inicio = hho.get_data();
	    		}
	    	}
	    	
//	    	if (d_inicio == null){
//	    		for (Horas_Handle_Obj hho : lista){
//	    			if (d_inicio == null && hho.get_estado()!=1)
//	    				d_inicio = hho.get_data();
//	    			if ((d_inicio != null) && hho.get_data().before(d_inicio) && hho.get_estado()!=1)
//	    				d_inicio = hho.get_data();
//	    		}
//	    	}
    	}
    	
    	if (d_inicio==null){
    		for (TarefaHoras t : this.dm.lista_tarefas_time_user.values())
    		{
    			for (Date d : t.get_map().keySet())
    			{
    				if (d_inicio == null)
    					d_inicio = d;
    				if (d_inicio.after(d))
    					d_inicio = d;
    			}
    		}
    	}
    	
    	if (d_inicio != null){
	    	bloquear_horas_from_to(d_inicio, d_fim);
	    	set_aproved_or_not();
    	}
    }
    
    private void set_send_handler(){
    	ArrayList<Horas_Handle_Obj> lista = this.dm.lista_handler_horas;
    	//ver ultimo enviado
    	//se nao tiver nenhum, primeiro preenchido
    	Date d_inicio = null;
    	if (lista.size()>0)
    	{
    		for (Horas_Handle_Obj hho : lista)
	    	{
	    		if (hho.get_estado() != 1){
	    			//d_inicio a null
	    			if (d_inicio == null)
	    				d_inicio = hho.get_data();
	    			//d_inicio not null
	    			if ((d_inicio != null) && hho.get_data().before(d_inicio))
	    				d_inicio = hho.get_data();
	    		}
	    	}
	    	
//	    	if (d_inicio == null){
//	    		for (Horas_Handle_Obj hho : lista){
//	    			if (d_inicio == null && hho.get_estado()!=1)
//	    				d_inicio = hho.get_data();
//	    			if ((d_inicio != null) && hho.get_data().before(d_inicio) && hho.get_estado()!=1)
//	    				d_inicio = hho.get_data();
//	    		}
//	    	}
    	}
    	
    	if (d_inicio==null){
    		for (TarefaHoras t : this.dm.lista_tarefas_time_user.values())
    		{
    			for (Date d : t.get_map().keySet())
    			{
    				if (d_inicio == null)
    					d_inicio = d;
    				if (d_inicio.after(d))
    					d_inicio = d;
    			}
    		}
    	}
    	
    	if (d_inicio != null){
	    	Date d_fim = ultima_hora_adicionada();
	    	bloquear_horas_from_to(d_inicio, d_fim);
	    	set_aproved_or_not();
    	}
    }
    
    private void exportar_dados_action(){
    	ArrayList<Despesa_new> lista_despesas = null;
    	TreeMap<String,TarefaHoras> lista_horas = this.lista;
    	(new Export_user_info(this.dm,lista_despesas,lista_horas)).setVisible(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem add_despesa_menu_item;
    private javax.swing.JMenuItem add_fav;
    private javax.swing.JMenuItem add_favoritos;
    private javax.swing.JMenuItem add_nota;
    private javax.swing.JMenuItem copy_line_menu_item;
    private javax.swing.JMenuItem paste_menu_item;
    private javax.swing.JMenuItem add_to_fav;
    private javax.swing.JMenuItem add_to_fav2;
    private javax.swing.JButton adicionar_botao;
    private javax.swing.JLabel aprov_label;
    private javax.swing.JButton beforeWeekButton;
    private javax.swing.JButton botao_voltar;
    private javax.swing.JMenuItem cons_nota;
    private javax.swing.JMenuItem consultar_item;
    private javax.swing.JButton editar_botao;
    private javax.swing.JMenuItem editar_pop_menu;
    private javax.swing.JButton eliminar_botao;
    private javax.swing.JMenuItem eliminar_pop_menu;
    private javax.swing.JMenuItem fav_preferencias;
    private javax.swing.JMenu fav_tarefas;
    private javax.swing.JLabel intLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JFileChooser jf;
    private javax.swing.JButton nextWeekButton;
    private javax.swing.JPopupMenu pop_menu_1;
    private javax.swing.JPopupMenu pop_menu_2;
    private javax.swing.JButton saveBDButton;
    private javax.swing.JLabel t0;
    private javax.swing.JLabel t1;
    private javax.swing.JLabel t2;
    private javax.swing.JLabel t3;
    private javax.swing.JLabel t4;
    private javax.swing.JLabel t5;
    private javax.swing.JLabel t6;
    private JDateChooser goto_chooser;
    private JButton btnIrPara;
    private JButton button;
    private JButton smart_clean_button;
    private JButton botao_enviar_parte;
    private JMenuItem mntmExportarDados;
}
