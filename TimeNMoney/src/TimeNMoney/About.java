package TimeNMoney;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class About extends JFrame {

	private JPanel contentPane;

		public About(String version) {
	        
			setResizable(false);
			setTitle("ODKAS - Time &  Money");
			setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/TimeNMoney/odkas.tnm.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(About.class.getResource("/TimeNMoney/logo-timemoney2.png")));
		label.setBounds(10, 57, 404, 101);
		panel.add(label);
		
		JLabel lblVerso = new JLabel("Vers\u00E3o: " + version);
		lblVerso.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVerso.setHorizontalAlignment(SwingConstants.CENTER);
		lblVerso.setBounds(10, 189, 404, 14);
		panel.add(lblVerso);
		this.setLocationRelativeTo(null);
	}
}
