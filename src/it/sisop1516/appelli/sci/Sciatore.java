package it.sisop1516.appelli.sci;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sciatore extends Thread {
		private final int TEMPO_MIN_DISCESA=10;
		private final int TEMPO_MAX_DISCESA=30;
		private int numMaglia;
		private int tempo;
		private Gara gara;
		private Random r=new Random();
		
		public Sciatore(Gara gara,int numMaglia){
			this.gara=gara;
			this.numMaglia=numMaglia;
		}
		
		public int getMaglia(){return numMaglia;}
		
		public int getTempo(){return tempo;}
		
		public void run(){
			gara.partenza(this);
			tempo=r.nextInt(TEMPO_MAX_DISCESA-TEMPO_MIN_DISCESA+1)+TEMPO_MIN_DISCESA;
			try {
				TimeUnit.SECONDS.sleep(tempo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int arrivo=gara.arrivo(this);
			System.out.println("Sciatore #"+this.getId()+" posizione temporanea "+arrivo+"°");
		}
}
