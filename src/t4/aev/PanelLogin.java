package t4.aev;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Window.Type;

public class PanelLogin extends JFrame {

	private JFrame frame;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textPassword;
	private JTextField textUser;
	private JButton btnAceptar, btnCancelar;
	private JLabel luser, lpassword;

	public PanelLogin() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Login");
		
		frame.setSize(340, 300);
		frame.getContentPane().setLayout(null);
		frame.setBounds(500, 350, 330, 300);
		
		textUser = new JTextField();
		luser = new JLabel("Usuario");
		luser.setBounds(22, 70, 80, 30);
		textUser.setBounds(97, 70, 207, 30);
		frame.getContentPane().add(textUser);
		frame.getContentPane().add(luser);
		
		lpassword = new JLabel("Contrasenya");
		lpassword.setBounds(22, 111, 80, 30);
		passwordField.setBounds(102, 111, 193, 30);
		frame.getContentPane().add(passwordField);
		frame.getContentPane().add(lpassword);
		
		
		textPassword = new JTextField();
		textPassword.setBounds(97, 111, 207, 30);
		frame.getContentPane().add(textPassword);
		textPassword.setColumns(10);
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(64, 181, 89, 23);
		frame.getContentPane().add(btnAceptar);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(163, 181, 89, 23);
		frame.getContentPane().add(btnCancelar);
		
		JLabel lblNewLabel = new JLabel("Inicie sesion para acceder a la base de datos.");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(22, 23, 228, 14);
		frame.getContentPane().add(lblNewLabel);
	}
	
	public void setVisible(boolean b) {
		this.frame.setVisible(b);
	}
	
	//GETTERS

	public JTextField getTextUser() {
		return textUser;
	}
	
	public JTextField getTextPassword() {
		return textPassword;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	
	
}
