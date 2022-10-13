package t1.aev1;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;


public class Vista {

	private JFrame frame;
	private JTextField textField_Buscar;
	private JTextField textField_Reemplazar;
	private JTextField textFieldRuta;

	private JButton btnBuscar;
	private JButton btnAbrir;
	private JButton btnGuardar;
	private JButton btnRenombrar;
	private JButton btnSuprimir;
	private JButton btnCopiar;
	private JButton btnEditar;

	private JTextArea textArea_Original;
	private JTextArea textArea_Modificado;
	private JTextField textFieldGuardar;

	public Vista() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 799, 662);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane_Original = new JScrollPane();
		scrollPane_Original.setBounds(22, 20, 743, 222);
		frame.getContentPane().add(scrollPane_Original);

		textArea_Original = new JTextArea();
		textArea_Original.setLineWrap(true);
		textArea_Original.setRows(12);
		scrollPane_Original.setColumnHeaderView(textArea_Original);
		scrollPane_Original.getViewport().setView(textArea_Original);

		textField_Buscar = new JTextField();
		textField_Buscar.setBounds(45, 307, 177, 27);
		frame.getContentPane().add(textField_Buscar);
		textField_Buscar.setColumns(10);

		textField_Reemplazar = new JTextField();
		textField_Reemplazar.setColumns(10);
		textField_Reemplazar.setBounds(421, 252, 177, 27);
		frame.getContentPane().add(textField_Reemplazar);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscar.setBounds(232, 307, 120, 27);
		frame.getContentPane().add(btnBuscar);

		JScrollPane scrollPane_Modificado = new JScrollPane();
		scrollPane_Modificado.setBounds(22, 410, 743, 186);
		frame.getContentPane().add(scrollPane_Modificado);

		textArea_Modificado = new JTextArea();
		textArea_Modificado.setLineWrap(true);
		textArea_Modificado.setRows(10);
		scrollPane_Modificado.setColumnHeaderView(textArea_Modificado);
		scrollPane_Modificado.getViewport().setView(textArea_Modificado);

		textFieldRuta = new JTextField();
		textFieldRuta.setBounds(45, 253, 177, 26);
		frame.getContentPane().add(textFieldRuta);
		textFieldRuta.setColumns(10);

		btnAbrir = new JButton("Abrir");
		btnAbrir.setBounds(232, 253, 120, 27);
		frame.getContentPane().add(btnAbrir);

		textFieldGuardar = new JTextField();
		textFieldGuardar.setBounds(421, 310, 177, 24);
		frame.getContentPane().add(textFieldGuardar);
		textFieldGuardar.setColumns(10);

		btnRenombrar = new JButton("Renombrar");
		btnRenombrar.setBounds(608, 253, 120, 27);
		frame.getContentPane().add(btnRenombrar);

		btnCopiar = new JButton("Copiar");
		btnCopiar.setBounds(348, 355, 120, 27);
		frame.getContentPane().add(btnCopiar);

		btnSuprimir = new JButton("Suprimir");
		btnSuprimir.setBounds(478, 355, 120, 27);
		frame.getContentPane().add(btnSuprimir);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(608, 309, 120, 27);
		frame.getContentPane().add(btnGuardar);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(608, 355, 120, 27);
		frame.getContentPane().add(btnEditar);

		this.frame.setVisible(true);
	}

	// GETTERS

	public JButton getBtnAbrir() {
		return btnAbrir;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnRenombrar() {
		return btnRenombrar;
	}

	public JButton getBtnSuprimir() {
		return btnSuprimir;
	}

	public JButton getBtnCopiar() {
		return btnCopiar;
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public JTextField getTextFieldRuta() {
		return textFieldRuta;
	}

	public JTextField getTextFieldBuscar() {
		return textField_Buscar;
	}

	public JTextField getTextFieldReemplazar() {
		return textField_Reemplazar;
	}

	public JTextField getTextFieldGuardar() {
		return textFieldGuardar;
	}

	public JTextArea getTextAreaOriginal() {
		return textArea_Original;
	}

	public JTextArea getTextAreaModificado() {
		return textArea_Modificado;
	}
}