package it.sisop1516.cc;

import java.util.Random;

public class CorrentistaAtomic implements Runnable {
	private static int MIN_ATTESA=1;
	private static int MAX_ATTESA=3;
	
	private Random random=new Random();
	private AtomicIntegerCC cc;
	private int importo;
	private int numOperazioni;
	
	public CorrentistaAtomic(AtomicIntegerCC cc,int importo,int numOperazioni){
		if(numOperazioni%2!=0){throw new RuntimeException();}
		this.cc=cc;
		this.importo=importo;
		this.numOperazioni=numOperazioni;
	}
	
	private void attesaCasuale() throws InterruptedException {
		Thread.sleep((random.nextInt(MAX_ATTESA-MIN_ATTESA+1)+MIN_ATTESA));
	}
	@Override
	public void run() {
		try{
			for(int i=0;i<numOperazioni;i++)
			{
				attesaCasuale();
				if(i%2==0){cc.deposita(importo);}
				else{cc.preleva(importo);}
			}
		}catch(InterruptedException ex){System.out.println("Correntista #"+Thread.currentThread().getId()+" ha terminato le sue operazioni");}
		
	}
	

}
