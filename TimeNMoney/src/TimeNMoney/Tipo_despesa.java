/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

/**
 *
 * @author Ivo.Oliveira
 */
public class Tipo_despesa {
    String nome;
    
    public Tipo_despesa(){
        this.nome = "";
    }
    
    public void set_nome(String nome){
        this.nome = nome;
    }
    
    public String get_nome(){
        return this.nome;
    }
    
}
