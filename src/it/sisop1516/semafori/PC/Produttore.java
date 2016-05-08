package it.sisop1516.semafori.PC;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Produttore implements Runnable {
		
	private Buffer buffer;
	private static final int MAX_RANDOM=10;
	private static final int MIN_PRODUZIONE=1;
	private static final int MAX_PRODUZIONE=5;
	private static final int MAX_OPERAZIONI=10;
	private Random r=new Random();
	
	public Produttore(Buffer b){
		buffer=b;
	}
	
	public void run(){
		int cont=0;
		try{
			while(cont<MAX_OPERAZIONI)
			{	
				int i=produci();
				buffer.put(i);
			}
		}catch(InterruptedException e){}
	}

	private int produci() throws InterruptedException {
		attendi(MIN_PRODUZIONE,MAX_PRODUZIONE);
		return r.nextInt(MAX_RANDOM);
	}
	
	private void attendi(int min,int max) throws InterruptedException{
		TimeUnit.SECONDS.sleep(r.nextInt(max-min+1)+min);
	}
}
