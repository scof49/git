package TimeNMoney;

public class Main {
    public static void main(String [] args) {
    	System.setProperty("user.timezone", "Europe/London");
    	try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException e) {
        	new Log_erros_class().write_log_to_file(e);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Auto_Log_Class al = new Auto_Log_Class();
                al.init();
            }
        });
    }
}
