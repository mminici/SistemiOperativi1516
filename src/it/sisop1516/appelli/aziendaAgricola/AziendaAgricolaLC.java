package it.sisop1516.appelli.aziendaAgricola;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AziendaAgricolaLC extends AziendaAgricola {
	
	private Lock cassa=new ReentrantLock(true);
	private Lock magazzino=new ReentrantLock(true);
	private Condition aspettaCassa=cassa.newCondition();
	private Condition aspettaMagazzino=magazzino.newCondition();
	private LinkedList<Cliente> codaCassa=new LinkedList<>();
	private LinkedList<Cliente> codaMagazzino=new LinkedList<>();
	private static int CLIENTE=0;
	private static int ADDETTO=1;
	private static int CASSA=2;
	private static int MAGAZZINO=3;
	
	public AziendaAgricolaLC(){
		numSacchi=NUM_INIZIALE_SACCHI;
		incasso=0;
	}
	
	@Override
	protected void paga(int numSacchi) {
		Cliente c=(Cliente) Thread.currentThread();
		try
		{
			cassa.lock();
			codaCassa.add(c);
			while(!mioTurno(CLIENTE,CASSA))
			{
				aspettaCassa.await();
			}
			incasso+=(numSacchi*COSTO_SACCO);
			System.out.println("Il cliente #"+c.getId()+" ha pagato "+numSacchi+" sacchi");
			codaCassa.remove();
			aspettaCassa.signalAll();
		} 
		catch (InterruptedException e) {}
		finally{cassa.unlock();}
	}

	@Override
	protected void ritiraSacco() {
		Cliente c=(Cliente) Thread.currentThread();
		try
		{
			magazzino.lock();
			codaMagazzino.add(c);
			while(!mioTurno(CLIENTE,MAGAZZINO))
			{
				aspettaMagazzino.await();
			}
			numSacchi--;
			System.out.println("Il cliente #"+c.getId()+" ha ritirato un sacco");
			System.out.println("Rimangono "+numSacchi+" sacchi nel magazzino");
			codaMagazzino.remove();
			aspettaMagazzino.signalAll();
		} 
		catch (InterruptedException e){}
		finally{magazzino.unlock();}
		
	}

	@Override
	protected void ricarica() {
		try
		{
			magazzino.lock();
			while(!mioTurno(ADDETTO,MAGAZZINO))
			{	
				if(fineGiornata){return;}
				aspettaMagazzino.await();
			}
			numSacchi=NUM_INIZIALE_SACCHI;
			System.out.println("L'addetto ha ricaricato il magazzino");
			aspettaMagazzino.signalAll();
		} 
		catch (InterruptedException e) {}
		finally{magazzino.unlock();}
	}
	
	private boolean mioTurno(int persona,int posto){
		try
		{
			magazzino.lock();
			if(persona==ADDETTO)
			{	
				return numSacchi==0;
			}
			else if(persona==CLIENTE)
			{	
				Cliente c=(Cliente)Thread.currentThread();
				if(posto==CASSA)
				{
					return codaCassa.getFirst()==c;
				}
				else if(posto==MAGAZZINO)
				{
					return numSacchi>0 && codaMagazzino.getFirst()==c;
				}
				else
				{
					throw new IllegalArgumentException();
				}
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}finally{magazzino.unlock();}
	}
	
	public static void main(String[] args) throws InterruptedException{
		AziendaAgricola a=new AziendaAgricolaLC();
		a.test(100);
	}
}
