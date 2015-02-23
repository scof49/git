package TimeNMoney;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Actividade implements Serializable{
    String id;
    String nome;
    String descricao;
    
    public Actividade(){
        this.id = "";
        this.nome = "";
        this.descricao = "";
    }
    
    public void set_id(String i){
        this.id = i;
    }
    
    public void set_nome(String n){
        this.nome = n;
    }
    
    public void set_descricao(String d){
        this.descricao = d;
    }
    
    public String get_id(){
        return this.id;
    }
    
    public String get_nome(){
        return this.nome;
    }
    
    public String get_descricao(){
        return this.descricao;
    }
    
}
