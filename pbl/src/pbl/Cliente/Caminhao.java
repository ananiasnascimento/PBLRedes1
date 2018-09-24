/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.Cliente;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Nias
 */
public class Caminhao implements Runnable{
	
	private int nivel = 0;
	private int capacidade = 0;
	private List<Lixeira> lixeiras;
    private Socket socket;

    private BufferedReader in;
    private PrintStream out;

    private boolean inicializado;
    private boolean executando;

    private Thread  thread;

    public Caminhao(String endereco, int porta) throws Exception{
       inicializado = false;
       executando   = false;

       open(endereco, porta);
    }
    
    public int getNivel() {
    	return nivel;
    }
    
    public int getCapacidadeLixeira() {
    	return capacidade;
    }
    
    public void setNivel(int nivel) {
    	this.nivel = nivel;
    }
    
    public void setCapacidade(int capacidade) {
    	this.capacidade = capacidade;
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

    public void send(int nivelLixeira) {
       out.println(nivelLixeira);
    }


    @Override
    public void run() {

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

       Caminhao caminhao = new Caminhao("localhost", 2525);

       System.out.println("Conexãoo estabelecida com sucesso ...");

       caminhao.start();

       Scanner scanner = new Scanner(System.in);

       while (true) {

           System.out.print("Digite o nivel da lixeira: ");
           int nivelLixeira = caminhao.getNivel();

           if (!caminhao.isExecutando()) {
                break;
           }

           caminhao.send(nivelLixeira);
          
       }

       System.out.println("Encerrando cliente ...");

       caminhao.stop();

      }

public List<Lixeira> getLixeiras() {
	return lixeiras;
}

public void setLixeiras(List<Lixeira> lixeiras) {
	this.lixeiras = lixeiras;
}

}
