package it.sisop1516.semafori.LS;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Lettore implements Runnable {
	
	private MemoriaCondivisa mem;
	
	private static final int STOP=10;
	private static final int MIN_LETTURA=2;
	private static final int MAX_LETTURA=5;
	private static final int MIN_FAIALTRO=1;
	private static final int MAX_FAIALTRO=3;
	private Random r=new Random();
	
	public Lettore(MemoriaCondivisa m){
		this.mem=m;
	}
	
	@Override
	public void run() {
		int cont=0;
		try{
			while(cont!=STOP)
			{
				mem.inizioLettura();
				leggi();
				mem.fineLettura();
				faiAltro();
				cont++;
			}
		}catch(InterruptedException e){}
		
	}

	private void faiAltro() throws InterruptedException {
//		System.out.println("Lettore #"+Thread.currentThread().getId()+" fa altro...");
		attendi(MIN_FAIALTRO,MAX_FAIALTRO);
	}

	private void leggi() throws InterruptedException {
//		System.out.println("Lettore #"+Thread.currentThread().getId()+" legge...");
		attendi(MIN_LETTURA,MAX_LETTURA);	
	}
	
	private void attendi(int min, int max) throws InterruptedException {
		TimeUnit.SECONDS.sleep(r.nextInt(max-min+1)+min);
	}
	
	
}
