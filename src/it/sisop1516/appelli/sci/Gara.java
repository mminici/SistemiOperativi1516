package it.sisop1516.appelli.sci;

public abstract class Gara {
		protected final int TEMPO_PROX_TURNO=3;
		protected int numeroSciatori;
		protected int[] tempi; //vettore colonna, con numero di righe=numero di sciatori
		protected int prossimoSciatore; //indica il numero di maglia del prossimo sciatore a scendere
		
		protected abstract void partenza(Sciatore s);
		
		protected abstract int arrivo(Sciatore s);
		
		protected abstract boolean prossimo();
		
		protected void test(){
			Addetto addetto=new Addetto(this);
			Sciatore[] sciatori=new Sciatore[numeroSciatori];
			for(int i=0;i<numeroSciatori;i++)
			{
				sciatori[i]=new Sciatore(this,i);
			}
			for(int i=0;i<sciatori.length;i++){sciatori[i].start();}
			addetto.start();
		}
}
