package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

public class ClienteStream {

	private final static String server_url =
			"http://192.168.1.101:8080/";
	private final static String ipServer="192.168.1.101";
	private final static String puertoRtsp = "8554";
	private final static String nick = "JQ";
	public static void main (String [] args) throws IOException{
		Interfaz inter = new Interfaz();
		inter.setVisible(true);
		rellenar(0);
		//reproducir();
	}
	
	public static void reproducir() throws IOException {
		
		String[] datos = {"vlc","-vvv","rtsp://"+ipServer+":8554/JQ"};
		Process ejecucion = Runtime.getRuntime().exec(datos);
		String linea;
		BufferedReader salida = new BufferedReader(new InputStreamReader(ejecucion.getInputStream()));
		while((linea = salida.readLine()) != null) {
			System.out.println(linea);
		}
		salida.close();
	}
	
	public static void enviar(String nombre) {
		try {
			XmlRpcClient server=new XmlRpcClient(server_url);
			Vector parametros = new Vector();
			parametros.addElement(new String(nombre) );

			String resultado = (String) server.execute("ejecutar.buscarPelicula", parametros);

			System.out.println("Resultado de la  Resta: "+resultado);
			JOptionPane.showMessageDialog(null, resultado);
			

		} catch (XmlRpcException exception) {
			System.err.println("JavaClient: XML-RPC Fault #" + Integer.toString(exception.code) + ": " + exception.toString());
		} catch (Exception exception) {
			System.err.println("JavaClient: " + exception.toString());
		}
	}
	
	public static String rellenar(int envio) {
		try {		
			XmlRpcClient server=new XmlRpcClient(server_url);
			Vector parametros = new Vector();
			
			parametros.addElement(new Integer(0) );
			String resultado = (String) server.execute("ejecutar.enviarPeliculas", parametros);
			
			
			return resultado;

		} catch (XmlRpcException exception) {
			System.err.println("JavaClient: XML-RPC Fault #" + Integer.toString(exception.code) + ": " + exception.toString());
			return null;
		} catch (Exception exception) {
			System.err.println("JavaClient: " + exception.toString());
			return null;
		}
	}

	public static int enviarVideo(String video) {
		
		try {		
			XmlRpcClient server=new XmlRpcClient(server_url);
			Vector parametros = new Vector();
			
			parametros.addElement(new String (video) );
			int resultado = (Integer) server.execute("ejecutar.ejecutarVLC", parametros);
			
			System.out.println(resultado);
			if(resultado == 0) {
				System.out.println("Si se ejecuta");
				reproducir();
			}
			
			return resultado;

		} catch (XmlRpcException exception) {
			System.err.println("JavaClient: XML-RPC Fault #" + Integer.toString(exception.code) + ": " + exception.toString());
			return 0;
		} catch (Exception exception) {
			System.err.println("JavaClient: " + exception.toString());
			return 0;
		}
	}


}
