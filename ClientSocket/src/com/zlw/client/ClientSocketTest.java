package com.zlw.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientSocketTest {
	public static void main(String[] args) {
		Socket socket = null;
		ByteArrayOutputStream bos = null;
		InputStream is = null;
		try {
			socket = new Socket(Inet4Address.getByName("192.168.10.102"),10091);
			OutputStream os = socket.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			is = socket.getInputStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = reader.readLine();
			System.out.println(message);
			
			System.out.println("请输入要发送的信息：");
			Scanner scanner = new Scanner(System.in);
			while(true){
				String next = scanner.next();
				System.out.println(next);
				 writer.write(next);
		         writer.newLine();
		         writer.flush();
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
		}
	}
}
