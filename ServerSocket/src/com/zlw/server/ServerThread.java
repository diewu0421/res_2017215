package com.zlw.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import util.Exit;

public class ServerThread extends Thread{
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	public ServerThread(Socket socket) {
		this.socket = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		welcome();
		
		while(true){
			try {
				String message = reader.readLine();
				if (message != null) {
					sendToOther(message);
				} else {
					Thread.sleep(1000);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println("断开链接");
				ServerSocketTest.list.remove(this);
				sendToOther(socket.getInetAddress() + "已经推出聊天室！");
				Exit.closeAll(socket,writer,reader);
				break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void sendToOther(String message) {
		// TODO Auto-generated method stub
		for (ServerThread thread : ServerSocketTest.list) {
			if (thread != null) {
				thread.sendMessage(message);
			}
		}
	}
	
	private void sendMessage(String message){
		if (message == null) {
			System.out.println("message为空 " );
			return ;
		}
		try {
			writer.write(message);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void welcome() {
		// TODO Auto-generated method stub
		sendToOther("欢迎" + socket.getInetAddress() + "进入聊天室!");
	}
}
