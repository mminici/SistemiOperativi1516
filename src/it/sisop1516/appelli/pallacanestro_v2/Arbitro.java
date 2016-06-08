package it.sisop1516.appelli.pallacanestro_v2;

import java.util.concurrent.TimeUnit;

public class Arbitro extends Thread {
	private Partita partita;
	private final int DURATA_PARTITA=3;//min
	
	public Arbitro(Partita partita){
		this.partita=partita;
	}
	
	public void run(){
		try
		{
			TimeUnit.MINUTES.sleep(DURATA_PARTITA);
			partita.termina();
		} catch (InterruptedException e) {}
	}
}
