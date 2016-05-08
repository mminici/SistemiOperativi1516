package it.sisop1516.semafori.LS;

import java.util.concurrent.Semaphore;

public class MemoriaCondivisaSem extends MemoriaCondivisa {
	
	private int numLettori=0;
	private Semaphore scrittori=new Semaphore(1);
	private Semaphore lettori=new Semaphore(1);
	
	
	public static void main(String[] args) {
		MemoriaCondivisa mem=new MemoriaCondivisaSem();
		mem.test(10,4);

	}

	@Override
	public void inizioLettura() throws InterruptedException {
		/*
		 * Se sono il primo lettore devo mettere rosso il semaforo 
		 * degli scrittori.È necessaria mutua esclusione tra lettori e scrittori
		 */
		lettori.acquire();
		/*
		 * È necessario garantire la mutua esclusione tra i lettori
		 * nell'esecuzione di questo codice, pena il rischio di stallo tra lettori.
		 * ATTENZIONE: 
		 * Più lettori possono leggere contemporaneamente ma un solo lettore 
		 * alla volta può eseguire il metodo inizioLettura(), è ben diverso!
		 */
		if(numLettori==0){scrittori.acquire();}
		numLettori++;
		lettori.release();
		
	}

	@Override
	public void inizioScrittura() throws InterruptedException {
		scrittori.acquire();
		
	}

	@Override
	public void fineLettura() throws InterruptedException {
		lettori.acquire();
		numLettori--;
		if(numLettori==0){scrittori.release();}
		lettori.release();
		
	}

	@Override
	public void fineScrittura() throws InterruptedException {
		scrittori.release();
		
	}

}
