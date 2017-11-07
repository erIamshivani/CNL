package swselectiverepeat;

import java.net.*; 
import java.io.*;
import java.util.*;

public class SRclient{
	
	static Socket connection;

	public static void main(String a[]) throws SocketException {
		
		try {
			int v[] = new int[10]; 
			int n = 0;
			Random rands = new Random();
			int rand = 0; 
 			 
			InetAddress addr = InetAddress.getByName("Localhost");
			System.out.println(addr);
			connection = new Socket(addr, 8011);
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			DataInputStream in = new DataInputStream(
					connection.getInputStream());
			
			//first read frame size
			int p = in.read();
			System.out.println("No of frame is:" + p);
			
			//read array elements
			for (int i = 0; i < p; i++) {
				v[i] = in.read();
				System.out.println(v[i]);
				//g[i] = v[i];
			}

			//generate random element n make its flag -1
			rand = rands.nextInt(p);//FRAME NO. IS RANDOMLY GENERATED			
			v[rand] = -1;
			for (int i = 0; i < p; i++)
			 {
					System.out.println("Received frame is: " + v[i]);

				}

			//ask for -1 ka element
			for (int i = 0; i < p; i++)
				if (v[i] == -1) {
					System.out.println("Request to retransmit packet no "
							+ (i+1) + " again!!");
					n = i;
					out.write(n);
					out.flush();
				}

			System.out.println();
			
				v[n] = in.read();
				System.out.println("Received frame is: " + v[n]);
			
			

			System.out.println("Completed!");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
