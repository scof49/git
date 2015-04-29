package TimeNMoney;

import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@SuppressWarnings("serial")
public class Menu_administrador extends javax.swing.JFrame {
    Funcionario user;
    TreeMap<String,Projecto> lista_projectos;
    TreeMap<String,Funcionario> lista_funcionarios_ativos;
    Connection con;
    boolean inicializacao;
    ScheduledExecutorService executor;
    int contador_thread;
    
    public Menu_administrador(Funcionario f) {
        initComponents();
        this.inicializacao = true;
        this.con = (new Connection_bd(this.user.get_username())).get_connection();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.user = f;
        set_lista_projectos();
        set_lista_funcionarios();
        iniciar_thread_update_lista_projectos();
        this.inicializacao = false;
        this.contador_thread = 0;
    }
    
    private void set_lista_funcionarios(){
    	this.lista_funcionarios_ativos = new TreeMap<>();
		try{
			//get users nao ativos
			ArrayList<String> inativos = new ArrayList<String>();
			String sql = "select * from tnm_users_inativos" ;
			PreparedStatement ps;
			ps = this.con.prepareStatement(sql);
			ResultSet rs;
			rs = ps.executeQuery();
			while (rs.next()){
				inativos.add(rs.getString("username"));
			}
			
			sql = "select * from tnm_funcionario" ;
			ps = this.con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()){
		          String username = rs.getString("USERNAME");
				  String nome = rs.getString("NOME");
				  if (!inativos.contains(username)){
					  Funcionario f = new Funcionario();
					  f.set_nome(nome);
					  f.set_username(username);
					  lista_funcionarios_ativos.put(username, f);
				  }
		  	}  
		  	rs.close();
		  	ps.close();
		  }
		  catch(SQLException e)
		  {
		      e.printStackTrace();
		      this.setCursor(Cursor.getDefaultCursor());
		      new Log_erros_class().write_log_to_file(this.user.get_username(),e);
		  }
    }

    private void set_lista_projectos(){
        this.lista_projectos = new TreeMap<>();
    	try{
        //Connection con = (new Connection_bd()).get_connection();
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
                this.lista_projectos.put(codigo, p);
            }
            catch(HeadlessException | IOException | ClassNotFoundException | SQLException e){
                e.printStackTrace();
                this.setCursor(Cursor.getDefaultCursor());
                new Log_erros_class().write_log_to_file(this.user.get_username(),e);
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
            new Log_erros_class().write_log_to_file(this.user.get_username(),e);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        botao_projectos = new javax.swing.JButton();
        botao_etapas = new javax.swing.JButton();
        botao_actividades = new javax.swing.JButton();
        botao_tarefas = new javax.swing.JButton();
        botao_funcionarios = new javax.swing.JButton();
        botao_viagens = new javax.swing.JButton();
        botao_horas = new javax.swing.JButton();
        botao_despesas = new javax.swing.JButton();
        botao_voltar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        botao_clientes = new javax.swing.JButton();
        tipo_despesa_botao = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\Ivo.Oliveira\\Desktop\\logo-timemoney2.png")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Painel"));

        botao_projectos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botao_projectos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_projecto_icon.png"))); // NOI18N
        botao_projectos.setText("Projectos");
        botao_projectos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_projectosActionPerformed(evt);
            }
        });

        botao_etapas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botao_etapas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_etapa_icon.png"))); // NOI18N
        botao_etapas.setText("Etapas");
        botao_etapas.setMaximumSize(new java.awt.Dimension(2083, 125));
        botao_etapas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_etapasActionPerformed(evt);
            }
        });

        botao_actividades.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botao_actividades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_activity_icon.png"))); // NOI18N
        botao_actividades.setText("Actividades");
        botao_actividades.setMaximumSize(new java.awt.Dimension(2083, 125));
        botao_actividades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_actividadesActionPerformed(evt);
            }
        });

        botao_tarefas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botao_tarefas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_task_icon.png"))); // NOI18N
        botao_tarefas.setText("Tarefas");
        botao_tarefas.setMaximumSize(new java.awt.Dimension(2083, 125));
        botao_tarefas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_tarefasActionPerformed(evt);
            }
        });

        botao_funcionarios.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botao_funcionarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_funcionario_icon.png"))); // NOI18N
        botao_funcionarios.setText("Funcionarios");
        botao_funcionarios.setMaximumSize(new java.awt.Dimension(2083, 125));
        botao_funcionarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_funcionariosActionPerformed(evt);
            }
        });

        botao_viagens.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botao_viagens.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_travel-icon.png"))); // NOI18N
        botao_viagens.setText("Viagens ( Revisão )");
        botao_viagens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_viagensActionPerformed(evt);
            }
        });

        botao_horas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botao_horas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/task_review_icon.png"))); // NOI18N
        botao_horas.setText("Horas ( Revisão )");
        botao_horas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_horasActionPerformed(evt);
            }
        });

        botao_despesas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botao_despesas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/expense_review_icon.png"))); // NOI18N
        botao_despesas.setText("Despesas ( Revisão )");
        botao_despesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_despesasActionPerformed(evt);
            }
        });

        botao_voltar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botao_voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_back.png"))); // NOI18N
        botao_voltar.setText("Voltar");
        botao_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_voltarActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_exit.png"))); // NOI18N
        jButton1.setText("Sair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        botao_clientes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        botao_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_customer_item.png"))); // NOI18N
        botao_clientes.setText("Clientes");
        botao_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_clientesActionPerformed(evt);
            }
        });

        tipo_despesa_botao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tipo_despesa_botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_expense_icon.png"))); // NOI18N
        tipo_despesa_botao.setText("Tipos de despesas");
        tipo_despesa_botao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipo_despesa_botaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botao_voltar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(botao_viagens, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botao_etapas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botao_funcionarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(botao_tarefas, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tipo_despesa_botao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botao_projectos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botao_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botao_actividades, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)))
                    .addComponent(botao_despesas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botao_horas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botao_horas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botao_despesas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botao_viagens, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(botao_projectos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botao_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_funcionarios, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botao_actividades, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_etapas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botao_tarefas, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(tipo_despesa_botao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao_voltar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/admin-icon-big.png"))); // NOI18N
        jLabel4.setText("Menu Administrador");

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/money_ex_icon.png"))); // NOI18N
        jMenu1.setText("Câmbio");

        jMenuItem1.setText("Alterar taxas de câmbio");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/report_mini.png"))); // NOI18N
        jMenu4.setText("Relatórios");

        jMenuItem2.setText("Gerar Relatório");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(51, 51, 51))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botao_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_voltarActionPerformed
    	this.dispose();
    }//GEN-LAST:event_botao_voltarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    	System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void botao_despesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_despesasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Aprovar_despesas ad = new Aprovar_despesas(this.user.get_username(),this.lista_projectos,this.con,this.lista_funcionarios_ativos);
        ad.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { 
//                	set_lista_projectos();
                }
                @Override
                public void windowClosed(WindowEvent we) {}
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });
        ad.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_despesasActionPerformed

    private void botao_horasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_horasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Menu_aprova_tarefas_horas mah = new Menu_aprova_tarefas_horas(this.user.get_username(),this.lista_projectos,this.con,this.lista_funcionarios_ativos);
        mah.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) {
//                	set_lista_projectos();
                }
                @Override
                public void windowClosed(WindowEvent we) {}
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });
        mah.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_horasActionPerformed

    private void botao_viagensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_viagensActionPerformed
        
    }//GEN-LAST:event_botao_viagensActionPerformed

    private void botao_projectosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_projectosActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Adicionar_consultar_projecto ad = new Adicionar_consultar_projecto(this.user);
        ad.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) {}
                @Override
                public void windowClosing(WindowEvent we) {}
                @Override
                public void windowClosed(WindowEvent we) {}
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) {}
        });
        ad.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_projectosActionPerformed

    private void botao_tarefasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_tarefasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Adicionar_consultar_tarefa ad = new Adicionar_consultar_tarefa(this.user);
        ad.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {}
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });
        ad.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_tarefasActionPerformed

    private void botao_actividadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_actividadesActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Adicionar_consultar_actividade ad = new Adicionar_consultar_actividade(this.user);
        ad.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {}
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });
        ad.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_actividadesActionPerformed

    private void botao_etapasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_etapasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Adicionar_consultar_etapa ad = new Adicionar_consultar_etapa(this.user);
        ad.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {}
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });
        ad.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_etapasActionPerformed

    private void refresh_lista_funcionarios(){
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	set_lista_funcionarios();
    	this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void botao_funcionariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_funcionariosActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Adicionar_consultar_funcionario ad = new Adicionar_consultar_funcionario(this.user);
        ad.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { 
                }
                @Override
                public void windowClosed(WindowEvent we) {
                	refresh_lista_funcionarios();
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
        ad.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_funcionariosActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void botao_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_clientesActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Adicionar_consultar_cliente ad = new Adicionar_consultar_cliente(this.user);
        ad.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {}
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });
        ad.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_botao_clientesActionPerformed

    private void tipo_despesa_botaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipo_despesa_botaoActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Adicionar_consultar_tipo_despesa ad = new Adicionar_consultar_tipo_despesa(this.user);
        ad.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {}
                @Override
                public void windowIconified(WindowEvent we) {}
                @Override
                public void windowDeiconified(WindowEvent we) {}
                @Override
                public void windowActivated(WindowEvent we) {}
                @Override
                public void windowDeactivated(WindowEvent we) { }
        });
        ad.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_tipo_despesa_botaoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        new Menu_taxa_cambio(this.user.get_username(),this.con).setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        new Menu_relatorios(this.user.get_username(),this.lista_funcionarios_ativos).setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void iniciar_thread_update_lista_projectos(){
    	Runnable helloRunnable = new Runnable() {
    	    public void run() {
    	    	boolean flag = get_continue_flag();
    	    	if (flag)
    	    		update_projectos_thread();
    	    }
    	};
    	this.executor = Executors.newScheduledThreadPool(1);
    	executor.scheduleAtFixedRate(helloRunnable, 0, 60, TimeUnit.SECONDS);
    }
    
    private boolean get_continue_flag(){
    	if (this.isVisible())
    		return true;
    	if (this.contador_thread>0)
    		this.executor.shutdown();
    	this.contador_thread++;
    	return false;
    }
    
    private void update_projectos_thread(){
    	if (!this.inicializacao)
    	{
    		try{
    			//update connection
    			if (!this.con.isValid(10))
    			{
    				Connection con2 = (new Connection_bd(this.user.get_username())).get_connection();
    				this.con.close();
    				this.con = con2;
    			}
    			set_lista_projectos();
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    		this.setCursor(Cursor.getDefaultCursor());
	    		new Log_erros_class().write_log_to_file(this.user.get_username(),e);
	    	}
    	}
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botao_actividades;
    private javax.swing.JButton botao_clientes;
    private javax.swing.JButton botao_despesas;
    private javax.swing.JButton botao_etapas;
    private javax.swing.JButton botao_funcionarios;
    private javax.swing.JButton botao_horas;
    private javax.swing.JButton botao_projectos;
    private javax.swing.JButton botao_tarefas;
    private javax.swing.JButton botao_viagens;
    private javax.swing.JButton botao_voltar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton tipo_despesa_botao;
    // End of variables declaration//GEN-END:variables
}
