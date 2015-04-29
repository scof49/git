package TimeNMoney;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Cursor;
import java.awt.Label;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Aprovar_multiplas_datas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String username;
	private int situacao;
	private Connection con;

	public Aprovar_multiplas_datas(String username, Date data_i, int situacao, Connection con) {
		this.username = username;
		this.situacao = situacao;
		this.con = con;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				set_location();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Aprovar_multiplas_datas.class.getResource("/TimeNMoney/odkas.tnm.png")));
		setTitle("ODKAS - Time &  Money");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 386, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Label label = new Label("Aprovar de:");
		label.setFont(new Font("Dialog", Font.BOLD, 12));
		label.setAlignment(Label.CENTER);
		label.setBounds(58, 48, 85, 33);
		contentPane.add(label);
		
		date_de = new JDateChooser();
		date_de.setDateFormatString("dd/MM/yyyy");
		date_de.setBounds(149, 48, 168, 33);
		contentPane.add(date_de);
		
		Label label_1 = new Label("Aprovar até:");
		label_1.setFont(new Font("Dialog", Font.BOLD, 12));
		label_1.setAlignment(Label.CENTER);
		label_1.setBounds(58, 87, 85, 36);
		contentPane.add(label_1);
		
		date_ate = new JDateChooser();
		date_ate.setDateFormatString("dd/MM/yyyy");
		date_ate.setBounds(149, 89, 168, 34);
		contentPane.add(date_ate);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sair_app();
			}
		});
		btnCancelar.setIcon(new ImageIcon(Aprovar_multiplas_datas.class.getResource("/TimeNMoney/Cancel-icon.png")));
		btnCancelar.setBounds(185, 157, 132, 47);
		contentPane.add(btnCancelar);
		
		JButton btnAprovar = new JButton("Aprovar");
		btnAprovar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aprovar();
			}
		});
		btnAprovar.setIcon(new ImageIcon(Aprovar_multiplas_datas.class.getResource("/TimeNMoney/aprov.png")));
		btnAprovar.setBounds(58, 157, 117, 47);
		contentPane.add(btnAprovar);
		
		iniciar(username, data_i, situacao);
	}
	
	private void set_location(){
		this.setLocationRelativeTo(null);
	}
	
	private void sair_app(){
		this.dispose();
	}
	
	private void iniciar(String username, Date data_i, int situacao){
		this.date_de.setDate(data_i);
		Calendar c = Calendar.getInstance();
		this.date_ate.setDate(c.getTime());
		
	}
	
	private void add_multi_to_handler(String username, Date inicio, Date fim, int situacao){
        Calendar c_fim = Calendar.getInstance();
        c_fim.clear();
        c_fim.setTime(fim);
        Calendar c_inicio = Calendar.getInstance();
        c_inicio.clear();
        c_inicio.setTime(inicio);
        while(!c_inicio.after(c_fim))
        {
            add_to_bd_handler_horas(username, c_inicio.getTime(), situacao);
            c_inicio.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
	
	@SuppressWarnings("resource")
	private void add_to_bd_handler_horas(String username, Date data, int aprov){
        java.sql.Date d1 = new java.sql.Date(data.getTime());
        try {
            PreparedStatement ps;
            String sql;
            ResultSet rs;
            sql = "select * from tnm_handle_horas where username='"+ username +"' and data = '" + d1 + "'";
            ps=this.con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next()){
                sql="insert into tnm_handle_horas (username,data,situacao) values(?,?,?)";
                ps=this.con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setDate(2, d1);
                ps.setInt(3, aprov);
                ps.executeUpdate();
            }
            else
            {
                sql="update tnm_handle_horas set situacao=? where username=? and data =?";
                ps=this.con.prepareStatement(sql);
                ps.setInt(1, aprov);
                ps.setString(2, username);
                ps.setDate(3, d1);
                ps.executeUpdate();
            }
          rs.close();
          ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.setCursor(Cursor.getDefaultCursor());
            new Log_erros_class().write_log_to_file(this.username,e);
        }
    }
	
	private void aprovar(){
		Date inicio = this.date_de.getDate();
		Date fim = this.date_ate.getDate();
		if (inicio == null || fim == null){
			JOptionPane.showMessageDialog(null, "Deve escolher data de início e data de fim!");
		}
		else{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			add_multi_to_handler(this.username, inicio, fim, this.situacao);
			this.dispose();
			create_thread_notification(this.username);
			this.setCursor(Cursor.getDefaultCursor());
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
            new Log_erros_class().write_log_to_file(this.username,e);
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
	
	private JDateChooser date_ate;
	private JDateChooser date_de;
	
}
