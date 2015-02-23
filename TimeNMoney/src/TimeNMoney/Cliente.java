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
public class Cliente implements Serializable{
    String id;
    String nome;
    String morada;
    String contactos;
    String notas;
    
    public Cliente(){
        this.id = "";
        this.nome = "";
        this.morada = "";
        this.contactos = "";
        this.notas = "";
    }
    
    public void set_id(String aux){
        this.id = aux;
    }
    
    public void set_nome(String aux){
        this.nome = aux;
    }
    
    public void set_morada(String aux){
        this.morada = aux;
    }
    
    public void set_contactos(String aux){
        this.contactos = aux;
    }
    
    public void set_notas(String aux){
        this.notas = aux;
    }
    
    public String get_id(){
        return this.id ;
    }
    
    public String get_nome(){
        return this.nome ;
    }
    
    public String get_morada(){
        return this.morada ;
    }
    
    public String get_contactos(){
        return this.contactos ;
    }
    
    public String get_notas(){
        return this.notas ;
    }
    
}
