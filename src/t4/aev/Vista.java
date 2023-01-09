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

public class Vista extends JFrame {

	private JFrame frame;
	private JTextField textFieldRuatXml;
	private JTextField textFieldSql;

	private JButton btnCargaJSON;
	private JButton btnConectar;
	private JButton btnDesconectar;
	private JButton btnSql;

	private JLabel lblTitulo;
	private JLabel lblSql;
	private JLabel lblEstado;
	private JButton btnMostrarTablas;
	private JScrollPane scrollPane;
	private JTextArea textAreaPrincipal;
	private JButton btnMostrarCampos;
	private JTextField textFieldTabla;
	private JLabel lblNombreDeTabla;
	private JScrollPane scrollPane_1;
	private JTextArea textAreaTablas;

	public Vista() {
		initialize();
	}

	private void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1220, 820);
		frame.getContentPane().setLayout(null);

		textFieldRuatXml = new JTextField();
		textFieldRuatXml.setBounds(21, 60, 980, 26);
		frame.getContentPane().add(textFieldRuatXml);
		textFieldRuatXml.setColumns(10);

		btnCargaJSON = new JButton("CARGAR JSON");
		btnCargaJSON.setBounds(1038, 62, 138, 23);
		frame.getContentPane().add(btnCargaJSON);

		btnConectar = new JButton("CONECTAR");
		btnConectar.setBounds(1038, 108, 138, 59);
		btnConectar.setBackground(new Color(153, 255, 204));
		frame.getContentPane().add(btnConectar);

		btnDesconectar = new JButton("DESCONECTAR");
		btnDesconectar.setBounds(1038, 187, 138, 59);
		btnDesconectar.setBackground(new Color(255, 153, 153));
		frame.getContentPane().add(btnDesconectar);

		textFieldSql = new JTextField();
		textFieldSql.setBounds(21, 703, 980, 26);
		textFieldSql.setColumns(10);
		frame.getContentPane().add(textFieldSql);

		btnSql = new JButton("ENVIAR CONSULTA");
		btnSql.setBounds(1038, 705, 138, 23);
		frame.getContentPane().add(btnSql);

		lblEstado = new JLabel("CONECTADO CON BD LIBROS EN 127.0.0.0 USUARIO: XXXX");
		lblEstado.setBounds(21, 760, 980, 14);
		frame.getContentPane().add(lblEstado);

		lblSql = new JLabel("Introduzca la consulta Sql que desea enviar");
		lblSql.setBounds(21, 678, 980, 14);
		frame.getContentPane().add(lblSql);

		lblTitulo = new JLabel("AEV2 T2 AAD Gestion de Biblioteca");
		lblTitulo.setBounds(0, 24, 1204, 14);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblTitulo);

		btnMostrarTablas = new JButton("MOSTRAR TABLAS BD");
		btnMostrarTablas.setBounds(1038, 674, 138, 23);
		btnMostrarTablas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		frame.getContentPane().add(btnMostrarTablas);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 98, 980, 569);
		frame.getContentPane().add(scrollPane);

		textAreaPrincipal = new JTextArea();
		textAreaPrincipal.setLineWrap(true);
		scrollPane.setViewportView(textAreaPrincipal);

		btnMostrarCampos = new JButton("MOSTRAR CAMPOS");
		btnMostrarCampos.setBounds(1038, 640, 138, 23);
		btnMostrarCampos.setFont(new Font("Tahoma", Font.PLAIN, 10));
		frame.getContentPane().add(btnMostrarCampos);

		textFieldTabla = new JTextField();
		textFieldTabla.setBounds(1038, 605, 138, 26);
		textFieldTabla.setColumns(10);
		frame.getContentPane().add(textFieldTabla);

		lblNombreDeTabla = new JLabel("Nombre de tabla");
		lblNombreDeTabla.setBounds(1038, 592, 138, 14);
		lblNombreDeTabla.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNombreDeTabla);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(1038, 257, 138, 183);
		frame.getContentPane().add(scrollPane_1);

		textAreaTablas = new JTextArea();
		textAreaTablas.setLineWrap(true);
		scrollPane_1.setViewportView(textAreaTablas);

		this.frame.setVisible(true);
	}

	// GETTERS

	public JTextField getTextFieldRuatXml() {
		return textFieldRuatXml;
	}

	public JTextArea getTextAreaPrincipal() {
		return textAreaPrincipal;
	}

	public JTextArea getTextAreaTablas() {
		return textAreaTablas;
	}

	public JTextField getTextFieldSql() {
		return textFieldSql;
	}

	public JTextField getTextFieldTabla() {
		return textFieldTabla;
	}

	public JButton getBtnMostrarCampos() {
		return btnMostrarCampos;
	}

	public JButton getBtnCargaJson() {
		return btnCargaJSON;
	}

	public JButton getBtnConectar() {
		return btnConectar;
	}

	public JButton getBtnDesconectar() {
		return btnDesconectar;
	}

	public JButton getBtnSql() {
		return btnSql;
	}

	public JButton getBtnMostrarTablas() {
		return btnMostrarTablas;
	}

	public JLabel getLblTitulo() {
		return lblTitulo;
	}

	public JLabel getLblSql() {
		return lblSql;
	}

	public JLabel getLblEstado() {
		return lblEstado;
	}
}
