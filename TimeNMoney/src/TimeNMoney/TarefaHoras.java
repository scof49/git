package TimeNMoney;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class TarefaHoras implements Serializable{
    private static final long serialVersionUID = 5339741741804254160L;
    String id;
    String id_projecto;
    String nome_projecto;
    String etapa;
    String descEtapa;
    String actividade;
    String descActividade;
    String tarefa;
    String descTarefa;
    String funcionario;
    String local;
    HashMap<Date,Double> map;
    
    public TarefaHoras(){
        this.id = "";
        this.id_projecto = "";
        this.nome_projecto = "";
        this.actividade = "";
        this.descActividade = "";
        this.etapa = "";
        this.descEtapa = "";
        this.tarefa = "";
        this.descTarefa = "";
        this.funcionario = "";
        this.local = "";
        this.map = new HashMap<>();
    }
    
    public TarefaHoras(TarefaHoras t){
        this.id = t.get_id();
        this.id_projecto = t.get_id_projecto();
        this.nome_projecto = t.get_nome_projecto();
        this.actividade = t.get_actividade();
        this.descActividade = t.get_desc_actividade();
        this.etapa = t.get_etapa();
        this.descEtapa = t.get_desc_etapa();
        this.tarefa = t.get_tarefa();
        this.descTarefa = t.get_desc_tarefa();
        this.funcionario = t.get_funcionario();
        this.local = t.get_local();
        this.map = t.get_map();
    }
    
    public void set_id(String aux){
        this.id = aux;
    }
    
    public void set_id_projecto(String aux){
        this.id_projecto = aux;
    }
    
    public void set_nome_projecto(String aux){
        this.nome_projecto = aux;
    }
    
    public void set_actividade(String aux){
        this.actividade = aux;
    }
    
    public void set_desc_actividade(String aux){
        this.descActividade = aux;
    }
    
    public void set_etapa(String aux){
        this.etapa = aux;
    }
    
    public void set_desc_etapa(String aux){
        this.descEtapa = aux;
    }
    
    public void set_tarefa(String aux){
        this.tarefa = aux;
    }
    
    public void set_desc_tarefa(String aux){
        this.descTarefa = aux;
    }
    
    public void set_funcionario(String aux){
        this.funcionario = aux;
    }
    
    public String get_id(){
        return this.id;
    }
    
    public String get_id_projecto(){
        return this.id_projecto;
    }
    
    public String get_nome_projecto(){
        return this.nome_projecto;
    }
    
    public String get_actividade(){
        return this.actividade;
    }
    
    public String get_desc_actividade(){
        return this.descActividade;
    }
    
    public String get_etapa(){
        return this.etapa;
    }
    
    public String get_desc_etapa(){
        return this.descEtapa;
    }
    
    public String get_tarefa(){
        return this.tarefa;
    }
    
    public String get_desc_tarefa(){
        return this.descTarefa;
    }
    
    public String get_funcionario(){
        return this.funcionario;
    }
    
    public String get_local(){
        return this.local;
    }
    
    public void set_map(HashMap<Date,Double> map){
        this.map = map;
    }
    
    public void set_local(String local){
        this.local = local;
    }
     
    public HashMap<Date,Double> get_map(){
        return this.map;
    }
    
    @Override
    public String toString(){
        return "ID: "+this.id+"\nProjecto: "+this.id_projecto + " - " + this.nome_projecto +"\nEtapa: "+this.etapa+"\nActividade: "+this.actividade+"\nTarefa: "+this.tarefa+"\nLocal: "+this.local;
    }
}
