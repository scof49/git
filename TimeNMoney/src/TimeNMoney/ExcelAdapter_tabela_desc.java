package TimeNMoney;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.datatransfer.*;

public class ExcelAdapter_tabela_desc implements ActionListener{
   private Clipboard system;
   private StringSelection stsel;
   private JTable jTable1 ;
   
   public ExcelAdapter_tabela_desc(JTable myJTable)
   {
	   jTable1 = myJTable;
	   KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
	   KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK,false);
	   jTable1.registerKeyboardAction(this,"Copy",copy,JComponent.WHEN_FOCUSED);
	   jTable1.registerKeyboardAction(this,"Paste",paste,JComponent.WHEN_FOCUSED);
	   system = Toolkit.getDefaultToolkit().getSystemClipboard();
   }
   
   public JTable getJTable() {return jTable1;}

   public void setJTable(JTable jTable1) {this.jTable1=jTable1;}
   
   public void actionPerformed(ActionEvent e)
   {
	  if (e.getActionCommand().compareTo("Copy")==0)
      {
         StringBuffer sbf=new StringBuffer();
         int numcols = jTable1.getColumnCount();
         int numrows=jTable1.getSelectedRowCount();
         int[] colsselected = new int[numcols];
         for (int cont=0;cont<numcols;cont++)
        	 colsselected[cont] = cont;
        	 
         int[] rowsselected=jTable1.getSelectedRows();
         if (!((numrows-1==rowsselected[rowsselected.length-1]-rowsselected[0] && numrows==rowsselected.length) && (numcols-1==colsselected[colsselected.length-1]-colsselected[0] && numcols==colsselected.length)))
         {
            JOptionPane.showMessageDialog(null, "Invalid Copy Selection","Invalid Copy Selection",JOptionPane.ERROR_MESSAGE);
            return;
         }
         for (int i=0;i<numrows;i++)
         {
            for (int j=0;j<numcols;j++)
            {
              	sbf.append(jTable1.getValueAt(rowsselected[i],colsselected[j]));
            	if (j<numcols-1) 
            		sbf.append("\t");
            }
            sbf.append("\n");
         }
         stsel  = new StringSelection(sbf.toString());
         system = Toolkit.getDefaultToolkit().getSystemClipboard();
         system.setContents(stsel,stsel);
      }
   }
   
   public void set_copy_line(){
	   StringBuffer sbf=new StringBuffer();
       int numcols = jTable1.getColumnCount();
       int numrows=jTable1.getSelectedRowCount();
       int[] colsselected = new int[numcols];
       for (int cont=0;cont<numcols;cont++)
      	 colsselected[cont] = cont;
      	 
       int[] rowsselected=jTable1.getSelectedRows();
       if (!((numrows-1==rowsselected[rowsselected.length-1]-rowsselected[0] && numrows==rowsselected.length) && (numcols-1==colsselected[colsselected.length-1]-colsselected[0] && numcols==colsselected.length)))
       {
          JOptionPane.showMessageDialog(null, "Invalid Copy Selection","Invalid Copy Selection",JOptionPane.ERROR_MESSAGE);
          return;
       }
       for (int i=0;i<numrows;i++)
       {
          for (int j=0;j<numcols;j++)
          {
          	sbf.append(jTable1.getValueAt(rowsselected[i],colsselected[j]));
          	if (j<numcols-1) 
          		sbf.append("\t");
          }
          sbf.append("\n");
       }
       stsel  = new StringSelection(sbf.toString());
       system = Toolkit.getDefaultToolkit().getSystemClipboard();
       system.setContents(stsel,stsel);
   }
}