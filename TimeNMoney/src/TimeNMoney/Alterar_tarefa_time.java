/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;


/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Alterar_tarefa_time extends javax.swing.JFrame {
    TarefaHoras tarefa;
    String username;
    TreeMap<String,Etapa> lista_etapas;
    TreeMap<String,Actividade> lista_actividades;
    TreeMap<String,Tarefa> lista_tarefas;
    TreeMap<String,Projecto> lista_projectos;
    String id_etapa;    
    String id_actividade;
    String id_tarefax;
    String id_local;    
    String id_projecto;
    String old_id;
    Calendar calendario;
    Data_manager dm;
    
    public Alterar_tarefa_time(Calendar cal, TarefaHoras t, String username, Data_manager dm) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.id_etapa = "";    
        this.id_actividade = "";
        this.id_tarefax = "";
        this.id_local = "";    
        this.id_projecto = "";
        this.tarefa = t;
        this.dm = dm;
        this.username = username;
        this.lista_etapas = new TreeMap<>();
        this.lista_actividades = new TreeMap<>();
        this.lista_tarefas = new TreeMap<>();
        this.lista_projectos = new TreeMap<>();
        this.old_id = t.get_id();
        this.calendario = (Calendar) cal.clone();
        calendar_to_begin_of_week();
        //carrega_combo_boxes_from_bd();
        carrega_combo_boxes_from_dm();
        //carrega_id_local();
        preenche_campos();
        
    }
    
    private void calendar_to_begin_of_week(){
        this.calendario.add(Calendar.DAY_OF_MONTH, -6);
    }
    
    @SuppressWarnings("unchecked")
	private void preenche_campos(){
        Projecto aux = this.dm.get_projectos_user().get(this.tarefa.get_id_projecto());
        String cliente = aux.get_cliente().get_nome();
        if (combo_box_cliente.getItemCount()>0)
            combo_box_cliente.setSelectedItem(cliente);
        
        //projField.setText(this.tarefa.get_projecto());
        int proj_preenchido = 0;
        for (Projecto p : lista_projectos.values()){
            if ((p.get_codigo()).equals(this.tarefa.get_id_projecto())){
                    projecto_combo_box.setSelectedItem(p.get_codigo() + " - " +p.get_nome());
                    proj_preenchido = 1;
            }
        }
        if (proj_preenchido==0){
            projecto_combo_box.addItem(this.tarefa.get_id_projecto() + " - " + this.tarefa.get_nome_projecto());
            projecto_combo_box.setSelectedItem(this.tarefa.get_id_projecto() + " - " + this.tarefa.get_nome_projecto());
        }
        
        int etapa_preenchida = 0;
        for (Etapa e : lista_etapas.values()){
            if (e.get_nome().equals(this.tarefa.get_etapa())){
                etapa_combo_box.setSelectedItem(e.get_nome());
                etapa_preenchida = 1;
            }
        }
        descEtapaField.setText(this.tarefa.get_desc_etapa());
        if (etapa_preenchida == 0){
            etapa_combo_box.addItem(this.tarefa.get_etapa());
            etapa_combo_box.setSelectedItem(this.tarefa.get_etapa());
        }
        
        int act_preenchida = 0;
        for (Actividade a : lista_actividades.values()){
            if (a.get_nome().equals(this.tarefa.get_actividade())){
                actividade_combo_box.setSelectedItem(a.get_nome());
                act_preenchida = 1;
            }
        }
        descActField.setText(this.tarefa.get_desc_actividade());
        if (act_preenchida == 0){
            actividade_combo_box.addItem(this.tarefa.get_actividade());
            actividade_combo_box.setSelectedItem(this.tarefa.get_actividade());
        }
        
        int tarefa_preenchida = 0;
        for (Tarefa t : lista_tarefas.values()){
            if (t.get_nome().equals(this.tarefa.get_tarefa())){
                tarefa_combo_box.setSelectedItem(t.get_nome());
                tarefa_preenchida = 1;
            }
        }
        descTarefaField.setText(this.tarefa.get_desc_tarefa());
        if (tarefa_preenchida == 0){
            tarefa_combo_box.addItem(this.tarefa.get_tarefa());
            tarefa_combo_box.setSelectedItem(this.tarefa.get_tarefa());
        }
        
        if (this.tarefa.get_local().equals("Angola"))
            local_combo_box.setSelectedItem("Angola");
        else if (this.tarefa.get_local().equals(""))
        {
            local_combo_box.addItem("");
            local_combo_box.setSelectedItem("");
        }
        else
            local_combo_box.setSelectedItem("Portugal");
        
        idField.setText(this.tarefa.get_id());
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        idField = new javax.swing.JTextField();
        descEtapaField = new javax.swing.JTextField();
        descActField = new javax.swing.JTextField();
        descTarefaField = new javax.swing.JTextField();
        etapa_combo_box = new javax.swing.JComboBox();
        actividade_combo_box = new javax.swing.JComboBox();
        tarefa_combo_box = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        local_combo_box = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        projecto_combo_box = new javax.swing.JComboBox();
        add_tarefa_projecto = new javax.swing.JButton();
        combo_box_cliente = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        setMinimumSize(new java.awt.Dimension(530, 520));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/Cancel-icon.png"))); // NOI18N
        cancelButton.setText("Cancelar");
        cancelButton.setMaximumSize(new java.awt.Dimension(120, 40));
        cancelButton.setMinimumSize(new java.awt.Dimension(120, 40));
        cancelButton.setPreferredSize(new java.awt.Dimension(120, 40));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/saveicon.png"))); // NOI18N
        saveButton.setText("Guardar");
        saveButton.setMaximumSize(new java.awt.Dimension(120, 40));
        saveButton.setMinimumSize(new java.awt.Dimension(120, 40));
        saveButton.setPreferredSize(new java.awt.Dimension(120, 40));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("ID:");

        jLabel2.setText("Projecto:");

        jLabel3.setText("Etapa:");

        jLabel4.setText("Descrição da Etapa:");

        jLabel5.setText("Actividade:");

        jLabel6.setText("Descrição da Actividade:");

        jLabel7.setText("Tarefa:");

        jLabel8.setText("Descrição da Tarefa:");

        descEtapaField.setEditable(false);

        descActField.setEditable(false);

        descTarefaField.setEditable(false);

        etapa_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etapa_combo_boxActionPerformed(evt);
            }
        });

        actividade_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actividade_combo_boxActionPerformed(evt);
            }
        });

        tarefa_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tarefa_combo_boxActionPerformed(evt);
            }
        });

        jLabel9.setText("Local");

        local_combo_box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Portugal", "Angola" }));
        local_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                local_combo_boxActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/task_icon.png"))); // NOI18N
        jLabel10.setText("Alterar Tarefa");

        projecto_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projecto_combo_boxActionPerformed(evt);
            }
        });

        add_tarefa_projecto.setText("+");
        add_tarefa_projecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_tarefa_projectoActionPerformed(evt);
            }
        });

        combo_box_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_clienteActionPerformed(evt);
            }
        });

        jLabel11.setText("Cliente:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(36)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jLabel10, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        						.addComponent(jLabel9, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        						.addComponent(jLabel11, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        						.addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        						.addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        						.addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        						.addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        						.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
        							.addGap(21)
        							.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
        						.addComponent(etapa_combo_box, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(idField)
        						.addComponent(local_combo_box, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(descEtapaField)
        						.addComponent(actividade_combo_box, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(descActField)
        						.addComponent(descTarefaField)
        						.addComponent(projecto_combo_box, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(tarefa_combo_box, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(add_tarefa_projecto))
        						.addComponent(combo_box_cliente, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        			.addGap(35))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel10, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel1))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(combo_box_cliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel11))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel2)
        				.addComponent(projecto_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(etapa_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel3))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel4)
        				.addComponent(descEtapaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(actividade_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel5))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(descActField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel6))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tarefa_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel7)
        				.addComponent(add_tarefa_projecto))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(descTarefaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel8))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(local_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel9))
        			.addGap(33)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
        				.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
        			.addGap(29))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        TarefaHoras tarefa_nova = new TarefaHoras(this.tarefa);
        if (!idField.getText().equals(tarefa_nova.get_id()))
            tarefa_nova.set_id(idField.getText());
        if ((projecto_combo_box.getItemCount()>0) && (!projecto_combo_box.getSelectedItem().toString().equals(this.tarefa.get_id_projecto() + " - " + this.tarefa.get_nome_projecto()))){
            String proj = projecto_combo_box.getSelectedItem().toString();
            String[] aux = proj.split(" - ");
            tarefa_nova.set_id_projecto(aux[0]);
            tarefa_nova.set_nome_projecto(proj.replace((aux[0]+" - "),""));
        }
        if ((etapa_combo_box.getItemCount()>0) && (!etapa_combo_box.getSelectedItem().toString().equals(tarefa_nova.get_etapa())))
            tarefa_nova.set_etapa(etapa_combo_box.getSelectedItem().toString());
        else if (etapa_combo_box.getItemCount()==0)
            tarefa_nova.set_etapa("");
        if (!descEtapaField.getText().equals(tarefa_nova.get_desc_etapa()))
            tarefa_nova.set_desc_etapa(descEtapaField.getText());
//        else
//            tarefa_nova.set_desc_etapa("");
        if ( (actividade_combo_box.getItemCount()>0) && (!actividade_combo_box.getSelectedItem().toString().equals(tarefa_nova.get_actividade())))
            tarefa_nova.set_actividade(actividade_combo_box.getSelectedItem().toString());
        else if (actividade_combo_box.getItemCount()==0)
            tarefa_nova.set_actividade("");
        if (!descActField.getText().equals(tarefa_nova.get_actividade()))
            tarefa_nova.set_desc_actividade(descActField.getText());
//        else
//            tarefa_nova.set_desc_actividade("");
        if ((tarefa_combo_box.getItemCount()>0) && (!tarefa_combo_box.getSelectedItem().toString().equals(tarefa_nova.get_tarefa())))
            tarefa_nova.set_tarefa(tarefa_combo_box.getSelectedItem().toString());
        else if (tarefa_combo_box.getItemCount()==0)
            tarefa_nova.set_tarefa("");
        if (!descTarefaField.getText().equals(tarefa_nova.get_desc_tarefa()))
            tarefa_nova.set_desc_tarefa(descTarefaField.getText());
//        else
//            tarefa_nova.set_desc_tarefa("");
        if (!local_combo_box.getSelectedItem().toString().equals(tarefa_nova.get_local()))
                tarefa_nova.set_local(local_combo_box.getSelectedItem().toString());
        try {
            set_to_bd(this.old_id,tarefa_nova.get_id(),tarefa_nova);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
        this.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void carrega_combo_etapas(){
        try{
        
        if (etapa_combo_box.getItemCount()>0){   
            String etapa_aux = etapa_combo_box.getSelectedItem().toString();
            for (Etapa et : this.lista_etapas.values())
                if (et.get_nome().equals(etapa_aux)){
                   descEtapaField.setText(et.get_descricao());
                   this.id_etapa = et.get_id();
                }
        }
        else{
            descEtapaField.setText("");
            this.id_etapa = "";
        }
        actualiza_id_geral();
        }
        catch(NullPointerException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
    }
    
    private void etapa_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etapa_combo_boxActionPerformed
        if (etapa_combo_box.getItemCount()>0)
            carrega_combo_etapas();
    }//GEN-LAST:event_etapa_combo_boxActionPerformed

    private void carrega_combo_actividades(){
        try{
        if (actividade_combo_box.getItemCount()>0){   
        
            String actividade_aux = actividade_combo_box.getSelectedItem().toString();
            for (Actividade at : this.lista_actividades.values())
                if (at.get_nome().equals(actividade_aux)){
                   descActField.setText(at.get_descricao());
                   this.id_actividade = at.get_id();
                }
        }
        else{
            descActField.setText("");
            this.id_actividade = "";
        }
        actualiza_id_geral();
        }
        catch(NullPointerException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
    }
    
    private void actividade_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actividade_combo_boxActionPerformed
        if (actividade_combo_box.getItemCount()>0)
            carrega_combo_actividades();
    }//GEN-LAST:event_actividade_combo_boxActionPerformed

    private void tarefa_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tarefa_combo_boxActionPerformed
        if (tarefa_combo_box.getItemCount()>0)
            carrega_combo_tarefas();
    }//GEN-LAST:event_tarefa_combo_boxActionPerformed

    private void carrega_combo_tarefas(){
        try{
        if (tarefa_combo_box.getItemCount()>0)
        {
            String tarefa_aux = tarefa_combo_box.getSelectedItem().toString();
            for (Tarefa ta : this.lista_tarefas.values())
                if (ta.get_nome().equals(tarefa_aux)){
                   descTarefaField.setText(ta.get_descricao());
                   this.id_tarefax = ta.get_id();
                }
        }
        else{
            descTarefaField.setText("");
            this.id_tarefax = "";
        }
        actualiza_id_geral();
        }catch(Exception e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
    }
    
    private void local_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_local_combo_boxActionPerformed
        carrega_id_local();
    }//GEN-LAST:event_local_combo_boxActionPerformed

    private void projecto_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projecto_combo_boxActionPerformed
        if (projecto_combo_box.getItemCount()>0)
           carrega_combo_projectos();
    }//GEN-LAST:event_projecto_combo_boxActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void add_tarefa_projectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_tarefa_projectoActionPerformed
        Adicionar_tarefa_a_projecto ad = new Adicionar_tarefa_a_projecto(this.lista_projectos.get(this.id_projecto), this.dm.lista_tarefa_total, this.dm);
        ad.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent we) { }
            @Override
            public void windowClosing(WindowEvent we) { }
            @Override
            public void windowClosed(WindowEvent we) {
                //carrega_combo_projectos();
                carrega_combo_projectos_tarefa();
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
    }//GEN-LAST:event_add_tarefa_projectoActionPerformed

    private void combo_box_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_clienteActionPerformed
        carrega_combo_clientes();
    }//GEN-LAST:event_combo_box_clienteActionPerformed

    @SuppressWarnings("unchecked")
	private void carrega_combo_clientes(){
        String cliente = combo_box_cliente.getSelectedItem().toString();
        TreeMap<String,Projecto> projectos = new TreeMap<>();
        projecto_combo_box.removeAllItems();
        for (Projecto p : this.dm.get_projectos_user().values())
            if (p.get_cliente().get_nome().equals(cliente))
            {
                projectos.put(p.get_codigo(),p);
                projecto_combo_box.addItem(p.get_codigo() + " - "+ p.get_nome());
            }
        this.lista_projectos = projectos;
        if (projecto_combo_box.getItemCount()>0)
        {
            projecto_combo_box.setSelectedIndex(0);
        }
        actualiza_id_geral();
        //carrega_combo_projectos();
    }
    
    @SuppressWarnings("unchecked")
	private void carrega_combo_projectos(){
        try{
        	TreeMap<String,Etapa> etapas;        
        	TreeMap<String,Actividade> actividades;
        	TreeMap<String,Tarefa> tarefas;
        etapa_combo_box.removeAllItems();
        actividade_combo_box.removeAllItems();
        tarefa_combo_box.removeAllItems();
        String[] projecto_aux_lista;
        String projecto_aux = "";
        if (projecto_combo_box.getItemCount()>0)
        {
            projecto_aux_lista = projecto_combo_box.getSelectedItem().toString().split(" - ");
            projecto_aux = projecto_aux_lista[0];
        }
        for (Projecto pr : this.lista_projectos.values())
            if (pr.get_codigo().equals(projecto_aux)){
               //descTarefaField.setText(ta.get_descricao());
               this.id_projecto = pr.get_codigo();
                etapas = new TreeMap<>(pr.get_etapas());
                actividades = new TreeMap<>(pr.get_actividades());
                tarefas = new TreeMap<>(pr.get_tarefas());
                for (Etapa e : etapas.values())
                    etapa_combo_box.addItem(e.get_nome());

                for (Actividade a : actividades.values())
                    actividade_combo_box.addItem(a.get_nome());

                for (Tarefa t : tarefas.values())
                    tarefa_combo_box.addItem(t.get_nome());
                this.lista_etapas = etapas;
                this.lista_actividades = actividades;
                this.lista_tarefas = tarefas;
                if (etapa_combo_box.getItemCount()>0)
                {
                    etapa_combo_box.setEnabled(true);
                    etapa_combo_box.setSelectedIndex(0);
                    carrega_combo_etapas();
                }
                else{
                    etapa_combo_box.setEnabled(false);
                    carrega_combo_etapas();
                }
                
                if (actividade_combo_box.getItemCount()>0)
                {
                    actividade_combo_box.setEnabled(true);
                    actividade_combo_box.setSelectedIndex(0);
                    carrega_combo_actividades();
                }
                else{
                    actividade_combo_box.setEnabled(false);
                    carrega_combo_actividades();
                }
                
                if (tarefa_combo_box.getItemCount()>0)
                {
                    tarefa_combo_box.setEnabled(true);
                    tarefa_combo_box.setSelectedIndex(0);
                    carrega_combo_tarefas();
                }
                else{
                    tarefa_combo_box.setEnabled(false);
                    carrega_combo_tarefas();
                }
            }
        actualiza_id_geral();
        }
        catch(NullPointerException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
    }
    
    @SuppressWarnings("unchecked")
	private void carrega_combo_projectos_tarefa(){
        try{
        	TreeMap<String,Tarefa> tarefas;
        tarefa_combo_box.removeAllItems();
        
        String[] projecto_aux_lista;
        String projecto_aux = "";
        int icount = projecto_combo_box.getItemCount();
        if (icount>0)
        {
            projecto_aux_lista = projecto_combo_box.getSelectedItem().toString().split(" - ");
            projecto_aux = projecto_aux_lista[0];
        }
        for (Projecto pr : this.lista_projectos.values())
            if (pr.get_codigo().equals(projecto_aux)){
               //descTarefaField.setText(ta.get_descricao());
                tarefas = new TreeMap<>(pr.get_tarefas());
        
                for (Tarefa t : tarefas.values())
                    tarefa_combo_box.addItem(t.get_nome());
                this.lista_tarefas = tarefas;
                
                if (tarefa_combo_box.getItemCount()>0)
                {
                    tarefa_combo_box.setEnabled(true);
                    tarefa_combo_box.setSelectedIndex(0);
                    carrega_combo_tarefas();
                }
                else
                    tarefa_combo_box.setEnabled(false);
                
            }
        actualiza_id_geral();
        }
        catch(NullPointerException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
    }
    
    private void carrega_id_local(){
         if (local_combo_box.getSelectedItem().toString().equals("Portugal"))
            this.id_local = "PT";
        else
            this.id_local = "AO";
        actualiza_id_geral();
    }
    
    @SuppressWarnings("unchecked")
	private void carrega_combo_boxes_from_dm(){
        ArrayList<Cliente> clientes;
        TreeMap<String,Etapa> etapas;        
        TreeMap<String,Actividade> actividades;
        TreeMap<String,Tarefa> tarefas;
        TreeMap<String,Projecto> projectos;
        
        clientes = this.dm.clientes;
        projectos = new TreeMap<>();
        if (!clientes.isEmpty())
        {
            for (Cliente c : clientes)
                combo_box_cliente.addItem(c.get_nome());
            
            for (Projecto p : this.dm.get_projectos_user().values())
                if (p.get_cliente().get_nome().equals(combo_box_cliente.getSelectedItem().toString()))
                    projectos.put(p.get_codigo(),p);
        }
        //projectos = this.dm.get_projectos_user();
        if (!projectos.isEmpty())
        {
//            for (Projecto p : projectos.values()){
//                projecto_combo_box.addItem(p.get_codigo() + " - "+ p.get_nome());
//            }

            String aux_id = projecto_combo_box.getSelectedItem().toString();
            String[] lista_id_aux = aux_id.split(" - ");
            aux_id = lista_id_aux[0];
            Projecto aux_p = projectos.get(aux_id);
            etapas = new TreeMap<>(aux_p.get_etapas());
            actividades = new TreeMap<>(aux_p.get_actividades());
            tarefas = new TreeMap<>(aux_p.get_tarefas());
            for (Etapa e : etapas.values())
                etapa_combo_box.addItem(e.get_nome());

            for (Actividade a : actividades.values())
                actividade_combo_box.addItem(a.get_nome());
            
            for (Tarefa t : tarefas.values())
                tarefa_combo_box.addItem(t.get_nome());

            //adicionar arrays na class
            this.lista_etapas = etapas;
            this.lista_actividades = actividades;
            this.lista_tarefas = tarefas;
            this.lista_projectos = projectos;
        }
        else{
            JOptionPane.showMessageDialog(null, "Não existem projectos associados ao seu username!");
            //this.close_flag = 1;
        }
    }
    
    private void actualiza_id_geral(){
        String id =  this.id_projecto + "_" + this.id_etapa +"."+ this.id_actividade +"."+ this.id_tarefax + "_" + this.id_local;
        idField.setText(id);
    }
    
    private void set_to_bd(String old_id, String id, TarefaHoras t) throws ClassNotFoundException, SQLException, IOException{
        if (!old_id.equals(id)){
            atualizar_com_id_diferente(t);
        }
    }
    
    private void atualizar_com_id_diferente(TarefaHoras t){
        update_data_week_existe(t);
    }
    
    private void update_data_week_existe(TarefaHoras t){
        HashMap<Date, Double> aux = new HashMap<>();
        int i = 0;
        Calendar cal_iterator = this.calendario;
        while(i<7){
            Date data_iterator = cal_iterator.getTime();
            if (this.tarefa.get_map().containsKey(data_iterator)){
                aux.put(data_iterator, this.tarefa.get_map().get(data_iterator));
                this.tarefa.get_map().remove(data_iterator);
            }
            cal_iterator.add(Calendar.DAY_OF_MONTH, 1);
            i++;
        }
        t.set_map(aux);

            //save t na bd
            update_new_tarefa(t);
       
            //update this.tarefa bd
            update_old_tarefa(this.tarefa);
    }
    
    @SuppressWarnings("unused")
	private void update_old_tarefa(TarefaHoras t){
        TarefaHoras taux = this.dm.get_lista_tarefas_horas_user().get(t.get_id());
        taux = t;
    }
    
    private void update_new_tarefa(TarefaHoras t){
        TarefaHoras taux = this.dm.get_lista_tarefas_horas_user().get(t.get_id());
        if (taux!=null){
            HashMap<Date,Double> mapaux = taux.get_map();
            for (Date d : t.get_map().keySet()){
                if (!mapaux.containsKey(d))
                {
                    mapaux.put(d,t.get_map().get(d));
                }
                else{
                    double conta = t.get_map().get(d) + mapaux.get(d);
                    if (conta>24)
                        conta = 24;
                    mapaux.put(d,conta);
                }
            }
            taux.set_map(mapaux);
        }
        else{
            this.dm.get_lista_tarefas_horas_user().put(t.get_id(), t);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox actividade_combo_box;
    private javax.swing.JButton add_tarefa_projecto;
    private javax.swing.JButton cancelButton;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox combo_box_cliente;
    private javax.swing.JTextField descActField;
    private javax.swing.JTextField descEtapaField;
    private javax.swing.JTextField descTarefaField;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox etapa_combo_box;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox local_combo_box;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox projecto_combo_box;
    private javax.swing.JButton saveButton;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox tarefa_combo_box;
    // End of variables declaration//GEN-END:variables
}
