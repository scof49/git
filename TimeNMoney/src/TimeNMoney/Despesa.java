package TimeNMoney;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Despesa implements Serializable{
   private static final long serialVersionUID = 8490860303865107266L;
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
   double valor;
   boolean faturavel;
   File recibo;
   String notas;
   
   public Despesa(){
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
       this.valor = 0.0;
       this.faturavel = true;
       this.recibo = null;
       this.notas = "";
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
   
   public void set_valor(double aux){
       this.valor = aux;
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
   
   public double get_valor(){
       return this.valor;
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
   
   @Override
   public String toString(){
       return "Projecto: "+ this.id_projecto + " - "+ this.nome_projecto + "\n" + "Tipo de Despesa: "+ this.tipo + "\n" + "Etapa: "+ this.etapa + "\n" + "Actividade: "+ this.actividade + "\n" +
               "Data: "+ this.dataDespesa.toString() + "\n" + "Notas: "+ this.notas + "\n";
   }
   
}   
