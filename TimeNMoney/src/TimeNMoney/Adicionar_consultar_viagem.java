/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import com.toedter.calendar.JTextFieldDateEditor;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
public class Adicionar_consultar_viagem extends javax.swing.JFrame {
    Funcionario user;
    ArrayList<Viagem> lista;
    ArrayList<Funcionario> funcionarios;
    String id_aux;
    int alteracoes;
    boolean novo;
    /**
     * Creates new form Adicionar_etapa
     * @param f
     */
    public Adicionar_consultar_viagem(Funcionario f) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.user = f;
        carrega_lista();
        this.id_aux = "";
        this.alteracoes = 0;
        this.funcionarios = new ArrayList<>();
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        botao_nova.setVisible(true);
        botao_id_auto.setEnabled(false);
        text_field_id.setEditable(false);
        //text_field_funcionario.setEditable(false);
        combo_funcionario.setEnabled(false);
        text_field_pais_destino.setEditable(false);
        ((JButton)date_field_inicio.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)date_field_inicio.getDateEditor()).setEditable(false);
        ((JButton)date_field_fim.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)date_field_fim.getDateEditor()).setEditable(false);
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
        this.novo = false;
        carrega_lista_funcionarios();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void carrega_lista(){
        ArrayList<Viagem> lista_aux = new ArrayList<>();
        DefaultListModel dlm = new DefaultListModel();
        try{
        Connection con = (new Connection_bd(this.user.get_username())).get_connection();
        String sql = "select * from tnm_viagens";
        if (!this.user.get_admin())
            sql+= " where funcionario = '"+ this.user.get_nome() +"'";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String id = rs.getString("ID");
            String funcionario = rs.getString("FUNCIONARIO");
            String destino = rs.getString("DESTINO");
            Date data_inicio = rs.getDate("DATA_PARTIDA");
            Date data_fim = rs.getDate("DATA_CHEGADA");
            Viagem vi = new Viagem();
            vi.set_id(id);
            vi.set_funcionario(funcionario);
            vi.set_destino_pais(destino);
            vi.set_data_partida(data_inicio);
            vi.set_data_chegada(data_fim);
            lista_aux.add(vi);
            dlm.addElement(id + " - "+ destino);
            
        }
        lista_viagens.setModel(dlm);
        this.lista = lista_aux;
        lista_viagens.addListSelectionListener(new ListSelectionListener() {

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
            new Log_erros_class().write_log_to_file(this.user.get_username(),e);
        }
    }
    
    @SuppressWarnings("unchecked")
	private void carrega_lista_funcionarios(){
        try{
            Connection con = (new Connection_bd(this.user.get_username())).get_connection();
            String sql = "select * from tnm_funcionario" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Funcionario aux = new Funcionario();
                aux.set_nome(rs.getString("NOME"));
                aux.set_username(rs.getString("USERNAME"));
                this.funcionarios.add(aux);   
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.user.get_username(),e);
        }
        
        if (this.user.get_admin()){
            //aparecem todos os funcionarios
            for (Funcionario f : this.funcionarios)
                combo_funcionario.addItem(f.get_nome());
        }
        else{
            //so aparece o nome do funcionario
            for (Funcionario f : this.funcionarios)
                if (f.get_username().equals(this.user.get_username()))
                   combo_funcionario.addItem(f.get_nome());
        }
    }
    
    private int exist_id_bd(String id){
        try{
            Connection con = (new Connection_bd(this.user.get_username())).get_connection();
            String sql = "select * from tnm_viagens where id = '" + id + "'" ;
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
            new Log_erros_class().write_log_to_file(this.user.get_username(),e);
            return 0;
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private int save_bd(boolean novo){
        String id = text_field_id.getText();
        String funcionario = combo_funcionario.getSelectedItem().toString();
        String destino = text_field_pais_destino.getText();
        Date data_inicio = date_field_inicio.getDate();
        Date data_fim = date_field_fim.getDate();
        
        try{
            String sql;
            PreparedStatement ps;
            Connection con = (new Connection_bd(this.user.get_username())).get_connection();
            String id_auxiliar;
            if (novo)
                id_auxiliar = id;
            else
                id_auxiliar = this.id_aux;
            int exist_bd = exist_id_bd(id_auxiliar);
            if (exist_bd!=0){
                if (!this.novo && (exist_id_bd(id)==0 || id.equals(id_auxiliar))){
                    sql = "update tnm_viagens set id = ?, funcionario = ?, destino = ?, data_partida = ?, data_chegada = ? where id = '" + id_auxiliar + "'";
                    DefaultListModel d = new DefaultListModel();
                    for (Viagem vi : this.lista){
                        if (vi.get_id().equals(id_auxiliar)){
                            vi.set_id(id);
                            vi.set_funcionario(funcionario);
                            vi.set_destino_pais(destino);
                            vi.set_data_partida(data_inicio);
                            vi.set_data_chegada(data_fim);
                        }
                        d.addElement(vi.get_id() + " - " + vi.get_destino_pais());
                    }
                    lista_viagens.setModel(d);
                }
                else{
                    JOptionPane.showMessageDialog(null, "ID já existe!");
                    return 0;
                }
            }
            else{
                sql = "insert into tnm_viagens (id,funcionario,destino,data_partida,data_chegada) values (?,?,?,?,?)";
                Viagem vi = new Viagem();
                vi.set_id(id);
                vi.set_funcionario(funcionario);
                vi.set_destino_pais(destino);
                vi.set_data_partida(data_inicio);
                vi.set_data_chegada(data_fim);
                this.lista.add(vi);
                DefaultListModel d = new DefaultListModel();
                for (Viagem v_aux : this.lista)
                    d.addElement(v_aux.get_id() + " - " + v_aux.get_destino_pais());
                lista_viagens.setModel(d);

            }
            ps=con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, funcionario);
            ps.setString(3, destino);
            long inicio = 0;
            if (data_inicio!=null)
                inicio = data_inicio.getTime();
            java.sql.Date data_inicio_aux = new java.sql.Date(inicio);
            ps.setDate(4, data_inicio_aux);
            long fim = 0;
            if (data_fim!=null)
                fim = data_fim.getTime();
            java.sql.Date data_fim_aux = new java.sql.Date(fim);
            ps.setDate(5, data_fim_aux);
            ps.executeUpdate();
            this.alteracoes = 0;
            lista_viagens.setEnabled(true);
            etiqueta_label.setText("Viagem: "+ id);
            botao_cancelar.setVisible(false);
            botao_guardar.setVisible(false);
            botao_nova.setVisible(true);
            botao_id_auto.setEnabled(false);
            text_field_id.setEditable(false);
            combo_funcionario.setEnabled(false);
            text_field_pais_destino.setEditable(false);
            ((JButton)date_field_inicio.getCalendarButton()).setEnabled(false);
            ((JTextFieldDateEditor)date_field_inicio.getDateEditor()).setEditable(false);
            ((JButton)date_field_fim.getCalendarButton()).setEnabled(false);
            ((JTextFieldDateEditor)date_field_fim.getDateEditor()).setEditable(false);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.user.get_username(),e);
            return 0;
        }
        return 1;
    }
    
    public void change_panel(){
        if (this.alteracoes==0)
        {
            String id = "";
            String funcionario = "";
            String destino = "";
            Date data_inicio = null;
            Date data_fim = null;
            if (!lista_viagens.isSelectionEmpty()){
                String[] n_aux = lista_viagens.getSelectedValue().toString().split(" - ");
                String id_aux_local = n_aux[0];
                for (Viagem vi : this.lista){
                    if (vi.get_id().equals(id_aux_local))
                    {
                        id = vi.get_id();
                        funcionario = vi.get_funcionario();
                        destino = vi.get_destino_pais();
                        data_inicio = vi.get_data_partida();
                        data_fim = vi.get_data_chegada();
                        this.id_aux = id;
                    }
                }
                etiqueta_label.setText("Viagem: "+ id);
                text_field_id.setText(id);
                for (int i = 0; i< combo_funcionario.getItemCount();i++)
                    if (combo_funcionario.getItemAt(i).equals(funcionario))
                        combo_funcionario.setSelectedIndex(i);
                text_field_pais_destino.setText(destino);
                date_field_inicio.setDate(data_inicio);
                date_field_fim.setDate(data_fim);
                botao_alterar.setVisible(true);//(this.user.get_admin());
                botao_eliminar.setVisible(true);//(this.user.get_admin());
            }
            else{
                etiqueta_label.setText("Viagem: Nenhuma viagem seleccionada");
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
        lista_viagens = new javax.swing.JList();
        painel_etapa = new javax.swing.JPanel();
        etiqueta_label = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        text_field_id = new javax.swing.JTextField();
        botao_nova = new javax.swing.JButton();
        botao_alterar = new javax.swing.JButton();
        botao_guardar = new javax.swing.JButton();
        botao_cancelar = new javax.swing.JButton();
        botao_eliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        date_field_fim = new com.toedter.calendar.JDateChooser();
        date_field_inicio = new com.toedter.calendar.JDateChooser();
        text_field_pais_destino = new javax.swing.JTextField();
        botao_id_auto = new javax.swing.JButton();
        combo_funcionario = new javax.swing.JComboBox();
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
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/travel-icon.png"))); // NOI18N
        jLabel1.setText("Adicionar/Consultar Viagem");

        jScrollPane1.setViewportView(lista_viagens);

        painel_etapa.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        etiqueta_label.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiqueta_label.setText("Viagem: Nenhuma viagem seleccionada");

        jLabel3.setText("ID:");

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

        jLabel2.setText("Funcionário:");

        jLabel6.setText("País Destino:");

        jLabel7.setText("Data Início:");

        jLabel8.setText("Data Fim:");

        botao_id_auto.setText("ID automático");
        botao_id_auto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_id_autoActionPerformed(evt);
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
                        .addComponent(etiqueta_label, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(botao_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painel_etapaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botao_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botao_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botao_alterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botao_nova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painel_etapaLayout.createSequentialGroup()
                        .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_field_pais_destino)
                            .addGroup(painel_etapaLayout.createSequentialGroup()
                                .addComponent(text_field_id)
                                .addGap(18, 18, 18)
                                .addComponent(botao_id_auto))
                            .addGroup(painel_etapaLayout.createSequentialGroup()
                                .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(date_field_inicio, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(date_field_fim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(combo_funcionario, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(jLabel3)
                    .addComponent(text_field_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_id_auto))
                .addGap(18, 18, 18)
                .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combo_funcionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(text_field_pais_destino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painel_etapaLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painel_etapaLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                                .addGroup(painel_etapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botao_nova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botao_alterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botao_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botao_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(painel_etapaLayout.createSequentialGroup()
                                .addComponent(date_field_fim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(painel_etapaLayout.createSequentialGroup()
                        .addComponent(date_field_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
        					.addComponent(painel_etapa, GroupLayout.PREFERRED_SIZE, 611, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(0, 551, Short.MAX_VALUE)
        					.addComponent(botao_voltar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_sair, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(15)
        					.addComponent(jLabel1)
        					.addGap(0, 464, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel1)
        			.addGap(12)
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
        botao_id_auto.setEnabled(true);
        text_field_id.setEditable(true);
        combo_funcionario.setEnabled(true);
        text_field_pais_destino.setEditable(true);
        ((JButton)date_field_inicio.getCalendarButton()).setEnabled(true);
        ((JTextFieldDateEditor)date_field_inicio.getDateEditor()).setEditable(true);
        ((JButton)date_field_fim.getCalendarButton()).setEnabled(true);
        ((JTextFieldDateEditor)date_field_fim.getDateEditor()).setEditable(true);
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
        botao_nova.setVisible(false);
        this.alteracoes = 1;
        lista_viagens.setEnabled(false);
    }//GEN-LAST:event_botao_alterarActionPerformed

    private void botao_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_cancelarActionPerformed
        this.novo = false;
        change_panel();
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        botao_id_auto.setEnabled(false);
        text_field_id.setEditable(false);
        combo_funcionario.setEnabled(false);
        text_field_pais_destino.setEditable(false);
        ((JButton)date_field_inicio.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)date_field_inicio.getDateEditor()).setEditable(false);
        ((JButton)date_field_fim.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)date_field_fim.getDateEditor()).setEditable(false);
        botao_alterar.setVisible(true);
        botao_eliminar.setVisible(true);
        botao_nova.setVisible(true);
        this.alteracoes = 0;
        lista_viagens.setEnabled(true);
        clear_fields();
    }//GEN-LAST:event_botao_cancelarActionPerformed

    private void botao_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_guardarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int res = validar_datas();
        if (res==0){
            int ret = save_bd(this.novo);
            if (ret>0){
                JOptionPane.showMessageDialog(null, "Guardado com sucesso");
                this.novo = false;
                clear_fields();
            }
        }
        else if (res==1){
            JOptionPane.showMessageDialog(null, "Data de chegada não pode ser anterior à data de partida!");
            date_field_fim.setDate(date_field_inicio.getDate());
        }
        else{
            JOptionPane.showMessageDialog(null, "Não pode ter data de chegada e não ter data de partida!");
            date_field_inicio.setDate(date_field_fim.getDate());
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_guardarActionPerformed

    private int validar_datas(){
        //0 se tudo ok
        //1 se data anterior
        //2 se data inicio null e data fim nao null
        Date a1 = date_field_inicio.getDate();
        Date a2 = date_field_fim.getDate();
        if (a1!=null && a2==null)
            return 0;
        else if (a1==null && a2 !=null)
            return 2;
        else if (a1!=null && a2 != null && a2.before(a1))
            return 1;
        else
            return 0;
    }
    
    private void botao_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_sairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_botao_sairActionPerformed

    private void botao_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_voltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botao_voltarActionPerformed

    private void botao_novaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_novaActionPerformed
        this.alteracoes = 1;
        this.novo = true;
        lista_viagens.clearSelection();
        text_field_id.setText("");
        combo_funcionario.setSelectedIndex(0);
        text_field_pais_destino.setText("");
        date_field_inicio.setDate(null);
        date_field_fim.setDate(null);
        etiqueta_label.setText("Nova viagem");
        botao_cancelar.setVisible(true);
        botao_guardar.setVisible(true);
        botao_id_auto.setEnabled(true);
        text_field_id.setEditable(true);
        combo_funcionario.setEnabled(true);
        text_field_pais_destino.setEditable(true);
        ((JButton)date_field_inicio.getCalendarButton()).setEnabled(true);
        ((JTextFieldDateEditor)date_field_inicio.getDateEditor()).setEditable(true);
        ((JButton)date_field_fim.getCalendarButton()).setEnabled(true);
        ((JTextFieldDateEditor)date_field_fim.getDateEditor()).setEditable(true);
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
        botao_nova.setVisible(false);
        lista_viagens.setEnabled(false);
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
                Viagem elimin = null;
                for (Viagem vi : this.lista){
                    if (!vi.get_id().equals(id))
                        d.addElement(vi.get_id() + " - " + vi.get_destino_pais());
                    else
                        elimin = vi;
                }
                lista_viagens.setModel(d);
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
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_eliminarActionPerformed

    private void botao_id_autoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_id_autoActionPerformed
        ArrayList<String> lista_aux = new ArrayList<>();
        try{
            Connection con = (new Connection_bd(this.user.get_username())).get_connection();
            String sql = "select * from tnm_viagens" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String id_ret = rs.getString("ID");
                if (id_ret.contains("VIAGEM"))
                    lista_aux.add(id_ret);
            }
            text_field_id.setText(get_next_id(lista_aux));
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.user.get_username(),e);
        }        
    }//GEN-LAST:event_botao_id_autoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private String get_next_id(ArrayList<String> lista){
        String ret;
        int i = 1;
        for (String s : lista)
        {
            String[] aux = s.split("VIAGEM");
            String num = aux[1];
            try{
                if (Integer.valueOf(num) == i)
                    i++;
            }
            catch(NumberFormatException e){
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(this.user.get_username(),e);
            }
        }
        if (i<10)
            ret = "VIAGEM000"+String.valueOf(i);
        else if (i>9 && i<100)
            ret = "VIAGEM00"+String.valueOf(i);
        else if (i>99 && i<1000)
            ret = "VIAGEM0"+String.valueOf(i);
        else 
            ret = "VIAGEM"+String.valueOf(i);
        return ret;
    }
    
    private int delete_from_bd(String id){
        try{
            Connection con = (new Connection_bd(this.user.get_username())).get_connection();
            String sql = "delete from tnm_viagens where id = '" + id + "'" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ps.executeUpdate();
            return 0;
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.user.get_username(),e);
            return 1;
        }
    }
    
    private void clear_fields(){
        text_field_id.setText("");
        combo_funcionario.setSelectedIndex(0);
        text_field_pais_destino.setText("");
        date_field_inicio.setDate(null);
        date_field_fim.setDate(null);
        etiqueta_label.setText("Viagem: Nenhuma viagem seleccionada");
        lista_viagens.clearSelection();
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botao_alterar;
    private javax.swing.JButton botao_cancelar;
    private javax.swing.JButton botao_eliminar;
    private javax.swing.JButton botao_guardar;
    private javax.swing.JButton botao_id_auto;
    private javax.swing.JButton botao_nova;
    private javax.swing.JButton botao_sair;
    private javax.swing.JButton botao_voltar;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox combo_funcionario;
    private com.toedter.calendar.JDateChooser date_field_fim;
    private com.toedter.calendar.JDateChooser date_field_inicio;
    private javax.swing.JLabel etiqueta_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList lista_viagens;
    private javax.swing.JPanel painel_etapa;
    private javax.swing.JTextField text_field_id;
    private javax.swing.JTextField text_field_pais_destino;
    // End of variables declaration//GEN-END:variables
}
