package com.zlw.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketTest {
	public static List<ServerThread> list = new ArrayList();
	public static void main(String[] args) {
		OutputStream os = null;
		try {
			ServerSocket server = new ServerSocket(10091);
			//accept 这个方法是阻塞的  会一直都等待客户端的链接
			while(true){
				System.out.println("监听端口10091中...");
				Socket socket = server.accept();
				System.out.println(socket.getInetAddress());
				if (socket == null) {
					System.out.println("为空");
					continue;
				} 
				ServerThread thread = new ServerThread(socket);
				thread.start();
				list.add(thread);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
