package it.sisop1516.appelli.pallacanestro_v2;

public abstract class Partita {
	
	protected final int GIOCATORI_PER_SQUADRA=5;
	protected final int NUM_SQUADRE=2;
	protected final int PASSAGGI_AL_TIRO=3;
	protected int[] punteggio=new int[NUM_SQUADRE];
	protected int squadraInPossesso,passaggiConsecutivi;

	protected abstract boolean riceviPalla(int s) throws InterruptedException;
	
	protected abstract boolean lanciaPalla(int s,int p) throws InterruptedException;
	
	protected abstract void termina() throws InterruptedException;
	
	protected void test(int numGiocatori){
		Giocatore[] giocatori=new Giocatore[numGiocatori];
		for(int i=0;i<numGiocatori;i++)
		{	
			if(i<=4){giocatori[i]=new Giocatore(this,0);}
			else{giocatori[i]=new Giocatore(this,1);}
		}
		Arbitro arbitro=new Arbitro(this);
		arbitro.start();
		for(Thread t:giocatori) t.start(); 
	}
	
}
