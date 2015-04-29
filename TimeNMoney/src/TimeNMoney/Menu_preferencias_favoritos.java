/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Menu_preferencias_favoritos extends javax.swing.JFrame {
    String username;
    Data_manager dm;
    ArrayList<TarefaHoras> lista_favoritos;
    ArrayList<TarefaHoras> remove_list;
        
    /**
     * Creates new form Menu_preferencias_favoritos
     * @param username
     * @param dm
     */
    public Menu_preferencias_favoritos(String username, Data_manager dm) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.username = username;
        this.dm = dm;
        get_from_dm();
        set_model_table();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        back_button = new javax.swing.JButton();
        send_button = new javax.swing.JButton();
        add_button = new javax.swing.JButton();
        delete_button = new javax.swing.JButton();
        edit_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/fav_icon_big.png"))); // NOI18N
        jLabel1.setText("Menu de Preferências Favoritos");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        back_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/mini_back.png"))); // NOI18N
        back_button.setText("Voltar");
        back_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_buttonActionPerformed(evt);
            }
        });

        send_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/send_icon.png"))); // NOI18N
        send_button.setText("Enviar");
        send_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_buttonActionPerformed(evt);
            }
        });

        add_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/add_fav.png"))); // NOI18N
        add_button.setText("Adicionar Favorito");
        add_button.setToolTipText("");
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        delete_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/delete_fav.png"))); // NOI18N
        delete_button.setText("Eliminar Favorito");
        delete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_buttonActionPerformed(evt);
            }
        });

        edit_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/edit_fav.png"))); // NOI18N
        edit_button.setText("Editar Favorito");
        edit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPane1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        					.addComponent(jLabel1)
        					.addGap(254, 339, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(add_button)
        					.addGap(18)
        					.addComponent(edit_button)
        					.addGap(18)
        					.addComponent(delete_button))
        				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        					.addComponent(send_button)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(back_button)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel1)
        			.addGap(40)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        				.addComponent(delete_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(edit_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(add_button))
        			.addGap(18)
        			.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(back_button)
        				.addComponent(send_button))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        Adicionar_tarefa_time_fav ad = new Adicionar_tarefa_time_fav(this.username,this.dm);
        ad.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {
                    get_from_dm();
                    set_model_table();
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
    }//GEN-LAST:event_add_buttonActionPerformed

    private void back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_buttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_back_buttonActionPerformed

    private void send_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_buttonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int res = send_fav(true);
        if (res==0)
            JOptionPane.showMessageDialog(null, "Gravado com sucesso!");
        else
            JOptionPane.showMessageDialog(null, "Ocorreu um erro na gravação na base de dados, tente outra vez ou contacte um Administrador!");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_send_buttonActionPerformed

    public int send_fav(boolean backup){
        return save_to_bd(backup);
    }
    
    private void delete_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_buttonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int[] rows = jTable1.getSelectedRows();
        if (rows.length>0){
            int i = rows.length - 1;
            while(i>=0){
                //delete_task(jTable1.getValueAt(rows[i], 0).toString());
                delete_task(lista_favoritos.get(rows[i]).get_id());
                i--;
            }
            //JOptionPane.showMessageDialog(null, "Eliminado com sucesso!");
        }
        else
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_delete_buttonActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void edit_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_buttonActionPerformed
        int row = jTable1.getSelectedRow();
        if (row>=0){
            String id = lista_favoritos.get(row).get_id();//jTable1.getValueAt(row, 0).toString();
//            for (TarefaHoras t : this.lista){
//                if (t.get_id().equals(id))
//                    call_menu_alterar(t);
//            }
            TarefaHoras t = null;
            for (TarefaHoras aux : this.lista_favoritos)
                if (aux.get_id().equals(id))
                    t = aux;
            if (t!=null){
                call_menu_alterar(t);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Não seleccionou nenhuma tarefa!");
        }
    }//GEN-LAST:event_edit_buttonActionPerformed

    private void call_menu_alterar(TarefaHoras t){
//        Alterar_tarefa_time at = new Alterar_tarefa_time(this.cal,t,this.username);
        Alterar_tarefa_time_fav atf = new Alterar_tarefa_time_fav(t,this.username,this.dm);
        atf.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent we) { }
                @Override
                public void windowClosing(WindowEvent we) { }
                @Override
                public void windowClosed(WindowEvent we) {
                    get_from_dm();
                    set_model_table();
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
        atf.setVisible(true);
    }
    
    private void delete_task(String id){
        for (TarefaHoras t : this.lista_favoritos){
            if (id.equals(t.get_id())){
                remove_list.add(t);
            }
        }
        for (TarefaHoras t : remove_list)
            this.lista_favoritos.remove(t);
        set_model_table();
    }
    
    private int save_to_bd(boolean backup){
        int res = 0;
        Backup_data_manager bdm = new Backup_data_manager(this.dm);
        if (backup)
        	bdm.save_backup_file();
        
        for (TarefaHoras t : this.lista_favoritos)
        {
            res += set_to_bd(t);
        }
        for (TarefaHoras t : this.remove_list)
        {
            res += remove_from_bd(t);
        }
        this.remove_list = new ArrayList<>();
        this.dm.set_remove_lista_favoritos(remove_list);
        
        if (backup){
        	if (res==0)
        		bdm.delete_backup();
        	else
        		this.dm = bdm.get_file_backup();
        }
        
        return res;
    }
    
    private int set_to_bd(TarefaHoras t){
        int res = 0;
        try{
        Connection con = (new Connection_bd(this.username)).get_connection();
        PreparedStatement ps;
        String sql;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(t);
        oos.flush();
        oos.close();
        bos.close();

        
        byte[] data = bos.toByteArray();
        sql = "select * from tnm_tarefas_favoritos where id_tarefa = '" +t.get_id()+"' and username='"+this.username+"'";
        ResultSet rs;
        ps=con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next())
            sql = "update tnm_tarefas_favoritos set tarefa=? where id_tarefa=? and username=?";
        else
            sql="insert into tnm_tarefas_favoritos (tarefa,id_tarefa,username) values(?,?,?)";
        ps=con.prepareStatement(sql);
        ps.setObject(1, data);
        ps.setString(2, t.get_id());
        ps.setString(3, this.username);
        ps.executeUpdate();
        
        rs.close();
        ps.close();
        con.close();
        }
        catch(IOException | SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username,e);
            res++;
        }
        return res;
    }
    
    private int remove_from_bd(TarefaHoras t){
        int res = 0;
        try{
        Connection con = (new Connection_bd(this.username)).get_connection();
        PreparedStatement ps;
        String sql;
        sql = "delete from tnm_tarefas_favoritos where id_tarefa = '" +t.get_id()+"' and username='"+this.username+"'";
        ps=con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
        con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username,e);
            res++;
        }
        return res;
    }
    
    private void get_from_dm(){
        this.lista_favoritos = this.dm.get_lista_tarefas_favoritas();
        this.remove_list = this.dm.get_remove_lista_favoritos();
    }
    
    private void set_model_table(){
        ArrayList<TarefaHoras> lista_fav = new ArrayList<>();
        for (TarefaHoras t : this.lista_favoritos)
            lista_fav.add(t);
        //String columnNamesTarefas[] = {"ID","Projecto","Etapa","Actividade","Tarefa","Local"};
        String columnNamesTarefas[] = {"Cliente","Projecto","Etapa","Actividade","Tarefa","Local"};
        jTable1.setModel(new ModeloTabelaTarefas(columnNamesTarefas, lista_fav,this.dm.get_projectos_user()));   
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_button;
    private javax.swing.JButton back_button;
    private javax.swing.JButton delete_button;
    private javax.swing.JButton edit_button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton send_button;
    // End of variables declaration//GEN-END:variables
}
