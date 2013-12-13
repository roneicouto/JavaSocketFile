package br.com.javasocketfile.app.bean;

import java.io.File;
import java.io.Serializable;

public class FileMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1912721347021219938L;
	private String cliente;
	private File file;
	
	public FileMessage(String cliente) {
		// TODO Auto-generated constructor stub
	}
	
	public FileMessage(String cliente, File file){
		this.setCliente(cliente);
		this.setFile(file);
	}
	
	public FileMessage(){
		
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	

}
