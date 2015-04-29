/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeNMoney;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

/**
 *
 * @author Ivo.Oliveira
 */
public class Log_erros_class {
    public Log_erros_class(){}
    
    public void write_log_to_file(String username, Exception e){
        try{
        	String delimiter = "----------------------------------------------------\r\n";
        	StringWriter sw = new StringWriter();
        	PrintWriter pw = new PrintWriter(sw);
        	e.printStackTrace(pw);
        	String stack_str = sw.toString();
        	send_mail_erro(username,stack_str);
        	Calendar c = Calendar.getInstance();
	        String data_hora = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
	        String mensagem = delimiter + data_hora + " -\r\n " + stack_str + delimiter;
	        String defaultFolder = System.getenv("APPDATA");
            File s_file = new File(defaultFolder + "\\TimeNMoney\\error_folder");
            s_file.mkdirs();
	        String name_file = defaultFolder + "\\TimeNMoney\\error_folder\\log_erros.txt";
	        
	        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name_file, true)));
	        out.println(mensagem);
	        out.close();
	        }
	        catch(IOException ex){}
    }
    
    private void send_mail_erro(String username, String msg_text){
    	Runnable r = new Runnable() {
    	    public void run() {
    	    	send(username, msg_text);
    	    }
    	};
    	new Thread(r).start();
    	
    }
    
    private void send(String username, String msg_text){
    	SendMailTLS smt = new SendMailTLS();
    	String mail_to = "ivo.oliveira@odkas.com";
		smt.send_mail_relatorio_erro(username, mail_to, msg_text);
    }
}
