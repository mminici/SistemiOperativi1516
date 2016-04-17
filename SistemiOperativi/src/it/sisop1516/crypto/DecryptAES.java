package it.sisop1516.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.crypto.KeyGenerator;


public class DecryptAES extends Thread {
	private byte[] range=new byte[2]; //indicates range of keys
	private byte[] goal; //decrypted byte[] we are looking for
	private byte[] key=new byte[16]; //key used to decrypt
	boolean found=false;
	private File encryptedDocument;
	private File decryptedDocument;
	private FileInputStream inputStream;
	public DecryptAES(byte range[],String text,String encryptedFile){
		this.range=range;
		goal=text.getBytes();
		encryptedDocument=new File(encryptedFile);
		decryptedDocument=new File(System.getProperty("user.home")+FileSystems.getDefault().getSeparator()+"document.decrypted");
		
	}
	public boolean getBool(){return found;}
	public byte[] getKey(){return key;}
	public void run(){
		for(byte i=range[0];i<range[1];i++)
		{
			//trying all keys in the range
			key[0]=i;
			for(byte j=range[0];j<range[1];j++)
			{
				key[1]=j;
				for(byte k=range[0];k<range[1];k++)
				{
					key[2]=k;
					for(byte l=range[0];l<range[1];l++)
					{
						key[3]=l;
						for(byte h=range[0];h<range[1];h++)
						{
							key[4]=h;
							for(byte g=range[0];g<range[1];g++)
							{
								key[5]=g;
								for(byte f=range[0];f<range[1];f++)
								{
									key[6]=f;
									for(byte d=range[0];d<range[1];d++)
									{
										key[7]=d;
										for(byte s=range[0];s<range[1];s++)
										{
											key[8]=s;
											for(byte a=range[0];s<range[1];s++)
											{
												key[9]=a;
												for(byte q=range[0];q<range[1];q++)
												{
													key[10]=q;
													for(byte w=range[0];w<range[1];w++)
													{
														key[11]=w;
														for(byte e=range[0];e<range[1];e++)
														{
															key[12]=e;
															for(byte r=range[0];r<range[1];r++)
															{
																key[13]=e;
																for(byte t=range[0];t<range[1];t++)
																{
																	key[14]=t;
																	for(byte y=range[0];y<range[1];y++)
																	{
																		key[15]=y;
																		try {
																			CryptoUtils.decrypt(key, encryptedDocument, decryptedDocument);
																		} catch (CryptoException e1){
																			e1.printStackTrace();
																		}
															
																		try {
																			inputStream=new FileInputStream(decryptedDocument);
																		} catch (FileNotFoundException e1) {
																			// TODO Auto-generated catch block
																			e1.printStackTrace();
																		}
																		
																		byte[] decryptedArray=new byte[(int)decryptedDocument.length()];
																		try {
																			inputStream.read(decryptedArray);
																			inputStream.close();
																		} catch (IOException e1) {
																			// TODO Auto-generated catch block
																			e1.printStackTrace();
																		}
																		if(decryptedArray.equals(goal))
																		{	
																			found=true;
																			this.interrupt();
																		}
																		
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	public static void main(String[] args) throws NoSuchAlgorithmException {
		//encrypting a file with a random Key
		KeyGenerator keyGen=KeyGenerator.getInstance("AES");
		keyGen.init(128);
	    Key key=keyGen.generateKey(); //generate 16 byte key
		String s= FileSystems.getDefault().getSeparator();
		File inputFile = new File(System.getProperty("user.home")+s+"documentoImportante.txt");
		File encryptedFile = new File(System.getProperty("user.home")+s+"documento.encrypted");
		try {
			//encrypt the important document
            CryptoUtils.encrypt(key, inputFile, encryptedFile);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
		System.out.println("Scegli quanti thread usare");
		int numThread=4;
		System.out.println("Verranno lanciati "+numThread+" threads");
		int infBound=-128;
		int offset= (256/numThread);
		LinkedList<Thread> list=new LinkedList<>();
		while(infBound<128){
			System.out.println("QUI");
			byte[] b={(byte)infBound,(byte) (infBound+offset)};
			list.addLast(new DecryptAES(b,"SISOP2015-2016",encryptedFile.getPath()));
			infBound= (infBound+offset);
		}
		long currentTime=System.nanoTime();
		for(Thread t:list){ t.start();}
		while(true){
			for(Thread t:list)
			{
				if(t.isInterrupted() && ((DecryptAES) t).getBool())
				{
					System.out.println("Complimenti la chiave è "+Arrays.toString(((DecryptAES) t).getKey()));
					currentTime=System.nanoTime()-currentTime;
					System.out.println("Calcolato dopo: "+TimeUnit.SECONDS.convert(currentTime,TimeUnit.NANOSECONDS)+" secondi");
					System.exit(0);
				}
			}
		}
		
	}//main
	
	
}
