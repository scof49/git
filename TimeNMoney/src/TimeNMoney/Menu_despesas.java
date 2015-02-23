package TimeNMoney;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

@SuppressWarnings("serial")
public class Menu_despesas extends javax.swing.JFrame {
    String username;
    ArrayList<Despesa_new> lista;
    ArrayList<Despesa_new> listaback;
    TreeMap<Integer,Integer> estados;
    Data_manager dm;
    ExcelAdapter_tabela_desc excel_adapter_descritivo_despesas;
    
    public Menu_despesas(String username, Data_manager dm) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.username = username;
        this.dm = dm;
        userLabel.setText(username);
        this.lista = new ArrayList<>();   
        this.listaback = new ArrayList<>();
        this.estados = new TreeMap<>();
        load_table();
        this.excel_adapter_descritivo_despesas = new ExcelAdapter_tabela_desc(jTable1);
    }

    private ArrayList<Integer> get_aproved(){
        ArrayList<Integer> aux = new ArrayList<>();
        int n = jTable1.getRowCount();
        int i=0;
        while(i<n){
            int id = lista.get(i).get_id();//Integer.parseInt(jTable1.getValueAt(i, 0).toString());
            //go to list and verify id state
            if (this.estados.containsKey(id))
                if (this.estados.get(id) == 2)
                    aux.add(i);
            i++;
        } 
        return aux;
    }
    
    private ArrayList<Integer> get_lista_alteradas_rejeitadas(){
    	ArrayList<Integer> aux = new ArrayList<>();
    	int n = jTable1.getRowCount();
    	int i=0;
    	while(i<n){
    		int id = lista.get(i).get_id();//Integer.parseInt(jTable1.getValueAt(i, 0).toString());
    		//go to list and verify id state
    		if (this.estados.containsKey(id))
    			if (this.estados.get(id) == 4)
    				aux.add(i);
    		i++;
    	} 
    	return aux;
    }
    
    private ArrayList<Integer> get_not_aproved(){
        ArrayList<Integer> aux = new ArrayList<>();
        int n = jTable1.getRowCount();
        int i=0;
        while(i<n){
            int id = lista.get(i).get_id();//Integer.parseInt(jTable1.getValueAt(i, 0).toString());
            //go to list and verify id state
            if (this.estados.containsKey(id))
                if (this.estados.get(id) == 3)
                    aux.add(i);
            i++;
        } 
        return aux;
    }
    
    private void set_aproved(){
        ArrayList<Integer> aprovadas = get_aproved();        
        ArrayList<Integer> rejeitadas = get_not_aproved();
        ArrayList<Integer> lista_alteradas_rejeitadas = get_lista_alteradas_rejeitadas();
    
        int n = jTable1.getColumnCount();
        int i =0;
        while (i<n){
            jTable1.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer(aprovadas,rejeitadas,lista_alteradas_rejeitadas));
            i++;
        }
    }
    
    private String get_totais_aprovados(){
        double tot = 0;
        for (Despesa_new d : this.lista){
            if (this.estados.containsKey(d.get_id()) && this.estados.get(d.get_id())==2)
                tot += d.get_quantidade() * d.get_valor_euros();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(tot).replace(",", ".");
    }
    
    private String get_totais_rejeitados(){
        double tot = 0;
        for (Despesa_new d : this.lista){
            if (this.estados.containsKey(d.get_id()) && this.estados.get(d.get_id())==3)
                tot += d.get_quantidade() * d.get_valor_euros();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(tot).replace(",", ".");
    }
    
    private String get_totais_em_espera(){
        double tot = 0;
        for (Despesa_new d : this.lista){
            if (!this.estados.containsKey(d.get_id()) || (this.estados.containsKey(d.get_id()) && this.estados.get(d.get_id())<=1))
                tot += d.get_quantidade() * d.get_valor_euros();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(tot).replace(",", ".");
        
    }
    
    private void setTotais(String ap, String rj, String es){
        apLabel.setText(ap);
        rjLabel.setText(rj);
        esLabel.setText(es);
        double tot = Double.parseDouble(ap) + Double.parseDouble(rj) + Double.parseDouble(es);
        DecimalFormat df = new DecimalFormat("#.##");
        totLabel.setText(df.format(tot).replace(",", "."));
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenu1 = new javax.swing.JPopupMenu();
        changeMenu = new javax.swing.JMenuItem();
        deleteMenu = new javax.swing.JMenuItem();
        readRecibo = new javax.swing.JMenuItem();
        copy_button = new javax.swing.JMenuItem();
        popMenu2 = new javax.swing.JPopupMenu();
        viewMenu = new javax.swing.JMenuItem();
        readRecibo1 = new javax.swing.JMenuItem();
        jf = new javax.swing.JFileChooser();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        changeButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();
        userLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        apLabel = new javax.swing.JLabel();
        rjLabel = new javax.swing.JLabel();
        esLabel = new javax.swing.JLabel();
        totLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        botao_enviar = new javax.swing.JButton();
        onlyNotAprov = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        changeMenu.setText("Consultar/Alterar");
        changeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeMenuActionPerformed(evt);
            }
        });
        popMenu1.add(changeMenu);

        deleteMenu.setText("Apagar");
        deleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuActionPerformed(evt);
            }
        });
        popMenu1.add(deleteMenu);

        readRecibo.setText("Ver recibo");
        readRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readReciboActionPerformed(evt);
            }
        });
        popMenu1.add(readRecibo);
        
        copy_button.setText("Copiar");
        copy_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	copy_buttonActionPerformed(evt);
            }
        });
        popMenu1.add(copy_button);

        viewMenu.setText("Consultar");
        viewMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewMenuActionPerformed(evt);
            }
        });
        popMenu2.add(viewMenu);

        readRecibo1.setText("Ver recibo");
        readRecibo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readRecibo1ActionPerformed(evt);
            }
        });
        popMenu2.add(readRecibo1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/add_expense_icon.png"))); // NOI18N
        jButton1.setText("Adicionar Despesa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Despesas"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        changeButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        changeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/edit_expense_icon.png"))); // NOI18N
        changeButton.setText("Alterar Despesa");
        changeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeButtonActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(0, 150, 0));
        jLabel1.setText("Despesa aprovada");

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Despesa Rejeitada");

        deleteButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/remove_expense_icon.png"))); // NOI18N
        deleteButton.setText("Eliminar Despesa");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        userLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel4.setText("Despesa por Verificar");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Totais"));

        jLabel5.setText("Aprovado:");

        jLabel6.setText("Rejeitado:");

        jLabel7.setText("Em espera:");

        apLabel.setText("0");

        rjLabel.setText("0");

        esLabel.setText("0");

        totLabel.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(64, 64, 64)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(rjLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(apLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(esLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(apLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rjLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(esLabel))
                .addGap(18, 18, 18)
                .addComponent(totLabel)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/expense_icon.png"))); // NOI18N
        jLabel3.setText("Menu de Despesas");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_back.png"))); // NOI18N
        jButton2.setText("Voltar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        botao_enviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/send_icon.png"))); // NOI18N
        botao_enviar.setText("Enviar");
        botao_enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_enviarActionPerformed(evt);
            }
        });

        onlyNotAprov.setText("Apenas despesas por aprovar");
        onlyNotAprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlyNotAprovActionPerformed(evt);
            }
        });

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/export_excel_mini.png"))); // NOI18N
        jMenu1.setText("Importar/Exportar");

        jMenuItem1.setText("Exportar excel exemplo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Importar excel despesas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);
        
        mntmExportarDados = new JMenuItem("Exportar dados");
        mntmExportarDados.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		exportar_dados_action();
        	}
        });
        jMenu1.add(mntmExportarDados);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jScrollPane1)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel1)
        					.addGap(18)
        					.addComponent(jLabel2)
        					.addGap(18)
        					.addComponent(jLabel4)
        					.addPreferredGap(ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
        					.addComponent(botao_enviar, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(jButton1)
        							.addGap(18)
        							.addComponent(changeButton)
        							.addGap(18)
        							.addComponent(deleteButton))
        						.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel3))
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(onlyNotAprov)
        						.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(jLabel3)
        					.addGap(20)
        					.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(changeButton)
        						.addComponent(jButton1)
        						.addComponent(deleteButton)))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(19)
        					.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(onlyNotAprov)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel1)
        						.addComponent(jLabel2)
        						.addComponent(jLabel4))
        					.addGap(26))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        						.addComponent(botao_enviar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addContainerGap())))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Adicionar_despesa ae = new Adicionar_despesa(this.username);
        Adicionar_despesa ae = new Adicionar_despesa(this.username,this.dm);
        ae.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) {}

                @Override
                public void windowClosing(WindowEvent we) {}

                @Override
                public void windowClosed(WindowEvent we) {
                    load_table();
                    set_onlyaprov_option();
                }
                
                @Override
                public void windowIconified(WindowEvent we) {}

                @Override
                public void windowDeiconified(WindowEvent we) {}

                @Override
                public void windowActivated(WindowEvent we) {}

                @Override
                public void windowDeactivated(WindowEvent we) {}
        });
        if (ae.get_close_flag()==0)
            ae.setVisible(true);
        else
            ae.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void changeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeButtonActionPerformed
        Despesa_new d = get_selected_expense();
        if (d!=null){
        	boolean despesa_rejeitada = false;
            boolean b = false;
            if (d.get_data_aprovacao()==null)
                b=true;
            if (this.estados.containsKey(d.get_id()) && this.estados.get(d.get_id()) == 2)
                b=false;
            if (this.estados.containsKey(d.get_id()) && this.estados.get(d.get_id()) == 3)
                despesa_rejeitada = true;
            //Alterar_despesa ce = new Alterar_despesa(username, d,b);
            Alterar_despesa ce = new Alterar_despesa(username, d,b,this.dm,despesa_rejeitada);
            ce.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) {}

                @Override
                public void windowClosing(WindowEvent we) {}

                @Override
                public void windowClosed(WindowEvent we) {
                    load_table();
                    set_onlyaprov_option();
                }
                
                @Override
                public void windowIconified(WindowEvent we) {}

                @Override
                public void windowDeiconified(WindowEvent we) {}

                @Override
                public void windowActivated(WindowEvent we) {}

                @Override
                public void windowDeactivated(WindowEvent we) {}
            });
            ce.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null,"Não seleccionou nenhuma despesa!");
        }
    }

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {
            if ( SwingUtilities.isRightMouseButton(evt))
            {
                Point p = evt.getPoint();
                int rowNumber = jTable1.rowAtPoint(p);
                jTable1.setRowSelectionInterval(rowNumber, rowNumber);
                String s = jTable1.getValueAt(rowNumber, 4).toString();
                if (s.equals("")){
                    jTable1.setComponentPopupMenu(popMenu1);
                    popMenu1.show(jTable1, evt.getX(), evt.getY());
                }
                else{
                    jTable1.setComponentPopupMenu(popMenu2);
                    popMenu2.show(jTable1, evt.getX(), evt.getY());
                }
            }
    }
    
    private int delete_from_dm(int id){
        Despesa_new el = null;
        for (Despesa_new d : this.dm.despesas_user)
            if (d.get_id()==id && d.get_username().equals(this.username)){
                el = d;
            }
        
        if (el!=null){
            this.dm.despesas_user.remove(el);
            this.dm.estado_despesas_user.remove(el.get_id());
            this.dm.despesas_user_eliminadas.add(el);
        }
        return 1;
    }
    
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int[] rows = jTable1.getSelectedRows();
        for (int i = rows.length - 1; i >= 0;i--)
        {
	        int row = rows[i];
        	if (row>=0){
	            String ap = jTable1.getValueAt(row, 4).toString();
	            if (ap.equals(""))
	            {
	                int id = lista.get(row).get_id();
	                int res = delete_from_dm(id);
	                if (res==1){
	                    load_table();
	                }
	                else
	                    JOptionPane.showMessageDialog(null, "Erro ao eliminar despesa "+ id +"! Tente outra vez ou contacte administrador!");
	            }
	            else
	                JOptionPane.showMessageDialog(null, "Não é possível eliminar uma despesa já aprovada!");
	        }
	        else
	            JOptionPane.showMessageDialog(null,"Não seleccionou nenhuma despesa!");
        }
        set_onlyaprov_option();
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        this.setLocationRelativeTo(null);
    }

    private void readReciboActionPerformed(java.awt.event.ActionEvent evt) {
        read_rebibo_function();
    }
    
    private void copy_buttonActionPerformed(java.awt.event.ActionEvent evt) {
        copiar_linhas();
    }
    
    private void copiar_linhas(){
    	this.excel_adapter_descritivo_despesas.set_copy_line();
    }

    private void deleteMenuActionPerformed(java.awt.event.ActionEvent evt) {
        int row = jTable1.getSelectedRow();
        int id = lista.get(row).get_id();
        int res = delete_from_dm(id);
        if (res==1){
            load_table();
        }
        else
        JOptionPane.showMessageDialog(null, "Erro ao eliminar despesa! Tente outra vez ou contacte administrador!");
    }

    private void changeMenuActionPerformed(java.awt.event.ActionEvent evt) {
        Despesa_new d = get_selected_expense();
        if (d!=null){
            boolean b = false;
            boolean despesa_rejeitada = false;
            if (d.get_data_aprovacao()==null)
                b=true;
            if (this.estados.containsKey(d.get_id()) && this.estados.get(d.get_id()) == 2)
                b=false;
            if (this.estados.containsKey(d.get_id()) && this.estados.get(d.get_id()) == 3)
            	despesa_rejeitada=true;
            //Alterar_despesa ce = new Alterar_despesa(username, d,b);
            Alterar_despesa ce = new Alterar_despesa(username, d,b,this.dm,despesa_rejeitada);
            ce.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) {}

                @Override
                public void windowClosing(WindowEvent we) {}

                @Override
                public void windowClosed(WindowEvent we) {
                    load_table();
                }
                
                @Override
                public void windowIconified(WindowEvent we) {}

                @Override
                public void windowDeiconified(WindowEvent we) {}

                @Override
                public void windowActivated(WindowEvent we) {}

                @Override
                public void windowDeactivated(WindowEvent we) {}
            });
            ce.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null,"Não seleccionou nenhuma despesa!");
        }
    }

    private void readRecibo1ActionPerformed(java.awt.event.ActionEvent evt) {
        read_rebibo_function();
    }

    private void viewMenuActionPerformed(java.awt.event.ActionEvent evt) {
        Despesa_new d = get_selected_expense();
        if (d!=null){
            //Alterar_despesa ce = new Alterar_despesa(username, d,false);
            Alterar_despesa ce = new Alterar_despesa(username, d,false,this.dm,false);
            ce.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) {}

                @Override
                public void windowClosing(WindowEvent we) {}

                @Override
                public void windowClosed(WindowEvent we) {
                    load_table();
                }

                @Override
                public void windowIconified(WindowEvent we) {}

                @Override
                public void windowDeiconified(WindowEvent we) {}

                @Override
                public void windowActivated(WindowEvent we) {}

                @Override
                public void windowDeactivated(WindowEvent we) {}
            });
            ce.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null,"Não seleccionou nenhuma despesa!");
        }
    }

    private void botao_enviarActionPerformed(java.awt.event.ActionEvent evt) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int res = save_despesas(true);
        if (res==0){
            this.dm.despesas_user_eliminadas = new ArrayList<>();
            JOptionPane.showMessageDialog(null, "Despesas submetidas com sucesso!");
        }
        else
            JOptionPane.showMessageDialog(null, "Erro ao enviar despesas, tente novamente ou contacte administrador!");
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void onlyNotAprovActionPerformed(java.awt.event.ActionEvent evt) {
    	set_onlyaprov_option();
    }

    private void set_onlyaprov_option(){
    	boolean b = onlyNotAprov.isSelected();
        only_aprov_table(b);
        String[] columnNames = {"Cliente","Projecto","Data transacção","Tipo","Data aprovação","Quantidade","Valor (€)","Valor Original","Notas","Etapa","Actividade"};
        jTable1.setModel(new ModeloTabelaDespesas(columnNames, lista, this.dm.get_projectos_user()));
        set_aproved();
        setTotais(get_totais_aprovados(),get_totais_rejeitados(),get_totais_em_espera());
    }
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        new Export_to_excel(this.username,month,year,1).setVisible(true);
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        jf.setMultiSelectionEnabled(false);
        int esc = jf.showOpenDialog(this);
        if (esc==JFileChooser.APPROVE_OPTION){
            File file = jf.getSelectedFile();
            read_file_from_import_xls(file);
        }  
    }
    
//    private void read_file_from_import(File f){
//        BufferedReader br;
//        String line;
//        int cont = 0;
//        boolean add_flag = true;
//        try{
//            br = new BufferedReader(new FileReader(f));
//            while (((line = br.readLine()) != null)){
//                if (cont==1 && add_flag)
//                {
//                    //saber username
//                    String[] aux_line = line.split(";");
//                    String username_file = aux_line[1];
//                    if (!username_file.equals(this.username))
//                    {
//                        add_flag = false;
//                        JOptionPane.showMessageDialog(null, "Username do ficheiro não é igual ao user logado! Apenas pode importar despesas relativas ao user logado!");
//                    }
//                }
//                else if (cont>3 && add_flag)
//                    parse_linha_add_despesa(line,(cont+1));
//                cont++;
//            }
//            load_table();
//        }
//        catch(IOException e)
//        {
//        	this.setCursor(Cursor.getDefaultCursor());
//        	new Log_erros_class().write_log_to_file(e);
//        }
//    }
    
    private void read_file_from_import_xls(File f){
    	WorkbookSettings ws = new WorkbookSettings();
    	ws.setEncoding("Cp1252");
        try {
          Workbook w = Workbook.getWorkbook(f,ws);
          // Get the first sheet
          Sheet sheet = w.getSheet(0);
          // Loop over first 10 column and lines
          boolean add_flag = true;
          for (int row = 0; row < sheet.getRows(); row++) {
        	  String tipo_despesa = "";
        	  String id_projecto = "";
        	  String cliente = "";
        	  String etapa = "";
        	  String atividade = "";
        	  String transacao = "";
        	  String quantidade = "";
        	  String valor = "";
        	  String moeda = "";
        	  String notas = "";
        	  for (int col = 0; col < sheet.getColumns(); col++) {
//              Cell cell = sheet.getCell(j, i);
//              CellType type = cell.getType();
//              if (type == CellType.LABEL) {
//                System.out.println("I got a label "
//                    + cell.getContents());
//              }
//
//              if (type == CellType.NUMBER) {
//                System.out.println("I got a number "
//                    + cell.getContents());
//              }
//	
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
                		  //tipo de despesa
                		  Cell cell = sheet.getCell(col,row);
                          tipo_despesa = cell.getContents();
                		  break;
                	  }
                	  case 1:{
                		  //cliente
                		  Cell cell = sheet.getCell(col,row);
                          cliente = cell.getContents();
                		  break;
                	  }
                	  case 2:{
                		  //id projecto
                		  Cell cell = sheet.getCell(col,row);
                          id_projecto = cell.getContents();
                		  break;
                	  }
                	  case 3:{
                		  //etapa
                		  Cell cell = sheet.getCell(col,row);
                          etapa = cell.getContents();
                		  break;
                	  }
                	  case 4:{
                		  //atividade
                		  Cell cell = sheet.getCell(col,row);
                          atividade = cell.getContents();
                		  break;
                	  }
                	  case 5:{
                		  //transacao
                		  Cell cell = sheet.getCell(col,row);
                          transacao = cell.getContents();
                		  break;
                	  }
                	  case 6:{
                		  //quantidade
                		  Cell cell = sheet.getCell(col,row);
                          quantidade = cell.getContents();
                		  break;
                	  }
                	  case 7:{
                		  //valor
                		  Cell cell = sheet.getCell(col,row);
                          valor = cell.getContents();
                		  break;
                	  }
                	  case 8:{
                		  //moeda
                		  Cell cell = sheet.getCell(col,row);
                          moeda = cell.getContents();
                		  break;
                	  }
                	  case 9:{
                		  //notas
                		  Cell cell = sheet.getCell(col,row);
                          notas = cell.getContents();
                		  break;
                	  }
                		  
                	  }
                  }
            }
            if (row>3 && add_flag)
            	parse_line_xls(tipo_despesa, id_projecto, cliente, etapa, atividade, transacao, quantidade, valor, moeda, notas,row);
          }
          
          
          
          
        } catch (BiffException | IOException e) {
          e.printStackTrace();
          this.setCursor(Cursor.getDefaultCursor());
          new Log_erros_class().write_log_to_file(e);
        } 
    }
    
    private void parse_line_xls(String tipo_despesa,String id_projecto, String cliente, String etapa, String atividade, String transacao, String quantidade, String valor_str, String moeda, String notas,int cont_linha){
    	Despesa_new d = new Despesa_new();
        d.set_username(this.username);
        //tipo despesa
        for (String t : this.dm.tipos_despesa)
        {
            String a1 = t.replace(" ", "").toUpperCase();
            String a2 = tipo_despesa.toUpperCase();
            if (a1.trim().equals(a2.trim()))
            {
                d.set_tipo(t);
            }
        }
        if (d.get_tipo().equals("") && !tipo_despesa.equals(""))
            JOptionPane.showMessageDialog(null,"Tipo de despesa: "+ tipo_despesa + " não existe. Linha: "+ cont_linha);
        //id projecto
        if (this.dm.get_projectos_user().containsKey(id_projecto.toUpperCase()))
        {
            Projecto p = this.dm.get_projectos_user().get(id_projecto.toUpperCase());
            d.set_id_projecto(p.get_codigo());
            d.set_nome_projecto(p.get_nome());
            
            //etapa
            for (Etapa e : p.get_etapas().values())
            {
                if (e.get_nome().trim().toUpperCase().equals(etapa.toUpperCase()))
                {
                    d.set_etapa(e.get_nome());
                }
            }
            if (d.get_etapa().equals("") && !etapa.equals(""))
                JOptionPane.showMessageDialog(null, "Não existe a etapa: " + etapa + "! Linha: " + cont_linha);
            
            //actividade
            for (Actividade a : p.get_actividades().values())
            {
                if (a.get_nome().trim().toUpperCase().equals(atividade.trim().toUpperCase()))
                {
                    d.set_actividade(a.get_nome());
                }
            }
            if (d.get_actividade().equals("") && !atividade.equals(""))
                JOptionPane.showMessageDialog(null, "Não existe a actividade: " + atividade + "! Linha: " + cont_linha);
                       
            //transacao
            Date data = get_data_from_string(transacao);
            d.set_data_despesa(data);
            
            int quant = Integer.valueOf(quantidade.trim());
            d.set_quantidade(quant);
            
            //valor
            Double valor = Double.valueOf(convert_number_str(valor_str.trim()));
            String valor_original = string_valor_original(moeda.trim().toUpperCase(),valor,data);
            valor = convert_valor(moeda.trim().toUpperCase(),valor,data);
            d.set_valor_euros(valor);
            d.set_valor_original(valor_original);
            
            //notas
            d.set_notas(notas);
            
            //id despesa
            d.set_id(choose_id());
            add_to_dm(d);
        }
        else
            JOptionPane.showMessageDialog(null, "Não existe projecto com id: " + id_projecto + "! Linha: "+cont_linha);
    }
    
//    private void parse_linha_add_despesa(String line, int cont_linha){
//        String tipo_despesa;
//        //String id_etapa = "";
//        //String id_actividade = "";
//        line = line.replace(";", " ;");
//        String[] aux_line = line.split(";");
//        
//        
//        Despesa_new d = new Despesa_new();
//        d.set_username(this.username);
//        //tipo de despesa
//        tipo_despesa = aux_line[0];
//        for (String t : this.dm.tipos_despesa)
//        {
//            String a1 = t.replace(" ", "").toUpperCase();
//            String a2 = tipo_despesa.toUpperCase();
//            if (a1.trim().equals(a2.trim()))
//            {
//                d.set_tipo(t);
//            }
//        }
//        if (d.get_tipo().equals("") && !tipo_despesa.equals(""))
//            JOptionPane.showMessageDialog(null,"Tipo de despesa: "+ tipo_despesa + " não existe. Linha: "+ cont_linha);
//        
//        String id_projecto = aux_line[2];
//        id_projecto = id_projecto.replace(" ", "").toUpperCase();
//        if (this.dm.get_projectos_user().containsKey(id_projecto))
//        {
//              Projecto p = this.dm.get_projectos_user().get(id_projecto);
//            d.set_id_projecto(p.get_codigo());
//            d.set_nome_projecto(p.get_nome());
//            
//            //etapa
//            String etapa_line = aux_line[3];
//            etapa_line = etapa_line.trim();
//            for (Etapa e : p.get_etapas().values())
//            {
//                if (e.get_nome().trim().toUpperCase().equals(etapa_line.toUpperCase()))
//                {
//                    d.set_etapa(e.get_nome());
//                }
//            }
//            if (d.get_etapa().equals("") && !etapa_line.equals(""))
//                JOptionPane.showMessageDialog(null, "Não existe a etapa: " + etapa_line + "! Linha: " + cont_linha);
////            
//            //actividade
//            String actividade_line = aux_line[4];
//            actividade_line = actividade_line.trim();
//            for (Actividade a : p.get_actividades().values())
//            {
//                if (a.get_nome().trim().toUpperCase().equals(actividade_line.toUpperCase()))
//                {
//                    d.set_actividade(a.get_nome());
//                }
//            }
//            if (d.get_actividade().equals("") && !actividade_line.equals(""))
//                JOptionPane.showMessageDialog(null, "Não existe a actividade: " + actividade_line + "! Linha: " + cont_linha);
//            
//            
////            
//            //transacao
//            String data_despesa = aux_line[5];
//            Date data = get_data_from_string(data_despesa);
//            d.set_data_despesa(data);
//            
//            String quantidade_str = aux_line[6];
//            int quant = Integer.valueOf(quantidade_str.trim());
//            d.set_quantidade(quant);
//            
//            //moeda
//            String moeda_str = aux_line[8].trim();
//            //valor
//            String valor_str = aux_line[7].trim();
//            Double valor = Double.valueOf(convert_number_str(valor_str.trim()));
//            String valor_original = string_valor_original(moeda_str,valor,data);
//            valor = convert_valor(moeda_str,valor,data);
//            
//            d.set_valor_euros(valor);
//            d.set_valor_original(valor_original);
//            
//            //notas
//            String notas = "";
//            try{
//            	notas = aux_line[9];
//            }
//            catch(Exception e){}
//            d.set_notas(notas);
//            
//            //id despesa
//            d.set_id(choose_id());
//            add_to_dm(d);
//        }
//        else
//            JOptionPane.showMessageDialog(null, "Não existe projecto com id: " + id_projecto + "! Linha: "+cont_linha);
//        
//    }
    
    private void add_to_dm(Despesa_new d){
        this.dm.despesas_user.add(0,d);
        this.dm.despesas_add_alteradas.add(d.get_id());
    }
    
    private int choose_id(){
        ArrayList<Integer> listaAux = new ArrayList<>();
        for (Despesa_new d : this.dm.despesas_user){
            listaAux.add(d.get_id());
        }
        int id = get_max_more(listaAux);
        return id;
    }
    
    private int get_max_more(ArrayList<Integer> lista){
        int imax = 0;
        for (int aux : lista){
            if (aux>imax){
                imax = aux;
            }
        }
        imax++;
        return imax;
    }
    
    private double convert_valor(String moeda, double valor,Date data){
        if (moeda.equals("USD"))
            valor = valor*get_taxa_usd_data(data);
        else if (moeda.equals("AKZ"))
            valor = valor*get_taxa_akz_data(data);
        return round(valor,2);
    }
    
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    private String string_valor_original(String moeda, double val,Date data){
    	if (moeda.equals("USD"))
            return val + " USD - Taxa: "+ get_taxa_usd_data(data);
        else if (moeda.equals("AKZ"))
            return val + " AKZ - Taxa: "+ get_taxa_akz_data(data);
        else
            return "";
    }
    
    private double get_taxa_usd_data(Date data){
    	double val = 0.0;
    	//get mes e ano
    	Calendar c = new GregorianCalendar();
    	c.setTime(data);
    	int mes = c.get(Calendar.MONTH) + 1;
    	int ano = c.get(Calendar.YEAR);
    	val = this.dm.get_rate_usd(mes,ano);
    	return val;
    }
    
    private double get_taxa_akz_data(Date data){
    	double val = 0.0;
    	//get mes e ano
    	Calendar c = new GregorianCalendar();
    	c.setTime(data);
    	int mes = c.get(Calendar.MONTH) + 1;
    	int ano = c.get(Calendar.YEAR);
    	val = this.dm.get_rate_akz(mes,ano);
    	return val;
    }
    
    private String convert_number_str(String aux){
    	return aux.replace(",",	".");
    }
    
    private Date get_data_from_string(String data_despesa){
    	Calendar c = Calendar.getInstance();
    	c.clear();
    	String[] data = data_despesa.split("-");
    	String dia_str = data[0];
    	String mes_str = data[1];
    	String ano_str = data[2];
    	int dia = Integer.valueOf(dia_str.trim());
    	int mes = Integer.valueOf(mes_str.trim());
    	mes--;
    	int ano = Integer.valueOf(ano_str.trim());
    	c.set(Calendar.DAY_OF_MONTH, dia);
    	c.set(Calendar.MONTH, mes);
    	c.set(Calendar.YEAR, ano);
    	return c.getTime();
    }
    
    private void only_aprov_table(boolean b){
        if (b){
            this.listaback = this.lista;
            ArrayList<Despesa_new> aux = new ArrayList<>();
            for (Despesa_new d : this.lista){
                int idx = d.get_id();
                if ((!this.estados.containsKey(idx)) || (this.estados.get(idx)!=2))
                    aux.add(d);
            }
            this.lista = aux;
        }
        else{
            this.lista = this.listaback;
        }
    }
    
    public int save_despesas(boolean backup){
        int res = 0;
        
        Backup_data_manager bdm = new Backup_data_manager(this.dm);
        if (backup){
        	bdm.save_backup_file();
        }
        
        try {
	        Connection con = (new Connection_bd()).get_connection();
	        if (con!=null){
		        for (Despesa_new d : this.lista){
		            if (this.dm.get_despesas_adicionadas_alteradas().contains(d.get_id()))
	            	{
		            	res += set_to_bd(d,con);
	            	}
		        }
		        for (int id : this.dm.despesas_rejeitadas_alteradas)
		        {
		        	res += change_db_despesas_rejeitadas(con,id);
		        }
		        
		        for (Despesa_new d : this.dm.despesas_user_eliminadas){
		            res += remove_from_bd(d,con);
		        }
		        if (res == 0){
	            	this.dm.despesas_user_eliminadas.clear();
	            	this.dm.despesas_add_alteradas.clear();
	            	this.dm.despesas_rejeitadas_alteradas.clear();
		        }
				con.close();
			}
	        else
	        	res++;
		} catch (SQLException e) {
			this.setCursor(Cursor.getDefaultCursor());
			new Log_erros_class().write_log_to_file(e);
			e.printStackTrace();
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
    
    private int change_db_despesas_rejeitadas(Connection con, int id){
    	try{
    		Calendar c = Calendar.getInstance();
    		java.sql.Date data = new java.sql.Date(c.getTime().getTime());
    		
    		String sql = "update tnm_handlepagamentos set situacao = ?, data = ? where id = ? and username = ?";
    		PreparedStatement ps = con.prepareStatement(sql);
    		ps.setInt(1, 4);
    		ps.setDate(2, data);
    		ps.setInt(3, id);
    		ps.setString(4, this.username);
    		ps.executeUpdate();
    		ps.close();
    		return 0;
    	}
    	catch(Exception e){
    		this.setCursor(Cursor.getDefaultCursor());
    		new Log_erros_class().write_log_to_file(e);
			e.printStackTrace();
			return 1;
    	}
    }
    
    @SuppressWarnings("resource")
	private int set_to_bd(Despesa_new d,Connection con){
        int ret = 0;
        try{
        PreparedStatement ps;
        String sql;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(d);
        oos.flush();
        oos.close();
        bos.close();
        byte[] data = bos.toByteArray();
        
        sql = "select * from tnm_despesas where id_despesa = ? and username = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, d.get_id());
        ps.setString(2, this.username);
        ResultSet res = ps.executeQuery();
        if (res.next()){
            sql="update tnm_despesas set despesa=? where id_despesa=? and username=?";
            ps=con.prepareStatement(sql);
            ps.setObject(1, data);
            ps.setInt(2, d.get_id());
            ps.setString(3, this.username);
            ps.executeUpdate();
        }
        else
        {
            sql="insert into tnm_despesas (despesa,id_despesa,username) values(?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setObject(1, data);
            ps.setInt(2, d.get_id());
            ps.setString(3, this.username);
            ps.executeUpdate();
        }
        }
        catch(IOException | SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            ret++;
        }
        return ret;
    }
    
    private int remove_from_bd(Despesa_new d,Connection con){
        int ret = 0;
        try{
        PreparedStatement ps;
        String sql;
        sql = "delete from tnm_despesas where id_despesa = ? and username = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, d.get_id());
        ps.setString(2, this.username);
        ps.executeUpdate();
        
        sql = "delete from tnm_handlepagamentos where id = ? and username = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, d.get_id());
        ps.setString(2, this.username);
        ps.executeUpdate();
        
        ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            ret++;
        }
        return ret;
    }

    
    private void read_rebibo_function(){
        Despesa_new d = get_selected_expense();
        if (d!=null){
            File f = d.get_recibo();
            if (f!=null){
                try{
                    Desktop.getDesktop().open(f);
                }
                catch(IOException e){
                    e.printStackTrace();
                    this.setCursor(Cursor.getDefaultCursor());
                    new Log_erros_class().write_log_to_file(e);
                    JOptionPane.showMessageDialog(null, "Erro ao abrir recibo!");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Não está associado nenhum recibo!");
            }
        }
    }
    
    private Despesa_new get_selected_expense(){
        int row = jTable1.getSelectedRow();
        if (row>=0){
            int id = lista.get(row).get_id();//Integer.parseInt(jTable1.getValueAt(row, 0).toString());
            for (Despesa_new d : this.lista){
                if (d.get_id()==id)
                    return d;
            }
        }
        return null;
        
    }
    
    private void load_table(){
        //get_from_bd();
        this.lista = this.dm.get_despesas_user();
        this.listaback = this.lista;
        //get_state_from_bd();
        this.estados = this.dm.get_estado_despesas_user();
        
        setTotais(get_totais_aprovados(),get_totais_rejeitados(),get_totais_em_espera());
        String[] columnNames = {"Cliente","Projecto","Data transacção","Tipo","Data aprovação","Quantidade","Valor (€)","Valor original","Notas","Etapa","Actividade"};
        jTable1.setModel(new ModeloTabelaDespesas(columnNames, lista, this.dm.get_projectos_user()));
        jTable1.getTableHeader().setReorderingAllowed(false);
        set_aproved();
    }
    
    private void exportar_dados_action(){
    	ArrayList<Despesa_new> lista_despesas;
    	if (this.listaback.size() > 0)
    		lista_despesas = this.listaback;
    	else
    		lista_despesas = this.lista;
    	TreeMap<String,TarefaHoras> lista_horas = null;
    	(new Export_user_info(this.dm,lista_despesas,lista_horas)).setVisible(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apLabel;
    private javax.swing.JButton botao_enviar;
    private javax.swing.JButton changeButton;
    private javax.swing.JMenuItem changeMenu;
    private javax.swing.JButton deleteButton;
    private javax.swing.JMenuItem deleteMenu;
    private javax.swing.JLabel esLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JFileChooser jf;
    private javax.swing.JCheckBox onlyNotAprov;
    private javax.swing.JPopupMenu popMenu1;
    private javax.swing.JPopupMenu popMenu2;
    private javax.swing.JMenuItem readRecibo;
    private javax.swing.JMenuItem copy_button;
    private javax.swing.JMenuItem readRecibo1;
    private javax.swing.JLabel rjLabel;
    private javax.swing.JLabel totLabel;
    private javax.swing.JLabel userLabel;
    private javax.swing.JMenuItem viewMenu;
    private JMenuItem mntmExportarDados;
    // End of variables declaration//GEN-END:variables
    
}

