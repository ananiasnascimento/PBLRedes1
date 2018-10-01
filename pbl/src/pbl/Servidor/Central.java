/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import pbl.Cliente.Lixeira;

/**
 *
 * @author Nias
 */
public class Central {
    ArrayList<Lixeira> lixeiras = new ArrayList();
    public static void main(String[] args) throws IOException //psvm ctrl+space	
    {                
                Central central = new Central();
                try {
                    central.udp();
                    central.tcp();
                    
			
		}catch(Exception excecao)
		{
			System.out.println(excecao.toString());
		}
    }
    public void tcp() throws Exception {
        System.out.println("Iniciando Servidor.");
        
        ServidorTCP servidor = new ServidorTCP(2525);
        servidor.start();
        
        System.out.println("Pressione ENTER para encerrar o servidor.");
        new Scanner(System.in).nextLine();
        System.out.println("Encerrando Servidor");
        servidor.stop();
    }
    
    public void udp() throws IOException, Exception {
                Lixeira l = new Lixeira();
                System.out.println("Servidor On\nEsperando Lixeiras...");
                Scanner scanner = new Scanner(System.in);
                DatagramSocket servidor = new DatagramSocket(12345);
                int i = 0;
            while(true){
		try {
			
			byte[] receptor = new byte[1024];
			
			//O pacote que o servidor espera
			DatagramPacket bufferRecebimento = new DatagramPacket(receptor, receptor.length);
			servidor.receive(bufferRecebimento);
			
			//Mostra na tela a mensagem
			String mensagemNaTela = new String(bufferRecebimento.getData());
                        String array[] = mensagemNaTela.split("-");                         
                        int id = Integer.parseInt(array[2]);
                        double nivel = Double.parseDouble(array[0]);
                        l.setID(id);
                        l.setNivel(nivel);
                        lixeiras.add(l);
                        Iterator<Lixeira> iterator = lixeiras.iterator();
                        while (iterator.hasNext()) {                            
                            if(iterator.next().getID() == id){
                                iterator.next().setNivel(nivel);
                            }
                            else{                                
                                l.setNivel(nivel);                                
                                l.setID(id);
                                lixeiras.add(l);
                            }
                            i++;
                        }
                        
//                        String[] msg; Fazer o Split.
//                        msg = mensagemNaTela.split("-");
//                        msg[0] ;
//                        msg[1];
			System.out.println("Recebido quantidade de: " + array[0] + " da Lixeira" + array[1]);
			
//			//Envio de pacote como resposta
//			DatagramPacket respostaAoCliente = new DatagramPacket(receptor, receptor.length, bufferRecebimento.getAddress(), bufferRecebimento.getPort());
//			servidor.send(respostaAoCliente);
			
			
		}catch(Exception excecao)
		{
			System.out.println(excecao.toString());
		}
	
            }
    }
}
