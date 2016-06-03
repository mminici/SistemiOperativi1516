package it.sisop1516.appelli.aziendaAgricola;

import java.util.concurrent.TimeUnit;

public class Addetto extends Thread {
		private AziendaAgricola azienda;
		private long tempoRicarica=10;
		
		public Addetto(AziendaAgricola azienda){
			this.azienda=azienda;
		}
		
		public void run(){
			azienda.ricarica();
			try {
				TimeUnit.MINUTES.sleep(tempoRicarica);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
