package Cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import java.awt.Component;
import javax.swing.Box;
import java.awt.SystemColor;

public class Interfaz extends  JFrame implements ActionListener{

	private JLabel lblFecha, lblPaciente,lblSigVit ;
	private JTextField txtFecha,txtPelicula ;
	private JTextArea txtPeliculasNombre;
	private JButton btnReproducir,btnSalir;
	private Calendar fechaAct;
	private ClienteStream cliente;

	public String texto;


	private ArrayList <String> listaMedico, listaPaciente;
	private JLabel lblIcono;
	private JButton btnBuscar;
	private JLabel lblFondo;
	private JLabel lblColotTitulo2;
	private JLabel lblLogo;


	public Interfaz() {

		super ("FELCOM");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);


		lblFecha = new JLabel("Fecha de Ingreso:");
		lblFecha.setBounds(64, 125, 127, 14);
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblFecha);
		lblPaciente = new JLabel("Pelicula que desea ver:");
		lblPaciente.setBounds(82, 420, 152, 14);
		lblPaciente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblPaciente);
		lblSigVit = new JLabel("Peliculas Disponibles");
		lblSigVit.setBounds(326, 168, 217, 33);
		lblSigVit.setFont(new Font("Tahoma", Font.BOLD, 17));
		getContentPane().add(lblSigVit);

		txtFecha = new JTextField();
		txtFecha.setBounds(201, 122, 107, 20);
		txtFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtFecha.setEditable(false);
		txtFecha.setBackground(new Color(255, 255, 255));
		getContentPane().add(txtFecha);

		fechaAct = Calendar.getInstance();
		String fecha=fechaAct.get(fechaAct.YEAR)+"-"+(fechaAct.get(fechaAct.MONTH)+1)+"-"+fechaAct.get(fechaAct.DATE);
		txtFecha.setText(fecha);

		btnReproducir = new JButton("REPRODUCIR");
		btnReproducir.setIcon(new ImageIcon("src/imagen/P.png"));
		btnReproducir.setBounds(199, 531, 205, 69);
		btnReproducir.setBackground(Color.WHITE);
		btnReproducir.addActionListener(this);
		getContentPane().add(btnReproducir);
		btnSalir = new JButton("SALIR");
		btnSalir.setBounds(735, 612, 89, 23);
		btnSalir.setBackground(Color.WHITE);
		btnSalir.addActionListener(this);
		getContentPane().add(btnSalir);

		lblIcono = new JLabel("");
		lblIcono.setIcon(new ImageIcon(Interfaz.class.getResource("/imagen/Logo.PNG")));
		lblIcono.setBounds(368, 698, 107, 44);
		getContentPane().add(lblIcono);
		ImageIcon imagen = new ImageIcon(getClass().getResource("/imagen/Logo.PNG"));
		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(lblIcono.getWidth(),lblIcono.getHeight(),Image.SCALE_DEFAULT));
		lblIcono.setIcon(icono);
		
		

		txtPelicula = new JTextField();
		txtPelicula.setBackground(Color.LIGHT_GRAY);
		txtPelicula.setBounds(253, 416, 284, 22);
		getContentPane().add(txtPelicula);



		btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cliente = new ClienteStream();
				texto = txtPelicula.getText();
				cliente.enviar(texto);
				System.out.println(texto);
			}
		});
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setBounds(577, 417, 107, 23);
		getContentPane().add(btnBuscar);

		JButton btnListar = new JButton("Listar");
		btnListar.setBackground(Color.WHITE);
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cliente = new ClienteStream();
				String cadena= cliente.rellenar(0);
				String salida = "";

				StringTokenizer token = new StringTokenizer(cadena,",");
				int num = token.countTokens();
				String [] lista = new String [num];
				int i =0;

				while(token.hasMoreTokens()) {
					String temp = token.nextToken();
					lista [i]=temp;
					i++;
				}
				
				for(int j=0;j<lista.length;j++) {
					salida += lista[j] + "\n";
				}
				
				//JOptionPane.showMessageDialog(null, salida);
				txtPeliculasNombre.setText(salida);
				

			}
		});
		btnListar.setBounds(626, 271, 117, 25);
		getContentPane().add(btnListar);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setIcon(new ImageIcon( "src/imagen/Parar.png"));
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setBounds(461, 531, 205, 69);
		getContentPane().add(btnCancelar);
		
		txtPeliculasNombre = new JTextArea();
		txtPeliculasNombre.setBackground(Color.LIGHT_GRAY);
		txtPeliculasNombre.setBounds(200, 226, 380, 114);
		getContentPane().add(txtPeliculasNombre);
		
		JLabel lblColorTitulo = new JLabel("");
		lblColorTitulo.setIcon(new ImageIcon(Interfaz.class.getResource("/imagen/color.jpeg")));
		lblColorTitulo.setBounds(0, 698, 370, 44);
		getContentPane().add(lblColorTitulo);
		ImageIcon imagen2 = new ImageIcon(getClass().getResource("/imagen/color.jpeg"));
		Icon icono2 = new ImageIcon(imagen2.getImage().getScaledInstance(lblColorTitulo.getWidth(),lblColorTitulo.getHeight(),Image.SCALE_DEFAULT));
		lblColorTitulo.setIcon(icono2);
		
		lblColotTitulo2 = new JLabel("");
		lblColotTitulo2.setIcon(new ImageIcon(Interfaz.class.getResource("/imagen/color.jpeg")));
		lblColotTitulo2.setBounds(475, 698, 359, 44);
		getContentPane().add(lblColotTitulo2);
		ImageIcon imagen3 = new ImageIcon(getClass().getResource("/imagen/color.jpeg"));
		Icon icono3 = new ImageIcon(imagen2.getImage().getScaledInstance(lblColotTitulo2.getWidth(),lblColotTitulo2.getHeight(),Image.SCALE_DEFAULT));
		lblColotTitulo2.setIcon(icono3);
		
		lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Interfaz.class.getResource("/imagen/logoNetCom.PNG")));
		lblLogo.setBounds(326, 11, 175, 94);
		getContentPane().add(lblLogo);
		ImageIcon imagen4 = new ImageIcon(getClass().getResource("/imagen/logoNetCom.PNG"));
		Icon icono4 = new ImageIcon(imagen4.getImage().getScaledInstance(lblLogo.getWidth(),lblLogo.getHeight(),Image.SCALE_DEFAULT));
		lblLogo.setIcon(icono4);


	}





	public static void main(String[] args) {

		Interfaz capturar = new Interfaz();
		capturar.setBounds(0,0,850,775);
		capturar.setVisible(true);
		capturar.setLocationRelativeTo(null); //Aparezaca al centro de pantalla
		capturar.setResizable(true); //no deja modificar el tamaño de la interfaz
	}
	public void actionPerformed(ActionEvent arg0) {
		cliente = new ClienteStream();

		cliente.enviarVideo("Vengadores");
	}
}