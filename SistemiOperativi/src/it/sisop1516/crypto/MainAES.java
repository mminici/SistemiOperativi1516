package it.sisop1516.crypto;


import java.nio.file.FileSystems;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import it.sisop1516.stringspace.GenericNumericStringSpace;

public class MainAES {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Scegli quanti thread usare");
		byte numThread=4;
		int infBound=0; 
		int offset= (Integer.MAX_VALUE/numThread);
		int keyLength=16;
		System.out.println("Ogni chiave è lunga "+keyLength+" caratteri");
		LinkedList<Thread> list=new LinkedList<>();
		while(list.size()<numThread)
		{  //generate range of key
			GenericNumericStringSpace space=new GenericNumericStringSpace(keyLength,infBound,infBound+offset);
			list.addLast(new DecryptFakeAES("SISOP1516",System.getProperty("user.home")+FileSystems.getDefault().getSeparator()+"document.encryptedOK.txt", space /*insert StringSpace */));
			System.out.println("ok "+infBound+":"+(infBound+offset));
			infBound=(infBound+offset+1);
		}
		System.out.println("Verranno lanciati "+list.size()+" threads");
		long currentTime=System.nanoTime();
		for(Thread t:list)
		{
			//starting each thread
			t.start();
		}
		while(true){
			for(Thread t:list)
			{
				if(t.isInterrupted() && ((DecryptFakeAES) t).getBool())
				{
					System.out.println("Complimenti la chiave è "+((DecryptFakeAES) t).getKey());
					currentTime=System.nanoTime()-currentTime;
					System.out.println("Calcolato dopo: "+TimeUnit.SECONDS.convert(currentTime,TimeUnit.NANOSECONDS)+" secondi");
					System.exit(0);
				}
			}
		}
	}

}
