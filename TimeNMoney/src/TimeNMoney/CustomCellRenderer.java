/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
@SuppressWarnings("serial")
public class CustomCellRenderer extends DefaultTableCellRenderer {
    ArrayList<Integer> listaA;
    ArrayList<Integer> listaR;  
    ArrayList<Integer> listaAlt;  
    int aprov;


    public CustomCellRenderer(ArrayList<Integer> listaA, ArrayList<Integer> listaR, ArrayList<Integer> lista_alteradas){
        this.listaA = listaA;
        this.listaR = listaR;
        this.listaAlt = lista_alteradas;
    }
    
    public CustomCellRenderer(int aprov){
        this.listaA = null;
        this.listaR = null;
        this.aprov = aprov;
    }
    
    @Override
    public Component  getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {

        Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
        JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);

      //Set foreground color
      // rendererComp.setForeground(Color.red);
      //Set background color
        if (listaA!=null && listaR!=null && listaAlt!=null){
            l.setToolTipText(l.getText());
            Color ap, rj,alt;
            ap = new Color(0, 150, 0);
            rj = Color.RED;
            alt = Color.ORANGE;
            if (listaA.contains(row))
                rendererComp.setForeground(ap);
            else if (listaR.contains(row))
                rendererComp.setForeground(rj);
            else if (listaAlt.contains(row))
            	rendererComp.setForeground(alt);
            else 
                rendererComp.setForeground(Color.black);    
        }
        else {
            l.setToolTipText(l.getText());
            if (aprov==1){
                Color ap;
                ap = new Color(0, 150, 0);
                rendererComp.setForeground(ap);    
            }
            else if (aprov==2){
                Color rj;
                rj = Color.RED;
                rendererComp.setForeground(rj);    
            }
            else if (aprov==3){
                Color al;
                al = new Color(209,169,59);
                rendererComp.setForeground(al);    
            }
            else if (aprov==9){
            	Color bl;
            	bl = Color.GRAY;
            	rendererComp.setForeground(bl);
            }
            else{
                rendererComp.setForeground(Color.black);    
            }
        }
        
        return rendererComp ;
     }

}