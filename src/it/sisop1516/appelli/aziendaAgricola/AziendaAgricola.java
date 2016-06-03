package it.sisop1516.appelli.aziendaAgricola;

import java.util.concurrent.Semaphore;

public abstract class AziendaAgricola {
		protected static int NUM_INIZIALE_SACCHI=200; 
		protected static int COSTO_SACCO=3;
		protected int numSacchi;
		protected int incasso;
		protected int numClienti;
		protected Semaphore finito=new Semaphore(0);
		
		protected abstract void paga(int numSacchi);
		
		protected abstract void ritiraSacco();
		
		protected abstract void ricarica();
		
		protected void test(int numClienti) throws InterruptedException{
			Addetto addetto=new Addetto(this);
			Cliente[] clienti=new Cliente[numClienti];
			for(int i=0;i<numClienti;i++)
			{
				clienti[i]=new Cliente(this);
			}
			this.numClienti=numClienti;
			addetto.start();
			for(Thread t:clienti){t.start();}
			finito.acquire();
			System.out.println("L'incasso di giornata è stato di "+incasso+" euro");
		}
}
