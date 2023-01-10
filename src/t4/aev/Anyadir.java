package t4.aev;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;

public class Anyadir {
	
	private JFrame frame;
	private JButton btnEditGuardar, btnBuscar, btnReemplazar;
	private JEditorPane editorPane;
	private JTextField textFieldTitulo;
	private JTextField textFieldAutor;
	private JTextField textFieldEditorial;
	private JTextField textField;
	private JTextField textField_1;

	public Anyadir() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Anyadir Libro");
		frame.setBounds(100, 100, 799, 662);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane_Original = new JScrollPane();
		scrollPane_Original.setBounds(10, 20, 755, 225);
		frame.getContentPane().add(scrollPane_Original);
		
		editorPane = new JEditorPane();
		scrollPane_Original.setViewportView(editorPane);
		
		btnEditGuardar = new JButton("Guardar");
		btnEditGuardar.setBounds(645, 445, 120, 27);
		frame.getContentPane().add(btnEditGuardar);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(515, 445, 120, 27);
		frame.getContentPane().add(btnBuscar);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 307, 82, 14);
		frame.getContentPane().add(lblTitulo);
		
		btnReemplazar = new JButton("Reemplazar");
		btnReemplazar.setBounds(515, 483, 120, 27);
		frame.getContentPane().add(btnReemplazar);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setColumns(10);
		textFieldTitulo.setBounds(102, 301, 200, 27);
		frame.getContentPane().add(textFieldTitulo);
		
		textFieldAutor = new JTextField();
		textFieldAutor.setColumns(10);
		textFieldAutor.setBounds(102, 339, 200, 27);
		frame.getContentPane().add(textFieldAutor);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutor.setBounds(10, 345, 82, 14);
		frame.getContentPane().add(lblAutor);
		
		textFieldEditorial = new JTextField();
		textFieldEditorial.setColumns(10);
		textFieldEditorial.setBounds(523, 301, 200, 27);
		frame.getContentPane().add(textFieldEditorial);
		
		JLabel lblEditorial = new JLabel("Editorial");
		lblEditorial.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditorial.setBounds(449, 307, 82, 14);
		frame.getContentPane().add(lblEditorial);
		
		JLabel lblAnyoNac = new JLabel("Anyo Nacimineto");
		lblAnyoNac.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnyoNac.setBounds(0, 383, 102, 14);
		frame.getContentPane().add(lblAnyoNac);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(102, 377, 200, 27);
		frame.getContentPane().add(textField);
		
		JLabel lblAnyoPub = new JLabel("Anyo Publicacion");
		lblAnyoPub.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnyoPub.setBounds(0, 421, 102, 14);
		frame.getContentPane().add(lblAnyoPub);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(102, 415, 200, 27);
		frame.getContentPane().add(textField_1);
		
		
		
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		this.frame.setVisible(b);
	}
	
	public JEditorPane getEditorPane() {
		return editorPane;
	}
	
	public JButton getButtonMostrar() {
		return btnBuscar;
	}
	
	public JButton getBtnEditGuardar() {
		return btnEditGuardar;
	}
	
	public JButton getBtnReemplazar() {
		return btnReemplazar;
	}
	
	public JButton getBtnBuscar() {
		return btnBuscar;
	}
	

}
