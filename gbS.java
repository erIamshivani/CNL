package swgobackn;

import java.io.*;
import java.net.*;

class testserver
{
	public static void main(String args[])throws IOException
	{
		System.out.println("Server Socket created!");

		ServerSocket ss=new ServerSocket(5500);

		Socket client=new Socket();
		client=ss.accept();

		BufferedInputStream in=new BufferedInputStream(client.getInputStream());
		DataOutputStream out=new DataOutputStream(client.getOutputStream());

		System.out.println("Received request for sending frames");
		int p=in.read();

		boolean f[]=new boolean[p];

		int pc=in.read();
		System.out.println("Sending frames...");

		//errorless
		if(pc==0)
	{
			for(int i=0;i<p;++i)
			{
				System.out.println("Sending frame "+i);
				out.write(i);
				out.flush();
				System.out.println("Waiting for acknowledgement");
				try
				{
					Thread.sleep(7000);
				}
				catch(Exception e){
					System.out.println(e);
				}

				int a=in.read();	//rec ack
				System.out.println("Received acknowledgement for frame "+i+" as "+a);
			}
			out.flush();
	}
		//error
		else
		{
			for(int i=0;i<p;++i)
			{
				if(i==2)
				{
					System.out.println("Sending frame no "+i);
				}
				else
				{
					System.out.println("Sending frame no "+i);
					out.write(i);
					out.flush();
					System.out.println("Waiting for acknowledgement ");
					try
					{
						Thread.sleep(7000);
					}
					catch(Exception e){}
 
					int a=in.read();	

					if(a!=255)		//when a -1
					{
						System.out.println("received ack for frame no: "+i+" as "+a);
						f[i]=true;
					}
				}// end of inner else
}// end of for

// check which frames have not been ack

			for(int a=0;a<p;++a)
			{
				if(f[a]==false)
				{
					System.out.println("Resending frame "+a);
					out.write(a);
					out.flush();
					System.out.println("Waiting for ack ");
					try
					{
						Thread.sleep(5000);
					}
					catch(Exception e){}
 
					int b=in.read();
					System.out.println("Received ack for frame no: "+a+" as "+b);
					f[a]=true;
				}
			}
			out.flush();
		}// end of else which is for error 
 
		in.close();
		out.close();
		client.close();
		ss.close();
		System.out.println("Completed!");
 
	}// end main method
 }// end main class
