package TimeNMoney;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
public class Adicionar_tarefa_time extends javax.swing.JFrame {
    Calendar calendario;
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
    Data_manager dm;


    
    public Adicionar_tarefa_time(Calendar cal, String username, Data_manager dm) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.id_etapa = "";    
        this.id_actividade = "";
        this.id_tarefax = "";
        this.id_local = "";    
        this.id_projecto = "";
        this.calendario = cal;
        this.username = username;
        this.lista_etapas = new TreeMap<>();
        this.lista_actividades = new TreeMap<>();
        this.lista_tarefas = new TreeMap<>();
        this.lista_projectos = new TreeMap<>();
        this.dm = dm;
        //carrega_combo_boxes_from_bd();
        carrega_combo_boxes_from_dm();
        carrega_id_local();
        idField.setEditable(false);
    }
    
    public Adicionar_tarefa_time(Calendar cal, String username, Data_manager dm, TarefaHoras t) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.id_etapa = "";    
        this.id_actividade = "";
        this.id_tarefax = "";
        this.id_local = "";    
        this.id_projecto = "";
        this.calendario = cal;
        this.username = username;
        this.lista_etapas = new TreeMap<>();
        this.lista_actividades = new TreeMap<>();
        this.lista_tarefas = new TreeMap<>();
        this.lista_projectos = new TreeMap<>();
        this.dm = dm;
        //carrega_combo_boxes_from_bd();
        carrega_combo_boxes_from_dm();
        carrega_id_local();
        set_lista_projectos();
        idField.setEditable(false);
        preenche_campos(t);
    }
    
	private void preenche_campos(TarefaHoras t){
		Projecto aux = lista_projectos.get(t.get_id_projecto());
       	//select cliente
        String cliente = aux.get_cliente().get_nome();
       	combo_box_cliente.setSelectedItem(cliente);
       	
       	//select projecto
       	String proj = aux.get_codigo() + " - " + aux.get_nome();
       	projecto_combo_box.setSelectedItem(proj);
       	
       	//select etapa
       	etapa_combo_box.setSelectedItem(t.get_etapa());
       	descEtapaField.setText(t.get_desc_etapa());
       	
       	//select actividade
       	actividade_combo_box.setSelectedItem(t.get_actividade());
       	descActField.setText(t.get_desc_actividade());
       	
       	//select tarefa
       	tarefa_combo_box.setSelectedItem(t.get_tarefa());
       	descTarefaField.setText(t.get_desc_tarefa());
       	
       	//local
       	switch(t.get_local())
       	{
       	case "Angola":
       		local_combo_box.setSelectedItem("Angola");
       	default: 
       		local_combo_box.setSelectedItem("Portugal");
       	}
    }
	
	private void set_lista_projectos(){
        this.lista_projectos = this.dm.lista_projectos_user;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
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
        jLabel9 = new javax.swing.JLabel();
        tarefa_combo_box = new javax.swing.JComboBox();
        etapa_combo_box = new javax.swing.JComboBox();
        actividade_combo_box = new javax.swing.JComboBox();
        local_combo_box = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        projecto_combo_box = new javax.swing.JComboBox();
        add_tarefa_projecto = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        combo_box_cliente = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
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

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/saveicon.png"))); // NOI18N
        addButton.setText("Guardar");
        addButton.setMaximumSize(new java.awt.Dimension(120, 40));
        addButton.setMinimumSize(new java.awt.Dimension(120, 40));
        addButton.setPreferredSize(new java.awt.Dimension(120, 40));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
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

        jLabel9.setText("Local");

        tarefa_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tarefa_combo_boxActionPerformed(evt);
            }
        });

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

        local_combo_box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Portugal", "Angola" }));
        local_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                local_combo_boxActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/task_icon.png"))); // NOI18N
        jLabel10.setText("Adicionar Tarefa");

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

        jLabel11.setText("Cliente:");

        combo_box_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_clienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(36)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        					.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel10)
        					.addGap(0, 305, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        							.addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
        							.addGroup(layout.createSequentialGroup()
        								.addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addPreferredGap(ComponentPlacement.RELATED)))
        						.addGroup(layout.createSequentialGroup()
        							.addGap(0)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(jLabel11, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
        								.addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))))
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addComponent(descTarefaField, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(tarefa_combo_box, 0, 291, Short.MAX_VALUE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(add_tarefa_projecto))
        						.addComponent(descActField, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        						.addComponent(actividade_combo_box, 0, 338, Short.MAX_VALUE)
        						.addComponent(descEtapaField, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        						.addComponent(etapa_combo_box, 0, 338, Short.MAX_VALUE)
        						.addComponent(local_combo_box, 0, 338, Short.MAX_VALUE)
        						.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        							.addGap(1)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(projecto_combo_box, Alignment.TRAILING, 0, 337, Short.MAX_VALUE)
        								.addComponent(combo_box_cliente, Alignment.TRAILING, 0, 337, Short.MAX_VALUE)
        								.addComponent(idField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))))))
        			.addGap(29))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel10)
        			.addGap(40)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel1))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel11)
        				.addComponent(combo_box_cliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(projecto_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel2))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(etapa_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel3))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(descEtapaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel4))
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
        				.addComponent(jLabel7)
        				.addComponent(tarefa_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(add_tarefa_projecto))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel8)
        				.addComponent(descTarefaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel9)
        				.addComponent(local_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
        				.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (idField.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Preencha o ID");
        else{
            TarefaHoras t = new TarefaHoras();
            t.set_id(idField.getText());
            if (projecto_combo_box.getItemCount()>0){
                t.set_id_projecto(projecto_combo_box.getSelectedItem().toString().split(" - ")[0]);
                t.set_nome_projecto(projecto_combo_box.getSelectedItem().toString().replace((t.get_id_projecto() + " - "), ""));
            }
            else{
                t.set_id_projecto("");
                t.set_nome_projecto("");
            }
            if (etapa_combo_box.getItemCount()>0)
                t.set_etapa(etapa_combo_box.getSelectedItem().toString());
            else
                t.set_etapa("");
            t.set_desc_etapa(descEtapaField.getText());
            if (actividade_combo_box.getItemCount()>0)
                t.set_actividade(actividade_combo_box.getSelectedItem().toString());
            else
                t.set_actividade("");
            t.set_desc_actividade(descActField.getText());
            if (tarefa_combo_box.getItemCount()>0)
                t.set_tarefa(tarefa_combo_box.getSelectedItem().toString());
            else
                t.set_tarefa("");
            t.set_desc_tarefa(descTarefaField.getText());
            t.set_funcionario(username);
            t.set_local(local_combo_box.getSelectedItem().toString());
            Date d = this.calendario.getTime();
            HashMap<Date,Double> map = new HashMap<>();
            map.put(d,0.0);
            t.set_map(map);
            set_to_dm(idField.getText(), t);
            //set_to_bd(idField.getText(), t);
            this.dispose();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_addButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void carrega_combo_etapas(){
        try{
        String etapa_aux = etapa_combo_box.getSelectedItem().toString();
        for (Etapa et : this.lista_etapas.values())
            if (et.get_nome().equals(etapa_aux)){
               descEtapaField.setText(et.get_descricao());
               this.id_etapa = et.get_id();
            }
        actualiza_id_geral();
        }
        catch(NullPointerException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    private void etapa_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etapa_combo_boxActionPerformed
        if (etapa_combo_box.getItemCount()>0)
            carrega_combo_etapas();
    }//GEN-LAST:event_etapa_combo_boxActionPerformed

    private void carrega_combo_actividades(){
        try{
        String actividade_aux = actividade_combo_box.getSelectedItem().toString();
        for (Actividade at : this.lista_actividades.values())
            if (at.get_nome().equals(actividade_aux)){
               descActField.setText(at.get_descricao());
               this.id_actividade = at.get_id();
            }
        actualiza_id_geral();
        }
        catch(NullPointerException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    private void actividade_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actividade_combo_boxActionPerformed
        if (actividade_combo_box.getItemCount()>0)
            carrega_combo_actividades();
    }//GEN-LAST:event_actividade_combo_boxActionPerformed

    private void carrega_combo_tarefas(){
        try{
        String tarefa_aux = tarefa_combo_box.getSelectedItem().toString();
        for (Tarefa ta : this.lista_tarefas.values())
            if (ta.get_nome().equals(tarefa_aux)){
               descTarefaField.setText(ta.get_descricao());
               this.id_tarefax = ta.get_id();
            }
        actualiza_id_geral();
        }catch(Exception e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    private void tarefa_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tarefa_combo_boxActionPerformed
        if (tarefa_combo_box.getItemCount()>0)
            carrega_combo_tarefas();
    }//GEN-LAST:event_tarefa_combo_boxActionPerformed

    private void local_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_local_combo_boxActionPerformed
        carrega_id_local();
    }//GEN-LAST:event_local_combo_boxActionPerformed

    private void projecto_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projecto_combo_boxActionPerformed
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
        int icount = projecto_combo_box.getItemCount();
        if (icount>0)
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
                this.lista_etapas = new TreeMap<>(etapas);
                this.lista_actividades = new TreeMap<>(actividades);
                this.lista_tarefas = new TreeMap<>(tarefas);
                if (etapa_combo_box.getItemCount()>0)
                {
                    etapa_combo_box.setEnabled(true);
                    etapa_combo_box.setSelectedIndex(0);
                    carrega_combo_etapas();
                }
                else
                    etapa_combo_box.setEnabled(false);
                
                if (actividade_combo_box.getItemCount()>0)
                {
                    actividade_combo_box.setEnabled(true);
                    actividade_combo_box.setSelectedIndex(0);
                    carrega_combo_actividades();
                }
                else
                    actividade_combo_box.setEnabled(false);
                
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
            new Log_erros_class().write_log_to_file(this.username,e);
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
            new Log_erros_class().write_log_to_file(this.username,e);
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
        
        if (!projectos.isEmpty())
        {

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
            if (this.lista_projectos.size()>0)
                carrega_combo_projectos();
            if (this.lista_etapas.size()>0)
                carrega_combo_etapas();
            if (this.lista_actividades.size()>0)
                carrega_combo_actividades();
            if (this.lista_tarefas.size()>0)
                carrega_combo_tarefas();
        }
        else{
            JOptionPane.showMessageDialog(null, "Não existem projectos associados ao seu username!");
            //this.close_flag = 1;
        }
    }
    
    private void actualiza_id_geral(){
        String id = this.id_projecto + "_" + this.id_etapa +"."+ this.id_actividade +"."+ this.id_tarefax + "_" + this.id_local;
        idField.setText(id);
    }
    
    private void set_to_dm(String id, TarefaHoras t){
        //this.dm.lista_tarefas_time_user.add(t);
        TarefaHoras t_aux = this.dm.get_lista_tarefas_horas_user().get(id);
        if (t_aux!=null){
            for (Date d : t.get_map().keySet())
                if (!t_aux.get_map().containsKey(d))
                    t_aux.get_map().put(d,t.get_map().get(d));
        }
        else{
            this.dm.get_lista_tarefas_horas_user().put(id,t);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox actividade_combo_box;
    private javax.swing.JButton addButton;
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
    @SuppressWarnings("rawtypes")
    private javax.swing.JComboBox tarefa_combo_box;
    // End of variables declaration//GEN-END:variables
}
