package it.sisop1516.esercitazioni;

import it.sisop1516.cc.AtomicIntegerCC;
import it.sisop1516.cc.ContoCorrente;
import it.sisop1516.cc.Correntista;
import it.sisop1516.cc.SyncCC;

public class TestCC {

	public static void main(String[] args) throws InterruptedException {
		int depositoIniziale=10000000;
		ContoCorrente cc=new SyncCC(depositoIniziale);
		int numCorrentisti=2000;
		int importo=100;
		int numOperazioni=5000;
		long currentTime=System.nanoTime();
		testContoCorrente(cc, numCorrentisti,importo,numOperazioni);
		currentTime=System.nanoTime()-currentTime;
		System.out.println("Versione Sync");
		System.out.println("Tempo impiegato: "+currentTime+" nanosecondi");
		if(cc.getDeposito()==depositoIniziale){System.out.println("Corretto.");}
		else if(cc.getDeposito()!=depositoIniziale){System.out.println("Errore, importo attuale: "+cc.getDeposito()+" invece di "+depositoIniziale);}
		System.out.println();
		ContoCorrente cc1=new AtomicIntegerCC(depositoIniziale);
		currentTime=System.nanoTime();
		testContoCorrente(cc1,numCorrentisti,importo,numOperazioni);
		currentTime=System.nanoTime()-currentTime;
		System.out.println("Versione Atomic");
		System.out.println("Tempo impiegato: "+currentTime+" nanosecondi");
		if(cc.getDeposito()==depositoIniziale){System.out.println("Corretto.");}
		else if(cc.getDeposito()!=depositoIniziale){System.out.println("Errore, importo attuale: "+cc.getDeposito()+" invece di "+depositoIniziale);}
		
	}
	
//	private static void testContoCorrente(AtomicIntegerCC cc, int numCorrentisti, int importo, int numOperazioni) throws InterruptedException {
//		CorrentistaAtomic[] correntisti= new CorrentistaAtomic[numCorrentisti];
//		for(int i=0;i<numCorrentisti;i++)
//		{
//			correntisti[i]=new CorrentistaAtomic(cc,importo,numOperazioni);
//		}
//		Thread[] threadCorrentisti=new Thread[numCorrentisti];
//		for(int i=0;i<numCorrentisti;i++)
//		{
//			threadCorrentisti[i]=new Thread(correntisti[i]);
//			threadCorrentisti[i].start();
//		}
//		for(int i=0;i<numCorrentisti;i++)
//		{
//			threadCorrentisti[i].join();
//		}
//	}
	
	private static void testContoCorrente(ContoCorrente cc, int numCorrentisti, int importo, int numOperazioni) throws InterruptedException {
		Correntista[] correntisti= new Correntista[numCorrentisti];
		for(int i=0;i<numCorrentisti;i++)
		{
			correntisti[i]=new Correntista(cc,importo,numOperazioni);
		}
		Thread[] threadCorrentisti=new Thread[numCorrentisti];
		for(int i=0;i<numCorrentisti;i++)
		{
			threadCorrentisti[i]=new Thread(correntisti[i]);
			threadCorrentisti[i].start();
		}
		for(int i=0;i<numCorrentisti;i++)
		{
			threadCorrentisti[i].join();
		}
	}

}
