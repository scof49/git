/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Viagem implements Serializable{
    String id;
    String funcionario; //funcionario que fez viagem
    String destino_pais;    //Angola por defeito
    Date data_partida;
    Date data_chegada;
    
    public Viagem(){
        this.id = "";
        this.funcionario = "";
        this.destino_pais = "Angola";
        this.data_partida = null;
        this.data_chegada = null;
    }
    
    public void set_id(String x){
        this.id = x;
    }
    
    public void set_funcionario(String x){
        this.funcionario = x;
    }
    
    public void set_destino_pais(String x){
        this.destino_pais = x;
    }
    
    public void set_data_partida(Date x){
        this.data_partida = x;
    }
    
    public void set_data_chegada(Date x){
        this.data_chegada = x;
    }
    
    public String get_id(){
        return this.id;
    }
    
    public String get_funcionario(){
        return this.funcionario;
    }
    
    public String get_destino_pais(){
        return this.destino_pais;
    }
    
    public Date get_data_partida(){
        return this.data_partida;
    }
    
    public Date get_data_chegada(){
        return this.data_chegada;
    }
}
