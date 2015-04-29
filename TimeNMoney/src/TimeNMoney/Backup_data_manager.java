package TimeNMoney;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Backup_data_manager {
	private Data_manager dm;
	
	public Backup_data_manager(Data_manager data){
		this.dm = data;
	}
	
	public void save_backup_file(){
        try{  
        	String defaultFolder = System.getenv("APPDATA");
        	File s_file = new File(defaultFolder + "\\TimeNMoney\\data");
            s_file.mkdirs();
            String path = s_file.getAbsolutePath();
            String name_file = path + "\\backup_data_manager.sav";
            FileOutputStream saveFile=new FileOutputStream(name_file);
            ObjectOutputStream save;
            save = new ObjectOutputStream(saveFile);
            save.writeObject(this.dm);
            save.close(); 
        }
        catch(IOException e){
            e.printStackTrace(); // If there was an error, print the info.
            new Log_erros_class().write_log_to_file(this.dm.username,e);
        }
    }
	
	public Data_manager get_file_backup(){
        try{
        String defaultFolder = System.getenv("APPDATA");
        String name_file = defaultFolder + "\\TimeNMoney\\data\\backup_data_manager.sav";
        FileInputStream saveFile = new FileInputStream(name_file);
        ObjectInputStream save = new ObjectInputStream(saveFile);
        this.dm = (Data_manager) save.readObject();
        save.close();
        }
        catch(IOException | ClassNotFoundException e){
        	e.printStackTrace(); // If there was an error, print the info.
            new Log_erros_class().write_log_to_file(this.dm.username,e);
        	this.dm = null;
        }
        return this.dm;
    }
	
	public void delete_backup(){
        try{
			String defaultFolder = System.getenv("APPDATA");
	        String name_file = defaultFolder + "\\TimeNMoney\\data\\backup_data_manager.sav";
	        File f = new File(name_file);
	        f.delete();
        }
        catch(Exception e){
        	e.printStackTrace(); // If there was an error, print the info.
            new Log_erros_class().write_log_to_file(this.dm.username,e);
        }
    }
}
