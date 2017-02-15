package com.zlw.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerReadThread extends Thread{

	private Socket socket ;
	public ServerReadThread(Socket socket){
		this.socket = socket;
	}
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		while(true){
			try {
				 InputStream is = socket.getInputStream();
				 byte[] bytes = new byte[1024];
                 int len = is.read(bytes);
                 StringBuffer buffer = new StringBuffer();
                 
                 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
