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
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
@SuppressWarnings("serial")
public class Consultar_despesa extends javax.swing.JFrame {
    String username;
    File fileaux;
    Despesa_new despesa;
    TreeMap<String,Projecto> lista_projectos;
    
    public Consultar_despesa(String user, Despesa_new d, boolean b) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.username = user;
        this.despesa = d;
        this.lista_projectos = new TreeMap<>();
        set_lista_projectos();
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
        reciboButton.setEnabled(b);
        moedaField.setEnabled(b);
    }
    
    @SuppressWarnings("unchecked")
	private void preencher_fields(){
        //tipo de despesa
        combo_tipo_despesa.addItem(this.despesa.get_tipo());
        Projecto aux = lista_projectos.get(this.despesa.get_id_projecto());
        combo_box_cliente.addItem(aux.get_cliente().get_nome());
        projecto_combo_box.addItem(aux.get_codigo() + " - " + aux.get_nome());
        combo_box_etapas.addItem(this.despesa.get_etapa());
        combo_box_actividades.addItem(this.despesa.get_actividade());
        dataTransField.setDate(this.despesa.get_data_despesa());
        quantidadeField.setText(String.valueOf(this.despesa.get_quantidade()));
        //valorField.setText(String.valueOf(this.despesa.get_valor()));
        valorField.setText(preencher_campo_valor_e_combo_moeda());
        faturavelBox.setSelected(this.despesa.get_faturavel());
        notasField.setText(this.despesa.get_notas());
        this.fileaux = this.despesa.get_recibo();
        if (this.fileaux!=null)
            nomeFicheiroLabel.setText(this.fileaux.getName());
    }
    
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jf = new javax.swing.JFileChooser();
        jLabel13 = new javax.swing.JLabel();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        notasField = new javax.swing.JTextArea();
        moedaField = new javax.swing.JComboBox();
        nomeFicheiroLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        removeFile = new javax.swing.JButton();
        combo_box_etapas = new javax.swing.JComboBox();
        combo_box_actividades = new javax.swing.JComboBox();
        combo_tipo_despesa = new javax.swing.JComboBox();
        projecto_combo_box = new javax.swing.JComboBox();
        combo_box_cliente = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();

        jLabel13.setText("Cliente:");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel2.setText("Tipo de despesa");

        jLabel3.setText("Projecto");

        jLabel4.setText("Etapa");

        jLabel5.setText("Actividade");

        jLabel6.setText("Transacção");

        jLabel7.setText("Quantidade");

        jLabel8.setText("Valor");

        jLabel9.setText("Não faturável");

        jLabel10.setText("Recibo");

        jLabel11.setText("Notas");

        quantidadeField.setText("1");

        valorField.setText("0.0");
        valorField.setToolTipText("");

        reciboButton.setText("Escolher");
        reciboButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reciboButtonActionPerformed(evt);
            }
        });

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_back.png"))); // NOI18N
        cancelButton.setText("Voltar");
        cancelButton.setMaximumSize(new java.awt.Dimension(120, 40));
        cancelButton.setMinimumSize(new java.awt.Dimension(120, 40));
        cancelButton.setPreferredSize(new java.awt.Dimension(120, 40));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        notasField.setColumns(20);
        notasField.setLineWrap(true);
        notasField.setRows(5);
        jScrollPane2.setViewportView(notasField);

        moedaField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EUR", "USD", "AKZ" }));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/expense_icon.png"))); // NOI18N
        jLabel12.setText("Consultar detalhes da despesa.");

        removeFile.setText("X");
        removeFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFileActionPerformed(evt);
            }
        });

        combo_box_etapas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_etapasActionPerformed(evt);
            }
        });

        combo_tipo_despesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipo_despesaActionPerformed(evt);
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

        jLabel14.setText("Cliente:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(57)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(0, 371, Short.MAX_VALUE)
        					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        						.addComponent(jLabel14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        						.addComponent(jLabel3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel6, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel7, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel8, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel9, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel10, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel11, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        									.addComponent(nomeFicheiroLabel, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.UNRELATED)
        									.addComponent(removeFile)))
        							.addGap(0, 126, Short.MAX_VALUE))
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
        				.addComponent(jLabel14))
        			.addGap(18)
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
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        					.addGap(26))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel11)
        					.addContainerGap())))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void set_lista_projectos(){
        try{
        Connection con = (new Connection_bd()).get_connection();
        String sql;
        PreparedStatement ps;
        ResultSet rs;
        //projectos
        sql = "select * from tnm_trf_projecto" ;
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()){
            ByteArrayInputStream bais;
            ObjectInputStream ois;
            try{
                String codigo = rs.getString("ID_PROJECTO");
                bais = new ByteArrayInputStream(rs.getBytes("PROJECTO"));
                ois = new ObjectInputStream(bais);
                Projecto p = (Projecto)ois.readObject();
                this.lista_projectos.put(codigo, p);
            }
            catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(e);
            }
        }  
        rs.close();
        ps.close();
        con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
    }
    
    private void removeFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFileActionPerformed
        this.fileaux = null;
        nomeFicheiroLabel.setText("");
        removeFile.setVisible(false);
    }//GEN-LAST:event_removeFileActionPerformed

    private void projecto_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projecto_combo_boxActionPerformed
        //carrega_combo_projectos();
    }//GEN-LAST:event_projecto_combo_boxActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void combo_box_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_clienteActionPerformed
        //carrega_combo_clientes();
    }//GEN-LAST:event_combo_box_clienteActionPerformed

    private void combo_box_etapasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_etapasActionPerformed
     
    }//GEN-LAST:event_combo_box_etapasActionPerformed

    private void combo_tipo_despesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipo_despesaActionPerformed
      
    }//GEN-LAST:event_combo_tipo_despesaActionPerformed
    
    @SuppressWarnings({ "unused", "unchecked" })
	private void carrega_combo_clientes(){
        String cliente = combo_box_cliente.getSelectedItem().toString();
        TreeMap<String,Projecto> projectos = new TreeMap<>();
        projecto_combo_box.removeAllItems();
        for (Projecto p : this.lista_projectos.values())
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
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
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
    private javax.swing.JLabel jLabel14;
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
