package it.sisop1516.appelli.camerieri;

public abstract class Buffer {
		
	protected int numPiatti;
	protected int maxPiatti;
	protected int tipo;
	protected final int CONTENITORE=0;
	protected final int SCOLAPIATTI=1;
	protected final int MAX_CONTENITORE=50;
	protected final int MAX_SCOLAPIATTI=30;
	
	public Buffer(int tipo){
		if(maxPiatti<1 || (tipo!=CONTENITORE && tipo!=SCOLAPIATTI)) throw new IllegalArgumentException();
		this.tipo=tipo;
		if(tipo==CONTENITORE)
		{
			maxPiatti=MAX_CONTENITORE;
		}
		else
		{
			maxPiatti=MAX_SCOLAPIATTI;
		}
		numPiatti=0;
	}
	
	protected abstract int get() throws InterruptedException;
	
	protected abstract void put() throws InterruptedException;
}
