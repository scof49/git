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
public class Class_auxiliar_tabelas {
    String descricao;
    boolean selected;
    
    public Class_auxiliar_tabelas(){
        this.descricao = "";
        this.selected = false;
    }
    
    public void set_descricao(String desc){
        this.descricao = desc;
    }
    
    public void set_selected(boolean sel){
        this.selected = sel;
    }
    
    public String set_descricao(){
        return this.descricao;
    }
    
    public boolean set_selected(){
        return this.selected;
    }
    
}
