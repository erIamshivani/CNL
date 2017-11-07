package swgobackn;

import java.io.*;
import java.net.*;
import java.util.*;

class testclient{

 public static void main(String args[])throws IOException
 {
	 InetAddress addr=InetAddress.getByName("Localhost");
	 System.out.println(addr);
 
	 Socket connection=new Socket(addr,5500);
 

	 BufferedInputStream in=new BufferedInputStream(connection.getInputStream());
	 DataOutputStream out=new DataOutputStream(connection.getOutputStream());
	 Scanner scr=new Scanner(System.in);// this will be used to accept i/p from console
 
 
	 System.out.println(".......Client........");
	 System.out.println("Connect");
	 System.out.println("Enter the number of frames to send");
	 int c=scr.nextInt();
 
	 out.write(c);
	 out.flush();
 
	 System.out.println("Enter the type of transmission\n1.Error\n0.No error");
	 int choice=scr.nextInt();
	 out.write(choice);

	 int check=0,i=0,j=0;
 
	 if(choice==0)
	 {
		 for(j=0;j<c;++j)
		 {
			 i=in.read();
			 System.out.println("Received frame no: "+i);
			 System.out.println("Sending acknowledgement for frame no: "+i);
			 out.write(i);
			 out.flush();
		 }
		 out.flush();
	 }
	 else
	 {
		 for(j=0;j<c;++j)
		 {
			 i=in.read();
			 if(i==check)
			 {
				 System.out.println("Received frame no: "+i);
				 System.out.println("Sending acknowledgement for frame no: "+i);
				 out.write(i);
				 ++check;
			 }
			 else
			 {
				 --j;
				 System.out.println("Discarded frame no: "+i);
				 System.out.println("Sending NEGATIVE ack");
				 out.write(-1);
			 }
			 out.flush();
		 }
	 }//end of else for error
 
	 in.close();
	 out.close();
	 System.out.println("Completed!");
 
 	}// end of main method
 }// end of main class
