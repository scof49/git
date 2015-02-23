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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
@SuppressWarnings("serial")
public class Adicionar_consultar_funcionario extends javax.swing.JFrame {
    ArrayList<Funcionario> lista;
    String id_aux;
    int alteracoes;
    boolean novo;
    boolean admin;
    Funcionario funcionario;
    /**
     * Creates new form Adicionar_etapa
     * @param f
     */
    public Adicionar_consultar_funcionario(Funcionario f) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.admin = f.get_admin();
        this.funcionario = f;
        carrega_lista();
        this.id_aux = "";
        this.alteracoes = 0;
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        username_field.setEditable(false);
        nome_field.setEditable(false);
        contacto_ao_field.setEditable(false);
        contacto_pt_field.setEditable(false);
        mail_field.setEditable(false);
        contacto_sk_lq.setEditable(false);
        ((JButton)data_nascimento_field.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)data_nascimento_field.getDateEditor()).setEditable(false);
        admin_check.setEnabled(false);
        botao_alterar.setVisible(false);
        reset_pass_button.setVisible(false);
        botao_eliminar.setVisible(false);
        botao_nova.setVisible(this.admin);
        this.novo = false;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void carrega_lista(){
        ArrayList<Funcionario> lista_aux = new ArrayList<>();
        DefaultListModel dlm = new DefaultListModel();
        try{
            Connection con = (new Connection_bd()).get_connection();
        String sql = "select * from tnm_funcionario";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String user_name = rs.getString("USERNAME");
            String nome = rs.getString("NOME");
            String contacto_pt = rs.getString("TELEMOVEL_PT");
            String contacto_ao = rs.getString("TELEMOVEL_AO");
            String contacto_sk = rs.getString("SKYPE");
            String mail = rs.getString("MAIL");
            boolean ad = (rs.getInt("ADMIN") != 0);
            Date dn = rs.getDate("DATA_NASCIMENTO");
            
            Funcionario fn = new Funcionario();
            fn.set_username(user_name);
            fn.set_nome(nome);            
            fn.set_phone_pt(contacto_pt);
            fn.set_phone_ao(contacto_ao);
            fn.set_mail(mail);
            fn.set_id_skype(contacto_sk);
            fn.set_admin(ad);
            fn.set_nascimento(dn);
            lista_aux.add(fn);
            dlm.addElement(nome + " ( " + user_name + " )");
            
        }
        lista_funcionarios.setModel(dlm);
        this.lista = lista_aux;
        lista_funcionarios.addListSelectionListener(new ListSelectionListener() {

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
    
    private int exist_username_bd(String user_name){
        int res;
        try{
            Connection con = (new Connection_bd()).get_connection();
            String sql = "select * from tnm_funcionario where username = '" + user_name + "'" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                res=1;
            else
                res=0;
            
            rs.close();
            ps.close();
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            res = 0;
        }
        return res;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private int save_bd(boolean novo){
        String user_name = username_field.getText();
        String nome = nome_field.getText();
        String mail = mail_field.getText();
        String contacto_pt = contacto_pt_field.getText();
        String contacto_ao = contacto_ao_field.getText();
        String contacto_sk = contacto_sk_lq.getText();
        boolean ad = admin_check.isSelected();
        Date dn = data_nascimento_field.getDate();
        try{
    	String sql;
        Connection con = (new Connection_bd()).get_connection();
        String id_auxiliar;
        PreparedStatement ps;
        int op = 0;
        if (novo)
            id_auxiliar = user_name;
        else
            id_auxiliar = this.id_aux;
        int exist_bd = exist_username_bd(id_auxiliar);
        if (exist_bd!=0){
            if (!this.novo && (exist_username_bd(user_name)==0 || user_name.equals(id_auxiliar))){
                sql = "update tnm_funcionario set username = ?, nome = ?, mail = ?, telemovel_pt = ?, telemovel_ao = ?, skype = ?, data_nascimento = ?, admin = ? where username = ?";
                op = 1;
                DefaultListModel d = new DefaultListModel();
                for (Funcionario fun : this.lista){
                    if (fun.get_username().equals(this.id_aux)){
                        fun.set_nome(nome);
                        fun.set_username(user_name);
                        fun.set_phone_pt(contacto_pt);
                        fun.set_phone_ao(contacto_ao);
                        fun.set_nascimento(dn);
                        fun.set_admin(ad);
                        fun.set_id_skype(contacto_sk);
                        fun.set_mail(mail);
                    }
                    d.addElement(fun.get_nome() + " ( " +fun.get_username() + " )");
                }
                lista_funcionarios.setModel(d);
            }
            else{
                JOptionPane.showMessageDialog(null, "ID já existe!");
                return 0;
            }
        }
        else{
            sql = "insert into tnm_funcionario (username,nome,mail,telemovel_pt,telemovel_ao,skype,data_nascimento,admin) values (?,?,?,?,?,?,?,?)";
            Funcionario fun = new Funcionario();
            fun.set_nome(nome);
            fun.set_username(user_name);
            fun.set_phone_pt(contacto_pt);
            fun.set_phone_ao(contacto_ao);
            fun.set_nascimento(dn);
            fun.set_admin(ad);
            fun.set_id_skype(contacto_sk);
            fun.set_mail(mail);
            this.lista.add(fun);
            DefaultListModel d = new DefaultListModel();
            for (Funcionario fn : this.lista)
                d.addElement(fn.get_nome() + " ( " +fn.get_username() + " )");
            lista_funcionarios.setModel(d);
            if (this.novo){
                set_bd_logs_new_func(user_name,con);
                set_funcionario_id_para_tarefas(user_name,con);
            }
        }
        ps=con.prepareStatement(sql);
        
        ps.setString(1, user_name);
        ps.setString(2, nome);
        ps.setString(3, mail);
        ps.setString(4, contacto_pt);
        ps.setString(5, contacto_ao);
        ps.setString(6, contacto_sk);
        java.sql.Date aux = null;
        if (dn!=null)
            aux = new java.sql.Date(dn.getTime());
        ps.setDate(7, aux);
        int ad_int = 0;
        if (ad)
            ad_int = 1;
        ps.setInt(8, ad_int);
        if (op==1){
            ps.setString(9, user_name);
        }
        ps.executeUpdate();
        this.alteracoes = 0;
        lista_funcionarios.setEnabled(true);
        etiqueta_label.setText("Funcionário: "+ nome);
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        botao_nova.setVisible(true);
        username_field.setEditable(false);
        nome_field.setEditable(false);
        contacto_ao_field.setEditable(false);
        contacto_pt_field.setEditable(false);
        mail_field.setEditable(false);
        contacto_sk_lq.setEditable(false);
        ((JButton)data_nascimento_field.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)data_nascimento_field.getDateEditor()).setEditable(false);
        admin_check.setEnabled(false);
        
        ps.close();
        con.close();
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
    
    private void set_bd_logs_new_func(String username,Connection con){
        try{
        	String pass = "odkas.tnm";
	    	byte[] pass_b = set_password(pass);
	    	String sql = "insert into tnm_enc_logb (username,password) values (?,?)" ;
            
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setBytes(2, pass_b);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
    }
    
    private void set_funcionario_id_para_tarefas(String username,Connection con){
    	String id = get_new_id_funcionario(con);
    	try{
    		String sql = "insert into tnm_user_id (username,id_user) values (?,?)";
    		PreparedStatement ps = con.prepareStatement(sql);
    		ps.setString(1, username);
    		ps.setString(2, id);
    		ps.executeUpdate();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		this.setCursor(Cursor.getDefaultCursor());
    		new Log_erros_class().write_log_to_file(e);
    	}
    }
    
    private String get_new_id_funcionario(Connection con){
    	String res = "";
    	int max = 0;
    	try{
    		String sql = "select * from tnm_user_id";
    		PreparedStatement ps = con.prepareStatement(sql);
    		ResultSet rs = ps.executeQuery();
    		while (rs.next()){
    			int aux = Integer.parseInt(rs.getString("ID_USER"));
    			if (aux>max)
    				max = aux;
    		}
    		max++;
    		if (max<10){
    			res = "000" + max;
    		}
    		else if (max<100){
    			res = "00" + max;
    		}
    		else if (max<1000){
    			res = "0" + max;
    		}
    		else
    			res = String.valueOf(max);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		this.setCursor(Cursor.getDefaultCursor());
    		new Log_erros_class().write_log_to_file(e);
    	}
    	return res;
    }
    
    public void change_panel(){
        if (this.alteracoes==0)
        {
            String user_name = "";
            String nome = "";
            String mail = "";
            String contacto_pt = "";
            String contacto_ao = "";
            String contacto_sk = "";
            boolean ad = false;
            Date dn = null;
            if (!lista_funcionarios.isSelectionEmpty()){
                String n_aux = lista_funcionarios.getSelectedValue().toString();
                String[] n_aux_lista = n_aux.split("\\(");
                n_aux = n_aux_lista[1].replace(" ", "");
                n_aux = n_aux.replace(")", "");
                for (Funcionario fun : this.lista){
                    if (fun.get_username().equals(n_aux))
                    {
                        user_name = fun.get_username();
                        nome = fun.get_nome();
                        mail = fun.get_mail();
                        contacto_pt = fun.get_phone_pt();
                        contacto_ao = fun.get_phone_ao();
                        contacto_sk = fun.get_id_skype();
                        ad = fun.get_admin();
                        dn = fun.get_nascimento();
                        this.id_aux = user_name;
                    }
                }
                etiqueta_label.setText("Funcionário: "+ nome);
                username_field.setText(user_name);
                nome_field.setText(nome);
                mail_field.setText(mail);
                contacto_pt_field.setText(contacto_pt);
                contacto_ao_field.setText(contacto_ao);
                contacto_sk_lq.setText(contacto_sk);
                data_nascimento_field.setDate(dn);
                admin_check.setSelected(ad);
                botao_alterar.setVisible(this.admin);
                botao_eliminar.setVisible(this.admin);
            }
            else{
                etiqueta_label.setText("Funcionário: Nenhum funcionário seleccionado");
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({ "rawtypes", "deprecation" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista_funcionarios = new javax.swing.JList();
        painel_etapa = new javax.swing.JPanel();
        etiqueta_label = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        username_field = new javax.swing.JTextField();
        nome_field = new javax.swing.JTextField();
        botao_nova = new javax.swing.JButton();
        botao_alterar = new javax.swing.JButton();
        botao_guardar = new javax.swing.JButton();
        botao_cancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        mail_field = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        contacto_pt_field = new javax.swing.JTextField();
        botao_eliminar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        contacto_ao_field = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        contacto_sk_lq = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        data_nascimento_field = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        admin_check = new javax.swing.JCheckBox();
        botao_sair = new javax.swing.JButton();
        botao_voltar = new javax.swing.JButton();
        reset_pass_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/funcionario_icon.png"))); // NOI18N
        jLabel1.setText("Adicionar/Consultar Funcionário");

        jScrollPane1.setViewportView(lista_funcionarios);

        painel_etapa.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        etiqueta_label.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiqueta_label.setText("Funcionário: Nenhum funcionário seleccionado");

        jLabel3.setText("Username:");

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

        jLabel2.setText("Email:");

        jLabel6.setText("Contactos (PT):");

        botao_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_delete.png"))); // NOI18N
        botao_eliminar.setText("Eliminar");
        botao_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_eliminarActionPerformed(evt);
            }
        });

        jLabel7.setText("Contactos (AO):");

        jLabel8.setText("Contacto (SKYPE/LYNC):");

        jLabel9.setText("Data de nascimento:");

        jLabel10.setText("Administrador:");

        javax.swing.GroupLayout painel_etapaLayout = new javax.swing.GroupLayout(painel_etapa);
        painel_etapaLayout.setHorizontalGroup(
        	painel_etapaLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(painel_etapaLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(painel_etapaLayout.createSequentialGroup()
        					.addGap(0, 97, Short.MAX_VALUE)
        					.addComponent(botao_guardar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_cancelar, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_alterar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_nova, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(painel_etapaLayout.createSequentialGroup()
        					.addGroup(painel_etapaLayout.createParallelGroup(Alignment.TRAILING, false)
        						.addComponent(jLabel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel6, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        						.addComponent(jLabel2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(painel_etapaLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(nome_field, 342, 342, 342)
        						.addComponent(username_field, 342, 342, 342)
        						.addComponent(mail_field, 342, 342, 342)
        						.addComponent(contacto_pt_field, 342, 342, 342)
        						.addComponent(contacto_ao_field, 342, 342, 342)
        						.addComponent(contacto_sk_lq, 342, 342, 342)
        						.addComponent(data_nascimento_field, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)))
        				.addGroup(painel_etapaLayout.createSequentialGroup()
        					.addGroup(painel_etapaLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(painel_etapaLayout.createSequentialGroup()
        							.addComponent(etiqueta_label, GroupLayout.PREFERRED_SIZE, 474, GroupLayout.PREFERRED_SIZE)
        							.addGap(18)
        							.addComponent(botao_eliminar))
        						.addGroup(painel_etapaLayout.createSequentialGroup()
        							.addComponent(jLabel10, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(admin_check)))
        					.addGap(0, 0, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        painel_etapaLayout.setVerticalGroup(
        	painel_etapaLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(painel_etapaLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(etiqueta_label, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        				.addComponent(botao_eliminar))
        			.addGap(18)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel3)
        				.addComponent(username_field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel4)
        				.addComponent(nome_field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel2)
        				.addComponent(mail_field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel6)
        				.addComponent(contacto_pt_field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel7)
        				.addComponent(contacto_ao_field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel8)
        				.addComponent(contacto_sk_lq, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jLabel9)
        				.addComponent(data_nascimento_field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(11)
        			.addGroup(painel_etapaLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jLabel10)
        				.addComponent(admin_check))
        			.addGap(13)
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

        reset_pass_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/pass_icon.png"))); // NOI18N
        reset_pass_button.setLabel("<html>Reset<br />Password</html>");
        reset_pass_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_pass_buttonActionPerformed(evt);
            }
        });
        
        at_desact_button = new JButton("Ativar/Desativar");
        at_desact_button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		goto_menu_ap_disap_funcionario();
        	}
        });
        at_desact_button.setIcon(new ImageIcon(Adicionar_consultar_funcionario.class.getResource("/TimeNMoney/ap_disap_funci.png")));
        
        manager_btn = new JButton("Menu Manager");
        manager_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		goto_menu_manager();
        	}
        });
        manager_btn.setIcon(new ImageIcon(Adicionar_consultar_funcionario.class.getResource("/TimeNMoney/manager_icon.png")));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
        					.addGap(18)
        					.addComponent(painel_etapa, GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(at_desact_button, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(reset_pass_button, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(manager_btn)
        					.addPreferredGap(ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
        					.addComponent(botao_voltar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(botao_sair, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(8)
        					.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 597, GroupLayout.PREFERRED_SIZE)
        					.addGap(0, 226, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        				.addComponent(painel_etapa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        				.addComponent(manager_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(botao_sair, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(botao_voltar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(at_desact_button, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        				.addComponent(reset_pass_button, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botao_alterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_alterarActionPerformed
        this.novo = false;
        botao_cancelar.setVisible(true);
        botao_guardar.setVisible(true);
        username_field.setEditable(true);
        nome_field.setEditable(true);
        contacto_ao_field.setEditable(true);
        contacto_pt_field.setEditable(true);
        mail_field.setEditable(true);
        contacto_sk_lq.setEditable(true);
        ((JButton)data_nascimento_field.getCalendarButton()).setEnabled(true);
        ((JTextFieldDateEditor)data_nascimento_field.getDateEditor()).setEditable(true);
        admin_check.setEnabled(true);
        reset_pass_button.setVisible(true);
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
        botao_nova.setVisible(false);
        this.alteracoes = 1;
        lista_funcionarios.setEnabled(false);
    }//GEN-LAST:event_botao_alterarActionPerformed

    private void botao_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_cancelarActionPerformed
        this.novo = false;
        change_panel();
        botao_cancelar.setVisible(false);
        botao_guardar.setVisible(false);
        username_field.setEditable(false);
        nome_field.setEditable(false);
        contacto_ao_field.setEditable(false);
        contacto_pt_field.setEditable(false);
        mail_field.setEditable(false);
        contacto_sk_lq.setEditable(false);
        ((JButton)data_nascimento_field.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)data_nascimento_field.getDateEditor()).setEditable(false);
        admin_check.setEnabled(false);
        reset_pass_button.setVisible(false);
        botao_alterar.setVisible(true);
        botao_nova.setVisible(true);
        botao_eliminar.setVisible(true);
        this.alteracoes = 0;
        lista_funcionarios.setEnabled(true);
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
        lista_funcionarios.clearSelection();
        clear_fields();
        etiqueta_label.setText("Novo funcionário");
        botao_cancelar.setVisible(true);
        botao_guardar.setVisible(true);
        username_field.setEditable(true);
        nome_field.setEditable(true);
        contacto_ao_field.setEditable(true);
        contacto_pt_field.setEditable(true);
        mail_field.setEditable(true);
        contacto_sk_lq.setEditable(true);
        ((JButton)data_nascimento_field.getCalendarButton()).setEnabled(true);
        ((JTextFieldDateEditor)data_nascimento_field.getDateEditor()).setEditable(true);
        admin_check.setEnabled(true);
        botao_alterar.setVisible(false);
        botao_nova.setVisible(false);
        botao_eliminar.setVisible(false);
        lista_funcionarios.setEnabled(false);
        this.id_aux = "";
        this.alteracoes = 0;
    }//GEN-LAST:event_botao_novaActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void botao_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_eliminarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String user = username_field.getText();
        if (!user.equals("")){
            if (delete_from_bd(user)==0){
                DefaultListModel d = new DefaultListModel();
                Funcionario elimin = null;
                for (Funcionario et : this.lista){
                    if (!et.get_username().equals(user))
                        d.addElement(et.get_nome() + " ( "+ et.get_username() + " )");
                    else
                        elimin = et;
                }
                lista_funcionarios.setModel(d);
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void reset_pass_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_pass_buttonActionPerformed
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	int res = reset_password(this.id_aux);
    	if (res==0)
    	{
    		clear_fields();
    		JOptionPane.showMessageDialog(null, "Password reiniciada com sucesso!");
    	}
    	else if (res<0)
    		JOptionPane.showMessageDialog(null, "Funcionário não tem log. Confirme se está ativo antes de reiniciar palavra pass ou contacte responsável!");
    	else
    		JOptionPane.showMessageDialog(null, "Ocorrência de erro! Contacte responsável!");
    	
    	this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_reset_pass_buttonActionPerformed

    private static byte[] set_password(String text){
    	try {
         	 String key = "491massaIvo51765"; // 128 bit key
	         SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), "AES");
	         Cipher cipher = Cipher.getInstance("AES");
	         cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	         byte[] encrypted = cipher.doFinal(text.getBytes());
	         return encrypted;
	      }catch(Exception e) {
	         return null;
	      }
    }
    
    private int reset_password(String username){
    	try{
	    	String pass = "odkas.tnm";
	    	byte[] pass_b = set_password(pass);
    		Connection con = (new Connection_bd()).get_connection();
    		
    		String sql = "select * from tnm_enc_logb where username = ?";
    		PreparedStatement ps=con.prepareStatement(sql);
	        ps.setString(1, username);
	        ResultSet rs = ps.executeQuery();
    		if (rs.next())
    		{
	    		sql = "update tnm_enc_logb set password = ? where username = ?" ;
		        ps=con.prepareStatement(sql);
		        ps.setBytes(1, pass_b);
		        ps.setString(2, username);
		        ps.executeUpdate();
    		}
    		else
    			return -1;
	        ps.close();
	        con.close();
	        return 0;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		this.setCursor(Cursor.getDefaultCursor());
    		new Log_erros_class().write_log_to_file(e);
            return 1;
    	}
    }
    
    private int delete_from_bd(String username){
        try{
            Connection con = (new Connection_bd()).get_connection();
            String sql = "delete from tnm_funcionario where username = '" + username + "'" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ps.executeUpdate();
            
            sql = "delete from tnm_enc_logb where username = '" + username + "'" ;
            ps=con.prepareStatement(sql);
            ps.executeUpdate();
            
            sql = "delete from tnm_user_id where username = '" + username + "'" ;
            ps=con.prepareStatement(sql);
            ps.executeUpdate();
            
            ps.close();
            con.close();
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
        username_field.setText("");
        nome_field.setText("");
        mail_field.setText("");
        contacto_pt_field.setText("");
        contacto_ao_field.setText("");
        contacto_sk_lq.setText("");
        admin_check.setSelected(false);
        data_nascimento_field.setDate(null);
        etiqueta_label.setText("Cliente: Nenhum cliente seleccionado");
        lista_funcionarios.clearSelection();
        botao_alterar.setVisible(false);
        botao_eliminar.setVisible(false);
        reset_pass_button.setVisible(false);
    }
    
    private void goto_menu_ap_disap_funcionario(){
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	Ativar_desativar_user adu = new Ativar_desativar_user();
    	adu.setVisible(true);
    	this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void goto_menu_manager(){
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	Admin_manager am = new Admin_manager();
    	am.setVisible(true);
    	this.setCursor(Cursor.getDefaultCursor());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox admin_check;
    private javax.swing.JButton botao_alterar;
    private javax.swing.JButton botao_cancelar;
    private javax.swing.JButton botao_eliminar;
    private javax.swing.JButton botao_guardar;
    private javax.swing.JButton botao_nova;
    private javax.swing.JButton botao_sair;
    private javax.swing.JButton botao_voltar;
    private javax.swing.JTextField contacto_ao_field;
    private javax.swing.JTextField contacto_pt_field;
    private javax.swing.JTextField contacto_sk_lq;
    private com.toedter.calendar.JDateChooser data_nascimento_field;
    private javax.swing.JLabel etiqueta_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList lista_funcionarios;
    private javax.swing.JTextField mail_field;
    private javax.swing.JTextField nome_field;
    private javax.swing.JPanel painel_etapa;
    private javax.swing.JButton reset_pass_button;
    private javax.swing.JTextField username_field;
    private JButton at_desact_button;
    private JButton manager_btn;
    // End of variables declaration//GEN-END:variables
}
