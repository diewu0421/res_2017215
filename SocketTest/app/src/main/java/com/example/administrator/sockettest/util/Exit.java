package com.example.administrator.sockettest.util;

import java.io.Closeable;
import java.io.IOException;

public class Exit{
	
	public static void closeAll(Closeable... closeables){
		for (Closeable closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
