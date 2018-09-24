/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.Servidor;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Nias
 */
public class Central implements Runnable{
    private ServerSocket server;
    
    private List<Atendente> atendentes;
    
    private boolean inicializado;
    private boolean executando;
    
    private Thread thread;
    
    public Central (int porta) throws Exception {
        atendentes = new ArrayList<Atendente>();
        
        inicializado = false;
        executando = false;
        
        open(porta);
    }
    
    private void open(int porta) throws Exception {
        server = new ServerSocket(porta);
        inicializado = true;
    }
    
    private void close() {
        for (Atendente atendente : atendentes){
           try{
               atendente.stop();
           }
           catch (Exception e){
               System.out.println(e);
           }
        }
        try{
            server.close();
        }
        catch(IOException e) {
            System.out.println(e);            
        }
        
        server = null;
        inicializado = false;
        executando = false;
    }
    
    public void start(){
        if(!inicializado || executando){
            return;
        }
        executando = true;
        thread = new Thread(this);
        thread.start();
    }
    
    /**
     *
     * @throws Exception
     */
    public void stop() throws Exception{
        executando = false;
        thread.join();
        
    }
    
    @Override
    public void run(){
        while(executando){
           try{
               server.setSoTimeout(2500);
               
               Socket socket = server.accept();
               
               System.out.println("Conexão Estabelecida.");
               
               Atendente atendente = new Atendente(socket);
               atendente.start();
               
               atendentes.add(atendente);
            }
           catch(SocketTimeoutException e){
           }
           catch (Exception e){
               System.out.println(e);
               break;
           }
        }
        close();
    }
    
    

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception{
        System.out.println("Iniciando Servidor.");
        
        Central servidor = new Central(2525);
        servidor.start();
        
        System.out.println("Pressione ENTER para encerrar o servidor.");
        new Scanner(System.in).nextLine();
        System.out.println("Encerrando Servidor");
        servidor.stop();
        // TODO code application logic here
    }
    
}
