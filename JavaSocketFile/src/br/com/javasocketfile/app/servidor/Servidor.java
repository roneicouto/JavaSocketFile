package br.com.javasocketfile.app.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.javasocketfile.app.bean.FileMessage;

public class Servidor {
	
	public static final Entry<String, ObjectOutputStream> kv = null;
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
			// iniciamos a mensagem como vazia.
			FileMessage message = null;
			
			try {
				//verificando se a mensagem não está vazia e convertendo a objeto da mensagem
				while((message = (FileMessage) inputStream.readObject()) != null){
					streamMap.put(message.getCliente(), outputStream);
					
					//para não enviar mensagens vazias
					//apenas a conexão está estabelecida
					if(message.getFile() != null){
						for (Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet());
							//evitar para que o cliente que enviou a mensagem não receba a mensagem
							if (!message.getCliente().equals(kv.getKey())){
								kv.getValue().writeObject(message);
							}
					}  
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				streamMap.remove(message.getCliente());
				System.out.println(message.getCliente() + "desconectou-se!");
			}
			
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
		public static void main(String[] args) {
			new Servidor();
		}
	}
