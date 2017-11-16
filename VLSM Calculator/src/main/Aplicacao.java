package main;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import main.ControlIp;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class Aplicacao {

	private JFrame frame;
	private JTextField textField_1;
	private JTable hosts;
	
	ControlIp control = ControlIp.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Aplicacao window = new Aplicacao();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Aplicacao() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 644, 433);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
//		txtIpBase = new JTextField();
		MaskFormatter F_Mascara = new MaskFormatter();
		try {
			F_Mascara.setMask("###.###.###.###"); // Atribui a mascara
			F_Mascara.setPlaceholderCharacter('0');
		} catch (Exception excecao) {
			excecao.printStackTrace();
		}
		JFormattedTextField txtIpBase = new JFormattedTextField(F_Mascara);
		
		txtIpBase.setToolTipText("");
		txtIpBase.setBounds(10, 11, 204, 20);
		frame.getContentPane().add(txtIpBase);
		txtIpBase.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 215, 99, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Quantidade");
		hosts = new JTable(model);
		hosts.setBounds(10, 42, 204, 162);
		frame.getContentPane().add(hosts);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(10, 249, 99, 20);
		frame.getContentPane().add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(10, 283, 99, 20);
		frame.getContentPane().add(spinner_1);
		
		JTextArea logUser = new JTextArea();
		logUser.setBounds(224, 11, 394, 292);
		frame.getContentPane().add(logUser);

		JButton btnNewButton = new JButton("Adicionar");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String quantidadeHost = textField_1.getText();
				model.addRow(new Object[]{quantidadeHost});
			}
		});
		btnNewButton.setBounds(119, 214, 95, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnCalcular = new JButton("Subnet");
		btnCalcular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String ipBase = txtIpBase.getText();
				Integer subnet = (Integer) spinner.getValue();
				
				String ipBinary = control.avaliaIP(ipBase, subnet, null);
				logUser.append(ipBinary);
//				logUser.append(control.discoverClass(ipBase));
//				logUser.append(control.ipToBinary(ipBase));
			}
		});
		btnCalcular.setBounds(119, 248, 95, 23);
		frame.getContentPane().add(btnCalcular);
		
		JButton btnCalcularPorHosts = new JButton("Hosts");
		btnCalcularPorHosts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ipBase = txtIpBase.getText();
				Integer hosts = (Integer) spinner_1.getValue();
				
				String ipBinary = control.avaliaIP(ipBase, null, hosts);
				logUser.append(ipBinary);
			}
		});
		btnCalcularPorHosts.setBounds(119, 282, 95, 23);
		frame.getContentPane().add(btnCalcularPorHosts);
		
		
		
	}
}
