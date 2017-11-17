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
import main.Control;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class Aplicacao {

	private JFrame frmVlsmCalculator;
	private JTextField textField_1;
	private JTable hosts;
	
	Control control = Control.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Aplicacao window = new Aplicacao();
					window.frmVlsmCalculator.setVisible(true);
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
		frmVlsmCalculator = new JFrame();
		frmVlsmCalculator.setTitle("VLSM Calculator");
		frmVlsmCalculator.setBounds(100, 100, 748, 451);
		frmVlsmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVlsmCalculator.getContentPane().setLayout(null);
		
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
		txtIpBase.setBounds(10, 13, 204, 20);
		frmVlsmCalculator.getContentPane().add(txtIpBase);
		txtIpBase.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 277, 99, 20);
		frmVlsmCalculator.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Quantidade");
		model.addColumn("Quantidade + 2");
		model.addColumn("ProxPotencia");
		hosts = new JTable(model);
		hosts.setBounds(10, 44, 204, 222);
		frmVlsmCalculator.getContentPane().add(hosts);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(10, 311, 99, 20);
		frmVlsmCalculator.getContentPane().add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(10, 345, 99, 20);
		frmVlsmCalculator.getContentPane().add(spinner_1);
		
		JTextArea logUser = new JTextArea();
		logUser.setAutoscrolls(false);
		logUser.setEditable(false);
		logUser.setBounds(224, 11, 498, 390);
		frmVlsmCalculator.getContentPane().add(logUser);

		JButton btnNewButton = new JButton("Adicionar");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String quantidadeHost = textField_1.getText();
				int hostMais2 = Integer.parseInt(quantidadeHost) + 2;
				model.addRow(new Object[]{quantidadeHost, hostMais2, control.nextPowerOf2(hostMais2)});
			}
		});
		btnNewButton.setBounds(119, 276, 95, 23);
		frmVlsmCalculator.getContentPane().add(btnNewButton);
		
		JButton btnCalcular = new JButton("Subnet");
		btnCalcular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String ipBase = txtIpBase.getText();
				Integer subnet = (Integer) spinner.getValue();
				
				String ipBinary = control.avaliaIP(ipBase, subnet, null);
				logUser.setText("");
				logUser.append(ipBinary);
			}
		});
		btnCalcular.setBounds(119, 310, 95, 23);
		frmVlsmCalculator.getContentPane().add(btnCalcular);
		
		JButton btnCalcularPorHosts = new JButton("Hosts");
		btnCalcularPorHosts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ipBase = txtIpBase.getText();
				Integer hosts = (Integer) spinner_1.getValue();
				
				String ipBinary = control.avaliaIP(ipBase, null, hosts);
				logUser.setText("");
				logUser.append(ipBinary);
			}
		});
		btnCalcularPorHosts.setBounds(119, 344, 95, 23);
		frmVlsmCalculator.getContentPane().add(btnCalcularPorHosts);
		
		JButton btnSubnetHosts = new JButton("Subnet e Hosts");
		btnSubnetHosts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String ipBase = txtIpBase.getText();
				Integer hosts = control.maiorPotencia(model);
				Integer subnet = model.getRowCount();
				
				String ipBinary = control.avaliaIP(ipBase, subnet, hosts);
				logUser.setText("");
				logUser.append(ipBinary);
			}
		});
		btnSubnetHosts.setBounds(10, 378, 204, 23);
		frmVlsmCalculator.getContentPane().add(btnSubnetHosts);
		
		
		
	}
}
