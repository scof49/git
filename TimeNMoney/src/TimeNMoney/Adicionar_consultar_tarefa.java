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
import java.util.TreeMap;
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
public class Adicionar_consultar_tarefa extends javax.swing.JFrame {
    TreeMap<String,Tarefa> lista;
    String id_aux;
    int alteracoes;
    boolean novo;
    boolean admin;
    Funcionario funcionario;
    /**
     * Creates new form Adicionar_etapa
     * @param f
     */
    public Adicionar_consultar_tarefa(Funcionario f) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.admin = f.get_admin();
        this.funcionario = f;
        carrega_lista();
        this.id_aux = "";
        this.alteracoes = 0;
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        text_field_id.setEditable(false);
        text_field_nome.setEditable(false);
        text_area_descricao.setEditable(false);
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
        botao_nova.setVisible(this.admin);
        this.novo = false;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void carrega_lista(){
    	TreeMap<String,Tarefa> lista_aux = new TreeMap<String,Tarefa>();
        DefaultListModel dlm = new DefaultListModel();
        try{
        Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
        //etapas
        String sql = "select * from tnm_trf_tarefa order by nome";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String id = rs.getString("ID");
            String nome = rs.getString("NOME");
            String desc = rs.getString("DESCRICAO");
            Tarefa et = new Tarefa();
            et.set_id(id);
            et.set_nome(nome);            
            et.set_descricao(desc);
            lista_aux.put(et.get_nome(),et);
            dlm.addElement(nome);
            
        }
        lista_tarefas.setModel(dlm);
        this.lista = lista_aux;
        lista_tarefas.addListSelectionListener(new ListSelectionListener() {

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
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
        }
    }
    
    private int exist_id_bd(String id){
        try{
            Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
            String sql = "select * from tnm_trf_tarefa where id = '" + id + "'" ;
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
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
            return 0;
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private int save_bd(boolean novo){
        String id = text_field_id.getText();
        String nome = text_field_nome.getText();
        String desc = text_area_descricao.getText();
        try{
        String sql;
        Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
        String id_auxiliar;
        PreparedStatement ps;
        if (novo)
            id_auxiliar = id;
        else
            id_auxiliar = this.id_aux;
        int exist_bd = exist_id_bd(id_auxiliar);
        if (exist_bd!=0){
            if (!this.novo && (exist_id_bd(id)==0 || id.equals(id_auxiliar))){
                sql = "update tnm_trf_tarefa set id = '"+id+"', nome = '"+nome+"', descricao = '"+desc+"' where id = '" +id_auxiliar+ "'";
                DefaultListModel d = new DefaultListModel();
                for (Tarefa et : this.lista.values()){
                    if (et.get_id().equals(id_auxiliar)){
                        et.set_id(id);
                        et.set_nome(nome);
                        et.set_descricao(desc);
                    }
                    d.addElement(et.get_nome());
                }
                lista_tarefas.setModel(d);
            }
            else{
                JOptionPane.showMessageDialog(null, "ID já existe!");
                return 0;
            }
        }
        else{
            sql = "insert into tnm_trf_tarefa (id,nome,descricao) values ('"+id+"','"+nome+"','"+desc+"')";
            Tarefa e = new Tarefa();
            e.set_id(id);
            e.set_nome(nome);
            e.set_descricao(desc);
            this.lista.put(nome,e);
            DefaultListModel d = new DefaultListModel();
            for (Tarefa et : this.lista.values())
                d.addElement(et.get_nome());
            lista_tarefas.setModel(d);
            add_user_tarefa_bd(id,this.funcionario.get_username(),con);
        }
        ps=con.prepareStatement(sql);
        ps.executeUpdate();
        con.close();
        this.alteracoes = 0;
        lista_tarefas.setEnabled(true);
        etiqueta_label.setText("Tarefa: "+ nome);
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        botao_nova.setVisible(true);
        text_field_id.setEditable(false);
        text_field_nome.setEditable(false);
        text_area_descricao.setEditable(false);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
            return 0;
        }
        return 1;
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
			new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
		}
	}

	public void change_panel(){
        if (this.alteracoes==0)
        {
            String id = "";
            String nome = "";
            String descricao = "";
            if (!lista_tarefas.isSelectionEmpty()){
                String n_aux = lista_tarefas.getSelectedValue().toString();
                for (Tarefa e : this.lista.values()){
                    if (e.get_nome().equals(n_aux))
                    {
                        id = e.get_id();
                        nome = n_aux;
                        descricao = e.get_descricao();
                        this.id_aux = id;
                    }
                }
                etiqueta_label.setText("Tarefa: "+ nome);
                text_field_id.setText(id);
                text_field_nome.setText(nome);
                text_area_descricao.setText(descricao);
                botao_alterar.setVisible(this.admin);
                botao_eliminar.setVisible(this.admin);
            }
            else{
                etiqueta_label.setText("Tarefa: Nenhuma tarefa seleccionada");
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({ "rawtypes" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista_tarefas = new javax.swing.JList();
        painel_etapa = new javax.swing.JPanel();
        etiqueta_label = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        text_field_id = new javax.swing.JTextField();
        text_field_nome = new javax.swing.JTextField();
        botao_nova = new javax.swing.JButton();
        botao_alterar = new javax.swing.JButton();
        botao_guardar = new javax.swing.JButton();
        botao_cancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        text_area_descricao = new javax.swing.JTextArea();
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
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/task_icon.png"))); // NOI18N
        jLabel1.setText("Adicionar/Consultar Tarefa");

        jScrollPane1.setViewportView(lista_tarefas);

        painel_etapa.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        etiqueta_label.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiqueta_label.setText("Tarefa: Nenhuma tarefa seleccionada");

        jLabel3.setText("ID:");

        jLabel4.setText("Nome:");

        jLabel5.setText("Descrição:");

        botao_nova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/new_icon.png"))); // NOI18N
        botao_nova.setText("Nova");
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

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        text_area_descricao.setColumns(20);
        text_area_descricao.setLineWrap(true);
        text_area_descricao.setRows(5);
        jScrollPane2.setViewportView(text_area_descricao);

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
        painel_etapaLayout.setHorizontalGroup(
        	painel_etapaLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(painel_etapaLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(painel_etapaLayout.createSequentialGroup()
        					.addGroup(painel_etapaLayout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(painel_etapaLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(text_field_nome, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
        						.addComponent(text_field_id, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
        						.addComponent(jScrollPane2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)))
        				.addGroup(painel_etapaLayout.createSequentialGroup()
        					.addGap(0, 89, Short.MAX_VALUE)
        					.addComponent(botao_guardar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_cancelar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_alterar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_nova, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(painel_etapaLayout.createSequentialGroup()
        					.addComponent(etiqueta_label, GroupLayout.PREFERRED_SIZE, 453, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
        					.addComponent(botao_eliminar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        painel_etapaLayout.setVerticalGroup(
        	painel_etapaLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(painel_etapaLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(etiqueta_label, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
        				.addComponent(botao_eliminar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(59)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel3)
        				.addComponent(text_field_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel4)
        				.addComponent(text_field_nome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jLabel5)
        				.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(botao_nova, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(botao_alterar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(botao_cancelar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(botao_guardar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        painel_etapa.setLayout(painel_etapaLayout);

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
        					.addComponent(painel_etapa, GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(0, 551, Short.MAX_VALUE)
        					.addComponent(botao_voltar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_sair, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(10)
        					.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 467, GroupLayout.PREFERRED_SIZE)
        					.addGap(0, 320, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel1)
        			.addGap(32)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(painel_etapa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jScrollPane1))
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
        text_field_id.setEditable(true);
        text_field_nome.setEditable(true);
        text_area_descricao.setEditable(true);
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
        botao_nova.setVisible(false);
        this.alteracoes = 1;
        lista_tarefas.setEnabled(false);
    }//GEN-LAST:event_botao_alterarActionPerformed

    private void botao_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_cancelarActionPerformed
        this.novo = false;
        change_panel();
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        text_field_id.setEditable(false);
        text_field_nome.setEditable(false);
        text_area_descricao.setEditable(false);
        botao_alterar.setVisible(true);
        botao_nova.setVisible(true);
        botao_eliminar.setVisible(true);
        this.alteracoes = 0;
        lista_tarefas.setEnabled(true);
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
        lista_tarefas.clearSelection();
        text_field_id.setText("");
        text_field_nome.setText("");
        text_area_descricao.setText("");
        etiqueta_label.setText("Nova tarefa");
        botao_cancelar.setVisible(true);
        botao_guardar.setVisible(true);
        text_field_id.setEditable(true);
        text_field_nome.setEditable(true);
        text_area_descricao.setEditable(true);
        botao_alterar.setVisible(false);
        botao_nova.setVisible(false);
        botao_eliminar.setVisible(false);
        lista_tarefas.setEnabled(false);
        this.id_aux = "";
        this.alteracoes = 0;
    }//GEN-LAST:event_botao_novaActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void botao_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_eliminarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String id = text_field_id.getText();
        if (!id.equals("")){
            if (delete_from_bd(id)==0){
                DefaultListModel d = new DefaultListModel();
                Tarefa elimin = null;
                for (Tarefa et : this.lista.values()){
                    if (!et.get_id().equals(id))
                        d.addElement(et.get_nome());
                    else
                        elimin = et;
                }
                lista_tarefas.setModel(d);
                this.lista.remove(elimin.get_nome());
                clear_fields();
                JOptionPane.showMessageDialog(null, "Eliminado com sucesso!");
            }
            else
                JOptionPane.showMessageDialog(null, "Erro, contacte administrador!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Deve seleccionar um item!");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_eliminarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private int delete_from_bd(String id){
        try{
            Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
            String sql = "delete from tnm_trf_tarefa where id = '" + id + "'" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ps.executeUpdate();
            
            sql = "delete from TNM_USER_TAREFA where id_tarefa = '" + id + "'" ;
            ps=con.prepareStatement(sql);
            ps.executeUpdate();
            
            ps.close();
            con.close();
            return 0;
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
            return 1;
        }
    }
    
    private void clear_fields(){
        text_field_id.setText("");
        text_field_nome.setText("");
        text_area_descricao.setText("");
        etiqueta_label.setText("Tarefa: Nenhuma tarefa seleccionada");
        lista_tarefas.clearSelection();
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList lista_tarefas;
    private javax.swing.JPanel painel_etapa;
    private javax.swing.JTextArea text_area_descricao;
    private javax.swing.JTextField text_field_id;
    private javax.swing.JTextField text_field_nome;
    // End of variables declaration//GEN-END:variables
}
