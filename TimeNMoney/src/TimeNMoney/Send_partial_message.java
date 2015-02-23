package TimeNMoney;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.toedter.calendar.JDateChooser;

public class Send_partial_message extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblEnviarTarefasAt;
	private JButton cancel_btn;
	private JButton ok_btn;
	public int resposta;
	public Date data_resposta;
	private JDateChooser date_chooser;

	public Send_partial_message() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				set_relative();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Send_partial_message.class.getResource("/TimeNMoney/odkas.tnm.png")));
		setTitle("Envio Parcial");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 135);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		lblEnviarTarefasAt = new JLabel("Enviar tarefas até:");
		lblEnviarTarefasAt.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		cancel_btn = new JButton("Cancelar");
		cancel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fechar_janela();
			}
		});
		
		ok_btn = new JButton("OK");
		ok_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ok_btn_action();
			}
		});
		date_chooser = new JDateChooser();
		date_chooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				change_date_action();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(10, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblEnviarTarefasAt, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(date_chooser, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGap(131)
							.addComponent(ok_btn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cancel_btn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblEnviarTarefasAt, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(19)
							.addComponent(date_chooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(ok_btn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(cancel_btn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(1))
		);
		contentPane.setLayout(gl_contentPane);
		init();
	}
	
	@SuppressWarnings("deprecation")
	private void init(){
		Date d = Calendar.getInstance().getTime();
		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(0);
		date_chooser.setDate(d);
		resposta = 0;
		data_resposta = d;
	}
	
	private void fechar_janela(){
		this.dispose();
	}
	
	private void ok_btn_action(){
		resposta = 1;
		this.dispose();
	}
	
	public int get_resposta(){return this.resposta;}
	public Date get_data_resposta(){return this.data_resposta;}
	
	private void set_relative(){
		this.setLocationRelativeTo(null);
	}
	
	private void change_date_action(){
		Date d = date_chooser.getDate();
		data_resposta = d;
	}
}
