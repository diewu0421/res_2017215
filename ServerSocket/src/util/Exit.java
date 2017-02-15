package util;

import java.io.Closeable;
import java.io.IOException;

public class Exit{
	
	public static void closeAll(Closeable... closeables){
		for (Closeable closeable : closeables) {
			try {
				closeable.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
