package it.sisop1516.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;

import it.sisop1516.stringspace.ExceededStringBoundException;
import it.sisop1516.stringspace.StringSpace;

public class DecryptFakeAES extends Thread {
		private StringSpace currentKey;
		private boolean found=false;//true if decrypted file is equal to goal
		private byte[] goal;
		private File decryptedDocument,encryptedDocument;
		private InputStream inputStream;
		private int cont=0;
		public DecryptFakeAES(String text,String encryptedFile,StringSpace s){
			System.out.println("QUI");
			this.currentKey=s;
			goal=text.getBytes();
			encryptedDocument=new File(encryptedFile);
			decryptedDocument=new File(System.getProperty("user.home")+FileSystems.getDefault().getSeparator()+"document.decrypted");
		}
		
		public boolean getBool(){return found;}
		
		public String getKey(){return currentKey.getString();}
		
		public void run(){
				while(true){
				try{
				System.out.println();
				System.out.println("checking... "+currentKey.getString()+" key");
				System.out.println();
				try {
					CryptoUtils.decrypt(currentKey.getString(), encryptedDocument, decryptedDocument);
				} catch (CryptoException e) {/* possible comments here */ }
				try {
					inputStream=new FileInputStream(decryptedDocument);
				} catch (FileNotFoundException e1) {/* possible comments here */ }
				byte[] decryptedArray=new byte[(int)decryptedDocument.length()];
				try {
					inputStream.read(decryptedArray);
					inputStream.close();
				} catch (IOException e1) {/* possible comments here */ }
				if(decryptedArray.equals(goal))
				{	
					//if correctKey is found then interrupt current thread
					found=true;
					this.interrupt();
				}
				cont++;
				currentKey.nextString();
			}catch(ExceededStringBoundException ex)
				{
					System.out.println(); 
					System.out.println("Fine analisi del thread #"+this.getId()); 
					System.out.println("controllate: "+cont+" chiavi");
					System.out.println(); 
				}
		}
	}
}
