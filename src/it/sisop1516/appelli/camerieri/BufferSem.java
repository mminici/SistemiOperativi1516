package it.sisop1516.appelli.camerieri;

import java.util.concurrent.Semaphore;

public class BufferSem extends Buffer {
	
	private Semaphore possoPrelevare=new Semaphore(0);
	private Semaphore possoMettere;
	
	public BufferSem(int dimensione){
		super(dimensione);
		possoMettere=new Semaphore(maxPiatti);
	}
	
	@Override
	protected int get() throws InterruptedException {
		possoPrelevare.acquire();
		possoMettere.release();
		return 1;
	}

	@Override
	protected void put() throws InterruptedException {
		int x=1;
		if(tipo==CONTENITORE)
		{
			x=4;
		}
		possoPrelevare.release(x);
		possoMettere.acquire(x);
	}

}
