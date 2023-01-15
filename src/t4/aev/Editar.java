package t4.aev;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Editar extends JFrame {

	private JFrame frmEditarLibro;
	private JButton btnGuardar, btnCerrar, btnImagen, btnCargarLibro, btnBorrarCampos;
	private JEditorPane editorPane;
	private JTextField textFieldTitulo;
	private JTextField textFieldAutor;
	private JTextField textFieldEditorial;
	private JTextField textFieldAnyoNac;
	private JTextField textFieldAnyoPub;
	private JTextField textFieldNumPaginas;
	private JLabel lblImagenTitulo, lblImagen;
	private JTextField textFieldImagen;
	private JTextField textFieldId;

	public Editar() {
		initialize();
	}
	
	private void initialize() {
		frmEditarLibro = new JFrame();
		frmEditarLibro.setTitle("Editar Libro");
		frmEditarLibro.setBounds(100, 100, 799, 576);
		frmEditarLibro.getContentPane().setLayout(null);
		
		JScrollPane scrollPane_Original = new JScrollPane();
		scrollPane_Original.setBounds(10, 20, 755, 113);
		frmEditarLibro.getContentPane().add(scrollPane_Original);
		
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setText("INTRODUZCA ID DEL LIBRO A EDITAR");
		scrollPane_Original.setViewportView(editorPane);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(645, 445, 120, 27);
		frmEditarLibro.getContentPane().add(btnGuardar);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(515, 445, 120, 27);
		frmEditarLibro.getContentPane().add(btnCerrar);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(36, 211, 82, 14);
		frmEditarLibro.getContentPane().add(lblTitulo);
		
		btnImagen = new JButton("SELECCIONAR IMAGEN");
		btnImagen.setBounds(620, 299, 145, 27);
		frmEditarLibro.getContentPane().add(btnImagen);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setColumns(10);
		textFieldTitulo.setBounds(128, 205, 200, 27);
		frmEditarLibro.getContentPane().add(textFieldTitulo);
		
		textFieldAutor = new JTextField();
		textFieldAutor.setColumns(10);
		textFieldAutor.setBounds(128, 243, 200, 27);
		frmEditarLibro.getContentPane().add(textFieldAutor);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutor.setBounds(36, 249, 82, 14);
		frmEditarLibro.getContentPane().add(lblAutor);
		
		textFieldEditorial = new JTextField();
		textFieldEditorial.setColumns(10);
		textFieldEditorial.setBounds(128, 365, 200, 27);
		frmEditarLibro.getContentPane().add(textFieldEditorial);
		
		JLabel lblEditorial = new JLabel("Editorial");
		lblEditorial.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditorial.setBounds(36, 371, 82, 14);
		frmEditarLibro.getContentPane().add(lblEditorial);
		
		JLabel lblAnyoNac = new JLabel("Anyo Nacimineto");
		lblAnyoNac.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnyoNac.setBounds(26, 287, 102, 14);
		frmEditarLibro.getContentPane().add(lblAnyoNac);
		
		textFieldAnyoNac = new JTextField();
		textFieldAnyoNac.setColumns(10);
		textFieldAnyoNac.setBounds(128, 281, 200, 27);
		frmEditarLibro.getContentPane().add(textFieldAnyoNac);
		
		JLabel lblAnyoPub = new JLabel("Anyo Publicacion");
		lblAnyoPub.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnyoPub.setBounds(26, 325, 102, 14);
		frmEditarLibro.getContentPane().add(lblAnyoPub);
		
		textFieldAnyoPub = new JTextField();
		textFieldAnyoPub.setColumns(10);
		textFieldAnyoPub.setBounds(128, 319, 200, 27);
		frmEditarLibro.getContentPane().add(textFieldAnyoPub);
		
		JLabel lblNumPaginas = new JLabel("Num. Paginas");
		lblNumPaginas.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumPaginas.setBounds(36, 409, 82, 14);
		frmEditarLibro.getContentPane().add(lblNumPaginas);
		
		textFieldNumPaginas = new JTextField();
		textFieldNumPaginas.setColumns(10);
		textFieldNumPaginas.setBounds(128, 403, 200, 27);
		frmEditarLibro.getContentPane().add(textFieldNumPaginas);
		
		lblImagenTitulo = new JLabel("Imagen");
		lblImagenTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagenTitulo.setBounds(36, 445, 82, 14);
		frmEditarLibro.getContentPane().add(lblImagenTitulo);
		
		textFieldImagen = new JTextField();
		textFieldImagen.setColumns(10);
		textFieldImagen.setBounds(128, 440, 200, 27);
		frmEditarLibro.getContentPane().add(textFieldImagen);
		
		textFieldId = new JTextField();
		textFieldId.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldId.setColumns(10);
		textFieldId.setBounds(128, 167, 102, 27);
		frmEditarLibro.getContentPane().add(textFieldId);
		
		JLabel lblId = new JLabel("ID");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setBounds(36, 173, 82, 14);
		frmEditarLibro.getContentPane().add(lblId);
		
		btnCargarLibro = new JButton("CARGAR LIBRO");
		btnCargarLibro.setBounds(262, 167, 120, 27);
		frmEditarLibro.getContentPane().add(btnCargarLibro);
		
		btnBorrarCampos = new JButton("Borrar Campos");
		btnBorrarCampos.setBounds(645, 483, 120, 27);
		frmEditarLibro.getContentPane().add(btnBorrarCampos);
		
		lblImagen = new JLabel("Imagen");
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagen.setBounds(430, 191, 170, 243);
		frmEditarLibro.getContentPane().add(lblImagen);
		
		
		
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		this.frmEditarLibro.setVisible(b);
	}
	
	public JEditorPane getEditorPane() {
		return editorPane;
	}
	
	public JButton getBtnGuardar() {
		return btnGuardar;
	}
	
	public JButton getBtnImagen() {
		return btnImagen;
	}
	
	public JButton getBtnCargarLibro() {
		return btnCargarLibro;
	}
	public JFrame getFrame() {
		return frmEditarLibro;
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

	public JTextField getTextFieldId() {
		return textFieldId;
	}

	public JButton getBtnBorrarCampos() {
		return btnBorrarCampos;
	}

	public JLabel getLblImagen() {
		return lblImagen;
	}

	public void setLblImagen(JLabel lblImagen) {
		this.lblImagen = lblImagen;
	}

	public void setBtnGuardar(JButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public JLabel getLblImagenTitulo() {
		return lblImagenTitulo;
	}

	public JFrame getFrmEditarLibro() {
		return frmEditarLibro;
	}
}
