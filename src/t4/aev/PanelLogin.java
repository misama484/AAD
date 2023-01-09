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

	private JPanel contentPane;
	private JTextField textField;

	public PanelLogin() {
		initialize();
	}

	private void initialize() {
		JFrame frame = new JFrame();
		frame.setTitle("Login");
		frame.setVisible(false);
		JPasswordField pwd = new JPasswordField();
		frame.setSize(340, 300);
		frame.getContentPane().setLayout(null);
		
		JTextField textUser = new JTextField();
		JLabel luser = new JLabel("Usuario");
		luser.setBounds(22, 70, 80, 30);
		textUser.setBounds(100, 200, 100, 30);
		frame.getContentPane().add(pwd);
		frame.getContentPane().add(luser);
		
		JLabel lpassword = new JLabel("Contrasenya");
		lpassword.setBounds(22, 111, 80, 30);
		pwd.setBounds(102, 111, 193, 30);
		frame.getContentPane().add(pwd);
		frame.getContentPane().add(lpassword);
		
		
		textField = new JTextField();
		textField.setBounds(102, 70, 193, 30);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(64, 181, 89, 23);
		frame.getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(163, 181, 89, 23);
		frame.getContentPane().add(btnCancelar);
		
		JLabel lblNewLabel = new JLabel("Inicie sesion para acceder a la base de datos.");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(22, 23, 228, 14);
		frame.getContentPane().add(lblNewLabel);
	}
}
