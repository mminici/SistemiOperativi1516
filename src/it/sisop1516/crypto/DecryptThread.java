package it.sisop1516.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DecryptThread extends Thread {
		private int infBound,supBound;
		private String currentKey;
		private String zeros="0000000000000000";
		private String toFind="SISOP1516";
		private byte[] toDecrypt;
		private static boolean found=false;
		public static boolean getBool(){return found;}
		public DecryptThread(int infBound,int supBound,File toDecrypt) throws IOException{
			this.infBound=infBound;
			this.supBound=supBound;
			FileInputStream is=new FileInputStream(toDecrypt);
            this.toDecrypt=new byte[(int)toDecrypt.length()];
            is.read(this.toDecrypt);
            is.close();   
		}
		public void run(){
			for(int i=infBound;i<=supBound && !found;i++)
			{
				String key=String.valueOf(i);
				StringBuilder sb=new StringBuilder(16);
				sb.append(zeros);
				sb.replace(16-key.length(), 16, key);
				currentKey=sb.toString();
				//chiave costruita
				String text="";
				try {
					text=CryptoUtils.decrypt(currentKey, toDecrypt);
				} catch (CryptoException e) {}
				//cercare se è presente la stringa SISOP1516
				if(text.contains(toFind))
				{
					found=true;
					System.out.println("Chiave trovata: "+currentKey);
				}
				
			}
			System.out.println("Il thread #"+this.getId()+" ha finito le sue operazioni");
		}
		public static void main(String [] args) throws IOException{
			int numThread=4;
			int infBound=0;
			int offset=(Integer.MAX_VALUE/numThread);
			File toDecrypt=new File("/home/marco/document.encryptedOK.txt");
			DecryptThread[] dt=new DecryptThread[numThread];
			for(int i=0;i<numThread;i++)
			{
				dt[i]=new DecryptThread(infBound,infBound+offset,toDecrypt);
				infBound=infBound+offset+1;
			}
			for(Thread t:dt)
			{
				t.start();
			}
			/*
			 * altro main
			 */
//			FileInputStream is=new FileInputStream(toDecrypt);
//            byte[] b=new byte[(int)toDecrypt.length()];
//            is.read(b);
//            is.close();
//            try {
//				System.out.println(CryptoUtils.decrypt("0000001534211010", b));
//			} catch (CryptoException e) {}
		}
}
