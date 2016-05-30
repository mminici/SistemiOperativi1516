package it.sisop1516.appelli.supermercato;


public abstract class Cassa {
	
	protected Cassiere[] cassieric; 
	protected int numClienti;
	
	protected abstract int getID();

	protected abstract void consegnaProdotti(int id, int p);

	protected abstract int segnalaCassaLibera(int id);

	protected abstract void congedaCliente(int id);
	
	protected abstract boolean finito();
	
	
}