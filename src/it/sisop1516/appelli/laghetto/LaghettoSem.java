package it.sisop1516.appelli.laghetto;

import java.util.concurrent.Semaphore;

public class LaghettoSem extends Laghetto {
	
	private Semaphore mutex=new Semaphore(1);
	private Semaphore pesciPescabili;
	private Semaphore pesciInseribili;
	private Semaphore occupato=new Semaphore(0);
	
	public LaghettoSem(int minPesci,int maxPesci,int numPesci)
	{	
		if(!(numPesci>minPesci && numPesci<maxPesci)){throw new IllegalArgumentException();}
		this.minPesci=minPesci;
		this.maxPesci=maxPesci;
		this.numPesci=numPesci;
		this.pesciPescabili=new Semaphore(numPesci-minPesci);
		this.pesciInseribili=new Semaphore(maxPesci-numPesci);
		numAddetti=0;
		numPescatori=0;
	}
	
	@Override
	public void inizia(int t) {
		if(t==0)
		{
		try {
			if(numAddetti==0)
			{
			pesciPescabili.acquire();
			mutex.acquire();
			}
			else
			{
			pesciPescabili.acquire();
			occupato.acquire();
//			pesciPescabili.acquire();
			mutex.acquire();	
			}
			System.out.println("Persona tipo #"+t+" sta iniziando ad operare");
			numPescatori++;
			mutex.release();
			} catch (InterruptedException e) {}
		}
		else
		{
			try {
				if(numPescatori==0)
				{
				pesciInseribili.acquire(10);
				mutex.acquire();
				}
				else
				{
				pesciInseribili.acquire(10);
				occupato.acquire();
//				pesciInseribili.acquire(10);
				mutex.acquire();	
				}
				System.out.println("Persona tipo #"+t+" sta iniziando ad operare");
				numAddetti++;
				mutex.release();
				} catch (InterruptedException e) {}
		}
		
	}

	@Override
	public void finisci(int t) {
		if(t==0)
		{
		try {
			mutex.acquire();
			System.out.println("Persona tipo #"+t+" sta finendo di operare");
			numPesci--;
			numPescatori--;
			pesciInseribili.release();
			if(numPescatori==0){occupato.release();}
			mutex.release();
		} catch (InterruptedException e) {}
		}
		else
		{
			try {
				mutex.acquire();
				System.out.println("Persona tipo #"+t+" sta finendo di operare");
				numPesci+=10;
				numAddetti--;
				pesciPescabili.release(10);
				if(numAddetti==0){occupato.release();}
				mutex.release();
			} catch (InterruptedException e) {}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException
	{	
		System.out.println("Soluzione Sem");
		Thread.sleep(1000);
		Laghetto l=new LaghettoSem(50,200,100);
		l.test(40, 5);
	}

}
