package TimeNMoney;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JMenu;

@SuppressWarnings("serial")
public class Menu_inicial_utilizador extends javax.swing.JFrame {
    Funcionario funcionario;
    boolean autolog;
    boolean savelog;
    Data_manager dm;
    ScheduledExecutorService executor_dm;
    ScheduledExecutorService executor_estados;
    ScheduledExecutorService executor_log;
    ScheduledExecutorService executor_handlers;
    ScheduledExecutorService executor_listas;
    int contador_thread;
    
    /**
     * @wbp.parser.constructor
     */
    public Menu_inicial_utilizador(Funcionario f, boolean auto_log,boolean save_log) {
        this.autolog = auto_log;
        this.savelog = save_log;
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.funcionario = f;
        username_label.setText(f.get_username());
        set_admin_botao();
        //this.dm = new Data_manager(this.funcionario.get_username());
        set_dm();
        iniciar_thread_save_dm();
        iniciar_thread_estado_con();
        iniciar_thread_confirma_log();
        iniciar_thread_update_handleres();
        iniciar_thread_update_app();
        iniciar_thread_update_listas_dados();
        set_menu_manager_visible();
        this.contador_thread = 0;
        set_foto_panel();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                JFrame frame = (JFrame)e.getSource();
                //save_if_not_saved();
                save_dm();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
            }
        });
    }
    
    private void set_menu_manager_visible(){
		try{
			menu_manager.setVisible(this.dm.funcionario_geridos_por_mim.size()>0);
		}
    	catch(Exception e){
    		menu_manager.setVisible(false);
    		e.printStackTrace();
    		this.setCursor(Cursor.getDefaultCursor());
    		new Log_erros_class().write_log_to_file(e);
    	}
    }
    
    private void iniciar_thread_save_dm(){
    	Runnable runnable_save_dm = new Runnable() {
    	    public void run() {
	    		save_dm_thread();
    	    }
    	};
    	this.executor_dm = Executors.newScheduledThreadPool(1);
    	executor_dm.scheduleAtFixedRate(runnable_save_dm, 0, 5, TimeUnit.MINUTES);
    }
    
    //update handlers tarefas + despesas
    private void iniciar_thread_update_handleres(){
    	Runnable runnable_update_handlers = new Runnable() {
    	    public void run() {
	    		update_handlers();
    	    }
    	};
    	this.executor_handlers = Executors.newScheduledThreadPool(1);
    	executor_handlers.scheduleAtFixedRate(runnable_update_handlers, 0, 5, TimeUnit.MINUTES);
    }
    
    private void update_handlers(){
    	String username = this.funcionario.get_username();
    	Connection con = (new Connection_bd()).get_connection();
    	if (con != null){
    		Backup_data_manager bdm = new Backup_data_manager(this.dm);
    		bdm.save_backup_file();
    		
    		int res = this.dm.get_aprov_or_not_task_list2(username,con);
    		res += this.dm.get_estado_despesas2(username,con);
    		if (res==0)
    			bdm.delete_backup();
    		else
    			this.dm = bdm.get_file_backup();
        }
    }
    
    //update listas de dados -> projectos, etapas, atividades, ...
    private void iniciar_thread_update_listas_dados(){
    	Runnable runnable_update_listas = new Runnable() {
    	    public void run() {
	    		update_listas();
    	    }
    	};
    	this.executor_listas = Executors.newScheduledThreadPool(1);
    	executor_listas.scheduleAtFixedRate(runnable_update_listas, 0, 5, TimeUnit.MINUTES);
    }
    
    private void update_listas(){
    	String username = this.funcionario.get_username();
    	Connection con = (new Connection_bd()).get_connection();
    	if (con != null){
    		Backup_data_manager bdm = new Backup_data_manager(this.dm);
    		bdm.save_backup_file();
    		
    		int res = this.dm.get_list_projectos_from_bd2(username,con);
    		res += this.dm.get_list_tipos_despesa2(con);
    		res += this.dm.get_taxas_moeda2(con);
    		if (res==0)
    			bdm.delete_backup();
    		else
    			this.dm = bdm.get_file_backup();
        }
    }
    
    private void save_dm_thread(){
    	this.dm.save_file();
    	set_menu_manager_visible();
    }
    
    public int send_partial_auto_action(){
    	int res = 0;
        try {
	    	Connection con = (new Connection_bd()).get_connection();
	    	res += send_nova_tarefa_proj(con);
	    	con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			this.setCursor(Cursor.getDefaultCursor());
			new Log_erros_class().write_log_to_file(e);
        	res++;
		}
        return res;
    }
    
    private int send_nova_tarefa_proj(Connection con){
        int res = 0;
        try{
	        for (String proj : this.dm.projectos_alterados)
	        {
	            Projecto p = this.dm.lista_projectos_user.get(proj);
	            res += send_proj_bd(p,con);
	        }
	        if (res==0)
	        	this.dm.projectos_alterados = new ArrayList<String>();
	        
	        for (String tar : this.dm.tarefas_adicionadas)
	        {
	            Tarefa t = this.dm.lista_tarefa_total.get(tar);
	            if (t!=null)
	            	res+= send_tarefa_bd(t,con);
	        }
	        
	        if (res==0)
	        	this.dm.tarefas_adicionadas = new ArrayList<String>();
        }
        catch(Exception e){
        	e.printStackTrace();
        	this.setCursor(Cursor.getDefaultCursor());
        	new Log_erros_class().write_log_to_file(e);
        	res++;
        }
        return res;
    }
    
    private int send_tarefa_bd(Tarefa t,Connection con){
        int res = 0;
        try{
        String sql;
        PreparedStatement ps;
        sql = "select * from tnm_trf_tarefa where id = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1, t.get_id());
        ResultSet rs = ps.executeQuery();
        if (!rs.next())
        {
	        sql="insert into tnm_trf_tarefa (id,nome,descricao) values (?,?,?)";
	        ps=con.prepareStatement(sql);
	        ps.setString(1, t.get_id());
	        ps.setString(2, t.get_nome());
	        ps.setString(3, t.get_descricao());
	        ps.executeUpdate();
	        add_user_tarefa_bd(t.get_id(),this.funcionario.get_username(),con);
        }
        rs.close();
        ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            res++;
        }

        return res;
    }
    
    private void add_user_tarefa_bd(String id, String username, Connection con) {
		try{
    	String sql = "insert into tnm_user_tarefa (id_tarefa,username) values (?,?)";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, username);
        ps.executeUpdate();
        ps.close();
		}
		catch(SQLException e){
			e.printStackTrace();
			this.setCursor(Cursor.getDefaultCursor());
			new Log_erros_class().write_log_to_file(e);
		}
	}
    
    private int send_proj_bd(Projecto p,Connection con){
        int res = 0;
        try{
        String sql;
        PreparedStatement ps;
        sql="select * from tnm_trf_projecto where id_projecto = ?";
        ps=con.prepareStatement(sql);
        ps.setString(1, p.get_codigo());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            ByteArrayInputStream bais;
            ObjectInputStream ois;
            try{
                bais = new ByteArrayInputStream(rs.getBytes("PROJECTO"));
                ois = new ObjectInputStream(bais);
                Projecto aux = (Projecto)ois.readObject();
                aux = add_tarefa_new_proj(aux, p);
                sql="update tnm_trf_projecto set projecto = ? where id_projecto = ?";
                ps=con.prepareStatement(sql);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(aux);
                oos.flush();
                oos.close();
                bos.close();
                byte[] data = bos.toByteArray();
                ps.setObject(1, data);
                ps.setString(2, p.get_codigo());
                ps.executeUpdate();
                ps.close();
            }
            catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(e);
                res++;
            }  
        }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
            res++;
        }

        return res;
    }
    
    private Projecto add_tarefa_new_proj(Projecto bd, Projecto novo){
        Projecto retorno;
        for (String s : novo.get_tarefas().keySet())
            if (!bd.get_tarefas().containsKey(s))
                bd.get_tarefas().put(s,novo.get_tarefas().get(s));
        retorno = bd;
        return retorno;
    }
    
//    private boolean get_continue_flag(){
//    	System.out.println(this.isVisible());
//    	if (this.isVisible())
//    		return true;
////    	if (this.contador_thread>0)
////    		this.executor.shutdown();
////    	this.contador_thread++;
//    	return false;
//    }
    
    private void save_dm(){
        this.dm.save_file();
    }
    
    private void set_dm(){
        Data_manager aux = read_file();
        if (aux == null){
            aux = new Data_manager(this.funcionario.get_username());
        }
        this.dm = aux;
    }
    
    private Data_manager read_file(){
        try{
//            String path = new File("").getAbsolutePath();
//            String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        	String defaultFolder = System.getenv("APPDATA");
            String name_file = defaultFolder + "\\TimeNMoney\\data\\" + this.funcionario.get_username() + ".sav";
            FileInputStream saveFile = new FileInputStream(name_file);
            
            Data_manager aux;
            try (ObjectInputStream save = new ObjectInputStream(saveFile)) {
                aux = (Data_manager) save.readObject();
            } 
            return aux;
        }
        catch(IOException | ClassNotFoundException e){
            return null;
        }
    }
    
    private void set_admin_botao(){
        admin_menu.setVisible(this.funcionario.get_admin());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_foto = new javax.swing.JLabel();
        username_label = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        botao_horas = new javax.swing.JButton();
        botao_despesas = new javax.swing.JButton();
        botao_viagens = new javax.swing.JButton();
        botao_perfil = new javax.swing.JButton();
        botao_exit = new javax.swing.JButton();
        botao_logoff = new javax.swing.JButton();
        load_data_button = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        admin_menu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        label_foto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_foto.setIcon(new ImageIcon(Menu_inicial_utilizador.class.getResource("/TimeNMoney/no_image_icon.png"))); // NOI18N
        label_foto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        username_label.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        username_label.setText("username");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/logo-timemoney2.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Painel"));

        botao_horas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_horas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/task_icon.png"))); // NOI18N
        botao_horas.setText("Consultar/Adicionar Detalhes Carga Horária");
        botao_horas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_horasActionPerformed(evt);
            }
        });

        botao_despesas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_despesas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/expense_icon.png"))); // NOI18N
        botao_despesas.setText("Consultar/Adicionar Despesas");
        botao_despesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_despesasActionPerformed(evt);
            }
        });

        botao_viagens.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_viagens.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/travel-icon.png"))); // NOI18N
        botao_viagens.setText("Consultar/Adicionar Viagem");
        botao_viagens.setEnabled(false);
        botao_viagens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_viagensActionPerformed(evt);
            }
        });

        botao_perfil.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_perfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/profile_icon.png"))); // NOI18N
        botao_perfil.setText("Alterar Perfil");
        botao_perfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_perfilActionPerformed(evt);
            }
        });

        botao_exit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/close_app.png"))); // NOI18N
        botao_exit.setText("Fechar aplicação");
        botao_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_exitActionPerformed(evt);
            }
        });

        botao_logoff.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        botao_logoff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/logout_icon.png"))); // NOI18N
        botao_logoff.setText("Logout");
        botao_logoff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_logoffActionPerformed(evt);
            }
        });

        load_data_button.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        load_data_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/receive_icon.png"))); // NOI18N
        load_data_button.setText("Actualizar Dados");
        load_data_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                load_data_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(botao_horas, GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
        				.addComponent(botao_despesas, GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
        				.addComponent(botao_viagens, GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(botao_perfil, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        						.addComponent(botao_logoff, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
        					.addGap(18)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(load_data_button, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        						.addComponent(botao_exit, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))))
        			.addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(10)
        			.addComponent(botao_horas, GroupLayout.PREFERRED_SIZE, 67, Short.MAX_VALUE)
        			.addGap(10)
        			.addComponent(botao_despesas, GroupLayout.PREFERRED_SIZE, 67, Short.MAX_VALUE)
        			.addGap(10)
        			.addComponent(botao_viagens, GroupLayout.PREFERRED_SIZE, 67, Short.MAX_VALUE)
        			.addGap(10)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(botao_perfil, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(load_data_button, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addGap(10)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(botao_logoff, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(botao_exit, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addGap(25))
        );
        jPanel1.setLayout(jPanel1Layout);

        admin_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/admin-icon-mini.png"))); // NOI18N
        admin_menu.setText("Administrador");
        admin_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admin_menuActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Ir para Menu de Administrador");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        admin_menu.add(jMenuItem1);

        jMenuBar1.add(admin_menu);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/export_excel_mini.png"))); // NOI18N
        jMenu1.setText("Importar/Exportar");

        jMenuItem2.setText("Exportar excel exemplo");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);
        
        mntmSobre = new JMenuItem("Sobre");
        mntmSobre.setIcon(new ImageIcon(Menu_inicial_utilizador.class.getResource("/TimeNMoney/about_icon.png")));
        mntmSobre.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String ver = (new Version_class()).get_version();
        		new About(ver).setVisible(true);
        	}
        });
        
        menu_manager = new JMenu("Manager");
        menu_manager.setIcon(new ImageIcon(Menu_inicial_utilizador.class.getResource("/TimeNMoney/manager_icon_small.png")));
        jMenuBar1.add(menu_manager);
        
        mntmRevisoHoras = new JMenuItem("Revisão Horas");
        mntmRevisoHoras.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		goto_revisao_horas();
        	}
        });
        mntmRevisoHoras.setIcon(new ImageIcon(Menu_inicial_utilizador.class.getResource("/TimeNMoney/task_review_icon.png")));
        menu_manager.add(mntmRevisoHoras);
        
        mntmRevisoDespesas = new JMenuItem("Revisão Despesas");
        mntmRevisoDespesas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		goto_revisao_despesas();
        	}
        });
        mntmRevisoDespesas.setIcon(new ImageIcon(Menu_inicial_utilizador.class.getResource("/TimeNMoney/expense_review_icon.png")));
        menu_manager.add(mntmRevisoDespesas);
        
        mnUpdate = new JMenu("Update");
        mnUpdate.setIcon(new ImageIcon(Menu_inicial_utilizador.class.getResource("/TimeNMoney/update_icon.png")));
        jMenuBar1.add(mnUpdate);
        
        menu_update = new JMenuItem("Update da aplicação");
        menu_update.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		update_action();
        	}
        });
        mnUpdate.add(menu_update);
        jMenuBar1.add(mntmSobre);
        
        panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
        					.addContainerGap())
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(label_foto, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
        					.addGap(52)
        					.addComponent(username_label, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
        					.addComponent(jLabel3)
        					.addGap(51))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
        					.addContainerGap())))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jLabel3)
        				.addComponent(username_label, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
        				.addComponent(label_foto, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
        			.addGap(10)
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        panel.setLayout(null);
        
        bd_con_light = new JLabel("");
        bd_con_light.setIcon(new ImageIcon(Menu_inicial_utilizador.class.getResource("/TimeNMoney/green_light_icon.png")));
        bd_con_light.setBounds(248, 11, 18, 14);
        panel.add(bd_con_light);
        
        JLabel lblAcessoBaseDe = new JLabel("Acesso base de dados:");
        lblAcessoBaseDe.setBounds(61, 11, 177, 14);
        panel.add(lblAcessoBaseDe);
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botao_horasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_horasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Menu_tarefas_horas mth = new Menu_tarefas_horas(this.funcionario.get_username(),this.dm);
        mth.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) {}
                @Override
                public void windowClosed(WindowEvent we) {
                    set_visible(true);
                }
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });        
        mth.setVisible(true);
        set_visible(false);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_horasActionPerformed

    private void set_visible(boolean b){
        this.setVisible(b);
        if (!b)
        {
            if (!this.executor_dm.isShutdown())
            	this.executor_dm.shutdown();
            if (!this.executor_estados.isShutdown())
            	this.executor_estados.shutdown();
            if (!this.executor_log.isShutdown())
            	this.executor_log.shutdown();
        }
        else
        {
        	iniciar_thread_save_dm();
        	iniciar_thread_estado_con();
            iniciar_thread_confirma_log();
        }
    }
    
    private void botao_despesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_despesasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Menu_despesas md = new Menu_despesas(this.funcionario.get_username(),this.dm);
        md.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {
                    set_visible(true);
                }
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        }); 
        md.setVisible(true);
        //this.dispose();
        set_visible(false);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_despesasActionPerformed

    private void botao_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_exitActionPerformed
    	this.dm.save_file();
        if (!this.executor_dm.isShutdown())
        	this.executor_dm.shutdown();
        if (!this.executor_estados.isShutdown())
        	this.executor_estados.shutdown();
        if (!this.executor_log.isShutdown())
        	this.executor_log.shutdown();
        System.exit(0);
    }//GEN-LAST:event_botao_exitActionPerformed

    private void botao_logoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_logoffActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.dm.save_file();
        this.dispose();
        if (!this.executor_dm.isShutdown())
        	this.executor_dm.shutdown();
        if (!this.executor_estados.isShutdown())
        	this.executor_estados.shutdown();
        if (!this.executor_log.isShutdown())
        	this.executor_log.shutdown();
        if (this.autolog || this.savelog)
            new Menu_log_in(this.funcionario,this.autolog,this.savelog).setVisible(true);
        else 
            new Menu_log_in().setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_logoffActionPerformed

    private void admin_click(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.dm.save_file();
        Menu_administrador ma = new Menu_administrador(this.funcionario);
        ma.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {
                    set_visible(true);
                }
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        }); 
        ma.setVisible(true);
        //this.dispose();
        set_visible(false);
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void botao_viagensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_viagensActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Adicionar_consultar_viagem av = new Adicionar_consultar_viagem(funcionario);
        av.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) {}

                @Override
                public void windowClosing(WindowEvent we) {}

                @Override
                public void windowClosed(WindowEvent we) {  
                }
                
                @Override
                public void windowIconified(WindowEvent we) {}

                @Override
                public void windowDeiconified(WindowEvent we) {}

                @Override
                public void windowActivated(WindowEvent we) {}

                @Override
                public void windowDeactivated(WindowEvent we) {}
        });
        av.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_viagensActionPerformed

    private void botao_perfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_perfilActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Alterar_perfil ap = new Alterar_perfil(this.funcionario,this.autolog,this.savelog);
        ap.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) {}

                @Override
                public void windowClosing(WindowEvent we) {
                	
                }

                @Override
                public void windowClosed(WindowEvent we) {
                	
                }
                
                @Override
                public void windowIconified(WindowEvent we) {}

                @Override
                public void windowDeiconified(WindowEvent we) {}

                @Override
                public void windowActivated(WindowEvent we) {}

                @Override
                public void windowDeactivated(WindowEvent we) {
                	Funcionario f = ap.get_funcionario_return();
                	actualiza_funcionario_foto(f);
                }
        });
        ap.setVisible(true);
        this.dispose();
        if (!this.executor_dm.isShutdown())
        	this.executor_dm.shutdown();
        if (!this.executor_estados.isShutdown())
        	this.executor_estados.shutdown();
        if (!this.executor_log.isShutdown())
        	this.executor_log.shutdown();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_perfilActionPerformed
    
    private void actualiza_funcionario_foto(Funcionario f){
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	this.funcionario = f;
        set_foto_panel();
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void load_data_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_load_data_buttonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        int result = JOptionPane.showConfirmDialog(
                null,
                "Tem que enviar todas as alterações antes de actualizar ou corre o risco de perder todos os dados. Deseja enviar as alterações agora?",
                "Atenção",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (result == JOptionPane.YES_OPTION){
            int res = send_all_data();
            if (res==0){
            	this.dm = new Data_manager(this.funcionario.get_username());
            	JOptionPane.showMessageDialog(null, "Dados enviados e atualizados com sucesso!");
            }
            else{
            	JOptionPane.showMessageDialog(null, "Erro ao enviar os dados!");
            }
        }
        else if (result == JOptionPane.NO_OPTION){
        	this.dm = new Data_manager(this.funcionario.get_username());
        	set_menu_manager_visible();
        	JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_load_data_buttonActionPerformed

    private void admin_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admin_menuActionPerformed
        
    }//GEN-LAST:event_admin_menuActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        admin_click();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        new Export_to_excel(this.funcionario.get_username()).setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    
    private int send_all_data(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int res;
        //fazer backup do datamanager antes de enviar tarefas
        Backup_data_manager bdm = new Backup_data_manager(this.dm);
        bdm.save_backup_file();
        res = (new Menu_tarefas_horas(this.funcionario.get_username(),this.dm)).save_button_action(false);
        res += (new Menu_despesas(this.funcionario.get_username(),this.dm)).save_despesas(false);
        res += (new Menu_preferencias_favoritos(this.funcionario.get_username(), this.dm)).send_fav(false);
        
        if (res == 0) //se envio ok, eliminar backup
        	bdm.delete_backup();
        else //se der erro, repor backup
        	this.dm = bdm.get_file_backup();
        
        this.setCursor(Cursor.getDefaultCursor());
        return res;
    }
    
    private void iniciar_thread_estado_con(){
    	Runnable runnable_estados = new Runnable() {
    	    public void run() {
    	    	try{
	    	    	Connection con = (new Connection_bd()).get_connection();
	    	        boolean con_ready = false;
					if (con!=null)
						con_ready = con.isValid(300);
					change_light_bd(con_ready);
    	    	}
    	    	catch(Exception e){
    	    		e.printStackTrace();
    	    		new Log_erros_class().write_log_to_file(e);
    	    		change_light_bd(false);
    	    	}
    	    }
    	};
    	this.executor_estados = Executors.newScheduledThreadPool(1);
    	this.executor_estados.scheduleAtFixedRate(runnable_estados, 0, 60, TimeUnit.SECONDS);
    }
    
    private void iniciar_thread_confirma_log(){
    	Runnable runnable_log = new Runnable() {
    	    public void run() {
	    		try{
	    	    	Connection con = (new Connection_bd()).get_connection();
	    	        confirma_log(con);
    	    	}
    	    	catch(Exception e){
    	    		e.printStackTrace();
    	    		new Log_erros_class().write_log_to_file(e);
    	    	}
    	    }
    	};
    	this.executor_log = Executors.newScheduledThreadPool(1);
    	this.executor_log.scheduleAtFixedRate(runnable_log, 0, 1, TimeUnit.MINUTES);
    }
    
    private void confirma_log(Connection con){
    	String sql = "select * from tnm_users_inativos where username = ?";
    	try {
    		PreparedStatement ps = con.prepareStatement(sql);
    		ps.setString(1, this.funcionario.get_username());
    		ResultSet rs = ps.executeQuery();
    		if (rs.next())
    			delete_log_file();
    		rs.close();
    		ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
    		this.setCursor(Cursor.getDefaultCursor());
    		new Log_erros_class().write_log_to_file(e);
		}
    	
    	
    }
    
    private void delete_log_file(){
//    	String path = new File("").getAbsolutePath();
//    	String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    	String defaultFolder = System.getenv("APPDATA");
        String name_file = defaultFolder + "\\TimeNMoney\\data\\log_file.sav";
        File saveFile = new File(name_file);
        saveFile.delete();
    }
    
    private void change_light_bd(boolean b)
    {
    	if (b)
    		bd_con_light.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/green_light_icon.png")));
    	else
    		bd_con_light.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/red_light_icon.png")));
    	
    }
    
    private TreeMap<String,Projecto> get_lista_projectos(Connection con){
    	TreeMap<String,Projecto> lista_projectos = new TreeMap<>();
    	try{
        String sql;
        PreparedStatement ps;
        ResultSet rs;
        //projectos
        sql = "select * from tnm_trf_projecto" ;
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()){
            ByteArrayInputStream bais;
            ObjectInputStream ois;
            try{
                String codigo = rs.getString("ID_PROJECTO");
                bais = new ByteArrayInputStream(rs.getBytes("PROJECTO"));
                ois = new ObjectInputStream(bais);
                Projecto p = (Projecto)ois.readObject();
                lista_projectos.put(codigo, p);
            }
            catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(e);
            }
        }  
        rs.close();
        ps.close();
//        con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
        }
    	return lista_projectos;
    }
    
    private void goto_revisao_horas(){
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	Connection con = (new Connection_bd()).get_connection();
        TreeMap<String, Projecto> lista_projectos = get_lista_projectos(con);
		Menu_aprova_tarefas_horas mah = new Menu_aprova_tarefas_horas(this.funcionario.get_username(),lista_projectos,con,this.dm.funcionario_geridos_por_mim);
        mah.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void goto_revisao_despesas(){
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	Connection con = (new Connection_bd()).get_connection();
        TreeMap<String, Projecto> lista_projectos = get_lista_projectos(con);
        Aprovar_despesas ad = new Aprovar_despesas(this.funcionario.get_username(),lista_projectos,con,this.dm.funcionario_geridos_por_mim);
        ad.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void set_foto_panel(){
    	if (funcionario.get_foto()!=null){
        	BufferedImage foto = arrayToImage(funcionario.get_foto());
        	ImageIcon icon = new ImageIcon(resize(foto,100,100));
        	label_foto.setIcon(icon);
        }
        else{
        	label_foto.setIcon(new ImageIcon(Menu_inicial_utilizador.class.getResource("/TimeNMoney/no_image_icon.png")));
        }
    }
    
    private BufferedImage arrayToImage(byte[] foto){
    	ByteArrayInputStream bais = new ByteArrayInputStream(foto);
    	try {
			BufferedImage image = ImageIO.read(bais);
			return image;
		} 
    	catch (IOException e) {
			e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
			return null;
		}
    }
    
    private static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
    
    private void update_action(){
    	//last versao bd
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	String versao = (new Version_class()).get_version();
    	String versao_bd = get_last_version_bd();
    	boolean update_need = confirm_need_update(versao,versao_bd);
    	if (update_need){
    		update_application(versao_bd);
    	}
    	else{
    		JOptionPane.showMessageDialog(null, "Aplicação já se encontra na última versão disponível!");
    	}
    	this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void update_app_action_thread(){
    	//last versao bd
    	String versao = (new Version_class()).get_version();
    	String versao_bd = get_last_version_bd();
    	boolean update_need = confirm_need_update(versao,versao_bd);
    	if (update_need)
    		update_application(versao_bd);
    }
    
    private void iniciar_thread_update_app(){
    	Runnable runnable_update = new Runnable() {
    	    public void run() {
    	    	update_app_action_thread();
    	    }
    	};
    	runnable_update.run();
    }
    
    private boolean confirm_need_update(String v1, String v2){
    	int i = compare_string_version(v1, v2);
    	return (i<0);
    }
    
    private void update_application(String version){
    	UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
    	int res = JOptionPane.showConfirmDialog(null, "Aplicação necessita de atualizações. Deve enviar todos os seus dados antes de proceder à atualização. Deseja atualizar agora?"
    			,"Update aplicação",JOptionPane.YES_NO_OPTION);
    	if (res == JOptionPane.YES_OPTION){
    		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    		File instalador = get_file_from_bd(version);
    		if (instalador != null)
    			install_or_not(instalador);
    		else
    			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao descarregar instalador. Tente mais tarde ou contacte responsável!");
    		this.setCursor(Cursor.getDefaultCursor());
    	}
    }
    
    private void install_or_not(File instalador){
    	UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
    	int res = JOptionPane.showConfirmDialog(null, "Instalador de atualização descarregado na pasta 'Instalador'. Deseja iniciar atualização agora?"
    			,"Update aplicação",JOptionPane.YES_NO_OPTION);
    	if (res == JOptionPane.YES_OPTION){
    		try {
    			Runtime.getRuntime().exec(instalador.getAbsolutePath());
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
	            this.setCursor(Cursor.getDefaultCursor());
	            new Log_erros_class().write_log_to_file(e);
			}
    	}
    }
    
    private File get_file_from_bd(String version){
    	FileOutputStream fos = null;
    	File f = null;
    	try{
    		Connection con = (new Connection_bd()).get_connection();
    		String sql = "select * from tnm_update_table where versao = ?";
    		String name = "";
    		String path = "";
    		PreparedStatement ps = con.prepareStatement(sql);
    		ps.setString(1, version);
    		ResultSet rs = ps.executeQuery();
    		while (rs.next()){
    			name = rs.getString("nome_ficheiro");
    			byte[] inst = rs.getBytes("ficheiro");
    			File s_file = new File("Instalador");
                s_file.mkdirs();
                path = s_file.getAbsolutePath();
    			fos = new FileOutputStream(path + "\\" + name);
    			fos.write(inst);
    			fos.close();
    		}
    		rs.close();
    		ps.close();
    		con.close();
    		f = new File(path + "\\" + name);
    	}
    	catch(Exception e){
    		e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
    	}
    	return f;
    }
    
    private String get_last_version_bd(){
    	String big_ver = "0.0.0.0";
    	try{
    	Connection con = (new Connection_bd()).get_connection();
    	String sql = "select versao from tnm_update_table";
    	PreparedStatement ps = con.prepareStatement(sql);
    	ResultSet rs = ps.executeQuery();
    	while(rs.next()){
    		String aux = rs.getString("versao");
    		if (compare_string_version(aux, big_ver) > 0)
    			big_ver = aux;
    	}
    	rs.close();
    	ps.close();
    	con.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(e);
    	}
    	return big_ver;
    }
    
    private int compare_string_version(String v1, String v2){
    	String[] v1_dividida = v1.split("\\.");
    	int v1a = Integer.valueOf(v1_dividida[0]);
    	int v1b = Integer.valueOf(v1_dividida[1]);
    	int v1c = Integer.valueOf(v1_dividida[2]);
    	int v1d = Integer.valueOf(v1_dividida[3]);
    	
    	String[] v2_dividida = v2.split("\\.");
    	int v2a = Integer.valueOf(v2_dividida[0]);
    	int v2b = Integer.valueOf(v2_dividida[1]);
    	int v2c = Integer.valueOf(v2_dividida[2]);
    	int v2d = Integer.valueOf(v2_dividida[3]);
    	
    	if (v1a < v2a)
    		return -1;
    	else if (v1a > v2a)
    		return 1;
    	else{
    		if (v1b < v2b)
        		return -1;
        	else if (v1b > v2b)
        		return 1;
        	else{
        		if (v1c < v2c)
            		return -1;
            	else if (v1c > v2c)
            		return 1;
            	else{
            		if (v1d < v2d)
                		return -1;
                	else if (v1d > v2d)
                		return 1;
                	else
                		return 0;
            	}
        	}
    	}
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu admin_menu;
    private javax.swing.JButton botao_despesas;
    private javax.swing.JButton botao_exit;
    private javax.swing.JButton botao_horas;
    private javax.swing.JButton botao_logoff;
    private javax.swing.JButton botao_perfil;
    private javax.swing.JButton botao_viagens;
    private javax.swing.JLabel label_foto;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton load_data_button;
    private javax.swing.JLabel username_label;
    private JMenuItem mntmSobre;
    private JPanel panel;
    private JLabel bd_con_light;
    private JMenu menu_manager;
    private JMenuItem mntmRevisoHoras;
    private JMenuItem mntmRevisoDespesas;
    private JMenu mnUpdate;
    private JMenuItem menu_update;
}
