package TimeNMoney;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;

public class Menu_atualizacao_dados extends JFrame {

	private JPanel contentPane;
	private JLabel lblMenuAtualizarDados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu_atualizacao_dados frame = new Menu_atualizacao_dados();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu_atualizacao_dados() {
		initComponents();
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblMenuAtualizarDados = new JLabel("Menu Atualizar Dados");
		lblMenuAtualizarDados.setIcon(new ImageIcon(Menu_atualizacao_dados.class.getResource("/TimeNMoney/receive_icon.png")));
		lblMenuAtualizarDados.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMenuAtualizarDados.setBounds(10, 11, 414, 30);
		contentPane.add(lblMenuAtualizarDados);
	}
}
