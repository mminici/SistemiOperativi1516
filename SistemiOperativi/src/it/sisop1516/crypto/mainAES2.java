package it.sisop1516.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;



public class mainAES2 {

	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println("Scegli quanti thread usare");
		int numThread=900;
		int infBound=0; 
		int offset= (Integer.MAX_VALUE/numThread);
		int keyLength=16;
		File f1=new File("/home/marco/document.encryptedOK.txt");
		InputStream iss=new FileInputStream(f1);
		byte[] inputBytes1=new byte[(int) f1.length()];
		iss.read(inputBytes1);
		iss.close();
		byte[] output=new byte[50];
		System.out.println("Ogni chiave è lunga "+keyLength+" caratteri");
		LinkedList<Thread> list=new LinkedList<>();
		while(list.size()<numThread)
		{  //generate range of key
			list.addLast(new DecryptAES2(infBound,infBound+offset,keyLength,"SISOP1516",inputBytes1,output));
			System.out.println("ok "+infBound+":"+(infBound+offset));
			infBound=(infBound+offset+1);
			output=new byte[50];
		}
		System.out.println("Verranno lanciati "+list.size()+" threads");
		long currentTime=System.nanoTime();
		for(Thread t:list)
		{
			//starting each thread
			t.start();
		}
		ciclo: for(;;)
		{
			for(Thread t:list)
			{	
				if(t.isInterrupted() && ((DecryptAES2) t).getBool())
				{	
					for(Thread th:list){th.wait();}
					System.out.println("Chiave trovata: "+((DecryptAES2) t).getKey());
					currentTime=System.nanoTime()-currentTime;
					System.out.println("Calcolato dopo: "+TimeUnit.SECONDS.convert(currentTime,TimeUnit.NANOSECONDS)+" secondi");
					Thread.sleep(10000000);
					break ciclo;
				}
			}
		}
		System.out.println();
		System.out.println("COMPLIMENTI");

	}

}
