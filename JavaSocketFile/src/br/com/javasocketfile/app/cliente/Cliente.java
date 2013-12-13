package br.com.javasocketfile.app.cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.plaf.OptionPaneUI;

import br.com.javasocketfile.app.bean.FileMessage;

public class Cliente {
	
	private Socket socket;
	private ObjectOutputStream outputStream;
	 
	public Cliente() throws IOException{
		this.socket = new Socket("localhost", 5555);
		this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		
		new Thread(new ListenerSocket(socket)).start();
		
		menu();
	}
	
	private void menu() throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Digite seu nome: ");
		String nome = scanner.nextLine();
		//adicionando nome do cliente na lista de clientes
		this.outputStream.writeObject(new FileMessage(nome));
		
		int option = 0;
		
		while(option != -1){
			System.out.print("1 - Sair | 2 - Enviar :");
			option = scanner.nextInt();
			//se ==2 uma mensagem será enviada
			if (option == 2){
				send(nome);
			}else if (option == 1){
				System.exit(0);
			}else{
				System.out.println("Digite apenas 1 ou 2 ");
			}
		}
		
	}

	private void send(String nome) throws IOException {
		// TODO Auto-generated method stub

		JFileChooser fileChooser = new JFileChooser();
		int opt = fileChooser.showOpenDialog(null);
		if (opt == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			this.outputStream.writeObject(new FileMessage(nome, file));
			
		} else {
			System.out.print("Operação cancelada");
		}
		
	}

	private class ListenerSocket implements Runnable {
		
		private ObjectInputStream inputStream;
		
		public ListenerSocket(Socket socket) throws IOException {
			this.inputStream = new ObjectInputStream(socket.getInputStream());
			
		}

		@Override
		public void run() {
			
			FileMessage message = null;
			
			try {
				//verificando se a mensagem não está vazia e convertendo a objeto da mensagem
				while((message = (FileMessage) inputStream.readObject()) != null) {
					
					//metodo de leitura e exibição da mensagem e arquivo
					System.out.println("\nVocê recebeu um arquivo de :"+ message.getCliente());
					System.out.println("O arquivo: " + message.getFile().getName());
					
					imprime(message);
					System.out.println("1 - Sair | 2- Enviar: ");
					}
				}catch (IOException e){
					e.printStackTrace();
					System.out.println("nao deu 1");
				}catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("nao deu 2");
				}
			
		}

		private void imprime(FileMessage message) throws IOException {
			// TODO Auto-generated method stub
			FileReader fileReader = new FileReader(message.getFile());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha;
			
			while((linha = bufferedReader.readLine()) != null) {
				System.out.println(linha);
			}
				
			 
		}
	}

	public static void main(String[] args) {
		try {
			new Cliente();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
