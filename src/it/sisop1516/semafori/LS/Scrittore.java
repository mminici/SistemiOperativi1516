package it.sisop1516.semafori.LS;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Scrittore implements Runnable {
	
	private MemoriaCondivisa mem;
	
	private static final int STOP=15;
	private static final int MIN_SCRITTURA=4;
	private static final int MAX_SCRITTURA=10;
	private static final int MIN_FAIALTRO=1;
	private static final int MAX_FAIALTRO=3;
	private Random r=new Random();
	
	public Scrittore(MemoriaCondivisa m){
		this.mem=m;
	}
	
	@Override
	public void run() {
		int cont=0;
		try{
			while(cont!=STOP)
			{
				mem.inizioScrittura();
				scrivi();
				mem.fineScrittura();
				faiAltro();
				cont++;
			}
		}catch(InterruptedException e){}
		
	}

	private void faiAltro() throws InterruptedException {
//		System.out.println("Scrittore #"+Thread.currentThread().getId()+" fa altro...");
		attendi(MIN_FAIALTRO,MAX_FAIALTRO);
	}

	private void scrivi() throws InterruptedException {
//		System.out.println("Scrittore #"+Thread.currentThread().getId()+" scrive...");
		attendi(MIN_SCRITTURA,MAX_SCRITTURA);	
	}
	
	private void attendi(int min, int max) throws InterruptedException {
		TimeUnit.SECONDS.sleep(r.nextInt(max-min+1)+min);
	}
	
	
}

