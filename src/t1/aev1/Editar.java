package t1.aev1;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;

public class Editar {
	
	private JFrame frame;
	private JButton btnEditGuardar, btnBuscar, btnReemplazar;
	private JTextField textFieldBuscar;
	private JTextField textFieldReemplazar;
	private JEditorPane editorPane;

	public Editar() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 799, 662);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane_Original = new JScrollPane();
		scrollPane_Original.setBounds(10, 20, 755, 387);
		frame.getContentPane().add(scrollPane_Original);
		
		editorPane = new JEditorPane();
		scrollPane_Original.setViewportView(editorPane);
		
		btnEditGuardar = new JButton("Guardar");
		btnEditGuardar.setBounds(645, 445, 120, 27);
		frame.getContentPane().add(btnEditGuardar);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(515, 445, 120, 27);
		frame.getContentPane().add(btnBuscar);
		
		textFieldBuscar = new JTextField();
		textFieldBuscar.setText("Introduzca palabra a buscar");
		textFieldBuscar.setBounds(305, 445, 200, 27);
		frame.getContentPane().add(textFieldBuscar);
		textFieldBuscar.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Edite el texto en el panel de arriba");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(276, 418, 229, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textFieldReemplazar = new JTextField();
		textFieldReemplazar.setBounds(305, 483, 200, 27);
		frame.getContentPane().add(textFieldReemplazar);
		textFieldReemplazar.setColumns(10);
		
		btnReemplazar = new JButton("Reemplazar");
		btnReemplazar.setBounds(515, 483, 120, 27);
		frame.getContentPane().add(btnReemplazar);
		
		
		
		
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
	
	public JTextField getTextFieldBuscar() {
		return textFieldBuscar;
	}
	
	public JTextField getTextFieldReemplazar() {
		return textFieldReemplazar;
	}
}
