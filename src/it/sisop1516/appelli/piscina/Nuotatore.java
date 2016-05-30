package it.sisop1516.appelli.piscina;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Nuotatore extends Thread {
		private Piscina piscina;
		private final int MIN_TEMPO_NUOTATA=30;
		private final int MAX_TEMPO_NUOTATA=60;
		private final int TEMPO_DOCCIA=20;
		private Random r=new Random();
		
		public void run(){
			if(piscina.aperta())
			{
				int corsia=piscina.getCorsia();
				int tempo=r.nextInt(MAX_TEMPO_NUOTATA-MIN_TEMPO_NUOTATA+1)+MIN_TEMPO_NUOTATA;
				int tempoResiduo=piscina.tempoAllaChiusura();
				if(tempo<tempoResiduo)
				{
					try {
						TimeUnit.MINUTES.sleep(tempo);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else
				{
					try {
						TimeUnit.MINUTES.sleep(tempoResiduo);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				piscina.lasciaCorsia(corsia);
				try {
					TimeUnit.MINUTES.sleep(TEMPO_DOCCIA);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
}
