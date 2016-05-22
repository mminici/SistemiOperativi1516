package it.sisop1516.appelli.laghetto;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LaghettoLC extends Laghetto {
	
	private Lock l=new ReentrantLock();
	private Condition c=l.newCondition();
	
	public LaghettoLC(int minPesci,int maxPesci,int numPesci)
	{	
		if(!(numPesci>minPesci && numPesci<maxPesci)){throw new IllegalArgumentException();}
		this.minPesci=minPesci;
		this.maxPesci=maxPesci;
		this.numPesci=numPesci;
		numAddetti=0;
		numPescatori=0;
	}
	
	@Override
	public void inizia(int t) {
		if(t==0)
		{
			//pescatore vuole iniziare a pescare
			try
			{
			l.lock();
			while(numPesci==minPesci && numAddetti>0)
			{
				c.await();
			}
			System.out.println("Persona tipo#"+t+"sta iniziando ad operare");
			numPescatori++;
			}catch(InterruptedException e){}finally{l.unlock();}
		}
		else
		{
			//addetto vuole iniziare a ripopolare
			try
			{
			l.lock();
			while(numPesci>maxPesci-10 && numPescatori>0)
			{
				c.await();
			}
			System.out.println("Persona tipo#"+t+"sta iniziando ad operare");

			numAddetti++;
			}catch(InterruptedException e){}finally{l.unlock();}
		}
		
	}

	@Override
	public void finisci(int t) {
		if(t==0)
		{
			//pescatore ha finito di pescare
			try
			{
			l.lock();
			System.out.println("Persona tipo#"+t+"sta finendo di operare");
			numPesci--;
			numPescatori--;
			if(numPescatori==0){c.signalAll();}
			/*
			 * potrebbe avere senso creare una coda di pescatori ed una di addetti?
			 * Quando si finisce l'operazione, si controlla il numPesci e in base al
			 * valore si risvegliano tutti, si risveglia solo la coda degli addetti
			 * o solo quella dei pescatori.
			 */
			}finally{l.unlock();}
		}
		else
		{
			//addetto ha finito di ripopolare
			try
			{
				l.lock();
				System.out.println("Persona tipo#"+t+"sta finendo di operare");
				numPesci+=10;
				numAddetti--;
				if(numAddetti==0){c.signalAll();}
			}finally{l.unlock();}
		}
		
	}
	
	public static void main(String[] args)
	{
		Laghetto l=new LaghettoLC(50,200,100);
		l.test(40, 5);
	}

}
