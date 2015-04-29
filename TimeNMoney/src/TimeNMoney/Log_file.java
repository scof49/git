/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Ivo.Oliveira
 */
public class Log_file implements Serializable{
    
    private static final long serialVersionUID = 6529685098267757690L;
    String username;
    String password;
    boolean savelog;
    boolean autolog;
    ArrayList<Funcionario> lista_funcionarios;
    
    public Log_file()
    {
        this.username = "";
        this.password = "";
        this.savelog = false;
        this.autolog = false;
        get_lista_funcionarios();
    }
    
    public Log_file(ArrayList<Funcionario> l)
    {
        this.username = "";
        this.password = "";
        this.savelog = false;
        this.autolog = false;
        this.lista_funcionarios = l;
    }
    
    public ArrayList<Funcionario> get_funcionarios()
    {
        return this.lista_funcionarios;
    }
    
    public void set_funcionarios(ArrayList<Funcionario> f)
    {
        this.lista_funcionarios = f;
    }
    
    public String get_username()
    {
        return this.username;
    }
    
    public String get_password()
    {
        return this.password;
    }
    
    public boolean get_savelog()
    {
        return this.savelog;
    }
    
    public boolean get_autolog()
    {
        return this.autolog;
    }
    
    public void set_username(String user)
    {
        this.username = user;
    }
    
    public void set_password(String pass)
    {
        this.password = pass;
    }
    
    public void set_savelog(boolean save)
    {
        this.savelog = save;
    }
    
    public void set_autolog(boolean save)
    {
        this.autolog = save;
    }
    
    public void save_file(){
        try{  
//            File s_file = new File("data");
//            s_file.mkdirs();
//            String path = s_file.getAbsolutePath();
//            String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        	String defaultFolder = System.getenv("APPDATA");
            File s_file = new File(defaultFolder + "\\TimeNMoney\\data");
            s_file.mkdirs();
            String name_file = defaultFolder + "\\TimeNMoney\\data\\log_file.sav";
            FileOutputStream saveFile=new FileOutputStream(name_file);
            ObjectOutputStream save;
            save = new ObjectOutputStream(saveFile);
            save.writeObject(this);
            save.close(); // This also closes saveFile.
        }
        catch(IOException e){
            //exc.printStackTrace(); // If there was an error, print the info.
        	new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    
    @SuppressWarnings("unused")
	private boolean get_credentials(){
        try{
            Connection con = (new Connection_bd(this.username)).get_connection();
            PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_enc_logb where username='"+ this.username +"'";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
//                String aux = rs.getString("PASSWORD");
            	byte[] pass_b = rs.getBytes("PASSWORD");
            	String aux = get_password(pass_b);
                if (aux.equals(this.password))
                    return true;
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
        return false;
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
	    	new Log_erros_class().write_log_to_file(this.username,e);
	        return "";
	    }
	}

    
    public void get_lista_funcionarios(){
        this.lista_funcionarios = new ArrayList<Funcionario>();
        try{
            Connection con = (new Connection_bd(this.username)).get_connection();
            java.sql.Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM tnm_FUNCIONARIO");
            while (rs.next()) {
                String name = rs.getString("NOME");
                String mail = rs.getString("MAIL");
                String user = rs.getString("USERNAME");
                String phone_pt = rs.getString("TELEMOVEL_PT");
                String phone_ao = rs.getString("TELEMOVEL_AO");
                boolean admin = (rs.getInt("ADMIN") != 0);
//                InputStream fs = rs.getBinaryStream("FOTO");
//                Image img = null;
//                if (fs!=null)
//                    img = ImageIO.read(fs);
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
//                if (img!=null){
//                    f.set_foto(img);
//                }
//                else 
//                    f.set_foto(null);
                f.set_foto(foto);
                this.lista_funcionarios.add(f);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
}
