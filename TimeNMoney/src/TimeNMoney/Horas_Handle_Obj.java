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
public class Horas_Handle_Obj implements Serializable{
    String username;
    Date dia;
    int estado;
    
    public Horas_Handle_Obj(){
        username = "";
        dia = new Date();
        estado = 0;
    }
    
    public Horas_Handle_Obj(String user, Date d1, int est){
        username = user;
        dia = d1;
        estado = est;
    }
    
    public void set_username(String user){
        username = user;
    }
    
    public void set_data(Date d){
        dia = d;
    }
    
    public void set_estado(int e){
        estado = e;
    }
    
    public String get_username(){
        return username;
    }
    
    public Date get_data(){
        return dia;
    }
    
    public int get_estado(){
        return estado;
    }
}
