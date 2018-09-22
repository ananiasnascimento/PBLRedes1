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
/**
 *
 * @author Nias
 */
public class ServidorUDP {
    


	public static void main(String[] args) throws IOException //psvm ctrl+space	
	{
		try {
			DatagramSocket servidor = new DatagramSocket();
			byte[] receptor = new byte[1024];
			
			//O pacote que o servidor espera
			DatagramPacket bufferRecebimento = new DatagramPacket(receptor, receptor.length);
			servidor.receive(bufferRecebimento);
			
			//Mostra na tela a mensagem
			String mensagemNaTela = new String(bufferRecebimento.getData());
			System.out.println(mensagemNaTela);
			
			//Envio de pacote como resposta
			DatagramPacket respostaAoCliente = new DatagramPacket(receptor, receptor.length, bufferRecebimento.getAddress(), bufferRecebimento.getPort());
			servidor.send(respostaAoCliente);
			
			servidor.close();
		}catch(Exception excecao)
		{
			System.out.println(excecao.toString());
		}
	}
}


