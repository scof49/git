/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ivo.Oliveira
 */
public class Connection_bd {
    Connection con;
    
    public Connection_bd(){
        set_connection();
    }
    
    private void set_connection(){
        try{
        	//1 bd netbeans backup
//        Class.forName("org.apache.derby.jdbc.ClientDriver");
//        String username = "tarefas";
//        String password = "tarefas";
//        this.con = DriverManager.getConnection("jdbc:derby://localhost:1527/BDTarefas", username,password);
        	
        	//2 bd virtual machine //qas
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            String user_bd = "tnmdb";
//            String password = "tnmdb";
//            this.con = DriverManager.getConnection("jdbc:oracle:thin:@tnmdb-pc:1521/xe", user_bd, password);
        	
            //PRD
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	String user_bd = "tnmdbusr";
	        String pass_bd = "0dk@s2014";
	        this.con = DriverManager.getConnection("jdbc:sqlserver://SQLB15.webcontrolcenter.com:1433", user_bd, pass_bd);
    
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            new Log_erros_class().write_log_to_file(e);
        }
    }
    
    public Connection get_connection(){
        return this.con;
    }
    
}
