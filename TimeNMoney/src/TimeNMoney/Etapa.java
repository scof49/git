/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.io.Serializable;

/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Etapa implements Serializable{
    String id;
    String nome;
    String descricao;
    
    public Etapa(){
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
