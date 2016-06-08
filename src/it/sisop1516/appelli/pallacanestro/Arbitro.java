package it.sisop1516.appelli.pallacanestro;

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
			partita.iniziaPartita();
			TimeUnit.MINUTES.sleep(DURATA_PARTITA);
			partita.terminaPartita();
			}catch(InterruptedException e){}
		}
}
