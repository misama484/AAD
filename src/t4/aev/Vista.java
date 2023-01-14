package t4.aev;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;

public class Vista extends JFrame {

	private JFrame frmAevTAad;
	private JButton btnConectar;
	private JButton btnDesconectar;
	private JButton btnMostrarBD;
	private JButton btnBorrarLibro;

	private JLabel lblTitulo;
	private JLabel lblEstado;
	private JButton btnAnyadirLibro;
	private JScrollPane scrollPane;
	private JTextArea textAreaPrincipal;
	private JButton btnEditarLibro;
	private JScrollPane scrollPane_1;
	private JTextArea textAreaTablas;
	private JButton btnBuscarLibro;

	public Vista() {
		Initialize();
	}

	private void Initialize() {

		frmAevTAad = new JFrame();
		frmAevTAad.setTitle("AEV3 T4 AAD");
		frmAevTAad.setResizable(false);
		frmAevTAad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAevTAad.setBounds(100, 100, 1220, 820);
		frmAevTAad.getContentPane().setLayout(null);

		btnConectar = new JButton("CONECTAR");
		btnConectar.setBounds(1038, 108, 138, 59);
		btnConectar.setBackground(new Color(153, 255, 204));
		frmAevTAad.getContentPane().add(btnConectar);

		btnDesconectar = new JButton("DESCONECTAR");
		btnDesconectar.setBounds(1038, 187, 138, 59);
		btnDesconectar.setBackground(new Color(255, 153, 153));
		frmAevTAad.getContentPane().add(btnDesconectar);

		btnMostrarBD = new JButton("MOSTRAR LIBROS");
		btnMostrarBD.setBounds(1038, 705, 138, 23);
		frmAevTAad.getContentPane().add(btnMostrarBD);

		lblEstado = new JLabel("CONECTADO CON BD LIBROS EN 127.0.0.0 USUARIO: XXXX");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEstado.setBounds(21, 747, 980, 23);
		frmAevTAad.getContentPane().add(lblEstado);

		lblTitulo = new JLabel("AEV3 T4 AAD Gestion de Biblioteca - MongoDB");
		lblTitulo.setBounds(0, 11, 1204, 38);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		frmAevTAad.getContentPane().add(lblTitulo);

		btnAnyadirLibro = new JButton("LIBRO NUEVO");
		btnAnyadirLibro.setBounds(1038, 674, 138, 23);
		btnAnyadirLibro.setFont(new Font("Tahoma", Font.PLAIN, 10));
		frmAevTAad.getContentPane().add(btnAnyadirLibro);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 76, 980, 621);
		frmAevTAad.getContentPane().add(scrollPane);

		textAreaPrincipal = new JTextArea();
		textAreaPrincipal.setLineWrap(true);
		textAreaPrincipal.setEditable(false);
		scrollPane.setViewportView(textAreaPrincipal);

		btnEditarLibro = new JButton("EDITAR LIBRO");
		btnEditarLibro.setBounds(1038, 640, 138, 23);
		btnEditarLibro.setFont(new Font("Tahoma", Font.PLAIN, 10));
		frmAevTAad.getContentPane().add(btnEditarLibro);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(1038, 278, 138, 73);
		frmAevTAad.getContentPane().add(scrollPane_1);

		textAreaTablas = new JTextArea();
		textAreaTablas.setLineWrap(true);
		scrollPane_1.setViewportView(textAreaTablas);
		
		btnBuscarLibro = new JButton("BUSCAR LIBRO");
		btnBuscarLibro.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnBuscarLibro.setBounds(1038, 605, 138, 23);
		frmAevTAad.getContentPane().add(btnBuscarLibro);
		
		btnBorrarLibro = new JButton("BORRAR LIBRO");
		btnBorrarLibro.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnBorrarLibro.setBounds(1038, 571, 138, 23);
		frmAevTAad.getContentPane().add(btnBorrarLibro);

		this.frmAevTAad.setVisible(true);
	}

	// GETTERS


	public JTextArea getTextAreaPrincipal() {
		return textAreaPrincipal;
	}

	public JTextArea getTextAreaTablas() {
		return textAreaTablas;
	}

	public JButton getBtnEditarLibro() {
		return btnEditarLibro;
	}

	public JButton getBtnConectar() {
		return btnConectar;
	}

	public JButton getBtnDesconectar() {
		return btnDesconectar;
	}

	public JButton getBtnMostrarBD() {
		return btnMostrarBD;
	}

	public JButton getBtnAnyadirLibro() {
		return btnAnyadirLibro;
	}

	public JLabel getLblTitulo() {
		return lblTitulo;
	}

	public JLabel getLblEstado() {
		return lblEstado;
	}

	public void setLblEstado(JLabel lblEstado) {
		this.lblEstado = lblEstado;
	}

	public JButton getBtnBuscarLibro() {
		return btnBuscarLibro;
	}

	public JButton getBtnBorrarLibro() {
		return btnBorrarLibro;
	}
}
