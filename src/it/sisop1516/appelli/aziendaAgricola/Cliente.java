package it.sisop1516.appelli.aziendaAgricola;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cliente extends Thread {
		private AziendaAgricola azienda;
		private int maxSacchetti=10;
		private int minSacchetti=1;
		private long tempoRitiro=1;
		
		public Cliente(AziendaAgricola azienda){
			this.azienda=azienda;
		}
		
		public void run(){
			Random r=new Random();
			int sacchi=r.nextInt(maxSacchetti-minSacchetti+1)+minSacchetti;
			azienda.paga(sacchi);
			while(sacchi>0)
			{
				azienda.ritiraSacco();
				try {
					TimeUnit.MINUTES.sleep(tempoRitiro);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sacchi--;
			}
			azienda.numClienti--;
			if(azienda.numClienti==0){azienda.finito.release();}
		}
}
