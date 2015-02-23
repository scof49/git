package TimeNMoney;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;

import javax.swing.border.MatteBorder;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoadScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel label;
	private JLabel lblPorFavorAguarde;
	private JLabel load_box_label;
	private JLabel msg_label;
	
	public LoadScreen() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				set_location();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			label = new JLabel("");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/logo-timemoney2.png")));
			label.setBounds(10, 25, 430, 123);
			contentPane.add(label);
		}
		{
			lblPorFavorAguarde = new JLabel("Por favor aguarde um momento...");
			lblPorFavorAguarde.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblPorFavorAguarde.setHorizontalAlignment(SwingConstants.CENTER);
			lblPorFavorAguarde.setBounds(10, 159, 414, 14);
			contentPane.add(lblPorFavorAguarde);
		}
		{
			load_box_label = new JLabel("");
			load_box_label.setHorizontalAlignment(SwingConstants.CENTER);
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/0p.png")));
			load_box_label.setBounds(10, 184, 414, 20);
			contentPane.add(load_box_label);
		}
		{
			msg_label = new JLabel("Mensagem...");
			msg_label.setFont(new Font("Tahoma", Font.BOLD, 11));
			msg_label.setHorizontalAlignment(SwingConstants.CENTER);
			msg_label.setBounds(10, 215, 414, 14);
			contentPane.add(msg_label);
		}
		setUndecorated(true);  
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
//		this.dm = dm;
//		this.username = user;
	}
	
	public LoadScreen(Funcionario segue, boolean selected, boolean selected2) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				set_location();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			label = new JLabel("");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/logo-timemoney2.png")));
			label.setBounds(10, 25, 430, 123);
			contentPane.add(label);
		}
		{
			lblPorFavorAguarde = new JLabel("Por favor aguarde um momento...");
			lblPorFavorAguarde.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblPorFavorAguarde.setHorizontalAlignment(SwingConstants.CENTER);
			lblPorFavorAguarde.setBounds(10, 159, 414, 14);
			contentPane.add(lblPorFavorAguarde);
		}
		{
			load_box_label = new JLabel("");
			load_box_label.setHorizontalAlignment(SwingConstants.CENTER);
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/0p.png")));
			load_box_label.setBounds(10, 184, 414, 20);
			contentPane.add(load_box_label);
		}
		{
			msg_label = new JLabel("Mensagem...");
			msg_label.setFont(new Font("Tahoma", Font.BOLD, 11));
			msg_label.setHorizontalAlignment(SwingConstants.CENTER);
			msg_label.setBounds(10, 215, 414, 14);
			contentPane.add(msg_label);
		}
		setUndecorated(true);  
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		
	}

	private void set_location(){
		this.setLocationRelativeTo(null);
	}
	
	public void set_load(String message, int load_int){
		msg_label.setText(message);
		if (load_int < 10)
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/0p.png")));
		else if (load_int < 20 && load_int >= 10)
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/10p.png")));
		else if (load_int < 30 && load_int >= 20)
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/20p.png")));
		else if (load_int < 40 && load_int >= 30)
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/30p.png")));
		else if (load_int < 50 && load_int >= 40)
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/40p.png")));
		else if (load_int < 60 && load_int >= 50)
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/50p.png")));
		else if (load_int < 70 && load_int >= 60)
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/60p.png")));
		else if (load_int < 80 && load_int >= 70)
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/70p.png")));
		else if (load_int < 90 && load_int >= 80)
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/80p.png")));
		else if (load_int < 100 && load_int >= 90)
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/90p.png")));
		else
			load_box_label.setIcon(new ImageIcon(LoadScreen.class.getResource("/TimeNMoney/100p.png")));
	}
	
}
