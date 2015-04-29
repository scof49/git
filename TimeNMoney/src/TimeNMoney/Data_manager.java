/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.awt.HeadlessException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

/**
 *
 * @author Ivo.Oliveira
 */
public class Data_manager implements Serializable{
    private static final long serialVersionUID = 6529685098267757690L;
    String username;
    String id_user_tarefa;
    ArrayList<Despesa_new> despesas_user; //despesas do user logado
    ArrayList<Integer> despesas_add_alteradas;
    ArrayList<Integer> despesas_rejeitadas_alteradas;
    ArrayList<Despesa_new> despesas_user_eliminadas;
    TreeMap<Integer,Integer> estado_despesas_user; //estado das despesas do user
    TreeMap<String,Projecto> lista_projectos_user;
    ArrayList<String> tipos_despesa;
    TreeMap<String,TarefaHoras> lista_tarefas_time_user;    
    ArrayList<TarefaHoras> lista_tarefas_favoritas;    
    ArrayList<TarefaHoras> remove_lista_favoritos;
    ArrayList<Horas_Handle_Obj> lista_handler_horas;
    ArrayList<Horas_Handle_Obj> lista_handler_horas_modificacoes;
    TreeMap<String,TreeMap<Date,String>> notas_tarefas;
    TreeMap<String,Tarefa> lista_tarefa_total;
    ArrayList<String> projectos_alterados;
    ArrayList<String> tarefas_adicionadas;
    ArrayList<Cliente> clientes;
    TreeMap<String,Funcionario> funcionario_geridos_por_mim;//funcionarios cujo user tem capacidade para gerir tempos e despesas
    TreeMap<String,Funcionario> lista_funcionarios;	//lista total de funcionarios
    ArrayList<Date> lista_feriados; //lista de feriados angolanos para apresentar nos calendarios
    int read;
    
//    double rate_usd;
//    double rate_akz;
    HashMap<String,Double> taxas; //id -> mes_ano_moeda
    
    public Data_manager(String username){
        this.username = username;
//        get_save_data_bd(username);
        get_save_data_bd(username);
    }
    
//    public Data_manager(){
//        this.username = "";
//        init_data_bd();
//    }
    
    public String get_id_user_tarefa(){
    	return this.id_user_tarefa;
    }
    
    public TreeMap<String,TreeMap<Date,String>> get_notas_tarefas(){
        return this.notas_tarefas;
    }
    
    public void set_notas_tarefas(TreeMap<String,TreeMap<Date,String>> aux){
        this.notas_tarefas = aux;
    }
    
    public double get_rate_usd(int mes, int ano){
    	boolean done_flag = false;
    	int cont = 0;
    	double res = 0.0;
    	String id = "";
    	while(!done_flag){
    		id = mes + "_" + ano + "_" + "USD";
    		if ((this.taxas != null) && this.taxas.containsKey(id))
    		{
    			res = this.taxas.get(id);
    			done_flag = true;
    		}
    		else if ((this.taxas != null) && ( !this.taxas.containsKey(id))){
    			mes--;
    			if (mes==0){
    				mes = 12;
    				ano--;
    			}
    			cont++;
    			if (cont > this.taxas.size())
    				return 1.0;
    		}
    		else{
    			return 1.0;
    		}
    	}
    	return res;
    }
    
    public double get_rate_akz(int mes, int ano){
    	boolean done_flag = false;
    	int cont = 0;
    	double res = 0.0;
    	String id = "";
    	while(!done_flag){
    		id = mes + "_" + ano + "_" + "AKZ";
    		if ((this.taxas != null) && this.taxas.containsKey(id))
    		{
    			res = this.taxas.get(id);
    			done_flag = true;
    		}
    		else if ((this.taxas != null) && ( !this.taxas.containsKey(id))){
    			mes--;
    			if (mes==0){
    				mes = 12;
    				ano--;
    			}
    			cont++;
    			if (cont > this.taxas.size())
    				return 1.0;
    		}
    		else{
    			return 1.0;
    		}
    	}
    	return res;
    }
    
    public ArrayList<Despesa_new> get_despesas_user(){
        return this.despesas_user;
    }
    
    public ArrayList<Despesa_new> get_despesas_eliminadas(){
        return this.despesas_user_eliminadas;
    }
    
    public ArrayList<Integer> get_despesas_adicionadas_alteradas(){
        return this.despesas_add_alteradas;
    }
    
    public TreeMap<Integer,Integer> get_estado_despesas_user(){
        return this.estado_despesas_user;
    }
    
    public TreeMap<String,Projecto> get_projectos_user(){
        return this.lista_projectos_user;
    }
    
    public TreeMap<String,TarefaHoras> get_lista_tarefas_horas_user(){
        return this.lista_tarefas_time_user;
    }
    
    public ArrayList<TarefaHoras> get_lista_tarefas_favoritas(){
        return this.lista_tarefas_favoritas;
    }
    
    public ArrayList<TarefaHoras> get_remove_lista_favoritos(){
        return this.remove_lista_favoritos;
    }
    
    public void set_remove_lista_favoritos(ArrayList<TarefaHoras> lista){
        this.remove_lista_favoritos = lista;
    }
    
    public void set_despesas_eliminadas(ArrayList<Despesa_new> lista)
    {
        this.despesas_user_eliminadas = lista;
    }
    
    public ArrayList<Horas_Handle_Obj> get_lista_handle_horas(){
        return this.lista_handler_horas;
    }
    
    public ArrayList<Horas_Handle_Obj> get_lista_handle_horas_modificacoes(){
        return this.lista_handler_horas_modificacoes;
    }
    
    public void set_lista_handle_horas_modificacoes(ArrayList<Horas_Handle_Obj> aux){
        this.lista_handler_horas_modificacoes = aux;
    }
    
    private void get_save_data_bd(String username){
        //if nao existe, le da bd
    	try {
	    	Connection con = (new Connection_bd(this.username)).get_connection();
	        get_despesas_from_bd(username,con);
	        get_estado_despesas(username,con);
	        get_list_projectos_from_bd(username,con);
	        get_list_tipos_despesa(con);
	        get_tarefas_time_from_bd(username,con);
	        get_aprov_or_not_task_list(username,con);
	        get_favoritos_from_bd(username,con);
	        this.remove_lista_favoritos = new ArrayList<>();
	        this.despesas_user_eliminadas = new ArrayList<>();
	        this.despesas_add_alteradas = new ArrayList<>();
	        this.despesas_rejeitadas_alteradas = new ArrayList<>();
	        get_taxas_moeda(con);
	        get_notas_tarefas_bd(con);
	        get_lista_tarefas(con);
	        this.projectos_alterados = new ArrayList<>();
	        this.tarefas_adicionadas = new ArrayList<>();
	        this.lista_handler_horas_modificacoes = new ArrayList<>();
	        get_lista_clientes(con);
	        get_id_user_tarefas_bd(username,con);
	        get_lista_funcionarios(con);
	        get_funcionarios_geridos(username,con);
	        get_lista_feriados(con);
        	con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new Log_erros_class().write_log_to_file(this.username,e);
		}
    }
    
//    private void init_data_bd(){
//    	id_user_tarefa = "";
//        despesas_user = new ArrayList<Despesa_new>(); //despesas do user logado
//        despesas_add_alteradas = new ArrayList<Integer> ();;
//        despesas_rejeitadas_alteradas = new ArrayList<Integer> ();
//        despesas_user_eliminadas = new ArrayList<Despesa_new> ();
//        estado_despesas_user = new TreeMap<Integer,Integer> (); //estado das despesas do user
//        lista_projectos_user = new TreeMap<String,Projecto>();
//        tipos_despesa = new ArrayList<String>();
//        lista_tarefas_time_user = new TreeMap<String,TarefaHoras>();    
//        lista_tarefas_favoritas = new ArrayList<TarefaHoras>();    
//        remove_lista_favoritos = new ArrayList<TarefaHoras>();
//        lista_handler_horas = new ArrayList<Horas_Handle_Obj>();
//        lista_handler_horas_modificacoes = new ArrayList<Horas_Handle_Obj>();
//        notas_tarefas = new TreeMap<String,TreeMap<Date,String>>();
//        lista_tarefa_total = new TreeMap<String,Tarefa>();
//        projectos_alterados = new ArrayList<String>();
//        tarefas_adicionadas = new ArrayList<String>();
//        clientes = new ArrayList<Cliente>();
//        funcionario_geridos_por_mim = new TreeMap<String,Funcionario>();//funcionarios cujo user tem capacidade para gerir tempos e despesas
//        lista_funcionarios = new TreeMap<String,Funcionario>();	//lista total de funcionarios
//    }
    
//    public void get_save_data_bd_splash(String username){
//        LoadScreen ls = new LoadScreen();
//        ls.setVisible(true);
////    	ls.set_load("A carregar despesas...", 0);
////    	//if nao existe, le da bd
////    	try {
////	    	Connection con = (new Connection_bd()).get_connection();
////	        get_despesas_from_bd(username,con);
////	        get_estado_despesas(username,con);
////	        ls.set_load("A carregar lista de projectos...",10);
////	        get_list_projectos_from_bd(username,con);
////	        ls.set_load("A carregar tipos de despesas...", 20);
////	        get_list_tipos_despesa(con);
////	        ls.set_load("A carregar carga horária...", 30);
////	        get_tarefas_time_from_bd(username,con);
////	        get_aprov_or_not_task_list(username,con);
////	        ls.set_load("A carregar favoritos...", 40);
////	        get_favoritos_from_bd(username,con);
////	        ls.set_load("A carregar taxas de câmbio...", 50);
////	        this.remove_lista_favoritos = new ArrayList<>();
////	        this.despesas_user_eliminadas = new ArrayList<>();
////	        this.despesas_add_alteradas = new ArrayList<>();
////	        get_taxas_moeda(con);
////	        ls.set_load("A carregar notas...", 60);
////	        get_notas_tarefas_bd(con);
////	        ls.set_load("A carregar lista de tarefas...", 70);
////	        get_lista_tarefas(con);
////	        this.projectos_alterados = new ArrayList<>();
////	        this.tarefas_adicionadas = new ArrayList<>();
////	        this.lista_handler_horas_modificacoes = new ArrayList<>();
////	        ls.set_load("A carregar lista de clientes...", 80);
////	        get_lista_clientes(con);
////	        ls.set_load("A carregar id's utilizador...", 80);
////	        get_id_user_tarefas_bd(username,con);
////	        get_lista_funcionarios(con);
////	        get_funcionarios_geridos(username,con);
////        	con.close();
////        	ls.set_load("A finalizar carregamento...", 100);
////		} catch (SQLException e) {
////			e.printStackTrace();
////			new Log_erros_class().write_log_to_file(e);
////		}
//    }


	public void update_save_data_bd(String username){
        //if nao existe, le da bd
    	try {
	    	Connection con = (new Connection_bd(this.username)).get_connection();
	    	get_lista_tarefas(con);
	        get_list_projectos_from_bd(username,con);
	        get_list_tipos_despesa(con);
	        get_taxas_moeda(con);
	        get_lista_clientes(con);
	        get_lista_funcionarios(con);
	        get_funcionarios_geridos(username,con);
	        con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new Log_erros_class().write_log_to_file(this.username,e);
		}
    }
    
    void get_id_user_tarefas_bd(String user, Connection con) {
    	try{
            PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_user_id where username = ?";
            ps=con.prepareStatement(sql);
            ps.setString(1,user);
            rs = ps.executeQuery();
            while (rs.next()){
                this.id_user_tarefa = rs.getString("ID_USER");
            }
            ps.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
	}

	void get_lista_clientes(Connection con){
        this.clientes = new ArrayList<>();
        try{
            PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_trf_cliente";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                String id = rs.getString("ID");
                String nome = rs.getString("NOME");
                String morada = rs.getString("MORADA");
                String contactos = rs.getString("CONTACTOS");
                String notas = rs.getString("NOTAS");
                Cliente c = new Cliente();
                c.set_id(id);
                c.set_nome(nome);
                c.set_morada(morada);
                c.set_contactos(contactos);
                c.set_notas(notas);
                this.clientes.add(c);
            }
            ps.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    void get_notas_tarefas_bd(Connection con){
        this.notas_tarefas = new TreeMap<>();
        try{
            PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_notas_tarefas where username = '"+ this.username +"'";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                String id_tarefa = rs.getString("ID_TAREFA");
                Date d_nota = rs.getDate("DATA_NOTA");
                String nota = rs.getString("NOTA");
                //id tarefa ja existe
                if (this.notas_tarefas.containsKey(id_tarefa))
                    this.notas_tarefas.get(id_tarefa).put(d_nota, nota);
                else
                {
                	TreeMap<Date,String> aux = new TreeMap<>();
                    aux.put(d_nota,nota);
                    this.notas_tarefas.put(id_tarefa, aux);
                }
            }
            ps.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    void get_taxas_moeda(Connection con){
        try{
            this.taxas = new HashMap<String, Double>();
        	PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_taxas_moeda";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                String moeda = rs.getString("ID_MOEDA");
                double taxa = rs.getDouble("TAXA");
                int mes = rs.getInt("MES");
                int ano = rs.getInt("ANO");
                String id = mes +"_"+ ano +"_"+ moeda.toUpperCase();
                id = id.trim();
                this.taxas.put(id,taxa);
            }
            ps.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    int get_taxas_moeda2(Connection con){
    	int res = 0;
        try{
        	this.taxas = new HashMap<String, Double>();
        	PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_taxas_moeda";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                String moeda = rs.getString("ID_MOEDA");
                double taxa = rs.getDouble("TAXA");
                int mes = rs.getInt("MES");
                int ano = rs.getInt("ANO");
                String id = mes +"_"+ ano +"_"+ moeda.toUpperCase();
                id = id.trim();
                this.taxas.put(id,taxa);
            }
            ps.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            res++;
        }
        return res;
    }
    
    void get_despesas_from_bd(String user,Connection con){
        ArrayList<Despesa_new> aux = new ArrayList<>();
        try{
            PreparedStatement ps;
            ResultSet rs = null;
            String sql = "select * from tnm_despesas where username='"+ user +"' order by id_despesa desc";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                ByteArrayInputStream bais;
                ObjectInputStream ois;
                try{
                    bais = new ByteArrayInputStream(rs.getBytes("DESPESA"));
                    ois = new ObjectInputStream(bais);
                    Despesa_new d = (Despesa_new)ois.readObject();
                    aux.add(d);
                }
                catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                    new Log_erros_class().write_log_to_file(this.username,e);
                }
            }
            ps.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
        this.despesas_user = aux;
    }
    
    void get_estado_despesas(String user,Connection con){
    	TreeMap<Integer,Integer> estado = new TreeMap<>();
        try{
            PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_handlepagamentos where username='"+ user +"'";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID");
                int sit = rs.getInt("SITUACAO");
                estado.put(id, sit);
            }
            ps.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
        this.estado_despesas_user = estado;
    }
    
    public int get_estado_despesas2(String user,Connection con){
    	int res = 0;
    	TreeMap<Integer,Integer> estado = new TreeMap<>();
        try{
            PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_handlepagamentos where username='"+ user +"'";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID");
                int sit = rs.getInt("SITUACAO");
                estado.put(id, sit);
            }
            ps.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            res++;
        }
        this.estado_despesas_user = estado;
        return res;
    }
    
    void get_list_projectos_from_bd(String user,Connection con){
    	TreeMap<String,Projecto> projectos = new TreeMap<>();
        
        try{
        //projectos
        String sql = "select * from tnm_trf_projecto" ;
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            ByteArrayInputStream bais;
            ObjectInputStream ois;
            try{
                String codigo = rs.getString("ID_PROJECTO");
                bais = new ByteArrayInputStream(rs.getBytes("PROJECTO"));
                ois = new ObjectInputStream(bais);
                Projecto p = (Projecto)ois.readObject();
                if (p.get_funcionarios().containsKey(user))
                    projectos.put(codigo, p);
                
            }
            catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                e.printStackTrace();
                new Log_erros_class().write_log_to_file(this.username,e);
            }
        }  
        rs.close();
        ps.close();
        
        this.lista_projectos_user = projectos;
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    int get_list_projectos_from_bd2(String user,Connection con){
    	TreeMap<String,Projecto> projectos = new TreeMap<>();
        int res = 0;
        try{
        //projectos
        String sql = "select * from tnm_trf_projecto" ;
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            ByteArrayInputStream bais;
            ObjectInputStream ois;
            try{
                String codigo = rs.getString("ID_PROJECTO");
                bais = new ByteArrayInputStream(rs.getBytes("PROJECTO"));
                ois = new ObjectInputStream(bais);
                Projecto p = (Projecto)ois.readObject();
                if (p.get_funcionarios().containsKey(user))
                    projectos.put(codigo, p);
                
            }
            catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                e.printStackTrace();
                new Log_erros_class().write_log_to_file(this.username,e);
                res++;
            }
        }  
        rs.close();
        ps.close();
        
        this.lista_projectos_user = projectos;
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            res++;
        }
        return res;
    }
    
    void get_list_tipos_despesa(Connection con){
        ArrayList<String> tipos = new ArrayList<>();
        
        try{
         //tipo despesas
        String sql = "select * from tnm_tipo_despesa";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String tipo = rs.getString("DESCRICAO");
            tipos.add(tipo);
        }
        rs.close();
        ps.close();
        
        this.tipos_despesa = tipos;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    int get_list_tipos_despesa2(Connection con){
        ArrayList<String> tipos = new ArrayList<>();
        int res = 0;
        try{
         //tipo despesas
        String sql = "select * from tnm_tipo_despesa";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String tipo = rs.getString("DESCRICAO");
            tipos.add(tipo);
        }
        rs.close();
        ps.close();
        
        this.tipos_despesa = tipos;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            res++;
        }
        return res;
    }
    
    void get_lista_tarefas(Connection con){
    	TreeMap<String,Tarefa> tarefas = new TreeMap<>();
        
        try{
         //tipo despesas
        String sql = "select * from tnm_trf_tarefa";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Tarefa t = new Tarefa();
            t.set_id(rs.getString("ID"));
            t.set_nome(rs.getString("NOME"));
            t.set_descricao(rs.getString("DESCRICAO"));
            //tipos.put(t.get_nome(), t);
            tarefas.put(t.get_id(), t);
        }
        rs.close();
        ps.close();
        
        this.lista_tarefa_total = tarefas;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
    public void save_file(){
        try{  
//        	String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        	String defaultFolder = System.getenv("APPDATA");
        	File s_file = new File(defaultFolder + "\\TimeNMoney\\data");
            s_file.mkdirs();
            String path = s_file.getAbsolutePath();
            String name_file = path + "\\" + this.username + ".sav";
            FileOutputStream saveFile=new FileOutputStream(name_file);
            ObjectOutputStream save;
            save = new ObjectOutputStream(saveFile);
            save.writeObject(this);
            save.close(); // This also closes saveFile.
        }
        catch(IOException e){
            e.printStackTrace(); // If there was an error, print the info.
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
    
   public void get_tarefas_time_from_bd(String user,Connection con){
    	TreeMap<String,TarefaHoras> aux = new TreeMap<>();
        try{
            PreparedStatement ps;
            ResultSet rs = null;
            String sql = "select * from tnm_tarefas where username='"+ user +"'";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                ByteArrayInputStream bais;
                ObjectInputStream ois;
                try{
                    bais = new ByteArrayInputStream(rs.getBytes("TAREFA"));
                    ois = new ObjectInputStream(bais);
                    TarefaHoras t = (TarefaHoras)ois.readObject();
                    aux.put(t.get_id(),t);
                }
                catch(HeadlessException | IOException | SQLException e){
                    e.printStackTrace();
                    new Log_erros_class().write_log_to_file(this.username,e);
                }
            }
            rs.close();
            ps.close();
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
        this.lista_tarefas_time_user = aux;
    }
    
    public void get_favoritos_from_bd(String user,Connection con){
        ArrayList<TarefaHoras> aux = new ArrayList<>();
        try{
            PreparedStatement ps;
            ResultSet rs = null;
            String sql = "select * from tnm_tarefas_favoritos where username='"+ user +"'";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                ByteArrayInputStream bais;
                ObjectInputStream ois;
                try{
                    bais = new ByteArrayInputStream(rs.getBytes("TAREFA"));
                    ois = new ObjectInputStream(bais);
                    TarefaHoras t = (TarefaHoras)ois.readObject();
                    aux.add(t);
                }
                catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                    new Log_erros_class().write_log_to_file(this.username,e);
                }
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
        this.lista_tarefas_favoritas = aux;
    }
    
    public void get_aprov_or_not_task_list(String username,Connection con){
        ArrayList<Horas_Handle_Obj> aux = new ArrayList<>();
        try{
            PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_handle_horas where username='"+ username +"'";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                String u = rs.getString("USERNAME");
                Date dia = rs.getDate("DATA");
                int e = rs.getInt("SITUACAO");
                Horas_Handle_Obj ho = new Horas_Handle_Obj(u,dia,e);
                aux.add(ho);
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
        }
        this.lista_handler_horas = aux;
    }
    
    public int get_aprov_or_not_task_list2(String username,Connection con){
        int ret = 0;
    	ArrayList<Horas_Handle_Obj> aux = new ArrayList<>();
        try{
            PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_handle_horas where username='"+ username +"'";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                String u = rs.getString("USERNAME");
                Date dia = rs.getDate("DATA");
                int e = rs.getInt("SITUACAO");
                Horas_Handle_Obj ho = new Horas_Handle_Obj(u,dia,e);
                aux.add(ho);
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(this.username,e);
            ret++;
        }
        this.lista_handler_horas = aux;
        return ret;
    }
    
    private void get_funcionarios_geridos(String username,Connection con){
    	this.funcionario_geridos_por_mim = new TreeMap<String, Funcionario>();
    	try{
    		String sql = "select * from tnm_manager where manager = ?";
    		PreparedStatement ps = con.prepareStatement(sql);
    		ps.setString(1, username);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next())
    		{
    			String user_func = rs.getString("user_funcionario");
    			Funcionario f = this.lista_funcionarios.get(user_func);
    			this.funcionario_geridos_por_mim.put(user_func, f);
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		new Log_erros_class().write_log_to_file(this.username,e);
    	}
    	
    }
    
    private void get_lista_feriados(Connection con){
    	this.lista_feriados = new ArrayList<Date>();
    	try{
    		String sql = "select * from tnm_feriados";
    		PreparedStatement ps = con.prepareStatement(sql);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next())
    		{
    			Date data = rs.getDate("data");
    			this.lista_feriados.add(data);
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		new Log_erros_class().write_log_to_file(this.username,e);
    	}
    	
    }
    
    public void get_lista_funcionarios(Connection con){
    	this.lista_funcionarios = new TreeMap<>();
		try{
			//get users nao ativos
			ArrayList<String> inativos = new ArrayList<String>();
			String sql = "select * from tnm_users_inativos" ;
			PreparedStatement ps;
			ps = con.prepareStatement(sql);
			ResultSet rs;
			rs = ps.executeQuery();
			while (rs.next()){
				inativos.add(rs.getString("username"));
			}
			
			sql = "select * from tnm_funcionario" ;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()){
		          String username = rs.getString("USERNAME");
				  String nome = rs.getString("NOME");
				  if (!inativos.contains(username)){
					  Funcionario f = new Funcionario();
					  f.set_nome(nome);
					  f.set_username(username);
					  lista_funcionarios.put(username, f);
				  }
		  	}  
		  	rs.close();
		  	ps.close();
		  }
		  catch(SQLException e)
		  {
		      e.printStackTrace();
		      new Log_erros_class().write_log_to_file(this.username,e);
		  }
    }
    
}
