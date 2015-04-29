/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import com.toedter.calendar.JTextFieldDateEditor;

import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Adicionar_consultar_projecto extends javax.swing.JFrame {
    TreeMap<String,Projecto> lista_projectos_interna;
    String id_aux_cliente; //para codigo de projecto automatico
    TreeMap<String,Etapa> lista_etapas_completa;
    TreeMap<String,Actividade> lista_actividades_completa;
    TreeMap<String,Tarefa> lista_tarefas_completa;
    TreeMap<String,Funcionario> lista_funcionarios_completa;
    TreeMap<String,Cliente> lista_clientes;
    boolean admin;
    Funcionario funcionario;
    boolean edicao;
    boolean copia;
    boolean novo;
    String old_id;
    /**
     * Creates new form Adicionar_consultar_projecto
     * @param f
     */
    public Adicionar_consultar_projecto(Funcionario f) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.admin = f.get_admin();
        this.funcionario = f;
        this.edicao = false;
        this.copia = false;
        this.novo = false;
        this.old_id = "";
        this.lista_projectos_interna = new TreeMap<>();
        this.id_aux_cliente = "";
        this.lista_etapas_completa = new TreeMap<>();
        this.lista_actividades_completa = new TreeMap<>();
        this.lista_tarefas_completa = new TreeMap<>();
        this.lista_funcionarios_completa = new TreeMap<>();
        this.lista_clientes = new TreeMap<>();
        carrega_lista_projectos();
        set_listbox_projectos();
        add_status_to_combo_box();
        add_clientes_to_combo_box();
        carrega_listas_from_bd();
        add_data_hoje();
        lista_projectos.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
               check_auto_code.setSelected(false);
               evento_lista_projectos();
            }
        });
        if (this.admin)
            modo_administrador();
        else
            modo_nao_administrador();
        //lista_projectos.setSelectedIndex(0);
    }
    
    private void evento_lista_projectos(){
        if (this.edicao && this.copia)
            preencher_dados_projecto_copia();
        else
            preencher_dados_projecto();
    }
    
    private void modo_nao_administrador(){
        lista_projectos.setEnabled(true);
        text_field_codigo.setEditable(false);
        text_field_nome.setEditable(false);
        check_auto_code.setEnabled(false);
        combo_clientes.setEnabled(false);
        ((JButton)date_field_inicio.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)date_field_inicio.getDateEditor()).setEditable(false);
        ((JButton)date_field_fim.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)date_field_fim.getDateEditor()).setEditable(false);
        combo_status.setEnabled(false);
        check_copiar_projecto.setEnabled(false);
        //botoes etapas
        etapas_envia_todos.setEnabled(false);
        etapas_retira_todos.setEnabled(false);
        botao_etapas_adicionar.setEnabled(false);
        botao_etapas_retirar.setEnabled(false);
        //botoes atividades
        actividades_envia_todos.setEnabled(false);
        actividades_retira_todos.setEnabled(false);
        botao_actividades_adicionar.setEnabled(false);
        botao_actividades_retirar.setEnabled(false);
        //botoes tarefas
        tarefas_envia_todos.setEnabled(false);
        tarefas_retira_todos.setEnabled(false);
        botao_tarefas_adicionar.setEnabled(false);
        botao_tarefas_retirar.setEnabled(false);
        //botoes funcionarios
        funcionarios_envia_todos.setEnabled(false);
        funcionarios_retira_todos.setEnabled(false);
        botao_funcionarios_adicionar.setEnabled(false);
        botao_funcionarios_retirar.setEnabled(false);
        
        etapa_lista_todos.setEnabled(false);
        etapa_lista_escolhidos.setEnabled(false);
        actividade_lista_todos.setEnabled(false);
        actividade_lista_escolhidos.setEnabled(false);
        tarefa_lista_todos.setEnabled(false);
        tarefa_lista_escolhidos.setEnabled(false);
        funcionarios_lista_todos.setEnabled(false);
        funcionarios_lista_escolhidos.setEnabled(false);
        
        //botoes
        botao_alterar_projecto.setVisible(false);
        botao_novo_projecto.setVisible(false);
        botao_guardar_projecto.setVisible(false);
        botao_eliminar_projecto.setVisible(false);
        botao_cancelar_edit.setVisible(false);
        botao_voltar.setVisible(true);
        botao_sair.setVisible(true);
    }
    
    private void modo_administrador(){
        lista_projectos.setEnabled(true);
        text_field_codigo.setEditable(false);
        text_field_nome.setEditable(false);
        check_auto_code.setEnabled(false);
        combo_clientes.setEnabled(false);
        ((JButton)date_field_inicio.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)date_field_inicio.getDateEditor()).setEditable(false);
        ((JButton)date_field_fim.getCalendarButton()).setEnabled(false);
        ((JTextFieldDateEditor)date_field_fim.getDateEditor()).setEditable(false);
        combo_status.setEnabled(false);
        check_copiar_projecto.setEnabled(false);
        //botoes etapas
        etapas_envia_todos.setEnabled(false);
        etapas_retira_todos.setEnabled(false);
        botao_etapas_adicionar.setEnabled(false);
        botao_etapas_retirar.setEnabled(false);
        //botoes atividades
        actividades_envia_todos.setEnabled(false);
        actividades_retira_todos.setEnabled(false);
        botao_actividades_adicionar.setEnabled(false);
        botao_actividades_retirar.setEnabled(false);
        //botoes tarefas
        tarefas_envia_todos.setEnabled(false);
        tarefas_retira_todos.setEnabled(false);
        botao_tarefas_adicionar.setEnabled(false);
        botao_tarefas_retirar.setEnabled(false);
        //botoes funcionarios
        funcionarios_envia_todos.setEnabled(false);
        funcionarios_retira_todos.setEnabled(false);
        botao_funcionarios_adicionar.setEnabled(false);
        botao_funcionarios_retirar.setEnabled(false);
        
        etapa_lista_todos.setEnabled(false);
        etapa_lista_escolhidos.setEnabled(false);
        actividade_lista_todos.setEnabled(false);
        actividade_lista_escolhidos.setEnabled(false);
        tarefa_lista_todos.setEnabled(false);
        tarefa_lista_escolhidos.setEnabled(false);
        funcionarios_lista_todos.setEnabled(false);
        funcionarios_lista_escolhidos.setEnabled(false);
        
        //botoes
        botao_alterar_projecto.setVisible(true);
        botao_novo_projecto.setVisible(true);
        botao_guardar_projecto.setVisible(false);
        botao_eliminar_projecto.setVisible(false);
        botao_cancelar_edit.setVisible(false);
        botao_voltar.setVisible(true);
        botao_sair.setVisible(true);
    }
    
    private void modo_alterar_projecto(){
        lista_projectos.setEnabled(false);
        text_field_codigo.setEditable(true);
        text_field_nome.setEditable(true);
        check_auto_code.setEnabled(true);
        combo_clientes.setEnabled(true);
        ((JButton)date_field_inicio.getCalendarButton()).setEnabled(true);
        ((JTextFieldDateEditor)date_field_inicio.getDateEditor()).setEditable(true);
        ((JButton)date_field_fim.getCalendarButton()).setEnabled(true);
        ((JTextFieldDateEditor)date_field_fim.getDateEditor()).setEditable(true);
        combo_status.setEnabled(true);
        check_copiar_projecto.setEnabled(true);
        //botoes etapas
        etapas_envia_todos.setEnabled(true);
        etapas_retira_todos.setEnabled(true);
        botao_etapas_adicionar.setEnabled(true);
        botao_etapas_retirar.setEnabled(true);
        //botoes atividades
        actividades_envia_todos.setEnabled(true);
        actividades_retira_todos.setEnabled(true);
        botao_actividades_adicionar.setEnabled(true);
        botao_actividades_retirar.setEnabled(true);
        //botoes tarefas
        tarefas_envia_todos.setEnabled(true);
        tarefas_retira_todos.setEnabled(true);
        botao_tarefas_adicionar.setEnabled(true);
        botao_tarefas_retirar.setEnabled(true);
        //botoes funcionarios
        funcionarios_envia_todos.setEnabled(true);
        funcionarios_retira_todos.setEnabled(true);
        botao_funcionarios_adicionar.setEnabled(true);
        botao_funcionarios_retirar.setEnabled(true);
        
        etapa_lista_todos.setEnabled(true);
        etapa_lista_escolhidos.setEnabled(true);
        actividade_lista_todos.setEnabled(true);
        actividade_lista_escolhidos.setEnabled(true);
        tarefa_lista_todos.setEnabled(true);
        tarefa_lista_escolhidos.setEnabled(true);
        funcionarios_lista_todos.setEnabled(true);
        funcionarios_lista_escolhidos.setEnabled(true);
        
        //botoes
        botao_alterar_projecto.setVisible(false);
        botao_novo_projecto.setVisible(false);
        botao_guardar_projecto.setVisible(true);
        botao_cancelar_edit.setVisible(true);
        botao_voltar.setVisible(false);
        botao_sair.setVisible(false);
        
        this.old_id = text_field_codigo.getText();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void modo_novo_projecto(){
        lista_projectos.setEnabled(false);
        lista_projectos.clearSelection();
        text_field_codigo.setEditable(true);
        text_field_codigo.setText("");
        text_field_nome.setEditable(true);
        text_field_nome.setText("");
        check_auto_code.setEnabled(true);
        check_auto_code.setSelected(false);
        combo_clientes.setEnabled(true);
        combo_clientes.setSelectedIndex(0);
        ((JButton)date_field_inicio.getCalendarButton()).setEnabled(true);
        ((JTextFieldDateEditor)date_field_inicio.getDateEditor()).setEditable(true);
        add_data_hoje();
        ((JButton)date_field_fim.getCalendarButton()).setEnabled(true);
        ((JTextFieldDateEditor)date_field_fim.getDateEditor()).setEditable(true);
        date_field_fim.setDate(null);
        combo_status.setEnabled(true);
        combo_status.setSelectedIndex(0);
        check_copiar_projecto.setEnabled(true);
        check_copiar_projecto.setSelected(false);
        //botoes etapas
        etapas_envia_todos.setEnabled(true);
        etapas_retira_todos.setEnabled(true);
        botao_etapas_adicionar.setEnabled(true);
        botao_etapas_retirar.setEnabled(true);
        //botoes atividades
        actividades_envia_todos.setEnabled(true);
        actividades_retira_todos.setEnabled(true);
        botao_actividades_adicionar.setEnabled(true);
        botao_actividades_retirar.setEnabled(true);
        //botoes tarefas
        tarefas_envia_todos.setEnabled(true);
        tarefas_retira_todos.setEnabled(true);
        botao_tarefas_adicionar.setEnabled(true);
        botao_tarefas_retirar.setEnabled(true);
        //botoes funcionarios
        funcionarios_envia_todos.setEnabled(true);
        funcionarios_retira_todos.setEnabled(true);
        botao_funcionarios_adicionar.setEnabled(true);
        botao_funcionarios_retirar.setEnabled(true);
        //botoes actividades
        etapa_lista_todos.setEnabled(true);
        etapa_lista_escolhidos.setEnabled(true);
        actividade_lista_todos.setEnabled(true);
        actividade_lista_escolhidos.setEnabled(true);
        tarefa_lista_todos.setEnabled(true);
        tarefa_lista_escolhidos.setEnabled(true);
        funcionarios_lista_todos.setEnabled(true);
        funcionarios_lista_escolhidos.setEnabled(true);
        carrega_listas_from_bd();
        DefaultListModel dm = new DefaultListModel();
        etapa_lista_escolhidos.setModel(dm);
        actividade_lista_escolhidos.setModel(dm);
        tarefa_lista_escolhidos.setModel(dm);
        funcionarios_lista_escolhidos.setModel(dm);
        
        //botoes
        botao_alterar_projecto.setVisible(false);
        botao_novo_projecto.setVisible(false);
        botao_guardar_projecto.setVisible(true);
        botao_cancelar_edit.setVisible(true);
        botao_voltar.setVisible(false);
        botao_sair.setVisible(false);
    }
    
    private void add_data_hoje(){
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        date_field_inicio.setDate(d);
    }
    
    @SuppressWarnings("unchecked")
	private void add_status_to_combo_box(){
        try{
            Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
            String sql = "select * from tnm_status_projectos" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                combo_status.addItem(rs.getString("DESCRICAO")); 
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void set_defaultlistmodel_etapas(){
    	DefaultListModel lm = new DefaultListModel();
    	for (String nome : this.lista_etapas_completa.keySet())
    		lm.addElement(nome);
    	etapa_lista_todos.setModel(lm);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void set_defaultlistmodel_actividades(){
    	DefaultListModel lm = new DefaultListModel();
    	for (String nome : this.lista_actividades_completa.keySet())
    		lm.addElement(nome);
    	actividade_lista_todos.setModel(lm);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void set_defaultlistmodel_tarefas(){
    	DefaultListModel lm = new DefaultListModel();
    	for (String nome : this.lista_tarefas_completa.keySet())
    		lm.addElement(nome);
    	tarefa_lista_todos.setModel(lm);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void set_defaultlistmodel_funcionarios(){
    	DefaultListModel lm = new DefaultListModel();
    	for (Funcionario f : this.lista_funcionarios_completa.values())
    		lm.addElement(f.get_nome());
    	funcionarios_lista_todos.setModel(lm);
    }
   
	private void carrega_listas_from_bd(){
        try{
        Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
        //etapas
        String sql = "select * from tnm_trf_etapa";
        PreparedStatement ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Etapa e = new Etapa();
            e.set_nome(rs.getString("NOME"));
            e.set_id(rs.getString("ID"));
            e.set_descricao(rs.getString("DESCRICAO"));
            this.lista_etapas_completa.put(e.get_nome(),e);
        }
        set_defaultlistmodel_etapas();
        
        //actividades
        sql = "select * from tnm_trf_actividade";
        ps=con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()){
            Actividade ac = new Actividade();
            ac.set_id(rs.getString("ID"));
            ac.set_nome(rs.getString("NOME"));
            ac.set_descricao(rs.getString("DESCRICAO"));
            this.lista_actividades_completa.put(ac.get_nome(),ac);
        }
        set_defaultlistmodel_actividades();
        //tarefas
        sql = "select * from tnm_trf_tarefa";
        ps=con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()){
            Tarefa ta = new Tarefa();
            ta.set_id(rs.getString("ID"));
            ta.set_nome(rs.getString("NOME"));
            ta.set_descricao(rs.getString("DESCRICAO"));
            this.lista_tarefas_completa.put(ta.get_nome(),ta);
        }
        set_defaultlistmodel_tarefas();
        //funcionarios
        sql = "select * from tnm_funcionario";
        ps=con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()){
            Funcionario f = new Funcionario();
            f.set_nome(rs.getString("NOME"));
            f.set_username(rs.getString("USERNAME"));
            this.lista_funcionarios_completa.put(f.get_username(),f);
        }
        set_defaultlistmodel_funcionarios();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
        }
    }
    
    @SuppressWarnings("unchecked")
	private void add_clientes_to_combo_box(){
        try{
        	TreeMap<String,Cliente> clientes = new TreeMap<>();
            Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
            String sql = "select * from tnm_trf_cliente" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                combo_clientes.addItem(rs.getString("NOME"));
                Cliente c = new Cliente();
                c.set_id(rs.getString("ID"));
                c.set_nome(rs.getString("NOME"));
                clientes.put(c.get_nome(),c);
            }
            this.lista_clientes = clientes;
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
        }
    }
    
    private void auto_code(){
        String cli = combo_clientes.getSelectedItem().toString();
        String part_id;
        Cliente c = this.lista_clientes.get(cli);
        part_id = c.get_id();
        String last_digits = get_last_digits(part_id);
        String codigo;
        if (!part_id.equals(""))
            codigo = "ODKASR" + part_id + last_digits;
        else
            codigo = "ODKASR" + "00" + last_digits;
        text_field_codigo.setText(codigo);
    }
    
    private String get_last_digits(String id){
        ArrayList<String> lista = new ArrayList<>();
        ArrayList<Integer> lista_numeros_cliente = new ArrayList<>();
        try{
            Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
            String sql = "select id_projecto from tnm_trf_projecto" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                lista.add(rs.getString("ID_PROJECTO"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
        }
        
        for (String s : lista){
            String cliente = return_cliente_from_id(s);
            if (cliente.equals(id))
            {
                lista_numeros_cliente.add(return_index_from_id(s));
            }
        }
        String ret = choose_next_id(lista_numeros_cliente);
        return ret;
    }
    
    private String choose_next_id(ArrayList<Integer> lista){
        int i = 1;
        int flag = 0;
        String ret = "";
        while (flag==0){
            if (lista.contains(i)){
                i++;
            }
            else{
                flag++;
                ret = String.valueOf(i);
                if (i<10)
                    ret = "0"+ret;
            }
        }
        return ret;
    }
    
    private String return_cliente_from_id(String id){
        if (id.contains("ODKASR"))
        {
            char a = id.toCharArray()[6];
            char b = id.toCharArray()[7];
            String n = String.valueOf(a)+String.valueOf(b);
            return n;
        }
        return "XX";
    }
    
    private int return_index_from_id(String id){
        char a = id.toCharArray()[8];
        char b = id.toCharArray()[9];
        String n = String.valueOf(a)+String.valueOf(b);
        int ret;
        try{
            ret = Integer.valueOf(n);
        }
        catch(NumberFormatException e)
        {
            ret = 0;
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
        }
        return ret;
    }
    
    private void set_listbox_projectos(){
    	DefaultListModel<String> d = new DefaultListModel<String>();
    	for (Projecto p : this.lista_projectos_interna.values())
    		d.addElement(p.get_codigo()+ " - "+ p.get_nome());
    	lista_projectos.setModel(d);
    }
    
	private void carrega_lista_projectos(){
        try{
            Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
            String sql = "select * from tnm_trf_projecto" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
//            DefaultListModel d = new DefaultListModel();
            while (rs.next()){
                ByteArrayInputStream bais;
                ObjectInputStream ois;
                try{
                    String codigo = rs.getString("ID_PROJECTO");
                    bais = new ByteArrayInputStream(rs.getBytes("PROJECTO"));
                    ois = new ObjectInputStream(bais);
                    Projecto p = (Projecto)ois.readObject();
                    lista_projectos_interna.put(codigo, p);
//                    d.addElement(codigo+ " - "+ p.get_nome());
                }
                catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                    this.setCursor(Cursor.getDefaultCursor());
                    new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
                }
//                lista_projectos.setModel(d);
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
        }
    }
    
    private Projecto get_projecto_from_menu(){
        Projecto p = new Projecto();
        p.set_codigo(text_field_codigo.getText());
        p.set_nome(text_field_nome.getText());
        p.set_cliente(return_cliente_from_combo());
        p.set_etapas(return_etapas_from_list());
        p.set_actividades(return_actividades_from_list());
        p.set_tarefas(return_tarefas_from_list());
        p.set_funcionarios(return_funcionarios_from_list());
        p.set_data_inicio(date_field_inicio.getDate());
        p.set_data_fim(date_field_fim.getDate());
        p.set_status(combo_status.getSelectedItem().toString());
        return p;
    }
    
    private Projecto get_projecto_from_listbox(){
        String[] aux = this.lista_projectos.getSelectedValue().toString().split(" - ");
        return this.lista_projectos_interna.get(aux[0]);
    }
    
    private void preencher_dados_projecto(){
        if (lista_projectos.getSelectedValue()!=null){
            botao_eliminar_projecto.setVisible(true);
            Projecto p = get_projecto_from_listbox();
            text_field_codigo.setText(p.get_codigo());
            text_field_nome.setText(p.get_nome());
            date_field_inicio.setDate(p.get_data_inicio());
            date_field_fim.setDate(p.get_data_fim());
            combo_clientes.setSelectedItem(p.get_cliente().get_nome());
            combo_status.setSelectedItem(p.get_status());
            preencher_listas_etapas(new TreeMap<>(p.get_etapas()));
            preencher_listas_actividades(new TreeMap<>(p.get_actividades()));
            preencher_listas_tarefas(new TreeMap<>(p.get_tarefas()));
            preencher_listas_funcionarios(new TreeMap<>(p.get_funcionarios()));
        }
    }
    
    private void preencher_dados_projecto_copia(){
        if (lista_projectos.getSelectedValue()!=null){
            Projecto p = get_projecto_from_listbox();
            combo_clientes.setSelectedItem(p.get_cliente().get_nome());
            preencher_listas_etapas(new TreeMap<>(p.get_etapas()));
            preencher_listas_actividades(new TreeMap<>(p.get_actividades()));
            preencher_listas_tarefas(new TreeMap<>(p.get_tarefas()));
            preencher_listas_funcionarios(new TreeMap<>(p.get_funcionarios()));
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void preencher_listas_etapas(TreeMap<String,Etapa> lista){
        DefaultListModel lm_todos = new DefaultListModel();
        DefaultListModel lm_escolhidos = new DefaultListModel();
        for (String s : this.lista_etapas_completa.keySet())
            if (lista.containsKey(s))
                lm_escolhidos.addElement(s);
            else
               lm_todos.addElement(s);
        etapa_lista_todos.setModel(lm_todos);
        etapa_lista_escolhidos.setModel(lm_escolhidos);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void preencher_listas_actividades(TreeMap<String,Actividade> lista){
        DefaultListModel lm_todos = new DefaultListModel();
        DefaultListModel lm_escolhidos = new DefaultListModel();
        for (String s : this.lista_actividades_completa.keySet())
            if (lista.containsKey(s))
                lm_escolhidos.addElement(s);
            else
               lm_todos.addElement(s);
        actividade_lista_todos.setModel(lm_todos);
        actividade_lista_escolhidos.setModel(lm_escolhidos);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void preencher_listas_tarefas(TreeMap<String,Tarefa> lista){
        DefaultListModel lm_todos = new DefaultListModel();
        DefaultListModel lm_escolhidos = new DefaultListModel();
        for (String s : this.lista_tarefas_completa.keySet())
            if (lista.containsKey(s))
                lm_escolhidos.addElement(s);
            else
               lm_todos.addElement(s);
        tarefa_lista_todos.setModel(lm_todos);
        tarefa_lista_escolhidos.setModel(lm_escolhidos);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void preencher_listas_funcionarios(TreeMap<String,Funcionario> lista){
        DefaultListModel lm_todos = new DefaultListModel();
        DefaultListModel lm_escolhidos = new DefaultListModel();
        for (String s : this.lista_funcionarios_completa.keySet())
            if (lista.containsKey(s))
                lm_escolhidos.addElement(s);
            else
               lm_todos.addElement(s);
        funcionarios_lista_todos.setModel(lm_todos);
        funcionarios_lista_escolhidos.setModel(lm_escolhidos);
    }
    
    private Cliente return_cliente_from_combo(){
        String aux = combo_clientes.getSelectedItem().toString();
        return this.lista_clientes.get(aux);
    }
    
    private HashMap<String,Etapa> return_etapas_from_list(){
    	HashMap<String,Etapa> aux = new HashMap<>();
        for (int i=0;i<etapa_lista_escolhidos.getModel().getSize();i++)
        {
            String str_aux = etapa_lista_escolhidos.getModel().getElementAt(i).toString();
            aux.put(str_aux, this.lista_etapas_completa.get(str_aux));
        }
        return aux;
    }
    
    private HashMap<String,Actividade> return_actividades_from_list(){
    	HashMap<String,Actividade> aux = new HashMap<>();
        for (int i=0;i<actividade_lista_escolhidos.getModel().getSize();i++)
        {
            String str_aux = actividade_lista_escolhidos.getModel().getElementAt(i).toString();
            aux.put(str_aux, this.lista_actividades_completa.get(str_aux));
        }
        return aux;
    }
    
    private HashMap<String,Tarefa> return_tarefas_from_list(){
    	HashMap<String,Tarefa> aux = new HashMap<>();
        for (int i=0;i<tarefa_lista_escolhidos.getModel().getSize();i++)
        {
            String str_aux = tarefa_lista_escolhidos.getModel().getElementAt(i).toString();
            aux.put(str_aux, this.lista_tarefas_completa.get(str_aux));
        }
        return aux;
    }
    
    private HashMap<String,Funcionario> return_funcionarios_from_list(){
    	HashMap<String,Funcionario> aux = new HashMap<>();
        for (int i=0;i<funcionarios_lista_escolhidos.getModel().getSize();i++)
        {
            String str_aux = funcionarios_lista_escolhidos.getModel().getElementAt(i).toString();
            aux.put(str_aux, this.lista_funcionarios_completa.get(str_aux));
        }
        return aux;
    }
    
    
    
    private int adicionar_novo_projecto_bd(Projecto p){
        //0 se tudo ok
        //1 se já existir codigo
        //2 se erro
        try{
        Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
        PreparedStatement ps;
        String sql;
        sql="select * from tnm_trf_projecto where id_projecto = '"+ p.get_codigo() +"'";
        ps=con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return 1;
        else{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(p);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();
            sql="insert into tnm_trf_projecto (id_projecto,projecto) values (?,?)";
            ps=con.prepareStatement(sql);
            ps.setString(1, p.get_codigo());
            ps.setObject(2, data);
            ps.executeUpdate();
            return 0;
        }
        }
        catch(IOException | SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
            return 2;
        }
    }
    
    private int confirma_novo_antigo_id(String novo, String antigo){
        if (novo.equals(antigo))
            return 0;
        else{
            try{
                Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
            PreparedStatement ps;
            String sql;
            sql="select * from tnm_trf_projecto where id_projecto = '"+ novo +"'";
            ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return 1;
            else{
                return 0;
            }
            }
            catch(SQLException e){
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
                return 2;
            }
        }
    }
    
    private int guardar_antigo_projecto_bd(Projecto p,String old_id){
        //0 se tudo ok
        //1 se ja existir novo id
        //2 se erro
        int res = confirma_novo_antigo_id(p.get_codigo(),old_id);
        if (res==1)
            return 1;
        else if (res==2)
            return 2;
        try{
            Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
            PreparedStatement ps;
            String sql;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(p);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();
            sql="update tnm_trf_projecto set id_projecto = ?, projecto = ? where id_projecto = ?";
            ps=con.prepareStatement(sql);
            ps.setString(1, p.get_codigo());
            ps.setObject(2, data);
            ps.setString(3, old_id);
            ps.executeUpdate();
            return 0;
        }
        catch(IOException | SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
            return 2;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    @SuppressWarnings("rawtypes")
	private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista_projectos = new javax.swing.JList<String>();
        botao_sair = new javax.swing.JButton();
        botao_voltar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        text_field_codigo = new javax.swing.JTextField();
        text_field_nome = new javax.swing.JTextField();
        combo_clientes = new javax.swing.JComboBox();
        date_field_inicio = new com.toedter.calendar.JDateChooser();
        combo_status = new javax.swing.JComboBox();
        date_field_fim = new com.toedter.calendar.JDateChooser();
        check_auto_code = new javax.swing.JCheckBox();
        check_copiar_projecto = new javax.swing.JCheckBox();
        tabbed_component = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        botao_etapas_adicionar = new javax.swing.JButton();
        botao_etapas_retirar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        etapa_lista_todos = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        etapa_lista_escolhidos = new javax.swing.JList();
        etapas_envia_todos = new javax.swing.JButton();
        etapas_retira_todos = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        actividade_lista_todos = new javax.swing.JList();
        botao_actividades_adicionar = new javax.swing.JButton();
        botao_actividades_retirar = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        actividade_lista_escolhidos = new javax.swing.JList();
        actividades_envia_todos = new javax.swing.JButton();
        actividades_retira_todos = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tarefa_lista_todos = new javax.swing.JList();
        botao_tarefas_adicionar = new javax.swing.JButton();
        botao_tarefas_retirar = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tarefa_lista_escolhidos = new javax.swing.JList();
        tarefas_envia_todos = new javax.swing.JButton();
        tarefas_retira_todos = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        funcionarios_lista_todos = new javax.swing.JList();
        botao_funcionarios_adicionar = new javax.swing.JButton();
        botao_funcionarios_retirar = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        funcionarios_lista_escolhidos = new javax.swing.JList();
        funcionarios_envia_todos = new javax.swing.JButton();
        funcionarios_retira_todos = new javax.swing.JButton();
        botao_alterar_projecto = new javax.swing.JButton();
        botao_novo_projecto = new javax.swing.JButton();
        botao_cancelar_edit = new javax.swing.JButton();
        botao_guardar_projecto = new javax.swing.JButton();
        botao_eliminar_projecto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/projecto_icon.png"))); // NOI18N
        jLabel1.setText("Adicionar/Consultar Projecto");

        jScrollPane1.setViewportView(lista_projectos);

        botao_sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_exit.png"))); // NOI18N
        botao_sair.setText("Sair");
        botao_sair.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_sair.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_sair.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_sairActionPerformed(evt);
            }
        });

        botao_voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_back.png"))); // NOI18N
        botao_voltar.setText("Voltar");
        botao_voltar.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_voltar.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_voltar.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_voltarActionPerformed(evt);
            }
        });

        jLabel2.setText("Código Projecto:");

        jLabel3.setText("Nome Projecto:");

        jLabel4.setText("Cliente:");

        jLabel5.setText("Data de início:");

        jLabel10.setText("Status:");

        jLabel11.setText("Data de fim:");
        jLabel11.setPreferredSize(new java.awt.Dimension(35, 14));

        combo_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_clientesActionPerformed(evt);
            }
        });

        check_auto_code.setText("Código Automático");
        check_auto_code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_auto_codeActionPerformed(evt);
            }
        });

        check_copiar_projecto.setText("Copiar de Projecto Existente");
        check_copiar_projecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_copiar_projectoActionPerformed(evt);
            }
        });

        tabbed_component.setBackground(new java.awt.Color(204, 204, 204));

        botao_etapas_adicionar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_etapas_adicionar.setText("-->");
        botao_etapas_adicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_etapas_adicionarActionPerformed(evt);
            }
        });

        botao_etapas_retirar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_etapas_retirar.setText("<--");
        botao_etapas_retirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_etapas_retirarActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(etapa_lista_todos);
        jScrollPane3.setViewportView(etapa_lista_escolhidos);

        etapas_envia_todos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        etapas_envia_todos.setText(">>");
        etapas_envia_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etapas_envia_todosActionPerformed(evt);
            }
        });

        etapas_retira_todos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        etapas_retira_todos.setText("<<");
        etapas_retira_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etapas_retira_todosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
        			.addGap(10)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(etapas_retira_todos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(botao_etapas_retirar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(botao_etapas_adicionar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(etapas_envia_todos, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
        			.addGap(10)
        			.addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
        			.addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(58)
        			.addComponent(etapas_envia_todos)
        			.addGap(18)
        			.addComponent(botao_etapas_adicionar)
        			.addGap(18)
        			.addComponent(botao_etapas_retirar)
        			.addGap(18)
        			.addComponent(etapas_retira_todos)
        			.addGap(52))
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(10)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPane3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        				.addComponent(jScrollPane2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
        			.addGap(10))
        );
        jPanel1.setLayout(jPanel1Layout);

        tabbed_component.addTab("Etapas", jPanel1);

        jScrollPane4.setViewportView(actividade_lista_todos);

        botao_actividades_adicionar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_actividades_adicionar.setText("-->");
        botao_actividades_adicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_actividades_adicionarActionPerformed(evt);
            }
        });

        botao_actividades_retirar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_actividades_retirar.setText("<--");
        botao_actividades_retirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_actividades_retirarActionPerformed(evt);
            }
        });
        jScrollPane5.setViewportView(actividade_lista_escolhidos);

        actividades_envia_todos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        actividades_envia_todos.setText(">>");
        actividades_envia_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actividades_envia_todosActionPerformed(evt);
            }
        });

        actividades_retira_todos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        actividades_retira_todos.setText("<<");
        actividades_retira_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actividades_retira_todosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2Layout.setHorizontalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
        			.addGap(10)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(actividades_retira_todos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(botao_actividades_retirar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(botao_actividades_adicionar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(actividades_envia_todos, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
        			.addGap(10)
        			.addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addGap(58)
        			.addComponent(actividades_envia_todos)
        			.addGap(18)
        			.addComponent(botao_actividades_adicionar)
        			.addGap(18)
        			.addComponent(botao_actividades_retirar)
        			.addGap(18)
        			.addComponent(actividades_retira_todos)
        			.addGap(52))
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addGap(10)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPane5, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        				.addComponent(jScrollPane4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
        			.addGap(10))
        );
        jPanel2.setLayout(jPanel2Layout);

        tabbed_component.addTab("Actividades", jPanel2);

        jScrollPane6.setViewportView(tarefa_lista_todos);

        botao_tarefas_adicionar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_tarefas_adicionar.setText("-->");
        botao_tarefas_adicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_tarefas_adicionarActionPerformed(evt);
            }
        });

        botao_tarefas_retirar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_tarefas_retirar.setText("<--");
        botao_tarefas_retirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_tarefas_retirarActionPerformed(evt);
            }
        });
        jScrollPane7.setViewportView(tarefa_lista_escolhidos);

        tarefas_envia_todos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tarefas_envia_todos.setText(">>");
        tarefas_envia_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tarefas_envia_todosActionPerformed(evt);
            }
        });

        tarefas_retira_todos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tarefas_retira_todos.setText("<<");
        tarefas_retira_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tarefas_retira_todosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3Layout.setHorizontalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jScrollPane6, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
        			.addGap(10)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(tarefas_retira_todos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(botao_tarefas_adicionar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(tarefas_envia_todos, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        				.addComponent(botao_tarefas_retirar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addGap(10)
        			.addComponent(jScrollPane7, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
        			.addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addGap(58)
        			.addComponent(tarefas_envia_todos)
        			.addGap(18)
        			.addComponent(botao_tarefas_adicionar)
        			.addGap(18)
        			.addComponent(botao_tarefas_retirar)
        			.addGap(18)
        			.addComponent(tarefas_retira_todos)
        			.addGap(55))
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addGap(10)
        			.addComponent(jScrollPane6, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        			.addGap(10))
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addGap(10)
        			.addComponent(jScrollPane7, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        			.addGap(10))
        );
        jPanel3.setLayout(jPanel3Layout);

        tabbed_component.addTab("Tarefas", jPanel3);

        jScrollPane8.setViewportView(funcionarios_lista_todos);

        botao_funcionarios_adicionar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_funcionarios_adicionar.setText("-->");
        botao_funcionarios_adicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_funcionarios_adicionarActionPerformed(evt);
            }
        });

        botao_funcionarios_retirar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_funcionarios_retirar.setText("<--");
        botao_funcionarios_retirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_funcionarios_retirarActionPerformed(evt);
            }
        });
        jScrollPane9.setViewportView(funcionarios_lista_escolhidos);

        funcionarios_envia_todos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        funcionarios_envia_todos.setText(">>");
        funcionarios_envia_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funcionarios_envia_todosActionPerformed(evt);
            }
        });

        funcionarios_retira_todos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        funcionarios_retira_todos.setText("<<");
        funcionarios_retira_todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funcionarios_retira_todosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4Layout.setHorizontalGroup(
        	jPanel4Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel4Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jScrollPane8, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
        			.addGap(10)
        			.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(funcionarios_retira_todos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(botao_funcionarios_retirar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(botao_funcionarios_adicionar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(funcionarios_envia_todos, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
        			.addGap(10)
        			.addComponent(jScrollPane9, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
        			.addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
        	jPanel4Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel4Layout.createSequentialGroup()
        			.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel4Layout.createSequentialGroup()
        					.addGap(58)
        					.addComponent(funcionarios_envia_todos)
        					.addGap(18)
        					.addComponent(botao_funcionarios_adicionar)
        					.addGap(18)
        					.addComponent(botao_funcionarios_retirar)
        					.addGap(18)
        					.addComponent(funcionarios_retira_todos))
        				.addGroup(jPanel4Layout.createSequentialGroup()
        					.addGap(10)
        					.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jScrollPane9, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        						.addComponent(jScrollPane8, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))))
        			.addGap(10))
        );
        jPanel4.setLayout(jPanel4Layout);

        tabbed_component.addTab("Funcionários", jPanel4);

        botao_alterar_projecto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/edit_icon.png"))); // NOI18N
        botao_alterar_projecto.setText("Alterar");
        botao_alterar_projecto.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_alterar_projecto.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_alterar_projecto.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_alterar_projecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_alterar_projectoActionPerformed(evt);
            }
        });

        botao_novo_projecto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/new_icon.png"))); // NOI18N
        botao_novo_projecto.setText("Novo");
        botao_novo_projecto.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_novo_projecto.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_novo_projecto.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_novo_projecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_novo_projectoActionPerformed(evt);
            }
        });

        botao_cancelar_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/Cancel-icon.png"))); // NOI18N
        botao_cancelar_edit.setText("Cancelar");
        botao_cancelar_edit.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_cancelar_edit.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_cancelar_edit.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_cancelar_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_cancelar_editActionPerformed(evt);
            }
        });

        botao_guardar_projecto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/saveicon.png"))); // NOI18N
        botao_guardar_projecto.setText("Guardar");
        botao_guardar_projecto.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_guardar_projecto.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_guardar_projecto.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_guardar_projecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_guardar_projectoActionPerformed(evt);
            }
        });

        botao_eliminar_projecto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_delete.png"))); // NOI18N
        botao_eliminar_projecto.setText("Eliminar");
        botao_eliminar_projecto.setMaximumSize(new java.awt.Dimension(120, 40));
        botao_eliminar_projecto.setMinimumSize(new java.awt.Dimension(120, 40));
        botao_eliminar_projecto.setPreferredSize(new java.awt.Dimension(120, 40));
        botao_eliminar_projecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_eliminar_projectoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 504, Short.MAX_VALUE)
        					.addComponent(botao_eliminar_projecto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
        					.addGap(20)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(botao_guardar_projecto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(botao_novo_projecto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(botao_alterar_projecto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(botao_voltar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(botao_cancelar_edit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(botao_sair, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createSequentialGroup()
        							.addGap(7)
        							.addComponent(tabbed_component))
        						.addGroup(layout.createSequentialGroup()
        							.addGap(41)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel10, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel11, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(combo_status, 0, 430, Short.MAX_VALUE)
        								.addComponent(date_field_inicio, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        								.addComponent(text_field_nome, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        								.addComponent(combo_clientes, 0, 430, Short.MAX_VALUE)
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(text_field_codigo, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        									.addGap(18)
        									.addComponent(check_auto_code)
        									.addGap(97))
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(date_field_fim, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        									.addGap(67)
        									.addComponent(check_copiar_projecto)))
        							.addGap(165)))
        					.addGap(0)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel1)
        				.addComponent(botao_eliminar_projecto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(3)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel2)
        						.addComponent(text_field_codigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(check_auto_code))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel3)
        						.addComponent(text_field_nome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel4)
        						.addComponent(combo_clientes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(date_field_inicio, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addGap(18)
        					.addComponent(tabbed_component, GroupLayout.PREFERRED_SIZE, 287, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(combo_status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel10))
        					.addGap(23)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        							.addComponent(jLabel11, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(date_field_fim, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(check_copiar_projecto)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(botao_voltar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(botao_sair, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(botao_alterar_projecto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(botao_novo_projecto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(botao_cancelar_edit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(botao_guardar_projecto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
        				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botao_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_voltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botao_voltarActionPerformed

    private void botao_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_sairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_botao_sairActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void botao_etapas_adicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_etapas_adicionarActionPerformed
        int[] lista_select = etapa_lista_todos.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < etapa_lista_todos.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = etapa_lista_todos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = etapa_lista_todos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = etapa_lista_todos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = etapa_lista_todos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < etapa_lista_escolhidos.getModel().getSize();i++){
                    Object obj = etapa_lista_escolhidos.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            etapa_lista_todos.setModel(lm_todos);
            etapa_lista_escolhidos.setModel(lm_escolhidos);
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma etapa!");
    }//GEN-LAST:event_botao_etapas_adicionarActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void botao_etapas_retirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_etapas_retirarActionPerformed
        int[] lista_select = etapa_lista_escolhidos.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < etapa_lista_escolhidos.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = etapa_lista_escolhidos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = etapa_lista_escolhidos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = etapa_lista_escolhidos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = etapa_lista_escolhidos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < etapa_lista_todos.getModel().getSize();i++){
                    Object obj = etapa_lista_todos.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            etapa_lista_escolhidos.setModel(lm_todos);
            etapa_lista_todos.setModel(lm_escolhidos); 
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma etapa!");
    }//GEN-LAST:event_botao_etapas_retirarActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void botao_actividades_adicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_actividades_adicionarActionPerformed
        int[] lista_select = actividade_lista_todos.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < actividade_lista_todos.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = actividade_lista_todos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = actividade_lista_todos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = actividade_lista_todos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = actividade_lista_todos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < actividade_lista_escolhidos.getModel().getSize();i++){
                    Object obj = actividade_lista_escolhidos.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            actividade_lista_todos.setModel(lm_todos);
            actividade_lista_escolhidos.setModel(lm_escolhidos);
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma actividade!");
    }//GEN-LAST:event_botao_actividades_adicionarActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void botao_tarefas_adicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_tarefas_adicionarActionPerformed
        int[] lista_select = tarefa_lista_todos.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < tarefa_lista_todos.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = tarefa_lista_todos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = tarefa_lista_todos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = tarefa_lista_todos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = tarefa_lista_todos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < tarefa_lista_escolhidos.getModel().getSize();i++){
                    Object obj = tarefa_lista_escolhidos.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            tarefa_lista_todos.setModel(lm_todos);
            tarefa_lista_escolhidos.setModel(lm_escolhidos);
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
    }//GEN-LAST:event_botao_tarefas_adicionarActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void botao_funcionarios_adicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_funcionarios_adicionarActionPerformed
        int[] lista_select = funcionarios_lista_todos.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < funcionarios_lista_todos.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = funcionarios_lista_todos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = funcionarios_lista_todos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = funcionarios_lista_todos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = funcionarios_lista_todos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < funcionarios_lista_escolhidos.getModel().getSize();i++){
                    Object obj = funcionarios_lista_escolhidos.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            funcionarios_lista_todos.setModel(lm_todos);
            funcionarios_lista_escolhidos.setModel(lm_escolhidos);
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum funcionário!");
    }//GEN-LAST:event_botao_funcionarios_adicionarActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void botao_actividades_retirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_actividades_retirarActionPerformed
        int[] lista_select = actividade_lista_escolhidos.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < actividade_lista_escolhidos.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = actividade_lista_escolhidos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = actividade_lista_escolhidos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = actividade_lista_escolhidos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = actividade_lista_escolhidos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < actividade_lista_todos.getModel().getSize();i++){
                    Object obj = actividade_lista_todos.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            actividade_lista_escolhidos.setModel(lm_todos);
            actividade_lista_todos.setModel(lm_escolhidos); 
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma actividade!");
    }//GEN-LAST:event_botao_actividades_retirarActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void botao_tarefas_retirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_tarefas_retirarActionPerformed
        int[] lista_select = tarefa_lista_escolhidos.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < tarefa_lista_escolhidos.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = tarefa_lista_escolhidos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = tarefa_lista_escolhidos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = tarefa_lista_escolhidos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = tarefa_lista_escolhidos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < tarefa_lista_todos.getModel().getSize();i++){
                    Object obj = tarefa_lista_todos.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            tarefa_lista_escolhidos.setModel(lm_todos);
            tarefa_lista_todos.setModel(lm_escolhidos); 
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
    }//GEN-LAST:event_botao_tarefas_retirarActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void botao_funcionarios_retirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_funcionarios_retirarActionPerformed
        int[] lista_select = funcionarios_lista_escolhidos.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < funcionarios_lista_escolhidos.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = funcionarios_lista_escolhidos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = funcionarios_lista_escolhidos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = funcionarios_lista_escolhidos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = funcionarios_lista_escolhidos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < funcionarios_lista_todos.getModel().getSize();i++){
                    Object obj = funcionarios_lista_todos.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            funcionarios_lista_escolhidos.setModel(lm_todos);
            funcionarios_lista_todos.setModel(lm_escolhidos); 
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum funcionário!");
    }//GEN-LAST:event_botao_funcionarios_retirarActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void funcionarios_retira_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_funcionarios_retira_todosActionPerformed
        ListModel lm = funcionarios_lista_todos.getModel();
        ListModel lm2 = funcionarios_lista_escolhidos.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        funcionarios_lista_escolhidos.setModel(vazio);
        funcionarios_lista_todos.setModel(uniao);
    }//GEN-LAST:event_funcionarios_retira_todosActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void funcionarios_envia_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_funcionarios_envia_todosActionPerformed
        ListModel lm = funcionarios_lista_todos.getModel();
        ListModel lm2 = funcionarios_lista_escolhidos.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        funcionarios_lista_todos.setModel(vazio);
        funcionarios_lista_escolhidos.setModel(uniao);
    }//GEN-LAST:event_funcionarios_envia_todosActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void tarefas_envia_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tarefas_envia_todosActionPerformed
        ListModel lm = tarefa_lista_todos.getModel();
        ListModel lm2 = tarefa_lista_escolhidos.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        tarefa_lista_todos.setModel(vazio);
        tarefa_lista_escolhidos.setModel(uniao);
    }//GEN-LAST:event_tarefas_envia_todosActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void tarefas_retira_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tarefas_retira_todosActionPerformed
        ListModel lm = tarefa_lista_todos.getModel();
        ListModel lm2 = tarefa_lista_escolhidos.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        tarefa_lista_escolhidos.setModel(vazio);
        tarefa_lista_todos.setModel(uniao);
    }//GEN-LAST:event_tarefas_retira_todosActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void actividades_envia_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actividades_envia_todosActionPerformed
        ListModel lm = actividade_lista_todos.getModel();
        ListModel lm2 = actividade_lista_escolhidos.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        actividade_lista_todos.setModel(vazio);
        actividade_lista_escolhidos.setModel(uniao);
    }//GEN-LAST:event_actividades_envia_todosActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void actividades_retira_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actividades_retira_todosActionPerformed
        ListModel lm = actividade_lista_todos.getModel();
        ListModel lm2 = actividade_lista_escolhidos.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        actividade_lista_escolhidos.setModel(vazio);
        actividade_lista_todos.setModel(uniao);
    }//GEN-LAST:event_actividades_retira_todosActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private DefaultListModel une_list_model(ListModel l1, ListModel l2){
        DefaultListModel aux = new DefaultListModel();
        for (int i=0;i<l1.getSize();i++)
            aux.addElement(l1.getElementAt(i));
        for (int i=0;i<l2.getSize();i++)
            aux.addElement(l2.getElementAt(i));
        return aux;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void etapas_envia_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etapas_envia_todosActionPerformed
        ListModel lm = etapa_lista_todos.getModel();
        ListModel lm2 = etapa_lista_escolhidos.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        etapa_lista_todos.setModel(vazio);
        etapa_lista_escolhidos.setModel(uniao);
    }//GEN-LAST:event_etapas_envia_todosActionPerformed

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void etapas_retira_todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etapas_retira_todosActionPerformed
        ListModel lm = etapa_lista_todos.getModel();
        ListModel lm2 = etapa_lista_escolhidos.getModel();
        DefaultListModel uniao = une_list_model(lm, lm2);
        DefaultListModel vazio = new DefaultListModel();
        etapa_lista_escolhidos.setModel(vazio);
        etapa_lista_todos.setModel(uniao);
    }//GEN-LAST:event_etapas_retira_todosActionPerformed

    private void check_auto_codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_auto_codeActionPerformed
        if (check_auto_code.isSelected())
            auto_code();
    }//GEN-LAST:event_check_auto_codeActionPerformed

    private void combo_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_clientesActionPerformed
        if (check_auto_code.isSelected())
            auto_code();
    }//GEN-LAST:event_combo_clientesActionPerformed

    private void botao_novo_projectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_novo_projectoActionPerformed
        modo_novo_projecto();
        this.edicao = true;
        this.novo = true;
    }//GEN-LAST:event_botao_novo_projectoActionPerformed

    private void botao_guardar_projectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_guardar_projectoActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Projecto p = get_projecto_from_menu();
        int res;
        if (this.novo){
            res = adicionar_novo_projecto_bd(p);
            this.novo = false;
        }
        else
            res = guardar_antigo_projecto_bd(p,this.old_id);
        
        if (res==0){
            JOptionPane.showMessageDialog(null, "Projecto guardado com sucesso!");
            //recarrega lista projectos
            carrega_lista_projectos();
            //
            modo_administrador();
            this.edicao = false;
            lista_projectos.setSelectedIndex(0);
        }
        else if (res==1)
            JOptionPane.showMessageDialog(null, "Já existe um projecto com esse código. Altere o código e volte a tentar!");
        else{
            JOptionPane.showMessageDialog(null, "Ocorreu um erro! Contacte administrador!");
            //recarrega lista projectos
            carrega_lista_projectos();
            //
            modo_administrador();
            this.edicao = false;
            lista_projectos.setSelectedIndex(0);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_guardar_projectoActionPerformed

    private void botao_alterar_projectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_alterar_projectoActionPerformed
        modo_alterar_projecto();
        this.edicao = true;
    }//GEN-LAST:event_botao_alterar_projectoActionPerformed

    private void botao_cancelar_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_cancelar_editActionPerformed
        modo_administrador();
        this.edicao = false;
        lista_projectos.setSelectedIndex(0);
    }//GEN-LAST:event_botao_cancelar_editActionPerformed

    private void check_copiar_projectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_copiar_projectoActionPerformed
        this.copia = check_copiar_projecto.isSelected();
        lista_projectos.setEnabled(check_copiar_projecto.isSelected());
    }//GEN-LAST:event_check_copiar_projectoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void botao_eliminar_projectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_eliminar_projectoActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String codigo = text_field_codigo.getText();
        if (!codigo.equals("")){
            if (delete_from_bd(codigo)==0){
                DefaultListModel d = new DefaultListModel();
                Projecto elimin = null;
                for (Projecto pr : this.lista_projectos_interna.values()){
                    if (!pr.get_codigo().equals(codigo))
                        d.addElement(pr.get_codigo()+ " - "+ pr.get_nome());
                    else
                        elimin = pr;
                }
                lista_projectos.setModel(d);
                if (elimin!=null)
                    this.lista_projectos_interna.remove(elimin.get_codigo());
                JOptionPane.showMessageDialog(null, "Eliminado com sucesso!");
                modo_administrador();
                this.edicao = false;
                lista_projectos.setSelectedIndex(0);
            }
            else
                JOptionPane.showMessageDialog(null, "Erro, contacte administrador!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Deve seleccionar um item!");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_eliminar_projectoActionPerformed

    private int delete_from_bd(String codigo){
        try{
            Connection con = (new Connection_bd(this.funcionario.get_username())).get_connection();
            String sql = "delete from tnm_trf_projecto where id_projecto = '" + codigo + "'" ;
            PreparedStatement ps=con.prepareStatement(sql);
            ps.executeUpdate();
            return 0;
        }
        catch(SQLException e){
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.funcionario.get_username(),e);
            return 1;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    @SuppressWarnings("rawtypes")
	private javax.swing.JList actividade_lista_escolhidos;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList actividade_lista_todos;
    private javax.swing.JButton actividades_envia_todos;
    private javax.swing.JButton actividades_retira_todos;
    private javax.swing.JButton botao_actividades_adicionar;
    private javax.swing.JButton botao_actividades_retirar;
    private javax.swing.JButton botao_alterar_projecto;
    private javax.swing.JButton botao_cancelar_edit;
    private javax.swing.JButton botao_eliminar_projecto;
    private javax.swing.JButton botao_etapas_adicionar;
    private javax.swing.JButton botao_etapas_retirar;
    private javax.swing.JButton botao_funcionarios_adicionar;
    private javax.swing.JButton botao_funcionarios_retirar;
    private javax.swing.JButton botao_guardar_projecto;
    private javax.swing.JButton botao_novo_projecto;
    private javax.swing.JButton botao_sair;
    private javax.swing.JButton botao_tarefas_adicionar;
    private javax.swing.JButton botao_tarefas_retirar;
    private javax.swing.JButton botao_voltar;
    private javax.swing.JCheckBox check_auto_code;
    private javax.swing.JCheckBox check_copiar_projecto;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox combo_clientes;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox combo_status;
    private com.toedter.calendar.JDateChooser date_field_fim;
    private com.toedter.calendar.JDateChooser date_field_inicio;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList etapa_lista_escolhidos;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList etapa_lista_todos;
    private javax.swing.JButton etapas_envia_todos;
    private javax.swing.JButton etapas_retira_todos;
    private javax.swing.JButton funcionarios_envia_todos;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList funcionarios_lista_escolhidos;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList funcionarios_lista_todos;
    private javax.swing.JButton funcionarios_retira_todos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
	private javax.swing.JList<String> lista_projectos;
    private javax.swing.JTabbedPane tabbed_component;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList tarefa_lista_escolhidos;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList tarefa_lista_todos;
    private javax.swing.JButton tarefas_envia_todos;
    private javax.swing.JButton tarefas_retira_todos;
    private javax.swing.JTextField text_field_codigo;
    private javax.swing.JTextField text_field_nome;
    // End of variables declaration//GEN-END:variables
}
