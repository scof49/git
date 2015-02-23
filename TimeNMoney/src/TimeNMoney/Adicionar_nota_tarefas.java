/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author Ivo.Oliveira
 */
@SuppressWarnings("serial")
public class Adicionar_nota_tarefas extends javax.swing.JFrame {
    String id_tarefa;
    Date data_nota;
    String nota;
    Data_manager dm;
    
    public Adicionar_nota_tarefas(String id, Date data, Data_manager dm) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("odkas.tnm.png")));
        this.id_tarefa = id;
        this.data_nota = data;
        this.nota = "";
        this.dm = dm;
        preenche_field(); 
    }
    
    private void preenche_field(){
        id_field.setText(this.id_tarefa);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String d_aux = df.format(this.data_nota);
        data_field.setText(d_aux);
        nota_field.setText(existe_nota());
    }
    
    private String existe_nota(){
        String aux = "";
        if (this.dm.notas_tarefas.containsKey(this.id_tarefa))
            if (this.dm.notas_tarefas.get(this.id_tarefa).containsKey(this.data_nota))
                aux = this.dm.notas_tarefas.get(this.id_tarefa).get(this.data_nota);
        return aux;
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        id_field = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        nota_field = new javax.swing.JTextArea();
        cancel_button = new javax.swing.JButton();
        save_button = new javax.swing.JButton();
        data_field = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ODKAS - Time &  Money");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/report.png"))); // NOI18N
        jLabel1.setText("Adicionar Nota");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Tarefa:");

        id_field.setText("iddatarefa");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Dia:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Nota:");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nota_field.setColumns(20);
        nota_field.setLineWrap(true);
        nota_field.setRows(5);
        jScrollPane1.setViewportView(nota_field);

        cancel_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/Cancel-icon.png"))); // NOI18N
        cancel_button.setText("Cancelar");
        cancel_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_buttonActionPerformed(evt);
            }
        });

        save_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TimeNMoney/saveicon.png"))); // NOI18N
        save_button.setText("Gravar");
        save_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_buttonActionPerformed(evt);
            }
        });

        data_field.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        					.addGroup(layout.createSequentialGroup()
        						.addComponent(save_button)
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addComponent(cancel_button))
        					.addGroup(layout.createSequentialGroup()
        						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        							.addComponent(jLabel6, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(jLabel4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(jLabel2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
        						.addPreferredGap(ComponentPlacement.UNRELATED)
        						.addGroup(layout.createParallelGroup(Alignment.LEADING)
        							.addComponent(id_field, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
        							.addComponent(data_field, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        			.addGap(34))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jLabel1)
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel2)
        				.addComponent(id_field))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel4)
        				.addComponent(data_field))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jLabel6)
        				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
        			.addGap(24)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(0, 0, Short.MAX_VALUE)
        					.addComponent(save_button))
        				.addComponent(cancel_button, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void cancel_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_buttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancel_buttonActionPerformed

    private void save_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_buttonActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.nota = nota_field.getText();
        if (this.dm.notas_tarefas.containsKey(this.id_tarefa))
            this.dm.notas_tarefas.get(this.id_tarefa).put(this.data_nota,this.nota);
        else
        {
        	TreeMap<Date,String> aux = new TreeMap<>();
            aux.put(this.data_nota, this.nota);
            this.dm.notas_tarefas.put(this.id_tarefa, aux);
        }
        this.setCursor(Cursor.getDefaultCursor());
        this.dispose();
    }//GEN-LAST:event_save_buttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel_button;
    private javax.swing.JLabel data_field;
    private javax.swing.JLabel id_field;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea nota_field;
    private javax.swing.JButton save_button;
    // End of variables declaration//GEN-END:variables
}
