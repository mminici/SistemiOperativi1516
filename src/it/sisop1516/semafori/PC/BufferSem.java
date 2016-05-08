package it.sisop1516.semafori.PC;

import java.util.concurrent.Semaphore;

public class BufferSem extends Buffer {

	private Semaphore s=new Semaphore(1);
	private Semaphore ciSonoElementi=new Semaphore(0);
	private Semaphore ciSonoPostiVuoti;
	
	public BufferSem(int dimensione) {
		super(dimensione);
		ciSonoPostiVuoti=new Semaphore(dimensione);
	}
	
	@Override
	public int get() throws InterruptedException {
		//@consumatore
		ciSonoElementi.acquire();
		s.acquire();
		System.out.println("Consumatore #"+Thread.currentThread().getId()+" consuma");
		int x=buffer[out];
		buffer[out]=-1; //simbolicamente svuoto la cella
		out=(out+1)%buffer.length;
		System.out.println("Consumatore #"+Thread.currentThread().getId()+" fine");
		s.release();
		ciSonoPostiVuoti.release();
		return x;
	}

	@Override
	public void put(int i) throws InterruptedException {
		//@produttore
		ciSonoPostiVuoti.acquire();
		s.acquire();
		System.out.println("Produttore #"+Thread.currentThread().getId()+" produce");
		buffer[in]=i;
		in=(in+1)%buffer.length;
		System.out.println("Produttore #"+Thread.currentThread().getId()+" fine");
		s.release();
		ciSonoElementi.release();
	}
	
	public static void main(String[] args){
		int dimensione=10;
		Buffer b=new BufferSem(dimensione);
		int numConsumatori=10;
		int numProduttori=3;
		b.test(numProduttori, numConsumatori);
	}

}
