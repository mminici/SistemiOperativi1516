package it.sisop1516.appelli.pallacanestro;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Giocatore extends Thread {
		private int id,squadra,probSuccesso;
		private final int MIN_POSSESSO=1;//sec
		private final int MAX_POSSESSO=5;//sec
		private final int MIN_SUCCESSO=30;
		private final int MAX_SUCCESSO=60;
		private Random r=new Random();
		private Partita partita;
		
		public Giocatore(Partita partita,int id,int squadra){
			this.partita=partita;
			this.id=id;
			this.squadra=squadra;
			probSuccesso=r.nextInt(MAX_SUCCESSO-MIN_SUCCESSO+1)+MIN_SUCCESSO;
		}
		
		public void run(){
			while(true)
			{	
				try
				{
					partita.aspettaPossesso(squadra,id);
					System.out.println("Giocatore squadra #"+squadra+" maglia -"+id+"- è in possesso");
					TimeUnit.SECONDS.sleep(r.nextInt(MAX_POSSESSO-MIN_POSSESSO+1)+MIN_POSSESSO);
					partita.effettuaAzione(this);
				}catch(InterruptedException e){break;}
			}
		}

		public int getNum(){return id;}
		
		public boolean provaAzione(){
			int successo=r.nextInt(100); //percentuale
			if(successo>probSuccesso){return false;}
			return true;
		}
		
		
}
