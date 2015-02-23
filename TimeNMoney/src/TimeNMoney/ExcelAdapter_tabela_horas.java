package TimeNMoney;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.datatransfer.*;
import java.util.*;

public class ExcelAdapter_tabela_horas implements ActionListener{
   private String rowstring,value;
   private Clipboard system;
   private StringSelection stsel;
   private JTable jTable1 ;
   private Menu_tarefas_horas menu;
   
   public ExcelAdapter_tabela_horas(JTable myJTable, Menu_tarefas_horas mt)
   {
	   menu = mt;
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
            	String s = String.valueOf(jTable1.getValueAt(rowsselected[i],colsselected[j]));
            	s = s.replace(".", ",");
              	sbf.append(s);
            	if (j<numcols-1) 
            		sbf.append("\t");
            }
            sbf.append("\n");
         }
         stsel  = new StringSelection(sbf.toString());
         system = Toolkit.getDefaultToolkit().getSystemClipboard();
         system.setContents(stsel,stsel);
      }
      if (e.getActionCommand().compareTo("Paste")==0)
      {
          int startRow=(jTable1.getSelectedRows())[0];
          int startCol=(jTable1.getSelectedColumns())[0];
          try
          {
             String trstring= (String)(system.getContents(this).getTransferData(DataFlavor.stringFlavor));
             StringTokenizer st1=new StringTokenizer(trstring,"\n");
             for(int i=0;st1.hasMoreTokens();i++)
             {
                rowstring=st1.nextToken();
                StringTokenizer st2=new StringTokenizer(rowstring,"\t");
                for(int j=0;st2.hasMoreTokens();j++)
                {
                   value=(String)st2.nextToken();
                   value = value.replace(",", ".");
                   if (startRow+i< jTable1.getRowCount()  && startCol+j< jTable1.getColumnCount())
                	   jTable1.setValueAt(value,startRow+i,startCol+j);
               }
            }
            menu.action_table();
         }
         catch(Exception ex){ex.printStackTrace();}
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
        	String s = String.valueOf(jTable1.getValueAt(rowsselected[i],colsselected[j]));
        	s = s.replace(".", ",");
          	sbf.append(s);
          	if (j<numcols-1) 
          		sbf.append("\t");
          }
          sbf.append("\n");
       }
       stsel  = new StringSelection(sbf.toString());
       system = Toolkit.getDefaultToolkit().getSystemClipboard();
       system.setContents(stsel,stsel);
   }
   
   public void set_paste(int startRow, int startCol){
//	   	int startRow=(jTable1.getSelectedRows())[0];
//       int startCol=(jTable1.getSelectedColumns())[0];
       try
       {
          String trstring= (String)(system.getContents(this).getTransferData(DataFlavor.stringFlavor));
          StringTokenizer st1=new StringTokenizer(trstring,"\n");
          for(int i=0;st1.hasMoreTokens();i++)
          {
             rowstring=st1.nextToken();
             StringTokenizer st2=new StringTokenizer(rowstring,"\t");
             for(int j=0;st2.hasMoreTokens();j++)
             {
                value=(String)st2.nextToken();
                value = value.replace(",", ".");
                if (startRow+i< jTable1.getRowCount()  && startCol+j< jTable1.getColumnCount())
             	   jTable1.setValueAt(value,startRow+i,startCol+j);
            }
         }
         menu.action_table();
      }
      catch(Exception ex){ex.printStackTrace();}
   }
}