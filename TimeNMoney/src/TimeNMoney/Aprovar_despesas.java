package TimeNMoney;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Aprovar_despesas extends javax.swing.JFrame {
    ArrayList<Despesa_new> lista; //lista das despesas a mostrar na tabela
    ArrayList<Despesa_new> listaback; //lista de despesas backup para alternar entre aprovadas e todos
    TreeMap<Integer,Integer> estados;
    String username_activo;
    TreeMap<String,Projecto> lista_projectos;
    TreeMap<String,Funcionario> lista_funcionarios;
    String username_admin;
    boolean inicializacao;
    Connection con;
    
    public Aprovar_despesas(String user_admin, TreeMap<String,Projecto> lista_p,Connection con_rec, TreeMap<String,Funcionario> funcionarios) {
        initComponents();
        this.inicializacao = true;
        this.con = con_rec;
        this.username_admin = user_admin;
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.lista = new ArrayList<>();        
        this.listaback = new ArrayList<>();
        this.estados = new TreeMap<>();
        //this.lista_projectos = new HashMap<>();
        //set_lista_projectos();
        this.lista_projectos = new TreeMap<>(lista_p);
        this.lista_funcionarios = funcionarios;
        get_users_from_bd();
        set_username_from_combo();
        load_table(this.username_activo);
        this.inicializacao = false;
        set_to_notaprov_only();
        
    }
    
    private void set_to_notaprov_only(){
        onlyNotAprov.setSelected(true);
        only_aprov_table(true);
        String[] columnNames = {"Cliente","Projecto","Data transacção","Tipo","Quantidade","Valor (€)","Valor Original"};
        jTable1.setModel(new ModeloTabelaDespesasPart(columnNames, this.lista,this.lista_projectos));
    }
    
    private void set_decision_aprov(){
        if (onlyNotAprov.isSelected()){
	    	only_aprov_table(true);
	        String[] columnNames = {"Cliente","Projecto","Data transacção","Tipo","Quantidade","Valor (€)","Valor Original"};
	        jTable1.setModel(new ModeloTabelaDespesasPart(columnNames, this.lista,this.lista_projectos));
        }
    }

    private void set_username_from_combo(){
        String aux = funcionario_combo_box.getSelectedItem().toString();
        String[] lista_aux = aux.split("\\(");
        String user = lista_aux[1];
        user = user.replace(" ", "");
        user = user.replace(")", "");
        this.username_activo = user;
    }
    
    private ArrayList<Integer> get_aproved(){
        ArrayList<Integer> aux = new ArrayList<>();
        int n = jTable1.getRowCount();
        int i=0;
        while(i<n){
            int id = lista.get(i).get_id();//Integer.valueOf(jTable1.getValueAt(i, 0).toString());
            //go to list and verify id state
            if (this.estados.containsKey(id))
                if (this.estados.get(id) == 2)
                    aux.add(i);
            i++;
        } 
        return aux;
    }
    
    private ArrayList<Integer> get_not_aproved(){
        ArrayList<Integer> aux = new ArrayList<>();
        int n = jTable1.getRowCount();
        int i=0;
        while(i<n){
            int id = lista.get(i).get_id();//Integer.valueOf(jTable1.getValueAt(i, 0).toString());
            //go to list and verify id state
            if (this.estados.containsKey(id))
                if (this.estados.get(id) == 3)
                    aux.add(i);
            i++;
        } 
        return aux;
    }
    
    private void set_aproved(){
        ArrayList<Integer> laux = get_aproved();        
        ArrayList<Integer> laux2 = get_not_aproved(); 
        ArrayList<Integer> lista_rejeitadas = get_lista_alteradas_rejeitadas();
    
        int n = jTable1.getColumnCount();
        int i =0;
        while (i<n){
            jTable1.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer(laux,laux2,lista_rejeitadas));
            i++;
        }
    }
    
    private ArrayList<Integer> get_lista_alteradas_rejeitadas(){
    	ArrayList<Integer> aux = new ArrayList<>();
    	int n = jTable1.getRowCount();
    	int i=0;
    	while(i<n){
    		int id = lista.get(i).get_id();//Integer.parseInt(jTable1.getValueAt(i, 0).toString());
    		//go to list and verify id state
    		if (this.estados.containsKey(id))
    			if (this.estados.get(id) == 4)
    				aux.add(i);
    		i++;
    	} 
    	return aux;
    }
    
    private void get_state_from_bd(String username){
    	 TreeMap<Integer,Integer> estado = new TreeMap<>();
        try{
            PreparedStatement ps;
            ResultSet rs;
            String sql = "select * from tnm_handlepagamentos where username='"+ username +"'";
            ps=this.con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID");
                int sit = rs.getInt("SITUACAO");
                estado.put(id, sit);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        this.estados = estado;
    }
    
    @SuppressWarnings("unchecked")
	private void get_users_from_bd(){
        for (Funcionario f : this.lista_funcionarios.values())
        funcionario_combo_box.addItem(f.get_nome() + " ( " + f.get_username() + " )");
    } 
     
    private void load_table(String user){
        get_from_bd(user);
        get_state_from_bd(user);
        String[] columnNames = {"Cliente","Projecto","Data transacção","Tipo","Quantidade","Valor (€)","Valor Original"};
        jTable1.setModel(new ModeloTabelaDespesasPart(columnNames, lista,this.lista_projectos));
        jTable1.getTableHeader().setReorderingAllowed(false);
        set_aproved();
        setTotais(get_totais_aprovados(),get_totais_rejeitados(),get_totais_em_espera());
    }
    
    private ArrayList<Despesa_new> get_selected_expenses(){
        ArrayList<Despesa_new> list = new ArrayList<>();
        int[] rows = jTable1.getSelectedRows();
        for (int row : rows){
           int id = lista.get(row).get_id();
           for (Despesa_new d : this.lista){
                if (d.get_id() == id)
                    list.add(d);
           } 
        }
        if (list.size()<1){
            JOptionPane.showMessageDialog(null, "Tem que seleccionar alguma despesa!");
        }
        return list;
    }
    
    private void get_from_bd(String username){
        ArrayList<Despesa_new> aux = new ArrayList<>();
        try{
            PreparedStatement ps;
            ResultSet rs = null;
            String sql = "select * from tnm_despesas where username='"+ username +"' order by id_despesa desc";
            ps=this.con.prepareStatement(sql);
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
                    this.setCursor(Cursor.getDefaultCursor());
                    new Log_erros_class().write_log_to_file(this.username_admin,e);
                }
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
        this.lista = aux;
    }
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    @SuppressWarnings("rawtypes")
	private void initComponents() {

        right_click = new javax.swing.JPopupMenu();
        consultar_item = new javax.swing.JMenuItem();
        recibo_item = new javax.swing.JMenuItem();
        backlButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        aprovar_botao = new javax.swing.JButton();
        rejeitar_botao = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        funcionario_combo_box = new javax.swing.JComboBox();
        revisao_botao = new javax.swing.JButton();
        onlyNotAprov = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        apLabel5 = new javax.swing.JLabel();
        rjLabel5 = new javax.swing.JLabel();
        esLabel5 = new javax.swing.JLabel();
        totLabel5 = new javax.swing.JLabel();

        consultar_item.setText("Consultar");
        consultar_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultar_itemActionPerformed(evt);
            }
        });
        right_click.add(consultar_item);

        recibo_item.setText("Ver recibo");
        recibo_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recibo_itemActionPerformed(evt);
            }
        });
        right_click.add(recibo_item);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        backlButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        backlButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_back.png"))); // NOI18N
        backlButton.setText("Voltar");
        backlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backlButtonActionPerformed(evt);
            }
        });

        exitButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_exit.png"))); // NOI18N
        exitButton.setText("Sair");
        exitButton.setMaximumSize(new java.awt.Dimension(120, 40));
        exitButton.setMinimumSize(new java.awt.Dimension(120, 40));
        exitButton.setPreferredSize(new java.awt.Dimension(120, 40));
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        aprovar_botao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        aprovar_botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/aprov.png"))); // NOI18N
        aprovar_botao.setText("Aprovar Pagamento(s)");
        aprovar_botao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aprovar_botaoActionPerformed(evt);
            }
        });

        rejeitar_botao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rejeitar_botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/dis_aprov.png"))); // NOI18N
        rejeitar_botao.setText("Rejeitar Pagamento(s)");
        rejeitar_botao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejeitar_botaoActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Projecto", "Data", "Tipo", "Valor"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        funcionario_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funcionario_combo_boxActionPerformed(evt);
            }
        });

        revisao_botao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        revisao_botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/wait_icon.png"))); // NOI18N
        revisao_botao.setText("Colocar em revisão");
        revisao_botao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revisao_botaoActionPerformed(evt);
            }
        });

        onlyNotAprov.setText("Apenas Despesas por aprovar");
        onlyNotAprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlyNotAprovActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Funcionário:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/money-icon.png"))); // NOI18N
        jLabel2.setText("Aprovar Despesas");

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Totais"));

        jLabel20.setText("Aprovado:");

        jLabel21.setText("Rejeitado:");

        jLabel22.setText("Em espera:");

        apLabel5.setText("0");

        rjLabel5.setText("0");

        esLabel5.setText("0");

        totLabel5.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(esLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rjLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(apLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(apLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rjLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(esLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(totLabel5))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(funcionario_combo_box, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(onlyNotAprov))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(aprovar_botao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rejeitar_botao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(revisao_botao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(backlButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(exitButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(funcionario_combo_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlyNotAprov))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(aprovar_botao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rejeitar_botao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(revisao_botao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backlButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void funcionario_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_funcionario_combo_boxActionPerformed
        if (!this.inicializacao)
    	{
        	this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        	onlyNotAprov.setSelected(false);
        	set_username_from_combo();
        	load_table(this.username_activo);
        	set_to_notaprov_only();
        	this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_funcionario_combo_boxActionPerformed

    private void aprovar_botaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aprovar_botaoActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ArrayList<Despesa_new> d = get_selected_expenses();
        for (Despesa_new ds : d){
            add_to_bd_handler_despesa(ds.get_id(), 2, this.username_activo, ds.get_data_despesa(),this.con);
            add_data_aprovacao_despesa(ds,this.username_activo);
            load_table(this.username_activo);
        }
        set_decision_aprov();
        create_thread_notification(this.username_activo);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_aprovar_botaoActionPerformed

    private String get_totais_aprovados(){
        double tot = 0.0;
        for (Despesa_new d : this.lista){
            if (this.estados.containsKey(d.get_id()) && this.estados.get(d.get_id())==2)
                tot += d.get_quantidade() * d.get_valor_euros();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return (df.format(tot).replace(",", "."));
    }
    
    private String get_totais_rejeitados(){
        double tot = 0;
        for (Despesa_new d : this.lista){
            if (this.estados.containsKey(d.get_id()) && this.estados.get(d.get_id())==3)
                tot += d.get_quantidade() * d.get_valor_euros();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(tot).replace(",", ".");
    }
    
    private String get_totais_em_espera(){
        double tot = 0;
        for (Despesa_new d : this.lista){
            if (!this.estados.containsKey(d.get_id()) || (this.estados.containsKey(d.get_id()) && this.estados.get(d.get_id())<=1))
                tot += d.get_quantidade() * d.get_valor_euros();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(tot).replace(",", ".");
        
    }
    
    private void setTotais(String ap, String rj, String es){
        apLabel5.setText(ap);
        rjLabel5.setText(rj);
        esLabel5.setText(es);
        double tot = Double.parseDouble(ap) + Double.parseDouble(rj) + Double.parseDouble(es);
        DecimalFormat df = new DecimalFormat("#.##");
        totLabel5.setText(df.format(tot).replace(",", "."));
    }
    
    private void add_data_aprovacao_despesa(Despesa_new ds, String username){
        Calendar hoje = Calendar.getInstance();
        Date dh = hoje.getTime();
        ds.set_data_aprovacao(dh);
        try {
            PreparedStatement ps;
            String sql;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(ds);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();
            sql="update tnm_despesas set despesa = ? where id_despesa=? and username=?";
            ps=this.con.prepareStatement(sql);
            ps.setObject(1, data);
            ps.setInt(2, ds.get_id());
            ps.setString(3, username);
            ps.executeUpdate();
            ps.close();
            
        } catch (IOException | SQLException e) {
        	this.setCursor(Cursor.getDefaultCursor());
        	new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
    }
    
    private void remove_data_aprovacao_despesa(Despesa_new ds, String username){
        ds.set_data_aprovacao(null);
        try {
            PreparedStatement ps;
            String sql;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(ds);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();
            sql="update tnm_despesas set despesa = ? where id_despesa=? and username=?";
            ps=this.con.prepareStatement(sql);
            ps.setObject(1, data);
            ps.setInt(2, ds.get_id());
            ps.setString(3, username);
            ps.executeUpdate();
            ps.close();
            
        } catch (IOException | SQLException e) {
        	this.setCursor(Cursor.getDefaultCursor());
        	new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
    }
    
    private void rejeitar_botaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejeitar_botaoActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ArrayList<Despesa_new> d = get_selected_expenses();
        for (Despesa_new ds : d){
            add_to_bd_handler_despesa(ds.get_id(), 3, this.username_activo, ds.get_data_despesa(),this.con);
            remove_data_aprovacao_despesa(ds,this.username_activo);
            load_table(this.username_activo);
        }
        set_decision_aprov();
        create_thread_notification(this.username_activo);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_rejeitar_botaoActionPerformed

    private void revisao_botaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revisao_botaoActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ArrayList<Despesa_new> d = get_selected_expenses();
        for (Despesa_new ds : d){
            add_to_bd_handler_despesa(ds.get_id(), 1, this.username_activo, ds.get_data_despesa(),this.con);
            remove_data_aprovacao_despesa(ds,this.username_activo);
            load_table(this.username_activo);
        }
        set_decision_aprov();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_revisao_botaoActionPerformed

    private void onlyNotAprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlyNotAprovActionPerformed
        boolean b = onlyNotAprov.isSelected();
        only_aprov_table(b);
        String[] columnNames = {"Cliente","Projecto","Data transacção","Tipo","Quantidade","Valor (€)","Valor Original"};
        jTable1.setModel(new ModeloTabelaDespesasPart(columnNames, this.lista,this.lista_projectos));
        if (!b)
            set_aproved();
    }//GEN-LAST:event_onlyNotAprovActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void backlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backlButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_backlButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        if ( SwingUtilities.isRightMouseButton(evt))
            {
                Point p = evt.getPoint();
                int rowNumber = jTable1.rowAtPoint(p);
                jTable1.setRowSelectionInterval(rowNumber, rowNumber);
                jTable1.setComponentPopupMenu(right_click);
                right_click.show(jTable1, evt.getX(), evt.getY());

            }
    }//GEN-LAST:event_jTable1MousePressed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
    }//GEN-LAST:event_jTable1MouseReleased

    private void consultar_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultar_itemActionPerformed
        Despesa_new d = get_selected_expense();
        if (d!=null){
            Consultar_despesa ce = new Consultar_despesa(this.username_activo, d,false);
            ce.addWindowListener(new WindowListener() {
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
            ce.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null,"Não seleccionou nenhuma despesa!");
        }
    }//GEN-LAST:event_consultar_itemActionPerformed

    private void recibo_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recibo_itemActionPerformed
        read_rebibo_function();
    }//GEN-LAST:event_recibo_itemActionPerformed

    private void read_rebibo_function(){
        Despesa_new d = get_selected_expense();
        if (d!=null){
            File f = d.get_recibo();
            if (f!=null){
                try{
                    Desktop.getDesktop().open(f);
                }
                catch(IOException e){
                    e.printStackTrace();
                    this.setCursor(Cursor.getDefaultCursor());
                    new Log_erros_class().write_log_to_file(this.username_admin,e);
                    JOptionPane.showMessageDialog(null, "Erro ao abrir recibo!");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Não está associado nenhum recibo!");
            }
        }
    }
    
    private Despesa_new get_selected_expense(){
        int row = jTable1.getSelectedRow();
        if (row>=0){
            int id = lista.get(row).get_id();//Integer.parseInt(jTable1.getValueAt(row, 0).toString());
            for (Despesa_new d : this.lista){
                if (d.get_id()==id)
                    return d;
            }
        }
        return null;
        
    }
    
    private void only_aprov_table(boolean b){
        if (b){
            this.listaback = this.lista;
            ArrayList<Despesa_new> aux = new ArrayList<>();
            for (Despesa_new d : this.lista){
                int idx = d.get_id();
                if ((!this.estados.containsKey(idx)) || (this.estados.get(idx)<2))
                    aux.add(d);
            }
            this.lista = aux;
        }
        else{
            this.lista = this.listaback;
        }
    }
    
    
    @SuppressWarnings("resource")
	private void add_to_bd_handler_despesa(int id, int sit, String username,Date data, Connection con){
        try {
            PreparedStatement ps;
            String sql;
            ResultSet rs;
            sql = "select * from tnm_handlepagamentos where id="+ id +" and username ='"+username+"'";
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next()){
                sql="insert into tnm_handlepagamentos (id,situacao,username,data) values(?,?,?,?)";
                ps=con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setInt(2, sit);
                ps.setString(3, username);
                ps.setDate(4, new java.sql.Date(data.getTime()));
                ps.executeUpdate();
            }
            else
            {
                sql="update tnm_handlepagamentos set situacao=?, data=? where id = ? and username = ?";
                ps=con.prepareStatement(sql);
                ps.setInt(1, sit);
                ps.setDate(2, new java.sql.Date(data.getTime()));
                ps.setInt(3, id);
                ps.setString(4,username);
                ps.executeUpdate();
            }
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
        	this.setCursor(Cursor.getDefaultCursor());
        	new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
    }

    @SuppressWarnings("resource")
	private void begin_notification_thread(String username){
    	try {
			 Calendar c = Calendar.getInstance();
	         int dia = c.get(Calendar.DAY_OF_MONTH);
	         int mes = c.get(Calendar.MONTH);
	         int ano = c.get(Calendar.YEAR);
	         c.clear();
	         c.set(Calendar.DAY_OF_MONTH,dia);
	         c.set(Calendar.MONTH,mes);
	         c.set(Calendar.YEAR,ano);
	         Date hoje = c.getTime();
	         
            PreparedStatement ps;
            String sql;
            ResultSet rs;
            sql = "select * from tnm_handle_notif where username='"+ username + "'";
            ps=this.con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next())
            {
            	Date aux = rs.getDate("DATA");
            	if (!aux.equals(hoje)){
            		SendMailTLS smt = new SendMailTLS();
                	smt.send_mail_notificacao(username);
                	java.sql.Date d_hoje = new java.sql.Date(hoje.getTime());
                	sql="update tnm_handle_notif set data=? where username=?";
                	ps=this.con.prepareStatement(sql);
                	ps.setDate(1, d_hoje);
                	ps.setString(2, username);
                	ps.executeUpdate();
            	}
            }
            else
            {
            	SendMailTLS smt = new SendMailTLS();
            	smt.send_mail_notificacao(username);
            	
            	java.sql.Date d_hoje = new java.sql.Date(hoje.getTime());
            	sql="insert into tnm_handle_notif (username,data) values(?,?)";
                ps=this.con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setDate(2, d_hoje);
                ps.executeUpdate();
            }
          rs.close();
          ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username_admin,e);
        }
    }
    

	private void create_thread_notification(String username){
    	Runnable r = new Runnable() {
			public void run() {
				begin_notification_thread(username);
			}
		};
		new Thread(r).start();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    @SuppressWarnings("unused")
	private javax.swing.JLabel apLabel;
    @SuppressWarnings("unused")
    private javax.swing.JLabel apLabel1;
    @SuppressWarnings("unused")
    private javax.swing.JLabel apLabel2;
    @SuppressWarnings("unused")
    private javax.swing.JLabel apLabel3;
    @SuppressWarnings("unused")
    private javax.swing.JLabel apLabel4;
    private javax.swing.JLabel apLabel5;
    private javax.swing.JButton aprovar_botao;
    private javax.swing.JButton backlButton;
    private javax.swing.JMenuItem consultar_item;
    @SuppressWarnings("unused")
    private javax.swing.JLabel esLabel;
    @SuppressWarnings("unused")
    private javax.swing.JLabel esLabel1;
    @SuppressWarnings("unused")
    private javax.swing.JLabel esLabel2;
    @SuppressWarnings("unused")
    private javax.swing.JLabel esLabel3;
    @SuppressWarnings("unused")
    private javax.swing.JLabel esLabel4;
    private javax.swing.JLabel esLabel5;
    private javax.swing.JButton exitButton;
    @SuppressWarnings("rawtypes")
	private javax.swing.JComboBox funcionario_combo_box;
    private javax.swing.JLabel jLabel1;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel10;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel11;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel12;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel13;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel14;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel15;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel16;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel17;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel18;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel5;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel6;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel7;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel8;
    @SuppressWarnings("unused")
    private javax.swing.JLabel jLabel9;
    @SuppressWarnings("unused")
    private javax.swing.JPanel jPanel1;
    @SuppressWarnings("unused")
    private javax.swing.JPanel jPanel2;
    @SuppressWarnings("unused")
    private javax.swing.JPanel jPanel3;
    @SuppressWarnings("unused")
    private javax.swing.JPanel jPanel4;
    @SuppressWarnings("unused")
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JCheckBox onlyNotAprov;
    private javax.swing.JMenuItem recibo_item;
    private javax.swing.JButton rejeitar_botao;
    private javax.swing.JButton revisao_botao;
    private javax.swing.JPopupMenu right_click;
    @SuppressWarnings("unused")
    private javax.swing.JLabel rjLabel;
    @SuppressWarnings("unused")
    private javax.swing.JLabel rjLabel1;
    @SuppressWarnings("unused")
    private javax.swing.JLabel rjLabel2;
    @SuppressWarnings("unused")
    private javax.swing.JLabel rjLabel3;
    @SuppressWarnings("unused")
    private javax.swing.JLabel rjLabel4;
    private javax.swing.JLabel rjLabel5;
    @SuppressWarnings("unused")
    private javax.swing.JLabel totLabel;
    @SuppressWarnings("unused")
    private javax.swing.JLabel totLabel1;
    @SuppressWarnings("unused")
    private javax.swing.JLabel totLabel2;
    @SuppressWarnings("unused")
    private javax.swing.JLabel totLabel3;
    @SuppressWarnings("unused")
    private javax.swing.JLabel totLabel4;
    private javax.swing.JLabel totLabel5;
    // End of variables declaration//GEN-END:variables
}
