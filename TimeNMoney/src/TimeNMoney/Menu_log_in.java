/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Menu_log_in extends javax.swing.JFrame {
    ArrayList<Funcionario> listaFuncionarios;
    Log_file file_log;
    /**
      * Creates new form Menu_log_in
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Menu_log_in() {
        this.listaFuncionarios = new ArrayList();
        get_file_log();
        get_lista_funcionarios();
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Menu_log_in(Funcionario f) {
        this.listaFuncionarios = new ArrayList();
        get_file_log();
        get_lista_funcionarios();
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        get_user_guardado(f);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Menu_log_in(Funcionario f, boolean auto_log, boolean save_log) {
        this.listaFuncionarios = new ArrayList();
        get_file_log();
        get_lista_funcionarios();
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        get_user_guardado(f);
        check_box_entrar_auto.setSelected(auto_log);
        check_box_guardar_dados.setSelected(save_log);
    }

    private void get_user_guardado(Funcionario f){
        //get user e pass de bd
        String user = f.get_username();
        String pass = get_pass_from_bd(user);
        
        text_field_username.setText(user);
        pass_field_password.setText(pass);
        check_box_guardar_dados.setSelected(true);

    }
    
    private void get_file_log(){
        try{
//        String path = new File("").getAbsolutePath();
//    	String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        String defaultFolder = System.getenv("APPDATA");
        String name_file = defaultFolder + "\\TimeNMoney\\data\\log_file.sav";
        FileInputStream saveFile = new FileInputStream(name_file);
        ObjectInputStream save = new ObjectInputStream(saveFile);
        this.file_log = (Log_file) save.readObject();
        save.close();
        }
        catch(IOException | ClassNotFoundException e){
            this.file_log = null;
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void get_lista_funcionarios(){
        this.listaFuncionarios = new ArrayList();
        if (this.file_log != null){
            this.listaFuncionarios = this.file_log.get_funcionarios();
        }
        else
        {
            try{
                Connection con = (new Connection_bd("login")).get_connection();
                String sql = "SELECT * FROM tnm_FUNCIONARIO";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("NOME");
                    String mail = rs.getString("MAIL");
                    String user = rs.getString("USERNAME");
                    String phone_pt = rs.getString("TELEMOVEL_PT");
                    String phone_ao = rs.getString("TELEMOVEL_AO");
                    boolean admin = (rs.getInt("ADMIN") != 0);
//                    InputStream fs = rs.getBinaryStream("FOTO");
//                    Image img = null;
//                    if (fs!=null)
//                        img = ImageIO.read(fs);
                    byte[] foto = rs.getBytes("FOTO");
                    String idSkype = rs.getString("SKYPE");
                    Date d = rs.getDate("DATA_NASCIMENTO");
                    Funcionario f = new Funcionario();
                    f.set_nome(name);
                    f.set_username(user);
                    f.set_mail(mail);
                    f.set_phone_pt(phone_pt);
                    f.set_phone_ao(phone_ao);
                    f.set_admin(admin);
                    f.set_id_skype(idSkype);
                    f.set_nascimento(d);
//                    if (img!=null){
//                        f.set_foto(img);
//                    }
//                    else 
//                        f.set_foto(null);
                    f.set_foto(foto);
                    this.listaFuncionarios.add(f);
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file("login",e);
            }
        }
    }
    
    private String get_pass_from_bd(String user){
        byte[] pass_b = null;
        
        if ((this.file_log != null) && this.file_log.get_username().equals(user))
        {
            return this.file_log.get_password();
        }
        else
        {
            try{
                Connection con = (new Connection_bd("login")).get_connection();
                java.sql.Statement stmt = con.createStatement();

                ResultSet rs = stmt.executeQuery("SELECT * FROM tnm_enc_logb WHERE USERNAME = '"+ user + "'");
                while (rs.next()) {
                    pass_b = rs.getBytes("PASSWORD");
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file("login",e);
            }
        }
        String pass = get_password(pass_b);
        return pass;
    }
    
	private String get_password(byte[] password){	    	
		try {
			String key = "491massaIvo51765"; // 128 bit key
			SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			String decrypted = new String(cipher.doFinal(password));
			return decrypted;
		}catch(Exception e) {
			 e.printStackTrace();
			 this.setCursor(Cursor.getDefaultCursor());
			 new Log_erros_class().write_log_to_file("login",e);
			 return "";
		}
	}

    
    @SuppressWarnings("deprecation")
	private void log_on(){
        if (text_field_username.getText().equals("") || pass_field_password.getText().equals("")){
           JOptionPane.showMessageDialog(null,"Tem que preencher os campos de username e password obrigatoriamente!");
        }
        else{
            int aut = 0;
            Funcionario segue = new Funcionario();
            String user_aux = text_field_username.getText();
            String pass_aux = get_pass_from_bd(user_aux);
            if (this.listaFuncionarios!=null)
            {
                for (Funcionario f : this.listaFuncionarios)
                {
                    if (f.get_username().equals(user_aux) && pass_aux.equals(pass_field_password.getText()))
                    {
                        aut++;
                        segue = f;
                    }
                }
                if (aut==0)
                {
                    JOptionPane.showMessageDialog(this, "Username ou password incorrectos!");
                    text_field_username.setText("");
                    pass_field_password.setText("");
                }
                else
                {
                    //actualizar bd log
                    //save_log_bd(user_aux,pass_aux,check_box_guardar_dados.isSelected(),check_box_entrar_auto.isSelected());
                	save_log(user_aux,pass_aux,check_box_guardar_dados.isSelected(),check_box_entrar_auto.isSelected());
                	
                    new Menu_inicial_utilizador(segue,check_box_entrar_auto.isSelected(),check_box_guardar_dados.isSelected()).setVisible(true);
                	
                    this.dispose();
                }
            }
            else 
                JOptionPane.showMessageDialog(null, "Não existem funcionários! Contacte administrador.");
        }
    }
    
    private void save_log(String user, String pass, boolean saveData, boolean autoLog){
        Log_file lf = new Log_file(this.listaFuncionarios);
        lf.set_username(user);
        lf.set_password(pass);
        lf.set_autolog(autoLog);
        lf.set_savelog(saveData);
        lf.save_file();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        text_field_username = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pass_field_password = new javax.swing.JPasswordField();
        pass_field_password.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		 int key = e.getKeyCode();
			     if (key == KeyEvent.VK_ENTER) {
			    	 log_button_action();
			     }
        	}
        });
        check_box_guardar_dados = new javax.swing.JCheckBox();
        check_box_entrar_auto = new javax.swing.JCheckBox();
        botao_log_in = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new ImageIcon(Menu_log_in.class.getResource("/TimeNMoney/logo-timemoney2.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("USERNAME:");

        text_field_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                text_field_usernameKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("PASSWORD:");

        check_box_guardar_dados.setText("Guardar dados");

        check_box_entrar_auto.setText("Entrar automaticamente");

        botao_log_in.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        botao_log_in.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/login_icon.png"))); // NOI18N
        botao_log_in.setText("Entrar");
        botao_log_in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_log_inActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(text_field_username)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pass_field_password)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(check_box_guardar_dados)
                    .addComponent(check_box_entrar_auto)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(botao_log_in, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(text_field_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pass_field_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(check_box_guardar_dados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(check_box_entrar_auto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botao_log_in)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(276, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(277, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botao_log_inActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_log_inActionPerformed
        log_button_action();
    }//GEN-LAST:event_botao_log_inActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void text_field_usernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_field_usernameKeyTyped
        pass_field_password.setText("");
    }//GEN-LAST:event_text_field_usernameKeyTyped

    private void log_button_action(){
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        log_on();
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botao_log_in;
    private javax.swing.JCheckBox check_box_entrar_auto;
    private javax.swing.JCheckBox check_box_guardar_dados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField pass_field_password;
    private javax.swing.JTextField text_field_username;
    // End of variables declaration//GEN-END:variables
}
