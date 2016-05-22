package it.sisop1516.appelli.laghetto;


public class Addetto extends Persona {
	public Addetto(int min,int max,int p,Laghetto l)
	{	
		this.l=l;
		minTempo=min;
		maxTempo=max;
		pausa=p;
	}
	
	private void popola() throws InterruptedException{opera();}
	
	public void run(){
		while(true)
		{	
			try{
			l.inizia(1);
			popola();
			l.finisci(1);
			pausa();
			}catch(InterruptedException e){}
		}
	}
}
