package TimeNMoney;

import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class Admin_manager extends JFrame {
	private JPanel contentPane;
	private JLabel lblMenuManagers;
	private JPanel panel;
	@SuppressWarnings("rawtypes")
	private JComboBox manager_combo_box;
	private JPanel panel_1;
	private JButton cancel_btn;
	private JButton save_btn;
	private JPanel panel_2;
	private JPanel panel_3;
	@SuppressWarnings("rawtypes")
	private JList todos_func_lista;
	@SuppressWarnings("rawtypes")
	private JList geridos_func_lista;
	private JButton goto_manager;
	private JButton left_manager;
	private TreeMap<String,Funcionario> lista_funcionarios;
	private TreeMap<String,TreeMap<String,Funcionario>> lista_geridos_total;
	private Connection con;
	private boolean init_flag;
	private String username;

	@SuppressWarnings({ "rawtypes" })
	public Admin_manager(String username) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.init_flag = true;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				set_relative_to_null();
			}
		});
		setTitle("ODKAS - Time &  Money");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Admin_manager.class.getResource("/TimeNMoney/odkas.tnm.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 852, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		{
			lblMenuManagers = new JLabel("Menu Managers");
			lblMenuManagers.setBounds(62, 11, 764, 50);
			lblMenuManagers.setIcon(new ImageIcon(Admin_manager.class.getResource("/TimeNMoney/manager_icon_big.png")));
			lblMenuManagers.setFont(new Font("Tahoma", Font.BOLD, 18));
		}
		{
			panel = new JPanel();
			panel.setBounds(10, 72, 816, 55);
			panel.setBorder(new TitledBorder(null, "Manager", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			{
				manager_combo_box = new JComboBox();
				manager_combo_box.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						combo_change_action();
					}
				});
			}
		}
		{
			panel_1 = new JPanel();
			panel_1.setBounds(10, 133, 816, 400);
			panel_1.setBorder(new TitledBorder(null, "Funcion\u00E1rios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			{
				panel_2 = new JPanel();
				panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Todos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				panel_2.setLayout(new BorderLayout(0, 0));
				{
					todos_func_lista = new JList();
					todos_func_lista.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
					panel_2.add(todos_func_lista, BorderLayout.CENTER);
				}
			}
			{
				panel_3 = new JPanel();
				panel_3.setBorder(new TitledBorder(null, "Geridos por:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_3.setLayout(new BorderLayout(0, 0));
				{
					geridos_func_lista = new JList();
					geridos_func_lista.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
					panel_3.add(geridos_func_lista, BorderLayout.CENTER);
				}
			}
			goto_manager = new JButton(">");
			goto_manager.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					to_geridos_action();
				}
			});
			goto_manager.setFont(new Font("Tahoma", Font.BOLD, 11));
			left_manager = new JButton("<");
			left_manager.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					to_todos_action();
				}
			});
			left_manager.setFont(new Font("Tahoma", Font.BOLD, 11));
			GroupLayout gl_panel_1 = new GroupLayout(panel_1);
			gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(4)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addComponent(goto_manager, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
							.addComponent(left_manager, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
						.addGap(4))
			);
			gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(7)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
						.addGap(4))
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(150)
						.addComponent(goto_manager)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(left_manager)
						.addGap(150))
			);
			panel_1.setLayout(gl_panel_1);
		}
		{
			cancel_btn = new JButton("Cancelar");
			cancel_btn.setBounds(697, 536, 129, 39);
			cancel_btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cancel_button_action();
				}
			});
			cancel_btn.setIcon(new ImageIcon(Admin_manager.class.getResource("/TimeNMoney/Cancel-icon.png")));
		}
		{
			save_btn = new JButton("Guardar");
			save_btn.setBounds(562, 536, 129, 39);
			save_btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					save_button_action();
				}
			});
			save_btn.setIcon(new ImageIcon(Admin_manager.class.getResource("/TimeNMoney/saveicon.png")));
		}
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(manager_combo_box, 0, 784, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(manager_combo_box, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(null);
		contentPane.add(lblMenuManagers);
		contentPane.add(panel);
		contentPane.add(panel_1);
		contentPane.add(save_btn);
		contentPane.add(cancel_btn);
		init(username);
		this.init_flag = false;
		this.setCursor(Cursor.getDefaultCursor());
	}
	
	private void init(String username){
		this.username = username;
		this.con = (new Connection_bd(this.username)).get_connection();
		set_lista_funcionarios(this.con);
		set_combo_funcionarios();
		String username_activo = get_user_combo();
		set_lista_geridos_init(this.con);
		preenche_lista_funcionarios_nao_geridos(username_activo);
		preenche_lista_funcionarios_geridos(username_activo);
	}
	
	private void set_relative_to_null(){
		this.setLocationRelativeTo(null);
	}
	
	private void cancel_button_action(){
		this.dispose();
	}
	
	private void save_button_action(){
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int res = 0;
		for (String manager : this.lista_funcionarios.keySet())
		{
			TreeMap<String,Funcionario> users_bd = get_lista_geridos_ind(con, manager);
			TreeMap<String,Funcionario> users_prog = this.lista_geridos_total.get(manager);
			
			ArrayList<String> funcionarios_a_eliminar = get_funcionarios_a_eliminar(users_bd,users_prog);
			ArrayList<String> funcionarios_a_adicionar = get_funcionarios_a_adicionar(users_bd,users_prog);
			res += delete_funcionarios(manager,funcionarios_a_eliminar);
			res += adicionar_funcionarios(manager,funcionarios_a_adicionar);
		}
		if (res==0)
		{
			JOptionPane.showMessageDialog(null, "Guardado com sucesso!");
			this.dispose();
		}
		else
			JOptionPane.showMessageDialog(null, "Ocorrência de erro! Contacte responsável!");
		this.setCursor(Cursor.getDefaultCursor());
	}
	
	private int delete_funcionarios(String manager,ArrayList<String> lista){
		String sql = "delete from tnm_manager where manager = ? and user_funcionario = ?";
		try{
			PreparedStatement ps = this.con.prepareStatement(sql);
			for (String user_funcionario : lista){
				ps.setString(1, manager);
				ps.setString(2, user_funcionario);
				ps.executeUpdate();
			}
			ps.close();
			return 0;
		}
		catch(Exception e){
			e.printStackTrace();
			this.setCursor(Cursor.getDefaultCursor());
			new Log_erros_class().write_log_to_file(this.username,e);
			return 1;
		}
	}
	
	private int adicionar_funcionarios(String manager,ArrayList<String> lista){
		String sql = "insert into tnm_manager (manager,user_funcionario) values (?,?)";
		try{
			PreparedStatement ps = this.con.prepareStatement(sql);
			for (String user_funcionario : lista){
				ps.setString(1, manager);
				ps.setString(2, user_funcionario);
				ps.executeUpdate();
			}
			ps.close();
			return 0;
		}
		catch(Exception e){
			e.printStackTrace();
			this.setCursor(Cursor.getDefaultCursor());
			new Log_erros_class().write_log_to_file(this.username,e);
			return 1;
		}
	}
	
	private ArrayList<String> get_funcionarios_a_eliminar(TreeMap<String,Funcionario> users_bd,TreeMap<String,Funcionario> users_prog){
		ArrayList<String> lista = new ArrayList<String>();
		for (String user : users_bd.keySet())
			if (!users_prog.containsKey(user))
				lista.add(user);
		return lista;
	}
	
	private ArrayList<String> get_funcionarios_a_adicionar(TreeMap<String,Funcionario> users_bd,TreeMap<String,Funcionario> users_prog){
		ArrayList<String> lista = new ArrayList<String>();
		for (String user : users_prog.keySet())
			if (!users_bd.containsKey(user))
				lista.add(user);
		return lista;
	}
	
	private void to_geridos_action(){
		String user_activo = get_user_combo();
		int[] lista_select = todos_func_lista.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
            for (int i : lista_select){
            	String obj = (String)todos_func_lista.getModel().getElementAt(i);
            	String user = obj.split("\\(")[1].replace(")", "").trim();
            	this.lista_geridos_total.get(user_activo).put(user, this.lista_funcionarios.get(user));
            }  
            preenche_lista_funcionarios_nao_geridos(user_activo);
			preenche_lista_funcionarios_geridos(user_activo);
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum funcionário!");
	}
	
	private void to_todos_action(){
		String user_activo = get_user_combo();
		int[] lista_select = geridos_func_lista.getSelectedIndices();
        int tam = lista_select.length;
        if (tam>0){
        	for (int i : lista_select){
            	String obj = (String)geridos_func_lista.getModel().getElementAt(i);
            	String user = obj.split("\\(")[1].replace(")", "").trim();
            	this.lista_geridos_total.get(user_activo).remove(user);
            }  
            preenche_lista_funcionarios_nao_geridos(user_activo);
			preenche_lista_funcionarios_geridos(user_activo);
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhum funcionário!");
	}
	
	@SuppressWarnings("unchecked")
	private void set_combo_funcionarios(){
		for (Funcionario f : this.lista_funcionarios.values())
			manager_combo_box.addItem(f.get_nome() + " ( " + f.get_username() + " )");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void preenche_lista_funcionarios_nao_geridos(String user_activo){
		DefaultListModel lm_todos = new DefaultListModel();
		for (Funcionario f : this.lista_funcionarios.values())
			if ((!this.lista_geridos_total.get(user_activo).containsKey(f.get_username())) && (!f.get_username().equals(user_activo)))
				lm_todos.addElement(f.get_nome() + " ( " + f.get_username() + " )");
		todos_func_lista.setModel(lm_todos);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void preenche_lista_funcionarios_geridos(String user_activo){
		DefaultListModel lm_geridos = new DefaultListModel();
		for (Funcionario f : this.lista_geridos_total.get(user_activo).values())
			if (!f.get_username().equals(user_activo))
				lm_geridos.addElement(f.get_nome() + " ( " + f.get_username() + " )");
		geridos_func_lista.setModel(lm_geridos);
	}
	
	private String get_user_combo(){
		String res = "";
		String aux = (String)manager_combo_box.getSelectedItem();
		res = aux.split("\\(")[1].replace(")", "").trim();
		return res;
	}
	
	private void set_lista_funcionarios(Connection con){
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
		      this.setCursor(Cursor.getDefaultCursor());
		      new Log_erros_class().write_log_to_file(this.username,e);
		  }
    }
	
	private void set_lista_geridos_init(Connection con){
		this.lista_geridos_total = new TreeMap<String, TreeMap<String,Funcionario>>();
		for (String user : this.lista_funcionarios.keySet())
			this.lista_geridos_total.put(user,get_lista_geridos_ind(con, user));
	}
	
	private TreeMap<String, Funcionario> get_lista_geridos_ind(Connection con,String manager){
		TreeMap<String, Funcionario> lista = new TreeMap<String, Funcionario>();
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
			
			sql = "select * from tnm_manager where manager = ?" ;
			ps = con.prepareStatement(sql);
			ps.setString(1, manager);
			rs = ps.executeQuery();
			while (rs.next()){
		          String username = rs.getString("user_funcionario");
				  if (!inativos.contains(username)){
					  Funcionario f = this.lista_funcionarios.get(username);
					  lista.put(username, f);
				  }
		  	}  
		  	rs.close();
		  	ps.close();
		  }
		  catch(SQLException e)
		  {
		      e.printStackTrace();
		      this.setCursor(Cursor.getDefaultCursor());
		      new Log_erros_class().write_log_to_file(this.username,e);
		  }
		return lista;
	}
	
	private void combo_change_action(){
		if (!init_flag){
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			String username_activo = get_user_combo();
			preenche_lista_funcionarios_nao_geridos(username_activo);
			preenche_lista_funcionarios_geridos(username_activo);
			this.setCursor(Cursor.getDefaultCursor());
		}
	}
}
