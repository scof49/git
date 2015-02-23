package TimeNMoney;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Despesa_new implements Serializable{
   private static final long serialVersionUID = 8490860303865107267L;
   int id;
   String username;
   String tipo;
   String id_projecto;
   String nome_projecto;
   String etapa;
   String actividade;
   Date dataDespesa;
   Date dataAprovacao;
   int quantidade;
   double valor_euros;
   String valor_original;
   boolean faturavel;
   File recibo;
   String notas; 
   
   public Despesa_new(){
       this.id = 0;
       this.username = "";
       this.tipo = "";
       this.id_projecto ="";
       this.nome_projecto ="";
       this.etapa = "";
       this.actividade = "";
       this.dataDespesa = null;
       this.dataAprovacao = null;
       this.quantidade = 0;
       this.valor_euros = 0.0;
       this.valor_original = "";
       this.faturavel = true;
       this.recibo = null;
       this.notas = "";
   }
   
   public Despesa_new(Despesa d){
       this.id = d.get_id();
       this.username = d.get_username();
       this.tipo = d.get_tipo();
       this.id_projecto =d.get_id_projecto();
       this.nome_projecto =d.get_nome_projecto();
       this.etapa = d.get_etapa();
       this.actividade = d.get_actividade();
       this.dataDespesa = d.get_data_despesa();
       this.dataAprovacao = d.get_data_aprovacao();
       this.quantidade = d.get_quantidade();
       this.valor_euros = d.get_valor();
       this.valor_original = "";
       this.faturavel = d.get_faturavel();
       this.recibo = d.get_recibo();
       this.notas = d.get_notas();
   }
   
   public void set_id(int aux){
       this.id = aux;
   }
   
   public void set_username(String aux){
       this.username = aux;
   }
   
   public void set_tipo(String aux){
       this.tipo = aux;
   }
   
   public void set_id_projecto(String aux){
       this.id_projecto = aux;
   }
   
   public void set_nome_projecto(String aux){
       this.nome_projecto = aux;
   }
   
   public void set_etapa(String aux){
       this.etapa = aux;
   }
   
   public void set_actividade(String aux){
       this.actividade = aux;
   }
   
   public void set_data_despesa(Date aux){
       this.dataDespesa = aux;
   }
   
   public void set_data_aprovacao(Date aux){
       this.dataAprovacao = aux;
   }
   
   public void set_quantidade(int aux){
       this.quantidade = aux;
   }
   
   public void set_valor_euros(double aux){
       this.valor_euros = aux;
   }
   
   public void set_valor_original(String aux){
       this.valor_original = aux;
   }
   
   public void set_faturavel(boolean aux){
       this.faturavel = aux;
   }
   
   public void set_recibo(File aux){
       this.recibo = aux;
   }
   
   public void set_notas(String aux){
       this.notas = aux;
   }
   
   public int get_id(){
       return this.id;
   }
   
   public String get_username(){
       return this.username;
   }
   
   public String get_tipo(){
       return this.tipo;
   }
   
   public String get_id_projecto(){
       return this.id_projecto;
   }
   
   public String get_nome_projecto(){
       return this.nome_projecto;
   }
   
   public String get_etapa(){
       return this.etapa;
   }
   
   public String get_actividade(){
       return this.actividade;
   }
   
   public Date get_data_despesa(){
       return this.dataDespesa;
   }
   
   public Date get_data_aprovacao(){
       return this.dataAprovacao;
   }
   
   public int get_quantidade(){
       return this.quantidade;
   }
   
   public double get_valor_euros(){
       return this.valor_euros;
   }
   
   public String get_valor_original(){
       return this.valor_original;
   }
   
   public boolean get_faturavel(){
       return this.faturavel;
   }
   
   public File get_recibo(){
       return this.recibo;
   }
   
   public String get_notas(){
       return this.notas;
   }
}   
