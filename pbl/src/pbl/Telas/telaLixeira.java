package pbl.Telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.Box;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class telaLixeira extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					telaLixeira frame = new telaLixeira();
					frame.setVisible(true);
					//frame.btnEsvaziar.setActionCommand(spinner.setValue(0));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public telaLixeira() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(156, 80, 110, 33);
		contentPane.add(spinner);
		
		JButton btnEsvaziar = new JButton("Esvaziar");
		btnEsvaziar.setForeground(Color.RED);
		btnEsvaziar.setBounds(314, 11, 110, 33);
		contentPane.add(btnEsvaziar);		
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(156, 146, 110, 33);
		contentPane.add(spinner_1);
		
		JLabel lblNvelDaLixeira = new JLabel("N\u00EDvel da Lixeira");
		lblNvelDaLixeira.setHorizontalAlignment(SwingConstants.CENTER);
		lblNvelDaLixeira.setBounds(156, 55, 98, 14);
		contentPane.add(lblNvelDaLixeira);
		
		JLabel lblCapacidadeDaLixeira = new JLabel("Capacidade da Lixeira");
		lblCapacidadeDaLixeira.setHorizontalAlignment(SwingConstants.CENTER);
		lblCapacidadeDaLixeira.setBounds(156, 121, 119, 14);
		contentPane.add(lblCapacidadeDaLixeira);
	}
}
