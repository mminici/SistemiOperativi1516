package it.sisop1516.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;

public class CryptoException extends Exception { 
  
	private static final long serialVersionUID = -8964574259027224192L;

	public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
	public static void main(String[] args) throws IOException{
		File f=new File("/home/marco/documentoImportante.txt");
		InputStream is=new FileInputStream(f);
		byte[] inputBytes=new byte[(int)f.length()];
		is.read(inputBytes);
		System.out.println(Arrays.toString(inputBytes));
		System.out.println();
		String s="SISOP1516"+"\n";
		byte[] b=s.getBytes();
		System.out.println(Arrays.toString(b));
		is.close();
		File f1=new File("/home/marco/document.encryptedOK.txt");
		InputStream iss=new FileInputStream(f1);
		byte[] inputBytes1=new byte[(int) f1.length()];
		iss.read(inputBytes1);
		System.out.println();
		System.out.println(Arrays.toString(inputBytes1));
		byte[] bbb=new byte[2];
		bbb[0]=1; bbb[1]=2;
		inputBytes1=bbb;
		System.out.println(Arrays.toString(inputBytes1));
		iss.close();
	}
}
