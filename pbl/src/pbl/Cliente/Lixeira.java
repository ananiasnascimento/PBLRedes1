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

public class Lixeira {
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
		this.nivel = 0.0;
	}
	public double encherLixeira(double quantidade){             
            while(true){
                try{
                    Thread.sleep(500*(int)(Math.random()*5+1));
                
                }
                catch(InterruptedException exc){
                    exc.printStackTrace();
                }
                quantidade = quantidade + 5;
                return quantidade;
            }
        }
            
	public Lixeira() throws Exception {
	              
	}
           
	public static void main(String[] args) throws Exception {
            Lixeira lixeira = new Lixeira(); // Instancia a nova lixeira
                
                
                
	    Scanner scanner = new Scanner(System.in); 
            int porta = 12345; // Definida a porta de comunicação. O servidor tem que escutar na mesma porta.
            String ip = "localhost"; // Define o IP para comunicação com o servidor
            String mensagem = null;
            String msg = null;
            DatagramSocket cliente = new DatagramSocket(); // 
            InetAddress endereçoDeIp = InetAddress.getByName(ip); // obtém o endereço de IP                
            System.out.println("Informe o ID da Lixeira. ");
            int id = scanner.nextInt();
            lixeira.setID(id);
            System.out.println("Lixeira: " +lixeira.getID()+ " criada.");
            System.out.println("Informe a capacidade da Lixeira. ");
            double capacidade = 0;
            capacidade = scanner.nextDouble();
            lixeira.setCapacidade(capacidade);
            System.out.println("Capacidade da Lixeira: " + lixeira.getCapacidade());
            double novo = 0.0;
            //acessa o servidor através de um broadCast(?), à medida em que recebe um datagram do servidor. Disso ele extrai o IP dele.
                
                    
                
            while(true){
                try {
                                    
                            
                    double val = 0.0;
                    
                    msg = String.valueOf(lixeira.encherLixeira(lixeira.getNivel()));
                    novo = Double.parseDouble(msg);
                    lixeira.setNivel(novo);                    
                    mensagem = msg + "-" + lixeira.ID;                    
                    byte[] pegarMensagem =  mensagem.getBytes();
                           
                    DatagramPacket pacote = new DatagramPacket(pegarMensagem, pegarMensagem.length, endereçoDeIp, porta); 
                    //o pacote precisa de: DataPacket(bufferDaMensagem, Tamanho da mensagem, o endereço de ip, a porta)
                    cliente.send(pacote); // Envia o pacote para o servidor
                    System.out.println("Enviado:" +mensagem+ " para o servidor");
                    Thread.sleep(500); // Tempo de espera até o envio do próximo pacote
                    if(lixeira.getCapacidade() == novo){
                        System.out.println("Lixeira cheia. ");
                        String acao = scanner.next();
                        if("Esvaziar".equals(acao)){
                            lixeira.esvaziaLixeira();
                        }
                        else{
                            System.out.println("Comando errado");
                        }
                    }    
                    }catch(Exception excecao){
                            System.out.println(excecao.toString());
                    }
//                    String scan = scanner.next();                    
//                if(scan == "Encerrar"){
//                        cliente.close();
//                    }
	      
           }
        
	
    }
}
