package it.sisop1516.semafori.PC;

import java.util.concurrent.TimeUnit;

public class Consumatore implements Runnable {
	private Buffer buffer;
	private static final int MAX_OPERAZIONI=10;
	
	public Consumatore(Buffer b){
		buffer=b;
	}
	
	public void run(){
		int cont=0;
		try{
			while(cont<MAX_OPERAZIONI)
			{
				int i=buffer.get();
				consuma(i);
			}
		}catch(InterruptedException e){}
	}
	
	private void consuma(int x) throws InterruptedException{
		TimeUnit.SECONDS.sleep(x);
	}
}
