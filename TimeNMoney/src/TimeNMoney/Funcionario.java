package TimeNMoney;

import java.io.Serializable;
import java.util.Date;

public class Funcionario implements Serializable{
    private static final long serialVersionUID = -7568221828581210915L;
	public String nome;
    public String mail;
    public String username;
    public String phone_pt;    
    public String phone_ao;
    public String idSkype;
    public byte[] foto;
    public Date data_nascimento;
    public boolean admin;

    public Funcionario()
    {
        this.nome = "";
        this.mail = "";
        this.username = "";
        this.phone_pt = "";
        this.phone_ao = "";
        this.idSkype = "";
        this.data_nascimento = null;
        this.foto = null;
        this.admin = false;
    }
    
    public String get_nome() {
        return nome;
    }

    public void set_nome(String nome) {
        this.nome = nome;
    }

    public String get_mail() {
        return mail;
    }

    public void set_mail(String mail) {
        this.mail = mail;
    }
    
    public String get_username() {
        return username;
    }

    public void set_username(String user) {
        this.username = user;
    }
    
    public boolean get_admin(){
        return this.admin;
    }
    
    public void set_admin(boolean ad){
        this.admin = ad;
    }

    public void set_phone_pt(String ph){
        this.phone_pt = ph;
    }
    
    public String get_phone_pt(){
        return this.phone_pt;
    }
    
    public void set_phone_ao(String ph){
        this.phone_ao = ph;
    }
    
    public String get_phone_ao(){
        return this.phone_ao;
    }
    
    public void set_id_skype(String id){
        this.idSkype = id;
    }
    
    public String get_id_skype(){
        return this.idSkype;
    }
    
    public void set_foto(byte[] f){
        this.foto = f;
    }
    
    public byte[] get_foto(){
        return this.foto;
    }
    
    public void set_nascimento(Date d){
        this.data_nascimento = d;
    }
    
    public Date get_nascimento(){
        return this.data_nascimento;
    }
}
