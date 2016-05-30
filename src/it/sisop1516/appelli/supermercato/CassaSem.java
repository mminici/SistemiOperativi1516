package it.sisop1516.appelli.supermercato;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CassaSem extends Cassa {
	
	private int numClientiSoddisfatti;
	@SuppressWarnings("unused")
	private int numCasse;
	private Semaphore coda=new Semaphore(0,true);
	private Semaphore[] cassieri;
	private Semaphore mutex=new Semaphore(1);
	private boolean[] casse;
	
	public CassaSem(int numClienti,int numCasse){
		this.numClienti=numClienti;
		this.numCasse=numCasse;
		cassieric=new Cassiere[numCasse];
		cassieri=new Semaphore[numCasse];
		casse=new boolean[numCasse];
		for(int i=0;i<numCasse;i++)
		{
			
			Cassiere c=new Cassiere(i,this);
			cassieric[i]=c;
			cassieri[i]=new Semaphore(0);
			casse[i]=true;
		}
		numClientiSoddisfatti=0;
	}
	
	public Cassiere[] getCs(){return cassieric;}
	@Override
	public int getID() {
		int cliente=(int) Thread.currentThread().getId();
		try {
//			System.out.println("Cliente #"+cliente+" richiede ID cassa");
			coda.acquire();
//			System.out.println("La cassa può soddisfare la richiesta del cliente #"+cliente);
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0;i<casse.length;i++)
		{ 
			if(casse[i])
			{
				mutex.release(); 
				System.out.println("Il cliente #"+cliente+" si rivolgerà alla cassa #"+i);
				return i;
			}
		}
		mutex.release();
		throw new IllegalStateException();
	}

	@Override
	public void consegnaProdotti(int id, int p) {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("Il cliente #"+Thread.currentThread().getId()+" consegna i prodotti alla cassa #"+id);
		cassieric[id].setP(p); //consegno i prodotti al cassiere id
		mutex.release();
		cassieri[id].release();
	}

	@Override
	public int segnalaCassaLibera(int id) {
		try {
			mutex.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		casse[id]=true; //congendando il cliente, la cassa è di nuovo disponibile
		mutex.release();
		coda.release(); //trasmetto sullo schermo che una cassa è libera
		System.out.println("La cassa #"+id+" segnala la sua disponibilità");
		try {
			//cassiere aspetta che il cliente consegni i prodotti
			cassieri[id].acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		casse[id]=false;
		return cassieric[id].getP();
	}

	@Override
	public void congedaCliente(int id) {
		try {
			mutex.acquire();
			numClientiSoddisfatti++;
			System.out.println("La cassa #"+id+" congeda il cliente");
			mutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean finito() {
		return numClientiSoddisfatti>=numClienti;
		}
	
	public static void main(String ... args){
		int numClienti=100;
		int numCassieri=10;
		Random r=new Random();
		Cassa cassa=new CassaSem(numClienti,numCassieri);
		Cassiere[] c=((CassaSem) cassa).getCs();
		Cliente[] clienti=new Cliente[numClienti];
		for(int i=0;i<numClienti;i++) clienti[i]=new Cliente(cassa,r.nextInt(Cliente.MAX_PRODOTTI));
		for(Thread t:c) t.start();
		for(Thread t:clienti) t.start();
	}
}
