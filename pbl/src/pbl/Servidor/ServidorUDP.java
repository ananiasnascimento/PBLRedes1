/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 *
 * @author Nias
 */
public class ServidorUDP {
    


	public static void main(String[] args) throws IOException //psvm ctrl+space	
	{
                System.out.println("Servidor On\nEsperando Lixeiras...");
                Scanner scanner = new Scanner(System.in);
                DatagramSocket servidor = new DatagramSocket(12345);
                while(true){
		try {
			
			byte[] receptor = new byte[1024];
			
			//O pacote que o servidor espera
			DatagramPacket bufferRecebimento = new DatagramPacket(receptor, receptor.length);
			servidor.receive(bufferRecebimento);
			
			//Mostra na tela a mensagem
			String mensagemNaTela = new String(bufferRecebimento.getData());
//                        String[] msg; Fazer o Split.
//                        msg = mensagemNaTela.split("-");
//                        msg[0] ;
//                        msg[1];
			System.out.println("Recebido quantidade de: " + mensagemNaTela + " da Lixeira");
			
//			//Envio de pacote como resposta
//			DatagramPacket respostaAoCliente = new DatagramPacket(receptor, receptor.length, bufferRecebimento.getAddress(), bufferRecebimento.getPort());
//			servidor.send(respostaAoCliente);
			
			
		}catch(Exception excecao)
		{
			System.out.println(excecao.toString());
		}
//                String scan = scanner.next();
//                if(scan == "Encerrar"){
//                        servidor.close();
//                    }
	}
        }
}


