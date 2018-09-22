package pbl.Cliente;

import java.net.Socket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
               Random ran = new Random();
               int val = 0;
               int novo = 0;
               
	        String mensagem = "Cuidado, tem uma mensagem com dois trojans montados em cima dela.";
		byte[] pegarMensagem =  mensagem.getBytes();
		int porta = 12345;
		String ip = "localhost";
		
		//acessa o servidor através de um broadCast(?), à medida em que recebe um datagram do servidor. Disso ele extrae o IP dele.
		try {
                        novo = ran.nextInt(10);
                        val = val + novo;
                        
                        DatagramSocket cliente = new DatagramSocket();
			InetAddress endereçoDeIp = InetAddress.getByName(ip); 
			DatagramPacket pacote = new DatagramPacket(pegarMensagem, pegarMensagem.length, endereçoDeIp, porta); 
			//o pacote precisa de: DataPacket(bufferDaMensagem, Tamanho da mensagem, o endereço de ip, a porta)
			
			cliente.send(pacote);
			//é o que o cliente envia.	
			
			//Recebendo resposta do servidor, sim, da para fazer o cliente escutar.
			byte[] respostaDoServidor= new byte[1024];
			DatagramPacket recebendo = new DatagramPacket(respostaDoServidor, respostaDoServidor.length, endereçoDeIp, porta);
			cliente.receive(recebendo);
			
			String ipServidor = recebendo.getAddress().toString();
			System.out.println(ipServidor);
			//fechar o client
			cliente.close();
		}catch(Exception excecao)
		{
			System.out.println(excecao.toString());
		}
	      }
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

}
