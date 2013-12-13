package br.com.javasocketfile.app.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
	
	public class ListenerSocket implements Runnable {
		private ObjectOutputStream outputStream;
		private ObjectInputStream inputStream;
		
		public ListenerSocket(Socket socket) throws IOException{
			this.outputStream = new ObjectOutputStream(socket.getOutputStream());
			this.inputStream = new ObjectInputStream(socket.getInputStream());
			
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}


	private ServerSocket serversocket;
	private Socket socket;
	//chave String para o nome do cliente, e Object para as mensagens
	private Map<String, ObjectOutputStream> streamMap = new HashMap<String , ObjectOutputStream>();
	
	
	public Servidor() {

		try {
			serversocket = new ServerSocket(5555);
			//confirmação de sucesso do servidor
			System.out.println("Servidor Ativo!");
			
			//criar variavel para o objeto socket da conexão
			socket = serversocket.accept();
			
			new Thread(new ListenerSocket(socket)).start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	

}
	}
