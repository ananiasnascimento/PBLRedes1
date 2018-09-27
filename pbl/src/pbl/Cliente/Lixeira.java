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
	private double nivel = 0;
	private double capacidade = 0;
        
	public int getID(){
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public double getNivel() {
		return nivel;
	}
	public void setNivel(double nivel) {
		this.nivel = nivel;
	}
	public double getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(double capacidade) {
		this.capacidade = capacidade;
	}
	public void esvaziaLixeira() {
		this.nivel = 0;
	}
	public double encherLixeira(double val, double novo){             
            Random ran = new Random();
            novo = ran.nextInt(10);
            val = val + novo;
            if(val == this.getCapacidade()){
                System.out.println("Lixeira Cheia. ");
                return 0.0;
            }
            return val;
        }
	 	private Socket socket;

	    private BufferedReader in;
	    private PrintStream out;

	    private boolean inicializado;
	    private boolean executando;

	    private Thread  thread;

	   public Lixeira() throws Exception{
	              
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
                Lixeira lixeira = new Lixeira();
                
                
                
	        Scanner scanner = new Scanner(System.in);
		int porta = 12345;
		String ip = "localhost";
		String mensagem = null;
                String msg = null;
                DatagramSocket cliente = new DatagramSocket();
                InetAddress endereçoDeIp = InetAddress.getByName(ip);
                System.out.println("Lixeira: " +lixeira.getID()+ " criada.");
                System.out.println("Informe o ID da Lixeira. ");
                int id = scanner.nextInt();
                lixeira.setID(id);
                System.out.println("Informe a capacidade da Lixeira. ");
                double capacidade = 0;
                capacidade = scanner.nextDouble();
                lixeira.setCapacidade(capacidade);
                System.out.println("Capacidade da Lixeira: " + lixeira.getCapacidade());
		//acessa o servidor através de um broadCast(?), à medida em que recebe um datagram do servidor. Disso ele extrae o IP dele.
                
                    
                
		while(true){
                    try {
                                    
                            
                            double val = 0.0;
                double novo = 0.0;
                            msg = String.valueOf(lixeira.encherLixeira(val,novo));
                            mensagem = msg + "-" + lixeira.ID;
                            byte[] pegarMensagem =  mensagem.getBytes();
                            
                             
                            DatagramPacket pacote = new DatagramPacket(pegarMensagem, pegarMensagem.length, endereçoDeIp, porta); 
                            //o pacote precisa de: DataPacket(bufferDaMensagem, Tamanho da mensagem, o endereço de ip, a porta)

                            cliente.send(pacote);
                            System.out.println("Enviado:" +mensagem+ " para o servidor");
                            Thread.sleep(500);
                            
                            //é o que o cliente envia.	

                            //Recebendo resposta do servidor, sim, da para fazer o cliente escutar.
    //			byte[] respostaDoServidor= new byte[1024];
    //			DatagramPacket recebendo = new DatagramPacket(respostaDoServidor, respostaDoServidor.length, endereçoDeIp, porta);
    //			cliente.receive(recebendo);
    //			
    //			String ipServidor = recebendo.getAddress().toString();
    //			System.out.println(ipServidor);
    			//fechar o client
    			
                    }catch(Exception excecao)
                    {
                            System.out.println(excecao.toString());
                    }
//                    String scan = scanner.next();                    
//                if(scan == "Encerrar"){
//                        cliente.close();
//                    }
	      
           }
        
	
    }
}
