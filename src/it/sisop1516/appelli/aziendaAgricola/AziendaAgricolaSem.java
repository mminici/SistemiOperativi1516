package it.sisop1516.appelli.aziendaAgricola;

import java.util.concurrent.Semaphore;

public class AziendaAgricolaSem extends AziendaAgricola {
	
	private Semaphore ricarica=new Semaphore(0);
	private Semaphore mutex=new Semaphore(1);
	private Semaphore sacchi;
	private Semaphore cassa=new Semaphore(1,true);
	
	public AziendaAgricolaSem(){
		numSacchi=NUM_INIZIALE_SACCHI;
		incasso=0;
		sacchi=new Semaphore(numSacchi,true);
	}
	@Override
	protected void paga(int numSacchi){
		try 
		{
			cassa.acquire();
		} catch (InterruptedException e) {}
		incasso+=(numSacchi*COSTO_SACCO);
		cassa.release();
	}

	@Override
	protected void ritiraSacco() {
		try
		{
			sacchi.acquire();
			mutex.acquire();
		}catch(InterruptedException ex){}
		numSacchi--;
		if(numSacchi==0)ricarica.release();
		mutex.release();
		sacchi.release();
	}

	@Override
	protected void ricarica() {
		try
		{
			ricarica.acquire();
			mutex.acquire();
		}catch(InterruptedException e){}
		numSacchi=NUM_INIZIALE_SACCHI;
		mutex.release();
	}
	
	public static void main(String [] args) throws InterruptedException{
		AziendaAgricola a=new AziendaAgricolaSem();
		a.test(100);
	}
}
