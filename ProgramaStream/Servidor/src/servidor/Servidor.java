package servidor;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.apache.xmlrpc.WebServer;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

public class Servidor {

	public String buscarPelicula(String pelicula) {
		
		System.out.println(pelicula);
		String sFichero = "/home/jorge/Documentos/streaming/"+pelicula+".mp4";
		File fichero = new File(sFichero);

		if (fichero.exists()) {
			System.out.println("Existe");
			return "Existe";
			
		}
		else {
			System.out.println("No Existe");
			return "No Existe";
		}

	}

	public String enviarPeliculas(int num) {

		
		String[] ficheros = null;
		String pelis = "";
		
		if(num == 0) {
			File dir = new File("/home/jorge/Documentos/streaming");
			ficheros = dir.list();
			
			if (ficheros == null)
				System.out.println("No hay ficheros en el directorio especificado");
			else { 
				for (int x=0;x<ficheros.length;x++)
					pelis += ficheros[x]+",";
			}

			
		}
		return pelis;
		

	}


    public int ejecutarVLC(String pelicula) {
    	
    	//vlc -vvv /home/jorge/Documentos/streaming/video1.mp4 --sout '#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:rtp{sdp=rtsp://:8554/JQ} '
    	
    	System.out.println (pelicula);
    	String simple = " ' ";
    	System.out.print(simple);
    	try {
    		String [] cmd = {"vlc","-vvv" ,"/home/jorge/Documentos/streaming/"+pelicula+".mp4",":sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:rtp{sdp=rtsp://:8554/JQ} "}; //Comando de apagado en windows
    		Runtime.getRuntime().exec(cmd);
    		return 0;
    	} catch (IOException ioe) {
    		System.out.println (ioe);
    	}
    	
    	return 0;
    }
    
  


	public static void main (String [] args) {
		try {

			WebServer servidor = new WebServer(8080);
			System.out.println("Servidor Iniciado....");			
			servidor.start();
			servidor.addHandler("ejecutar", new Servidor());

		} catch (Exception exception) {
			System.err.println("JavaServer: " + exception.toString());
		}
	}

}