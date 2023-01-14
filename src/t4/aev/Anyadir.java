package t4.aev;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;

public class Anyadir {
	
	private JFrame frame;
	private JButton btnGuardar, btnCerrar, btnImagen;
	private JEditorPane editorPane;
	private JTextField textFieldTitulo;
	private JTextField textFieldAutor;
	private JTextField textFieldEditorial;
	private JTextField textFieldAnyoNac;
	private JTextField textFieldAnyoPub;
	private JTextField textFieldNumPaginas;
	private JLabel lblImagenTitulo, lblImagen;
	private JTextField textFieldImagen;

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
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(631, 570, 120, 27);
		frame.getContentPane().add(btnGuardar);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(501, 570, 120, 27);
		frame.getContentPane().add(btnCerrar);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 307, 82, 14);
		frame.getContentPane().add(lblTitulo);
		
		btnImagen = new JButton("SELECCIONAR IMAGEN");
		btnImagen.setBounds(359, 459, 145, 27);
		frame.getContentPane().add(btnImagen);
		
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
		textFieldEditorial.setBounds(102, 453, 200, 27);
		frame.getContentPane().add(textFieldEditorial);
		
		JLabel lblEditorial = new JLabel("Editorial");
		lblEditorial.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditorial.setBounds(10, 459, 82, 14);
		frame.getContentPane().add(lblEditorial);
		
		JLabel lblAnyoNac = new JLabel("Anyo Nacimineto");
		lblAnyoNac.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnyoNac.setBounds(0, 383, 102, 14);
		frame.getContentPane().add(lblAnyoNac);
		
		textFieldAnyoNac = new JTextField();
		textFieldAnyoNac.setColumns(10);
		textFieldAnyoNac.setBounds(102, 377, 200, 27);
		frame.getContentPane().add(textFieldAnyoNac);
		
		JLabel lblAnyoPub = new JLabel("Anyo Publicacion");
		lblAnyoPub.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnyoPub.setBounds(0, 421, 102, 14);
		frame.getContentPane().add(lblAnyoPub);
		
		textFieldAnyoPub = new JTextField();
		textFieldAnyoPub.setColumns(10);
		textFieldAnyoPub.setBounds(102, 415, 200, 27);
		frame.getContentPane().add(textFieldAnyoPub);
		
		JLabel lblNumPaginas = new JLabel("Num. Paginas");
		lblNumPaginas.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumPaginas.setBounds(10, 497, 82, 14);
		frame.getContentPane().add(lblNumPaginas);
		
		textFieldNumPaginas = new JTextField();
		textFieldNumPaginas.setColumns(10);
		textFieldNumPaginas.setBounds(102, 491, 200, 27);
		frame.getContentPane().add(textFieldNumPaginas);
		
		lblImagenTitulo = new JLabel("Imagen");
		lblImagenTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagenTitulo.setBounds(392, 426, 82, 14);
		frame.getContentPane().add(lblImagenTitulo);
		
		textFieldImagen = new JTextField();
		textFieldImagen.setColumns(10);
		textFieldImagen.setBounds(102, 553, 128, 36);
		frame.getContentPane().add(textFieldImagen);
		
		lblImagen = new JLabel("Imagen");
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagen.setBounds(525, 307, 200, 252);
		frame.getContentPane().add(lblImagen);
		
		
		
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		this.frame.setVisible(b);
	}
	
	public JEditorPane getEditorPane() {
		return editorPane;
	}
	
	public JButton getButtonBorrar() {
		return btnCerrar;
	}
	
	public JButton getBtnGuardar() {
		return btnGuardar;
	}
	
	public JButton getBtnImagen() {
		return btnImagen;
	}
	
	public JButton getBtnBuscar() {
		return btnCerrar;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public JTextField getTextFieldTitulo() {
		return textFieldTitulo;
	}

	public JTextField getTextFieldAutor() {
		return textFieldAutor;
	}

	public JTextField getTextFieldEditorial() {
		return textFieldEditorial;
	}

	public JTextField getTextFieldAnyoNac() {
		return textFieldAnyoNac;
	}

	public JTextField getTextFieldAnyoPub() {
		return textFieldAnyoPub;
	}

	public JTextField getTextFieldNumPaginas() {
		return textFieldNumPaginas;
	}

	public JTextField getTextFieldImagen() {
		return textFieldImagen;
	}

	public void setTextFieldImagen(JTextField textFieldImagen) {
		this.textFieldImagen = textFieldImagen;
	}

	public void setEditorPane(JEditorPane editorPane) {
		this.editorPane = editorPane;
	}

	public JLabel getLblImagen() {
		return lblImagen;
	}

}
