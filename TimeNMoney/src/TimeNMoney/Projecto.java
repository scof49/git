package TimeNMoney;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Projecto implements Serializable{
    
    String codigo;
    String nome;
    Cliente cliente;
    Date data_inicio;
    HashMap<String,Etapa> etapas;
    HashMap<String,Actividade> actividades;
    HashMap<String,Tarefa> tarefas;
    HashMap<String,Funcionario> funcionarios;
    String status;
    Date data_fim;
    
    public Projecto(){
        this.codigo = "";
        this.nome = "";
        this.cliente = null;
        this.data_inicio = null;
        this.etapas = null;
        this.actividades = null;
        this.tarefas = null;
        this.funcionarios = null;
        this.status = "";
        this.data_fim = null;
    }
    
    public void set_codigo(String i){
        this.codigo = i;
    }
    
    public void set_nome(String n){
        this.nome = n;
    }
    
    public void set_cliente(Cliente c){
        this.cliente = c;
    }
    
    public void set_data_inicio(Date d){
        this.data_inicio = d;
    }
    
    public void set_etapas(HashMap<String,Etapa> lista){
        this.etapas = lista;
    }
    
    public void set_actividades(HashMap<String,Actividade> lista){
        this.actividades = lista;
    }
    
    public void set_tarefas(HashMap<String,Tarefa> lista){
        this.tarefas = lista;
    }
    
    public void set_funcionarios(HashMap<String,Funcionario> lista){
        this.funcionarios = lista;
    }
    
    public void set_status(String s){
        this.status = s;
    }
    
    public void set_data_fim(Date d){
        this.data_fim = d;
    }
    
    public String get_codigo(){
        return this.codigo;
    }
    
    public String get_nome(){
        return this.nome;
    }
    
    public Cliente get_cliente(){
        return this.cliente;
    }
    
    public Date get_data_inicio(){
        return this.data_inicio;
    }
    
    public HashMap<String,Etapa> get_etapas(){
        return this.etapas;
    }
    
    public HashMap<String,Actividade> get_actividades(){
        return this.actividades;
    }
    
    public HashMap<String,Tarefa> get_tarefas(){
        return this.tarefas;
    }
    
    public HashMap<String,Funcionario> get_funcionarios(){
        return this.funcionarios;
    }
    
    public String get_status(){
        return this.status;
    }
    
    public Date get_data_fim(){
        return this.data_fim;
    }
}
