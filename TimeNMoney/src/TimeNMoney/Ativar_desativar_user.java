package TimeNMoney;

import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.JList;
import javax.swing.border.LineBorder;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Ativar_desativar_user extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblAtivardesativarFuncionrios;
	private JPanel panel;
	private JPanel panel_activos;
	private JPanel panel_inativos;
	private JButton desativar_button;
	private JButton ativar_button;
	private JButton cancel_button;
	private JButton save_button;
	private TreeMap<String,Funcionario> lista_funcionarios_todos;
	private HashMap<String,byte[]> lista_funcionarios_inativos;
	private JList<String> list;
	private JList<String> lista_ativos;
	private JList<String> lista_inativos;

	public Ativar_desativar_user() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				set_location_to_null();
			}
		});
		setTitle("ODKAS - Time &  Money");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ativar_desativar_user.class.getResource("/TimeNMoney/odkas.tnm.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 696, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		{
			lblAtivardesativarFuncionrios = new JLabel("Ativar/Desativar Funcionários");
			lblAtivardesativarFuncionrios.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblAtivardesativarFuncionrios.setIcon(new ImageIcon(Ativar_desativar_user.class.getResource("/TimeNMoney/ap_disap_funci_big.png")));
		}
		{
			panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Funcion\u00E1rios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			{
				panel_activos = new JPanel();
				panel_activos.setBorder(new TitledBorder(null, "Ativos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			}
			{
				panel_inativos = new JPanel();
				panel_inativos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Inativos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			}
			{
				desativar_button = new JButton(">");
				desativar_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						desativar_selected();
					}
				});
				desativar_button.setFont(new Font("Tahoma", Font.BOLD, 11));
			}
			{
				ativar_button = new JButton("<");
				ativar_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ativar_selected();
					}
				});
				ativar_button.setFont(new Font("Tahoma", Font.BOLD, 11));
			}
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(4)
						.addComponent(panel_activos, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addGap(10)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(desativar_button, GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
							.addComponent(ativar_button, GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
						.addGap(10)
						.addComponent(panel_inativos, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addGap(4))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(6)
						.addComponent(panel_activos, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
						.addGap(4))
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(92)
						.addComponent(desativar_button)
						.addGap(11)
						.addComponent(ativar_button))
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(6)
						.addComponent(panel_inativos, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
						.addGap(4))
			);
			panel_inativos.setLayout(new BorderLayout(0, 0));
			{
				lista_inativos = new JList<String>();
				lista_inativos.setBorder(new LineBorder(new Color(0, 0, 0)));
				panel_inativos.add(lista_inativos, BorderLayout.CENTER);
			}
			{
				list = new JList<String>();
			}
			lista_ativos = new JList<String>();
			lista_ativos.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_activos.setLayout(new BorderLayout(0, 0));
			panel_activos.add(list);
			panel_activos.add(lista_ativos);
			panel.setLayout(gl_panel);
		}
		cancel_button = new JButton("Cancelar");
		cancel_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel_action();
			}
		});
		cancel_button.setIcon(new ImageIcon(Ativar_desativar_user.class.getResource("/TimeNMoney/Cancel-icon.png")));
		save_button = new JButton("Guardar");
		save_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save_action();
			}
		});
		save_button.setIcon(new ImageIcon(Ativar_desativar_user.class.getResource("/TimeNMoney/saveicon.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAtivardesativarFuncionrios, GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(5))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(408, Short.MAX_VALUE)
					.addComponent(save_button, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cancel_button, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(lblAtivardesativarFuncionrios, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(save_button, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(cancel_button, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)))
		);
		contentPane.setLayout(gl_contentPane);
		init();
	}
	
	private void init(){
		Connection con = (new Connection_bd()).get_connection();
		lista_funcionarios_todos = get_lista_funcionarios(con);
		lista_funcionarios_inativos = get_lista_funcionarios_inativos(con);
		
		set_lista_ativos();
		set_lista_inactivos();
	}
	
	private void cancel_action(){
		this.dispose();
	}
	
	private void save_action(){
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		Connection con = (new Connection_bd()).get_connection();
		
		//get users para desativar
		ArrayList<String> users_para_desativar = get_users_para_desativar();
		
		//get users para ativar
		ArrayList<String> users_para_ativar = get_users_para_ativar();
		
		int res = 0;
		//desativar users
		for (String s : users_para_desativar)
			res += desativar_user(s,con);
		
		//reativar users
		for (String s : users_para_ativar)
			res += ativar_user(s,con);
		
		if (res==0)
		{
			JOptionPane.showMessageDialog(null, "Guardado com sucesso!");
			this.dispose();
		}
		else
			JOptionPane.showMessageDialog(null,"Ocorrência de erro, contacte responsável!");
		this.setCursor(Cursor.getDefaultCursor());
	}
	
	private int desativar_user(String username, Connection con){
		String sql = "select * from tnm_enc_logb where username = ?";
		byte[] old_pass = null;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				old_pass = rs.getBytes("password");
			
			sql = "delete from tnm_enc_logb where username = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.executeUpdate();
			
			sql = "insert into tnm_users_inativos (username,old_password) values (?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setBytes(2, old_pass);
			ps.executeUpdate();
			
			ps.close();
			rs.close();
			return 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			this.setCursor(Cursor.getDefaultCursor());
			new Log_erros_class().write_log_to_file(e);
			return 1;
		}
		
	}
	
	private int ativar_user(String username, Connection con){
		String sql = "select * from tnm_users_inativos where username = ?";
		byte[] old_pass = null;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				old_pass = rs.getBytes("old_password");
			
			sql = "delete from tnm_users_inativos where username = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.executeUpdate();
			
			sql = "insert into tnm_enc_logb (username,password) values (?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setBytes(2, old_pass);
			ps.executeUpdate();
			
			ps.close();
			rs.close();
			return 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			this.setCursor(Cursor.getDefaultCursor());
			new Log_erros_class().write_log_to_file(e);
			return 1;
		}
		
	}
	
	private ArrayList<String> get_users_para_desativar(){
		ArrayList<String> users = new ArrayList<String>();
		for (int i = 0;i < lista_inativos.getModel().getSize();i++)
		{
			String s = lista_inativos.getModel().getElementAt(i);
			String username = s.split("\\(")[1].replace(")", "").trim();
			if (!this.lista_funcionarios_inativos.containsKey(username))
				users.add(username);
		}
		return users;
	}
	
	private ArrayList<String> get_users_para_ativar(){
		ArrayList<String> users = new ArrayList<String>();
		for (int i = 0;i < lista_ativos.getModel().getSize();i++)
		{
			String s = lista_ativos.getModel().getElementAt(i);
			String username = s.split("\\(")[1].replace(")", "").trim();
			if (this.lista_funcionarios_inativos.containsKey(username))
				users.add(username);
		}
		return users;
	}
	
	private TreeMap<String,Funcionario> get_lista_funcionarios(Connection con){
		TreeMap<String,Funcionario> lista_aux = new TreeMap<>();
        try{
            String sql = "select * from tnm_funcionario";
	        PreparedStatement ps=con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()){
	            String user_name = rs.getString("USERNAME");
	            String nome = rs.getString("NOME");
	            String contacto_pt = rs.getString("TELEMOVEL_PT");
	            String contacto_ao = rs.getString("TELEMOVEL_AO");
	            String contacto_sk = rs.getString("SKYPE");
	            String mail = rs.getString("MAIL");
	            boolean ad = (rs.getInt("ADMIN") != 0);
	            Date dn = rs.getDate("DATA_NASCIMENTO");
	            
	            Funcionario fn = new Funcionario();
	            fn.set_username(user_name);
	            fn.set_nome(nome);            
	            fn.set_phone_pt(contacto_pt);
	            fn.set_phone_ao(contacto_ao);
	            fn.set_mail(mail);
	            fn.set_id_skype(contacto_sk);
	            fn.set_admin(ad);
	            fn.set_nascimento(dn);
	            lista_aux.put(user_name,fn);
	        }
	        rs.close();
	        ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
        return lista_aux;
	}
	
	private HashMap<String,byte[]> get_lista_funcionarios_inativos(Connection con){
		HashMap<String,byte[]> lista_aux = new HashMap<>();
        try{
            String sql = "select * from tnm_users_inativos";
	        PreparedStatement ps=con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()){
	            String user = rs.getString("username");
	            byte[] old_pass = rs.getBytes("old_password");
	            lista_aux.put(user,old_pass);
	        }
	        rs.close();
	        ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
        return lista_aux;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void set_lista_inactivos(){
		DefaultListModel dlm = new DefaultListModel();
		for (String s : this.lista_funcionarios_inativos.keySet())
		{
			Funcionario aux = null;
			for (Funcionario f : this.lista_funcionarios_todos.values())
				if (f.get_username().equals(s))
					aux = f;
			if (aux!=null)
			{
				dlm.addElement(aux.get_nome() + " ( " + aux.get_username() + " )");
			}
		}
		lista_inativos.setModel(dlm);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void set_lista_ativos(){
		DefaultListModel dlm = new DefaultListModel();
		for (Funcionario f : this.lista_funcionarios_todos.values())
			if (!lista_funcionarios_inativos.containsKey(f.get_username()))
			{
				dlm.addElement(f.get_nome() + " ( " + f.get_username() + " )");
			}
		lista_ativos.setModel(dlm);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void desativar_selected(){
		int[] lista_select = lista_ativos.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < lista_ativos.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = lista_ativos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = lista_ativos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = lista_ativos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = lista_ativos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < lista_inativos.getModel().getSize();i++){
                    Object obj = lista_inativos.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            lista_ativos.setModel(lm_todos);
            lista_inativos.setModel(lm_escolhidos);
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum funcionário!");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void ativar_selected(){
		int[] lista_select = lista_inativos.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            tam--;
            int j = 0;
            DefaultListModel lm_todos = new DefaultListModel();
            DefaultListModel lm_escolhidos = new DefaultListModel();
            for (int i = 0; i < lista_inativos.getModel().getSize();i++){
                if (i<lista_select[j]){
                    Object obj = lista_inativos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
                else if (i==lista_select[j]){
                    if (j<tam){
                        j++;
                        Object obj = lista_inativos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                    else if (j==tam){
                        Object obj = lista_inativos.getModel().getElementAt(i);
                        lm_escolhidos.addElement(obj);
                    }
                }
                else{
                    Object obj = lista_inativos.getModel().getElementAt(i);
                    lm_todos.addElement(obj);
                }
            }
            for (int i = 0; i < lista_ativos.getModel().getSize();i++){
                    Object obj = lista_ativos.getModel().getElementAt(i);
                    lm_escolhidos.addElement(obj);
            }
            lista_inativos.setModel(lm_todos);
            lista_ativos.setModel(lm_escolhidos); 
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum funcionário!");
	}
	
	private void set_location_to_null(){
		this.setLocationRelativeTo(null);
	}
}
