/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Adicionar_consultar_tipo_despesa extends javax.swing.JFrame {
    ArrayList<Tipo_despesa> lista;
    String id_aux;
    int alteracoes;
    boolean novo;
    boolean admin;
    Funcionario funcionario;
    /**
     * Creates new form Adicionar_etapa
     * @param f
     */
    public Adicionar_consultar_tipo_despesa(Funcionario f) {
        initComponents(); 
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        carrega_lista();
        this.admin = f.get_admin();
        this.funcionario = f;
        this.id_aux = "";
        this.alteracoes = 0;
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        text_field_nome.setEditable(false);
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
        botao_nova.setVisible(this.admin);
        this.novo = false;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void carrega_lista(){
        ArrayList<Tipo_despesa> lista_aux = new ArrayList<>();
        DefaultListModel dlm = new DefaultListModel();
        try{
        Connection con = (new Connection_bd()).get_connection();
        String sql = "select * from tnm_tipo_despesa";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String desc = rs.getString("DESCRICAO");
            Tipo_despesa tipo = new Tipo_despesa();
            tipo.set_nome(desc);
            lista_aux.add(tipo);
            dlm.addElement(desc);
            
        }
        lista_tipos.setModel(dlm);
        this.lista = lista_aux;
        lista_tipos.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
               change_panel();
            }
        });
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
    }
    
    private int exist_id_bd(String desc){
        try{
            Connection con = (new Connection_bd()).get_connection();
            String sql = "select * from tnm_tipo_despesa where descricao = '" + desc + "'" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return 1;
            else
                return 0;
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            return 0;
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private int save_bd(boolean novo){
        String nome = text_field_nome.getText();
        try{
        String sql;
        PreparedStatement ps;
        Connection con = (new Connection_bd()).get_connection();
        String nome_auxiliar;
        if (novo)
            nome_auxiliar = nome;
        else
            nome_auxiliar = this.id_aux;
        int exist_bd = exist_id_bd(nome_auxiliar);
        if (exist_bd!=0){
            if (!this.novo && (exist_id_bd(nome)==0 || nome.equals(nome_auxiliar))){
                  sql = "update tnm_tipo_despesa set descricao = '"+nome+"' where descricao = '" +nome_auxiliar+ "'";
                DefaultListModel d = new DefaultListModel();
                for (Tipo_despesa tp : this.lista){
                    if (tp.get_nome().equals(nome_auxiliar)){
                        tp.set_nome(nome);
                    }
                    d.addElement(tp.get_nome());
                }
                lista_tipos.setModel(d);
            }
            else{
                JOptionPane.showMessageDialog(null, "Tipo já existe!");
                return 0;
            }
        }
        else{
            sql = "insert into tnm_tipo_despesa (descricao) values ('"+nome+"')";
            Tipo_despesa tp = new Tipo_despesa();
            tp.set_nome(nome);
            this.lista.add(tp);
            DefaultListModel d = new DefaultListModel();
            for (Tipo_despesa st : this.lista)
                d.addElement(st.get_nome());
            lista_tipos.setModel(d);
            
        }
        ps=con.prepareStatement(sql);
        ps.executeUpdate();
        this.alteracoes = 0;
        lista_tipos.setEnabled(true);
        etiqueta_label.setText("Tipo de Despesa: "+ nome);
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        botao_nova.setVisible(true);
        text_field_nome.setEditable(false);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            return 0;
        }
        return 1;
    }
    
    public void change_panel(){
        if (this.alteracoes==0)
        {
            String nome = "";
            if (!lista_tipos.isSelectionEmpty()){
                String n_aux = lista_tipos.getSelectedValue().toString();
                for (Tipo_despesa st : this.lista){
                    if (st.get_nome().equals(n_aux))
                    {
                        nome = n_aux;
                        this.id_aux = nome;
                    }
                }
                etiqueta_label.setText("Tipo de Despesa: ");
                text_field_nome.setText(nome);
                botao_alterar.setVisible(this.admin);
                botao_eliminar.setVisible(this.admin);
            }
            else{
                etiqueta_label.setText("Tipo de Despesa: Nenhum tipo seleccionado");
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    @SuppressWarnings("rawtypes")
	private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista_tipos = new javax.swing.JList();
        painel_etapa = new javax.swing.JPanel();
        etiqueta_label = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        text_field_nome = new javax.swing.JTextField();
        botao_nova = new javax.swing.JButton();
        botao_alterar = new javax.swing.JButton();
        botao_guardar = new javax.swing.JButton();
        botao_cancelar = new javax.swing.JButton();
        botao_eliminar = new javax.swing.JButton();
        botao_sair = new javax.swing.JButton();
        botao_voltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/expense_icon.png"))); // NOI18N
        jLabel1.setText("Adicionar/Consultar Tipo de Despesa");

        jScrollPane1.setViewportView(lista_tipos);

        painel_etapa.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        etiqueta_label.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiqueta_label.setText("Tipo de Despesa: Nenhum tipo seleccionado");

        jLabel4.setText("Nome:");

        botao_nova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/new_icon.png"))); // NOI18N
        botao_nova.setText("Novo");
        botao_nova.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_nova.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_nova.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_nova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_novaActionPerformed(evt);
            }
        });

        botao_alterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/edit_icon.png"))); // NOI18N
        botao_alterar.setText("Alterar");
        botao_alterar.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_alterar.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_alterar.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_alterarActionPerformed(evt);
            }
        });

        botao_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/saveicon.png"))); // NOI18N
        botao_guardar.setText("Guardar");
        botao_guardar.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_guardar.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_guardar.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_guardarActionPerformed(evt);
            }
        });

        botao_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/Cancel-icon.png"))); // NOI18N
        botao_cancelar.setText("Cancelar");
        botao_cancelar.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_cancelar.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_cancelar.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_cancelarActionPerformed(evt);
            }
        });

        botao_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_delete.png"))); // NOI18N
        botao_eliminar.setText("Eliminar");
        botao_eliminar.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_eliminar.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_eliminar.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painel_etapaLayout = new javax.swing.GroupLayout(painel_etapa);
        painel_etapa.setLayout(painel_etapaLayout);
        painel_etapaLayout.setHorizontalGroup(
            painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_etapaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painel_etapaLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_field_nome))
                    .addGroup(painel_etapaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botao_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botao_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botao_alterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botao_nova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painel_etapaLayout.createSequentialGroup()
                        .addComponent(etiqueta_label, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(botao_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        painel_etapaLayout.setVerticalGroup(
            painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painel_etapaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiqueta_label, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(text_field_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botao_nova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_alterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        botao_sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_exit.png"))); // NOI18N
        botao_sair.setText("Sair");
        botao_sair.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_sair.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_sair.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_sairActionPerformed(evt);
            }
        });

        botao_voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_back.png"))); // NOI18N
        botao_voltar.setText("Voltar");
        botao_voltar.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_voltar.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_voltar.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_voltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        					.addGap(18)
        					.addComponent(painel_etapa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(0, 551, Short.MAX_VALUE)
        					.addComponent(botao_voltar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_sair, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(8)
        					.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 546, GroupLayout.PREFERRED_SIZE)
        					.addGap(0, 243, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel1)
        			.addGap(32)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
        				.addComponent(painel_etapa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(botao_sair, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(botao_voltar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botao_alterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_alterarActionPerformed
        botao_cancelar.setVisible(true);
        botao_guardar.setVisible(true);
        text_field_nome.setEditable(true);
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
        botao_nova.setVisible(false);
        this.alteracoes = 1;
        lista_tipos.setEnabled(false);
    }//GEN-LAST:event_botao_alterarActionPerformed

    private void botao_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_cancelarActionPerformed
        this.novo = false;
        change_panel();
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        text_field_nome.setEditable(false);
        botao_alterar.setVisible(true);
        botao_eliminar.setVisible(true);
        botao_nova.setVisible(true);
        this.alteracoes = 0;
        lista_tipos.setEnabled(true);
        clear_fields();
    }//GEN-LAST:event_botao_cancelarActionPerformed

    private void botao_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_guardarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int ret = save_bd(this.novo);
        if (ret>0){
            JOptionPane.showMessageDialog(null, "Guardado com sucesso");
            this.novo = false;
            clear_fields();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_guardarActionPerformed

    private void botao_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_sairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_botao_sairActionPerformed

    private void botao_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_voltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botao_voltarActionPerformed

    private void botao_novaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_novaActionPerformed
        this.alteracoes = 1;
        this.novo = true;
        lista_tipos.clearSelection();
        text_field_nome.setText("");
        etiqueta_label.setText("Novo tipo de Despesa");
        botao_cancelar.setVisible(true);
        botao_guardar.setVisible(true);
        text_field_nome.setEditable(true);
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
        botao_nova.setVisible(false);
        lista_tipos.setEnabled(false);
        this.id_aux = "";
        this.alteracoes = 0;
    }//GEN-LAST:event_botao_novaActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void botao_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_eliminarActionPerformed
        String nome = text_field_nome.getText();
        if (!nome.equals("")){
            if (delete_from_bd(nome)==0){
                DefaultListModel d = new DefaultListModel();
                Tipo_despesa elimin = null;
                for (Tipo_despesa st : this.lista){
                    if (!st.get_nome().equals(nome))
                        d.addElement(st.get_nome());
                    else
                        elimin = st;
                }
                lista_tipos.setModel(d);
                this.lista.remove(elimin);
                clear_fields();
                JOptionPane.showMessageDialog(null, "Eliminado com sucesso!");
            }
            else
                JOptionPane.showMessageDialog(null, "Erro, contacte administrador!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Deve seleccionar um item!");
        }
    }//GEN-LAST:event_botao_eliminarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private int delete_from_bd(String nome){
        try{
            Connection con = (new Connection_bd()).get_connection();
            String sql = "delete from tnm_tipo_despesa where descricao = '" + nome + "'" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ps.executeUpdate();
            return 0;
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            return 1;
        }
    }
    
    private void clear_fields(){
        text_field_nome.setText("");
        etiqueta_label.setText("Tipo de Despesa: Nenhum tipo seleccionado");
        lista_tipos.clearSelection();
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botao_alterar;
    private javax.swing.JButton botao_cancelar;
    private javax.swing.JButton botao_eliminar;
    private javax.swing.JButton botao_guardar;
    private javax.swing.JButton botao_nova;
    private javax.swing.JButton botao_sair;
    private javax.swing.JButton botao_voltar;
    private javax.swing.JLabel etiqueta_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList lista_tipos;
    private javax.swing.JPanel painel_etapa;
    private javax.swing.JTextField text_field_nome;
    // End of variables declaration//GEN-END:variables
}
