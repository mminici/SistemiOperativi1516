package it.sisop1516.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {
	private final static String ALGORITHM="AES";
	private final static String TRANSFORMATION="AES";
	
	public static void encrypt(String key,File inputFile,File outputFile) throws CryptoException
	{
		doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
	}//encrypt(String)
	
	public static void encrypt(Key key,File inputFile,File outputFile) throws CryptoException
	{
		doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
	}//encrypt(Key)
	
	public static void encrypt(byte[] key,File inputFile,File outputFile) throws CryptoException
	{
		doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
	}//encrypt(byte[])
	
	public static void decrypt(String key,File inputFile,File outputFile) throws CryptoException
	{
		doCrypto(Cipher.DECRYPT_MODE,key,inputFile,outputFile);
	}//decrypt(String)
	
	public static void decrypt(Key key,File inputFile,File outputFile) throws CryptoException
	{
		doCrypto(Cipher.DECRYPT_MODE,key,inputFile,outputFile);
	}//decrypt(Key)
	
	public static void decrypt(byte[] key,File inputFile,File outputFile) throws CryptoException
	{
		doCrypto(Cipher.DECRYPT_MODE,key,inputFile,outputFile);
	}//decrypt(byte[])
	
	public static String decrypt(String key,byte [] encrypted) throws CryptoException{
		return doCrypto(Cipher.DECRYPT_MODE,key,encrypted);
	}
	
	private static String doCrypto(int cipherMode,String key,byte[] input)  {
		try{
			//creo un oggetto chiave a partire dalla stringa di riferimento
			Key secretKey=new SecretKeySpec(key.getBytes(),ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey); //inizializzo il cipher con il relativo algoritmo crittografico
            return new String(cipher.doFinal(input));
            }catch(NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException ex){return "";}
	}
	
	private static void doCrypto(int cipherMode, byte[] b, File inputFile, File outputFile) throws CryptoException {
		try{
			Key secretKey=new SecretKeySpec(b,ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
            FileInputStream inputStream=new FileInputStream(inputFile);
            byte[] inputBytes=new byte[(int)inputFile.length()];
            inputStream.read(inputBytes);
            
            byte[] outputBytes=cipher.doFinal(inputBytes);
            FileOutputStream outputStream=new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
            inputStream.close();
            outputStream.close();
		}catch(NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex){ throw new CryptoException("Error encrypting/decrypting file", ex);}
		
	}//doCrypto
	private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
		try{
			Key secretKey=new SecretKeySpec(key.getBytes(),ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
            FileInputStream inputStream=new FileInputStream(inputFile);
            byte[] inputBytes=new byte[(int)inputFile.length()];
            inputStream.read(inputBytes);
    
            byte[] outputBytes=cipher.doFinal(inputBytes);
            FileOutputStream outputStream=new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
            inputStream.close();
            outputStream.close();
		}catch(NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex){ throw new CryptoException("Error encrypting/decrypting file", ex);}
		
	}//doCrypto
	
	private static void doCrypto(int cipherMode, Key secretKey, File inputFile, File outputFile) throws CryptoException {
		try{
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
            FileInputStream inputStream=new FileInputStream(inputFile);
            byte[] inputBytes=new byte[(int)inputFile.length()];
            inputStream.read(inputBytes);
            
            byte[] outputBytes=cipher.doFinal(inputBytes);
            FileOutputStream outputStream=new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
            inputStream.close();
            outputStream.close();
		}catch(NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex){ throw new CryptoException("Error encrypting/decrypting file", ex);}
		
	}//doCrypto
	
	public static void main(String [] args) throws IOException, CryptoException{
		File f=new File("/home/marco/informatic_probs.txt");
		File enc=new File("/home/marco/documentoImportante.txt");
		String key="0000000000000012";
		encrypt(key,f,enc);
		try{
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			Key key2=new SecretKeySpec( "0000000000000012".getBytes(),ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key2);
            FileInputStream inputStream=new FileInputStream(enc);
            byte[] inputBytes=new byte[(int)enc.length()];
            inputStream.read(inputBytes);
            
            byte[] outputBytes=cipher.doFinal(inputBytes);
            System.out.println(Arrays.toString(outputBytes));
            System.out.println(new String(outputBytes));
            inputStream.close();
         
		}catch(NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex){ throw new CryptoException("Error encrypting/decrypting file", ex);}
		
//		decrypt("0000000000000012",enc,new File("/home/marco/document.decrypted"));
		
	}
}
