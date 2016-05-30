package it.sisop1516.appelli.piscina;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Piscina {
		protected final int NUM_CORSIE=5;
		protected final int MAX_PERSONE_CORSIA=4;
		protected boolean presenteIstruttore=false;
		protected final int MATTINA=0;
		protected final int POMERIGGIO=1;
		protected int mod;
		
		protected abstract int getMod();
		
		protected abstract void apriPiscina();
		
		protected abstract int tempoAllaChiusura();
		
		protected abstract int getCorsia();
		
		protected abstract void chiudiPiscina();
		
		protected abstract void lasciaCorsia(int corsia);
	    
		protected abstract boolean aperta();
		
		protected void test(int numeroNuotatori) throws InterruptedException
		{	
			Random r=new Random();
			Nuotatore[] nuotatori=new Nuotatore[numeroNuotatori];
			for(int i=0;i<nuotatori.length/3;i++)
			{
				TimeUnit.MINUTES.sleep(r.nextInt(5));
				nuotatori[i].start();
			}
			TimeUnit.HOURS.sleep(r.nextInt(1));
			for(int i=nuotatori.length/3;i<nuotatori.length;i++)
			{
				nuotatori[i].start();
			}
		}
		
}
