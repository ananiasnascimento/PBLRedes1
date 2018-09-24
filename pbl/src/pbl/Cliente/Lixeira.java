package pbl.Cliente;

import java.net.Socket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.util.Random;
import java.util.Scanner;

public class Lixeira implements Runnable {
	private int ID;
	private int nivel = 0;
	private int capacidade = 0;
	
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}
	public void esvaziaLixeira() {
		this.nivel = 0;
	}
	
	 	private Socket socket;

	    private BufferedReader in;
	    private PrintStream out;

	    private boolean inicializado;
	    private boolean executando;

	    private Thread  thread;

	   public Lixeira(String endereco, int porta) throws Exception{
	       inicializado = false;
	       executando   = false;

	       open(endereco, porta);
	    }

	    private void open(String endereco, int porta) throws Exception{
	       try {
	           socket = new Socket(endereco, porta); 

	           in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	           out = new PrintStream(socket.getOutputStream());

	           inicializado = true;
	       }
	       catch (Exception e) {
	           System.out.println(e);
	           close();
	           throw e;
	       }
	    }

	    private void close(){
	       if (in != null) {
	           try {
	                in.close();
	           }
	           catch (Exception e){
	               System.out.println(e);
	           }
	       }

	       if (out != null) {
	           try {
	              out.close();
	           }
	           catch (Exception e){
	              System.out.println(e);
	           }
	       }

	       if (socket != null) {
	           try {
	               socket.close();
	           }
	           catch (Exception e){
	               System.out.println(e);
	           }
	       }

	       in     = null;
	       out    = null;
	       socket = null;

	       inicializado = false;
	       executando   = false;

	       thread = null;

	    }

	    public void start() {
	       if (!inicializado || executando) {
	           return;
	       }

	       executando = true;
	       thread = new Thread(this);
	       thread.start();
	    }

	    public void stop() throws Exception{
	       executando = false;

	       if (thread != null) {
	           thread.join();
	       }
	    }

	    public boolean isExecutando() {
	       return executando;
	    }

	    public void send(int mensagem) {
	       out.println(mensagem);
	       out.flush();
	    }


	    public void run(){

	       while (executando) {
	           try {
	               socket.setSoTimeout(2500);               

	           }	          
	           catch (Exception e) {
	               System.out.println(e);
	               break;
	           }
	       }

	       close();


	    }

	   public static void main(String[] args) throws Exception {

	       System.out.println("Iniciando cliente ...");

	       System.out.println("Iniciando conexão com o servidor ...");

	       Lixeira cliente = new Lixeira("localhost", 2525);

	       System.out.println("Conexão estabelecida com sucesso ...");

	       cliente.start();

	       Scanner scanner = new Scanner(System.in);
	       int val = 0;

	       while (true) {	    	   
	    	   int mensagem = scanner.nextInt();
	    	   cliente.setNivel(mensagem);                      
	           val = cliente.getNivel();
	           cliente.send(val);
	           Thread.sleep(1000);
	           
	           if (!cliente.isExecutando()) {
	                break;
	           }	           
	       }
	       
	       System.out.println("Encerrando cliente ...");

	       cliente.stop();

	      }
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

}
