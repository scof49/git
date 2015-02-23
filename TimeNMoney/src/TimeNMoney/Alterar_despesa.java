package TimeNMoney;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
@SuppressWarnings("serial")
public class Alterar_despesa extends javax.swing.JFrame {
    String username;
    File fileaux;
    Despesa_new despesa;
    TreeMap<String,Etapa> lista_etapas;
    TreeMap<String,Actividade> lista_actividades;
    TreeMap<String,Projecto> lista_projectos;
    Data_manager dm;
    boolean rejeitada;
    
    public Alterar_despesa(String user, Despesa_new d, boolean b, Data_manager dm, boolean rej) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.username = user;
        this.fileaux = null;
        this.despesa = d;
        this.lista_etapas = new TreeMap<>();
        this.lista_actividades = new TreeMap<>();
        this.lista_projectos = new TreeMap<>();
        this.dm = dm;
        this.rejeitada = rej;
        carrega_combo_boxes_from_dm();
        preencher_fields();
        disable_fields(b);
        if (this.fileaux==null)
            removeFile.setVisible(false);
    }
    
    private void disable_fields(boolean b){
        combo_tipo_despesa.setEnabled(b);
        combo_box_cliente.setEnabled(b);
        projecto_combo_box.setEnabled(b);
        combo_box_etapas.setEnabled(b);
        combo_box_actividades.setEnabled(b);
        dataTransField.getCalendarButton().setEnabled(b);
        ((JTextField)dataTransField.getDateEditor()).setEditable(b);
        quantidadeField.setEditable(b);
        valorField.setEditable(b);
        faturavelBox.setEnabled(b);
        notasField.setEditable(b);
        addButton.setVisible(b);
        reciboButton.setEnabled(b);
        cleanButton.setVisible(b);
        moedaField.setEnabled(b);
    }

    private void select_combo_item_etapas(String str){
        int count = 0;
        int max_count = combo_box_etapas.getItemCount();
        while (count>=0 && count<max_count){
            Object obj = combo_box_etapas.getItemAt(count);
            if (obj.toString().equals(str)){
                combo_box_etapas.setSelectedIndex(count);
                count = -2;
            } 
            count++;
        }
    }
    
    private void select_combo_item_actividades(String str){
        int count = 0;
        int max_count = combo_box_actividades.getItemCount();
        while (count>=0 && count<max_count){
            Object obj = combo_box_actividades.getItemAt(count);
            if (obj.toString().equals(str)){
                combo_box_actividades.setSelectedIndex(count);
                count = -2;    
            } 
            count++;
        }
    }
    
    private void select_combo_tipo_despesa(String str){
        int count = 0;
        int max_count = combo_tipo_despesa.getItemCount();
        while (count>=0 && count<max_count){
            Object obj = combo_tipo_despesa.getItemAt(count);
            if (obj.toString().equals(str)){
                combo_tipo_despesa.setSelectedIndex(count);
                count = -2;   
            } 
            count++;
        }
    }
    
    private void select_combo_item_projectos(String str){
        int count = 0;
        int max_count = projecto_combo_box.getItemCount();
        while (count>=0 && count<max_count){
            Object obj = projecto_combo_box.getItemAt(count);
            String aux = obj.toString().split(" - ")[0];
            if (aux.equals(str)){
                projecto_combo_box.setSelectedIndex(count);
                count = -2;
            } 
            count++;
        }
    }
    
    private void preencher_fields(){
        select_combo_tipo_despesa(this.despesa.get_tipo());
        Projecto aux = this.dm.get_projectos_user().get(this.despesa.get_id_projecto());
        String cliente = aux.get_cliente().get_nome();
        if (combo_box_cliente.getItemCount()>0)
            combo_box_cliente.setSelectedItem(cliente);

        select_combo_item_projectos(this.despesa.get_id_projecto());
        select_combo_item_etapas(this.despesa.get_etapa());
        select_combo_item_actividades(this.despesa.get_actividade());
        dataTransField.setDate(this.despesa.get_data_despesa());
        quantidadeField.setText(String.valueOf(this.despesa.get_quantidade()));
        valorField.setText(preencher_campo_valor_e_combo_moeda());
        faturavelBox.setSelected(this.despesa.get_faturavel());
        notasField.setText(this.despesa.get_notas());
        this.fileaux = this.despesa.get_recibo();
        if (this.fileaux!=null)
            nomeFicheiroLabel.setText(this.fileaux.getName());
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jf = new javax.swing.JFileChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        dataTransField = new com.toedter.calendar.JDateChooser();
        quantidadeField = new javax.swing.JTextField();
        valorField = new javax.swing.JTextField();
        faturavelBox = new javax.swing.JCheckBox();
        reciboButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        notasField = new javax.swing.JTextArea();
        moedaField = new javax.swing.JComboBox();
        nomeFicheiroLabel = new javax.swing.JLabel();
        cleanButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        removeFile = new javax.swing.JButton();
        combo_box_etapas = new javax.swing.JComboBox();
        combo_box_actividades = new javax.swing.JComboBox();
        combo_tipo_despesa = new javax.swing.JComboBox();
        projecto_combo_box = new javax.swing.JComboBox();
        combo_box_cliente = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel2.setText("Tipo de despesa:");

        jLabel3.setText("Projecto:");

        jLabel4.setText("Etapa:");

        jLabel5.setText("Actividade:");

        jLabel6.setText("Transacção:");

        jLabel7.setText("Quantidade:");

        jLabel8.setText("Valor:");

        jLabel9.setText("Não faturável:");

        jLabel10.setText("Recibo:");

        jLabel11.setText("Notas:");

        quantidadeField.setText("1");

        valorField.setText("0.0");
        valorField.setToolTipText("");

        reciboButton.setText("Escolher");
        reciboButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reciboButtonActionPerformed(evt);
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
        addButton.setText("Gravar");
        addButton.setMaximumSize(new java.awt.Dimension(120, 40));
        addButton.setMinimumSize(new java.awt.Dimension(120, 40));
        addButton.setPreferredSize(new java.awt.Dimension(120, 40));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        notasField.setColumns(20);
        notasField.setLineWrap(true);
        notasField.setRows(5);
        jScrollPane2.setViewportView(notasField);

        moedaField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EUR", "USD", "AKZ" }));

        cleanButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/Eraser-icon.png"))); // NOI18N
        cleanButton.setText("Limpar");
        cleanButton.setMaximumSize(new java.awt.Dimension(120, 40));
        cleanButton.setMinimumSize(new java.awt.Dimension(120, 40));
        cleanButton.setPreferredSize(new java.awt.Dimension(120, 40));
        cleanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanButtonActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/expense_icon.png"))); // NOI18N
        jLabel12.setText("Alterar detalhes da despesa.");

        removeFile.setText("X");
        removeFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFileActionPerformed(evt);
            }
        });

        projecto_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projecto_combo_boxActionPerformed(evt);
            }
        });

        combo_box_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_clienteActionPerformed(evt);
            }
        });

        jLabel13.setText("Cliente:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(57)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(0, 119, Short.MAX_VALUE)
        					.addComponent(cleanButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(addButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel13, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel9, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel10, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel11, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
        						.addComponent(combo_box_etapas, 0, 395, Short.MAX_VALUE)
        						.addComponent(combo_box_actividades, 0, 395, Short.MAX_VALUE)
        						.addComponent(combo_tipo_despesa, Alignment.TRAILING, 0, 395, Short.MAX_VALUE)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(faturavelBox)
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        										.addComponent(dataTransField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        										.addComponent(valorField)
        										.addComponent(quantidadeField))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(moedaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(reciboButton)
        									.addPreferredGap(ComponentPlacement.UNRELATED)
        									.addComponent(nomeFicheiroLabel, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        									.addPreferredGap(ComponentPlacement.UNRELATED)
        									.addComponent(removeFile)))
        							.addGap(126))
        						.addComponent(projecto_combo_box, 0, 395, Short.MAX_VALUE)
        						.addComponent(combo_box_cliente, 0, 395, Short.MAX_VALUE))))
        			.addGap(52))
        		.addGroup(layout.createSequentialGroup()
        			.addGap(29)
        			.addComponent(jLabel12, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(127, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(20)
        			.addComponent(jLabel12, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel2)
        				.addComponent(combo_tipo_despesa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(combo_box_cliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel13))
        			.addGap(22)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(jLabel3)
        								.addComponent(projecto_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(jLabel4)
        								.addComponent(combo_box_etapas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(jLabel5)
        								.addComponent(combo_box_actividades, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        							.addGap(18)
        							.addComponent(jLabel6))
        						.addComponent(dataTransField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(jLabel7)
        								.addComponent(quantidadeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(jLabel8)
        								.addComponent(valorField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(moedaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(jLabel9)
        								.addComponent(faturavelBox))
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(jLabel10)
        								.addComponent(reciboButton)))
        						.addComponent(removeFile, Alignment.TRAILING)))
        				.addComponent(nomeFicheiroLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        						.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cleanButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        					.addContainerGap(26, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel11)
        					.addContainerGap())))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String preencher_campo_valor_e_combo_moeda(){
        String valor;
        if (this.despesa.get_valor_original().equals(""))
        {
            valor = String.valueOf(this.despesa.get_valor_euros());
        }
        else
        {
            String[] aux = this.despesa.get_valor_original().split(" - ")[0].split(" ");
            valor = aux[0];
            moedaField.setSelectedItem(aux[1]);
        }
        return valor;
    }
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void reciboButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reciboButtonActionPerformed
        jf.setMultiSelectionEnabled(false);
        int esc = jf.showOpenDialog(this);
        if (esc==JFileChooser.APPROVE_OPTION){
            File ficheirorecebido = jf.getSelectedFile();
            nomeFicheiroLabel.setText(ficheirorecebido.getName());
            this.fileaux = ficheirorecebido;
            removeFile.setVisible(true);
        }               
    }//GEN-LAST:event_reciboButtonActionPerformed

    @SuppressWarnings("unchecked")
	private void carrega_combo_boxes_from_dm(){
        ArrayList<Cliente> clientes;
        TreeMap<String,Etapa> etapas;        
        TreeMap<String,Actividade> actividades;
        TreeMap<String,Projecto> projectos;
        
        for (String s : this.dm.tipos_despesa){
            combo_tipo_despesa.addItem(s);
        }
        
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
            for (Etapa e : etapas.values())
                combo_box_etapas.addItem(e.get_nome());

            for (Actividade a : actividades.values())
                combo_box_actividades.addItem(a.get_nome());

            //adicionar arrays na class
            this.lista_etapas = etapas;
            this.lista_actividades = actividades;
            this.lista_projectos = projectos;
        }
        else{
            JOptionPane.showMessageDialog(null, "Não existem projectos associados ao seu username!");
        }
    }
    
    private void clear_fields(){
        combo_tipo_despesa.setSelectedIndex(0);
        projecto_combo_box.setSelectedIndex(0);
        combo_box_etapas.setSelectedIndex(0);
        combo_box_actividades.setSelectedIndex(0);
        dataTransField.setDate(null);
        quantidadeField.setText("0");
        valorField.setText("0.0");
        faturavelBox.setSelected(false);
        nomeFicheiroLabel.setText("");
        notasField.setText("");
        this.fileaux = null;
    }
    
    private void cleanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanButtonActionPerformed
        clear_fields();
    }//GEN-LAST:event_cleanButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!quantity_aprov() && !claim_aprov()){
            JOptionPane.showMessageDialog(null, "Campos de quantidade e valor da despesa mal preenchidos. Devem ser unicamente numéricos!");
            quantidadeField.setText("0");
            valorField.setText("0.0");
        }
        else if (!quantity_aprov()){
            JOptionPane.showMessageDialog(null, "Campo de quantidade deve ser unicamente numérico!");
            quantidadeField.setText("0");
        }
        else if (!claim_aprov()){
            JOptionPane.showMessageDialog(null, "Campo de valor deve ser unicamente numérico!");
            valorField.setText("0.0");
        }
        else if (dataTransField.getDate()==null){
            JOptionPane.showMessageDialog(null, "Seleccione uma data de Transacção!");
        }
        else{
            this.despesa.set_username(this.username);
            if (projecto_combo_box.getItemCount()>0){
                this.despesa.set_id_projecto(projecto_combo_box.getSelectedItem().toString().split(" - ")[0]);
                this.despesa.set_nome_projecto(projecto_combo_box.getSelectedItem().toString().replace((this.despesa.get_id_projecto() + " - "), ""));
            }
            else{
                this.despesa.set_id_projecto("");
                this.despesa.set_nome_projecto("");
            }
            if (combo_tipo_despesa.getItemCount()>0)
                this.despesa.set_tipo(combo_tipo_despesa.getSelectedItem().toString());
            else
                this.despesa.set_tipo("");
            if (combo_box_etapas.getItemCount()>0)
                this.despesa.set_etapa(combo_box_etapas.getSelectedItem().toString());
            else
                this.despesa.set_etapa("");
            if (combo_box_actividades.getItemCount()>0)
                this.despesa.set_actividade(combo_box_actividades.getSelectedItem().toString());
            else
                this.despesa.set_actividade("");
            this.despesa.set_data_despesa(dataTransField.getDate());
            this.despesa.set_quantidade(Integer.parseInt(quantidadeField.getText()));
            this.despesa.set_valor_euros(convert_valor());
            this.despesa.set_valor_original(string_valor_original());
            this.despesa.set_faturavel(faturavelBox.isSelected());
            this.despesa.set_recibo(this.fileaux);
            this.despesa.set_notas(notasField.getText());
            //save BD
            change_dm_despesa(this.despesa);
            if (this.rejeitada){
            	this.dm.despesas_rejeitadas_alteradas.add(this.despesa.get_id());
            	this.dm.get_estado_despesas_user().put(this.despesa.get_id(), 4);
            }
            this.dispose();
            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_addButtonActionPerformed

    private void removeFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFileActionPerformed
        this.fileaux = null;
        nomeFicheiroLabel.setText("");
        removeFile.setVisible(false);
    }//GEN-LAST:event_removeFileActionPerformed

    private void projecto_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projecto_combo_boxActionPerformed
        carrega_combo_projectos();
    }//GEN-LAST:event_projecto_combo_boxActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

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
        
    }
    
    @SuppressWarnings("unchecked")
	private void carrega_combo_projectos(){
        try{
        	TreeMap<String,Etapa> etapas;        
        	TreeMap<String,Actividade> actividades;
        combo_box_etapas.removeAllItems();
        combo_box_actividades.removeAllItems();
        String projecto_aux = "";
        if (projecto_combo_box.getItemCount()>0)
            projecto_aux = projecto_combo_box.getSelectedItem().toString().split(" - ")[0];
        for (Projecto pr : this.lista_projectos.values())
            if (pr.get_codigo().equals(projecto_aux)){
                etapas = new TreeMap<>(pr.get_etapas());
                actividades = new TreeMap<>(pr.get_actividades());
                for (Etapa e : etapas.values())
                    combo_box_etapas.addItem(e.get_nome());

                for (Actividade a : actividades.values())
                    combo_box_actividades.addItem(a.get_nome());

                this.lista_etapas = etapas;
                this.lista_actividades = actividades;
                if (combo_box_etapas.getItemCount()>0){
                    combo_box_etapas.setEnabled(true);
                    combo_box_etapas.setSelectedIndex(0);
                }
                else
                    combo_box_etapas.setEnabled(false);
                if (combo_box_actividades.getItemCount()>0){
                    combo_box_actividades.setEnabled(true);
                    combo_box_actividades.setSelectedIndex(0);
                }
                else
                    combo_box_actividades.setEnabled(false);
            }
        //actualiza_id_geral();
        }
        catch(NullPointerException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
    }
    
    private double convert_valor(){
        double val = Double.parseDouble(valorField.getText().replace(",", "."));
        if (moedaField.getSelectedItem().toString().equals("USD"))
            val = val*get_taxa_usd_data();
        else if (moedaField.getSelectedItem().toString().equals("AKZ"))
            val = val*get_taxa_akz_data();
        return round(val,2);
    }
    
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    private String string_valor_original(){
        if (moedaField.getSelectedItem().toString().equals("USD"))
            return valorField.getText().replace(",",".") + " USD - Taxa: "+ get_taxa_usd_data();
        else if (moedaField.getSelectedItem().toString().equals("AKZ"))
            return valorField.getText().replace(",",".") + " AKZ - Taxa: "+ get_taxa_akz_data();
        else
            return "";
    }
    
    private double get_taxa_usd_data(){
    	double val = 0.0;
    	//get mes e ano
    	Date d = dataTransField.getDate();
    	Calendar c = new GregorianCalendar();
    	c.setTime(d);
    	int mes = c.get(Calendar.MONTH) + 1;
    	int ano = c.get(Calendar.YEAR);
    	val = this.dm.get_rate_usd(mes,ano);
    	return val;
    }
    
    private double get_taxa_akz_data(){
    	double val = 0.0;
    	//get mes e ano
    	Date d = dataTransField.getDate();
    	Calendar c = new GregorianCalendar();
    	c.setTime(d);
    	int mes = c.get(Calendar.MONTH) + 1;
    	int ano = c.get(Calendar.YEAR);
    	val = this.dm.get_rate_akz(mes,ano);
    	return val;
    }
    
    private boolean quantity_aprov(){
        try{
            String st = quantidadeField.getText();
            Integer.parseInt(st);
            return true;
        }
        catch(NumberFormatException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            return false;
        }
    }
    
    private boolean claim_aprov(){
        try{
            String st = valorField.getText().replace(",",".");
            Double.parseDouble(st);
            return true;
        }
        catch(NumberFormatException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            return false;
        }
    }
    
    private void change_dm_despesa(Despesa_new d){
        for (Despesa_new d_aux : this.dm.despesas_user){
            if (d_aux.get_id()==d.get_id() && d_aux.get_username().equals(d.get_username())){
                d_aux = d;
            }
        }
        this.dm.despesas_add_alteradas.add(d.get_id());
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton cleanButton;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox combo_box_actividades;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox combo_box_cliente;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox combo_box_etapas;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox combo_tipo_despesa;
    private com.toedter.calendar.JDateChooser dataTransField;
    private javax.swing.JCheckBox faturavelBox;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JFileChooser jf;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox moedaField;
    private javax.swing.JLabel nomeFicheiroLabel;
    private javax.swing.JTextArea notasField;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox projecto_combo_box;
    private javax.swing.JTextField quantidadeField;
    private javax.swing.JButton reciboButton;
    private javax.swing.JButton removeFile;
    private javax.swing.JTextField valorField;
    // End of variables declaration//GEN-END:variables
}
