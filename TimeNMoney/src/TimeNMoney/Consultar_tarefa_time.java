package TimeNMoney;

import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TreeMap;

import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
@SuppressWarnings("serial")
public class Consultar_tarefa_time extends javax.swing.JFrame {
    TarefaHoras tarefa;
    String username;
    TreeMap<String,Projecto> lista_projectos;
    
    /**
     * @wbp.parser.constructor
     */
    public Consultar_tarefa_time(Calendar cal, TarefaHoras t, String username) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.tarefa = t;
        this.username = username;
        this.lista_projectos = new TreeMap<>();
        set_lista_projectos();
        preenche_campos();
        block_campos();
    }
    
    public Consultar_tarefa_time(Calendar cal, TarefaHoras t, String username, Data_manager dm) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.tarefa = t;
        this.username = username;
        this.lista_projectos = new TreeMap<>(dm.get_projectos_user());
        preenche_campos();
        block_campos();
    }
    
    private void set_lista_projectos(){
        try{
        Connection con = (new Connection_bd(this.username)).get_connection();
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
                new Log_erros_class().write_log_to_file(this.username,e);
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
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    private void block_campos(){
        idField.setEditable(false);
        combo_box_cliente.setEnabled(false);
        projecto_combo_box.setEnabled(false);
        etapa_combo_box.setEnabled(false);
        descEtapaField.setEditable(false);
        actividade_combo_box.setEnabled(false);
        descActField.setEditable(false);
        tarefa_combo_box.setEnabled(false);
        descTarefaField.setEnabled(false);
        local_combo_box.setEnabled(false);
    }
    
    @SuppressWarnings("unchecked")
	private void preenche_campos(){
        
        Projecto aux = lista_projectos.get(this.tarefa.get_id_projecto());
        combo_box_cliente.addItem(aux.get_cliente().get_nome());
        projecto_combo_box.addItem(aux.get_codigo() + " - " + aux.get_nome());
        etapa_combo_box.addItem(this.tarefa.get_etapa());
        descEtapaField.setText(this.tarefa.get_desc_etapa());
        actividade_combo_box.addItem(this.tarefa.get_actividade());
        descActField.setText(this.tarefa.get_desc_actividade());
        tarefa_combo_box.addItem(this.tarefa.get_tarefa());
        descTarefaField.setText(this.tarefa.get_desc_tarefa());
        switch (this.tarefa.get_local()) {
            case "Angola":
                local_combo_box.setSelectedItem("Angola");
                break;
            case "":
                local_combo_box.addItem("");
                local_combo_box.setSelectedItem("");
                break;
            default:
                local_combo_box.setSelectedItem("Portugal");
                break;
        }
        idField.setText(this.tarefa.get_id());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelButton = new javax.swing.JButton();
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

        local_combo_box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Portugal", "Angola" }));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/task_icon.png"))); // NOI18N
        jLabel10.setText("Consultar Tarefa");

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
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        							.addGap(161)
        							.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
        						.addComponent(etapa_combo_box, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(idField)
        						.addComponent(local_combo_box, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(descEtapaField)
        						.addComponent(actividade_combo_box, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(descActField)
        						.addComponent(tarefa_combo_box, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(descTarefaField)
        						.addComponent(projecto_combo_box, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(combo_box_cliente, Alignment.TRAILING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
        			.addGap(22)
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
        				.addComponent(jLabel7))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(descTarefaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel8))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(local_combo_box, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel9))
        			.addGap(33)
        			.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
        			.addGap(29))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed
        
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox actividade_combo_box;
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
