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
import javax.swing.JComboBox;
import java.awt.Color;

public class Buscar extends JFrame {

	private JFrame frmEditarLibro;
	private JButton btnBuscar, btnCerrar, btnBorrarCampos, btnBorrarLibro;
	private JEditorPane editorPane;
	private JTextField textFieldCampoBusqueda;
	private JComboBox comboBox;
	private JLabel lblCampo;
	private JComboBox comboBoxCriterio;
	private JLabel lblMayorMenor;
	private JButton btnBuscarCriterio;

	public Buscar() {
		initialize();
	}
	
	private void initialize() {
		frmEditarLibro = new JFrame();
		frmEditarLibro.setTitle("Editar Libro");
		frmEditarLibro.setBounds(100, 100, 799, 370);
		frmEditarLibro.getContentPane().setLayout(null);
		
		JScrollPane scrollPane_Original = new JScrollPane();
		scrollPane_Original.setBounds(10, 20, 755, 154);
		frmEditarLibro.getContentPane().add(scrollPane_Original);
		
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setText("Seleccione un campo de busqueda");
		scrollPane_Original.setViewportView(editorPane);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(370, 208, 120, 27);
		frmEditarLibro.getContentPane().add(btnBuscar);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(630, 208, 120, 27);
		frmEditarLibro.getContentPane().add(btnCerrar);
		
		textFieldCampoBusqueda = new JTextField();
		textFieldCampoBusqueda.setColumns(10);
		textFieldCampoBusqueda.setBounds(160, 208, 200, 27);
		frmEditarLibro.getContentPane().add(textFieldCampoBusqueda);
		
		btnBorrarCampos = new JButton("Borrar Campos");
		btnBorrarCampos.setBounds(500, 208, 120, 27);
		frmEditarLibro.getContentPane().add(btnBorrarCampos);
		
		comboBox = new JComboBox();
		comboBox.setBounds(10, 210, 120, 22);
		frmEditarLibro.getContentPane().add(comboBox);
		
		lblCampo = new JLabel("Campo");
		lblCampo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCampo.setBounds(49, 185, 46, 14);
		frmEditarLibro.getContentPane().add(lblCampo);
		
		btnBorrarLibro = new JButton("Borrar Libro");
		btnBorrarLibro.setBackground(new Color(255, 91, 91));
		btnBorrarLibro.setBounds(630, 278, 120, 27);
		frmEditarLibro.getContentPane().add(btnBorrarLibro);
		
		comboBoxCriterio = new JComboBox();
		comboBoxCriterio.setBounds(10, 280, 120, 22);
		frmEditarLibro.getContentPane().add(comboBoxCriterio);
		
		lblMayorMenor = new JLabel("Criterio de busqueda");
		lblMayorMenor.setHorizontalAlignment(SwingConstants.CENTER);
		lblMayorMenor.setBounds(10, 255, 120, 14);
		frmEditarLibro.getContentPane().add(lblMayorMenor);
		
		btnBuscarCriterio = new JButton("Buscar con criterio de busqueda");
		btnBuscarCriterio.setBounds(160, 278, 185, 27);
		frmEditarLibro.getContentPane().add(btnBuscarCriterio);
		
		
		
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		this.frmEditarLibro.setVisible(b);
	}

	public JFrame getFrmEditarLibro() {
		return frmEditarLibro;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public JButton getBtnBorrarCampos() {
		return btnBorrarCampos;
	}

	public JEditorPane getEditorPane() {
		return editorPane;
	}

	public JTextField getTextFieldCampoBusqueda() {
		return textFieldCampoBusqueda;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public void setFrmEditarLibro(JFrame frmEditarLibro) {
		this.frmEditarLibro = frmEditarLibro;
	}

	public JButton getBtnBorrarLibro() {
		return btnBorrarLibro;
	}

	public JButton getBtnBuscarCriterio() {
		return btnBuscarCriterio;
	}

	public JComboBox getComboBoxCriterio() {
		return comboBoxCriterio;
	}

}
