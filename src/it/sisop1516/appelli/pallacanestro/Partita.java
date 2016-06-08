package it.sisop1516.appelli.pallacanestro;


public abstract class Partita {
		
		protected final int GIOCATORI_PER_SQUADRA=5;
		protected final int NUM_SQUADRE=2;
		protected int[] punteggio=new int[NUM_SQUADRE];
		protected Giocatore[][] squadre=new Giocatore[NUM_SQUADRE][GIOCATORI_PER_SQUADRA];
		protected int squadraInPossesso,giocatoreInPossesso,passaggiConsecutivi;
		
		protected abstract void iniziaPartita() throws InterruptedException;
		
		protected abstract void terminaPartita() throws InterruptedException;
		
		protected abstract void aspettaPossesso(int squadra,int id) throws InterruptedException;

		protected abstract void effettuaAzione(Giocatore g) throws InterruptedException;
		
		protected void test(){
			Arbitro arbitro=new Arbitro(this);
			arbitro.run();
		}
}
