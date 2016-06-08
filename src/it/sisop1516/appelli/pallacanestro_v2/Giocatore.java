package it.sisop1516.appelli.pallacanestro_v2;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Giocatore extends Thread {
	private int squadra,probSuccesso;
	private final int MIN_POSSESSO=1;//sec
	private final int MAX_POSSESSO=5;//sec
	private final int MIN_SUCCESSO=30;
	private final int MAX_SUCCESSO=60;
	private Random r=new Random();
	private Partita partita;
	
	public Giocatore(Partita partita,int squadra){
		this.partita=partita;
		this.squadra=squadra;
		probSuccesso=r.nextInt(MAX_SUCCESSO-MIN_SUCCESSO+1)+MIN_SUCCESSO;
	}
	
	public void run(){
		while(true)
		{
			try
			{
			if(!partita.riceviPalla(squadra)) return;
			TimeUnit.SECONDS.sleep(r.nextInt(MAX_POSSESSO-MIN_POSSESSO+1)+MIN_POSSESSO);
			if(!partita.lanciaPalla(squadra, probSuccesso)) return;
			}catch(InterruptedException ex){}
		}
	}
}
