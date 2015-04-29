/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Auto_Log_Class {
    ArrayList<Funcionario> lista;
    
    public Auto_Log_Class(){
        this.lista = new ArrayList<>();
    }
    
    public void init(){
        get_user_guardado();
    }
    
    private Log_file read_file(){
        try{
//        	String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        	String defaultFolder = System.getenv("APPDATA");
            String name_file = defaultFolder + "\\TimeNMoney\\data\\log_file.sav";
            FileInputStream saveFile = new FileInputStream(name_file);
            
            Log_file aux;
            try (ObjectInputStream save = new ObjectInputStream(saveFile)) {
                aux = (Log_file) save.readObject();
            } 
            return aux;
        }
        catch(IOException | ClassNotFoundException e){
            return null;
        }
    }
    
    private void get_user_guardado(){
        Log_file lf = read_file();
        String user = "";
        String pass = "";
        boolean saveData = false;
        boolean autoLog = false;
        
        if (lf != null){
            user = lf.get_username();
            pass = lf.get_password();
            saveData = lf.get_savelog();
            autoLog = lf.get_autolog();
            this.lista = lf.get_funcionarios();
        }
        if (user.equals("") || pass.equals("") || !saveData)
        {
            new Menu_log_in().setVisible(true);
        }
        else if (!user.equals("") && !pass.equals("") && autoLog && saveData){
            log_on(user,pass);
        }
        else{
            Funcionario segue = get_funcionario(user,pass);
            new Menu_log_in(segue).setVisible(true);
        }
    }  
    
    private Funcionario get_funcionario(String user, String pass){
        Funcionario segue = null;
        //if (get_pass_from_bd(user).equals(pass))
            for (Funcionario f : this.lista)
            {
                if (f.get_username().equals(user))
                    segue = f;
            }
        return segue;
    }
    
    @SuppressWarnings("unused")
	private String get_pass_from_bd(String user){
        byte[] pass_b = null;
        try{
            Connection con = (new Connection_bd(user)).get_connection();
            java.sql.Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM tnm_enc_logb WHERE USERNAME = '"+ user +"'");
            while (rs.next()) {
                pass_b = rs.getBytes("PASSWORD");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(user,e);
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
	    	new Log_erros_class().write_log_to_file("null",e);
            return "";
	    }
    }

    
    private void log_on(String user, String pass){
    
        Funcionario segue = get_funcionario(user,pass);
        if (this.lista!=null)
        {
            if (segue!=null){
                new Menu_inicial_utilizador(segue,true,true).setVisible(true);
                
            }
            else{
                new Menu_log_in().setVisible(true);
            }
        }
        else {
            //JOptionPane.showMessageDialog(null,"Contacte administrador, não existem users em base de dados");
            new Menu_log_in().setVisible(true);
        }
    }
}
